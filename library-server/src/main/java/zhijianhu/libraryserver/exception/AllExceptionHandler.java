package zhijianhu.libraryserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/7
 * 说明:
 */
@Slf4j
@RestControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error("发生异常: {}", e.getMessage());
        return "发生异常，请看日志";
    }

}
