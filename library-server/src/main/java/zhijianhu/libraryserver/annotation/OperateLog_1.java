package zhijianhu.libraryserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/8
 * 说明:
 */
@Target(ElementType.METHOD)// 表示该注解只能用于方法上
@Retention(RetentionPolicy.RUNTIME)// 表示该注解在运行时生效
public @interface OperateLog {
}
