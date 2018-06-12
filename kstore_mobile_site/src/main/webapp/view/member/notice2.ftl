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
    <input type="hidden" id="basePath" value="${basePath}"/>
  <div class="col-12">
    <a href="${basePath}/adminnotice.html">
      管理员通知
    </a>
  </div>
  <div class="col-12">
    <a href="${basePath}/ordernotice.html" class="selected">系统通知</a>
  </div>
</div>

<div class="notice_list mb50">
  <#if (pb?size!=0)>
  <#list pb as notice>
  <div class="notice_item">
    <div class="head row">
      <i class="iconfont icon-yinliang"></i>
      <h4>订单通知</h4>
      <span class="time">${notice.createTime?string('yyyy-MM-dd HH:mm')}</span>
    </div>
    <div class="cont">
     <input type="hidden" id="noyice${notice.noticeId}" value="${notice.noticeId}"/>
      <p>${notice.noticeContent!''}</p>
    </div>
    <div class="ctrl">
      <a href="javascript:;" class="open_btn" onClick="isRead(${notice.noticeId},${notice.orderId!''});">
        查看订单
        <i class="ion-chevron-right fr"></i>
      </a>
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
    function isRead(noticeId,orderId){
        $.ajax({
            url:"readnotice.htm",
            type:"post",
            data:{noticeId:noticeId},
            success:function(data){
                if(isNaN(data)){
                    window.location.href=$("#basePath").val()+data;
                }else{
                    window.location.href=$("#basePath").val()+"/customer/detail-"+orderId+".html";
                }
            }
        });
        
    }
    
    $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");
</script>
</body>
</html>