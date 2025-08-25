package zhijianhu.enumPojo;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/4
 * 说明:
 */
@Getter
public enum StatusType {
    OK(1,"已解决"),
    UNRESOLVED(0,"未解决");

    private final int code;
    private final String name;

    StatusType(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public static String getNameByCode(int code) {
        return Arrays.stream(values())
            .filter(s -> s.code == code)
            .findFirst()
                .get().getName();

    }


}
