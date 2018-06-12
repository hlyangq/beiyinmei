<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>商品评价</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
</head>
<body>
<div class="bar-top common">
    <input type="hidden" id="basePath" value="${basePath}"/>
    <input type="hidden" id="productId" value="${productId}"/>
    <a class="bar-item active haopingall">全部<span class="num" id="haopingall"></span></a>
    <a class="bar-item haoping">好评<span class="num" id="haoping"></span></a>
    <a class="bar-item zhongping">中评<span class="num" id="zhongping"></span></a>
    <a class="bar-item chaping">差评<span class="num" id="chaping"></span></a>
    <#--<a class="bar-item">有图<span class="num">2345</span></a>-->
</div>
<div class="content-common">
    <div class="common mt10" id="commentBody">

    </div>
</div>
<script src="${basePath}/js/goods/pro_common.js"></script>

<script>
    $(function(){
        $('.commonpic ul').width($('.commonpic ul li').width()*$('.commonpic ul li').length)

        loadComment(1,4)
    })
</script>
</body>
</html>