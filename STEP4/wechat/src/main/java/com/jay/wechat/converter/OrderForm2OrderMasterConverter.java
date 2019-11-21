package com.jay.wechat.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jay.wechat.bean.OrderDetail;
import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2OrderMasterConverter {
    public static OrderMaster convert(OrderForm orderForm){
        Gson gson=new Gson();
        OrderMaster orderMaster=new OrderMaster();

        orderMaster.setBuyer_name(orderForm.getName());
        orderMaster.setBuyer_phone(orderForm.getPhone());
        orderMaster.setBuyer_address(orderForm.getAddress());
        orderMaster.setBuyer_openid(orderForm.getOpenid());
        //对orderForm中的item（字符串类型，但实际上是json格式的字符串）进行转换成购物车（Cart）内容
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderForm.setItems(orderForm.getItems().replaceAll("productId","product_id"));
        orderForm.setItems(orderForm.getItems().replaceAll("productQuantity","product_quantity"));
        try {
            orderDetailList=gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderMaster.setOrderDetailList(orderDetailList);
        return orderMaster;
    }
}
