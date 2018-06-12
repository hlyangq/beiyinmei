var basePath = $("#basePath").val();
var backPrice=$("#backorderprice").attr("placeholder");

$(function(){
	/*退款*/
    $("#submitButtonprice").click(function() {
        if($(".flag_saved").val()=="1"){
            return false;
        }else {
            $(".backReasonContent").text("");
            $(".applyCredentialsContent").text("");
            $(".backPriceContent").text("");
            $(".backRemarkContent").text("");
            $(".uploadDocumentContent").text("");
            var backReason = $("#backReasons").text();
            if (backReason == '请选择') {
                $(".backReasonContent").text("请选择退单原因！");
                return;
            }
            
            var backRemark = $("#backRemark").val();
            if (backRemark == '') {
                $(".backRemarkContent").text("问题说明不能为空！");
                return;
            }
            //$(".upload_pics").attr("order-id")
            var showPics = $(".share" + $(".upload_pics").attr("order-id"));
            var imgs = '';
            for (var i = 0; i < showPics.length; i++) {
                imgs += $(showPics[i]).val() + ",";
            }

            if ($("#backPrice").val() == '') {
                $("#backPrice").attr("value", $(".back_price").val());
            }
            $(".flag_saved").val("1");
            $(".upload_documents").attr("value", imgs);
            $("#upload-form").removeAttr("target");
            $("#upload-form").attr("action", basePath+"/back/keepBackOrderInfoprice.htm");
            $("#upload-form").submit();
        }
    });
    //退货
    $("#submitButtongoods").click(function() {
    	if($(".flag_saved").val()=="1"){
    		return false;
    	}else {
            //checkNum();
            if(flag >0){
                return ;
            }
    		$(".backReasonContent").text("");
    		$(".applyCredentialsContent").text("");
    		$(".backPriceContent").text("");
    		$(".backRemarkContent").text("");
    		$(".uploadDocumentContent").text("");

            var _checkStatus=$("#isUseCoupon").val();
            if(_checkStatus=='0'){
                var goods = $(".choosegood");
                var isOk = false;
                for(var i=0;i<goods.length;i++){
                    if($(goods[i]).hasClass("checked")){
                        isOk = true;
                    }
                }
                if(!isOk){
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请选择退货商品！</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                    return;
                }
            }

    		var showPics = $(".share" + $(".upload_pics").attr("order-id"));
    		var imgs = '';
    		for (var i = 0; i < showPics.length; i++) {
    			imgs += $(showPics[i]).val() + ",";
    		}

    		
    		if ($("#backPrice").val() == '') {
    			$("#backPrice").attr("value", $(".back_price").val());
    		}

            var backway = $("div.returen-way").children(".selected").attr("backway");
            if(backway != null){
                $("#backWay").val(backway);
            }

    		$(".flag_saved").val("1");
    		$(".upload_documents").attr("value", imgs);
    		$("#upload-form").removeAttr("target");
    		$("#upload-form").attr("action", basePath+"/back/keepBackOrderInfo.htm");
    		$("#upload-form").submit();
    	}
    });
    
    //上传凭证
    $(".upload_pics").change(function() {
        if($(this).val()!=""){
            var orderId = $(this).attr("order-id");
            var shareImg = $(".share" + orderId);
            var waitImg = $("#wait_img"+orderId);
            gid = orderId;
            if(waitImg.length>0) return;
            if(shareImg.length==3) {
                $("#err_img_"+orderId).show();
                setTimeout(function(){
                    $("#err_img_"+orderId).hide();
                },3000);
                return;
            }
            $("#upload-form").attr("action",basePath+"/back/upload.htm");
            $("#upload-form").submit();
            $("#show_pics"+orderId).find("li:last").before('<li id="wait_img'+orderId+'"><a href="#"><image src="'+basePath+'/images/load.jpg"/></a></li>');
        }
    });
});

//退单金额的价格
var back_price = 0;
//保存要退的商品Id
var backGoodsId_sum = 0;

function mit(obj){
    if( !$(obj).hasClass("disabled")) {
        var minNum = 0;
        var num = 0;
        var nowNum = $(obj).next().val();
        var goodsprice = $(obj).next().attr("attr-price");
        var setNum = 0;
        if (nowNum > minNum) {
            setNum = Subtr(nowNum, 1);
            $(obj).parent().find(".add").removeClass("disabled");
        } else {
            setNum = nowNum;
        }
        $(obj).next().val(setNum);
        if (setNum == 0) {
            $(obj).addClass("disabled");
        }
        num = Subtr(nowNum, setNum);
        var subprice = accMul(goodsprice, num);
        var newprice = Subtr(backPrice, subprice);
        $("#backorderprice").attr("placeholder", newprice);
        backPrice = newprice;
        $("#backPrice").val(backPrice);
    }
}


function add(obj){
    if( !$(obj).hasClass("disabled")) {
        //获取 可用的最大数量
        var maxNum = $(obj).prev().attr("attr-maxnum");
        var goodsprice = $(obj).prev().attr("attr-price");
        var nowNum = $(obj).prev().val();
       var setNum = 0;
        if (Subtr(maxNum, nowNum) <= 0) {
            setNum = nowNum;
        } else {
            setNum = accAddInt(nowNum, 1);
            $(obj).parent().find(".reduce").removeClass("disabled");
        }newprice
        $(obj).prev().val(setNum);
        if (setNum == maxNum) {
            $(obj).addClass("disabled");
        }
        var addprice = accMul(goodsprice, 1);
        var newprice = accAdd(backPrice, addprice);
        $("#backorderprice").attr("placeholder", newprice);
        backPrice = newprice;
        $("#backPrice").val(backPrice);
    }
}


/**
 * 上传文件成功后触发事件
 * @param msg
 */
function callback(msg) {
    //上传失败
    if(msg.split(",").length<2) {
        if(msg=='101') {
            $("#wait_img"+gid).remove();
            dia(5);
        } else if(msg=='102') {
            $("#wait_img"+gid).remove();
            dia(4);

        }
        return;
    }
    //上传成功
    var imageName = msg.split(",")[0],newImg ="";
    var orderId = msg.split(",")[1];
    $("#wait_img"+orderId).remove();
    if(imageName.indexOf("!")!= -1){
        newImg = imageName.substring(0,imageName.indexOf("!")+1)+56;
    }else{
        newImg = imageName;
    }
    $("#show_pics"+orderId).find("li:last").before('<li class="share_img"><input class="share'+orderId+'" type="hidden" value="'+imageName+'" ></input><span style="display:none;" class="del-img"><a href="javascript:;"  onclick="show_delPic('+orderId+');">x</a></span><img id="share'+orderId+'"  width="68px" height="68px" alt="" src="'+newImg+'" /></li>');
    $(".share_img").click(function() {
        $(".share_img").removeClass("selected");
        $(this).addClass("selected");
        $(this).find(".del-img").show();
    });
}

function show_delPic(orderId){
	var shareImg = $(".share"+orderId);
    $(".upload_pics").val("");
    for(var i=0; i<shareImg.length; i++) {
        if(shareImg[i].parentElement.className.indexOf("selected")>0) {
            shareImg[i].parentElement.remove();
        }
    }
}
var  flag =0;
function checkNum(){
    var reg = new RegExp("^[0-9]*$");
    var staCheck = $("#staCheck").val();
    backPrice =0;
    $('input[class="back_goods_id"]').each(function(){
        if(reg.test(parseInt($(this).val()))){
            $(this).parent().next(".numError").text("");
            if(parseInt($(this).val())>parseInt($(this).attr("attr-maxnum"))){
                $(this).parent().next(".numError").text("您输入的数量有误！");
                flag = 1;
            }else{
                var goodsprice = $(this).attr("attr-price");
                var num =parseInt($(this).val());
                var addprice = accMul(goodsprice, num);
                var newprice = accAdd(backPrice, addprice);
                if(staCheck =='' || staCheck !=1){
                    $("#backorderprice").attr("placeholder", newprice);
                    backPrice = newprice;
                    $("#backPrice").val(backPrice);
                }
            }
        }else{
            $(this).parent().next(".numError").text("您输入的数量有误！");
           flag = 1;
        }
    });
}