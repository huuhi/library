package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.ExamineReviewDTO;
import zhijianhu.dto.ReviewDTO;
import zhijianhu.dto.ReviewPageDTO;
import zhijianhu.entity.Review;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.ReviewPageVO;
import zhijianhu.vo.ReviewVO;

import java.util.List;
import java.util.Map;

/**
* @author windows
* @description 针对表【review(图书评论表)】的数据库操作Service
* @createDate 2025-03-05 18:03:24
*/
public interface ReviewService extends IService<Review> {

    Boolean sendReview(ReviewDTO reviewDTO);

    List<ReviewVO> getReviewByBookId(Integer bookId, Integer userId);

    boolean completeRemove(Integer id);

    boolean auditReview(ExamineReviewDTO reviewDTO);

    PageVO<ReviewPageVO> getReviewByPage(ReviewPageDTO reviewPageDTO);
}
