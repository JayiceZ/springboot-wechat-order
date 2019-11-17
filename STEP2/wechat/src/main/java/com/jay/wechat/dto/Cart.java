package com.jay.wechat.dto;

import lombok.Data;

//购物车
@Data
public class Cart {
    private String product_id;

    private Integer product_quantity;

    public Cart(String product_id, Integer product_quantity) {
        this.product_id = product_id;
        this.product_quantity = product_quantity;
    }
}
