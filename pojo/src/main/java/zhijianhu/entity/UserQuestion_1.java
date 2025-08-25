package zhijianhu.entity;



import lombok.Data;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Date;


/**
* 
* @TableName user_question
*/
@Data
public class UserQuestion implements Serializable {

    /**
    * 
    */

    private Integer id;
    /**
    * 用户ID
    */

    private Integer userId;
    /**
    * 关联借阅记录ID
    */

    private Integer borrowRecordId;
    /**
    * 0=罚款申诉 1=借阅申诉 2=评论申诉 3=其他
    */

    private Integer appealType;
    /**
    * 0=待处理 1=已解决
    */

    private Integer status;
    /**
    * 处理人ID
    */

    private Integer managerId;
    /**
    * 用户提交的简要描述
    */

    private String note;
    /**
    * 处理意见
    */

    private String processNote;
    /**
    * 创建时间
    */

    private LocalDateTime createTime;

    private LocalDateTime endTime;

    private Integer isReadStatus;



}
