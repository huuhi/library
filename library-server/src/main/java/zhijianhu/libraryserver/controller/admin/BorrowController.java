package zhijianhu.libraryserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.BorrowDTO;
import zhijianhu.dto.BorrowPageDTO;
import zhijianhu.libraryserver.annotation.OperateLog;
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
@RequestMapping("/borrow")
public class BorrowController {
//    借阅管理
    @Autowired
    private BorrowRecordsService borrowService;

//    分页获取所有借阅记录
    @GetMapping
//    @Cacheable(value = "borrowList", key = "'borrowList'")
    public Result<PageVO<BorrowVO>> borrow(@ModelAttribute BorrowPageDTO borrowPageDTO){
        log.info("查询所有借阅记录: {}", borrowPageDTO);
        PageVO<BorrowVO> borrowList = borrowService.getBorrowList(borrowPageDTO);
        return Result.success(borrowList);
    }

//    根据id查询借阅记录
    @GetMapping("/{id}")
    public Result<BorrowVO> getBorrowById(@PathVariable("id") Integer id){
        log.info("查询借阅记录: {}", id);
        BorrowVO borrowVO = borrowService.getBorrowById(id);
        return Result.success(borrowVO);
    }

//    查询未还书数量
    @GetMapping("/count/{id}")
    public Result<Integer> getBorrowCount(@PathVariable("id") Integer userId){
        log.info("查询未还书数量{}",userId);
        Integer count = borrowService.getBorrowCount(userId);
        return Result.success(count);
    }
//    查询用户借阅数量
    @GetMapping("/user/count/{id}")
    public Result<Integer> getUserBorrowCount(@PathVariable("id") Integer userId){
        log.info("查询用户借阅数量{}",userId);
        Integer count = borrowService.getUserBorrowCount(userId);
        return Result.success(count);
    }
//    修改借阅记录
    @PostMapping("/update")
    public Result<Void> updateStatus(@RequestBody BorrowDTO borrowDTO){
        log.info("修改借阅记录：{}",borrowDTO);
        Boolean success = borrowService.updateStatus(borrowDTO);
        return success ? Result.success() : Result.error("修改状态失败");
    }
    //续借
    @PostMapping("/renew/{id}")
    public Result<Void> renew(@PathVariable("id") Integer id){
//        这里需要判断用户是否续借过一次，如果有，则不能续借
        log.info("续借id{}",id);
        Boolean success= borrowService.addReturnDate(id);
        return success ? Result.success() : Result.error(MessageConstant.NOT_ALLOW_LEND);
    }
//    删除
    @DeleteMapping("/{id}")
    @OperateLog
    public Result<Void> deleteBorrowRecord(@PathVariable("id") List<Integer> ids){
        log.info("删除借阅记录: {}",ids);
        boolean b = borrowService.removeBatchByIds(ids);
        return b ? Result.success() : Result.error("删除借阅记录失败");
    }

}
