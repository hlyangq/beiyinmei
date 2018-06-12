<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单-评价晒单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
</head>
<body>
<div class="order">
    <div class="order-common">
        <div class="wcommon-list">
            <div class="order-info">
                <div class="list-body-line">
                    <#if order.goods?size!=0>
			         <#list order.goods as good>
	                    <div class="list-item">
	                      <a href="${basePath}/item/${good.goodsId}.html">
	                        <div class="pro-item">
	                            <div class="propic">
	                                <img alt="${good.goodsName}" src="${good.goodsImg}" width="60" height="60" />
	                            </div>
	                            <div class="prodesc">
	                                <h3 class="title">${(good.goodsName)!''}</h3>
	                                <p class="price">¥&nbsp;<span class="num">
	                                <#if good.goodsPrice??>
										${good.goodsPrice?string('0.00')}
									</#if></span></p>
	                            </div>
	                        </div>
	                        </a>
		                        <#if !good.evaluateFlag?? || good.evaluateFlag=='0'>
	                       		 <a class="btn common-go" href="${basePath}/commentgoods-${(order.orderId)!''}-${(good.goodsId)!''}.html">
		                       		 评价
		                          <#else>
		                          	<a class="btn common-go" href="${basePath}/commentgoods-${(order.orderId)!''}-${(good.goodsId)!''}-${(good.commentId)!''}.html">
		                          	已评价
		                        </#if>
	                       		 </a>
	                    </div>
			         </#list>
			        </#if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>