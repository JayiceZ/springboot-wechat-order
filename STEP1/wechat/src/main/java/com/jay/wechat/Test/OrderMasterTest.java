package com.jay.wechat.Test;

import com.jay.wechat.bean.OrderDetail;
import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.dto.Cart;
import com.jay.wechat.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderMasterTest {
    @Autowired
    private OrderMasterService orderMasterService;

    @GetMapping("/testa")
    @ResponseBody
    public List<OrderMaster> testa(){
        List<OrderMaster> ans=orderMasterService.findByBuyerOpenid("jj66666",0,3);
        return ans;
    }

    @GetMapping("/testb")
    @ResponseBody
    public OrderMaster testb(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrder_id("2");
        orderMaster.setBuyer_name("周杰伦");
        orderMaster.setBuyer_phone("1233123123");
        orderMaster.setBuyer_address("台湾省");
        orderMaster.setBuyer_openid("jay666");
        orderMaster.setOrder_amount(new BigDecimal(100));
        orderMasterService.insertNewOrder(orderMaster);
        return orderMaster;
    }

    @GetMapping("/testq")
    @ResponseBody
    public OrderMaster testq(){
        return orderMasterService.findOne("1573806469231235791");
    }

    @GetMapping("/teste")
    @ResponseBody
    public String teste(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setBuyer_name("林俊杰");
        orderMaster.setBuyer_phone("1364564");
        orderMaster.setBuyer_address("新加坡");
        orderMaster.setBuyer_openid("jj66666");

        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setProduct_id("a1234");
        orderDetail.setProduct_quantity(2);
        orderDetailList.add(orderDetail);

        OrderDetail orderDetail2=new OrderDetail();
        orderDetail2.setProduct_id("a1240");
        orderDetail2.setProduct_quantity(3);
        orderDetailList.add(orderDetail2);

        OrderDetail orderDetail3=new OrderDetail();
        orderDetail3.setProduct_id("a1239");
        orderDetail3.setProduct_quantity(3);
        orderDetailList.add(orderDetail3);

        orderMaster.setOrderDetailList(orderDetailList);

        orderMasterService.insertNewOrder(orderMaster);
        return "success";
    }

    @GetMapping("/testr")
    @ResponseBody
    public String testr(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrder_id("1573806469231235791");
        orderMaster.setOrder_status(1);
        orderMasterService.updateOrderStatus(orderMaster);
        return "success";
    }

    @GetMapping("/testy")
    @ResponseBody
    public String testy(){
        OrderMaster orderMaster=orderMasterService.findOne("1573807989524746417");
        orderMasterService.cancel(orderMaster);
        return "success";
    }

    @GetMapping("/testu")
    @ResponseBody
    public String testu(){
        OrderMaster orderMaster=orderMasterService.findOne("1573807989524746417");
        orderMasterService.finish(orderMaster);
        return "success";
    }

    @GetMapping("/testi")
    @ResponseBody
    public String testi(){
        OrderMaster orderMaster=orderMasterService.findOne("1573807989524746417");
        orderMasterService.paid(orderMaster);
        return "success";
    }

}
