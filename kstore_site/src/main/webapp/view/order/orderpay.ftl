<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="Keywords" content="${topmap.seo.meteKey}">
    <meta name="description" content="${topmap.seo.meteDes}">
<#assign basePath=request.contextPath>
    <title>${topmap.systembase.bsetName}</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css"/>
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
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

<body onkeydown="backspace(event)">
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
            <a href="${topmap.systembase.bsetAddress}">
                <img src="${topmap.systembase.bsetLogo}" alt="" style="width: 165px;height: 40px;"/>
            </a>
        </h1>
    </div>
</div>

<!-- 未支付情况 -->
<#if order?? && order.orderStatus!='1'>
<div style="background: #f5f5f5;min-height:600px;">
    <div class="container clearfix pt20 pb10">
        <div class="order_info_simple clearfix">
            <div class="total fr">
                <p>应付总额：<strong>￥${order.orderPrice?string("0.00")}</strong></p>
            </div>
            <div class="info fl">
                <div class="words">
                    <p>
                        <strong>
                            商品名称：
                            <#if order.orderGoodsList??>
                                <#list order.orderGoodsList as good>
                                    <#if good_index==0>
                                    ${good.goodsInfoName!''}
                                    </#if>

                                </#list>
                                <#if order?? && order.orderGoodsList?? && (order.orderGoodsList?size>1)>
                                    &nbsp;等多件
                                </#if>
                            </#if>
                        </strong>
                    </p>
                    <p><strong>订单编号：${order.orderCode}</strong></p>
                    <p><strong><a href="/customer/detail-${order.orderId}.html" target="_blank">订单详情&gt;&gt;</a></strong></p>
                </div>
            </div>
            <div class="clr"></div>
        </div>
        <div class="payment-box">
            <form action="payorder.html" class="payOrder" method="post" target="_blank">

                <!-- 阻止自动enter 提交-->
                <input type="text" name="stopenter" style="display: none">
                <input type="text" name="stopenter1" style="display: none">

                <input type="hidden" value="${order.orderCode}" name="orderCode"/>
                <input type="hidden" value="${order.orderId}" name="orderId" id="orderId"/>
                <div class="box">
                    <p class="h4">请选择支付方式：</p>
                    <ul class="types clearfix">
                        <#if payList??>
                            <#list payList as pay>
                                <#if pay.payDefault=="1">

                                    <#if pay.payType=='5'>
                                        <script>
                                            var firstPayType = '${pay.payType}';
                                        </script>
                                    </#if>

                                    <input type="hidden" value="${pay.payId}" name="payId" id="payId"/>
                                    <input type="hidden" value="${pay.payType}" name="payType" id="payType"/>
                                </#if>

                                <#if (pay.isOpen=='1'&& (pay.payType=='1'||pay.payType=='2'||pay.payType=='4'||pay.payType=='5'))>
                                    <li title="${pay.payName}">

                                        <a paytype = "${pay.payType}" onclick="payTypeClick(this,'${pay.payId}','${pay.payType}');"
                                            <#if pay.payDefault=="1">
                                           class="selected"
                                            </#if>
                                           href="javascript:;">
                                            <#if pay.payType=='5'>
                                                <img src="${basePath}/images/payment3.jpg" alt="${pay.payName}"><!-- 余额支付 -->
                                            <#elseif pay.payType=='1'>
                                                <img src="${basePath}/images/payment1.jpg" alt="${pay.payName}"><!-- 支付宝 -->
                                            <#elseif pay.payType=='2'><!-- 银联 -->
                                                <img src="${basePath}/images/payment4.jpg" alt="${pay.payName}">
                                            <#elseif pay.payType=='4'><!-- 千米收银台 -->
                                                <img src="${basePath}/images/payment5.jpg" alt="${pay.payName}">
                                            <#else>
                                                <img src="${pay.payImage!''}" alt="${pay.payName}">
                                            </#if>
                                        </a>
                                    </li>
                                </#if>
                            </#list>
                        </#if>
                    </ul>
                </div>
                <div class="box for-pre-deposit" style="display: none;">
                    <div class="alert-box">
                        <p class="fl">可用存款：￥<span id="predeposit">null</span></p>
                        <p class="tip-alert fl">
                            <!--
                            预存款不足，请使用其他支付方式
                            -->
                        </p>
                        <div class="clr"></div>
                    </div>

                    <div id="payInput">
                        <p class="h4">支付密码：</p>
                        <div class="charge-form">
                            <input type="password" style="display: none;" id="password" name="password">
                            <!--
                            <input type="password" name="payPassword" id="payPassword" class="text">
                            -->
                            <input class="text" type="text" name="payPassword" id="payPassword" onfocus="this.type='password'" autocomplete="off" />
                            <a id="passhref" href="${basePath}/deposit/changepaypasswordview.htm" target="_blank">忘记密码？</a>
                        </div>
                        <p class="error"></p>
                    </div>

                    <p class="error" id="errorMsg" ></p>
                </div>

                <div class="box">
                    <a href="javascript:;" class="pay-btn disabled">确认支付</a>
                </div>
            </form>
        </div>
    </div>
</div>
<#else>
<div class="reg_success">
    <div class="notice2">
        <img alt="" src="${basePath}/images/failed.png">订单不存在！<span>
    </div>
    <div class="notice3">
        <strong><span id="time">5</span>秒自动进入<a href="${basePath}/index.html">“首页”</a></strong></span>
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

<#--
<div class="dialog dia1">
    <div class="dia_tit clearfix">
        <h4 class="fl">支付提示</h4>
        <a class="dia_close fr" href="javascript:;" onclick="cls()"></a>
    </div>
    <div class="dia_cont">
        <div class="dia_intro no_tc pt30">
            <em>付款进行中...</em>
            <div class="mt20">
                <p class="lh180">支付完成前，请不要关闭此支付验证窗口。</p>
                <p class="lh180">支付完成后，请根据您支付的情况点击下面按钮。</p>
            </div>
        </div>
        <div class="dia_ops mt20 tc">
            <a class="go_pay" href="${basePath}/customer/myorder.html">完成支付</a>
            <a class="go_shopping" href="javaScript:void(0)" onclick="showHelp(this)" date-value="" id="payhelp">付款遇到问题</a>
        </div>
    </div>
</div>
-->

<div class="member-dialog dia1">
    <div class="member-dialog-body">
        <div class="title"><a href="javascript:;" onclick="cls()">×</a>提示</div>
        <div class="dia-tips">
            <p class="txt">
                支付完成前请勿关闭此窗口
                请确认是否已经完成支付
            </p>
        </div>
        <div class="dia-btn">
            <a class="btn active" href="${basePath}/customer/myorder.html">完成支付</a>
            <a class="btn" href="javascript:void(0)" onclick="showHelp(this)" date-value="" id="payhelp">付款遇到问题</a>
        </div>
    </div>
</div>


<div class="dialog dia2 agreement_dia">
    <div class="dia_tit clearfix">
        支付问题描述
        <a class="dia_close fr" href="javascript:;" onclick="cls()"></a>
    </div>
    <div class="dia_cont">
        <div class="agreement_wp no_tc pt30" id="payHelpDesc" style="width:auto">
        </div>
        <div class="dia_ops mt20 tc">
        </div>
    </div>
</div>

<script type="text/javascript" src="${basePath}/js/minShopping.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/customer.js"></script>
<script type="text/javascript" src="${basePath}/js/default.js"></script>

<script type="text/javascript">
    //首次进入页面的时候，当支付方式为余额支付的时候，手动触发余额支付的点击事件
    var firstPayType = firstPayType||"";
    if((firstPayType||"")==5){
        $(".types li a[payType='5']").click();
    }else{//其他支付方式，按钮点亮
        $(".pay-btn").removeClass("disabled");
    }

    setTimeout(countDown, 1000);
    function countDown(){
        var time = $("#time").text();
        $("#time").text(time - 1);
        if (time == 1) {
            location.href='${basePath}/index.html';
        } else {
            setTimeout(countDown, 1000);
        }
    }

    function showHelp(obj){
        $(".dia1").hide();
        $("#payhelp").attr("date-value");
        var payId = ( $("#payhelp").attr("date-value"));
        $.ajax({
            type:"POST",
            url:"${basePath}/findpayone.htm",
            data:"payid="+payId,
            success:function(data){
                $("#payHelpDesc").html(data.payHelp);
                var _wd = $(window).width();
                var _hd = $(window).height();
                $(".dia2").css("top",(_hd - $(".dia2").height())/2).css("left",(_wd - $(".dia2").width())/2);
                $(".dia2").show();
            }
        });
    }
    function payTypeClick(dom, payId, payType) {
        var $this = $(dom);
        $(".types li a").removeClass("selected");
        $this.addClass("selected");
        $("#payId").val(payId);
        $("#payType").val(payType);
        $("#errorMsg").text("").hide();
        if(payType=='5'){ //余额款支付的时候
            //提交按钮置灰
            $(".pay-btn").addClass('disabled');
            //查询后台的余额信息
            var defer = $.ajax({
                url:'checkdepositpay.htm',
                type:'post',
                data:$(".payOrder").serialize()
            });
            defer.done(function(res){
                if(res.retcode=='-1'){//返回码
                    $("#payInput").hide();
                    //$(".tip-alert.fl").html(res.msg + '<a>去设置</a>');
                    if(res.errorcode==1){//错误码1没有设置支付密码。
                        $(".tip-alert.fl").html(res.msg + '&nbsp;&nbsp;<a href="${basePath}/customer/securitycenter.html">去设置</a>');
                    }else{
                        $(".tip-alert.fl").html(res.msg);
                    }
                }else if(res.retcode=='0'){
                    $(".tip-alert.fl").html("");
                    $("#payInput").show();
                }
                //debugger;
                if(res.predeposit!=null){
                    res.predeposit = res.predeposit.toFixed(2);
                }
                $("#predeposit").text(res.predeposit+"");

                $(".box.for-pre-deposit").show();
            });
            //$("#payPassword").attr("type","password");
        }else{
            $(".box.for-pre-deposit").hide();
            $(".pay-btn").removeAttr('disabled').removeClass("disabled");
        }
    }

    //确认支付点击的时候
    $('.pay-btn').click(function(){
        if($(this).hasClass("disabled")){
            return;
        }
        var payType = $("#payType").val();
        if(payType==5){
            var payPassword = $("#payPassword").val();
            if(!payPassword){//密码为空
                $(".box.for-pre-deposit #errorMsg").text('密码为空');
                //提交按钮置灰
                $(".pay-btn").addClass('disabled');
                return;
            }else{
                //支付。。。。
                var defer = $.ajax({
                    url:'/payorder.html',
                    type:'post',
                    data:$(".payOrder").serialize()
                });
                defer.done(function(res){
                    if(res.retcode=='-1'){
                        $("#errorMsg").text(res.msg).show();
                    }else if(res.retcode=='0'){
                        //支付成功
                        var orderId = $("#orderId").val();
                        window.location.href = "/depositpaysuccess.htm?orderId="+orderId;
                    }
                });
            }
        }else{
            dia(1);
            //表单提交
            $('.payOrder').submit();
        }
    });

    //事件处理
    function bindEvent(){
        $("#payPassword").keyup(function(){
            var val = $(this).val();
            if(!val){
                $(".box.for-pre-deposit #errorMsg").text('密码为空').show();
                $(".pay-btn").addClass('disabled');
            }else{
                $(".box.for-pre-deposit #errorMsg").text('').hide();
                $(".pay-btn").removeClass('disabled');
            }
        });
    }
    bindEvent();
    //var images
    function backspace(evt){
        evt=evt?evt:window.event;
        if (evt.keyCode == 8 && evt.srcElement.tagName != "INPUT" && evt.srcElement.type != "text")
            evt.returnValue=false;
    }
    $(function(){
        $("#payPassword").val("");
    })
</script>

</body>
</html>