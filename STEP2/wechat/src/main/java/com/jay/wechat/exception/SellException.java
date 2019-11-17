package com.jay.wechat.exception;

import com.jay.wechat.enums.ResultEnum;

public class SellException extends RuntimeException{
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        this.code=resultEnum.getCode();
    }

    public SellException(Integer code,String msg){
        super(msg);
        this.code=code;
    }
}
