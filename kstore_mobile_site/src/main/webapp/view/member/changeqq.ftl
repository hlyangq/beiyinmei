<!DOCTYPE html>
<html>
<head>
<#assign basePath=request.contextPath>
  <meta charset="UTF-8">
  <title>会员中心 - 我的账户</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
  <script src="${basePath}/js/idangerous.swiper.js"></script>
</head>
<body>

<div class="member_form">
  <dl>
    <dt>设置QQ</dt>
    <dd>
      <input type="text" value="${customer.infoQQ!''}" class="text" placeholder="请在此输入您的qq号码">
    </dd>
  </dl>
  <p class="help_block" style="display:none;" id="nickName_msg"></p>
  <p class="help_block">QQ号码由6-10位数字组成</p>
</div>

<div class="p10 mb50">
  <a href="#" class="btn btn-full">保存</a>
</div>
<#include '/common/smart_menu.ftl' />
<script>
    $(".btn-full").click(function(){
        var qq = $(".text").val();
        var reg = new RegExp("^[1-9]{1}[0-9]{5,9}$", "g");
     if (!reg.test(qq)) {
     $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>QQ格式不正确</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
    }
      window.location.href="${basePath}/saveqq.htm?changeQq="+qq;
    });
    
    $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");
</script>
</body>
</html>