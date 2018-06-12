<!doctype html>
<html lang="en">
<head>
    <#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>我的预存款</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css"/>
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
    <link rel="stylesheet" href="${basePath}/index_twentyone/css/index_css.m.css"/>
    <link rel="stylesheet" href="${basePath}/index_seven/css/style.css"/>
    <link rel="stylesheet" href="${basePath}/css/receive.m.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.min.js"></script>
</head>
<body>

<div class="member-dialog dia1">
    <div class="member-dialog-body">
        <div class="title"><a href="${basePath}/deposit/mydeposit.htm" onclick="">×</a>提示</div>
        <div class="dia-tips">
            <p class="txt">
                在线充值完成:
                ${msg}
            </p>
        </div>
        <div class="dia-btn">
            <a href="${basePath}/deposit/mydeposit.htm" class="btn active">确定</a>
            <a href="${basePath}/deposit/mydeposit.htm" onclick="" class="btn">取消</a>
        </div>
    </div>
</div>

<script type="text/javascript" src="${basePath}/js/customer/customer.js"></script>
<script type="text/javascript" src="${basePath}/js/tab-switch.js"></script>
<script type="text/javascript" src="${basePath}/js/default.js"></script>
<script type="text/javascript" src="${basePath}/js/newapp.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/findcode.js"></script>
<script type="text/javascript" src="${basePath}/js/jsOperation.js"></script>

<script type="text/javascript">
    //dia(1);
    //跳转到预存款页面
    window.location.href = "${basePath}/deposit/mydeposit.htm";
</script>

</body>
</html>