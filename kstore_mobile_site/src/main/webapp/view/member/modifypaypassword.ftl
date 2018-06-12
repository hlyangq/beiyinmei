<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 我的账户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
<#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>
<#assign mo="${custInfo.infoMobile?substring(3,custInfo.infoMobile?length-4)}" />
<#assign mob="${custInfo.infoMobile?replace(mo,'****')}" />
<div class="form_title">
    <dl>
        <dt>已验证手机</dt>
        <dd>
            <span>${mob}</span>
        </dd>
    </dl>
</div>
<form id="codeform" action="${basePath}/tonextpassword.htm">
    <div class="member_form2">
        <dl>
            <dt>验证码</dt>
            <dd>
                <input type="text" value="" class="text" name="code" id="code" placeholder="请输入手机验证码">
                <a href="javascript:;" class="get_code">
                    <em id="valicode" onclick="getCode();">发送验证码</em>
                </a>
            </dd>
        </dl>
    </div>
</form>
<input type="hidden" value="${error_msg!''}" id="error_msg">
<div class="p10">
    <a href="javascript:void(0);" class="btn btn-full">下一步</a>
</div>

<script>
    $(function(){
        if($("#error_msg").val() != ''){
            alertStr($("#error_msg").val());
        }
    });

    function getCode(){
        $.ajax({
            url:"${basePath}/sendpaycode.htm",
            type:'post',
            success:function(data){
                if(data == 1){
                    $("#valicode").html('60s');
                    $("#valicode").attr('data-t','60');
                    $("#valicode").removeAttr("onClick");
                    $("#valicode").css("color","gray");
                    setTimeout(codeCountDown, 1000);
                }else{
                    $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>发送失败</h3></div></div>');
                    setTimeout(function(){
                        $('.tip-box').remove();
                    },1000);
                }
            }
        });
    }

    function codeCountDown(){
        var time = $("#valicode").attr('data-t');
        $("#valicode").html((time - 1)+"s");
        $("#valicode").attr('data-t',(time - 1));
        time = time - 1;
        if (time == 1) {
            $("#valicode").html("获取验证码");
            $("#valicode").attr("onClick","getCode();");
            $("#valicode").css("color","red");
        } else {
            setTimeout(codeCountDown, 1000);
        }
    }

    function checkInput(){
        var code = $("#code").val();

        if (code == null || code == "") {
            alertStr("请输入验证码");
            return false;
        }
        return true;
    }

    $(".btn-full").click(function(){
        if(checkInput()){
            var code = $("#code").val();
            $.ajax({
                url:"${basePath}/validatepaycode.htm",
                type:"post",
                data:{code:code},
                success:function(data){
                    if(data == 1){
                        //验证码正确
                        $("#codeform").submit();
                    }else{
                        //验证码错误
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>验证码错误</h3></div></div>');
                        setTimeout(function(){
                            $('.tip-box').remove();
                        },1000);
                    }
                }
            });
            /*$.ajax({
                url:'/submitpayset.htm',
                type:'post',
                data:{code:$("#code").val(), password:$("#newpassword").val()},
                success:function(data){
                    if(data == 1){
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>支付密码设置成功</h3></div></div>');
                        setTimeout(function(){
                            $('.tip-box').remove();
                            window.location.href = "${basePath}/myaccount.html";
                        },1000);
                    }else{
                        alertStr("设置失败");
                    }
                }
            });*/
        }

    });

    function alertStr(str){
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+str+'</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
    }

</script>
</body>
</html>