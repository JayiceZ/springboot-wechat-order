package com.jay.wechat.service.impl;

import com.jay.wechat.bean.OrderDetail;
import com.jay.wechat.dao.OrderDetailDao;
import com.jay.wechat.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public boolean insertNewOrderDetail(OrderDetail orderDetail) {
        try {
            orderDetailDao.insertNewOrderDetail(orderDetail);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<OrderDetail> findByOrderId(String order_id) {
        return orderDetailDao.findByOrderId(order_id);
    }
}
