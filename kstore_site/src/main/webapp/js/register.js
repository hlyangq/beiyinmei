var valid_flag = false;
var basePath = $("#basePath").val();
$(function(){
	$("#act_user").focus(function(){
		$(this).next().next().next().hide();
		$(this).removeClass('n_error');
        $(this).next().next().html("请填写6-20位字母、数字或下划线组成的用户名");
		$(this).next().next().show();
	});
	
	$("input[type='password']").focus(function(){
		$(this).next().next().next().hide();
		$(this).removeClass('n_error');
		$(this).next().next().show();
	});
	
	$("#act_user").blur(function(){
		$(this).next().next().hide();
		if(isExist($(this).attr('class'),'user_chk')){
			valid_flag = user_chk($(this));
			if(valid_flag){
				if($.trim($(this).val()).length>=4){
					checkExist();
				}
			}
		}
	});
	$("input[type='password']").blur(function(){
		$(this).next().next().hide();
		if(isExist($(this).attr('class'),'psd_chk')){
			valid_flag = psd_chk($(this));
		}
		if(isExist($(this).attr('class'),'psd_conf')){
			valid_flag = psd_conf($(this));
		}
	});
	
	$("#checkCode").focus(function(){
        $(".code_info").parents(".reg_form_item").removeClass("error");
        $(".code_info").hide();
	});
    $("#mobileInput").focus(function(){
        $(".mobile_info").parents(".reg_form_item").removeClass("error");
        $(".mobile_info").hide();
	});
    $("#message").focus(function(){
        $(".code_info").parents(".reg_form_item").removeClass("error");
        $(".code_info").hide();
    });
    $("#checkCode").focus(function(){
        $(".code_info1").parents(".reg_form_item").removeClass("error");
        $(".code_info1").hide();
    });
    $(".passwordconf").focus(function(){
        $(".pass").parents(".reg_form_item").removeClass("error");
        $(".pass").hide();
    });
    $(".repasswordconf").focus(function(){
        $(".repass").parents(".reg_form_item").removeClass("error");
        $(".repass").hide();
    });

    $("#varification").focus(function(){
		$(".code_error").hide();
		$(this).removeClass('n_error');
		$(".code_tips").show();
	});
	
	$("#varification").blur(function(){
		$(".code_tips").hide();
	});

    $(".passwordconf").blur(function(){
        psdchk($(this));
    });

    $(".repasswordconf").blur(function(){
        psdconf($(this));
    });
    $("#mobileInput").blur(function(){
        var mobile = $("#mobileInput").val();
        if(mobile == '' || mobile == null){
            $(".mobile_info").show();
            $(".mobile_info").html("请输入您的手机号码");
            $(".mobile_info").parents(".reg_form_item").addClass("error");
        }else if(!(/^0?1[3|4|5|8|7][0-9]\d{8}$/).test(mobile)){
            $(".mobile_info").show();
            $(".mobile_info").html("请输入正确的手机号码");
            $(".mobile_info").parents(".reg_form_item").addClass("error");
        }else {
            $.ajax({
                url: "checkMobileExist.htm?mobile="+$(this).val(),
                context: document.body,
                success: function(data){
                    if(data >= 1){
                        $(".mobile_info").show();
                        $(".mobile_info").html("该手机号已被注册");
                        $(".mobile_info").parents(".reg_form_item").addClass("error");
                    }else{
                        $(".mobile_info").hide();
                        $(".mobile_info").html("");
                        $(".mobile_info").parents(".reg_form_item").removeClass("error");
                    }
                },
                error : function() {
                }
            });
        }

    });

	
});

$(function(){
	//验证码绑定onclick事件
	$("#checkCodeA").click(
		function(){
			$("#checkCodeImg").click();
		}
	);
});

// 提交第一步的验证数据
function submitFirst() {
    var id = rsgister();
    var mobile = $("#mobileInput").val();
    var checkCode = $("#checkCode").val();
    if(mobile == '' || mobile == null){
        $(".mobile_info").show();
        $(".mobile_info").html("请输入您的手机号码");
        $(".mobile_info").parents(".reg_form_item").addClass("error");
    }else if(!(/^0?1[3|4|5|8|7][0-9]\d{8}$/).test(mobile)){
        $(".mobile_info").show();
        $(".mobile_info").html("请输入正确的手机号码");
        $(".mobile_info").parents(".reg_form_item").addClass("error");
    }else if(checkCode == null || $.trim(checkCode) == '') {
        $(".code_info").html("请输入验证码");
        $(".code_info").parents(".reg_form_item").addClass("error");
        $(".code_info").show();
    }else if(!$("#readme").prop("checked")){
        $(".pro_info").html("请接受服务条款");
        $(".pro_info").parents(".reg_form_item").addClass("error");
        $(".pro_info").show();
    } else {
        if (isNaN(id)) {
            id = null;
        }
        $("#custId").val(id);
        $("#registerFirstForm").submit();
    }
}

// 提交第二步的验证数据
function submitSecond() {
    var code = $("#message").val();
    var checkCode = $("#checkCode").val();
    if (checkCode == null || $.trim(checkCode) == '') {
        $(".code_info1").parents(".reg_form_item").addClass("error");
        $(".code_info1").show();
        return false;
    }
    if (code == null || $.trim(code) == '') {
        $(".code_send").hide();
        $(".code_send").hide();
        $(".code_info").parents(".reg_form_item").addClass("error");
        $(".code_info").show();
        return false;
    }

    $("#registSecondForm").submit();
}

function sendMessage(){
    $("#sendbtn").attr("disabled","disabled");
    var message = $("#message").val();
    var mobile = $("#mobileNum").val();
    var checkCode = $("#checkCode").val();
    $.ajax({
        url: "regsendcode.htm?mobile="+mobile+"&checkCode="+checkCode,
        context: document.body,
        success: function(data){
            if(data == 1){
                //发送成功
                $(".timeleft").text(59);
                $(".code_info").parents(".reg_form_item").removeClass("error");
                $(".code_info").hide();
                $(".code_send").show();
                setTimeout(countDown, 1000);
                $("#sendbtn").attr("disabled","disabled");
            }else if(data == 2){
                $(".code_info").html("该手机号已被注册");
                $(".code_info").parents(".reg_form_item").addClass("error");
                $(".code_info").show();
            }else if(data == -1){
                $(".code_info1").html("请输入正确的验证码");
                $(".code_info1").parents(".reg_form_item").addClass("error");
                $(".code_info1").show();
                $("#sendbtn").removeAttr("disabled");

            }else{
                $(".code_info").html("发送失败");
                $(".code_info").parents(".reg_form_item").addClass("error");
                $(".code_info").show();
            }
        },
        error : function() {
            //网络异常
            $("#sendbtn").removeAttr("disabled");
            //dia(2);
        }
    });
    function countDown(){
        var time = $(".timeleft").text();
        $(".timeleft").text(time - 1);
        if (time == 1) {
            $(".code_send").hide();
            $("#sendbtn").removeAttr("disabled");
        } else {
            setTimeout(countDown, 1000);
        }
    }
}

function register(){
    var valid_flag=true;
    var id = rsgister();
    valid_flag = psdchk($(".passwordconf")) && valid_flag;
    valid_flag = psdconf($(".repasswordconf")) && valid_flag;
    if(valid_flag){
        if(isNaN(id)){
            $("#regform").submit();
        }else{
            $('#cusId').val(id);
            $.ajax({
                type:'post',
                url:'checkRegsiter.htm',
                success:function(data){
                    if(getCookie("ip")==null){
                        setCookie("ip",data);
                            $("#regform").submit();
                    }else{
                        if(getCookie("ip")==data){
                            //dia(2);
                        }else{
                            $("#regform").submit();
                        }
                    }
                }
            });
        }
    }else{
        return false;
    }
}

//注册
var num=0;
function reg(){
	var flag = "0";
	var id = rsgister();
	valid_flag = user_chk($("#act_user"));
	valid_flag = psd_chk($(".psd_chk")) && valid_flag;
	valid_flag = psd_conf($(".psd_conf")) && valid_flag;
	valid_flag = checkReadMe() && valid_flag;
	//$(".code_error").css({"display":"none"});
	var enterValue = $(".code_text").val();
	if(valid_flag){
		//$("#userform").submit();

		if(isNaN(id)){
        $.ajax({
            url: "checkpatchca.htm?enterValue="+enterValue,
            context: document.body,
            success: function(data){
                if(enterValue != ''){
                    if(data==0){
                        $("#varification").addClass("n_error");
                        $(".code_error").html('您输入的验证码不正确');
                        $(".code_error").show();
                        $(".code_tips").hide();
                        //如果失败修更新验证码
                        $("#checkCodeImg").click();
                    }else{
                        //验证用户名是否存在
                        if(checkExista()){
                            $(".hi_name").val($("#act_user").val());
                            $(".hi_pwd").val($(".psd_chk").val());
                            if(valid_flag&&num==0){
                                num+=1;
                                $("#userform").submit();
                            }
                        }else{
                            //如果失败修更新验证码
                            $("#checkCodeImg").click();
                        }
                    }
                }else{
                    $(".code_error").html('请输入验证码');
                    $(".code_error").show();
                }
            }});
	}else{
		$('#cusId').val(id);
		if(enterValue == emp){
			if(checkExista()){
			$.ajax({
				type:'post',
				url:'checkRegsiter.htm',
				success:function(data){
					if(getCookie("ip")==null){
						setCookie("ip",data);
						$(".hi_name").val($("#act_user").val());
						$(".hi_pwd").val($(".psd_chk").val());
						if(flag=='0'){
							flag='1';
							$("#userform").submit();
						}
						
					}else{
						if(getCookie("ip")==data){
							dia(2);
						}else{
							$(".hi_name").val($("#act_user").val());
							$(".hi_pwd").val($(".psd_chk").val());
							if(flag=='0'){
								flag='1';
								$("#userform").submit();
							}
						}
						
					}
				}
			});
		}	
     }
 }
}
	
}


//判断注册是否是会员推送的链接
function rsgister(){
	//对获取的会员ID进行解密处理
	var b = new Base64(); 
	//当前注册页面url
	var registerUrl = window.location.href; 
	//截取会员ID
	var index =  registerUrl.substring(registerUrl.indexOf("?")+1,registerUrl.length);
	//对会员ID解密
	index = b.decode(index);  
	//获取等号的索引
	var denghao  = index.indexOf("=")+1;
	//截取会员ID
	index = index.substring(denghao,index.length-1);
	return index;
}


//判断用户名是否合法
//1、判断是不是邮箱注册
//2、判断用户名是否符合正则表达式
//3、判断用户名长度是否够
function user_chk(obj){
	var x = obj.val();
	var eReg =  /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	var mReg = /^0?(13|15|17|18|14)[0-9]{9}$/;
	if(x.indexOf("@") != -1){
		if(!eReg.test(x)){
			obj.addClass('n_error');
			obj.next().next().hide();
			if(obj.find('.n_tips').length == 0){
				obj.next().next().next().show().html('邮件格式不合法');
				return false;
			}
		}else{
			obj.removeClass('n_error');
			obj.next().next().hide();
			obj.next().next().next().hide();
			return true;
		}
	}else if(mReg.test(x)){
		obj.removeClass('n_error');
		obj.next().next().hide();
		obj.next().next().next().hide();
		return true;
	}else if(!isUser(x)){
		obj.addClass('n_error');
		obj.next().next().hide();
		if(obj.find('.n_tip').length == 0){
			obj.next().next().next().show().html('请输入6-20位字母、数字和下划线组成的用户名');
			return false;
		}
	} else{
		obj.removeClass('n_error');
		obj.next().next().hide();
		obj.next().next().next().hide();
		return true;
	}
}


function psdchk(obj){
	var x = obj.val();
    var regS=/\s/g;
    if(regS.test(x)){
        obj.addClass('n_error');
        obj.next().next().hide();
        obj.next().next().next().show().html('请勿输入空格');
        obj.val('');
        return false;
    }
    else if(x.length < 6 || x.length > 20){
		obj.addClass('n_error');
		obj.next().next().hide();
		if(obj.find('.n_tips').length == 0){
			obj.next().next().next().show().html('密码必须是6-20位');
			return false;
		}
	}
	else if(checkPass(x) ==0) {
        obj.addClass('n_error');
        obj.next().next().hide();
        if(obj.find('.n_tips').length == 0){
            obj.next().next().next().show().html('密码必须是数字和英文字母组合');
            return false;
        }
    }
    else{
		obj.removeClass('n_error');
		obj.next().next().hide();
		obj.next().next().next().hide();
		return true;
	}
}

function psdconf(obj){
	var x = obj.val();
    var regS=/\s/g;
    if(regS.test(x)){
        obj.addClass('n_error');
        obj.next().next().hide();
        obj.next().next().next().show().html('请勿输入空格');
        obj.val('');
        return false;
    }
    else if(x.length==0){
		obj.next().next().hide();
		obj.addClass('n_error');
		obj.next().next().next().show().html('密码必须是6-20位');
		return false;
	}
	if(x != $('.psd_chk').val()){
		obj.addClass('n_error');
		obj.next().next().hide();
		if(obj.find('.n_tips').length == 0){
			obj.next().next().next().show().html('两次填写的密码必须一致');
			return false;
		}
	}else{
		obj.removeClass('n_error');
		obj.next().next().hide();
		obj.next().next().next().hide();
		return true;
	}
}

function checkReadMe() {
    if ($("#readme").prop("checked")) {
        $("#protocol_error").hide();
        return true;
    } else {
        $("#protocol_error").show();
        return false;
    }
}

var emp = null;
function getPatcha(){
	$.ajax({
		url: "patchcaSession.htm", 
		context: document.body, 
		success: function(data){
			emp = data;
		}});
}

function isUser(x){
	var reg = /^\w+$/;
	if(reg.test(x) && (x.length >=6) && (x.length <= 20)){
		return true;
	}
	else{
		return false;
	}
}

function isExist(str,s){
	if(str.indexOf(s) > 0){
		return true;
	}
	else{
		return false;
	}
}


function requird(obj){
	var x = obj.val();
	if(x == ''){
		obj.addClass('n_error');
		obj.next().next().hide();
		if(obj.find('.n_tips').length == 0){
			obj.next().next().next().show().html('输入框不能为空');
			return false;
		}
	}
	else{
		obj.removeClass('n_error');
		obj.next().next().show();
		obj.next().next().next().hide();
		return true;
	}
}

/**
 * 唯一验证
 * tipStr	提示内容  checkExist("customerUsername",$("#customerUsername").val(),"checkExistCustomerUsername.htm","用户名");
 */
function checkExist(){
	$("#act_user").next().next().html("验证中，请稍等...");
	$("#act_user").next().next().show();
	$("#act_user").removeClass("n_error");
	$("#act_user").next().next().next().hide();
	$("#act_user").next().next().next().html("请填写6-20位字母、数字或下划线组成的用户名");
		var url="checkExistCustomerUsername.htm",
		datas="1=1";
		datas+="&customerUsername="+$("#act_user").val();
		$.ajax({
	         type: 'post',
	         url:url,
	         data:datas,
	         timeout: 3000,
	         dataType:'json',
	         success: function(data) {
	        	 if (data > 0){
	        		$("#act_user").next().next().html("请填写6-20位字母、数字或下划线组成的用户名");
	        		$("#act_user").next().next().hide();
	        		$("#act_user").next().next().next().html("用户名已存在,请更换你的用户名");
	        		$("#act_user").next().next().next().show();
	        	 }else{
	        		$("#act_user").next().next().html("用户名可用");
		        	$("#act_user").next().next().show();
		        	$("#act_user").next().next().removeClass("n_error");
		        	$("#act_user").next().next().next().hide();
	        	 }
	         },
	         error: function(){
	        	 $("#act_user").next().next().hide();
	 	    	$("#act_user").next().next().next().html("网络异常，请稍后再试！").show();
	     	 }
		 });
}


function checkPass(obj) {
    if (null == obj || obj.length <6) {
        return 0;
    }

    var reg=/^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i;

    if (reg.test(obj))
    {
        return 1;
    }

    return 0;
}

/**
 * propName 属性名
 * value 	属性值
 * url 		查询路径
 * tipStr	提示内容  checkExist("customerUsername",$("#customerUsername").val(),"checkExistCustomerUsername.htm","用户名");
 */
function checkExista(){
	$("#act_user").next().next().html("验证中，请稍等...");
	$("#act_user").next().next().show();
	$("#act_user").next().removeClass("n_error");
	$("#act_user").next().next().next().hide();
	$("#act_user").next().next().next().html("请填写6-20位字母、数字或下划线组成的用户名");
		var url="checkExistCustomerUsername.htm",
		datas="1=1",flag=false;
		datas+="&customerUsername="+$("#act_user").val();
		$.ajax({
	         type: 'post',
	         url:url,
	         data:datas,
	         timeout: 3000,
	         async:false,
	         dataType:'json', 
	         success: function(data) {
	        	 if (data > 0){
	        		$("#act_user").next().next().html("请填写6-20位字母、数字或下划线组成的用户名");
	        		$("#act_user").next().next().hide();
	        		$("#act_user").next().next().removeClass("n_error");
	        		$("#act_user").next().next().next().html("用户名已存在,请更换你的用户名");
	        		$("#act_user").next().next().next().show();
	        	 }else{
	        		$("#act_user").next().next().html("用户名可用");
		        	$("#act_user").next().next().show();
		        	$("#act_user").next().next().removeClass("n_error");
		        	$("#act_user").next().next().next().hide();
		        	flag= true;
	        	 }
	         },
	         error: function(){
	 	    	$("#act_user").next().next().html("网络异常，请稍后再试！");
	     	 }
		 });
		return flag;
}

function agreeonProtocol() {
    if ($("#readme").attr("checked") == true) {
        $("#protocol_error").hide();
        return true;
    }
}

//推广链接 会员ID解密
function Base64() {
	 
	// private property
	_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
 
	// public method for encoding
	this.encode = function (input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
		input = _utf8_encode(input);
		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output +
			_keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
			_keyStr.charAt(enc3) + _keyStr.charAt(enc4);
		}
		return output;
	}
 
	// public method for decoding
	this.decode = function (input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
		while (i < input.length) {
			enc1 = _keyStr.indexOf(input.charAt(i++));
			enc2 = _keyStr.indexOf(input.charAt(i++));
			enc3 = _keyStr.indexOf(input.charAt(i++));
			enc4 = _keyStr.indexOf(input.charAt(i++));
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
			output = output + String.fromCharCode(chr1);
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}
		output = _utf8_decode(output);
		return output;
	}
 
	// private method for UTF-8 encoding
	_utf8_encode = function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
 
		}
		return utftext;
	}
 
	// private method for UTF-8 decoding
	_utf8_decode = function (utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
		while ( i < utftext.length ) {
			c = utftext.charCodeAt(i);
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			} else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return string;
	}
}

function getCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]); 
    else 
        return null; 
} 


function setCookie(name,value) 
{ 
    var Days = 30; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
}

//返回正常注册页面
function registerUrl(){
	var url = window.location.href;
	var index = url.indexOf('?');
	var registerUrl = url.substring(0,index);
	window.location.href = registerUrl;
}

function psdchk(obj){
    var x = obj.val();
    var regS=/\s/g;
    if(regS.test(x)){
        obj.parents(".reg_form_item").addClass("error");
        $(".pass").show().html('请勿输入空格');
        return false;
    }else if(x.length < 6 || x.length > 20){
        obj.parents(".reg_form_item").addClass("error");
        $(".pass").show().html('密码必须是6-20位');
        return false;
    }else if(checkPass(x)==0) {
        obj.parents(".reg_form_item").addClass("error");
        $(".pass").show().html('密码必须是数字和英文字母组合');
        return false;
    }else{
        obj.parents(".reg_form_item").removeClass("error");
        $(".pass").hide();
        return true;
    }
}

function psdconf(obj){
    var x = obj.val();
    var regS=/\s/g;
    if(regS.test(x)){
        obj.parents(".reg_form_item").addClass("error");
        $(".repass").show().html('请勿输入空格');
        return false;
    }else if(x.length==0){
        obj.parents(".reg_form_item").addClass("error");
        $(".repass").show().html('密码必须是6-20位');
        return false;
    }else if(x != $('.passwordconf').val()){
        obj.parents(".reg_form_item").addClass("error");
        $(".repass").show().html('两次填写的密码必须一致');
        return false;
    }else{
        obj.parents(".reg_form_item").removeClass("error");
        $(".repass").hide();
        return true;
    }
}

function checkPass(obj) {
    if (null == obj || obj.length <6) {
        return 0;
    }
    var reg=/^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i;
    if (reg.test(obj))
    {
        return 1;
    }
    return 0;
}