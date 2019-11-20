package com.jay.wechat.dao;

import com.jay.wechat.bean.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductCategoryDao {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    List<ProductCategory> findAll();

    ProductCategory selectOneCategory(int id);

    boolean insertCategory(ProductCategory productCategory);

    void updateCategoryType(ProductCategory productCategory);

    void updateCategoryName(ProductCategory productCategory);

}
