package zhijianhu.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:
 */
@Data
public class PenaltyRecordVO {
    private Integer id;
    /**
    * 用户id
    */
    private Integer userId;
    private String userName;
    /**
    * 借阅记录id
    */

    private Integer borrowRecordId;
    /**
    * 罚款金额
    */

    private Integer bookId;
    private String bookName;


    private BigDecimal penaltyAmount;
    /**
    * 0未缴纳1已缴纳
    */

    private Integer status;

    private String statusName;
    /**
    * 备注
    */

    private String note;



}

