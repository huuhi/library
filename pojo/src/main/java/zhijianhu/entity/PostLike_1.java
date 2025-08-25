package zhijianhu.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
* 帖子点赞
* @TableName post_like
*/
@Data
@Builder
public class PostLike implements Serializable {

    /**
    * 帖子id
    */

    private Integer postId;
    /**
    * 用户id
    */

    private Integer userId;

}
