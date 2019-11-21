package com.jay.wechat.service;

import com.jay.wechat.bean.OrderMaster;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

public interface PayService {

    PayResponse create(OrderMaster orderMaster);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderMaster orderMaster);
}
