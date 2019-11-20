<html>
    <#include "../common/header.ftl">
    <body>
    <div id="wrapper" class="toggled">
    <#-- 边栏 -->
        <#include "../common/nav.ftl">

    <#-- 主要代码区域 -->
        <div id="page-content-wrapper">
            <#-- 主要内容 -->
            <div class="container">
                <div class="row clearfix">

                <#-- 订单 -->
                    <div class="col-md-4 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>订单id</th>
                                <th>订单总金额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${orderMaster.order_id}</td>
                                <td>${orderMaster.order_amount}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                <#-- 订单详情 -->
                    <div class="col-md-12 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>商品id</th>
                                <th>商品名称</th>
                                <th>价格</th>
                                <th>数量</th>
                                <th>总额</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list orderMaster.orderDetailList as orderDetail>
                    <tr>
                        <td>${orderDetail.detail_id}</td>
                        <td>${orderDetail.product_name}</td>
                        <td>${orderDetail.product_price}</td>
                        <td>${orderDetail.product_quantity}</td>
                        <td>${orderDetail.product_price * orderDetail.product_quantity}</td>
                    </tr>
                    </#list>
                            </tbody>
                        </table>
                    </div>

                    <div class="col-md-12 column">
                <#if orderMaster.getOrderStatusEnum().msg == "新订单">
                    <a href="/sell/seller/order/finish?orderId=${orderMaster.order_id}" type="button" class="btn btn-default btn-info">完结订单</a>
                    <a href="/sell/seller/order/cancel?orderId=${orderMaster.order_id}" type="button" class="btn btn-default btn-danger">取消订单</a>
                </#if>
                    </div>


                </div>
            </div>
        </div>
    </div>
    </body>
</html>