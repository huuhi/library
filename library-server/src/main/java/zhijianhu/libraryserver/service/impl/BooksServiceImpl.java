package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.entity.Books;
import zhijianhu.libraryserver.mapper.BooksMapper;
import zhijianhu.libraryserver.service.BooksService;

/**
* @author windows
* @description 针对表【books(图书信息表)】的数据库操作Service实现
* @createDate 2025-02-24 14:31:33
*/
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books>
    implements BooksService {

}




