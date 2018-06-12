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
    <link rel="stylesheet"href="${basePath}/css/pages.css"/>
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
    <link rel="stylesheet" href="${basePath}/css/receive.m.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
    <style>
        .cart-top {
            top: 5px !important;
        }

        label.error {
            display: block;
            line-height: 20px;
            padding-left: 18px;
            background: url('../images/ero.png') no-repeat left center;
            color: #E45050;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<#--一引入头部 <#include "/index/topnew.ftl" />
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
-->
<div style="">
    <div class="container clearfix pr">
        <div class="mini_head">
            <h1 class="logo">
                <a href="${topmap.systembase.bsetAddress}">
                    <img src="${topmap.systembase.bsetLogo}" alt="" style="width: 165px;height: 40px;"/>
                </a>
                <span>充值</span>
            </h1>
        </div>
    </div>

    <div style="background: #f5f5f5;">
        <div class="container clearfix pt20 pb10">
            <div class="recharge-box">
                <div class="title">
                    <p class="h2">充值到预存款</p>
                    <p class="light">当前预存款总额：<strong>￥${totalDeposit?string('0.00')} </strong></p>
                </div>
                <div class="body">
                    <form action="${basePath}/customer/deposit/recharge" id="rechargeForm" method="post"
                          target="_blank">
                        <div class="box">
                            <p class="h4">请选择充值方式：</p>
                            <ul class="types clearfix">
                                <input type="hidden" value="" name="payId" id="payId"/>

                            <#if map.payList??>
                                <#list map.payList as pay>
                                    <#if (pay.payType=='1' && pay.isOpen=='1')>
                                        <li title="${pay.payName}">
                                            <a
                                                <#if pay.payDefault=="1">
                                                        class="selected"
                                                </#if>
                                                        href="javascript:;">
                                                <img src="${basePath}/images/payment1.jpg" alt=""
                                                     onclick="changethis(this,${pay.payId});" alt="${pay.payName}">
                                            </a>
                                        </li>
                                    </#if>

                                        <#if (pay.payType=='2' && pay.isOpen=='1')><!-- 银联 -->
                                        <li title="${pay.payName}">
                                            <a
                                                <#if pay.payDefault=="1">
                                                        class="selected"
                                                </#if>
                                                        href="javascript:;">
                                                <img src="${basePath}/images/payment4.jpg" alt=""
                                                     onclick="changethis(this,${pay.payId});" alt="${pay.payName}">
                                            </a>
                                        </li>
                                    </#if>

                                        <#if (pay.payType=='4' && pay.isOpen=='1')><!-- 千米收银台 -->
                                        <li title="${pay.payName}">
                                            <a
                                                <#if pay.payDefault=="1">
                                                        class="selected"
                                                </#if>
                                                        href="javascript:;">
                                                <img src="${basePath}/images/payment5.jpg" alt=""
                                                     onclick="changethis(this,${pay.payId});" alt="${pay.payName}">
                                            </a>
                                        </li>
                                    </#if>

                                </#list>
                            </#if>

                            </ul>
                        </div>
                        <div class="box">
                            <p class="h4">充值金额：</p>
                            <div class="charge-form">
                                <input type="text" class="text" style="display: none"/>
                                <input type="text" class="text" placeholder="0.00" id="totalFee" name="totalFee"/>
                                <span>元</span>
                            </div>
                        <#--<p class="error" style="display: none;">金额必须为整数或小数，小数点后不超过2位</p>-->
                        </div>
                        <div class="box">
                            <a href="javascript:;" class="charge-btn" id="submitRecharge"
                               onclick="validateForm();">确认充值</a>
                        </div>
                    </form>
                </div>
                <div class="charge-tips">
                    <p class="h4">充值提示</p>
                    <p>1.充值成功后，预存款可能存在延迟现象，一般1到5分钟内到账，如有问题，请咨询客服；</p>
                    <p>2.充值金额必须大于0，且不得超过1,000,000元；</p>
                    <p>3.支付密码输错3次将锁定预存款账户，锁定后可充值，不可消费和提现，第二天系统自动解锁。</p>
                </div>
            </div>
        </div>
    </div>

    <div class="member-dialog dia1">
        <div class="member-dialog-body">
            <div class="title"><a href="javascript:;" onclick="cls()">×</a>提示</div>
            <div class="dia-tips">
                <p class="txt">
                    请确认是否已经完成支付
                </p>
            </div>
            <div class="dia-btn">
                <a class="btn active" href="${basePath}/deposit/mydeposit.htm">完成支付</a>
                <a class="btn" href="javascript:void(0)" onclick="cls()" id="payhelp">付款遇到问题</a>
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

<div class="mask"></div>

<script type="text/javascript" src="${basePath}/js/default.js"></script>
<script type="text/javascript" src="${basePath}/js/tab-switch.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/customer.js"></script>
<script type="text/javascript" src="${basePath}/js/jsOperation.js"></script>

<script type="text/javascript" src="${basePath}/js/common/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath}/js/common/jquery.validate_extend.js"></script>

<script>
    function changethis(obj, id) {
        $("#payId").val(id);
        $(".types li>a").removeClass("selected");
        $(obj).parent('a').addClass("selected");
    }


    var $form = $("#rechargeForm");
    $form.validate({
        rules: {
            payId: {
                required: true
            },
            totalFee: {
                required: true,
                money: true,
                min:0.01,
                max: 1000000,
            },
        },
        messages: {
            payId: {
                required: '请选择支付方式'
            },
            totalFee: {
                required: '请输入充值金额',
                money: '金额必须为整数或小数，小数点后不超过2位',
                max: '充值金额不可超过1,000,000元',
            },
        },
        errorPlacement: function (error, element) {
            $(element).parents('.box').append(error);
        },
    });

    $form.validate();

    function validateForm() {
        if ($form.valid()) {
            dia(1);
            $form.submit();
        }
    }


    $(function () {
        if ($('.types li>a.selected').size() == 0) {
            $('.types li:eq(0)>a>img').click();
        }
        else{
            $('.types li>a.selected').click();
        }

    });
</script>

</body>
</html>