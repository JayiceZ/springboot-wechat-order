package com.jay.wechat.bean;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

@Alias("ProductInfo")
@Data
public class ProductInfo {
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
    //商品状态。0为正常，1为下架
    private int product_status;
    //所属类目编号
    private int category_type;

    public ProductInfo(String product_id, String product_name, BigDecimal product_price, int product_stock, String product_description, String product_icon, int product_status, int category_type) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_stock = product_stock;
        this.product_description = product_description;
        this.product_icon = product_icon;
        this.product_status = product_status;
        this.category_type = category_type;
    }

    public ProductInfo() {
    }
}
