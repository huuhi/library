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
           // 处理OPTIONS预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, token");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_OK); // 直接返回200
            return false; // 不继续处理
        }

        // 其他请求处理逻辑
        String jwt = request.getHeader("token");
        if (jwt == null || jwt.isEmpty()) {
            Result<Void> res = Result.error("NOT_LOGIN");
            String json = JSONObject.toJSONString(res);
            response.getWriter().write(json);
            return false;
        }

        try {
            Claims claims = JwtUtil.parseJWT(jwt);
            Integer id = (Integer) claims.get(JwtClaimsConstant.USER_ID);
            log.info("登录成功，用户id为：" + id);
        } catch (Exception e) {
            Result<Void> res = Result.error("NOT_LOGIN");
            String json = JSONObject.toJSONString(res);
            response.getWriter().write(json);
            return false;
        }

        // 设置CORS头
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        return true;
    }

    //在请求处理之后调用，但是在视图被渲染之前（Controller方法调用之后），
    // 也就是说在这个方法中对ModelAndView对象进行操作，可以对ModelAndView对象进行设置。
}
