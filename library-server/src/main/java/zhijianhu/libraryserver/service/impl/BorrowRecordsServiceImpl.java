package zhijianhu.libraryserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.BorrowDTO;
import zhijianhu.dto.BorrowPageDTO;
import zhijianhu.entity.Books;
import zhijianhu.entity.BorrowRecords;
import zhijianhu.entity.PenaltyRecords;
import zhijianhu.entity.Users;
import zhijianhu.enumPojo.BorrowStatus;
import zhijianhu.libraryserver.mapper.BooksMapper;
import zhijianhu.libraryserver.mapper.BorrowRecordsMapper;
import zhijianhu.libraryserver.service.BorrowRecordsService;
import zhijianhu.libraryserver.service.PenaltyRecordsService;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.BorrowVO;
import zhijianhu.vo.PageVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;




/**
* @author windows
* @description 针对表【borrow_records(借阅记录表)】的数据库操作Service实现
* @createDate 2025-03-01 13:45:52
*/
@Service
@Slf4j
public class BorrowRecordsServiceImpl extends ServiceImpl<BorrowRecordsMapper, BorrowRecords>
    implements BorrowRecordsService {
    @Autowired
    private BooksMapper booksService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PenaltyRecordsService penaltyRecordsService;


    @Override
    public boolean deleteBorrowRecord(Integer bookId, Integer userId) {
        //修改借阅信息
        BorrowRecords one = lambdaQuery()
                .eq(BorrowRecords::getUserId, userId)
                .eq(BorrowRecords::getBookId, bookId)
                .eq(BorrowRecords::getStatus,StatusConstant.ENABLE)
                .one();
        one.setStatus(StatusConstant.DISABLE);
        one.setReturnTime(LocalDate.now());
        return updateById(one);
    }

    @Override
    public boolean addBorrowRecord(Integer id, Integer userId) {
        //添加借阅信息
        Books book = booksService.selectById(id);
        String image = book.getImage();
        LocalDate mustReturnTime = LocalDate.now().plusDays(30);//借书期限30天
        BorrowRecords record = BorrowRecords
                .builder()
                .bookId(id)
                .userId(userId)
                .image(image)
                .mustReturnTime(mustReturnTime)
                .build();
        return save(record);
    }

    @Override
    public PageVO<BorrowVO> getBorrowList(BorrowPageDTO borrowPageDTO) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPage(borrowPageDTO.getPageNum());
        pageQuery.setPageSize(borrowPageDTO.getPageSize());
        //        按照借阅时间排序
        Page<BorrowRecords> mpPage = pageQuery.toMpPage(OrderItem.desc("lend_time"));

        Integer status = borrowPageDTO.getStatus();
        Integer userId = borrowPageDTO.getUserId();
        Page<BorrowRecords> recordsPage = lambdaQuery()
                .eq(status != null, BorrowRecords::getStatus, status)
                .eq(userId != null, BorrowRecords::getUserId, userId)
                .page(mpPage);

//        判断是否有数据
          if (recordsPage == null || CollectionUtils.isEmpty(recordsPage.getRecords())) {
                return PageVO.of(new Page<>(pageQuery.getPage(), pageQuery.getPageSize()), BorrowVO.class);
            }
          // 批量预加载
        Set<Integer> userIds = recordsPage.getRecords().stream()
            .map(BorrowRecords::getUserId)
            .collect(Collectors.toSet());
        Map<Integer, Users> userMap = usersService.listByIds(userIds)
            .stream().collect(Collectors.toMap(Users::getId, u -> u));

        Set<Integer> bookIds = recordsPage.getRecords().stream()
            .map(BorrowRecords::getBookId)
            .collect(Collectors.toSet());
        Map<Integer, Books> bookMap = booksService.selectBatchIds(bookIds)
            .stream().collect(Collectors.toMap(Books::getId, b -> b));



        return PageVO.of(recordsPage,record->{
            BorrowVO vo = new BorrowVO();
            BeanUtils.copyProperties(record, vo);

            // 安全获取关联数据
            vo.setUserName(Optional.ofNullable(userMap.get(record.getUserId()))
                .map(Users::getName)
                .orElse("未知用户"));

            vo.setBookName(Optional.ofNullable(bookMap.get(record.getBookId()))
                .map(Books::getName)
                .orElse("已下架"));

            vo.setStatusName(BorrowStatus.getNameByCode(record.getStatus()));
            return vo;
        });
    }

    @Override
    public BorrowVO getBorrowById(Integer id) {
        BorrowRecords records = getById(id);
//        根据id查询直接去数据库查询
        Integer status = records.getStatus();
        Integer userId = records.getUserId();
        Integer bookId = records.getBookId();
        BorrowVO borrowVO = BeanUtil.copyProperties(records, BorrowVO.class);
        borrowVO.setStatusName(BorrowStatus.getNameByCode(status));
        borrowVO.setUserName(Optional.ofNullable(usersService.getById(userId).getName()).orElse("未知用户"));
        borrowVO.setBookName(Optional.ofNullable(booksService.selectById(bookId).getName()).orElse("未知书籍"));
        return borrowVO;
    }

    @Override
    public Integer getBorrowCount(Integer userId) {
        Long count = lambdaQuery()
                .eq(userId != null, BorrowRecords::getUserId, userId)
                .eq(BorrowRecords::getStatus, StatusConstant.ENABLE)
                .count();
        return count.intValue();
    }

    @Override
    public Integer getUserBorrowCount(Integer userId) {
        Long count = lambdaQuery()
                .eq(BorrowRecords::getUserId, userId)
                .count();
        return  count.intValue();
    }

    @Override
    public Boolean updateStatus(BorrowDTO borrow) {
//        先根据id获取借阅信息
        Integer id = borrow.getId();
        Integer status = borrow.getStatus();
        BorrowRecords record = getById(id);
        record.setStatus(status);
//        如果状态等于0，说明管理员主动归还，则需要更新归还时间和书籍状态
        if(Objects.equals(status, StatusConstant.DISABLE)){
            record.setReturnTime(LocalDate.now());
            Integer bookId = record.getBookId();
            Books books = booksService.selectById(bookId);
            books.setStatus(StatusConstant.ENABLE);
            booksService.updateById(books);
        }
//        id和status不能为null，其他字段可以为null
        String note = borrow.getNote();
        BigDecimal penaltyAmount = borrow.getPenaltyAmount();
        String violationReason = borrow.getViolationReason();
//        判断是否为null
        if(note!= null){
            record.setNote(note);
        }
        if(penaltyAmount != null){
//            说明需要罚款
            PenaltyRecords penaltyRecord = PenaltyRecords.builder()
                    .borrowRecordId(id)
                    .userId(record.getUserId())
                    .penaltyAmount(penaltyAmount)
                    .bookId(record.getBookId())
                    .build();
            boolean save = penaltyRecordsService.save(penaltyRecord);
            log.info("添加罚款记录：{},成功？：{}", penaltyRecord, save);
            record.setPenaltyAmount(penaltyAmount);
        }
        if(violationReason!=null){
            record.setViolationReason(violationReason);
        }
        return updateById(record);
    }

    @Override
    public Boolean addReturnDate(Integer id) {
        BorrowRecords record = getById(id);
        LocalDate mustReturnTime = record.getMustReturnTime();
        LocalDate lendTime = record.getLendTime();
//        计算差值
        long days = mustReturnTime.toEpochDay() - lendTime.toEpochDay();
//        如果小于30，则可以续借
        if(days<=30){
            LocalDate localDateTime = mustReturnTime.plusDays(15);//续借15天
            record.setMustReturnTime(localDateTime);
            return updateById(record);
        }
        return false;
    }
//



}




