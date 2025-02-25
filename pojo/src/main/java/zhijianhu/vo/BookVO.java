package zhijianhu.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/25
 * 说明:
 */
@Data
public class BookVO {
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
    private String clazz;

    /**
     * 存放地址ID
     */
    private String address;

    /**
     * 出版社
     */
    private String publish;

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
    private String status;

    /**
     * 出版日期
     */
    private Date publishDate;

    /**
     *
     */
    private String image;


}
