package zhijianhu.libraryserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zhijianhu.libraryserver.Interceptor.LoginCheckInterceptor;


import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/17
 * 说明:配置拦截器
 */
@Configuration//表示这是一个配置类
public class WebInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器,排除登录页面
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login");
    }
}
