
function resetTabs(obj) {
	$(obj).parent().parent().next("div").find(">div").hide();
	$(obj).parent().parent().find("a").removeClass("current");
}
function loadTab() {
	$(".tab_content > div").hide();
	$(".tabs").each(function () {
		$(this).find("li:first a").addClass("current");
	});
	$(".tab_content").each(function () {
		$(this).find("div:first").show();
	});
	$(".tabs a").on("mouseover", function (e) {
		e.preventDefault();
		if ($(this).attr("class") == "current") {
			return;
		} else {
			resetTabs(this);
			$(this).addClass("current");
			$(this).parent().parent().next().find($(this).attr("name")).fadeIn();
		}
	});
}

