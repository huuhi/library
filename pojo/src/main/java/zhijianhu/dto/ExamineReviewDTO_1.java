package zhijianhu.dto;

import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/7
 * 说明:
 */
@Data
public class ExamineReviewDTO {
    private Integer id;
    private String description;
    private Integer status;//评论状态，0 不通过，1 通过
}
