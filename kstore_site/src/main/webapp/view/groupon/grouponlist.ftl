<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<#assign basePath=request.contextPath>
<title>${topmap.systembase.bsetName}</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/style.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/index.css" />
<#if (topmap.systembase.bsetHotline)??>
	<link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
	<link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
<style>
    .new_tlist li{ width: 353px;float: left; background: #fff; border:1px solid #fff; padding: 9px; margin: 20px 0px 0px 20px; height: 537px;}
    .new_tlist li:hover{ border: 1px solid #dddddd; box-shadow:0 2px 5px #ddd; -moz-box-shadow:0 2px 5px #ddd; -webkit-box-shadow:0 2px 5px #ddd;*height:537px;}
    .new_tlist li .name{ font-family: "微软雅黑"; font-size: 14px; color: #666666; line-height: 25px; height: 50px; overflow: hidden;}
    .new_tlist li .price{ font-size: "Arial"; font-size: 24px; color: #f0375e; display: inline;}
    .new_tlist li .price i{ font-size: 16px; font-style: normal;}
    .new_tlist li .price span{ color: #999; font-family: "微软雅黑"; font-size: 14px; margin-left: 10px; text-decoration: line-through;}
    .col9{ color: #999;}
	.pss_court_d{ margin-top: 2px; margin-right: 10px;}
	.pss_count_down{ font-size: 18px; font-family: "Verdana"; line-height: 25px;}
    .paging a:hover{background:#df1738;border-color:#df1738;}
    .paging {margin-right:10px;}
</style>
</head>
<body>
	<#if (topmap.temp)??>
			<#if (topmap.temp.tempId==1)>
				<#include "../index/topnew.ftl">
			<#elseif (topmap.temp.tempId==3)>
				<#include "../index/newheader.ftl">
			<#elseif (topmap.temp.tempId==9)>
				<#include "../index/newheader4.ftl">
			<#elseif (topmap.temp.tempId==10)>
				<#include "../index/newheader5.ftl">
			<#elseif (topmap.temp.tempId==11)>
				<#include "../index/newheader6.ftl">
			<#elseif (topmap.temp.tempId==12)>
				<#include "../index/newheader7.ftl">
			<#elseif (topmap.temp.tempId==13)>
				<#include "../index/newheader8s.ftl">
			<#elseif (topmap.temp.tempId==14)>
				<#include "../index/newheader9.ftl">
			<#elseif (topmap.temp.tempId==15)>
				<#include "../index/newheader10.ftl">
			<#elseif (topmap.temp.tempId==16)>
				<#include "../index/newheader11.ftl">
			<#elseif (topmap.temp.tempId==17)>
				<#include "../index/newheader12.ftl">
			<#elseif (topmap.temp.tempId==18)>
				<#include "../index/newheader13.ftl">
			<#elseif (topmap.temp.tempId==19)>
				<#include "../index/newheader14.ftl">
            <#elseif (topmap.temp.tempId==20)>
                <#include "../index/newheader15.ftl">
			<#elseif (topmap.temp.tempId==21)>
				<#include "../index/newheader21.ftl">
			<#else>
				<#include "../index/newheader3.ftl">
			</#if>
	 </#if>

		
	<#if avs?? && (avs?size>0)>
		<#list avs as bigAdvert>
            <div style="text-align:center;"><a href="${bigAdvert.adverHref}"><img alt="" src="${bigAdvert.adverPath}" /></a></div>
       </#list>
     </#if>
     	<div class="container">
		<div style="background:#f7f7f7;">
			<div class="pss_tuan_title pt10 pl20">精品团购</div>
				<ul class="clearfix new_tlist">
					<#list pb.list as groupons> 
						<li>
							<a href="${basePath}/item/${groupons.goodsProductVo.goodsInfoId}.html"><img src="<#if groupons.goodsProductVo.imageList?? && groupons.goodsProductVo.imageList[0]??>${groupons.goodsProductVo.imageList[0].imageBigName!''}</#if>" width="353" height="353"/></a>
							<p class="name"><a href="${basePath}/item/${groupons.goodsProductVo.goodsInfoId}.html">${groupons.goodsProductVo.productName}</a></p>
							<div class="pt10 clearfix">
								<div class="pss_court_d fl">
									<#if groupons??&&groupons.marketing??&&groupons.marketing.groupon??&&groupons.marketing.groupon.grouponDiscount??>${groupons.marketing.groupon.grouponDiscount*10}<#else>0</#if>
								 	折
								</div>
								<p class="price"><i>¥</i>${(groupons.goodsProductVo.goodsInfoPreferPrice*groupons.marketing.groupon.grouponDiscount)!""}<span>${(groupons.goodsProductVo.goodsInfoPreferPrice?string("0.00"))!""}</span></p>
								<#--<span class="col9 pl10">销量：<#if  groupons??&&groupons.marketing??&&groupons.marketing.groupon??&&groupons.marketing.groupon.participateCount??>${groupons.marketing.groupon.participateCount}<#else>0</#if></span>-->
							</div>
							<a href="${basePath}/item/${groupons.goodsProductVo.goodsInfoId}.html" class="pss_hot_buy" >立即参团</a>
							<input type="hidden" class="alltimes" idattr="${groupons_index+1}"  value="${groupons.marketing.marketingEnd?string("yyyy-MM-dd HH:mm:ss")}" id="time${groupons_index+1}">
							<div class="pss_count_down mt20">
		                       <div id="count${groupons_index+1}">
	                                    <span class="day"></span>
	                                    <span class="hour"></span>&nbsp;时&nbsp;
	                                    <span class="minute"></span>&nbsp;分&nbsp;
	                                    <span class="second"></span>秒
	                            </div>
	                    	</div>
						</li>
					</#list>
				</ul>
			 <div class="pages mt10 tr">
           		  <#if (pb.list?size!=0)>
                        <#-- 分页 -->
                        <#import "/pagination/pageBean.ftl" as page>
                        <@page.pagination pb />
                  </#if>
              </div>
		</div>
	<!--这是底部-->
	<#if (topmap.temp)??>
	<#if (topmap.temp.tempId==1)>
		<#include "../index/bottom.ftl">
	<#else>
	    <#include "../index/newbottom.ftl" />
	</#if>
	</#if>
	</div>
	
  <script type="text/javascript" src="${basePath}/js/jquery.lazyload.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/cloud-zoom.1.0.2.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/group.js"></script>	
</body>
</html>