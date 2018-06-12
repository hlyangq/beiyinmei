<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单-<#if type?? && type='1'>待付款<#elseif  type?? && type='3'>待收货<#elseif  type?? && type='4'>待评价<#elseif  type?? && type='6'>待退款/退货<#else>全部</#if>订单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
     <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
</head>

<body>
<input type="hidden" id="type" value="${type!''}">
<input type="hidden" id="basePath" value="${basePath}">
<div class="order content-order-all" id="items">
<#if (pb.list?size!=0)>
    <#list pb.list as order>
        <#assign cFlag=0 />
    <div class="order-item">
        <div class="order-number">
            <div class="list-item">
                <h3 class="item-head"><label for="">订单号：</label><span>${order.orderNo}</span>
                    <#list order.goods as good>
                        <#if good.evaluateFlag== '0'>
                            <#assign cFlag=cFlag+1 />
                        </#if>
                    </#list>
                    <span class="curValue text-them">
                        <#if order.orderStatus??>
                            <#if order.orderStatus=="0">
                                <#if order.orderLinePay=="0">
                                    等待发货
                                <#else>
                                    等待付款
                                </#if>
                            <#elseif (order.orderStatus=="1" || order.orderStatus=="5" || order.orderStatus=="6") >
                                等待发货
                            <#elseif order.orderStatus=="2">
                                等待收货
                            <#elseif (order.orderStatus=="3" && cFlag>0) >
                                等待评价
                            <#elseif order.orderStatus=="3" && cFlag=0>
                                交易完成
                            <#elseif (order.orderStatus=="4") >
                                交易取消
                            <#elseif order.orderStatus=="7">
                                退货审核中
                            <#elseif order.orderStatus=="8">
                                同意退货
                            <#elseif order.orderStatus=="9">
                                拒绝退货
                            <#elseif order.orderStatus=="10">
                                待商家收货
                            <#elseif order.orderStatus=="11">
                                退货结束
                            <#elseif order.orderStatus=="15">
                                退款审核中
                            <#elseif order.orderStatus=="13">
                                拒绝退款
                            <#elseif order.orderStatus=="14">
                                已提交退货审核
                            <#elseif order.orderStatus=="16">
                                商家收货失败
                            <#elseif order.orderStatus=="17">
                                已退款
                            <#elseif order.orderStatus=="18">
                                退款成功
                            </#if>
                        </#if>
                    </span>
                </h3>
            </div>
        </div>
        <div class="order-info">
            <#if (order.goods?size>2)>
            <div class="list-body-box">
                <div class="list-item">
                    <div class="box-body">
                        <ul>
                            <#list order.goods as good>
                                <li>
                                    <a href="${basePath}/customer/detail-${order.orderId}.html">
                                        <img width="78" height="78" title="${(good.goodsName)!''}" alt="${(good.goodsName)!''}" src="${(good.goodsImg)!''}" />
                                    </a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </div>
            <#else>
                <#list order.goods as good>
                <div class="list-body-line">
                    <div class="list-item">
                        <a href="${basePath}/customer/detail-${order.orderId}.html">
                            <div class="pro-item">
                                <div class="propic">
                                    <img width="78" height="78" title="${(good.goodsName)!''}" alt="${(good.goodsName)!''}" src="${(good.goodsImg)!''}" />
                                </div>
                                <div class="prodesc">
                                    <h3 class="title"><#if good.isPresent?? && good.isPresent == '1'><span style="color: white;background-color: lightgrey;">&nbsp;赠品&nbsp;</span></#if> ${good.goodsName!''}</h3>
                                    <p class="price">
                                        <#--
                                        ¥&nbsp;
                                        <span class="num">

                                            <#if good.goodsPrice??>
                                                ${good.goodsPrice?string('0.00')}
                                            </#if>

                                        </span>
                                        -->
                                    </p>
                                    <span class="pro-num">×${good.goodsNum}</span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
                </#list>
            </#if>
        </div>
         <div class="order-bottom">
                <div class="list-item">
                    <h3 class="item-head">
                        <label for="">实付款：</label><span class="pay-money">￥
                        <#if order.moneyPaid??>
                        ${order.moneyPaid?string('0.00')}
                        </#if></span>
                    </h3>
                    <div class="too-btn">
                        <#if order.orderStatus??>
                            <#if order.orderStatus=="0">
                                <#if order.payId?? && order.payId==1>
                                    <a href="javascript:void(0);" class="btn pay-btn" onclick="payOrder('${order.orderId}','${order.moneyPaid}')">立即支付</a>
                                </#if>
                                <a href="javascript:void(0);" class="btn" onClick="cancelOrder('${order.orderId}')">取消订单</a>
                            <#elseif (order.orderStatus=="2")>
                                <#list order.expressno as expressno>
                                    <#if expressno_index==0>
                                        <a href="http://m.kuaidi100.com/index_all.html?type=${expressno.expressName!''}&postid=${expressno.expressNo!''}&callbackurl=${sys.bsetAddress}/mobile/customer/myorder-3-1.html" class="btn">查看物流</a>
                                    </#if>
                                </#list>
                                <a href="javascript:void(0);" class="btn" onClick="comfirmgoods('${order.orderId}')">确认收货</a>
                            </#if>
                        <#if isBackOrder==1>
                            <#if (order.orderStatus=="3")>
                                <a  href="${basePath}/comment-${order.orderId}.html" class="<#if cFlag gt 0>btn<#else>btn-grey</#if>">评价</a>
                               <#-- <a href="javascript:void(0)" class="btn" onclick="addCar('${order.orderId}')">再次购买</a>-->
                                <a class="btn"  href="javascript:;" onclick="timeBackOrder(${order.orderId})">申请退货</a>
                            <#elseif order.orderStatus=="1"|| order.orderStatus=="4" || order.orderStatus=="5" || order.orderStatus=="6">
                               <#-- <a href="javascript:void(0)" class="btn" onclick="addCar('${order.orderId}')">再次购买</a>-->
                            <#elseif (order.orderStatus=="13" ||order.orderStatus=="15" ||order.orderStatus=="18") >
                                <a class="btn" href="${basePath}/customer/backorderpricedetail-${order.orderId}.html">退款详情</a>
                            <#elseif order.orderStatus=="8">
                                    <a class="btn fill-flow" href="javascript:void(0)" onclick="expressInfo('${(order.orderNo)!''}')" style="width:100px">填写物流信息</a>
                            <#elseif  (order.orderStatus=="7" ||
                            order.orderStatus=="8" ||
                            order.orderStatus=="9" ||
                            order.orderStatus=="10" ||
                            order.orderStatus=="11" ||
                            order.orderStatus=="14" ||
                            order.orderStatus=="16" ||
                            order.orderStatus=="17" ||
                            order.orderStatus=="12")>
                                <#if order.orderStatus=="8">
                                    <a class="btn fill-flow" href="javascript:void(0)" onclick="expressInfo('${(order.orderNo)!''}')">物流信息</a>
                                </#if>
                                <a class="btn" href="${basePath}/customer/backordergoodsdetail-${order.orderId}.html">退货详情</a>
                            </#if>
                            <#if ( order.orderStatus=="5" || order.orderStatus=="6") >
                                <a  href="${basePath}/customer/applybackgoods-${order.orderId}.html">申请退货</a>
                            </#if>
                            <#if order.orderStatus=="1">
                                <a  class="btn"  href="${basePath}/customer/mapplybackmoney-${order.orderId}.html">申请退款</a>
                            </#if>
                        <#elseif  isBackOrder==2>
                            <#if (order.orderStatus=="3")>
                            <a  href="${basePath}/comment-${order.orderId}.html" class="<#if cFlag gt 0>btn<#else>btn-grey</#if>">评价</a>
                           <#-- <a href="javascript:void(0)" class="btn" onclick="addCar('${order.orderId}')">再次购买</a>-->
                            <#elseif order.orderStatus=="1"|| order.orderStatus=="4" || order.orderStatus=="5" || order.orderStatus=="6">
                              <#--  <a href="javascript:void(0)" class="btn" onclick="addCar('${order.orderId}')">再次购买</a>-->
                            </#if>
                        </#if>
                        </#if>
                        <!--再次买 -->
                        <a href="javascript:void(0)" class="btn" onclick="addCar('${order.orderId}')">再次购买</a>
                    </div>
                </div>
            </div>
       </div>
    </#list>
<#else>
    <div class="no-orders">
        <div class="list-item">
            <i class="dingdan"></i>
            <h3>您还没有相关的订单</h3>
            <p>再去逛逛吧~</p>
        </div>
        <div class="list-item">
            <a class="btn btn-full" href="${basePath}/main.html">去逛逛</a>
        </div>
    </div>
</#if>
</div>
<div class="list-loading" id="showmore"  style="display:none" >
    <img alt="" src="${basePath}/images/loading.gif">
    <span>加载中……</span>
</div>



<div class="opacity-bg-1" style="display:none"></div>
<input id="payOrderId" value="" type="hidden">
<input id="status" value="0" type="hidden">

   <div class="tip-box" id="confirm_order" style="display:none">
         <div class="tip-body2">
             <h3 id="tiptitle">是否确认收货？</h3>
             <div class="btn-group">
                 <a class="btn btn-grey" href="javascript:;" onclick="cancelComfirmGoods()">取&nbsp;消</a>
                 <a class="btn btn-them" href="javascript:;" onclick="comfirmGoodsSucc()">确&nbsp;定</a>
             </div>
         </div>
     </div>

<form class="fill-flow-form" style="display:none;" id="fillForm">
    <input type="hidden" id="orderNo" name="orderNo" value=""/>
    <div class="list-item">
        <h3 class="item-head pr0"><label for="">物流公司</label>
        </h3>
        <div class="list-value">
            <div class="shuoming">
                <input type="text" class="text" placeholder="请填写正确的物流公司" name="wlname" id="wlname" onBlur="wuliuname()">
                <label id="yanzhengname"></label>
            </div>
        </div>
    </div>
    <div class="list-item">
        <h3 class="item-head pr0"><label for="">物流单号</label>
        </h3>
        <div class="list-value">
            <div class="shuoming">
                <input type="text" class="text" placeholder="请填写准确的物流单号"  name="wlno" id="wlno" onBlur="wuliudanhao()">
                <label id="yanzhengno"></label>
            </div>
        </div>
    </div>
</form>
 <script src="${basePath}/js/customer/myorder.js"></script>
<#--<script src="${basePath}/js/customer/backorder.js"></script>-->
 <script>
     var zhifu ='';
     $(function(){
          zhifu = '<div class="pay-btns">';
         $.ajax({
             url: basePath+'/querypayments.htm',
             async:false,
             success:function(result){
                 if(result != ''){
                     for(var i=0;i<result.length;i++){
                         if(result[i].isOpen == '1' && result[i].payType=='3'&& isWeiXin()){
                             zhifu += '<a class="btn btn-full mb10 weixin" href="javascript:;"><i></i>微信支付</a>';
                         }else if(result[i].isOpen == '1' && result[i].payType=='1' && !isWeiXin()){
                             zhifu += '<a class="btn btn-full mb10 weixin" href="javascript:;"><i></i>支付宝支付</a>';
                         }else if(result[i].isOpen == '1' && result[i].payType=='5'){
                             zhifu += '<a class="btn btn-full mb10 yue" href="javascript:;"><i></i>预存款支付</a>';
                         }
                     }
                 }
                 zhifu += '</div>';
             }
         });
     })

     //判断是否是微信打开
     function isWeiXin(){
         var ua = window.navigator.userAgent.toLowerCase();
         if(ua.match(/MicroMessenger/i) == 'micromessenger'){
             return true;
         }else{
             return false;
         }
     }
  $(window).scroll(function(){
	    if($(this).scrollTop() >= ($('body').height() - $(window).height())){
	     show();
	    }
    });
  function payOrder(orderId,orderPrice) {
      /*if(isWeiXin()){
          zhifu = '<div class="pay-btns"><a class="btn btn-full-grey yue"><i></i>预存款支付</a></div>';
      }else{
          zhifu = '<div class="pay-btns"><a class="btn btn-full-grey yue"><i></i>预存款支付</a></div>';
      }*/
      var payBox = dialog({
          width: 300,
          title: '选择一种支付方式',
          content: zhifu,
          onshow: function (orderId) {
              var weixinPay = dialog({
                  width: 260,
                  content: '<p class="tc">您需要为该笔订单支付</p><p class="tc"><span class="text-them" id="paymoney">￥55.00</span></p> ',
                  okValue: '继续支付',
                  cancelValue: '返回',
                  ok: function () {
                      if(isWeiXin()){
                          //确认离开，跳转到下一页面
                          $.ajax({
                              type: "POST",
                              url: "${basePath}/getmwxparam.htm",
                              data: "orderId=" + $("#payOrderId").val(),
                              success: function (msg) {
                                  callpay(msg.appId, msg.timeStamp, msg.nonceStr, msg.package, msg.sign);
                              }
                          });
                      }else{
                          window.location.href="${basePath}/orderlistpay.htm?orderId="+$("#payOrderId").val();
                      }
                      return false;
                  },
                  cancel: function () {
                      //停留在当前页面
                      return true;
                  }
              });

              $('a.weixin').click(function () {
                  payBox.close().remove();
                  weixinPay.showModal();
                  $("#paymoney").text('￥' + orderPrice);
                  weixinPay.reset();
              });
              $('.yue').click(function(){
                  payBox.close().remove();
                  var orderId = $("#payOrderId").val();
                  $.ajax({
                      url:'${basePath}/checkDepositPay.htm?type=0&orderPrice='+orderPrice,
                      type:'get',
                      success: function(result){
                          var return_code = result.return_code;
                          if(return_code == 'success'){
                              var yuePay = dialog({
                                  id: 'deposit-id',
                                  width : 260,
                                  content : '<form id="depositpay"> <div style="padding:0 5px"><p class="tc">请输入支付密码</p><p class="tc"><input onkeyup="validDeposit(this);" name="payPassword" type="password" class="p10" id="payPassword"></p><div class="error_tips" style="display: none"><a href="#" class="fr">忘记密码？</a><span>密码错误</span></div></div><div class="dia-buttons"><a onclick="closeDialog();" class="cancel">取消</a><a id="depositok" onclick="depositPay(this,'+orderId+');" class="ok disabled" style="margin-left: 4%;">确定</a></div></div>'
                              });
                              yuePay.showModal();
                          }else if(return_code == 'fail'){
                              var fail_code = result.fail_code;
                              var return_msg = result.return_msg;
                              if(fail_code == 'pass_fail'){
                                  var passFail = dialog({
                                      id: 'deposit-pass',
                                      width : 260,
                                      content : '<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a onclick="closeDialog();" class="cancel">其他方式支付</a><a href="${basePath}/accountsafe.html" id="depositok" class="ok" style="margin-left: 4%;">设置支付密码</a></div></div>'
                                  });
                                  passFail.reset();
                                  passFail.showModal();
                              }else if(fail_code == 'frozen_fail'){
                                  var frozenFail = dialog({
                                      id: 'deposit-frozen',
                                      width : 260,
                                      content : '<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a onclick="closeDialog();" id="depositok" style="margin: 0;width: 100%" class="ok">确认</a></div></div>'
                                  });
                                  frozenFail.reset();
                                  frozenFail.showModal();
                              }else if(fail_code == 'balance_fail'){
                                  var balanceFail = dialog({
                                      id: 'deposit-balance',
                                      width : 260,
                                      content : '<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a onclick="closeDialog();" id="depositok" style="margin: 0;width: 100%" class="ok">确认</a></div></div>'
                                  });
                                  balanceFail.reset();
                                  balanceFail.showModal();
                              }

                          }
                      }
                  });
              });

          }
      });
      $("#payOrderId").val(orderId);
      payBox.showModal();
   }

     function validDeposit(obj){
         var pass = $(obj).val();
         if($(obj).val() != ''){
             $(".ok").removeClass("disabled");
//                depositdialog.reset();
         }else{
             $(".ok").addClass("disabled");
         }


     }


     function depositPay(obj,orderId){
         if($(obj).hasClass('disabled')){
             return false;
         }
         $("#chPay").val("5");
         $.ajax({
             url:'${basePath}/depositpayorder.htm',
             type:'post',
             data: {"orderId":orderId,"payPassword":$('#payPassword').val()},
             success: function(result){
                 if(result.return_code == "success"){
                     $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>支付成功！</h3></div></div>');
                     setTimeout(function(){
                         $('.tip-box').remove();
                         location.href="${basePath}/customer/myorder.html";
                     },1000);
                 }else if(result.return_code == "fail"){
                     var return_msg = result.return_msg;
                     var fail_code = result.fail_code;
                     if(fail_code == 'pass_fail'){
                         dialog.getCurrent().content('<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a class="cancel">选择其他支付方式</a><a id="depositok" href="${basePath}/accountsafe.html" class="ok" style="margin-left: 4%;">设置支付密码</a></div></div>');
                     }else if(fail_code == 'frozen_fail'){
                         dialog.getCurrent().content('<div style="padding:0 20px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons" style="padding: 0 20px"><a id="depositok" onclick="closeDialog();" style="margin: 0;width: 100%" class="ok">确认</a></div></div>');
                     }else{
                         dialog.getCurrent().content('<form id="depositpay"><div style="padding:0 5px"><p class="tc">支付密码</p><p class="tc"><input onkeyup="validDeposit(this);" type="password" name="payPassword" id="payPassword" class="p10"></p><div class="error_tips"><a href="${basePath}/member/topaypassword.html" class="fr">忘记密码？</a><span>'+ return_msg +'</span></div></div><div class="dia-buttons"><a onclick="closeDialog();" class="cancel">取消</a><a id="depositok" onclick="depositPay(this,'+ orderId +');" class="ok disabled" style="margin-left: 4%;">确定</a></div></div>');
                     }
//                    this.content('<div style="padding:0 20px"><p class="tc">支付密码</p><p class="tc"><input type="text" class="p10"></p><div class="error_tips"><a href="#" class="fr">忘记密码？</a><span>密码错误</span></div></div> ');
                     dialog.getCurrent().reset();
                     return false;
                 }
             }
         });
     }

     function closeDialog(){
         dialog.getCurrent().close();
     }


 
  function callpay(appId,timeStamp,nonceStr,package,sign){
         WeixinJSBridge.invoke('getBrandWCPayRequest',{
          "appId" : appId,
          "timeStamp" : timeStamp, 
          "nonceStr" : nonceStr, 
          "package" : package,
          "signType" : "MD5", 
          "paySign" : sign 
                },function(res){
                    WeixinJSBridge.log(res.err_msg);
                    if(res.err_msg == "get_brand_wcpay_request:ok"){
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>支付成功！</h3></div></div>');
                        setTimeout(function(){
                            $('.tip-box').remove();
                            location.href="${basePath}/paysucccesswx.htm";
                        },1000);

                    }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>用户取消支付！</h3></div></div>');
                        setTimeout(function(){
                            $('.tip-box').remove();
                            location.href="${basePath}/customer/detail-"+$("#payOrderId").val()+".html";
                        },1000);
                    }else{
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="failed"></i><h3>支付失败！</h3></div></div>');
                        setTimeout(function(){
                            $('.tip-box').remove();
                            location.href="${basePath}/customer/detail-"+$("#payOrderId").val()+".html";
                        },1000);
                    }
                })
            }

 </script>
</body>

</html>