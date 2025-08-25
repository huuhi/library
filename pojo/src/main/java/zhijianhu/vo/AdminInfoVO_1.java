package zhijianhu.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class AdminInfoVO implements Serializable {
    private Long id;           // 管理员ID
    private String name;       // 管理员姓名
    private String username;   // 用户名
    private String image;      // 头像URL
    private LocalDateTime regTime;      // 注册/入职时间
//    private Integer roleLevel; // 角色级别
}