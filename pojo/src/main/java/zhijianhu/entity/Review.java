package zhijianhu.entity;



import lombok.Data;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Date;

/**
* 图书评论表
* @TableName review
*/
@Data
public class Review implements Serializable {

    /**
    * 
    */

    private Integer id;
    /**
    * 用户id
    */

    private Integer userId;

    private String image;

    /**
    * 评论内容
    */

    private String content;
    /**
    * 图书id
    */

    private Integer bookId;
    /**
    * 评论时间
    */

    private LocalDateTime createTime;

    private Integer isAudit;

    private Integer likeCount;

//评论说明
    private String description;

    private Integer postId;

}
