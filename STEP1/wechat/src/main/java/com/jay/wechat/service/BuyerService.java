package com.jay.wechat.service;

import com.jay.wechat.bean.OrderMaster;

public interface BuyerService {

    //查询一个订单
    OrderMaster findOrderOne(String openid,String orderId);

    //取消一个订单
    OrderMaster cancelOrder(String openid,String orderId);
}
