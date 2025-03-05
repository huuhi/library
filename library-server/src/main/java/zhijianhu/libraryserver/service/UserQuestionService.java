package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.AddUserQuestionDTO;
import zhijianhu.dto.QuestionWorkOutDTO;
import zhijianhu.dto.UserQuestionPageDTO;
import zhijianhu.entity.UserQuestion;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserQuestionPageVO;
import zhijianhu.vo.UserQuestionVO;

import java.util.List;

/**
* @author windows
* @description 针对表【user_question】的数据库操作Service
* @createDate 2025-03-03 17:55:38
*/
public interface UserQuestionService extends IService<UserQuestion> {

    boolean addUserQuestion(AddUserQuestionDTO dto);
    boolean isExist(Integer userId,Integer borrowRecordId);

    PageVO<UserQuestionPageVO> getUserQuestionList(UserQuestionPageDTO dto);

    boolean handleUserQuestion(QuestionWorkOutDTO dto);

    UserQuestionPageVO getUserQuestion(Integer id);

    Integer countUnread(Integer id);

    List<UserQuestionVO> getUserQuestionByUserId(Integer id);
}
