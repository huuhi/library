package zhijianhu.libraryserver.pojo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import zhijianhu.vo.BorrowingTrendsVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:
 */

@Slf4j
public class DataTest {
    @Test
    public void test() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(30);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(now);

        System.out.println(localDateTime.isBefore(now));
        System.out.println(localDateTime.isAfter(now));
    }

    @Test
    public void test2(){
        //        获取前months的月份
        int months=6;
        List<String> monthNames = new ArrayList<>();
        List<Integer> borrowCounts = new ArrayList<>();

        LocalDate now = LocalDate.now();

        for (int i = 0; i < months; i++) {
            // 获取前几个月的月份名称
            LocalDate currentMonth = now.minusMonths(i);
            String monthName = currentMonth.getMonth().getDisplayName(TextStyle.SHORT, Locale.CHINA);
            monthNames.add(monthName);

            // 获取当前月的第一天和最后一天
            LocalDate firstDayOfMonth = currentMonth.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastDayOfMonth = currentMonth.with(TemporalAdjusters.lastDayOfMonth());

            // 查询当月的借阅数量
            log.info("当月第一天: {},最后一天：{}", firstDayOfMonth,lastDayOfMonth);
        }
        for (String monthName : monthNames) {
            System.out.println(monthName);
        }
    }

}
