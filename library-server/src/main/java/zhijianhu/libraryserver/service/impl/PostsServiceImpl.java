package zhijianhu.libraryserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhijianhu.constant.Level;
import zhijianhu.dto.PostDTO;
import zhijianhu.entity.*;
import zhijianhu.libraryserver.mapper.PostsMapper;
import zhijianhu.libraryserver.service.*;
import zhijianhu.result.TextResult;
import zhijianhu.utils.RedisUtil;
import zhijianhu.utils.ServiceUtils;
import zhijianhu.utils.TextModerationPlusDemo;
import zhijianhu.vo.PostVO;
import zhijianhu.vo.TagsVO;
import zhijianhu.vo.UserNameAndImage;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static zhijianhu.constant.RedisConstant.CACHE_POST_KEY;
import static zhijianhu.constant.RedisConstant.CACHE_POST_TTL;


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
    private final PostLikeService postLikeService;
    private final StringRedisTemplate redisTemplate;
    public PostsServiceImpl(PostTagsService postTagsService, UsersService usersService, TagsService tagsService, PostLikeService postLikeService, StringRedisTemplate redisTemplate) {
        this.postTagsService = postTagsService;
        this.usersService = usersService;
        this.tagsService = tagsService;
        this.postLikeService = postLikeService;
        this.redisTemplate = redisTemplate;
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
//            判断标题,标题是唯一的
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
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
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
        String key=CACHE_POST_KEY+id;
        redisUtil.delCache(key);//删除缓存
        return true;
    }

    @Override
    public List<PostVO> getAllPost(String key,Integer UserId) {
        List<Posts> posts = lambdaQuery()
                .like(key != null, Posts::getTitle, key)
                .eq(UserId!=null,Posts::getUserId,UserId)
                .list();
        if(posts == null || posts.isEmpty()){
            return List.of();//说明没有帖子
        }
        List<PostVO> postVOS = BeanUtil.copyToList(posts, PostVO.class);
//        批量用户用户id和用户名头像，方便插入数据
        Map<Integer, UserNameAndImage> Usermap = ServiceUtils.buildEntityMap(
                posts,
                Posts::getUserId,
                usersService::listByIds,
                Users::getId,
                user -> new UserNameAndImage(user.getUsername(), user.getImage())
        );

        Map<Integer, List<TagsVO>> tagMap = initTagMap(posts);

        postVOS.forEach(p->{
            Integer userId = p.getUserId();
            p.setUserName(Usermap.get(userId).getUserName());
            p.setUserImage(Usermap.get(userId).getImage());
//    判断是不是我的帖子
//    private Boolean isMy;
            if(Objects.equals(userId, UserContext.getUserId())){
                p.setIsMy(true);//是我的帖子
            }
            p.setTags(tagMap.getOrDefault(p.getId(), Collections.emptyList()));
        });
        return postVOS;
    }

    @Override
    public PostVO getPostById(Integer id) {
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        String postKey=CACHE_POST_KEY+id;
//        因为有太多业务操作了，所以这里只能调用基本的存储
        PostVO cache = redisUtil.getCache(postKey, PostVO.class);
        if(cache!=null){
            return cache;
        }
        Posts post = getById(id);
        if(post!=null){
            ArrayList<Posts> list = new ArrayList<>();
            list.add(post);
            Map<Integer, List<TagsVO>> tagMap = initTagMap(list);
            PostVO postVO = BeanUtil.copyProperties(post, PostVO.class);
            Integer userId = postVO.getUserId();
            Users user = usersService.getById(userId);
            postVO.setUserName(user.getUsername());
            postVO.setUserImage(user.getImage());
            if(Objects.equals(userId, UserContext.getUserId())){
                postVO.setIsMy(true);//是我的帖子
            }
//            还需要判断是否点赞
            PostLike one = postLikeService.lambdaQuery()
                    .eq(PostLike::getPostId, id)
                    .eq(PostLike::getUserId, UserContext.getUserId())
                    .one();
            if(one!=null){
                postVO.setIsLike(true);
            }
            postVO.setTags(tagMap.getOrDefault(postVO.getId(), Collections.emptyList()));
//            存储在redis中
            redisUtil.set(postKey,postVO,CACHE_POST_TTL, TimeUnit.MINUTES);
            return postVO;
        }
        return null;
    }

    private void updateTag(Integer id,List<Integer> newTags){
//          List<Integer> newTags = postDTO.getTagsId();
        if(newTags==null||newTags.isEmpty()||newTags.contains(null)){
            log.debug("不需要更新标签:{}",newTags);
            return;//不需要更新标签
        }
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
    private Map<Integer,List<TagsVO>> initTagMap(List<Posts> posts){
//        获取帖子id集合
        Set<Integer> postIds = posts.stream()
                .map(Posts::getId)
                .collect(Collectors.toSet());

//      批量获取标签
        return ServiceUtils.buildPostTagMap(
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
    }

}
