package zhijianhu.entity;


import lombok.Data;

import java.io.Serializable;

/**
* 消息表
* @TableName message
*/
@Data
public class Message implements Serializable {


    private Integer id;
    /**
    * 1.点赞类型 2.评论类型
    */

    private Integer type;
    /**
    * 发送消息的人
    */

    private String sender;

    private Integer receiverId;

    private Integer isRead;


}
