<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <title>订单-退款/退货</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
     <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
    <script src="${basePath}/js/customer/wxforward.js"></script>
</head>
<body>
<input id="status" value="0" type="hidden">
<input type="hidden" id="type" value="${type!''}">
<input type="hidden" id="basePath" value="${basePath}">
<div class="order content-order-all" id="items">
 <#if (pb.list)?? &&  (pb.list?size!=0)>
        <#list pb.list as backorder>
		    <div class="order-item">
		        <div class="order-number">
		            <div class="list-item">
		                <h3 class="item-head"><label for="">退单号：</label><span>${(backorder.backOrderCode)!''}</span>
		                    <span class="curValue text-them">
		                      <#if backorder.backCheck??>
	                                <#if backorder.backCheck=="0">
	                                   	 退货审核
	                                <#elseif backorder.backCheck=="1">
	                                                                         同意退货
	                                <#elseif backorder.backCheck=="2">
	                                  	  拒绝退货
	                                <#elseif backorder.backCheck=="3">
	                                   	 待商家收货
	                                <#elseif backorder.backCheck=="4">
	                                    	退单结束
	                                <#elseif backorder.backCheck=="6">
	                                    	退款审核
	                                <#elseif backorder.backCheck=="7">
	                                    	拒绝退款
	                                <#elseif backorder.backCheck=="8">
	                                   	 拒绝收货
	                                <#elseif backorder.backCheck=="9">
	                                    	待填写物流信息
	                                <#elseif backorder.backCheck=="10">
	                                  	 退款成功
	                                </#if>
	                            </#if>
		                    </span></h3>
		            </div>
		        </div>
		        <div class="order-info">
		            <#if backorder.orderGoodslistVo?? && backorder.orderGoodslistVo?size gt 2>
		                <div class="list-body-box">
			             <div class="list-item">
		                    <div class="box-body">
		                        <ul>
		             			  <#list backorder.orderGoodslistVo as backgoods>
		                            <li>
		                            <a  title="${backgoods.goodsInfoName!''}" href="${basePath}/item/${backgoods.goodsInfoId}.html">
                              	    <img width="78" height="78" title="${(backgoods.goodsInfoName)!''}" alt="${(backgoods.goodsInfoName)!''}" src="${(backgoods.goodsInfoImgId)!''}" />
		                            </a>
		                            </li>
	                            </#list>
		                        </ul>
		                    </div>
		                   </div>
                        <#else>
		            	<div class="list-body-line">
                            <#list backorder.orderGoodslistVo as backgoods>
                                <a title="${backgoods.goodsInfoName!''}" href="${basePath}/item/${backgoods.goodsInfoId}.html">
					                <div class="list-item">
					                    <div class="pro-item">
					                        <div class="propic">
	                              			  <img width="78" height="78" title="${(backgoods.goodsInfoName)!''}" alt="${(backgoods.goodsInfoName)!''}" src="${(backgoods.goodsInfoImgId)!''}" />
					                        </div>
					                        <div class="prodesc">
					                            <h3 class="title">${(backgoods.goodsInfoName)!''}</h3>
					
					                            <p class="price">
													<#--
													¥&nbsp;
													<span class="num">
					                                <#if (backgoods.goodsInfoPreferPrice)??>
                                                        ${backgoods.goodsInfoPreferPrice?string('0.00')}
                                                    </#if>
													</span>
													-->
												</p>
					                            <span class="pro-num">×${(backorder.backNum)!'0'}</span>
					                        </div>
					                    </div>
					                </div>
				                </a>
                            </#list>
                        </#if>
		            </div>
		        </div>
		        <div class="order-bottom">
		            <div class="list-item">
		                <h3 class="item-head">
		                    <label for="">退款金额：</label><span class="pay-money">￥ 
		                    <#if backorder.backPrice??>
                                ${backorder.backPrice?string('0.00')}
                            </#if></span>
		                </h3>
		                 <div class="too-btn">
		                <#if (backorder.backCheck)??>
	                       <#if backorder.backCheck=="9">
	                          <a class="btn fill-flow" href="javascript:void(0)" onclick="expressInfo('${(backorder.orderCode)!''}')" style="width:100px">填写物流信息</a>
		                  </#if>
		               </#if>
		                 <#if (backorder.isBack)??>
		                  <#if backorder.isBack=="2">
	                            <a class="btn" href="${basePath}/customer/backorderpricedetail<#if backorder?? && backorder.order??>-${backorder.order.orderId!''}</#if>.html">退款详情</a>
	                        <#else>
	                            <a class="btn" href="${basePath}/customer/backordergoodsdetail<#if backorder?? && backorder.order??>-${backorder.order.orderId!''}</#if>.html">退货详情</a>
	                      </#if>
		                </#if>
							 <!-- 再次买 -->
                             <a href="javascript:void(0)" class="btn" onclick="addCar('${(backorder.order.orderId)!''}')">再次购买</a>
		                </div>
		            </div>
		        </div>
		    </div>
       </#list>
      <#else>
	 <div class="no-orders">
        <div class="list-item">
            <i class="dingdan"></i>
            <h3>您还没有相关的订单</h3>
            <p>再去逛逛吧~</p>
        </div>
        <div class="list-item">
            <a class="btn btn-full" href="${basePath}/main.html">去逛逛</a>
        </div>
    </div>
  </#if>       
</div>
	<div class="list-loading" id="showmore"  style="display:none" >
        <img alt="" src="${basePath}/images/loading.gif">
        <span>加载中……</span>
    </div>
</body>

<form class="fill-flow-form" style="display:none;" id="fillForm">
<input type="hidden" id="orderNo" name="orderNo" value=""/>
    <div class="list-item">
        <h3 class="item-head pr0"><label for="">物流公司</label>
        </h3>
        <div class="list-value">
            <div class="shuoming">
                <input type="text" class="text" placeholder="请填写正确的物流公司" name="wlname" id="wlname" onBlur="wuliuname()">
                <label id="yanzhengname"></label>
            </div>
        </div>
    </div>
    <div class="list-item">
        <h3 class="item-head pr0"><label for="">物流单号</label>
        </h3>
        <div class="list-value">
            <div class="shuoming">
                <input type="text" class="text" placeholder="请填写准确的物流单号"  name="wlno" id="wlno" onBlur="wuliudanhao()">
                 <label id="yanzhengno"></label>
            </div>
        </div>
    </div>
</form>

 <script src="${basePath}/js/customer/backorder.js"></script>
 <script>
        $(function(){
            $('.content-order-all .list-body-box ul').each(function () {
                var $li = $(this).find('li');
                $(this).width($li[0].offsetWidth*$li.length+($li.length-1)*10)
            })
        });
	  $(window).scroll(function(){
		    if($(this).scrollTop() >= ($('body').height() - $(window).height())){
		     show();
		    }
	    });
	    
 </script>
</html>