<html>
   <#include "../common/header.ftl">
    <body>
    <div id="wrapper" class="toggled">
        <#-- 边栏 -->
        <#include "../common/nav.ftl">

        <#-- 主要代码区域 -->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>商品id</th>
                                <th>名称</th>
                                <th>图片</th>
                                <th>单价</th>
                                <th>库存</th>
                                <th>状态</th>
                                <th>描述</th>
                                <th>类目</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list productInfoList as productInfo>
                    <tr>
                        <td>${productInfo.product_id}</td>
                        <td>${productInfo.product_name}</td>
                        <td><img height="100" width="100" src="${productInfo.product_icon}" alt=""></td>
                        <td>${productInfo.product_price}</td>
                        <td>${productInfo.product_stock}</td>
                        <td>${productInfo.getProductStatusEnum().message}</td>
                        <td>${productInfo.product_description}</td>
                        <td>${productInfo.category_type}</td>
                        <td><a href="/sell/seller/product/index?productId=${productInfo.product_id}">修改</a></td>
                        <td>
                            <<#if productInfo.getProductStatusEnum().message=="在架">
                                <a href="/sell/seller/product/off_sale?productId=${productInfo.product_id}">下架</a>
                            <#else>
                                <a href="/sell/seller/product/on_sale?productId=${productInfo.product_id}">上架</a>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                            </tbody>
                        </table>
                    </div>

                    <div class="col-md-12 column">
                        <ul class="pagination pull-left">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                    </#if>
                    <#list 1..pages as index>
                        <#if currentPage==index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                    <#if currentPage gte pages>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                    </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

