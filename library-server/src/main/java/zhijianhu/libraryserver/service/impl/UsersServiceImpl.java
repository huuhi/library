package zhijianhu.libraryserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import zhijianhu.constant.MessageConstant;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.UserChangePasswordDTO;
import zhijianhu.dto.UserDTO;
import zhijianhu.dto.UserLoginDTO;
import zhijianhu.dto.UserPageQueryDTO;
import zhijianhu.entity.Users;
import zhijianhu.exception.AccountLockedException;
import zhijianhu.exception.AccountNotFoundException;
import zhijianhu.exception.PasswordErrorException;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.libraryserver.mapper.UsersMapper;
import org.springframework.stereotype.Service;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserNameAndIdVO;
import zhijianhu.vo.UserPageVO;

import java.util.List;

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
   @Autowired
   private UsersMapper usersMapper;

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

        if(user.getStatus()== StatusConstant.DISABLE){
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
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
}




