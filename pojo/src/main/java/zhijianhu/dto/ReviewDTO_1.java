package zhijianhu.dto;

import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:
 */
@Data
public class ReviewDTO {
    private Integer userId;
    private String image;
    private String content;
    /**
    * 图书id
    */
    private Integer bookId;
    private Integer postId;
}
