package zhijianhu.libraryserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import zhijianhu.constant.StatusConstant;
import zhijianhu.entity.Books;
import zhijianhu.entity.BorrowRecords;
import zhijianhu.libraryserver.mapper.BooksMapper;
import zhijianhu.libraryserver.service.BorrowRecordsService;
import zhijianhu.libraryserver.mapper.BorrowRecordsMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author windows
* @description 针对表【borrow_records(借阅记录表)】的数据库操作Service实现
* @createDate 2025-02-26 13:54:47
*/
@Service
public class BorrowRecordsServiceImpl extends ServiceImpl<BorrowRecordsMapper, BorrowRecords>
    implements BorrowRecordsService{


    @Autowired
    private BooksMapper booksService;
    @Override
    public boolean deleteBorrowRecord(Integer bookId, Integer userId) {
        //修改借阅信息
        BorrowRecords one = lambdaQuery()
                .eq(BorrowRecords::getUserId, userId)
                .eq(BorrowRecords::getBookId, bookId).one();
        one.setStatus(StatusConstant.DISABLE);
        one.setReturnTime(LocalDateTime.now());
        return updateById(one);
    }

    @Override
    public boolean addBorrowRecord(Integer id, Integer userId) {
        //添加借阅信息
        Books book = booksService.selectById(id);
        String image = book.getImage();
        LocalDateTime mustReturnTime = LocalDateTime.now().plusDays(30);//借书期限30天
        BorrowRecords record = BorrowRecords
                .builder()
                .bookId(id)
                .userId(userId)
                .image(image)
                .mustReturnTime(mustReturnTime)
                .build();
        return save(record);


    }
}




