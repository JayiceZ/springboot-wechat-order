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
                    <form role="form" method="post" action="/sell/seller/category/update">
                        <div class="form-group">
                            <label>名字</label><input name="category_name" type="text" class="form-control" value="${productCategory.category_name}"/>
                        </div>
                        <input hidden type="number" name="category_id" value="${productCategory.category_id}">
                        <button type="submit" class="btn btn-default">修改</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

