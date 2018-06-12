<!DOCTYPE html>
<html>
<head>
    <#assign basePath=request.contextPath/>
  <meta charset="UTF-8">
  <title>会员中心 - 站内信</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
</head>
<body>

<div class="common_tabs member_box row">
  <div class="col-12">
    <a href="${basePath}/adminnotice.html" class="selected">
      管理员通知
    </a>
  </div>
  <div class="col-12">
    <a href="${basePath}/ordernotice.html">系统通知</a>
  </div>
</div>

<div class="notice_list mb50">
    <#if (pb?size!=0)>
  <#list pb as letter>
  <div class="notice_item shutdown" id="notice${letter.letterId}">
    <input type="hidden" value="${letter.letterId}" id="letterId"/>
    <div class="head row">
      <i class="iconfont icon-yinliang"></i>
      <h4>系统管理员</h4>
      <span class="time">${letter.createTime?string('yyyy-MM-dd HH:mm')}</span>
    </div>
    <div class="cont">
        <p style="color:red;">${letter.letterTitle}</p>
      <p>${letter.letterContent!''}</p>
    </div>
    <div class="ctrl">
      <a href="javascript:;" class="open_btn" onClick="open1('${letter.letterId}');">查看全部</a>
      <a href="javascript:;" class="close_btn" onClick="shutdown1('${letter.letterId}');">收起</a>
    </div>
  </div>
 </#list>
<#else>
    <div class="content" id="no">
      <div class="no_tips">
        <p>木有通知哦</p>
      </div>
    </div>
</#if>
</div>
 <#include '/common/smart_menu.ftl' />   
<script>

    function open1(id){
        $("#notice"+id).removeClass("shutdown");
        $("#notice"+id).addClass("open");
        $.ajax({
            url:"${basePath}/readletterm.htm",
            type:"post",
            data:{letterId:id},
            success:function(data){
            
            }
        });
    }
    
    function shutdown1(id){
        $("#notice"+id).removeClass("open");
        $("#notice"+id).addClass("shutdown");
    }
    
    $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");
</script>
</body>
</html>