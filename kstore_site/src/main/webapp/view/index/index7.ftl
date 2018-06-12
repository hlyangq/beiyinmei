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
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
<#if (topmap.systembase.bsetHotline)??>
    <link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
    <link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
    <style>
        .dropdown-menu {display:block!important;}
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
        .onlineServiceQQ {height:100px;overflow-y:auto;}
        /* 设置滚动条的样式 */
        .onlineServiceQQ::-webkit-scrollbar {
            width:5px;
        }
        /* 滚动槽 */
        .onlineServiceQQ::-webkit-scrollbar-track {
            -webkit-box-shadow:inset006pxrgba(0,0,0,0.3);
            border-radius:0;
        }
        /* 滚动条滑块 */
        .onlineServiceQQ::-webkit-scrollbar-thumb {
            border-radius:0;
            background:rgba(0,0,0,0.1);
            -webkit-box-shadow:inset006pxrgba(0,0,0,0.5);
        }
    </style>
</head>
<body>
<#include "newheader7.ftl">
<#include "index7.html"/>

<div class="sj_share clearfix">
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
<#include "newbottom.ftl">
</body>
</html>

