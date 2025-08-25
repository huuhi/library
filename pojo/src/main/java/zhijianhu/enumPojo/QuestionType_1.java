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
public enum QuestionType {
//    0=罚款申诉 1=借阅申诉 2=评论申诉 3=其他
    FINE_APPEAL(0,"罚款申诉"),
    LOAN_APPEAL(1,"借阅申诉"),
    COMMENT_APPEAL(2,"评论申诉"),
    OTHER_APPEAL(3,"其他"),
    UNKNOWN(4,"未知");

    private final int code;
    private final String name;
    QuestionType(int code, String name) {
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
