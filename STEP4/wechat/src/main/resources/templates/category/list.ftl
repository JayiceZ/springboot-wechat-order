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
                            <th>类目id</th>
                            <th>名字</th>
                            <th>Type</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                    <#list productCategoryList as productCategory>
                    <tr>
                        <td>${productCategory.category_id}</td>
                        <td>${productCategory.category_name}</td>
                        <td>${productCategory.category_type}</td>
                        <td><a href="/sell/seller/category/index?categoryId=${productCategory.category_id}">修改</a></td>
                    </tr>
                    </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

