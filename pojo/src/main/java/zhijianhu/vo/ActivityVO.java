package zhijianhu.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/8
 * 说明:
 */
@Data
public class ActivityVO {
    private Long id;           // 活动ID
    private String type;       // 活动类型（primary, success, warning, info, danger）
    private String content;    // 活动内容
    private Date time;         // 活动时间
}
