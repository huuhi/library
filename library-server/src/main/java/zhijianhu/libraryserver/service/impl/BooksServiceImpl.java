package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.BookPageDTO;
import zhijianhu.entity.Books;
import zhijianhu.entity.StorageAddress;
import zhijianhu.libraryserver.mapper.BooksMapper;
import zhijianhu.libraryserver.service.BookClassesService;
import zhijianhu.libraryserver.service.BooksService;
import zhijianhu.libraryserver.service.StorageAddressService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.BookVO;
import zhijianhu.vo.PageVO;

import java.time.LocalDateTime;
import java.util.Objects;

/**
* @author windows
* @description 针对表【books(图书信息表)】的数据库操作Service实现
* @createDate 2025-02-24 14:31:33
*/
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books>
    implements BooksService {

    @Autowired
    private BookClassesService bookClassesService;
    @Autowired
    private StorageAddressService storageAddressService;

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
        return PageVO.of(booksPage, book -> {
            Integer addressId = book.getAddressId();
            Integer clazzId = book.getClazzId();
            Integer status1 = book.getStatus();
            String address = storageAddressService.getById(addressId).getAddress();
            String fullPath = bookClassesService.getFullPath(clazzId);
            BookVO bookVO = BeanUtil.copyProperties(book, BookVO.class);
            bookVO.setAddress(address);
            bookVO.setClazz(fullPath);
            if(Objects.equals(status1, StatusConstant.ENABLE)){
                bookVO.setStatus("可借");
            }else{
                bookVO.setStatus("已借出");
            }
            return bookVO;
        });
    }

    @Override
    public boolean addBook(BookVO book) {
        Books books = BeanUtil.copyProperties(book, Books.class);
        books.setCreateTime(LocalDateTime.now());
        return save(books);
    }

    @Override
    public BookVO getBookById(Integer id) {
        Books book = getById(id);
        Integer addressId = book.getAddressId();
        Integer clazzId = book.getClazzId();
        Integer status1 = book.getStatus();
        String address = storageAddressService.getById(addressId).getAddress();
        String fullPath = bookClassesService.getFullPath(clazzId);
        BookVO bookVO = BeanUtil.copyProperties(book, BookVO.class);
        bookVO.setAddress(address);
        bookVO.setClazz(fullPath);
        if(Objects.equals(status1, StatusConstant.ENABLE)){
            bookVO.setStatus("可借");
        }else{
            bookVO.setStatus("已借出");
        }
        return bookVO;
    }

    @Override
    public boolean changeBook(BookVO book) {
        Books books = BeanUtil.copyProperties(book, Books.class);
        books.setUpdateTime(LocalDateTime.now());
        return updateById(books);
    }

    @Override
    public boolean changeBookStatus(Integer id, Integer status) {
        Books book = getById(id);
        book.setStatus(status);
        book.setUpdateTime(LocalDateTime.now());
        return updateById(book);
    }
}




