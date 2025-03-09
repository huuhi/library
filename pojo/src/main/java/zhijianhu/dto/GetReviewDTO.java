package zhijianhu.dto;

import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/9
 * 说明:
 */
@Data
public class GetReviewDTO {
    private Integer userId;
    /**
    * 图书id
    */
    private Integer bookId;
    private Integer postId;

}
