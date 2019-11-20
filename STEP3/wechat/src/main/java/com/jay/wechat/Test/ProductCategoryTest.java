package com.jay.wechat.Test;

import com.jay.wechat.bean.ProductCategory;
import com.jay.wechat.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProductCategoryTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/test1")
    @ResponseBody
    public ProductCategory selectOneCategory(){
        ProductCategory productCategory=productCategoryService.selectOneCategory(1);
        return productCategory;
    }

    @GetMapping("/test2")
    @ResponseBody
    public String insertCategory(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategory_name("老人最爱");
        productCategory.setCategory_type(7);
        if(productCategoryService.insertCategory(productCategory))
            return "success";
        return "error";
    }

    @GetMapping("/test3")
    @ResponseBody
    public String updateCategoryType(){
        ProductCategory productCategory=productCategoryService.selectOneCategory(1);
        productCategory.setCategory_type(2);
        productCategoryService.updateCategoryType(productCategory);
        return "success";
    }

    @GetMapping("/test4")
    @ResponseBody
    public String findByCategoryTypeIn(){
        List list= Arrays.asList(2,3,4);
        List ans=productCategoryService.findByCategoryTypeIn(list);
        return ans.toString();
    }

    @GetMapping("/test5")
    @ResponseBody
    public String findAll(){
        List ans=productCategoryService.findAll();
        return ans.toString();
    }


}
