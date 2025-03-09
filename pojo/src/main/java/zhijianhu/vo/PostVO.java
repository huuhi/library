package zhijianhu.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/9
 * 说明:返回帖子实体类
 */
@Data
public class PostVO {

    private Integer id;

    private Integer userId;

    private String userName;

    private String title;

    private String content;

    private String image;

//    最后更新时间
    private LocalDateTime updatedTime;

//    点赞数量
    private Integer likeCount;
//   评论数量
    private Integer reviewCount;

    private List<TagsVO> tags;

//    判断是不是我的帖子
    private Boolean isMy;
}
