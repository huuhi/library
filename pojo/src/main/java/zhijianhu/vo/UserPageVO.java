package zhijianhu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/24
 * 说明:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageVO {
    /**
    * 用户ID
    */

    private Integer id;
    /**
    * 用户名
    */

    private String username;
    /**
    * 真实姓名
    */
    private String name;
    /**
    * 手机号
    */

    private String phone;
    /**
    *
    */

    private String image;
    /**
    * 状态（1正常 0封禁）
    */

    private String status;
    /**
    * 性别（0未知 1男 2女）
    */
    private String gender;
    /**
    * 角色（0用户 1管理员）
    */
    private String role;
    /**
    * 注册时间
    */
    private LocalDateTime regTime;
    /**
    * 最后登录时间
    */
    private LocalDateTime lastLogin;
    /**
    * 封禁原因
    */
    private String violationReason;

}
