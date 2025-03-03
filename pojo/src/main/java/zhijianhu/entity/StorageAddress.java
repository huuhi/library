package zhijianhu.entity;


import lombok.Data;

import java.io.Serializable;

/**
* 
* @TableName storage_address
*/
@Data
public class StorageAddress implements Serializable {
    private Integer id;
    /**
    * 地区
    */

    private String area;
    /**
    * 书架编号
    */

    private String shelf;

    private Integer floor;

    private Integer rackLayer;

    private String address;



}
