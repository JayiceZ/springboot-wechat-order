package com.jay.wechat.service;

import com.jay.wechat.bean.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    ProductCategory selectOneCategory(int id);

    List<ProductCategory> findAll();

    boolean insertCategory(ProductCategory productCategory);

    void updateCategoryType(ProductCategory productCategory);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    void updateCategoryName(ProductCategory productCategory);


}
