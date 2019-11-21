package com.jay.wechat.service;

import com.jay.wechat.bean.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    //添加订单详情
    boolean insertNewOrderDetail(OrderDetail orderDetail);

    //根据订单获得所有的订单详情
    List<OrderDetail> findByOrderId(String order_id);

}
