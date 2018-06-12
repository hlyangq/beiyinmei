

$(function(){
	
    $(".sub_add").click(function() {
    	var flag= validateChkFun.formSubmit(['#as','#ae','#mo','#ad','#az']);
    	if(flag){
    		if($("#ad").val().trim().length < 75){
    			$(this).parents("form").submit();
    		}else{
    			$("#ad").parent().addClass("has-error");
    			$("#ad").next().html('地址超出长度限制,不要超过75个字符!');
    		}
    	}
    });
    $(".sub_up").click(function() {
    	var flag= validateChkFun.formSubmit(['#as','#ae','#mo','#ad','#az']);
    	if(flag){
    		if($("#ad").val().trim().length < 75){
    			$(this).parents("form").submit();
    		}else{
    			$("#ad").parent().addClass("has-error");
    			$("#ad").next().html('地址超出长度限制,不要超过75个字符!');
    		}
    	}
    });
    var num=0;
    $(".reg").click(function() {
    	var flag= validateChkFun.formSubmit(['#mob','#mc','#pd']);
    	if(flag&&num==0){
    		if($("#pdt").val()==$("#pd").val()){
    			$("#pdt").parent().removeClass("has-error");
    			$("#pdt").next().html("");
    			var checkCode = $("#mc").val();
    			$.ajax({
    				type: 'post',
    				url: "validate/getMCode.htm?code="+checkCode, 
    				context: document.body, 
    				async:false,
    				success: function(data){
    					if(data == 1){
                            num+=1;
    						$(".reg").parent().attr('action',"customer/addcustomer.htm");
    						$(".reg").parent().submit();
    					}else{
    						$("#mc").parent().addClass("has-error");
    						$("#mc").next().html("手机验证码错误或者已经失效");
    					}
    			}});
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

function subRegister() {
    var custId = rsgister();
	var mobile = $("#mobile").val();
	var code = $("#code").val();
	var password = $("#password").val();
	var repassword = $("#repassword").val();
    if (isNaN(custId)) {
        custId = null;
    }
	if(checkRegInput()){
		$(".btn-full").attr("onClick","");
		$.ajax({
			type: 'post',
			url: "register.htm?mobile="+mobile+"&code="+code+"&password="+password+"&repassword="+repassword+"&url="+$("#urlhide").val()+"&custId="+custId,
			success: function(data){
				if(data == 1){
					alertStr("手机号已存在");
					$(".btn-full").attr("onClick","subRegister();");
				}else if(data == 2){
					alertStr("手机验证码错误或者已经失效");
					$(".btn-full").attr("onClick","subRegister();");
				}else if (isNaN(data)) {
					window.location.href = $("#basePath").val()+data;
				}
		}});
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

function checkRegInput(){
	var mobile = $("#mobile").val();
	var code = $("#code").val();
	var password = $("#password").val();
	var repassword = $("#repassword").val();
	if (mobile == '' || mobile == null) {
        alertStr("请输入手机号");
        return false;
	} else if(!(/^0?1[3|4|5|8|7][0-9]\d{8}$/).test(mobile)){
        alertStr("手机号格式错误");
        return false;
	}else if(code == '' || code == null){
        alertStr("请输入验证码");
        return false;
	} else if(password == '' || password == null){
        alertStr("请输入密码");
        return false;
	} else if(password.length<6 || password.length > 20){
		alertStr("密码必须是6-20位");
		return false;
	} else if(!((/^(([A-Za-z]+[0-9]+)|([0-9]+[A-Za-z]+))[A-Za-z0-9]*$/).test(password))){
		alertStr("密码必须是数字和英文字母组合");
        return false;
	} else if(repassword == '' || repassword == null){
        alertStr("请输入确认密码");
        return false;
	} else if(password != repassword){
        alertStr("两次输入的密码不一致");
        return false;
	}
	return true;
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
		$(this).attr("placeholder","输入手机号");
	}
});
$("#code").focus(function(){
	$(this).attr("placeholder","");
});
$("#code").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","短信验证码");
	}
});
$("#password").focus(function(){
	$(this).attr("placeholder","");
});
$("#password").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","设置密码");
	}
});
$("#repassword").focus(function(){
	$(this).attr("placeholder","");
});
$("#repassword").blur(function(){
	if ($(this).val()==null || $(this).val()=='') {
		$(this).attr("placeholder","再次输入密码");
	}
});

//对会员ID进行加密
function Base64() {
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
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
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
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
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}