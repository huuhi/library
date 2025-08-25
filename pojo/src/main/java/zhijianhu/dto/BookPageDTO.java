package zhijianhu.dto;

import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/25
 * 说明:
 */
@Data
public class BookPageDTO {
    private String name;
    private Integer status;
    private Integer page=1;
    private Integer pageSize=10;
//    private Integer categoryId;


}
