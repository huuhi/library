package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.dto.BookPageDTO;
import zhijianhu.entity.Books;
import zhijianhu.libraryserver.mapper.BooksMapper;
import zhijianhu.libraryserver.service.BooksService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.BookVO;
import zhijianhu.vo.PageVO;

/**
* @author windows
* @description 针对表【books(图书信息表)】的数据库操作Service实现
* @createDate 2025-02-24 14:31:33
*/
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books>
    implements BooksService {

    @Override
    public PageVO<BookVO> getBooksByPage(BookPageDTO bookDTO) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPage(bookDTO.getPage());
        pageQuery.setPageSize(bookDTO.getPageSize());
        String name = bookDTO.getName();
        String author = bookDTO.getAuthor();
        Integer status = bookDTO.getStatus();
        Page<Books> page = pageQuery.toMpPage(OrderItem.desc("publish_date"));
        Page<Books> booksPage = lambdaQuery().like(name != null, Books::getName, name)
                .like(author != null, Books::getAuthor, author)
                .eq(status != null, Books::getStatus, status)
                .page(page);
        return PageVO.of(booksPage, BookVO.class);
    }
}




