<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title></title>

  <!-- Bootstrap -->
  <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
  <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
  <link href="<%=basePath %>css/signin.css" rel="stylesheet">
  <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script src="<%=basePath %>js/newlogin/particles.js"></script>
    <link href="<%=basePath %>js/newlogin/application.css" media="all" rel="stylesheet">
    <script src="<%=basePath %>js/newlogin/application.js"></script>
  <script type="text/javascript">


  </script>
</head>
<body class="sessions-new" style="background: url(<%=basePath %>/js/newlogin/star.png) #000 no-repeat center center;background-size:auto 650px;">
<div id="particles-js">
    <canvas style="width: 100%; height: 100%;" width="2560" height="724"></canvas>
</div>
<div class="container">
<input type="hidden" id="patchcaFlag"/>
  <div class="login_box">
  <form class="form-signin form-horizontal" id="lForm">
    <h2 class="form-signin-heading"><img alt="" src="" width="165" max-height="39" id="lgLogo">  </h2>
    <div class="form-group">
      <div class="input-group">
        <span class="input-group-addon"><i class="icon iconfont">&#xe616;</i></span>
     	 <input type="text" class="form-control" placeholder="用户名/邮箱"  name="name" id="managername">
      </div>
    </div>
    <div class="form-group">
      <div class="input-group">
        <span class="input-group-addon"><i class="icon iconfont">&#xe617;</i></span>
         <input type="password" class="form-control" placeholder="密码"  name="password" id="managerpassword">
      </div>
    </div>
    <div class="form-group" id="geetestDiv">
      <div class="col-sm-15">
        <div class="input-group">
          <span class="input-group-addon"><i class="icon iconfont">&#xe618;</i></span>
          <input type="text" class="form-control code_text" placeholder="验证码" onfocus="getPatcha()">
        </div>
      </div>
      <div class="col-sm-1"></div>
      <div class="col-sm-8">
        <a href="javascript:;"><img height="40" class="code_image" src="patchca.htm" alt="验证码" title="点击换一张图片" style="cursor:pointer; width:100%; margin-top:1px;" onclick="this.src=this.src+'?'+Math.random(); " ></a>
      </div>
    </div>
     <div class="form-group">
      <div class="alert alert-danger alert-dismissible fade in" role="alert" id="login_error"  style="display:none;width:290px;">
        <button type="button" class="close"  onclick="hidediv(this);"  aria-label="Close"><span aria-hidden="true">×</span></button>
        <div style="float: left;"><strong>用户名或密码</strong>填写有误! </div><b data-container="body" data-toggle="popover" data-placement="right" data-trigger="hover" data-content="请联系我们的客服为您重置密码，请将您的身份认证信息准备齐全。" style="color: #428bca; display: inline-flex;">忘记密码？</b>
      </div>
       <div class="alert alert-danger alert-dismissible fade in" role="alert" id="code_old" style="display:none;">
        <button type="button" class="close" onclick="hidediv(this);" aria-label="Close"><span aria-hidden="true">×</span></button>
        <strong>验证码错误！</strong>
      </div>

    </div>
    <div class="form-group" style="">
      <button class="btn btn-lg btn-primary btn-block" type="button" onclick="login();">登录</button>
    </div>

  </form>
  </div>

  <div class="bottom_links"  style="background:rgba(0,0,0,.5);padding-top:0px;">
    <ul style="color:#ffffff;margin-bottom:0;">
      <li><a href="javascript:;">© <span id="copyright"></span></a></li>
      <li><a href="javascript:;"><span id="beian"></span></a></li>
      <li><a href="javascript:changeBgimg();">切换背景</a></li>
    </ul>
  </div>

</div> <!-- /container -->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script>

    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
            //要做的事情
            login();
        }
    };

    var emp = null;
    function getPatcha(){
    }

    var patchaFlag;
    $(function(){




    });

    function login(){
        $("#login_error").css({"display":"none"});
        $("#code_old").css({"display":"none"});
        $("#managername").parents(".form-group").removeClass("has-error");
        $("#managerpassword").parents(".form-group").removeClass("has-error");
        patchaFlag = $("#patchcaFlag").val();
        var enterValue = $(".code_text").val();
        if( patchaFlag!="" && patchaFlag==0){
            $.ajax({
                url: "iflogin.htm",
                context: document.body,
                type: 'POST',
                async:false,
                data:{name:$("#managername").val(),password:$("#managerpassword").val(),code:enterValue},
                success: function(data){
                    if(data == 0){
                        //用户名错误
                        $("#login_error").css({"display":"block"});
                        $("#managername").parents(".form-group").addClass("has-error");
                        //getPatcha();
                    }else if(data == 2){
                        //密码错误
                        $("#login_error").css({"display":"block"});
                        //getPatcha();
                    }else if(data == 3){
                        //用户名错误
                        $("#login_error").css({"display":"block"});
                        //getPatcha();
                    }else{
                        window.location.href="index.htm";
                    }
                }});
        }else{
            $.ajax({
                url: "iflogin.htm",
                context: document.body,
                async:false,
                type: 'POST',
                data:{name:$("#managername").val(),password:$("#managerpassword").val(),code:enterValue},
                success: function(data){
                    if(data == 0){
                        //用户名错误
                        $("#login_error").css({"display":"block"});
                        $("#managername").parents(".form-group").addClass("has-error");
                        /*如果失败修更新验证码**/
                        $(".code_image").click();
                        $(".code_text").val("");
                        getPatcha();
                    }else if(data == 2){
                        //密码错误
                        $("#login_error").css({"display":"block"});
                        $("#managerpassword").parents(".form-group").addClass("has-error");
                        /*如果失败修更新验证码*/
                        $(".code_image").click();
                        $(".code_text").val("");
                        getPatcha();
                    }else if(data == 3){
                        //用户名错误*/
                        $("#login_error").css({"display":"block"});
                        $("#managername").parents(".form-group").addClass("has-error");
                        $("#managerpassword").parents(".form-group").addClass("has-error");
                        $(".code_image").click();
                        $(".code_text").val("");
                        getPatcha();
                    }else if (data == -1){
					    $(".code_text").parents(".form-group").addClass("has-error");
                        $("#code_old").css({"display":"block"});
                        $(".code_image").click();
                        $(".code_text").val("");
                        getPatcha();
                }else
				{
					  window.location.href="index.htm";
				}
                }});




        }
    }
    //随机生成一张背景图片
   function changeBgimg(){
        var n = Math.floor(Math.random()*11)+1;
        $("body").css("backgroundImage",'url(<%=basePath%>images/bgimg/'+n+'.png)');
        $("body").css("background-repeat",'no-repeat');

       if(n==10){
           $("body").css("background-size",'auto 900px');
       }else if(n==11){
           $("body").css("background-size",'auto 100%');
       }else{
           $("body").css("background-size",'auto 650px');
       }
    }
   $(function(){changeBgimg();});
  $(function () {
    $("#geetestDiv .float").css("float","right");
    $('[data-toggle="popover"]').popover();


      //获取验证码开关
      $.ajax({
          url: "newgetLoginPatcha.htm?CSRFToken=${token}",
          async:false,
          success: function(data){
              if(data!=null&&data !=''){
                  $("#geetestDiv").hide();
                  $("#patchcaFlag").val(0);
                  $(".code_text").val(data);
              }
          }});

      //获取登录logo
      $.ajax({
          url: "ajaxGetSysBasic.htm?CSRFToken=${token}",
          success: function(data){
              if(data.loginLogo!=""){
                  $("#lgLogo").attr("src",data.loginLogo);
              }
              if(data.temp1!=""){
                  $("#copyright").text(data.temp1);
              }
              if(data.temp2!=""){
                  $("#beian").text(data.temp2);
              }
              if(data.bsetName!=null&&data.bsetName!=""){
                  $("title").html("后台登录 - "+data.bsetName);
              }
          }});


  });
  function hidediv(obj){
      $(obj).parent().hide();
  }
</script>
</body>
</html>