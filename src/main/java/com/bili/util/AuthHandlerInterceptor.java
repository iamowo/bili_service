package com.bili.util;

import com.bili.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;
//    @Value("${token.refreshTime}")
    private Long refreshTime = (3600 * 24 * 1L);   // 刷新时间
//    @Value("${token.expiresTime}")
    private Long expiresTime = (3600 * 24 * 3L);  // 过期时间
    /**
     * 权限认证的拦截操作.
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        log.info("=======进入拦截器========");
        // 如果不是映射到方法直接通过,可以访问资源.
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        //为空就返回错误
        String token = httpServletRequest.getHeader("token");
        log.info("==============token:" + token);
        if (null == token || "".equals(token.trim())) {
            return false;
        }
        Map<String, String> map = tokenUtil.parseToken(token);
        String userId = map.get("userId");
        String userRole = map.get("userRole");
        long timeOfUse = (System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"))) / 1000;   //   得到 秒
        long timeOfUse2 = (System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"))) / 1000 / 60;   //    得到 分钟
        log.info(timeOfUse + " =0= " + timeOfUse2);
        //1.判断 token 是否过期
        if (timeOfUse < refreshTime) {
            log.info("token验证成功");
            return true;
        }
        //超过token刷新时间，刷新 token
        else if (timeOfUse >= refreshTime && timeOfUse < expiresTime) {
            httpServletResponse.setHeader("token",tokenUtil.getToken(userId,userRole));
            log.info("token刷新成功");
            return true;
        }
        //token过期就返回 token 无效.
        else {
            log.info("token无效");
//            throw new TokenAuthExpiredException();
            // 失效时的状态码
            httpServletResponse.setStatus(401);
            return false;
//            throw new RuntimeException("token过期或不正确，请重新登录");
        }
    }
}