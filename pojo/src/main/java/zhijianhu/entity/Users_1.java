package zhijianhu.entity;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户信息表
* @TableName users
*/
@Data
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

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
    * 密码（bcrypt加密）
    */

    private String password;
    /**
    * 身份证号
    */

    private String cardId;
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

    private Integer status;
    /**
    * 最大借书数限制
    */

    private Integer confine;
    /**
    * 性别（0未知 1男 2女）
    */
    private Integer gender;
    /**
    * 角色（0用户 1管理员）
    */

    private Integer role;
    /**
    * 注册时间
    */

    private LocalDateTime regTime;
    /**
    * 最后登录时间
    */
    private LocalDateTime lastLogin;
    /**
    * 用户地址
    */

    private String address;
    /**
    * 封禁原因
    */

    private String violationReason;



}
