package zhijianhu.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDate;
import java.util.Date;

/**
* 借阅记录表
* @TableName borrow_records
*/
@Data
@Builder
public class BorrowRecords implements Serializable {

    /**
    * 
    */

    private Integer id;
    /**
    * 借书人ID
    */

    private Integer userId;
    /**
    * 图书ID
    */

    private Integer bookId;
    /**
    * 图书图片url
    */

    private String image;
    /**
    * 借阅状态（0已还1在借2违规未还3丢失4损坏）
    */

    private Integer status;

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
