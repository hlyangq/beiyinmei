<!DOCTYPE html>
<html>
<head>
    <#assign basePath=request.contextPath/>
  <meta charset="UTF-8">
  <title>会员中心 - 我的账户</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
  <script src="${basePath}/js/idangerous.swiper.js"></script>
</head>
<body>

<div class="form_title">
    <input type="hidden"  id="basePath" value="${basePath}"/>
  <dl>
    <dt>当前账号</dt>
    <dd>
      <span>${cust.customerUsername?substring(0,2)}***${cust.customerUsername?substring(cust.customerUsername?length-2)}</span>
    </dd>
  </dl>
</div>
<div class="member_form2">
  <dl>
    <dt>当前密码</dt>
    <dd>
      <input type="password" class="text" id="password"  placeholder="当前密码"/>
    </dd>
  </dl>
  <dl>
    <dt>新密码</dt>
    <dd>
      <input type="password" id="newpassword" placeholder="6-20位数字、字母组合" class="text">
      <#--<a href="javascript:;" class="show_password">
        <i class="ion-eye"></i>
      </a>-->
    </dd>
  </dl>

    <dl>
        <dt>确认密码</dt>
        <dd>
            <input type="password" id="newpasswordagain" placeholder="6-20位数字、字母组合" class="text">
        <#--<a href="javascript:;" class="show_password">
          <i class="ion-eye"></i>
        </a>-->
        </dd>
    </dl>

    <dl>
        <dt>验证码</dt>
        <dd>
            <input type="text" id="code" placeholder="验证码" class="text"/>
            <span class="valid_img">
                <img alt="" id="code_image" src="${basePath}/patchca.htm" alt="验证码" onclick="this.src=this.src+'?'+Math.random();"/>
            </span>
        </dd>
    </dl>
</div>

<div class="p10 mb50">
  <a href="#" class="btn btn-full">提交</a>
</div>
<#include '/common/smart_menu.ftl' />
<script>
  $(function(){

    /* 显示密码 */
    $('.show_password').click(function(){
      if($(this).attr('class').indexOf('selected') > 0){
        $(this).removeClass('selected');
        $(this).prev().attr('type','password');
      }
      else{
        $(this).addClass('selected');
        $(this).prev().attr('type','text');
      }
    });

  });
  
  $(".btn-full").click(function(){
        var password = $("#password").val();
        var newpassword = $("#newpassword").val();
        var code = $("#code").val();
        if(checkInput()){
            $.ajax({
            type: 'post',
            url: "modifypassword.htm?password="+password+"&newPassword="+newpassword+"&code="+code,
            success: function(data){
                if(data == 2){
                    alertStr("当前密码错误");
                }else if(data == 1){
                    alertStr("修改成功");
                    window.location.href = $("#basePath").val()+"/myaccount.html";
                }else if(data == 0){
                    alertStr("修改失败");
                }else if(data == 3){
                    alertStr("登陆账号异常，请重新登录");
                }else if (data == -1)
                {
                    alertStr("验证码错误，请重新输入");
                    $("#code_image").click();
                }

        }});
        }
  });
  
  function checkInput(){
        var password = $("#password").val();
        var newpassword = $("#newpassword").val();
        var passwordagain = $("#newpasswordagain").val();

        var code = $("#code").val();


        if(password == null || password == ''){
            alertStr("请输入当前密码");
            return false;
        }

      if (newpassword == null || newpassword == '') {
          alertStr("请输入新密码");
          return false;
      } else if (passwordagain != newpassword) {
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

$("#password").focus(function(){
    $(this).attr("placeholder","");
});
$("#password").blur(function(){
    if ($(this).val()==null || $(this).val()=='') {
        $(this).attr("placeholder","当前密码");
    }
});
$("#newpassword").focus(function(){
    $(this).attr("placeholder","");
});
$("#newpassword").blur(function(){
    if ($(this).val()==null || $(this).val()=='') {
        $(this).attr("placeholder","6-20位数字、字母或-_");
    }
});

      $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");
</script>
</body>
</html>