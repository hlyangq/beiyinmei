<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <title>申请退款</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/base.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css" />
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
    <style>
        .return-orders {padding:0 30px;color:#666;}
        .return-intro {line-height:180%;border:1px solid #ddd;background:#f8f8f8;padding:10px 20px;margin-bottom:20px;}
        .back-orders {padding:0 30px;color:#666;}
        .details-item {margin-bottom:20px;}
        .details-item .code-num {margin-right:100px;}
        .details-item h3, .details-item em {margin-bottom:20px;font-size:14px;font-weight:700;}
        .form-group {padding:0 20px;}
        .form-group .form-item {overflow:hidden;margin:0 0 10px 0;line-height:30px;}
        .form-item .control-label {float:left;width:120px;text-align:right;}
        .form-item .controls {margin:0 0 0 150px;}
        .order-table {width:100%;}
        .order-table th, .order-table td {border:1px solid #ccc;padding:10px;text-align:center;}
        .order-table .tl {text-align: left;}
        .order-table th {background:#f8f8f8;}
        .order-table .od-img img {width:50px;height:50px;border:1px solid #ddd;}
        .order-table .od-name {display:inline-block;zoom:1;*display:inline;width:200px;margin-left:10px;vertical-align:top;}
        .order-table .od-tip {font-size:12px;font-weight:500;background:#f00;padding:2px 3px;color:#fff;line-height:15px;border-radius:3px;margin-left:2px;}
        .order-table .od-code {display:block;line-height:150%;}
        .choose-label {margin-right:30px;}
        .choose-label input {vertical-align:middle;margin-right:5px;}
        .form-control {height:30px;border:1px solid #ccc;min-width:100px;padding:0 10px;}
        textarea.form-control {min-width: 350px;min-height:100px;}
        .btn-file {position:relative;width:70px;height:30px;display:inline-block;zoom:1;*display:inline;margin-right:10px;}
        .btn-file .btn {width:70px;height:30px;background:#ea5870;border:none;border-radius:5px;color:#fff;line-height:28px;}
        .btn-file input {width:70px;height:30px;opacity:0;position:absolute;top:0;left:0;}
        .voucher-imgs {margin-top:10px;}
        .voucher-imgs li {float:left;margin-right:5px;position:relative;}
        .voucher-imgs  .del-img {position:absolute;width:16px;height:16px;background:#ccc;color:#fff;top:-5px;right:-5px;text-align:center;line-height:14px;border-radius:50%;}
        .voucher-imgs  img {width:50px;height:50px;border:1px solid #ccc;}
        .operation-box {margin-top:30px;text-align:center;}
        .operation-box .btn {padding:0px 25px;margin:0 5px;border-radius:5px;font-size:14px;cursor:pointer;}
        .operation-box .btn-primary {background:#ea5870;border:1px solid #e64f25;color:#fff;}
        .operation-box .btn-default {background:#fafafa;border:1px solid #ddd;}
        .hide {display:none;}
        .od-name a{
            line-height:18px;
        }
        pre {border:none!important;}
        .member_right{width:930px; padding:20px;background:#fff;margin-bottom:10px;}
        .memebr_title {
            height: 38px;
            line-height: 38px;
            border: none;
        }
        .memebr_title h2 {
            color: #666;
            text-indent: 20px;
            font-size: 16px;
            font-family: microsoft YaHei;
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
<#include "newtop.ftl"/>
<div style="background:#f5f5f5;">
<div class="container pt20">

    <div class="member_box mb20">
        <#include "newleftmenu.ftl" >
        <div class="member_right fl ml10">
            <div class="memebr_title mb20">
                <h2 class="f14 fb">申请退款</h2>
            </div>

            <div class="return-orders">
                <div class="return-intro">
                    <h4>退款说明：</h4>
                    <p style="width:980px;">${backPriceRemark!''}</p>
                    <#--<p>商城承诺，自客户收到商品之日起7日内可以退货，15日内可以换货，客户可在线提交返修申请办理退换货事宜。</p>-->
                    <#--<p>1. 任何非本商城出售的商品（序列号不符）；</p>-->
                    <#--<p>2. 过保商品（超过三包保修期的商品）；</p>-->
                    <#--<p>3. 未经授权的维修、误用、碰撞、疏忽、滥用、进液、事故、改动、不正确的安装所造成的商品质量问题，或撕毁、涂改标贴、机器序号、防伪标记；</p>-->
                    <#--<p>4. 无法提供商品的发票、保修卡等三包凭证或者三包凭证信息与商品不符及被涂改的；</p>-->
                    <#--<p>5. 其他依法不应办理退换货的。</p>-->
                </div>
                <form id="upload-form" name="upload-form" method="post" enctype="multipart/form-data" target="hidden_frame">
                    <input id="customerId" type="hidden" value="${customerId!''}" name="customerId"/>
                    <input id="backGoodsId" type="hidden" value="${backGoodsIdAndSum}" name="backGoodsIdAndSum"/>
                    <div class="back-orders">
                        <div class="details-item">
                            <nobr>
                            <h3 style="display: inline-block">订单编号：</h3>
                            <span class="code-num">${order.orderCode!''}</span>
                            <h3 style="display: inline-block">订单总额：</h3>
                            <span>&yen;${(order.orderPrice)?string('0.00')}元</span>
                            </nobr>
                        </div>
                        <div class="details-item">
                            <h3>填写申请表</h3>
                            <div class="item-cont">
                                <div class="form-group">
                                    <div class="form-item" style="display:none">
                                        <label class="control-label"><span style="color:red;">*</span>是否退款：</label>
                                        <div class="controls return-choose">
                                            <label class="choose-label"><input name="isBack" class="isBack" type="radio" value="2" checked="checked"/>退款</label>
                                        </div>
                                    </div>
                                    <div class="form-item">
                                        <label class="control-label"><span style="color:red;">*</span>退款原因：</label>
                                        <div class="controls">
                                            <select class="form-control" id="backReason" name="backReason">
                                                <option value="0">请选择</option>
                                                <option value="1">不想买了</option>
                                                <option value="2">收货人信息有误</option>
                                                <option value="3">未按指定时间发货</option>
                                                <option value="4">其他</option>
                                            </select>
                                            <span class="backReasonContent" style="color:red;"></span>
                                        </div>
                                    </div>
                                    <div class="form-item" style="display:none;">
                                        <label class="control-label"><span style="color:red;">*</span>申请凭据：</label>
                                        <div class="controls">
                                            <p><label class="choose-label"><input class="type-pics" type="radio" name="applyCredentials"checked="checked" id="applyCredentials0" value="3"/>没有任何凭据</label></p>
                                            <p><label id="appl_s1" class="choose-label"><input class="type-pics" type="radio" name="applyCredentials" id="applyCredentials1" value="1"/>有发票</label></p>
                                            <p><label id="appl_s2" class="choose-label"><input class="type-pics" type="radio" name="applyCredentials" id="applyCredentials2" value="2"/>有质检报告</label></p>
                                            <span class="applyCredentialsContent" style="color:red;"></span>
                                        </div>
                                    </div>
                                    <div class="form-item item_f" style="display: none;">
                                        <label class="control-label">上传凭证：</label>
                                        <div class="controls">
                                            <div class="btn-file">
                                                <button class="btn" type="button" class="upload_pics2">上传图片</button>
                                                <input type="file" class="upload_pics" id="uploadDocuments" name="uploadDocument" order-id="${order.orderId}"/>
                                                <input type="hidden" name="uploadDocuments" class="upload_documents"/>
                                            </div>
                                            <label class="ml30">最多上传10张图片</label>
                                            <span class="uploadDocumentContent" style="color:red;"></span>
                                            <ul class="voucher-imgs clearfix" id="show_pics${order.orderId}">

                                            </ul>
                                            <font color="red" id="err_img_${order.orderId}" style="display:none;color:red;">最多上传10张图片哦!</font>
                                            <div class="cb"></div>
                                        </div>
                                    </div>
                                    <div class="form-item" id="returnPrice02">
                                        <label class="control-label">退款金额：</label>
                                        <div class="controls">&yen;${(order.orderPrice)?string('0.00')}元</div>
                                        <input type="hidden" class="back_price" value="${order.orderPrice}">
                                    </div>
                                    <div class="form-item">
                                        <label class="control-label">问题说明：</label>
                                        <div class="controls">
                                            <textarea class="form-control" name="backRemark" id="backRemark"  maxlength="200" placeholder="您最多可填写200字"></textarea>                                            <span id="backRemark_tips"></span>
                                            <span class="backRemarkContent" style="color:red;"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="details-item" id="backGoods">
                            <h3>退单商品列表</h3>
                            <span class="backGoodsContent" style="color:red;"></span>
                            <div class="item-cont">
                                <table class="order-table">
                                    <thead>
                                    <tr>

                                        <th class="tl" width="40%">退货商品</th>
                                        <th>商品编号</th>
                                        <th>销售单价</th>
                                        <th>数量</th>
                                        <th>商品总价</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if backorder??>
                                        <#list backorder.goods as border>
                                        <tr>
                                            <td class="tl">
                                                <a class="od-img" href="javascript:;"><img alt="" src="${border.goodsImg!''}"/></a>
                                                <p class="od-name"><a href="javascript:;" style="word-break: break-all;"><#if border.isPresent?? && border.isPresent=="1"><span style="color: white;background-color: lightgrey;">&nbsp;赠品&nbsp;</span>&nbsp;</#if>${border.goodsName!''}</a></p>
                                            </td>
                                            <td><span class="od-code">${border.goodsNo}</span></td>
                                            <td>&yen;
                                                <#if border.goodsPrice??>
                                                ${border.goodsPrice?string('0.00')}
                                                </#if>
                                            </td>
                                            <td>${border.goodsNum}</td>
                                            <td>&yen;
                                                <#if border.goodsInfoSumPrice??>
                                                ${border.goodsInfoSumPrice?string('0.00')}
                                                </#if>
                                            </td>
                                        </tr>
                                        </#list>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                        <div class="operation-box">
                            <button class="btn btn-primary" id="submitButtonprice" type="button">提交</button>
                            <button class="btn btn-default" type="button"><a href="${basePath}/customer/myorder.html">取消</a></button>
                        </div>
                    </div>
                    <input type="hidden" name="orderId" id="orderId" value="${order.orderId}"/>
                    <input type="hidden" name="orderCode" value="${order.orderCode}"/>
                    <input type="hidden" name="backPrice" value="${backorder.moneyPaid}"/>
                </form>
                <iframe name="hidden_frame" style="display:none"></iframe>
            </div>


        </div><!-- END OF member_right -->
        <div class="cb"></div>
    </div><!-- END OF member_box -->
</div><!--container-->

<div class="member-dialog promotion_dialog_2">
    <div class="member-dialog-body">
        <div class="title"><a href="javascript:;" onclick="cls()">×</a>提示</div>
        <div class="tc">
            <div class="que-delete clearfix">
                <img src="${basePath}/images/images_l6.png"/>
                <div class="fl tl">
                    <p class="f16 red">您确定要该图片吗？</p>
                    <p >删除后，您将无法恢复！</p>
                    <div class="m-btn mt20">
                        <a href="javascript:;" onclick="delPic()" >确定</a>
                        <a onclick="cls()">取消</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<div class="member-dialog dia30">
    <div class="member-dialog-body">
        <div class="title"><a href="javascript:;" onclick="cls()">×</a>提示</div>
        <div class="tc">
            <div class="que-delete clearfix">
                <img src="${basePath}/images/images_l6.png"/>
                <div class="fl tl">
                    <p class="f16 red">该订单状态已发生改变！</p>
                    <div class="m-btn mt20">
                        <a  id="go_pay_01" href="javascript:cls();">确定</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>



</div>
<input type="hidden" class="flag_saved" value="0">
<#--引入底部 <#include "/index/bottom.ftl" /> -->
<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">
    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>
<script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${basePath}/js/default.js"></script>
<script type="text/javascript" src="${basePath}/js/jcarousellite_1.0.1.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.zclip.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.zclip.min.js"></script>
<script type="text/javascript" src="${basePath}/js/tab-switch.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/applybackmoney.js"></script>
<script type="text/javascript" src="${basePath}/js/newapp.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/findcode.js"></script>
<script type="text/javascript" src="${basePath}/js/jsOperation.js"></script>
<script>
    $(function(){
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
    })
</script>
</body>
</html>