package zhijianhu.enumPojo;

import lombok.Data;
import lombok.Getter;

/**
 * 活动类型枚举
 */
@Getter
public enum ActivityType {
    BORROW(1, "借阅", "primary"),
    AUDIT(2, "审核", "success"),
    OVERDUE(3, "逾期", "warning"),
    SYSTEM(4, "系统", "info"),
    DAMAGE(5, "损坏", "danger");
    
    private final int code;
    private final String name;
    private final String displayType;
    
    ActivityType(int code, String name, String displayType) {
        this.code = code;
        this.name = name;
        this.displayType = displayType;
    }
}