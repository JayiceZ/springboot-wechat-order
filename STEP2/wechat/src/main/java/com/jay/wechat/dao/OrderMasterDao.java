package com.jay.wechat.dao;

import com.jay.wechat.bean.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMasterDao {
    List<OrderMaster> findByBuyerOpenid(String buyer_openid,int start, int len);

    boolean insertNewOrder(OrderMaster orderMaster);

    //修改订单状态
    void updateOrderStatus(OrderMaster orderMaster);

    //修改支付状态
    void updatePayStatus(OrderMaster orderMaster);

    //查询单个订单
    OrderMaster findOne(String order_id);

    //取消订单
    OrderMaster cancel(OrderMaster orderMaster);

    //完结订单
    OrderMaster finish(OrderMaster orderMaster);

    //支付订单
    OrderMaster paid(OrderMaster orderMaster);
}
