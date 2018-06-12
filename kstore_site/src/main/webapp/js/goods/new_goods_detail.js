$(function(){
	/*点击商品评价的时候*/
	$(".product_comment").click(function(){
		loadComment(1,3);
	});
	/*点击商品咨询的时候*/
	$(".product_ask").click(function(){
		loadCommentAsk(1,null);
	});

	/*点击评价中的tab时候*/
	$(".cmt_tabs li").click(function(){
		//选中自己，兄弟节点取消选中
       $(this).addClass('cur').siblings().removeClass('cur');
       var type = $(this).find("a").attr("data-role");
       loadComment(1,type);
	});
	/*点击咨询中的tab时候*/
	$(".consult_tabs li").click(function(){
		//选中自己，兄弟节点取消选中
	    $(this).addClass('cur').siblings().removeClass('cur');
	    loadCommentAsk(1,$(this).attr("data-role"));
	});
	//评论回复
	$(".reply_btn").click(function(){
		$(this).parent().next(".reply_wp").toggleClass("none");
	});


	/*点击页面上的共有多少人评价*/
	$(".comment_target").click(function(){
		$(".product_comment a").click();
	});
	
	/*判断是不是锚链接*/
	if(window.location.href.indexOf("#comment")!=-1){
		$(".product_comment a").click();
	}

    /*判断是不是锚链接*/
    if(window.location.href.indexOf("#consult")!=-1){
        $(".product_ask a").click();
    }
	/*搜索咨询回车事件*/
	$(".consult_text").keydown(function(e){
		if(e.keyCode==13){
			  $(this).parent().find(".cs_search_btn").click();
			}
		});

	//定义一个标记,标记为0时标明异步加载的相关数据未加载,否则,1 为已经加载
	var isLoaded="0";
	//
	$(window).scroll(function(){
		if($(document).scrollTop() > 450) {
			if(isLoaded=="0"){
				//加载相关的分类和品牌数据
				getRelativeCatAndBrand();
				//加载最终购买数据
				getFinalBuyVosByProductCatId();
				//热销商品
				getHotSalesRank();
				//console.log("ajax for relative data...");
				isLoaded="1";
			}
		};
	});

	//异步加载相关的分类,品牌信息
	function getRelativeCatAndBrand(){
		$.getJSON($("#basePathVal").val()+"/getRelativeCatAndBrandByProductCatId.htm",{catId:$("#belongCatId").val()},function(data){
			if(data!=null){
				//var cbHtml="";
				if(data.goodscates != null && data.goodscates.length > 0){
					var catHtml="";
                    var firstcateId= $(".first_catId").val();
                    for(var i=0;i < data.goodscates.length; i++){
						var cate=data.goodscates[i];
						catHtml+="<li><a class='aboutcate' href='"+$("#basePathVal").val()+"/list/"+cate.catId+"-"+firstcateId+".html"+"'>"+cate.catName+"</a></li>";
					}
					$("#relativeCatGoods").html(catHtml);
				}
				if(data.goodsBrands != null && data.goodsBrands.length > 0){
					//分类ID
					var cateId= $(".bread_crumb_cat_id").val();
					var firstcateId= $(".first_catId").val();
					var brandHtml="";
					for(var i=0;i<data.goodsBrands.length;i++){
						var brandvo=data.goodsBrands[i];
						brandHtml+="<li><a class='aboutbrand' href='"+$("#basePathVal").val()+"/list/"+cateId+"-"+firstcateId+".html?brands="+brandvo.brand.brandId+"'>"+brandvo.brand.brandName+"</a></li>";
					}
					$("#relativeBrandGoods").html(brandHtml);
				}
			}
		});
	}
	//异步加载最终购买信息
	function getFinalBuyVosByProductCatId(){
		$.getJSON($("#basePathVal").val()+"/getFinalBuyVosByProductCatId.htm",{catId:$("#belongCatId").val()},function(data){
			if(data!=null && data.length > 0){
				var fbHtml="";
				for(var i=0;i < data.length;i++){
					var product=data[i];
					fbHtml+="<li><div class='browse_img'><a alt='"+product.product.goodsInfoName+"' title='"+product.product.goodsInfoName+"' target='_blank' href='"+$("#basePathVal").val()+"/item/"+product.product.goodsInfoId+".html'><img class='lazy' alt='"+product.product.goodsInfoName+"' title='"+product.product.goodsInfoName+"' data-original='"+product.product.goodsInfoImgId+"'  width='120' height='120' src='"+product.product.goodsInfoImgId+"'/></a></div>";
					fbHtml+="<p class='browse_name mt5'><a alt='"+product.product.goodsInfoName+"' title='"+product.product.goodsInfoName+"' target='_blank' href='"+$("#basePathVal").val()+"/item/"+product.product.goodsInfoId+".html'>"+product.product.goodsInfoName+"</a></p>";
					fbHtml+="<p class='browse_price mt5'>¥ "+product.product.goodsInfoPreferPrice+"</p></li>";
				}
				$("#finalBuyGoods").html(fbHtml);
			}
		});
	}

	//异步加载热销商品
	function getHotSalesRank(){
		$.getJSON($("#basePathVal").val()+"/getHotSalesRank.htm",{catId:$("#belongCatId").val(),price:$("#goodsPriceVal").val(),brandId:$("#goodsBrandIdVal").val()}, function (data) {
			if(data.hotSales!=null) {
				var hsHtml = "<div class='charts-list tagMenu'><ul class='menu clearfix mt10'>";
				hsHtml += "<li class='typeshow current' attr_type='isShow1' onclick='changeShow(this)'>同价位</li>";
				hsHtml += "<li class='typeshow ' attr_type='isShow2' onclick='changeShow(this)'>同品牌</li>";
				hsHtml += "<li class='typeshow ' attr_type='isShow3' onclick='changeShow(this)'>同类别</li>";
				hsHtml += "</ul></div>";
				//同价位
				if (data.pricehotSales != null && data.pricehotSales.length > 0) {
					hsHtml += "<ul class='ranking_list mt10 isShow1' >";
					for (var i = 0; i < data.pricehotSales.length; i++) {
						var pricehotSales = data.pricehotSales[i];
						hsHtml += "<li><em ";
						if (i < 3) {
							hsHtml += "class='pre-top'";
						}
						hsHtml += ">" + (i + 1) + "</em>";
						hsHtml += "<a class='ranking_img fl' alt='" + pricehotSales.goodsInfoName + "' title='" + pricehotSales.goodsInfoName + "' target='_blank' href='" + pricehotSales.goodsInfoId + ".html'><img class='lazy' alt='" + pricehotSales.goodsInfoName + "' title='" + pricehotSales.goodsInfoName + "' data-original='" + pricehotSales.goodsInfoImgId + "'  width='50' height='50' src='"+pricehotSales.goodsInfoImgId+"'/></a>";
						hsHtml += "<div class='ranking_info fl ml10'>";
						hsHtml += "<a class='ranking_name' alt='" + pricehotSales.goodsInfoName + "' title='" + pricehotSales.goodsInfoName + "' target='_blank' href='" + $("#basePathVal").val() + "/item/" + pricehotSales.goodsInfoId + ".html'>" + pricehotSales.goodsInfoName + "</a>";
						hsHtml += "<p class='browse_price mt5'>¥" + pricehotSales.goodsInfoPreferPrice + "</p>";
						hsHtml += "</div></li>";
					}
					hsHtml += "</ul>";
				} else {
					hsHtml += "<ul class='ranking_list mt10 isShow1' ><li><em class='pre-top'>1</em><div class='ranking_info fl ml10'> ";
					hsHtml += "<a class='ranking_name'>近一个月同价位的货品暂无记录!</a></div></li></ul>";
				}
				//同品牌
				if (data.brandhotSales != null && data.brandhotSales.length > 0) {
					hsHtml += "<ul class='ranking_list mt10 isShow2' style='display: none'>";
					for (var i = 0; i < data.brandhotSales.length; i++) {
						hsHtml += "<li><em ";
						if (i < 3) {
							hsHtml += "class='pre-top'";
						}
                        var brandhotSales=data.brandhotSales[i];
						hsHtml += ">" + (i + 1) + "</em>";
						hsHtml += "<a class='ranking_img fl' alt='" + brandhotSales.goodsInfoName + "' title='" + brandhotSales.goodsInfoName + "' target='_blank' href='${basePath}/item/" + brandhotSales.goodsInfoId + ".html'><img class='lazy' alt='" + brandhotSales.goodsInfoName + "' title='" + brandhotSales.goodsInfoName + "' data-original='" + brandhotSales.goodsInfoImgId + "'  width='50' height='50' src='"+brandhotSales.goodsInfoImgId+"'/></a>";
						hsHtml += "<div class='ranking_info fl ml10'>";
						hsHtml += "<a class='ranking_name' alt='" + brandhotSales.goodsInfoName + "' title='" + brandhotSales.goodsInfoName + "' target='_blank' href='" + $("basePathVal").val() + "/item/" + brandhotSales.goodsInfoId + ".html'>" + brandhotSales.goodsInfoName + "</a>";
						hsHtml += "<p class='browse_price mt5'>¥" + brandhotSales.goodsInfoPreferPrice + "</p>";
						hsHtml += "</div>";
					}
					hsHtml += "</ul>";
				} else {
					hsHtml += "<ul class='ranking_list mt10 isShow2' style='display:none'><li><em class='pre-top'>1</em><div class='ranking_info fl ml10'> ";
					hsHtml += "<a class='ranking_name'>近一个月同品牌的货品暂无记录!</a></div></li></ul>";
				}
				//同类别
				if (data.hotSales != null && data.hotSales.length > 0) {
					hsHtml += "<ul class='ranking_list mt10 isShow3' style='display: none'>";
					for (var i = 0; i < data.hotSales.length; i++) {
						var product = data.hotSales[i];
						hsHtml += "<li><em ";
						if (i < 3) {
							hsHtml += "class='pre-top'";
						}
						hsHtml += ">"+(i+1)+"</em>";
						hsHtml += "<a class='ranking_img fl' alt='" + product.goodsInfoName + "' title='" + product.goodsInfoName + "' target='_blank' href='" + $("#basePathVal").val() + "/item/" + product.goodsInfoId + ".html'><img class='lazy' alt='" + product.goodsInfoName + "' title='" + product.goodsInfoName + "' data-original='" + product.goodsInfoImgId + "'  width='50' height='50' src='"+product.goodsInfoImgId+"'/></a>";
						hsHtml += "<div class='ranking_info fl ml10'>";
						hsHtml += "<a class='ranking_name' alt='" + product.goodsInfoName + "' title='" + product.goodsInfoName + "' target='_blank' href='" + $("#basePathVal").val() +"/item/" + product.goodsInfoId + ".html'>" + product.goodsInfoName + "</a>";
						hsHtml += "<p class='browse_price mt5'>" + product.goodsInfoPreferPrice + "</p>";
						hsHtml += "</div></li>";
					}
					hsHtml += "</ul>";
				} else {
					hsHtml += "<ul class='ranking_list mt10 isShow3' style='display:none'><li><em class='pre-top'>1</em><div class='ranking_info fl ml10'> ";
					hsHtml += "<a class='ranking_name'>近一个月同类别的货品暂无记录!</a></div></li></ul>";
				}
				$("#hotSalesGoodsRanking").append(hsHtml);
			}
		});
	}
	
});
//点击热销排行榜的同价位/同品牌/同类别间切换
function changeShow(obj){
	$(obj).siblings(".current").removeClass("current");
	$(obj).addClass("current");
	var str=$(obj).attr("attr_type");
	$(".ranking_list").hide();
	$("."+str).show();
}

function reply(t) {
	$(t).parent().next(".reply_wp").toggleClass("none");
};

function reply2(t) {
    $(t).parents(".reply_ct").find(".reply_wp").toggleClass("none");
};
function r_cmt(t) {
	$(t).parents(".reply_ct").find(".reply_wp").toggleClass("none");
};

/*加载商品评论*/
function loadComment(pageNo,type){
	/*清空相关的div*/
	$(".comment_list").html("");
	$(".comment_pages").html("");
	var productId=$("#productId").val();
	var params="&productId="+productId;
	params+="&pageNo="+pageNo;
	var totalCount=0;
	var haoCount=0;
	var zhongCount=0;
	var chaCount=0;
	/*AJAX查询商品评论总行数*/
	$.get("../goods/queryProducCommentForDetail.html?type=3"+params,function(data){
		/*设置所有的行数*/
		totalCount=data.rows;
        //当评价总数大于1条时，才会展示页面上的“[查看全部评价]”
        if(totalCount > 1){
            $("#sign").show();
        }else{
            $("#sign").hide();
        }
		if(type==3){
			putPageComment(type,data);
		}
		$(".allCount").text(totalCount);
		/*计算所占的百分比*/
		calcCommentPercent(totalCount,haoCount,zhongCount,chaCount);

        /*AJAX查询商品好评*/
        $.get("../goods/queryProducCommentForDetail.html?type=0"+params,function(data){
            /*设置所有的行数*/
            haoCount=data.rows;
            if(type==0){
                putPageComment(type,data);
            }
            $(".haoCount").text(haoCount);
            /*计算所占的百分比*/
            calcCommentPercent(totalCount,haoCount,zhongCount,chaCount);
        });
        /*AJAX查询商品中评*/
        $.get("../goods/queryProducCommentForDetail.html?type=1"+params,function(data){
            /*设置所有的行数*/
            zhongCount=data.rows;
            if(type==1){
                putPageComment(type,data);
            }
            $(".zhongCount").text(zhongCount);
            /*计算所占的百分比*/
            calcCommentPercent(totalCount,haoCount,zhongCount,chaCount);
        });
        /*AJAX查询商品差评*/
        $.get("../goods/queryProducCommentForDetail.html?type=2"+params,function(data){
            /*设置所有的行数*/
            chaCount=data.rows;
            if(type==2){
                putPageComment(type,data);
            }
            $(".chaCount").text(chaCount);
            /*计算所占的百分比*/
            calcCommentPercent(totalCount,haoCount,zhongCount,chaCount);
        });
    });
}



/*将查询到的评论加载到页面中*/
 function putPageComment(type,data){
	var commentHtml="";
	if(data.list!=null && data.list.length>0){
		for(var l=0;l<data.list.length;l++){
			var comment = data.list[l];

			commentHtml+="<div class='comment_box clearfix'>" +
							"<div class='cmt_user mt20 fl'>" +
								"<a class='user_img' href='javascript:;'><img alt='' ";
									if(comment.customerImg!=null&& comment.customerImg!=""){
										commentHtml+=" src="+comment.customerImg;
									}else{
										commentHtml+=" src='../images/user_img.gif'";
									}
            if(comment.isAnonymous=='1'){
                commentHtml+=" width='54' height='54' /></a>" +
                "<p class='uname'><a href='javascript:;'>*****</a></p></div><!--/cmt_user--><div style='margin-left: 10px; margin-top:30px;' class='star_wp fl'>" +
                "<span class='star star_"+parseFloat(comment.commentScore).toFixed(0)+"_0'></span></div>";
            }else{
                commentHtml+=" width='54' height='54' /></a>" +
                "<p class='uname'><a href='javascript:;'>"+comment.customerNickname+"</a></p></div><!--/cmt_user--><div style='margin-left: 10px; margin-top:30px;' class='star_wp fl'>" +
                "<span class='star star_"+parseFloat(comment.commentScore).toFixed(0)+"_0'></span></div>";
            }


            commentHtml+="<div class='x-model fl'> </div>";


        //<!--<b class='cmt_arrow'></b><div class='cmt_tit clearfix'>-->" +
        //    "<!-- <span class='cmt_date fr'>"+timeStamp2String(comment.publishTime)+"</span></div> --> <!--/cmt_tit-->"

            commentHtml+="<div class='cmt_main fr'>";
            commentHtml+="<div class='cmt-det'> "+comment.commentContent+"</div>"
            commentHtml+="<div class='clearfix'> <span class='buy_date'>"+timeStamp2String(comment.publishTime)+"</span> </div> <div class='cmt_content mt10'>";
            //commentHtml+="<dl class='clearfix'><dt>标签：</dt><dd><ul class='rec_points clearfix'>";
		   ///*加载评论标签*/
		   //if(comment.commentTag!=null){
			//   var tags = comment.commentTag.split(",");
			//   for(var i =0;i<tags.length;i++){
			//	   commentHtml+="<li>"+tags[i]+"</li>";
			//   }
		   //}
		   commentHtml+="<div class='clearfix'>";
		   if(null != comment.imageList && comment.imageList.length>0){
			   commentHtml+=" <ul class='od_list clearfix'>";
			   for(var i =0;i<comment.imageList.length;i++){
				   commentHtml+="<li data-img="+comment.imageList[i].imageName+"><a href='javascript:;'><img alt='' src="+comment.imageList[i].imageName+" width='60' height='60' /></a>" +
				   		"<b></b></li>";
			   }
			   commentHtml+="</ul><!--/od_list--><div class='photo_viewer'><img alt='' src='' /></div><!--/photo_viewer-->";
		   }
            commentHtml+="</div>";//图片div

            commentHtml+=" <div class='clearfix'> ";
            if(comment.commentTag!=null){
                commentHtml+=  "<ul class='rec_points clearfix'> " ;
                var tags = comment.commentTag.split(",");
                for(var i =0;i<tags.length;i++){
                    commentHtml+="<li>"+tags[i]+"</li>";
                }
                commentHtml+="</ul>";//标签ul
            }
            commentHtml+="</div>";//标签div



            commentHtml+="<div class='tl'><a class='reply_btn' onclick='reply(this)' href='javascript:;'>回复(";
		   if(null != comment.replays && comment.replays.length>0){
			   commentHtml+=comment.replays.length;
		   }else{
			   commentHtml+="0";
		   }
		   commentHtml+=")</a></div>";

		   commentHtml+="<div class='reply_wp mt10 clearfix none'><div class='arrow'><em>◆</em><span>◆</span>" +
		   				"</div><!--/arrow--><div class='reply_cont'>回复：<div class='reply_input mt5 clearfix'>" +
		   				"<input style='width:380px' class=' reply_txa fl replay_txa_"+comment.commentId+"'/><input class='replyBtn fr' type='button' onclick='upReplay(this)' data-comment="+comment.commentId+" value='回复' />" +
		   				"</div><!--/reply_input--></div><!--/reply_cont--></div><!--/reply_wp-->";

		   commentHtml+="</div><!--/cmt_content--></div><!--/cmt_main-->";
            if(null != comment.replays && comment.replays.length>0){
                for(var i =0;i<comment.replays.length;i++){
                    commentHtml+="<div class='cb'></div><div class='reply_list mt20'><div class='reply_ct'><div class='clearfix'><strong class='fl'>"+parseFloat(parseFloat(i)+parseFloat(1))+"</strong>" +
                    " <div class='rp_wp fl ml10'> <div class='rp_con lh150'><span class='rp-name'><a href='javascript:;'>" +
                    comment.replays[i].customerNickname+"</a>回复<a href='javascript:;'>"+comment.customerNickname+"</a>：</span><span class='rp_ct'>" +
                    comment.replays[i].commentContent+"</span>" +
                    "</div><!--/rp_con--><div class='clearfix mt10'><span class='fl ml5'>"+timeStamp2String(comment.replays[i].publishTime)+"</span><!--<a class='rp_btn rp_type fr mr10' href='javascript:;' onclick=reply2(this)>回复</a>--></div>" +
                    "</div><!--/rp_wp--></div>";

                    commentHtml+="<div class='reply_wp mt10 clearfix none'> <div class='arrow'> <em>◆</em> <span>◆</span> " +
                    "</div><!--/arrow--> <div class='reply_cont'>回复： <div class='reply_input mt5 clearfix'>" +
                    " <textarea class='reply_txa fl'></textarea> <input class='replyBtn fr' type='button' " +
                    "value='回复'> </div><!--/reply_input--> </div><!--/reply_cont--> </div></div><!--/reply_ct--></div><!--/reply_list-->";
                }
            }
           commentHtml+= "</div><!--/comment_box-->";
		}
		/*加载分页部分*/
		var paging="";
		var pageNo=0;
		if((data.pageNo-2)>0){
			pageNo=(data.pageNo-2);
		}else{
			pageNo=data.firstPageNo;
		}
		var endNo=0;
		
		if((data.lastPageNo-1)>0){
			endNo=(data.lastPageNo-2);
		}else{
			endNo=1;
		}
		if(data.pageNo==1){
			paging+="<a class='nono' href='javascript:void(0);'>上一页</a>";
		}else{
			paging+="<a class='pg_prev' ";
			if(data.nextPageNo>0){
				paging+="onClick='loadComment("+data.prePageNo+","+type+");'" ;
			}
			paging+=" href='javascript:;'>上一页</a>";
			if(data.pageNo>=4){
				paging+="<a class='num' href='javascript:void(0)' onClick='loadComment("+data.prePageNo+","+type+");'>"+data.firstPageNo+"</a>";
			}
		}
		if((data.pageNo-3)>1){
			paging+="<span class='ellipsis'>...</span>";
		}
		/*定义循环的标记*/
		var no_index=0;
		for(var i=pageNo;i<=data.endNo;i++){
			if(no_index<=4){
				if(i==data.pageNo){
					paging+="<a class='num_cur prev' href='javascript:void(0);'>"+i+"</a>";
				}else{
					paging+="<a class='num' href='javascript:void(0);' onClick='loadComment("+i+","+type+");'>"+i+"</a>";
				}
			}
			/*循环一次,标记加1*/
			no_index++;
		}
		if(data.pageNo!=data.lastPageNo){
			if((data.lastPageNo-data.pageNo)>3){
				if(data.lastPageNo){
					paging+="<span class='ellipsis'>...</span>";
				}
			}
		}
		if((data.pageNo==data.lastPageNo || data.endNo<=1)){
			if(data.lastPageNo>data.pageNo){
				paging+="<a class='num_cur' href='javascript:void(0);'>"+data.lastPageNo+"</a>";
			}
			paging+="<a class='nono' href='javascript:void(0);'>下一页</a>";
		}else{
			if((data.lastPageNo-data.pageNo)>=3){
				if(data.lastPageNo>5){
					paging+="<a class='num' href='javascript:void(0);' onClick='loadComment("+data.lastPageNo+","+type+");' >"+data.lastPageNo+"</a>";
				}
			}
			paging+="<a class='pg_next' href='javascript:void(0);'";
			if(data.nextPageNo>0){
				paging+=" onClick='loadComment("+data.nextPageNo+","+type+");'";
			}
			paging+=" >下一页</a>";
		}
		$(".comment_pages").html(paging);
		/*加载分页部分 END*/
		$(".all_cmt").show();
	}else{
		commentHtml+="<div ><p style='margin-bottom:2px;'>暂无商品评价！</p><span>只有购买过该商品的用户才能进行评价。</span></div>";
		$(".all_cmt").hide();
	}
	$(".comment_list").html(commentHtml);
		var spec =$(".chooseParamId");
		var html="";
     // <p><span>颜色：</span>银色</p> <p><span>型号：</span>公开版（16G ROM）</p> ";
		if(null != spec && spec.length>0){
			   for(var i=0;i<spec.length;i++){
				   html+="<p><span>"+$(spec[i]).attr('data-spec')+"：</span>"+$(spec[i]).attr('data-detail')+"</p>";
			   }
			   $(".x-model").html(html);
		}
		
		initImgClick();
		
		
		/*绑定回复框的回车事件*/
		$('.reply_txa').keydown(function(e){
			if(e.keyCode==13){
			  $(this).parent().find(".replyBtn").click();
			}
		});
}
 /*初始化晒单中的图片的点击事件*/
 function initImgClick(){
	//评论晒单
		$(".od_list").each(function(){
			var _this = $(this);
			_this.find("a").click(function(){
				if($(this).parent().hasClass("cur")) {
					$(this).parent().removeClass("cur");
					_this.next(".photo_viewer").hide().width(0).height(0);
					_this.next(".photo_viewer").find("img").attr("src","").width(0).height(0);
				} else {
					var _src = $(this).parent("li").attr("data-img");
					var img_url = _src + "?" + Date.parse(new Date());
					var _img = new Image();
					_img.src = img_url;
					var nw = _this.next(".photo_viewer").width();
					var nh = _this.next(".photo_viewer").height();
					_this.find(".cur").removeClass("cur");
					$(this).parent("li").addClass("cur");
					_this.next(".photo_viewer").show().width(nw).height(nh);
					_this.next(".photo_viewer").find("img").attr("src",_src);
					_img.onload = function(){
						var nw = _img.width + 8;
						var nh = _img.height + 8;
                        if(nw > 490){
                            nw = 490;
                        }if(nh>430){
                            nh=430;
                        }
						_this.next(".photo_viewer").find("img").animate({
							width: nw - 8,
							height: nh - 8
						},500);
						_this.next(".photo_viewer").animate({
							width: nw,
							height: nh
						},500);
					};
				};
			});
			_this.next(".photo_viewer").click(function(){
				_this.find(".cur").removeClass("cur");
				$(this).hide().width(0).height(0);
				$(this).find("img").attr("src","").width(0).height(0);
			});
		});
 }

 
/*加载商品咨询*/
function loadCommentAsk(pageNo,type){
	//初始化咨询列表
	$(".consult_list").html("");
	//初始化咨询分页
	var title=$(".consult_text").val();
	var productId=$("#productId").val();
	var params="productId="+productId;
	params+="&pageNo="+pageNo;
	params+="&title="+title;
	if(null != type && type!=0){
		params+="&askType="+type;
	}
	/*AJAX查询商品咨询*/
	$.get("../goods/queryProducCommentForDetail.html?"+params,function(data){
		$(".consult_count").html(data.list.length);
		if(data!=null && data.list.length>0){
			var askHtml="";
			for(var i=0;i<data.list.length;i++){
				if(data.list[i].isDisplay==1){
					var ask=data.list[i];
					askHtml+="<div class='consult_item'><dl class='consult_user clearfix'><dt>网&nbsp;&nbsp;友：</dt>" +
							"<dd><span class='cs_name'>"+ask.customerNickname+"</span><span class='ml30'>"+timeStamp2String(ask.publishTime)+"</span></dd>" +
							"</dl><!--/consult_user--><dl class='cs_con clearfix'><dt>咨询内容：</dt><dd><a href='javascript:;'>"+ask.commentContent+"</a></dd></dl><!--/cs_con-->";
					if(ask.replays!=null && ask.replays.length>0){
						for(var j=0;j<ask.replays.length;j++){
							askHtml+="<dl class='cs_reply clearfix'><dt>客服回复：</dt><dd>" +
									"<p class='fl'>"+ask.replays[j].commentContent+"</p>" +
									" <span class='cr_date fr'>"+timeStamp2String(ask.replays[j].publishTime)+"</span></dd></dl><!--/cs_reply-->";
						}
					}
					askHtml+="</div><!--/consult_item-->";
				}
			}
			/*将数据显示到页面上*/
			$(".consult_list").html(askHtml);
			$(".all_consult_tip").show();
			
			/*加载分页部分*/
			var paging="";
			var pageNo=0;
			if((data.pageNo-2)>0){
				pageNo=(data.pageNo-2);
			}else{
				pageNo=data.firstPageNo;
			}
			var endNo=0;
			
			if((data.lastPageNo-1)>0){
				endNo=(data.lastPageNo-2);
			}else{
				endNo=1;
			}
			if(data.pageNo==1){
				paging+="<a class='no_pages pg_prev' href='javascript:void(0);'>上一页</a>";
			}else{
				paging+="<a class='pg_prev' ";
				if(data.nextPageNo>0){
					paging+="onClick='loadCommentAsk("+data.prePageNo+","+type+");'" ;
				}
				paging+=" href='javascript:;'>上一页</a>";
				if(data.pageNo>=4){
					paging+="<a class='num' href='javascript:void(0)' onClick='loadCommentAsk("+data.prePageNo+","+type+");'>"+data.firstPageNo+"</a>";
				}
			}
			if((data.pageNo-3)>1){
				paging+="<span class='ellipsis'>...</span>";
			}
			/*定义循环的标记*/
			var no_index=0;
			for(var i=pageNo;i<=data.endNo;i++){
				if(no_index<=4){
					if(i==data.pageNo){
						paging+="<a class='num_cur prev' href='javascript:void(0);'>"+i+"</a>";
					}else{
						paging+="<a class='num' href='javascript:void(0);' onClick='loadCommentAsk("+i+","+type+");'>"+i+"</a>";
					}
				}
				/*循环一次,标记加1*/
				no_index++;
			}
			if(data.pageNo!=data.lastPageNo){
				if((data.lastPageNo-data.pageNo)>3){
					if(data.lastPageNo){
						paging+="<span class='ellipsis'>...</span>";
					}
				}
			}
			if((data.pageNo==data.lastPageNo || data.endNo<=1)){
				if(data.lastPageNo>data.pageNo){
					paging+="<a class='num_cur' href='javascript:void(0);'>"+data.lastPageNo+"</a>";
				}
				paging+="<a class='no_pages pg_next' href='javascript:void(0);'>下一页</a>";
			}else{
				if((data.lastPageNo-data.pageNo)>=3){
					if(data.lastPageNo>5){
						paging+="<a class='num' href='javascript:void(0);' onClick='loadCommentAsk("+data.lastPageNo+","+type+");' >"+data.lastPageNo+"</a>";
					}
				}
				paging+="<a class='pg_next' href='javascript:void(0);'";
				if(data.nextPageNo>0){
					paging+=" onClick='loadCommentAsk("+data.nextPageNo+","+type+");'";
				}
				paging+=" >下一页</a>";
			}
			$(".cosult_pages").html(paging);
			/*加载分页部分 END*/
			
			
		}else{
			$(".consult_list").html("<div class='no_data'><p style='line-height:40px;'>暂无咨询信息！</p></div>");
			$(".all_consult_tip").hide();
		}
	});
}
/*发表商品评论回复*/
function upReplay(obj){
	var commentId = $(obj).attr("data-comment");
	var content = $(".replay_txa_"+commentId).val();
	/*如果输入的回复内容为空,就提示不能输入空,并跳转方法*/
	if(content==null || content==""){
		/*初始化弹框样式*/
		$(".info_tip_content2").html("请输入回复内容！");
		$(".info_tip_img2").attr("src","../images/error.png");
		$(".info_tip_cancel2").attr("href","javascript:;").hide();
		$(".info_tip_submit2").attr("href","javascript:;").html("确定").show().unbind("click");
		$(".info_tip_submit2").click(function(){
			cls();
		});
		dia(2);
		return;
	}else if(content.length<10){
		/*初始化弹框样式*/
		$(".info_tip_content2").html("回复内容不能小于10个字符！");
		$(".info_tip_img2").attr("src","../images/error.png");
		$(".info_tip_cancel2").attr("href","javascript:;").hide();
		$(".info_tip_submit2").attr("href","javascript:;").html("确定").show().unbind("click");
		$(".info_tip_submit2").click(function(){
			cls();
		});
		dia(2);
		return;
	} else {
        var reg= /^([\u4e00-\u9fa5_A-Za-z0-9 \\`\\~\\!\\@\\#\\$\\^\\&\*\(\)\=\{\}\'\:\;\'\,[\]\.\/\?\~\！\@\#\￥\…\…\&\*\（\）\;\—\|\{\}\【\】\‘\；\：\”\“\'\。\，\、\？])+$/;
        if(!reg.test(content)){
            $(".info_tip_content2").html("回复内容不能包含特殊字符！");
            $(".info_tip_img2").attr("src","../images/error.png");
            $(".info_tip_cancel2").attr("href","javascript:;").hide();
            $(".info_tip_submit2").attr("href","javascript:;").html("确定").show().unbind("click");
            $(".info_tip_submit2").click(function(){
                cls();
            });
            dia(2);
            return;
        }
    }
	/*ajax执行发表回复*/
	$.post("../goods/uploadCommentReplay.html?replayContent="+content+"&commentId="+commentId,function(data){
		if(data>=0){
			var pageNo=$(".comment_pages .num_cur").html();
			var type=$(".cmt_tabs .cur").find("a").attr("data-role");
			loadComment(pageNo,type);
		}else{
			/*初始化弹框样式*/
			$(".info_tip_content2").html("是否跳转到登录页?");
			$(".info_tip_img2").attr("src","../images/index_ques.png");
			$(".info_tip_cancel2").attr("href","javascript:;").html("取消").show();
			$(".info_tip_submit2").attr("href","../login.html?item/"+$("#productId").val()).show().html("确定").unbind("click");
			$(".info_tip_cancel2").click(function(){
				cls();
			});
			dia(2);
		}
	});
	
}



/*计算商品的评论所占百分比*/
function calcCommentPercent(totalCount,haoCount,zhongCount,chaCount){
	var haoPercent=((haoCount/totalCount)*100).toFixed(0);
	haoPercent=isNaN(haoPercent)?0:haoPercent;
	var zhongPercent=((zhongCount/totalCount)*100).toFixed(0);
	zhongPercent=isNaN(zhongPercent)?0:zhongPercent;
	var chaPercent=((chaCount/totalCount)*100).toFixed(0);
	chaPercent=isNaN(chaPercent)?0:chaPercent;
	$(".haoPercent").text(haoPercent+"%");
	$(".haoPercentLine").width(haoPercent+"%");
	$(".bigHaoPercent").html(haoPercent+"<em>%</em>");
	$(".zhongPercent").text(zhongPercent+"%");
	$(".zhongPercentLine").width(zhongPercent+"%");
	$(".chaPercent").text(chaPercent+"%");
	$(".chaPercentLine").width(chaPercent+"%");
}
/*处理时间格式化*/
function timeStamp2String(time){
		var date=new Date(parseFloat(time));
	    var datetime = new Date();
	    datetime.setTime(date);
	    var year = datetime.getFullYear();
	    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}