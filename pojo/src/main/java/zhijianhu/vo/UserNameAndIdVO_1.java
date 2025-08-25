package zhijianhu.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:
 */
@Data
public class UserNameAndIdVO implements Serializable {
    private Integer id;
    private String username;
}
