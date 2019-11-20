package com.jay.wechat.service.impl;

import com.jay.wechat.bean.SellerInfo;
import com.jay.wechat.dao.SellerInfoDao;
import com.jay.wechat.service.SellerInfoService;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo findByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }

    @Override
    public void addNewSeller(SellerInfo sellerInfo) {
        sellerInfoDao.addNewSeller(sellerInfo);
    }
}
