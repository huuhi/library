package zhijianhu.dto;

import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:
 */
@Data
public class AddUserQuestionDTO {
    private Integer userId;
    private Integer borrowRecordId;
    private Short questionType;
    private String note;
}
