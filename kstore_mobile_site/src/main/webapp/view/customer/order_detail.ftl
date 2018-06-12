<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <meta name="format-detection" content="telephone=no" />
   <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    
</head>
<body>
<#if order??>
<div class="order content-order-detail mb50">
    <div class="order-number">
        <div class="list-item">
            <h3 class="item-head"><label for="">订单号：</label><span>${(order.orderNo)!''}</span>
            <#assign cFlag=0 />	
      		<#list order.goods as good>
	        	<#if good.evaluateFlag== '0'>
	                 <#assign cFlag=cFlag+1 />
	            </#if>
	         </#list>
            <span class="curValue text-them">
            <#if order.orderStatus??>
				<#if order.orderStatus=="0">
                    <#if order.orderLinePay=="0">
                        等待发货
                    <#else>
					   等待付款
                    </#if>
				<#elseif (order.orderStatus=="1" || order.orderStatus=="5" || order.orderStatus=="6") >
					等待发货
				<#elseif order.orderStatus=="2">
					等待收货
				<#elseif (order.orderStatus=="3" && cFlag>0) >
                	等待评价
				<#elseif order.orderStatus=="3">
					交易完成
				<#elseif (order.orderStatus=="4") >
					交易取消
				</#if>
			</#if>
            </span></h3>
        </div>
        <div class="list-item">
            <h3>感谢您的购买，欢迎再次光临！</h3>
        </div>
    </div>
    <div class="receive-info mt10">
        <div class="list-item">
            <h3>
                <span class="name"><i class="user"></i>

                    <#if order.orderExpressType == 1><!-- 表示自提信息取用户收货信息 -->
                        ${(shippingAddress.addressName)!''}
                    <#else>
                        <#if order.shippingPerson??>
                            ${order.shippingPerson}
                        </#if>
                    </#if>
                </span>
				<span class="phoneNum"><i class="phone"></i>
                    <#if order.orderExpressType == 1><!-- 表示自提信息取用户收货信息 -->
                        ${shippingAddress.addressMoblie!''}
                    <#else>
                        <#if order.shippingMobile??>
                            ${order.shippingMobile?default('')}
                        </#if>
                    </#if>
                </span>
            </h3>
            <p class="dress-info"><i class="dress"></i>
              <#if order.orderExpressType == 1><!-- 表示自提信息 -->
                  ${(shippingAddress.province.provinceName)!''}
                  ${(shippingAddress.city.cityName)!''}
                  ${(shippingAddress.district.districtName)!''}
                  ${(shippingAddress.addressDetail)!''}
              <#else>
                  <#if order.shippingProvince??>${order.shippingProvince?default('')}</#if>
                  <#if order.shippingCity??>${order.shippingCity?default('')}</#if>
                  <#if order.shippingCounty??>${order.shippingCounty?default('')}</#if>
                  <#if order.shippingAddress??>${order.shippingAddress?default('')}</#if>
              </#if>

            </p>
            <#--<i class="arrow-right"></i>-->
        </div>
    </div>
    <div class="order-info mt10">
        <div class="list-body-line">
            <#if (order.goods)?size gt 0>
              <#list order.goods as good>
               <#if good_index lt 2>
               <a class="order_good_link"  href="${basePath}/item/${good.goodsId}.html">
	            <div class="list-item order-pro-item">
	                <div class="pro-item">
	                    <div class="propic">
	                        <img width="78" height="78" title="${(good.goodsName)!''}" alt="${(good.goodsName)!''}" src="${(good.goodsImg)!''}" />
	                    </div>
	                    <div class="prodesc">
	                        <h3 class="title"><#if good.isPresent?? && good.isPresent == '1'><span style="color: white;background-color: lightgrey;">&nbsp;赠品&nbsp;</span></#if> ${(good.goodsName)!''}</h3>
                            <p class="spec">
                                <#if good.specVo??>
                                    <#list good.specVo as specVo>
                                        ${specVo.spec.specName}:<span><#if specVo.specValueRemark??&&specVo.specValueRemark!='undefined'>${specVo.specValueRemark}<#else>${specVo.goodsSpecDetail.specDetailName!''} </#if></span>
                                    </#list>
                                </#if>
                            </p>
	                        <p class="price">¥&nbsp;<span class="num"><#if good.goodsPrice??>
												${good.goodsPrice?string('0.00')}
											</#if> </span></p>
	                        <span class="pro-num">×${good.goodsNum}</span>
	                    </div>
	                </div>
	            </div>
	            </a>
	            <#else>
	            <a class="order_good_link"  href="${basePath}/item/${good.goodsId}.html">
	            <div class="list-item order-pro-item" style="display:none">
	                <div class="pro-item">
	                    <div class="propic">
	                        <img width="78" height="78" title="${(good.goodsName)!''}" alt="${(good.goodsName)!''}" src="${(good.goodsImg)!''}" />
	                    </div>
	                    <div class="prodesc">
	                        <h3 class="title"><#if good.isPresent?? && good.isPresent == '1'><span style="color: white;background-color: lightgrey;">&nbsp;赠品&nbsp;</span></#if> ${(good.goodsName)!''}</h3>
	                        <p class="price">¥&nbsp;<span class="num"><#if good.goodsPrice??>
								${good.goodsPrice?string('0.00')}
							</#if> </span></p>
	                        <span class="pro-num">×${good.goodsNum}</span>
	                    </div>
	                </div>
	            </div>
	           </a> 
            </#if>
          </#list>
       </#if>
      </div>
      <#if (order.goods)?size gt 2>
         <div class="list-item see-all">
            — — — — 显示其他${order.goods?size-2}件商品 — — — —
        </div>
      </#if>
    </div>
    <div class="paid-way mt10">
        <div class="list-item">
            <h3 class="item-head">支付方式及发票信息</h3>

            <div class="list-value">
                <p>
                    <label for="">支付方式：</label>
	                <#if order.payId??>
	                      <#if order.payId==2>
								货到付款	                                  	
	                      <#else>
	                         	在线支付
	                      </#if>
	                </#if>
                </p>
               <!-- <ul>
                    <li><label for="">户名：</label>234</li>
                    <li><label for="">开户行：</label>中国银行</li>
                    <li><label for="">账号：</label>6223384773829847857</li>
                    <li><label for="">汇款识别码：</label>123456</li>
                </ul>-->
                <#if order.invoiceType??&&order.invoiceType!='0'>
                 <p><label for="">发票类型：</label>
                   <#if order.invoiceType=='1'>
                                    普通发票
                   <#elseif order.invoiceType=='2'>
                                     增值发票
                  </#if>
                </p>
                <p class="light">
                    <label for="">发票抬头：</label>

                        <#if order.invoiceTitle??>
                             ${order.invoiceTitle?default('')}
                        </#if>
                </p>
               <p class="light">
                   <label for="">发票内容：</label>

                        <#if order.invoiceContent??>
                             ${order.invoiceContent?default('')}
                        </#if>
                </p>
             </#if>
            </div>
        </div>
        <#if order.orderExpressType==1><!-- 表示自提信息 -->
            <div class="list-item">
                <h3 class="item-head">配送信息</h3>
                <div class="list-value">
                    <p><label for="">配送方式：</label>上门自提 </p>
                    <p><label for="">自提点：</label>${(deliveryPoint.name)!''}</p>
                    <p><label for="">自提地址：</label>${(deliveryPoint.address)!''}</p>
                    <p><label for="">联系电话：</label>${(deliveryPoint.telephone)!''}<a href="tel://${(deliveryPoint.telephone)!''}" class="tel"><i class="ion-ios-telephone-outline"></i></a> </p>
                    <p><label for="">联系人：</label>${(deliveryPoint.linkman)!''}</p>
                </div>
            </div>
        </#if>

    </div>
    <div class="all-info mt10">
        <div class="list-item">
            <ul class="price-total">
                <li><label for="">商品金额</label><span class="value">￥${(order.oldPrice)?string('0.00')}</span></li>
                <li><label for="">优惠金额</label><span class="value">-${order.prePrice?string('0.00')}</span></li>
                <li><label for="">使用积分</label><span class="value">-${(order.orderIntegral)!'0'}<#if (order.jfPrice)??>(￥${order.jfPrice})</#if></span></li>
                <li><label for="">配送费用</label><span class="value">￥<#if order.shippingFee??> ${(order.shippingFee)?string('0.00')} <#else>0.00</#if></span></li>
                <li><label for="">总额</label><span class="value text-them">￥<#if order.moneyPaid??> ${(order.moneyPaid)?string('0.00')}<#else>0.00</#if></span></li>
            </ul>
        </div>
    </div>
   <!-- <div class="all-info mt10">
        <div class="application-after-sale">
            <a class="btn btn-full" href="order-aftersale.html"><i></i>申请售后</a>
        </div>
    </div>-->
</div>
</#if>
<#include "../common/smart_menu.ftl">
</body>
<script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <script>
        $(function(){
            $(".bar-bottom a:eq(3)").addClass("selected");
            /* 显示隐藏的商品 */
            $('.see-all').click(function(){
                $(this).prev('.list-body-line').find('.list-item').show();
                $(this).remove();
            });
        });
  </script>
</html>