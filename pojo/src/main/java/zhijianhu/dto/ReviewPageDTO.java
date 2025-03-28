package zhijianhu.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/7
 * 说明:
 */
@Data
@Builder
public class ReviewPageDTO {
    private Integer pageNum;
    private Integer pageSize;
    private Integer isAudit;
}
