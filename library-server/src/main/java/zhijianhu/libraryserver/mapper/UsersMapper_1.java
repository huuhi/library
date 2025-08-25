package zhijianhu.libraryserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zhijianhu.dto.UserLoginDTO;
import zhijianhu.entity.Books;
import zhijianhu.entity.Users;

/**
* @author windows
* @description 针对表【users(用户信息表)】的数据库操作Mapper
* @createDate 2025-02-24 14:57:02
* @Entity zhijianhu.entity.Books.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    @Select("SELECT * FROM users WHERE username = #{username}")
    Users login(String username);
}




