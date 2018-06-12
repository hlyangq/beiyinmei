<!DOCTYPE html>
<html lang="zh-cn">
<head>
<#assign basePath=request.contextPath>

    <meta charset="UTF-8">
    <title>优惠套装</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>

    <script src="${basePath}/js/idangerous.swiper.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <style>
        .group-item:not(.open) .goods li:nth-child(n+4) {display: none;}
        .group-item:not(.open) .goods li.more {display:block;}
    </style>
</head>
<body>

<div class="group-sales">
    <div class="head" style="
    opacity: 1;
    z-index: 9999;">
        <a href="javascript:history.back();" class="back pull-left"><i class="ion-ios-arrow-left"></i></a>
    <#--<a href="${basePath}/myshoppingmcart.html" class="pull-right"><i class="ion-ios-cart-outline"></i></a>-->
        <h2>选择套装</h2>
    </div>

    <div class="group-list">
    <#list groupList as group>
        <div class="group-item">
            <div class="top">
                <h4>${group.groupName}</h4>
                <p class="price">${group.price?string("0.00")}</p>
                <i class="ion-ios-arrow-down"></i>
            </div>
            <ul class="goods">
                <#assign size = group.productList?size/>
                <#list group.productList as product>
                    <#assign vo = product.productDetail/>
                    <li>
                        <a href="${basePath}/item/${vo.goodsInfoId}.html">
                            <img src="${vo.goodsInfoImgId}" alt="">
                            <div class="word">
                                <p class="name">${vo.goodsInfoName}</p>
                                <p class="num">×${product.productNum?number}</p>
                                <p class="price">￥${vo.goodsInfoPreferPrice?string("0.00")}</p>
                            </div>
                        </a>
                    </li>
                </#list>
                <#if (size > 3) >
                <li class="more"><i class="ion-more"></i></li>
                </#if>

            </ul>
            <div class="bottom">
                <div class="pull-left">
                    <p>套装价：<span class="price">￥${group.price?string("0.00")}</span></p>
                </div>
                <div class="pull-right">
                    <a href="javascript:;" class="btn-them push-cart" data-group="${group.groupId}">加入购物车</a>
                </div>
            </div>
        </div>

    </#list>


    </div>

</div>

<!-- 引用公共脚部 -->
<#--<#include "../common/smart_menu.ftl" />-->

<script>
    $(function () {

        /**
         * 展开第一个套装
         * */
        $('.group-item').first().addClass('open');

        /*
        * 展开/收起套装
        * */
        $('.group-item .top').click(function () {
            $(this).parent().toggleClass('open');
        });

        /*
         * 加入购物车成功提示
         * */
        $('.push-cart').click(function () {
            var fitId = $(this).attr("data-group");
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="waiting"></i><h3>正在加入购物车</h3></div></div>');
            $.ajax({
                type: 'post',
                url: "${basePath}/buyPrePackage.htm?fitId=" + fitId,
                async: false,
                success: function (data) {
                    if (data > 0) {
                        setTimeout(function () {
                            $('.tip-box').remove();
                            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>成功加入购物车</h3></div></div>');
                        }, 1000);
                        setTimeout(function () {
                            $('.tip-box').remove();
                        }, 2000)
                    }
                    else {
                        setTimeout(function () {
                            $('.tip-box').remove();
                            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>miss</h3></div></div>');
                        }, 1000);
                        setTimeout(function () {
                            $('.tip-box').remove();
                        }, 2000)

                    }
                }
            });

        });

    });
</script>

</body>
</html>