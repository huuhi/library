package zhijianhu.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/1
 * 说明:
 */
@Data
public class BorrowPageDTO {
    private Integer pageNum;
    private Integer pageSize;
    private Integer userId;
    @Nullable
    private Integer status;
}
