package zhijianhu.libraryserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"zhijianhu"})
@EnableTransactionManagement //开启注解方式的事务管理
@EnableCaching//开启注解方式的缓存管理
@Slf4j
@EnableScheduling //开启注解方式的定时任务
public class LibraryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServerApplication.class, args);
        log.info("项目启动");
    }

}
