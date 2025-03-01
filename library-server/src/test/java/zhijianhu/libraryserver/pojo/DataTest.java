package zhijianhu.libraryserver.pojo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:
 */

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

}
