package zhijianhu.libraryserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.ExamineReviewDTO;
import zhijianhu.dto.GetReviewDTO;
import zhijianhu.dto.ReviewDTO;
import zhijianhu.dto.ReviewPageDTO;
import zhijianhu.enumPojo.ActivityType;
import zhijianhu.libraryserver.annotation.LogActivity;
import zhijianhu.libraryserver.annotation.OperateLog;
import zhijianhu.libraryserver.service.ReviewService;
import zhijianhu.result.Result;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.ReviewPageVO;
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
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //    发表评论
    @PostMapping("/send")
    public Result<Void> sendReview(@RequestBody ReviewDTO reviewDTO){
        log.info("发表评论:{}", reviewDTO);
        Boolean  success= reviewService.sendReview(reviewDTO);
        return success? Result.success() : Result.error(MessageConstant.REVIEW_ILLEGAL);
    }
    @GetMapping("/get")
    public Result<List<ReviewVO>> getReviewByBookId(@ModelAttribute GetReviewDTO dto){
        log.info("获取评论:{}", dto);
        List<ReviewVO> reviewList = reviewService.getReviewByBookId(dto);
        return Result.success(reviewList);
    }
//    下面的真正删除评论，而不是逻辑删除
    @DeleteMapping("/delete/{id}")
    @OperateLog
    public Result<Void> deleteReview(@PathVariable("id") Integer id){
        log.info("删除评论:{}", id);
        boolean delete= reviewService.completeRemove(id);
        return delete? Result.success() : Result.error(MessageConstant.DELETE_FAIL);
    }
//    管理员审核评论
    @PutMapping("/audit")
    @LogActivity(
            type = ActivityType.AUDIT,
            description = "审核评论{#reviewDTO.id}"
    )
    public Result<Void> auditReview(@RequestBody ExamineReviewDTO reviewDTO){
        log.info("审核评论:{}", reviewDTO);
        boolean success= reviewService.auditReview(reviewDTO);
        return success? Result.success() : Result.error(MessageConstant.UPDATE_FAIL);
    }
//    分页查询评论
    @GetMapping("/page")
    public Result<PageVO<ReviewPageVO>> getReviewByPage(@ModelAttribute ReviewPageDTO reviewPageDTO){
        log.info("分页查询评论:{}", reviewPageDTO);
        PageVO<ReviewPageVO> pageVO = reviewService.getReviewByPage(reviewPageDTO);
        return Result.success(pageVO);
    }
    @GetMapping("/review/pending")
    public Result<PageVO<ReviewPageVO>> getPendingComments(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        ReviewPageDTO dto = ReviewPageDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .isAudit(StatusConstant.DISABLE)
                .build();
        PageVO<ReviewPageVO> pageVO = reviewService.getReviewByPage(dto);
        return Result.success(pageVO);
    }
}
