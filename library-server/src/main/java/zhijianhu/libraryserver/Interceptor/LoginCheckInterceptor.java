package zhijianhu.libraryserver.Interceptor;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zhijianhu.constant.JwtClaimsConstant;
import zhijianhu.result.Result;
import zhijianhu.utils.JwtUtil;


/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/17
 * 说明:拦截器
 */
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {


    @Override
    //在请求处理之前调用，返回 true 表示继续处理，返回 false 表示中断处理。
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //需要登录，检查是否登录
        String jwt = request.getHeader("token");
        if(jwt == null || jwt.isEmpty()){
            //未登录，重定向到登录页面
            log.info("未登录，重定向到登录页面");
            Result res = Result.error("NOT_LOGIN");
            //转换json
            String json = JSONObject.toJSONString(res); //将对象转换为json字符串
            response.getWriter().write(json);//响应json数据给前端
            return false;
        }
        //如果存在就解析
        try {
            Claims claims = JwtUtil.parseJWT(jwt);
             Integer id = (Integer)claims.get(JwtClaimsConstant.USER_ID);
            log.info("登录成功，用户id为："+id);
        } catch (Exception e) {
            log.info("解析jwt失败");
            //解析失败，重定向到登录页面
            Result res = Result.error("NOT_LOGIN");
            //转换json
            String json = JSONObject.toJSONString(res); //将对象转换为json字符串
            response.getWriter().write(json);//响应json数据给前端
            return false;
        }
        //登录成功，放行
        log.info("登录成功，放行");
        return true;
    }

    //在请求处理之后调用，但是在视图被渲染之前（Controller方法调用之后），
    // 也就是说在这个方法中对ModelAndView对象进行操作，可以对ModelAndView对象进行设置。
}
