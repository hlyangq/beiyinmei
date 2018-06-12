<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>商品详情-查看图片</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <#assign basePath=request.contextPath>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>

    <script type="text/javascript" src="${basePath}/js/jquery-1.10.1.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery.event.drag-1.5.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery.touchSlider.js"></script>
</head>
<body>
<div class="pro-details-top">
    <a href="javascript:history.back();" class="back">
        <i class="ion-ios-arrow-left"></i>
    </a>
    <h1>商品详情</h1>
    <a href="javascript:;" class="box-tool">
        <i class="ion-ios-more"></i>
    </a>
    <div class="slideTool-box" style="display:none;">
        <ul>
            <li><a href="${basePath}/customercenter.html"><i class="mine"></i>我的</a></li>
            <li><a href="${basePath}/main.html"><i class="home"></i>主页</a></li>
            <li><a href="${basePath}/customer/mycollections.html"><i class="collection"></i>我的收藏</a></li>
        </ul>
    </div>
</div>
<div class="content-detail-pic">
    <div class="main_visual">
        <div class="main_image">
            <ul style="width: 494px; overflow: visible;">
                <#if detailBean.productVo.imageList??>
                    <#list detailBean.productVo.imageList as image>
                        <li><img src="${image.imageBigName!''}" alt=""/></li>
                    </#list>
                </#if>
            </ul>
            <a href="javascript:;" class="btn_prev"></a>
            <a href="javascript:;" class="btn_next"></a>
        </div>
    </div>
    <div class="slide-nav">
        <#if detailBean.productVo.imageList??>
            <#list detailBean.productVo.imageList as image>
                <a href="javascript:;" class=""></a>
            </#list>
        </#if>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $(".slide-nav a:first-child").addClass("cur");
        //顶部工具箱
        $('.box-tool').click(function(){
            $(this).next().slideToggle('fast');
        });

        $(".main_visual").hover(function () {
            $("#btn_prev,#btn_next").fadeIn()
        }, function () {
            $("#btn_prev,#btn_next").fadeOut()
        });

        $dragBln = false;

        $(".main_image").touchSlider({
            flexible: true,
            speed: 200,
            btn_prev: $("#btn_prev"),
            btn_next: $("#btn_next"),
            paging: $(".flicking_con a"),
            counter: function (e) {
                $(".slide-nav a").removeClass("cur").eq(e.current - 1).addClass("cur");
            }
        });

        $(".main_image").bind("mousedown", function () {
            $dragBln = false;
        });

        $(".main_image").bind("dragstart", function () {
            $dragBln = true;
        });

        $(".main_image a").click(function () {
            if ($dragBln) {
                return false;
            }
        });

        timer = setInterval(function () {
            $("#btn_next").click();
        }, 5000);

        $(".main_visual").hover(function () {
            clearInterval(timer);
        }, function () {
            timer = setInterval(function () {
                $("#btn_next").click();
            }, 5000);
        });

        $(".main_image").bind("touchstart", function () {
            clearInterval(timer);
        }).bind("touchend", function () {
            timer = setInterval(function () {
                $("#btn_next").click();
            }, 5000);
        });

    });
</script>
</html>