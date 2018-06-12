<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员中心 - 提现记录页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
<#assign basePath =request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/idangerous.swiper.js"></script>
    <script src="${basePath}/js/dataformat.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
    <script>
        $(document).ready(function(){
            $("#type").val("0");
            clearInitialization();
            showNextPage();
        });
    </script>
</head>
<body>

<div class="fixed_header">
    <div class="tabs c5 row">
        <a href="javascript:;" data-val="0" class="active">全部</a>
        <a href="javascript:;" data-val="1">待审核</a>
        <a href="javascript:;" data-val="2">待确认</a>
        <a href="javascript:;" data-val="3" >已打回</a>
        <a href="javascript:;" data-val="4">已完成</a>
    </div>


</div>
<input id="type" value="0" type="hidden">
<input id="status" value="0" type="hidden">
<input id="basePath" value="${basePath}" type="hidden">

<div class="content-home">
    <div class="cash_records income_list">
    </div>
    <div class="list-loading" style="display:none" id="showmore">
    <#--<img alt="" src="${basePath}/images/loading.gif">
            <span>加载中……</span>-->
    </div>
    <div class="tips">
    </div>
</div>


<#include '/common/smart_menu.ftl' />
<script src="${basePath}/js/customer/cashrecords.js" />
<script>

    /* 选择银行 */
    $('#bank').click(function(){
        $('body').append('<div class="opacity-bg-3"></div>');
        $('.bank-box').show();
    });
    $('.bank-box .back').click(function(){
        $('.opacity-bg-3').remove();
        $('.bank-box').hide();
    });

</script>
</body>
</html>