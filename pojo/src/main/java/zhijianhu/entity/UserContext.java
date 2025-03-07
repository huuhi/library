package zhijianhu.entity;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/7
 * 说明:存储当前登录的用户id
 */
public class UserContext {
    // 存储当前登录的用户id,使用ThreadLocal保证线程安全
    private static final ThreadLocal<Integer> userIdHolder=new ThreadLocal<>();
//    赋值用户id
    public static void setUserId(Integer userId){
        userIdHolder.set(userId);
    }
//获取用户id
    public static Integer getUserId(){
       return  userIdHolder.get();
    }
//    清除用户id
    public static void clear(){
        userIdHolder.remove();
    }

}
