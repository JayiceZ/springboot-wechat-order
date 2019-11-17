package com.jay.wechat.controller;


import com.jay.wechat.bean.ProductCategory;
import com.jay.wechat.bean.ProductInfo;
import com.jay.wechat.enums.ProductStatusEnum;
import com.jay.wechat.service.ProductCategoryService;
import com.jay.wechat.service.ProductInfoService;
import com.jay.wechat.utils.ResultVOUtil;
import com.jay.wechat.viewobject.ProductInfoVO;
import com.jay.wechat.viewobject.ProductVO;
import com.jay.wechat.viewobject.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* 买家相关
* */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;


    //查出所有上架商品，用于展现
    @GetMapping("/list")
    public ResultVO list(){
        //查出所有上架商品
        List<ProductInfo> productInfoList=productInfoService.findByProductStatus(ProductStatusEnum.UP.getCode());

        //查询类目
        List<Integer> categoryTypelist=new ArrayList<>();

        //根据所有上架商品对的类目，来对categoryTypelist进行初始化
        for(ProductInfo productInfo:productInfoList){
            categoryTypelist.add(productInfo.getCategory_type());
        }

        List<ProductCategory> productCategoryList=productCategoryService.findByCategoryTypeIn(categoryTypelist);

        List<ProductVO> productVOList=new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryType(productCategory.getCategory_type());
            productVO.setCategoryName(productCategory.getCategory_name());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if (productInfo.getCategory_type()==productCategory.getCategory_type()){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setList(productInfoVOList);
            productVOList.add(productVO);
        }
        //获取输出结果
        ResultVO resultVO= ResultVOUtil.success(productVOList);
        return resultVO;
    }
}
