<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单-申请退货</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
    <style type="text/css">
        .choosegood{
            margin-top: 30px;
        }
        .pro-items{
            margin-left: 30px;!important;
            width: 90%;!important;
        }
        #backorderprice{
            color: #aeaeae;
        }
    </style>
</head>
<body>
    <input id="basePath" type="hidden" value="${basePath!''}"/>
<div class="order content-order-aftersale">
 <form id="upload-form" name="upload-form" method="post" enctype="multipart/form-data" target="hidden_frame">
    <input name="isBack"" type="hidden" value="1" />
    <input id="customerId" type="hidden" value="${cusId!''}" name="customerId"/>
    <input id="backGoodsId" type="hidden" value="" name="backGoodsIdAndSum"/>
    <input id="applyCredentials" type="hidden" value="3" name="applyCredentials"/>
    <input id="backReason" type="hidden" value="1" name="backReason"/>
    <input id="isUseCoupon" type="hidden" value="${(isUseCoupon)!0}" name="isUseCoupon"/>
    <div class="order-number">
        <div class="list-item">
            <h3 class="item-head text-them">申请退货</h3> 

            <h3 class="item-head"><label for="">订单号：</label><span>${order.orderCode!''}</span></h3>

            <h3 class="item-head"><label for="">订单总额：</label><span class="text-them">￥${(order.orderPrice)?string('0.00')}</span></h3>
            <span class="backPriceContent" style="color:red;"><#if isUseCoupon?? && isUseCoupon='1'>注:此订单使用了优惠券，请整单进行退货。</#if></span>
        </div>
    </div>
    <div class="mt10">
        <div class="order-info">
            <div class="list-body-line">
             <span class="backGoodsContent" style="color:red;"></span>
                <#if backorder??>
                    <#list backorder.goods as border>
                    <div class="list-item">
                        <#if isUseCoupon?? && isUseCoupon='1'><#else><div class="check-box choosegood"  backprice="${border.goodsBackPrice!0}" backstr="${border.goodsId!0},${border.goodsNum!0},${border.orderGoodsId}"></div></#if>
                    <div class="pro-item <#if !(isUseCoupon?? && isUseCoupon='1')>pro-items</#if>">
                        <div class="propic">
                        <a href="${basePath}/item/${border.goodsId}.html">
                            <img alt="" src="${border.goodsImg!''}"  width="78" height="78"  title="${(border.goodsName)!''}" alt="${(border.goodsName)!''}" />
                        </div>
                        <div class="prodesc" style="padding-right: 20px;!important;">
                            <h3 class="title"><#if border.isPresent?? && border.isPresent == '1'><span style="color: white;background-color: lightgrey;">&nbsp;赠品&nbsp;</span></#if> ${border.goodsName!''}</h3>

                            <p class="price">¥&nbsp;<span class="num"> <#if border.goodsPrice??>
                            ${border.goodsPrice?string('0.00')}
                            </#if></span></p>

                            <p class="number">×<span class="num">${(border.goodsNum)!'0'}</span></p>
                        </div>
                        </a>
                    </div>
                    <#--<div class="return-num">
                        <h3>申请数量
                            <div class="buy-num">
                                &lt;#&ndash;<span class="reduce disabled" onclick="mit(this)">−</span>
                                <input type="text" class="back_goods_id" placeholder="<#if staCheck??&staCheck='1'>${(border.goodsNum)!'0'}<#else>0</#if>" attr-maxnum="${(border.goodsNum)!'0'}" value="<#if staCheck??&staCheck='1'>${(border.goodsNum)!'0'}<#else>0</#if>" attr-price="${border.goodsPrice?string('0.00')}" attr-goods="${(border.goodsId)!''}" <#if staCheck??&staCheck='1'>readonly</#if>>
                                <span class="add <#if staCheck??&staCheck='1'>disabled</#if>" onclick="add(this)">+</span>&ndash;&gt;
                                <span>${(border.goodsNum)!'0'}</span>
                            </div>
                            <span class="numError" style="color: red; "></span>
                        </h3>
                    </div>-->
                </div>
                    </#list>
                </#if>
                
            </div>
        </div>
    </div>
    <div class="mt10">
        <div class="list-item">
            <h3 class="item-head">退货原因</h3>
            <div class="list-value">
                <ul class="yuanying">
                    <li><div class="check-box checked bumai"></div>不想买了</li>
                    <li><div class="check-box zhiliang"></div>商品质量问题</li>
                    <li><div class="check-box bufu"></div>收到商品与描述不符</li>
                    <li><div class="check-box qita"></div>其他</li>
                </ul>
            </div>
        </div>
        <div class="list-item">
            <h3 class="item-head">申请凭据</h3>
            <div class="list-value">
                <ul class="pingju">
                    <li><div class="check-box checked noevidence"></div>无凭证</li>
                    <li><div class="check-box bill"></div>有发票</li>
                    <li><div class="check-box report"></div>有质检报告</li>
                </ul>
            </div>
        </div>
        <div class="list-item">
            <h3 class="item-head pr0"><label for="">问题说明</label>
                <span class="curValue">您最多可填写200字</span>
            </h3>
            <div class="list-value">
                <div class="shuoming">
                    <textarea name="backRemark" id="backRemark" cols="30" rows="3" maxlength="200"></textarea>
                     <span id="backRemark_tips"></span>
                     <span class="backRemarkContent" style="color:red;"></span>
                </div>
            </div>
        </div>
        <div class="list-item" style="display:none">
            <h3 class="item-head">商品返回方式</h3>
            <div class="list-value returen-way">
                <a class="btn-grey selected" href="javascript:;" backway="0">快递</a>
                <!--<a class="btn-grey" href="javascript:;">上门取件</a>-->
            </div>
        </div>
        <div class="list-item" id="pubevidence" style="display:none">
            <h3 class="item-head">上传凭证</h3>
            <div class="updata-pic">
                <div class="pic-list">
                    <ul id="show_pics${order.orderId}">
                        <li>
                            <div class="uppic">
                                <a href="javascript:;">
                                    <i></i>
                                </a>
                                <input type="file"class="upload_pics" id="uploadDocuments" name="uploadDocument" accept="image/jpg,image/gif,image/png,image/jpeg" order-id="${order.orderId}">
                               <input type="hidden" name="uploadDocuments" class="upload_documents"/>
                            </div>
                        </li>
                    </ul>
                </div>
                <p>最多上传3张，每张不超过5M，支持bmp，gif，jpg， png，jpeg</p>
            </div>
        </div>
        <div class="list-item">
            <h3 class="item-head pr0"><label for="">退款金额</label>
                <input type="hidden" class="back_price" value="${newprice!0}">
                <span class="curValue">您最多可退款${(newprice)?string('0.00')}元</span>
            </h3>
            <div class="list-value">
                <div class="shuoming">
                    <input type="text" id="backorderprice" <#if isUseCoupon?? && isUseCoupon='1'>placeholder="${(newprice)?string('0.00')}" value="${newprice!0}"<#else>placeholder="0.00"</#if> readonly>
                </div>
            </div>
        </div>
    </div>
       <input type="hidden" name="backWay" id="backWay">
     	<input type="hidden" name="orderId" value="${order.orderId}"/>
     	<input type="hidden" name="orderCode" value="${order.orderCode}"/>
	    <input type="hidden" name="backPrice" id="backPrice" value="${(newprice)?string('0.00')}"/>
    <div class="cancel">
        <div class="list-item">
            <a class="btn btn-full" href="javascript:void(0);" id="submitButtongoods"><i></i>提交申请</a>
        </div>
    </div>
  </form>  
   <iframe name="hidden_frame" style="display:none"></iframe>
</div>
<input type="hidden" class="flag_saved" value="0">
</body>
<script src="${basePath}/js/shoppingcart/jsOperation.js"></script>
<script type="text/javascript" src="${basePath}/js/customer/applybackorder.js"></script>
<script>
    $(function(){

        /* 选择退货原因 */
        $('.return_reason').click(function(){
            var reasonBox = dialog({
                width : 260,
                title : '选择一个退货原因',
                content : '<ul class="fav-list reasons"><li><a href="javascript:void(0);" value="1">不想买了<i class="check-box"></i></a></li><li><a href="javascript:void(0);" value="5">商品质量问题<i class="check-box"></i></a></li><li><a href="javascript:void(0);" value="6">收到商品与描述不符<i class="check-box"></i></a></li><li><a href="javascript:void(0);" value="4">其他<i class="check-box"></i></a></li></ul>',
                okValue : '确定',
                cancelValue : '取消',
                ok : function(){
                    $('.return_reason .curValue').text($('.reasons li.selected a').text());
                    $('#backReason').val($('.reasons li.selected a').attr('value'));
                    $(".backReasonContent").text("");
                    return true;
                },
                cancel : function(){
                    return true;
                }
            });
            reasonBox.showModal();
        });
        $('body').on('click','.reasons li',function(){
           $(this).addClass('selected').siblings().removeClass('selected');
        });

        /* 凭据选择 */
        $('.pingju li').click(function(){
            $(this).find('div.check-box').addClass('checked');
            $(this).siblings().find('div.check-box').removeClass('checked');
            if($(this).find('div').hasClass('noevidence')){
                $("#applyCredentials").val(3);
                $("#pubevidence").hide();
            }else if($(this).find('div').hasClass('bill')){
                $("#applyCredentials").val(1);
                $("#pubevidence").show();
            }else if($(this).find('div').hasClass('report')){
                $("#applyCredentials").val(2);
                $("#pubevidence").show();
            }
        });

        /* 退货原因 */
        $('.yuanying li').click(function(){
            $(this).find('div.check-box').addClass('checked');
            $(this).siblings().find('div.check-box').removeClass('checked');
            if($(this).find('div').hasClass('bumai')){
                $("#backReason").val(1);
            }else if($(this).find('div').hasClass('zhiliang')){
                $("#backReason").val(5);
            }else if($(this).find('div').hasClass('bufu')){
                $("#backReason").val(6);
            }else if($(this).find('div').hasClass('qita')){
                $("#backReason").val(4);
            }
        });
    });

    //选中商品退货
    $(".choosegood").click(function(){
        if($(this).hasClass("checked")){
            $(this).removeClass("checked")
        }else{
            $(this).addClass("checked")
        }
        checkmoney();
    });

    //计算退货金额
    function checkmoney(){
        var goods = $(".choosegood");
        var backStrs = "";
        var backprice = 0;
        for(var i=0;i<goods.length;i++){
            if($(goods[i]).hasClass("checked")){
                var price = $(goods[i]).attr("backprice");
                backprice = parseFloat(backprice)+parseFloat(price);
                var backStr = $(goods[i]).attr("backstr");
                backStrs +=  backStr + "-";

            }
        }

        $("#backorderprice").val(backprice.toFixed(2));
        $(".back_price").val(backprice);
        $("#backPrice").val(backprice);
        $("#backGoodsId").val(backStrs);
    }
</script>
</html>