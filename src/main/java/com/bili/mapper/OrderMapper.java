package com.bili.mapper;

import com.bili.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void updateById(Orders order);

    void addOrders(Orders orders);
    Orders getByOrderId(Integer id);

    void dealDone(Integer id);
}
