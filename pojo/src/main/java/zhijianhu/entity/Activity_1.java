package zhijianhu.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 活动记录表
* @TableName activity
*/
@Data
public class Activity implements Serializable {

    /**
    * 活动ID
    */

    private Integer id;

    private Integer activityType;
    /**
    * 活动内容
    */

    private String content;
    /**
    * 活动时间
    */

    private Date time;
    /**
    * 相关用户ID
    */

    private Integer userId;
    /**
    * 相关图书ID
    */

    private Long bookId;
    /**
    * 相关管理员ID
    */

    private Long adminId;

    /**
    * 活动ID
    */

}
