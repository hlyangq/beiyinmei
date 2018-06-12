/**
 * Created by chenpeng on 2016/10/10.
 */

//weixin pay
var basePath = $("#basePath").val();
function toRecharge(){

    var result = true;
    if($("#rechargePrice").val().trim()==''){
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写充值金额</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        result =  false;
    }else if(!(/^([0-9][0-9]*(\.[0-9]{1,2})?|0\.(?!0+$)[0-9]{1,2})$/.test( $("#rechargePrice").val())) || $("#rechargePrice").val()<=0 ||$("#rechargePrice").val()>1000000){
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入正确的金额</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        result = false;
    }
    if(result){
        if($("#zhifubao") && !$("#zhifubao").is(":hidden")){
            $("#recharge").attr("action", basePath+"/member/zhifubaopecharge.html");
            $("#recharge").submit();
        }else if($("#weixin") && !$("#weixin").is(":hidden")){

            $.ajax({
                url:basePath+'/member/wxrecharge.html',
                type:'post',
                data:{"orderPrice":$("#rechargePrice").val()},
                success:function(msg){
                    /*if(msg==''){
                        location.href=basePath+"/customer/myorder.html";
                    }*/
                    $("#orderId").val(msg.orderId);
                    var recharg = dialog({
                        id: 'deposit-id',
                        width : 260,
                        content : '<form id="depositpay"> <div style="padding:0 5px"><p class="tc">请确认您是否已完成充值</p><p class="tc"></p><div class="dia-buttons"><a href="javascript:;" onclick="closeDialog();" class="cancel">重新选择方式</a><a href= "'+ basePath +'/member/showmypredeposits.html" id="depositok" class="ok" style="margin-left: 4%;">已充值完成</a></div></div>'
                    });
                    recharg.showModal();
                    onBridgeReady(msg.appId,msg.timeStamp,msg.nonceStr,msg.package,msg.sign);
                }
            });
        }
    }


}
function closeDialog(){
    dialog.getCurrent().close();
}

function onBridgeReady(appId,timeStamp,nonceStr,package,paySign){
    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
            "appId" : appId,     //公众号名称，由商户传入
        "timeStamp": timeStamp,         //时间戳，自1970年以来的秒数
        "nonceStr" : nonceStr, //随机串
        "package" : package,
        "signType" : "MD5",         //微信签名方式：
        "paySign" : paySign //微信签名
    },function(res){
        if(res.err_msg == "get_brand_wcpay_request：ok" ) {
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>充值成功！</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
                location.href= basePath+"/customer/customerTradeInfo.html";
            },1000);
        } else if(res.err_msg == "get_brand_wcpay_request：cancel"){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>充值已取消！</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
                location.href= basePath+"/customer/customerTradeInfo.html";
            },1000);
        } else if(res.err_msg == "get_brand_wcpay_request：fail"){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>充值失败！</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
                location.href= basePath+"/customer/customerTradeInfo.html";
            },1000);
        }
        }
    );
}
