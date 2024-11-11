package com.bili.redais;

import com.bili.service.FibMachineCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {
    @Autowired
    private FibMachineCouponService fibMachineCouponService;


    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = message.toString();
        log.info("过期的key：" + expireKey);
        //在这里做过期key的处理
    }
}
