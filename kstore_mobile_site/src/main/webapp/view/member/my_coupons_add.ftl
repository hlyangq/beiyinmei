<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <#assign basePath=request.contextPath/>
  <meta charset="UTF-8">
  <title>会员中心 - 优惠券</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
</head>
<body>

<div class="form_title">
  <dl>
    <dt>已绑手机</dt>
    <dd>
      <span><#if customercon.infoMobile?? && customercon.infoMobile!=''>${customercon.infoMobile?substring(0,3)}***${customercon.infoMobile?substring(customercon.infoMobile?length-4)}<#else>未绑定</#if></span>
    </dd>
  </dl>
</div>
<div class="member_form2">
  <dl>
    <dt>券号</dt>
    <dd>
      <input type="text" id="couponCode" class="text" placeholder="16位字母数字组合"/> 
    </dd>
  </dl>
</div>
<input type="hidden" id="flag" value="${flag!'0'}"/>
<div class="bottom-fixed p10">
  <a href="javascript:;" class="btn btn-full">提交</a>
</div>

<script>
  $(function(){
  });
  
  $(".btn-full").click(function(){
        var couponCode = $("#couponCode").val();
        if(couponCode == "" || couponCode == null){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入券号</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }
        if(!(/^[A-Za-z0-9]{16}$/).test(couponCode)){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>券号格式不正确</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }
        
        $.ajax({
            url:"addcoupons.htm",
            type:"post",
            data:{couponCode:couponCode},
            success:function(data){
                if(data == 2){
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>添加成功</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                    if($("#flag").val()=='0'){
                        window.location.href="${basePath}/customer/coupon.html";
                    }else{
                        window.location.href="${basePath}/tocouponlist.htm?codeNo=${codeNo!''}&invoiceTitle=${invoiceTitle!''}&invoiceType=${invoiceType!''}";
                    }

                }else if(isNaN(data)){
                    window.location.href="${basePath}/data";
                }else if(data == 3){
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>您领取的该优惠券已达上限</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }else{
                     $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>输入的券码无效</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }
            }
        });
        
  });
</script>
</body>
</html>