<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 账户明细页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <meta content="telephone=no" name="format-detection">
<#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/idangerous.swiper.js"></script>
    <script src="${basePath}/js/dataformat.js"></script>
</head>
<body>

<div class="fixed_header">
    <div class="tabs c3 row">
        <a href="javascript:;" data-val="0" class="active">全部</a>
        <a href="javascript:;" data-val="1">收入</a>
        <a href="javascript:;" data-val="2">支出</a>
    </div>
</div>
<input id="status" value="0" type="hidden">
<input id="type" value="0" type="hidden">
<input type="hidden" id="basePath" value="${basePath}">
<div class="content-home" style="padding-bottom:0;">

    <div class="income_details member_box">

        <div class="content-slide">
            <div class="income_list">
            <#if (pb.list?size!=0)>
                <#list pb.list as trade>
                <div class="income_item">
                    <dl>

                            <#if trade.orderType == '0'>
                            <dt><span>
                                在线充值
                            </span></dt>
                                <dd><span class="green">+${trade.orderPrice?string("0.00")}</span></dd>
                            <#elseif trade.orderType == '1'>
                                <dt><span>
                                订单退款
                            </span></dt>
                                <dd><span class="green">+${trade.orderPrice?string("0.00")}</span></dd>
                            <#elseif trade.orderType == '2'>
                                <dt><span>
                                线下提现
                            </span></dt>
                                <dd><span class="red">-${trade.orderPrice?string("0.00")}</span></dd>
                            <#elseif trade.orderType == '3'>
                                <dt><span>
                                订单消费
                            </span></dt>
                                <dd><span class="red">-${trade.orderPrice?string("0.00")}</span></dd>
                            </#if>


                    </dl>
                    <dl>
                        <dt><span class="light">${trade.createTime?string("yyyy-MM-dd HH:mm:ss")}</span></dt>
                        <dd><span class="light">
                            <#if trade.orderStatus??>
                                <#if trade.orderStatus == '0'>
                                    待审核
                                <#elseif trade.orderStatus == '1'>
                                    打回
                                <#elseif trade.orderStatus == '2'>
                                    已通过
                                <#elseif trade.orderStatus == '3'>
                                    确认
                                <#elseif trade.orderStatus == '4'>
                                    已完成
                                <#elseif trade.orderStatus == '5'>
                                    未支付
                                <#elseif trade.orderStatus == '6'>
                                    充值成功
                                <#elseif trade.orderStatus == '7'>
                                    充值失败
                                <#elseif trade.orderStatus == '8'>
                                    已取消
                                </#if>
                            <#elseif trade.orderType == '3'>
                                已完成
                            </#if>
                            </span></dd>
                    </dl>
                </div>
                </#list>
            </#if>
            </div>
        </div>
        <div class="list-loading" style="display:none" id="showmore">
            <#--<img alt="" src="${basePath}/images/loading.gif">
            <span>加载中……</span>-->
        </div>
        <div class="tips">
        <#if !pb?? ||  (pb.list?size=0)>
            <div class="no_tips" id="no">
                <p>没有交易记录！</p>
            </div>
        </#if>
        </div>
    </div>

</div>

<#include '/common/smart_menu.ftl' />

<script>
    var mySwiper = new Swiper('.swiper-container',{
        slidesPerView: 'auto'
    });
</script>
<script src="${basePath}/js/customer/tradeinfo.js"></script>
</body>
</html>