package zhijianhu.result;

import lombok.Builder;
import lombok.Data;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/7
 * 说明:
 */
@Data
@Builder
public class TextResult {
    private String level;//风险等级
    private String text;//风险描述
}
