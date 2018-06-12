<!DOCTYPE html>
<html lang="zh-cn" xmlns:c="http://www.w3.org/1999/html">
<#assign basePath=request.contextPath>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>订单确认</title>
<link rel="stylesheet" href="${basePath}/css/style.min.css"/>
<link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
<link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
<style>
    .cart_gifts{
        position: relative;
        padding-left:2em;
        margin-bottom:1em;
    }
    .cart_gifts p{
        color:#999;
        line-height: 2em;
    }
    .cart_gifts p span.name{
        display:inline-block;
        vertical-align: bottom;
        width:12em;
        overflow:hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    .cart_gifts a.change_gift{
        position: absolute;
        right:0;
        bottom:.5em;
        color:#E63D59;
    }
    .ui-dialog-footer button[disabled]{
        background-color: #cccccc;
        color: #ffffff;
    }


</style>
</head>
<body>
<!--计算总商品数量-->
<#assign num=0>
<!--计算boss商品数量-->
<#assign bossNum=0>
<!--记录第三方商品数量-->
<#assign thirdNum=0>
<!--计算优惠金额-->
<#assign  youhui=map.youhui/>
<#assign bossyouhui=map.bossyouhui/>

<#assign choseDeliveryPoint=deliveryMap?? && deliveryMap.choseDeliveryPoint??/>
<#assign deliveries=deliveryMap?? && deliveryMap.deliveries??&&deliveryMap.deliveries?size!=0/>



<form  method="post" id="subForm">
    <!--支付方式 1在线支付 2 货到付款-->
    <input type="hidden"name="ch_pay" id="chPay"/>

    <#if choseDeliveryPoint><!-- 不为空的时候上门自提 -->
        <input type="hidden" name="typeId" value="1" id="typeId">
    <#else><!-- 其他配送方式 -->
        <input type="hidden" name="typeId" value="0" id="typeId">
    </#if>

    <#if coupon??>
        <input type="hidden" name="codeNo" value="${coupon.codeNo}">
    </#if>
    <input type="hidden" value="${invoiceType!'0'}" name="invoiceType" id="invoiceType">
    <input type="hidden" value="${invoiceTitle!''}" name="invoiceTitle" id="invoiceTitle">
    <input type="hidden"name="addressId" value="${orderAddress.addressId!''}" id="addressId"/>
    <input id="sumOldPrice" type="hidden" value="${map.sumOldPrice!''}"/>
    <input id="sumPrice" type="hidden" value="${map.sumPrice!''}"/>
    <input id="zhek"  type="hidden" value="${map.zheKPrice}"/>
    <input id="fPrice"  type="hidden" value="${map.fPrice.freightmoney}"/>
    <input id="jfen" type="hidden" value="${map.customerPoint}"/>
    <input id="pointSet" type="hidden" value="${map.pointSet}"/>
    <input id="isOpen" type="hidden" value="${map.isOpen!''}"/>
    <input type="hidden"name="payPassword" id="subPassword"/>
    <#if choseDeliveryPoint>
        <input name="deliveryPointId" type="hidden" id="deliveryPointId" value="${deliveryMap.choseDeliveryPoint.deliveryPointId!''}"/>
    </#if>

<!--商品优惠价格-->
<#assign preprice=0>
    <#if map.shoplist??&&map.shoplist?size!=0>
        <#list map.shoplist as cart>
            <#if cart.goodsDetailBean??&&cart.goodsDetailBean.productVo.thirdId??>
                <#if cart.goodsDetailBean.productVo.thirdId!=0>
                    <#assign thirdNum=thirdNum+1>
                <#elseif cart.goodsDetailBean.productVo.thirdId==0>
                    <#assign bossNum=bossNum+1>
                </#if>
            <#elseif cart.fitId??>
                <#assign bossNum=bossNum+1>
            </#if>
        </#list>
    </#if>
    <#--[debug]bossNum=${bossNum}-->
<div class="order content-order-confirm">
    <div class="receive-info">
        <#if orderAddress??&&orderAddress.addressId??>
            <input type="hidden"name="addressId" value="${orderAddress.addressId!''}" id="addressId"/>
            <div class="list-item">
            <a href="${basePath}/addresslist.htm?flag=1">
                <h3>
                    <span class="name">${(orderAddress.addressName)!""}</span>
                    <span class="phoneNum">${(orderAddress.addressPhone)!""}</span>
                </h3>
                <p class="dress-info">${(orderAddress.addressDetail)!""}&nbsp;
                ${(orderAddress.addressDetailInfo)!""}</p>
                <i class="arrow-right"></i>
            </a>
        </div>
        <#else>
        <div class="list-item">
            <a href="${basePath}/addresslist.htm?flag=1">
                <p style="padding:5px 10px;text-align: center;font-size:1.1em;">
                    <i class="ion-plus-round"></i>
                    <span>添加收货地址</span>
                </p>
            </a>
        </div>
        </#if>
    </div>
<#if bossNum!=0>
    <div class="order-info mt10">
        <div class="list-body-line">

                <#assign bnum=0/>
                        <#list map.shoplist as cart>
                        <#--[debug]cart=${cart}-->
                            <#assign bnum=bnum?number+1 />
                            <#if bnum==1>
                                <div class="profrom-way">
                                    <span class="some-tip">自营商品</span>
                                </div>
                            </#if>
                            <#if cart.goodsDetailBean??&&cart.goodsDetailBean.productVo??&&cart.goodsDetailBean.productVo.thirdId?? &&cart.goodsDetailBean.productVo.thirdId==0>

                                <div class="list-item confirm-pro-item"  <#if (bnum?number>3)>style="display:none" </#if>>

                                        <a target="_blank" title="${cart.goodsDetailBean.productVo.productName}">
                                        <div class="pro-item">
                                            <div class="propic">
                                                <input type="hidden" value="${cart.shoppingCartId}" name="shoppingCartId">
                                                <img src="${cart.goodsDetailBean.productVo.goodsInfoImgId}" alt="产品图">
                                            </div>
                                            <div class="prodesc">
                                                <h3 class="title">${cart.goodsDetailBean.productVo.productName!''}</h3>

                                                <p class="price">¥&nbsp;<span class="num">${cart.goodsDetailBean.productVo.goodsInfoPreferPrice?string("0.00")}</span></p>
                                                <span class="pro-num">×${cart.goodsNum!''}</span>

                                                <#if cart.marketingList??&&cart.marketingList?size!=0>
                                                    <#list cart.marketingList as mar>
                                                        <#if cart.marketingActivityId??&&mar.marketingId==cart.marketingActivityId>
                                                            <p class="some-info">优惠：
                                                                <span>
                                                                    <!--直降-->
                                                                    <#if mar.codexType=='1'>
                                                                        直降${mar.priceOffMarketing.offValue}
                                                                        元
                                                                    </#if>

                                                                    <!--满减-->
                                                                    <#if mar.codexType=='5'>
                                                                        <#list mar.fullbuyReduceMarketings as fr>
                                                                            满 ${fr.fullPrice}
                                                                            减${fr.reducePrice}元 &nbsp;
                                                                        </#list>

                                                                    </#if>

                                                                    <!--满折-->
                                                                    <#if mar.codexType=='8'>
                                                                        <#list mar.fullbuyDiscountMarketings as mz>
                                                                            满 ${mz.fullPrice}
                                                                            打 ${mz.fullbuyDiscount*10}折
                                                                            &nbsp;
                                                                        </#list>
                                                                    </#if>
                                                                <#--团购-->
                                                                <#--<#if market.codexType=='10'>-->
                                                                <#--${market.groupon.grouponDiscount}折团购-->
                                                                <#--</#if>-->
                                                                <#--抢购-->
                                                                    <#if mar.codexType=='11'>
                                                                    ${(mar.rushs[0].rushDiscount*10)?string('0.#')}折抢购
                                                                    </#if>
                                                                    <!--包邮-->
                                                                <#--<#if market.codexType=='12'>-->
                                                                <#--<#if market.shippingMoney??>满 ${market.shippingMoney} 包邮</#if>-->
                                                                <#--</#if>-->
                                                                    <#--${mar.marketingName}-->
                                                                    <!--满赠-->
                                                                    <#if mar.codexType=='6'>
                                                                        <#list mar.fullbuyPresentMarketings as fp>
                                                                            <#if fp.presentType == '0'>
                                                                                满 ${fp.fullPrice}元送赠品&nbsp;
                                                                            <#elseif fp.presentType == '1'>
                                                                                满 ${fp.fullPrice?number}件送赠品&nbsp;
                                                                            </#if>
                                                                        </#list>

                                                                    </#if>
                                                                </span></p>
                                                        </#if>
                                                    </#list>
                                                </#if>



                                </div>
                                        </div>
                                        </a>
                                    <!--赠品-->
                                <#if cart.presentGoodsProducts??>
                                    <div class="cart_gifts">
                                        <#list cart.presentGoodsProducts as presentGoodsProduct>
                                            <p>
                                                <span>【赠品】</span>
                                                <span class="name">${presentGoodsProduct.goodsInfoName}</span>
                                                <span>×${presentGoodsProduct.scopeNum}</span>
                                                <input type="hidden" name="presentScopeId" value="${presentGoodsProduct.presentScopeId}">
                                            </p>
                                        </#list>
                                    </div>
                                </#if>
                                </div>

                            <#elseif cart.fitId??>
                                <input type="hidden" value="${cart.shoppingCartId}" name="shoppingCartId">

                                <#assign group = cart.goodsGroupVo>
                                <div class="list-item confirm-pro-item">
                                    <div class="group-title">
                                        <span class="amount">×${cart.goodsNum!0}套</span>
                                        <span class="price">¥${group.price?string("0.00")}</span>
                                        <p><span class="label-line">套装</span>${group.groupName}</p>
                                    </div>
                                    <#list group.productList as product>
                                        <#assign vo = product.productDetail/>
                                        <a href="${basePath}/item/${vo.goodsInfoId}.html">
                                            <div class="pro-item">
                                                <div class="propic">
                                                    <img src="${vo.goodsInfoImgId}">
                                                </div>
                                                <div class="prodesc">
                                                    <h3 class="title">${vo.goodsInfoName}</h3>
                                                    <p class="price">¥&nbsp;<span
                                                            class="num">${vo.goodsInfoPreferPrice?string("0.00")}</span>
                                                    </p>
                                                    <span class="pro-num">×${product.productNum?number}件/套</span>
                                                </div>
                                            </div>
                                        </a>
                                    </#list>
                                </div>


                            </#if>



                        </#list>


        </div>
        <#if (bossNum>3)>
            <div class="list-item see-all" onclick="showGoods(this)">
                <input type="hidden" class="storeId" value="0">
                — — — — 显示其他${bossNum-3}件商品 — — — —
            </div>
        </#if>
        <div class="list-item">
            <div class="send-way">
                <h3 class="item-head">配送方式</h3>

                <div class="list-value">
                    <#if map.frightlist??&&map.frightlist?size!=0>

                        <#list map.frightlist as  fr>
                            <#if fr.freightThirdId==0>
                                <a class="btn-grey selected" href="javascript:;">${fr.freightTemplateName!''}</a>
                            </#if>
                        </#list>
                    </#if>
                    <!-- 将条件放入 -->
                    <#if deliveries||choseDeliveryPoint>
                        <#if choseDeliveryPoint>
                            <a class="btn-grey selected" href="javascript:;" id="selfPick">
                                上门自提
                            </a>
                        <#else>
                            <a class="btn-grey" href="javascript:;" id="selfPick">
                                上门自提
                            </a>
                        </#if>

                    </#if>
                </div>
            </div>
        </div>

        <!-- 自提点，有已经选择的提点直接展示 -->
        <#if choseDeliveryPoint>
            <div class="list-item" id="deliverylist">
                <a href="javascript:;">
                    <i class="arrow-right"></i>
                    <h3 class="item-head">自提地点<span class="curValue" dpId="${deliveryMap.choseDeliveryPoint.deliveryPointId!''}">${deliveryMap.choseDeliveryPoint.name!''}</span></h3>
                </a>
            </div>
        <!-- 没有勾选自提点,存在自提点 -->
        <#elseif deliveries>
            <div class="list-item" style="display:none;" id="deliverylist">
                <a href="javascript:;">
                    <i class="arrow-right"></i>
                    <h3 class="item-head">自提地点
                        <#list deliveryMap.deliveries as dp>
                            <#if dp_index == 0>
                                <span class="curValue" dpId="${dp.deliveryPointId!''}">${dp.name!''}</span>
                            </#if>
                        </#list>
                    </h3>
                </a>
            </div>
        <#else>

        </#if>

    </div>
</#if>
    <#if thirdNum!=0>
        <#list map.thirds as store>
            <#if store.thirdId!=0>
                <div class="order-info mt10">
                        <#assign tNum=0 />
                        <#list  map.shoplist as cart>
                            <#if cart.goodsDetailBean??&& cart.goodsDetailBean.productVo.thirdId?? && cart.goodsDetailBean.productVo.thirdId==store.thirdId&&store.thirdId!=0>
                                <#assign tNum=tNum+1/>
                            <div class="list-item  goods${store.thirdId}"  <#if (tNum?number>3)>style="display:none" </#if>>
                                <#if tNum==1>
                                <div class="profrom-way"><span class="some-tip">${store.thirdName!''}</span></div>
                                </#if>
                                        <div class="pro-item" >
                                           <a href="${basePath}/item/${cart.goodsDetailBean.productVo.goodsInfoId}.html" target="_blank" title="${cart.goodsDetailBean.productVo.productName}">
                                            <div class="propic">
                                                <input type="hidden" value="${cart.shoppingCartId}" name="shoppingCartId">
                                                <img src="${cart.goodsDetailBean.productVo.goodsInfoImgId}" alt="产品图">
                                            </div>
                                            <div class="prodesc">
                                                <h3 class="title">${cart.goodsDetailBean.productVo.productName!''}</h3>

                                                <p class="price">¥&nbsp;<span class="num">${cart.goodsDetailBean.productVo.goodsInfoPreferPrice?string("0.00")}</span></p>
                                                <span class="pro-num">×${cart.goodsNum!''}</span>
                                                <#if cart.marketingList??&&cart.marketingList?size!=0>
                                                    <#list cart.marketingList as mar>
                                                        <#if cart.marketingActivityId??&&mar.marketingId==cart.marketingActivityId>
                                                            <p class="some-info">优惠：
                                                                <span>
                                                                     <!--直降-->
                                                                    <#if mar.codexType=='1'>
                                                                        直降${mar.priceOffMarketing.offValue}
                                                                        元
                                                                    </#if>

                                                                    <!--满减-->
                                                                    <#if mar.codexType=='5'>
                                                                        <#list mar.fullbuyReduceMarketings as fr>
                                                                            满 ${fr.fullPrice}
                                                                            减${fr.reducePrice}元 &nbsp;
                                                                        </#list>

                                                                    </#if>

                                                                    <!--满折-->
                                                                    <#if mar.codexType=='8'>
                                                                        <#list mar.fullbuyDiscountMarketings as mz>
                                                                            满 ${mz.fullPrice}
                                                                            打 ${mz.fullbuyDiscount*10}折
                                                                            &nbsp;
                                                                        </#list>
                                                                    </#if>
                                                                <#--团购-->
                                                                <#--<#if market.codexType=='10'>-->
                                                                <#--${market.groupon.grouponDiscount}折团购-->
                                                                <#--</#if>-->
                                                                <#--抢购-->
                                                                    <#if mar.codexType=='11'>
                                                                    ${(mar.rushs[0].rushDiscount*10)?string('0.#')}折抢购
                                                                    </#if>
                                                                    <!--包邮-->
                                                                <#--<#if market.codexType=='12'>-->
                                                                <#--<#if market.shippingMoney??>满 ${market.shippingMoney} 包邮</#if>-->
                                                                <#--</#if>-->
                                                                <#--${mar.marketingName}-->
                                                            </span></p>
                                                        </#if>
                                                    </#list>
                                                </#if>

                                            </div>
                                         </a>
                                        </div>
                            </div>
                            </#if>
                        </#list>

                    <#if (tNum>3)>
                        <div class="list-item see-all" onclick="showGoods(this)">
                            <input type="hidden" class="storeId" value="${store.thirdId}">
                            — — — — 显示其他${tNum-3}件商品 — — — —
                        </div>
                    </#if>
                    <div class="list-item">
                        <div class="send-way">
                            <h3 class="item-head">配送方式</h3>

                            <div class="list-value">
                                <#if map.frightlist??&&map.frightlist?size!=0>
                                    <#list map.frightlist as  fr>
                                        <#if fr.freightThirdId==store.thirdId>
                                            <a class="btn-grey selected" href="javascript:;">${fr.freightTemplateName!''}</a>
                                        </#if>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                    </div>

                </div>
             </#if>
        </#list>
    </#if>

    <div class="all-info mt10">
        <div class="list-item">
            <a href="${basePath}/tochangeInvoice.htm?invoiceTitle=${invoiceTitle!''}&invoiceType=${invoiceType!''}<#if orderAddress??>&addressId=${orderAddress.addressId!''}</#if>&typeId=${typeId!''}&ch_pay=${ch_pay!''}&deliveryPointId=${deliveryPointId!''}&codeNo=<#if coupon??>${coupon.codeNo!''}</#if>">
                <i class="arrow-right"></i>
                <h3 class="item-head">发票信息<span class="curValue text-them">${invoiceTitle!'不开发票'}</span></h3>
            </a>
        </div>
        <#assign  couponPrice=0/>
    <#assign  bosscouponPrice=0/>
    <#if map.couponlist??&&map.couponlist?size!=0>
        <div class="list-item">
            <a href="${basePath}/tocouponlist.htm?invoiceTitle=${invoiceTitle!''}&invoiceType=${invoiceType!''}<#if coupon??>&codeNo=${coupon.codeNo}</#if>">
                <i class="arrow-right"></i>
                <h3 class="item-head">使用优惠券<small id="coupon"><#if coupon??>${coupon.couponName}</#if></small></h3>
                <input id="couponPrice" value="<#if coupon??&&coupon.couponRulesType=='2'>${coupon.couponFullReduction.reductionPrice}<#elseif coupon??&&coupon.couponRulesType=='1'>${coupon.couponStraightDown.downPrice}</#if>" type="hidden"/>
        <#if coupon??&&coupon.couponRulesType=='2'>
            <#assign  couponPrice=coupon.couponFullReduction.reductionPrice?number+couponPrice?number/>
        <#elseif coupon??&&coupon.couponRulesType=='1'>
            <#assign  couponPrice=couponPrice?number+coupon.couponStraightDown.downPrice?number/>

        </#if>
                <#if coupon??&& coupon.businessId==0>
                    <#if map.bossPrice?number-couponPrice?number<0.01>
                        <#assign  youhui=youhui?number+ map.bossPrice?number-0.01 />
                        <#assign  bosscouponPrice= map.bossPrice?number-0.01/>
                    <#else>
                        <#assign  bosscouponPrice=couponPrice?number/>
                        <#assign  youhui=youhui?number+couponPrice?number />
                    </#if>
                <#else>
                    <#list map.storeList as store>
                        <#if coupon??&&store.thirdId==coupon.businessId>
                            <#if store.sumPrice?number-couponPrice?number<0.01>
                                <#assign  youhui=youhui?number+ store.sumPrice?number-0.01 />
                            <#else>
                                <#assign  youhui=youhui?number+couponPrice?number />
                            </#if>
                        </#if>
                    </#list>
                </#if>
            </a>
        </div>
    </#if>
        <input id="bossPrice" type="hidden" value="${map.allPrice?number+map.fPrice.bossfreight?number}"/>
        <input id="bosscouponPrice" type="hidden" value="${bosscouponPrice}"/>
        <input id="youhui" type="hidden"  value="${youhui}"/>
        <input type="hidden" id="isThirdSku" value="${bossNum}">
        <#if bossNum!=0>
            <div class="list-item">
                <div id="jifenonjudeg" class="use-points on-off" on="on"></div>
                <h3 class="item-head">使用积分
                    <small><span class="small" id="jfSmall">可用1150积分，抵扣¥</span></small>
                    <input type="hidden" id="maxjf"/>
                </h3>
            </div>
            <div class="list-item use-points-form" style="display: none;">
                <h3 class="item-head">
                    <span class="use-jifen">使用<input type="text" class="judge" id="jifen" name='point' onkeyup="caljifen(this)"/>积分，抵扣¥<span id="jifenPrice">0.00</span></span><br/>
                    <span id="jifenError" style="color:red;"></span>
                </h3>
            </div>
        </#if>
    </div>

    <#assign payPrice=map.sumOldPrice?number+map.fPrice.freightmoney?number-map.zheKPrice?number-youhui?number/>
    <input value="${payPrice}" type="hidden" id="payPrice"/>
    <input  type="hidden" id="jfPrice"/><!--使用积分后-->
    <div class="all-info mt10">
        <div class="list-item">
            <ul class="price-total">
                <li><label for="">商品金额</label><span class="value">¥${map.sumOldPrice!'0'}</span></li>
                <li><label for="">优惠金额</label><span class="value" id="yPrice">-¥${youhui!'0'}</span></li>
                <li><label for="">会员折扣</label><span class="value">-¥${map.zheKPrice}</span></li>
                <li><label for="">配送费用</label><span class="value">¥${map.fPrice.freightmoney!'0'}</span></li>
                <li><label for="">总额</label><span class="value text-them" id="sPrice">¥${payPrice!'0'}</span></li>
            </ul>
        </div>
    </div>
    <div class="all-info mt10">
        <div class="pay-btns">
        <#list pays as pay>
                <#if pay.isOpen == '1' && pay.payType=="3">
                    <a class="btn btn-full weixin" href="javascript:;"><i></i>微信支付</a>
                <#elseif pay.isOpen == '1' && pay.payType=="1">
                    <a class="btn btn-full" id="zhifubao" href="javascript:;">支付宝支付</a>
                <#elseif pay.isOpen == '1' && pay.payType=="5">
                    <a class="btn btn-full yue"><i></i>预存款支付</a>
                </#if>
        </#list>
            <#if thirdNum==0>
            <#list paylist as payType>
                <#if payType.isOpen == '1' && payType.paymentId==2>
                    <a class="btn btn-full huodao" href="javascript:;"><i></i>货到支付</a>
                <#break>
                </#if>
            </#list>
            </#if>
            <#--<a class="btn btn-full-grey bank" href="javascript:;"><i></i>银行转账</a>-->
        </div>
    </div>
</div>
    </form>
<input type="hidden" id="orderId"/>
<input value="${basePath}" type="hidden" id="basePath"/>
<script src="${basePath}/js/jquery-1.10.1.js"></script>
<script src="${basePath}/js/dialog-min.js"></script>
<script src="${basePath}/js/pageAction.js"></script>
<script type="text/javascript" src="${basePath}/js/shoppingcart/jsOperation.js"></script>
<script src="${basePath}/js/customer/wxforward.js"></script>
<script>
    //判断是否是微信打开
    function isWeiXin(){
        var ua = window.navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i) == 'micromessenger'){
            return true;
        }else{
            return false;
        }
    }

    var jf=$("#jfen").val();
    var pointset=$("#pointSet").val();
    var bossPrice=Subtr($("#bossPrice").val(),$("#bosscouponPrice").val());
    if(bossPrice.indexOf(".") != -1){
        bossPrice = bossPrice.substring(0,bossPrice.indexOf("."));
    }
    function caljifen(obj) {
        if (parseInt($("#isOpen").val()) == 1) {
            if (!isNaN($(obj).val())) {
                if ($(obj).val() == '') {
                    $("#jifenError").text("注：请输入需要兑换的积分!");
                    return;
                }
                if ($(obj).val() > 0) {
                    if (Subtr($("#maxjf").val(), $(obj).val()) < 0) {
                        $(obj).val('');
                        $("#jifenError").text("注：亲您没有那么多可用积分！");
                    } else if ($(obj).val() % 10 != 0) {
                        $("#jifenError").text("注：兑换的积分必须为10的倍数!");
                    } else {
                        $("#jifenError").text("");
                    }
                } else {
                    $(obj).val('');
                    $("#jifenError").text("注：使用积分不允许输入负数！");
                }
            } else {
                $(obj).val('');
                $("#jifenError").text("注：请输入数字!");
            }
        }else{
            $(obj).val('');
            $("#jifenError").text("注：不允许使用积分兑换金额,如有疑问请联系客服!");
        }

            var price = accDiv(accMul(accAddInt($(obj).val(), 0), pointset), 10);
            var myprice = Subtr($("#payPrice").val(), price);
            if (myprice <= 0) {
                $("#sPrice").html("¥" + "0.00");
                $("#yPrice").html("-¥" + accAdd(accAdd(price, myprice), $("#youhui").val()))
            } else {
                $("#sPrice").html("¥" + Subtr($("#payPrice").val(), price));
                $("#yPrice").html("-¥" + accAdd(price, $("#youhui").val()))
            }

            $("#jfPrice").val(price);
            $("#jifenPrice").html(price);
        }


    function checkjifen(obj){
        if(!isNaN($(obj).val())) {
            if($(obj).val() > 0){
                if (Subtr($("#maxjf").val(), $(obj).val()) < 0) {
                    $(obj).val('');
                    $("#jifenError").text("注：亲您没有那么多可用积分！");
                } else if ($(obj).val() % 10 != 0) {
                    $(obj).val('');
                    $("#jifenError").text("注：兑换的积分必须为10的倍数!");
                }else if(parseInt($("#isOpen").val()) == 0){
                    $(obj).val('');
                    $("#jifenError").text("注：不允许使用积分兑换金额,如有疑问请联系客服!");
                }else {
                    $("#jifenError").text("");
                }
            }else{
                $(obj).val('');
                $("#jifenError").text("注：使用积分不允许输入负数！");
            }
        }else{
            $(obj).val('');
            $("#jifenError").text("注：请输入数字!");
        }

        var price=  accDiv(accMul(accAddInt($(obj).val(),0),pointset),10);
        var myprice = Subtr($("#payPrice").val(),price);
        if(myprice<=0){
            $("#sPrice").html("¥"+"0.00");
            $("#yPrice").html("-¥"+accAdd(accAdd(price,myprice),$("#youhui").val()))
        }else{
            $("#sPrice").html("¥"+Subtr($("#payPrice").val(),price));
            $("#yPrice").html("-¥"+accAdd(price,$("#youhui").val()))
        }

        $("#jfPrice").val(price);
        $("#jifenPrice").html(price);
    }
    $(function(){
        if(!isWeiXin()){
            $(".weixin").hide();
            $("#zhifubao").show();
        }
        if(isWeiXin()){
            $(".weixin").show();
            $("#zhifubao").hide();
        }
        /* 使用积分 */
        $('.use-points').click(function(){
            $(this).toggleClass('on');
            $('.use-points-form').slideToggle('fast');
            $("#jifenError").text("");
            if($(".use-points").parent().find(".on").length==0){
                $("#jifen").val('');
                checkjifen($("#jifen"));
            }
        });

        if(Subtr(accDiv(accMul(bossPrice,10),pointset),jf)<0){
            $("#jfSmall").html("可用"+parseInt(accDiv(accMul(bossPrice,10),pointset))+"积分，抵扣¥"+accDiv(accMul(parseInt(accDiv(accMul(bossPrice,10),pointset)),pointset),10));
            $("#maxjf").val(parseInt(accDiv(accMul(bossPrice,10),pointset)));
        }else{
            $("#jfSmall").html("可用"+parseInt(accAddInt(jf,0))+"积分，抵扣¥"+accDiv(accMul(jf,pointset),10));
            $("#maxjf").val(parseInt(accAddInt(jf,0)));
        }

        /* 微信支付 */
        $('.weixin').click(function(){
            if($(".weixin").hasClass("disabled")){
                return;
            };

            if (typeof($("#jifenonjudeg").attr("on")) == "undefined") {
                var judgejifen = $(".judge").val();
                if(parseInt($("#isOpen").val()) == 1) {
                    if(parseInt($("#isThirdSku").val()) > 0) {
                        if (judgejifen == '') {
                            $("#jifenError").text("注：请输入需要兑换的积分!");
                            return;
                        }

                        if (judgejifen % 10 != 0) {
                            $(".judge").val('');
                            $("#jifenPrice").html('0.00');
                            $("#jifenError").text("注：兑换的积分必须为10的倍数!");
                            return;
                        }
                    }
                }
            }

            if($("#addressId").val()==''){
                setTimeout(function(){
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>请选择收货地址！</h3></div></div>');
                },10);
                setTimeout(function(){
                    $(".tip-box").hide();
                },1000)
                return;
            }
            var orderpayPrice=$("#payPrice").val();
            if(Number(orderpayPrice) < 0){
                setTimeout(function(){
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>订单金额有误,无法提交！</h3></div></div>');
                },10);
                setTimeout(function(){
                    $(".tip-box").hide();
                },1000)
                return ;
            }
            var payPrice=$("#sPrice").html();
            var weixinPay = dialog({
                width : 260,
                content : '<p class="tc">您需要为该笔订单支付</p><p class="tc"><span class="payPrice text-them">'+payPrice+'</span></p> ',
                okValue : '继续支付',
                cancelValue : '返回',
                ok : function(){
                    $(".huodao").hide();
                    $(".weixin").addClass("disabled");
                    $("#zhifubao").addClass("disabled");
                    //确认离开，跳转到下一页面
                    $("#chPay").val("1");
                    $.ajax({
                        type: "POST",
                        url: "${basePath}/getwxparam.htm",
                        data:$("#subForm").serialize(),
                        success: function(msg){
                            if(msg==''){
                                location.href="${basePath}/customer/myorder.html";
                            }
                            $("#orderId").val(msg.orderId);
                            callpay(msg.appId,msg.timeStamp,msg.nonceStr,msg.package,msg.sign);
                        }
                    });
                    return true;
                },
                cancel : function(){
                    //停留在当前页面
                    return true;
                }
            });
            weixinPay.showModal();
        });

        /* 支付宝支付 */
        $('#zhifubao').click(function(){
            if($("#zhifubao").hasClass("disabled")){
                return;
            };
            if (typeof($("#jifenonjudeg").attr("on")) == "undefined") {
                var judgejifen = $(".judge").val();

                if(parseInt($("#isOpen").val()) == 1){
                    if(parseInt($("#isThirdSku").val()) > 0){
                        if (judgejifen == '') {
                            $("#jifenError").text("注：请输入需要兑换的积分!");
                            return;
                        }

                        if (judgejifen % 10 != 0) {
                            $(".judge").val('');
                            $("#jifenPrice").html('0.00');
                            $("#jifenError").text("注：兑换的积分必须为10的倍数!");
                            return;
                        }
                    }
                }
            }

            /**
             * 判断选择使用的优惠券是否能使用
             *
             * */
            var flagss = true;
            if($("input[name='codeNo']").val() ==null ){

            }else{
                 $.ajax({
                    url:"${basePath}/queryStatus.htm?couponNo="+$("input[name='codeNo']").val(),
                    type:"post",
                     async:false,
                    success:function(data){
                        if(data != 0){
                            flagss = false;
                        }
                    }
                });
            }
            if(flagss==false){
                setTimeout(function(){
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>使用的优惠券无效！</h3></div></div>');
                },10);
                setTimeout(function(){
                    $(".tip-box").hide();
                },1000)
                return;
            }
            if($("#addressId").val()==''){
                setTimeout(function(){
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>请选择收货地址！</h3></div></div>');
                },10);
                setTimeout(function(){
                    $(".tip-box").hide();
                },1000)
                return;
            }
            var orderpayPrice=$("#payPrice").val();
            if(Number(orderpayPrice) < 0){
                setTimeout(function(){
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>订单金额有误,无法提交！</h3></div></div>');
                },10);
                setTimeout(function(){
                    $(".tip-box").hide();
                },1000)
                return ;
            }
            var payPrice=$("#sPrice").html();
            var zhifubaoPay = dialog({
                width : 260,
                content : '<p class="tc">您需要为该笔订单支付</p><p class="tc"><span class="payPrice text-them">'+payPrice+'</span></p> ',
                okValue : '继续支付',
                cancelValue : '返回',
                ok : function(){
                    //确认离开，跳转到下一页面
                    $(".weixin").addClass("disabled");
                    $("#zhifubao").addClass("disabled");
                    $(".huodao").addClass("disabled");
                    //$("#chPay").val("3");
                    $("#subForm").attr("action","${basePath}/payorder.htm");
                    $("#subForm").submit();
                    //return true;
                },
                cancel : function(){
                    //停留在当前页面
                    return true;
                }
            });
            zhifubaoPay.showModal();
        });

        /* 货到付款 */
        $('.huodao').click(function(){
            if($(".huodao").hasClass("disabled")){
                return;
            }

            if (typeof($("#jifenonjudeg").attr("on")) == "undefined") {
                var judgejifen = $(".judge").val();

                if (judgejifen == '') {
                    $("#jifenError").text("注：请输入需要兑换的积分!");
                    return;
                }

                if (judgejifen % 10 != 0) {
                    $(".judge").val('');
                    $("#jifenPrice").html('0.00');
                    $("#jifenError").text("注：兑换的积分必须为10的倍数!");
                    return;
                }
            }

            if($("#addressId").val()==''){
                setTimeout(function(){
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>请选择收货地址！</h3></div></div>');
                },10);
                setTimeout(function(){
                    $(".tip-box").hide();
                },1000)
                return;
            }

            var orderpayPrice=$("#payPrice").val();
            if(Number(orderpayPrice) < 0){
                setTimeout(function(){
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>订单金额有误,无法提交！</h3></div></div>');
                },10);
                setTimeout(function(){
                    $(".tip-box").hide();
                },1000)
                return ;
            }
            var yuePay = dialog({
                width : 260,
                content : '<p class="tc">确定要货到付款！</p>',
                okValue : '确定',
                cancelValue : '取消',
                ok : function(){
                    $(".weixin").addClass("disabled");
                    $("#zhifubao").addClass("disabled");
                    $(".huodao").addClass("disabled");
                    //确认离开，跳转到下一页面
                    $("#chPay").val("2");
                    $("#subForm").attr("action","${basePath}/submitorder.htm");
                    $("#subForm").submit();
                    return false;
                },
                cancel : function(){
                    //停留在当前页面
                    return true;
                }
            });
            yuePay.showModal();
        });
        /* 余额支付 */
        $('.yue').click(function(){
            var price = $("#sPrice").html();
            price = price.substring(1);
            $.ajax({
               url:'${basePath}/checkDepositPay.htm?type=0&orderPrice='+price,
               type:'get',
               success: function(result){
                   var return_code = result.return_code;
                   if(return_code == 'success'){
                       var yuePay = dialog({
                           id: 'deposit-id',
                           width : 260,
                           content : '<form id="depositpay"> <div style="padding:0 5px"><p class="tc">请输入支付密码</p><p class="tc"><input onkeyup="validDeposit(this);" name="payPassword" type="password" class="p10" id="payPassword"></p><div class="error_tips" style="display: none"><a href="#" class="fr">忘记密码？</a><span>密码错误</span></div></div><div class="dia-buttons"><a onclick="closeDialog();" class="cancel">取消</a><a id="depositok" onclick="depositPay(this);" class="ok disabled" style="margin-left: 4%;">确定</a></div></div>'
//                content : '<div style="padding:0 13px"><p class="tc">支付密码</p><p class="tc"><input onkeyup="validDeposit(this);" type="text" class="p10" placeholder="可用预存款￥1000.00"></p></div> ',
                           /*okValue : '确定',
                           cancelValue : '取消',
                           button:[
                               {
                                   id:'cancel',
                                   value: '取消',
                                   callback: function(){
                                       return true;
                                   }
                               },
                               {
                                   id: 'button-submit',
                                   value: '提交',
                                   disabled: true,
                                   callback: function(){
                                       var password;



                                   }
                               }
                           ]*/

                       });
                       yuePay.showModal();
                   }else if(return_code == 'fail'){
                       var fail_code = result.fail_code;
                       var return_msg = result.return_msg;
                       if(fail_code == 'pass_fail'){
                           var passFail = dialog({
                               id: 'deposit-pass',
                               width : 260,
                               content : '<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a onclick="closeDialog();" class="cancel">其他方式支付</a><a href="${basePath}/accountsafe.html" id="depositok" class="ok" style="margin-left: 4%;">设置支付密码</a></div></div>'
                           });
                           passFail.reset();
                           passFail.showModal();
                       }else if(fail_code == 'frozen_fail'){
                           var frozenFail = dialog({
                               id: 'deposit-frozen',
                               width : 260,
                               content : '<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a onclick="closeDialog();" id="depositok" style="margin: 0;width: 100%" class="ok">确认</a></div></div>'
                           });
                           frozenFail.reset();
                           frozenFail.showModal();
                       }else if(fail_code == 'balance_fail'){
                           var balanceFail = dialog({
                               id: 'deposit-balance',
                               width : 260,
                               content : '<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a onclick="closeDialog();" id="depositok" style="margin: 0;width: 100%" class="ok">确认</a></div></div>'
                           });
                           balanceFail.reset();
                           balanceFail.showModal();
                       }

                   }
               }
            });
        });

        /* 转账支付 */
        $('.bank').click(function(){
            var bankPay = dialog({
                width : 260,
                content : '<p class="tc">订单提交成功</p><div class="bank_info"><span>请按照一下收款方信息，及时到银行汇款</span><ul><li>收款人名称：天天买</li><li>开户银行：农业银行</li><li>收款人账号：2375298375923758923</li><li>汇款识别码：132413413246</li></ul></div> ',
                okValue : '确定',
                ok : function(){
                    //确认离开，跳转到下一页面
                    return true;
                }
            });
            bankPay.showModal();
        });

    });
    /* 显示隐藏的商品 */
    function showGoods(obj){
        var storeId=$(obj).find("input").val();
        if(storeId=='0'){
            $('.confirm-pro-item').show();
        }else{
            $('.goods'+storeId).show();
        }
        $(obj).remove();
    }

    //
    function validDeposit(obj){
        var pass = $(obj).val();
        if($(obj).val() != ''){
            $(".ok").removeClass("disabled");
//                depositdialog.reset();
        }else{
            $(".ok").addClass("disabled");
        }


    }


    function depositPay(obj){
        if($(obj).hasClass('disabled')){
            return false;
        }
        $("#subPassword").val($('#payPassword').val());
        $("#chPay").val("5");
        $.ajax({
            url:'${basePath}/order/depositpay.html',
            type:'post',
            data: $("#subForm").serialize(),
            success: function(result){
                if(result != null && result !=''){
                    if(result.return_code == "success"){
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>支付成功！</h3></div></div>');
                        setTimeout(function(){
                            $('.tip-box').remove();
                            location.href="${basePath}/customer/myorder.html";
                        },1000);
                    }else if(result.return_code == "fail"){
                        var return_msg = result.return_msg;
                        var fail_code = result.fail_code;
                        if(fail_code == 'pass_fail'){
                            dialog.getCurrent().content('<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a class="cancel">选择其他支付方式</a><a id="depositok" onclick="depositPay(this);" class="ok" style="margin-left: 4%;">设置支付密码</a></div></div>');
                        }else if(fail_code == 'frozen_fail'){
                            dialog.getCurrent().content('<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a id="depositok" onclick="closeDialog();" style="margin: 0;width: 100%" class="ok">确认</a></div></div>');
                        }else{
                            dialog.getCurrent().content('<form id="depositpay"><div style="padding:0 5px"><p class="tc">支付密码</p><p class="tc"><input onkeyup="validDeposit(this);" type="password" name="payPassword" id="payPassword" class="p10"></p><div class="error_tips"><a href="${basePath}/member/topaypassword.html" class="fr">忘记密码？</a><span>'+ return_msg +'</span></div></div><div class="dia-buttons"><a onclick="closeDialog();" class="cancel">取消</a><a id="depositok" onclick="depositPay(this);" class="ok disabled" style="margin-left: 4%;">确定</a></div></div>');
                        }

//                    this.content('<div style="padding:0 20px"><p class="tc">支付密码</p><p class="tc"><input type="text" class="p10"></p><div class="error_tips"><a href="#" class="fr">忘记密码？</a><span>密码错误</span></div></div> ');
                        dialog.getCurrent().reset();
                        return false;
                    }
                }else{
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>支付异常！</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }
            }
        });
    }

    function closeDialog(){
        dialog.getCurrent().close();
    }

</script>

<script type="text/javascript">
    function callpay(appId,timeStamp,nonceStr,package,sign){
        WeixinJSBridge.invoke('getBrandWCPayRequest',{
            "appId" : appId,
            "timeStamp" : timeStamp,
            "nonceStr" : nonceStr,
            "package" : package,
            "signType" : "MD5",
            "paySign" : sign
        },function(res){
            WeixinJSBridge.log(res.err_msg);
            if(res.err_msg == "get_brand_wcpay_request: ok"){
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>支付成功！</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                    location.href="${basePath}/paysucccesswx.htm?orderId="+$("#orderId").val();
                },1000);

            }else if(res.err_msg == "get_brand_wcpay_request: cancel"){
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>用户取消支付！</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                    location.href="${basePath}/customer/detail-"+$("#orderId").val()+".html";
                },1000);
            }else{
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>支付失败！</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                    location.href="${basePath}/customer/detail-"+$("#orderId").val()+".html";
                },1000);
            }
        })
    }
</script>
<script>
    //处理自提点js相关函数
    $(".send-way .list-value a").click(function(){
        $(".send-way .list-value a").removeClass("selected");
        var $this = $(this);
        $this.addClass("selected");
        //selfPick,上门字体按钮
        var id = $this.attr("id");
        if(id=="selfPick"){//点击的是上门自提按钮
            //自提点信息展示
            //btn btn-full-grey huodao货到付款不可点击
            $(".btn.btn-full-grey.huodao").hide();
            $("#deliverylist").show();
            $("#typeId").val("1");//自提
            var dp = $("#deliveryPointId").val();
            if(dp){

            }else{
                //没有dp时候，第一个放到隐藏域
                var dpid = $("#deliverylist span").attr("dpid");
                var dom  = '<input name="deliveryPointId" type="hidden" id="deliveryPointId" value="'+dpid+'">';
                $("#subForm").prepend(dom);
            }
        }else{
            $(".btn.btn-full-grey.huodao").show();
            $("#deliverylist").hide();
            $("#typeId").val("0");//快递
            $("#deliveryPointId").remove();
        }
    });
    //获取自提点信息
    var deliveryPoint = '${(deliveryMap.choseDeliveryPoint.deliveryPointId)!''}';
    if(deliveryPoint){//有自提点信息的时候
        $(".send-way .list-value a").removeClass("selected");
        $("#selfPick").addClass("selected");//自提点选中
        $(".btn.btn-full-grey.huodao").hide();//货到付款隐藏
    }else{
        //alert();
    }
    //点击自提点时
    $("#deliverylist a").click(function(){
        var dpid = $(this).find("span").attr("dpid");
        if(!dpid){return;}
        window.location.href = "${basePath}/order/deliverylist.html?dpId="+ dpid;
    });
</script>
</body>
</html>