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
    <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css"/>
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css">
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/receive.m.css"/>
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
                    <div class="n-title">我的预存款</div>
                    <div class="ex-bar-total ex-bar-lg clearfix">
                        <dl class="box">
                            <dt>预存款总额：</dt>
                            <dd>${(deposit.preDeposit+deposit.freezePreDeposit)?string('0.00')}元</dd>
                        </dl>
                        <dl class="box">
                            <dt>可用预存款：</dt>
                            <dd><span class="red">${deposit.preDeposit?string('0.00')}</span>元</dd>
                        </dl>
                        <dl class="box">
                            <dt>冻结款总额：</dt>
                            <dd>${deposit.freezePreDeposit?string('0.00')}元</dd>
                        </dl>
                        <div class="buttons">
                            <a href="/deposit/rechargeview.htm" class="red" target="_blank">充值</a>
                            <a onclick="newWithdraw()">提现</a>
                        </div>
                    </div>
                    <div class="order-menu">
                        <ul class="menu clearfix">

                            <li>
                                <a href="${basePath}/deposit/mydeposit.htm">全部</a>
                            </li>
                            <li>
                                <a href="${basePath}/deposit/mydeposit.htm?type=0">收入</a>
                            </li>
                            <li>
                                <a href="${basePath}/deposit/mydeposit.htm?type=1">支出</a>
                            </li>
                        </ul>
                        <div class="select-box" onmouseover="$(this).addClass('open')" onmouseout="$(this).removeClass('open')">
                            <span class="value"><!-- 时间段 -->
                                <#if time==''>
                                    近三个月收支明细
                                <#elseif time=='before3Month'>
                                    三个月前收支明细
                                <#elseif time=='before1Year'>
                                    一年前收支明细
                                </#if>
                                <i></i>
                            </span>
                            <div class="options">
                                <a href="javascript:;"
                                   <#if time==''>
                                   class="selected"
                                   </#if>
                                   onclick="addFormParam('')">
                                    近三个月收支明细
                                </a>
                                <a href="javascript:;"
                                    <#if time=='before3Month'>
                                       class="selected"
                                    </#if>
                                   onclick="addFormParam('before3Month')">
                                    三个月前收支明细
                                </a>
                                <a href="javascript:;"
                                    <#if time=='before1Year'>
                                       class="selected"
                                    </#if>
                                   onclick="addFormParam('before1Year')">
                                    一年前收支明细
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="content">
                        <div class="layout" id="No0">
                            <table class="bought-table mt10">
                                <thead>
                                <tr>
                                    <th>时间</th>
                                    <th>交易类型</th>
                                    <!--
                                    <th>收入金额</th>
                                    <th>支出金额</th>
                                    -->
                                    <#if type??><!-- type不空 -->
                                        <#if type == '0'>
                                            <th>收入金额</th>
                                        <#elseif type == '1'>
                                            <th>支出金额</th>
                                        <#elseif type == ''>
                                            <th>收入金额</th>
                                            <th>支出金额</th>
                                        </#if>
                                    <#else>

                                    </#if>
                                    <th>交易状态</th>
                                    <th>当前余额</th>
                                    <th>备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#if pb.list??&&(pb.list?size!=0)>
                                        <#list pb.list as trade>
                                        <tr class="order-bd">
                                            <td>
                                                ${trade.createTime?string("yyyy-MM-dd HH:mm:ss")}
                                            </td>
                                            <td>
                                                <!-- 判断订单类型 -->
                                                <#if trade.orderType=='0'>
                                                    在线充值
                                                <#elseif trade.orderType=='1'>
                                                    订单退款
                                                <#elseif trade.orderType=='2'>
                                                    线下提现
                                                <#elseif trade.orderType=='3'>
                                                    订单消费
                                                <#else>

                                                </#if>
                                            </td>

                                            <#if type??><!-- type不空 -->
                                                <#if type=='0'>
                                                    <td>
                                                        <#if trade.orderType=='0' || trade.orderType=='1'>
                                                            <span class="green">+￥${trade.orderPrice!''}</span>
                                                        </#if>
                                                    </td>
                                                <#elseif type=='1'>
                                                    <td>
                                                        <#if trade.orderType=='2' || trade.orderType=='3'>
                                                            <span class="red">-￥${trade.orderPrice?string('0.00')}</span>
                                                        </#if>
                                                    </td>
                                                <#elseif type==''>
                                                    <td>
                                                        <#if trade.orderType=='0' || trade.orderType=='1'>
                                                            <span class="green">+￥${trade.orderPrice?string('0.00')}</span>
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if trade.orderType=='2' || trade.orderType=='3'>
                                                            <span class="red">-￥${trade.orderPrice?string('0.00')}</span>
                                                        </#if>
                                                    </td>
                                                </#if>
                                            <#else>

                                            </#if>

                                            <td>
                                                <!-- 0【提现】待审核 1【提现】已打回 2【提现】已通过待打款 3【提现】已打款待确认 4【提现】已完成 5充值中 6充值成功 7充值失败 8已取消-->
                                                <#if trade.orderStatus??&&trade.orderStatus=='0'>
                                                    待审核
                                                <#elseif trade.orderStatus??&&trade.orderStatus=='1'>
                                                    已打回
                                                <#elseif trade.orderStatus??&&trade.orderStatus=='2'>
                                                    已通过
                                                <#elseif trade.orderStatus??&&trade.orderStatus=='3'>
                                                    待确认
                                                <#elseif trade.orderStatus??&&trade.orderStatus=='4'>
                                                    已完成
                                                <#elseif trade.orderStatus??&&trade.orderStatus=='5'>
                                                    <!--充值中 状态修改-->
                                                    未支付
                                                <#elseif trade.orderStatus??&&trade.orderStatus=='6'>
                                                    充值成功
                                                <#elseif trade.orderStatus??&&trade.orderStatus=='7'>
                                                    <!-- 充值失败 状态修改-->
                                                <#elseif trade.orderStatus??&&trade.orderStatus=='8'>
                                                    已取消
                                                <#else>
                                                    已完成
                                                </#if>
                                            </td>

                                            <td>
                                                ￥${trade.currentPrice?string('0.00')}
                                            </td>
                                            <td style="word-wrap: break-word;" title="${trade.tradeRemark!''}">
                                                <input class="tradeRemark" type="hidden" value="${trade.tradeRemark!''}">
                                                <!-- trade.tradeRemark!'' -->
                                                <#-- ${trade.tradeRemark!''}-->
                                                <!--  这段代码构建一个备注信息，由于这个信息只是一堆字符串 -->
                                            </td>
                                        </tr>
                                    </#list>
                                <#else>
                                    <tr>
                                        <td colspan=
                                            <#if type??>
                                                <#if type=='0'>
                                                    6
                                                <#elseif type=='1'>
                                                    6
                                                <#elseif type==''>
                                                    7
                                                </#if>
                                            <#else>

                                            </#if>
                                            >
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

<div class="mask">
    <form id="queryForm" method="get">
        <input type='hidden' name='time' value='${time!''}'><!-- 查询分类 -->
    </form>
</div>

<script type="text/javascript" src="${basePath}/js/default.js"></script>
<script type="text/javascript" src="${basePath}/js/tab-switch.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/customer.js"></script>
<script type="text/javascript" src="${basePath}/js/newapp.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/findcode.js"></script>
<script type="text/javascript" src="${basePath}/js/jsOperation.js"></script>

<script src="${basePath}/js/plugin/artDialog/dialog.js"></script>


<script>

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

    //添加form查询条件
    var pageSize = '${pb.pageSize}';
    var pageNo = '${pb.pageNo}';
    var type = '${type!''}';
    function addFormParam(time){
        $("#queryForm")
                .empty()
                .attr('action','/deposit/mydeposit.htm')
                .append("<input type='hidden' name='pageSize' value='"+pageSize+"'>")
                .append("<input type='hidden' name='pageNo' value='1'>")
                .append("<input type='hidden' name='time' value='"+time+"'>")
                .append("<input type='type' name='type' value='"+type+"'>")
                .submit();
    }
    //分页函数，js控制，来控制多参数
    function page(pageNo,pageSize) {
        $("#queryForm").attr('action','/deposit/mydeposit.htm')
                .append("<input type='hidden' name='pageSize' value='"+pageSize+"'>")
                .append("<input type='hidden' name='pageNo' value='"+pageNo+"'>")
                .append("<input type='type' name='type' value='"+type+"'>")
                .submit();
    }

    $('.item_title').each(function(){
        $(this).click(function(){
            $(this).next().toggle('fast',function(){
                if($(this).is(':visible')){
                    $(this).prev().removeClass('up');
                    $(this).prev().addClass('down');
                }
                else{
                    $(this).prev().removeClass('down');
                    $(this).prev().addClass('up');
                }
            });
        });
    });

    if(type){
        if(type=="0"){
            $(".menu.clearfix li:eq(1)").addClass("current");
        }else if(type=="1"){
            $(".menu.clearfix li:eq(2)").addClass("current");
        }
    }else{
        $(".menu.clearfix li:eq(0)").addClass("current");
    }
    //li 点击
    $(".menu.clearfix li").click(function(){
        var $this = $(this);
        window.location.href = $this.find("a").attr("href");
    });
    //设置备注信息
    function setTradeRemark(){
        var trs = $(".tradeRemark");
        var count = trs.length;
        var oneTr = null;
        for(var i=0;i<count;i++){
            oneTr = $(trs[i]);
            if(!oneTr){continue;}
            var val = oneTr.val();
            var rs = val.split(",");
            var html = "";
            //订单编号:2016101914405910,订单编号:2016101914405910，这种格式
            if(rs.length>=2){
                for(var j=0;j<rs.length;j++){
                    var k = rs[j];
                    var splits = k.split(":");
                    if(splits.length>=2){
                        html+=splits[0]+":<br>";
                        html+=splits[1]+"<br>";
                    }else{
                        html+=splits[0]+"<br>";
                    }
                }
            }else{
                var splits = val.split(":");
                if(splits.length>=2){
                    html+=splits[0]+":<br>";
                    html+=splits[1]+"<br>";
                }else{
                    html+=splits[0]+"<br>";
                }
            }
            $(oneTr).parent().html(html);
        }
    }
    setTradeRemark();
</script>

</body>
</html>