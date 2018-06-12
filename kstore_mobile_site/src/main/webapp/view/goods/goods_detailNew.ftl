<!DOCTYPE html>
<html lang="zh-cn">
<head>


    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
<#assign basePath=request.contextPath>
<#if (sys.bsetName)??>
    <title><#if map.detailBean.productVo.goods.goodsSeoTitle?length &gt; 0> ${map.detailBean.productVo.goods.goodsSeoTitle!''}<#else>${map.detailBean.productVo.productName!''}</#if>${sys.bsetName}</title>
<#else>
    <title><#if map.detailBean.productVo.goods.goodsSeoTitle?length &gt; 0> ${map.detailBean.productVo.goods.goodsSeoTitle!''}<#else>${map.detailBean.productVo.productName!''}</#if>${seo.mete}</title>
</#if>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/idangerous.swiper.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" ></script>
    <script >

        var wxconfig;
        var link = window.location.href.split("?")[0]+'?isWx=wx';
        /*
        $.ajax({
            url:"${basePath}/queryWxConfig.htm?curUrl="+ encodeURIComponent(window.location.href.split('#')[0]),
            async:false,
            success:function(data){
                if(data != null){
                    wxconfig = {
                        appId : data.appId,
                        timestamp : data.timestamp,
                        nonceStr : data.nonceStr,
                        signature : data.signature
                    };
                }

            }
        });
        */

        wx.config({
            debug: false,
            appId: wxconfig.appId, // 公众号的唯一标识
            timestamp:wxconfig.timestamp , // 生成签名的时间戳
            nonceStr: wxconfig.nonceStr, // 生成签名的随机串
            signature: wxconfig.signature,// 签名
            jsApiList: ['onMenuShareTimeline',
                'onMenuShareAppMessage'] // 需要使用的JS接口列表
        });

        //处理失败验证
        wx.error(function(res){

        });
        wx.ready(function(){

            //分享到朋友圈
            wx.onMenuShareTimeline({
                title: '${map.detailBean.productVo.productName!''}', // 分享标题
                link: link, // 分享链接
                imgUrl: '${map.detailBean.productVo.goodsInfoImgId!''}', // 分享图标
                success: function () {
                    // 用户确认分享后执行的回调函数
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                }
            });
            //分享给朋友
            wx.onMenuShareAppMessage({
                title: '${map.detailBean.productVo.productName!''}', // 分享标题
                desc: '${map.detailBean.productVo.subtitle!''}', // 分享描述
                link: link, // 分享链接
                imgUrl: '${map.detailBean.productVo.goodsInfoImgId!''}', // 分享图标
                success: function () {
                    // 用户确认分享后执行的回调函数
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                }
            });
        });

    </script>
</head>
<body>

<div class="pro-details-top">
    <a href="javascript:history.back();" class="back">
        <i class="ion-ios-arrow-left"></i>
    </a>
    <h1>商品详情</h1>
    <a href="javascript:;" class="box-tool">
        <i class="ion-ios-more"></i>
    </a>
    <div class="slideTool-box" style="display:none;">
        <ul>
            <!--<li><a href="javascript:;"><i class="searchicon"></i>分享</a></li>-->
            <li><a href="${basePath}/customercenter.html"><i class="mine"></i>我的</a></li>
            <li><a href="${basePath}/main.html"><i class="home"></i>主页</a></li>
            <li><a href="${basePath}/customer/mycollections.html"><i class="collection"></i>我的收藏</a></li>
        </ul>
    </div>
</div>

<div class="content-detail" id="proTop">
    <input type="hidden" value="${basePath}" id="basePath"/>
    <input type="hidden" id="goodsId" value="${map.detailBean.productVo.goodsId}" />
    <input type="hidden" id="productId" value="${map.detailBean.productVo.goodsInfoId}" />
    <input type="hidden" id="brandId" value="${map.detailBean.productVo.goods.brandId}" />
    <input type="hidden" id="productStock" value="${map.detailBean.productVo.goodsInfoStock}" />
    <input type="hidden" id="catId" value="${map.detailBean.productVo.goods.catId}" />
    <input type="hidden" id="goodsInfoAdded" value="${map.detailBean.productVo.goodsInfoAdded}" />
    <input type="hidden" id="followPrice" value="${map.detailBean.productVo.goodsInfoPreferPrice}"/>
    <input type="hidden" id="marketingId" value=""/>
    <input type="hidden" id="thirdId" value="${map.detailBean.productVo.thirdId}"/>

    <div class="good_img">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <!--  商品详情页轮播图片 -->
            <#if map.detailBean.productVo.imageList??>
                <#list map.detailBean.productVo.imageList as image>
                    <div class="swiper-slide"><a href="${basePath}/showBigImage-${map.detailBean.productVo.goodsInfoId}.html"><img alt="" src="${image.imageArtworkName!''}"></a></div>
                </#list>
            </#if>
            </div>
        </div>
        <div class="swiper-pagination"></div>
    </div>
    <div class="prodesc">
        <h3 class="title">${map.detailBean.productVo.productName!''}</h3>
        <small class="fav-info">${map.detailBean.productVo.subtitle!''}</small>
        <!-- 商品销售价 -->
        <p class="price preprice">¥&nbsp;<span class="num">${map.detailBean.productVo.goodsInfoPreferPrice?string("0.00")}</span></p>
        <input type="hidden" id="oldPrice"  value="${map.detailBean.productVo.goodsInfoPreferPrice?string("0.00")}">
        <!-- 商品原价 -->
    <#--<p  hidden="hidden" id="oldPrice">¥&nbsp;<span class="num oldPrice"></span></p>-->
        <!-- 商品优惠价 -->
        <p  class="price afterpri" hidden="hidden" id="oldPrice">¥&nbsp;<span class="num mark_price"></span></p>

    </div>
    <div class="summary-list">
        <div class="list-item">
            <a href="javascript:;">
                <label>领券</label>
                <div class="list-value" id="coupon">

                </div>
                <i class="arrow-right" id="couponBtn"></i>
            </a>
        </div>
        <div class="list-item promotion-choose">
            <label>促销</label>
            <div class="list-value">
                <div class="promotion-value">
                </div>
                <i class="arrow-right" id="marketBtn"></i>
            </div>

            <div class="list-value" style="display: none;">
                <span>可享受以下促销</span>
                <i class="arrow-up"></i>
                <ul class="fav-list promotions">

                </ul>
            </div>
        </div>
        <#--&lt;#&ndash;<div class="list-item promotions-list" style="display: none;">&ndash;&gt;-->
            <#--<div class="list-value">-->
                <#--<span>可享受以下促销</span>-->
                <#--<ul class="fav-list promotions">-->

                <#--</ul>-->
            <#--</div>-->
        <#--</div>-->
        <div class="list-item spec-choose">
            <label>已选</label>
            <div class="list-value" id="yixuan">

            </div>
            <i class="arrow-right" id="specBtn"></i>
        </div>
        <div class="list-item area-choose">
            <label>送至</label>
            <div class="list-value" id="songzhi">
                <span class="current-location">${map.chAddress}</span>
                <i class="gps"></i>
            </div>
        </div>
        <div class="list-item" id="yunFeiShow" style="display: none;">
            <label>运费</label>
            <div class="list-value" id="yunfei"></div>
        </div>
    </div>
    <div class="common mt10" id="comment">
        <div class="list-item">
            <label>商品评价</label>
            <div class="list-value common-percent">
                好评度<span id="haoPingLv"></span>
            </div>
            <span class="curValue" onclick="showPingLun()"><span id="commentCount"></span>人评论</span>
            <i class="arrow-right" id="commentBtn" onclick="showPingLun()"></i>
            <div class="common-way">
                <a class="btn-grey selected" role="0" id="haoping"></a>
                <a class="btn-grey" onclick="" role="1" id="zhongping"></a>
                <a class="btn-grey" onclick="" role="2" id="chaping"></a>
            </div>
        </div>
        <div id="commentBody" onclick="showPingLun()">

        </div>
    </div>
    <div class="see-more">
        <h3>— — — 点击这里，查看图文详情 — — —</h3>
    </div>
    <!--
    <div class="slideBar">
        <div class="slideBar-item sTop"><i></i></div>
    </div>-->
</div>

<!-- 这个里面的内容是后续加载进来的 -->
<div class="pro-details-box" id="proBottom">

</div>

<div class="bar-bottom">
    <div class="half row">
    <#if (map.detailBean.productVo.thirdId)??>
        <#if map.detailBean.productVo.thirdId != 0>
            <#if map.storeInfo.qqSwitch == '1'>
                <div class="col-6">
                    <a class="bar-item " href="${basePath}/myshoppingmcart.html">
                        <i class="bar-bottom-i cart"><span class="tip" id="shopCartNum"></span></i>购物车
                    </a>
                </div>
                <div class="col-6">
                    <a class="bar-item " href="${basePath}/thirdStoreIndex.htm?storeId=${map.detailBean.productVo.thirdId}">
                        <i class="bar-bottom-i shop"></i>店铺
                    </a>
                </div>
                <div class="col-6" id="onlineService">
                    <a class="bar-item" href="http://wpa.qq.com/msgrd?v=3&uin=${map.storeInfo.serviceQq}&site=qq&menu=yes" target="_blank">
                        <i class="bar-bottom-i mine"></i>客服
                    </a>
                </div>

                <div class="col-6">
                    <a class="bar-item collection" href="javascript:;">
                        <i class="bar-bottom-i love"></i>收藏
                    </a>
                </div>
            <#else>
                <div class="col-8">
                    <a class="bar-item " href="${basePath}/myshoppingmcart.html">
                        <i class="bar-bottom-i cart"><span class="tip" id="shopCartNum"></span></i>购物车
                    </a>
                </div>

                <div class="col-8">
                    <a class="bar-item " href="${basePath}/thirdStoreIndex.htm?storeId=${map.detailBean.productVo.thirdId}">
                        <i class="bar-bottom-i shop"></i>店铺
                    </a>
                </div>

                <div class="col-8">
                    <a class="bar-item collection" href="javascript:;">
                        <i class="bar-bottom-i love"></i>收藏
                    </a>
                </div>
            </#if>
        <#else>
            <#if map.url !="" >
                <div class="col-8">
                    <a class="bar-item " href="${basePath}/myshoppingmcart.html">
                        <i class="bar-bottom-i cart"><span class="tip" id="shopCartNum"></span></i>购物车
                    </a>
                </div>

                <#if map.type !="NO">
                    <#if map.type =="QQ" >
                        <div class="col-8" id="onlineService">
                            <a class="bar-item kstService" href="http://wpa.qq.com/msgrd?v=3&uin=${map.url}&site=qq&menu=yes">
                                <i class="bar-bottom-i mine"></i>客服
                            </a>
                        </div>
                    <#else>
                        <div class="col-8" id="onlineService">
                            <a class="bar-item kstService" href="${basePath}/${map.url}">
                                <i class="bar-bottom-i mine"></i>客服
                            </a>
                        </div>
                    </#if>
                </#if>

                <div class="col-8">
                    <a class="bar-item collection" href="javascript:;">
                        <i class="bar-bottom-i love"></i>收藏
                    </a>
                </div>
            <#else>
                <div class="col-12">
                    <a class="bar-item " href="${basePath}/myshoppingmcart.html">
                        <i class="bar-bottom-i cart"><span class="tip" id="shopCartNum"></span></i>购物车
                    </a>
                </div>
                <div class="col-12">
                    <a class="bar-item collection" href="javascript:;">
                        <i class="bar-bottom-i love"></i>收藏
                    </a>
                </div>
            </#if>
        </#if>
    </#if>

    </div>
    <div class="half">
        <a class="btn addToCart" href="javascript:;">加入购物车</a>
    </div>
</div>

<div class="pro-chose" style="display: none;">
    <input type="hidden" id="allSpecLength" value="${map.detailBean.productVo.specVo?size}" />
    <i class="close"></i>
    <div class="prodesc">
        <div class="propic">
        <#if map.detailBean.productVo.imageList??>
            <#list map.detailBean.productVo.imageList as image>
                <#if image_index==0>
                    <img src="${image.imageInName!''}" alt=""/>
                </#if>
            </#list>
        </#if>

        </div>
        <p  class="price preprice">¥&nbsp;<span class="num">${map.detailBean.productVo.goodsInfoPreferPrice?string("0.00")}</span></p>
        <input type="hidden" id="oldPrice"  value="${map.detailBean.productVo.goodsInfoPreferPrice?string("0.00")}">
        <!-- 商品原价 -->
    <#--<p  hidden="hidden" id="oldPrice">¥&nbsp;<span class="num oldPrice"></span></p>-->
        <!-- 商品优惠价 -->
        <p  class="price afterpri" hidden="hidden" id="oldPrice">¥&nbsp;<span class="num mark_price"></span></p>
        <h3 class="title">${map.detailBean.productVo.productName!''}</h3>
    </div>
    <input type="hidden" id="allSpecLength" value="${map.detailBean.productVo.specVo?size}" />
<#list map.openSpec as spec>
    <div class="list-item specNames">
        <h3 class="item-head">${spec.spec.specName}</h3>
        <div class="list-value" id="specA">
            <#list spec.specValList as specvalue>
                <a onclick="clickSpecDetail(this,false);" data-parent="${spec.spec.specId}" data-value="${specvalue.specDetail.specDetailId}"   class="btn-grey _sku" href="javascript:void(0);">${specvalue.specValueRemark!''}</a>
            </#list>
        </div>
    </div>
</#list>

    <!-- 保存已经选择的规格值 -->
    <div class="hide">
    <#if (map.detailBean.productVo.specVo??)>
        <#list map.detailBean.productVo.specVo as spec>
            <input type="hidden" class="chooseParamId" value="${spec.goodsSpecDetail.specDetailId}" data-spec="${spec.spec.specName}" data-detail="${spec.specValueRemark!''}" />
        </#list>
    </#if>
    </div>

    <div class="list-item">
        <h3 class="item-head">数量</h3>
        <div class="list-value">
            <div class="num">
                <span class="reduce disabled num_minus">&#8722</span>
                <input type="text" value="1" class="count_num product_count" onblur="checkGoodsNum()">
                <span class="add num_plus">&#43</span>
            </div>
            <span class="kucun"><span id="numError" style="color: red; "></span></span>
        </div>
    </div>
    <a class="btn btn-full push-cart" id="add_cart" href="javascript:void(0);">确&nbsp;定</a>
</div>


<div class="screen area-box-lv1" style="display:none;">
    <div class="screen-header">
        <a class="back" href="javascript:;" id="dqCancle">取消</a>
        请选择省
    </div>
    <div class="screen-cont">
        <div class="lists province">
            <dl id="province">

            </dl>
        </div>
    </div>
</div>

<div class="screen area-box-lv2" style="display:none;">
    <div class="screen-header">
        <a class="back" href="javascript:;"><i class="back-icon" id="back-icon1"></i></a>
        请选择市
    </div>
    <div class="current-area">
        已选择：
        <span id="okProvince"></span>
    </div>
    <div class="screen-cont">
        <div class="lists city">
            <dl id="city">
            </dl>
        </div>
    </div>
</div>

<div class="screen area-box-lv3" style="display:none;">
    <div class="screen-header">
        <a class="back" href="javascript:;"><i class="back-icon" id="back-icon2"></i></a>
        请选择地区
    </div>
    <div class="current-area">
        已选择：
        <span id="okProvince1"></span>
        <span id="okCity"></span>
    </div>
    <div class="screen-cont">
        <div class="lists district">
            <dl id="district">
            </dl>
        </div>
    </div>
</div>

<div id="myvalue" hidden="hidden">
    <div class="bar-top"><a class="bar-item half active"
                            onclick="$(this).addClass('active').siblings().removeClass('active');$('.pro-desc').show();$('.pro-parameters').hide();">商品介绍</a><a
            class="bar-item half"
            onclick="$(this).addClass('active').siblings().removeClass('active');$('.pro-desc').hide();$('.pro-parameters').show();">规格参数</a>
    </div>
    <div class="pro-desc" style="padding-top: 5px;">
        <div class="pro-desc-body"><p>商品名称：${map.detailBean.productVo.productName!''}</p>

            <p>商品编号：${map.detailBean.productVo.goodsInfoItemNo}</p>

            <p>店铺：<a
                    href="javascript:;"><#if map.detailBean.productVo.isThird='0'>${sys.bsetName!''}<#else>${map.detailBean.productVo.thirdName!''}</#if></a>
            </p>

            <p>
                时间：${map.detailBean.productVo.goodsInfoAddedTime?string("yyyy-MM-dd HH:mm:ss")}</p><#list map.detailBean.expandPrams as expandParam>
            <p>${expandParam.expandParamVo.expandparamName}
                ：${expandParam.expangparamValue.expandparamValueName}</p></#list>
            <br>${map.detailBean.productVo.goods.mobileDesc!''}</div>
    </div>
    <div class="pro-parameters" style="display:none;padding-top: 5px;">
        <div class="pro-parameters-body">
            <table>
                <thead>
                <tr>
                    <td colspan="2">详细参数</td>
                </tr>
                </thead>
                <tbody><#list map.detailBean.param as param><#if (param.paramValue)??&&(param.paramValue)!=''>
                <tr>
                    <td width="150">${param.param.paramName}：</td>
                    <td>${param.paramValue}</td>
                </tr></#if></#list></tbody>
            </table>
        </div>
    </div>
    </div>


<!-- 保存商品参数部分相关参数 -->
<form id="paramGoodsForm" action="${map.detailBean.productVo.goodsInfoId}.html" method="post">
    <input  type="hidden" name="chAddress" class="ch_address" value="${map.chAddress}" />
    <input type="hidden" name="chProvince" class="ch_province" value="${map.chProvince}" />
    <input type="hidden" name="chCity" class="ch_city" value="${map.chCity}" />
    <input type="hidden" name="chDistinct" class="ch_distinct" value="${map.chDistinct}" />
    <input type="hidden" name="distinctId" class="ch_distinctId" value="${map.distinctId}"  />
    <input type="hidden" class="productStock" value="${map.detailBean.productVo.goodsInfoStock}" />
    <input type="hidden" id="isAjax" value="${map.isAjax}" name="isAjax"/>
</form>

<script src="${basePath}/js/goods/goods_detailNew.js"></script>
<script src="${basePath}/js/lodash.min.js"></script>

<script>
    $(function(){

        /* 顶部随页面滑动显示 */
        $(window).scroll(function(){
            if($(this).scrollTop() <= 200){
                $('.pro-details-top h1').css({
                    opacity : $(this).scrollTop() * 0.005
                });
            }
            else{
                $('.pro-details-top h1').css({
                    opacity : 1
                });
            }
        });

        //顶部工具箱
        $('.box-tool').click(function(){
            $(this).next().slideToggle('fast');
        });

        /* 商品大图展示 */
        $('.good_img,.good_img img').css('height',parseInt($(window).width()) + 'px');
        var mySwiper = new Swiper('.swiper-container',{
            pagination: '.swiper-pagination',
            loop:true,
            grabCursor: true,
            autoplay : 3000
        });

        /**
         * 取消选择地区
         * */
        $('#dqCancle').click(function(){
            $('.opacity-bg-3').remove();
            $('.area-box-lv1').hide();
        });

        /**
         * 返回到选择省
         * */
        $('#back-icon1').click(function(){
            $('.area-box-lv2').hide();
            $('.area-box-lv1').show();
        });
        /**
         * 返回到选择市
         * */
        $('#back-icon2').click(function(){
            $('.area-box-lv3').hide();
            $('.area-box-lv2').show();
        });


        /* 促销选择 */
        $('.promotion-choose .list-value').click(function () {
            $(this).hide().siblings().show();
        });


        /* 规格选择 */
        $('.spec-choose,.addToCart').click(function(){
            $('body').append('<div class="opacity-bg-4" onclick="offOpacity()"></div>');
            $('.pro-chose').slideDown('fast');
        });
        $('.pro-chose .close').click(function(){
            $('.opacity-bg-4').remove();
            $('.pro-chose').hide();
        });

        /* 加入购物车 */
//        $('.push-cart').click(function(){
//            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="waiting"></i><h3>正在加入购物车</h3></div></div>');
//
//        });

        /* 选择地区 */
        $('#songzhi').click(function(){
            $('body').append('<div class="opacity-bg-3"></div>');
            $('.area-box-lv1').show();
        });
        $('.province dd').click(function(){
            $('.area-box-lv1').hide();
            $('.area-box-lv2').show();
        });
        $('.city dd').click(function(){
            $('.area-box-lv2').hide();
            $('.area-box-lv3').show();
        });
//        $('.district dd').click(function(){
//            $('.opacity-bg-3').remove();
//            $('.area-box-lv3').hide();
//        });

        /* 悬浮工具框 */
        //工具箱
        //回到顶部
        $('.sTop>i').click(function(){
            $('html,body').animate({scrollTop: 0}, 800);
        });


        /* 点击查看图文详情 */
        $('.see-more').click(function(){
            if($('#proBottom .bar-top').length > 0){
                $(this).find('h3').text('— — — 点击这里，查看图文详情 — — —')
                $('.content-detail').animate({
                    paddingBottom : '50px',
                    marginTop : 0
                },800);
                setTimeout(function(){
                    $('#proBottom').html('');
                },800)
            }
            else{
                $('#proBottom').append($("#myvalue").html());
                $(this).find('h3').text('— — — 点击这里，回到商品信息 — — —');
                $('body').scrollTop(0);
                $('.content-detail').animate({
                    paddingBottom : 0,
                    marginTop : - $('.content-detail').height() + 40 + 'px'
                },800);
            }

        });


        <!-- 加载商品促销的信息 houyichang 2015/10-->
        loadGoodsPromotion();

        <!-- 加载优惠券列表 -->
        loadCoupon();

        <!-- 加载商品评论 -->
        loadComment(1,0);

        <!-- 默认选中一个规格以及显示第一个规格在主页 -->
        //showDefaultSpec();

        <!-- 加载规格值区域 -->
        var productList=null;
        $.get("${basePath}/all/"+$("#goodsId").val()+".html",function(data){
            productList=data;
            <!-- 把查询到的数据传递到js方法中-->
            loadAllProduct(productList);
            loadChooseParam();
        });

        /*初始化已经选择的城市*/
        loadInit();

        <!-- 加载购物车数量 -->
        loadShopCartNum();

        <!-- 查询改用户是否收藏该商品 -->
        loadCollection();

        <!-- 判断是否是切换规格 -->
//        if($("#isAjax").val() == "1"){
//            $('body').append('<div class="opacity-bg-4" onclick="offOpacity()"></div>');
//            $('.pro-chose').slideDown('fast');
//        }

    });





</script>
</body>
</html>