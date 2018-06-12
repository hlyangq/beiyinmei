<!DOCTYPE html>
<html>
<head>
<#assign basePath=request.contextPath>
  <meta charset="UTF-8">
  <title>会员中心 - 我的账户</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
  <script src="${basePath}/js/idangerous.swiper.js"></script>
</head>
<body>

<div class="member_form2">
  <dl>
    <dt>绑定手机号</dt>
    <dd>
      <input type="text" class="text" id="mobile">
    </dd>
  </dl>
  <dl>
    <dt>验证码</dt>
    <dd>
      <input type="password" class="text" id="code">
      <a href="javascript:;" class="get_code">
        <em id="huode" onClick="getCode();">获取验证码</em>
      </a>
    </dd>
  </dl>
</div>
<div class="form_title" style="display:none;" id="validate"></div>
<div class="p10 mb50">
  <a href="#" class="btn btn-full">提交</a>
</div>
<#include '/common/smart_menu.ftl' />
<script>
  function getCode(){
    var mobile = $("#mobile").val();
    if(mobile==null || mobile==''){
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>输入手机号</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
         return false;
    }
    if(!(/^0?1[3|4|5|8|7][0-9]\d{8}$/).test(mobile)){
         $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>手机号格式错误</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
         return false;
    }
    $.ajax({
            url:"${basePath}/sendcodereg.htm",
            type:"post",
            data:{mobile:$("#mobile").val()},
            success:function(data){ 
                if(data == 1){
                    //发送成功
                    $("#huode").html('60秒后重新获取');
                    $("#huode").attr('data-t','60');
                     $("#huode").removeAttr("onClick");
                    $("#mobile").attr("readonly","readonly");
                    $("#huode").css("color","gray");
                    setTimeout(countDown, 1000);
                }else if(data == 2){
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>该手机号已被使用</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }else{
                    //发送失败
                    //alert("发送失败");
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>发送失败</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }
            }
        });
  }
  $(".btn-full").click(function(){
        var mobile = $("#mobile").val();
        if(mobile==null || mobile==''){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入手机号</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }
        if(!(/^0?1[3|4|5|8|7][0-9]\d{8}$/).test(mobile)){
         $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>手机号格式错误</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
             return false;
        }
        var code = $("#code").val();
        if(code==null || code==''){
         $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入验证码</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }
        
        $.ajax({
            url:"${basePath}/modifyMobile.htm",
            type:"post",
            data:{code:code,mobile:mobile},
            success:function(data){
                if(isNaN(data)){
                      window.location.href="${basePath}"+data;
                }else if(data == 2){
                    //验证码错误
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>验证码错误</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }
            }
        });
    });
    
    $(".text").change(function(){
         $("#validate").html("");
         $("#validate").hide();
    });
    function countDown(){
        var time = $("#huode").attr('data-t');
        $("#huode").html((time - 1)+"秒后重新获取");
        $("#huode").attr('data-t',(time - 1));
        time = time - 1;
        if (time == 1) {
            $("#huode").attr("onClick","getCode();");
            $("#huode").html("获取验证码");
            $("#mobile").removeAttr("readonly");
            $("#huode").css("color","red");
        } else {
            setTimeout(countDown, 1000);
        }
    }
    
    $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");
</script>
</body>
</html>