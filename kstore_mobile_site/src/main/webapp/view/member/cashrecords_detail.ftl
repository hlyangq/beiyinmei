<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 提现记录详情页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
<#assign basePath =request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/swipebox.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/jquery.swipebox.min.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>

<div class="content-home" style="padding-top:0;min-height:700px;background: #fff;">
    <div class="record_details">
        <div class="box">
            <div class="line">
                <span>提现状态：</span>
                <#if tradeDetail.orderStatus??>


                <#if tradeDetail.orderStatus == '0'>
                    <p>待审核</p>
                <#elseif tradeDetail.orderStatus == '1'>
                        <p>已打回</p>
                <#elseif tradeDetail.orderStatus == '2'>
                    <p>已通过</p>
                <#elseif tradeDetail.orderStatus == '3'>
                    <p>待确认</p>
                <#elseif tradeDetail.orderStatus == '4'>
                    <p>已完成</p>
                <#elseif tradeDetail.orderStatus == '8'>
                    <p>已取消</p>
                </#if>
                </#if>
            </div>
            <div class="line">
                <span>提现单号：</span>
                <p>${tradeDetail.orderCode!''}</p>
            </div>
            <div class="line">
                <span>申请时间：</span>
                <p>${tradeDetail.createTime?string("yyyy-MM-dd HH:mm:ss")}</p>
            </div>
            <#if tradeDetail.chargeWithdraw??>
                <div class="line">
                    <span>收款银行：</span>
                    <p>${tradeDetail.chargeWithdraw.bankName!''}</p>
                </div>
                <div class="line">
                    <span>收款账号：</span>
                    <p>${tradeDetail.chargeWithdraw.bankNo!''}</p>
                </div>
                <div class="line">
                    <span>户名：</span>
                    <p>${tradeDetail.chargeWithdraw.accountName!''}</p>
                </div>
                <div class="line">
                    <span>手机号：</span>
                    <p>${tradeDetail.chargeWithdraw.contactMobile!''}</p>
                </div>
                <div class="line">
                    <span>提现金额：</span>
                    <p>￥${tradeDetail.chargeWithdraw.amount?string("0.00")}</p>
                </div>
                <div class="line">
                    <span>申请备注：</span>
                    <p><#if tradeDetail.chargeWithdraw.remark !=''>${tradeDetail.chargeWithdraw.remark}<#else>无</#if></p>
                </div>
            </#if>
        </div>
<#if tradeDetail.orderStatus??>
        <#if tradeDetail.orderStatus == '3' || tradeDetail.orderStatus == '4' >
            <div class="box">
                <div class="line">
                    <span>付款单号：</span>
                    <p>${tradeDetail.payBillNum!''}</p>
                </div>
                <div class="line">
                    <span>付款备注：</span>
                    <p><#if tradeDetail.payRemark??>${tradeDetail.payRemark!''}<#else>无</#if> </p>
                </div>
                <div class="line">
                    <span>付款凭证：</span>
                    <p>
                        <#if tradeDetail.certificateImg?? >
                            <a href="${tradeDetail.certificateImg!''}" class="swipebox">
                                <img src="${tradeDetail.certificateImg!''}" alt="">
                            </a>
                            <#else >
                            无
                        </#if>
                    </p>
                </div>
            </div>
        </#if>
</#if>
    </div>
</div>

<#if tradeDetail.orderStatus == '0'>
<div class="bar-bottom">
    <div class="btns">
        <a href="#" onclick="showUpdateDia(0, ${tradeDetail.id});" >取消申请</a>
    </div>
</div>
<#elseif tradeDetail.orderStatus == '3' >
<div class="bar-bottom">
    <div class="btns">
        <a href="#" onclick="showUpdateDia(3,${tradeDetail.id});">确认收款</a>
    </div>
</div>
</#if>

<#--<#include '/common/smart_menu.ftl' />-->

<script type="text/javascript">
    ;( function( $ ) {

        $( '.swipebox' ).swipebox();

    } )( jQuery );

    function showUpdateDia(type,tradeInfoId){
        var return_msg;
        var updateDia;
        if('0' == type){
            return_msg = '确定要取消申请?';
            updateDia = dialog({
                id: 'deposit-frozen',
                width : 260,
                content : '<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a onclick="updateApply('+ type+','+tradeInfoId+')" id="depositok" class="ok" >确定</a><a href="javascript:;" onclick="closeDialog();" class="cancel" style="margin-left: 4%;" >再看看</a></div></div>'
            });
        }else if('3' == type){
            return_msg = '确定收款后,冻结的预存款将被扣除,是否确认已收到打款?';
            updateDia = dialog({
                id: 'deposit-frozen',
                width : 260,
                content : '<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a href="javascript:;" onclick="closeDialog();" class="cancel">取消</a><a onclick="updateApply('+ type+','+tradeInfoId+')" id="depositok" class="ok" style="margin-left: 4%;">确定</a></div></div>'
            });
        }

        updateDia.reset();
        updateDia.showModal();
    }

    function updateApply(type, tradeInfoId){
        $.ajax({
            url:'${basePath}/updatetradeinfostatus.htm',
            type:'post',
            data:{'type': type,'tradeInfoId': tradeInfoId},
            success:function(result){
                var return_msg;
                if(result== true){
                    if('0' == type){
                        return_msg = '取消申请成功';
                    }else if('3' == type){
                        return_msg = '确认收款成功';
                    }
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+return_msg +'</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                        location.reload();
                    },1000);
                }else{
                    if('0' == type){
                        return_msg = '取消申请失败';
                    }else if('3' == type){
                        return_msg = '确认收款失败';
                    }
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+return_msg +'</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                        location.reload();
                    },1000);
                }
            }
        });
    }

    function closeDialog(){
        dialog.getCurrent().close();
    }
</script>
</body>
</html>