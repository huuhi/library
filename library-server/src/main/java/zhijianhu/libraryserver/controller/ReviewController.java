package zhijianhu.libraryserver.controller;

import cn.hutool.db.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.ExamineReviewDTO;
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
//    下面的真正删除评论，而不是逻辑删除
    @DeleteMapping("/delete/{id}")
    @OperateLog
    public Result<Void> deleteReview(@PathVariable("id") Integer id){
        log.info("删除评论:{}", id);
        boolean delete= reviewService.completeRemove(id);
        return delete? Result.success() : Result.error("删除评论失败");
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
        return success? Result.success() : Result.error("审核评论失败");
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
