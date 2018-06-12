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
<input type="hidden" id="mobile" value="${custInfo.infoMobile!''}"/>
<input type="hidden" id="isMobile" value="${custInfo.isMobile!''}"/>
<#if custInfo.isMobile?? && custInfo.isMobile=="1">
 <div class="form_title">
  <dl>
    <dt>已绑手机</dt>
    <dd>
        <input type="hidden" id="mobile" value="${custInfo.infoMobile}"/>
      <span>
        <#assign mo="${custInfo.infoMobile?substring(3,custInfo.infoMobile?length-4)}" />
        <#assign mob="${custInfo.infoMobile?replace(mo,'****')}" />
        ${mob}
        </span>
    </dd>
  </dl>
</div>

<div class="member_form2">
    <dl>
        <dt>验证码</dt>
        <dd>
            <input type="text" name="checkCode" id="checkCode" class="text" placeholder="输入图片验证码"/>
           <a href="javascript:void(0);"> <img id="checkCodeImg" src="${basePath}/patchca.htm" onclick="this.src=this.src+'?'+Math.random(); " alt="验证码">
           </a>
        </dd>
    </dl>
</div>
<div class="member_form2">
  <dl>
    <dt>验证码</dt>
    <dd>
      <input type="password" value="" class="text" id="code" placeholder="输入验证码"/>
      <a href="#" class="get_code">
        <em id="huode" onClick="getCode();">获取验证码</em>
      </a>
    </dd>
  </dl>
</div>
<#else>
 <div class="form_title">
  <dl>
    <dt>请先输入登录密码以验证身份</dt>
    <dd>&nbsp;</dd>
  </dl>
</div>
<div class="member_form2">
  <dl>
    <dt>登录密码</dt>
    <dd>
      <input type="password" class="text" id="password">
    </dd>
  </dl>
  <dl>
    <dt>验证码</dt>
    <dd>
      <input type="text"  class="text" id="valiCode">
      <a href="javascript:;" class="show_password">
        <img alt="" id="code_image" src="${basePath}/patchca.htm" alt="验证码" onclick="this.src=this.src+'?'+Math.random();" height="34"/>
      </a>
    </dd>
  </dl>
</div>
</#if>

<div class="form_title" style="display:none;" id="validate"></div>
<div class="p10 mb50">
  <a href="#" class="btn btn-full">下一步</a>
</div>
<#include '/common/smart_menu.ftl' />
<script>
    function getCode(){
        if($("#mobile").val() == null || $("#mobile").val()==''){
             $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>未绑定手机号</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                    return false;
        }
        if($("#checkCode").val() == null || $("#checkCode").val()==''){
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入图片验证码</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
            return false;
        }
        $.ajax({
            url:"${basePath}/sendcode.htm",
            type:"post",
            data:{mobile:$("#mobile").val(),checkCode:$("#checkCode").val()},
            success:function(data){
                if(data == 1){
                    //发送成功
                    //alert("发送成功");
                    $("#huode").html('60s');
                    $("#huode").attr('data-t','60');
                    $("#huode").removeAttr("onClick");
                    $("#huode").css("color","gray");
                    setTimeout(countDown, 1000);
                }else if(data==2){
                    //发送失败
                    //alert("发送失败");
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>图片验证错误</h3></div></div>');
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
        var isMobile = $("#isMobile").val();
        if(isMobile=='1'){
            var code = $("#code").val();
            if(code==null || code==''){
                 $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入验证码</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                return false;
            }
            
            $.ajax({
                url:"${basePath}/validate/getMCode.htm",
                type:"post",
                data:{code:code},
                success:function(data){
                    if(data == 1){
                        //验证码正确
                        window.location.href="${basePath}/phonevalidate3.htm";
                    }else{
                        //验证码错误
                         $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>验证码错误</h3></div></div>');
                            setTimeout(function(){
                                $('.tip-box').remove();
                            },1000);
                    }
                }
            });
        }else{
            var password = $("#password").val();
            var valiCode = $("#valiCode").val();
            if(password==null || password==''){
                 $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入登录密码</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
                return false;
            }
            if(valiCode==null || valiCode==''){
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入验证码</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
                return false;
            }
            $.ajax({
                url:"${basePath}/validate/checkidentity.htm",
                type:"post",
                data:{valiCode:valiCode,password:password},
                success:function(data){
                    if(data == 1){
                        //验证通过
                        window.location.href="${basePath}/phonevalidate3.htm";
                    }else if(data == 2){
                        //验证码错误
                         $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>验证码错误</h3></div></div>');
                            setTimeout(function(){
                                $('.tip-box').remove();
                            },1000);
                            $("#code_image").click();
                    }else if(data == 3){
                        //密码错误
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>密码错误</h3></div></div>');
                            setTimeout(function(){
                                $('.tip-box').remove();
                            },1000);
                        $("#code_image").click();
                    }else if(data == 0){
                       //登陆
                        window.location.href="${basePath}/loginm.html?url=/myaccount.html";
                    }
                }
            });
            
        }
        
    });
    
    $(".text").change(function(){
         $("#validate").html("");
         $("#validate").hide();
    });
    
    function countDown(){
        var time = $("#huode").attr('data-t');
        $("#huode").html((time - 1)+"s");
        $("#huode").attr('data-t',(time - 1));
        time = time - 1;
        if (time == 1) {
            $("#huode").html("获取验证码");
            $("#huode").attr("onClick","getCode();");
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