<!DOCTYPE html>
<html lang="zh-CN">
<head>
<#assign basePath=request.contextPath>
<meta charset="utf-8">
<title>商家入驻</title>
<link rel="stylesheet" href="http://kstore.qianmi.com/index_seven/css/style.css">
<link rel="stylesheet" href="${basePath}/css/receive.m.css">
<link rel="stylesheet" href="${basePath}/css/animate.css">
<style>
    .container{
        width: 1200px;
        margin: 0px auto;
    }
    /*以上样式无需引用*/
.tc{text-align: center;}
.section1 { background: url(${basePath}/images/business_01.jpg) 50%;}
.section2 { background: url(${basePath}/images/business_02.jpg) 50%;}
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
.process{ position: absolute; top:30%; width: 960px; left: 50%; margin-left: -480px;}
.pro1{ padding: 20px 67px; border:4px solid #595850; color: #9a9b9b; font-size: 24px; line-height: 45px; text-align: center; margin-top: 50px;}
.enter-but{ margin-top: 40px;}
.enter-but a{ display: inline-block; width: 190px; height: 60px; line-height: 60px; text-align: center; border-radius: 8px;  margin-right: 60px;}
.enter-but a.l-but{ background: #ea5b08; color: #fff; font-size: 24px;}
.enter-but a.r-but{ background: #cecece; color: #333; font-size: 24px;}
.flow{ position: relative; top:18%;}
.flow h2{ font-size: 60px; text-align: center; color: #fff; display: none;}
.flow p{ font-size: 24px; color: #fff; text-align: center; margin-top: 40px; display: none;}
.flow ul { width: 1250px; margin-top: 80px; display: none;}
.flow ul li{ width: 263px;height: 360px; float: left; margin-right: 49px;}
.xia-la{
    position: absolute; width: 100px; left: 50%; margin-left: -75px; bottom: 0;
    -webkit-animation-name: jump;
    animation-name: jump;
    -webkit-animation-timing-function: ease-in-out;
    animation-timing-function: ease-in-out;
    -webkit-animation-iteration-count: infinite;
    animation-iteration-count: infinite;
    -webkit-animation-duration: 2.5s;
    animation-duration: 2.5s;
    -webkit-animation-direction: normal;
    animation-direction: normal;
}
    .xia-la img{ width: 100px;}
</style>
<script src="${basePath}/js/jquery.min.js"></script>
<script src="${basePath}/js/jquery.fullPage.min.js"></script>
<script>
$(function(){
	$('#dowebok').fullpage({
        afterLoad: function(anchorLink, index){
            if(index == 2){
                $("h2").show();
                $("h2").addClass('animated rotateIn');
                $("p").show(500);
                $("p").addClass('animated lightSpeedIn');
                $("ul").show(700);
                $("ul").addClass('animated jello');
            }
        }
    });
});
</script>
</head>

<body>

<div id="dowebok">

	<div class="section section1 pr">
		<div style="background:#fff;">
			<div class="container">
		        <div class="head2 clearfix">
		            <a href="default.html" class="pull-left">
		                <img id="logo_pic" alt="" src="http://img01.ningpai.com/1432547534672.jpg" style="height:45px;width:auto;">
		            </a>
		            <h1>商家入驻</h1>
		        </div>            
		    </div>	    
		</div>
		<div class="process pr">
			<div class="tc"><img class="animated bounceInDown" src="${basePath}/images/img1.png"/></div>
			<div class="pro1 animated flipInX">
				与电商保健巨头携手，让您身价倍增，产品全渠道监控，让您销售更顺畅量身
打造培训体系，让您稳步提升让徽商更正规，让销售更简单。
			</div>
			 <div class="tc enter-but animated zoomInUp">
			 	<a href="${basePath}/register.html" class="l-but">我要入驻</a>
			 	<a href="${basePath}/login.html" class="r-but">商家登录</a>
                <a href="${basePath}/progress.html" class="l-but">进度查询</a>
             </div>
			 
		</div>
        <div class="xia-la"><img src="${basePath}/images/la.png"/></div>
	</div>
	<div class="section section2">
		<div class="flow">
			<div class="container">
				<h2>入驻流程</h2>
				<p>千米商城是以“满足经营所需”为宗旨的专业网上商场，是企业的一站式网上采购平台。</p>
				<ul class="clearfix">
					<li>
						<a href="#">
							<img src="${basePath}/images/img2.jpg"/>
						</a>
					</li>
					<li>
						<a href="#">
							<img src="${basePath}/images/img3.jpg"/>
						</a>
					</li>
					<li>
						<a href="#">
							<img src="${basePath}/images/img4.jpg"/>
						</a>
					</li>
					<li>
						<a href="#">
							<img src="${basePath}/images/img5.jpg"/>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
</div>
</body>
</html>