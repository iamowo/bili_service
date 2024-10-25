package com.bili.service;
import com.bili.entity.Orders;
import com.bili.mapper.OrderMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    public Orders getByOrderId(Integer id) {
        return orderMapper.getByOrderId(id);
    }

    public Integer addOrders(Orders orders) {
        orderMapper.addOrders(orders);
        Integer id = orders.getId();
        return id;
    }

    public void updateOrder(Integer id) {
        orderMapper.dealDone(id);
    }

    public Integer queryOrderStatus(Integer id) {
        Orders orders = orderMapper.getByOrderId(id);
        Integer res = orders.getStatus();
        return res;
    }
}
