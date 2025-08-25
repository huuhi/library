package zhijianhu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/24
 * 说明:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable{
    private Integer id;

    private String username;

    private String image;

    private String name;

    private String token;

//    用户类型 0-普通用户 1-管理员
    private Integer role;
}
