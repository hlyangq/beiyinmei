<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 充值页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/idangerous.swiper.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>

<div class="recharge_form">
    <div class="box">
        <label>充值方式</label>
        <div class="payments_choose">
            <#list payments as pay>
            <#if pay.payType == '1'>
                <div class="item" id="zhifubao">
                    <em class="payment_icon2"></em>
                    <p class="value">支付宝</p>
                    <i class="select-box selected"></i>
                </div>
            <#elseif pay.payType == '3'>
                <div class="item" id="weixin">
                    <em class="payment_icon1"></em>
                    <p class="value">微信</p>
                    <i class="select-box selected"></i>
                </div>
            </#if>

            </#list>

            <#---->
        </div>
    </div>
    <form id="recharge">
        <div class="box">
            <label>请输入充值金额</label>
            <div class="form_input">
                <input type="text" class="text" id="rechargePrice" name="rechargePrice" placeholder="充值金额必须大于0且不超过1,000,000">
                <span class="extra">元</span>
            </div>
        </div>
    </form>
    <div class="box">
        <a href="javascript:toRecharge();" class="sub_btn">立即充值</a>
    </div>
</div>
<input type="hidden" id="basePath" value="${basePath}"/>
<#include '/common/smart_menu.ftl' />
<script src="${basePath}/js/pay/rechargepay.js" ></script>
<script>
    var mySwiper = new Swiper('.swiper-container',{
        slidesPerView: 'auto'
    });

    //判断是否是微信打开
    function isWeiXin(){
        var ua = window.navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i) == 'micromessenger'){
            return true;
        }else{
            return false;
        }
    }

    $(function(){
        if(isWeiXin()){
            if($("#zhifubao")){
                $("#zhifubao").hide();
            }
        }else{
            if($("#weixin")){
                $("#weixin").hide();
            }
        }
    });
</script>
</body>
</html>