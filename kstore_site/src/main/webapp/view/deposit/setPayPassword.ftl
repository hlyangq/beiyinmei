<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我的预存款-${topmap.systembase.bsetName}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="Keywords" content="${topmap.seo.meteKey}">
    <meta name="description" content="${topmap.seo.meteDes}">
<#assign basePath=request.contextPath>
<#if (topmap.systembase.bsetHotline)??>
    <link rel="Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
    <link rel="Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
    <style>
        .timeleft{
            color: red;
        }
    </style>
</head>
<body class="grey_bg" style="padding-top:30px;">

<#--一引入头部 <#include "/index/topnew.ftl" />  -->
<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==8)>
        <#include "../index/newtop3.ftl">
    <#elseif (topmap.temp.tempId==9)>
        <#include "../index/newtop4.ftl">
    <#elseif (topmap.temp.tempId==10)>
        <#include "../index/newtop7.ftl">
    <#elseif (topmap.temp.tempId==11)>
        <#include "../index/newtop6.ftl">
    <#elseif (topmap.temp.tempId==12)>
        <#include "../index/newtop7.ftl">
    <#elseif (topmap.temp.tempId==13)>
        <#include "../index/newtop8.ftl">
    <#elseif (topmap.temp.tempId==14)>
        <#include "../index/newtop9.ftl">
    <#elseif (topmap.temp.tempId==15)>
        <#include "../index/newtop8.ftl">
    <#elseif (topmap.temp.tempId==16)>
        <#include "../index/newtop11.ftl">
    <#elseif (topmap.temp.tempId==17)>
        <#include "../index/newtop12.ftl">
    <#elseif (topmap.temp.tempId==18)>
        <#include "../index/newtop13.ftl">
    <#elseif (topmap.temp.tempId==19)>
        <#include "../index/newtop14.ftl">
    <#elseif (topmap.temp.tempId==20)>
        <#include "../index/newtop15.ftl">
    <#elseif (topmap.temp.tempId==21)>
        <#include "../index/newtop21.ftl">
    <#else>
        <#include "../index/newtop.ftl"/>
    </#if>
</#if>

<#include "../customer/newtop.ftl"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/receive.m.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css"/>
<div style="background: #f5f5f5;">
    <div class="container clearfix pt20 pb10">
    <#include "../customer/newleftmenu.ftl" />
        <div class="new_member-right">
            <div class="safety_con">
                <div class="n-title">设置支付密码</div>
                <div class="s-amend">
                    <div class="amend-step">
                        <div class="amend-step-1 ac-step-01"></div>
                        <ul class="clearfix">
                            <li>设置新密码</li>
                            <li>完成</li>
                        </ul>
                    </div>
                </div>

                <form action="/deposit/setpaypassword.htm" method="post" onsubmit="return validateForm()">
                    <div class="n_password">
                        <div class="n_item clearfix mb20">
                            <span class="label fl">新密码：</span>
                            <div class="fl" id="typePwd">
                                <input type="text" class="" id="password" name="password" placeholder="请设置新支付密码" onfocus="this.type='password'">
                                <div class="pwd_tip vd_tip m_tip tips" style="color:#cc0000"></div>
                            </div>
                        </div>
                        <div class="n_item clearfix mb20">
                            <span class="label fl">确认密码：</span>
                            <div class="fl" id="reTypePwd">
                                <input type="text" class="" id="repassword" name="repassword" placeholder="请再次输入新支付密码" onfocus="this.type='password'">
                                <div class="pwd_tip vd_tip m_tip tips" style="color:#cc0000"></div>
                            </div>
                        </div>

                        <div class="n_item clearfix mb20">
                            <span class="label fl">验证码：</span>
                            <div class="fl" id="yzcode">
                                <input type="text" name="mobileCode" id="code" placeholder="请输入验证码">
                                <a tabindex="1" id="sendCode" class="hq_code disabled" onclick="sendValidateCode();">获取短信验证码</a>
                                <div id="m_tip" class="m_tip vd_tip" style="display:none;">
                                    校验码已发出，请注意查收短信，如果没有收到，您可以在<span class="timeleft" style="color: rgb(228, 57, 60); font-weight: bold;">59</span>秒后要求系统重新发送
                                </div>
                                <div class="pwd_tip vd_tip m_tip tips" style="color:#cc0000">${msg!''}</div>
                            </div>
                        </div>

                        <div class="n_item clearfix mb20">
                            <span class="label fl"></span>
                            <button class="nw-submit" type="submit" tabindex="4" >提交</button>
                        </div>
                    </div>
                </form>


            </div>
        </div>
    </div>
</div>



<#--引入底部 <#include "/index/bottom.ftl" />  -->
<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">
    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>

<div class="mask">

</div>

<script>

    function countDown(){
        var time = $(".timeleft").text();
        $(".timeleft").text(time - 1);
        if (time == 1) {
            //
            $("#sendCode").prop("disabled",false);
            $("#m_tip").hide();
            $(".timeleft").text(59);
        } else {
            setTimeout(countDown, 1000);
        }
    }

    /*发送验证码*/
    function sendValidateCode() {
        var defer = $.ajax({
            url:'/deposit/sendvalidatecode.htm',
        });
        defer.done(function(data){
            if(data==1) {
                $("#m_tip").show();
                setTimeout(countDown, 1000);
                $("#sendCode").attr("disabled","disabled");
            }
            else if (data ==2) {
                $(".tips").show();
                $("#yzcode .tips").html("验证码错误").css("color","#dd6330");
                $("#sendCode").removeAttr("disabled");
            }
            else if(data==0){
                //网络异常
                $("#yzcode").parent().addClass('error');
                $("#yzcode .tips").html("网络异常").show();
                $("#sendCode").removeAttr("disabled");
            }else if(data==-1){
                $("#yzcode .tips").html("60秒内只能获取一次验证码,请稍后重试").show();
            }
        });
    }
    function validateForm(){
        var pwdVal = $('#password').val();
        var repwdVal = $('#repassword').val();
        //正则表达式匹配输入密码
        var r = /^[\x21-\x7E]{6,20}$/.test(pwdVal);
        if(!r){
            $("#typePwd .tips").text("密码必须是6-20位数字或字母或符号组成").show();
            $("#typePwd .correct").hide();
            return false;
        }else{
            $("#typePwd .tips").hide();
            $("#typePwd .correct").show();
        }
        if(pwdVal!=repwdVal){
            $("#reTypePwd .tips").text('两次输入的密码不一致').show();
            $("#reTypePwd .correct").hide();
            return false;
        }else{
            $("#reTypePwd .tips").hide();
            $("#reTypePwd .correct").show();
        }

        var code = $("#code").val();
        if(!code){
            $("#yzcode .tips").text("请输入验证码").show();
            return false;
        }else{
            $("#yzcode .tips").hide();
        }
        return true;
    }

    function bindEvent() {
        //移除焦点时候
        $("#code").blur(function(){
            var code = $("#code").val();
            if(!code){
            }else{
                $("#yzcode .tips").text("").hide();
            }
        });

        $('#password').blur(function(){

            var pwdVal = $('#password').val();
            var r = /^[\x21-\x7E]{6,20}$/.test(pwdVal);
            if(!r){
                $("#typePwd .tips").parent().parent().addClass('error');
                $("#typePwd .tips").text("密码必须是6-20位数字或字母或符号组成").show();
                $("#typePwd .correct").hide();
            }else{
                $("#typePwd .tips").parent().parent().removeClass('error');
                $("#typePwd .correct").show();
                $("#typePwd .tips").text("").hide();
            }
        });
        $('#repassword').blur(function(){
            var pwdVal = $('#password').val();
            var repwdVal = $('#repassword').val();
            if(pwdVal!=repwdVal){
                $("#reTypePwd .tips").text('两次输入的密码不一致').show();
                $('#repassword').parent().parent().addClass('error');
                $("#reTypePwd .correct").hide();
            }else{
                $("#reTypePwd .correct").show();
                $("#reTypePwd .tips").text('').hide();
                $('#repassword').parent().parent().removeClass('error');
            }
        });
    }
    bindEvent();
    $("#securitycenter").addClass("cur");
</script>

</body>
</html>