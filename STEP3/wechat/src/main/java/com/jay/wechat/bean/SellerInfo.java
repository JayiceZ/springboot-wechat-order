package com.jay.wechat.bean;


import lombok.Data;
import org.apache.ibatis.type.Alias;


@Data
@Alias("SellerInfo")
public class SellerInfo {

    private String seller_id;

    private String user_name;

    private String password;

    private String openid;
}
