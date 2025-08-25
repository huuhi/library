package zhijianhu.libraryserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.AuditLogDTO;
import zhijianhu.dto.LogDTO;
import zhijianhu.libraryserver.annotation.OperateLog;
import zhijianhu.libraryserver.service.AuditLogService;
import zhijianhu.result.Result;
import zhijianhu.vo.AuditLogPageVO;
import zhijianhu.vo.PageVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/7
 * 说明: 这里是想让管理员查看日志？
 */
@RestController
@Slf4j
@RequestMapping("/log")
public class AuditLogController {
    @Autowired
    private AuditLogService logService;
    @GetMapping("/page")
    public Result<PageVO<AuditLogPageVO>> getLogByPage(@ModelAttribute LogDTO logDTO){
        log.info("获取操作日志~");
        PageVO<AuditLogPageVO> list=logService.getLogByPage(logDTO);
        return Result.success(list);
    }

//    批量删除
    @DeleteMapping("/delete/{id}")
    @OperateLog
    public Result<Void> deleteLog(@PathVariable("id")List<Integer> ids){
        log.warn("批量删除日志");
        if(ids.isEmpty()){
            return Result.error(MessageConstant.IS_NULL);
        }
        boolean b = logService.removeBatchByIds(ids);
        return b?Result.success():Result.error(MessageConstant.DELETE_FAIL);
    }
//    记录备注
    @PutMapping("/note")
    public Result<Void> updateNote(@RequestBody AuditLogDTO auditLogDTO){
        log.info("修改备注");
        boolean b = logService.updateNote(auditLogDTO);
        return b?Result.success():Result.error(MessageConstant.UPDATE_FAIL);
    }

    @GetMapping("/{id}")
    public Result<AuditLogPageVO> getLogById(@PathVariable("id") Integer id){
        log.info("获取日志");
        AuditLogPageVO logById = logService.getLogById(id);
        return Result.success(logById);
    }




}
