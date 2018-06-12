<!DOCTYPE html>
<html>
<head>
    <#assign basePath=request.contextPath/>
  <meta charset="UTF-8">
  <title>会员中心 - 我的账户</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
        <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
        <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
  <script src="${basePath}/js/idangerous.swiper.js"></script>
        <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>

<div class="safe_list">
  <div class="safe_item">
    <a href="${basePath}/tochangepassword.html">
      <h4>
        <span>登录密码</span>
      </h4>
      <p>
        建议您定期更改密码以保护账户安全
      </p>
      <i class="ion-ios-arrow-right"></i>
    </a>
  </div>
  <div class="safe_item">
    <a href="${basePath}/phonevalidate1.html">
      <h4>
        <span>手机验证</span>
        <span class="right"><em><#if cust.infoMobile??&&cust.infoMobile!=''>
            <#assign mo="${cust.infoMobile?substring(3,cust.infoMobile?length-4)}" />
            <#assign mob="${cust.infoMobile?replace(mo,'****')}" />
        ${mob}
        <#else>未绑定</#if></em></span>
      </h4>
      <p>
        若您的验证手机已丢失或停用，请立即修改
      </p>
      <i class="ion-ios-arrow-right"></i>
    </a>
  </div>
    <div class="safe_item">
        <a href="javascript:;" id="setPayPassword">
            <h4>
                <span>支付密码</span>
            </h4>
            <p>设置支付密码用户可进行预存款支付</p>
            <i class="ion-ios-arrow-right"></i>
        </a>
    </div>
  <#--
  <div class="safe_item">
    <a href="my_account_pay_password1.htm">
      <h4>
        <span>支付密码</span>
        <span class="right">安全程度 <em>高</em></span>
      </h4>
      <i class="ion-ios-arrow-right"></i>
    </a>
  </div>
  -->
</div>

<div class="safe_list mb50">
  <div class="safe_item">
    <a href="${basePath}/tosafetips.html">
      <h4>
        <span>安全提示</span>
      </h4>
      <i class="ion-ios-arrow-right"></i>
    </a>
  </div>
</div>
<#include '/common/smart_menu.ftl' />
<script>
  $(function(){
      /*
    * 设置支付密码前提示
    * */
      $('#setPayPassword').click(function(){
          $.ajax({
              url:'${basePath}/mobilevalidation.htm',
              type:'GET',
              dataType:'text',
              async:true,
              success:mobileValidate
          });


      });

  });
  
  $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");

    function mobileValidate(responseData){
      if(responseData == 'false'){
          var setPayPassword = dialog({
              width : 260,
              content : '<div style="padding:0 15px"><p class="tc">设置支付密码，必须先验证手机，请先验证手机</p></div> ',
              okValue : '立即验证',
              cancelValue : '稍后再说',
              ok : function(){
                  //确认离开，跳转到下一页面
                  window.location.href="${basePath}/phonevalidate1.html";
                  return false;
              },
              cancel : function(){
                  //演示需要，跳转到密码支付页面
//                  window.location.href="my_account_pay_password1.html";
                  return true;
              }
          });
          setPayPassword.showModal();
      }else{
          window.location.href="${basePath}/member/topaypassword.html";
      }
    }
</script>
</body>
</html>