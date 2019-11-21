package com.jay.wechat.utils;

//用来校验金额
public class MathUtil {

    //误差校验，若两值相差不超过MONEY_RANGE，则可以判断支付成功
    private static final Double MONEY_RANGE=0.01;

    public static Boolean equals(Double d1,Double d2){
        Double result = Math.abs(d1-d2);
        return (result<MONEY_RANGE)?true:false;
    }
}
