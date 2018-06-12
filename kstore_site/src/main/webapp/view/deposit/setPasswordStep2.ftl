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
    <link rel="stylesheet" type="text/css" href="${basePath}/css/receive.m.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css"/>
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

<div style="background: #f5f5f5;">
    <div class="container clearfix pt20 pb10">
    <#include "../customer/newleftmenu.ftl" />
        <div class="new_member-right">
            <div class="safety_con">
                <div class="n-title">修改密码</div>
                <div class="s-amend">
                    <div class="amend-step">
                        <div class="amend-step-2"></div>
                        <ul class="clearfix">
                            <li>验证身份</li>
                            <li>重置密码</li>
                            <li>重置成功</li>
                        </ul>
                        <div class="n_password">
                            <form action="/deposit/resetpaypwd.htm" method="post" onsubmit="return validateForm();">
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
                                    <span class="label fl">&nbsp;</span>
                                    <button type="submit" class="nw-submit sub_btn free">下一步</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
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
    function validateForm() {
        //var valid = '';
        debugger;
        var pwdVal = $('#password').val();
        var repwdVal = $('#repassword').val();
        //正则表达式匹配输入密码
        var r = /^[\x21-\x7E]{6,20}$/.test(pwdVal);
        if(!r){
            $("#typePwd .tips").text("密码必须是6-20位数字或字母或符号组成").show();
            $("#typePwd .correct").hide();
            $("#typePwd").parent().addClass("error");
            return false;
        }else{
            $("#typePwd .tips").text('').hide();
            $("#typePwd").parent().removeClass("error");
            $("#typePwd .correct").show();
        }
        if(pwdVal!=repwdVal){
            $("#reTypePwd .tips").text('两次输入的密码不一致').show();
            $("#reTypePwd").parent().addClass("error");
            $("#reTypePwd .correct").hide();
            return false;
        }else{
            $("#reTypePwd .tips").text('').hide();
            $("#reTypePwd").parent().removeClass("error");
            $("#reTypePwd .correct").show();
        }
        return true;
    }
    //事件处理
    function bindEvent() {
        $('#password').blur(function(){
            var pwdVal = $('#password').val();
            var r = /^[\x21-\x7E]{6,20}$/.test(pwdVal);
            if(!r){
                $("#typePwd .tips").text("密码必须是6-20位数字或字母或符号组成").show();
                $("#typePwd .correct").hide();
                $("#typePwd").parent().addClass("error");
            }else{
                $("#typePwd .correct").show();
                $("#typePwd .tips").text("").hide();
                $("#typePwd").parent().removeClass("error");
            }
        });
        $('#repassword').blur(function(){
            var pwdVal = $('#password').val();
            var repwdVal = $('#repassword').val();
            if(pwdVal!=repwdVal){
                $("#reTypePwd .tips").text('两次输入的密码不一致').show();
                $("#reTypePwd .correct").hide();
                $("#reTypePwd").parent().addClass("error");
            }else{
                $("#reTypePwd .correct").show();
                $("#reTypePwd .tips").text('').hide();
                $("#reTypePwd").parent().removeClass("error");
            }
        })
    }
    bindEvent();
    $("#securitycenter").addClass("cur");
</script>

</body>
</html>