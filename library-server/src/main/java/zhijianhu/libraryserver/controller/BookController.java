package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.BookDTO;
import zhijianhu.dto.BookPageDTO;
import zhijianhu.dto.ChangeBookStatusDTO;
import zhijianhu.enumPojo.ActivityType;
import zhijianhu.libraryserver.annotation.LogActivity;
import zhijianhu.libraryserver.annotation.OperateLog;
import zhijianhu.libraryserver.service.BooksService;
import zhijianhu.result.Result;
import zhijianhu.vo.BookVO;
import zhijianhu.vo.PageVO;

import java.awt.print.Book;
import java.util.List;
import java.util.Objects;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/24
 * 说明:
 */
@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {
    @Autowired
    private BooksService bookService;

//    分页查询图书
    @GetMapping("/page")
    public Result<PageVO<BookVO>> getBooksByPage(BookPageDTO page) {
        log.info("getBooksByPage: page={}", page);
         PageVO<BookVO>  books=bookService.getBooksByPage(page);
         return Result.success(books);
    }

    @PostMapping("/add")
    @LogActivity(
        type = ActivityType.SYSTEM,
        description = "系统添加了图书《{#book.name}》"
    )
    public Result<Void> addBook(@RequestBody BookDTO book) {
        log.info("addBook: book={}", book);
        boolean success = bookService.addBook(book);
        return success ? Result.success() : Result.error("添加图书失败");
    }

    @GetMapping("/{id}")
    public Result<BookVO> getBookById(@PathVariable Integer id) {
        log.info("getBookById: id={}", id);
        BookVO book = bookService.getBookById(id);
        return book != null ? Result.success(book) : Result.error("图书不存在");
    }

    @PutMapping("/change")
    public Result<Void> changeBook(@RequestBody BookVO book) {
       boolean success =  bookService.changeBook(book);
       return success ? Result.success() : Result.error("修改图书失败");
    }
//    借书 以及 还书
    @PutMapping("/status")
    @LogActivity(
            type = ActivityType.BORROW,
            description = "用户{#dto.userId}{#dto.status == 0 ? '借了' : '归还了'}图书{#dto.bookId}",
            condition = "#result.code == 1"
    )
    public Result<Void> changeBookStatus(@RequestBody ChangeBookStatusDTO dto) {
        if(Objects.equals(dto.getStatus(), StatusConstant.DISABLE)){
            log.info("借书：{}",dto);
        }else{
            log.info("还书：{}",dto);
        }
        boolean success = bookService.changeBookStatus(dto);
        return success ? Result.success() : Result.error("借书已达上限");
    }
    @DeleteMapping("/{id}")
    @LogActivity(
        type = ActivityType.SYSTEM,
        description = "管理员删除了图书id{#ids}"
    )
    @OperateLog
    public Result<Void> deleteBook(@PathVariable("id") List<Integer> ids) {
        log.info("deleteBook: id={}", ids);
        boolean b = bookService.removeBatchByIds(ids);
        return b ? Result.success() : Result.error("删除图书失败");

    }



}
