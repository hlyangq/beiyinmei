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
<form action="${basePath}/savename.html" id="addForm" method="post">
  <dl>
    <dt>设置姓名</dt>
    <dd>
      <input type="text" value="${customer.infoRealname!''}" class="text" name="infoRealname"/>
    </dd>
  </dl>
  <p class="help_block" style="display:none;" id="nickName_msg"></p>
  <p class="help_block">姓名由中文或字母组成</p>
 </form>
</div>

<div class="p10 mb50">
  <a href="#" class="btn btn-full">保存</a>
</div>
<#include '/common/smart_menu.ftl' />
<script>
    $(".text").change(function(){
        $("#nickName_msg").html("");
        $("#nickName_msg").hide();
    });
    $(".btn-full").click(function(){
        var nickName = $(".text").val();
        var reg = new RegExp("^([a-zA-Z]|[\\u4E00-\\u9FFF]|[·])+$", "g");
    if(nickName ==null || nickName ==''){
        $("#addForm").submit();
        return true;
    }else if (nickName.indexOf("··") != -1) {
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>不允许连续出现中间点</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
    }else if (nickName.substring(0, 1) == "·" || nickName.substring(nickName.length - 1) == "·") {
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>名字首尾不能出现中间点</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
    }
    else if (!reg.test(nickName)) {
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>姓名格式不正确</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
    }else if (nickName.replace(/[^\x00-\xff]/g, "**").length < 4
            || nickName.replace(/[^\x00-\xff]/g, "**").length > 20) {
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入真实姓名,长度在4-20之间</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
    }
      $("#addForm").submit();
    });
    
    $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");
</script>
</body>
</html>