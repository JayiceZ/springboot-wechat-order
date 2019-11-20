package com.jay.wechat.service;

import com.jay.wechat.bean.OrderMaster;

import java.util.List;

public interface OrderMasterService {
    //根据微信号查找订单
    List<OrderMaster> findByBuyerOpenid(String buyer_openid, int start, int len);

    //创建新订单
    OrderMaster insertNewOrder(OrderMaster orderMaster);

    //查询单个订单
    OrderMaster findOne(String order_id);

    //查询所有订单(卖家端)
    List<OrderMaster> findAll(int start,int len);

    //修改订单状态
    boolean updateOrderStatus(OrderMaster orderMaster);

    //修改支付状态
    boolean updatePayStatus(OrderMaster orderMaster);

    //取消订单
    OrderMaster cancel(OrderMaster orderMaster);

    //完结订单
    OrderMaster finish(OrderMaster orderMaster);

    //支付订单
    OrderMaster paid(OrderMaster orderMaster);
}
