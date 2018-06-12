<!doctype html>
<#assign basePath=request.contextPath>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单确认-发票信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/style.min.css">
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css">
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>
<div class="order content-order-confirm">
    <form action="" method="post" id="subForm">
        <input type="hidden" value="${invoiceType!''}" name="invoiceType" id="invoiceType">
        <input type="hidden" value="${ch_pay!''}" name="ch_pay">
        <input type="hidden" value="${typeId!''}" name="typeId">
        <input type="hidden" value="${codeNo!''}" name="codeNo">
        <input type="hidden" value="${deliveryPointId!''}" name="deliveryPointId" >
        <input type="hidden" value="${addressId!''}" name="addressId" >
        <div class="fap-info">
        <div class="list-item">
            <div class="list-value fap-options">
                <a class="btn-grey" href="javascript:;" data-status="close">不开发票</a>
                <a class="btn-grey selected" href="javascript:;" data-status="open">普通发票</a>
            </div>
        </div>
        <div class="list-item mt10 taitou">
            <h3 class="item-head">发票抬头</h3>
            <div class="list-value">
                <input type="text" name="invoiceTitle" value="${invoiceTitle!''}" id="invoiceTitle" placeholder="可填写个人姓名或企业全名">
            </div>
        </div>

        <div class="list-item mt10 neirong">
            <a href="#" class="fap-cont">
                <h3 class="item-head">
                    <i class="arrow-right"></i>
                    发票内容
                    <span class="curValue">
                    <#if invoiceType??&&invoiceType!=''>
                       ${invoiceType}
                       <#else>
                        商品明细
                    </#if></span>
                </h3>
            </a>
        </div>
        <a class="btn btn-full sure" onclick="checkForm()"><i></i>确&nbsp;定</a>
    </div>
        </form>
</div>

<div class="fap_cont_list" style="display: none;">
    <ul class="fav-list">
        <li <#if invoiceType??&&invoiceType=='商品明细'>class="selected" </#if>>
            <input type="hidden" value="商品明细"/>
            <a href="javascript:void(0);">
                商品明细 <i class="check-box"></i>
            </a>
        </li>
        <li <#if invoiceType??&&invoiceType=='设备耗材'>class="selected" </#if>>
            <input type="hidden" value="设备耗材"/>
            <a href="javascript:void(0);">
                设备耗材 <i class="check-box"></i>
            </a>
        </li>
        <li <#if invoiceType??&&invoiceType=='办公用品'>class="selected" </#if>>
            <input type="hidden" value="办公用品"/>
            <a href="javascript:void(0);">
                办公用品 <i class="check-box"></i>
            </a>
        </li>
        <li <#if invoiceType??&&invoiceType=='电脑配件'>class="selected" </#if>>
            <input type="hidden" value="电脑配件"/>
            <a href="javascript:void(0);">
                电脑配件 <i class="check-box"></i>
            </a>
        </li>
    </ul>
</div>

<script>
    $(function(){

        /*选择是否开发票*/
        $('.fap-options a').click(function(){
            $(this).addClass('selected').siblings().removeClass('selected');
            if($(this).attr('data-status') == 'open'){
                $('.taitou input').removeAttr('disabled');
                $('.neirong').show();
                $('.taitou').show();
            }
            else if($(this).attr('data-status') == 'close'){
                $('.taitou input').attr('disabled',true);
                $('.neirong').hide();
                $('.taitou').hide();
            }
        });

        /* 选择发票内容 */
        $('.fap-cont').click(function(){
           ;
            $(".fap_cont_list ul li").each(function () {
               var text= $(this).find("input").val();
                if($(".curValue").text().trim()==text){
                    $(".fap_cont_list").find(".selected").removeClass("selected")
                    $(this).addClass("selected");
                }
            })
            var chooseFap = dialog({
                width : 260,
                title : '选择一个发票内容',
                content : $('.fap_cont_list'),
                okValue : '确定',
                cancelValue : '取消',
                onshow : function(){
                    $('body').on('click','.fap_cont_list ul li',function(){
                        $(this).addClass('selected').siblings().removeClass('selected');
                    });
                },
                ok : function(){
                    var theValue = $('.fap_cont_list .selected a').text();
                    $('.fap-info .fap-cont span.curValue').text(theValue);
                    return true;
                },
                cancel : function(){
                    return true;
                }
            });
            chooseFap.showModal();
        });

    });


    $(function(){
//        //发票抬头验证
//        $("#invoiceTitle").blur(function(){
//            if($(this).val().trim()==''){
//                $("#invoiceTitle").parent().addClass("error");
//            }else{
//                $("#invoiceTitle").parent().removeClass("error");
//            }
//        })
    })

    function checkForm(){
       if($(".list-value .selected").attr("data-status")=='close'){
           $("#invoiceTitle").val("")
           $("#invoiceType").val("")
           $("#subForm").attr("action","suborder.htm").submit();
       }else{
            $("#invoiceType").val($(".curValue").text())
           $("#subForm").attr("action","suborder.htm").submit();
       }
    }
</script>


</body>
</html>