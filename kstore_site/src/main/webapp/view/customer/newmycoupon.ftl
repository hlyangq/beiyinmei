<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>我的优惠券-${topmap.systembase.bsetName}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="Keywords" content="${topmap.seo.meteKey}">
    <meta name="description" content="${topmap.seo.meteDes}">
<#assign basePath=request.contextPath>
<#if (topmap.systembase.bsetHotline)??>
    <link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
    <link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css" />
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>

<#if codeStatus=='2'||codeStatus=='3'>
    <style> .air-discountTic{background-color: #b2b2b2;  cursor: not-allowed;  }
    .air-discountTic:hover{background-color: #b2b2b2;  cursor: not-allowed;  }</style>
</#if>


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


<div style="background: #f5f5f5;">
    <div class="container clearfix pt20 pb10">
        <!--new_member_left-->
    <#include "newleftmenu.ftl"/>
        <div class="new_member-right">
            <div class="air-account-discount">
                <div class="n-title">我的优惠券</div>
                <div class="tagMenu air-account-menu">
                    <ul class="menu clearfix">
                        <li  <#if codeStatus=='1'>class="current"</#if>  onclick="javascript:location='${basePath}/mycoupon-1.html'"><a href="javascript:;">未使用优惠券</a></li>
                        <li <#if codeStatus=='2'>class="current"</#if>  onclick="javascript:location='${basePath}/mycoupon-2.html'"><a href="javascript:;">已使用优惠券</a>
                        </li>
                        <li <#if codeStatus=='3'>class="current"</#if>  onclick="javascript:location='${basePath}/mycoupon-3.html'"><a href="javascript:;">已过期优惠券</a></li>
                    </ul>
                </div>
                <div class="content">
                    <div class="layout">
                        <div class="air-discountList">
                        <#if (pb.list?size>0) >
                            <#list pb.list as coupen>

                                <div class="air-discountTic mr20">
                                    <div class="ticTitle">
                                        <div class="tl">
                                            <span class="ticValue">&yen;</span>
                                            <span class="ticValue"><#if coupen.couponRulesType??><#if coupen.couponRulesType=="1" &&(coupen.couponStraightDown.downPrice)??>${coupen.couponStraightDown.downPrice}<#elseif coupen.couponRulesType=="2" &&coupen.couponFullReduction.reductionPrice??>${coupen.couponFullReduction.reductionPrice}</#if></#if></span>
                                        </div>
                                    <#--<div class="fr">券号：-->
                                    <#--<span class="ticNumber">${coupen.codeNo}</span>-->
                                    <#--</div>-->
                                    </div>
                                    <ul>
                                        <li><#if coupen.businessId==0>${basicSet.bsetName}自营<#elseif coupen.thirdName??>${coupen.thirdName}</#if></li>
                                        <li>使用条件：<#if coupen.couponRulesType??><#if coupen.couponRulesType=="1" &&(coupen.couponStraightDown.downPrice)??>不限制<#elseif coupen.couponRulesType=="2" &&coupen.couponFullReduction.fullPrice??>满
                                        ${coupen.couponFullReduction.fullPrice?string.currency}
                                        </#if></#if></li>
                                        <li>使用日期：${coupen.couponStartTime?string("yyyy-MM-dd")}&nbsp;至&nbsp;${coupen.couponEndTime?string("yyyy-MM-dd")}</li>
                                        <#if coupen.gclist??>
                                            <li>品类限制：仅可购买指定分类商品</li>
                                            <li>平台限制：<#list coupen.gclist as gclist>${gclist.catName} </#list></li>
                                        </#if>
                                        <#if coupen.gblist??>
                                            <li>品类限制：仅可购买指定品牌商品</li>
                                            <li>平台限制：<#list coupen.gblist as gblist>${gblist.brandName} </#list></li>
                                        </#if>
                                        <#if coupen.gplist??>
                                            <li>品类限制：仅可购买指定商品</li>
                                            <li>平台限制：<#list coupen.gplist as gplist>${gplist.goodsInfoName} </#list></li>
                                        </#if>
                                    </ul>
                                <#--<span class="deleteTic">-->
                                <#--<a onclick="dia(3)"><img src="${basePath}/images/delete-tic.png" alt="删除"/></a>-->
                                <#--</span>-->
                                </div>

                            </#list>
                        <#else>
                            <div  style="margin-top:10px;border:1px #e8e8e8 solid; height:40px; text-align: center; font-size: 18px;line-height:40px;;">
                                暂无优惠券！
                            </div>
                        </#if>

                        </div>
                        <div class="paging_area">
                        <#if pb.list?? && (pb.list?size!=0)>
                                    <#-- 分页 -->
                                        <#import "../pagination/pageBean.ftl" as page>
                                        <@page.pagination pb />
                                    </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>  <div class="mask"></div>
<div class="member-dialog dia3">
    <div class="member-dialog-body">
        <div class="title"><a href="javascript:;" onclick="cls()">×</a>提示</div>
        <div class="tc">
            <div class="que-delete clearfix">
                <img src="${basePath}/images/images_l6.png"/>
                <div class="fl tl">
                    <p class="f16 red">确定要删除此优惠券吗？</p>
                    <div class="m-btn mt20">
                        <a  href="javascript:;" id="delMyCoupon()">确定</a>
                        <a class="" href="javascript:cls();">取消</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<#--引入底部 <#include "/index/bottom.ftl" /> -->
<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">
    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>


<script type="text/javascript" src="${basePath}/js/default.js"></script>
<script type="text/javascript" src="${basePath}/js/jcarousellite_1.0.1.js"></script>
<script type="text/javascript" src="${basePath}/js/tab-switch.js"></script>
<script type="text/javascript" src="${basePath}/js/newapp.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/findcode.js"></script>
<script type="text/javascript" src="${basePath}/js/jsOperation.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
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
        //选择标签
        var codeStatus = $("#codeStatus").val();
        if(codeStatus==1){
            $("#tag1").addClass('current');
            $("#tag2").removeClass('current');
            $("#tag3").removeClass('current');
        }else if(codeStatus==2){
            $("#tag1").removeClass('current');
            $("#tag2").addClass('current');
            $("#tag3").removeClass('current');
        }else if(codeStatus==3){
            $("#tag1").removeClass('current');
            $("#tag2").removeClass('current');
            $("#tag3").addClass('current');
        }
        $(".pro_sort").addClass("pro_sort_close");
        $(".new_member_left div:eq(3) ul li:eq(4)").addClass("cur");
    });
    function showDialog(n){
        $(".mask").fadeIn();
        $(".promotion_dialog_"+n).fadeIn();
    };
    function cls(){
        $(".dialog").fadeOut();
        $(".mask").fadeOut();
    };
    $("#mycoupon").addClass("cur");
</script>
</body>
</html>
