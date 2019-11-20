package com.jay.wechat.dto;

import com.jay.wechat.bean.OrderDetail;
import com.jay.wechat.enums.OrderStatusEnum;
import com.jay.wechat.enums.PayStatusEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderMasterDTO {

    //订单id
    private String order_id;

    //买家名字
    private String buyer_name;

    //买家手机号
    private String buyer_phone;

    //买家地址
    private String buyer_address;

    //买家微信
    private String buyer_openid;

    //订单总金额
    private BigDecimal order_amount;

    //订单状态,默认为新下单
    private int order_status;

    //支付状态,默认为0，未支付
    private int pay_status;

    //创建时间
    private Date create_time;

    //更新时间
    private Date update_time;

    private List<OrderDetail> orderDetailList;
}
