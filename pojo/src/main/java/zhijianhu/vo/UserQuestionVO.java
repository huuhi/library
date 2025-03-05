package zhijianhu.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/5
 * 说明:
 */
@Data
public class UserQuestionVO {
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

    private String appealTypeName;
    /**
    * 0=待处理 1=已解决
    */

    private Integer status;

    private String statusName;
    /**
    * 处理人ID
    */

    private Integer managerId;

    private String managerName;
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
//  反馈时间
    private LocalDateTime createTime;

//  处理时间
    private LocalDateTime endTime;
    private Integer isReadStatus;

    private Boolean isRead;
}
