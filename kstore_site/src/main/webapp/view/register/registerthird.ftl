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
        <img alt="kstore" src="" id="reg_third" style="height:40px;width:165px;">
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
      <div class="step active">
        <i>2</i>
        <span>设置用户信息</span>
      </div>
      <div class="step">
        <i>3</i>
        <span>注册成功</span>
      </div>
    </div>
    <form action="${basePath}/addcust.html" method="post" id="regform">
      <input type="hidden" name="cusId" id="cusId" value="" />
      <input type="hidden" name="infoMobile" id="mobile" value="${mobile}" />
      <input type="hidden" name="customerUsername" value="${mobile}" />
        <div class="reg_form">
          <div class="reg_form_item">
            <label class="for_text">账号密码</label>
            <div class="item">
              <input type="password" class="text long passwordconf" name="customerPassword" placeholder="设置账号密码">
              <i class="correct" style="display: none"></i>
              <p class="tips pass" style="display: none">密码必须是6-20位数字或字母或符号组成</p>
            </div>
          </div>
          <div class="reg_form_item">
            <label class="for_text">确认密码</label>
            <div class="item">
              <input type="password" class="text long repasswordconf" placeholder="确认账号密码">
              <i class="correct" style="display: none"></i>
              <p class="tips repass" style="display: none">确认密码必须与账号密码保持一致</p>
            </div>
          </div>
      </form>
      <div class="reg_form_item">
        <label class="for_text">&nbsp;</label>
        <div class="item">
          <div class="sub_btn" onclick="register()">下一步</div>
        </div>
      </div>
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

<script src="${basePath}/js/jquery.min.js"></script>
<script src="${basePath}/js/icheck.min.js"></script>
<script>
  $(function(){
    $(".i-checks").iCheck({
      checkboxClass:"icheckbox_square-green",
      radioClass:"iradio_square-green"
    })
      $.ajax({
          url: 'loadlogo.htm',
          success: function(data){
              $("#reg_third").prop("src",data.logo.bsetLogo);
          }
      });
  });
</script>

</body>
</html>