package com.jay.wechat.service;

import com.jay.wechat.bean.ProductInfo;
import com.jay.wechat.dto.Cart;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    //根据商品状态查找商品
    List<ProductInfo> findByProductStatus(int product_status);

    //管理端：添加商品
    boolean insertProductInfo(ProductInfo productInfo);

    //根据id查询商品
    ProductInfo findOne(String product_id);

    //管理端：查询所有商品，注意要有一个分页的功能
    List<ProductInfo> findAll(int start,int len);

    void updateStock(String product_id,int product_quantity);

    //加库存
    void increaseStock(List<Cart> cartList);

    //减库存
    void decreaseStock(List<Cart> cartList);

    //上架
    ProductInfo onSale(String product_id);

    //下架
    ProductInfo offSale(String product_id);

    //更新商品所有状态
    void updateProductAll(ProductInfo productInfo);
}
