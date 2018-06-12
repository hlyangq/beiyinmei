<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单-评价</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <meta content="telephone=no" name="format-detection">
<#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
</head>
<body>
<input type="hidden" id="basePath" value="${basePath}">
<div class="order">
    <div class="order-common">
    <div class="order-info">
    <div class="list-body-line">
    <#if good??>
        <form role="form" id="commForm" action="${basePath}/addgoodscomment.htm" method="post">
            <input name="goodsId" value="${(good.goodsId)!''}" type="hidden" />
            <input name="orderGoodsId" value="${(good.orderGoodsId)!''}" type="hidden" />
            <input name="customerId" value="${(cusId)!''}" type="hidden" />
            <input name="orderId" value="${(good.orderId)!''}" type="hidden" id="orderId"/>
            <input name="isAnonymous" value="1" type="hidden" id="isnoname"/>
            <div class="list-item">
                <div class="pro-item">
                    <a href="${basePath}/item/${good.goodsId}.html">
                        <div class="propic">
                            <img alt="${good.goodsName}" src="${good.goodsImg}" width="78" height="78" />
                        </div>
                        <div class="prodesc">
                            <h3 class="title">${(good.goodsName)!''}</h3>

                            <p class="price">¥&nbsp;<span class="num">
                                <#if good.goodsPrice??>
									${good.goodsPrice?string('0.00')}
								</#if></span></p>
                        </div>
                </div>
            </div>
            </a>
    </div>
    </div>
        <div class="list-item common-box">
            <h3 class="item-head">评分
                <div class="star">
                    <#if !good.evaluateFlag?? || good.evaluateFlag=='0'>
                        <div class="star5"></div>
                    <#else> <div class="star${(good.commentScore)!'5'}"></div>
                    </#if>
                    <div class="star-btn">
                        <a href="javascript:;" data-star="1"></a>
                        <a href="javascript:;" data-star="2"></a>
                        <a href="javascript:;" data-star="3"></a>
                        <a href="javascript:;" data-star="4"></a>
                        <a href="javascript:;" data-star="5"></a>
                    </div>
                </div>
            </h3>
            <input type="hidden" name="commentScore"  id="commentScore" value="5"/>
            <div class="edit-box">
                <#if !good.evaluateFlag?? || good.evaluateFlag=='0'>
                    <textarea style="color: #aeaeae" name="commentContent" id="complaincon" cols="30" rows="4" maxlength="500"
                              onFocus="if (value =='说说你对它的想法和使用心得吧~'){value ='';this.style.color='#363636'}"
                              onBlur="if (value ==''){value='说说你对它的想法和使用心得吧~';this.style.color='#aeaeae'}">说说你对它的想法和使用心得吧~</textarea>
                <#else>
                    <span>${(good.commentContent)!''}</span>
                </#if>
            </div>
            <label class="pull-left" id="commTip">&nbsp;</label>
        </div>
        <h3 class="niming"><i class="select-box <#if !(good.isAnonymous)?? ||((good.isAnonymous)?? && good.isAnonymous='1')>selected</#if>"></i>匿名评价</h3>
        </form>
    </#if>
    <#if !(good.evaluateFlag)?? || good.evaluateFlag=='0'>
        <div class="list-item bottom-full">
            <a class="btn btn-full" href="javascript:void(0);" onclick="checkComment()">提&nbsp;交</a>
        </div>
    </#if>
    </div>
</div>

</body>
<script src="${basePath}/js/customer/comment.js"></script>
<script>
    <#if !(good.evaluateFlag)?? || good.evaluateFlag=='0'>
    $(function(){
        $('.star-btn a').click(function(){
            var starNum = parseInt($(this).attr('data-star'));
            $('.star>div:first-child').attr('class',('star' + starNum));
            $("#commentScore").val(starNum);
        });

        $(".select-box").click(function(){
            if($(this).hasClass("selected")){
                $(this).removeClass("selected");
                $("#isnoname").val(0);
            }else{
                $(this).addClass("selected");
                $("#isnoname").val(1);
            }
        });
    });
    </#if>
</script>
</html>