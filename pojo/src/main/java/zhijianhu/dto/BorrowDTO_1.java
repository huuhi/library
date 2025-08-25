package zhijianhu.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:
 */
@Data
public class BorrowDTO implements Serializable {
    private Integer id;
    private Integer status;
    /**
    * 违规原因
    */
    private String violationReason;

//    罚款金额
    private BigDecimal penaltyAmount;
    /**
    * 备注
    */

    private String note;
}
