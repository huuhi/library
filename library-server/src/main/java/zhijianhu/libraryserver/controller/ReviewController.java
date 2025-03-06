package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.dto.ReviewDTO;
import zhijianhu.entity.Review;
import zhijianhu.libraryserver.service.ReviewService;
import zhijianhu.result.Result;
import zhijianhu.vo.ReviewVO;

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
        return success? Result.success() : Result.error("评论疑似违规，等待管理员审核");
    }
    @GetMapping("/get/{bookId}/{userId}")
    public Result<List<ReviewVO>> getReviewByBookId(@PathVariable("bookId") Integer bookId,
                                                    @PathVariable("userId") Integer userId){
        log.info("获取评论:{}", bookId);
        List<ReviewVO> reviewList = reviewService.getReviewByBookId(bookId,userId);
        return Result.success(reviewList);
    }
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteReview(@PathVariable("id") Integer id){
        log.info("删除评论:{}", id);
        boolean delete= reviewService.removeById(id);
        return delete? Result.success() : Result.error("删除评论失败");
    }
}
