package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.PostLikeDTO;
import zhijianhu.entity.PostLike;

/**
* @author windows
* @description 针对表【post_like(帖子点赞)】的数据库操作Service
* @createDate 2025-03-09 19:07:34
*/
public interface PostLikeService extends IService<PostLike> {

    boolean likePost(PostLikeDTO postDTO);

    boolean unlikePost(PostLikeDTO postDTO);
}
