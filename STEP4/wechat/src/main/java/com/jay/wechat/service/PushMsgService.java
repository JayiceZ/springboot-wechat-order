package com.jay.wechat.service;


import com.jay.wechat.bean.OrderMaster;

//消息推送service
public interface PushMsgService {

    void pushMsg(OrderMaster orderMaster);
}
