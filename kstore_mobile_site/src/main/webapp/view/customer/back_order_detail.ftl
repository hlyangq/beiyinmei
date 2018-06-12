<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单-退款/退货详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
</head>
<body>
<div class="order content-order-return">
    <div class="order-number">
        <div class="list-item">
            <h3 class="item-head text-them">
                <#if bOrder.backCheck??>
                    <#if bOrder.backCheck=="0">
                       	 退货审核
                    <#elseif bOrder.backCheck=="1">
                         同意退货
                    <#elseif bOrder.backCheck=="2">
                      	  拒绝退货
                    <#elseif bOrder.backCheck=="3">
                       	 待商家收货
                    <#elseif bOrder.backCheck=="4">
                        	退单结束
                    <#elseif bOrder.backCheck=="6">
                        	退款审核
                    <#elseif bOrder.backCheck=="7">
                        	拒绝退款
                    <#elseif bOrder.backCheck=="8">
                       	 拒绝收货
                    <#elseif bOrder.backCheck=="9">
                        	待填写物流地址
                    <#elseif bOrder.backCheck=="10">
                      	 退款成功
                    </#if>
                </#if>
            </h3>

            <h3 class="item-head"><label for="">订单号：</label><span>${(backorder.orderCode)!''}</span></h3>

            <h3 class="item-head"><label for="">退单号：</label><span>${(bOrder.backOrderCode)!''}</span></h3>
        </div>
    </div>
    <div class="mt10">
        <div class="order-info">
            <div class="list-body-line">
                <#if backorder.orderGoodsList?? && backorder.orderGoodsList?size gt 0>
                    <#list backorder.orderGoodsList as list>
	                    <#if list_index lt 2>
	                        <a title="${list.goodsInfoName!''}" href="${basePath}/item/${list.goodsInfoId}.html">
	                        <div class="list-item">
			                    <div class="pro-item">
			                        <div class="propic">
			                            <img alt="" src="${list.goodsImg}" style="width:78px;height:78px;"/>
			                        </div>
			                        <div class="prodesc">
			                            <h3 class="title"><#if list.isPresent?? && list.isPresent == '1'><span style="color: white;background-color: lightgrey;">&nbsp;赠品&nbsp;</span></#if> ${(list.goodsInfoName)!''}</h3>
			
			                            <p class="price">¥&nbsp;<span class="num">${(list.goodsInfoPrice)?string('0.00')}</span></p>
			                            <span class="pro-num">×${list.goodsInfoNum}</span>
			                        </div>
			                    </div>
	                	  </div>
	                	  </a>
	                	  <#else>
	                        <a title="${list.goodsInfoName!''}" href="${basePath}/item/${list.goodsInfoId}.html">
	                        <div class="list-item" style="display:none">
			                    <div class="pro-item">
			                        <div class="propic">
			                            <img alt="" src="${list.goodsImg}" style="width:78px;height:78px;"/>
			                        </div>
			                        <div class="prodesc">
			                            <h3 class="title"><#if list.isPresent?? && list.isPresent == '1'><span style="color: white;background-color: lightgrey;">&nbsp;赠品&nbsp;</span></#if> ${(list.goodsInfoName)!''}</h3>
			
			                            <p class="price">¥&nbsp;<span class="num">${(list.goodsInfoPrice)?string('0.00')}</span></p>
			                            <span class="pro-num">×${list.goodsInfoNum}</span>
			                        </div>
			                    </div>
	                	  </div>
	                	  </a>
	                	 </#if>
                    </#list>
                </#if>
            </div>
            <#if (bOrder.orderGoodslistVo)?size gt 2>
	         <div class="list-item see-all">
	            — — — — 显示其他${bOrder.orderGoodslistVo?size-2}件商品 — — — —
	        </div>
     	 </#if>
        </div>
    </div>
    <div class="mt10">
        <div class="list-item">
            <ul class="tep-strip">
               <#if backOrderLogs??>
	             <#list backOrderLogs as  backOrderLog>
	                <#if backOrderLog.backLogStatus??>
	                    <li>
	                    <div class="body">
	                         <span class="vertical-line"></span>
	                         <span class="point"><b><i></i></b></span>
	                    <#if backOrderLog.backLogStatus == '9'>
	                        <h4>拒绝退款(操作：平台)<#if backOrderLog.backRemark?? && backOrderLog.backRemark !=''><#if backOrderLog.backRemark?length gt 3>留言：${backOrderLog.backRemark?substring(0,2)}...<#else >留言：${backOrderLog.backRemark}</#if></#if></h4>
	                    </#if>
	                    <#if backOrderLog.backLogStatus == '7' || backOrderLog.backLogStatus == '8'>
	                        <h4>退款${backorder.backPrice!''}元成功 (操作：平台)<#if backOrderLog.backRemark?? && backOrderLog.backRemark !=''><#if backOrderLog.backRemark?length gt 3>留言：${backOrderLog.backRemark?substring(0,2)}...<#else >留言：${backOrderLog.backRemark}</#if></#if></h4>
	                    </#if>
	                    <#if backOrderLog.backLogStatus == '6'>
	                        <h4>收货失败 (操作：平台)<#if backOrderLog.backRemark?? && backOrderLog.backRemark !=''><#if backOrderLog.backRemark?length gt 3>留言：${backOrderLog.backRemark?substring(0,2)}...<#else >留言：${backOrderLog.backRemark}</#if></#if></h4>
	                    </#if>
	                    <#if backOrderLog.backLogStatus == '5'>
	                        <h4>确认收货(操作：平台)<#if backOrderLog.backRemark?? && backOrderLog.backRemark !=''><#if backOrderLog.backRemark?length gt 3>留言：${backOrderLog.backRemark?substring(0,2)}...<#else >留言：${backOrderLog.backRemark}</#if></#if></h4>
	                    </#if>
	                    <#if backOrderLog.backLogStatus == '4'>
	                        <h4>填写快递信息(操作：顾客)<#if backOrderLog.backRemark?? && backOrderLog.backRemark !=''><#if backOrderLog.backRemark?length gt 3>留言：${backOrderLog.backRemark?substring(0,2)}...<#else >留言：${backOrderLog.backRemark}</#if></#if></h4>
	                    </#if>
	                    <#if backOrderLog.backLogStatus == '3'>
	                        <h4>申请审核不通过(操作：平台)<#if backOrderLog.backRemark?? && backOrderLog.backRemark !=''><#if backOrderLog.backRemark?length gt 3>留言：${backOrderLog.backRemark?substring(0,2)}...<#else >留言：${backOrderLog.backRemark}</#if></#if></h4>
	                    </#if>
	                    <#if backOrderLog.backLogStatus == '2'>
	                        <h4>申请审核通过(操作：平台)<#if backOrderLog.backRemark?? && backOrderLog.backRemark !=''><#if backOrderLog.backRemark?length gt 3>留言：${backOrderLog.backRemark?substring(0,2)}...<#else >留言：${backOrderLog.backRemark}</#if></#if></h4>
	                    </#if>
	                    <#if backOrderLog.backLogStatus == '1'>
	                        <h4>申请退单审核(操作：顾客)<#if backOrderLog.backRemark?? && backOrderLog.backRemark !=''><#if backOrderLog.backRemark?length gt 3>留言：${backOrderLog.backRemark?substring(0,2)}...<#else >留言：${backOrderLog.backRemark}</#if></#if></h4>
	                    </#if>
	                        <p>${backOrderLog.backLogTime?string("yyyy-MM-dd HH:mm:ss")}</p>
	                    </div>
	                   </li>
	                </#if>
	            </#list>
	           </#if>
            </ul>
        </div>
    </div>
    <div class="all-info mt10">
        <div class="list-item">
            <ul class="price-total">
                <li><label for="">退款金额</label><span class="value text-them">￥ ${(bOrder.backPrice)?string('0.00')}</span></li>
                <#if bOrder.backCheck=='4'>
                <#if backorder.backPrice??>
                    <li><label for="">实退金额</label><span class="value text-them">￥ ${(backorder.backPrice)?string('0.00')}</span></li>
                </#if>
                </#if>
                <li><label for=""><#if bOrder.isBack=='1'>退货原因<#else>退款原因</#if></label>
                	<span class="value">
                	 <#if bOrder.backReason??>
                        <#if bOrder.backReason=='1'>不想买了</#if>
                         <#if bOrder.backReason=='2'>
                             <#if bOrder.isBack=='2'>收货人信息有误
                             <#else>商品质量问题
                             </#if>
                         </#if>
                         <#if bOrder.backReason=='3'>
                             <#if bOrder.isBack=='2'>未按指定时间发货
                             <#else>收到商品与描述不符
                             </#if>
                         </#if>
                         <#if bOrder.backReason=='4'>其他</#if>
                    </#if>
                	</span>
                </li>
                <li style="height:auto"><label for="">问题说明</label><span class="value" style="text-indent:2em">${(bOrder.backRemark)!''}</span></li>
                <#if bOrder.isBack=='1'>
                <li><label for="">返回方式</label><span class="value">快递</span></li>
                <li><label for="">申请凭据</label>
	                <span class="value">
	                 <#if bOrder.applyCredentials??>
                        <#if bOrder.applyCredentials=='3'>
                           	 没有任何凭证
                        <#elseif bOrder.applyCredentials=='1'>
                            	有发票
                        <#elseif bOrder.applyCredentials=='2'>
                            	有质检报告
                        <#else>
                            	有发票、有质检报告
                        </#if>
                    </#if>
	                </span>
                </li>
             </#if>
                <li>
                    <label for="">物流公司</label>
                    <span class="value" style="text-indent:2em">
                        <#if general??>
                            <#if general.ogisticsName??>
                            ${general.ogisticsName!''}
                            <#else>
                                无
                            </#if>
                        <#else>
                            无
                        </#if>
                    </span>
                </li>
                <li>
                    <label for="">物流单号</label>
                    <span class="value" style="text-indent:2em">
                        <#if general??>
                            <#if general.ogisticsNo??>
                            ${general.ogisticsNo}
                            <#else>
                                无
                            </#if>
                        <#else>
                            无
                        </#if>
                    </span>
                </li>
            </ul>
        </div>
    </div>
    <#if backOrderLogs??>
	    <div class="mt10">
	        <div class="list-item">
	            <h3 class="item-head">卖家留言</h3>
	            <ul class="message">
	                <#assign notice = 0>
		             <#list backOrderLogs as  backOrderLog>
		                <#if (backOrderLog.backRemark)?? && backOrderLog.backRemark !=''>
	                <#assign notice = 1>
		                <li>
                            ${(backOrderLog.backRemark)!''}
		                    <p class="time">${backOrderLog.backLogTime?string("yyyy-MM-dd HH:mm:ss")}</p>
		                </li>
		                </#if>
	                </#list>
	                <#if notice ==0>
	                   <li> 暂无留言<li>
	               </#if>
	            </ul>
	        </div>
	    </div>
    </#if>
    <#assign showflag =0 />
<#if imglist?? && imglist?size gt 0>
    <#list imglist as imgs>
        <#if imgs?? && imgs !=''>
            <#assign showflag =1 />
        </#if>
    </#list>
</#if>
<#if showflag==1>
<div class="mt10">
    <div class="list-item">
        <div class="recommend-list">
              <ul>
                <#list imglist as imgs>
                    <#if imgs?? && imgs !=''>
                    <li><img src="${imgs}" alt="" style="width:70px;height:70px;"/></li>
                    </#if>
                </#list>
                </ul>
        </div>
    </div>
</div>
</#if>
    <!-- <div class="cancel">
         <div class="list-item">
             <a class="btn btn-full" href="javascript:;"><i></i>取消申请</a>
         </div>
     </div>-->
</div>
</body>
<script>
    $(function(){
        $('.recommend-list ul').width($('.recommend-list ul li').width()*$('.recommend-list ul li').length+($('.recommend-list ul li').length-1)*10);
      /* 显示隐藏的商品 */
            $('.see-all').click(function(){
                $(this).prev('.list-body-line').find('.list-item').show();
                $(this).remove();
            });
    })
</script>
</html>