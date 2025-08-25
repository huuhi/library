package zhijianhu.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
* 评论点赞表
* @TableName review_like
*/
@Data
@Builder
public class ReviewLike implements Serializable {
    /**
    * 评论id
    */

    private Integer reviewId;
    /**
    * 用户id
    */

    private Integer userId;

}
