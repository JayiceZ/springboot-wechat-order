package com.jay.wechat.enums;


import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISH(1,"已完结"),
    CANCEL(2,"已取消"),
    ;

    private int code;

    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
