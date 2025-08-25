package zhijianhu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/4
 * 说明:
 */
@Data
public class QuestionWorkOutDTO implements Serializable {
    private Integer id;
    private Integer status;
    private Integer managerId;
    private String processNote;
}
