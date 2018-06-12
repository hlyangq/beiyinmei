<!DOCTYPE html>
<html>
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <title>注册成功</title>
    <link rel="stylesheet" href="${basePath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/style.css"/>

    <style>
        * {margin: 0; padding: 0;}

        .success-wrap {
            padding: 130px 0;
            background-color: #f5f5f5;
        }
        .success-box {
            width: 600px;
            height: 365px;
            margin: 0 auto;
            padding-top: 55px;
            background-color: #fff;
            border-radius: 20px;
            text-align: center;
            color: #666;
        }
        .success-box > img {
            width: 120px;
        }
        .success-box > h2 {
            margin: 20px 0;
            font-size: 24px;
        }
        .success-btn {
            display: inline-block;
            margin-top: 20px;
            width: 195px;
            height: 40px;
            line-height: 40px;
            background-color: #ec6a1e;
            border-radius: 3px;
            color: #fff;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="success-wrap">
    <div class="success-box">
        <img src="http://pic.qianmi.com/themes/boss/login2015/images/reg-success.png" alt="">
        <h2>恭喜您，注册成功</h2>
        <p>您可以点击登录立即体验(^_^)...</p>
        <a class="success-btn" href="${basePath}/registersucceed.html">立即登录</a>
    </div>
</div>


<script type="text/javascript" src="${basePath}/js/login/login.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${basePath}/js/third.js"></script>
<script type="text/javascript">
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
            login();
        }
    };
    //用户名 密码失去焦点 隐藏提示语句
    function pwd(){
        $('.password_tip').html('');
        $('.username_tip').html('');
    }

    //搜藏本站
    function addFavorite(title,url){
        var title = document.title;
        var url = location.href;
        if (window.sidebar) {
            window.sidebar.addPanel(title, url, "");
        } else if (document.all) {
            window.external.AddFavorite(url, title);
        } else {
            return true;
        }


    }

</script>
</body>
</html>
