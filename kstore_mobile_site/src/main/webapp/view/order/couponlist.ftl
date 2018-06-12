<!doctype html>
<#assign basePath=request.contextPath>
<html lang="en"><head>
    <meta charset="UTF-8">
    <title>订单确认-优惠券选择</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css">
</head>
<body>
<div class="bar-top">
    <a class="bar-item half active" onclick="showCouponList(this)">可用券（<span>${couponlist?size}</span>）</a>
    <a class="bar-item half" onclick="showCoupons(this)">不可用券（<span><#if coupons??&&coupons?size!=0>${coupons?size}<#else>0</#if></span>）</a>
</div>
<div class="order content-order-confirm">
    <div class="use-coupons">
        <div class="coupons-xin">
            <#list couponlist as coupon>
                <div class="coupons-item  coupons" onclick="changeCoupon(this)">
                    <i class="select-box2 <#if codeNo??&& coupon.codeNo==codeNo>selected</#if>"></i>
                    <input value="${coupon.codeNo}" class="codeNo" type="hidden"/>
                    <div class="coupons-body">
                        <div class="body-left">
                            <div class="title">
                                <h1>
                                    <span>¥</span>
                                    <span class="num">
                                        <#if coupon.couponRulesType=='1'>
                                            ${coupon.couponStraightDown.downPrice}
                                            <#elseif coupon.couponRulesType=='2'>
                                             ${coupon.couponFullReduction.reductionPrice}
                                        </#if>
                                    </span>
                                </h1>
                                <p>
                                    <#if coupon.couponRulesType=='1'>
                                        直降${coupon.couponStraightDown.downPrice}
                                    <#elseif coupon.couponRulesType=='2'>
                                   满${coupon.couponFullReduction.fullPrice}减${coupon.couponFullReduction.reductionPrice}
                                    </#if>
                                </p>
                            </div>
                        </div>
                        <div class="body-right">
                            <dl>
                                <dt>${coupon.couponName!''}</dt>
                                <#--<dd><a href="javascript:;" class="show-range">查看适用范围 &gt;&gt;</a></dd>-->
                                <dd><label for="">有效期:</label>${coupon.couponStartTime?string("YYYY.MM.dd")}-${coupon.couponEndTime?string("YYYY.MM.dd")}</dd>
                                <dd><label for="">券号:</label>${coupon.codeNo}</dd>
                            </dl>
                        </div>
                    </div>
                </div>
            </#list>
                <#if coupons??&&coupons?size!=0>
                    <#list coupons as coupon>
                        <div class="coupons-item  update" style="display: none;">
                            <i class="select-box2 <#if codeNo??&& coupon.codeNo==codeNo>selected</#if>"></i>
                            <input value="${coupon.codeNo}" class="codeNo" type="hidden"/>
                            <div class="coupons-body">
                                <div class="body-left">
                                    <div class="title">
                                        <h1>
                                            <span>¥</span>
                                    <span class="num">
                                        <#if coupon.couponRulesType=='1'>
                                            ${coupon.couponStraightDown.downPrice}
                                            <#elseif coupon.couponRulesType=='2'>
                                        ${coupon.couponFullReduction.reductionPrice}
                                        </#if>
                                    </span>
                                        </h1>
                                        <p>
                                            <#if coupon.couponRulesType=='1'>
                                                直降${coupon.couponStraightDown.downPrice}
                                            <#elseif coupon.couponRulesType=='2'>
                                                满${coupon.couponFullReduction.fullPrice}减${coupon.couponFullReduction.reductionPrice}
                                            </#if>
                                        </p>
                                    </div>
                                </div>
                                <div class="body-right">
                                    <dl>
                                        <dt>${coupon.couponName!''}</dt>
                                        <#--<dd><a href="javascript:;" class="show-range">查看适用范围 &gt;&gt;</a></dd>-->
                                        <dd><label for="">有效期:</label>${coupon.couponStartTime?string("YYYY.MM.dd")}-${coupon.couponEndTime?string("YYYY.MM.dd")}</dd>
                                        <dd><label for="">券号:</label>${coupon.codeNo}</dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </#list>
                <#else>
                </#if>

        </div>
        <div class="list-item bottom-full">
            <a class="btn btn-full" href="${basePath}/toaddcoupons.htm?flag=1<#if codeNo??>&codeNo=${codeNo}</#if><#if invoiceTitle??>&invoiceTitle=${invoiceTitle}</#if><#if invoiceType??>&invoiceType=${invoiceType}</#if>"><i></i>添加优惠卷</a>
        </div>
    </div>
</div>

<form id="subForm" method="post">
    <input type="hidden" name="codeNo" id="codeNo" >
    <input type="hidden" value="${invoiceType!''}" name="invoiceType" >
    <input type="hidden" name="invoiceTitle" value="${invoiceTitle!''}" >
</form>
<script src="${basePath}/js/jquery-1.11.1.min.js"></script>
<script>
    function changeCoupon(obj){
        if($(obj).find(".selected").length==0){
            $(".select-box2").removeClass("selected");
            $(obj).find(".select-box2:first").addClass("selected");
            $("#codeNo").val($(obj).find(".codeNo").val())
            $("#subForm").attr("action","suborder.htm").submit();
        }else{
            $(".select-box2").removeClass("selected");
            $("#codeNo").val("")
            $("#subForm").attr("action","suborder.htm").submit();
        }
    }

    function showCoupons(obj){
        $(".bar-item").removeClass("active");
        $(obj).addClass("active");
        $(".coupons").hide();
        $(".update").show();
    }
    function showCouponList(obj){
        $(".bar-item").removeClass("active");
        $(obj).addClass("active");
        $(".update").hide();
        $(".coupons").show();
    }
</script>
</body>
</html>