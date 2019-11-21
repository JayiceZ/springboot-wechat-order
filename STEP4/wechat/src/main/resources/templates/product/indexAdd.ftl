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
                    <form role="form" method="post" action="/sell/seller/product/addProduct">
                        <div class="form-group">
                            <label>名称</label><input name="product_name" type="text" class="form-control" value=""/>
                        </div>
                        <div class="form-group">
                            <label>价格</label><input name="product_price" type="number" class="form-control" value=""/>
                        </div>
                        <div class="form-group">
                            <label>库存</label><input name="product_stock" type="number" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label><input name="product_description" type="text" class="form-control" value=""/>
                        </div>
                        <div class="form-group">
                            <label>图片地址</label>
                            <input name="product_icon" type="text" class="form-control" value=""/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="category_type" class="form-control">
                                <#list productCategoryList as category>
                                    <option value="${category.category_type}">${category.category_name}</option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-default">添加</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

