<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
<#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.10.1.js"></script>
    <script type="text/javascript" src="${basePath}/js/idangerous.swiper.js"></script>
</head>
<body>
<div class="content-home" style="padding-top:0px;padding-bottom: 0px;">
<div class="member_brief member_box">
  <div class="cover">
      <a href="${basePath}/myaccount.html">
      <div class="avatar">
          <img alt="" style="height: 100%;" src="<#if cust.customerImg?? && cust.customerImg!=''>${cust.customerImg}<#else>${basePath}/images/avatar.png</#if>">
      </div>
      </a>
    <h2>${cust.customerNickname!''}</h2>
      <p class="lv">${cust.pointLevelName}</p>
      <div class="message config">
          <a href="${basePath}/myaccount.html">
              <i class="iconfont icon-shezhi"></i>
          </a>
      </div>
    <div class="message">
      <a href="${basePath}/adminnotice.html">
        <i class="iconfont icon-icxiaoxi"></i>
        <#if xiaoxiCount==0>
        
        <#else>
            <span class="badge">${xiaoxiCount!''}</span>
        </#if>
      </a>
    </div>
  </div>
  <div class="data row">
    <div class="col-12">
      <p>
        <a href="${basePath}/customer/mycollections.html">
          <span>${(mycollectcount)!'0'}</span>
          我的关注
        </a>
      </p>
    </div>
    <div class="col-12">
      <p>
        <a href="${basePath}/customer/mybrowerecord.html">
          <span>${browereCount!'0'}</span>
          浏览历史
        </a>
      </p>
    </div>
  </div>
</div>

<div class="order_area member_box">
    <div class="common_line row">
    <a href="${basePath}/customer/myorder.html">

            <em class="order_icon"></em>
            <h4>我的订单</h4>
            <span>查看全部订单</span>
            <i class="ion-chevron-right"></i>
    </a>
    </div>
  <div class="top row">
    <div class="col-6">
      <a href="${basePath}/customer/myorder-1-1.html">
        <p class="order_state_1">
          <i class="iconfont icon-daifukuan"></i>
          待付款
            <#if noMoneyCount?? && noMoneyCount != 0>
                <span class="badge">${noMoneyCount!''}</span>
            </#if>
        </p>
      </a>
    </div>
    <div class="col-6">
      <a href="${basePath}/customer/myorder-3-1.html">
        <p class="order_state_2">
          <i class="iconfont icon-daishouhuo"></i>
          待收货
<#if noFaHuoCount?? && noFaHuoCount != 0>
            <span class="badge">${noFaHuoCount!''}</span>
</#if>
        </p>
      </a>
    </div>
    <div class="col-6">
      <a href="${basePath}/customer/myorder-4-1.html">
        <p class="order_state_3">
          <i class="iconfont icon-klmpingjia"></i>
          待评价
<#if noCommentCount?? && noCommentCount !=0>
            <span class="badge">${noCommentCount!''}</span>
</#if>
        </p>
      </a>
    </div>
    <div class="col-6">
      <a href="${basePath}/customer/myorder-6-1.html">
        <p class="order_state_4">
          <i class="iconfont icon-tuikuanshouhou"></i>
          退款/退货
<#if cancelCount?? && cancelCount != 0>
            <span class="badge">${cancelCount!''}</span>
</#if>
        </p>
      </a>
    </div>
  </div>

</div>

<#--<div class="member_box">
 <a href="${basePath}/customer/myintegral.html">
  <div class="common_line row">
      <em class="point_icon"></em>
      <h4>积分</h4>
      <i class="ion-chevron-right"></i>
  </div>
  </a>
  <a href="${basePath}/customer/coupon.html">
  <div class="common_line row">
      <em class="coupon_icon"></em>
      <h4>优惠券</h4>
      <i class="ion-chevron-right"></i>
  </div>-->

      <div class="order_area member_box">
          <div class="common_line row">
              <a href="#">
                  <em class="balance_icon"></em>
                  <h4>我的资产</h4>
              </a>
          </div>
          <div class="top row">
              <div class="col-6">
                  <a href="${basePath}/member/showmypredeposits.html">
                      <p class="order_state_1">
                          <i class="iconfont icon-yue"></i>
                          预存款
                      </p>
                  </a>
              </div>
              <div class="col-6">
                  <a href="${basePath}/customer/customerTradeInfo.html">
                      <p class="order_state_2">
                          <i class="iconfont icon-tixianjilu"></i>
                          账户明细
                      </p>
                  </a>
              </div>
              <div class="col-6">
                  <a href="${basePath}/customer/myintegral.html">
                      <p class="order_state_3">
                          <i class="iconfont icon-xingxing"></i>
                          积分
                      </p>
                  </a>
              </div>
              <div class="col-6">
                  <a href="${basePath}/customer/coupon.html">
                      <p class="order_state_4">
                          <i class="iconfont icon-youhuiquan"></i>
                          优惠券
                      </p>
                  </a>
              </div>
           </a>
          </div>
          <div class="order_area member_box" id="onlineService">
              <div class="common_line row">
                  <a href="" class="onlineService">
                      <em class="kefu_icon"></em>
                      <h4>在线客服</h4>
                      <i class="ion-chevron-right"></i>
                  </a>
              </div>
          </div>

          <#--<div class="order_area member_box" id="onlineService_KST">-->
              <#--<div class="common_line row">-->
                  <#--<a href="${basePath}/goToKst.htm" class="kstService">-->
                      <#--<em class="kefu_icon"></em>-->
                      <#--<h4>在线客服</h4>-->
                      <#--<i class="ion-chevron-right"></i>-->
                  <#--</a>-->
              <#--</div>-->
          <#--</div>-->
          <#--<div class="order_area member_box" id="onlineService_QQ">-->
              <#--<div class="common_line row">-->
                  <#--<a href="${basePath}/readOnLineServiceQQList.htm" class="qqService">-->
                      <#--<em class="kefu_icon"></em>-->
                      <#--<h4>在线客服</h4>-->
                      <#--<i class="ion-chevron-right"></i>-->
                  <#--</a>-->
              <#--</div>-->
          <#--</div>-->

<div class="member_box">
  <div class="common_line row">
    <a href="javascript:;">
      <em class="like_icon"></em>
      <h4>猜你喜欢</h4>
    </a>
  </div>
  <div class="slide_goods">
    <div class="swiper-container">
      <div class="swiper-wrapper">
      <#if guessLikes??>
        <#list guessLikes as like>
        <div class="swiper-slide">
          <div class="like_good">
            <a href="${basePath}/item/${like.goodsInfoId}.html">
              <div class="img">
                <img alt="" src="${like.goodsInfoImgId!''}" height="110px;">
              </div>
              <p class="name">
               <#if (like.goodsInfoName)?length gt 15> 
              ${(like.goodsInfoName)?substring(0,15)}...
              <#else> ${like.goodsInfoName!''}
            </#if>    
              </p>
              <p class="price">
                 ￥${like.goodsInfoPreferPrice?string("0.00")}
              </p>
            </a>
          </div>
        </div>
        </#list>
        <#else>
        
        </#if>
      </div>
    </div>
  </div>
</div>
</div>
<#--
<div class="bar-bottom">
  <a class="bar-item" href="${basePath}/main.html"><i class="bar-bottom-i home"></i>首页</a>
  <a class="bar-item" href="${basePath}/cates.html"><i class="bar-bottom-i class"></i>分类</a>
  <a class="bar-item" href="${basePath}/myshoppingmcart.html"><i class="bar-bottom-i cart"></i>购物车</a>
  <a class="bar-item selected" href="${basePath}/customercenter.html"><i class="bar-bottom-i mine"></i>我的</a>
</div>
-->
<#include '/common/smart_menu.ftl' />
<script>
  var mySwiper = new Swiper('.swiper-container',{
    slidesPerView: 'auto'
  });
  $(".bar-bottom a").removeClass("selected");
  $(".bar-bottom a:eq(3)").addClass("selected");
  $(function(){

      <#--$.ajax({-->
          <#--type: 'get',-->
          <#--url:"${basePath}/checkOnLineService.htm",-->
          <#--async:false,-->
          <#--success: function(data) {-->
              <#--if(data == true){-->
                  <#--$("#onlineService").show();-->
                  <#--$(".onlineService").attr("href","${basePath}/readOnLineServiceQQList.htm");-->
              <#--}else if(data == false){-->
                  <#--$("#onlineService").show();-->
                  <#--$(".onlineService").attr("href","${basePath}/goToKst.htm");-->
              <#--}else if(data == null) {-->
                  <#--$("#onlineService").hide();-->
                  <#--$(".onlineService").attr("href","");-->
              <#--}-->
          <#--},-->
          <#--error: function(){-->
              <#--alert("该服务暂停使用，请稍后再试！");-->
          <#--}-->
      <#--});-->

      $.ajax({
          type: 'get',
          url:"${basePath}/checkOnLineService.htm",
          async:false,
          success: function(data) {
              if(data.url == "") {
                  $("#onlineService").hide();
                  $(".onlineService").attr("href","");
              }else{
                  $("#onlineService").show();
                  if(data.type == "QQ"){
                      $(".onlineService").attr("href","http://wpa.qq.com/msgrd?v=3&uin="+data.url+"&site=qq&menu=yes");
                  }else{
                      $(".onlineService").attr("href",data.url);
                  }
              }
          },
          error: function(){
              alert("该服务暂停使用，请稍后再试！");
          }
      });


  })


</script>
</body>
</html>