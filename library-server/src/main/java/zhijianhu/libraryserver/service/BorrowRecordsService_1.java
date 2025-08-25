package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.BorrowDTO;
import zhijianhu.dto.BorrowPageDTO;
import zhijianhu.entity.BorrowRecords;
import zhijianhu.vo.BorrowVO;
import zhijianhu.vo.PageVO;

import java.time.LocalDate;

/**
* @author windows
* @description 针对表【borrow_records(借阅记录表)】的数据库操作Service
* @createDate 2025-03-01 13:45:52
*/
public interface BorrowRecordsService extends IService<BorrowRecords> {
     boolean deleteBorrowRecord(Integer id, Integer userId);

     boolean addBorrowRecord(Integer id, Integer userId);

    PageVO<BorrowVO> getBorrowList(BorrowPageDTO borrowPageDTO);

    BorrowVO getBorrowById(Integer id);

    Integer getBorrowCount(Integer userId);


    Integer getUserBorrowCount(Integer userId);


    Boolean updateStatus(BorrowDTO borrowDTO);

    Boolean addReturnDate(Integer id);

    Integer getLendCountByMonth(LocalDate first, LocalDate last);

    Integer getReturnCountByMonth(LocalDate first, LocalDate last);

    Boolean checkBorrowReturnTime();
}
