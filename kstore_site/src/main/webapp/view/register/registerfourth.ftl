<!DOCTYPE html>
<html>
<head lang="zh">
  <meta charset="UTF-8">
  <title>注册页</title>
<#assign basePath=request.contextPath>
  <link rel="stylesheet" href="http://kstore.qianmi.com/index_seven/css/style.css">
  <link rel="stylesheet" href="${basePath}/css/iCheck/custom.css">
  <link rel="stylesheet" href="${basePath}/css/receive.m.css">
  <script src="${basePath}/js/jquery.js"></script>
  <script type="text/javascript" src="${basePath}/js/register.js"></script>
</head>
<body class="grey_bg">

<div class="container">

  <div class="mini_head">
    <h1 class="logo">
      <a href="${basePath}/index.html">
        <img alt="kstore" src="" style="height:40px;width:165px;" id="reg_fouth">
      </a>
      <span>欢迎注册</span>
    </h1>
  </div>

  <p class="login_tip">
    我已经注册，马上
    <a href="${basePath}/login.html">登录 &gt;</a>
  </p>

  <div class="user_box">

    <div class="user_flow step3">
      <div class="step">
        <i>1</i>
        <span>设置登录名</span>
      </div>
      <div class="step">
        <i>2</i>
        <span>设置用户信息</span>
      </div>
      <div class="step active">
        <i>3</i>
        <span>注册成功</span>
      </div>
    </div>

    <div class="success_tips">
      <i class="successIcon"></i>
      <#assign m="${cust.customerUsername}" />
      <#assign mo="${m?substring(3,m?length-4)}" />
      <#assign mob="${m?replace(mo,'****')}" />
      <p>恭喜您，${mob}账号注册成功！</p>
      <a href="${basePath}/index.html" class="btn">立即购物</a>
    </div>

  </div>

<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">

    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>

</div>

<script src="js/jquery.min.js"></script>
<script src="js/icheck.min.js"></script>
<script>
  $(function(){
    $(".i-checks").iCheck({
      checkboxClass:"icheckbox_square-green",
      radioClass:"iradio_square-green"
    })
      $.ajax({
          url: 'loadlogo.htm',
          success: function(data){
              $("#reg_fouth").prop("src",data.logo.bsetLogo);
          }
      });
  });
</script>

</body>
</html>