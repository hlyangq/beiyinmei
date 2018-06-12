var basePath = $("#basePath"). val();
var orderId = $("#orderId").val();
//商品评论
var flag =1;
function checkComment(){
	if(!checkContext()){
		return;
	}
    if(flag==1){
        $.ajax({
            url:basePath+"/addgoodscomment.htm",
            data:$("#commForm").serialize(),
            type:'post',
            success:function(data){
                flag =0;
                if(data>0){
                    setTimeout(function(){
                        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><i class="success"></i><h3>评价成功</h3></div></div>');
                    },1000);
                    setTimeout(function(){
                        window.location.href = basePath+"/comment-" + orderId + ".html";
                    },2000);
                }
            }
        });
    }
}


//评论内容
function checkContext(){
	var col = $("#complaincon").css("color");
	if(col == 'rgb(174, 174, 174)'){
		$("#commTip").text("字数在10-500之间!");
		$("#commTip").addClass("err");
		return false;
	}else{
		var str = $("#complaincon").val();
		var reg= /^([\u4e00-\u9fa5_A-Za-z0-9 \\`\\~\\!\\@\\#\\$\\^\\&\*\(\)\=\{\}\'\:\;\'\,[\]\.\/\?\~\！\@\#\￥\…\…\&\*\（\）\;\—\|\{\}\【\】\‘\；\：\”\“\'\。\，\、\？])+$/;
		//内容长度最多500
		if(str.trim().length>500 || str.trim().length<10) {
			$("#commTip").text("字数在10-500之间!");
			$("#commTip").addClass("err");
			return false;
		}
		if(!reg.test(str)){
			$("#commTip").text("不能包含特殊字符!");
			$("#commTip").addClass("err");
			return false;
		}
		$("#commTip").text("");
		$("#commTip").removeClass("err");
		return true;
	}
}