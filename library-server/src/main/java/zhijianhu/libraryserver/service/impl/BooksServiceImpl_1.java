package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zhijianhu.constant.MessageConstant;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.BookDTO;
import zhijianhu.dto.BookPageDTO;
import zhijianhu.dto.ChangeBookStatusDTO;
import zhijianhu.entity.Books;
import zhijianhu.entity.Publish;
import zhijianhu.entity.StorageAddress;
import zhijianhu.exception.LendFileException;
import zhijianhu.libraryserver.mapper.BooksMapper;
import zhijianhu.libraryserver.service.*;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.BookVO;
import zhijianhu.vo.PageVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
* @author windows
* @description 针对表【books(图书信息表)】的数据库操作Service实现
* @createDate 2025-02-24 14:31:33
*/
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books>
    implements BooksService {

    private final BookClassesService bookClassesService;
    private final StorageAddressService storageAddressService;
    private final BorrowRecordsService borrowRecordsService;
    private final PublishService publishService;

    @Lazy
    public BooksServiceImpl(BookClassesService bookClassesService, StorageAddressService storageAddressService, BorrowRecordsService borrowRecordsService, PublishService publishService) {
        this.bookClassesService = bookClassesService;
        this.storageAddressService = storageAddressService;
        this.borrowRecordsService = borrowRecordsService;
        this.publishService = publishService;
    }

    @Override
    public PageVO<BookVO> getBooksByPage(BookPageDTO bookDTO) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPage(bookDTO.getPage());
        pageQuery.setPageSize(bookDTO.getPageSize());
        String name = bookDTO.getName();
        Integer status = bookDTO.getStatus();
        Integer categoryId = bookDTO.getCategoryId();
//        获取categoryId的所有子分类
        List<Integer> categoryIds = null;
        if (categoryId != null) {
            categoryIds = bookClassesService.getAllSubCategoryIds(categoryId);
            if (!categoryIds.contains(categoryId)) {
                categoryIds.add(categoryId);
            }
        }
        Page<Books> page = pageQuery.toMpPage(OrderItem.desc("publish_date"));
//        获取到了父类父类的id，我们需要查找父类类id以及子类id
       Page<Books> booksPage = lambdaQuery()
                .eq(status != null, Books::getStatus, status)
                .and(name != null, wrapper -> wrapper  // [!code focus]
                        .like(Books::getName, name)        // [!code focus]
                        .or()                              // [!code focus]
                        .like(Books::getAuthor, name)      // [!code focus]
                )
               .in(categoryIds!=null, Books::getClazzId,categoryIds)
                .page(page);
        return PageVO.of(booksPage, this::getBookVO);
    }

    @NotNull
    private BookVO getBookVO(Books book) {
        Integer addressId = book.getAddressId();
        Integer clazzId = book.getClazzId();
        Integer status1 = book.getStatus();
        Integer publishId = book.getPublishId();
        StorageAddress storageAddress = storageAddressService.getById(addressId);
        String address ="未知地址";
        if(storageAddress!=null){
            address=storageAddress.getAddress();
        }
        String fullPath = bookClassesService.getFullPath(clazzId);
        Publish publish = publishService.getById(publishId);
        String publishName = "未知出版社";
        if(publish!=null){
            publishName=publish.getName();
        }
        BookVO bookVO = BeanUtil.copyProperties(book, BookVO.class);
        bookVO.setAddress(address);
        bookVO.setClazz(fullPath);
        bookVO.setPublish(publishName);
        if(Objects.equals(status1, StatusConstant.ENABLE)){
            bookVO.setStatus("可借");
        }else{
            bookVO.setStatus("已借出");
        }
        return bookVO;
    }

    @Override
    public boolean addBook(BookDTO book) {
        Books books = BeanUtil.copyProperties(book, Books.class);
        books.setCreateTime(LocalDateTime.now());
        return save(books);
    }

    @Override
    public BookVO getBookById(Integer id) {
        Books book = getById(id);
        return getBookVO(book);
    }

    @Override
    public boolean changeBook(BookVO book) {
        Books books = BeanUtil.copyProperties(book, Books.class);
        books.setUpdateTime(LocalDateTime.now());
        return updateById(books);
    }

    @Override
    public boolean changeBookStatus(ChangeBookStatusDTO dto) {
//        需要判断是借书还是还书
//        借书：status=0，还书：status=1
        Integer id = dto.getBookId();
        Integer status = dto.getStatus();
        Integer userId = dto.getUserId();
        boolean isSuccess;
        if(Objects.equals(status, StatusConstant.DISABLE)){
            isSuccess =  borrowRecordsService.addBorrowRecord(id, userId);
        }else{
            isSuccess= borrowRecordsService.deleteBorrowRecord(id, userId);
        }
        if(!isSuccess){
            throw new LendFileException(MessageConstant.LEND_BOOK_FAIL);
        }
        Books book = getById(id);
        book.setStatus(status);
        book.setUpdateTime(LocalDateTime.now());
        return updateById(book);
    }
}




