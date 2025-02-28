package zhijianhu.dto;

import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:借书还书DTO
 */
@Data
public class ChangeBookStatusDTO {
    private Integer bookId;
    private Integer userId;
    private Integer status;
}
