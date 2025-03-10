package zhijianhu.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/10
 * 说明:
 */
@Data
public class MessageVO {
    private Integer id;
//    private String username;
    private String title;//mm点赞了你的mm，或者mm评论了你的mm
    private Integer senderId;
//    根据类型展示内容，如果在帖子下评论直接展示评论
// 如果是点赞，需要展示点赞的内容，比如评论，如果点赞了帖子，则展示帖子的标题
    private String content;
    private String image;//头像
    private LocalDateTime time;
    private Integer reviewId;
    private Integer postId;
}
