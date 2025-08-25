package zhijianhu.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AdminStatisticsVO implements Serializable {
    private Integer totalUsers;          // 总用户数
    private Double userGrowth;           // 用户增长率
    private Integer totalBooks;          // 总图书数
    private Double bookGrowth;           // 图书增长率
    private Integer monthlyBorrows;      // 本月借阅数
    private Double borrowGrowth;         // 借阅增长率
    private Integer resolvedIssues;      // 已处理问题数
    private Double issueResolutionRate;  // 问题解决率
}