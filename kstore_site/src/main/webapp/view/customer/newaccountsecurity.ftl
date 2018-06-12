<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户中心-${topmap.systembase.bsetName}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${topmap.seo.meteKey}">
<meta name="description" content="${topmap.seo.meteDes}">
<#assign basePath=request.contextPath>
<#if (topmap.systembase.bsetHotline)??>
	<link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
	<link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
    <link rel="stylesheet" href="${basePath}/css/receive.m.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css" />

    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>

    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
</head>
<body>
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

<#include "newtop.ftl"/>

    <div style="background: #f5f5f5;">
        <div class="container clearfix pt20 pb10">
           <!--new_member_left-->
            <#include "newleftmenu.ftl"/>
            <div class="new_member-right">
                <div class="new_order_list">
                    <div class="n-title">账户安全</div>
                    <div class="new-safe-level pt20">
                        安全级别：
                        <div class="n_per_bar" style="width: 100px;"><span style="width: <#if  customer.isEmail == '0' &&  customer.isMobile == '0'>33%;<#elseif (customer.isEmail == '1' &&  customer.isMobile == '0')||(customer.isEmail == '0' &&  customer.isMobile == '1')>66% <#elseif customer.isEmail == '1' &&  customer.isMobile == '1'>100%</#if>"></span></div>
                    <#if  customer.isEmail == '0' &&  customer.isMobile == '0'>
                        <span style="color: #71b247;" class="pl20">低级</span>
                        <span class="safe_bar low_sf"></span>
                    <#elseif customer.isEmail == '1' &&  customer.isMobile == '0'>
                        <span style="color: #71b247;" class="pl20">中级</span>
                        <span class="safe_bar md_sf"></span>
                    <#elseif customer.isEmail == '0' &&  customer.isMobile == '1'>
                        <span style="color: #71b247;" class="pl20">中级</span>
                        <span class="safe_bar md_sf"></span>
                    <#elseif customer.isEmail == '1' &&  customer.isMobile == '1'>
                        <span style="color: #71b247;" class="pl20">高级</span>
                        <span class="safe_bar hg_sf"></span>
                    </#if>
                        <em>建议您启动全部安全设置，以保障账户及资金安全。</em>
                    </div>
                    <div class="new-change-code clearfix">
                        <div class="can-tit fl tit_01">
                            登录密码
                        </div>
                        <div class="can-det">
                            互联网账号存在被盗风险，建议您定期更改密码以保护账户安全。
                            <a class="fr" href="${basePath}/validate/validateidentity.html?type=pwd">修改</a>
                        </div>
                    </div>
                    <div class="new-change-code clearfix">
                        <div class="can-tit fl tit_02">
                            邮箱验证
                        </div>
                        <div class="can-det">
                        <#if customer.isEmail == '1'>
                            <#if customer.infoEmail??>

                                <#if (customer.infoEmail?trim)?length==0>
                                    验证后，可用于快速找回登录密码。
                                <#else>
                                <#--<#assign email="${customer.infoEmail?substring(1,customer.infoEmail?index_of('@')+1)}" />
                                <#assign emailc="${customer.infoEmail?replace(email,'*****')}" /> ${emailc} -->
                                    您的验证邮箱:${customer.infoEmail?substring(0,1)}*****${customer.infoEmail?substring(customer.infoEmail?index_of('@'),customer.infoEmail?length)}
                                </#if>
                            <#else>
                                验证后，可用于快速找回登录密码。
                            </#if>

                        <#else>
                            验证后，可用于快速找回登录密码。
                        </#if>
                        <#if !isThirdLogin??>
                            <#if customer.isEmail == '1'&&customer.infoEmail??&& customer.infoEmail!=''>
                                <a class="fr" href="${basePath}/validate/validateidentity.html?type=email">修改</a>
                            <#else>
                                <a class="fr" href="${basePath}/validate/validateidentity.html?type=email">立即验证</a>
                            </#if>
                        <#else>
                            <#if customer.isEmail == '1'&&customer.infoEmail??&& customer.infoEmail!=''>
                                <a class="fr" href="${basePath}/validate/reirectpem.html?type=email">修改</a>
                            <#else>
                                <a class="fr" href="${basePath}/validate/reirectpem.html?type=email">立即验证</a>
                            </#if>
                        </#if>
                        </div>
                    </div>
                    <div class="new-change-code clearfix">
                        <div class="can-tit fl tit_03">
                            手机验证
                        </div>
                        <div class="can-det">
                        <#if customer.isMobile == '1'>
                            <#if customer.infoMobile??>
                                <#if (customer.infoMobile?trim)?length==0>
                                    验证后，可用于快速找回登录密码。
                                <#else>
                                    <#assign mo="${customer.infoMobile?substring(3,customer.infoMobile?length-3)}" />
                                    <#assign mob="${customer.infoMobile?replace(mo,'*****')}" />
                                    您的验证手机:${mob}
                                </#if>
                            <#else>
                                验证后，可用于快速找回登录密码。
                            </#if>
                        <#else>
                            验证后，可用于快速找回登录密码。
                        </#if>
                        <#if !isThirdLogin??>
                            <#if customer.isMobile == '1'&& customer.infoMobile??&& customer.infoMobile!=''>
                                <a class="fr" href="${basePath}/validate/validateidentity.html?type=mobile">修改</a>
                            <#else>
                                <a class="fr" href="${basePath}/validate/validateidentity.html?type=mobile">立即验证</a>
                            </#if>
                        <#else>
                            <#if customer.isMobile == '1'&& customer.infoMobile??&& customer.infoMobile!=''>
                                <a class="fr" href="${basePath}/validate/reirectpem.html?type=mobile">修改</a>
                            <#else>
                                <a class="fr" href="${basePath}/validate/reirectpem.html?type=mobile">立即验证</a>
                            </#if>
                        </#if>
                        </div>
                    </div>

                    <div class="new-change-code clearfix">
                        <div class="can-tit fl tit_03">
                            支付密码
                        </div>
                        <div class="can-det">
                            <#if customer.isMobile == '1'>
                                <#if customer.infoMobile??>
                                    <#assign mo="${customer.infoMobile?substring(3,customer.infoMobile?length-3)}" />
                                    <#assign mob="${customer.infoMobile?replace(mo,'*****')}" />
                                    <#--您的验证手机:${mob}-->
                                    设置支付密码可用于进行预存款支付。
                                <#else>

                                </#if>
                            <#else>
                                设置支付密码可用于进行预存款支付。
                            </#if>

                            <#if customer.isMobile == '1' && customer.infoMobile?? && customer.infoMobile!=''>
                                <!-- 判断账户信息有没有密码，没有set,有change -->
                                <#if deposit.payPassword??&&deposit.payPassword!=''>
                                    <a class="fr" href="${basePath}/deposit/changepaypasswordview.htm">修改</a>
                                    <!--
                                    <a class="fr" href="javascript:;" onclick="dia(1)">修改</a>
                                    -->
                                <#else >
                                    <a class="fr" href="${basePath}/deposit/setpaypasswordview.htm">设置</a>
                                    <!--
                                    <a class="fr" href="javascript:;" onclick="dia(1)">设置</a>
                                    -->
                                </#if>
                            <#else><!-- 没有手机验证手机 -->
                                <a class="fr" href="javascript:;" onclick="dia(1)">设置</a>
                            </#if>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="member-dialog dia1">
        <div class="member-dialog-body">
            <div class="title"><a href="javascript:;" onclick="cls()">×</a>提示</div>
            <div class="dia-tips">
                <p class="txt">您还没有验证手机，请先验证手机再设置支付密码</p>
            </div>
            <div class="dia-btn">
                <a href="${basePath}/validate/validateidentity.html?type=mobile" class="btn active">去验证</a>
                <a href="javascript:;" onclick="cls()" class="btn">取消</a>
            </div>
        </div>
    </div><!--/dialog-->

    <#--引入底部 <#include "/index/bottom.ftl" /> -->
    <#if (topmap.temp)??>
		<#if (topmap.temp.tempId==1)>
			<#include "../index/bottom.ftl">
		<#else>
		    <#include "../index/newbottom.ftl" />
		</#if>
	</#if>

<script type="text/javascript" src="${basePath}/js/default.js"></script>
<script type="text/javascript" src="${basePath}/js/jcarousellite_1.0.1.js"></script>
<script type="text/javascript" src="${basePath}/js/tab-switch.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/findcode.js"></script>
<script type="text/javascript" src="${basePath}/js/jsOperation.js"></script>
<script type="text/javascript" src="${basePath}/js/newapp.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$('.item_title').each(function(){
		$(this).click(function(){
			$(this).next().toggle('fast',function(){
				if($(this).is(':visible')){
					$(this).prev().removeClass('up');
					$(this).prev().addClass('down');
				}
				else{
					$(this).prev().removeClass('down');
					$(this).prev().addClass('up');
				}
			});
		});
	});
    $(".pro_sort").addClass("pro_sort_close");
    //$(".new_member_left div:eq(3) ul li:eq(1)").addClass("cur");
    
});
</script>
</body>
</html>
