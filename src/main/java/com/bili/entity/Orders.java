package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Orders {
    private Integer id;
    private Integer uid;
    private Integer status;  // 0 未完成  1 已完成
    private String traceNo; // 订单编号
    private Double totalAmount;
    /*
    *   产品:
    *   1 10￥充电   2 30￥充电   3 20￥一月会员  4 200￥一年会员
    * */
    private String product;
    private Timestamp startTime;
    private Timestamp endTime;
    private String tradeNo;   // 交易编号
}
