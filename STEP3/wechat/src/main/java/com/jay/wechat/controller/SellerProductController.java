package com.jay.wechat.controller;

import com.jay.wechat.bean.ProductCategory;
import com.jay.wechat.bean.ProductInfo;
import com.jay.wechat.enums.ProductStatusEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.form.ProductForm;
import com.jay.wechat.service.ProductCategoryService;
import com.jay.wechat.service.ProductInfoService;
import com.jay.wechat.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "size",defaultValue = "10") Integer size){
        List<ProductInfo> ans=productInfoService.findAll((page-1)*size,size);
        List<ProductInfo> count = productInfoService.findAll(0,Integer.MAX_VALUE);
        Map<String,Object> map = new HashMap<>();
        map.put("productInfoList",ans);
        map.put("pages",count.size()/size+1);
        map.put("size",size);
        map.put("currentPage",page); //当前页
        return new ModelAndView("/product/list",map);
    }

    @GetMapping("/off_sale")
    public ModelAndView off_sale(@RequestParam("productId")String productId){
        Map<String,Object>map=new HashMap<>();
        try {
            productInfoService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg","");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("/on_sale")
    public ModelAndView on_sale(@RequestParam("productId")String productId){
        Map<String,Object>map=new HashMap<>();
        try {
            productInfoService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg","");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("/index")
    //productId参数非必传
    public ModelAndView index(@RequestParam(value = "productId",required = false)String productId){
        Map<String,Object>map = new HashMap<>();
        //查询所有类目
        List<ProductCategory> productCategoryList = productCategoryService.findAll();

        map.put("productCategoryList",productCategoryList);

        //若传参了
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo=productInfoService.findOne(productId);
            map.put("productInfo",productInfo);
            return new ModelAndView("/product/index",map);
        }
        //若没有传参
        return new ModelAndView("/product/indexAdd",map);
    }

    @RequestMapping(value = "/addProduct",method = RequestMethod.POST)
    public ModelAndView addProduct(ProductForm productForm){
        Map<String,Object>map = new HashMap<>();
        ProductInfo productInfo=new ProductInfo();
        BeanUtils.copyProperties(productForm,productInfo);
        productInfo.setProduct_id(KeyUtil.getUniqueKey());
        productInfo.setProduct_status(ProductStatusEnum.UP.getCode());
        try {
            productInfoService.insertProductInfo(productInfo);
        }catch (SellException e){
            map.put("msg","错误");
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg","添加成功");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }

    @PostMapping("/update")
    public ModelAndView update(ProductInfo productInfo){
        Map<String,Object>map = new HashMap<>();
        productInfo.setProduct_status(ProductStatusEnum.UP.getCode());
        try {
            productInfoService.updateProductAll(productInfo);
        }catch (SellException e){
            map.put("msg","错误");
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg","更新成功");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }


}
