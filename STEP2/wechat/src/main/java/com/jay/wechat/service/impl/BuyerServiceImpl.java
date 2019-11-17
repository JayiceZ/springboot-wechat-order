package com.jay.wechat.service.impl;

import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.service.BuyerService;
import com.jay.wechat.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderMasterService orderMasterService;
    @Override
    public OrderMaster findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderMaster cancelOrder(String openid, String orderId) {
        OrderMaster orderMaster=checkOrderOwner(openid,orderId);
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderMaster;
    }

    //判断订单归属是否合法
    private OrderMaster checkOrderOwner(String openid,String orderId){
        OrderMaster orderMaster=orderMasterService.findOne(orderId);
        if (orderMaster==null){
            return null;
        }
        //若该订单不是本人在进行查询
        if(!orderMaster.getBuyer_openid().equals(openid)){
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderMaster;
    }
}
