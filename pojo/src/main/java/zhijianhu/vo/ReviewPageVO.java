package zhijianhu.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/7
 * 说明:
 */
@Data
public class ReviewPageVO implements Serializable {
    private Integer id;
    private String content;
    private String username;
    private String description;
    private LocalDateTime createTime;
    private Integer isAudit;//状态
    private String statusName;
}
