package com.bili.config;

import com.bili.redais.KeyExpiredListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author Naruto
 * @date 2024/1/20 19:03
 * @description redis配置类
 */

@Configuration
public class RedisConfig {
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer() {
//        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
//        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
//        return redisMessageListenerContainer;
//    }
//
//    @Bean
//    public KeyExpiredListener keyExpiredListener() {
//        return new KeyExpiredListener(this.redisMessageListenerContainer());
//    }
}
