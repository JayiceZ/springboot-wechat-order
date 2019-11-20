package com.jay.wechat.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/*
* 商品详情
* */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String product_id;

    @JsonProperty("name")
    private String product_name;

    @JsonProperty("price")
    private BigDecimal product_price;

    @JsonProperty("description")
    private String product_description;

    @JsonProperty("icon")
    private String product_icon;

}
