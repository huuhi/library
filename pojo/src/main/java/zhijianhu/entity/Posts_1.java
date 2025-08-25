package zhijianhu.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName posts
*/
@Data
@Builder
public class Posts implements Serializable {

    /**
    * 
    */

    private Integer id;
    /**
    *
    */

    private Integer userId;

    private String content;

    private String title;
    /**
    *
    */

    private String image;
    /**
    *
    */

    private LocalDateTime createdTime;
    /**
    *
    */

    private LocalDateTime updatedTime;
//  点赞数量
    private Integer likeCount;
//    评论数量
    private Integer reviewCount;

}
