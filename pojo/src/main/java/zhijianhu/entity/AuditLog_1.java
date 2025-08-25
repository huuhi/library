package zhijianhu.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
* 操作日志表
* @TableName audit_log
*/
@Data
@Builder
public class AuditLog implements Serializable {

    /**
    * 
    */

    private Integer id;
    /**
    * 操作人id
    */

    private Integer userId;
    /**
    * 操作时间
    */

    private LocalDateTime operationTime;
    /**
    * 操作方法名
    */

    private String methodName;
    /**
    * 方法参数
    */

    private String methodParams;
    /**
    * 耗时
    */

    private Long costTime;
    /**
    * 备注
    */

    private String note;

    private String className;

}
