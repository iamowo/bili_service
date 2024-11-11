package com.bili.mapper;

import com.bili.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void addOrders(Orders orders);
    Orders getByOrderId(Integer id);

    void dealDone(Orders orders);
}
