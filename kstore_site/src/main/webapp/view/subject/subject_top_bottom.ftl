<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${topmap.systembase.bsetName}</title>
<#if (subject.seoKeyword)?? && (subject.seoKeyword?length>0)>
<meta name="Keywords" content="${subject.seoKeyword}-${topmap.seo.meteKey}">
<#else>
<meta name="Keywords" content="${topmap.seo.meteKey}">
</#if>
<#if (subject.seoDesc)?? && (subject.seoDesc?length>0)>
<meta name="description" content="${subject.seoDesc}-${topmap.seo.meteDes}">
<#else>
<meta name="description" content="${topmap.seo.meteDes}">
</#if>
<#assign basePath=request.contextPath>
<meta content="text/html; charset=UTF-8" http-equiv=Content-Type>
<#if (topmap.systembase.bsetHotline)??>
	<link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
	<link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/style.css" />
<style>
.containerbackground {background-image: url(${(subject.backgroundImg)!''});}
.backgroundYellow {
background-color:#eb6122;
}
.section_headerTop .slot_headerTop01 .siteNav li > a{height:21px;}
     
</style>
</head>

<body>
	<div>
		<#if (topmap.temp)??>
			<#if (topmap.temp.tempId==1)>
				<#include "../index/topnew.ftl">
			<#elseif (topmap.temp.tempId==3)>
				<#include "../index/newheader.ftl">
			<#elseif (topmap.temp.tempId==9)>
				<#include "../index/newheader4.ftl">
			<#elseif (topmap.temp.tempId==11)>
				<#include "../index/newheader6.ftl">	
			<#elseif (topmap.temp.tempId==10)>
				<#include "../index/newheader5.ftl">
			<#elseif (topmap.temp.tempId==12)>
				<#include "../index/newheader7.ftl">
			<#elseif (topmap.temp.tempId==13)>
				<#include "../index/newheader8s.ftl">
			<#elseif (topmap.temp.tempId==14)>
				<#include "../index/newheader9.ftl">
			<#elseif (topmap.temp.tempId==15)>
				<#include "../index/newheader10.ftl">
			<#elseif (topmap.temp.tempId==16)>
				<#include "../index/newheader11.ftl">
			<#elseif (topmap.temp.tempId==17)>
				<#include "../index/newheader12.ftl">
			<#elseif (topmap.temp.tempId==18)>
				<#include "../index/newheader13.ftl">
			<#elseif (topmap.temp.tempId==19)>
				<#include "../index/newheader14.ftl">
            <#elseif (topmap.temp.tempId==20)>
                <#include "../index/newheader15.ftl">
			<#elseif (topmap.temp.tempId==21)>
				<#include "../index/newheader21.ftl">
			<#else>
				<#include "../index/newheader3.ftl">
			</#if>
		</#if>
	</div>
	<div class="containerbackground">
	 ${(subject.content)!''}
	</div>
     
<script type="text/javascript">
$(document).ready(function(){
	$(".pro_sort").addClass("pro_sort_close");
	$("#navbg").addClass("backgroundYellow");
   
});
$(function(){
    $.ajax({ url:"${basePath}/getnavsort.htm", async:false ,success: function(data){
        $(".navLinks ul").find("li").each(function(){
            $(this).find("a").removeClass("on");
            if($(this).index()+1==data){
                $(this).find("a").addClass("on");
            }
        });
    }
    });
});
</script>
<#--引入底部
-->
	<#if (topmap.temp)??>
		<#if (topmap.temp.tempId==1)>
			<#include "../index/bottom.ftl">
		<#else>
		    <#include "../index/newbottom.ftl" />
		</#if>
	</#if>
</body>
</html>
