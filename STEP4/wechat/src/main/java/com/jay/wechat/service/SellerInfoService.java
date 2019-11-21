package com.jay.wechat.service;

import com.jay.wechat.bean.SellerInfo;


//卖家端
public interface SellerInfoService {

    //根据openid查找SellerInfo
    SellerInfo findByOpenid(String openid);

    //添加新的Seller
    void addNewSeller(SellerInfo sellerInfo);
}
