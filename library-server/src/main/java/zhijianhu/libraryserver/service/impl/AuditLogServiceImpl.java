package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.entity.AuditLog;
import zhijianhu.libraryserver.mapper.AuditLogMapper;
import zhijianhu.libraryserver.service.AuditLogService;

/**
* @author windows
* @description 针对表【audit_log(操作日志表)】的数据库操作Service实现
* @createDate 2025-03-07 09:12:51
*/
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog>
    implements AuditLogService {

}




