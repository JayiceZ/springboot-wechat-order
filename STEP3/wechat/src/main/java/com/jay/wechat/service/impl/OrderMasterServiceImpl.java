package com.jay.wechat.service.impl;

import com.jay.wechat.bean.OrderDetail;
import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.bean.ProductInfo;
import com.jay.wechat.dao.OrderMasterDao;
import com.jay.wechat.dto.Cart;
import com.jay.wechat.enums.OrderStatusEnum;
import com.jay.wechat.enums.PayStatusEnum;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.service.OrderDetailService;
import com.jay.wechat.service.OrderMasterService;
import com.jay.wechat.service.PayService;
import com.jay.wechat.service.ProductInfoService;
import com.jay.wechat.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private PayService payService;

    @Override
    public List<OrderMaster> findByBuyerOpenid(String buyer_openid, int start, int len) {
        return orderMasterDao.findByBuyerOpenid(buyer_openid,start,len);
    }

    @Override
    @Transactional
    public OrderMaster insertNewOrder(OrderMaster orderMaster) {
        //订单id生成
        String order_id=KeyUtil.getUniqueKey();
        //当前总价
        BigDecimal order_amount=new BigDecimal(0);

        List<Cart> cartList=new ArrayList<>();
        //查询商品（数量，价格）
        List<OrderDetail> orderDetailList=orderMaster.getOrderDetailList();
       for(OrderDetail orderDetail:orderDetailList){
           ProductInfo productInfo=productInfoService.findOne(orderDetail.getProduct_id());
           //若商品不存在
           if(productInfo==null){
               throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }
           //加上该种商品,当前订单总价获得更新
           order_amount=productInfo.getProduct_price().multiply(new BigDecimal(orderDetail.getProduct_quantity())).add(order_amount);


           //复制
           BeanUtils.copyProperties(productInfo,orderDetail);
           //订单详情入库
           orderDetail.setDetail_id(KeyUtil.getUniqueKey());
           orderDetail.setOrder_id(order_id);

           orderDetailService.insertNewOrderDetail(orderDetail);
           //写入购物车信息中
           Cart cart=new Cart(orderDetail.getProduct_id(),orderDetail.getProduct_quantity());
           cartList.add(cart);
       }
       //写入订单数据
       OrderMaster orderMasterAns=new OrderMaster();
        //把前端传过来的关于订单主的信息（比如说微信号)复制过来
        BeanUtils.copyProperties(orderMaster,orderMasterAns);
       orderMasterAns.setOrder_id(order_id);
       orderMasterAns.setOrder_amount(order_amount);

       orderMasterDao.insertNewOrder(orderMasterAns);

       //扣库存
        productInfoService.decreaseStock(cartList);

        return orderMasterAns;
    }

    @Override
    public OrderMaster findOne(String order_id) {
        OrderMaster orderMaster=orderMasterDao.findOne(order_id);
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList=orderDetailService.findByOrderId(order_id);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        orderMaster.setOrderDetailList(orderDetailList);
        return orderMaster;
    }


    @Override
    public boolean updateOrderStatus(OrderMaster orderMaster) {
        try {
            orderMasterDao.updateOrderStatus(orderMaster);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updatePayStatus(OrderMaster orderMaster) {
        try {
            orderMasterDao.updatePayStatus(orderMaster);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public OrderMaster cancel(OrderMaster orderMaster) {
        //判断订单状态
            //若已经取消，则无法再取消了
        if(orderMaster.getOrder_status()!= OrderStatusEnum.NEW.getCode()){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态，修改为取消
        orderMaster.setOrder_status(OrderStatusEnum.CANCEL.getCode());
        boolean flag=updateOrderStatus(orderMaster);
        if (!flag){
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //返还库存
            //若该订单中没有商品
        if (CollectionUtils.isEmpty(orderMaster.getOrderDetailList())){
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<Cart> cartList=orderMaster.getOrderDetailList().stream().map(e -> new Cart(e.getProduct_id(),e.getProduct_quantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartList);

        //如果已支付，需要退款
        if (orderMaster.getPay_status()== PayStatusEnum.SUCCESS.getCode()){
            payService.refund(orderMaster);
            System.out.println("退款");
        }
        return orderMaster;
    }

    @Override
    public OrderMaster finish(OrderMaster orderMaster) {
        //判断订单状态
            //若不是新建态，则无法转换状态
        if(orderMaster.getOrder_status()!=OrderStatusEnum.NEW.getCode()){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderMaster.setOrder_status(OrderStatusEnum.FINISH.getCode());
            //在数据库中修改状态
        boolean flag = updateOrderStatus(orderMaster);
        if (!flag){
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderMaster;
    }

    @Override
    @Transactional
    public OrderMaster paid(OrderMaster orderMaster) {
        //判断订单状态
            //只有新建订单才能被支付
        if(orderMaster.getOrder_status()!=OrderStatusEnum.NEW.getCode()){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
            //如果不是等待支付状态，则无法完成支付
        if(orderMaster.getPay_status()!=PayStatusEnum.WAIT.getCode()){
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderMaster.setPay_status(PayStatusEnum.SUCCESS.getCode());
        boolean flag=updatePayStatus(orderMaster);
        if(!flag){
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderMaster;
    }

    @Override
    public List<OrderMaster> findAll(int start, int len) {
        List<OrderMaster> ans = orderMasterDao.findAll(start,len);
        return ans;
    }
}
