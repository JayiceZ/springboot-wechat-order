package com.jay.wechat.controller;

import com.jay.wechat.bean.OrderDetail;
import com.jay.wechat.bean.OrderMaster;
import com.jay.wechat.converter.OrderForm2OrderMasterConverter;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import com.jay.wechat.form.OrderForm;
import com.jay.wechat.service.BuyerService;
import com.jay.wechat.service.OrderMasterService;
import com.jay.wechat.utils.ResultVOUtil;
import com.jay.wechat.viewobject.ResultVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;
    @PostMapping("/create")
    //创建订单
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //若前端传来的参数不正确，则抛出异常
        if (bindingResult.hasErrors()){
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderMaster orderMaster= OrderForm2OrderMasterConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderMaster.getOrderDetailList())){
            throw new SellException( ResultEnum.CART_EMPTY);
        }
        OrderMaster orderMasterInsertCompleted=orderMasterService.insertNewOrder(orderMaster);
        Map<String,String> map=new HashMap<>();
        map.put("orderId",orderMasterInsertCompleted.getOrder_id());

        return ResultVOUtil.success(map);
    }

    //取消订单
    @PostMapping("/cancel")
    @ResponseBody
    public ResultVO cancel(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){
        OrderMaster orderMaster = buyerService.findOrderOne(openid, orderId);
        orderMasterService.cancel(orderMaster);
        return ResultVOUtil.success();
    }

    //订单详情
    @GetMapping("/detail")
    @ResponseBody
    public ResultVO<OrderMaster> detail(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){
        OrderMaster orderMaster=buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderMaster);
    }

    //订单列表
    @GetMapping("/list")
    @ResponseBody
    public ResultVO<List<OrderMaster>> list(@RequestParam("openid") String openid,@RequestParam(value = "page",defaultValue = "0") Integer page,@RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //根据页数信息获取订单信息
        List<OrderMaster> orderMasterList=orderMasterService.findByBuyerOpenid(openid,page*size,size);
        //订单信息打包成返回结果
        ResultVO resultVO=ResultVOUtil.success(orderMasterList);
        return resultVO;
    }
}
