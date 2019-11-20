package com.jay.wechat.controller;

import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.service.OrderMasterService;
import com.lly835.bestpay.rest.type.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//这里是网页版卖家端的controller，由于卖家端更多的是展示界面，页面的跳转，所以采用Controller而不是RestController

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;


    /*
    * page:第几页
    * size:一页有多少条数据
    * */
    //订单列表
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "size",defaultValue = "10") Integer size){
        List<OrderMaster> ans=orderMasterService.findAll((page-1)*size,size);
        List<OrderMaster> count = orderMasterService.findAll(0,Integer.MAX_VALUE);
        Map<String,Object> map = new HashMap<>();
        map.put("orderMasterList",ans);
        map.put("pages",count.size()/size+1);
        map.put("size",size);
        map.put("currentPage",page); //当前页
        return new ModelAndView("/order/list",map);
    }


    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId){
        Map<String,Object>map = new HashMap<>();
        OrderMaster orderMaster;
        try {
            orderMaster = orderMasterService.findOne(orderId);
            orderMasterService.cancel(orderMaster);
        }catch (SellException e){
            map.put("msg",ResultEnum.ORDER_NOT_EXIST.getMsg());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId){
        Map<String,Object> map = new HashMap<>();
        OrderMaster orderMaster;
        try {
            orderMaster = orderMasterService.findOne(orderId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error",map);
        }

        map.put("orderMaster",orderMaster);
        return new ModelAndView("/order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId")String orderId){
        Map<String,Object>map = new HashMap<>();
        try {
            OrderMaster orderMaster = orderMasterService.findOne(orderId);
            orderMasterService.finish(orderMaster);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS);
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/success",map);
    }
}
