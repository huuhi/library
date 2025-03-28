package zhijianhu.libraryserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.AddUserQuestionDTO;
import zhijianhu.dto.QuestionWorkOutDTO;
import zhijianhu.dto.UserQuestionPageDTO;
import zhijianhu.enumPojo.ActivityType;
import zhijianhu.libraryserver.annotation.LogActivity;
import zhijianhu.libraryserver.annotation.OperateLog;
import zhijianhu.libraryserver.service.UserQuestionService;
import zhijianhu.result.Result;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserQuestionPageVO;
import zhijianhu.vo.UserQuestionVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class UserQuestionController {

    private final UserQuestionService userQuestionService;

    public UserQuestionController(UserQuestionService userQuestionService) {
        this.userQuestionService = userQuestionService;
    }

    @PostMapping("/add")
//    @CacheEvict(value = "userQuestion", allEntries = true)
    @LogActivity(
            type= ActivityType.AUDIT,
            description = "用户{#dto.userId}反馈问题{#dto.note}"
    )
    public Result<Void> addUserQuestion(@RequestBody AddUserQuestionDTO dto) {
        log.info("用户反馈问题：{}", dto);
        boolean exist = userQuestionService.isExist(dto.getUserId(), dto.getBorrowRecordId());
        if (exist) {
            return Result.error(MessageConstant.APPEAL_EXIST);
        }

        boolean success = userQuestionService.addUserQuestion(dto);
        return success? Result.success() : Result.error(MessageConstant.APPEAL_FAIL);
    }
//    分页查询用户的问题
    @GetMapping("/list")
    public Result<PageVO<UserQuestionPageVO>> getUserQuestionList(@ModelAttribute UserQuestionPageDTO dto) {
        log.info("分页查询用户的问题：{}", dto);
        PageVO<UserQuestionPageVO> pageVO = userQuestionService.getUserQuestionList(dto);
        return Result.success(pageVO);
    }
//    管理员处理用户的问题
    @PostMapping("/workOut")
//    @CacheEvict(value = "userQuestion", allEntries = true)
    @LogActivity(
            type = ActivityType.AUDIT,
            description = "管理员{#dto.managerId}处理用户的问题{#dto.id}"
    )
    public Result<Void> adminDealUserQuestion(@RequestBody QuestionWorkOutDTO dto) {
        log.info("管理员处理用户的问题：{}", dto);
        boolean success= userQuestionService.handleUserQuestion(dto);
        return success? Result.success() : Result.error(MessageConstant.LEND_BOOK_FAIL);
    }
    @GetMapping("/{id}")
    public Result<UserQuestionPageVO> getUserQuestion(@PathVariable("id") Integer id) {
        log.info("查询用户的问题：{}", id);
        UserQuestionPageVO userQuestion = userQuestionService.getUserQuestion(id);
        return Result.success(userQuestion);
    }
    @DeleteMapping("/{id}")
    @OperateLog
    public Result<Void> deleteUserQuestion(@PathVariable("id") List<Integer> ids) {
        log.info("删除用户的问题：{}", ids);
        boolean success= userQuestionService.removeBatchByIds(ids);
        return success? Result.success() : Result.error(MessageConstant.DELETE_FAIL);
    }
    @GetMapping("/getCount/{userId}")
    public Result<Integer> getCount(@PathVariable("userId") Integer id) {
        log.info("查询用户的问题数量：{}", id);
//      返回用户未读消息数量
        return Result.success(userQuestionService.countUnread(id));
    }
//    根据用户id获取用户的反馈问题
    @GetMapping("/getUserQuestion/{userid}")
//    @Cacheable(value = "userQuestion", key = "#id")
    public Result<List<UserQuestionVO>> getUserQuestionByUserId(@PathVariable("userid") Integer id) {
        log.info("根据用户id获取用户的反馈问题：{}",id);
//        这里是用户在查看自己的信息，需要将状态修改成为已读

        List<UserQuestionVO> userQuestion = userQuestionService.getUserQuestionByUserId(id);
        return Result.success(userQuestion);
    }
    @GetMapping("/user-feedback/pending")
    public Result<PageVO<UserQuestionPageVO>> getPendingFeedbacks(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
//        查询用户问题列表
        UserQuestionPageDTO dto = UserQuestionPageDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
        PageVO<UserQuestionPageVO> pendingFeedbacks =userQuestionService.getUserQuestionList(dto);
        return Result.success(pendingFeedbacks);
    }
}
