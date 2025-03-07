package com.bili.controler;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.bili.config.AlipayConfiguration;
import com.bili.entity.Orders;
import com.bili.service.OrderService;
import com.bili.service.RedisService;
import com.bili.util.Response;
import com.bili.websocket.WebSocktService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AliPayController {
    @Resource
    AlipayConfiguration alipayConfiguration;

    @Resource
    private OrderService orderService;
    private static final String GATEWAY_URL ="https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT ="JSON";
    private static final String CHARSET ="utf-8";
    private static final String SIGN_TYPE ="RSA2";

    // 创建订单
    @PostMapping("/addOrders")
    public Response addOrders(@RequestBody Orders orders) {
        try {
            Integer res = orderService.addOrders(orders);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error"+ e);
        }
    }

    // 查询订单状态
    @GetMapping("/queryOrderStatus")
    public Response queryOrderStatus(@PathVariable Integer id) {
        try {
            Integer res = orderService.queryOrderStatus(id);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error"+ e);
        }
    }
    @GetMapping("/pay") // &traceNo=xxx
    public void pay(Integer id, HttpServletResponse httpResponse) throws Exception {
        // 订单信息
        Orders orders = orderService.getByOrderId(id);
        // 1.创建Client
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, alipayConfiguration.getAppId(),
                alipayConfiguration.getAppPrivateKey(), FORMAT, CHARSET, alipayConfiguration.getAlipayPublicKey(), SIGN_TYPE);

        // 2.创建并设置Request及参
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(alipayConfiguration.getNotifyUrl());
        request.setBizContent("{\"out_trade_no\":\"" + orders.getId() + "\","
                + "\"total_amount\":\"" + orders.getTotalAmount() + "\","
                + "\"subject\":\"" + orders.getProduct() + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        // request.setReturnUrl("http://localhost:3000");  // 支付完成后跳转地址
        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    // 回调
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public void payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayConfiguration.getAlipayPublicKey(), "UTF-8");

            String tradeNo = params.get("out_trade_no");    // 订单编号
            String gmtPayment = params.get("gmt_payment");  // 支付时间
            String alipayTradeNo = params.get("trade_no");  // 交易编号

            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 更新订单未已支付(订单已完成)
                orderService.updateOrder(Integer.parseInt(tradeNo), params.get("subject"), params.get("trade_no"));

                Orders orders = orderService.getByOrderId(Integer.parseInt(tradeNo));
                HashSet<Integer> userIds = new HashSet<>();
                userIds.add(orders.getUid());
                if (orders.getStatus() == 1) {
                    WebSocktService.sendMessage("pay_success", userIds);
                } else {
                    WebSocktService.sendMessage("pay_error", userIds);
                }
            }
        }
    }
}




