package zhijianhu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/1
 * 说明:
 */
@Data
public class BorrowVO implements Serializable {
    private Integer id;
    /**
    * 借书人ID
    */

    private Integer userId;

    private String userName;
    /**
    * 图书ID
    */

    private Integer bookId;


    private String bookName;
    /**
    * 图书图片url
    */

    private String image;
    /**
    * 借阅状态（0已还1在借2违规未还3丢失4损坏）
    */

    private Integer status;

    private String statusName;

    private LocalDate lendTime;
    /**
    * 应还时间
    */

    private LocalDate mustReturnTime;
    /**
    * 实际还时间
    */

    private LocalDate returnTime;
    /**
    * 逾期天数
    */

    private Integer overdueDays;
    /**
    * 违规原因
    */

    private String violationReason;

    private BigDecimal penaltyAmount;
    /**
    * 备注
    */

    private String note;
}
