$(function(){
	$(".act_list li").each(function(){
		var cur = $(this);
		cur.bind("mouseenter",function(){
			t = setTimeout(function(){
				cur.find(".act_info").animate({
					bottom: 0
				},400);
			},200);		
		});
		cur.bind("mouseleave",function(){
			clearTimeout(t);
			cur.find(".act_info").animate({
				bottom: -130
			},400);
		});
	});

	function tabs(t1, t2, t3) {
		$("."+ t1).find("li:first").addClass("cur");
		$("."+ t2).find("."+ t3 +":first").fadeIn('slow').addClass("show");
		$("."+ t1 +" li").each(function(n){
			var current = $(this);
			$(this).find("a").click(function(){
				var cur = $(this);
				$("."+ t1).find("li.cur").removeClass("cur");
				$("."+ t2).find("."+ t3 +".show").hide().removeClass("show");
				current.addClass("cur");
				$("."+ t2 +" ."+ t3 +":eq("+ n +")").fadeIn('slow').addClass("show");
				});
			});
		};
		
	tabs('brand_tabs','brand_cont','brand_box');
});