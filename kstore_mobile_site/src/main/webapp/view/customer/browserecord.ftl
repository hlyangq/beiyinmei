<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>会员中心 - 浏览历史</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta name="keywords" content="${(seo.meteKey)!''}">
  <meta name="description" content="${(seo.meteDes)!''}">
  <meta content="telephone=no" name="format-detection">
    <#assign basePath=request.contextPath>
  <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
</head>
<body>
<input id="status" value="0" type="hidden">
 <input id="basePath" type="hidden" value="${basePath}" /> 
<div class="content" style="padding-top:0;">
  <div class="prolist list-line" id="items">
    <#if (pb.list?size!=0)>
     <#list pb.list as browse>
       <a href="${basePath}/item/${browse.goodsId}.html">
	    <div class="list-item<#if browse.goods.goodStock?? ><#if (browse.goods.goodStock>0)><#else> sell-out</#if></#if>" id="browere${browse.likeId}">
	      <div class="propic">
	        <img width="100" height="100" alt="<#if browse.goods.goodsName??> ${browse.goods.goodsName}</#if>" 
	        		src="<#if browse.goods.goodsImg??> ${browse.goods.goodsImg}</#if>" />
	      </div>
	      <div class="prodesc">
	        <h3 class="title">
	        <#if (browse.goods.goodsName)?length gt 50> 
	          ${(browse.goods.goodsName)?substring(0,50)}
	          <#else> ${browse.goods.goodsName}
	        </#if>    
	       </h3>
	        <p class="price">&yen;&nbsp;<span>${browse.goods.goodsPrice?string('0.00')}</span></p>
	      </div>
	    </div>
	    </a>
     </#list>
     <#else>
		<div class="content">
		  <div class="no_tips">
		    <p>木有浏览记录哦</p>
		  </div>
		</div>
    </#if>
  </div>
    <div class="list-loading" style="display:none" id="showmore">
        <img alt="" src="${basePath}/images/loading.gif">
        <span>加载中……</span>
    </div>
</div>

<#include '/common/smart_menu.ftl' />
<script src="${basePath}/js/customer/browerecord.js"></script>
<script>
  $(function(){
	$(".bar-bottom  a").removeClass("selected");
    $(".bar-bottom:eq(0) a:eq(3) ").addClass("selected");
    /* 排序 */
    $('.sort_choose').click(function(){
      if($('.opacity-bg-1').length == 0){
        $('body').append('<div class="opacity-bg-1"></div>');
        $('.sort-list').slideDown('fast');
      }
    });
    $('.sort-list li').click(function(){
      $(this).addClass('selected').siblings().removeClass('selected');
      $('.opacity-bg-1').remove();
      $('.sort-list').hide();
    });

    /* 筛选 */
    $('.filter_choose').click(function(){
      if($('.opacity-bg-3').length == 0){
        $('body').append('<div class="opacity-bg-3"></div>');
        $('.screen').show();
      }
    });
    $('a.sure').click(function(){
      $('.opacity-bg-3').remove();
      $('.screen').hide();
    });

 	$(window).scroll(function(){
	    if($(this).scrollTop() >= ($('body').height() - $(window).height())){
	      show();
	    }
    });
  });
</script>
</body>
</html>