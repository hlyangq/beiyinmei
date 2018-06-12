<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="Keywords" content="${topmap.seo.meteKey}">
    <meta name="description" content="${topmap.seo.meteDes}">
<#assign basePath=request.contextPath>
    <title>我的购物车-${topmap.systembase.bsetName}</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/base.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/css/index.css" />
<#if (topmap.systembase.bsetHotline)??>
    <link rel = "Shortcut Icon" href="${(topmap.systembase.bsetHotline)!''}">
<#else>
    <link rel = "Shortcut Icon" href="${basePath}/images/Paistore.ico">
</#if>
    <link rel="stylesheet" type="text/css" href="${basePath}/index_two/css/header.css" />
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery.lazyload.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/cloud-zoom.1.0.2.min.js"></script>
    <style type="text/css">
        .area_text {margin-top: 5px;}
        .locate_cont{top:30px;}
        .dia_ops a{
            padding:0px;
        }
    </style>

    <script type="text/javascript">

        function checkOne(obj){
            for(i=0;i<$(".mjchecked").length;i++){
                if(!$(".mjchecked")[i].checked){
                    $(".check_all").attr("checked", false);
                    break;
                }else{
                    $(".check_all").attr("checked", true);
                }
            }

            //获取外部的DIV 是否是促销的商品
            var codexType = $(obj).parents(".marketgroup").attr("attr-codextype");
            if(codexType!='0'){
                if(obj.checked){

                    if(codexType=='1'){
                        var youhui = 0 ;
                        var xiaoji = 0;
                        $(obj).parents(".marketgroup").find(".mjchecked").each(function(){
                            if($(this)[0].checked) {
                                var man = $(this).parents(".cart_goods").find(".count").find(".marketPrice").val();
                                var jian = $(this).parents(".cart_goods").find(".count").find(".text").val();
                                var onesumprice = $(this).parents(".cart_goods").find(".smprice").val();
                                youhui = accAdd(youhui, accMul(man, jian));
                                xiaoji = accAdd(onesumprice, xiaoji);
                            }
                        });
                        $(obj).parents(".marketgroup").find(".youhui").val(youhui);

                        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);

                    }

                    else if(codexType=='11'){//抢购
                        var youhui = 0 ;
                        var xiaoji = 0;
                        $(obj).parents(".marketgroup").find(".cart_goods").each(function(){
                            //金额
                            var man= $(this).find(".g_count").find(".count").find(".rushPrice").val();
                            //件数
                            var jian = $(this).find(".g_count").find(".count").find(".text").val();
                            var onesumprice =$(this).find(".g_promotion").find(".smprice").val();
                            youhui = accAdd(youhui,accMul(man,jian));
                            xiaoji = accAdd(onesumprice,xiaoji);
                        });
                        $(obj).parents(".marketgroup").find(".youhui").val(youhui);

                        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);
                    }


                    else if(codexType=='5'){
                        var mj_sumprice = 0;
                        var mj_end=0;
                        //满减
                        $(obj).parents(".marketgroup").find(".mjchecked").each(function(){
                            //判断是否选中
                            if($(this)[0].checked){
                                mj_sumprice = accAdd($(this).parents(".cart_goods").find(".smprice").val(),mj_sumprice);
                            }
                        });


                        $(obj).parents(".marketgroup").find(".manjian_reducePrice").each(function(){
                            var man = $(this).val().split(",")[0];
                            var jian = $(this).val().split(",")[1];
                            if(Subtr(mj_sumprice,man)>=0){
                                mj_end = jian;
                            }

                        });
                        $(obj).parents(".marketgroup").find(".xiaoji").val(mj_sumprice);
                        $(obj).parents(".marketgroup").find(".youhui").val(mj_end);

                    }else if(codexType=='8'){
                        //满折
                        var mz_sumprice = 0;
                        var mz_end=0;
                        $(obj).parents(".marketgroup").find(".mjchecked").each(function(){
                            //判断是否选中
                            if($(this)[0].checked){
                                mz_sumprice = accAdd($(this).parents(".cart_goods").find(".smprice").val(),mz_sumprice);
                            }
                        });

                        $(obj).parents(".marketgroup").find(".manzhe_fullbuyDiscount").each(function(){
                            var man = $(this).val().split(",")[0];
                            var zhe = $(this).val().split(",")[1];
                            if(Subtr(mz_sumprice,man)>=0){
                                mz_end = accMul(mz_sumprice,Subtr(1,zhe));
                            }

                        });
                        $(obj).parents(".marketgroup").find(".xiaoji").val(mz_sumprice);
                        $(obj).parents(".marketgroup").find(".youhui").val(mz_end);

                    }else if(codexType=='-1'){
                        //-1代表是套装
                        var num = $(obj).parents(".cart_item").find(".decrement").next().val();
                        var xiaoji = 0 ;
                        $(obj).parents(".marketgroup").find(".cart_item_goods").each(function(){
                            //var isprice = $(this).find(".smprice").val();//accMul($(this).find(".smprice").val(),num);不用相乘，会导致十倍差距
                            var isprice = accMul($(this).find(".smprice").val(),1);//不用相乘，会导致十倍差距
                            xiaoji = accAdd(xiaoji,isprice);
                        });
                        var groupPreferamount = $(obj).parents(".cart_item").find(".groupPreferamount").val();
                        var youhui = accMul(num,groupPreferamount);
                        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);
                        $(obj).parents(".marketgroup").find(".youhui").val(youhui);
                    }else if(codexType=='6'){
                        var youhui = 0 ;
                        var xiaoji = 0;
                        $(obj).parents(".marketgroup").find(".mjchecked").each(function(){
                            if($(this)[0].checked) {
                                var man = 0;
                                var jian = $(this).parents(".cart_goods").find(".count").find(".text").val();
                                var onesumprice = $(this).parents(".cart_goods").find(".smprice").val();
                                youhui = accAdd(youhui, accMul(man, jian));
                                xiaoji = accAdd(onesumprice, xiaoji);
                            }
                        });
                        $(obj).parents(".marketgroup").find(".youhui").val(youhui);
                        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);
                        var thisprice=$(obj).parents(".cart_goods").find(".smprice").val();
                        var num = $(obj).parents(".cart_goods").find(".text").val();

                        //赠送类型0金额1件数
                        var presentType = $(obj).parents(".marketgroup").attr("attr-pretype");
                        //赠送方式0全赠1一种赠送
                        var presentMode = $(obj).parents(".marketgroup").find(".exchange").attr("attr_presentmode");
                        if(presentType == 0){
                            //原先所有满赠商品的总价（判断符合几级满赠）
                            var preAll = $(obj).parents(".marketgroup").find(".gift:first").find(".fullGiftAllPrices").val();
                            //现在剩下的满赠商品总价
                            var nowAll = accAdd(preAll,thisprice);
                            $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                                $(this).val(nowAll);
                            })
                        }else{
                            //满数量赠时
                            //原先所有满赠商品的总数量（判断符合几级满赠）
                            var preAll = $(obj).parents(".marketgroup").find(".gift:first").find(".fullGiftAllPrices").val();
                            var nowAll = accAdd(preAll,num);
                            $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                                $(this).val(nowAll);
                            })
                        }
                        $(obj).parents(".marketgroup").find(".gift:visible").hide();
                        isHasGift(obj,nowAll);
                    }
                }else{
                    if(codexType=='1'){
                        var man= $(obj).parents(".cart_goods").find(".marketPrice").val();
                        var jian = $(obj).parents(".cart_goods").find(".text").val();
                        var you= $(obj).parents(".marketgroup").find(".youhui").val();
                        youhui=Subtr(you,accMul(man,jian));
                        $(obj).parents(".marketgroup").find(".youhui").val(youhui);
                        //直降
                        var thisprice=$(obj).parents(".cart_goods").find(".smprice").val();
                        //总计+单品小计
                        var oldxiaoji = $(obj).parents(".marketgroup").find(".xiaoji").val();
                        var xiaoji = Subtr(oldxiaoji,thisprice);
                        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);


                    }else if(codexType=='6'){
                        var man= 0;
                        var jian = $(obj).parents(".cart_goods").find(".text").val();
                        var you= $(obj).parents(".marketgroup").find(".youhui").val();
                        youhui=Subtr(you,accMul(man,jian));
                        $(obj).parents(".marketgroup").find(".youhui").val(youhui);
                        //直降
                        var thisprice=$(obj).parents(".cart_goods").find(".smprice").val();
                        //总计+单品小计
                        var oldxiaoji = $(obj).parents(".marketgroup").find(".xiaoji").val();
                        var xiaoji = Subtr(oldxiaoji,thisprice);
                        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);

                        //赠送类型0金额1件数
                        var presentType = $(obj).parents(".marketgroup").attr("attr-pretype");
                        //赠送方式0全赠1一种赠送
                        var presentMode = $(obj).parents(".marketgroup").find(".exchange").attr("attr_presentmode");
                        if(presentType == 0){
                            //原先所有满赠商品的总价（判断符合几级满赠）
                            var preAll = $(obj).parents(".marketgroup").find(".gift:first").find(".fullGiftAllPrices").val();
                            //现在剩下的满赠商品总价
                            var nowAll = Subtr(preAll,thisprice);
                            $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                                $(this).val(nowAll);
                            })
                        }else{
                            //满数量赠时
                            //原先所有满赠商品的总数量（判断符合几级满赠）
                            var preAll = $(obj).parents(".marketgroup").find(".gift:first").find(".fullGiftAllPrices").val();
                            var nowAll = Subtr(preAll,jian);
                            $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                                $(this).val(nowAll);
                            })
                        }
                        $(obj).parents(".marketgroup").find(".gift:visible").hide();
                        isHasGift(obj,nowAll);
                    }
//                    else if(codexType=='10'){//团购
//                        var groupPrice= $(obj).parents(".cart_goods").find(".groupPrice").val();
//                        var jian = $(obj).parents(".cart_goods").find(".text").val();
//                        var you= $(obj).parents(".marketgroup").find(".youhui").val();
//                        youhui=Subtr(you,accMul(groupPrice,jian));
//                        $(obj).parents(".marketgroup").find(".youhui").val(youhui);
//                        //团购
//                        var thisprice=$(obj).parents(".cart_goods").find(".smprice").val();
//                        //总计+单品小计
//                        var oldxiaoji = $(obj).parents(".marketgroup").find(".xiaoji").val();
//                        var xiaoji = Subtr(oldxiaoji,thisprice);
//                        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);
//
//                    }

                    else if(codexType=='11'){//抢购
                        var rushPrice= $(obj).parents(".cart_goods").find(".rushPrice").val();
                        var jian = $(obj).parents(".cart_goods").find(".text").val();
                        var you= $(obj).parents(".marketgroup").find(".youhui").val();
                        youhui=Subtr(you,accMul(rushPrice,jian));
                        $(obj).parents(".marketgroup").find(".youhui").val(youhui);
                        //抢购
                        var thisprice=$(obj).parents(".cart_goods").find(".smprice").val();
                        //总计+单品小计
                        var oldxiaoji = $(obj).parents(".marketgroup").find(".xiaoji").val();
                        var xiaoji = Subtr(oldxiaoji,thisprice);
                        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);

                    }

                    else if(codexType=='5'){
                        //满减
                        var mj_sumprice = 0;
                        var mj_end=0;
                        //满减
                        $(obj).parents(".marketgroup").find(".mjchecked").each(function(){
                            //判断是否选中
                            if($(this)[0].checked){
                                mj_sumprice = accAdd($(this).parents(".cart_goods").find(".smprice").val(),mj_sumprice);
                            }
                        });

                        $(obj).parents(".marketgroup").find(".manjian_reducePrice").each(function(){
                            var man = $(this).val().split(",")[0];
                            var jian = $(this).val().split(",")[1];
                            if(Subtr(mj_sumprice,man)>=0){
                                mj_end = jian;
                            }

                        });
                        $(obj).parents(".marketgroup").find(".xiaoji").val(mj_sumprice);
                        $(obj).parents(".marketgroup").find(".youhui").val(mj_end);

                    }else if(codexType=='8'){
                        //满折
                        var mz_sumprice = 0;
                        var mz_end=0;
                        $(obj).parents(".marketgroup").find(".mjchecked").each(function(){
                            //判断是否选中
                            if($(this)[0].checked){
                                mz_sumprice = accAdd($(this).parents(".cart_goods").find(".smprice").val(),mz_sumprice);
                            }
                        });

                        $(obj).parents(".marketgroup").find(".manzhe_fullbuyDiscount").each(function(){
                            var man = $(this).val().split(",")[0];
                            var zhe = $(this).val().split(",")[1];
                            if(Subtr(mz_sumprice,man)>=0){
                                mz_end = accMul(mz_sumprice,Subtr(1,zhe));
                            }

                        });
                        $(obj).parents(".marketgroup").find(".xiaoji").val(mz_sumprice);
                        $(obj).parents(".marketgroup").find(".youhui").val(mz_end);

                    }else if(codexType=='-1'){
                        //-1代表是套装
                        $(obj).parents(".marketgroup").find(".xiaoji").val(0);
                        $(obj).parents(".marketgroup").find(".youhui").val(0);
                    }
                }
            }else{
                $(obj).parents(".marketgroup").find(".mjchecked").each(function(){
                    //判断是否选中
                    if($(this)[0].checked){
                        var oldxiaoji = $(obj).parents(".marketgroup").find(".smprice").val();
                        $(obj).parents(".marketgroup").find(".xiaoji").val(oldxiaoji);
                    }else{
                        $(obj).parents(".marketgroup").find(".xiaoji").val(0);
                    }
                });
            }
            lastsum();
        }

        function isHasGift(obj,nowAll){
            //
            //赠送类型0金额1件数
            var presentType = $(obj).parents(".marketgroup").attr("attr-pretype");
            //赠送方式0全赠1一种赠送
            var presentMode = $(obj).parents(".marketgroup").find(".gift:visible").find(".exchange").attr("attr_presentmode");
            $(obj).parents(".marketgroup").find(".grades").each(function(){
                if(parseFloat(nowAll) >= parseFloat($(this).val())){
                    $(this).parent(".gift").show();
//                    $(this).parent(".gift").find(".light").show();
                }
            });
            $(obj).parents(".marketgroup").find(".gift:visible:last").show();
            $(obj).parents(".marketgroup").find(".gift:visible:last").siblings(".gift:visible").hide();
            fillData();
        }

        function lastsum(){
            var zongji = 0;
            var fanxian = 0;
            var allcount = 0;
            /*
            $(".xiaoji").each(function(){
                zongji = accAdd(zongji,$(this).val());
            });

            $(".youhui").each(function(){
                fanxian = accAdd(fanxian,$(this).val());
            });
            */
            //小计与优惠需要单独一对一计算，因为一个商品的优惠和小计，相加为负数
            var xjs = $(".xiaoji");//理论上这两个个数相等
            var yhs = $(".youhui");//
            var payPricelist = [];
            for(var i=0;i<xjs.length;i++){
                var $xji = $(xjs[i]);
                var $yhi = $(yhs[i]);

                var xjVal = $xji.val();
                var yhVal = $yhi.val();
                //优惠大于小计，有问题,商品为0.01
                if(parseFloat(yhVal)>parseFloat(xjVal)){
                    zongji = accAdd(zongji,xjVal);
                    fanxian = accAdd(fanxian,Subtr(xjVal,'0.01'));//总返现减去0.01
                    payPricelist.push(accAdd(xjVal,"0.00"));//此时的总钱小计中
                }else{
                    zongji = accAdd(zongji,$xji.val());
                    fanxian = accAdd(fanxian,$yhi.val());
                    payPricelist.push(parseFloat($xji.val()));
                }
            }

            $(".mjchecked").each(function(){
                if($(this)[0].checked){
                    allcount = accAddInt($(this).parents(".cart_goods").find(".decrement").next().val(),allcount);
                }
            });

            $(".allcount").html(allcount);
            $(".zongji").html("￥"+zongji);
            $(".fanxian").html("-￥"+fanxian);
            if(Subtr(zongji,fanxian)<0){
                $(".payPrice").html("￥"+0.01);
            }else{
                var payAll = "0.00";
                for(var i=0;i<payPricelist.length;i++){
                    payAll = accAdd(payAll,payPricelist[i]);
                }
                //payAll = payAll.toFixed(2);//两位小数
                $(".payPrice").html("￥"+Subtr(payAll,fanxian));
            }

        }

    </script>

</head>

<body>
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
        <#include "../index/newtop7.ftl">
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
<div class="container">
    <input type="hidden" id="currentProvince" value="${chProvince!''}" />
    <input type="hidden" id="basePath" value="${basePath}" />
    <div class = "logo fl head2" style="margin-top:20px;">
        <!--
        <a href="${basePath}/index.html"><img src="${basePath}/index_two/images/logo.png" alt="" /></a>
		-->

    <#if (topmap.temp)??>
        <#if (topmap.temp.tempId!=10)>
            <a href="${topmap.systembase.bsetAddress}"><img src="${topmap.systembase.bsetLogo}" alt="" style="width:165px;height:40px;"/></a>
        </#if>
    </#if>

    </div>
    <div style="font-family: arial;">
        <div class="head_s mb20">
            <div class="fr w700 pt10">
                <div class="flow_progress1">
                    <ul>
                        <li class="step1">1.查看购物车</li>
                        <li class="step2">2.填写核对订单信息</li>
                        <li class="step3">3.提交订单成功</li>
                    </ul>
                </div>
            </div>

            <div class="cb"></div>
        </div><!-- /head_s -->
        <div class="flow_title">
            <b></b>
        	<span>
            <#if customerId??>
            <#else>
                建议您立即<a href="login.html">登录</a>，以确保顺利进行购物。
            </#if>
            </span>
        </div>
        <div class="cart_box">
            <div class="cart_head">
                <div class="thead t_check">
                    <input type="checkbox" class="check_all" checked="checked" onclick="oncheckAll(this);"/>
                    <label>全选</label>
                </div>
                <div class="thead t_goods">商品</div>
                <div class="thead t_price">销售价</div>
                <div class="thead t_promotion">小计</div>
                <div class="choose_area fl pr">
                    <div class="area_text ">${wareUtil.provinceName}<b></b></div>
                    <div class="locate_cont">
                        <ul class="locate_tabs clearfix">
                            <li class="cur show_province"><a href="javascript:;"><span class="province_text">${wareUtil.provinceName}</span><b></b></a></li>
                            <li class="show_city" ><a href="javascript:;" ><span class="city_text">${wareUtil.cityName}</span><b></b></a></li>
                            <li class="show_distinct"><a href="javascript:;" ><span class="distinct_text">${wareUtil.distinctName}</span><b></b></a></li>
                        </ul><!--/locate_tabs-->
                        <div class="locate_wp">
                            <ul style="display: block;" class="locate_list clearfix province_list show">
                            </ul><!--/locate_list-->
                            <ul class="locate_list clearfix city_list">
                            </ul><!--/locate_list-->
                            <ul class="locate_list clearfix distinct_list">
                            </ul><!--/locate_list-->
                        </div><!--/locate_wp-->
                        <a class="close_area" href="javascript:;"></a>
                    </div><!--/locate_cont-->
                </div><!--/choose_area-->
                <div class="thead t_count">数量</div>
                <div class="thead t_action">操作</div>
            </div><!-- /cart_head -->
            <form name="subForm" id="subForm" action="${basePath}/order/newsuborder.html" method="post">
                <input name="presentScopeIds" id="presentScopeIds" value="" type="hidden"/>
            <#assign sumPrice=0>
            <#assign fxPrice=0>
            <#assign sumcount=0>

            <#if cartMap??>
                <#if cartMap.thirds??&&cartMap.thirds?size!=0>
                    <!--根据商家进行分组-->
                    <#list cartMap.thirds as third>
                        <div>
                            <div class="activity" style="background:none;border-bottom:2px solid #9E9E9E;line-height:30px;">
                                <div class="fl w500 ml15">
                                    <strong>${third.thirdName!''}</strong>
                                </div>
                            </div><!-- /activity -->

                            <#if cartMap.marketinglist??&&cartMap.marketinglist?size!=0>
                                <#list cartMap.marketinglist as market>
                                    <#assign zjPrice=0>
                                    <#if market.businessId??&&market.businessId==third.thirdId>
                                        <div attr-codexType="${market.codexType}" class="marketgroup"
                                            <#assign haveGoods="0">
                                            <#if cartMap.shoplist??&&cartMap.shoplist?size!=0>
                                                <#list cartMap.shoplist as cars>
                                                    <#if cars.marketingActivityId??&&cars.marketingActivityId!=0&&cars.marketingActivityId==market.marketingId>
                                                        <#assign haveGoods="1">
                                                    </#if>
                                                </#list>
                                            </#if>
                                            <#if haveGoods=="0">
                                             style="display:none;"
                                            </#if>
                                                >
                                            <!--直降-->
                                            <#if market.codexType=='1'>
                                                <div class="activity">
                                                    <div class="fl w500 ml15"   style="height:31px; line-height:31px;overflow:hidden;">
                                                    ${market.marketingName}  直降${market.priceOffMarketing.offValue}元;
                                                        <input type="hidden" value="${market.priceOffMarketing.offValue}" id="zhijiang_offValue"/>
                                                    </div>
                                                    <div class="fl">
                                                        <!--<strong>￥5196.00</strong>-->
                                                    </div>
                                                </div><!-- /activity -->
                                            </#if>

                                            <!--满减-->
                                            <#if market.codexType=='5'>
                                                <div class="activity">
                                                    <div class="fl w500 ml15"   style="height:31px; line-height:31px;overflow:hidden;">
                                                    ${market.marketingName}
                                                        <#list market.fullbuyReduceMarketings as fr>
                                                            满 ${fr.fullPrice}减${fr.reducePrice}元 &nbsp;
                                                            <input type="hidden" value="${fr.fullPrice},${fr.reducePrice}" class="manjian_reducePrice"/>
                                                        </#list>
                                                    </div>
                                                    <div class="fl">
                                                        <!--<strong>￥5196.00</strong>-->
                                                    </div>
                                                </div><!-- /activity -->
                                            </#if>
                                            <!--满金额赠-->
                                            <#if market.codexType=='6'>
                                                <div class="activity">
                                                    <div class="fl w500 ml15"   style="height:31px; line-height:31px;overflow:hidden;">
                                                    ${market.marketingName}&nbsp;活动商品
                                                        <#list market.fullbuyPresentMarketings as fr>
                                                            <#if (fr.presentType?number == 0)>
                                                                满${fr.fullPrice}元，
                                                            </#if>
                                                            <#if (fr.presentType?number == 1)>
                                                                满${fr.fullPrice?string("0")}件，
                                                            </#if>
                                                        </#list>可获得赠品
                                                    </div>
                                                    <div class="fl">
                                                        <!--<strong>￥5196.00</strong>-->
                                                    </div>
                                                </div><!-- /activity -->
                                            </#if>
                                            <!--满折-->
                                            <#if market.codexType=='8'>
                                                <div class="activity">
                                                    <div class="fl w500 ml15"   style="height:31px; line-height:31px;overflow:hidden;">
                                                    ${market.marketingName}
                                                        <#list market.fullbuyDiscountMarketings as mz>
                                                            满 ${mz.fullPrice}打 ${mz.fullbuyDiscount*10}折 &nbsp;
                                                            <input type="hidden" value="${mz.fullPrice},${mz.fullbuyDiscount}" class="manzhe_fullbuyDiscount"/>
                                                        </#list>
                                                    </div>
                                                    <div class="fl">
                                                        <!--<strong>￥5196.00</strong>-->
                                                    </div>
                                                </div><!-- /activity -->
                                            </#if>


                                        <#--团购促销-->
                                        <#--<#if market.codexType=='10'>-->
                                        <#--<div class="activity">-->
                                        <#--<div class="fl w500 ml15">-->
                                        <#--<input type="hidden" value="${market.groupon.grouponDiscount}" class="bbb"/>-->
                                        <#--${market.marketingName}-->
                                        <#--${market.groupon.grouponDiscount}折团购-->
                                        <#--</div>-->
                                        <#--<div class="fl">-->
                                        <#--</div>-->
                                        <#--</div><!-- /activity &ndash;&gt;-->
                                        <#--</#if>-->
                                        <#--抢购促销促销-->
                                            <#if market.codexType=='11'>
                                                <div class="activity">
                                                    <div class="fl w500 ml15"   style="height:31px; line-height:31px;overflow:hidden;">
                                                    ${market.marketingName}
                                                    ${(market.rushs[0].rushDiscount*10)?string('0.#')}折抢购
                                                    </div>
                                                    <div class="fl">
                                                    </div>
                                                </div><!-- /activity -->
                                            </#if>


                                            <#assign marPrice=0>
                                            <#if cartMap.shoplist??&&cartMap.shoplist?size!=0>
                                                <#list cartMap.shoplist as cars>
                                                    <#if cars.marketingActivityId??&&cars.marketingActivityId!=0&&cars.marketingActivityId==market.marketingId>

                                                        <#setting number_format="0">  <div class="cart_goods" id="cart_goods_${cars.shoppingCartId}">
                                                        <div class="cart_item">
                                                            <div class="cell g_check">
                                                                <input type="checkbox" class="g_ckeck mjchecked"  attr_codexType="${market.codexType}" attr_marketingId="${market.marketingId}" name="box" checked="checked" value="${cars.shoppingCartId}" onclick="checkOne(this);"/>
                                                            </div>
                                                            <div class="cell g_goods">
                                                                <div class="img">
                                                                    <a href="${basePath}/item/${cars.goodsDetailBean.productVo.goodsInfoId}.html" target="_blank" title="${basePath}/item/${cars.goodsDetailBean.productVo.goodsInfoId}.html"><img style="width:50px;height:50px;" title="${cars.goodsDetailBean.productVo.productName}" alt="${cars.goodsDetailBean.productVo.productName}" src="<#if cars.goodsDetailBean.productVo.goodsInfoImgId??>${cars.goodsDetailBean.productVo.goodsInfoImgId}</#if>" /></a>
                                                                </div>
                                                                <div class="name">
                                                                    <a href="${basePath}/item/${cars.goodsDetailBean.productVo.goodsInfoId}.html" target="_blank" title="${basePath}/item/${cars.goodsDetailBean.productVo.goodsInfoId}.html">${cars.goodsDetailBean.productVo.productName}</a>
                                                                </div>
                                                                <div class="cb"></div>
                                                                <#if market.codexType == '6'>
                                                                    <#list market.fullbuyPresentMarketings as presentMarks>
                                                                        <#assign idone="${presentMarks.marketingId?number}"/>
                                                                        <#list cartMap.marketingIds as marketingIds>
                                                                            <#assign idtwo="${marketingIds?number}"/>
                                                                            <#if idone == idtwo>
                                                                                <#assign index ="${marketingIds_index}"/>
                                                                                <#assign fullprice="${presentMarks.fullPrice?number}"/>
                                                                                <#list cartMap.fullGiftAllPrices as fullGiftAllPrices>
                                                                                    <#if (fullGiftAllPrices_index?number == index?number)>
                                                                                        <#--<#if fullGiftAllPrices?number gte fullprice?number>-->
                                                                                            <div class="gift mt10" style="display: none">
                                                                                                <input type="hidden" class="presentScopesIds" value=""/>
                                                                                                <input type="hidden" class="fullGiftAllPrices" value="${fullGiftAllPrices}"/>
                                                                                                <input type="hidden" class="grades" value="${fullprice}"/>
                                                                                                <input type="hidden" class="presentType" value="${presentMarks.presentType}"/>
                                                                                                <input type="hidden" class="cartId" value=""/>
                                                                                                <#list presentMarks.fullbuyPresentScopes as presentScopes>
                                                                                                    <#if (presentMarks.presentMode?number == 0)>
                                                                                                        <p class="light">
                                                                                                            <input type="hidden" class="proStock" value="${presentScopes.goodsProduct.goodsInfoStock}"/>
                                                                                                            <input type="hidden" class="proName" value="${presentScopes.goodsProduct.goodsInfoName}"/>
                                                                                                            <input type="hidden" class="proImg" value="${presentScopes.goodsProduct.goodsInfoImgId}"/>
                                                                                                            <input type="hidden" class="presentScoId" value="${presentScopes.presentScopeId}"/>
                                                                                                            <input type="hidden" class="proNum" value="${presentScopes.scopeNum}"/>
                                                                                                            <span>[赠品]</span>
                                                                                                            <a href="${basePath}/item/${presentScopes.scopeId}.html" target="_blank">
                                                                                                            ${presentScopes.goodsProduct.goodsInfoName}
                                                                                                            </a>
                                                                                                            <span>X${presentScopes.scopeNum}</span>
                                                                                                        <div class="cb"></div>
                                                                                                        </p>
                                                                                                    </#if>
                                                                                                    <#if (presentMarks.presentMode?number == 1)>
                                                                                                        <#if presentScopes_index == 0>
                                                                                                            <p class="light">
                                                                                                                <input type="hidden" class="proStock" value="${presentScopes.goodsProduct.goodsInfoStock}"/>
                                                                                                                <input type="hidden" class="proName" value="${presentScopes.goodsProduct.goodsInfoName}"/>
                                                                                                                <input type="hidden" class="proImg" value="${presentScopes.goodsProduct.goodsInfoImgId}"/>
                                                                                                                <input type="hidden" class="presentScoId" value="${presentScopes.presentScopeId}"/>
                                                                                                                <input type="hidden" class="proNum" value="${presentScopes.scopeNum}"/>
                                                                                                                <span>[赠品]</span>
                                                                                                                <a href="${basePath}/item/${presentScopes.scopeId}.html" target="_blank">
                                                                                                                ${presentScopes.goodsProduct.goodsInfoName}
                                                                                                                </a>
                                                                                                                <span>X${presentScopes.scopeNum}</span>
                                                                                                            <div class="cb"></div>
                                                                                                            </p>
                                                                                                        <#else>
                                                                                                            <p class="light" style="display: none">
                                                                                                                <input type="hidden" class="proStock" value="${presentScopes.goodsProduct.goodsInfoStock}"/>
                                                                                                                <input type="hidden" class="proName" value="${presentScopes.goodsProduct.goodsInfoName}"/>
                                                                                                                <input type="hidden" class="proImg" value="${presentScopes.goodsProduct.goodsInfoImgId}"/>
                                                                                                                <input type="hidden" class="presentScoId" value="${presentScopes.presentScopeId}"/>
                                                                                                                <input type="hidden" class="proNum" value="${presentScopes.scopeNum}"/>
                                                                                                                <span>[赠品]</span>
                                                                                                                <a href="${basePath}/item/${presentScopes.scopeId}.html" target="_blank">
                                                                                                                ${presentScopes.goodsProduct.goodsInfoName}
                                                                                                                </a>
                                                                                                                <span>X${presentScopes.scopeNum}</span>
                                                                                                            <div class="cb"></div>
                                                                                                            </p>
                                                                                                        </#if>
                                                                                                    </#if>

                                                                                                </#list>
                                                                                                <div class="exchange" attr_presentMode="${presentMarks.presentMode}">
                                                                                                    <p style="color:#3399FF; cursor: pointer;">
                                                                                                        修改赠品
                                                                                                    </p>
                                                                                                </div>
                                                                                            </div>
                                                                                        <#--</#if>-->
                                                                                    </#if>
                                                                                </#list>
                                                                            </#if>
                                                                        </#list>
                                                                    </#list>
                                                                </#if>
                                                                <style>
                                                                    .gift{position:relative;}
                                                                    .gift p.light{margin-bottom: 5px;}
                                                                    .gift p.light:after{content:'';display:block;clear:both;}
                                                                    .gift p.light a{float:left;display:block;width:300px;overflow:hidden;text-overflow:ellipsis;white-space: nowrap;margin:0 10px;}
                                                                    .gift p.light span{float:left;color:#999;}
                                                                    .gift .exchange{position:absolute;right:0;bottom:0;}
                                                                </style>
                                                            </div>

                                                            <div class="cell g_price">

                                                                <#setting number_format="0.00">
                                                            <#--后台已经处理过的实际金额-->
                                                                <#assign price=cars.goodsDetailBean.productVo.goodsInfoPreferPrice>
                                                                ￥${price}

                                                            </div>

                                                            <div class="cell g_promotion">

                                                                <#assign activeFlag="0"/>
                                                                <#assign oneprice="0"/>
                                                                <!--判断参与的促销-->
                                                                <#if cars.marketingActivityId??&&cars.marketingActivityId!=0>

                                                                    <#if cars.marketingList??&&cars.marketingList?size!=0>
                                                                        <!--循环促销-->
                                                                        <#list cars.marketingList as market>

                                                                            <!--判断是否是相同促销，并且是折扣促销-->
                                                                            <!--直降-->
                                                                            <#if market.codexType=='1'&&cars.marketingActivityId==market.marketingId>
                                                                                <#assign oneprice="${price?number}"/>
                                                                                <#assign manPrice="${price?number*cars.goodsNum}"/>
                                                                                <#assign marPrice="${marPrice?number+manPrice?number}">
                                                                                <#assign zjPrice="${zjPrice?number+market.priceOffMarketing.offValue?number*cars.goodsNum}">
                                                                                <#assign smprice="${manPrice}"/>
                                                                            </#if>
                                                                        <#--团购促销-->
                                                                            <#if market.codexType=='10'&&cars.marketingActivityId==market.marketingId>
                                                                                <#assign oneprice="${price?number}"/>
                                                                                <#assign manPrice="${price?number*cars.goodsNum}"/>
                                                                                <#assign marPrice="${marPrice?number+manPrice?number}">
                                                                            <#--优惠的金额加上团购优惠的金额-->
                                                                            <#--<#assign zjPrice="${zjPrice?number+(1-market.groupon.grouponDiscount?number)*manPrice?number}">-->
                                                                                <#assign smprice="${manPrice}"/>
                                                                            </#if>

                                                                        <#--抢购促销-->
                                                                            <#if market.codexType=='11'&&cars.marketingActivityId==market.marketingId>
                                                                                <#assign oneprice="${price?number}"/>
                                                                                <#assign manPrice="${price?number*cars.goodsNum}"/>
                                                                                <#assign marPrice="${marPrice?number+manPrice?number}">
                                                                            <#--优惠的金额加上抢购的金额-->
                                                                                <#assign zjPrice="${zjPrice?number+(1-market.rushs[0].rushDiscount?number)*manPrice?number}">
                                                                                <#assign smprice="${manPrice}"/>
                                                                            </#if>

                                                                            <!--满减-->
                                                                            <#if market.codexType=='5'&&cars.marketingActivityId==market.marketingId>
                                                                                <#assign oneprice="${price?number}"/>
                                                                                <#assign manPrice="${price?number*cars.goodsNum}"/>
                                                                                <#assign marPrice="${marPrice?number+manPrice?number}">
                                                                                <#assign smprice="${manPrice}"/>

                                                                            </#if>
                                                                            <!--满金额赠减-->
                                                                            <#if market.codexType=='6'&&cars.marketingActivityId==market.marketingId>
                                                                                <#assign oneprice="${price?number}"/>
                                                                                <#assign manPrice="${price?number*cars.goodsNum}"/>
                                                                                <#assign marPrice="${marPrice?number+manPrice?number}">
                                                                                <#assign smprice="${manPrice}"/>
                                                                            </#if>
                                                                            <!--满折-->
                                                                            <#if market.codexType=='8'&&cars.marketingActivityId==market.marketingId>
                                                                                <#assign oneprice="${price?number}"/>
                                                                                <#assign manPrice="${price?number*cars.goodsNum}"/>
                                                                                <#assign marPrice="${marPrice?number+manPrice?number}">
                                                                                <#assign smprice="${manPrice}"/>

                                                                            </#if>

                                                                        </#list>

                                                                    </#if>
                                                                </#if>
                                                                <input type="hidden" value="${oneprice}" class="oneprice"/>

                                                                <input type="hidden" value="${smprice!''}" class="smprice"/>
                                                                <#setting number_format="0.00">   ￥<span class="pv_smprice">  ${smprice!''}  </span>
                                                                <#setting number_format="0">

                                                            </div>
                                                            <div class="cell g_stock">

                                                                <#if cars.goodsDetailBean.productVo.goodsInfoDelflag='1' || cars.goodsDetailBean.productVo.goodsInfoAdded!='1' >
                                                                    <span class="light" style="color:red;">已下架</span><input type="hidden" class="noProduct" value="0"/>
                                                                <#else>
                                                                    <#if (cars.goodsDetailBean.productVo.goodsInfoStock>0)>
                                                                        <span class="light">有货</span><input type="hidden" class="noProduct" value="1"/>
                                                                    <#else>
                                                                        <span class="light" style="color:red;">无货</span><input type="hidden" class="noProduct" value="0"/>
                                                                    </#if>
                                                                </#if>

                                                            </div>

                                                            <div class="cell g_count">

                                                                <div class="count">
                                                                    <#setting number_format="0.00">
                                                                    <#if market.codexType=='1'&&cars.marketingActivityId==market.marketingId>
                                                                        <input type="hidden" value="${market.priceOffMarketing.offValue}" class="marketPrice"/>
                                                                    </#if>
                                                                <#--团购-->
                                                                    <#if market.codexType=='10'&&cars.marketingActivityId==market.marketingId>
                                                                        <input type="hidden" value="${(1-market.groupon.grouponDiscount?number)*price?number}" class="groupPrice"/>
                                                                    </#if>
                                                                <#--是否进行抢购-->
                                                                    <#assign isrushing="0">
                                                                <#--抢购-->
                                                                    <#if market.codexType=='11'&&cars.marketingActivityId==market.marketingId>
                                                                        <input type="hidden" value="${(1-market.rushs[0].rushDiscount?number)*price?number}" class="rushPrice"/>
                                                                        <#assign isrushing="1">
                                                                    </#if>

                                                                    <#setting number_format="0">
                                                                <#--允许购买数量-->
                                                                    <#assign rushflag="0">
                                                                <#--购物车传过来的货品数量-->
                                                                    <#assign rushnum="0">

                                                                    <#if market.codexType=='11'&&cars.marketingActivityId==market.marketingId>
                                                                        <#if market.customerbuynum ?? && cars.goodsNum?number &gt;market.customerbuynum!1>
                                                                            <#assign rushnum="${market.customerbuynum!1}">

                                                                        <#else >
                                                                            <#assign rushnum="${cars.goodsNum}">
                                                                        </#if>

                                                                    <#--抢购限制数量-->
                                                                        <#if market.customerbuynum ?? && cars.goodsDetailBean.productVo.goodsInfoStock?number &gt;market.customerbuynum >
                                                                            <#assign rushflag="${market.customerbuynum!1}">
                                                                        <#else>
                                                                            <#assign rushflag="${cars.goodsDetailBean.productVo.goodsInfoStock}">
                                                                        </#if>
                                                                    <#else>
                                                                        <#assign rushflag="${cars.goodsDetailBean.productVo.goodsInfoStock}">
                                                                        <#assign rushnum="${cars.goodsNum}">
                                                                    </#if>
                                                                    <a href="javascript:void(0);" class="decrement" onclick="mit(this,${cars.shoppingCartId});">-</a>
                                                                    <input type="text" class="text w30"  oldgoodsnum="${cars.goodsNum}" value="${rushnum}" attr-maxnum="${rushflag}"
                                                                           onblur="opblur(this,${cars.shoppingCartId});"/>
                                                                    <#assign sumcount="${sumcount?number+cars.goodsNum?number}">
                                                                    <a href="javascript:void(0);" class="increment" onclick="add(this,${cars.shoppingCartId});">+</a>
                                                                </div>

                                                                <div class="red">

                                                                    <#if market.codexType=='11'&&cars.marketingActivityId==market.marketingId>
                                                                        <#if market.customerbuynum ?? && market.customerbuynum &lt;=0>
                                                                            达到限制购买件数
                                                                            <input type="hidden" value="1" class="rushlimit" attr_id="${cars.shoppingCartId}"/>
                                                                        <#else>
                                                                            <#if  cars.goodsDetailBean.productVo.goodsInfoStock<=20 >

                                                                                仅剩${cars.goodsDetailBean.productVo.goodsInfoStock}件！
                                                                            </#if>
                                                                        </#if>
                                                                    <#else>
                                                                        <#if  cars.goodsDetailBean.productVo.goodsInfoStock<=20 >

                                                                            仅剩${cars.goodsDetailBean.productVo.goodsInfoStock}件！
                                                                        </#if>
                                                                    </#if>

                                                                </div>

                                                            </div>

                                                            <div class="cell g_action">
                                                                <div>
                                                                    <a class="g_delete" href="javascript:void(0);" onclick="del(${cars.shoppingCartId},${cars.goodsInfoId})">删除</a>
                                                                </div>

                                                                <div class="">
                                                                    <a class="change_promotion" href="javascript:void(0);"
                                                                       <#if isrushing=='1'>style="display: none" </#if>
                                                                            >修改优惠</a>
                                                                    <div class="dialog promotion_dialog">
                                                                        <div class="dialog-outer">
											                                    <span class="dialog-bg dialog-bg-n">
											                                    </span>
											                                    <span class="dialog-bg dialog-bg-ne">
											                                    </span>
											                                    <span class="dialog-bg dialog-bg-e">
											                                    </span>
											                                    <span class="dialog-bg dialog-bg-se">
											                                    </span>
											                                    <span class="dialog-bg dialog-bg-s">
											                                    </span>
											                                    <span class="dialog-bg dialog-bg-sw">
											                                    </span>
											                                    <span class="dialog-bg dialog-bg-w">
											                                    </span>
											                                    <span class="dialog-bg dialog-bg-nw">
											                                    </span>
                                                                            <div class="dialog-inner">
                                                                                <div class="dialog-toolbar clearfix">
                                                                                    <a class="dialog-close" href="javascript:void(0);" title="关闭" onclick="$('.promotion_dialog').hide();">
                                                                                        关闭
                                                                                    </a>
                                                                                    <h3 class="dialog-title">
                                                                                        请选择一项优惠
                                                                                    </h3>
                                                                                </div>
                                                                                <div class="dialog-content clearfix">
                                                                                    <div class="p10 tc w300">
                                                                                        <label>活动优惠：</label>
                                                                                        <select class="chooseMarket" name="chooseMarket">
                                                                                            <option value="0">不使用活动优惠</option>
                                                                                            <#setting number_format="0">
                                                                                            <#if cars.marketingList??&&cars.marketingList?size!=0>
                                                                                                <!--循环促销-->
                                                                                                <#list cars.marketingList as market>
                                                                                                    <#if market.codexType!='15'&&market.codexType!='12'&& market.codexType!='10'>
                                                                                                        <option <#if cars.marketingActivityId ==market.marketingId > selected="selected"</#if> value="${market.marketingId}"><#if (market.marketingName)?length gt 20>${market.marketingName?substring(0,20)}...<#else>${market.marketingName}</#if></option>
                                                                                                    </#if>
                                                                                                </#list>
                                                                                            </#if>

                                                                                        </select>
                                                                                    </div>
                                                                                    <div class="tc p15">
                                                                                        <a class="red_btn" href="javascript:void(0);" onclick="changemarketing(this,${cars.shoppingCartId},${cars.goodsGroupId!0});">确定</a>
                                                                                        <a class="light_btn2" href="javascript:void(0);" onclick="$('.promotion_dialog').hide();">取消</a>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div><!-- /dialog -->
                                                                </div>
                                                            </div>
                                                            <div class="cb"></div>
                                                        </div>
                                                    </div><!-- /cart_goods -->
                                                        <#setting number_format="0.00">
                                                    </#if>

                                                </#list>

                                            </#if>

                                            <input type="hidden" class="xiaoji" value="${marPrice}"/>

                                            <!--判断满减-->

                                            <#assign yhprice=zjPrice>
                                            <!--满减-->
                                            <#if market.codexType=='5'>

                                                <#list market.fullbuyReduceMarketings as fr>
                                                    <#if (marPrice?number>=fr.fullPrice?number)>
                                                        <#assign yhprice="${fr.reducePrice}">
                                                    </#if>
                                                    <!-- 满 ${fr.fullPrice}减${fr.reducePrice}元 &nbsp;-->
                                                </#list>
                                            </#if>
                                            <!--满折-->
                                            <#if market.codexType=='8'>
                                                <#list market.fullbuyDiscountMarketings as mz>
                                                    <#if (marPrice?number>=mz.fullPrice?number)>
                                                        <#assign yhprice="${marPrice?number*(1-mz.fullbuyDiscount?number)}">
                                                    </#if>
                                                </#list>
                                            </#if>
                                            <input type="hidden" class="youhui" value="${yhprice}"/>
                                            <!--小计累加-->
                                            <#assign sumPrice="${sumPrice?number+marPrice?number}">
                                            <#assign fxPrice="${fxPrice?number+yhprice?number}">
                                        </div>

                                    </#if>
                                </#list>
                            </#if>

                            <!--加载没参加促销的-->

                            <#if cartMap.shoplist??&&cartMap.shoplist?size!=0>

                                <#list cartMap.shoplist as cars>
                                    <!--判断是否是套装-->
                                    <#if cars.fitId??&&cars.thirdId==third.thirdId>

                                        <#setting number_format="0">
                                        <div attr-codexType="-1" class="marketgroup carts_${cars.shoppingCartId}" attr-group="1">
                                            <div class="cart_goods">
                                                <div class="cart_item">
                                                    <div class="cell g_check" style="padding-top:2px;">
                                                        <input type="checkbox" class="g_ckeck mjchecked" name="box" checked="checked" value="${cars.shoppingCartId}"  onclick="checkOne(this);"/>
                                                    </div>
                                                    <div class="cell g_goods">
                                                        <strong>【套装】${cars.goodsGroupVo.groupName} 每套优惠${cars.goodsGroupVo.groupPreferamount}元<input type="hidden" value="${cars.goodsGroupVo.groupPreferamount}" class="groupPreferamount"/></strong>
                                                    </div>
                                                    <div class="cell g_price">
                                                        &nbsp;
                                                    </div>
                                                    <div class="cell g_promotion">
                                                        &nbsp;
                                                    </div>
                                                    <div class="cell g_stock">
							                        <span class="light">
                                                        <#if (cars.goodsGroupVo.stock>0)>
                                                            <span class="light">有货</span><input type="hidden" class="noProduct" value="1"/>
                                                        <#else>
                                                            <span class="light red">无货</span><input type="hidden" class="noProduct" value="0"/>
                                                        </#if>
                                                    </span>
                                                    </div>
                                                    <div class="cell g_count">
                                                        <div class="count">
                                                            <a href="javascript:void(0);" class="decrement" onclick="mit(this,${cars.shoppingCartId});">-</a>
                                                            <input type="text" class="text w30" value="${cars.goodsNum}" attr-maxnum="${cars.goodsGroupVo.stock}" onblur="opblur(this,${cars.shoppingCartId});"/>
                                                            <#assign sumcount="${sumcount?number+cars.goodsNum?number}">
                                                            <a href="javascript:void(0);" class="increment" onclick="add(this,${cars.shoppingCartId});">+</a>
                                                        </div>
                                                        <div class="red">
                                                            <#if  cars.goodsGroupVo.stock<=20 >

                                                                仅剩${cars.goodsGroupVo.stock}套！
                                                            </#if>
                                                        </div>
                                                    </div>
                                                    <div class="cell g_action">
                                                        <div>
                                                            <a class="g_delete" href="javascript:void(0);" onclick="del(${cars.shoppingCartId},${cars.goodsInfoId})">删除</a>
                                                        </div>
                                                    </div>
                                                    <div class="cb"></div>
                                                </div>
                                                <#assign tzsumprice="0">
                                                <#if cars.goodsGroupVo.productList??&&cars.goodsGroupVo.productList?size!=0>
                                                    <#list cars.goodsGroupVo.productList as pro>
                                                        <#if pro.productDetail??>
                                                            <div class="cart_item mb10 cart_item_goods">
                                                                <div class="cell g_check"></div>
                                                                <div class="cell g_goods">
                                                                    <div class="img">
                                                                        <a href="${basePath}/item/${pro.productDetail.goodsInfoId}.html" target="_blank" title="${basePath}/item/${pro.productDetail.goodsInfoId}.html"><img style="width:50px;height:50px;" title="${pro.productDetail.goodsInfoName}" alt="${pro.productDetail.goodsInfoName}" src="<#if pro.productDetail.goodsInfoImgId??>${pro.productDetail.goodsInfoImgId}</#if>" /></a>
                                                                    </div>
                                                                    <div class="name">
                                                                        <a href="${basePath}/item/${pro.productDetail.goodsInfoId}.html" target="_blank" title="${basePath}/item/${pro.productDetail.goodsInfoId}.html">${pro.productDetail.goodsInfoName}</a>
                                                                    </div>
                                                                    <div class="cb"></div>
                                                                </div>
                                                                <div class="cell g_price">
                                                                    <#setting number_format="0.00">  	   ￥  ${pro.productDetail.goodsInfoPreferPrice}
                                                                </div>
                                                                <div class="cell g_promotion">
                                                                    <#if pro.productNum??>
                                                                        <#assign  productNum="${pro.productNum?string('0')}">
                                                                    </#if>
                                                                    <#if !pro.productNum??>
                                                                        <#assign  productNum="1">
                                                                    </#if>
                                                                    ￥  <span class="pv_smprice"> ${pro.productDetail.goodsInfoPreferPrice?number*productNum?number*cars.goodsNum?number} </span>
                                                                    <#assign tzsumprice="${(pro.productDetail.goodsInfoPreferPrice?number*productNum?number*cars.goodsNum?number)+tzsumprice?number}">
                                                                    <input type="hidden" value="${pro.productDetail.goodsInfoPreferPrice}" class="oneprice">
                                                                    <input type="hidden" value="${pro.productDetail.goodsInfoPreferPrice?number*productNum?number*cars.goodsNum?number}" class="smprice">
                                                                    <input type="hidden" value="${productNum}" class="psum">
                                                                </div>
                                                                <#setting number_format="0">
                                                                <div class="cell g_stock">
                                                                    <span class="light">&nbsp;</span>
                                                                </div>
                                                                <div class="cell g_count">
                                                                    <div class="count">
                                                                    ${productNum}件/套 X<span class="buyNum">${cars.goodsNum}</span>
                                                                    </div>

                                                                </div>
                                                                <div class="cell g_action">
                                                                    <div>

                                                                    </div>
                                                                </div>
                                                                <div class="cb"></div>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                                <#setting number_format="0.00">
                                                <input type="hidden" class="xiaoji" value="${tzsumprice}"/> 	<input type="hidden" class="youhui" value="${cars.goodsGroupVo.groupPreferamount?number*cars.goodsNum}"/>

                                                <#assign sumPrice="${tzsumprice?number+sumPrice?number}">
                                                <#assign fxPrice="${fxPrice?number+(cars.goodsGroupVo.groupPreferamount?number*cars.goodsNum?number)}">
                                            </div><!-- /cart_goods -->



                                        </div>


                                        <!--不是套装-->
                                    <#else>
                                        <#if cars.marketingActivityId??&&cars.marketingActivityId!=0>

                                        <#elseif cars.goodsDetailBean??&&cars.goodsDetailBean.productVo??&&cars.goodsDetailBean.productVo.thirdId==third.thirdId>

                                            <div attr-codexType="0" class="marketgroup carts_${cars.shoppingCartId}" attr-group="0">

                                                <#setting number_format="0">
                                                <div class="cart_goods" id="cart_goods_${cars.shoppingCartId}">
                                                    <div class="cart_item">
                                                        <div class="cell g_check">
                                                            <input type="checkbox" class="g_ckeck mjchecked" name="box" checked="checked" value="${cars.shoppingCartId}"  onclick="checkOne(this);"/>
                                                        </div>
                                                        <div class="cell g_goods">
                                                            <div class="img">
                                                                <a href="${basePath}/item/${cars.goodsDetailBean.productVo.goodsInfoId}.html" target="_blank" title="${basePath}/item/${cars.goodsDetailBean.productVo.goodsInfoId}.html"><img style="width:50px;height:50px;" title="${cars.goodsDetailBean.productVo.productName}" alt="${cars.goodsDetailBean.productVo.productName}" src="<#if cars.goodsDetailBean.productVo.goodsInfoImgId??>${cars.goodsDetailBean.productVo.goodsInfoImgId}</#if>" /></a>
                                                            </div>
                                                            <div class="name">
                                                                <a href="${basePath}/item/${cars.goodsDetailBean.productVo.goodsInfoId}.html" target="_blank" title="${basePath}/item/${cars.goodsDetailBean.productVo.goodsInfoId}.html">${cars.goodsDetailBean.productVo.productName}</a>
                                                            </div>
                                                            <div class="cb"></div>
                                                        </div>
                                                        <div class="cell g_price">
                                                            <#setting number_format="0.00">
                                                            <#--后台进行过金额处理-->
                                                            <#assign price=cars.goodsDetailBean.productVo.goodsInfoPreferPrice>

                                                            ￥ ${price}

                                                        </div>
                                                        <div class="cell g_promotion">
                                                            <#assign manPrice="${price?number*cars.goodsNum}"/>
                                                            <#assign smprice="${manPrice}"/>
                                                            ￥  <span class="pv_smprice"> ${smprice}  </span>
                                                            <input type="hidden" value="${price}" class="oneprice">
                                                            <input type="hidden" value="${smprice!''}" class="smprice"/>
                                                        </div>
                                                        <#setting number_format="0">
                                                        <div class="cell g_stock">
                                                            <#if cars.goodsDetailBean.productVo.goodsInfoDelflag='1' || cars.goodsDetailBean.productVo.goodsInfoAdded!='1' >
                                                                <span class="light" style="color:red;">已下架</span><input type="hidden" class="noProduct" value="0"/>
                                                            <#else>
                                                                <#if (cars.goodsDetailBean.productVo.goodsInfoStock>0)>
                                                                    <span class="light">有货</span><input type="hidden" class="noProduct" value="1"/>
                                                                <#else>
                                                                    <span class="light" style="color:red;">无货</span><input type="hidden" class="noProduct" value="0"/>
                                                                </#if>
                                                            </#if>
                                                        </div>
                                                        <div class="cell g_count">
                                                            <div class="count">
                                                                <a href="javascript:void(0);" class="decrement" onclick="mit(this,${cars.shoppingCartId});">-</a>
                                                                <input type="text" class="text w30" value="${cars.goodsNum}" attr-maxnum="${cars.goodsDetailBean.productVo.goodsInfoStock}" onblur="opblur(this,${cars.shoppingCartId});"/>
                                                                <#assign sumcount="${sumcount?number+cars.goodsNum?number}">
                                                                <a href="javascript:void(0);" class="increment" onclick="add(this,${cars.shoppingCartId});">+</a>

                                                            </div>
                                                            <div class="red">
                                                                <#if  cars.goodsDetailBean.productVo.goodsInfoStock<=20 >

                                                                    仅剩${cars.goodsDetailBean.productVo.goodsInfoStock}件！
                                                                </#if>
                                                            </div>
                                                        </div>

                                                        <div class="cell g_action">
                                                            <div>
                                                                <a class="g_delete" href="javascript:void(0);" onclick="del(${cars.shoppingCartId},${cars.goodsInfoId})">删除</a>
                                                            </div>

                                                            <div class="">
                                                                <a class="change_promotion" href="javascript:void(0);">修改优惠</a>
                                                                <div class="dialog promotion_dialog">
                                                                    <div class="dialog-outer">
								                                    <span class="dialog-bg dialog-bg-n">
								                                    </span>
								                                    <span class="dialog-bg dialog-bg-ne">
								                                    </span>
								                                    <span class="dialog-bg dialog-bg-e">
								                                    </span>
								                                    <span class="dialog-bg dialog-bg-se">
								                                    </span>
								                                    <span class="dialog-bg dialog-bg-s">
								                                    </span>
								                                    <span class="dialog-bg dialog-bg-sw">
								                                    </span>
								                                    <span class="dialog-bg dialog-bg-w">
								                                    </span>
								                                    <span class="dialog-bg dialog-bg-nw">
								                                    </span>
                                                                        <div class="dialog-inner">
                                                                            <div class="dialog-toolbar clearfix">
                                                                                <a class="dialog-close" href="javascript:void(0);" title="关闭" onclick="$('.promotion_dialog').hide();">
                                                                                    关闭
                                                                                </a>
                                                                                <h3 class="dialog-title">
                                                                                    请选择一项优惠
                                                                                </h3>
                                                                            </div>
                                                                            <div class="dialog-content clearfix">
                                                                                <div class="p10 tc w300">
                                                                                    <label>活动优惠：</label>
                                                                                    <select class="chooseMarket" name="chooseMarket">
                                                                                        <option value="0">不使用活动优惠</option>
                                                                                        <#if cars.marketingList??&&cars.marketingList?size!=0>
                                                                                            <!--循环促销-->
                                                                                            <#list cars.marketingList as market>
                                                                                                <#if market.codexType!='15'&&market.codexType!='12' && market.codexType!='10'>
                                                                                                    <option <#if cars.marketingActivityId??&&cars.marketingActivityId!=0&&cars.marketingActivityId ==market.marketingId > selected="selected"</#if> value="${market.marketingId}">${market.marketingName}</option>
                                                                                                </#if>
                                                                                            </#list>
                                                                                        </#if>

                                                                                    </select>
                                                                                </div>
                                                                                <div class="tc p15">
                                                                                    <a class="red_btn" href="javascript:void(0);" onclick="changemarketing(this,${cars.shoppingCartId},${cars.goodsGroupId!0});">确定</a>
                                                                                    <a class="light_btn2" href="javascript:void(0);" onclick="$('.promotion_dialog').hide();">取消</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div><!-- /dialog -->
                                                            </div>
                                                        </div>
                                                        <div class="cb"></div>
                                                    </div>
                                                </div><!-- /cart_goods -->
                                                <#setting number_format="0.00">
                                                <input type="hidden" class="xiaoji" value="${smprice}"/> 	<input type="hidden" class="youhui" value="0"/>
                                                <#assign sumPrice="${sumPrice?number+smprice?number}">
                                            </div>

                                        </#if>

                                    </#if>


                                </#list>
                            </#if>
                        </div>
                    </#list>
                <#else>
                    <p style="text-align:center;padding:20px 0;">购物车为空！！<a href="${indexUrl}" style="color:#c00"> 去逛逛</a></p>
                </#if>
            </#if>

            </form>

            <div class="cart_tools p10 lh180">
                <div class="fr w200">
                    <input type="hidden" id="zongji" value="${sumPrice!''}"/>
                    <p><span class="fr zongji">￥ ${sumPrice!''}</span>总计：</p>
                    <input type="hidden" id="fanxian" value="${fxPrice!''}"/>
                    <p><span class="fr fanxian">-￥${fxPrice!''}</span>返现：</p>
                </div>
                <div class="fr mr10">
                    <b class="red allcount">${sumcount}</b>件商品
                </div>

                <div class="fl">
                    <!--<a href="javascript:void(0);" class="delete">删除所选商品</a>-->
                    <!--<a href="javascript:void(0);" class="piece">凑单商品</a>-->
                </div>
                <div class="cb"></div>
            </div><!-- /cart_tools -->

            <div class="cart_total">
                <div class="fr f14 fb mr20 w300">
                    <span class="fr f20 red payPrice">￥   ${sumPrice?number-fxPrice?number}</span>
                    总计（不含运费）：
                </div>
            </div><!-- /cart_total -->

        </div><!-- /cart_box -->

        <div class="cart_btn mt10">
            <a class="check_btn fr" href="javascript:void(0);" onclick="onpay();">去结算</a>
            <a class="continue_shopping" href="${basePath}/index.html">继续购物</a>
        </div><!-- /cart_btn -->


    </div><!-- /container -->
<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">
    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>

    <div id="delete_dialog" class="dialog" style="position:fixed;">
        <div class="dialog-outer">
        <span class="dialog-bg dialog-bg-n">
        </span>
        <span class="dialog-bg dialog-bg-ne">
        </span>
        <span class="dialog-bg dialog-bg-e">
        </span>
        <span class="dialog-bg dialog-bg-se">
        </span>
        <span class="dialog-bg dialog-bg-s">
        </span>
        <span class="dialog-bg dialog-bg-sw">
        </span>
        <span class="dialog-bg dialog-bg-w">
        </span>
        <span class="dialog-bg dialog-bg-nw">
        </span>
            <div class="dialog-inner">
                <div class="dialog-toolbar clearfix">
                    <a class="dialog-close" href="javascript:void(0);" title="关闭" onclick="hideDia()">
                        关闭
                    </a>
                    <h3 class="dialog-title">
                        删除商品
                    </h3>
                </div>
                <div class="dialog-content clearfix">
                    <div class="p10 tc red w200" id="diaText">
                        确定从购物车中删除此商品？
                    </div>
                    <div class="tc p15">
                        <form method="post" action="delshoppingcatgoodsgroup.htm" id="delGroupFil">
                            <input type="hidden" name="shoppingCartId" id="shoppingCartId"/>
                            <input type="hidden" name="goodsInfoId" id="goodsInfoId"/>
                            <input type="hidden" name="fitId" id="fitId"/>
                        </form>
                        <a class="red_btn" href="javascript:void(0);" onclick="dodel();">确定</a>
                        <a class="light_btn2" href="javascript:void(0);" onclick="hideDia()">取消</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="dialog s_dia dia2" style="text-align: center;background: #f2f2f2">
    <div class="dia_tit clearfix">
        <h4 class="fl">提示</h4>
        <a class="dia_close fr" href="javascript:;" onclick="cls()"></a>
    </div><!--/dia_tit-->
    <div class="dia_cont">
        <div class="dia_intro no_tc pt30">
            <img class="vm mr10" id="f_img" alt="" src="${basePath}/images/mod_war.png" />
            <em id="con_00">修改成功！</em>
        </div>
        <div class="dia_ops mt50 tc" style="text-align: center;">
            <a class="go_pay" id="go_pay_00" href="javascript:cls();">确定</a>
        </div><!--/dia_ops-->
    </div><!--/dia_cont-->
</div><!--/dialog-->

<div class="dialog s_dia dia3" style="text-align: center;background: #f2f2f2">
    <div class="dia_tit clearfix">
        <h4 class="fl">提示</h4>
        <a class="dia_close fr" href="javascript:;" onclick="cls()"></a>
    </div><!--/dia_tit-->
    <div class="dia_cont" style="min-height: 150px;!important;">
        <div class="dia_intro no_tc pt30">
            <em>购物车中没有可结算的商品！</em>
        </div>
        <div class="dia_ops mt50 tc" style="text-align: center;margin-top: 30px;!important;">
            <a class="go_pay" id="go_pay_00" href="javascript:cls();">确定</a>
            <#--<a class="go_shopping" href="javascript:cls();">确定</a>-->
        </div><!--/dia_ops-->
    </div><!--/dia_cont-->
</div><!--/dialog-->

<#--参与抢购达到限制的购买数量时-->
<div class="dialog s_dia dia6" style="text-align: center;background: #f2f2f2">
    <div class="dia_tit clearfix">
        <h4 class="fl">提示</h4>
        <a class="dia_close fr" href="javascript:;" onclick="cls()"></a>
    </div><!--/dia_tit-->
    <div class="dia_cont">
        <div class="dia_intro no_tc pt30">
            <em>参与的抢购达到该账号限制的购买数量</em>
        </div>
        <div class="dia_ops mt50 tc" style="text-align: center;">
            <a class="go_pay" id="go_pay_00" href="javascript:cls();">确定</a>
            <#--<a class="go_shopping" href="javascript:cls();">取消</a>-->
        </div><!--/dia_ops-->
    </div><!--/dia_cont-->
</div><!--/dialog-->



<div class="dialog s_dia dia55" style="text-align: center;background: #f2f2f2">
    <div class="dia_tit clearfix">
        <h4 class="fl">提示</h4>
        <a class="dia_close fr" href="javascript:;" onclick="cls()"></a>
    </div><!--/dia_tit-->
    <div class="dia_cont">
        <div class="dia_intro no_tc pt30">
            <em>包含无货或下架商品，请重新选择!</em>
        </div>
        <div class="dia_ops mt50 tr"  style="text-align: center;">
            <a class="go_pay" id="go_pay_00" href="javascript:cls();">确定</a>
            <#--<a class="go_shopping" href="javascript:cls();">取消</a>-->
        </div><!--/dia_ops-->
    </div><!--/dia_cont-->
</div>

<div class="dialog s_dia dia9" style="text-align: center;background: #f2f2f2; width: 400px">
    <div class="dia_tit clearfix">
        <h4 class="fl">修改赠品</h4>
        <a class="dia_close fr" href="javascript:;" onclick="giftCls()"></a>
    </div><!--/dia_tit-->
    <div class="dia_cont">
        <div class="dia_intro no_tc pt30">
            <div class="box_gifts">

            </div>
        </div>
        <div class="dia_ops mt30 tr"  style="text-align: center;">
            <a class="go_pay" id="confirmGift">确定</a>
            <a class="go_shopping" href="javascript:giftCls();">取消</a>
        </div><!--/dia_ops-->
    </div><!--/dia_cont-->
</div><!--/dialog-->


<div class="dialog s_dia dia4"  style="text-align: center;background: #f2f2f2">
    <div class="dia_tit clearfix">
        <h4 class="fl">提示</h4>
        <a class="dia_close fr" href="javascript:;" onclick="cls()"></a>
    </div><!--/dia_tit-->
    <div class="dia_cont">
        <div class="dia_intro no_tc pt30" style="text-align: center;">
            <em>是否一键下单？</em>
        </div>
        <div class="dia_ops mt50 tr" style="text-align: center;">
            <a class="go_pay" id="go_pay_00" href="javascript:;" onclick="goForm()">确定</a>
            <a class="go_shopping" href="javascript:cls();">取消</a>
        </div><!--/dia_ops-->
    </div><!--/dia_cont-->
</div><!--/dialog-->
<form action ="updateprovince.htm" method="post" class="subDis">
    <!--存放地址信息-->
    <input type="hidden" value=<#if wareUtil.districtId??>${wareUtil.districtId}</#if> name="distinctId" class="ch_distinctId">
    <input type="hidden" value=<#if wareUtil.provinceName??>${wareUtil.provinceName}</#if> name="chProvince" class="ch_province">
    <input type="hidden" value=<#if wareUtil.cityName??>${wareUtil.cityName}</#if> name="chCity" class="ch_city">
    <input type="hidden" value=<#if wareUtil.distinctName??>${wareUtil.distinctName}</#if> name="chDistinct" class="ch_distinct">
    <input type="hidden" name="chAddress" class="ch_address">
</form>
<!--修改优惠-->
<form method="post" action="changeshoppingcartmarts.htm" class="change_shopping">
    <input type="hidden" name="checkList" class="check_list">
    <input type="hidden" name="allChoose" class="all_list">
    <input type="hidden" name="shoppingCartId" class="shopping_cart_id">
    <input type="hidden" name="marketingActivityId" class="marketing_activity_id">
    <input type="hidden" name="goodsGroupMarketingId" class="goods_group_marketing_id">
    <input type="hidden" name="marketingId" class="marketing_id">
</form>
<input type="hidden" value="<#if list??>${list}</#if>" id="listSize"/>
<input type="hidden" value="<#if customerId??>${customerId}</#if>" id="customerId"/>

<script type="text/javascript" src="${basePath}/js/jcarousellite_1.0.1.js"></script>
<script type="text/javascript" src="${basePath}/js/default.js"></script>
<script type="text/javascript" src="${basePath}/js/shoppingcart/shoppingcart.js"></script>
<script type="text/javascript" src="${basePath}/js/jsOperation.js"></script>
<script type="text/javascript" src="${basePath}/index_two/js/index.js"></script>

<script type="text/javascript">
    $(function(){
        $(".marketgroup").each(function(){
            if($(this).attr("attr-codextype") == "6"){
                $(this).find(".gift:last").parents(".cart_goods").siblings().find(".gift").each(function(){
                    $(this).remove();
                });
                $(this).find(".fullGiftAllPrices").each(function(){
                    if(parseFloat($(this).val()) >= parseFloat($(this).next(".grades").val())){
                        $(this).parent(".gift").show();
                    }
                })
            }
            $(this).find(".gift:visible:last").show();
            $(this).find(".gift:visible:last").siblings(".gift:visible").hide();
        });
        $(".presentType").each(function(){
            if($(this).val() != ""){
                $(this).parents(".marketgroup").attr("attr-pretype",$(this).val());
            }
        });
        fillData();
        if($("#listSize").val() != ""){
            var selectList = $("#listSize").val().split(",");
            for(var i=0;i<selectList.length;i++) {
                $(".mjchecked[value=" + selectList[i] + "]").each(function(){
                    $(this).removeAttr("checked");
                    $(this).click();
                    $(this).removeAttr("checked");
                })
            }

        }
        //不是促销的商品与促销商品之间分界线
        if($(".marketgroup[attr-group=0]").length>0){
            $(".marketgroup[attr-group=0]:first").find(".cart_goods").attr("style","border-top:1px solid #999999")
        }
        //重新计算价格
        lastsum();
        $(".mjchecked").each(function(){
            if($(this)[0].checked){
                //获取外部的DIV 是否是促销的商品
                var obj=this;
                var codexType = $(obj).parents(".marketgroup").attr("attr-codextype");
                if(codexType!='0'){
                    if(obj.checked){
                        if(codexType=='11'){//抢购
                            var youhui = 0 ;
                            var xiaoji = 0;
                            $(obj).parents(".marketgroup").find(".cart_goods").each(function(){

                                //件数
                                var jian = $(this).find(".count").find(".text").val();

                                //当限购的数量大于购物车数量时调用当前blur
                                if(parseInt($(this).find(".count").find(".text").attr("attr-maxnum"))<parseInt($(this).find(".count").find(".text").attr("oldgoodsnum")) ){
                                    $($(this).find(".count").find(".text")).blur();
                                }

                            })

                        }


                    }
                }

            }
        });



        $('.g_delete').click(function(){
            if($('#delete_dialog').is(':hidden')){
                $('#delete_dialog').show();
            }
            else{
                $('#delete_dialog').hide();
            }
        });

        $('.change_promotion').click(function(){
            var p_x = ($(window).width()-1200)/2+1200-350;
            var p_y = $(this).offset().top;
            $(this).next().show();
            $(this).next(".promotion_dialog").css({
                left: p_x,
                top: p_y + 20 + 'px'
            });
        });
    });

    function mit(obj,cartId){
        if($(obj).parents(".marketgroup").attr("attr-codextype")!="-1"){
            //获取 可用的最大库存和最小
            var minNum = 1;

            var nowNum = $(obj).next().val();

            var setNum = 0;
            if(nowNum>minNum){
                setNum=SubtrInt(nowNum,1);
            }else{
                setNum=nowNum;
            }

            $(obj).next().val(setNum);
            var onePrice = $(obj).parents(".cart_goods").find(".oneprice").val();
            var xiaoji = accMul(onePrice,setNum);
            $(obj).parents(".cart_goods").find(".smprice").val(xiaoji);
            $(obj).parents(".cart_goods").find(".pv_smprice").text(xiaoji);
            var num = $(obj).parents(".cart_goods").find(".text").val();
            var nowAll;
            //赠送类型0金额1件数
            var presentType = $(obj).parents(".marketgroup").attr("attr-pretype");
            if(presentType == 0){
                //原先所有满赠商品的总价（判断符合几级满赠）
                var preAll = $(obj).parents(".marketgroup").find(".gift:first").find(".fullGiftAllPrices").val();
                //现在剩下的满赠商品总价
                var nowAll = Subtr(preAll,onePrice);
                $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                    $(this).val(nowAll);
                })
            }else{
                //满数量赠时
                if(nowNum == 1){
                    nowAll = $(obj).parents(".marketgroup").find(".fullGiftAllPrices:first").val();
                }else{
                    nowAll = Subtr(parseFloat($(obj).parents(".marketgroup").find(".fullGiftAllPrices:first").val()),1);
                }
                $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                    $(this).val(nowAll);
                })
            }
            $(obj).parents(".marketgroup").find(".gift:visible").hide();
            isHasGift(obj,nowAll);
            changeNum(cartId,setNum);
            jisuan(obj);
        }else{
            //套装
            var nowNum = $(obj).next().val();
            var flag = 0;
            if(nowNum=='1'){
                flag ++;
            }

            if(flag == 0 ){
                var zongji = 0 ;
                $(obj).next().val(SubtrInt(nowNum,1));
                nowNum --;
                $(obj).parents(".marketgroup").find(".cart_item_goods").each(function(){
                    $(this).find(".buyNum").text(nowNum);
                    var onePrice = $(this).find(".oneprice").val();
                    var psum = $(this).find(".psum").val();
                    var xiaoji = accMul(onePrice,nowNum);
                    xiaoji = accMul(xiaoji,psum);
                    zongji = accAdd(xiaoji,zongji);
                    $(this).find(".smprice").val(xiaoji);
                    $(this).find(".pv_smprice").text(xiaoji);
                });
                if($(obj).parents(".cart_item").find(".mjchecked")[0].checked){
                    $(obj).parents(".marketgroup").find(".xiaoji").val(zongji);
                    var oneyh = $(obj).parents(".cart_item").find(".groupPreferamount").val();
                    $(obj).parents(".marketgroup").find(".youhui").val(accMul(oneyh,nowNum));
                }
                changeNum(cartId,nowNum);
                lastsum();
            }

        }

    }


    function opblur(obj,cartId){

        if($(obj).parents(".marketgroup").attr("attr-codextype")!="-1"){
            var reg = new RegExp("^[0-9]*$");
            var nowNum = $(obj).val();
            var maxNum = $(obj).attr("attr-maxnum");
            var minNum = 1;
            var setNum = 0 ;
            if(nowNum=='0'){
                setNum = minNum ;
                nowNum =  minNum;
            }
            if(reg.test(nowNum)){

                if(SubtrInt(nowNum,maxNum)>0){
                    setNum = maxNum ;
                }else{
                    setNum = nowNum;
                }

            }else{
                setNum = minNum ;
            }

            $(obj).val(setNum);
            var onePrice = $(obj).parents(".cart_goods").find(".oneprice").val();
            var xiaoji = accMul(onePrice,setNum);
            $(obj).parents(".cart_goods").find(".smprice").val(xiaoji);
            $(obj).parents(".cart_goods").find(".pv_smprice").text(xiaoji);
            //赠送类型0金额1件数
            var presentType = $(obj).parents(".marketgroup").attr("attr-pretype");
            if(presentType == 0){
                //所有满赠商品的总价（判断符合几级满赠）
                var nowAll = 0;
                $(obj).parents(".marketgroup").find(".smprice").each(function(){
                    nowAll = accAdd(parseFloat(nowAll),parseFloat($(this).val()));
                });
                $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                    $(this).val(nowAll);
                })
            }else{
                //满数量赠时
                var nowAll = 0;
                $(obj).parents(".marketgroup").find(".text").each(function(){
                    nowAll = accAdd(parseFloat(nowAll),parseFloat($(this).val()));
                });
                $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                    $(this).val(nowAll);
                })
            }
            $(obj).parents(".marketgroup").find(".gift:visible").hide();
            isHasGift(obj,nowAll);
            changeNum(cartId,setNum);
            jisuan(obj);
        }else{
            var flag= 0 ;
            //套装
            var reg = new RegExp("^[0-9]*$");
            var nowNum = $(obj).val();
            if(!reg.test(nowNum)){
                nowNum = 1;
                $(obj).val(nowNum);
            }
            var maxNum = $(obj).attr("attr-maxnum");
            if(SubtrInt(maxNum,nowNum)<0){
                nowNum = maxNum;
            }
            $(obj).val(nowNum);
            var zongji = 0 ;
            $(obj).parents(".marketgroup").find(".cart_item_goods").each(function(){
                $(this).find(".buyNum").text(nowNum);
                var onePrice = $(this).find(".oneprice").val();
                var psum = $(this).find(".psum").val();
                var xiaoji = accMul(onePrice,nowNum);
                xiaoji = accMul(xiaoji,psum);
                zongji = accAdd(xiaoji,zongji);
                $(this).find(".smprice").val(xiaoji);
                $(this).find(".pv_smprice").text(xiaoji);
            });

            if($(obj).parents(".cart_item").find(".mjchecked")[0].checked){
                $(obj).parents(".marketgroup").find(".xiaoji").val(zongji);
                var oneyh = $(obj).parents(".cart_item").find(".groupPreferamount").val();
                $(obj).parents(".marketgroup").find(".youhui").val(accMul(oneyh,nowNum));
            }

            changeNum(cartId,nowNum);
            lastsum();

        }
    }


    function add(obj,cartId){
        //不等于-1 为套装
        if($(obj).parents(".marketgroup").attr("attr-codextype")!="-1"){
            //获取 可用的最大库存和最小
            var maxNum = $(obj).prev().attr("attr-maxnum");
            var nowNum = $(obj).prev().val();
            var setNum = 0;
            if(SubtrInt(maxNum,nowNum)<=0){
                setNum=nowNum;
            }else{
                setNum=accAddInt(nowNum,1);
            }

            $(obj).prev().val(setNum);
            var onePrice = $(obj).parents(".cart_goods").find(".oneprice").val();
            var xiaoji = accMul(onePrice,setNum);
            $(obj).parents(".cart_goods").find(".smprice").val(xiaoji);
            $(obj).parents(".cart_goods").find(".pv_smprice").text(xiaoji);
            //赠送类型0金额1件数
            var presentType = $(obj).parents(".marketgroup").attr("attr-pretype");
            if(presentType == 0){
                //原先所有满赠商品的总价（判断符合几级满赠）
                var preAll = $(obj).parents(".marketgroup").find(".gift:first").find(".fullGiftAllPrices").val();
                //现在剩下的满赠商品总价
                var nowAll = accAdd(preAll,onePrice);
                $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                    $(this).val(nowAll);
                })
            }else{
                //满数量赠时
                var nowAll = accAdd(parseFloat($(obj).parents(".marketgroup").find(".fullGiftAllPrices:first").val()),1);
                $(obj).parents(".marketgroup").find(".fullGiftAllPrices").each(function(){
                    $(this).val(nowAll);
                })
            }
            $(obj).parents(".marketgroup").find(".gift:visible").hide();
            isHasGift(obj,nowAll);
            changeNum(cartId,setNum);
            jisuan(obj);
        }else{
            //套装
            var nowNum = $(obj).prev().val();
            var flag = 0;
            var num = $(obj).prev().attr("attr-maxnum");
            if(SubtrInt(num,nowNum)<=0){
                flag ++ ;
            }


            if(flag == 0 ){
                var zongji = 0 ;
                $(obj).prev().val(accAddInt(nowNum,1));
                nowNum ++;
                $(obj).parents(".marketgroup").find(".cart_item_goods").each(function(){
                    $(this).find(".buyNum").text(nowNum);
                    var onePrice = $(this).find(".oneprice").val();
                    var psum = $(this).find(".psum").val();
                    var xiaoji = accMul(onePrice,nowNum);
                    xiaoji = accMul(xiaoji,psum);
                    zongji = accAdd(xiaoji,zongji);
                    $(this).find(".smprice").val(xiaoji);
                    $(this).find(".pv_smprice").text(xiaoji);
                });
                if($(obj).parents(".cart_item").find(".mjchecked")[0].checked){
                    $(obj).parents(".marketgroup").find(".xiaoji").val(zongji);
                    var oneyh = $(obj).parents(".cart_item").find(".groupPreferamount").val();
                    $(obj).parents(".marketgroup").find(".youhui").val(accMul(oneyh,nowNum));
                }
                changeNum(cartId,nowNum);
                lastsum();
            }

        }


    }



    function jisuan(obj){
        //计算价格 总计和优惠
        var codexType =  $(obj).parents(".marketgroup").attr("attr-codextype");

        var xiaoji = 0 ;
        var youhui = 0 ;
        $(obj).parents(".marketgroup").find(".mjchecked").each(function(){

            if($(this)[0].checked){
                //获取小计
                var thisprice=$(this).parents(".cart_goods").find(".smprice").val();
                //总计+单品小计
                xiaoji = accAdd(thisprice,xiaoji);


            }

        });


        if(codexType=='1'){
            $(obj).parents(".marketgroup").find(".mjchecked").each(function(){
                if($(this)[0].checked){
                    var num = $(this).parents(".cart_goods").find(".count").find(".text").val();
                    var zhijiang=$(this).parents(".cart_goods").find(".marketPrice").val();
                    youhui=accAdd(youhui,accMul(num,zhijiang));
                }
            })

        }
//        else if(codexType=='10'){//团购
//            $(obj).parents(".marketgroup").find(".cart_goods").each(function(){
//                var num= $(this).find(".count").find(".text").val();
//                var tuangou=$(this).parent().find(".groupPrice").val();
//                youhui=accAdd(youhui,accMul(num,tuangou));
//            })
//        }

        else if(codexType=='11'){//抢购
            $(obj).parents(".marketgroup").find(".cart_goods").each(function(){
                var num= $(this).find(".count").find(".text").val();
                var qianggou=$(this).parent().find(".rushPrice").val();
                youhui=accAdd(youhui,accMul(num,qianggou));
            })
        }

        else if(codexType=='5'){
            $(obj).parents(".marketgroup").find(".manjian_reducePrice").each(function(){
                var man = $(this).val().split(",")[0];
                var jian = $(this).val().split(",")[1];
                if(Subtr(xiaoji,man)>=0){
                    youhui = jian;
                }

            });

        }else if(codexType=='8'){
            $(obj).parents(".marketgroup").find(".manzhe_fullbuyDiscount").each(function(){
                var man = $(this).val().split(",")[0];
                var zhe = $(this).val().split(",")[1];
                if(Subtr(xiaoji,man)>=0){
                    youhui = accMul(xiaoji,Subtr(1,zhe));
                }

            });

        }

        $(obj).parents(".marketgroup").find(".xiaoji").val(xiaoji);
        $(obj).parents(".marketgroup").find(".youhui").val(youhui);

        lastsum();
    }


    function del(id,infoId){
        $('#shoppingCartId').val(id);
        $('#goodsInfoId').val(infoId);
    }


    function dodel(){
        var id = $('#shoppingCartId').val();
        var infoId=$("#goodsInfoId").val();
        var fitId=$("#fitId").val();
        if(fitId==""){

            $.post("delshoppingcartbyid/"+id+"-"+infoId, function (data)
            {
                if (data==1)
                {
                    $('#shoppingCartId').val('');
                    $('#delete_dialog').hide();
                    var tg = $('#cart_goods_'+id).parents(".marketgroup").parent().find(".marketgroup:visible").length;
                    if(tg==1){
                        var nums = $('#cart_goods_'+id).parents(".marketgroup").find(".cart_goods").length;
                        if(nums==1){
                            $('#cart_goods_'+id).parents(".marketgroup").parent().remove();
                        }else{
                            $('#cart_goods_'+id).remove();
                        }
                    }else{
                        var lg = $('#cart_goods_'+id).parents(".marketgroup").find(".cart_goods").length;
                        if(lg==1){
                            $('#cart_goods_'+id).parents(".marketgroup").remove();
                        }else{

                            $('#cart_goods_'+id).remove();
                            $('#cart_goods_'+id).find(".mjchecked").attr("checked", false);
                            checkOne($('#cart_goods_'+id).find(".mjchecked"));
                            $(".carts_"+id).remove();
                        }
                    }
                    $(".mjchecked").each(function(){
                        if($(this)[0].checked){
                            checkOne(this);
                        }
                    });
                    lastsum();
                    if ($("#subForm").find(".marketgroup").length == 0)
                    {
                        $("#subForm").html('<p style="text-align:center;padding:20px 0;">购物车为空！！<a href="${indexUrl}" style="color:#c00"> 去逛逛</a></p>');
                    }

                }else{
                    //删除失败
                    $("#con_00").html("删除失败！");
                    dia(2);
                }
            });
        }else{
            var inId="110012"+fitId;
            $.post("delshoppingcartbyid/"+id+"-"+inId, function (data)
            {
                if (data==1){
                    $("#delGroupFil").submit();
                }
            });

        }
    }





    function hideDia(){
        $("#fitId").attr("value","");
        $('#delete_dialog').hide();
    }


    function changemarketing(obj,id,goodsGroupId){
        //购物车选中的商品id数组
        var list = [];
        //购物车所有商品id数组
        var listTwo = [];
        //购物车未选中商品id数组
        var listThree = [];

        //购物车选中的商品id数组
        for(var i=0;i<$(".mjchecked:checked").length;i++){
            list.push($(".mjchecked:checked")[i].value);
        }
        //购物车所有商品id数组
        for(var i=0;i<$(".mjchecked").length;i++){
            listTwo.push($(".mjchecked")[i].value);
        }
        for(var k=0;k<listTwo.length;k++){
            //listTwo里是否包含list里面数据，没有的话push到listThree中
            if (arrayFindString(list,listTwo[k])<0){
                //购物车未选中的商品id数组
                listThree.push(listTwo[k]);
            }
        }

        var newMarId = $(obj).parents(".dialog-content").find(".chooseMarket").val();
        if(newMarId!=''){
            $(".marketing_activity_id").val(newMarId);
            $(".goods_group_marketing_id").val(goodsGroupId);
            $(".shopping_cart_id").val(id);
            $(".check_list").val(listThree);
            $("#listSize").val(listThree);
            $(".change_shopping").submit();
        }

    }

    function arrayFindString(arr, string) {
        var str = arr.join(",");
        return str.indexOf(string);
    }

    function oncheckAll(obj) {
        var allChecked = $(obj)[0].checked;
        $(".mjchecked").each(function () {
                $(this).attr("checked", allChecked);
                checkOne(this);

        });
        lastsum();
    }


    function changeNum(id, num) {
        $.post("changeshoppingcartbyid/" + id + "-" + num, function (data) {
            if (data == 1) {

            }
        });
    }


    function onpay(){
        var str = "";
        $("#subForm").find(".gift:visible").each(function(){
            str = str + $(this).find(".cartId").val() + ":" + $(this).find(".cartId").siblings(".presentScopesIds").val() + ";"
        });
        if(str != ""){
            str = str.substring(0,str.length-1);
        }
        $("#presentScopeIds").val(str);
        var $sum = 0 ;
        var $nopro = 0;
        var limit=0;
        $(".mjchecked").each(function(){
            if($(this)[0].checked){
                if($(this).parents(".cart_goods").find(".noProduct").val()=='0'){
                    $nopro = 1;
                }
                //获取外部的DIV 是否是促销的商品
                var obj=this;
                var codexType = $(obj).parents(".marketgroup").attr("attr-codextype");
                if(codexType!='0'){
                    if(obj.checked){
                        if(codexType=='11'){//抢购
                            if($(".rushlimit").val()==1 && $(".rushlimit").attr("attr_id")==$(obj).val()){
                                limit=1;
                            }

                        }


                    }
                }
                $sum++;
            }
        });

        if($nopro==0){
            if($sum==0){
                dia(3);
            }else{
                if(limit==1){
                    dia(6);
                }else{
                    $("#subForm").submit();
                }
            }
        }else{
            dia(55);

        }


    }

    //presentMode 0:默认全增，1：选择一种
    function showGift(presentMode,index,obj){
        $(".exchange").removeAttr("onclick");
        var htm = "";
        $(obj).parent(".gift").find(".light").each(function(){
           htm += '<div class="pr">';
            if(presentMode == "0"){
                if($(this).attr("style") == "display:none"){
                    htm += '<input type="checkbox" value="'+$(this).find("input[class=presentScoId]").val()+'"/>';
                }else{
                    htm += '<input type="checkbox" checked value="'+$(this).find("input[class=presentScoId]").val()+'"/>';
                }
            }else{
                htm += '<input type="radio" name="gift" class="singlechoose" value="'+$(this).find("input[class=presentScoId]").val()+'"/>';
            }
            if(parseFloat($(this).find(".proStock").val()) >= parseFloat($(this).find(".proNum").val())){
                htm += '<img style="width:50px;height:50px;" src="'+$(this).find("input[class=proImg]").val()+'">';
            }else{
                htm += '<img style="width:50px;height:50px;" src="'+$(this).find("input[class=proImg]").val()+'">';
                htm += '<img style="position:absolute;left:20px;top:10px;width:50px;height:50px;"" src="${basePath}/images/nostock.png">';
            }
           htm += '<a>'+$(this).find("input[class=proName]").val()+'</a>';
           htm += '<span>X'+$(this).find("input[class=proNum]").val()+'</span>';
           htm += '<div class="cb"></div></div>';
        });
        dia(9);
        $(".box_gifts").append(htm);
        $("#confirmGift").attr("href","javascript:confirmGift("+index+");");
        if(presentMode == "1"){
            $(".box_gifts").find(".singlechoose[value="+$(obj).siblings(".light:visible").find("input[class=presentScoId]").val()+"]").attr("checked","checked");
        }
    }

    function giftCls(){
        $(".dialog").fadeOut();
        $(".member-dialog").fadeOut();
        $(".mask").fadeOut();
        window.setTimeout("clearGifts()",500);
        $(".exchange").each(function(){
            $(this).attr("onclick","showGift("+$(this).attr('attr_presentmode')+","+$(this).attr('attr_presentmode')+",this)")
        })
    }

    function confirmGift(index){
        var array = "";
        $("#confirmGift").parent().prev(".dia_intro").find("input:checked").each(function(){
            if(array == ""){
                array = $(this).val();
            }else{
                array = array+","+$(this).val();
            }
        });
        var list = array.split(",");
        $(".exchange[attr_index="+index+"]").siblings(".light").attr("style","display:none");
        for(var i = 0; i < list.length; i++) {
            if(list.length == 0 || (list.length == 1 && list[0] == "")){
                $(".exchange[attr_index="+index+"]").attr("style","display:none");

            }else{
                $(".exchange[attr_index="+index+"]").siblings(".light").each(function(){
                    if($(this).find(".presentScoId").val() == list[i]){
                        $(this).removeAttr("style");
                    }
                });
            }
        }
        giftCls();
        fillData();
    }
    function clearGifts(){
        $(".box_gifts").children().remove();
    }

    function fillData(){
        var presentScopesIds = "";
        var num = 0;
        $(".exchange").each(function(){
            $(this).attr("attr_index",num);
            $(this).attr("onclick","showGift("+$(this).attr('attr_presentmode')+","+num+",this);");
            num++;
        });
        $(".gift").each(function(){
            $(this).find(".cartId").val($(this).parents(".g_goods").prev(".g_check").find(".mjchecked").val());
            if($(this).find(".light:visible").length == 0){
                presentScopesIds = 0;
            }else{
                $(this).find(".light:visible").each(function(){
                    if(presentScopesIds == ""){
                        presentScopesIds = $(this).find("input[class=presentScoId]").val();
                    }else{
                        presentScopesIds = presentScopesIds+","+$(this).find("input[class=presentScoId]").val();
                    }
                });
            }
            $(this).find(".presentScopesIds").val(presentScopesIds);
            presentScopesIds = "";
        })
    }
</script>
<style>
    .box_gifts{text-align:left;max-height:200px;overflow:auto;}
    .box_gifts div{margin-bottom:5px;padding-top:10px;}
    .box_gifts div input,.box_gifts div img,.box_gifts div a,.box_gifts div span{float:left;}
    .box_gifts div a{display:block;width:240px;margin:0 10px;white-space: nowrap;overflow:hidden;text-overflow: ellipsis;}
    .box_gifts div img{margin-top:-10px;border:1px solid #dfdfdf;}
</style>
</body>
</html>
