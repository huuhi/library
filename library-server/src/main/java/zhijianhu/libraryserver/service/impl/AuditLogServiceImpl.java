package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zhijianhu.dto.AuditLogDTO;
import zhijianhu.dto.LogDTO;
import zhijianhu.entity.AuditLog;

import java.util.*;
import java.util.stream.Collectors;
import zhijianhu.entity.Users;
import zhijianhu.libraryserver.mapper.AuditLogMapper;
import zhijianhu.libraryserver.service.AuditLogService;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.AuditLogPageVO;
import zhijianhu.vo.PageVO;


/**
* @author windows
* @description 针对表【audit_log(操作日志表)】的数据库操作Service实现
* @createDate 2025-03-07 09:12:51
*/
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog>
    implements AuditLogService {
    @Autowired
    @Lazy
    private UsersService userService;

    @Override
    public PageVO<AuditLogPageVO> getLogByPage(LogDTO logDTO) {
        PageQuery query = new PageQuery();
        query.setPage(logDTO.getPageNum());
        query.setPageSize(logDTO.getPageSize());
        Page<AuditLog> mpPage = query.toMpPage(OrderItem.asc("operation_time"));
        Integer userId = logDTO.getUserId();
        Page<AuditLog> page = lambdaQuery()
                .eq(userId != null, AuditLog::getUserId, userId)
                .page(mpPage);
        Map<Integer, String> userMap = getUserMap(page.getRecords());
        return PageVO.of(page,auditLog->{
            Integer userId1 = auditLog.getUserId();
            AuditLogPageVO auditLogPageVO = BeanUtil.copyProperties(auditLog, AuditLogPageVO.class);
            auditLogPageVO.setUserName(userMap.get(userId1));
            return auditLogPageVO;
        });
    }

    @Override
    public boolean updateNote(AuditLogDTO auditLogDTO) {
        Integer id = auditLogDTO.getId();
        AuditLog auditLog = getById(id);
        auditLog.setNote(auditLogDTO.getNote());
        return updateById(auditLog);
    }

    @Override
    public AuditLogPageVO getLogById(Integer id) {
        AuditLog auditLog = getById(id);
        AuditLogPageVO auditLogPageVO = BeanUtil.copyProperties(auditLog, AuditLogPageVO.class);
        Integer userId = auditLogPageVO.getUserId();
        if (userId != null) {
            Users user = userService.getById(userId);
            auditLogPageVO.setUserName(user.getUsername());
        }
        return auditLogPageVO;
    }

    private Map<Integer, String> getUserMap(List<AuditLog> list) {
        Set<Integer> collect = list.stream()
        .map(AuditLog::getUserId)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
//            获取所有的用户名，以map形式返回
        if (collect.isEmpty()) {
            return Collections.emptyMap();
        }
        return userService.listByIds(collect)
        .stream()
        .collect(Collectors.toMap(Users::getId, Users::getUsername));
    }
}




