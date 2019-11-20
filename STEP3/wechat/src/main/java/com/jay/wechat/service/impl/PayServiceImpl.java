package com.jay.wechat.service.impl;

import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.service.OrderMasterService;
import com.jay.wechat.service.PayService;
import com.jay.wechat.utils.JsonUtil;
import com.jay.wechat.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME="微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderMasterService orderMasterService;

    //支付逻辑
    @Override
    public PayResponse create(OrderMaster orderMaster) {
        PayRequest payRequest=new PayRequest();
        //订单openid写入
        payRequest.setOpenid(orderMaster.getBuyer_openid());
        //订单总价写入
        payRequest.setOrderAmount(orderMaster.getOrder_amount().doubleValue());
        //订单id写入
        payRequest.setOrderId(orderMaster.getOrder_id());
        //订单名字写入
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        //获得预付款信息
        PayResponse payResponse = bestPayService.pay(payRequest);
        /*
        payResponse的内容包括但不限于为:
            * appId:
            * timeStamp:
            * nonceStr(随机字符):
            * packAge(含有prepay_id):prepay_id=wx12344456465
            * signType(签名类型):MD5
            * paySign(签名)_
        * */

        return payResponse;

    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);

        //查询订单
        OrderMaster orderMaster = orderMasterService.findOne(payResponse.getOrderId());

        //判断订单是否存在
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断该订单支付的金额和数据库中该订单的金额是否一致
        if (!MathUtil.equals(payResponse.getOrderAmount(),orderMaster.getOrder_amount().doubleValue())){
            throw new SellException(ResultEnum.WECHAT_PAY_NOTIFY_MONRY_ERROR);
        }

        //修改订单的支付状态
        //订单被改为支付成功状态后，前端就不会再展示(去支付)这一按钮了
        orderMasterService.paid(orderMaster);
        return payResponse;
    }

    //退款
    @Override
    public RefundResponse refund(OrderMaster orderMaster) {
        RefundRequest refundRequest = new RefundRequest();

        refundRequest.setOrderId(orderMaster.getOrder_id());
        refundRequest.setOrderAmount(orderMaster.getOrder_amount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        //能接收到退款对象，则时退款成功了
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        return refundResponse;
    }
}
