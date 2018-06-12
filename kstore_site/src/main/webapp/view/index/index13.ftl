<!doctype html>
<html ng-app>
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>${topmap.seo.mete!''}</title>
    <!-- SHOP QQ LOGIN -->
    <meta property="qc:admins" content="${(topmap.qqCode.authClientCode)!''}" />
    <!-- SINA LOGIN -->
    <meta property="wb:webmaster" content="${(topmap.sinaCode.authClientCode)!''}" />
    <meta name="description" content="${(topmap.seo.meteDes)!''}">
    <meta name="keywords" content="${(topmap.seo.meteKey)!''}">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="${basePath}/index_thirteen/css/header.css"/>
    <link rel="stylesheet" href="${basePath}/index_thirteen/css/style.css"/>
    <#if (topmap.systembase.bsetHotline)??>
    <link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
	<#else>
    <link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
	</#if>
    <style>
        .dropdown-menu {display:block!important;}
        html,body{ background: #f9f9f9;}
        .sideBar span {font-size: 12px!important;
            padding: 2px 5px;
            width: 28px!important;
            line-height: normal!important}
    </style>
</head>
<body>
<#include "newheader13.ftl">
<#include "index13.html"/>
<#include "newbottom.ftl">
</body>
</html>