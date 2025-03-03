package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.AddUserQuestionDTO;
import zhijianhu.entity.UserQuestion;

/**
* @author windows
* @description 针对表【user_question】的数据库操作Service
* @createDate 2025-03-03 17:55:38
*/
public interface UserQuestionService extends IService<UserQuestion> {

    boolean addUserQuestion(AddUserQuestionDTO dto);
    boolean isExist(Integer userId,Integer borrowRecordId);
}
