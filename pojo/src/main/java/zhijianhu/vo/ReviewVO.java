package zhijianhu.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/6
 * 说明:
 */
@Data
public class ReviewVO implements Serializable {
     private Integer id;
    /**
    * 用户id
    */

    private Integer userId;
    private String userName;

    /**
    * 评论内容
    */

    private String image;
    private String content;
    /**
    * 图书id
    */

    private Integer bookId;
    /**
    * 评论时间
    */

    private LocalDateTime createTime;

    //当前用户是否点赞
    private Boolean isLike;

    private Integer likeCount;
}
