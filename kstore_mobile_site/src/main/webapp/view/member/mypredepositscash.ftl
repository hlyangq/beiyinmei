<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 提现页</title>
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
<form id="withdraw">
<div class="cash_form">
    <h2>提现申请单</h2>
    <div class="form_item">
        <label>收款银行</label>
        <div class="selector" id="bank">
            <span bankid=""></span>
            <input type="hidden" name="receivingBank" id="receivingBank" />
            <i class="ion-chevron-right"></i>
        </div>
    </div>
    <div class="form_item" style="display:none">
        <label>银行名称</label>
        <input type="text" class="text" name="bankName"  id="bankName" placeholder="请填写银行名称">
    </div>
    <div class="form_item">
        <label>收款账号</label>
        <input type="text" class="text" name="bankNo" maxlength="19" id="bankNo" pattern="[0-9]" placeholder="请填写收款账号">
    </div>
    <div class="form_item">
        <label>户名</label>
        <input type="text" class="text" name="accountName" maxlength="20" id="accountName" placeholder="请填写收款账号对应的户名">
    </div>
    <div class="form_item">
        <label>联系电话</label>
        <input type="text" class="text" name="contactMobile" id="contactMobile" placeholder="请填写联系人的电话">
    </div>
    <div class="form_item">
        <label>提现金额</label>
        <input type="text" class="text" name="amount" id="amount" placeholder="本次最多可提现￥${deposit.preDeposit?string("0.00")}">
    </div>
    <div class="form_item">
        <label>备注</label>
        <textarea rows="5" class="text" name="remark" id="remark" placeholder="(非必填)最多输入200字"></textarea>
    </div>
    <div class="form-tips">
        <i class="ion-ios-information-outline"></i>
        <p>提现银行可能收取手续费，以最终到账金额为准，如有任何疑问，请联系管理员</p>
    </div>
    <div class="box">
        <a href="javascript:;" onclick="subWithdrawal();" class="sub_btn">提交</a>
    </div>
    <input type="hidden" name="subPassword" id="subPassword" />
</div>

<div class="screen bank-box" style="display:none;">
    <div class="screen-header">
        <a class="back" href="javascript:;"><i class="back-icon"></i></a>
        请选择银行
    </div>

    <div class="screen-cont">
        <div class="lists district">
            <dl>
                <#list bankList as bank>
                    <dd bankid="${bank.id}" id="bank_${bank.id}" onclick="chooseBank(this);">${bank.bankName!''}</dd>
                </#list>
                    <dd bankid="-1" id="bank_-1" onclick="chooseBank(this);">其他银行</dd>
            </dl>
        </div>
    </div>
</div>
</form>

<#include '/common/smart_menu.ftl' />
<script src="${basePath}/js/dialog-min.js"></script>
<script>
    /* 选择银行 */
    $('#bank').click(function(){
        $('body').append('<div class="opacity-bg-3"></div>');
        var bankid = $(this).find("span").attr("bankid");
        $(".district").find("dd").each(function(){
                $(this).removeClass("checked");
        });
        $("#bank_"+bankid).addClass('checked');
        $('.bank-box').show();
    });
    $('.bank-box .back').click(function(){
        var bankname = $('.district .checked').html();
        var bankid = $('.district .checked').attr("bankid");
        if(bankid == -1){
            $("#bankName").parent(".form_item").show();
        }else{
            $("#bankName").parent(".form_item").hide();
        }
        $('#bank').find('span').html(bankname);
        $('#bank').find('span').attr("bankid", bankid);
        $("#receivingBank").val(bankid);
        $('.opacity-bg-3').remove();
        $('.bank-box').hide();
    });

    $('.district').find('dd').each(function(){
        $(this).addClass('checked');
    });

    function chooseBank(obj){
        $(obj).parent('dl').find('dd').each(function(){
            $(this).removeClass('checked');
        });
        $(obj).addClass('checked');
        var bankname = $('.district .checked').html();
        var bankid = $('.district .checked').attr("bankid");
        if(bankid == -1){
            $("#bankName").parent(".form_item").show();
        }else{
            $("#bankName").parent(".form_item").hide();
        }
        $('#bank').find('span').html(bankname);
        $('#bank').find('span').attr("bankid", bankid);
        $("#receivingBank").val(bankid);
        $('.opacity-bg-3').remove();
        $('.bank-box').hide();
    }

    function validDeposit(obj){
        var pass = $(obj).val();
        if($(obj).val() != ''){
            $(".ok").removeClass("disabled");
                dialog.getCurrent().reset();
        }else{
            $(".ok").addClass("disabled");
        }


    }
    function subWithdrawal(){
        if(checkInput()){
            $("#subPassword").val($("#payPassword").val());
            $.ajax({
               url:'${basePath}/checkDepositPay.htm',
                type:'post',
               data:{'orderPrice':$("#amount").val(),"type":"1"},
                success: function(result){
                    var return_code = result.return_code;
                    if(return_code == 'success'){
                        var yuePay = dialog({
                            id: 'deposit-id',
                            width : 260,
                            content : '<form id="depositpay"> <div style="padding:0 5px"><p class="tc">请输入支付密码</p><p class="tc"><input onkeyup="validDeposit(this);" name="payPassword" type="password" class="p10" id="payPassword"></p><div class="error_tips" style="display: none"><a href="#" class="fr">忘记密码？</a><span>密码错误</span></div></div><div class="dia-buttons"><a href="javascript:;" onclick="closeDialog();" class="cancel">取消</a><a href="jiavascript:;" id="depositok" onclick="depositWithdraw(this);" class="ok disabled" style="margin-left: 4%;">确定</a></div></div>'
                        });
                        yuePay.showModal();
                    }else if(return_code == 'fail'){
                        var fail_code = result.fail_code;
                        var return_msg = result.return_msg;
                        if(fail_code == 'pass_fail'){
                            var passFail = dialog({
                                id: 'deposit-pass',
                                width : 260,
                                content : '<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a href="javascript:;" onclick="closeDialog();" class="cancel">取消</a><a href="${basePath}/accountsafe.html" id="depositok" class="ok" style="margin-left: 4%;">设置</a></div></div>'
                            });
                            passFail.reset();
                            passFail.showModal();
                        }else if(fail_code == 'frozen_fail'){
                            var frozenFail = dialog({
                                id: 'deposit-frozen',
                                width : 260,
                                content : '<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a href="javascript:;" onclick="closeDialog();" id="depositok" style="margin: 0;width: 100%" class="ok">确认</a></div></div>'
                            });
                            frozenFail.reset();
                            frozenFail.showModal();
                        }else if(fail_code == 'balance_fail'){
                            var balanceFail = dialog({
                                id: 'deposit-balance',
                                width : 260,
                                content : '<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a href="jiavascript:;" onclick="closeDialog();" id="depositok" style="margin: 0;width: 100%" class="ok">确认</a></div></div>'
                            });
                            balanceFail.reset();
                            balanceFail.showModal();
                        }

                    }
                }
            });
        }
    }

    function checkInput(){
        if($('#bank').find('span').attr("bankid").trim() ==''){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请选择收款银行</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }


        if( !$("#bankName").parent(".form_item").is(":hidden")){
            if($("#bankName").val().trim()==''){
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写银行名称</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
                return false;
            }else if(! /^[\u4e00-\u9fa5]+$/.test( $("#bankName").val())){
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>银行名称不能包含特殊字符</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
                return false;
            }
        }

        if( $("#bankNo").val().trim()==''){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写收款账号</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }else if(! /^\d+$/.test( $("#bankNo").val())){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>收款账号格式不正确</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }

        if( $("#accountName").val().trim()==''){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写户名</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }else if(! /^[\u4e00-\u9fa5]+$/.test( $("#accountName").val())){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>户名不能包含特殊字符</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }



        //手机号
        if($("#contactMobile").val().trim()==''){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写联系电话</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }else if(!(/^(\d{3,4}-?)?\d{7,9}$/g.test( $("#contactMobile").val()))){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>联系电话格式错误</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }

        if($("#amount").val().trim()==''){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写提现金额</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }else if(!(/^([0-9][0-9]*(\.[0-9]{1,2})?|0\.(?!0+$)[0-9]{1,2})$/.test( $("#amount").val())) || $("#amount").val()<=0){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>提现金额格式错误</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }




        //详细地址
        if($("#remark").val().trim().length>200){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>备注最多输入200字符</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }

        return true;
    }

    function closeDialog(){
        dialog.getCurrent().close();
    }

    function depositWithdraw(obj){
        if($(obj).hasClass('disabled')){
            return false;
        }
        $("#subPassword").val($('#payPassword').val());
        $.ajax({
            url:'${basePath}/member/subchargewithdraw.html',
            type:'post',
            data:$("#withdraw").serialize(),
            success: function(result){
                if(result.return_code == "success"){

                    if(result.tradeId == 0){
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>申请提交异常！</h3></div></div>');
                        setTimeout(function(){
                            $('.tip-box').remove();
                        },1000);
                    }else{
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>申请提交成功，请等待管理员审核！</h3></div></div>');
                        setTimeout(function(){
                            $('.tip-box').remove();
                            location.href="${basePath}/member/towtradeinfodetail.html?tradeId="+result.tradeId;
                        },1000);
                    }

                }else if(result.return_code == "fail"){
                    var return_msg = result.return_msg;
                    var fail_code = result.fail_code;
                    if(fail_code == 'pass_fail'){
                        dialog.getCurrent().content('<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a href="javascript:;" class="cancel">选择其他支付方式</a><a href="jiavascript:;" id="depositok" onclick="depositPay(this);" class="ok">设置支付密码</a></div></div>');
                    }else if(fail_code == 'frozen_fail'){
                        dialog.getCurrent().content('<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a href="jiavascript:;" id="depositok" onclick="closeDialog();" style="margin: 0;width: 100%" class="ok">确认</a></div></div>');
                    }else if(fail_code == 'balance_fail'){
                        dialog.getCurrent().content('<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a href="jiavascript:;" id="depositok" onclick="closeDialog();" style="margin: 0;width: 100%" class="ok">确认</a></div></div>');
                    }else{
                        dialog.getCurrent().content('<form id="depositpay"><div style="padding:0 5px"><p class="tc">支付密码</p><p class="tc"><input onkeyup="validDeposit(this);" type="password" name="payPassword" id="payPassword" class="p10"></p><div class="error_tips"><a href="${basePath}/member/topaypassword.html" class="fr">忘记密码？</a><span>'+ return_msg +'</span></div></div><div class="dia-buttons"><a href="javascript:;" onclick="closeDialog();" class="cancel">取消</a><a href="jiavascript:;" id="depositok" onclick="depositWithdraw(this);" class="ok disabled" style="margin-left: 4%;">确定</a></div></div>');
                    }
                    dialog.getCurrent().reset();

//                    this.content('<div style="padding:0 20px"><p class="tc">支付密码</p><p class="tc"><input type="text" class="p10"></p><div class="error_tips"><a href="#" class="fr">忘记密码？</a><span>密码错误</span></div></div> ');
                    return false;
                }
            }
        });
    }


</script>
</body>
</html>