package zhijianhu.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/8
 * 说明:
 */
@Data
public class AuditLogPageVO implements Serializable {
    private Integer id;
    /**
    * 操作人id
    */

    private Integer userId;

    private String userName;
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
