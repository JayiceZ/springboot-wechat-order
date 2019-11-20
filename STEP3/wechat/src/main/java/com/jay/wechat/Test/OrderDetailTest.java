package com.jay.wechat.Test;

import com.jay.wechat.bean.OrderDetail;
import com.jay.wechat.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class OrderDetailTest {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/testc")
    @ResponseBody
    public OrderDetail testc(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetail_id("a3");
        orderDetail.setOrder_id("1");
        orderDetail.setProduct_id("a1238");
        orderDetail.setProduct_name("芒果丁");
        orderDetail.setProduct_price(new BigDecimal(10));
        orderDetail.setProduct_quantity(2);
        orderDetail.setProduct_icon("http://xxx.jpg");
        boolean flag=orderDetailService.insertNewOrderDetail(orderDetail);
        return flag?orderDetail:null;
    }

    @GetMapping("/testd")
    @ResponseBody
    public List testd(){
        List ans = orderDetailService.findByOrderId("1");
        return ans;
    }
}
