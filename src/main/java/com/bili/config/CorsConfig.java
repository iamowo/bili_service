package com.bili.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Value("${url2}")
    private String url;
    // 跨越请求有效时长
    private static final long MAX_AGE = 24 * 60 * 60;
    @Bean
    public CorsFilter corsFilter (){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // corsConfiguration.addAllowedOrigin(url);  // 访问源地址
        corsConfiguration.addAllowedOrigin("*");     // 访问源地址
        corsConfiguration.addAllowedHeader("*");  // 访问源头
        corsConfiguration.addAllowedMethod("*");  // 访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", corsConfiguration);  // 对接口跨域设置
        return new CorsFilter(source);
    }
}



//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*") // 允许所有域名访问，或指定前端域名
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(24 * 60 * 60);
//    }
//}