package com.jay.wechat.dao;

import com.jay.wechat.bean.ProductInfo;
import com.jay.wechat.dto.Cart;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoDao {

    //根据商品状态查询商品
    List<ProductInfo> findByProductStatus(int product_status);

    boolean insertProductInfo(ProductInfo productInfo);

    //根据id查询商品
    ProductInfo findOne(String product_id);

    //管理端：查询所有商品，注意要有一个分页的功能
    List<ProductInfo> findAll(Pageable pageable);

    void updateStock(ProductInfo productInfo);

    //加库存
    void increaseStock(List<Cart> cartList);

    //减库存
    void decreaseStock(List<Cart> cartList);

}
