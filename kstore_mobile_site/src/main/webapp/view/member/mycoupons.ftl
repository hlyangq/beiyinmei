<!DOCTYPE html>
<html lang="zh-CN">
<head>
   <#assign basePath=request.contextPath>
  <meta charset="UTF-8">
  <title>会员中心 - 优惠券</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
  <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
   <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>

<div class="common_tabs member_box row">
  <div class="col-8">
    <a href="${basePath}/customer/coupon.html?type=1" <#if type?? && type=='1'> class="selected"</#if>>
      未使用(${status1!''})
    </a>
  </div>
  <div class="col-8">
    <a href="${basePath}/customer/coupon.html?type=2" <#if type?? && type=='2'> class="selected"</#if>>
      已使用(${status2!''})
    </a>
  </div>
  <div class="col-8">
    <a href="${basePath}/customer/coupon.html?type=3" <#if type?? && type=='3'> class="selected"</#if>>
      已过期(${status3!''})
    </a>
  </div>
</div>

<div class="coupons-xin">
 <#if pb?size!=0>
  <#list pb as coupon>
  <div class="coupons-item <#if type?? && (type=='2' || type=='3')>update</#if>">
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
          <dd>
                            <#if coupon.codeNo?? && coupon.codeNo?length gt 16>
                                ${coupon.codeNo?substring(16)}
                            <#else>
                                ${coupon.codeNo!''}
                            </#if>
                         </dd>
        </dl>
      </div>
      <span class="state">
                <#if coupon.codeStatus??>
                <#switch coupon.codeStatus>
                <#case '1'>
                                                              未使用
                <#break>
                <#case '2'>
                                                               已使用
                <#break>
                 <#case '3'>
                                                               已过期
                <#break>
                <#default>
                </#switch>
              </#if>
        </span>
    </div>
  </div>
  </#list>
 <#else>
    <div class="content" id="no">
      <div class="no_tips">
        <p>木有优惠券哦</p>
      </div>
    </div>
</#if>
</div>

<div class="bottom-fixed p10">
  <a href="${basePath}/toaddcoupons.html" class="btn btn-full">+ 添加优惠券</a>
</div>
<#--
<#include '/common/smart_menu.ftl' />
-->
<script>
  
    
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