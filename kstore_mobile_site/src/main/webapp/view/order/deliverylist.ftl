<!DOCTYPE html>
<html lang="zh-cn" xmlns:c="http://www.w3.org/1999/html">
<#assign basePath=request.contextPath>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>订单确认-选择收货地址</title>
<link rel="stylesheet" href="${basePath}/css/add-ons.min.css"/>
<link rel="stylesheet" href="${basePath}/css/style.min.css"/>
<link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
<head>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
</head>
<body>
<div class="order content-order-confirm">
    <div class="chose-pickup-info">
        <#if deliveryMap.deliveries??&&deliveryMap.deliveries?size!=0>
            <#list deliveryMap.deliveries as dp>
                <div class="list-item">
                    <i class="select-box <#if dpId?string == dp.deliveryPointId?string>selected</#if>" deliveryPointId="${dp.deliveryPointId!''}"></i>
                    <div class="wrap">
                        <h3><span class="name">${dp.name!''}</span></h3>
                        <p class="dress-info">${dp.address!''}</p>
                    </div>
                    <a href="javascript:;" class="show_info" dpname="${dp.name!''}" dpaddress="${dp.address!''}" telephone="${dp.telephone!''}" dplinkman="${dp.linkman!''}">
                        <i class="ion-ios-list-outline"></i>
                    </a>
                </div>
            </#list>
        </#if>
    </div>
</div>

<div class="pickup_info" style="display:none;">
    <h4 id="dpname"></h4>
    <dl>
        <dt>详细地址</dt>
        <dd id="dpaddress"></dd>
    </dl>
    <dl>
        <dt>联系电话</dt>
        <dd id="dptelephone">
            <a href="" class="tel">
                <i class="ion-ios-telephone-outline"></i>
            </a>
        </dd>
    </dl>
    <dl>
        <dt>联系人</dt>
        <dd id="dplinkman"></dd>
    </dl>
</div>

<script>
    $(function(){
        $(document).on('click','.show_info',function(){
            var $this = $(this);
            var dpname = $this.attr('dpname');
            var dpaddress = $this.attr('dpaddress');
            var dptelephone = $this.attr('telephone');
            var dplinkman = $this.attr('dplinkman');
            $(".pickup_info #dpname").text(dpname);
            $(".pickup_info #dpaddress").text(dpaddress);

            $(".pickup_info #dptelephone").empty();
            var dom = dptelephone + '<a href="tel://'+dptelephone+'" class="tel"><i class="ion-ios-telephone-outline"></i></a>';
            $(".pickup_info #dptelephone").append(dom);

            $(".pickup_info #dplinkman").text(dplinkman);

            var d = dialog({
                width: 300,
                content: $('.pickup_info'),
                okValue: '我知道了',
                ok: function(){
                    return true;
                }
            });
            d.showModal();
        });
    });

    $(".chose-pickup-info").delegate(".list-item .select-box","click", function(dom){
        $(".list-item .select-box").removeClass("selected");
        var $this = $(this).addClass("selected");
        var deliveryPointId = $this.attr("deliveryPointId");
        //console.log("deliveryPointId:"+deliveryPointId);
        window.location.href = "${basePath}/order/suborder.html?deliveryPointId="+deliveryPointId;
    });
    //list-item
    $(".chose-pickup-info").delegate(".list-item .wrap","click", function(dom){
        event.stopPropagation();
        $(".list-item .select-box").removeClass("selected");
        var $this = $(this).parent().find("i").addClass("selected");
        var deliveryPointId = $this.parent().find("i").attr("deliveryPointId");
        window.location.href = "${basePath}/order/suborder.html?deliveryPointId="+deliveryPointId;
    });
</script>
</body>
</html>
