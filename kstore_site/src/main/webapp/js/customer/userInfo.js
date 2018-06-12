$(function() {
	$("#nickName_img").hide();
	$("#realName_img").hide();
	$("#idnum_img").hide();
	$("#address_img").hide();
	$("#infos_img").hide();
	$("#nickName").focus(function() {
		$("#nickName_img").hide();
		$("#nickName_msg").removeClass("tips_error");
		$("#nickName_msg").removeClass("tips_succ");
		$("#nickName_msg").html("4-20个字符，可由中英文、数字、“_”、“-”组成");
	});

	// 校验nickName是否正确
	$("#nickName").blur(function() {
		var nickName = $("#nickName").val();
		nickName = nickName.trim();
		$("#nickName").val(nickName);
		if (!validNickname(nickName)) {
			$("#nickName_img").prop("src", "../images/error-ico.png");
			$("#nickName_img").show();
			return;
		}
		checkNickName(nickName);
	});

	$("#realName").focus(function() {
		$("#realName_img").hide();
		$("#realName_msg").removeClass("tips_error");
		$("#realName_msg").removeClass("tips_succ");
		$("#realName_msg").html("请输入真实姓名");
	});

	$("#realName").blur(function() {
		var realName = $("#realName").val();
		realName = realName.trim();
		$("#realName").val(realName);
		if (!validRealname(realName)) {
			return;
		}
	});

	$("#email").focus(function() {
		$("#email_msg").html("请输入电子邮箱");
	});
	$("#email").blur(function() {
		var email = $("#email").val();
		email = email.trim();
		$("#email").val(email);
		if (!validEmail(email)) {
			return;
		}
	});
	$("#phone").focus(function() {
		$("#phone_msg").html("请输入手机号码");
	});
	$("#phone").blur(function() {
		var phone = $("#phone").val();
		phone = phone.trim();
		$("#phone").val(phone);
		if (!validPhone(phone)) {
			return;
		}
	});
	$("#idnum").focus(function() {
		$("#idnum_img").hide();
		$("#idnum_msg").removeClass("tips_error");
		$("#idnum_msg").removeClass("tips_succ");
		$("#idnum_msg").html("请输入身份证号码");
	});
	$("#idnum").blur(function() {
		var email = $("#idnum").val();
		email = email.trim();
		$("#idnum").val(email);
		if (!validIdnum(email)) {
			return;
		}
	});
	$("#address").focus(function() {
		$("#address_img").hide();
		$("#address_msg").removeClass("tips_error");
		$("#address_msg").removeClass("tips_succ");
		$("#address_msg").html("请输入详细地址");
	});
	$("#address").blur(function() {
		var addr = $("#address").val();
		addr = addr.trim();
		$("#address").val(addr);
		if (!validAddress(addr)) {
			return;
		}
	});
	$("#infoInterest").focus(function() {
		$("#infos_img").hide();
		$("#infos_msg").removeClass("tips_error");
		$("#infos_msg").removeClass("tips_succ");
		$("#infos_msg").html("请输入您的兴趣爱好，不超过120字");
	});
	$("#infoInterest").blur(function() {
		var addr = $("#infoInterest").val();
		addr = addr.trim();
		if (!validInfos(addr)) {
			return;
		}
	});
//	win();
//	$(window).resize(function() {
//		win();
//	});

});

/**
 * 爱好规则
 * 
 * @param address
 */
function validInfos(address) {
	var reg= /^[^\<\>]*$/;
	if (address == null || address == "") {
		$("#infos_msg").html("");
		$("#infoInterest").removeClass("text_error");
		$("#infos_msg").removeClass("tips_error");
		$("#infos_img").hide();
		return true;
	}else if(! reg.test(address)){
		$("#infos_msg").html("存在非法字符,请更换");
		$("#infoInterest").addClass("text_error");
		$("#infos_msg").addClass("tips_error");
		$("#infos_img").prop("src", "../images/error-ico.png");
		$("#infos_img").show();
		return false;
	} 
	else if (address.replace(/[^\x00-\xff]/g, "*").length > 120) {
		$("#infos_msg").html("长度超长");
		$("#infoInterest").addClass("text_error");
		$("#infos_msg").addClass("tips_error");
		$("#infos_img").prop("src", "../images/error-ico.png");
		$("#infos_img").show();
		return false;
	}
	$("#infos_msg").html("兴趣爱好正确").addClass("tips_succ");
	$("#infoInterest").removeClass("text_error");
	$("#infos_msg").removeClass("tips_error");
	$("#infos_img").prop("src", "../images/succ-ico.png");
	$("#infos_img").show();
	return true;
}
/**
 * 地址规则
 * 
 * @param address
 */
function validAddress(address) {
	if (address == null || address == "") {
		$("#address_msg").html("");
		$("#address").removeClass("text_error");
		$("#address_msg").removeClass("tips_error");
		$("#address_img").hide();
		return true;
	}
	else if(!/^[^\\<\\>]*$/.test(address)){
		$("#address_msg").html("存在非法字符,请更换");
		$("#address").addClass("text_error");
		$("#address_msg").addClass("tips_error");
		$("#address_img").prop("src", "../images/error-ico.png");
		$("#address_img").show();
		return false;
	} 
	else if (address.replace(/[^\x00-\xff]/g, "**").length > 120) {
		$("#address_msg").html("长度超长");
		$("#address").addClass("text_error");
		$("#address_msg").addClass("tips_error");
		$("#address_img").prop("src", "../images/error-ico.png");
		$("#address_img").show();
		return false;
	}
	$("#address_msg").html("详细地址正确").addClass("tips_succ");
	$("#address").removeClass("text_error");
	$("#address_msg").removeClass("tips_error");
	$("#address_img").prop("src", "../images/succ-ico.png");
	$("#address_img").show();
	return true;
}

function validPhone(phone) {
	var reg = /^(13[0-9]|17[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/;
	if (phone == null || phone == "") {
		
		return true;
	}
	if (!reg.test(phone)) {
		$("#phone_msg").html("手机格式不正确");
		$("#phone").addClass("text_error");
		return false;
	}
	$("#phone_msg").html("手机正确");
	$("#phone").removeClass("text_error");
	return true;
}
var flagId = false;
function validIdnum(idnum) {
	var reg = /^([1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3})|([1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X))$/;
//	var reg = /^[1-9]([0-9]{14}|[0-9]{17})$/;
	if (idnum == null || idnum == "") {
		$("#idnum_msg").html("");
		$("#idnum").removeClass("text_error");
		$("#idnum_msg").removeClass("tips_error");
		$("#idnum_img").hide();
		return true;
	}
	if (!reg.test(idnum)) {
		$("#idnum_msg").html("身份证号码格式不正确");
		$("#idnum").addClass("text_error");
		
		$("#idnum_msg").addClass("tips_error");
		$("#idnum_img").prop("src", "../images/error-ico.png");
		$("#idnum_img").show();
		return false;
	}
	$("#idnum_msg").html("身份证号码格式正确").addClass("tips_succ");
	$("#idnum").removeClass("text_error");
	
	$("#idnum_msg").removeClass("tips_error");
	$("#idnum_img").prop("src", "../images/succ-ico.png");
	$("#idnum_img").show();
	flagId = true;
	return true;
}

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

// 提交用户基本信息修改
function updateUserInfo() {
	var nickName = $("#nickName").val();
	var realName = $("#realName").val();
	var sex = $("input[name=infoGender]:checked").val();
    if($("#idnum").val().indexOf("*")<0){
        var idnum = $("#idnum").val();
    }

	var province = $("#infoProvince").val();
	var city = $("#infoCity").val();
	var county = $("#infoCounty").val();
	var infoStreet = $("#infoStreet").val();
	var address = $("#address").val();

	var birthday = $("#year").val() + "-" + $("#month").val() + "-"
			+ $("#day").val();

	var infoMarital = $("input[name=infoMarital]:checked").val();

	var infoSalary = $("#infoSalary").val();

	var infoInterest = $("#infoInterest").val();

	if (!validNickname(nickName)) {
		scroller("nickName", 500);
		return;
	}
	if (sex == null || sex == "") {
		$("#f_img").prop("src","../images/mod_war.png");
		$("#con_00").text("请选择性别！");
		dia(2);
		return;
	}
	if (!validBirthday()) {
		scroller("realName", 500);
		return;
	}
    if (!validRealname(realName)) {
		scroller("realName", 500);
		return;
	}
	if (!validIdnum(idnum)) {
		scroller("idnum", 500);
		return;
	}
	if (!validAddress(address)) {
		scroller("address", 500);
		return;
	}
	if (!validAddress(address)) {
		scroller("address", 500);
		return;
	}
	if (city == null || city == "" || county == null || county == "") {
		$("#city_msg").parent().addClass("prompt-error");
		$("#city_msg").parent().removeClass("prompt-06");
		$("#city_msg").html("请选择所在地");
		scroller("province", 500);
		return;
	}
	if(! validInfos(infoInterest)){
		scroller("infoInterest", 500);
		return;
	}
	var customerId = $("#hi_uid").val(), flag = 0;
	if (nickName != $("#nickName_").val()) {
		flag = 1;
	}

	var datas = "1=1";
	datas += "&customerNickname=" + nickName;
	datas += "&infoRealname=" + realName;
	datas += "&infoGender=" + sex;
	// datas += "&infoMobile=" + phone;
	datas += "&InfoBirthday=" + birthday;
	datas += "&infoProvince=" + province;
	datas += "&infoCity=" + city;
	datas += "&infoCounty=" + county;
	datas += "&infoStreet=" + infoStreet;
	datas += "&infoAddress=" + address;
	datas += "&infoMarital=" + infoMarital;
	datas += "&infoSalary=" + infoSalary;
	datas += "&infoInterest=" + infoInterest;
	datas += "&flag=" + flag;
	datas += "&CSRFToken="+$("#hi_token").val();

	if (flagId) {
		datas += "&infoCardid=" + idnum;
	}
	jQuery.ajax({
		type : "post",
		url : "../modifyInfo.html",
		data : datas,
		timeout : 10000,
		success : function(html) {
			if (html == 1) {
				dia(2);
			} else {
				$("#con_00").text("保存失败，请稍后再试...");
				dia(2);
			}
		},
		error:function(){
			$("#con_00").text("保存失败，请稍后再试...");
			dia(2);
		}
	});
}

function newBox() {
	jQuery
			.jdThickBox({
				type : "text",
				title : "提示",
				width : 200,
				height : 80,
				source : "<div class=\"tip-box icon-box\"><span class=\"succ-icon m-icon\"></span><div class=\"item-fore\"><h3 class=\"ftx02\">资料保存成功</h3><div class=\"op-btns\"><a href=\"javascript:void(0)\" class=\"btn-9\" onclick=\"jdThickBoxclose()\">关闭</a></div></div></div>",
				_autoReposi : true
			});
}
/**
 * 验证生日
 * @returns {boolean}
 */
function validBirthday() {
    var birthday = $("#year").val() + "-" + $("#month").val() + "-"
        + $("#day").val();
    var birth = new Date(Date.parse(birthday + " 00:00:00"));
    var nowDate = new Date();
    if(birth.getTime() > nowDate.getTime()){
        $("#birthday_msg").html("生日选择有误，请重新选择");
        $("#birthday_img").attr("src","../images/error-ico.png");
        return false;
    }
    $("#birthday_img").hide();
    $("#birthday_img").removeAttr("src");
    $("#birthday_img").attr("src","../images/succ-ico.png");
    $("#birthday_msg").html("生日正确");
    return true;
}
/**
 * 昵称规则
 * 
 * @param nickname
 */
function validNickname(nickName) {
	if (nickName == "") {
		$("#nickName").addClass("text_error");
		$("#nickName_msg").html("请输入昵称");
		$("#nickName_msg").addClass("tips_error");
		$("#nickName_img").prop("src", "../images/error-ico.png");
		$("#nickName_img").show();
		return false;
	}
	var reg = new RegExp("^([a-zA-Z0-9_-]|[\\u4E00-\\u9FFF])+$", "g");
	var reg_number = /^[0-9]+$/; // 判断是否为数字的正则表达式
	if (reg_number.test(nickName)) {
		$("#nickName_msg").html("昵称不能设置为手机号等纯数字格式，请您更换哦^^");
		$("#nickName_msg").addClass("tips_error");
		$("#nickName").addClass("text_error");
		$("#nickName_img").prop("src", "../images/error-ico.png");
		$("#nickName_img").show();
		return false;
	} else if (nickName.replace(/[^\x00-\xff]/g, "**").length < 4
			|| nickName.replace(/[^\x00-\xff]/g, "**").length > 20) {
		$("#nickName_msg").html("4-20个字符，可由中英文、数字、“_”、“-”组成");
		$("#nickName_msg").addClass("tips_error");
		$("#nickName").addClass("text_error");
		$("#nickName_img").prop("src", "../images/error-ico.png");
		$("#nickName_img").show();
		return false;
	} else if (!reg.test(nickName)) {
		$("#nickName_msg").html("昵称格式不正确");
		$("#nickName_msg").addClass("tips_error");
		$("#nickName").addClass("text_error");
		$("#nickName_img").prop("src", "../images/error-ico.png");
		$("#nickName_img").show();
		return false;
	}
	$("#nickName").removeClass("text_error");
	$("#nickName_msg").removeClass("tips_error");
	$("#nickName_msg").html("昵称正确").addClass("tips_succ");
	$("#nickName_img").prop("src", "../images/succ-ico.png");
	$("#nickName_img").show();
	return true;
}
function validEmail(email) {
	var reg = new RegExp(
			"^[0-9a-z][a-z0-9\._-]{1,}@[a-z0-9-]{1,}[a-z0-9]\.[a-z\.]{1,}[a-z]$",
			"g");
	if (!reg.test(email)) {
		$("#email_msg").html("邮箱格式不正确");
		$("#email").addClass("text_error");
		return false;
	}
	$("#email").removeClass("text_error");
	$("#email_msg").html("邮箱正确");
	return true;
}
/**
 * 真实姓名规则
 * 
 * @param realName
 */
function validRealname(realName) {
	// var uname_ = replaceChar(realName, "·"); // 去掉姓名中的·
	var reg = new RegExp("^([a-zA-Z]|[\\u4E00-\\u9FFF]|[·])+$", "g");
	if (realName == null || realName == "") {
		$("#realName").removeClass("text_error");
		$("#realName_msg").html("");
		return true;
	} else if (realName.indexOf("··") != -1) {
		// 姓名中不允许有连续多个·
		$("#realName").addClass("red");
		$("#realName_msg").html("不允许连续出现中间点");
		$("#realName").addClass("text_error");
		$("#realName_msg").addClass("tips_error");
		$("#realName_img").prop("src", "../images/error-ico.png");
		$("#realName_img").show();
		return false;
	} else if (realName.substring(0, 1) == "·" || realName.substring(realName.length - 1) == "·") {
		// 姓名前后不能加·
		$("#realName_msg").html("名字首尾不能出现中间点");
		$("#realName").addClass("text_error");
		$("#realName_msg").addClass("tips_error");
		$("#realName_img").prop("src", "../images/error-ico.png");
		$("#realName_img").show();
		return false;
	} else if (!reg.test(realName)) {
		$("#realName_msg").html("请输入真实姓名");
		$("#realName").addClass("text_error");
		$("#realName_msg").addClass("tips_error");
		$("#realName_img").prop("src", "../images/error-ico.png");
		$("#realName_img").show();
		return false;
	} else if (realName.replace(/[^\x00-\xff]/g, "**").length < 4
			|| realName.replace(/[^\x00-\xff]/g, "**").length > 20) {
		$("#realName_msg").html("请输入真实姓名,长度在4-20之间");
		$("#realName").addClass("text_error");
		$("#realName_msg").addClass("tips_error");
		$("#realName_img").prop("src", "../images/error-ico.png");
		$("#realName_img").show();
		return false;
	}
	$("#realName_msg").html("姓名正确").addClass("tips_succ");
	$("#realName_msg").removeClass("tips_error");
    $("#realName").removeClass("text_error");
	$("#realName_img").prop("src", "../images/succ-ico.png");
	$("#realName_img").show();
	return true;
}

function checkNickName(nickName) {
	// $("#nickName_msg").html("昵称唯一性验证中，请稍等...");
	// jQuery.ajax({
	// type: "GET",
	// url : "/user/petName/checkPetName.action?callback=?",
	// data : "petNewName=" + encodeURI(encodeURI(nickName)),
	// dataType: "jsonp",
	// timeout: 6000,
	// success : function(obj) {
	// if ("0" == obj.type) {
	// $("#nickName_msg").parent().removeClass("prompt-error");
	// $("#nickName_msg").parent().addClass("prompt-06");
	// $("#nickName_msg").html("");
	// $("#nickName").attr("class","itxt itxt-succ");
	// }
	// if ("1" == obj.type) {
	// $("#nickName_msg").parent().addClass("prompt-error");
	// $("#nickName_msg").parent().removeClass("prompt-06");
	// $("#nickName_msg").html("此昵称已被其他用户抢注，请修改");
	// $("#nickName").attr("class","itxt itxt-error");
	// }
	// },
	// error: function(){
	// }
	// });
}