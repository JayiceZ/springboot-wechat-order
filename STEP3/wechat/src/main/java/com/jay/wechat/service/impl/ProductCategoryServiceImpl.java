package com.jay.wechat.service.impl;

import com.jay.wechat.bean.ProductCategory;
import com.jay.wechat.dao.ProductCategoryDao;
import com.jay.wechat.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCategoryServiceImpl implements
        ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Override
    public ProductCategory selectOneCategory(int id) {
        ProductCategory ans;
        try {
            ans=productCategoryDao.selectOneCategory(id);
        }catch (Exception e){
            e.printStackTrace();
            ans=null;
        }
        return ans;
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDao.findAll();
    }

    @Override
    public boolean insertCategory(ProductCategory productCategory) {
        try {
            productCategoryDao.insertCategory(productCategory);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void updateCategoryType(ProductCategory productCategory) {
        productCategoryDao.updateCategoryType(productCategory);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public void updateCategoryName(ProductCategory productCategory) {
        productCategoryDao.updateCategoryName(productCategory);
    }
}
