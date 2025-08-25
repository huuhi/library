package zhijianhu.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:
 */
@Builder
@Data
public class AddressVO implements Serializable {
    private Integer id;
    private String address;
}
