package com.bili.service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    // 在Service或Controller中注入RedisTemplate，然后可以使用它来操作Redis。
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, Object value, Integer time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }

    public void setList(String key, List<?> list) {
        String json = JSON.toJSONString(list); // 使用fastjson或jackson
        stringRedisTemplate.opsForValue().set(key, json);
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json != null) {
            return JSON.parseArray(json, clazz); // fastjson
            // 或使用jackson: objectMapper.readValue(json, new TypeReference<List<T>>(){});
        }
        return null;
    }
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
