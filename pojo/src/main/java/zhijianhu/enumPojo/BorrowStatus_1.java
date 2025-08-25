package zhijianhu.enumPojo;


import lombok.Getter;

import java.util.Arrays;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/1
 * 说明:
 */

@Getter
public enum BorrowStatus {
    RETURNED(0, "已还"),
    BORROWING(1, "在借"),
    OVERDUE(2, "违规未还"),
    LOST(3, "丢失"),
    DAMAGED(4, "损坏"),
    UNKNOWN(-1, "未知");


    private final int code;
    private final String name;
    // 构造方法、getter...


    BorrowStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(int code) {
        return Arrays.stream(values())
            .filter(s -> s.code == code)
            .findFirst()
            .orElse(UNKNOWN)
            .getName();
    }
}
