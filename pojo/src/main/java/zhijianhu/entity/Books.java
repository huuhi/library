package zhijianhu.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 图书信息表
 * @TableName books
 */
@TableName(value ="books")
@Data
public class Books implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 书名
     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 分类ID
     */
    private Integer clazzId;



    /**
     * 存放地址ID
     */
    private Integer addressId;

    /**
     * 出版社
     */
    private Integer publishId;

    /**
     * 国际标准书号
     */
    private String isbn;

    /**
     * 图书简介
     */
    private String profile;

    /**
     * 1 可借 0不可借
     */
    private Integer status;

    /**
     * 出版日期
     */
    private Date publishDate;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 
     */
    private String image;


}