package com.bili.config;

import org.springframework.context.annotation.Configuration;
import com.bili.util.TokenUtil;
import org.springframework.web.servlet.HandlerInterceptor;

// springboot 3.0 失效
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
// new
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Configuration
public class TokenInterceptor implements HandlerInterceptor{
    //在请求处理方法被调用之前被调用
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token=request.getHeader("token");//必须把token放到请求头Header
//        if (!TokenUtil.checkToken(token)){//验证码失败
//            return false;
//        }
//        return true;
//    }
}
