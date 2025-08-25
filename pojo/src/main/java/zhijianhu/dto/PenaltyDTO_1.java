package zhijianhu.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:
 */
@Data
public class PenaltyDTO {
    private Integer id;
    private Integer status;
    private String note;
    private BigDecimal penaltyAmount;
}
