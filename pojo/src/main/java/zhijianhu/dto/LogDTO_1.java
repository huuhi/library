package zhijianhu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/8
 * 说明:
 */
@Data
public class LogDTO implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Integer userId;
}
