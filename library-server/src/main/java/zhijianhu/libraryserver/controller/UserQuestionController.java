package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.AddUserQuestionDTO;
import zhijianhu.dto.QuestionWorkOutDTO;
import zhijianhu.dto.UserQuestionPageDTO;
import zhijianhu.entity.UserQuestion;
import zhijianhu.libraryserver.service.UserQuestionService;
import zhijianhu.result.Result;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserQuestionPageVO;

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

    @Autowired
    private UserQuestionService userQuestionService;

    @PostMapping("/add")
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
    public Result<Void> deleteUserQuestion(@PathVariable("id") List<Integer> ids) {
        log.info("删除用户的问题：{}", ids);
        boolean success= userQuestionService.removeBatchByIds(ids);
        return success? Result.success() : Result.error(MessageConstant.DELETE_FAIL);
    }
}
