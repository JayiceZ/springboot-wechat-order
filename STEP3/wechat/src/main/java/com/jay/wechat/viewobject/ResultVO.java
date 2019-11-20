package com.jay.wechat.viewobject;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


/*
* http请求返回的对象
* */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //若有某一字段是null，则不会转化为json
public class ResultVO<T> {

    //错误码
    private int code;

    //提示信息
    private String msg="";

    //返回的具体内容
    private T data;
}
