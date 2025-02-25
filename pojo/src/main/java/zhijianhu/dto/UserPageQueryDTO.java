package zhijianhu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/24
 * 说明:
 */
@Data
public class UserPageQueryDTO implements Serializable{
    private Integer page;
    private Integer pageSize;
    private String username;
    private Integer role;
    private Integer status;
}
