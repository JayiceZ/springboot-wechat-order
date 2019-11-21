package com.jay.wechat.controller;


//卖家类目


import com.jay.wechat.bean.ProductCategory;
import com.jay.wechat.service.ProductCategoryService;
import com.lly835.bestpay.rest.type.Get;
import com.lly835.bestpay.rest.type.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ModelAndView list(){
        Map<String,Object> map=new HashMap<>();
        List<ProductCategory> productCategoryList=productCategoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("/category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false,defaultValue = "0") Integer categoryId){
        Map<String,Object> map =new HashMap<>();
        if (categoryId>0){
            //实际上list中只有一个值
            List<Integer> list=new ArrayList<>();
            list.add(categoryId);
            List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(list);
            //若查找不到对应类目
            if (CollectionUtils.isEmpty(productCategoryList)){
                map.put("msg","无法找到该类目");
                map.put("url","/sell/seller/category/list");
                return new ModelAndView("/common/error",map);
            }
            map.put("productCategory",productCategoryList.get(0));
            return new ModelAndView("/category/index",map);
        }
        //若走到这一步，说明没有传来categoryId，则是添加操作
        return new ModelAndView("/category/indexAdd");
    }


    @PostMapping("/addCategory")
    public ModelAndView addCategory(ProductCategory productCategory){
        Map<String,Object> map = new HashMap<>();
        try {
            productCategoryService.insertCategory(productCategory);
        }catch (Exception e){
            map.put("msg","插入失败");
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg","类目添加成功");
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("/common/success",map);
    }

    @PostMapping("/update")
    public ModelAndView update(@RequestParam("category_name") String category_name,@RequestParam("category_id") Integer category_id){
        Map<String,Object> map = new HashMap<>();
        System.out.println(category_name);
        System.out.println(category_id);
        try {
            ProductCategory productCategory=new ProductCategory();
            productCategory.setCategory_name(category_name);
            productCategory.setCategory_id(category_id);
            productCategoryService.updateCategoryName(productCategory);
        }catch (Exception e){
            map.put("msg","更新失败");
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("/common/error",map);
        }

        map.put("msg","更新成功！");
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("/common/success",map);
    }

}
