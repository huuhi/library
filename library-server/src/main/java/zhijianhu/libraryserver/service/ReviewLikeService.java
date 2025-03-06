package zhijianhu.libraryserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.ReviewLikeDTO;
import zhijianhu.entity.ReviewLike;

/**
* @author windows
* @description 针对表【review_like(评论点赞表)】的数据库操作Service
* @createDate 2025-03-06 12:47:39
*/
public interface ReviewLikeService extends IService<ReviewLike> {

    boolean likeReview(ReviewLikeDTO review);

    boolean cancelLikeReview(Integer reviewId,Integer userId);
}
