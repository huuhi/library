package zhijianhu.libraryserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import zhijianhu.entity.BorrowRecords;

import java.time.LocalDate;
import java.util.List;


/**
* @author windows
* @description 针对表【borrow_records(借阅记录表)】的数据库操作Mapper
* @createDate 2025-03-01 13:45:52
* @Entity zhijianhu/libraryserver.BorrowRecords
*/
public interface BorrowRecordsMapper extends BaseMapper<BorrowRecords> {

    @Select("select * from borrow_records where status=1 and must_return_time<now();")
    List<BorrowRecords> check();
}




