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
<form action="${basePath}/savenickname.html" id="addForm" method="post">
  <dl>
    <dt>设置昵称</dt>
    <dd>
      <input type="text" value="${customer.customerNickname!''}" class="text" name="customerNickname"/>
    </dd>
  </dl>
  <p class="help_block" style="display:none;" id="nickName_msg"></p>
  <p class="help_block">4-20字符，由中文、英文、数字、-和_组成</p>
</form>
</div>

<div class="p10 mb50">
  <a href="#" class="btn btn-full">保存</a>
</div>
<#include '/common/smart_menu.ftl' />
<script>
    $(".bar-bottom a").removeClass("selected");
    $(".bar-bottom a:eq(3)").addClass("selected");

    $(".text").change(function(){
        $("#nickName_msg").html("");
        $("#nickName_msg").hide();
    });
    $(".btn-full").click(function(){
        var nickName = $(".text").val();
        if(nickName.length<3 || nickName.length>20){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>昵称长度不符合</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }
        var reg = new RegExp("^([a-zA-Z0-9_-]|[\\u4E00-\\u9FFF])+$", "g");
    var reg_number = /^[0-9]+$/; // 判断是否为数字的正则表达式
    //if (reg_number.test(nickName)) {
    //    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>昵称不能设置为手机号等纯数字格式，请您更换哦^^</h3></div></div>');
    //    setTimeout(function(){
    //        $('.tip-box').remove();
    //    },1000);
    //    return false;
    //} 
     if (!reg.test(nickName)) {
     $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>昵称格式不正确</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
    }
      $("#addForm").submit();
    });
</script>
</body>
</html>