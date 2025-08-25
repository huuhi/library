package zhijianhu.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/4
 * 说明:
 */
@Data
@Builder
public class UserQuestionPageDTO implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Integer status;
    private Integer type;
}
