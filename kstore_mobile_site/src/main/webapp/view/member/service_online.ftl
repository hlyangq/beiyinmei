<!DOCTYPE html>
<html lang="zh-CN">
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>选择客服</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
</head>
<body>

<div class="service_box">
    <h4>选择客服</h4>
    <ul>
        <#list qqList as qq>
            <li class="">
                <a  href="http://wpa.qq.com/msgrd?v=3&uin=${qq.contactNumber}&site=qq&menu=yes">
                    <i class="iconfont icon-qq"></i>
                    <span>${qq.name}</span>
                    <i class="ion-ios-arrow-right"></i>
                </a>
            </li>
        </#list>
    </ul>
</div>

</body>
</html>