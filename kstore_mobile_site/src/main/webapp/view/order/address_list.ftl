<!DOCTYPE html>
<html lang="en">
<head>
<#assign basePath=request.contextPath>
    <meta charset="UTF-8">
    <title>订单确认-选择收货地址</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css">
    <script src="http://pic.ofcard.com/astore/wei-store/js/jquery-1.10.1.js"></script>
    <script src="http://pic.ofcard.com/astore/wei-store/js/pageAction.js"></script>
</head>
<body>
<div class="order content-order-confirm">
    <div class="chose-receive-info">

    <#if addresses??>
        <input type="hidden" value="${addresses?size}" id="mynum">
    </#if>
        <#if addresses??&&addresses?size!=0>
            <#list addresses as address>
                <div class="list-item">
                    <div class="checkaddr">
                    <i class="select-box <#if address.isDefault=='1'>selected</#if>"></i>
                    <input type="hidden" value="${address.addressId}"/>
                    <h3><span class="name">${address.addressName!''}</span></h3>
                    <p class="phoneNum">${address.addressMoblie!''}</p>
                    <p class="dress-info">${address.province.provinceName}${address.city.cityName}${address.district.districtName}${address.addressDetail}</p>
                    </div>
                    <i class="edit" onclick="window.location='${basePath}/toupdateAddress.htm?addressId=${address.addressId}&flag=1'"></i>
                </div>
            </#list>
        <#else>
            <div class="no_tips">
                <p>没有收货地址哦</p>
            </div>
        </#if>
            <form id="subForm" method="post">
                <input type="hidden" name="addressId" id="addressId">
            </form>

        <div class="list-item bottom-full">
            <a class="btn btn-full" onclick="mychecknum()" href="javascript:;"><i></i>新增收货地址</a>
        </div>
    </div>
</div>
</body>
<script src="${basePath}/js/jquery-1.11.1.min.js"></script>
<script>
    $(function(){
//        $(".select-box").click(function(){
//
//        })
        $(".checkaddr").click(function(){
            $("#addressId").val($(this).find(".select-box").next().val());
            $("#subForm").attr("action","${basePath}/changeAddress.htm").submit();
        })
    })

    function mychecknum() {
        var num = $("#mynum").val();
        if (num>=10) {
            $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>收货地址最多10个</h3></div></div>');
            setTimeout(function(){
                $('.tip-box').remove();
            },1000);
        }else
        {
            window.location.href ="${basePath}/toAddAddress.htm?flag=1";
        }

    }

</script>
</html>
