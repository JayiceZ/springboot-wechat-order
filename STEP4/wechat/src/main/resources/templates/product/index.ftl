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
                    <form role="form" method="post" action="/sell/seller/product/update">
                        <div class="form-group">
                            <label>名称</label><input name="product_name" type="text" class="form-control" value="${productInfo.product_name}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label><input name="product_price" type="text" class="form-control" value="${productInfo.product_price}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label><input name="product_stock" type="text" class="form-control" value="${productInfo.product_stock}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label><input name="product_description" type="text" class="form-control" value="${productInfo.product_description}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img height="100"  width="100" src="${productInfo.product_icon}" alt="">
                            <input name="product_icon" type="text" class="form-control" value="${productInfo.product_icon}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="category_type" class="form-control">
                                <#list productCategoryList as category>
                                    <option value="${category.category_type}"
                                            <#if (productInfo.category_type)??&&productInfo.category_type==category.category_type>
                                                selected
                                            </#if>
                                        >${category.category_name}</option>
                                </#list>
                            </select>
                        </div>
                    <input hidden type="text" name="product_id" value="${productInfo.product_id}">
                        <button type="submit" class="btn btn-default">修改</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

