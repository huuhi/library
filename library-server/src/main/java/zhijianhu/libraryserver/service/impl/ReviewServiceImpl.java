package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import zhijianhu.constant.MessageType;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.ExamineReviewDTO;
import zhijianhu.dto.GetReviewDTO;
import zhijianhu.dto.ReviewDTO;
import zhijianhu.dto.ReviewPageDTO;
import zhijianhu.entity.*;
import zhijianhu.libraryserver.service.*;
import zhijianhu.libraryserver.mapper.ReviewMapper;
import org.springframework.stereotype.Service;
import zhijianhu.query.PageQuery;
import zhijianhu.result.TextResult;
import zhijianhu.utils.ServiceUtils;
import zhijianhu.utils.TextModerationPlusDemo;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.ReviewPageVO;
import zhijianhu.vo.ReviewVO;

import java.util.*;
/**
* @author windows
* @description 针对表【review(图书评论表)】的数据库操作Service实现
* @createDate 2025-03-05 18:03:24
*/
@Service
@Slf4j
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
    implements ReviewService{
    private final ReviewLikeService reviewLikeService;
    private final UsersService userService;
    private final ReviewMapper reviewMapper;
    private final PostService postService;
    private final MessageService messageService;

    public ReviewServiceImpl(ReviewLikeService reviewLikeService,
                             UsersService userService, ReviewMapper reviewMapper,
                             PostService postService, MessageService messageService) {
        this.reviewLikeService = reviewLikeService;
        this.userService = userService;
        this.reviewMapper = reviewMapper;
        this.postService=postService;
        this.messageService = messageService;
    }

    @Override
    @Transactional
    public Boolean sendReview(ReviewDTO reviewDTO) {
//        发送评论
//        如果是在帖子下发送评论需要指向额外的操作
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
//            保存但是不展示，让管理员审核
            save(review);
            return false;
        }
        Integer postId = reviewDTO.getPostId();
        boolean save = save(review);
        if(postId!=null){
//            帖子评论加1
            addReviewCount(reviewDTO.getPostId());
//           说明是给帖子评论，添加，不需要给图书评论添加消息列表
//         这里不仅需要帖子的id，还需要获取评论的id，可以根据帖子的id和用户id获取评论的id
            Integer reviewId = review.getId();
            Integer userId = postService.getById(postId).getUserId();
            saveMessage(userId,review.getUserId(),postId,reviewId);
        }
        return save;
    }

    private void addReviewCount(Integer postId) {
        Posts post = postService.getById(postId);
        post.setReviewCount(post.getReviewCount()+1);
        postService.updateById(post);
    }

    @Override
    public List<ReviewVO> getReviewByBookId(GetReviewDTO dto) {
        Integer postId = dto.getPostId();
        Integer bookId = dto.getBookId();
        Integer userId = dto.getUserId();
        List<Review> list = lambdaQuery()
                .eq(bookId!=null, Review::getBookId, bookId)
                .eq(postId != null, Review::getPostId, postId)
                .list();
        if(list!=null&& !list.isEmpty()){
//            获取所有的用户id
            Map<Integer, String> map = ServiceUtils.buildEntityMap(
                    list,
                    Review::getUserId,
                    userService::listByIds,
                    Users::getId,
                    Users::getUsername
            );
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
            Map<Integer, String> userMap = ServiceUtils.buildEntityMap(
                    page.getRecords(),
                    Review::getUserId,
                    userService::listByIds,
                    Users::getId,
                    Users::getUsername
            );
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
    private void saveMessage(Integer receiverId, Integer senderId, Integer postId, Integer reviewId){
            if(Objects.equals(receiverId, senderId)){
                return;
            }
            Message message = Message.builder()
                    .receiverId(receiverId)
                    .senderId(senderId)
                    .postId(postId)
                    .reviewId(reviewId)
                    .type(MessageType.REVIEW).build();
            messageService.save(message);
    }
}




