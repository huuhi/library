package zhijianhu.entity;


import lombok.Data;

import java.io.Serializable;

/**
* 
* @TableName book_classes
*/
@Data
public class BookClasses implements Serializable {


    private Integer id;

    private String name;
    /**
    * 父分类ID
    */

    private Integer parentId;



}
