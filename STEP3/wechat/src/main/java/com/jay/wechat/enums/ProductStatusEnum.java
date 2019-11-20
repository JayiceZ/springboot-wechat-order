package com.jay.wechat.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum{

    UP(0,"在架"),
    DOWN(1,"下架")
    ;
    private int code;

    private String message;

    ProductStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return code;
    }
}
