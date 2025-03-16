package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.AddUserQuestionDTO;
import zhijianhu.dto.QuestionWorkOutDTO;
import zhijianhu.dto.UserQuestionPageDTO;
import zhijianhu.entity.UserQuestion;
import zhijianhu.entity.Users;
import zhijianhu.enumPojo.IsRead;
import zhijianhu.enumPojo.QuestionType;
import zhijianhu.enumPojo.StatusType;
import zhijianhu.libraryserver.mapper.UserQuestionMapper;
import zhijianhu.libraryserver.service.MessageService;
import zhijianhu.libraryserver.service.UserQuestionService;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserQuestionPageVO;
import zhijianhu.vo.UserQuestionVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author windows
* @description 针对表【user_question】的数据库操作Service实现
* @createDate 2025-03-03 17:55:38
*/
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion>
    implements UserQuestionService {
    private final UsersService userService;
    private final MessageService messageService;

    public UserQuestionServiceImpl(MessageService messageService, UsersService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }


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

//    分页获取用户的反馈记录
    @Override
    public PageVO<UserQuestionPageVO> getUserQuestionList(UserQuestionPageDTO dto) {
//      分页查询的参数
        Integer pageNum = dto.getPageNum();
        Integer pageSize = dto.getPageSize();
//        问题类型跟状态
        Integer type = dto.getType();
        Integer status = dto.getStatus();
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageSize(pageSize);
        pageQuery.setPage(pageNum);
//        获取分页对象
        Page<UserQuestion> mpPage = pageQuery.toMpPage();
//        查询
        Page<UserQuestion> page = lambdaQuery()
                .eq(type != null, UserQuestion::getAppealType, type)
                .eq(status != null, UserQuestion::getStatus, status)
                .page(mpPage);
//        对结果进行处理
        return PageVO.of(page, this::getUserQuestionPageVO);
    }

//    解决用户问题
    @Override
    public boolean handleUserQuestion(QuestionWorkOutDTO dto) {
        Integer id = dto.getId();
        UserQuestion question = getById(id);
        question.setStatus(dto.getStatus());
        question.setManagerId(dto.getManagerId());
        question.setProcessNote(dto.getProcessNote());
        question.setEndTime(LocalDateTime.now());
        question.setIsReadStatus(StatusConstant.ENABLE);
        return updateById(question);
    }

    @Override
    public UserQuestionPageVO getUserQuestion(Integer id) {
        UserQuestion question = getById(id);
        Integer managerId = question.getManagerId();
        String managerName = null;
        if(managerId!=null){
            managerName= userService.getById(managerId).getName();
        }
        UserQuestionPageVO userQuestionPageVO = getUserQuestionPageVO(question);
        userQuestionPageVO.setManagerName(managerName);
        return userQuestionPageVO;
    }

    @Override
    public Integer countUnread(Integer id) {
//     3.11 对此接口进行修改
 //         添加消息列表中未读数量
        return lambdaQuery()
                .eq(UserQuestion::getUserId, id)
                .eq(UserQuestion::getIsReadStatus, StatusConstant.ENABLE)
                .count().intValue()+messageService.getNotReadCount(id,null);
    }

    @Override
    public List<UserQuestionVO> getUserQuestionByUserId(Integer id) {
        List<UserQuestion> list = lambdaQuery()
                .eq(UserQuestion::getUserId, id)
                .list();
        if(list.isEmpty()){
            return List.of();
        }
        List<UserQuestionVO> userQuestionVOS = BeanUtil.copyToList(list, UserQuestionVO.class);
//        批量获取管理员的非null id 并且转换成set集合
        Set<Integer> collect = userQuestionVOS.stream()
                .map(UserQuestionVO::getManagerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
//        批量获取管理员的username 并转换成map
        Map<Integer, String> managerMap = userService.listByIds(collect)
                .stream()
                .collect(Collectors.toMap(Users::getId, Users::getUsername));

        userQuestionVOS.forEach(question->{
//           获取   appealTypeName,  statusName, managerName, isRead
            Integer managerId = question.getManagerId();
            if(managerId != null && managerMap.containsKey(managerId)){
                question.setManagerName(managerMap.get(managerId));
            }
            toUserQuestionVO(question);
        });
//        根据用户id查询反馈列表，说明用户在查看消息，这个时候需要将未读的消息设置为已读
        lambdaUpdate()
                .eq(UserQuestion::getUserId,id)
                .eq(UserQuestion::getIsReadStatus,StatusConstant.ENABLE)
                .set(UserQuestion::getIsReadStatus,StatusConstant.DISABLE)
                .update();
        return userQuestionVOS;
    }

//    处理
    private UserQuestionPageVO getUserQuestionPageVO(UserQuestion question) {
        UserQuestionPageVO userQuestionPageVO = BeanUtil.copyProperties(question, UserQuestionPageVO.class);
        String appealTypeName = QuestionType.getNameByCode(userQuestionPageVO.getAppealType());
        String statusName = StatusType.getNameByCode(userQuestionPageVO.getStatus());
        userQuestionPageVO.setAppealTypeName(appealTypeName);
        userQuestionPageVO.setStatusName(statusName);
        return userQuestionPageVO;
    }
    private void toUserQuestionVO(UserQuestionVO question) {
        Integer type = question.getAppealType();
        question.setAppealTypeName(QuestionType.getNameByCode(type));
        Integer status = question.getStatus();
        question.setStatusName(StatusType.getNameByCode(status));
        Integer isRead = question.getIsReadStatus();
        question.setIsRead(IsRead.getIsReadByCode(isRead));
    }


}




