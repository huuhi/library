package zhijianhu.entity;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer parentId;



}
