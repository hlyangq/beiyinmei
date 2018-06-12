<div class="new_member_left">
    <div class="title"><a href="${basePath}/customer/index.html">个人中心</a></div>
    <div class="menu_item">
        <a href="javascript:void(0)" class="item_title down">
            <span>订单中心</span>
        </a>
        <ul class="item_list" style="display: block;">
            <li class=""><a href="${basePath}/customer/myorder.html">我的订单</a></li>
            <li class=""><a href="${basePath}/customer/giftorder.html">积分兑换订单</a></li>
        <#--<li><a href="${basePath}/customer/marketorders.html">抢购订单</a></li>-->
        <#--<li><a href="${basePath}/customer/marketordergrounp.html">团购订单</a></li>-->
            <li><a href="${basePath}/customer/myfollw.html">我的关注</a></li>
            <li><a href="${basePath}/customer/browserecord.html">浏览历史</a></li>
        </ul>
    </div>
    <div class="menu_item">
        <a href="javascript:void(0)" class="item_title down">
            <span>客户服务</span>
        </a>
        <ul class="item_list" style="display: block;">
            <li><a href="${basePath}/customer/refundlist.html">取消订单记录</a></li>
            <li><a href="${basePath}/customer/ordercomplain.html">我的投诉</a></li>
        </ul>
    </div>

    <!-- 预存款信息 -->
    <div class="menu_item">
        <a href="javascript:void(0)" class="item_title down">
            <span>资产中心</span>
        </a>
        <ul class="item_list" style="display: block;">
            <li><a href="${basePath}/deposit/mydeposit.htm">我的预存款</a></li>
            <li><a href="${basePath}/deposit/withdraw-list.htm">提现记录</a></li>
            <li id="menu_myintegral"><a href="${basePath}/customer/myintegral.html">我的积分</a></li>
            <li id="mycoupon"><a href="${basePath}/mycoupon-1.html">我的优惠券</a></li>
        </ul>
    </div>

    <div class="menu_item">
        <a href="javascript:void(0)" class="item_title down">
            <span>账户中心</span>
        </a>
        <ul class="item_list" style="display: block;">
            <li><a href="${basePath}/customer/myinfo.html">账户信息</a></li>
            <li id="securitycenter"><a href="${basePath}/customer/securitycenter.html">账户安全</a></li>
            <li id="consume"><a href="${basePath}/customer/consume.html">消费记录</a></li>
            <!--
            <li><a href="${basePath}/customer/myintegral.html">我的积分</a></li>
            <li><a href="${basePath}/mycoupon-1.html">我的优惠券</a></li>
            -->
            <li><a href="${basePath}/customer/address.html">收货地址</a></li>
        </ul>
    </div>
    <div class="menu_item">
        <a href="javascript:void(0)" class="item_title down">
            <span>消息中心</span>
        </a>
        <ul class="item_list" style="display: block;">
            <li><a href="${basePath}/customer/comment.html">商品评价</a></li>
            <li id="consult"><a href="${basePath}/customer/consult.html">购买咨询</a></li>
            <li><a href="${basePath}/customer/insideletter.html">站内信息</a></li>
        </ul>
    </div>
</div>
<script>

    var href = window.location.href;
    $('.new_member_left a').each(function (index, item) {
        if (href.indexOf($(item).attr('href')) > 0) {
           $(item).parents('li').addClass('cur');
        }
    });

</script>
<!--new_member_left-->