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
    <link rel="stylesheet" href="${basePath}/css/pages.css"/>
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
    <link rel="stylesheet" href="${basePath}/css/receive.m.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
</head>
<body>


<div style="">

    <div class="container clearfix pr">
        <div class="mini_head">
            <h1 class="logo">
                <a href="${topmap.systembase.bsetAddress}">
                    <img src="${topmap.systembase.bsetLogo}" alt="" width="165px" height="40px"/>
                </a>
                <span>提现</span>
            </h1>
        </div>
    </div>

    <div style="background: #f5f5f5;">
        <div class="container clearfix pt20 pb10">
            <div class="cash-box">
                <div class="cash-flow" id="cashFlow">
                    <div class="inner">
                        <div class="item active">
                            <span>
                                <i class="icon1"></i>
                                申请提现
                            </span>
                        </div>
                        <div class="item">
                            <span>
                                <i class="icon2"></i>
                                处理审核
                            </span>
                        </div>
                        <div class="item">
                            <span>
                                <i class="icon3"></i>
                                确认收款
                            </span>
                        </div>
                        <div class="item">
                            <span>
                                <i class="icon4"></i>
                                完成
                            </span>
                        </div>
                    </div>
                </div>

                <form class="cash-form" id="withdrawForm" action="/deposit/withdraw.htm" method="post">
                    <div class="form-line">
                        <label><b>*</b>开户银行：</label>
                        <div class="form-input">
                            <select class="select" id="bank" name="receivingBank">
                                <option value="">请选择开户银行</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-line" style="display: none">
                        <div class="form-input">
                            <input type="text" class="text" name="bankName" placeholder="请填写银行名称">
                        </div>
                    </div>
                    <div class="form-line">
                        <label><b>*</b>收款账号：</label>
                        <div class="form-input">
                            <input type="text" class="text" name="bankNo">
                        </div>
                    </div>
                    <div class="form-line">
                        <label><b>*</b>户名：</label>
                        <div class="form-input">
                            <input type="text" class="text" name="accountName">
                        </div>
                    </div>
                    <div class="form-line">
                        <label><b>*</b>联系人号码：</label>
                        <div class="form-input">
                            <input type="text" class="text" name="contactMobile">
                        </div>
                    </div>
                    <div class="form-line">
                        <label><b>*</b>提现金额：</label>
                        <div class="form-input">
                            <input type="text" class="text" name="amount">元</input>
                            <p class="tips">
                                最多提现 <strong>￥${deposit.preDeposit!"0.00"}</strong>
                            </p>
                            <label id="amountError" class="error"></label>
                        </div>
                    </div>
                    <div class="form-line">
                        <label><b>*</b>支付密码：</label>
                        <div class="form-input">
                            <input type="password" class="text" name="payPwd">
                            <a href="/deposit/changepaypasswordview.htm">忘记密码？</a>
                            <label id="pwdError" class="error"></label>
                        </div>
                    </div>
                    <div class="form-line">
                        <label>申请备注：</label>
                        <div class="form-input">
                            <textarea class="words" rows="5" id="remark" name="remark"></textarea>
                            <span class="for-textarea">您还可以输入<strong id="remark_limit">200</strong>字</span>
                        </div>
                    </div>
                    <div class="form-line">
                        <div class="form-submit">
                            <a href="#" class="cash-btn" id="submitForm">提交</a>
                        </div>
                    </div>


                    <div class="cash-tips">
                        <p class="tips" style="">提现银行可能收取手续费，以最终到账金额为准,如有任何疑问,请联系管理员。</p>
                    </div>


                </form>
            </div>
        </div>
    </div>

</div>

<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">
    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>


<script type="text/template" id="successTemp">
    <div class="success_tips">
        <i class="successIcon"></i>
        <p>提交成功，请等待商家审核</p>
        <a href="/deposit/mydeposit.htm" class="active">完成</a>
        <a href="/deposit/withdraw.htm?id={{= tradeId }}" class="common">查看提现详情</a>
    </div>
</script>

<script type="text/javascript" src="${basePath}/js/common/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath}/js/common/jquery.validate_extend.js"></script>
<script type="text/javascript" src="${basePath}/js/lodash.min.js"></script>

<#setting number_format="0.00">

<script>
    _.templateSettings = {
        interpolate: /\{\{=(.+?)}}/g,
        evaluate: /\{\{(.+?)}}/g,
        escape: /\{\{\-(.+?)}}/g
    };


    var maxMoney = ${deposit.preDeposit!"0.00"};

    var $form = $("#withdrawForm");
    $form.validate({
        rules: {
            receivingBank: {
                required: true,
            },
            bankName: {
                required: {
                    depends: function (element) {
                        return $('#bank').val() == -1;
                    }
                },
                isChinese: true,
                maxlength: 15
            },
            bankNo: {
                required: true,
                digits: true,
                minlength: 19,
                maxlength: 19
            },
            accountName: {
                required: true,
                isChinese: true,
                maxlength: 20
            },
            contactMobile: {
                required: true,
                mobile: true
            },
            amount: {
                required: true,
                number: true,
                cash:true,
                le: maxMoney
            },
            payPwd: {
                required: true,
            },
            remark: {
                maxlength: 200
            },
        },
        messages: {
            bankName: {
                required: '银行名称不可为空'
            },
            bankNo: {
                required: '请输入收款账号',
            },
            contactMobile: {
                required: '请输入联系人号码，以便能及时联系到您',
            },
            payPwd: {
                required: '请输入支付密码',
            },
        },
        errorPlacement: function (error, element) {
            $(element).parents('.form-input').append(error);
        },
    });
    $form.validate();

    $('#bank').change(function () {
        if ($(this).val() == '-1') {
            $(this).parent().parent().next().show();
        }
        else {
            $(this).parent().parent().next().hide();
        }
    });


    $('[name=payPwd]').keyup(function (e) {
        $(e.currentTarget).parent().find('#pwdError').hide();
    });

    $('[name=amount]').keyup(function (e) {
        $(e.currentTarget).parent().find('#amountError').hide();
    });

    /**
     * 还可输入XX个字
     */
    $('#remark').keyup(function () {
        $('#remark_limit').text(200 - $(this).val().length);
    });

    $('#submitForm').click(function () {
        if ($form.valid()) {
            $.ajax({
                url: "/deposit/withdraw.htm",
                type: 'POST',
                data: $("#withdrawForm").serialize(),
            }).done(function (data) {
                        switch (data.code) {
                            case '0':
                                $form.replaceWith(
                                        _.template($('#successTemp').html())
                                        ({'tradeId': data['tradeId']})
                                );
                                break;
                            case '1':
                            case '2':
                            case '3':
                                $('#pwdError').text(data.msg).show();
                                break;
                            case '4':
                                $('#amountError').text(data.msg).show();
                                break;
                        }
                    }
            )

        }
    });

    function renderBankList() {
        $.ajax({
            url: '/banklist.htm',
            type: 'get'
        }).done(function (bankList) {
            var options;
            var optionTemplate = _.template('<option value="{{=id  }}">{{=bankName }}</option>');
            options = _.chain(bankList)
                    .map(function (bank) {
                        return optionTemplate(bank);
                    })
                    .reduce(function (s, option) {
                        return s += option;
                    })
                    .value();
            options += optionTemplate({'id': '-1', 'bankName': '其他银行'});
            $('#bank').append(options);
        });
    }

    $(function () {
        renderBankList();

    });
</script>

</body>
</html>