package zhijianhu.dto;

import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/2
 * 说明:
 */
@Data
public class PublishPageDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String name;
}
