package com.jay.wechat.Test;


import com.jay.wechat.bean.SellerInfo;
import com.jay.wechat.service.SellerInfoService;
import com.jay.wechat.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerInfoTest {

    @Autowired
    private SellerInfoService sellerInfoService;

    @GetMapping("/testz")
    public SellerInfo testz(){
        SellerInfo sellerInfo=sellerInfoService.findByOpenid("abc");
        return sellerInfo;
    }

    @GetMapping("/testx")
    public SellerInfo testx(){
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setSeller_id(KeyUtil.getUniqueKey());
        sellerInfo.setUser_name("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        sellerInfoService.addNewSeller(sellerInfo);
        return sellerInfo;
    }
}
