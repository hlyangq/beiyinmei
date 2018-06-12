<!DOCTYPE html>
<html lang="en">
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>登录-忘记密码</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
</head>
<body>
<div class="content-login-forget-password mb50">
    <input type="hidden" id="basepath" value="${basePath}"/>
    <div class="in-box">
        <div class="list-item">
            <label for="">手机号码</label>
            <input type="text" id="mobile" name="mobile" placeholder="请输入绑定手机"/>
        </div>
        <div class="list-item">
            <label for="">图片验证</label>
            <input type="text" name="checkCode" id="checkCode" placeholder="输入验证码"/>
            <img id="checkCodeImg" src="${basePath}/patchca.htm" class="get-confirmcode" onclick="this.src=this.src+'?'+Math.random(); " alt="验证码">
        </div>
        <div class="list-item">
            <label for="">验证码</label>
            <input type="text" id="code" name="code" placeholder="请输入验证码"/>
            <a class="get-confirmcode" href="#" id="huode" onClick="getCode();">获取验证码</a>
        </div>
        <div class="list-item">
            <label for="">新密码</label>
            <input type="password" id="newpassword" name="newpassword" placeholder="6-20位数字、字母组合"/>
            <#--<i class="eye"></i>-->
        </div>
        <div class="list-item">
            <label for="">确认密码</label>
            <input type="password" id="repassword" name="rewpassword" placeholder="6-20位数字、字母组合"/>
            <#--<i class="eye"></i>-->
        </div>
    </div>
    <div class="list-item tijiao">
        <a class="btn btn-full" href="javascript:;"><i>提&nbsp;交</i></a>
    </div>
</div>

<#include '/common/smart_menu.ftl' />

<script>
  $(function(){
    /* 切换密码字符显示状态，真实或隐藏 */
    $('.eye').click(function(){
      if($(this).attr('class').indexOf('active')>=0){
        $(this).removeClass('active');
        $("#newpassword").attr('type','password');
      }else{
        $(this).addClass('active');
         $("#newpassword").attr('type','text');
      }
    });
    $(".eye").show();
  });
  </script>
<script src="${basePath}/js/findpwd/allvalid.js"></script>
<script src="${basePath}/js/findpwd/asvalidate.js"></script>
</body>
</html>