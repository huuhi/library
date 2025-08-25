package zhijianhu.libraryserver.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zhijianhu.libraryserver.service.BorrowRecordsService;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/11
 * 说明:
 */
@Component
@Slf4j
public class BorrowTask {
    private final BorrowRecordsService borrowRecordsService;

    public BorrowTask(BorrowRecordsService borrowRecordsService) {
        this.borrowRecordsService = borrowRecordsService;
    }

//    明天18:00执行一次，检查借阅时间是否超过应还时间
    @Scheduled(cron = "0 0 18 * * ? ")
    public void CheckBorrowReturnTime(){
        log.info("检查借阅记录是否有违规未还");
        Boolean is= borrowRecordsService.checkBorrowReturnTime();
        if (is) {
            log.info("有违规还的记录");
        } else {
            log.info("没有");
        }
    }

}
