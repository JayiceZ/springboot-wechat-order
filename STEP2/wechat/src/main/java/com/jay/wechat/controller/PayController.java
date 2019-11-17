package com.jay.wechat.controller;

import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.service.OrderMasterService;
import com.jay.wechat.service.PayService;
import com.jay.wechat.utils.MathUtil;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private PayService payService;


    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl){
        OrderMaster orderMaster=orderMasterService.findOne(orderId);
        //若订单不存在
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //对该订单号发起支付，获得预支付对象
        PayResponse payResponse=payService.create(orderMaster);
        Map<String,Object> map=new HashMap<>();
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("/pay/create.ftl",map);
    }

    //接收微信支付状态的异步通知
    //在进行payService配置的时候，就已经配置了异步通知url（也就是该接口），当支付状态有改变时，异步访问该接口，参数为xml格式的字符串
    //另外在更新订单支付状态需要进行一系列安全的判断
    //都在service层进行处理
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        //1.验证签名
        //2.支付状态
        //（这两步由第三方sdk来进行，这里只在service层进行后面一步就可以了）
        //3.支付金额
        //4.支付人是否一样（要看具体需求，是否允许代付，在本项目中不进行限制，只要给了钱就可以了）

        PayResponse payResponse=payService.notify(notifyData);

        ////该接口会一直被回调，所以会频繁出现 异常被抛出 的情况，所以还要在修改完订单状态之后，调用微信的sdk，告知不要再继续回调我的接口了
        //该文件写的就是微信方想得到的xml内容，用来通知订单支付完了，不需要一直异步请求接口了
        return new ModelAndView("/pay/success.ftl");
    }
}
