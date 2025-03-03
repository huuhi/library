package zhijianhu.libraryserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.UserChangePasswordDTO;
import zhijianhu.dto.UserDTO;
import zhijianhu.dto.UserLoginDTO;
import zhijianhu.dto.UserPageQueryDTO;
import zhijianhu.entity.Books;
import zhijianhu.entity.Users;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserNameAndIdVO;
import zhijianhu.vo.UserPageVO;

import java.util.List;

/**
* @author windows
* @description 针对表【users(用户信息表)】的数据库操作Service
* @createDate 2025-02-24 14:57:02
*/
public interface UsersService extends IService<Users> {

    Users login(UserLoginDTO userLoginDTO);

    boolean register(UserDTO userDTO);


    Boolean updatePassword(UserChangePasswordDTO userChangePasswordDTO);

    PageVO<UserPageVO> querybyPage(UserPageQueryDTO userPageQueryDTO);

    List<UserNameAndIdVO> getAllUserNameAndId();
}
