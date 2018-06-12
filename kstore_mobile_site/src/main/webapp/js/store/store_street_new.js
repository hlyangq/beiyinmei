var pageIndex = 1;
var totalPages = 1;
/*
 * 模拟下滑到底自动加载
 * */
var loading = false;// 加载flag


$(function () {
    totalPages = $("#initTotalPages").val();
    if(totalPages <= 1){
        shopLoad();
    }
    /*
     * 顶部轮播大图
     * */
    $('#shopsGallery').swiper({
        pagination: '#shopsGallery .swiper-pagination',
        autoplay: 3000
    });

    /*
     * 推荐商品左右滑动
     * */
    $('#shopsInStreet .swiper-container').swiper({
        pagination: 'null',
        slidesPerView: 3,
        spaceBetween: 16
    });

    /*商品列表，查看更多，为适应屏幕而计算高度*/
    $('.empty_good').css({
        'height': $('.empty_good').width(),
        'lineHeight': $('.empty_good').width() + 'px'
    });

    /*
     * 所有分类点击后定位
     * */
    $('.cates').css({width:98*$('.cates a').length+90});
    $(document).on('click','.cates a',function(){
        var that = $(this);
        var offsetValue = that.position().left - ($(window).width()/2.0) + (that.width()/2.0);
        var sLeft = $('.shops_cates .content').scrollLeft() + offsetValue;
        if(sLeft > 0){

            var moveunit = sLeft/2.0;
            var tempv = moveunit;
            var offsetaction = setInterval(function(){
                $('.shops_cates .content').scrollLeft(tempv);
                tempv+=moveunit;
                if(tempv > sLeft){
                    clearInterval(offsetaction);
                    that.addClass('active').siblings().removeClass('active');
                }
            },100);
            if( $('#shopsStreet>.content').scrollTop() < $('#shopsGallery').height()) {
                var moveunit_v = $('#shopsGallery').height() / 4.0;
                var tempv_v = 0;
                var offsetaction_v = setInterval(function () {
                    $('#shopsStreet>.content').scrollTop(tempv_v);
                    tempv_v += moveunit_v;
                    if (tempv_v > $('#shopsGallery').height()) {
                        $('#shopsStreet>.content').scrollTop($('#shopsGallery').height());
                        clearInterval(offsetaction_v);
                    }
                }, 50);
            }
        }
        else{
            $('.shops_cates .content').scrollLeft(0);
            that.addClass('active').siblings().removeClass('active');
        }

    });

    /*
     * 显示/隐藏所有分类选择
     * */
    $(document).on('click', '.show_all', function () {
        if( $('.content').scrollTop() <= $('#shopsGallery').height()){
            var moveunit = $('#shopsGallery').height()/4.0;
            var tempv = 0;
            var offsetaction = setInterval(function(){
                $('.content').scrollTop(tempv);
                tempv+=moveunit;
                if(tempv > $('#shopsGallery').height()){
                    $('.content').scrollTop($('#shopsGallery').height());
                    console.log(moveunit + '/' + tempv +'/'+ $('#shopsGallery').height());
                    clearInterval(offsetaction);
                }
            },50);
            //$('.content').scrollTop($('#shopsGallery').height());
            setTimeout(function(){
                $('.content').scrollTop($('#shopsGallery').height());
                $('.all_cates_page').show();
                $('.all_cates_list').animate({height: 'auto'},200,'ease-in-out');
            },100);
        }
        else{
            $('.all_cates_page').show();
            $('.all_cates_list').animate({height: 'auto'},200,'ease-in-out');
        }
    });

    $(document).on('click', '.close_all', function () {
        $('.all_cates_list').css({height: 0});
        $('.all_cates_page').hide();
    });

    $(document).on('click','.all_cates_list a',function(){
        var theIndex = $('.all_cates_list a').index($(this));
        $(this).addClass('active').siblings().removeClass('active');
        $('.all_cates_list').css({height: 0});
        $('.all_cates_page').hide();

        var that = $('.cates a:nth-child('+ (theIndex + 1) +')');
        var offsetValue = that.position().left - ($(window).width()/2.0) + (that.width()/2.0);
        var sLeft = $('.shops_cates .content').scrollLeft() + offsetValue;
        if(sLeft > 0){
            //$('.shops_cates .content').scrollLeft(sLeft);
            //that.addClass('active').siblings().removeClass('active');
            var moveunit = sLeft/2.0;
            var tempv = moveunit;
            var offsetaction = setInterval(function(){
                $('.shops_cates .content').scrollLeft(tempv);
                tempv+=moveunit;
                if(tempv > sLeft){
                    clearInterval(offsetaction);
                    that.addClass('active').siblings().removeClass('active');
                }
            },100);
        }
        else{
            $('.shops_cates .content').scrollLeft(0);
            that.addClass('active').siblings().removeClass('active');
        }
    });

    // 注册'infinite'事件处理函数
    $(document).on('infinite', '.infinite-scroll-bottom',function() {
        if (loading) return; // 如果正在加载，则退出
        loading = true;// 设置flag

        // 模拟1s的加载过程
        setTimeout(function() {
            loading = false;// 重置加载flag
            pageIndex++;
            if (pageIndex > totalPages) {
                shopLoad();
                return;
            }

            // 添加店铺条目
            showShopList();

            //容器发生改变,如果是js滚动，需要刷新滚动
            $.refreshScroller();
        }, 1000);
    });
});
$.init();

/*根据一级商品分类ID获取 包含此分类的店铺*/
function selectstorebycateid(cateId){
    location.href=$('#basePath').val()+"/storeliststreet.htm?cateId="+cateId;
}

/*关注商店*/
function addcollectionsellerStore(obj){
    var id = $(obj).attr("data-id");
    $.ajax({
        type: 'post',
        url:$('#basePath').val()+'/addcollectionsellerStore.htm?storeId='+id,
        async:false,
        success: function(data) {
            if(data.status==2){
                location.href = $('#basePath').val()+'/loginm.html';
            }else if(data.status==1){
                $(obj).addClass("active");
            }else if(data.status==0){
                $(obj).removeClass("active");
            }

        }
    });
}

function selectCate(id) {
    $('.all_cates_list').css({height: 0});
    $('.all_cates_page').hide();

    $("#shopsInStreet").empty();
    shopLoad();

    $(".cates a[data-cate-id='"+id+"']").addClass('active').siblings("a").removeClass("active");
    $(".all_cates_list a[data-cate-id='"+id+"']").addClass('active').siblings("a").removeClass("active");


    pageIndex = 1;
    totalPages = 1;
    $("#catId").val(id);
    showShopList();
}


function showShopList(){
    var basePath = $('#basePath').val();
    var catId = $("#catId").val();
    var data = {
        'cateId':catId,
        'pageNo':pageIndex
    }
    var _options = {
        url: basePath + '/ajaxStorelistStreet.html',
        type: 'POST',
        data: data,
        dataType: "json",
        success:function(res){
            if(res.pb != null){
                totalPages = res.pb.totalPages;
            }
            //加载商铺
            if(res.pb!=null && res.pb.list != null){
                renderTbody(res.pb.list);
            }
        },
        error: function (xhr) {
            $.toast("操作失败");
            shopLoad();
        }
    };
    $.ajax(_options);
}

function renderTbody(shoplist){
    var basePath = $('#basePath').val();
    if($(shoplist).size() > 0){

        //商铺模板
        var shopImpl = trimComment($("#shop_impl").html());

        //商品模板
        var proImpl = trimComment($("#pro_impl").html());

        var shopListHtml = '';

        $(shoplist).forEach(function(shop){
            var t_shop  = {};
            var shop_img = basePath+'/images/third_logo.png';//考虑用默认图
            if(shop.logoUrl != null && shop.logoUrl != ''){
                shop_img = shop.logoUrl;
            }
            t_shop['shop_id'] = shop.storeId;
            t_shop['shop_img'] = '<img src="'+shop_img+'"/>';
            t_shop['shop_name'] = shop.storeName;

            var collectionNum = shop.collectionNum
            if(collectionNum == null || collectionNum == ''){
                collectionNum = 0;
            }

            if(collectionNum > 9999){
                collectionNum = (collectionNum/10000).toFixed(2);
                collectionNum += '万';
            }

            t_shop['shop_gz_num'] = collectionNum;
            if(shop.isCollection > 0){
                t_shop['shop_gz'] = 'active';
            }

            if(shop.storeProductList!=null){
                var goodsListHtml = '';
                $(shop.storeProductList).forEach(function (pro,i) {
                    if(i < 5){
                        var goodsObj = {};
                        goodsObj['goods_id'] = pro.goodsInfoId;
                        goodsObj['goods_img'] = $.trim(pro.goodsInfoImgId);
                        goodsObj['goods_name'] = pro.goodsInfoName;
                        goodsObj['goods_price'] = pro.goodsInfoPreferPrice.toFixed(2);

                        goodsListHtml += templ2html(proImpl,goodsObj);
                    }
                });
                t_shop['goods_list'] = goodsListHtml;
            }

            shopListHtml += templ2html(shopImpl,t_shop);
        });

        if(pageIndex == totalPages){
            shopListHtml += "<p style=\"text-align: center\">亲，加载到底啦</p>";
            shopLoad();
        }

        $("#shopsInStreet").append(shopListHtml);


        /*
         * 推荐商品左右滑动
         * */
        $('#shopsInStreet .swiper-container').swiper({
            pagination: 'null',
            slidesPerView: 3,
            spaceBetween: 16
        });

        /*商品列表，查看更多，为适应屏幕而计算高度*/
        $('.empty_good').css({
            'height': $('.empty_good').width(),
            'lineHeight': $('.empty_good').width() + 'px'
        });
    }else if(pageIndex == 1){
        $("#shopsInStreet").html("<p style=\"text-align: center\">没有相关店铺信息</p>");
        shopLoad();
    }
}

function trimComment(html){
    return replaceAll(replaceAll(html,'<!--',''),'-->','');
}

function shopLoad(){
    // 加载完毕，则注销无限加载事件，以防不必要的加载
    $.detachInfiniteScroll($('.infinite-scroll'));
    // 删除加载提示符
    $('.infinite-scroll-preloader').hide();
}