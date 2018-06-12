<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 预存款页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <#assign basePath =request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/idangerous.swiper.js"></script>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
</head>
<body>

<div class="content-home" style="padding-top:0;">
<input type="hidden" value="${basePath}" id="basepath">
    <div class="member_brief member_box">
        <div class="cover">
            <div class="data_lg">
                <p class="value"> <#if deposit??>${deposit.preDeposit?string("0.00")}</#if> </p>
                <p>可用预存款(元)</p>
            </div>
        </div>
        <div class="data_list row">
            <div class="col-12">
                <p class="value"><#if deposit??>${(deposit.preDeposit+deposit.freezePreDeposit)?string("0.00")}</#if></p>
                <p>预存款总额</p>
            </div>
            <div class="col-12">
                <p class="value"><#if deposit??>${deposit.freezePreDeposit?string("0.00")}</#if></p>
                <p>冻结预存款</p>
            </div>
        </div>
    </div>

    <div class="member_box">
        <div class="common_line row">
            <a href="${basePath}/member/mypredepositsrecharge.html">
                <em class="chongzhi_icon"></em>
                <h4>充值</h4>
                <i class="ion-chevron-right"></i>
            </a>
        </div>
        <div class="common_line row">
            <a onclick="check();">
                <em class="tixian_icon"></em>
                <h4>提现</h4>
                <i class="ion-chevron-right"></i>
            </a>
        </div>
    </div>

    <div class="member_box">
        <div class="common_line row">
            <a href="${basePath}/customer/customerTradeInfo.html">
                <em class="zhanghumingxi_icon"></em>
                <h4>账户明细</h4>
                <i class="ion-chevron-right"></i>
            </a>
        </div>
        <div class="common_line row">
            <a href="${basePath}/member/withdrawrecords.html">
                <em class="tixianjilu_icon"></em>
                <h4>提现记录</h4>
                <i class="ion-chevron-right"></i>
            </a>
        </div>
    </div>
    <input type="hidden" id="error_msg" value="${error_msg!''}">

</div>

<#include '/common/smart_menu.ftl' />
<script src="${basePath}/js/dialog-min.js"></script>
<script>
    function alertStr(str){
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+str+'</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
    }
    $(function(){
        if($("#error_msg").val() != ''){
            alertStr($("#error_msg").val());
        }
    });
    var mySwiper = new Swiper('.swiper-container',{
        slidesPerView: 'auto'
    });
    function closeDialog(){
        dialog.getCurrent().close();
    }
var basepath = $("#basepath").val();
    function check(){
        $.ajax({
           url:basepath+'/checkwithdraw.htm',
            success:function(result){
                if(!jQuery.isEmptyObject(result)){
                    var return_code = result.return_code;
                    var return_msg = result.return_msg;
                    if(return_code == 'fail'){
                        var passFail = dialog({
                            id: 'deposit-pass',
                            width : 260,
                            content : '<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a onclick="closeDialog();" class="cancel">稍后再说</a><a href="${basePath}/accountsafe.html" id="depositok" class="ok" style="margin-left: 4%;">去设置</a></div></div>'
                        });
                        passFail.reset();
                        passFail.showModal();
                    }
                }else{
                    location.href=basepath +'/member/tochargewithdraw.html';
                }
            }
        });
    }
</script>
</body>
</html>