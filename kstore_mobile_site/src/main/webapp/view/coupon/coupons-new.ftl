<!DOCTYPE html>
<html lang="en">
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>优惠券领券</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>
<div class="coupons-xin">

    <#if couponList??>
        <#list couponList as coupon>
        <#assign a =1/>
                <div class="coupons-item <#if cList?? &&cList?size gt 0 ><#list cList as c><#if c==coupon.couponId><#assign a =2/><#if a==2>update</#if></#if></#list></#if>" onClick="<#if a==1>getOffCoupon(${coupon.couponId},this);</#if>">
                    <div class="coupons-body">
                        <div class="body-left">
                            <div class="title">
                                <h1>
                                    <span>¥</span>
            <span class="num">
                <#if (coupon.couponRulesType=="1")>
                        ${((coupon.couponStraightDown.downPrice)!'0.00')}
                    <#else>
                ${((coupon.couponFullReduction.reductionPrice)!'0.00')}
                </#if>
            </span>
                                </h1>
                                <p><#if (coupon.couponRulesType=="1")>
                                    直减  ${((coupon.couponStraightDown.downPrice)!'0.00')}
                                <#else>
                                    满${((coupon.couponFullReduction.fullPrice)!'0.00')}减${((coupon.couponFullReduction.reductionPrice)!'0.00')}
                                </#if>
                                </p>
                            </div>
                        </div>
                        <div class="body-right">
                            <dl>
                                <dt><#if coupon.couponRulesType=="1" &&(coupon.couponStraightDown.downPrice)??>不限制<#elseif coupon.couponRulesType=="2" &&coupon.couponFullReduction.fullPrice??>满
                                ${coupon.couponFullReduction.fullPrice!'0.00'}
                                </#if>
                                </dt>
                                <dd>
                                    <a href="javascript:;" class="show-range" onClick="showfw(${coupon.couponId});">查看适用范围 &gt;&gt;</a>
                    <span style="display:none;" id="span${coupon.couponId}">
                    <#if (coupon.gplist??) >
                        <#list coupon.gplist as gp>
                            <#if gp.goodsInfoName??>
                            ${gp.goodsInfoName!''}；<br>
                            </#if>
                            </a>
                        </#list>
                    </#if>

                        <#if (coupon.gclist??) >
                            <#list coupon.gclist as gc>
                                <#if (gc.catName)??>
                                ${gc.catName!''}；<br>
                                </#if>
                                </a>
                            </#list>
                        </#if>
                        <#if (coupon.gblist??) >
                            <#list coupon.gblist as gb>
                                <#if (gb.brandName)??>
                                ${gb.brandName!''}；<br>
                                </#if>
                                </a>
                            </#list>
                        </#if>
                    </span>
                                </dd>
                                <dd>${coupon.couponStartTime?string("yyyy.MM.dd")}-
                                ${coupon.couponEndTime?string("yyyy.MM.dd")}
                                </dd>

                            </dl>
                        </div>
                    </div>
            </div>
        </#list>
    </#if>

</div>
<!--
<div class="tip-box">
    <div class="tip-body">
        <i class="success"></i>
        <h3>领取成功</h3>
    </div>
</div>
-->

<script>
    $(function(){
        /**
         * 查询当前登录用户所领取的所有优惠券
         * */
                
     });

    function getOffCoupon(couponId,obj){
        $.ajax({
            url:"getOffCouponMobile.htm?couponId="+couponId,
            type:"post",
            success:function(data){
                if(data == 1){
                    setTimeout(function(){
                        $('body').append('<div class="tip-box"><div class="tip-body"><i class="success"></i><h3>领取成功</h3></div></div>');
                    },500);
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }else if(data == 2){
                    setTimeout(function(){
                        $('body').append('<div class="tip-box"><div class="tip-body"><i class="failed"></i><h3>已领取完</h3></div></div>');
                    },500);
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                    $(obj).addClass("update");
                    $(obj).removeAttr("onClick");
                }else if(data == 3){
                    var url = window.location.href;
                    url = url.substring(url.lastIndexOf("/")+1,url.length);
                    location.href="loginm.html?url=/"+url;
                }else if(data == 4){
                    setTimeout(function(){
                        $('body').append('<div class="tip-box"><div class="tip-body"><i class="failed"></i><h3>已领取过</h3></div></div>');
                    },500);
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                    $(obj).addClass("update");
                    $(obj).removeAttr("onClick");
                }
            }
        });
    }

    function showfw(couponId){
        var fanwei = document.getElementById("span"+couponId).innerHTML;
        var showRange = dialog({
            width : 300,
            content : '<h4><strong>该优惠券适用范围：</strong></h4><p style="line-height:22px;">'+fanwei+'</p>',
            okValue : '确定',
            ok : function(){
                return true;
            }
        });
        showRange.showModal();
    }
</script>

</body>
</html>