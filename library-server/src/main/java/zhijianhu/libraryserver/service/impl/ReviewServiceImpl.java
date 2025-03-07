package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.ExamineReviewDTO;
import zhijianhu.dto.ReviewDTO;
import zhijianhu.dto.ReviewPageDTO;
import zhijianhu.entity.Review;
import zhijianhu.entity.ReviewLike;
import zhijianhu.entity.Users;
import zhijianhu.libraryserver.service.ReviewLikeService;
import zhijianhu.libraryserver.service.ReviewService;
import zhijianhu.libraryserver.mapper.ReviewMapper;
import org.springframework.stereotype.Service;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.query.PageQuery;
import zhijianhu.result.TextResult;
import zhijianhu.utils.TextModerationPlusDemo;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.ReviewPageVO;
import zhijianhu.vo.ReviewVO;

import java.util.*;
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
    @Autowired
    private ReviewMapper reviewMapper;
    @Override
    public Boolean sendReview(ReviewDTO reviewDTO) {
//        发送评论
        String content = reviewDTO.getContent();
        Review review = BeanUtil.copyProperties(reviewDTO, Review.class);
        String image = userService.getById(reviewDTO.getUserId()).getImage();
        review.setImage(image);
        TextResult textResult = TextModerationPlusDemo.DetectionText(content);
        assert textResult != null;//断言不为空
        String s = textResult.getLevel();
        review.setDescription(textResult.getText());//设置评论的描述
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
            Map<Integer, String> map = getUserMap(list);
            log.info("getReviewByBookId: "+list);
            List<ReviewVO> reviewVO = BeanUtil.copyToList(list, ReviewVO.class);
            reviewVO.forEach(review->{
                Integer id = review.getId();
                String name = map.get(review.getUserId());
                review.setUserName(name);
                review.setIsLike(isLike(userId,id));
            });
            return reviewVO;
        }
        return List.of();
    }

    @Override
    public boolean completeRemove(Integer id) {
        return reviewMapper.deleteReviewById(id);
    }

    @Override
    public boolean auditReview(ExamineReviewDTO reviewDTO) {
        Integer id = reviewDTO.getId();
        String description = reviewDTO.getDescription();
        Review review = reviewMapper.getReviewById(id);
        Integer status = reviewDTO.getStatus();
        review.setIsAudit(status);
        review.setDescription(description);
        return reviewMapper.updateReview(review);
    }

//    获取评论的分页信息
    @Override
    public PageVO<ReviewPageVO> getReviewByPage(ReviewPageDTO reviewPageDTO) {
//        初始化pageQuery,赋值pageNum,pageSize
        PageQuery pageQuery = new PageQuery();
        Integer pageNum = reviewPageDTO.getPageNum();
        Integer pageSize = reviewPageDTO.getPageSize();
        Integer isAudit = reviewPageDTO.getIsAudit();
        pageQuery.setPage(pageNum);
        pageQuery.setPageSize(pageSize);
//        设置排序并且获取分页信息
        Page<Review> mpPage = pageQuery.toMpPage(OrderItem.asc("create_time"));
//        查询
            Page<Review> page= reviewMapper.getReviewByPage(isAudit, mpPage);
            Map<Integer, String> userMap = getUserMap(page.getRecords());
            return PageVO.of(page,review->{
                Integer userId = review.getUserId();
                ReviewPageVO reviewPageVO = BeanUtil.copyProperties(review, ReviewPageVO.class);
                reviewPageVO.setUsername(userMap.get(userId));//设置用户名
                Integer isAudit1 = reviewPageVO.getIsAudit();
                if(isAudit1==0){
                    reviewPageVO.setStatusName("违规");
                }else{
                    reviewPageVO.setStatusName("正常");
                }
                return reviewPageVO;
            });
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
    private Map<Integer, String> getUserMap(List<Review> list) {
        Set<Integer> collect = list.stream()
        .map(Review::getUserId)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
//            获取所有的用户名，以map形式返回
        if (collect.isEmpty()) {
            return Collections.emptyMap();
        }
        return userService.listByIds(collect)
        .stream()
        .collect(Collectors.toMap(Users::getId, Users::getUsername));
    }
}




