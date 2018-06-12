<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>会员中心 - 我的收藏</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta name="keywords" content="${(seo.meteKey)!''}">
  <meta name="description" content="${(seo.meteDes)!''}">
  <meta content="telephone=no" name="format-detection">
  <#assign basePath=request.contextPath>
   <link rel="stylesheet" type="text/css" href="${basePath}/css/style.min.css"/>
   <link rel="stylesheet" href="${basePath}/css/ui-dialog.css">
   <script src="${basePath}/js/jquery-1.10.1.js"></script>
  <script src="${basePath}/js/dialog-min.js"></script>
    <style>
        .col9 {
            color: #999;
        }
    </style>

</head>
<body>
 <input id="status" value="0" type="hidden">
 <input id="basePath" type="hidden" value="${basePath}" /> 
 <input id="pb" type="hidden" value="${pb!''}" /> 
 <input id="pbsize" type="hidden" value="<#if pb?? && (pb.list?size!=0)>${pb.rows}<#else>0</#if>" /> 
<!--<div class="bar-top">
  <a class="bar-item sort_choose active">默认<i class="sharp-down"></i></a>
  <a class="bar-item">降价优先</a>
  <a class="bar-item">促销优先</a>
  <a class="bar-item filter_choose"><i class="screen-icon"></i>筛选</a>
  <!--<div class="sort-list" style="display:none;">
    <ul>
      <li class="selected">默认排序</li>
      <li>价格从低到高</li>
      <li>价格从高到低</li>
      <li>评价最好</li>
    </ul>
  </div>
</div>-->
<div class="content" style="padding-top:0;">
  <div class="prolist list-line" id="items">
     <#if (pb.list?size!=0)>
		<#list pb.list as follow>
		    <div class="list-item  pro-item<#if (follow.good.goodStock)?? ><#if (follow.good.goodStock>0)><#else> sell-out</#if></#if>" id="collection${follow.followId}">
			   <a href="${basePath}/item/${follow.goodsId}.html">
			      <div class="propic">
			        <img width="100" height="100" alt="<#if follow.good.goodsName??>${follow.good.goodsName}</#if>"
			         title="<#if follow.good.goodsName??>${follow.good.goodsName}</#if>" 
				            src="<#if follow.good.goodsImg??>${follow.good.goodsImg}</#if>"/>
			      </div>
			      <div class="prodesc">
			        <h3 class="title">
			        <#if (follow.good.goodsName)?length gt 50>
			        	${(follow.good.goodsName)?substring(0,50)}
			        	<#else>${(follow.good.goodsName)!''}
			        </#if>
			        </h3>
                      <p class="col9">
                          <#list follow.good.specVo as specVo>
                          ${specVo.spec.specName}:<#if specVo.specValueRemark??>${specVo.specValueRemark}<#else>${specVo.goodsSpecDetail.specDetailName!''} </#if>&nbsp;
                          </#list>
                      </p>
			        <p class="price">&yen;&nbsp;<span><#if follow.good.goodsPrice??>${follow.good.goodsPrice?string('0.00')}</#if></span>
			          <#if (follow.good.codeUtils)??>
			           <#list follow.good.codeUtils as code>
			           		<#if code.codexId==5>
                                <span class="fav reduce">减</span>
                            </#if>
                            <#if code.codexId==1>
                                <span class="fav reduce">直</span>
                            </#if>
                            <#if code.codexId==8>
                                <span class="fav discount">折</span>
                            </#if>
                            <#if code.codexId==12>
                                <span class="fav reduce">邮</span>
                            </#if>
			           </#list>
			          </#if>
			        </p>
			        <p class="comm">
			          <span class="item-percent">好评<span>${follow.productComment}</span>%</span>
			          <span class="item-pnum"><span>${(follow.good.commentCount)!'0'}</span>人评价</span>
			        </p>
			      </div>
		        </a>
		       <#--<div class="proctrl">
		        <a href="javascript:void(0);" class="addcart_btn"  data-val="<#if follow.good.goodStock?? ><#if (follow.good.goodStock>0)>1<#else>0 </#if></#if>" data-pro="${follow.goodsId}" onclick="addCar(this)">加入<br>购物车</a>
		        <a href="javascript:void(0);" class="del_btn" onclick="cancelcollection(this,'${follow.followId}')">取消<br>收藏</a>
		      </div>-->
                <a href="javascript:void(0);" class="del-btn"  onclick="doCancel(this,'${follow.followId}')"><i class="ion-ios-trash"></i></a>
		    </div>
		</#list>
	 </#if>	
		<div class="content" id="no">
		  <div class="no_tips">
		    <p>木有收藏商品哦</p>
		  </div>
		</div>
   </div>
	<div class="list-loading" style="display:none" id="showmore">
        <img alt="" src="${basePath}/images/loading.gif">
        <span>加载中……</span>
    </div>
</div>

<div class="screen" style="display:none">
  <div class="screen-header">
    <a class="back" href="javascript:;"><i class="back-icon"></i></a>
    筛选
    <a class="btn sure" href="javascript:;">确定</a>
  </div>
  <div class="screen-cont">
    <div class="lists">
      <dl>
        <dd>全部
          <div class="check-box"></div>
        </dd>
        <dd class="selected">有货商品
          <div class="check-box"></div>
        </dd>
        <dd>奢侈品
          <div class="check-box"></div>
        </dd>
        <dd>面部护肤
          <div class="check-box"></div>
        </dd>
        <dd>童车童床
          <div class="check-box"></div>
        </dd>
      </dl>
      <a class="clearSet" href="javascript:;">清除选项</a>
    </div>
  </div>
</div>

<#include '/common/smart_menu.ftl' />
<script src="${basePath}/js/customer/mycollections.js"></script>
<script>
  $(function(){
  if($("#pb").val() !=null && $("#pbsize").val()>0){
    $("#no").hide();
  }else{
  	$("#no").show();
  }
 	$(".bar-bottom  a").removeClass("selected");
    $(".bar-bottom:eq(0) a:eq(3) ").addClass("selected");
    /* 排序 */
    $('.sort_choose').click(function(){
    
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

      $('.style-trans').click(function(){
          if($('.list-line').length > 0){
              $('.list-line').addClass('list-box').removeClass('list-line');
              $(this).find('i.list-icon').addClass('list-2').removeClass('list-1');
          }
          else{
              $('.list-box').addClass('list-line').removeClass('list-box');
              $(this).find('i.list-icon').addClass('list-1').removeClass('list-2');
          }
      });
  //  loadcollection();
    
     $(window).scroll(function(){
	    if($(this).scrollTop() >= ($('body').height() - $(window).height())){
	      show();
	    }
    });
    
  });
  function addCar(obj){
         /* 加入购物车 */
        var addCartNum=0;
        var stock = $(obj).attr("data-val");
        var productId = $(obj).attr("data-pro");
		/*如果购买数量大于剩余库存不允许购买*/
			if(stock == 0){
	            setTimeout(function(){
	                if(stock = 1){
	                	$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>库存不足</h3></div></div>');
	                }
	            },1000);
	            setTimeout(function(){
	                window.location.reload();
	            },2000)
                return;
             }else if(stock == 1){
	            addCartNum=addCartNum+1;
	            $.ajax({
	              url:'${basePath}/addproducttoshopcarnew.htm?goodsNum=1&productId='+productId+'&distinctId=1103',
	              type:'post',
	              success:function(data){
	               if(data>0){
		               $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="waiting"></i><h3>正在加入购物车</h3></div></div>');
		            setTimeout(function(){
		                $('.tip-box').remove();
		                if(stock = 1){
		                	$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>成功加入购物车</h3></div></div>');
		                }
		            },1000);
		            setTimeout(function(){
		                window.location.reload();
		            },2000)
	                }
	               }
	            });
            }
  }

  function doCancel(obj,id){
      var discountBox = dialog({
          width: 260,
          title: '删除收藏',
          content: "确定删除收藏的商品？",
          okValue: '确定',
          cancelValue: '取消',
          ok: function () {
              cancelcollection(obj,id);
              setTimeout(function () {
                  $('.tip-box').remove();
                  $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>删除商品成功！</h3></div></div>');
              }, 10);
              setTimeout(function () {
                  $(".tip-box").hide();
                  location.reload();
              }, 1000)
              return true;
          },
          cancel: function () {
              return true;
          }
      });
      discountBox.showModal();
  }
</script>
</body>
</html>