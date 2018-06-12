<#assign basePath=request.contextPath>

<!--<script src="${basePath}/index_seven/js/jquery-1.11.1.min.js"></script>-->
<script src="${basePath}/js/jquery-1.7.2.min.js"></script>
<script src="${basePath}/index_seven/js/jquery.slides.min.js"></script>
<script src="${basePath}/index_seven/js/jcarousellite_1.0.1.js"></script>
<script src="${basePath}/index_seven/js/angular.min.js"></script>
<script src="${basePath}/index_seven/js/template.js"></script>
<script src="${basePath}/js/jsOperation.js"></script>
<script src="${basePath}/index_seven/js/jquery.lazyload.min.js"></script>
<script src="${basePath}/index_twentyone/js/tabScript.js"></script>
<script src="${basePath}/index_twentyone/js/dialog.min.js"></script>
<script src="${basePath}/index_twentyone/js/app.js"></script>
<script>
	function browserRedirect() {
       var url = window.location.href;
       if(url.indexOf("weixin")>-1){
       		url = url.replace(/item/,"mobile/getwxcode3.htm?toUrl=item");
       		if(url.lastIndexOf(".html")==-1){
       			url = url.replace(/#/,".html#");
       		}
       		url = url.substring(0,url.indexOf("#"));
       		location = url;
       }
		var sUserAgent = navigator.userAgent.toLowerCase();
		var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
		var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		var bIsMidp = sUserAgent.match(/midp/i) == "midp";
		var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
		var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
		var bIsAndroid = sUserAgent.match(/android/i) == "android";
		var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
		var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
		if ( bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
			//跳转到移动版
			<#if ((mobProjectUrl??) && (mobProjectUrl?length>0))>
				location = "${mobProjectUrl}";
			</#if>
		}
	}
	browserRedirect();
</script>
	
<#include "newtop21.ftl"/>
<#include "newheader21.html"/>
<input type="hidden" id="basePath" value="${basePath}"/>
<input type="hidden" id="oldsearchtext" value="${(keyWorlds)!''}"/>

<style>
	.mainnav .showlist .menuView ul li h3 {width:120px;}
    .mainnav .showlist .menuView ul li p {width:580px;} 
    .dropdown-menu li > a {margin-right:10px;}
    .tz-bar {background:#d3ebff;height:25px;line-height:25px;overflow:hidden;padding:0 5px;}
    .tz-bar .ct_info {float:left;}
    .tz-bar .del {float:right;}
    .tzOrder {background:#f8f8f8;}
    .ct_price {margin-left:10px;color:#dc0002;font-weight:700;}
    .ct_price b {font-weight:500;font-family:arial, tahoma;margin-right:5px;}
    .ct_price em {font-style:normal;}
</style>
<style>
	.showlist {z-index:9997!important;}
	.mcOrder .mcCost .del {white-space:nowrap;}
	.mcGo a {color:#fff!important; text-decoration:none!important;}
	.section-header {background:none!important;}
	.menuImg .bd-list img {width:84px; height:36px;}
</style>
<script>
	$(function(){
		$(".myminishopcart").live("mouseover",minicartonload);
		$(".delect_minicart").live("click", function(){
			var emp1 = $(this).next().val();
			var emp2 = $(this).next().next().val();
			$.ajax({ url: "${basePath}/delshoppingcartbyid.htm?shoppingCartId="+emp1+"&goodsInfoId="+emp2,
				async:false,
				success: function(dats){
				if(dats == 1){
					minicartonload();
				}
			}});
		});
		//预加载mini购物车
		minicartonload();
	});

	function minicartonload(){
		$(".mcBoxList").html('');
		$(".mcTotalFee").html('0.00')
		$(".cartNum").html('0');
		$(".mcNumChecked").html('0');
		$.ajaxSetup({ cache: false });
		$.ajax({ url: "${basePath}/minisscart.htm",  async:false ,success: function(datee){
	  		var cartgoods = datee.shopcart.miniGoodsList;
	  		var cust= datee.cust;
	  		var empvalue = 0;
			//设置我的购物车显示购物车商品数量
	  		if(cartgoods != null){
				$(".sc_num em").html(cartgoods.length);
			}else{
				$(".sc_num em").html("0");
			}
            //比如一件商品买了3件,详细统计购买商品数量
            var countgoods=0;
			//购物车中有商品
			if(cartgoods != null && cartgoods.length>0){
				//mini购物车头部
				//循环输出购物车中的商品
				$(".headMiniShopcart").html("");
				for(var i = 0 ; i < cartgoods.length ; i++) {
                    countgoods += cartgoods[i].buNum;
                    var cartgood = "";
                    if (cartgoods[i].fitId == null) {
                        cartgood = '<li class="row"><div class="img pull-left">'+
                                '<a href="${basePath}/item/' + cartgoods[i].goodsInfoId + '" title="'+ cartgoods[i].productName +'">' +
                                ' <img alt="" src="' + cartgoods[i].productPic + '">' +
                                '</a> </div>' +
                                '<div class="name pull-left"> <a href="${basePath}/item/' + cartgoods[i].goodsInfoId + '" title="' + cartgoods[i].productName + '">' + cartgoods[i].productName +
                                '</a> </div>' +
                                '<div class="price pull-right"> <p> <span>￥'+ cartgoods[i].productPrice +'</span>'+
                                '<span>×'+ cartgoods[i].buNum +'</span>' +
                                '</p> <a href="javascript:;" class="delete del delect_minicart" >删除</a>'+
								"<input type= 'hidden' class= 'goodsid' value = '" + cartgoods[i].shoppingCartId + "'>" +
								"<input type= 'hidden' class= 'goodsinfo' value = '" + cartgoods[i].goodsInfoId + "'>" +
                                '</div> </li>';
                        $(".headMiniShopcart").append(cartgood);
                    } else {
						var cartgood = '<li class="sales_bar"> <strong>[套装]</strong>' +
											'<span>' + cartgoods[i].miniFit.fitName + '</span>'+
											'<span style="margin-left: 30px;"><b>优惠：</b>&yen;'+cartgoods[i].miniFit.fitPrice+'<em>×' + cartgoods[i].buNum + '</em></span>'+
											'<a href="javascript:;" class="delete del delect_minicart" style="text-align: right;float: right;">删除</a>'+
											"<input type= 'hidden' class= 'goodsid' value = '" + cartgoods[i].shoppingCartId + "'>" +
											"<input type= 'hidden' class= 'goodsinfo' value = '" + cartgoods[i].goodsInfoId + "'>" +
										'</li>';
                        $(".headMiniShopcart").append(cartgood);
                        var goodsList = cartgoods[i].miniFit.miniGoods;
                        //套装中货品的销售价格
                        var suitPrice =0;
                        for (var j = 0; j < goodsList.length; j++) {
                            cartgood = '<li class="row"> <div class="img pull-left">' +
											'<a href="${basePath}/item/' + goodsList[j].goodsInfoId + '" title="'+ goodsList[j].productName +'">' +
											' <img alt="" src="' + goodsList[j].productPic + '">' +
											'</a> </div>' +
											'<div class="name pull-left"> <a href="${basePath}/item/' + goodsList[j].goodsInfoId + '" title="' + goodsList[j].productName + '">' + goodsList[j].productName +
											'</a> </div>' +
											'<div class="price pull-right"> <p> <span>￥'+ goodsList[j].productPrice +'</span>'+
											'<span>×'+ goodsList[j].buNum +'</span>' +
                            				'</div> </li>';
                            suitPrice=suitPrice+goodsList[j].productPrice*goodsList[j].buNum;
                            $(".headMiniShopcart").append(cartgood);
                        }
                    }
                    //计算总价格
                    if(cartgoods[i].fitId==null){
                    	empvalue =accAdd(empvalue,accMul(cartgoods[i].productPrice,cartgoods[i].buNum));
                    }else{
                    	empvalue=accAdd(empvalue,accMul(suitPrice-cartgoods[i].miniFit.fitPrice,cartgoods[i].buNum));
                    }
				}
				//设置mini购物车底部
				$(".mcNumTotal").text(countgoods);
				$(".mcNumChecked").text(countgoods);
				$(".cartNum").text(countgoods);
				$(".mcTotalFee").text(empvalue);
                $(".headMiniShopcart").show();
					$(".minicart_empty").hide();
			}else{
				$(".minicart_empty").show();
				$(".headMiniShopcart").hide();
			}
		}});
	};
</script>

<script> 
//加入收藏
	function addToFavorite(siteName){
		try {   
			window.external.AddFavorite($("#basePath").val(),siteName);
	    } catch (e) {   
	        try {   
	            window.sidebar.addPanel($("#basePath").val(), siteName, "");   
	        } catch (e) {   
	            $(".collect_tip_cancel").click(function(){
	            	cls();
	            });
	            sdia('ctDia');
	        }   
	    }   
	}	
</script>