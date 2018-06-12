<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>会员中心 - 我的积分</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta name="keywords" content="${(seo.meteKey)!''}">
  <meta name="description" content="${(seo.meteDes)!''}">
  <meta content="telephone=no" name="format-detection">
  <#assign basePath=request.contextPath>
  <link rel="stylesheet" type="text/css" href="${basePath}/css/style.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
  <script src="${basePath}/js/idangerous.swiper.js"></script>
</head>
<body>

<div class="my_balance member_box">
  <div class="cover">
    <h2>${(customer.infoPointSum)!'0'}</h2>
    <p>当前积分</p>
  </div>
</div>

<div class="income_details member_box">
  <h2>收支明细</h2>
  <div class="tabs row">
    <!--<a href="${basePath}/customer/myintegral.html" <#if !type??>class="active"</#if>>全部</a>
    <a href="#" <#if type?? && type=='1'>class="active"</#if>>收入</a>
    <a href="#" <#if type?? && type=='0'>class="active"</#if>>支出</a>-->
    <a href="#" data-val='' class="active">全部</a>
    <a href="#" data-val='1'>收入</a>
    <a href="#" data-val='0'>支出</a>
  </div>
  <!--<div class="swiper-container">
    <div class="swiper-wrapper">
      <div class="swiper-slide">-->
        <div class="content-slide">
          <div class="income_list" id="items">
           <#if (pb.list?size!=0)>
              <#list pb.list as point>
                <#if point.point!=0 && point.point??>
                 <div class="income_item">
		              <dl>
		                <dt><span>${(point.pointDetail)!''}</span></dt>
		                <dd><span class="light">${point.createTime?string("yyyy-MM-dd HH:mm:ss")}</span></dd>
		              </dl>
		              <dl>
		                <!--<dt><span class="light">余额：${(customer.infoPointSum)!'0'}</span></dt>-->
		                <dd>
		                <span class="red">
		                 <#if point.pointType??>
                            <#if point.pointType=='1'>
									+${point.point}
                            <#else>
									-${point.point}
                            </#if>
                        </#if>
		                </span></dd>
		              </dl>
           		 </div>
                </#if>
               </#list>
            </#if>
          </div>
        </div>
	    <div class="list-loading" style="display:none" id="showmore">
	        <img alt="" src="${basePath}/images/loading.gif">
	        <span>加载中……</span>
	    </div>
	    <#if !pb?? ||  (pb.list?size=0)>
	    <div class="no_tips" id="no">
	     <p>木有积分哦</p>
	    </div>
	   </#if>
      </div>
     
 
  <input id="type" type="hidden" value="${(type)!''}" /> 
  <input id="basePath" type="hidden" value="${basePath}" />
 <input id="status" value="0" type="hidden">
<#include '/common/smart_menu.ftl' />

<script>
    $(".bar-bottom  a").removeClass("selected");
    $(".bar-bottom:eq(0) a:eq(3) ").addClass("selected");
  $(".tabs a").on('touchstart mousedown',function(e){
    e.preventDefault();
    $(".tabs .active").removeClass('active');
    $(this).addClass('active');
    $(this).attr("data-val");
    $("#type").val($(this).attr("data-val"))
    showType();
  });
  $(".tabs a").click(function(e){
    e.preventDefault();
  })

</script>
</body>
<script src="${basePath}/js/customer/integral.js"></script>
<script src="${basePath}/js/dataformat.js"></script>
</html>