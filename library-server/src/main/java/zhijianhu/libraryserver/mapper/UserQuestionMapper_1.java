package zhijianhu.libraryserver.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import zhijianhu.entity.UserQuestion;

/**
* @author windows
* @description 针对表【user_question】的数据库操作Mapper
* @createDate 2025-03-03 17:55:38
* @Entity zhijianhu/libraryserver.UserQuestion
*/
public interface UserQuestionMapper extends BaseMapper<UserQuestion> {
    @Select("select count(*) from user_question")
    Integer getAllCount();
}




