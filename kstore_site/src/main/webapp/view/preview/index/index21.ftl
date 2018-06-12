<!DOCTYPE html>
<html>
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
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>
<#if (topmap.systembase.bsetHotline)??>
    <link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
    <link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
    <style>
        .dropdown-menu {display:block!important;}
        .cont {display:block!important;}
        .customer_service:hover, .feedback:hover {background:#c33;}
        .customer_service:hover em, .feedback:hover em {font-size:12px; position:relative; top:3px; font-style:normal;}
        .customer_service b {background:url(${basePath}/index_seven/images/-01.png) #fff no-repeat center center!important;}
        .feedback b {background:url(${basePath}/index_seven/images/-02.png) #fff no-repeat center center!important;}
        
        .customer-box {
        position: absolute;
		background: #efefef;
		width: 146px;
		min-height: 150px;
		border: 1px solid #ccc;
		padding: 9px;
		right: 50px;
		display: none;
		}
		.sideBar .customer-box a {width:auto; height:auto; background-color:transparent; border:none; line-height:normal;}
		.close-cs {
		position: absolute!important;
		top: 10px;
		right: 10px;
		background: url(${basePath}/index_seven/images/agree_close1.gif) no-repeat;
		width: 10px!important;
		height: 10px!important;
		display: block;
		}
		.customer-box p {overflow:hidden; margin-top:10px;}
		.customer-box p .qq_name {float:right;}
		.customer-box p .qq_img {float:left;}
		.sideBar a span {font-size:12px!important; padding:2px 5px; width:28px!important; line-height:normal!important;}
    </style>
</head>
<body>
<#include "newheader21.ftl">
<#include "index21_ftl.ftl"/>

    </div><!--content-->
</div><!--wrapper-->
<#include "newbottom.ftl">
</body>
</html>

