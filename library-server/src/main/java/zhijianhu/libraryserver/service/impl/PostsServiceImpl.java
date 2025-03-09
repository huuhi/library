package zhijianhu.libraryserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhijianhu.constant.Level;
import zhijianhu.dto.PostDTO;
import zhijianhu.entity.*;
import zhijianhu.libraryserver.mapper.PostsMapper;
import zhijianhu.libraryserver.service.PostService;
import zhijianhu.libraryserver.service.PostTagsService;
import zhijianhu.libraryserver.service.TagsService;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.result.TextResult;
import zhijianhu.utils.ServiceUtils;
import zhijianhu.utils.TextModerationPlusDemo;
import zhijianhu.vo.PostVO;
import zhijianhu.vo.TagsVO;

import java.util.*;
import java.util.stream.Collectors;


/**
* @author windows
* 针对表【posts】的数据库操作Service实现
* 2025-03-09 13:31:00
*/
@Service
@Slf4j
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts>
    implements PostService {
    private final PostTagsService postTagsService;
    private final UsersService usersService;
    private final TagsService tagsService;
    public PostsServiceImpl(PostTagsService postTagsService, UsersService usersService, UsersServiceImpl usersServiceImpl, TagsService tagsService) {
        this.postTagsService = postTagsService;
        this.usersService = usersService;
        this.tagsService = tagsService;
    }

    @Override
    public boolean sendPost(PostDTO postDTO) {
//      发帖子需要向帖子标签关系表添加,前端发送的标签id一定是存在的
//        首先将帖子插入表中，然后再获取帖子的id，再向帖子标签关系表中插入数据
        String content = postDTO.getContent();
        String title = postDTO.getTitle();
        if(Violation(content,title)){
            return false;
        }
        String image = postDTO.getImage();
        Integer userId = postDTO.getUserId();
        Posts post = Posts.builder()
                .content(content)
                .title(title)
                .userId(userId)
                .image(image)
                .build();
        boolean save = save(post);
        if(save){
//            判断条件，用户id和喜欢数量，帖子还没有发送成功喜欢数量是0，因为一个用户可以发多条帖子
            Integer id = lambdaQuery()
                    .eq(Posts::getUserId, userId)
                    .eq(Posts::getTitle,title)
                    .one()
                    .getId();
            if(id!=null){
                List<Integer> tagsId = postDTO.getTagsId();
                tagsId.forEach(t->{
                    postTagsService.save(
                            PostTags.builder()
                                    .postId(id)
                                    .tagId(t)
                                    .build()
                    );
                });
//              帖子发送成功
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updatePostById(PostDTO postDTO) {
        String content = postDTO.getContent();
        String title = postDTO.getTitle();
        if(Violation(content, title)){
            return false;
        }
        Integer id = postDTO.getId();
        Posts post = getById(id);
        post.setContent(content);
        post.setImage(postDTO.getImage());
        log.debug("修改帖子：{}",post);
        boolean update = updateById(post);
        if(!update) return false;
//          可能需要更新标签
        updateTag(id,postDTO.getTagsId());
        return true;
    }

    @Override
    public List<PostVO> getAllPost(String key,Integer UserId) {
        List<Posts> posts = lambdaQuery()
                .like(key != null, Posts::getTitle, key)
                .eq(UserId!=null,Posts::getUserId,UserId)
                .list();
        List<PostVO> postVOS = BeanUtil.copyToList(posts, PostVO.class);
//        批量用户用户id和用户名，方便插入数据
        Map<Integer, String> Usermap = ServiceUtils.buildEntityMap(
                posts,
                Posts::getUserId,
                usersService::listByIds,
                Users::getId,
                Users::getUsername
        );
//        获取帖子id集合
        Set<Integer> postIds = posts.stream()
                .map(Posts::getId)
                .collect(Collectors.toSet());

//      批量获取标签
        Map<Integer, List<TagsVO>> tagMap = ServiceUtils.buildPostTagMap(
                postIds,
                // 定义如何获取帖子-标签关系
                ids -> postTagsService.lambdaQuery()
                        .in(PostTags::getPostId, ids)
                        .list()
                        .stream()
                        .collect(Collectors.groupingBy(
                                PostTags::getPostId,
                                Collectors.mapping(PostTags::getTagId, Collectors.toList())
                        )),
                // 定义如何获取标签详情
                tagIds -> tagsService.listByIds(tagIds)
                        .stream()
                        .collect(Collectors.toMap(
                                Tags::getId,
                                t -> new TagsVO(t.getName(), t.getThemeColor())
                        ))
        );


        postVOS.forEach(p->{
            Integer userId = p.getUserId();
            p.setUserName(Usermap.get(userId));
//        private List<TagsVO> tags;
//
////    判断是不是我的帖子
//    private Boolean isMy;
            if(Objects.equals(userId, UserContext.getUserId())){
                p.setIsMy(true);//是我的帖子
            }
            p.setTags(tagMap.getOrDefault(p.getId(), Collections.emptyList()));
        });
        return postVOS;
    }

    private void updateTag(Integer id,List<Integer> newTags){
//          List<Integer> newTags = postDTO.getTagsId();
        log.debug("更新标签, id:{},新加标签id:{}",id,newTags);
        //获取旧标签
        List<Integer> oldTags = postTagsService.lambdaQuery()
//                .select(PostTags::getTagId)
                .eq(PostTags::getPostId, id)
                .list()
                .stream()
                .map(PostTags::getTagId)
                .toList();

//        找到旧标签中需要删除的标签，也就是不包含在新标签中的标签
        List<Integer> needDelete = oldTags.stream()
                .filter(oldTag -> !newTags.contains(oldTag))
                .toList();
//       真的需要新增的标签，也就是旧标签中没有的新标签
        List<Integer> needAdd = newTags.stream()
                .filter(newTag -> !oldTags.contains(newTag))
                .toList();
//        批量删除旧标签
        if(!needDelete.isEmpty()){
             postTagsService.lambdaUpdate()
                    .eq(PostTags::getPostId,id)
                    .in(PostTags::getTagId,needDelete)
                     .remove();
        }
//        批量插入新加的标签
        if(!needAdd.isEmpty()){
            List<PostTags> list = needAdd.stream()
                    .map(tagId -> PostTags.builder()
                            .postId(id)
                            .tagId(tagId)
                            .build())
                    .toList();
            postTagsService.saveBatch(list);
        }
    }
//   ai审核内容是否违规！
    private Boolean Violation(String content,String title){
        TextResult textResult = Objects.requireNonNull(TextModerationPlusDemo.DetectionText(content));
        TextResult titleResult = Objects.requireNonNull(TextModerationPlusDemo.DetectionText(title));
        String level = textResult.getLevel();
        String titleLevel = titleResult.getLevel();
        log.warn("内容说明:{} , 标题说明：{}",textResult.getText(),titleResult.getText()) ;

        //            log.warn("内容说明:{} , 标题说明：{}",textResult.getText(),titleResult.getText()) ;
        return !level.equals(Level.LEVEL_1) || !titleLevel.equals(Level.LEVEL_1);
    }

}
