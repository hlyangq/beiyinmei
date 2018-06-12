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
    <link rel="stylesheet" href="${basePath}/index_fifteen/css/style.css"/>
    <#if (topmap.systembase.bsetHotline)??>
    <link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
	<#else>
    <link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
	</#if>
    <style>
        .dropdown-menu {display:block!important;}
    </style>
</head>
<body>
<#include "newheader15.ftl"/>
<#include "index15.html"/>
<div class="sj_share">
            <div class="sj_share_left">
                <div class="title">晒家分享</div>
                <div class="details">
                <#if shares?? && (shares?size>0)>
                    <ul>
                    <#list shares as share>
                    <#if (share_index>=0) &&(share_index<6)>
                        <li>
                            <div class="left s-img">
                                <a href="${basePath}/share/detail-${share.shareId?c}.html" target="_blank"><img src="${(share.goodsImg)!''}" width="120" height="120"></a>
                            </div>
                            <div class="right r-comment">
                                <a href="${basePath}/share/detail-${share.shareId?c}.html" class="title" target="_blank">
                                <#if (share.shareTitle?length>14)>
                                		${share.shareTitle?substring(0,14)}
                                <#else>
                                		${share.shareTitle!''}
                                </#if>
                                </a>
                                <div class="com-detail">
                                    <span class="z_dot"></span>
                                  <#if (share.shareContent?length>28)>
                                		${share.shareContent?substring(0,28)}
                            	  <#else>
                                		${share.shareContent!''}
                            	  </#if>
                                    <span class="r_dot"></span>
                                </div>
                                <a href="${basePath}/share/detail-${share.shareId?c}.html" class="right" target="_blank">详细&gt;&gt;</a>
                            </div>
                        </li>
                     </#if>
                     </#list>
                    </ul>
                </#if>
                </div>
            </div><!--sj_share_left-->
            <div class="sj_share_rig">
                <div class="title">${temp.standby5!'热门活动'}</div>
                <div class="details">
                    <div class="ac-con">
                    <#if onepages?? && (onepages?size>0)>
                        <ul>
                        	<#list onepages as op>
                        	<#if (op_index>=0) && (op_index<6)>
                            <li>
                                <a href="${basePath}/onepage/${op.infoOPId?c}.html" target="_blank"><img src="${op.imgSrc!''}" width="110" height="110"/></a>
                                <p><a href="${basePath}/onepage/${op.infoOPId?c}.html" target="_blank">${op.title!''}</a></p>
                                <div>${op.description!''}</div>
                            </li>
                           	</#if>
                            </#list>
                            
                        </ul>
                    </#if>
                    </div>
                </div>
            </div><!--sj_share_rig-->
        </div><!--sj_share-->
    </div><!--content-->
</div><!--wrapper-->
<#include "newbottom.ftl"/>
</body>