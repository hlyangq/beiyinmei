<!DOCTYPE html>
<html>
<head lang="zh-cn">
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>充值成功</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css">
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
</head>
<body>

<div class="container">

    <div class="success_info">
        <div class="success_icon"></div>
        <p>恭喜您，充值成功！您可以<a href="${basePath}/customer/customerTradeInfo.html">查看交易明细</a></p>
    </div>

<#--<div class="relate_orders">-->
<#--<#list orderList as order>-->
<#--<div class="relate_order">-->
<#--<h4>订单号：${order.orderCode!''}</h4>-->
<#--<#list order.orderGoodsList as goods>-->
<#--<div class="good">-->
<#--<a href="#">-->
<#--<img class="img" alt="" src="${goods.goodsImg}">-->
<#--<p class="name">${goods.goodsInfoName}</p>-->
<#--</a>-->
<#--</div>-->
<#--</#list>-->
<#--</div>-->
<#--</#list>-->

<#--&lt;#&ndash;<div class="relate_order">&ndash;&gt;-->
<#--&lt;#&ndash;<h4>订单号：T2015090812349078</h4>&ndash;&gt;-->
<#--&lt;#&ndash;<div class="good">&ndash;&gt;-->
<#--&lt;#&ndash;<a href="#">&ndash;&gt;-->
<#--&lt;#&ndash;<img class="img" alt="" src="images/good_img.jpg">&ndash;&gt;-->
<#--&lt;#&ndash;<p class="name">米莱珠宝0.7克拉红宝石戒指18K金镶嵌10分……</p>&ndash;&gt;-->
<#--&lt;#&ndash;</a>&ndash;&gt;-->
<#--&lt;#&ndash;</div>&ndash;&gt;-->
<#--&lt;#&ndash;</div>&ndash;&gt;-->
<#--<div class="pay_total">-->
<#--<p>实付款：￥120.00</p>-->
<#--</div>-->
<#--</div>-->

</div>


</body>
</html>