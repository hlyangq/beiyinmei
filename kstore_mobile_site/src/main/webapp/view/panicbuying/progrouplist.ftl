<!DOCTYPE html>
<html lang="en">
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>抢购</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="js/jquery-1.10.1.js"></script>
    <script src="js/idangerous.swiper.js"></script>
</head>
<body>
<div class="bar-top">
    <div class="search complete_search" style="position:static">
        <div class="search_box">
            <div class="s_left">
                <a href="javascript:history.back(-1);" class="backto_page">
                    <i class="ion-ios-arrow-left"></i>
                </a>
            </div>
            <form action="${basePath}/searchProduct.htm" method="get" id="searchProductForm" style="margin-bottom:0;">
                <div class="search_form">
                    <i class="ion-ios-search-strong"></i>
                    <input id="searchInput" name="keyWords" type="text" placeholder="搜索商品">
                    <input type="hidden" value="0" name="storeId" id="storeId">
                </div>
                <div class="s_right">
                    <a href="javascript:;" class="search_btn">搜索</a>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="content">

    <div class="good_img">
        <div class="swiper-container">
            <div class="swiper-wrapper">

            <#if ChannelAdvers?exists && ChannelAdvers?size &gt; 0>
                <#list ChannelAdvers as ChannelAdver>
                    <div class="swiper-slide"><a href="#"><img alt="" src="${ChannelAdver.adverPath}"></a></div>
                </#list>
            <#else>
                <div class="swiper-slide"><a href="#"><img alt="" src="images/group_banner.jpg"></a></div>
            </#if>

            </div>
        </div>
        <div class="swiper-pagination"></div>
    </div>

    <div class="group-pros">
    <#list rushes as rush>
        <div class="pro-group-item">
            <div class="img">
                <a href="${basePath}/item/${rush.goodsProductVo.goodsInfoId}.html"><img height="100%" src="${rush.marketing.rushs[0].mobileRushImage!''}"/></a>
            </div>
            <div class="word">
                <p class="name">${10*rush.marketing.rushs[0].rushDiscount?number}折/ ${rush.goodsProductVo.productName}</p>
                <p class="sub_name">  ${rush.goodsProductVo.subtitle!''}</p>
                <p class="lefttime">
                    <font class="status"></font>
                    <span id="count${rush_index+1}" class="count">
                        <span class="day"></span><font></font>
                        <span class="hour"></span><font></font>
                        <span class="minute"></span><font></font>
                        <span class="second"></span><font></font>
                    </span>
                </p>
                <p class="del_price">原价：¥${(rush.goodsProductVo.goodsInfoPreferPrice?string("0.00"))!""}</p>
                <p class="price">
                    抢购价：<span><em>¥</em>${((rush.marketing.rushs[0].rushDiscount?number*rush.goodsProductVo.goodsInfoPreferPrice?number)?string("0.00"))!""}</span>
                </p>
                <a href="${basePath}/item/${rush.goodsProductVo.goodsInfoId}.html" class="btn btn-them">立即抢购</a>
            </div>
        </div>
        <input type="hidden" value="${rush.rushTime}" name="rushTime" id="rush${rush_index+1}">
        <input type="hidden" value="${rush.marketing.marketingBegin?string("yyyy-MM-dd HH:mm:ss")}" id="timebegin${rush_index+1}">
        <input type="hidden" class="alltimes" idattr="${rush_index+1}" value="${rush.marketing.marketingEnd?string("yyyy-MM-dd HH:mm:ss")}" id="time${rush_index+1}">
    </#list>
    </div>

</div>
<#include "../common/smart_menu.ftl" />

<script>
    $(function(){

        /*
 * 轮播广告
 * */
        $('.good_img,.good_img img').css('height', parseInt($(window).width() / (750 / 234)) + 'px');
        var mySwiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            loop: true,
            grabCursor: true,
            autoplay: 3000
        });

        //回到顶部
        $('.sTop>i').click(function(){
            $('html,body').animate({scrollTop: 0}, 800);
        });


        $(".search_btn").click(function(){
            $("#searchProductForm").submit();
        })

        $(".alltimes").each(function(){
            var id=$(this).attr("idattr");
            countDown($("#time"+id).val(),$("#timebegin"+id).val(),'#count'+id,$("#rush"+id).val())
            if($("#rush"+id).val()==3){
                $('#count'+id).prev().html("已结束");
                $('#count'+id).html("");
            }
        });

    });

    //date是结束日期，例如"2014/05/19";count是容器
    function countDown(dateend,datebegin,count,rush){
        var now = Date.parse(new Date());
        var date;
        if(rush==1){
            date=datebegin;
            $(count).parents(".word").find("a").addClass("btn-grey");
            $(count).parents(".word").find("a").removeClass("btn-them");
            $(count).parents(".word").find("a").removeAttr("href");
            $(count).parents(".word").find("a").html("未开始");
            $(count).parents(".word").parent().find(".img").find("a").removeAttr("href");
        }else if(rush==2){
            date=dateend;
        }else if(rush ==3){
            date=dateend;
            $(count).parents(".word").find("a").addClass("btn-grey");
            $(count).parents(".word").find("a").removeClass("btn-them");
            $(count).parents(".word").find("a").removeAttr("href");
            $(count).parents(".word").find("a").html("已结束");
            $(count).parents(".word").parent().find(".img").find("a").removeAttr("href");
        }
        var str =  date.replace(/-/g,"/");
        var target = Date.parse(new Date(str));
        var time = target - now;
        time = parseInt(time / 1000);
        var day = Math.floor(time / (60*60*24));
        time -= day * (60*60*24);
        var hour = Math.floor(time /(60*60));
        time -= hour * (60*60);
        var minute = Math.floor(time / 60);
        var second = time - (minute * 60);
        if(day<10){
            if(day<0){
                return;
            }else{
                day="0"+day;
            }
        }
        if(hour<10){
            hour="0"+hour;
        }
        if(minute<10){
            minute="0"+minute;
        }
        if(second<10){
            second="0"+second;
        }
        if(day==0&&hour==0&&minute==0&&second==0){
            if(rush==1){
                rush= 2;
                countDown(dateend,datebegin,count,rush);
                return;
            }else if(rush==2){
                $(count).prev().html("已结束");
                $(count).html("");
                return;
            }
        }else{
            if(rush==1){
                $(count).prev().html("离开始:");
                $(count).find('.day').html(day);
                $(count).find('.day').next().html(" 天");
                $(count).find('.hour').html(hour);
                $(count).find('.hour').next().html(" 时");
                $(count).find('.minute').html(minute);
                $(count).find('.minute').next().html(" 分");
                $(count).find('.second').html(second);
                $(count).find('.second').next().html(" 秒");
            }else if(rush==2){
                $(count).prev().html("离结束:");
                $(count).find('.day').html(day);
                $(count).find('.day').next().html(" 天");
                $(count).find('.hour').html(hour);
                $(count).find('.hour').next().html(" 时");
                $(count).find('.minute').html(minute);
                $(count).find('.minute').next().html(" 分");
                $(count).find('.second').html(second);
                $(count).find('.second').next().html(" 秒");
            }else if(rush ==3){
                $(count).prev().html("已结束");
                $(count).html("");
            }
            window.setTimeout(function(){countDown(dateend,datebegin,count,rush);},1000);
        }
    }
</script>
</body>
</html>