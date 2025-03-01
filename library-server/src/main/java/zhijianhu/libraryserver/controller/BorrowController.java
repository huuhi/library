package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zhijianhu.dto.BorrowPageDTO;
import zhijianhu.libraryserver.service.BorrowRecordsService;
import zhijianhu.result.Result;
import zhijianhu.vo.BorrowVO;
import zhijianhu.vo.PageVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/1
 * 说明:
 */
@RestController
@Slf4j
public class BorrowController {
//    借阅管理
    @Autowired
    private BorrowRecordsService borrowService;

//    分页获取所有借阅记录
    @GetMapping("/borrow")
//    @Cacheable(value = "borrowList", key = "'borrowList'")
    public Result<PageVO<BorrowVO>> borrow(BorrowPageDTO borrowPageDTO){
        log.info("查询借阅记录: {}", borrowPageDTO);
        PageVO<BorrowVO> borrowList = borrowService.getBorrowList(borrowPageDTO);
        return Result.success(borrowList);
    }

//    根据id查询借阅记录
    @GetMapping("/borrow/{id}")
    public Result<BorrowVO> getBorrowById(@PathVariable("id") Integer id){
        log.info("查询借阅记录: {}", id);
        BorrowVO borrowVO = borrowService.getBorrowById(id);
        return Result.success(borrowVO);
    }

//    查询未还书数量
    @GetMapping("/borrow/count/{id}")
    public Result<Integer> getBorrowCount(@PathVariable("id") Integer userId){
        log.info("查询未还书数量{}",userId);
        Integer count = borrowService.getBorrowCount(userId);
        return Result.success(count);
    }
//    查询用户借阅数量
    @GetMapping("/borrow/user/count/{id}")
    public Result<Integer> getUserBorrowCount(@PathVariable("id") Integer userId){
        log.info("查询用户借阅数量{}",userId);
        Integer count = borrowService.getUserBorrowCount(userId);
        return Result.success(count);
    }










}
