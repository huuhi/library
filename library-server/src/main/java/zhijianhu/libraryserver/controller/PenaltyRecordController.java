package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.PenaltyDTO;
import zhijianhu.dto.PenaltyRecordPageDTO;
import zhijianhu.libraryserver.annotation.OperateLog;
import zhijianhu.libraryserver.service.PenaltyRecordsService;
import zhijianhu.result.Result;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.PenaltyRecordVO;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/3
 * 说明:罚单
 */
@RestController
@RequestMapping("/penalty")
@Slf4j
public class PenaltyRecordController {
    @Autowired
    private PenaltyRecordsService penaltyRecordService;

//    分页查询
    @GetMapping("/page")
    public Result<PageVO<PenaltyRecordVO>> list(@ModelAttribute PenaltyRecordPageDTO pageDTO){
        log.info("分页查询罚单记录:{}",pageDTO);
        PageVO<PenaltyRecordVO>  pageVO =penaltyRecordService.getByPage(pageDTO);
        return Result.success(pageVO);
    }

//    根据id查询
    @GetMapping("/get/{id}")
    public Result<PenaltyRecordVO> getRecordById(@PathVariable Integer id){
        log.info("根据id查询罚单记录{}",id);
        PenaltyRecordVO record= penaltyRecordService.getRecordById(id);
        return Result.success(record);
    }
//    删除！
    @DeleteMapping("/{id}")
    @OperateLog
    public Result<Void> deleteById(@PathVariable Integer id){
        log.info("根据id删除 :{}",id);
        boolean success = penaltyRecordService.removeById(id);
        return success ? Result.success():Result.error(MessageConstant.DELETE_FAIL);
    }
//    修改
    @PostMapping("/update")
    public Result<Void> updateRecord(@RequestBody PenaltyDTO dto){
        log.info("修改：{}",dto);
        boolean success=penaltyRecordService.updateRecordById(dto);
        return success?Result.success():Result.error(MessageConstant.UPDATE_FAIL);
    }



}
