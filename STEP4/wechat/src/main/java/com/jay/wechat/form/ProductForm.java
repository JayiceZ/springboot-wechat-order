package com.jay.wechat.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {

    //id
    private String product_id;
    //名字
    private String product_name;
    //单价
    private BigDecimal product_price;
    //库存
    private int product_stock;
    //商品描述
    private String product_description;
    //商品图标，是一个链接地址
    private String product_icon;
    //所属类目编号
    private int category_type;
}
