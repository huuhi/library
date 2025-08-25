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
public class UserLoginDTO implements Serializable{

    private String username;

    private String password;

}
