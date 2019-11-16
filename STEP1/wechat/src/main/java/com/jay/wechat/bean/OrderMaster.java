package com.jay.wechat.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jay.wechat.enums.OrderStatusEnum;
import com.jay.wechat.enums.PayStatusEnum;
import com.jay.wechat.utils.serializer.Date2LongSerializer;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Alias("OrderMaster")
@Data
public class OrderMaster {
    //订单id
    @JsonProperty("orderId")
    private String order_id;

    //买家名字
    @JsonProperty("buyerName")
    private String buyer_name;

    //买家手机号
    @JsonProperty("buyerPhone")
    private String buyer_phone;

    //买家地址
    @JsonProperty("buyerAddress")
    private String buyer_address;

    //买家微信
    @JsonProperty("buyerOpenid")
    private String buyer_openid;

    //订单总金额
    @JsonProperty("orderAmount")
    private BigDecimal order_amount;

    //订单状态,默认为新下单
    @JsonProperty("orderStatus")
    private int order_status= OrderStatusEnum.NEW.getCode();

    //支付状态,默认为0，未支付
    @JsonProperty("payStatus")
    private int pay_status= PayStatusEnum.WAIT.getCode();

    //创建时间
    @JsonProperty("createTime")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date create_time;

    //更新时间
    @JsonProperty("updateTime")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date update_time;

    @Transient
    @JsonProperty("orderDetailList")
    private List<OrderDetail> orderDetailList;

}
