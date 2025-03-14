package zhijianhu.libraryserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.PostDTO;
import zhijianhu.enumPojo.ActivityType;
import zhijianhu.libraryserver.annotation.LogActivity;
import zhijianhu.libraryserver.service.PostService;
import zhijianhu.result.Result;
import zhijianhu.vo.PostVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/9
 * 说明:帖子相关接口
 */
@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //    首先是发帖子
    @PostMapping("/send")
    @LogActivity(
        type = ActivityType.AUDIT,
        description = "#{postDTO?.getUserId ?: '未知用户'} 发布了帖子"
    )

    public Result<Void> sendPost(@RequestBody PostDTO postDTO){
        log.info("发送帖子");
        boolean success=postService.sendPost(postDTO);
        return success?Result.success():Result.error(MessageConstant.SEND_POST_FAILURE);
    }
    @PutMapping("/update")
    public Result<Void> updatePost(@RequestBody PostDTO postDTO){
        log.info("修改帖子：{}",postDTO);
        boolean success=postService.updatePostById(postDTO);
        return success?Result.success():Result.error(MessageConstant.UPDATE_POST_FAILURE);
    }
//  获取帖子
    @GetMapping("/list")
    public Result<List<PostVO>> getAllPost(String keyWords){
        log.info("获取帖子");
        List<PostVO> postVOList=postService.getAllPost(keyWords,null);
        return Result.success(postVOList);
    }
//    根据用户id获取帖子
    @GetMapping("/{userId}")
    public Result<List<PostVO>> getPostById(@PathVariable("userId") Integer id){
        log.info("获取帖子");
        List<PostVO> postVOList=postService.getAllPost(null,id);
        return Result.success(postVOList);
    }
//   根据id删除
    @DeleteMapping("/{id}")
    public Result<Void> deletePostById(@PathVariable("id") Integer id){
        log.info("删除帖子");
        boolean success=postService.removeById(id);
        return success?Result.success():Result.error(MessageConstant.DELETE_POST_FAILURE);
    }
//    根据帖子id获取
    @GetMapping("/get/{id}")
    public Result<PostVO>  getById(@PathVariable("id") Integer id){
        PostVO post= postService.getPostById(id);
        return Result.success(post);
    }

}
