package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zhijianhu.dto.PostLikeDTO;
import zhijianhu.entity.PostLike;
import zhijianhu.entity.Posts;
import zhijianhu.libraryserver.mapper.PostLikeMapper;
import zhijianhu.libraryserver.service.PostLikeService;
import zhijianhu.libraryserver.service.PostService;


/**
* @author windows
* @description 针对表【post_like(帖子点赞)】的数据库操作Service实现
* @createDate 2025-03-09 19:07:34
*/
@Service
public class PostLikeServiceImpl extends ServiceImpl<PostLikeMapper, PostLike>
    implements PostLikeService {
    private final PostService postService;
    @Lazy
    public PostLikeServiceImpl(PostService postService) {
        this.postService = postService;
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
        Integer id = postDTO.getPostId();
        Integer userId = postDTO.getUserId();
        PostLike postLike = PostLike.builder()
                .postId(id)
                .userId(userId)
                .build();
        Posts post = postService.getById(id);
        if(post!=null){
            boolean save = save(postLike);
            if(like){
                post.setLikeCount(post.getLikeCount()+1);
            }else{
                post.setLikeCount(post.getLikeCount()-1);
            }
            return postService.updateById(post)&&save;
        }
        return false;
    }
}




