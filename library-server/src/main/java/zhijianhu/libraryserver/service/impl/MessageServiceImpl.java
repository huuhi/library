package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zhijianhu.constant.MessageType;
import zhijianhu.entity.Message;
import zhijianhu.entity.Posts;
import zhijianhu.entity.Review;
import zhijianhu.entity.Users;
import zhijianhu.libraryserver.mapper.MessageMapper;
import zhijianhu.libraryserver.service.MessageService;
import zhijianhu.libraryserver.service.PostService;
import zhijianhu.libraryserver.service.ReviewService;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.utils.ServiceUtils;
import zhijianhu.vo.MessageVO;
import zhijianhu.vo.UserNameAndImage;

import java.util.List;
import java.util.Map;

/**
* @author windows
* @description 针对表【message(消息表)】的数据库操作Service实现
* @createDate 2025-03-10 16:46:48
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService {
    private final UsersService usersService;
    private final PostService postService;
    private final ReviewService reviewService;

    @Lazy
    public MessageServiceImpl(UsersService usersService, PostService postService, ReviewService reviewService) {
        this.usersService = usersService;
        this.postService = postService;
        this.reviewService = reviewService;
    }


    @Override
    public List<MessageVO> getLikeMessageByUserId(Integer userId) {
        List<Message> list = lambdaQuery()
                .eq(Message::getType, MessageType.LIKE)
                .eq(Message::getReceiverId, userId)
                .list();
//           直接返回空集合
        if(list==null||list.isEmpty()){
            return List.of();
        }
        Map<Integer, UserNameAndImage> userMap = getIntegerUserNameAndImageMap(list);
        Map<Integer, String> postMap = getPostTitle(list);
//        批量获取评论
        Map<Integer, String> reviewMap = getReviewMap(list);
        List<MessageVO> messageVOList = BeanUtil.copyToList(list, MessageVO.class);
//        更新为已读
        updateIsRead(userId,MessageType.LIKE);


        messageVOList.forEach(m->{
            Integer senderId = m.getSenderId();
            UserNameAndImage user = userMap.get(senderId);
//            mm点赞了
            m.setImage(user.getImage());
            String title=user.getUserName()+ MessageType.LIKE_TITLE;
            Integer reviewId = m.getReviewId();
            Integer postId = m.getPostId();
            if(reviewId!=null){
//          评论只有一种可能，那就是在图书下评论，才会加入消息列表
               m.setContent(reviewMap.get(reviewId));
               title=title+"你的评论";
            }else{
                m.setContent(postMap.get(postId));
                title=title+"你的帖子";
            }
            m.setTitle(title);
        });

        return messageVOList;
    }

    private void updateIsRead(Integer userId,Integer type) {
        LambdaUpdateWrapper<Message> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(Message::getType, type)
                .eq(Message::getReceiverId, userId)
                .eq(Message::getIsRead, MessageType.NOT_READ)
                .set(Message::getIsRead, MessageType.IS_READ);
        update(updateWrapper);
    }

    private Map<Integer, String> getReviewMap(List<Message> list) {
        return ServiceUtils.buildEntityMap(
                list,
                Message::getReviewId,
                reviewService::listByIds,
                Review::getId,
                Review::getContent
        );
    }

    private Map<Integer, String> getPostTitle(List<Message> list) {
        return ServiceUtils
                .buildEntityMap(
                        list,
                        Message::getPostId,
                        postService::listByIds,
                        Posts::getId,
                        Posts::getTitle
                );
    }

    private Map<Integer, UserNameAndImage> getIntegerUserNameAndImageMap(List<Message> list) {
        //        首先是批量获取所有的用户id和用户名
        //        然后是批量获取帖子的标题
        return ServiceUtils
                .buildEntityMap(
                        list,
                        Message::getSenderId,
                        usersService::listByIds,
                        Users::getId,
                        user -> new UserNameAndImage(user.getUsername(), user.getImage())
                );
    }

    @Override
    public List<MessageVO> getReviewMessageByUserId(Integer id) {
        List<Message> list = lambdaQuery()
                .eq(id != null, Message::getReceiverId, id)
                .eq(Message::getType, MessageType.REVIEW)
                .list();
        if(list==null||list.isEmpty()){
            return List.of();// 直接返回空集合
        }

//        批量获取评论id及内容
        Map<Integer, String> reviewMap = getReviewMap(list);
//        批量获取帖子及帖子标题
//        Map<Integer, String> postTitle = getPostTitle(list);
        //批量获取用户的id及用户名和头像url
        Map<Integer, UserNameAndImage> userMap = getIntegerUserNameAndImageMap(list);
        List<MessageVO> messageVOList = BeanUtil.copyToList(list, MessageVO.class);
//        更新为已读
        updateIsRead(id,MessageType.REVIEW);


        messageVOList.forEach(m->{
            UserNameAndImage userNameAndImage = userMap.get(m.getSenderId());
            m.setImage(userNameAndImage.getImage());
            String title=userNameAndImage.getUserName()+MessageType.REVIEW_TITLE;
            m.setTitle(title);
//            评论只有可能是评论了帖子
            m.setContent(reviewMap.get(m.getReviewId()));
        });
        return messageVOList;
    }

    @Override
    public Integer getNotReadCount(Integer userId,Integer type) {
        return lambdaQuery()
                .eq(Message::getReceiverId,userId)
                .eq(Message::getIsRead,MessageType.NOT_READ)
                .eq(type!=null,Message::getType,type)
                .count().intValue();
    }

}




