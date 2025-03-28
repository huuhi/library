package zhijianhu.libraryserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import zhijianhu.constant.MessageConstant;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.UserChangePasswordDTO;
import zhijianhu.dto.UserDTO;
import zhijianhu.dto.UserLoginDTO;
import zhijianhu.dto.UserPageQueryDTO;
import zhijianhu.entity.*;
import zhijianhu.exception.AccountLockedException;
import zhijianhu.exception.AccountNotFoundException;
import zhijianhu.exception.PasswordErrorException;
import zhijianhu.libraryserver.mapper.UserQuestionMapper;
import zhijianhu.libraryserver.service.*;
import zhijianhu.libraryserver.mapper.UsersMapper;
import org.springframework.stereotype.Service;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
* @author windows
* @description 针对表【users(用户信息表)】的数据库操作Service实现
* @createDate 2025-02-24 14:57:02
*/
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
   private final UsersMapper usersMapper;
   private final BooksService booksService;
//   因为借阅service注入我的bean,所以这里注入mapper
    private final UserQuestionMapper userQuestionService;

    private final BookClassesService classesService;
   private final BorrowRecordsService borrowRecordsService;
   @Lazy
    public UsersServiceImpl(UsersMapper usersMapper, BooksService booksService, UserQuestionMapper userQuestionService, BookClassesService classesService, BorrowRecordsService borrowRecordsService) {
        this.usersMapper = usersMapper;
        this.booksService = booksService;
        this.userQuestionService = userQuestionService;
        this.classesService = classesService;
        this.borrowRecordsService = borrowRecordsService;
    }

    @Override
    public Users login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        Users user = usersMapper.login(username);
        if(user==null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 加密密码

        if(!encoder.matches(password, user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if(Objects.equals(user.getStatus(), StatusConstant.DISABLE)){
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

//        如果可以到这里说明用户登录成功
        UserContext.setUserId(user.getId());
        user.setLastLogin(LocalDateTime.now());
        updateById(user);
        return user;
    }

    @Override
    public boolean register(UserDTO userDTO) {
        Users users = BeanUtil.copyProperties(userDTO, Users.class);
        String password = users.getPassword();
        String pwd = encoder.encode(password);
        users.setPassword(pwd);
        return save(users);
    }

    @Override
    public Boolean updatePassword(UserChangePasswordDTO userChangePasswordDTO) {
        Integer id = userChangePasswordDTO.getId();
        String oldPassword = userChangePasswordDTO.getOldPassword();
        String newPassword = userChangePasswordDTO.getNewPassword();
        Users user = getById(id);
        if(user==null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if(!encoder.matches(oldPassword,user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        String encode = encoder.encode(newPassword);
        user.setPassword(encode);
        return updateById(user);
    }

    @Override
    public PageVO<UserPageVO> querybyPage(UserPageQueryDTO userPageQueryDTO) {
        PageQuery pageQuery = new PageQuery();
        Integer role = userPageQueryDTO.getRole();
        Integer status = userPageQueryDTO.getStatus();
        String username = userPageQueryDTO.getUsername();

        pageQuery.setPage(userPageQueryDTO.getPage());
        pageQuery.setPageSize(userPageQueryDTO.getPageSize());
        Page<Users> mpPage = pageQuery.toMpPage(OrderItem.asc("reg_time"));
        Page<Users> page = lambdaQuery().like(username != null, Users::getUsername, username)
                .eq(role != null, Users::getRole, role)
                .eq(status != null, Users::getStatus, status)
                .page(mpPage);
        return PageVO.of(page,u->{
            UserPageVO up = BeanUtil.copyProperties(u, UserPageVO.class);
            if(u.getGender() == 1) up.setGender("男");
            else if(u.getGender() == 2) up.setGender("女");
            else up.setGender("未知");
            if(u.getStatus() == 1) up.setStatus("启用");
            else if(u.getStatus() == 0) up.setStatus("禁用");
            if(u.getRole() == 1) up.setRole("管理员");
            else if(u.getRole() == 0) up.setRole("普通用户");

            return up;
        });
    }

    @Override
    public List<UserNameAndIdVO> getAllUserNameAndId() {
        List<Users> list = list();
        return BeanUtil.copyToList(list, UserNameAndIdVO.class);
    }

    @Override
    public Boolean updateConfine(Integer confine, Integer id) {
        Users user = getById(id);
        user.setConfine(confine);
        return updateById(user);
    }

    @Override
    public boolean updateStatus(Integer status, Integer id, String violationReason) {
        Users user = getById(id);
        user.setStatus(status);
        if(Objects.equals(status, StatusConstant.DISABLE)){
            log.info("封禁用户：{}",id);
            user.setViolationReason(violationReason);
        }
        return updateById(user);
    }

    @Override
    public AdminInfoVO getAdminInfo(Integer id) {
        Users user = getById(id);
        return BeanUtil.copyProperties(user, AdminInfoVO.class);
    }

    @Override
    public AdminStatisticsVO getAdminStatistics(Integer adminId) {
        //总用户数量
        int totalUsers = lambdaQuery().count().intValue();//解决
//        用户增长率是 总用户数跟上个月的用户数量相比较
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastMonth = now.minusMonths(1);
        double userGrowth = getUserGrowth(lambdaQuery()
                .between(Users::getRegTime, lastMonth, now)
                .count(), totalUsers);

//        总图书
        int totalBooks = (int) booksService.count();
// 上个月的总图书数
        double bookGrowth = getBookGrowth(totalBooks);
//        获取借阅增长率
        double borrowGrowth= getBorrowBalance();

//        获取已解决问题数量
        Long solveCount = userQuestionService.selectCount(
                new QueryWrapper<UserQuestion>()
                        .eq("status", StatusConstant.ENABLE)
                        .eq("manager_id", adminId)
        );

//        问题解决率
        Double issueResolutionRate =getSolveRate(solveCount);

        Long borrowCount = borrowRecordsService.lambdaQuery().between(BorrowRecords::getLendTime, lastMonth, now).count();

        return AdminStatisticsVO.builder()
                .bookGrowth(bookGrowth)
                .borrowGrowth(borrowGrowth)
                .issueResolutionRate(issueResolutionRate)
                .totalUsers(totalUsers)
                .monthlyBorrows(borrowCount.intValue())
                .resolvedIssues(solveCount.intValue())
                .totalBooks(totalBooks)
                .userGrowth(userGrowth)
                .build();
    }

    private double getBookGrowth(int totalBooks) {
        LocalDateTime startOfLastMonth = LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        int lastMonthTotalBooks = booksService.lambdaQuery()
                .lt(Books::getCreateTime, startOfLastMonth)  // 统计上个月之前创建的图书
                .count().intValue();
        // 图书增长率 = (当前总数 - 上个月总数) / 上个月总数
        double bookGrowth = lastMonthTotalBooks == 0 ? 0 : (double) (totalBooks - lastMonthTotalBooks) / lastMonthTotalBooks;
        return setTwoScale(bookGrowth);
    }

    private double getUserGrowth(Long lastMonth, int totalUsers) {
        int lastMonthTotal = lastMonth.intValue();
//        Double userGrowth = (double) (count - lastMonthCount) / lastMonthCount;

//        保留俩位小数
// 用户增长率 = (当前总数 - 上个月总数) / 上个月总数
        double userGrowth = lastMonthTotal == 0 ? 0 : (double) (totalUsers - lastMonthTotal) / lastMonthTotal;
        userGrowth = setTwoScale(userGrowth);
        return userGrowth;
    }

    @Override
    public BorrowingTrendsVO getBorrowingTrends(Integer months) {
//        获取前months的月份
        List<String> month = new ArrayList<>();
        List<Integer> borrowed = new ArrayList<>();
        List<Integer> returned = new ArrayList<>();
        LocalDate now = LocalDate.now();


        for (int i = 0; i<months ; i++) {
            LocalDate currentMonth = now.minusMonths(i);
            String monthName = currentMonth.getMonth().getDisplayName(TextStyle.SHORT, Locale.CHINA);
            month.add(monthName);
            LocalDate first = currentMonth.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate last = currentMonth.with(TemporalAdjusters.lastDayOfMonth());
//          获取当月借书数量
            Integer lendCount= borrowRecordsService.getLendCountByMonth(first,last);
//            获取当月还书数量
            Integer returnCount= borrowRecordsService.getReturnCountByMonth(first,last);
            borrowed.add(lendCount);
            returned.add(returnCount);
        }
//        获取当前月份的借书还书数量
//        减一个月
        Collections.reverse(month);
        Collections.reverse(borrowed);
        Collections.reverse(returned);
        return BorrowingTrendsVO.builder()
                .months(month)
                .borrowed(borrowed)
                .returned(returned)
                .build();
    }

    @Override
    public List<BookCategoryStatVO> getBookCategoryStats() {
        List<ClazzVO> bossClazz = classesService.getBossClazz();
        ArrayList<BookCategoryStatVO> list = new ArrayList<>();
        for(ClazzVO clazz:bossClazz){
            String name = clazz.getName();
            Integer id = clazz.getId();
            List<Integer> allSubCategoryIds = classesService.getAllSubCategoryIds(id);
            int count = booksService.lambdaQuery()
                    .in(Books::getClazzId, allSubCategoryIds)
                    .count().intValue();
            list.add(new BookCategoryStatVO(name,count));
        }
        return list;
    }
    private double setTwoScale(double value){
       return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private double getBorrowBalance() {
//        获取借阅增长率
                // 本月借阅量
        LocalDate startOfThisMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfThisMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        Long thisMonthBorrows = borrowRecordsService.lambdaQuery()
                .between(BorrowRecords::getLendTime, startOfThisMonth, endOfThisMonth)
                .count();

        // 上个月借阅量
        LocalDate startOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        Long lastMonthBorrows = borrowRecordsService.lambdaQuery()
                .between(BorrowRecords::getLendTime, startOfLastMonth, endOfLastMonth)
                .count();

        // 借阅增长率 = (本月借阅量 - 上月借阅量) / 上月借阅量
        double borrowGrowth = lastMonthBorrows == 0 ? 0 : (double) (thisMonthBorrows - lastMonthBorrows) / lastMonthBorrows;
        return setTwoScale(borrowGrowth);
    }
    private double getSolveRate(Long solveCount){
//        获取问题解决率

        Long allCount = Long.valueOf(userQuestionService.getAllCount());  // 确保返回 Long

        double issueResolutionRate = allCount == 0 ? 0 : (double) solveCount / allCount;
        return setTwoScale(issueResolutionRate);
    }


}




