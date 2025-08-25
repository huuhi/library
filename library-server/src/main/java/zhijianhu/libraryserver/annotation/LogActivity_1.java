package zhijianhu.libraryserver.annotation;

import zhijianhu.enumPojo.ActivityType;

import java.lang.annotation.*;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/8
 * 说明:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogActivity {
/**
     * 活动类型
     */
    ActivityType type();

    /**
     * 活动描述模板，支持SpEL表达式
     * 例如："用户{#userId}借阅了图书{#bookId}"
     */
    String description() default "";
    String condition() default "";
    /**
     * 是否为批量操作
     */
    boolean batch() default false;

    /**
     * 批量操作的集合表达式
     * 例如："#batchRequest.ids"
     */
    String batchItems() default "";

    /**
     * 批量操作的描述模板
     * 例如："管理员批量删除了评论{#item}"
     */
    String batchDescription() default "";

}
