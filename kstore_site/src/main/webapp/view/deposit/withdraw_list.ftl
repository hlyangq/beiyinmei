<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>提现记录-${topmap.systembase.bsetName}</title>
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
    <style>
        .cart-top {
            top: 5px !important;
        }
    </style>
</head>
<body>

<#--一引入头部 <#include "/index/topnew.ftl" />  -->
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

<div style="background: #f5f5f5;">
    <div class="container clearfix pt20 pb10">
    <#include "../customer/newleftmenu.ftl" />

        <div class="new_member-right">
            <div class="new_order_list">
                <div class="n-title">提现记录</div>
                <div class="ctrls">
                    <a class="btn" id="newBtn" onclick="newWithdraw()">新增提现申请</a>
                </div>
                <div class="order-menu">
                    <ul class="menu clearfix">
                        <li><a href="/deposit/withdraw-list.htm">全部申请</a></li>
                        <li><a href="/deposit/withdraw-list.htm?status=0">待审核</a></li>
                        <li><a href="/deposit/withdraw-list.htm?status=1">已打回</a></li>
                        <li><a href="/deposit/withdraw-list.htm?status=3">待确认</a></li>
                        <li><a href="/deposit/withdraw-list.htm?status=4">已完成</a></li>
                    </ul>
                </div>
                <div class="content">
                    <div class="layout" id="No0">
                        <table class="bought-table mt10">
                            <thead>
                            <tr>
                                <th>提现单号</th>
                                <th>申请时间</th>
                                <th>提现金额</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            <#if pb.list??&&(pb.list?size!=0)>
                                <#list pb.list as trade>
                                <tr class="order-bd">
                                    <td>
                                    ${trade.orderCode}
                                    </td>
                                    <td>
                                    ${trade.createTime?string("yyyy-MM-dd HH:mm:ss")}
                                    </td>
                                    <td>
                                        ￥${trade.orderPrice?string("0.00")}
                                    </td>
                                    <td>
                                    <#--提现状态 0待审核 1打回 2【充值】通过,【提现】待确认 3确认 4已完成 5充值中 6充值成功 7充值失败 8已取消-->
                                        <p>
                                            <span class="status">
                                                <#if trade.orderStatus=='0'>
                                                    待审核
                                                <#elseif trade.orderStatus=='1'>
                                                    已打回
                                                <#elseif trade.orderStatus=='2'>
                                                    已通过
                                                <#elseif trade.orderStatus=='3'>
                                                    待确认
                                                <#elseif trade.orderStatus=='4'>
                                                    已完成
                                                <#elseif trade.orderStatus=='8'>
                                                    已取消
                                                </#if>
                                            </span>
                                        </p>
                                        <p>
                                            <a href="/deposit/withdraw.htm?id=${trade.id}" target="_blank">查看详情</a>
                                        </p>
                                    </td>
                                    <td>
                                        <#if trade.orderStatus=='0'>
                                            <a onclick="cancelWithdraw(${trade.id})" class="btn"
                                               target="_blank">取消申请</a>
                                        <#elseif trade.orderStatus=='3'>
                                            <a onclick="confirmWithdraw(${trade.id})" class="btn" target="_blank">确认收款</a>
                                        <#else>
                                            暂无可用操作
                                        </#if>
                                    </td>
                                </tr>
                                </#list>
                            <#else>
                            <tr>
                                <td colspan="5">
                                    <center class="m10">暂无明细</center>
                                </td>
                            </tr>
                            </#if>
                            </tbody>
                        </table>

                    <#if pb?? && pb.list?? && (pb.list?size!=0)>
                        <#import "pageable/pageable.ftl" as page>
                        <@page.pagination pb />
                    </#if>

                    </div>
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

<form id="queryForm" method="get" style="display: none;" action="/deposit/withdraw-list.htm">
    <input type='hidden' name='status' value='${form.status!'-1'}'>
    <input type='hidden' name='pageNo' value='${pb.pageNo}'>
    <input type='hidden' name='pageSize' value='${pb.pageSize}'>
</form>

<script src="${basePath}/js/plugin/artDialog/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/default.js"></script>
<script type="text/javascript" src="${basePath}/js/tab-switch.js"></script>
<script src="${basePath}/js/deposit/withdraw.js"></script>

<script>
    //分页函数，js控制，来控制多参数
    function page(pageNo, pageSize) {
        var $form = $("#queryForm");
        $form.find('[name=pageNo]').val(pageNo);
        $form.find('[name=pageSize]').val(pageSize);
        $form.submit();
    }

    var pwdExist = ${pwdExist?c};

    function newWithdraw() {
        if (pwdExist) {
            window.open('/deposit/withdraw/new.htm');
        }
        else {
            dialog({
                title: '提示信息',
                content: '必须先设置支付密码后才可提现',
//                skin: 'warning',
                width: 200,
                height: 40,
                okValue: '去设置',
                cancelValue: '取消',
                ok: function () {
                    window.open('/customer/securitycenter.html');
                },
                cancel: function () {
                    return true;
                }
            }).showModal();
        }
    }

    $(function () {
        var status = $('#queryForm').find('[name=status]').val() || '-1';
        var activeIndex = {
            '-1': 0,
            '0': 1,
            '1': 2,
            '3': 3,
            '4': 4,
        }[status];

        $('.order-menu li:eq(' + activeIndex + ')').addClass('current');

    });

    //li 点击
    $(".menu.clearfix li").click(function(){
        var $this = $(this);
        window.location.href = $this.find("a").attr("href");
    });

</script>

</body>
</html>