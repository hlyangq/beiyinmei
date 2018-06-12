<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
<#assign basePath=request.contextPath>
    <title>购物车</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
<#if (sys.bsetName)??>
    <title>购物车-${(sys.bsetName)!''}</title>
    <input type="hidden" id="bsetName" value="${(sys.bsetName)!''}">
    <input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
<#else>
    <title>购物车-${(seo.mete)!''}</title>
    <input type="hidden" id="bsetName" value="${(seo.mete)!''}">
    <input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
</#if>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css">
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
    <script src="${basePath}/js/idangerous.swiper.js"></script>

</head>
<body>
<div class="content-cart">
    <div class="cart-head">
        <h1>我的购物车</h1>
    <#if customerId??>
        <#if shopMap.shoplist??&&shopMap.shoplist?size!=0>
            <a href="javascript:;" class="cart-edit">编辑</a>
        </#if>
    </#if>
    </div>
    <input type="hidden" value="${customerId!''}" id="customerId">
    <input type="hidden" value="${basePath!''}" id="basePath">
    <input type="hidden" value="" id="myzongji">
<#if customerId??>
<#else>
    <div class="list-item notlogin">
        <h3><a class="btn" href="${basePath}/loginm.html?url=${basePath}/myshoppingmcart.html">登&nbsp;录</a>登录后可同步购物车里的商品哦~
        </h3>
    </div>
</#if>
<#assign goodsNum=0/>
<#assign sumPrice=0/>
<#assign youhui=0/>
<#if shopMap.shoplist??&&shopMap.shoplist?size!=0>
    <ul class="cart-list">
        <#if shopMap.marketinglist??&&shopMap.marketinglist?size!=0>
            <#list shopMap.marketinglist as mar>
                <#assign yhprice=0/>
                    <#assign onePrice=0/><!--参加同种促销商品总价格-->
                    <#assign zjPrice=0/><!--直降的优惠-->
                    <#assign marNum=0/><!--促销的商品个数-->
                <#if shopMap.shoplist??&&shopMap.shoplist?size!=0>
                    <#list shopMap.shoplist as cars>
                        <#if cars.goodsDetailBean??>
                            <#if cars.marketingActivityId??&&cars.marketingActivityId!=0&&cars.marketingActivityId==mar.marketingId>
                                <#assign marNum=1>
                            </#if>
                        </#if>
                    </#list>
                </#if>

                <#list shopMap.shoplist as shop>
                    <#if shop.goodsDetailBean??>
                        <#if shop.marketingActivityId??&&shop.marketingList?size!=0&&shop.marketingActivityId!=0>
                            <#if shop.marketingActivityId==mar.marketingId>
                                    <#assign marNum2=0/><!--促销的商品个数-->
                                <#list shop.marketingList as mar>
                                    <#if  mar.codexType=='5'|| mar.codexType=='1'|| mar.codexType=='8' || mar.codexType=='11' || mar.codexType=='6'>
                                        <#assign marNum2=marNum2?number+1/>
                                    </#if>
                                </#list>
                                <li <#if marNum2==0> style="display:none;"</#if>>
                                    <div class="cart-promotions list-item">
                                        <h3>共有${marNum2}项促销优惠</h3>
                                        <i class="arrow-right"></i>
                                    </div>
                                </li>
                            <li <#if marNum2==0> style="display:none;"</#if>>
                                <ul class="fav-list c-discount" style="display:none;" attr-codextype="${mar.codexType}">
                                    <#if mar.codexType=='1'>
                                        <li>
                                            <a href="#" class="change_discount">

                                                <span class="fav gift">降</span>
                                                <span>  直降${mar.priceOffMarketing.offValue}元;</span>
                                                <input type="hidden" value="${mar.priceOffMarketing.offValue}"
                                                       class="zhijiang_offValue"/>
                                            </a>
                                        </li>
                                    </#if>

                                    <#if mar.codexType=='11'>
                                        <li>
                                            <a href="#" class="change_discount">

                                                <span class="fav gift">抢</span>
                                                <span>  ${10*mar.rushs[0].rushDiscount?number}折抢购</span>
                                                <input type="hidden"
                                                       value="${((shop.goodsDetailBean.productVo.goodsInfoPreferPrice?number-(mar.rushs[0].rushDiscount?number*shop.goodsDetailBean.productVo.goodsInfoPreferPrice?number))?string("0.00"))!""}"
                                                       class="qianggou_offValue"/>
                                            </a>
                                        </li>
                                    </#if>

                                    <#if mar.codexType=='5'>
                                        <li>
                                            <a href="#" class="change_discount">

                                                <span class="fav gift">减</span>
                                                <#list mar.fullbuyReduceMarketings as fr>
                                                    <#if fr_index==0>
                                                        <span>  满 ${fr.fullPrice}减${fr.reducePrice}元 &nbsp;</span>
                                                        <input type="hidden" value="${fr.fullPrice},${fr.reducePrice}"
                                                               class="manjian_reducePrice"/>
                                                    </#if>
                                                </#list>
                                            </a>
                                        </li>
                                        <#list mar.fullbuyReduceMarketings as fr>
                                            <#if fr_index!=0>
                                                <a href="#" class="change_discount">

                                                    <li>
                                                        <span> 满 ${fr.fullPrice}减${fr.reducePrice}元 &nbsp;</span>
                                                        <input type="hidden" value="${fr.fullPrice},${fr.reducePrice}"
                                                               class="manjian_reducePrice"/>
                                                    </li>
                                                </a>
                                            </#if>
                                        </#list>
                                    </#if>
                                    <#if mar.codexType=='8'>
                                        <li>
                                            <a href="#" class="change_discount">

                                                <span class="fav gift">折</span>
                                                <#list mar.fullbuyDiscountMarketings as mz>
                                                    <#if mz_index==0>
                                                        <span> 满 ${mz.fullPrice}打 ${mz.fullbuyDiscount*10}折 &nbsp;</span>
                                                        <input type="hidden"
                                                               value="${mz.fullPrice},${mz.fullbuyDiscount}"
                                                               class="manzhe_fullbuyDiscount"/>
                                                    </#if>
                                                </#list>
                                            </a>
                                        </li>
                                        <#list mar.fullbuyDiscountMarketings as mz>
                                            <#if mz_index!=0>
                                                <a href="#" class="change_discount">
                                                    <li>
                                                        <span> 满 ${mz.fullPrice}打 ${mz.fullbuyDiscount*10}折 &nbsp;</span>
                                                        <input type="hidden"
                                                               value="${mz.fullPrice},${mz.fullbuyDiscount}"
                                                               class="manzhe_fullbuyDiscount"/>
                                                    </li>
                                                </a>
                                            </#if>
                                        </#list>
                                    </#if>
                                    <#if mar.codexType=='6'>
                                        <li>
                                            <a href="#" class="select_gift">

                                                <span class="fav gift">满赠</span>
                                                <#list mar.fullbuyPresentMarketings as fr>
                                                    <#if fr_index==0>
                                                        <#if fr.presentType == '0'>
                                                            <span>  满 ${fr.fullPrice}元送赠品 &nbsp;</span>
                                                        <#elseif fr.presentType == '1'>
                                                            <span>  满 ${fr.fullPrice?number}件送赠品 &nbsp;</span>
                                                        </#if>
                                                        <input type="hidden"
                                                               value="${fr.fullPrice},${mar.marketingId},${fr.fullbuyPresentMarketingId},${fr.presentMode},${fr.presentType}"
                                                               class="fullbuy_present"/>
                                                    </#if>
                                                </#list>
                                            </a>
                                        </li>
                                        <#list mar.fullbuyPresentMarketings as fr>
                                            <#if fr_index!=0>
                                                <a href="#" class="select_gift">

                                                    <li>
                                                        <#if fr.presentType == '0'>
                                                            <span>  满 ${fr.fullPrice}元送赠品 &nbsp;</span>
                                                        <#elseif fr.presentType == '1'>
                                                            <span>  满 ${fr.fullPrice?number}件送赠品 &nbsp;</span>
                                                        </#if>
                                                        <input type="hidden"
                                                               value="${fr.fullPrice},${mar.marketingId},${fr.fullbuyPresentMarketingId},${fr.presentMode},${fr.presentType}"
                                                               class="fullbuy_present"/>
                                                    </li>
                                                </a>
                                            </#if>
                                        </#list>
                                    </#if>
                                </ul>
                                <#if mar.codexType=='1'>
                                    <#assign zjPrice=zjPrice+mar.priceOffMarketing.offValue?number*shop.goodsNum?number/>
                                </#if>
                                <div class="list-item cart-pro-item">
                                    <input type="hidden" class="goodInfoId"
                                           value="${shop.goodsDetailBean.productVo.goodsInfoId}"/>
                                    <input type="hidden" class="shoppingCartId" value="${shop.shoppingCartId}"/>
                                    <input type="hidden" class="distinctId" value="${shop.distinctId!''}"/>
                                    <#if (shop.goodsDetailBean.productVo.goodsInfoStock?number-shop.goodsNum?number<0)||shop.goodsDetailBean.productVo.goodsInfoAdded=='0'>
                                        <input type="hidden" value="1" class="noexit"/>
                                    </#if>
                                    <i class="select-box selected" onclick="checkone(this)"></i>

                                    <div class="propic">
                                        <a href="${basePath}/item/${shop.goodsDetailBean.productVo.goodsInfoId}.html"
                                           title="${shop.goodsDetailBean.productVo.productName}">
                                            <img src="${shop.goodsDetailBean.productVo.goodsInfoImgId}" alt="产品图">
                                            <#if (shop.goodsDetailBean.productVo.goodsInfoStock?number-shop.goodsNum?number<0)>
                                                <span class="sell-out">无货</span>
                                            <#elseif shop.goodsDetailBean.productVo.goodsInfoAdded=='0'>
                                                <span class="pull-down">下架</span>
                                            </#if>
                                        </a>
                                    </div>
                                    <div class="prodesc">
                                        <h3 class="title">
                                            <a href="${basePath}/item/${shop.goodsDetailBean.productVo.goodsInfoId}.html"
                                               title="${shop.goodsDetailBean.productVo.productName}">
                                            ${shop.goodsDetailBean.productVo.productName!''}
                                            </a>
                                        </h3>
                                        <p class="price">
                                            ¥&nbsp;<span>${shop.goodsDetailBean.productVo.goodsInfoPreferPrice?string('0.00')}</span>
                                        </p>
                                        <input type="hidden" class="goodsPrice"
                                               value="${shop.goodsDetailBean.productVo.goodsInfoPreferPrice}"/>
                                        <#if stockinfos ?? >
                                            <#list  stockinfos as stockinfo>
                                                <#if stockinfo.goodsId == shop.goodsDetailBean.productVo.goodsInfoId>
                                                    <div><p class="num" style="color: red">仅剩 ${stockinfo.stockNum}件</p>
                                                    </div>
                                                </#if>
                                            </#list>
                                        </#if>
                                        <div class="buy-num">
                                            <span class="reduce"
                                                  onclick="minus(this,${shop.shoppingCartId});">&#8722</span>
                                            <input type="text" class="goodsNum"
                                                   attr-maxnum="${shop.goodsDetailBean.productVo.goodsInfoStock}"
                                                   onblur="opblur(this,${shop.shoppingCartId});"
                                                   value="${shop.goodsNum}"/>
                                            <span class="add" onclick="plus(this,${shop.shoppingCartId});">&#43</span>
                                        </div>
                                        <#assign marNum=marNum+shop.goodsNum?number/>
                                        <#assign goodsNum=goodsNum+shop.goodsNum?number/>
                                        <#assign onePrice=onePrice+shop.goodsNum?number*shop.goodsDetailBean.productVo.goodsInfoPreferPrice?number/>
                                        <#assign sumPrice=sumPrice+shop.goodsNum?number*shop.goodsDetailBean.productVo.goodsInfoPreferPrice?number/>
                                    </div>
                                <#--<div class="tool-btn">-->
                                <#--<a class="in-collection collection" href="javascript:;" >移入<br>收藏</a>-->
                                <#--<a class="delete" onclick="del(${shop.shoppingCartId},${shop.goodsInfoId})" href="javascript:;"> 删除</a>-->
                                <#--</div>-->
                                </div>
                            <#--<a href="javascript:;" class="change_discount">-->
                            <#--<input type="hidden" value="${shop.shoppingCartId}"/>-->
                            <#--<span>重新选择优惠活动</span>-->
                            <#--<i class="arrow-right"></i>-->
                            <#--</a>-->
                                <div class="fav-list f1 promotions-box${shop.shoppingCartId}" style="display: none;">
                                    <ul class="promotions">
                                        <#list shop.marketingList as mar>
                                            <#if  mar.codexType=='5'|| mar.codexType=='1'|| mar.codexType=='8'||mar.codexType=='11' || mar.codexType=='6'>
                                                <li class="<#if mar.marketingId==shop.marketingActivityId>selected</#if>">
                                                    <a href="javascript:void(0);">
                                                        <span class="fav gift"> <#if mar.codexType=='5'>
                                                            减<#elseif mar.codexType=='1'>降<#elseif mar.codexType=='8'>
                                                            折<#elseif mar.codexType=='11'>抢</#if></span>
                                                    ${mar.marketingName!''}<i class="check-box"></i></a>
                                                    <input type="hidden" class="marketingAId"
                                                           value="${mar.marketingId}"/>
                                                </li>
                                            </#if>
                                        </#list>
                                    </ul>
                                </div>
                            </#if>
                        </#if>

                    </#if>
                </#list>
                <#if  marNum!=0>
                    <input class="oneprice" type="hidden" value="${onePrice}"/>

                    <!--判断满减-->
                    <#assign yhprice=zjPrice>
                    <!--满减-->
                    <#if mar.codexType=='5'>
                        <#list mar.fullbuyReduceMarketings as fr>
                            <#if (onePrice?number>=fr.fullPrice?number)>
                                <#assign yhprice="${fr.reducePrice}">
                            </#if>
                            <!-- 满 ${fr.fullPrice}减${fr.reducePrice}元 &nbsp;-->
                        </#list>
                    </#if>
                    <!--满折-->
                    <#if mar.codexType=='8'>
                        <#list mar.fullbuyDiscountMarketings as mz>
                            <#if (onePrice?number>=mz.fullPrice?number)>
                                <#assign yhprice="${onePrice?number*(1-mz.fullbuyDiscount?number)}">
                            </#if>
                        </#list>
                    </#if>
                    <!--满赠-->
                <#--<#if mar.codexType=='6'>
                    <input  class="youhui" type="hidden" value="${yhprice}"/>
                </#if>-->

                    <input class="youhui" type="hidden" value="${yhprice}"/>
                    <#assign youhui=youhui?number+yhprice?number/>

                    <!--已选赠品信息开始-->
                    <div class="cart_gifts">
                        <#if mar.codexType=='6' && mar.fullbuyPresentMarketing??>
                            <#list mar.fullbuyPresentMarketing.fullbuyPresentScopes as fullbuyPresentScope>
                                <p>
                                    <span>【赠品】</span>
                                    <span class="name">${fullbuyPresentScope.goodsProduct.goodsInfoName}</span>
                                    <span>×${fullbuyPresentScope.scopeNum}</span>
                                    <input type="hidden" value="${fullbuyPresentScope.presentScopeId}">
                                </p>
                                <#if mar.fullbuyPresentMarketing.presentMode == '1'>
                                    <#break>
                                </#if>
                            </#list>
                            <a href="javascript:;" class="change_gift"
                               id="select-gift_${mar.marketingId}_${mar.fullbuyPresentMarketing.fullbuyPresentMarketingId}_${mar.fullbuyPresentMarketing.presentMode}_${mar.fullbuyPresentMarketing.presentType}">修改赠品</a>
                        </#if>
                    </div>
                    <!--已选赠品信息结束-->
                    <!---满赠赠品弹层开始-->
                    <#if mar.fullbuyPresentMarketings??>
                        <#list mar.fullbuyPresentMarketings as fullbuyPresentMarketing>
                            <div class="set_gift"
                                 id="gift_box_${mar.marketingId}_${fullbuyPresentMarketing.fullbuyPresentMarketingId}_${fullbuyPresentMarketing.presentMode}_${fullbuyPresentMarketing.presentType}"
                                <#if mar.fullbuyPresentMarketing??><#if mar.fullbuyPresentMarketing.fullbuyPresentMarketingId != fullbuyPresentMarketing.fullbuyPresentMarketingId>
                                 style="display:none" </#if><#else>style="display:none"</#if>>
                                <div class="minigifts"
                                     id="gifts_${mar.marketingId}_${fullbuyPresentMarketing.fullbuyPresentMarketingId}_${fullbuyPresentMarketing.presentMode}_${fullbuyPresentMarketing.presentType}">
                                    <a href="javascript:;" class="close"></a>
                                    <h4>修改赠品</h4>
                                    <div class="swiper-container"
                                         id="chooseGifts_${mar.marketingId}_${fullbuyPresentMarketing.fullbuyPresentMarketingId}">
                                        <div class="swiper-wrapper">
                                            <#list fullbuyPresentMarketing.fullbuyPresentScopes as fullbuyPresentScope>
                                                <div class="swiper-slide">
                                                    <div class="gift_item gift_${fullbuyPresentScope.presentScopeId}">
                                                        <em><i class="ion-ios-checkmark-empty"></i></em>
                                                        <img alt=""
                                                             src="${fullbuyPresentScope.goodsProduct.goodsInfoImgId}"
                                                             width="110">
                                                        <p class="name">${fullbuyPresentScope.goodsProduct.goodsInfoName}</p>
                                                        <p class="num" scopenum="${fullbuyPresentScope.scopeNum}">
                                                            ×${fullbuyPresentScope.scopeNum}</p>
                                                        <input type="hidden"
                                                               value="${fullbuyPresentScope.presentScopeId}">
                                                    </div>
                                                </div>
                                            </#list>
                                        </div>
                                    </div>
                                    <a href="javascript:;" class="btn  btn-full" onclick="changeGift(this);">确认</a>
                                </div>
                            </div>
                        </#list>
                        <!---满赠赠品弹层开始-->
                    </#if>
                </li>
                </#if>
            </#list>

        </#if>


        <#list shopMap.shoplist as shop>
        <#if shop.fitId??>
            <#assign groupVo = shop.goodsGroupVo>

            <li>
                <input type="hidden" class="shoppingCartId" value="${shop.shoppingCartId}"/>
                <ul class="fav-list" style="display:none;" attr-codextype="-1">
                    <#--<li class="selected">-->
                        <#--<input type="hidden" class="marketingAId" value="${groupVo.groupId}">-->
                    <#--</li>-->
                </ul>

                <div class="cart-group-item">
                    <div class="group-name">
                        <i class="select-box selected" onclick="checkone(this)"></i>
                        <span class="label-line">套装</span>
                        <span>${groupVo.groupName}</span>
                    </div>
                </div>
                <#list groupVo.productList as product>
                    <div class="list-item cart-pro-item">
                        <div class="propic">
                            <a href="${basePath}/item/${product.productDetail.goodsInfoId}.html">
                                <img src="${product.productDetail.goodsInfoImgId}"
                                     alt="${product.productDetail.goodsInfoName}">
                            </a>
                        </div>
                        <div class="prodesc">
                            <a href="${basePath}/item/${product.productDetail.goodsInfoId}.html">
                                <h3 class="title">${product.productDetail.goodsInfoName}</h3>
                                <p class="price">¥&nbsp;
                                    <span class="num">${product.productDetail.goodsInfoPreferPrice?string('0.00')}</span>
                                </p>
                                <p class="amount">${product.productNum}件/套</p>
                            </a>
                        </div>
                    </div>
                </#list>

                <div class="group-settle">
                    <div class="buy-num">
                        <span class="reduce" onclick="minus(this,${shop.shoppingCartId});">&#8722</span>
                        <input type="text" class="goodsNum" attr-maxnum="${groupVo.stock}"
                               onblur="opblur(this,${shop.shoppingCartId});"
                               value="${shop.goodsNum}"/>
                        <span class="add" onclick="plus(this,${shop.shoppingCartId});">&#43</span>
                    </div>

                    <p>套装价：<span class="price">￥${groupVo.price?string('0.00')}</span></p>

                    <#assign originPrice = groupVo.price?number + groupVo.groupPreferamount?number/>
                    <#assign youhui=shop.goodsNum?number*groupVo.groupPreferamount?number/>
                    <#assign oneprice=shop.goodsNum?number*originPrice?number/>
                    <#assign sumPrice=sumPrice+oneprice/>

                    <input class="groupPreferamount" type="hidden" value="${groupVo.groupPreferamount}"/>
                    <input class="goodsPrice" type="hidden" value="${originPrice}"/>
                    <input class="youhui" type="hidden" value="${youhui}"/>
                    <input class="oneprice" type="hidden" value="${oneprice}"/>
                </div>
            </li>


        <#else>
            <#if shop.goodsDetailBean??>
                <#assign marketnum=0>
                <#--&lt;#&ndash; 没明白这部分代码什么作用,这部分代码会阻断促销商品，false放过去-->
                <#if shop.marketingActivityId??&&shop.marketingList?size!=0&&shop.marketingActivityId!=0>
                <#else>
                    <li>
                        <div class="list-item cart-pro-item">
                            <input type="hidden" class="goodInfoId"
                                   value="${shop.goodsDetailBean.productVo.goodsInfoId}"/>
                            <input type="hidden" class="shoppingCartId" value="${shop.shoppingCartId}"/>
                            <input type="hidden" class="distinctId" value="${shop.distinctId!''}"/>
                            <#if (shop.goodsDetailBean.productVo.goodsInfoStock?number-shop.goodsNum?number<0)||shop.goodsDetailBean.productVo.goodsInfoAdded=='0'>
                                <input type="hidden" value="1" class="noexit"/>
                            </#if>
                            <i class="select-box selected" onclick="checkone(this)"></i>

                            <div class="propic">
                                <a href="${basePath}/item/${shop.goodsDetailBean.productVo.goodsInfoId}.html"
                                   title="${shop.goodsDetailBean.productVo.productName}">
                                    <img src="${shop.goodsDetailBean.productVo.goodsInfoImgId}" alt="产品图">
                                    <#if (shop.goodsDetailBean.productVo.goodsInfoStock?number-shop.goodsNum?number<0)>
                                        <span class="sell-out">无货</span>
                                    <#elseif shop.goodsDetailBean.productVo.goodsInfoAdded=='0'>
                                        <span class="pull-down">下架</span>
                                    </#if>
                                </a>
                            </div>
                            <div class="prodesc">
                                <h3 class="title">
                                    <a href="${basePath}/item/${shop.goodsDetailBean.productVo.goodsInfoId}.html"
                                       title="${shop.goodsDetailBean.productVo.productName}">
                                    ${shop.goodsDetailBean.productVo.productName!''}
                                    </a>
                                </h3>
                                <p class="price">¥&nbsp;<span
                                        class="num">${shop.goodsDetailBean.productVo.goodsInfoPreferPrice?string('0.00')}</span>
                                </p>
                                <input type="hidden" class="goodsPrice"
                                       value="${shop.goodsDetailBean.productVo.goodsInfoPreferPrice}">
                                <#if stockinfos ?? >
                                    <#list  stockinfos as stockinfo>
                                        <#if stockinfo.goodsId == shop.goodsDetailBean.productVo.goodsInfoId>
                                            <div><p class="num" style="color: red">仅剩 ${stockinfo.stockNum}件</p></div>
                                        </#if>
                                    </#list>
                                </#if>
                                <div class="buy-num">
                                    <span class="reduce" onclick="minus(this,${shop.shoppingCartId});">&#8722</span>
                                    <input type="text" class="goodsNum"
                                           attr-maxnum="${shop.goodsDetailBean.productVo.goodsInfoStock}"
                                           onblur="opblur(this,${shop.shoppingCartId});" value="${shop.goodsNum}"/>
                                    <span class="add" onclick="plus(this,${shop.shoppingCartId});">&#43</span>
                                </div>
                                <#assign goodsNum=goodsNum+shop.goodsNum?number/>
                                <#assign oneprice=shop.goodsNum?number*shop.goodsDetailBean.productVo.goodsInfoPreferPrice?number/>
                                <#assign sumPrice=sumPrice+oneprice/>
                                <input class="oneprice" type="hidden" value="${oneprice}">
                            </div>
                        <#--<div class="tool-btn">-->
                        <#--<a class="in-collection collection" href="javascript:;" >移入<br>收藏</a>-->
                        <#--<a class="delete" href="javascript:;"> 删除</a>-->
                        <#--</div>-->
                        </div>
                    </li>
                </#if>
            </#if>

        </#if>
        </#list>


    </ul>

    <div class="pay-box">
        <div class="select-all">
            <i class="select-box selected" onclick="checkAll()" id="selectAll"></i><label>全选</label>
        </div>
        <div class="cart-money">
            <h4>总计：<span class="zongji">￥<span>${(sumPrice?number-youhui?number)?string('0.00')}</span></span></h4>
            <p>(不含运费，已优惠<span class="fanxian" style="color: #999;">${youhui?string('0.00')}</span>)</p>
        </div>

        <a class="btn cart-pay" onclick="onpay()">结算(<span id="goodsNum">${goodsNum}</span>)</a>
        <div class="goods-ctrl">
            <a href="javascript:;" class="btn btn-grey">移入收藏</a>
            <a href="javascript:;" class="btn btn-them">删除</a>
        </div>
    </div>
<#else>
    <div class="cart-empty">
        <i></i>
        <h3>您的购物车空空如也，快去装满它！</h3>
        <a class="btn btn-full" href="${basePath}/main.html">去逛逛</a>
    </div>
    <div class="cart-recommend">
        <h3 class="recommend-top"><span class="splitline">— — — — </span>猜你喜欢<span class="splitline">— — — — </span>
        </h3>
        <div class="pro-recommend">
            <div class="pro-recommend-body">
                <#if goods??&&goods?size!=0>
                    <#list goods as good>
                        <a href="${basePath}/item/${good.goodsInfoId}.html">
                            <div class="list-item">
                                <div class="propic">
                                    <img src="${good.goodsInfoImgId!''}" alt="产品图">
                                </div>
                                <div class="prodesc">
                                    <h5 class="title">${good.goodsInfoName!''}</h5>
                                    <p class="price">¥&nbsp;<span class="num">${good.goodsInfoPreferPrice!'0.00'}</span>
                                    </p>
                                </div>
                            </div>
                        </a>
                    </#list>
                </#if>
            </div>
        </div>
    </div>
</#if>
</div>
<!--修改优惠-->
<form method="post" action="${basePath}/changeshoppingcartmarts.htm" class="change_shopping">
    <input type="hidden" name="shoppingCartId" class="shopping_cart_id">
    <input type="hidden" name="marketingActivityId" class="marketing_activity_id">
    <input type="hidden" name="marketingId" class="marketing_id">
</form>
<!--修改优惠-->
<form method="post" action="${basePath}/order/suborder.html" class="subForm">

</form>
<#include "../common/smart_menu.ftl"/>
<script src="${basePath}/js/lodash.min.js"></script>
<script src="${basePath}/js/shoppingcart/jsOperation.js"></script>
<script src="${basePath}/js/shoppingcart/shoppingcart.js"></script>
<script>


    $(function () {
        $(".bar-bottom a:eq(2)").addClass("selected");
        /* 显示/隐藏优惠 */
        $('.cart-promotions').click(function () {
            $(this).toggleClass('open');
            $(this).parent().next().find('.c-discount').slideToggle('fast');
        });


        /* 选择赠品 */
        $('.get-gift-btn').click(function () {
            $('body').append('<div class="opacity-bg-4"></div>');
            $('.getgift').slideDown('fast');
        });
        $('.getgift .close').click(function () {
            $('.opacity-bg-4').remove();
            $('.getgift').slideUp('fast');
        });
        /* 删除商品 */
        $('.btn-them').click(function () {
            if ($('.cart-list .select-box.selected').length != 0) {
                var discountBox = dialog({
                    width: 260,
                    title: '删除商品',
                    content: "确定从购物车中删除选择的商品？",
                    okValue: '确定',
                    cancelValue: '取消',
                    ok: function () {
                        $('.cart-list .select-box.selected').each(function () {
                            var obj = $(this).parents("li");
                            var goodsInfoId = $(obj).find(".goodInfoId").val() || 0;
                            var shoppingCartId = $(obj).find(".shoppingCartId").val() || 0;
                            $.post("${basePath}/delshoppingcartbyid/" + shoppingCartId + "-" + goodsInfoId, function (data) {
                            });
                        })
                        setTimeout(function () {
                            $('.tip-box').remove();
                            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>删除商品成功！</h3></div></div>');
                        }, 10);
                        setTimeout(function () {
                            $(".tip-box").hide();
                            location.reload();
                        }, 1000)
                        return true;
                    },
                    cancel: function () {

                        return true;
                    }
                });
                discountBox.showModal();
            } else {
                setTimeout(function () {
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>请选择商品！</h3></div></div>');
                }, 10);
                setTimeout(function () {
                    $(".tip-box").hide();
                }, 1000)
            }

        });

        /* 选择优惠 */
        $('.change_discount').click(function () {
            var sid = $(this).parent().parents("li").find(".shoppingCartId").val();
            var discountBox = dialog({
                fixed: true,
                width: 260,
                title: '选择一个优惠活动',
                content: $('.promotions-box' + sid),
                okValue: '确定',
                cancelValue: '取消',
                ok: function () {
                    var newMarId = $('.promotions-box' + sid).find(".selected").find(".marketingAId").val();
                    if (newMarId != '') {
                        $(".marketing_activity_id").val(newMarId)
                        $(".shopping_cart_id").val(sid);
                        $(".change_shopping").submit();
                    }
                    return true;
                },
                cancel: function () {

                    return true;
                }
            });
            discountBox.showModal();
        });
        /* 点击编辑显示编辑按钮 */
        var cartEditable = false;
        $('.cart-edit').click(function () {
            if (cartEditable) {
                $('.pay-box').removeClass('editable');
                $(this).text('编辑');
                $(".select-all .select-box").removeClass("selected");
                cartEditable = !cartEditable;
            }
            else {
                $('.pay-box').addClass('editable');
                $(this).text('确定');
//                $(".select-all").addClass("selected");
                $(".select-all .select-box").addClass("selected");
                cartEditable = !cartEditable;
            }
            checkAll();
        });


        /**
         * 收藏商品
         * */
        $(".btn-grey").click(function () {
            if ($(".list-item .selected").length != 0) {


                var discountBox = dialog({
                    width: 260,
                    title: '商品移入收藏',
                    content: "移入收藏将从购物车中删除,确定移入收藏？",
                    okValue: '确定',
                    cancelValue: '取消',
                    ok: function () {
                        $(".list-item .selected").each(function () {
                            var districtId = $(this).parents(".list-item").find(".distinctId").val();
                            var goodsprice = $(this).parents(".list-item").find(".goodsPrice").val();
                            var productId = $(this).parents(".list-item").find(".goodInfoId").val();
                            var _this = $(this);
                            $.post("${basePath}/addcollection.htm", {
                                productId: productId,
                                districtId: districtId,
                                goodsprice: goodsprice
                            }, function (data) {
                                if (data == "-2") {
                                    //返回结果为2代表用户未登录，跳转到登录页面4444
                                    location.href = "../loginm.html?url=/customercenter.html";
                                } else if (data == "1") {
                                    // 返回购物车页面
                                    location.href = "${basePath}/myshoppingmcart.html";
                                } else {
                                    setTimeout(function () {
                                        $('.tip-box').remove();
                                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>系统异常请重试！</h3></div></div>');
                                    }, 10);
                                    setTimeout(function () {
                                        $(".tip-box").hide();
                                    }, 1000)
                                }
                            });
                        })
                        return true;
                    },
                    cancel: function () {

                        return true;
                    }
                });
                discountBox.showModal();
            } else {
                setTimeout(function () {
                    $('.tip-box').remove();
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>请选择商品！</h3></div></div>');
                }, 10);
                setTimeout(function () {
                    $(".tip-box").hide();
                }, 1000)
            }

        });

        /* 选择赠品 */
        $('.select_gift').click(function () {
            var sid = $(this).parent().parents("li").find(".shoppingCartId").val();
            var discountBox = dialog({
                fixed: true,
                width: 260,
                title: '选择一个优惠活动',
                content: $('.promotions-box' + sid),
                okValue: '确定',
                cancelValue: '取消',
                ok: function () {
                    var newMarId = $('.promotions-box' + sid).find(".selected").find(".marketingAId").val();
                    if (newMarId != '') {
                        $(".marketing_activity_id").val(newMarId)
                        $(".shopping_cart_id").val(sid);
                        $(".change_shopping").submit();
                    }
                    return true;
                },
                cancel: function () {

                    return true;
                }
            });
            discountBox.showModal();
        });

        /* 点击选择赠品/关闭 */
        $('.change_gift').click(function () {
            var selectGiftIdValue = $(this).attr("id");
            var selectGiftValue = selectGiftIdValue.substring(selectGiftIdValue.indexOf("_") + 1);
            $(this).parent().find("input").each(function () {
                $("#gift_box_" + selectGiftValue).find(".gift_" + $(this).val()).addClass("checked");
            });
            $("#gift_box_" + selectGiftValue).css({'top': 0});
            setTimeout(function () {
                $("#gifts_" + selectGiftValue).animate({'bottom': 0}, 500)
            }, 10);
            var mySwiper = new Swiper('#chooseGifts_' + selectGiftValue, {
                paginationClickable: true,
                slidesPerView: 3,
                loop: true
            });
        });
        $('.set_gift .close').click(function () {
            $('.set_gift .minigifts').animate({'bottom': '-300px'});
            setTimeout(function () {
                $('.set_gift').css({'top': '999px'});
            }, 500)
        });

        /* 赠品选择勾选 */
        $(document).on('click', '.gift_item', function () {
            var minigifts = $(this).parents(".minigifts").attr("id");
            var presentModel = minigifts.split("_")[3];
            if (presentModel == '1') {
                $(this).parents(".minigifts").find(".gift_item").removeClass("checked");
            }
            $(this).toggleClass('checked');
        });
    });

    function changeGift(obj) {
        var selectedGifts = '';
        $(obj).parents(".set_gift").find(".gift_item").each(function () {
            if ($(this).hasClass("checked")) {
                selectedGifts += '<p><span>【赠品】</span>' +
                        '<span class="name">' + $(this).find(".name").html() + '</span>' +
                        '<span scopenum=' + $(this).find(".num").attr("scopenum") + '>×' + $(this).find(".num").attr("scopenum") + '</span>' +
                        '<input type="hidden" value="' + $(this).find("input").val() + '">' +
                        '</p>';
            }
        });

//        var changebutton = $(".cart_gifts").find("a").clone();
        $(".cart_gifts").find("p").remove();
        $(".cart_gifts").find("a").before(selectedGifts);
        $('.set_gift .minigifts').animate({'bottom': '-300px'});
        setTimeout(function () {
            $('.set_gift').css({'top': '999px'});
        }, 500)
//        $(".cart_gifts").append(changebutton);
    }

    function showchangegift(obj) {
        var selectGiftIdValue = $(obj).attr("id");
        var selectGiftValue = selectGiftIdValue.substring(selectGiftIdValue.indexOf("_") + 1);
        $(obj).parent().find("input").each(function () {
            $("#gift_box_" + selectGiftValue).find(".gift_" + $(this).val()).addClass("checked");
        });
        $("#gift_box_" + selectGiftValue).css({'top': 0});
        setTimeout(function () {
            $("#gifts_" + selectGiftValue).animate({'bottom': 0}, 500)
        }, 10);
        var mySwiper = new Swiper('#chooseGifts_' + selectGiftValue, {
            paginationClickable: true,
            slidesPerView: 3,
            loop: true
        });
    }

</script>

<script>

    /* 选择赠品时左右滑动查看赠品 */
    var mySwiper = new Swiper('#chooseGifts', {
        paginationClickable: true,
        slidesPerView: 3,
        loop: true
    });

    function getQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }

    var buyingLimitError = '${buyingLimitError!''}'
    var buyerror =  getQueryString("buyingLimitError");
    if(buyerror){
        var msg = "所买抢购商品达到了限购上限";
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+msg +'</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
    }

</script>
</body>
</html>