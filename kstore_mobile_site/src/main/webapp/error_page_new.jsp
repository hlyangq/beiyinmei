<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>意外错误</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="<%=basePath%>css/style.min.css"/>
  <script src="<%=basePath%>js/jquery-1.10.1.js"></script>
</head>
<body>

<div class="content">
  <div class="no_tips">
    <p>意外出现错误，您可以回到首页继续浏览</p>
  </div>
  <div class="p20">
    <a href="<%=basePath%>main.html" class="btn btn-full">回到首页</a>
  </div>
</div>


</body>
</html>