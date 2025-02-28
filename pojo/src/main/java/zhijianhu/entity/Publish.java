package zhijianhu.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:
 */
@Data
@Builder
public class Publish {
    private Integer id;
    private String name;
    private String address;
}
