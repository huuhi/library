package zhijianhu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/25
 * 说明:
 */
@Data
public class BookVO implements Serializable {
     private Integer id;

    /**
     * 书名
     */
    private String name;

    /**
     * 作者
     */
    private String author;


//    private String clazz;
//    private Integer clazzId;

    /**
     * 存放地址ID
     */
    private String address;

    private Integer addressId;

    /**
     * 出版社
     */
    private String publishingHouse;

//    private Integer publishId;

    /**
     * 国际标准书号
     */

    private String ISBN;

    /**
     * 图书简介
     */
    private String profile;

    /**
     * 1 可借 0不可借
     */
    private String status;



    /**
     * 出版日期
     */
    private Date publishDate;

    /**
     *
     */
    private String image;

    private String quote;

    private Double rating;


}
