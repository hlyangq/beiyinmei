<html>
<head>
    <meta charset='utf-8'>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>在线客服</title>
    <style>
        * {margin: 0; padding: 0;}
        .container {width: 100%; height: 100%;margin-top: -60px;}
    </style>
</head>
<body>
<#if kst??>
<iframe src="${kst.appOperation}" class="container"></iframe>
</#if>
</body>
</html>

