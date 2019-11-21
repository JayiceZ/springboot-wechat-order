package com.jay.wechat.dao;

import com.jay.wechat.bean.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao {
    //根据订单获得所有的订单详情
    List<OrderDetail> findByOrderId(String order_id);

    //添加订单详情
    boolean insertNewOrderDetail(OrderDetail orderDetail);
}
