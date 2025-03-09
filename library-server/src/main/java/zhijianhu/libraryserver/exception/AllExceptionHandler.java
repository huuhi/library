package zhijianhu.libraryserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zhijianhu.constant.MessageConstant;
import zhijianhu.result.Result;

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
    public Result<Void> handleException(Exception e) {
        log.error("发生异常: {}", e);
        return Result.error(MessageConstant.SERVER_QUESTION);
    }

}
