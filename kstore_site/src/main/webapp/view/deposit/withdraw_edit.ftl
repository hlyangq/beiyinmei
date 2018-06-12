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
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css">
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <link rel="stylesheet" href="${basePath}/css/receive.m.css"/>

    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
</head>
<body>


<div style="">

    <div class="container clearfix pr">
        <div class="mini_head">
            <h1 class="logo">
                <a href="/">
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
                        <div class="item">
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

                <div class="cash-data">
                    <div class="data-block">
                        <ul>
                            <li>
                                <span class="dt">提现状态：</span>
                                <p><span class="red" id="statusInfo">Boom</span></p>
                            </li>
                            <li>
                                <span class="dt">提现单号：</span>
                                <p>${vo.orderCode}</p>
                            </li>
                            <li>
                                <span class="dt">申请时间：</span>
                                <p>${vo.createTime?string("yyyy-MM-dd HH:mm:ss")}</p>
                            </li>
                            <li>
                                <span class="dt">收款银行：</span>
                                <p>${vo.bankName}</p>
                            </li>
                            <li>
                                <span class="dt">收款账号：</span>
                                <p>${vo.bankNo}</p>
                            </li>
                            <li>
                                <span class="dt">户名：</span>
                                <p>${vo.accountName}</p>
                            </li>
                            <li>
                                <span class="dt">联系人号码：</span>
                                <p>${vo.contactMobile}</p>
                            </li>
                            <li>
                                <span class="dt">提现金额：</span>
                                <p>￥${vo.amount?string("0.00")}</p>
                            </li>
                            <li>
                                <span class="dt">申请备注：</span>
                                <p style="word-wrap: break-word;">${vo.remark!'无'}</p>
                            </li>
                        </ul>
                    </div>

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


    <script type="text/template" id="confirmWithdraw">
        <div class="data-block">
            <ul>
                <li>
                    <span class="dt">付款单号：</span>
                    <p>{{=payBillNum }}</p>
                </li>
                <li>
                    <span class="dt">付款凭证：</span>
                    <div class="vouchers">
                        {{ if (certificateImg.length == 0) { }}
                        [无]
                        {{ } else { }}
                        <a href="javascript:;" class="show-voucher" data-img="{{=certificateImg }}">
                            <img src="{{=certificateImg }}" alt="" style="width:100px;height: 100px;">
                            <span>点击查看大图</span>
                        </a>
                        {{ } }}
                    </div>
                </li>
                <li>
                    <span class="dt">付款备注：</span>
                    <p style="word-wrap: break-word;">{{=payRemark }}</p>
                </li>
            </ul>
        </div>

    </script>

    <script type="text/template" id="confirmWithdrawSub">
        <div class="buttons">
            <a href="javascript:;" class="btn" onclick="confirmCurWithdraw()">确认收款</a>
        </div>
        <div class="cash-tips" style="margin-top: 20px;">
            <p class="tips" style="">管理员打款后，如果您在7天之内没有确认收款，则系统自动确认收款，并扣减提现金额，请及时确认收款。</p>
        </div>
    </script>

    <script type="text/template" id="rejectReason">
        <div class="data-block">
            <ul>
                <li>
                    <span class="dt">打回原因：</span>
                    <p>{{=reviewedRemark }}</p>
                </li>
            </ul>
        </div>
    </script>

    <div class="mask" style="display: none;"></div>
    <div class="img-dialog dia1">
        <div class="img-dialog-body">
            <a href="javascript:;" class="close"></a>
            <img src="" alt="">
        </div>
    </div><!--/dialog-->


    <script src="${basePath}/js/lodash.min.js"></script>
    <script src="${basePath}/js/lodash.fp.min.js"></script>
    <script src="${basePath}/js/plugin/artDialog/dialog.js"></script>
    <script src="${basePath}/js/deposit/withdraw.js"></script>
    <script>
        _.templateSettings = {
            interpolate: /\{\{=(.+?)}}/g,
            evaluate: /\{\{(.+?)}}/g,
            escape: /\{\{\-(.+?)}}/g
        };

        var orderStatus = ${vo.orderStatus};
        var id = ${vo.id};
        //         todo refactor

        function cancelCurWithdraw() {
            cancelWithdraw(id);
        }

        function confirmCurWithdraw() {
            confirmWithdraw(id);
        }

        $(function () {
            /*
            * 点击查看大图
            * */
            $(document).on('click','.show-voucher',function () {
                $('.mask').fadeIn();
                $('.img-dialog').fadeIn();
                $('.img-dialog-body img').attr('src', this.dataset.img);
                $('.img-dialog-body').css({
                    'left': ($(window).width() - $('.img-dialog-body').width()) / 2 + 'px',
                    'top': ($(window).height() - $('.img-dialog-body').height()) / 2 + 'px'
                });
            });
            $('.img-dialog .close').click(function () {
                $('.mask').fadeOut();
                $('.img-dialog').fadeOut();
            });

            var $cashData = $('.cash-data');
            var template;
            var html;
            var data = {
                'payBillNum': '${vo.payBillNum!'无'}',
                'certificateImg': '${vo.certificateImg!''}',
                'reviewedRemark': '${vo.reviewedRemark!'无'}',
                'payRemark': '${vo.payRemark!'无'}',
            };
            switch (orderStatus) {
                case 0://待审核
                    html = '<div class="buttons">    \
                           <a href="javascript:;" class="btn" onclick="cancelCurWithdraw()">取消申请</a>  \
                        </div>';
                    break;
                case 1://已打回
                    template = _.template($("#rejectReason").html());
                    html = template(data);
                    $('#cashFlow').find('.item:eq(1)')
                            .attr('style', 'border-color:#ccc');
                    break;
                case 3:// 待确认
                    template = _.template($("#confirmWithdraw").html());
                    html = template(data);
                    html += $('#confirmWithdrawSub').html();
                    break;
                case 4:// 已完成
                    template = _.template($("#confirmWithdraw").html());
                    html = template(data);
                    break;
                case 8:// 已取消 : 把第一个flow-item 后面的线设为`未激活`状态
                    $('#cashFlow').find('.item:eq(0)')
                            .attr('style', 'border-color:#ccc');
                    break;
            }

            var statusInfoList = {
                '0': '待审核',
                '1': '已打回',
                '2': '已通过',
                '3': '待确认',
                '4': '已完成',
                '8': '已取消',
            };

            $('#statusInfo').text(statusInfoList[orderStatus]);

            var activeNum = {
                '0': 1,
                '1': 2,
                '2': 2,
                '3': 3,
                '4': 4,
                '8': 1,

            }[orderStatus];
            $('#cashFlow').find('.item:lt(' + activeNum + ')')
                    .each(function (i, a) {
                        $(a).addClass('active');
                    });


            $cashData.append(html);
        });
    </script>

</body>
</html>