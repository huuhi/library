package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.AddUserQuestionDTO;
import zhijianhu.libraryserver.service.UserQuestionService;
import zhijianhu.result.Result;

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




}
