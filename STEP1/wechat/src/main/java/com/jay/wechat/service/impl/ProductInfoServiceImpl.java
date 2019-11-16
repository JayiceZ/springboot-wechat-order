package com.jay.wechat.service.impl;

import com.jay.wechat.bean.ProductInfo;
import com.jay.wechat.dao.ProductInfoDao;
import com.jay.wechat.dto.Cart;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public List<ProductInfo> findByProductStatus(int product_status) {
        List ans;
        try {
            ans=productInfoDao.findByProductStatus(product_status);
        }catch (Exception e){
            e.printStackTrace();
            ans=null;
        }
        return ans;
    }

    @Override
    public boolean insertProductInfo(ProductInfo productInfo) {
        try {
            productInfoDao.insertProductInfo(productInfo);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ProductInfo findOne(String product_id) {
        ProductInfo productInfo=productInfoDao.findOne(product_id);
        return productInfo;
    }

    @Override
    public List<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
    public void updateStock(String product_id, int product_quantity) {
        ProductInfo productInfo=productInfoDao.findOne(product_id);
        productInfo.setProduct_stock(productInfo.getProduct_stock()+product_quantity);
        productInfoDao.updateStock(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<Cart> cartList) {
        for(Cart cart:cartList){
            updateStock(cart.getProduct_id(),cart.getProduct_quantity());
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<Cart> cartList) {
        for(Cart cart:cartList){
            ProductInfo productInfo=productInfoDao.findOne(cart.getProduct_id());
            //若不存在
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProduct_stock()-cart.getProduct_quantity();
            //若减去之后库存不足，则异常
            if (result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            //否则就更新库存
            updateStock(cart.getProduct_id(),-cart.getProduct_quantity());
        }
    }
}
