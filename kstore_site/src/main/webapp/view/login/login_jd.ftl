<!doctype html>
<html lang="en" style="overflow-y:hidden;">
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>登录${topmap.systembase.bsetName}</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/jd.base.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/jd.style.css"/>
    <style>
        .n_row .n_btn_a{
            height: 40px;
            background: #ec6a1e;
            color: #fff;
            text-align: center;
            line-height: 40px;
            font-size: 16px;
            border-radius: 3px;
            border: none;
            display: block;
            font-family: "微软雅黑";
            cursor: pointer;
            width:48.3%;
            float:left;
            margin-right:10px;
        }
        #login_name{ width: 18px;height: 18px;background: url(images/user.png) no-repeat; position: absolute; top: 10px; left: 10px;}
        #login_code{ width: 18px;height: 22px;background: url(images/code.png) no-repeat; position: absolute; top: 8px; left: 10px;}
        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px white inset;
            -webkit-text-fill-color: #333;
        }
    </style>
<#if (topmap.systembase.bsetHotline)??>
    <link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
    <link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
    <script type="text/javascript" src="${basePath}/js/jquery.js"></script>
</head>
<body>
<div class="container">
    <div class="head2">
        <a href="${topmap.systembase.bsetAddress}"><img id="logo_pic" alt="" src="" style="height:45px;width:auto;"></a><h1>欢迎登录</h1>
    </div>
</div>
<div class="n_login_bg" style="background-position:center center; background-repeat: no-repeat;height:420px;">
    <div class="container" style="width:900px;">
        <div class="new_login clearfix">
            <div class="new_login_con">
                <div class="n_title clearfix">
                    <span>会员登录</span>
                    <p>还没有账号？马上<a href="${basePath}/register.html">免费注册</a></p>
                    <#--<p style="display:none;position: absolute;top: 380px;"><span style="color:red;font-size: 14px;">注：密码输入错误6次将会锁定账号</span></p>-->
                </div><!--n_title-->
                <div class="mt20">
                    <form id="login_form" name="" action="${basePath}/signin.html" method="post">
                        <div class="n_row" style="margin-bottom:25px;">
                            <input type="text" maxLength="40" name="username" value="${username!''}" placeholder="已验证手机/邮箱/用户名" class="n_text" id="log_user"/>
                            <span id="login_name"></span>
                            <div class="n_tips">您输入的用户名不正确</div>
                        </div>
                        <div class="n_row" style="margin-bottom:25px;">
                            <input  maxLength="20" type="password" name="password" placeholder="密码" class="n_text" id="log_code" oncopy="return false;" oncut="return false;" onpaste="return false" onfocus="removeError()"/>
                            <span id="login_code"></span>
                            <div class="n_tips">您输入的密码不正确</div>
                        </div>
                        <div class="n_row clearfix">
                            <a href="${basePath}/findpwd.html" class="fr">忘记密码？</a>
                            <input type="checkbox" name="remember" class="re"><label>&nbsp;&nbsp;记住账号</label>
                            <!--<input type="checkbox" name="remember" class="re "><label>&nbsp;&nbsp;自动登录</label>-->
                        </div>
                        <input type=hidden id="urlhide" value="${url}" />
                        <div class="n_row clearfix">
                            <input type="button" name="login"  id="login_btn"
                            <#if isTemp??&&isTemp=='1'>
                                   class="n_btn_a"
                            <#else>
                                   class="n_btn"
                            </#if>value="登 录"/>
                        <#if isTemp??&&isTemp=='1'>
                            <input type="button" name="suorder" id="suborder_btn" class="n_btn_a" style="margin-right:0; background:#cc3333;" value="一键下单" />
                        </#if>
                        </div><!--n_row-->
                    </form>
                    <div class="n_row">
                        <p class="f14 col6">你也可以使用以下账号登录：</p>
                        <div class="mt10" style="width:350px;">
                        <#if t??>
                            <#list t as tt >
                                <#if tt.authType == '1'>
                                    <a href="loginqq.html?preurl=${url}" class="mr20"><img src="images/q1.png"/></a><!--腾讯QQ-->
                                    <!--<a href="#" class="mr20"><img src="images/q2.png"/></a>--><!--支付宝-->
                                <#elseif tt.authType == '2'>
                                    <a href="loginsina.html?preurl=${url}" class="mr20"><img src="images/q3.png"/></a><!--新浪微博-->
                                <#elseif tt.authType == '7'>
                                    <a href="loginweixin.html?preurl=${url}" class="mr20"><img src="images/q4.png"/></a><!--微信-->
                                    <!--<a href="#"><img src="images/q5.png"/></a>--><!--人人-->
                                <#elseif tt.authType == '11'>
                                    <a href="logintaobao.html?preurl=${url}" class="mr20"><img src="images/q2.png"/></a><!--淘宝登陆-->
                                </#if>
                            </#list>
                        </#if>
                        </div>
                    </div><!--n_row-->

                <#if isTemp??&&isTemp=='1'>
                <form  action="order/suborder.html" method="post" class="suborder_form">
                    <#list box as b>
                        <input type="hidden" name="box" value="${b}">
                    </#list>
                    <input type="hidden" value="1" name="isTemp">
                </#if>
                </div><!--mt20-->
            </div><!--new_login_con-->
        </div><!--new_login-->
    </div><!--container-->
</div><!--n_login_bg-->
<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">
    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>


<script type="text/javascript">
//    $(document).ready(function(){
//        var random_bg=Math.floor(Math.random()*4+1);
//        var bg='url(images/bg_'+random_bg+'.jpg)';
//        $(".n_login_bg").css("background-image",bg);
//    });
    $(function(){
        $("input[type='text']").blur(function(){
            $(this).next().next().hide();
        });

        $.ajax({
            url: 'loadlogo.htm',
            success: function(data){
                $("#logo_pic").prop("src",data.logo.bsetLogo);
                $(".n_login_bg").css("background-image",'url('+data.logo.siteLoginImg+')');
                //$("#loginImg").prop("src",data.logo.siteLoginImg);
            }
        });

        $("#login_btn").click(function(){
            login();
        });

        $("#suborder_btn").click(function(){
            $(".suborder_form").submit();
        });
    });
function removeError(){
    $("#log_code").next().next().hide();

}
    function login(){
        if(checkInput()){
            var type="";
            if($(".re").prop("checked")){
                type="0";
            }
            //if($(".au").prop("checked")){
            //if($(".re").prop("checked")){
            //type="1";
            //}
            //}
            $("input[type='text']").next().next().hide();
            //var url="checklogin-"+$("#log_user").val()+"-"+$("#log_psd").val()+"-"+$("#urlhide").val()+type;
            $.ajax({
                url: "checklogin.htm",
                type:"POST",
                data:{username:$("#log_user").val(),
                    password:$("#log_code").val(),
                    url:$("#urlhide").val(),
                    type:type},
                    success: function(data){
                    if(data == 0){
                        $("#log_code").next().next().show().html('密码和账户不匹配，请重新输入');
                    }else if(data == 8){
                        $("#log_code").next().next().show().html('该账户已经被锁定，请在30分钟后再次登陆');
                    }else if(isNaN(data)){
                        if(typeof(data)=="string"){
                            window.location.href=data;
                        }else{
                            if(3<data.loginErrorCount && data.loginErrorCount<6){
                                $("#log_code").next().next().show().html('密码错误6次将会锁定账号，您还有'+(6-data.loginErrorCount)+'次机会');
                            }else if(data.loginErrorCount==6){
                                $("#log_code").next().next().show().html('该账户已经被锁定，请在30分钟后再次登陆');
                            }else{
                                $("#log_code").next().next().show().html('密码和账户不匹配，请重新输入');
                        }
                    }
                    }else if(data == 2){
                        $("#log_user").next().next().show().html('你输入的用户不存在，请重新输入');
                    } else if (data == 3) {
                        $("#log_user").next().next().show().html('你输入的用户已经冻结,请联系管理员解锁');
                    }
                }
            });
        }
    }

    function checkInput(){
        var flag=false;
        var x = $("#log_user").val();
        if(x == ''){
            $("#log_user").addClass('n_error');
            $("#log_user").next().next().html('请输入用户名');
            $("#log_user").next().next().show();
        }
        else{
            $("#log_user").removeClass('n_error');
            $("#log_user").next().next().hide();
            flag=true;
        }
        var flagP=false;
        x = $("#log_code").val();
        var regS=/\s/g;
        if(regS.test(x)){
            $("#log_code").addClass('n_error');
            // $("#log_code").next().addClass('tips_error');
            $("#log_code").next().next().html('请勿输入空格');
            $("#log_code").val("");
            $("#log_code").next().next().show();
        }
        else if(x == ''){
            $("#log_code").addClass('n_error');
            //$("#log_code").next().addClass('tips_error');
            $("#log_code").next().next().html('请输入密码');
            $("#log_code").next().next().show();
        }
        else{
            $("#log_code").removeClass('n_error');
            $("#log_code").next().next().hide();
            flagP=true;
        }
        return flag&&flagP;
    }
    function isUser(x){
        var reg = /^\w+[@\.]?$/;
        if((x.length >=6) && (x.length <= 20)){
            return true;
        }
        else{
            return false;
        }
    }
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
            login();
        }
    };
</script>
</body>
</html>