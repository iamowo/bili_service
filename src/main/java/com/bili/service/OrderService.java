package com.bili.service;
import com.bili.entity.Orders;
import com.bili.mapper.OrderMapper;
import jakarta.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private RedisService redisService;
    public Orders getByOrderId(Integer id) {
        return orderMapper.getByOrderId(id);
    }

    public Integer addOrders(Orders orders) {
        orderMapper.addOrders(orders);
        Integer id = orders.getId();
        // 在Redis中存储一个键，设置30分钟过期
//        redisTemplate.opsForValue().set("order:" + id, id, 2, TimeUnit.MINUTES);
        redisService.setValue("orderId" + id, id, 10);
        return id;
    }

    // 当键过期时，Redis会自动调用该方法（需要配置Redis的过期事件通知功能）
    public void onOrderKeyExpired(Integer id) {
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus(3);
        orderMapper.dealDone(orders);
        redisService.deleteKey("orderId" + id);
    }
    public void updateOrder(Integer id, String subject, String tradeNo) {
        // 当前时间戳
        long timestamp = System.currentTimeMillis();
        Date now = new Date();
        Calendar calender = Calendar.getInstance();
        calender.setTime(now);
        if (Objects.equals(subject, "30￥充电") || Objects.equals(subject, "10￥充电") || Objects.equals(subject, "15￥一个月会员")) {
            // 不支持毫秒
            calender.add(Calendar.MONTH, 1);
        } else if (Objects.equals(subject, "80￥半年会员")) {
            calender.add(Calendar.MONTH, 6);
        } else {
            calender.add(Calendar.YEAR, 1);
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String paytime = dateFormat.format(timestamp);
        String endtime = dateFormat.format(calender.getTime());
        Orders orders = new Orders();
        orders.setId(id);
        // pay success
        orders.setStatus(1);
        orders.setPaytime(paytime);
        orders.setEndtime(endtime);
        orders.setTraceNo(tradeNo);
        orderMapper.dealDone(orders);
        redisService.deleteKey("orderId" + id);
    }

    public Integer queryOrderStatus(Integer id) {
        Orders orders = orderMapper.getByOrderId(id);
        Integer res = orders.getStatus();
        return res;
    }
}
