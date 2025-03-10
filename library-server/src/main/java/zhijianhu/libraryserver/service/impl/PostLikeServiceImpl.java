package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zhijianhu.constant.MessageType;
import zhijianhu.dto.PostLikeDTO;
import zhijianhu.entity.Message;
import zhijianhu.entity.PostLike;
import zhijianhu.entity.Posts;
import zhijianhu.entity.Users;
import zhijianhu.libraryserver.mapper.PostLikeMapper;
import zhijianhu.libraryserver.mapper.UsersMapper;
import zhijianhu.libraryserver.service.MessageService;
import zhijianhu.libraryserver.service.PostLikeService;
import zhijianhu.libraryserver.service.PostService;

import java.util.Objects;


/**
* @author windows
* @description 针对表【post_like(帖子点赞)】的数据库操作Service实现
* @createDate 2025-03-09 19:07:34
*/
@Service
public class PostLikeServiceImpl extends ServiceImpl<PostLikeMapper, PostLike>
    implements PostLikeService {
    private final PostService postService;
    private final MessageService messageService;
    private final UsersMapper usersMapper;
    @Lazy
    public PostLikeServiceImpl(PostService postService, MessageService messageService, UsersMapper usersMapper) {
        this.postService = postService;
        this.messageService = messageService;
        this.usersMapper = usersMapper;
    }

    @Override
    public boolean likePost(PostLikeDTO postDTO) {
        return isLike(postDTO,true);
    }

    @Override
    public boolean unlikePost(PostLikeDTO postDTO) {
        return isLike(postDTO,false);
    }

    private Boolean isLike(PostLikeDTO postDTO,boolean like){
//       3/10 点赞帖子优化，如果用户是点赞需要添加到message表中通知帖子的发布者
        Integer id = postDTO.getPostId();
        Integer userId = postDTO.getUserId();

        Posts post = postService.getById(id);
        if(post!=null){
            boolean save=false;
            if(like){
                PostLike postLike = PostLike.builder()
                    .postId(id)
                    .userId(userId)
                    .build();
                    save = save(postLike);
                    saveMessage(postDTO, post);
                post.setLikeCount(post.getLikeCount()+1);
            }else{
                save = lambdaUpdate()
                        .eq(PostLike::getPostId, id)
                        .eq(PostLike::getUserId, userId)
                        .remove();
                post.setLikeCount(post.getLikeCount()-1);
            }
            return postService.updateById(post)&&save;
        }
        return false;
    }

    private void saveMessage(PostLikeDTO postDTO, Posts post) {
        //                点赞 根据帖子获取发布者的id
        Integer receiverId = post.getUserId();
//                点赞的用户id
        Integer  senderId= postDTO.getUserId();
//        同一个人不需要消息~
        if(Objects.equals(receiverId, senderId)){
            return;
        }
        Message message = Message.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .postId(postDTO.getPostId())
                .type(MessageType.LIKE).build();
        messageService.save(message);
    }
}




