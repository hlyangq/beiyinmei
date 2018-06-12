<!DOCTYPE html>
<html lang="zh-cn">

<head>
<#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
<#if (sys.bsetName)??>
    <title><#if map.nowcate?? >${map.nowcate.catName}<#else>所有商品</#if>列表页--${sys.bsetName}</title>
<#else>
    <title><#if map.nowcate?? >${map.nowcate.catName}<#else>所有商品</#if>列表页--${seo.mete}</title>
</#if>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
</head>
<body>
<script src="${basePath}/js/jquery.js"></script>
<input type="hidden" id="show_status" value="${showStatus!""}">
<script>
    $(function() {
        //列表条形块形样式切换
        $('.style-trans').click(function(){
            if($("#showStatus").val()==0){
                $("#showStatus").val(1);
            }else{
                $("#showStatus").val(0);
            }
            if($('.list-line').length > 0){
                $('.list-line').addClass('list-box').removeClass('list-line');
                $(this).find('i.list-icon').addClass('list-2').removeClass('list-1');
            }
            else{
                $('.list-box').addClass('list-line').removeClass('list-box');
                $(this).find('i.list-icon').addClass('list-1').removeClass('list-2');
            }
        });
        if ($("#show_status").val() == 1) {
            $('.style-trans').click();
        }
    });
</script>

<div class="bar-top" style="position:static">
    <div class="search complete_search" style="position:static">
        <div class="search_box">
            <div class="s_left">
                <a href="javascript:;" onClick="javascript:history.back(-1);" class="backto_page">
                    <i class="ion-ios-arrow-left"></i>
                </a>
            </div>
            <div class="search_form">
                <i class="ion-ios-search-strong"></i>
                <form action="${basePath}/searchProduct.htm" method="get" id="searchProductForm" >
                    <input id="searchInput"  id="searchInput"  value="<#if keyWords??>${keyWords}</#if>" placeholder="请输入商品关键字搜索"  name="keyWords"  type="text" placeholder="大双曲屏 三星S6 edge+">
                    <input type="hidden" value="${storeId!""}" name="storeId" id="storeId">
                </form>
            </div>
            <div class="s_right">
                <a href="javascript:void(0);" id="searchBtn" class="search_btn">搜索</a>
            </div>
        </div>
    </div>

</div>
<div class="bar-top" style="position:static">
    <a val="5D" href="javascript:void(0);" class="bar-item filter-item change_sort <#if sort='' || sort='5D'>active</#if>"  filter_default ">默认</a>
    <a val="2D" href="javascript:void(0);" class="bar-item filter-item change_sort <#if sort!='' && sort='22D' || sort='2D' >active</#if>"  filter-sales">销量<#if sort!='' && sort='22D' ><i class="sharp-up"></i></#if><#if sort!='' && sort='2D' ><i class="sharp-down"></i></#if></a>
    <a val="1D" href="javascript:void(0);" class="bar-item filter-item change_sort <#if sort!='' && sort='11D' || sort='1D'>active</#if>"  filter-price">价格<#if sort!='' && sort='11D' ><i class="sharp-up"></i></#if><#if sort!='' && sort='1D' ><i class="sharp-down"></i></#if></a>
    <a href="javascript:void(0);" class="bar-item style-trans"><i class="list-icon list-1"></i></a>
</div>

<input type="hidden" value="<#if keyWords??>${keyWords}</#if>" id="keywords_v">
<div class="content"  style="padding-top:0;" >
    <div class="prolist list-line">
    <#if map.pb.data??>
        <#if map.pb.data?size!=0>


        <#list map.pb.data as product>

            <#assign productPrice=product.goodsInfoPreferPrice>
            <#assign stock=product.goodsInfoStock>
            <#if product.wareList??&&product.wareList?size &gt;0>
                <#list product.wareList as ware>
                    <#if ware.wareId==wareId>
                        <#assign productPrice=ware.warePrice>
                        <#assign stock=ware.wareStock>
                        <#break>
                    </#if>
                </#list>
            </#if>
              <div class="list-item">
                <a href="${basePath}/item/${product.goodsInfoId}.html">
                    <div class="propic">

                        <#--<img src="<#if  product.imgList?? && product.imgList?size &gt; 0 >${product.imgList[0].imageBigName!''}</#if>" alt="${product.productName!''}"/>-->
                        <img src="${product.goodsInfoImgId}" alt="${product.productName!''}"/>
                    </div>
                    <div class="prodesc">
                        <h3 class="title">${product.goodsInfoName!''}</h3>
                        <p class="price">&yen;&nbsp;<span>${productPrice?string('0.00')}</span>
                            <#if product.codeUtils??>
                            <#list product.codeUtils as code>
                                <#if code.codexId==5>
                                    <span class="fav reduce">减</span>
                                </#if>
                                <#if code.codexId==1>
                                    <span class="fav reduce">直</span>
                                </#if>
                                <#if code.codexId==8>
                                    <span class="fav discount">折</span>
                                </#if>
                                <#if code.codexId==12>
                                    <span class="fav reduce">邮</span>
                                </#if>
                            </#list>
                            </#if>
                        </p>
                        <p class="comm">
                            <#assign goodCount=0 >
                            <#assign count=0>
                            <#if product.commentUtilBean??>
                                <#assign goodCount="${(goodCount?number+product.commentUtilBean.goodCount)?number}">
                                <#assign count="${(count?number+product.commentUtilBean.count)?number}">
                                <#if product.commentUtilBean.goodCount?number gt 0 >
                                    <#assign goodCount="${goodCount?number/count?number}">
                                    <#assign goodCount="${goodCount?number*100}">

                                </#if>
                            </#if>
                            <span class="item-percent">好评<span><#if product.commentUtilBean??>${goodCount!""}<#else>0</#if></span>%</span>
                            <span class="item-pnum"><span><#if product.commentUtilBean??>${product.commentUtilBean.count!""}<#else>0</#if></span>人评价</span>
                        </p>
                    </div>
                </a>
            </div>
            </#list>
        <#else>
            <div class="no_tips">
            <p>木有搜索到商品哦</p>
        </div>
        </#if>
    </#if>
        <div class="slideBar">
            <div class="slideBar-item sTop"><i></i></div>
        </div>
    </div>
    <div id="showmore" class="list-loading"></div>

</div>

<div class="hide">
    <form action="searchProduct.htm" id="searchForm" method="post">
        <div class="filterList">
            <ul class="clearfix">
            <#if storeId??&&storeId!=0>
                <input type="hidden" name="storeId" value="${storeId}">
            </#if>
                <input type="hidden" name="keyWords" class="title" value="${keyWords!''}">
                <input type="hidden" name="pageNo" class="pageNo" value="${map.pb.pageNo}">
                <input type="hidden" name="sort" class="list_sort" value="${sort!''}">
                <input type="hidden" name="showStock" class="show_stock" value="${showStock!''}">
                <input type="hidden" name="showStatus" id="showStatus" value="0">
            </ul>
        </div>
        <!--/filterList-->
    </form>
</div>
<input class="storeId" type="hidden" value="${storeId!""}">
<input type="hidden" value="${wareId!""}" id="wareId_v">

<#include "../common/smart_menu.ftl" />
<input type="hidden" id="cateId" value="<#if map.nowcate??>${map.nowcate.catId}</#if>">
<input type="hidden" id="basePath" value="${basePath}">
<input type="hidden" value="0" id="status">

<script>
    $(function(){
        //回到顶部
        $('.sTop>i').click(function(){
            $('html,body').animate({scrollTop: 0}, 800);
        });
        /* 显示隐藏搜索 */
        $('.search-pro').click(function(){
            $('.pro-search-box').show();

        });

        $('.search_btn').click(function(){
            checkCookie($("#searchInput").val());
            $("#storeId").val($(".storeId").val());
            $("#searchProductForm").submit();
        });
        $('.search-cancel').click(function(){
            $('.pro-search-box').hide();
        });
        /* 商品列表排序 */

        //默认排序
        $('.filter_default').click(function(){
            $('.filter-item').removeClass('active').find('i').remove();
            $(this).addClass('active');
        });

        //销量排序
        $('.filter-sales').click(function(){
            $('.filter-item').removeClass('active').find('i').remove();
            $(this).addClass('active');
            $(this).append('<i class="sharp-down"></i>');
        });

        //价格排序
        $('.filter-price').click(function(){
            if($(this).find('i').length == 0){
                $('.filter-item').removeClass('active').find('i').remove();
                $(this).addClass('active');
                $(this).append('<i class="sharp-up"></i>');
            }
            else if($(this).find('i').attr('class') == 'sharp-up'){
                $(this).find('i').attr('class','sharp-down');
            }
            else{
                $(this).find('i').attr('class','sharp-up');
            }
        });







    });

    window.onload=function()//用window的onload事件，窗体加载完毕的时候
    {
        $(".pageNo").val(1);
        $("#status").val(0);
        //do something
    }

    $(window).scroll(function(){
        if($("#status").val()==1){
            return null;
        }
        if($(this).scrollTop() >= ($('body').height() - $(window).height())){
            var pageNo=$(".pageNo").val();
            pageNo++;
           $(".pageNo").val(pageNo);
            var strRequest="";
            if($("#catId").val!=null && $("#catId").val!=""){
                strRequest="&cid="+$("#cateId").val();
            }

            if($("#storeId").val!=null&&$("#storeId").val!=""){
                strRequest="&storeId="+$("#storeId").val();
            }
            $.ajax({
                type: "POST",
                url: "${basePath}/searchproductnew.htm",
                beforeSend: showLoadingImg,
                error: showFailure,
                data: "pageNo="+$(".pageNo").val()+"&sort="+$(".list_sort").val()+strRequest+"&keyWords="+$("#keywords_v").val(),
                success: function(msg){
                    if(msg.pb.data.length==0){
                        $("#status").val(1);
                        $('#showmore').html('已无更多结果');
                    }


                    var str="";
                    var wareId = $("#wareId_v").val();
                    for(var i =0 ;i< msg.pb.data.length;i++){
                        var mStr="";
                        var codes=msg.pb.data[i].codeUtils;
                        for(var j =0 ;j<codes.length;j++){
                            if(codes[j].codexId==5){
                                mStr+='<span class="fav reduce">减</span>';
                            }else if(codes[j].codexId==1){
                                mStr+='<span class="fav reduce">直</span>';
                            }else if(codes[j].codexId==8){
                                mStr+='<span class="fav discount">折</span>';
                            }else if(codes[j].codexId==12){
                                mStr+='<span class="fav reduce">邮</span>';
                            }
                        }

                        var price=msg.pb.data[i].goodsInfoPreferPrice;
                        if(msg.pb.data[i].wareList!= null  ){
                            for(var j=0 ; j<msg.pb.data[i].wareList.length;j++){
                                if(msg.pb.data[i].wareList[j]!=null){
                                    if(wareId == msg.pb.data[i].wareList[j].wareId){
                                        price=msg.pb.data[i].wareList[j].warePrice;
                                    }
                                }

                            }
                        }

                        var imgSrc="";
                        if(msg.pb.data[i].imgList!=null && msg.pb.data[i].imgList.length>0){
                            imgSrc=msg.pb.data[i].imgList[0].imageBigName;

                        }
                        var commentCount=0;
                        var precentCount=0;
                            if(msg.pb.data[i].commentUtilBean!=null&&msg.pb.data[i].commentUtilBean.goodCount>0){
                            commentCount=msg.pb.data[i].commentUtilBean.count;
                            precentCount=msg.pb.data[i].commentUtilBean.goodCount/commentCount;
                            precentCount=precentCount*100;
                        }

                        str+='<div class="list-item">'
                                +'<a href="${basePath}/item/'+ msg.pb.data[i].goodsInfoId+'.html">'
                                + '<div class="propic">'
                                + '<img src="'+ imgSrc+'" alt="'+ msg.pb.data[i].goodsInfoName+'"/>'
                                + '</div>'
                                + '<div class="prodesc"><h3 class="title">'+ msg.pb.data[i].goodsInfoName+'</h3> <p class="price">'
                                +'&yen;&nbsp;<span>'+price.toFixed(2)+'</span>'+mStr
                                +'</p><p class="comm"><span class="item-percent">好评<span>'+precentCount+'</span>%</span>'

                                +'<span class="item-pnum"><span>'+commentCount+'</span>人评价</span>'
                                +'</p></div></a> </div>';
                    }
                    $(".prolist").append(str);

                }
            });
        }
    });

    function showLoadingImg() {
        $('#showmore').html(' <img alt="" src="${basePath}/images/loading.gif"> <span>加载中……</span>');
    }

    function showFailure() {
        $('#showmore').html('<font color=red> 获取查询数据出错 </font>');
    }


</script>
</body>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${basePath}/js/bootstrap.min.js"></script>
<script src="${basePath}/js/fastclick.min.js"></script>
<script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
<script src="${basePath}/js/jquery.keleyi.js"></script>
<script src="${basePath}/js/jquery.lazyload.js"></script>
<script src="${basePath}/js/goods/goods_list.js"></script>
<script src="${basePath}/js/goods/goods_pro.js"></script>
</html>