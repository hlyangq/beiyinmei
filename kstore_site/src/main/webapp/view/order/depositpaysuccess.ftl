<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="Keywords" content="${topmap.seo.meteKey}">
    <meta name="description" content="${topmap.seo.meteDes}">
<#assign basePath=request.contextPath>
    <title>${topmap.systembase.bsetName}</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/base.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/css/base.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css"/>
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>
    <link rel="stylesheet" href="${basePath}/css/receive.m.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
<#if (topmap.systembase.bsetHotline)??>
    <link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
    <link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
    <link rel="stylesheet" type="text/css" href="${basePath}/index_two/css/header.css" />
    <style>
        .agreement_dia {
            width: 910px!important;
            height: auto;
            border: 5px solid rgba(238,238,238,.5);
            padding: 0;
        }
        .agreement_dia .dia_tit {
            background: #eee;
            text-align: center;
            font-size: 14px;
            font-weight: 700;
            color: #666;
        }
        .agreement_dia .dia_close {
            position: absolute;
            top: 8px;
            right: 20px;
            margin-top: 0;
            background: url(${basePath}/images/agree_close.gif) no-repeat;
        }
    </style>
</head>

<body>
<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==8)>
        <#include "../index/newtop3.ftl">
    <#elseif (topmap.temp.tempId==9)>
        <#include "../index/newtop4.ftl">
    <#elseif (topmap.temp.tempId==10)>
        <#include "../index/newtop5.ftl">
    <#elseif (topmap.temp.tempId==11)>
        <#include "../index/newtop6.ftl">
    <#elseif (topmap.temp.tempId==12)>
        <#include "../index/newtop7.ftl">
    <#elseif (topmap.temp.tempId==13)>
        <#include "../index/newtop8.ftl">
    <#elseif (topmap.temp.tempId==14)>
        <#include "../index/newtop9.ftl">
    <#elseif (topmap.temp.tempId==15)>
        <#include "../index/newtop10.ftl">
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

<div class="container clearfix pr">
    <div class="mini_head">
        <h1 class="logo">
            <a href="#">
                <img src="${topmap.systembase.bsetLogo}" alt="" />
            </a>
        </h1>
    </div>
</div>

<!-- 未支付情况 -->
<#if order??>
<div style="background: #f5f5f5;min-height:600px;">
    <div class="container clearfix pt20 pb10">
        <div class="order_submit_success clearfix">
            <div class="total fr">
                <p>应付总额：<strong>￥${totalPrice?string("0.00")}</strong></p>
            </div>
            <div class="info fl">
                <div class="words">
                    <h2>订单提交成功！我们会尽快为您发货！</h2>
                    <p><strong>订单编号：${order.orderCode}</strong></p>
                    <p class="count">
                        <span id="countNum">3</span>
                        秒后自动跳转到订单详情……
                        <a href="/customer/detail-${order.orderId}.html">立即查看&gt;&gt;</a>
                    </p>
                </div>
            </div>
            <div class="clr"></div>
        </div>
    </div>
</div>
</#if>

<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">
    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>

<script type="text/javascript" src="${basePath}/js/minShopping.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/customer.js"></script>
<script type="text/javascript" src="${basePath}/js/default.js"></script>

<script type="text/javascript">
    $(function(){
        var countNum = 3;//初始秒数
        var countInterval = setInterval(function(){
            if(countNum>0){
                countNum--;
                $('#countNum').text(countNum);
            }
            else{
                clearInterval(countInterval);
                window.location.href='/customer/detail-${order.orderId}.html';
            }
        },1000);
    });
</script>

</body>
</html>