package zhijianhu.libraryserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.ReviewLikeDTO;
import zhijianhu.libraryserver.service.ReviewLikeService;
import zhijianhu.result.Result;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/6
 * 说明:
 */
@RestController
@RequestMapping("/review-like")
@Slf4j
public class ReviewLikeController {
    @Autowired
    private ReviewLikeService reviewLikeService;

    @PostMapping("/like")
//    @CacheEvict(value = {"review_like_count", "user_like_status"}, key = "#review.reviewId + '-' + #review.userId")
    public Result<Void> likeReview(@RequestBody ReviewLikeDTO review){
         log.info("点赞评论:{}", review);
         boolean success = reviewLikeService.likeReview(review);
         return success? Result.success() : Result.error(MessageConstant.LIKE_POST_FAILURE);
    }
    @DeleteMapping("/cancel-like/{reviewId}/{userId}")
    public Result<Void> cancelLikeReview(@PathVariable("reviewId") Integer reviewId,
                                         @PathVariable("userId") Integer userId){
        log.info("取消点赞评论:{},{}", reviewId, userId);
        boolean success = reviewLikeService.cancelLikeReview(reviewId, userId);
         return success? Result.success() : Result.error(MessageConstant.UNLIKE_POST_FAILURE);
    }
}
