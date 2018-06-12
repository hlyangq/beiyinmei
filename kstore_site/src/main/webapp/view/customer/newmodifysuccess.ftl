<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>操作成功-${topmap.systembase.bsetName}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${topmap.seo.meteKey}">
<meta name="description" content="${topmap.seo.meteDes}">
<#assign basePath=request.contextPath>
<#if (topmap.systembase.bsetHotline)??>
	<link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
	<link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
<link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css" />
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
                    <div class="n-title">
                    <#if type??>
                        <#if (type=='pwd')>
                            修改密码
                        </#if>
                        <#if (type=='mobile') && cust.isMobile == '1'>
                            修改手机
                        <#elseif (type=='mobile') && cust.isMobile == '0'>
                            验证手机
                        </#if>
                        <#if (type=='email') && cust.isEmail == '1'>
                            修改邮箱
                        <#elseif (type=='email') && cust.isEmail == '0'>
                            验证邮箱
                        </#if>
                    </#if>
                    </div>
                    <div class="s-amend">
                        <div class="amend-step">
                            <div class="amend-step-3"></div>
                            <ul class="clearfix">
                                <li>验证身份</li>
                                <li><#if type??><#if (type=='pwd')>填写密码</#if><#if (type=='mobile')>填写手机</#if><#if (type=='email')>填写邮箱</#if></#if></li>
                                <li>完成</li>
                            </ul>
                            <form method="post" action="${basePath}/validate/modifypem.htm" id="upMobile">
                                <input value="${(type)!''}" type="hidden" id="e_type" name="type" />
                                <input type="hidden" id="CSRFToken" name="csrFToken" value="${CSRFToken!''}"/>
                            <#if (type=='pwd' )>
                                <div class="n_password ml30">
                                    <div class="n_set_over" style="margin-left: 190px;">
                                        <p class="gx_nin">恭喜您，重置密码成功！</p>
                                        <p class="col9 pt10">请牢记您新设置的密码。<a href="${topmap.systembase.bsetAddress}"  class="col0 f14">返回首页</a></p>
                                    </div>
                                </div>
                            <#elseif (type == 'mobile')>
                                <div class="n_password ml30">
                                    <div class="n_set_over" style="margin-left: 190px;">
                                        <p class="gx_nin">恭喜您，操作成功！</p>
                                        <div class="new-safe-level pt20">
                                            安全级别：
                                            <div class="n_per_bar" style="width: 100px;"><span style="width: <#if  cust.isEmail == '0' &&  cust.isMobile == '0'> 33%;<#elseif (cust.isEmail == '1' &&  cust.isMobile == '0')||(cust.isEmail == '0' &&  cust.isMobile == '1')>66% <#elseif cust.isEmail == '1' &&  cust.isMobile == '1'>100%</#if>"></span></div>
                                            <#if  cust.isEmail == '0' &&  cust.isMobile == '0'>
                                                <span style="color: #71b247;" class="pl20">低级</span>
                                                <span class="safe_bar low_sf"></span>
                                            <#elseif cust.isEmail == '1' &&  cust.isMobile == '0'>
                                                <span style="color: #71b247;" class="pl20">中级</span>
                                                <span class="safe_bar md_sf"></span>
                                            <#elseif cust.isEmail == '0' &&  cust.isMobile == '1'>
                                                <span style="color: #71b247;" class="pl20">中级</span>
                                                <span class="safe_bar md_sf"></span>
                                            <#elseif cust.isEmail == '1' &&  cust.isMobile == '1'>
                                                <span style="color: #71b247;" class="pl20">高级</span>
                                                <span class="safe_bar hg_sf"></span>
                                            </#if>
                                        </div>
                                        <p class="col6 pt10 f14">您的帐户安全级还能提升哦，快去<a style="color: red" href="${basePath}/customer/securitycenter.html">账户安全</a>完善安全设置吧！</p>
                                    </div>
                                </div>
                            <#elseif (type == 'email')>
                                <div class="n_password ml30">
                                    <div class="n_set_over" style="margin-left: 190px;">
                                        <p class="gx_nin"><#if ok??>恭喜您，邮箱绑定成功！<#else>恭喜您，邮件发送成功！</#if></p>
                                        <p class="col9 pt10"><a href="${topmap.systembase.bsetAddress}" class="col0 f14">返回首页</a></p>
                                    </div>
                                </div>
                            </#if>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
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
<script type="text/javascript" src="${basePath}/js/customer/validatepwd.js">
</script><script type="text/javascript" src="${basePath}/js/newapp.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/findcode.js"></script>
<script type="text/javascript" src="${basePath}/js/jsOperation.js"></script>

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
    $("#securitycenter").addClass("cur");
});
</script>
</body>
</html>
