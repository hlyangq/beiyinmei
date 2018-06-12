/**
 * 
 */
$(function(){


    var checkedUsing=$("#checkedUsing").val();
    if(checkedUsing=='0') {
        $(".code_tip").show();
        $(".code_tip").html("验证码错误").css("color", "#dd6330");
    }
	//验证码绑定onclick事件
	$("#checkCodeA").click(
		function(){
			$("#checkCodeImg").click();
		}
	);
	$("#pwd").focus(function(){
		$(this).parent().find(".vd_tip").show().html("请输入登录密码").css("color","#dd6330");
	});
	$("#pwd").blur(function(){
		$(this).parent().find(".vd_tip").hide().html("登录密码错误").css("color","#dd6330");
	});
	$("#npwd").focus(function(){
		$(this).parent().find(".vd_tip").show().html("请输入登录密码").css("color","#dd6330");
	});
	$("#npwd").blur(function(){
		$(this).parent().find(".vd_tip").hide().html("密码格式不正确,请输入6-20位长度的密码!").css("color","#dd6330");
	});
	$("#repwd").focus(function(){
		$(this).parent().find(".ne_tips").show().html("请再次输入登录密码").css("color","#dd6330");
	});
	$("#repwd").blur(function(){
		checkRePwd();
	});
	$("#code").focus(function(){
		$(this).parent().find(".vd_tip").show().html("请输入验证码").css("color","#dd6330");
	//	getPatcha();
	});
	$("#code").blur(function(){
		$(this).parent().find(".vd_tip").hide().html("验证码错误").css("color","#dd6330");
	});
	$("#mcode").focus(function(){
		$(this).parent().find(".resend").show().html("请输入短信验证码").css("color","#dd6330");
	});
	$("#mcode").blur(function(){
		$(this).parent().find(".resend").hide().html("短信验证码错误").css("color","#dd6330");
	});
	$("#mobile").focus(function(){
		$(this).next().next().show().html("请填写正确的手机号码，我们将免费把校验码发送到您的手机");
		//getPatcha();
	});
	$("#email").focus(function(){
		$(this).next().show().html("请填写您常用的邮箱").css("color","#dd6330");
	//	getPatcha();
	});
	$(".m_tip").hide();
	$(".timeleft").css("color","#e4393c");
	$(".timeleft").css("font-weight","bold");
//	win();
//	$(window).resize(function() {
//		win();
//	});
});

//function win() {
//	var _wd = $(window).width();
//	var _hd = $(window).height();
//	$(".s_dia").css("top", (_hd - $(".dialog").height()) / 2).css("left",
//			(_wd - $(".s_dia").width()) / 2);
//};
//
//function dia(n) {
//	$(".mask").fadeIn();
//	$(".dia" + n).fadeIn();
//};
//
//function cls() {
//	$(".dialog").fadeOut();
//	$(".mask").fadeOut();
//}
//验证手机onkeyup事件
function checkM(obj){
    obj.next().html("发送短信验证码");
    if(checkMobile()){
        obj.next().removeAttr("disabled");
        $(".mobile_tip").html("");
    }
}
//验证邮箱onkeyup事件
function checkE(obj){
    obj.next().hide().html("&nbsp;").css("color","#dd6330");
    checkEmail();
}

function modifyPwd(type){
	if(! checkPwd()){
		return false;
	}
    if(! checkCurrentCode()){
        return false;
    }

	if(! checkPatcha()){
		return false;
	}
	if(! checkCurrentPwd()){
		return false;
	}

//	if(! checkMCode()){
//		return;
//	}
//	if(! checkCurrentMCode()){
//		return;
//	}

	//location.href="reirectpem.html?type="+type+"&ut=pwd&pwd="+$("#pwd").val()+"&code="+$("#code").val();
    $('#password').val($('#pwd').val());
    $('#reirectpem').submit();
	//location.href="reirectpem.html?type="+type+"&ut=pwd&pwd="+$("#pwd").val();

}
function modifyMobile(type){
	
	/*if(! checkMobile(1)){
		return;
	}*/
	if(! checkMCode()){
		return;
	}
	if(! checkCurrentCode()){
		return;
	}
	//if(! checkAuthCode()){
	//	return;
	//}
	var res=1;
	var checkCode = $("#mcode").val();
	$.ajax({
		url: "getMCode.htm?code="+checkCode,
		context: document.body,
		async:false,
		success: function(data){
			res=data;
	}});
	if(res == 1){
		$(".mcode_tip").hide();
		$(".mcode_tip").html("请输入短信验证码").css("color","#dd6330");
	}else if(res == -1){
		$(".mcode_tip").show();
		$(".mcode_tip").html("短信验证码已经失效").css("color","#dd6330");
		return;
	}else{
		$(".mcode_tip").show();
		$(".mcode_tip").html("短信验证码错误").css("color","#dd6330");
		return;
	}
	location.href="reirectpem.html?type="+type+"&ut=mobile&code="+$("#code").val()+"&mcode="+checkCode+"&status=mobile";
}

function checkPwd(){
	var checkPayPwd = $("#pwd").val();
	if(checkPayPwd.length==0){
		$(".pwd_tip").show();
		$(".pwd_tip").html("请输入登录密码").css("color","#dd6330");
		return false;
	}
	$(".pwd_tip").hide();
	$(".pwd_tip").html("登录密码错误").css("color","#dd6330");
	return true;
}

function checkPatcha(){
    var checkCode = $("#code").val();
    if(checkCode.trim().length==0){
        $(".code_tip").show();
        $(".code_tip").html("请输入验证码").css("color","#dd6330");
        return false;
    }else{
        var result = false;
        $.ajax({
            url: "../patchcaSession.htm",
            async:false,
            success: function(data){
                if(checkCode.trim() != data){
                    $(".code_tip").show().css("color","#dd6330");
                    $(".code_tip").html("验证码错误");
                    result = false;
                }else{
                    result = true;
                }
            }
        });
        return result;
    }
}
//var emp = null;
//function getPatcha(){
//	$.ajax({
//		url: "../patchcaSession.htm",
//		context: document.body,
//		success: function(data){
//			emp = data;
//		}});
//}
function checkCurrentCode(){
	var checkCode = $("#code").val();
	if(checkCode == ''||checkCode==null){
		$(".code_tip").show();
		$(".code_tip").html("请输入验证码").css("color","#dd6330");
		return false;
	}else{

            return true;

    }

}
function checkCurrentPwd(){
	var checkCode = $("#pwd").val();
	//var customerId = $("#hi_id").val();
	var flag=false;
	 $.ajax({
         type: 'post',
         url:'../checkcustomerpassword.htm',
         data:"password="+checkCode,
         async:false,
         success: function(data) {
             if (data==1) {//验证成功
            	 	$(".pwd_tip").hide();
            		$(".pwd_tip").html("请输入登录密码").css("color","#dd6330");
            		flag= true;
             } else {//验证失败
            	 $(".pwd_tip").show();
            	 $(".pwd_tip").html("登录密码错误").css("color","#dd6330");
            	 /*如果失败修更新验证码*/
         		 $("#checkCodeImg").click();

             }
         }
     });
	 return flag;
}

function checkMCode(){
	var checkCode = $("#mcode").val();
	if(checkCode != ""){
		$(".mcode_tip").hide();
		$(".mcode_tip").html("请输入短信验证码").css("color","#dd6330");
		return true;
	}else{
		$(".mcode_tip").show();
		$(".mcode_tip").html("短信验证码错误").css("color","#dd6330");
		return false;
	}
}
function checkCurrentMCode(){
	var checkCode = $("#mcode").val();
	$.ajax({
		url: "../getMCode.htm?code="+checkCode,
		context: document.body, 
		async:false,
		success: function(data){
			if(data == 1){
				$(".mcode_tip").hide();
				$(".mcode_tip").html("请输入短信验证码").css("color","#dd6330");
				return true;
			}else if(data == -1){
				$(".mcode_tip").show();
				$(".mcode_tip").html("短信验证码已经失效").css("color","#dd6330");
				return false;
			}else{
				$(".mcode_tip").show();
				$(".mcode_tip").html("短信验证码错误").css("color","#dd6330");
				return false;
			}
	}});
	
}

function updatepem(type){
	$("#upMobile").removeAttr("onsubmit");
    var flag=true;
	if(type=="pwd"){
		if(! checkNewPwd()){
            flag=false;
			return;
		}
		if(! checkRePwd()){
            flag=false;
			return;
		}
        if(! checkCurrentCode()){
            flag=false;
            return false;
        }
        if(flag){
            $("#upMobile").submit();
        }
	}else if(type=="mobile"){
		if(! checkMobile()){
            flag=false;
			return;
		}
		if(! checkMCode()){
            flag=false;
			return;
		}
        if(! checkCurrentCode()){
            flag=false;
            return false;
        }
		var res=0;
		var checkCode = $("#mcode").val();
		$.ajax({
			url: "getMCode.htm?code="+checkCode, 
			context: document.body, 
			async:false,
			success: function(data){
				res=data;
		}});
		if(res == 1){
			$(".mcode_tip").hide();
			$(".mcode_tip").html("请输入短信验证码").css("color","#dd6330");
		}else if(res == -1){
			$(".mcode_tip").show();
			$(".mcode_tip").html("短信验证码已经失效").css("color","#dd6330");
            flag=false;
			return;
		}
        else{
			$(".mcode_tip").show();
			$(".mcode_tip").html("短信验证码错误").css("color","#dd6330");
            flag=false;
			return;
		}
        if(flag){
            $("#upMobile").submit();
        }
	}else if(type=="email"){
		if(! checkEmail()){
            flag=false;
			return;
		}

	}

	if(type=="email"){
		var datas="email="+$("#email").val()+"&csrFToken="+$("#CSRFToken").val();
		datas+="&type="+type+"&code="+$("#code").val();
		$.ajax({
	        type: 'post',
	        url:'sendeamil.htm',
	        data:datas,
	        async:false,
	        success: function(obj) {
	        	if(obj==1) {
                    $("#sendValidEmail").attr("disabled","disabled");
                    $('#upMobile').submit();
				}else if(obj==2){
					$("#errorContent").html("验证码不能为空！");
					flag=false;
					dia(3);
				}else if(obj==-1){
					flag=false;
					dia(4);
				}else if(obj==3){
					flag=false;
					dia(2);
				}else{
					$("#errorContent").html("验证码不正确！");
                    flag=false;
					dia(3);
				}
	        },
	        error : function() {
                flag=false;
				dia(2);
	    	}
	    });
		return;
	}
}

function checkMobile(obj){
	var mobile = $("#mobile").val();
	if (!mobile) {
		$("#m_tip").hide();
		$(".mobile_tip").show();
		$(".mobile_tip").html("请输入手机号").css("color","#dd633" +
        "0");
        $("#sendCode").attr("disabled","disabled");
        return false ;
	}
	var re = /^1{1}[3,4,5,8,7]{1}\d{9}$/; // 判断是否为数字的正则表达式
        if (!re.test(mobile)) {
		$("#m_tip").hide();
		$(".mobile_tip").show();
		$(".mobile_tip").html("请输入正确的手机号码").css("color","#dd6330");
        $("#sendCode").attr("disabled","disabled");
		return false;
	}
	mFlag = false;
	if(obj != 1){
		var datass = "mobile=" + mobile;
		$.ajax({
	        type: 'post', 
	        url:'../checkmobileexist.htm',
	        data:datass,
	        async:false,
	        success: function(obj) {
	        	if(obj>0) {
	        		$("#m_tip").hide();
	        		$(".mobile_tip").show();
	        		$("#sendCode").attr("disabled","disabled");
	        		$(".mobile_tip").html("检测到此手机已被使用,请更换!");
	        		mFlag = true;
	        		return false;
				}
	        },
	        error : function() {
				dia(2);
				return false;
	    	}
	    });
	}
	if(mFlag){
		return false;
	}
	//$(".mobile_tip").hide();
	//$(".mobile_tip").html("&nbsp;");
	return true;
}
function getCode(){
    if(! checkCurrentCode())
    {
        return ;
    }
    $("#sendCode").attr("disabled","disabled");
	if(! checkMobile(1)){
		return;
	}
	var mFlag = false;
    var mobile = $("#mobile").val();
	var datas = "moblie=" + mobile;
    var checkCode = $("#code").val();
	$.ajax({
        type: 'get',
        url:'../sendcode.htm?code='+checkCode,
        data:datas,
        async:false,
        success: function(data) {
        	if(data==1) {
				$(".timeleft").text(59);
				$("#m_tip").show();
				$(".mobile_tip").hide();
				setTimeout(countDown, 1000);
				$("#mcode").removeAttr("disabled");
				$(".sub_btn").removeAttr("disabled");
				//$("#mobile").attr("readonly","readonly");
				$("#sendCode").attr("disabled","disabled");
                $(".code_tip").hide();
			}
            else if (data ==2)
            {
                $(".code_tip").show();
                $(".code_tip").html("验证码错误").css("color","#dd6330");
                $("#sendCode").removeAttr("disabled");
            }
            else if(data==0){
				//网络异常
				dia(2);
				$("#sendMobileCode").removeAttr("disabled");
			}else if(data==-1){
				$("#m_tip").show();
				$(".mobile_tip").hide();
				$(".timeleft").text(59);
				if($(".timeleft").text()==59){
					$(".resend").text("");
					$("#m_tip").find(".resend").remove();
					$("#m_tip").find("br").remove();
					$(".resend").html("60秒内只能获取一次验证码,请稍后重试");
				}else{
					$(".resend").text("");
					$("#m_tip").find(".resend").remove();
					$("#m_tip").find("br").remove();
					$(".resend").html("60秒内只能获取一次验证码,请稍后重试");
				}
				//$("#mcode").removeAttr("disabled");
			}
        },
        error : function() {
        	//网络异常
			$("#submitCode").removeAttr("disabled");
			dia(2);
    	}
    });
	
	function countDown(){
		var time = $(".timeleft").text();
		$(".timeleft").text(time - 1);
		if (time == 1) {
			$("#m_tip").hide();
			$("#sendCode").removeAttr("disabled");
			$("#mobile").removeAttr("disabled");
            $("#checkCodeImg").click();
		} else {
			setTimeout(countDown, 1000);
		}
	}
}


/**
 * 修改手机发送验证，手机号从session中拿，不要从页面传啦。不要公用那个有手机号的方法
 */
function sendCode(){

    if(! checkCurrentCode())
    {
        return ;
    }

    var checkCode = $("#code").val();
	var mFlag = false;
	$("#sendCode").attr("disabled","disabled");
	$.ajax({
        type: 'get',
        url:'../sendcodetovalidate.htm?code='+checkCode,
        async:true,
        success: function(data) {
        	if(data==1) {
				$(".timeleft").text(59);
				$("#m_tip").show();
                $(".resend").text("");
				$(".mobile_tip").hide();
				setTimeout(countDown, 1000);
				$("#mcode").removeAttr("disabled");
				$(".sub_btn").removeAttr("disabled");
				$("#sendCode").attr("disabled","disabled");
                $(".code_tip").hide();
			}

            else if (data ==2)
            {
                $(".code_tip").show();
                $(".code_tip").html("验证码错误").css("color","#dd6330");
                $("#sendCode").removeAttr("disabled");
            }
            else if(data==0){
				//网络异常
				dia(2);
				$("#sendMobileCode").removeAttr("disabled");
			}else if(data==-1){
				$("#m_tip").show();
				$(".mobile_tip").hide();
				/*$(".timeleft").text(59);
				if($(".timeleft").text()==59){
					$(".resend").text("");
                    //$("#m_tip").hide();
					$(".resend").html("60秒内只能获取一次验证码,请稍后重试").show();
				}else{*/
					$(".resend").text("");
                    //$("#m_tip").hide();
                    $(".resend").html("60秒内只能获取一次验证码,请稍后重试").show();
				/*}*/
				//$("#mcode").removeAttr("disabled");
			}
        },
        error : function() {
        	//网络异常
			$("#submitCode").removeAttr("disabled");
			dia(2);
    	}
    });
	function countDown(){
		var time = $(".timeleft").text();
		$(".timeleft").text(time - 1);
		if (time == 1) {
			$("#m_tip").hide();
			$("#sendCode").removeAttr("disabled");
			$("#mobile").removeAttr("disabled");
            $("#checkCodeImg").click();
		} else {
           setTimeout(countDown, 1000);
		}
	}
}

//判断密码是否是数字加字母组合
function checkPass(obj) {
    var is = 0;
    if(obj.match(/([0-9])+/)) {
        is++;
    }
    if(obj.match(/([a-z])+/)) {
        is++;
    }
    if(obj.match(/([A-Z])+/)) {
        is++;
    }
    if(obj.match(/[^a-zA-Z0-9]+/)) {
        is++;
    }
    return is;
}

function checkNewPwd(){
	var x=$("#npwd").val();
    var regS=/\s/g;
    if(regS.test(x)){
        $("#npwd").parent().find(".vd_tip").show().html("请勿输入空格!");
        $("#npwd").val('')
        return false;
    }
	else if(x.length < 6 || x.length > 20){
		$("#npwd").parent().find(".vd_tip").show().html("密码格式不正确,请输入6-20位长度的密码!");
		return false;
	}
    else if(!(/^[A-Za-z0-9\\w]{6,20}$/).test(x)) {
            $("#npwd").parent().find(".vd_tip").show().html("密码必须是数字和英文字母组合!");
            return false;
    }else{
        $("#npwd").parent().find(".vd_tip").show().html("");
        return true;
    }


}
function checkRePwd(){
	var x = $("#npwd").val();
	var y = $("#repwd").val();
    if(y ==""){
        $("#repwdtip").html("请再次输入登录密码").css("color","#dd6330");
        return false;
    }
	if(x != y){
        $("#repwdtip").html("两次输入的密码不一致").css("color","#dd6330");
		return false;
	}
	$("#repwdtip").hide().html("请输入登录密码").css("color","gray");
	return true;
}
//function checkAuthCode(){
//	var checkCode = $("#code").val();
//	if(checkCode.length==0){
//		$(".code_tip").show();
//		$(".code_tip").html("请输入验证码").css("color","#dd6330");;
//		return false;
//	}
//	$(".code_tip").hide();
//	$(".code_tip").html("验证码错误").css("color","gray");;
//	return true;
//}
function checkEmail(){
	var email = $("#email").val();
	if(!email){
		$(".email_tip").show();
		$(".email_tip").html("请输入您的常用邮箱");
		return false;
	}
	var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	if (email.replace(/[^\x00-\xff]/g, "**").length <= 4 || !reg.test(email) || email.length > 50) {
		$(".email_tip").show();
		$(".email_tip").html("邮箱格式不正确，请重新输入,长度在4-50之间");
		return false;
	}
	if($("#e_hide").val()==email){
		$(".email_tip").show();
		$(".email_tip").html("检测到此邮箱为当前使用邮箱,请更换!");
		return false;
	}
	var datas="email="+email,eFlag=false;
	$.ajax({
        type: 'post', 
        url:'../checkemailexist.htm',
        data:datas,
        async:false,
        success: function(obj) {
        	if(obj>0) {
        		$(".email_tip").show();
        		$(".email_tip").html("检测到此邮箱已被使用,请更换!");
        		eFlag = true;
        		return false;
			}
        },
        error : function() {
			dia(2);
    	}
    });
	if(eFlag){
		return false;
	}
	$(".email_tip").hide();
	$(".email_tip").html("");
	return true;
}

function sendCheckIdEmail(type){	
	if(!checkCurrentCode()){
		return;
	}else{
	$.ajax({
        type: 'post',
        url:'../validate/sendcheckidemail.htm',
        data:{"type":type,"code":$("#code").val()},
        async:false,
        success: function(obj) {
        	if(obj==1) {
                $("#sendCode").attr("disabled","disabled");
        		location.href="bindsucess.html?type="+type+"&code="+$("#code").val();
			}else{
                if(obj==-1){
                    $(".code_tip").show();
                    $(".code_tip").html("验证码输入错误").css("color","#dd6330");
                    $("#checkCodeImg").click();
                    return ;
                }
				dia(2);
			}
        },
        error : function() {
			dia(2);
    	}
    });
	}
}