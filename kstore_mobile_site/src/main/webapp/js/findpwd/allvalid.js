

$(function(){
	
    $(".v_next").click(function() {
    	var flag= validateChkFun.formSubmit(['#mob','#mc']);
    	if(flag){
    			var checkCode = $("#mc").val();
    			$.ajax({
    				type: 'post',
    				url: "validate/getMCode.htm?code="+checkCode, 
    				context: document.body, 
    				async:false,
    				success: function(data){
    					if(data == 1){
    						$(".v_next").parent().attr('method',"post");
    						$(".v_next").parent().attr('action',"m/govalididentity.htm");
    						$(".v_next").parent().submit();
    					}else{
    						$("#mc").parent().addClass("has-error");
    						$("#mc").next().html("手机验证码错误或者已经失效");
    					}
    			}});
    	}
    });
    $(".p_next").click(function() {
    	var flag= validateChkFun.formSubmit(['#pd']);
    	if(flag){
    		if($("#pdt").val()==$("#pd").val()){
    			$(".p_next").parent().attr('method',"post");
				$(".p_next").parent().attr('action',"m/goresetpassword.htm");
				$(".p_next").parent().submit();
    		}else{
    			$("#pdt").parent().addClass("has-error");
    			$("#pdt").next().html("你两次输入的密码不相同");
    		}
    	}
    });
    $("#mc_btn").click(function() {
    	$("#mob").blur();
    });
    validateChkFun.init(function() {
        validateChkFun.defaultChk({
            elem: '#as',
            isNull: '请输入地址别名',
            onCorrect: '',
            onlyNotNullChk: true,
            regExp: validateRegExp.addressas,
            onError: {
                badFormat: "地址别名有误"
            }
        });
        validateChkFun.defaultChk({
        	elem: '#ae',
        	isNull: '请输入姓名',
        	onCorrect: '',
        	onlyNotNullChk: true,
        	regExp: validateRegExp.realname,
        	onError: {
        		badFormat: "姓名有误"
        	}
        });
        validateChkFun.defaultChk({
        	elem: '#mo',
        	isNull: '请输入手机或者固话',
        	onCorrect: '',
        	onlyNotNullChk: true,
        	regExp: validateRegExp.tel,
        	onError: {
        		badFormat: "手机或者固话有误"
        	}
        });
        validateChkFun.defaultChk({
        	elem: '#ad',
        	isNull: '请输入详细地址',
        	onCorrect: '',
        	onlyNotNullChk: true,
        	regExp: validateRegExp.companyaddr,
        	onError: {
        		badFormat: "详细地址有误"
        	}
        });
        validateChkFun.defaultChk({
        	elem: '#az',
        	isNull: '请输入邮编',
        	onCorrect: '',
        	onlyNotNullChk: true,
        	regExp: validateRegExp.zipcode,
        	onError: {
        		badFormat: "邮编有误"
        	}
        });
        validateChkFun.defaultChk({
        	elem: '#mob',
        	isNull: '请输入手机号码',
        	onCorrect: '',
        	onlyNotNullChk: true,
        	regExp: validateRegExp.mobile,
        	onError: {
        		badFormat: "请输入正确的手机号码！"
        	}
        });
        validateChkFun.defaultChk({
        	elem: '#mc',
        	isNull: '请输入短信验证码',
        	onCorrect: '',
        	onlyNotNullChk: true,
        	regExp: validateRegExp.intege1,
        	onError: {
        		badFormat: "短信验证有误"
        	}
        });
        validateChkFun.defaultChk({
        	elem: '#pd',
        	isNull: '请输入密码',
        	onCorrect: '',
        	onlyNotNullChk: true,
        	regExp: validateRegExp.password,
        	onError: {
        		badFormat: "密码格式错误"
        	}
        });
    });
});
/**
 * 获取验证码
 */
function getCode(){
	if($("#mobile").val()==null || $("#mobile").val()==''){
        $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入绑定手机</h3></div></div>');
        setTimeout(function(){
            $('.tip-box').remove();
        },1000);
        return false;
    }
	if($("#checkCode").val()==null || $("#checkCode").val()==''){
		$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请输入图片验证</h3></div></div>');
		setTimeout(function(){
			$('.tip-box').remove();
		},1000);
		return false;
	}
    $.ajax({
        url:"sendcodepwd.htm",
        type:"post",
        data:{mobile:$("#mobile").val(),checkCode:$("#checkCode").val()},
        success:function(data){
            if(data == 1){
                //发送成功
                //alert("发送成功");
            	$("#mobile").attr("readonly","readonly");
                $("#huode").html('60秒后重新获取');
                $("#huode").attr('data-t','60');
                $("#huode").removeAttr("onClick");
                $("#huode").css("color","gray");
                setTimeout(countDown, 1000);
            }else if(data == 2){
            	 $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>手机号未绑定</h3></div></div>');
                 setTimeout(function(){
                     $('.tip-box').remove();
                 },1000);
            }else if(data==3){
				$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>图片验证码错误</h3></div></div>');
				setTimeout(function(){
					$('.tip-box').remove();
				},1000);
			}else{
                //发送失败
                //alert("发送失败");
                 $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>发送失败</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
            }
        }
    });
}
function countDown(){
    var time = $("#huode").attr('data-t');
    $("#huode").html((time - 1)+"秒后重新获取");
    $("#huode").attr('data-t',(time - 1));
    time = time - 1;
    if (time == 1) {
        $("#huode").html("获取验证码");
        $("#huode").attr("onClick","getCode();");
        $("#huode").css("color","red");
    } else {
        setTimeout(countDown, 1000);
    }
}
/**
 * 提交
 */
$(".btn-full").click(function(){
	var mobile = $("#mobile").val();
	var code = $("#code").val();
	var newpassword = $("#newpassword").val();
	if (checkInput()) {
		$.ajax({
			type: 'post',
			url: "forgetpassword.htm?mobile="+mobile+"&code="+code+"&newpassword="+newpassword, 
			success: function(data){
				if(data == 2){
					alertStr("手机号未注册");
				}else if(data == 1){
					alertStr("手机验证码错误或者已经失效");
				}else if (isNaN(data)) {
					window.location.href = $("#basepath").val()+data;
				}
		}});
	}
	
});
function checkInput(){
	var mobile = $("#mobile").val();
	var code = $("#code").val();
	var newpassword = $("#newpassword").val();
	var repassword = $("#repassword").val();
	if (mobile == null || mobile == '') {
		alertStr("请输入绑定的手机号");
		return false;
	}else if(!(/^0?1[3|4|5|8|7][0-9]\d{8}$/).test(mobile)) {
		alertStr("手机号格式错误");
		return false;
	}
	if (code == null || code == '') {
		alertStr("请输入验证码");
		return false;
	}
	if (newpassword == null || newpassword == '') {
		alertStr("请输入新密码");
		return false;
	} else if(checkPass(newpassword)<2){
		alertStr("新密码格式错误");
		return false;
	}
	if (repassword == null || repassword == '') {
		alertStr("请输入确认密码");
		return false;
	}
	if (repassword != newpassword) {
		alertStr("两次输入密码不一致");
		return false;
	}
	return true;
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
function alertStr(str){
	$('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+str+'</h3></div></div>');
    setTimeout(function(){
        $('.tip-box').remove();
    },1000);
}

$("#mobile").focus(function(){
	$(this).attr("placeholder","");
});
$("#mobile").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","请输入绑定手机号");
	}
});
$("#code").focus(function(){
	$(this).attr("placeholder","");
});
$("#code").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","输入验证码");
	}
});
$("#newpassword").focus(function(){
	$(this).attr("placeholder","");
});
$("#newpassword").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","6-20位数字、字母或-_");
	}
});
$("#repassword").focus(function(){
	$(this).attr("placeholder","");
});
$("#repassword").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","6-20位数字、字母或-_");
	}
});