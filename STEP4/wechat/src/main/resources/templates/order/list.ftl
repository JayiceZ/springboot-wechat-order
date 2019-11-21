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
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list orderMasterList as orderMaster>
                    <tr>
                        <td>${orderMaster.order_id}</td>
                        <td>${orderMaster.buyer_name}</td>
                        <td>${orderMaster.buyer_phone}</td>
                        <td>${orderMaster.buyer_address}</td>
                        <td>${orderMaster.order_amount}</td>
                        <td>${orderMaster.getOrderStatusEnum().msg}</td>
                        <td>${orderMaster.getPayStatusEnum().msg}</td>
                        <td><a href="/sell/seller/order/detail?orderId=${orderMaster.order_id}">详情</a></td>
                        <td>
                            <#if orderMaster.getOrderStatusEnum().msg=="新订单">
                                <a href="/sell/seller/order/cancel?orderId=${orderMaster.getOrder_id()}">取消</a>
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

    <#-- 弹窗 -->
    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提醒
                    </h4>
                </div>
                <div class="modal-body">
                    您有新的订单
                </div>
                <div class="modal-footer">
                    <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button onclick="location.reload()" type="button" class="btn btn-primary">查看新订单</button>
                </div>
            </div>
        </div>
    </div>
    <#-- 播放音乐 -->
    <audio id="notice" loop="loop">
        <<source src="/sell/mp3/song.mp3" type="audio/mpeg">
    </audio>
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        var websocket = null
        if ('WebSocket' in window){
            websocket=new WebSocket('ws://jay.natapp1.cc/sell/websocket');
        } else {
            alert('该浏览器不支持websocket')
        }
        websocket.onopen=function (event) {
            console.log("建立连接");
        }
        websocket.onclose=function (event) {
            console.log("连接关闭");
        }
        websocket.onmessage=function (event) {
            console.log("收到消息: "+event.data);
            //弹窗提醒，播放音乐
            $('#myModal').modal('show');
            document.getElementById('notice').play();
        }
        websocket.onerror=function () {
            alert("websocket通信发生错误!");
        }
        window.onbeforeunload=function (ev) {
            websocket.onclose();
        }

    </script>

    </body>
</html>

