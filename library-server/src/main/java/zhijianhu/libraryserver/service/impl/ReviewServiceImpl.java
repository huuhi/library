package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.ReviewDTO;
import zhijianhu.entity.Review;
import zhijianhu.entity.ReviewLike;
import zhijianhu.entity.Users;
import zhijianhu.libraryserver.service.ReviewLikeService;
import zhijianhu.libraryserver.service.ReviewService;
import zhijianhu.libraryserver.mapper.ReviewMapper;
import org.springframework.stereotype.Service;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.utils.TextModerationPlusDemo;
import zhijianhu.vo.ReviewVO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author windows
* @description 针对表【review(图书评论表)】的数据库操作Service实现
* @createDate 2025-03-05 18:03:24
*/
@Service
@Slf4j
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
    implements ReviewService{
    @Autowired
    private ReviewLikeService reviewLikeService;
    @Autowired
    private UsersService userService;
    @Override
    public Boolean sendReview(ReviewDTO reviewDTO) {
//        发送评论
        String content = reviewDTO.getContent();
        Review review = BeanUtil.copyProperties(reviewDTO, Review.class);
        String image = userService.getById(reviewDTO.getUserId()).getImage();
        review.setImage(image);
        String s = TextModerationPlusDemo.DetectionText(content);
        if(s!=null&&!s.equals("none")){
//            说明评论有问题！
            review.setIsAudit(StatusConstant.DISABLE);
            save(review);
            return false;
        }
        return save(review);
    }

    @Override
    public List<ReviewVO> getReviewByBookId(Integer bookId, Integer userId) {
        List<Review> list = lambdaQuery()
                .eq(Review::getBookId, bookId)
                .list();
        if(list!=null&& !list.isEmpty()){
//            获取所有的用户id
            Set<Integer> collect = list.stream()
                    .map(Review::getUserId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
//            获取所有的用户名，以map形式返回
            Map<Integer, String> map = userService.listByIds(collect)
                    .stream()
                    .collect(Collectors.toMap(Users::getId, Users::getUsername));
            log.info("getReviewByBookId: "+list);
            List<ReviewVO> reviewVO = BeanUtil.copyToList(list, ReviewVO.class);
            reviewVO.forEach(review->{
                Integer id = review.getId();
                String name = map.get(review.getUserId());
                review.setUserName(name);
                review.setIsLike(isLike(userId, id));
            });
            return reviewVO;
        }
        return List.of();
    }

//    判断当前用户是否点赞
    private boolean isLike(int userId,int reviewId){
//
        ReviewLike one = reviewLikeService
                .lambdaQuery()
                .eq(ReviewLike::getUserId, userId)
                .eq(ReviewLike::getReviewId, reviewId)
                .one();
        return one != null;
    }
}




