package zhijianhu.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class BorrowingTrendsVO implements Serializable {
    private List<String> months;     // 月份标签
    private List<Integer> borrowed;  // 借出数量
    private List<Integer> returned;  // 归还数量
}