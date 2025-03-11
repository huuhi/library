package zhijianhu.libraryserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageType;
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
    @GetMapping("/like/{userId}")
    public Result<List<MessageVO>> getLikeMessageByUserId(@PathVariable("userId")Integer userId){
        log.info("根据用户id获取点赞消息:{}",userId);
        List<MessageVO> messageVOList= messageService.getLikeMessageByUserId(userId);
        return Result.success(messageVOList);
    }
    @GetMapping("/review/{userId}")
    public Result<List<MessageVO>> getReviewMessageByUserId(@PathVariable("userId") Integer userId){
        log.info("根据用户id获取评论消息:{}",userId);
        List<MessageVO> messageVOList =messageService.getReviewMessageByUserId(userId);
        return Result.success(messageVOList);
    }
//    获取未读消息数量  在用户反馈接口中累加统计，总共未读消息数
    @GetMapping("/not-read/review/{userId}")
    public Result<Integer> getNotReadReview(@PathVariable("userId") Integer userId){
        log.info("获取当前用户评论未读消息：{}",userId);
        Integer reviewNotReadCount = messageService.getNotReadCount(userId, MessageType.REVIEW);
        return Result.success(reviewNotReadCount);
    }
    @GetMapping("/not-read/like/{userId}")
    public Result<Integer> getNotReadLike(@PathVariable("userId") Integer userId){
        log.info("获取当前用户点赞未读消息：{}",userId);
        Integer reviewNotReadCount = messageService.getNotReadCount(userId, MessageType.LIKE);
        return Result.success(reviewNotReadCount);
    }




}
