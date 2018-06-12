$(function(){
	basePath = $("#basePath").val();
	//判断是否有保存的搜索关键字
	if($("#oldsearchtext")){
		$(".inputSearch").val($("#oldsearchtext").val());
	}
	
	loadInitProvince();

    //全部商品分类
    $(".dropdown-menu li:gt(7)").hide();
    $(".showlist").mouseover(function(){
        $(".dropdown-menu").show();
    }).mouseout(function(){
        $(".dropdown-menu").hide();
    });

    //首页展开隐藏
    function proSort(){
        var st = 0;
        $(".showlist").bind("mouseenter",function(){
            st = setTimeout(function(){
            },200);
        });
        $(".showlist").bind("mouseleave",function(){
            clearTimeout(st);
        });
    };
    //首页判断
    if($("#isIndex").val()) {
        //判断是否显示分类导航
        if($("#tempcbshowflag").val()==0){
            $(".showlist").show();
        }
    }else{
        proSort();
    };

    var t = "";
    $(".dropdown-menu li").mouseover(function(){
        var cur = $(this);
        var index = $(this).index();
        var dl = $(".dropdown-menu").children("li");
        t = setTimeout(function(){
            dl.removeClass("hover").eq(index).addClass("hover");
            $(".menuView:visible").hide();
            $(".menuView").eq(index).show();
        },100);
    }).mouseout(function(){
        clearTimeout(t);
    });
    function hideSort(){
        $(".dropdown-menu").find(".hover").removeClass("hover");
        $(".menuView:visible").hide();
    };
    $(".showlist").mouseleave(function(){
        hideSort();
    });
    $(".menucat").mouseover(function(){
        hideSort();
    });
    //幻灯
    if($("#slides").length > 0 && $("#slides a").length > 1) {
        $("#slides").slidesjs({
            play: {
                active: false,
                effect: "fade",
                auto: true,
                interval: 4000,
                pauseOnHover: true,
                restartDelay: 2500
            },
            navigation: {
                active: true
            },
            pagination: {
                active: true,
                effect: "fade"
            }
        });
    };

    //楼层切换
    $(".proLists").each(function(){
        var $this = $(this),
            $tabs = $this.find(".flr-tabs"),
            $index = 0;
        $this.find(".flr-tabs li:first").addClass("cur");
        $this.find(".proCon:first").show();
        $tabs.find("a").click(function(){
            $index = $(this).parent("li").index();
            $this.find(".cur").removeClass("cur");
            $this.find(".proCon:visible").fadeOut();
            $tabs.find("li:eq("+$index+")").addClass("cur");
            $this.find(".proCon:eq("+$index+")").fadeIn();
        });
    });
    //购物车
    $(".cartfd").mouseover(function(){
        $(this).find(".miniCart").show();
    }).mouseout(function(){
        $(this).find(".miniCart").hide();
    });
    $(".mcOrder").each(function(){
        var $this = $(this);
        $this.mouseover(function(){
            $this.find(".minus, .plus").css("visibility","visible");
            $this.find(".del").show();
        }).mouseout(function(){
            $this.find(".minus, .plus").css("visibility","hidden");
            $this.find(".del").hide();
        });
    });


    if($("#proScroll").length > 0) {
        $("#proScroll").jCarouselLite({
            btnNext: ".j-next",
            btnPrev: ".j-prev",
            //auto: 3600,
            speed: 500,
            visible: 4,
            scroll:4
        });
    };
    //楼层幻灯
    $(".floor_con").each(function(){
        var $this = $(this).find(".p-slides"),
            _this = $(this).find(".v-slides");
        $this.slidesjs({
            width: 580,
            height: 332,
            navigation: {
                active: true
            },
            pagination: {
                active: true,
                effect: "slide"
            }
        });
        _this.slidesjs({
            width: 395,
            height: 480,
            navigation: {
                active: true
            },
            pagination: {
                active: true,
                effect: "slide"
            }
        });
    });
    $(".menu-list").hover(
        function(){
            $(this).addClass('hover')
        },
        function(){
            $(this).removeClass('hover')
        }
    );
    //侧边导航
    var scrollHandle = function(){
        var scrollTop = $(document).scrollTop();
        var contentItems = $(".proLists");
        var currentItem = "";
        contentItems.each(function(){
            var contentItem = $(this);
            var offsetTop = contentItem.offset().top;
            if(scrollTop > offsetTop-200){
                currentItem = "#" + contentItem.attr("id");
            }
        });
        if(currentItem != $(".sideBar").find(".cur").attr("href") && currentItem != ""){
            $(".sideBar").find(".cur").removeClass("cur");
            $(".sideBar").find("[href="+currentItem+"]").addClass("cur");
        } else if(currentItem == "") {
            $(".sideBar").find(".cur").removeClass("cur");
        };
    };
    $(".sideBar .sideItem").click(function(){
        //$(window).off("scroll");
        $(this).addClass("cur").siblings(".cur").removeClass("cur");
        var $anchor = $(this).attr("href"),
            $distance = $($anchor).offset().top;
        $("html, body").stop().animate({
            scrollTop: $distance
        },function(){
            $(window).scroll(scrollHandle);
        });
        return false;
    });
    $("#backtoTop").click(function(){
        $(".sideBar").find(".cur").removeClass("cur");
        $(window).off("scroll");
        $("html, body").stop().animate({
            scrollTop: 0
        },function(){
            $(window).scroll(scrollHandle);
        });
    });

    $(window).scroll(function(){
        scrollHandle();
    });
    
	loadCurrentCity();

    //客服弹窗
    $(".customer_service").click(function(){
        var $top = $(this).prev("ul").height() + 85;
        $(".customer-box").show().css("top",$top);
    });
    $(".close-cs").click(function(){
        $(".customer-box").hide();
    });


});

//搜索
function checkSearch(){
	var search=$.trim($(".inputSearch").val());
	if(search==""){
		$(".inputSearch").val($(".inputSearch").attr("placeholder"));
	}else if(search.length>100){
		//限制长度，过长截取
		$(".inputSearch").val(search.substring(0,100));
	}
	$(".mallSearch-form").submit();
}

//加入收藏
function sdia(dname) {
    $(".bh-mask").fadeIn();
    $("#"+dname).fadeIn();
};
function scls(t) {
    $(".bh-mask").fadeOut();
    $(t).parents(".bh-dialog").fadeOut();
};


/*初始化已经选择的省份*/
function loadInitProvince(){
	$.ajax({
		type: 'post',
		url:basePath+"/getAllProvince.htm",
		async:false,
		success: function(data) {
			var provinceHtml="";
			for(var i=0;i<data.length;i++){
				provinceHtml+="<li><a class='check_province' onClick='loadCitys("+data[i].provinceId+",this);' href='javascript:;'>"+data[i].provinceName+"</a></li>";
				
			}
			$(".localPanel").html(provinceHtml);
		}
	});
	var provinces = $(".check_province");
	for(var i = 0;i<provinces.length;i++){
		if($(provinces[i]).html()==$(".ch_province").val()){
			var click = $(provinces[i]).attr("onclick");
		}
	}
}


/*根据点击的省份加载省份*/
function loadCitys(pid,pro){
	//$(".province_text").text($(pro).text());
	$.ajax({
        url:basePath+'/selectCity.htm?provinceId='+pid,
        success:function(data) {
            if(data==1) {
            	location.href=document.URL;
            }
        }
    });
	//$(".show_province").removeClass("cur");
	//$('#home_area').removeClass('hover');
}

function loadCurrentCity(){
    var city=$('#currentProvince').val();
    if(city==""){
    	$.ajax({
            url:basePath+'/defaulteProvince.htm',
            success:function(data) {
                if(data==1) {
                	$('.province_text').html("江苏");
                	 //location.reload();
                }
            }
        });
    	
    }
    $('.province_text').html(city);
}
