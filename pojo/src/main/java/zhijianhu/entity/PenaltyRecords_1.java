package zhijianhu.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* 用户违规缴纳罚款记录表
* @TableName penalty_records
*/
@Data
@Builder
public class PenaltyRecords implements Serializable {


    private Integer id;
    /**
    * 用户id
    */

    private Integer userId;
    /**
    * 借阅记录id
    */

    private Integer borrowRecordId;
    /**
    * 罚款金额
    */

    private BigDecimal penaltyAmount;
    /**
    * 0未缴纳1已缴纳
    */

    private Integer status;
    /**
    * 备注
    */

    private String note;


    private Integer bookId;

}
