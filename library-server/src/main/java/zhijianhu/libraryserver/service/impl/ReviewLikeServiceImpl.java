package zhijianhu.libraryserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhijianhu.dto.ReviewLikeDTO;
import zhijianhu.entity.Review;
import zhijianhu.entity.ReviewLike;
import zhijianhu.libraryserver.mapper.ReviewLikeMapper;
import zhijianhu.libraryserver.mapper.ReviewMapper;
import zhijianhu.libraryserver.service.ReviewLikeService;

/**
* @author windows
* @description 针对表【review_like(评论点赞表)】的数据库操作Service实现
* @createDate 2025-03-06 12:47:39
*/
@Service
@Slf4j
public class ReviewLikeServiceImpl extends ServiceImpl<ReviewLikeMapper, ReviewLike>
    implements ReviewLikeService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Override
    public boolean likeReview(ReviewLikeDTO LikereviewDTO) {
        Integer reviewId = LikereviewDTO.getReviewId();
        Integer userId = LikereviewDTO.getUserId();
        ReviewLike reviewLike = ReviewLike.builder()
                .reviewId(reviewId)
                .userId(userId)
                .build();
        Review review = reviewMapper.selectById(reviewId);
        if(review == null) return false;
        Integer likeCount = review.getLikeCount();
        review.setLikeCount(likeCount + 1);
        reviewMapper.updateById(review);//更新评论点赞数
        return save(reviewLike);
    }

    @Override
    public boolean cancelLikeReview(Integer reviewId, Integer userId) {
        Review review = reviewMapper.selectById(reviewId);
        Integer likeCount = review.getLikeCount();
        review.setLikeCount(likeCount-1);
        reviewMapper.updateById(review);//更新点赞数量
        return lambdaUpdate()
                .eq(ReviewLike::getReviewId, reviewId)
                .eq(ReviewLike::getUserId, userId)
                .remove();
    }
}




