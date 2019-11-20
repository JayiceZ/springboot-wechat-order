package com.jay.wechat.utils;

import java.util.Random;

public class KeyUtil {
    //生成唯一的主键
    //格式：时间+随机数
    public static synchronized String getUniqueKey(){
        Random random=new Random();

        //生成六位随机数
       Integer a= random.nextInt(900000)+100000;

       return System.currentTimeMillis()+String.valueOf(a);
    }
}
