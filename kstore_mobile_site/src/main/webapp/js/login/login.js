/**
 * 
 */

function login() {
	var varification="";
    if($('#codeDiv').is(':visible')){
        varification = $("#code").val();
    }
	if (checkInput()) {
		$("input[type='text']").next().hide();
		var url = "checklogin.htm?username=" + $("#log_user").val() + "&password="
				+ $("#log_psd").val() + "&url=" + $("#urlhide").val()+"&code="+varification;
		$.ajax({
			url : url,
			success : function(data) {
				if (isNaN(data)) {
					window.location.href = data;
				}else if(data==3){
					$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>验证码错误</h3></div></div>');
			        setTimeout(function(){
			            $('.tip-box').remove();
			        },1000);
			        $(".eye").show();
			        $("#code_image").click();
			        $(".valid_img").show();
				}else if(data==2){
					$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>用户名不存在</h3></div></div>');
			        setTimeout(function(){
			            $('.tip-box').remove();
			        },1000);
			        $(".eye").show();
			        $("#code_image").click();
			        $(".valid_img").show();
				}else if(data==4){
					$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>用户名和密码不匹配</h3></div></div>');
			        setTimeout(function(){
			            $('.tip-box').remove();
			        },1000);
			        $(".eye").show();
			        $("#code_image").click();
			        $(".valid_img").show();
				}else if(data==5){
					$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>用户名和密码不匹配,请重新输入</h3></div></div>');
			        setTimeout(function(){
			            $('.tip-box').remove();
			        },1000);
			        $("#codeDiv").show();
			        $("#codeDiv .valid_img").show();
			        $(".eye").show();
			        $("#code_image").click();
			        $(".valid_img").show();
				}else if(data == 0){
					$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>用户名被冻结,请联系管理员解锁</h3></div></div>');
					setTimeout(function(){
						$('.tip-box').remove();
					},1000);
					$("#codeDiv").show();
					$("#codeDiv .valid_img").show();
					$(".eye").show();
					$("#code_image").click();
					$(".valid_img").show();
				} else {
					$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>用户或者密码错误，请重新输入</h3></div></div>');
			        setTimeout(function(){
			            $('.tip-box').remove();
			        },1000);
			        $(".eye").show();
			        $("#code_image").click();
			        $(".valid_img").show();
				}
			}
		});
	}
}

//注册协议
function xieyi(){
	window.location.href='getXieYi.htm';
}
function checkInput() {
	var x = $("#log_user").val();
	if (x == '' || x == null) {
		$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入用户名</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
	} else if (!isUser(x)) {
		$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>用户名格式错误</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
	} 
	x = $("#log_psd").val();
	if (x == '' || x == null) {
		$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入密码</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
	}
	//else if(!(/^[A-Za-z0-9\\w]{6,20}$/).test(x)){
	//	$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>密码格式必须是6-20位字母数字组合</h3></div></div>');
     //   setTimeout(function(){
     //       $('.tip-box').remove();
     //   },1000);
     //   return false;
	//}
	if(($('#codeDiv').is(':visible'))&&($("#code").val()==null ||$("#code").val()=='')){
		$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入验证码</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
    }
	return true;
}
function isUser(x) {
	if ((x.length >= 4) && (x.length <= 30)) {
		return true;
	} else {
		return false;
	}
}
document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) { // enter 键
		login();
	}
};

$("#log_user").focus(function(){
	$(this).attr("placeholder","");
});
$("#log_user").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","账号/手机号码");
	}
});
$("#log_psd").focus(function(){
	$(this).attr("placeholder","");
});
$("#log_psd").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","请输入密码");
	}
});
$("#code").focus(function(){
	$(this).attr("placeholder","");
});
$("#code").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","验证码");
	}
});