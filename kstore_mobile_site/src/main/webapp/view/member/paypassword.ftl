<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 我的账户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <#assign basePath = request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>
<form>
<div class="member_form2">
    <dl>
        <dt>新密码</dt>
        <dd>
            <input type="password" id="newpassword" value="" class="text" min="6" max="20"  placeholder="数字及英文大小写，6-20位字符">
        </dd>
    </dl>
    <dl>
        <dt>重复密码</dt>
        <dd>
            <input type="password" id="repeatpassword" value="" class="text" placeholder="请重复密码">
        </dd>
    </dl>
    <dl>
        <dt>验证码</dt>
        <dd>
            <input type="password" id="code" value="" class="text" placeholder="请输入手机验证码">
            <a href="javascript:;" class="get_code">
                <em id="valicode" onclick="getCode()">发送验证码</em>
            </a>
        </dd>
    </dl>
</div>


<div class="p10">
    <a href="#" class="btn btn-full">提交</a>
</div>
</form>
<script>
    $(function(){

    });

    $(".btn-full").click(function(){
        if(checkInput()){
            $.ajax({
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
            });
        }

    });

    function getCode(){
        $.ajax({
            url:"/sendpaycode.htm",
            type:'post',
            success:function(data){
                if(data == 1){
                    $("#valicode").html('60秒后重新获取');
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
        $("#valicode").html((time - 1)+"秒后重新获取");
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
        var newpassword = $("#newpassword").val();
        var repeatpassword = $("#repeatpassword").val();

        var code = $("#code").val();

        if (newpassword == null || newpassword == '') {
            alertStr("请输入新密码");
            return false;
        } else if (repeatpassword != newpassword) {
            alertStr("两次输入的密码不一致");
            return false;
        } else if (!(/^[A-Za-z0-9\\w]{6,20}$/).test(newpassword)) {

            alertStr("新密码格式错误");
            return false;
        }

        if (code == null || code == "") {
            alertStr("请输入验证码");
            return false;
        }
        return true;
    }

    function alertStr(str){
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+str+'</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
    }
</script>
</body>
</html>