package com.jay.wechat.Test;

import com.alibaba.druid.support.json.JSONUtils;
import com.jay.wechat.bean.ProductInfo;
import com.jay.wechat.dto.Cart;
import com.jay.wechat.enums.ProductStatusEnum;
import com.jay.wechat.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductInfoTest {
    @Autowired
    private ProductInfoService productInfoService;

    @GetMapping("/test6")
    @ResponseBody
    public String test6(){
        ProductInfo productInfo=new ProductInfo("a1237","豆浆",new BigDecimal(1000),1000,"非常好吃","http://xxx.jpg",0,2);
        boolean flag=productInfoService.insertProductInfo(productInfo);
        return flag?productInfo.toString():"error";
    }

    @GetMapping("/test7")
    @ResponseBody
    public List test7(){
        return productInfoService.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @GetMapping("/test8")
    @ResponseBody
    public ProductInfo test8(){
        return productInfoService.findOne("a1234");
    }

    @GetMapping("/test9")
    @ResponseBody
    public List test9(){
        PageRequest pageRequest=PageRequest.of(0,2);
        List ans=productInfoService.findAll(pageRequest);
        return ans;
    }

    @GetMapping("/testw")
    @ResponseBody
    public String testw(){
        productInfoService.updateStock("a1234",5);
        return "success";
    }

    @GetMapping("/testt")
    @ResponseBody
    public String testt(){
        List<Cart>cartList=new ArrayList<>();
        Cart cart=new Cart("a1240",5);
        cartList.add(cart);
        productInfoService.increaseStock(cartList);
        return "success";
    }
}
