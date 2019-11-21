package com.jay.wechat.service.impl;

import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.service.PushMsgService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class PushMsgServiceImpl implements PushMsgService {

    @Autowired
    private WxMpService wxMpService;

    @Override
    public void pushMsg(OrderMaster orderMaster) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        //要使用哪个模板
        wxMpTemplateMessage.setTemplateId("这里应该填写模板id");
        //要推送给哪个人？给下订单的那个人呗
        wxMpTemplateMessage.setToUser(orderMaster.getBuyer_openid());

        List<WxMpTemplateData> data= Arrays.asList(
                new WxMpTemplateData("first","亲，记得收货"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2","13313212"),  //商家电话
                new WxMpTemplateData("keyword3",orderMaster.getOrder_id()),  //订单号
                new WxMpTemplateData("keyword4",orderMaster.getOrderStatusEnum().getMsg()),   //订单状态
                new WxMpTemplateData("keyword5","￥"+orderMaster.getOrder_amount()),   //价格
                new WxMpTemplateData("keyword6","欢迎再次光临")  //备注
        );
        wxMpTemplateMessage.setData(data);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);  //调用这个方法，就会把消息推送到对应的openid上
        }catch (WxErrorException e){
            System.out.println("微信模块信息发送失败");
        }
    }
}
