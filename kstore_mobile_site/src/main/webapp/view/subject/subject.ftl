<!DOCTYPE html>
<#assign basePath=request.contextPath>
<!-- Bootstrap -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title><#if subject??>${subject.title}</#if></title>
<link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${basePath}/css/idangerous.swiper.css" rel="stylesheet">
<link href="${basePath}/css/style.css" rel="stylesheet">
<script src="${basePath}/js/jquery.js"></script>
<script>
    function browserRedirect() {
        <#if ((mobProjectUrl??) && (mobProjectUrl?length>0))>
            location = "${mobProjectUrl}";
        </#if>
    }
    window.onload = function(){
        $('body').append('<div class="pro-details-top" style="left:0;top:0"><a href="javascript:history.back();" class="back"><i class="ion-ios-arrow-left"></i></a></div>');
    };
</script>

<#if subject??>
${(subject.content)!''}
<#else >
<script>
    browserRedirect();
</script>
</#if>
<#include "../common/smart_menu.ftl"/>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${basePath}/js/bootstrap.min.js"></script>
<script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
<script src="${basePath}/js/fastclick.min.js"></script>
<script src="${basePath}/js/jquery.keleyi.js"></script>
<script src="${basePath}/js/customer/wxforward.js"></script>
