package com.jay.wechat.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

@Data
@Alias("OrderDetail")
public class OrderDetail {
    private String detail_id;

    //订单id
    private String order_id;

    //商品id
    @JsonProperty("productId")
    private String product_id;

    //商品名称
    private String product_name;

    //单价
    private BigDecimal product_price;

    //商品数量
    @JsonProperty("productQuantity")
    private int product_quantity;

    //商品图标
    private String product_icon;
}
