<!DOCTYPE html>
<html lang="en">
<head>
    <#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
</head>
<body>
<div class="content-login mb50">
    <input type="hidden"  id="basePath" value="${basePath}"/>
    <input type="hidden" id="urlhide" value="${url!''}" />
    <div class="login-banner">
        <img src="images/login.jpg" alt=""/>
        <div class="user">
            <div class="user-pic"><img src="images/user-pic.png" alt=""/></div>
        </div>
        <div class="tab-menu">
            <a class="cur" href="javascript:;">注册</a>
            <a href="loginm.html">登录</a>
        </div>
    </div>
    <div class="in-box">
        <div class="list-item">
            <label for="">手机号码</label>
            <input type="text" name="mobile" id="mobile" placeholder="输入手机号"/>
        </div>
        <div class="list-item">
            <label for="">图片验证</label>
            <input type="text" name="checkCode" id="checkCode" placeholder="输入验证码"/>
            <img id="checkCodeImg" src="${basePath}/patchca.htm" class="get-confirmcode" onclick="this.src=this.src+'?'+Math.random(); " alt="验证码">
        </div>
        <div class="list-item">
            <label for="">验证码</label>
            <input type="text" name="code" id="code" placeholder="短信验证码"/>
            <a class="get-confirmcode" href="#" onClick="codeNock();">获取验证码</a>
        </div>
        <div class="list-item"> 
            <label>密码</label>
            <input type="password" name="password" id="password" placeholder="设置密码"/>
            <#--<i class="eye"></i>-->
        </div>
        <div class="list-item">
            <label>确认密码</label>
            <input type="password" name="rePassword" id="repassword" placeholder="再次输入密码"/>
            <#--<i class="eye"></i>-->
        </div>
    </div>
    <div class="list-item regist">
        <a class="btn btn-full" href="javascript:;" onClick="subRegister();"><i>注&nbsp;册</i></a>
    </div>
</div>

<#include '../common/smart_menu.ftl' />

<script>
   function codeNock(){
        if($("#mobile").val()==null || $("#mobile").val()==''){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入手机号</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }
       if(!(/^0?1[3|4|5|8|7][0-9]\d{8}$/).test($("#mobile").val())){
           $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>手机号格式错误</h3></div></div>');
           setTimeout(function(){
               $('.tip-box').remove();
           },1000);
           return false;
       }
       if($("#checkCode").val()==null || $("#checkCode").val()==''){
           $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入图片验证</h3></div></div>');
           setTimeout(function(){
               $('.tip-box').remove();
           },1000);
           return false;
       }
        $.ajax({
            url:"${basePath}/sendcodereg.htm",
            type:"post",
            data:{mobile:$("#mobile").val(),checkCode:$("#checkCode").val()},
            success:function(data){
                if(data == 1){
                    //发送成功
                    //alert("发送成功");
                    $(".get-confirmcode").html('60秒后重新获取');
                    $(".get-confirmcode").attr('data-t','60');
                    $(".get-confirmcode").attr("onClick","");
                    $(".get-confirmcode").css("color","gray");
                    $("#mobile").attr("readonly","readonly");
                    setTimeout(countDown, 1000);
                }else if(data == 2){
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>该手机号已被注册</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }else if(data==3){
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>图片验证不正确</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }else{
                    //发送失败
                    //alert("发送失败");
                     $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>发送失败</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }
            }
        });
    }
    
    function countDown(){
        var time = $(".get-confirmcode").attr('data-t');
        $(".get-confirmcode").html((time - 1)+"秒后重新获取");
        $(".get-confirmcode").attr('data-t',(time - 1));
        time = time - 1;
        if (time < 1) {
            $(".get-confirmcode").attr("onClick","codeNock();");
            $(".get-confirmcode").html("获取验证码");
            $(".get-confirmcode").css("color","red");
            $("#mobile").removeAttr("readonly");
        } else {
            setTimeout(countDown, 1000);
        }
    }
</script>
<script src="${basePath}/js/customer/allvalid.js"></script>
</body>
</html>