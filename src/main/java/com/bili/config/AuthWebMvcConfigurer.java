package com.bili.config;

import com.bili.util.AuthHandlerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class AuthWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    AuthHandlerInterceptor authHandlerInterceptor;

    @Value("${url2}")
    private String url2;
    /**
     * 给除了 /login 的接口都配置拦截器,拦截转向到 authHandlerInterceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns为拦截此请求路径的请求
        // excludePathPatterns为不拦截此路径的请求
        registry.addInterceptor(authHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login", "/user/register",  "/user/findAccount/**", "/user/getByUidFollowed/**",
                        "/user/getByUidWithToken/**",
                        "/video/getAllVideo","/video/getByVid/**", "/video/getRandom",
                        "/video/getDm/**", "/video/getVideoLikely/**", "/video/searchKw/**",
                        "/video/getByMaintag/**", "/video/getAllMainTag",
                        "/comment/getAllComment", "/comment/getAllComment/**",
                        "/video/**", "/sys/**", "/avatar/**"
                        );
        //    /video/** 时资源地址， 应更改
    }
}
