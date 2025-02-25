package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.entity.BookClasses;
import zhijianhu.libraryserver.mapper.BookClassesMapper;
import zhijianhu.libraryserver.service.BookClassesService;

/**
* @author windows
* @description 针对表【book_classes】的数据库操作Service实现
* @createDate 2025-02-25 16:04:12
*/
@Service
public class BookClassesServiceImpl extends ServiceImpl<BookClassesMapper, BookClasses>
    implements BookClassesService {

}




