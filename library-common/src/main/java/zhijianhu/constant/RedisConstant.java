package zhijianhu.constant;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/21
 * 说明:
 */
public class RedisConstant {
    public static final long LOCK_TTL=5L;
    public static final long CACHE_NULL_TTL=1L;
    public static final String LOCK_POST_KEY="lock:post:";


    public static final String CACHE_POST_KEY="cache:post:";
    public static final long CACHE_POST_TTL=60L;
}
