package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.dto.ReviewDTO;
import zhijianhu.entity.Review;
import zhijianhu.libraryserver.service.ReviewService;
import zhijianhu.result.Result;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:
 */
@RestController
@Slf4j
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

//    发表评论
    @PostMapping("/send")
    public Result<Void> sendReview(@RequestBody ReviewDTO reviewDTO){
        log.info("发表评论:{}", reviewDTO);
        Boolean  success= reviewService.sendReview(reviewDTO);
        return success? Result.success() : Result.error("评论失败");
    }
    @GetMapping("/get/{bookId}")
    public Result<List<Review>> getReviewByBookId(@PathVariable("bookId") Integer bookId){
        log.info("获取评论:{}", bookId);
        List<Review> reviewList = reviewService.getReviewByBookId(bookId);
        return Result.success(reviewList);
    }
}
