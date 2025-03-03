package zhijianhu.libraryserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.dto.ReviewDTO;
import zhijianhu.entity.Review;
import zhijianhu.libraryserver.mapper.ReviewMapper;
import zhijianhu.libraryserver.service.ReviewService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author windows
* @description 针对表【review(图书评论表)】的数据库操作Service实现
* @createDate 2025-03-03 09:56:04
*/
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
    implements ReviewService {

    @Override
    public Boolean sendReview(ReviewDTO reviewDTO) {
        LocalDateTime now = LocalDateTime.now();
        Review review = BeanUtil.copyProperties(reviewDTO, Review.class);
        review.setCreateTime(now);
        return save(review);
    }

    @Override
    public List<Review> getReviewByBookId(Integer bookId) {
        return lambdaQuery()
                .eq(Review::getBookId, bookId)
                .list();
    }
}




