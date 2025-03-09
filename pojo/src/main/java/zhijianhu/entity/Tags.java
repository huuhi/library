package zhijianhu.entity;


import lombok.Data;

import java.io.Serializable;

/**
* 
* @TableName tags
*/
@Data
public class Tags implements Serializable {

    /**
    * 
    */

    private Integer id;
    /**
    * 
    */

    private String name;

    private String themeColor;

    private Integer createdBy;



}
