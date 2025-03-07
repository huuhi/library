package zhijianhu.libraryserver.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import zhijianhu.entity.AuditLog;
import zhijianhu.entity.UserContext;
import zhijianhu.libraryserver.service.AuditLogService;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/7
 * 说明:记录操作日志的切面类
 */
//@Aspect
//@Component
@Slf4j
public class LogRecord {
//    @Autowired
    private AuditLogService auditLogService;

//    只记录server包下的操作日志
    @Around("execution(* zhijianhu.libraryserver.service.*.*(..))")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取方法签名
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();// 获取方法名
        String args = Arrays.toString(joinPoint.getArgs());// 获取方法参数
        String className = joinPoint.getTarget().getClass().getName();
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();// 执行方法
        long end = System.currentTimeMillis();
        long costTime = end - start;// 方法执行时间
        AuditLog auditLog = AuditLog.builder()
                .methodName(methodName)
                .operationTime(LocalDateTime.now())
                .costTime(costTime)
                .methodParams(args)
                .userId(getUserId())
                .className(className)
                .build();
        try {
            saveAuditLogAsync(auditLog);
        } catch (Exception e) {
            log.error("记录操作日志失败：{}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("记录操作日志：{}", auditLog);
        return proceed;
    }
    private Integer getUserId() {
        Integer userId = UserContext.getUserId();
        if (userId == null) {
            log.info("未获取到用户信息，当前线程：{}",Thread.currentThread().getName());
            return null;
        } else {
            return userId;
        }
    }
    @Async
   public void saveAuditLogAsync(AuditLog auditLog) {
       auditLogService.save(auditLog);
   }

}
