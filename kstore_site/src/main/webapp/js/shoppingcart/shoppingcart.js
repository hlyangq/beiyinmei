$(function(){
	loadInit();
	
	jQuery.fn.isChildAndSelfOf = function(b){ return (this.closest(b).length > 0); };
	$(".choose_area").mouseenter(function(){
		var cur = $(this);
		c_tm = setTimeout(function(){
			cur.addClass("choose_area_hover");
		},200);
	});
	
	$(document).click(function(event){
		if(!$(event.target).isChildAndSelfOf(".choose_area")) {
			$(".choose_area").removeClass("choose_area_hover");
		};
	});
	$(".close_area").click(function(){
		$(".choose_area").removeClass("choose_area_hover");
	});
	$(".show_province").click(function(){
		$(".province_list").attr("style","display: block;");
		$(".city_list").attr("style","");
		$(".distinct_list").attr("style","");
		$(".show_province").addClass("cur");
		$(".show_city").removeClass("cur");
		$(".show_distinct").removeClass("cur");
	});
	$(".show_city").click(function(){
		$(".province_list").attr("style","");
		$(".city_list").attr("style","display: block;");
		$(".distinct_list").attr("style","");
		$(".show_city").addClass("cur");
		$(".show_province").removeClass("cur");
		$(".show_distinct").removeClass("cur");
	});
	$(".show_distinct").click(function(){
		$(".province_list").attr("style","");
		$(".city_list").attr("style","");
		$(".distinct_list").attr("style","display: block;");
		$(".show_distinct").addClass("cur");
		$(".show_province").removeClass("cur");
		$(".show_city").removeClass("cur");
	});
});

/*初始化已经选择的城市*/
function loadInit(){
	
	$.ajax({
		type: 'post',
		url:"getAllProvince.htm",
		async:false,
		success: function(data) {
			var provinceHtml="";
			for(var i=0;i<data.length;i++){
				provinceHtml+="<li><a class='check_province'  onClick='loadCity("+data[i].provinceId+",this);' href='javascript:;'>"+data[i].provinceName+"</a></li>";
			}
			$(".province_list").html(provinceHtml);
		}
	});
	
	var provinces = $(".check_province");
	for(var i = 0;i<provinces.length;i++){
		if($(provinces[i]).html()==$(".province_text").html()){
			var click = $(provinces[i]).attr("onclick");
			var pid = click.substring(9,click.indexOf(","));
			var url = "getAllCityByPid.htm?provinceId="+pid;
			$.ajax({
				type: 'post',
				url:url,
				async:false,
				success: function(data) {
					var cityHtml="";
					for(var i=0;i<data.length;i++){
						cityHtml+="<li><a class='check_city'  onClick='loadDistinct("+data[i].cityId+",this);' href='javascript:;'>"+data[i].cityName+"</a></li>";
					}
					$(".city_list").html(cityHtml);
				}
			});
		}
	}
	var citys = $(".check_city");
	for(var i = 0;i<citys.length;i++){
		if($(citys[i]).html()==$(".city_text").html()){
			var click = $(citys[i]).attr("onclick");
			var cid = click.substring(13,click.indexOf(","));
			var url="getAllDistrictByCid.htm?cityId="+cid;
			$.ajax({
				type: 'post',
				url:url,
				async:false,
				success: function(data) {
					var distinctHtml="";
					for(var i=0;i<data.length;i++){
						distinctHtml+="<li><a class='check_distinct'  onClick='checkDistinct("+data[i].districtId+",this);' href='javascript:;'>"+data[i].districtName+"</a></li>";
					}
					$(".distinct_list").html(distinctHtml);
				}
			});
		}
	}
}


/*根据点击的省份加载城市*/
function loadCity(pid,pro){
	$(".ch_address").val("");
	$(".ch_province").val($(pro).text());
	$(".province_text").text($(pro).text());
	$(".show_province").removeClass("cur");
	$(".show_city").addClass("cur");
	$(".city_text").click();
	$(".province_list").attr("style","");
	$(".city_list").attr("style","display: block;");
	$.get("getAllCityByPid.htm?provinceId="+pid,function(data){
        $(".ch_city").val(data[0].cityName);
        $(".city_text").text(data[0].cityName);
		var cityHtml="";
		for(var i=0;i<data.length;i++){
			cityHtml+="<li><a class='check_city'  onClick='loadDistinct("+data[i].cityId+",this);' href='javascript:;'>"+data[i].cityName+"</a></li>";
		}
		$(".city_list").html(cityHtml);
	});
}


/*根据点击的城市加载区县*/
function loadDistinct(cid,city){
	$(".city_list").attr("style","");
	$(".distinct_list").attr("style","display: block;");
	$(".ch_city").val($(city).text());
	$(".city_text").text($(city).text());
	$(".show_city").removeClass("cur");
	$(".show_distinct").addClass("cur");
	$(".distinct_text").click();
	$.get("getAllDistrictByCid.htm?cityId="+cid,function(data){
        $(".ch_distinct").val(data[0].districtName);
        $(".distinct_text").text(data[0].districtName);
		var distinctHtml="";
		for(var i=0;i<data.length;i++){
			distinctHtml+="<li><a class='check_distinct'  onClick='checkDistinct("+data[i].districtId+",this);' href='javascript:;'>"+data[i].districtName+"</a></li>";
		}
		$(".distinct_list").html(distinctHtml);
	});
}
/*点击区县的时候*/
function checkDistinct(dId,dis){
	$(".ch_distinct").prop("value",$(dis).html());
	$(".dir_xz").html($(dis).html());
	$(".ch_distinctId").attr("value",dId);
	$(".ch_address").val($(".ch_province").val()+$(".ch_city").val()+$(".ch_distinct").val());
	$(".subDis").submit();
}