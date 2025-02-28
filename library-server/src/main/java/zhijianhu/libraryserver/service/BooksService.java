package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.BookPageDTO;
import zhijianhu.dto.ChangeBookStatusDTO;
import zhijianhu.entity.Books;
import zhijianhu.vo.BookVO;
import zhijianhu.vo.PageVO;

/**
* @author windows
* @description 针对表【books(图书信息表)】的数据库操作Service
* @createDate 2025-02-24 14:31:33
*/
public interface BooksService extends IService<Books> {

    PageVO<BookVO> getBooksByPage(BookPageDTO page);

    boolean addBook(BookVO book);

    BookVO getBookById(Integer id);

    boolean changeBook(BookVO book);

    boolean changeBookStatus(ChangeBookStatusDTO dto);

}
