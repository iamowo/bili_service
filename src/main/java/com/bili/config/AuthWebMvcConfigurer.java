package com.bili.config;

import com.bili.util.AuthHandlerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class AuthWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    AuthHandlerInterceptor authHandlerInterceptor;
    /**
     * 给除了 /login 的接口都配置拦截器,拦截转向到 authHandlerInterceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns为拦截此请求路径的请求
        // excludePathPatterns为不拦截此路径的请求
        registry.addInterceptor(authHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/video/getAllVideo","/video/getByVid/**",
                        "/comment/getAllComment", "/video/getRandom", "/comment/getAllComment/**",
                        "/video/getDm/**", "/video/getVideoLikely/**", "/user/getByUidFollowed/**",
                        "/video/searchKw/**", "/video/getByMaintag/**", "/video/getAllMainTag");
    }
}
