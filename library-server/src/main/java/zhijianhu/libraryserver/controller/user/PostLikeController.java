package zhijianhu.libraryserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.PostLikeDTO;
import zhijianhu.libraryserver.service.PostLikeService;
import zhijianhu.result.Result;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/9
 * 说明:
 */
@RestController
@RequestMapping("/post-like")
@Slf4j
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    //    点赞
    @PostMapping()
    public Result<Void> likePost(@RequestBody PostLikeDTO postDTO){
        log.info("点赞帖子");
        boolean success=postLikeService.likePost(postDTO);
        return success?Result.success():Result.error(MessageConstant.LIKE_POST_FAILURE);
    }

    @DeleteMapping("/unlike")
    public Result<Void> unlikePost(@RequestBody PostLikeDTO postDTO){
        log.info("取消点赞帖子");
        boolean success=postLikeService.unlikePost(postDTO);
        return success?Result.success(): Result.error(MessageConstant.UNLIKE_POST_FAILURE);
    }

}
