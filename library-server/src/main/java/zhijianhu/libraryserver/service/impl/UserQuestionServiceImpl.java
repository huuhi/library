package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import zhijianhu.dto.AddUserQuestionDTO;
import zhijianhu.dto.QuestionWorkOutDTO;
import zhijianhu.dto.UserQuestionPageDTO;
import zhijianhu.entity.UserQuestion;
import zhijianhu.enumPojo.QuestionType;
import zhijianhu.enumPojo.StatusType;
import zhijianhu.libraryserver.mapper.UserQuestionMapper;
import zhijianhu.libraryserver.service.UserQuestionService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserPageVO;
import zhijianhu.vo.UserQuestionPageVO;

import java.time.LocalDateTime;

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

    @Override
    public PageVO<UserQuestionPageVO> getUserQuestionList(UserQuestionPageDTO dto) {
        Integer pageNum = dto.getPageNum();
        Integer pageSize = dto.getPageSize();
        Integer type = dto.getType();
        Integer status = dto.getStatus();
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageSize(pageSize);
        pageQuery.setPage(pageNum);
        Page<UserQuestion> mpPage = pageQuery.toMpPage();
        Page<UserQuestion> page = lambdaQuery()
                .eq(type != null, UserQuestion::getAppealType, type)
                .eq(status != null, UserQuestion::getStatus, status)
                .page(mpPage);
        return PageVO.of(page, this::getUserQuestionPageVO);
    }

    @Override
    public boolean handleUserQuestion(QuestionWorkOutDTO dto) {
        Integer id = dto.getId();
        UserQuestion question = getById(id);
        question.setStatus(dto.getStatus());
        question.setManagerId(dto.getManagerId());
        question.setProcessNote(dto.getProcessNote());
        question.setEndTime(LocalDateTime.now());
        return updateById(question);
    }

    @Override
    public UserQuestionPageVO getUserQuestion(Integer id) {
        UserQuestion question = getById(id);
        return getUserQuestionPageVO(question);
    }

    private UserQuestionPageVO getUserQuestionPageVO(UserQuestion question) {
        UserQuestionPageVO userQuestionPageVO = BeanUtil.copyProperties(question, UserQuestionPageVO.class);
        String appealTypeName = QuestionType.getNameByCode(userQuestionPageVO.getAppealType());
        String statusName = StatusType.getNameByCode(userQuestionPageVO.getStatus());
        userQuestionPageVO.setAppealTypeName(appealTypeName);
        userQuestionPageVO.setStatusName(statusName);
        return userQuestionPageVO;
    }
}




