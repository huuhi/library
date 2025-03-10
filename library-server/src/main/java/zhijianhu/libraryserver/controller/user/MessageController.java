package zhijianhu.libraryserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.libraryserver.service.MessageService;
import zhijianhu.result.Result;
import zhijianhu.vo.MessageVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/10
 * 说明:
 */
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {
    private final MessageService messageService;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    @GetMapping("/like/{id}")
    public Result<List<MessageVO>> getMessageByUserId(@PathVariable("id")Integer userId){
        log.info("根据用户id获取点赞消息:{}",userId);
        List<MessageVO> messageVOList= messageService.getLikeMessageByUserId(userId);
        return Result.success(messageVOList);
    }


}
