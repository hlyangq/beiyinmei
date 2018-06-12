
var basePath = $("#basePath").val();

/*
 *商品详情页通用JS
 * author YuanKangKang
 */
/**
 *
 * KKK 促销规则
直降促销	        1	可设置指定商品，进行直降M元进行促销
买赠促销	        2	为刺激消费，指定商品买即送赠品促销
买送优惠券促销	3	可设置指定商品，进行送优惠券促销
买折促销	        4	可设置指定商品，进行折扣促销
满减促销	        5	可设置指定商品，购买单个商品满M元减N元促销
满金额赠	        6	可指定商品，设置单品满M元，赠送赠品促销
满送优惠券促销	7	可指定商品，设置单品满M元，赠送优惠券促销
满折促销	        8	可指定商品，设置单品满M元，打N折促销
限购	            9	可指定商品，设置限购购买M件促销
团购	            10	可指定商品，设置团购促销
抢购	            11	可指定商品，设置抢购促销
包邮	            12	可指定商品，设置免运费促销
买够件数打折	    13	可指定购买M件不同的商品，打N折促销
买够件数多少钱	14	可指定购买M件不同的商品，共N元促销
折扣促销	        15	可指定商品，设置不同折扣促销
满数量赠	        16	可指定商品，设置满M件，赠送赠品促销

 */

function addPromotionList(promotionName) {
    $(".promotion-value").append("<span class=\"label\">" + promotionName + "</span>");
}

function renderDefaultMarketing(marketingOne, data) {
//直降促销
    if (marketingOne.codexType == 1) {
        $(".promotion-value").append("<span class=\"label\">直降</span>" + marketingOne.marketingName);
        $("#marketingId").val(marketingOne.marketingId);
    } else if (marketingOne.codexType == 5) {
        $(".promotion-value").append("<span class=\"label\">满减</span>" + marketingOne.marketingName);
        $("#marketingId").val(marketingOne.marketingId);
        //满折促销
    } else if (marketingOne.codexType == 8) {
        $(".promotion-value").append("<span class=\"label\">满折</span>" + marketingOne.marketingName);
        $("#marketingId").val(marketingOne.marketingId);
    } else if (marketingOne.codexType == 12 || marketingOne.codexType == 15) {
        for (var c = 0; c < data.length; c++) {
            if (data[c].codexType != 12 && data[c].codexType != 15) {
                if (null == $(".promotion-value").text() || "" == $(".promotion-value").text()) {
                    $(".promotion-value").append(data[c].marketingName);
                }
                $("#marketingId").val(data[c].marketingId);
            }
        }
    }
    //else if(marketingOne.codexType == 10){
    //    $(".promotion-value").append("<span class=\"label\">团购</span>" + marketingOne.marketingName);
    //    $("#marketingId").val(marketingOne.marketingId);
    //}
    else if (marketingOne.codexType == 11) {
        $(".promotion-value").append("<span class=\"label\">抢购</span>" + marketingOne.marketingName);
        $("#marketingId").val(marketingOne.marketingId);
    } else if (marketingOne.codexType == 6) {
        for (var i = 0; i < data.length; i++) {
            var marketing = data[i];
            if (marketing.codexType == 6) {

            }
            var fullbuyPresent = '<span class=\"label\">满赠</span>';


            if (marketing.fullbuyPresentMarketings != null && marketing.fullbuyPresentMarketings.length != 0) {
                for (var j = 0; j < marketing.fullbuyPresentMarketings.length; j++) {

                    if (marketing.fullbuyPresentMarketings[j].presentType == 0) {
                        fullbuyPresent += "满" + marketing.fullbuyPresentMarketings[j].fullPrice + "元&nbsp";
                    } else {
                        fullbuyPresent += "满" + marketing.fullbuyPresentMarketings[j].fullPrice + "件&nbsp";
                    }

                }

                fullbuyPresent += "送赠品";

            }
            $(".promotion-value").append(fullbuyPresent);
        }
        $("#marketingId").val(marketingOne.marketingId);
    }
    return marketing;
}
function renderMarketingDescList(marketingList) {
    var $promotionList = $(".promotions");

    for (var i = 0; i < marketingList.length; i++) {
        var marketing = marketingList[i];

        switch (marketing.codexType) {
            case "1":         //直降促销
                addPromotionList("直降");
                $promotionList.append("<li><a><span class=\"label\">直降</span>直降" + marketing.priceOffMarketing.offValue + "元 </a></li>");
                break;
            case "15":        //如果是折扣促销
                addPromotionList("折扣");
                var discountMarketingList = marketing.preDiscountMarketings || [];
                for (var j = 0; j < discountMarketingList.length; j++) {

                    // 计算折扣
                    if ($("#productId").val() == discountMarketingList[j].goodsId) {
                        var discountFlag = discountMarketingList[j].discountFlag;
                        var price = myaccMul(discountMarketingList[j].discountInfo, $("#followPrice").val()).toString();
                        //抹掉分
                        if (discountFlag == '1') {
                            //不采用四舍五入
                            if (price.length - price.indexOf(".") + 1 >= 2) {
                                price = price.substring(0, price.indexOf(".") + 2) + "0";
                            }
                        } else if (discountFlag == '2') {
                            if (price.length - price.indexOf(".") + 1 >= 1) {
                                price = price.substring(0, price.indexOf(".") + 1) + "00";
                            }
                        } else {
                            if (price.length - price.indexOf(".") + 1 >= 3) {
                                price = price.substring(0, price.indexOf(".") + 3);
                            }
                        }
                        $(".price").attr("style", "display:none");
                        $(".oldprice").text($("#oldPrice").val()).parent().show();
                        $(".mark_price").text(price).parent().show();

                    }
                }
                break;
            case "5": //满减促销
                addPromotionList("满减");

                var fullbuyReduceMarketings = marketing.fullbuyReduceMarketings || [];
                for (var j = 0; j < fullbuyReduceMarketings.length; j++) {
                    $promotionList.append("<li><a><span class=\"label\">满减</span>满" + fullbuyReduceMarketings[j].fullPrice + "减" + fullbuyReduceMarketings[j].reducePrice + " </a></li>");
                }
                break;
            case "8": //满折促销
                addPromotionList("满折");

                var fullbuyDiscountMarketings = marketing.fullbuyDiscountMarketings || [];
                for (var j = 0; j < fullbuyDiscountMarketings.length; j++) {
                    $promotionList.append("<li><a><span class=\"label\">满折</span>满" + fullbuyDiscountMarketings[j].fullPrice + "打" + (fullbuyDiscountMarketings[j].fullbuyDiscount * 10) + "折 </a></li>");
                }
                break;
            case "12":    //包邮
                addPromotionList("包邮");

                $("#yunfei").append("<span class=\"label\">包邮</span>支持" + marketing.shippingMoney + "免运费");
                $("#yunFeiShow").attr("style", "display:black;");
                break;
            case "11": // 抢购
                addPromotionList("抢购");

                // 计算折扣价
                var price = accMul(marketing.rushs[0].rushDiscount, $("#followPrice").val());
                $(".mark_price").text(price).parent().show();
                var preprice = $("#oldPrice").val();
                var prepricehtml = "原&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：<del class=\"f14 col6\">" + preprice + "</del>";
                $(".preprice").removeClass("price");
                $(".preprice").html(prepricehtml);
                $(".afterpri").removeClass("price");
                $(".afterpri").html("抢&nbsp;&nbsp;购&nbsp;&nbsp;价：" + "<span class='price'> ¥ " + price + "</span>");

                $promotionList.append("<li><a><span class=\"label\">抢购</span>" + marketing.marketingName + "</a></li>");
                break;
            case "6":     // 满赠
                addPromotionList("满赠");

                var fullbuyPresent = '';
                var fullbuyPresentMarketings = marketing.fullbuyPresentMarketings || [];
                for (var j = 0; j < fullbuyPresentMarketings.length; j++) {
                    fullbuyPresent += "<li><a>" +
                        "<span class=\"label\">赠</span>" + "满" + fullbuyPresentMarketings[j].fullPrice + "元送赠品" + " " +
                        "</a></li>";
                }
                $promotionList.append(fullbuyPresent);
                break;
        }

        //else if(marketing.codexType == 10){
        //    $(".promotions").append("<li><a href=\"javascript:void(0);\" onclick='showMarket(this,1,"+marketing.marketingId+")'><span class=\"label\">团购</span>"+marketing.marketingName+"</a></li>");
        //}

    }
    return marketing;
}

function renderGroupDesc(groupCount, productId) {
    if (groupCount > 0) {
        _.templateSettings.interpolate = /{{([\s\S]+?)}}/g;
        var compiled = _.template('<li><a href="' + basePath + '/queryGoodsGroup/{{ productId }}.html">      \
        <span class="label">优惠套装</span>共有{{ groupCount }}款套装<i class="arrow-right"></i>     \
        </a></li>   \
        ')
        ({
            productId: productId,
            groupCount: groupCount
        });
        addPromotionList("优惠套装");

        $(".promotions")
            .append(compiled);
    }
}


/**
 * KKK mobile 加载商品的促销信息
 */
function loadGoodsPromotion(){


    var productId = $("#productId").val();
    $.get(basePath+"/goodsMarketAndGroup/"+ productId+"-"+$("#brandId").val()+"-"+$("#catId").val()+".html",function(data){

        var marketingList = data['marketing'] || [];
        var groupList = data['group'] || [];
        var groupSize = groupList.length;
        var marketingSize = marketingList.length;
        var promotionSize = marketingSize + groupSize;
        if (promotionSize <= 0) {
            // $(".promotion-value").append("<span class=\"label\">无</span>此商品暂无促销信息");
            // $("#marketBtn").remove();
            $(".promotion-choose").hide();
        }else{
            // /* 促销选择 */
            // $('.promotion-choose .list-value').click(function(){
            //     if(marketingList[0].codexType!=6){
            //         $('.promotions-list').slideToggle('fast');
            //     }
            // });

            var marketingOne = data[0];
            // renderDefaultMarketing(marketingOne, data);
            if (marketingSize > 0) {
                renderMarketingDescList(marketingList);
            }
            if (groupSize > 0) {
                renderGroupDesc(groupSize, productId);
            }

        }
    });
}

/**
 * 显示促销
 * */
function showMarket(obj,num,marketingId){
    var market = $(obj).text().length;
    var market1 = $(obj).text().substring(2,market);
    $("#marketingId").val(marketingId);
    if(num == 1){
        $(".promotion-value").html("");
        $(".promotion-value").append("<span class=\"label\">直降</span>"+market1);
    }else if(num == 2){
        $(".promotion-value").html("");
        $(".promotion-value").append("<span class=\"label\">满减</span>"+market1);
    }else if(num == 8){
        $(".promotion-value").html("");
        $(".promotion-value").append("<span class=\"label\">满折</span>"+market1);
    }else if(num == 12){
        $(".promotion-value").html("");
        $(".promotion-value").append("<span class=\"label\">包邮</span>"+market1);
    }else if (num == 11)
    {
        $(".promotion-value").html("");
        $(".promotion-value").append("<span class=\"label\">抢</span>"+market1);
    }


}

/**
 * 加载优惠券列表
 * */
function loadCoupon(){
    $.ajax({
        url:basePath+"/queryCouponList.htm?productId="+$("#productId").val()+"&cateId="+$("#catId").val()+"&brandId="+$("#brandId").val()+"&thirdId="+$("#thirdId").val(),
        type:"post",
        success:function(data){
            if(null == data || data.length <= 0){
                $("#coupon").append("<span class=\"manjian\">无</span>");
                $("#couponBtn").remove();
            }else{
                for(var i = 0;i<data.length;i++){
                    $("#coupon").append("<span class=\"manjian\" onClick=\"goCoupon();\">"+data[i].couponName+"</span>");
                }
            }
        }
    });
}

/**
 * 跳转至优惠券领取页面
 * */
function goCoupon(){
    location.href=basePath+"/goCoupon-"+$("#productId").val()+"-"+$("#catId").val()+"-"+$("#brandId").val()+"-"+$("#thirdId").val()+".html";
}

/*加载商品评论*/
function loadComment(pageNo,type){
    /*清空相关的div*/
    $("#commentBody").html("");
    var productId=$("#productId").val();
    var params="&productId="+productId;
    params+="&pageNo="+pageNo+"&pageSize=1";
    var haoCount=0;
    var zhongCount=0;
    var chaCount=0;
    //获取所有评论总数
    var count=0;
    /*AJAX查询商品好评*/

    $.ajax({
        url:basePath+"/queryProducCommentForDetail.htm?type=0"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            haoCount=data.rows;
            putPageComment(type,data);
            $("#haoping").text("好评（"+haoCount+"）");
        }
    });

    /*AJAX查询商品中评*/
    $.ajax({
        url:basePath+"/queryCommentCountByType.htm?type=1"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            zhongCount=data;
            $("#zhongping").text("中评（"+zhongCount+"）");
        }
    });

    /*AJAX查询商品差评*/
    $.ajax({
        url:basePath+"/queryCommentCountByType.htm?type=2"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            chaCount=data;
            $("#chaping").text("差评（"+chaCount+"）");
        }

    });
    count = haoCount+zhongCount+chaCount;
    //多少人评论
    $("#commentCount").text(count);
    if(count == 0){
        $("#commentBtn").remove();
        $("#haoPingLv").text("100%");
    }else{
        var haoPingLv=parseInt((haoCount/count) * 100);
        $("#haoPingLv").text(haoPingLv+"%");
    }
}

/*加载商品评论*/
function loadComment1(pageNo,type){
    /*清空相关的div*/
    $("#commentBody").html("");
    var productId=$("#productId").val();
    var params="&productId="+productId;
    params+="&pageNo="+pageNo+"&pageSize=1";
    var haoCount=0;
    var zhongCount=0;
    var chaCount=0;
    //获取所有评论总数
    var count=0;
    /*AJAX查询商品好评*/

    $.ajax({
        url:basePath+"/queryCommentCountByType.htm?type=0"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            haoCount=data;
            $("#haoping").text("好评（"+haoCount+"）");
        }
    });

    /*AJAX查询商品中评*/
    $.ajax({
        url:basePath+"/queryProducCommentForDetail.htm?type=1"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            zhongCount=data.rows;
            putPageComment(type,data);
            $("#zhongping").text("中评（"+zhongCount+"）");
        }
    });

    /*AJAX查询商品差评*/
    $.ajax({
        url:basePath+"/queryCommentCountByType.htm?type=2"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            chaCount=data;
            $("#chaping").text("差评（"+chaCount+"）");
        }

    });
    count = haoCount+zhongCount+chaCount;
    //多少人评论
    $("#commentCount").text(count);
    if(count == 0){
        $("#commentBtn").remove();
        $("#haoPingLv").text("100%");
    }else{
        var haoPingLv=parseInt((haoCount/count) * 100);
        $("#haoPingLv").text(haoPingLv+"%");
    }
}

/*加载商品评论*/
function loadComment2(pageNo,type){
    /*清空相关的div*/
    $("#commentBody").html("");
    var productId=$("#productId").val();
    var params="&productId="+productId;
    params+="&pageNo="+pageNo+"&pageSize=1";
    var haoCount=0;
    var zhongCount=0;
    var chaCount=0;
    //获取所有评论总数
    var count=0;
    /*AJAX查询商品好评*/

    $.ajax({
        url:basePath+"/queryCommentCountByType.htm?type=0"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            haoCount=data;
            $("#haoping").text("好评（"+haoCount+"）");
        }
    });

    /*AJAX查询商品中评*/
    $.ajax({
        url:basePath+"/queryCommentCountByType.htm?type=1"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            zhongCount=data;
            $("#zhongping").text("中评（"+zhongCount+"）");
        }
    });

    /*AJAX查询商品差评*/
    $.ajax({
        url:basePath+"/queryProducCommentForDetail.htm?type=2"+params,
        type:"post",
        async:false,
        success:function(data){
            /*设置所有的行数*/
            chaCount=data.rows;
            putPageComment(type,data);
            $("#chaping").text("差评（"+chaCount+"）");
        }

    });
    count = haoCount+zhongCount+chaCount;
    //多少人评论
    $("#commentCount").text(count);
    if(count == 0){
        $("#commentBtn").remove();
        $("#haoPingLv").text("100%");
    }else{
        var haoPingLv=parseInt((haoCount/count) * 100);
        $("#haoPingLv").text(haoPingLv+"%");
    }
}

/*将查询到的评论加载到页面中*/
function putPageComment(type,data){
    var commentHtml="";
    if(data.list!=null && data.list.length>0){
        for(var l=0;l<data.list.length;l++){
            var comment = data.list[l];
            var star = "star"+comment.commentScore;
            if(comment.isAnonymous=='1'){
                commentHtml+="<div class='list-item'><div class='star'><div class='"+star+" cur'></div>" +
                "</div> <div class='user-item'><span>****</span>" +
                "&nbsp;<span>"+timeStamp2String(comment.buyTime)+"</span></div><p class='text'>"+comment.commentContent+"</p></div>";
            }else{
                commentHtml+="<div class='list-item'><div class='star'><div class='"+star+" cur'></div>" +
                "</div> <div class='user-item'><span>"+comment.customerNickname+"</span>" +
                "&nbsp;<span>"+timeStamp2String(comment.buyTime)+"</span></div><p class='text'>"+comment.commentContent+"</p></div>";
            }
        }
    }else{
        commentHtml+="<div class='list-item'><div class='user-item'><span></span></div><p class='text'>暂无商品评论</p></div>";
    }
    $("#commentBody").append(commentHtml);
}

/**
 * 点击好评时调用
 * */
$("#haoping").click(function(){
    $(this).addClass('selected').siblings().removeClass('selected');
    loadComment(1,0);
});

/**
 * 点击中评时调用
 * */
$("#zhongping").click(function(){
    $(this).addClass('selected').siblings().removeClass('selected');
    loadComment1(1,1);
});

/**
 * 点击差评时调用
 * */
$("#chaping").click(function(){
    $(this).addClass('selected').siblings().removeClass('selected');
    loadComment2(1,2);
});

var allProductList = new Array();
/**
 * 加载所属商品下的所有的货品并初始化规格值按钮
 * @param productList 货品列表
 */
function loadAllProduct(productList)
{
    allProductList = productList;
    /*如果货品信息为空或者长度为0，设置所有的规格值为空*/
    if (allProductList == null || allProductList.length == 0) {
        $("._sku").addClass("disabled");
    }
    else {
        //控制规格值得按钮
        controlSpecBtn(allProductList,null,true,false);
    }
}


/*控制规格值按钮*/
function controlSpecBtn(tempProductList,obj,bool,checkRunAgain)
{
    var specList = $("._sku");
    var canSelSpec = new Array();
    /*已经获取到匹配的货品取出所有的关联的规格值*/
    for (var i = 0; i < tempProductList.length; i++)
    {
        //循环可选择的货品的集合获取所有的可选择的规格按钮
        var goodsSpec = tempProductList[i].specVo;
        for (var k = 0; k < goodsSpec.length; k++) {
            canSelSpec.push(goodsSpec[k].goodsSpecDetail.specDetailId);
        }
    }
    /*控制页面中的按钮*/
    for (var i = 0; i < specList.length; i++)
    {
        var count = 0;
        //循环去可选按钮中匹配是否存在
        for (var k = 0; k < canSelSpec.length; k++) {
            if (canSelSpec[k] == $(specList[i]).attr("data-value")) {
                count = count - 1 + 2;
            }
        }
        //如果循环到的按钮匹配可选按钮的数量为0，则标记为不可选中，否则就是可选中
        if (count == 0) {
            $(specList[i]).remove();;
            /*删除保存的以选中记录,并添加新的*/
        }
        else {
            $(specList[i]).removeClass("disabled");
        }
    }
    //如果传递过来的对象不为空并且，再次执行计算按钮的标记为真，并且方法标记为false时执行以下方法，重新计算可选择的按钮
    if(!bool && obj!=null && checkRunAgain){
        $(obj).removeClass("disabled");
        clickSpecDetail(obj,false);
    }
}

/*点击规格值的时候*/
function clickSpecDetail(obj,bool)
{
    var self = $(obj);
    if (self.hasClass('disabled')) {
        //return false;
        self.removeClass('disabled');
    }else{
        //选中自己，兄弟节点取消选中
        self.addClass('selected').siblings().removeClass('selected');
    }
    /*删除保存的以选中记录,并添加新的*/
    $(".sel_spec_"+$(obj).attr("data-parent")).html("“"+$(obj).attr("title")+"”");

    self.append("<i></i>");
    var specList = $("._sku");
    var selSpecList = new Array();
    /*获取已经选中的规格值*/
    for (var i = 0; i < specList.length; i++) {
        if ($(specList[i]).hasClass("selected")) {
            selSpecList.push(specList[i]);
        }
    }
    var tempProductList = new Array();
    /*根据已经选中的规格值循环所有的货品筛选出可以被选中的货品*/
    var allRunCount=0;
    for (var j = 0; j < allProductList.length; j++)
    {
        //获取到货品的关联的规格
        var goodsSpec = allProductList[j].specVo;
        var count = 0;
        //循环货品的规格去选中的规格中匹配
        for (var k = 0; k < goodsSpec.length; k++)
        {
            for (var i = 0; i < selSpecList.length; i++)
            {
                //如果当前循环的规格和循环到的货品的规格相匹配就给标记+1
                if (goodsSpec[k].goodsSpecDetail.specDetailId == $(selSpecList[i]).attr("data-value")) {
                    count = count - 1 + 2;
                    if (count == 0) {
                        $(specList[i]).addClass("disabled");
                        /*删除保存的以选中记录,并添加新的*/
                    }
                    else {
                        $(specList[i]).removeClass("disabled");
                    }
                };
            };
        };
        /*如果匹配的数量大于等于已经选中的数量则说明完全匹配，跳转到货品详情页*/
        if (count >= selSpecList.length) {
            if($("#allSpecLength").val()==count){
                $("#productId").val(allProductList[j].goodsInfoId);
                $("#isAjax").val("1");
                $("#paramGoodsForm").attr("action",basePath+"/item/"+allProductList[j].goodsInfoId+".html");
                $("#paramGoodsForm").submit();
            }
            tempProductList.push(allProductList[j]);
        }else{
            //如果匹配的数量小于已经选中的数量则给标记+1
            allRunCount+=1;
        };
    }
    //如果标记等于所有的货品的长度说明没有匹配的货品，就保留当前选中的按钮，重新计算其他可选的按钮
    if(allRunCount==allProductList.length){
        //选中自己，其他的全部取消选中
        $("._sku").removeClass('selected');
        /*self.removeClass("disable");*/
        //取消完所有的选中之后选中当前传递的对象
        self.addClass('selected');
        self.append("<i></i>");
        //再次调用当前的方法进行计算
        clickSpecDetail(self,false);
        //设置控制按钮的方法循环执行计算可选按钮的标记为true
        bool=true;
    }
}

/*把已经选择的属性置为选中状态*/
function loadChooseParam() {

    var params = $(".chooseParamId");
    var specList = $("._sku");
    if (params.length > 0) {
       for (var i = 0; i < params.length; i++) {
            for (var k = 0; k < specList.length; k++) {
                $(specList[k]).removeClass("disabled");
                if ($(specList[k]).attr("data-value") == $(params[i]).val()) {
                    $(specList[k]).addClass("selected");
                    $(specList[k]).addClass("disabled");


                }
            }
        }
    }
    /***
     * 获取到页面中所有已选中的规格值名称 并拼接到已选处显示出来
     * */
    var specNamesss = "";
    $(".specNames").each(function(){
        var sss = $(this).find("div").find("a");
           $(sss).each(function(){
               if($(this).hasClass("selected")){
                   specNamesss += $(this).html();
               }
           });
    });
    $("#yixuan").append(specNamesss);
}


/*处理时间格式化*/
function timeStamp2String(time){
    var date=new Date(parseFloat(time));
    var datetime = new Date();
    datetime.setTime(date);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    //return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
    return year + "-" + month + "-" + date;
}

/*初始化已经选择的城市*/
function loadInit(){
    $.ajax({
        type: 'post',
        url:basePath+"/getAllProvince.htm",
        async:false,
        success: function(data) {
            var provinceHtml="";
            for(var i=0;i<data.length;i++){
                provinceHtml+="<dd onClick='loadCity("+data[i].provinceId+",this);'>"+data[i].provinceName+"</dd>";
            }
            $(".province").html(provinceHtml);
        }
    });
}

/*根据点击的省份加载城市*/
function loadCity(pid,pro){
    $(".ch_province").val($(pro).text());
    $("#okProvince").text($(pro).text());
    $.ajax({
        url:basePath+"/getAllCityByPid.htm?provinceId="+pid,
        type:"post",
        async:false,
        success:function(data){
            var cityHtml="";
            for(var i=0;i<data.length;i++){
                cityHtml+="<dd onClick='loadDistinct("+data[i].cityId+",this);'>"+data[i].cityName+"</dd>";
            }
            $(".city").html(cityHtml);
        }
    });
    $('.area-box-lv1').hide();
    $('.area-box-lv2').show();

}

/*根据点击的城市加载区县*/
function loadDistinct(cid,city){
    $(".ch_city").val($(city).text());
    $("#okProvince1").text($("#okProvince").text());
    $("#okCity").text($(city).text());
    $.get(basePath+"/getAllDistrictByCid.htm?cityId="+cid,function(data){
        var distinctHtml="";
        for(var i=0;i<data.length;i++){
            distinctHtml+="<dd onClick='checkDistinct("+data[i].districtId+",this);'>"+data[i].districtName+"</dd>";
        }
        $(".district").html(distinctHtml);
    });
    $('.area-box-lv2').hide();
    $('.area-box-lv3').show();
}

/*点击区县的时候*/
function checkDistinct(dId,dis){
    $(".ch_distinct").val($(dis).text());
    /*当点击最后一级区县的时候   把之前选择的省份,城市和区县信息拼装成一个字符串并提交到后台控制器*/
    $(".ch_address").val($(".ch_province").val()+$(".ch_city").val()+$(".ch_distinct").val());
    $(".ch_distinctId").val(dId);
    $("#isAjax").val("0");
    $("#paramGoodsForm").submit();
}

function checkGoodsNum(){
    var goodsNum = $(".product_count").val();
    var   r   =/^\d+$/g;
    if(r.test(goodsNum)){
        if(parseInt(goodsNum) < 1){
            $(".product_count").val("1");
        }
    }else{
        $(".product_count").val("1");
    }

}

/**
 * 加载购物车数量
 * */
function loadShopCartNum() {
    var goodsNums = 0;
    $.ajax({
        url: basePath + "/myshoppingmcartNum.htm",
        type: "post",
        success: function (data) {
            $("#shopCartNum").text(data || 0);
        }
    });
}

/**
 * 查询该用户是否收藏该商品
 * */
function loadCollection(){
    var productId = $("#productId").val();
    $.ajax({
        url:basePath+"/ajaxcollection.htm?productId="+productId,
        type:"post",
        success:function(data){
            if(data >= 1){
                $(".collection").addClass("selected");
            }
        }
    });
}

/**
 * 跳转到评论详情页
 * */
function showPingLun(){
    var productId = $("#productId").val();
    location.href=basePath+"/showPingLun.htm?productId="+productId;
}

/**
 * 关闭选择规格后的蒙层
 * */
function offOpacity(){
    $('.opacity-bg-4').remove();
    $('.pro-chose').hide();
}

$(function(){
    /*点击加按钮*/
    $(".num_plus").click(function(){
        var count = parseFloat($(".product_count").val());
        $(".product_count").val(parseFloat(count + parseFloat(1)));
    });

    /*点击减按钮*/
    $(".num_minus").click(function(){
        var count = parseFloat($(".product_count").val());
        if(count >1){
            $(".product_count").val(parseFloat(count - parseFloat(1)));
        }
    });

    /**
     * 加入购物车
     * */
    var addCartNum=0;
     $("#add_cart").click(function(){



        /*如果选中的规格小于所有的规格,表示规格选择不完全,不允许加入购物车*/
        if($(".selected").length<parseFloat($("#allSpecLength").val())){
            setTimeout(function(){
                $('.tip-box').remove();
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>请选择规格！</h3></div></div>');
            },1000);
            setTimeout(function(){
                window.location.reload();
            },2000)
            return ;
        }
        /*如果购买数量大于剩余库存不允许购买*/
        //$(".tip_text").html("库存不足!");
        //alert("库存不足！");

        var count = parseFloat($(".product_count").val());
        var stock = parseFloat($("#productStock").val());
        var goodsInfoAdded=parseFloat($("#goodsInfoAdded").val());
        if(goodsInfoAdded!=1){
            setTimeout(function(){
                $('.tip-box').remove();
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>货品已下架！</h3></div></div>');
            },1000);
            setTimeout(function(){
                window.location.reload();
            },2000)

            return;
        }
        if(count<=stock){
            addCartNum=addCartNum+1;
            $.ajax({
                url:basePath+"/addproducttoshopcarnew.htm?goodsNum="+$(".product_count").val()+"&productId="+$("#productId").val()+"&distinctId="+$(".ch_distinctId").val(),
                type:"post",
                success:function(data){
                    if(data > 0){
                        setTimeout(function(){
                            $('.tip-box').remove();
                            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>成功加入购物车</h3></div></div>');
                        },1000);
                        setTimeout(function(){
                            window.location.reload();
                        },2000)
                    }else if(data == -1){
                        setTimeout(function(){
                            $('.tip-box').remove();
                            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>购物车已达上限</h3></div></div>');
                        },1000);
                        setTimeout(function(){
                            window.location.reload();
                        },2000)
                    }else{
                        setTimeout(function(){
                            $('.tip-box').remove();
                            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>加入购物车失败</h3></div></div>');
                        },1000);
                        setTimeout(function(){
                            window.location.reload();
                        },2000)
                    }
                }
            });
            //location.href=basePath+"/addproducttoshopcarnew.htm?goodsNum="+$(".product_count").val()+"&productId="+$("#productId").val()+"&distinctId=1103";
        }else{
            //if(addCartNum==0){
            //$(".buy_now_tip").click();
            //}
            setTimeout(function(){
                $('.tip-box').remove();
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>库存不足！</h3></div></div>');
            },1000);
            setTimeout(function(){
                window.location.reload();
            },2000)


            return;
        }
    });

    /**
     * 收藏商品
     * */
    $(".collection").click(function(){
        var districtId = $(".ch_distinctId").val();
        var goodsprice = $("#followPrice").val();
        var productId = $("#productId").val();
        var _this=$(this);
        $.post(basePath+"/saveAtte.htm",{productId:productId,districtId:districtId,goodsprice:goodsprice},function(data){
            if(data=="-2"){
                //返回结果为2代表用户未登录，跳转到登录页面
                location.href=basePath+"/loginm.html?url=/customercenter.html";
            }
            else{
                if(_this.hasClass("selected")) {
                    _this.removeClass("selected");
                } else {
                    _this.addClass("selected");}
            }
        });
    });

});


//乘法函数
function accMul(arg1, arg2) {
    if(arg1==null){
        return;
    }
    if(arg2==null){
        return;
    }
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    }
    catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    }
    catch (e) {
    }
    return (Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)).toFixed(2);
}

//自定义乘法函数
function myaccMul(arg1, arg2) {
    if(arg1==null){
        return;
    }
    if(arg2==null){
        return;
    }
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    }
    catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    }
    catch (e) {
    }
    return (Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)).toFixed(3);


}