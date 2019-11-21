package com.jay.wechat.dao;

import com.jay.wechat.bean.SellerInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerInfoDao {

    SellerInfo findByOpenid(String openid);

    void addNewSeller(SellerInfo sellerInfo);

}
