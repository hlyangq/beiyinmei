<!DOCTYPE html>
<html>
<head>
    <#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>店铺街</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="http://kstoreimages.b0.upaiyun.com/1472540016819.jpg">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="${basePath}/css/swiper.min.css">
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <style type="text/css">
        .infinite-scroll-preloader {
            margin-top:-20px;
        }
    </style>
</head>
<body>
<input type="hidden" id="basePath" value="${basePath!''}"/>
<input type="hidden" id="catId" value="${catId!''}"/>
<input type="hidden" id="initTotalPages" value="${map.pb.totalPages!'1'}"/>

<div class="page-group">
    <div class="page page-current" id="shopsStreet">
        <div class="content infinite-scroll infinite-scroll-bottom">
            <#if map.channelAdvers?? >
            <div class="swiper-container" id="shopsGallery">
                <div class="swiper-wrapper">
                    <#list map.channelAdvers as bigAdvert>
                        <div class="swiper-slide">
                            <a href="${bigAdvert.adverHref}"><img src="${bigAdvert.adverPath}" alt=""></a>
                        </div>
                    </#list>
                </div>
                <div class="swiper-pagination"></div>
            </div>
            </#if>

            <div class="shops_cates">
                <div class="buttons-tab fixed-tab" data-offset="0">
                    <div class="cates_box content">
                        <div class="cates">
                            <a class="active" href="javascript:;" data-cate-id="-1" onclick="selectCate(-1);">精选</a>
                            <#list map.cateGory as cateGory>
                                <a href="javascript:;" data-cate-id="${cateGory.catId}" onclick="selectCate('${cateGory.catId}');">${cateGory.catName}</a>
                            </#list>
                        </div>
                    </div>
                    <a href="#" class="show_all"><span class="icon icon-down"></span></a>
                </div>
            </div>

            <div class="shops_in_street" data-distance="50">
                <div class="list-block">
                    <ul class="list-container" id="shopsInStreet">
                        <#if map.pb.rows gt 0>
                            <#list map.pb.list as storelist>
                                <li>
                                <div class="item">
                                    <div class="topment">
                                        <div class="shop_info_s">
                                            <a href="${basePath}/thirdStoreIndex.htm?storeId=${storelist.storeId}">
                                                <#assign  imageSrc="${basePath}/images/third_logo.png"/>
                                                <#if storelist.logoUrl?? && storelist.logoUrl != ''>
                                                    <#assign  imageSrc="${storelist.logoUrl}"/>
                                                </#if>
                                                <img src="${imageSrc}"/>
                                                <div class="words">
                                                    <p class="name"><#if storelist.storeName??>${storelist.storeName}</#if></p>
                                                    <p class="light">
                                                        <#if storelist.collectionNum gt 9999  >
                                                            <span>${(storelist.collectionNum/10000)?string('0.00')}万</span>
                                                        <#else>
                                                            <span>${storelist.collectionNum!0}</span>
                                                        </#if>人关注</p>
                                                </div>
                                            </a>
                                        </div>
                                        <#if storelist.isCollection gt 0  >
                                            <a href="javascript:;" class="focus active" data-id="${storelist.storeId}" onclick="addcollectionsellerStore(this)">
                                                <i class="heart"></i>
                                                <span>已</span>关注
                                            </a>
                                        <#else>
                                            <a href="javascript:;" class="focus" data-id="${storelist.storeId}" onclick="addcollectionsellerStore(this)">
                                                <i class="heart"></i>
                                                <span>已</span>关注
                                            </a>
                                        </#if>
                                    </div>
                                    <div class="rec_goods">
                                        <div class="swiper-container" id="shop_1">
                                            <div class="swiper-wrapper">
                                                <#list storelist.storeProductList as productList>
                                                    <#if productList_index lt 5>
                                                        <div class="swiper-slide">
                                                            <div class="good">
                                                                <a href="${basePath}/item/${productList.goodsInfoId}.html">
                                                                    <img src="<#if productList.goodsInfoImgId??>${productList.goodsInfoImgId}</#if>" alt="">
                                                                    <p class="name"><#if productList.goodsInfoName??>${productList.goodsInfoName}</#if></p>
                                                                    <p class="price">￥${productList.goodsInfoPreferPrice?string('0.00')}</p>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                </#list>
                                                <div class="swiper-slide">
                                                    <div class="empty_good">
                                                        <p onclick="window.location.href = '${basePath}/listByStore/${storelist.storeId}.html'">查看更多</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="swiper-pagination"></div>
                                        </div>
                                    </div>
                                </div>
                                </li>
                            </#list>
                            <#if map.pb.totalPages == 1>
                                <p style="text-align: center">亲，加载到底啦~</p>
                            </#if>
                        <#else>
                            <p style="text-align: center">没有相关店铺信息</p>
                        </#if>
                    </ul>
                </div>
                <!-- 加载提示符 -->
                <div class="infinite-scroll-preloader">
                    <div class="preloader"></div>
                </div>
            </div>

            <div class="all_cates_page" style="display: none;">
                <div class="all_cates_box">
                    <div class="topment">
                        <p class="h4">所有分类</p>
                        <a href="javascript:;" class="close_all"><i class="icon icon-up"></i></a>
                    </div>
                    <div class="all_cates_list">
                        <a class="active" href="javascript:;" data-cate-id="-1" onclick="selectCate(-1);">精选</a>
                        <#list map.cateGory as cateGory>
                            <a href="javascript:;" data-cate-id="${cateGory.catId}" onclick="selectCate('${cateGory.catId}');">${cateGory.catName}</a>
                        </#list>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>

<div id="shop_impl">
<!--
<li>
<div class="item">
    <div class="topment">
        <div class="shop_info_s">
            <a href="${basePath}/thirdStoreIndex.htm?storeId=#shop_id#">
                #shop_img#
                <div class="words">
                    <p class="name">#shop_name#</p>
                    <p class="light"><span>#shop_gz_num#</span>人关注</p>
                </div>
            </a>
        </div>
        <a href="javascript:;" class="focus #shop_gz#" data-id="#shop_id#" onclick="addcollectionsellerStore(this)">
            <i class="heart"></i>
            <span>已</span>关注
        </a>
    </div>
    <div class="rec_goods">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                #goods_list#
                <div class="swiper-slide">
                    <div class="empty_good">
                        <p onclick="window.location.href = '${basePath}/listByStore/#shop_id#.html'">查看更多</p>
                    </div>
                </div>
            </div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
</div>
</li>
-->
</div>

<div id="pro_impl">
<!--<div class="swiper-slide">
    <div class="good">
        <a href="${basePath}/item/#goods_id#.html">
            <img src="#goods_img#" alt="">
            <p class="name">#goods_name#</p>
            <p class="price">￥#goods_price#</p>
        </a>
    </div>
</div>-->
</div>

<script type='text/javascript' src='${basePath}/js/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='${basePath}/js/swiper.jquery.min.js' charset='utf-8'></script>
<script type='text/javascript' src='${basePath}/js/common.js' charset='utf-8'></script>
<script type='text/javascript' src='${basePath}/js/store/store_street_new.js' charset='utf-8'></script>
</body>
</html>