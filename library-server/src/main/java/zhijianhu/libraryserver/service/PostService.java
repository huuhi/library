package zhijianhu.libraryserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.PostDTO;
import zhijianhu.entity.Posts;
import zhijianhu.vo.PostVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/9
 * 说明:
 */
public interface PostService extends IService<Posts> {
    boolean sendPost(PostDTO postDTO);

    boolean updatePostById(PostDTO postDTO);

    List<PostVO> getAllPost(String key, Integer userId);

    PostVO getPostById(Integer id);

//    boolean likePost(PostLikeDTO postDTO);

}
