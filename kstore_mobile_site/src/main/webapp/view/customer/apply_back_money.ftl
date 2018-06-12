<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单-申请退款</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
</head>
<body>
    <input id="basePath" type="hidden" value="${basePath!''}"/>

<div class="order content-order-aftersale">
 <form id="upload-form" name="upload-form" method="post" enctype="multipart/form-data" target="hidden_frame">
    <input id="customerId" type="hidden" value="${cusId!''}" name="customerId"/>
    <input id="backGoodsId" type="hidden" value="${backGoodsIdAndSum}" name="backGoodsIdAndSum"/>
     <input name="isBack"" type="hidden" value="2" />
     <input id="applyCredentials" type="hidden" value="3" name="applyCredentials"/>
     <input id="backReason" type="hidden" value="" name="backReason"/>
    <div class="order-number">
        <div class="list-item">
            <h3 class="item-head text-them">申请退款</h3>

            <h3 class="item-head"><label for="">订单号：</label><span>${order.orderCode!''}</span></h3>

            <h3 class="item-head"><label for="">订单总额：</label><span class="text-them">￥${(order.orderPrice)?string('0.00')}</span></h3>
        </div>
    </div>
    <div class="mt10">
        <div class="order-info">
            <div class="list-body-line">
                <#if backorder??>
                    <#list backorder.goods as border>
                    <div class="list-item">
                    <div class="pro-item">
                        <div class="propic">
                        <a href="${basePath}/item/${border.goodsId}.html">
                            <img alt="" src="${border.goodsImg!''}"  width="78" height="78"  title="${(border.goodsName)!''}" alt="${(border.goodsName)!''}" />
                        </div>
                        <div class="prodesc">
                            <h3 class="title">${border.goodsName!''}</h3>

                            <p class="price">¥&nbsp;<span class="num"> <#if border.goodsPrice??>
                            ${border.goodsPrice?string('0.00')}
                            </#if></span></p>

                            <p class="number">×<span class="num">${(border.goodsNum)!'0'}</span></p>
                        </div>
                        </a>
                    </div>
                    <div class="return-num" style="display:none">
                        <h3>申请数量
                            <div class="buy-num">
                                <span class="reduce disabled">−</span>
                                <input type="text" placeholder="0">
                                <span class="add">+</span>
                            </div>
                        </h3>
                    </div>
                </div>
                    </#list>
                </#if>
                
            </div>
        </div>
    </div>
    <div class="mt10">
        <div class="list-item">
            <a href="javascript:;" class="return_reason">
                <h3 class="item-head">
                    <label for="">退款原因</label>
                    <span class="curValue" id="backReasons">请选择</span>
                    <i class="arrow-right"></i>
                    <span class="backReasonContent" style="color:red;"></span>
                </h3>
            </a>
        </div>
        <div class="list-item" style="display:none">
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
            <h3 class="item-head pr0"><label for="">退款金额</label>
                <span class="curValue">您最多可退款${(order.orderPrice)?string('0.00')}元</span>
            </h3>
            <div class="list-value">
                <div class="shuoming">
                    <input type="text" placeholder="${(order.orderPrice)?string('0.00')}"  readonly>
                </div>
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
      <#--  <div class="list-item">
            <h3 class="item-head">商品返回方式</h3>
            <div class="list-value returen-way">
                <a class="btn-grey selected" href="javascript:;">快递</a>
                <!--<a class="btn-grey" href="javascript:;">上门取件</a>&ndash;&gt;
            </div>
        </div>-->
        <div class="list-item"  style="display:none">
            <h3 class="item-head">上传凭证</h3>
            <div class="updata-pic">
                <div class="pic-list">
                    <ul>
                        <li><a href="#"><img src="images/pro-recommend01.jpg" alt=""></a></li>
                        <li><a href="#"><img src="images/pro-recommend02.jpg" alt=""></a></li>
                        <li>
                            <div class="uppic">
                                <a href="javascript:;">
                                    <i></i>
                                </a>
                                <input type="file" accept="image/jpg,image/gif,image/png,image/jpeg">
                            </div>
                        </li>
                    </ul>
                </div>
                <p>最多上传3张，每张不超过5M，支持bmp，gif，jpg， png，jpeg</p>
            </div>
        </div>
    </div>
     	<input type="hidden" name="orderId" value="${order.orderId}"/>
     	<input type="hidden" name="orderCode" value="${order.orderCode}"/>
	    <input type="hidden" name="backPrice" value="${backorder.moneyPaid}"/>
    <div class="cancel">
        <div class="list-item">
            <a class="btn btn-full" href="javascript:void(0);" id="submitButtonprice"><i></i>提交申请</a>
        </div>
    </div>
  </form>  
</div>
<input type="hidden" class="flag_saved" value="0">
</body>
<script type="text/javascript" src="${basePath}/js/customer/applybackorder.js"></script>
<script>
    $(function(){

        /* 选择退货原因 */
        $('.return_reason').click(function(){
            var reasonBox = dialog({
                width : 260,
                title : '选择一个退款原因',
                content : '<ul class="fav-list reasons"><li><a href="javascript:void(0);" value="1">不想买了<i class="check-box"></i></a></li><li><a href="javascript:void(0);" value="2">收货人信息有误<i class="check-box"></i></a></li><li><a href="javascript:void(0);" value="3">未按指定时间发货<i class="check-box"></i></a></li><li><a href="javascript:void(0);" value="4">其他<i class="check-box"></i></a></li></ul>',
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
    });
</script>
</html>