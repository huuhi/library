package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhijianhu.dto.BookPageDTO;
import zhijianhu.libraryserver.service.BooksService;
import zhijianhu.result.Result;
import zhijianhu.vo.BookVO;
import zhijianhu.vo.PageVO;

import java.awt.print.Book;

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



}
