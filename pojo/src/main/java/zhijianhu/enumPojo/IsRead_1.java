package zhijianhu.enumPojo;

import lombok.Getter;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/5
 * 说明:
 */
@Getter
public enum IsRead {
    READ(1,false),
    NOT_READ(0,true);

    private final Integer value;
    private final Boolean isRead;
    IsRead(Integer value, Boolean isRead) {
        this.value = value;
        this.isRead = isRead;
    }
    public static Boolean getIsReadByCode(int code) {
        for (IsRead isRead : IsRead.values()) {
            if (isRead.getValue()==code) {
                return isRead.isRead;
            }
        }
        return null;
    }
}
