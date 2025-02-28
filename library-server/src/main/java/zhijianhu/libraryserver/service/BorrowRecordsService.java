package zhijianhu.libraryserver.service;

import zhijianhu.entity.BorrowRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author windows
* @description 针对表【borrow_records(借阅记录表)】的数据库操作Service
* @createDate 2025-02-26 13:54:47
*/
public interface BorrowRecordsService extends IService<BorrowRecords> {



     boolean deleteBorrowRecord(Integer id, Integer userId);

     boolean addBorrowRecord(Integer id, Integer userId);
}
