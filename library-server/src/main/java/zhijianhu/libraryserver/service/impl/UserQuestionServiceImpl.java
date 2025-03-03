package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.dto.AddUserQuestionDTO;
import zhijianhu.entity.UserQuestion;
import zhijianhu.libraryserver.mapper.UserQuestionMapper;
import zhijianhu.libraryserver.service.UserQuestionService;

/**
* @author windows
* @description 针对表【user_question】的数据库操作Service实现
* @createDate 2025-03-03 17:55:38
*/
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion>
    implements UserQuestionService {

    @Override
    public boolean addUserQuestion(AddUserQuestionDTO dto) {

        UserQuestion userQuestion = BeanUtil.copyProperties(dto, UserQuestion.class);
        return this.save(userQuestion);
    }

//   判断是否已经反馈过
    @Override
    public boolean isExist(Integer userId, Integer borrowRecordId) {
        UserQuestion one = lambdaQuery()
                .eq(UserQuestion::getUserId, userId)
                .eq(UserQuestion::getBorrowRecordId, borrowRecordId)
                .one();
        return one != null;
    }
}




