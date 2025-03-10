package zhijianhu.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 消息表
* @TableName message
*/
@Data
@Builder
public class Message implements Serializable {


    private Integer id;
    /**
    * 1.点赞类型 2.评论类型
    */

    private Integer type;
    /**
    * 发送消息的人
    */

    private Integer senderId;

    private Integer receiverId;

    private Integer isRead;

    private LocalDateTime time;

    private Integer postId;

    private Integer reviewId;


}
