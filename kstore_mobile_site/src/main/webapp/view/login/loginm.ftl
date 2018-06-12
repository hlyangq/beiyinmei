<!DOCTYPE html>
<html lang="en">
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
</head>
<body>
<div class="content-login mb50">
    <input type="hidden" id="basepath" value="${basePath}"/>
     <input type="hidden" id="urlhide" value="${url}" />
    <div class="login-banner">
        <img src="images/login.jpg" alt=""/>
        <div class="user">
            <div class="user-pic"><img src="images/user-pic.png" alt=""/></div>
        </div>
        <div class="tab-menu">
            <a href="${basePath}/register.html?url=${url}">注册</a>
            <a class="cur" href="javascript:;">登录</a>
        </div>
    </div>
    <div class="in-box">
        <div class="list-item">
            <label for="">用户名</label>
            <input type="text" id="log_user" placeholder="账号/手机号码"/>
        </div>
        <div class="list-item">
            <label for="">密码</label>
            <input type="password" id="log_psd" placeholder="请输入密码"/>
            <i class="eye"></i>
        </div>
        <div id="codeDiv" style="display:none;">
        <div class="list-item" >
            <label for="">验证码</label>
            <input type="text" id="code" placeholder="验证码"/>
            <span class="valid_img">
                <img alt="" id="code_image" src="${basePath}/patchca.htm" alt="验证码" onclick="this.src=this.src+'?'+Math.random();"/>
            </span>
        </div>
        </div>
    </div>
    <div class="list-item login">
        <p class="forget-password" style="margin-top:0; margin-bottom: 10px;"><a href="${basePath}/valididentity.html">忘记密码？</a></p>
        <a class="btn btn-full" href="javascript:;" onClick="login()"><i>登&nbsp;录</i></a>

        <div class="n_row">
            <p class="f14 col6" style="color:#999; margin-top: 10px;">你也可以使用以下账号登录：</p>
            <div class="mt10" style="width:350px;">
            <#if t??>
                <#list t as tt >
                    <#if tt.authType == '1'>
                        <a href="loginmqq.html?preurl=${url}" class="mr10"><img src="images/q1.png"/></a><!--腾讯QQ-->
                    <#elseif tt.authType == '2'>
                        <a href="loginsina.html?preurl=${url}" class="mr10"><img src="images/q3.png"/></a><!--新浪微博-->
                    </#if>
                </#list>
            </#if>
            </div>
        </div><!--n_row-->

    </div>


</div>

<#include '/common/smart_menu.ftl' />

<script>
  $(function(){

    /* 切换密码字符显示状态，真实或隐藏 */
    $('.eye').click(function(){
      if($(this).attr('class').indexOf('active')>=0){
        $(this).removeClass('active');
        $("#log_psd").attr('type','password');
      }else{
        $(this).addClass('active');
         $("#log_psd").attr('type','text');
      }
    });
    $(".eye").show();
    $("#code_image").click();
  });
  </script>
  <script src="${basePath}/js/login/login.js"></script>
</body>
</html>