<!DOCTYPE html>
<html lang="zh-CN">
<head>
<#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <title>进度查询</title>
    <link rel="stylesheet" href="http://kstore.qianmi.com/index_seven/css/style.css">
    <link rel="stylesheet" href="${basePath}/css/common.m.css">
    <link rel="stylesheet" href="${basePath}/css/receive.m.css">
    <style>
        .container{width: 1200px; margin: 0px auto;}
        .head2 h1 {
            display: block;
            float: left;
            height: 43px;
            line-height: 43px;
            margin-left: 20px;
            padding-left: 20px;
            color: #666;
            border-left: 1px solid #C8C8C8;
            font-family: "微软雅黑";
            font-size: 25px;
        }
        .head2{margin: 0; padding: 20px 0;}
        .tips{ color:#c33; padding: 5px 18px; background:url(${basePath}/images/ero.png) left center no-repeat;}
    </style>
</head>

<body style="background: #fff;">
    <div class="container">
        <div class="head2 clearfix">
            <a href="default.html" class="pull-left">
                <img id="logo_pic" alt="" src="http://img01.ningpai.com/1432547534672.jpg" style="height:45px;width:auto;">
            </a>
            <h1>进度查询</h1>
        </div>
        <div class="enquiry-progress">
            <div>
                <h4>账户名:</h4>
                <div class="enquiry-input clearfix">
                    <input placeholder="请输入用户名/绑定手机/绑定邮箱" type="text" id="nameInput">
                    <button class="find">查询</button>
                </div>
                <p class="tips" style="display: none"></p>
            </div>
            <div class="enquiry-step">
                <div class="enquiry-step-d clearfix">
                    <div class="one step-m ">
                        <div class="circle">1</div>
                        <p>公司资质审核中</p>
                    </div>
                    <div class="s-line"></div>
                    <div class="two step-m">

                        <div class="circle">2</div>
                        <p>店铺信息审核中</p>
                    </div>
                    <div class="s-line"></div>
                    <div class="three step-m">
                        <div class="circle">3</div>
                        <p>审核通过/不通过</p>
                    </div>
                </div>
            </div>
            <a href="${basePath}/register.html" class="restart-in">重新入驻</a>
        </div>


    </div>
</body>
<script src="${basePath}/js/jquery.min.js"></script>
<script>
    $(function(){

    });
    $("#nameInput").focus(function(){
        $(".tips").hide();
    });
    $(".find").click(function(){
        if($("#nameInput").val() == ""){
            $(".one").removeClass("active");
            $(".two").removeClass("active");
            $(".three").removeClass("active");
            $(".three").find("p").html("审核通过/不通过");
            $(".tips").show().html("请输入您要查询的用户名！");
        }else{
            $.ajax({
                url:"queryProgress.htm?username="+$("#nameInput").val(),
                async : false,
                success:function(data) {
                    if(data == 1){
                        $(".one").addClass("active");
                    }else if(data == 2){
                        $(".one").addClass("active");
                        $(".two").addClass("active");
                        $(".three").addClass("active");
                        $(".three").find("p").html("审核通过");
                    }else if(data == 3){
                        $(".one").addClass("active");
                        $(".two").addClass("active");
                        $(".three").addClass("active");
                        $(".three").find("p").html("审核不通过");
                    }else if(data == 4){
                        $(".one").removeClass("active");
                        $(".two").removeClass("active");
                        $(".three").removeClass("active");
                        $(".three").find("p").html("审核通过/不通过");
                        $(".tips").show().html("该用户尚未创建店铺，请前往登陆创建！");
                    }else{
                        $(".one").removeClass("active");
                        $(".two").removeClass("active");
                        $(".three").removeClass("active");
                        $(".three").find("p").html("审核通过/不通过");
                        $(".tips").show().html("您输入的用户名不存在，请核实后查询！");
                    }
                }
            });
        }
    })
</script>
</html>