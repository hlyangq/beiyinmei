$(function(){
	queryMobCateBar();
    queryMobProduct(1);
	queryMobSubject(1);
	queryMobBrand(1);
	queryMobThird(1);
	//initTable();
});

function initTable(){
	$(".level-show").click(function(){
		var _this = $(this);
		_this.siblings(".level-hide").css("display","inline-block");
		_this.hide();
		_this.parents("tr").nextUntil(".level-1",".level-2").show();
	});
	$(".level-hide").click(function(){
		var _this = $(this);
		_this.siblings(".level-show").css("display","inline-block");
		_this.hide();
		_this.parents("tr").nextUntil(".level-1").hide();
	});
    $(".level-show2").click(function(){
        var _this = $(this);
        _this.siblings(".level-hide2").css("display","inline-block");
        _this.hide();
        _this.parents("tr").nextUntil(".level-2").show();
    });
    $(".level-hide2").click(function(){
        var _this = $(this);
        _this.siblings(".level-show2").css("display","inline-block");
        _this.hide();
        _this.parents("tr").nextUntil(".level-2 ").hide();
    });
	// $(".level-1").each(function(){
	// 	if($(this).find(".level-show").length > 0) {
	// 		$(this).find(".ct-choose").hide();
	// 	};
	// });

	$(".ct-choose").click(function(){
		var _cont = $(this).parents("tr").find(".link").text();
		$(".ctCont").text(_cont);
		$(".close").click();
	});
}