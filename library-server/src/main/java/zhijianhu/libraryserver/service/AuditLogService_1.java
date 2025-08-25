package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.AuditLogDTO;
import zhijianhu.dto.LogDTO;
import zhijianhu.entity.AuditLog;
import zhijianhu.vo.AuditLogPageVO;
import zhijianhu.vo.PageVO;

/**
* @author windows
* @description 针对表【audit_log(操作日志表)】的数据库操作Service
* @createDate 2025-03-07 09:12:52
*/
public interface AuditLogService extends IService<AuditLog> {

    PageVO<AuditLogPageVO> getLogByPage(LogDTO logDTO);

    boolean updateNote(AuditLogDTO auditLogDTO);

    AuditLogPageVO getLogById(Integer id);
}
