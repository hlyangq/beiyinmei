/**
 * 
 */

$(function(){
	/**
	 * 省份改变时 触发此事件 开始加载下一级 --城市 select[id^='province']
	 */
	$("#infoProvince").change(function() {
		loadCity($(this).val());
	});
	/**
	 * 城市改变时 触发此事件 开始加载下一级 --区县
	 */
	$("#infoCity").change(function() {
		loadDistrict($(this).val());
	});

	/**
	 * 区县改变时 触发此事件 开始加载下一级 --街道
	 */
	$("#infoCounty").change(function() {
		loadStreet($(this).val());
	});
});


/**
 * 选中 省市区街道
 * 
 * @param pid
 *            省编号
 * @param cid
 *            城市编号
 * @param did
 *            区县编号
 * @param sid
 *            街道编号
 * @param po
 *            infoProvince
 * @param co
 *            infoCity
 * @param dio
 *            infoCounty
 * @param so
 *            infoStreet
 */
function selectLocationOption(pid, cid, did, sid, po, co, dio, so) {
	if (pid == "") {
		return;
	}
	$("#" + po + " option[value='" + pid + "']").prop("selected", "selected"); // 选中省份
	loadCity(pid);
	$("#" + co + " option[value='" + cid + "']").prop("selected", "selected"); // 选中城市
	loadDistrict(cid);
	$("#" + dio + " option[value='" + did + "']").prop("selected", "selected");// 选中区县
	loadStreet(did);
	$("#" + so + " option[value='" + sid + "']").prop("selected", "selected"); // 选中街道
}

/**
 * 加载省份
 */
function loadProvice(pro) {
    //获取省份
    //var province_map = $('.province_map').val();
    var basePath=$("#basePath").val();
	$.ajax({
		type : 'post',
		url : basePath+'/getAllProvince.htm',
		async : false,
		success : function(data) {
			var options = "";
			for (var i = 0; i < data.length; i++) {
				var province = data[i];
				if (province.provinceName == pro && pro!="") {
					options += "<dd class='checked' id='pro"+province.provinceId+"' onclick='chooseP(" + province.provinceId + ");'>"+ province.provinceName + "</dd>";
				}else{
	                options += "<dd id='pro"+province.provinceId+"' onclick='chooseP(" + province.provinceId + ");'>"+ province.provinceName + "</dd>";
				}
			}
			$("#prodl").html(options);
		}
	});
	//loadCity($("#infoProvince").val());
};

/**
 * 加载城市
 *
 * @param pid
 *            省份编号
 */
function loadCity(pid,cname) {
    //获取城市
    //var city_map = $('.city_map').val();
    var basePath=$("#basePath").val();
	var paramStr = "provinceId=" + pid;
	$.ajax({
				type : 'post',
				url :basePath+ '/getAllCityByPid.htm',
				data : paramStr,
				async : false,
				success : function(data) {
					if (data.length != 0) {
						var options = "";
						for (var i = 0; i < data.length; i++) {
							var city = data[i];
							/*options += "<option value='" + city.cityId + "'>"+ city.cityName + "</option>";*/
                           options += "<dd id='ci"+city.cityId+"' onclick='chooseC(" + city.cityId + ");'>"+ city.cityName + "</dd>";
						}
						$("#citydl").html(options);
					} else {
						$("#citydl").html("");
					}
				}
			});
	//loadDistrict($("#infoCity").val());
}
/**
 * 加载区县
 *
 * @param pid
 *            省份编号
 */
function loadDistrict(pid) {
    //获取区县
    //var district_map = $('.district_map').val();
	var paramStr = "cityId=" + pid;
    var basePath=$("#basePath").val();
	$.ajax({
		type : 'post',
		url : basePath+'/getAllDistrictByCid.htm',
		data : paramStr,
		async : false,
		success : function(data) {
			if (data.length != 0) {
				var options = "";
				for (var i = 0; i < data.length; i++) {
					var district = data[i];
                    	options += "<dd id='ar"+district.districtId+"' onclick='chooseA(" + district.districtId + ");'>"+ district.districtName + "</dd>";
                        //options += "<option selected='selected' value='" + district.districtId + "'>"+ district.districtName + "</option>";
                        //options += "<option value='" + district.districtId + "'>"+ district.districtName + "</option>";
				}
				$('#areadl').html(options);
			} else {
				$("#areadl").html("");
			}
		}
	});
	//loadStreet($("#infoCounty").val());
}
/**
 * 加载街道
 *
 * @param cid
 *            城市编号
 */
function loadStreet(cid) {
	var paramStr = "dId=" + cid;
    var basePath=$("#basePath").val();
	$.ajax({
		type : 'post',
		url :basePath+ '/getAllStreetByDid.htm',
		data : paramStr,
		async : false,
		success : function(data) {
			if (data.length != 0) {
				var options = "";
				for (var i = 0; i < data.length; i++) {
					var street = data[i];
					options += "<option value='" + street.streetId + "'>"
							+ street.streetName + "</option>";
				}
				$('#infoStreet').html(options);
			} else {
				$("#infoStreet").html(
						"<option value='' >" + "请选择" + "</option>");
			}
		}
	});
}

/**
 * 根据地址搜索店铺信息
 */
function searchStoreByAddress(){
    var infoProvince = $('#infoProvince').val(); //省份
    var infoCity = $('#infoCity').val();  //城市
    var infoCounty = $('#infoCounty').val(); //街道
    // 请选择的value 的值是0
    $('.search_tip').html('');
    var bussAddrId = infoProvince+','+infoCity+','+infoCounty+',';
    $('.bussAddrId').val(bussAddrId);
    $('.searchStoreByAddress').submit();
}

/**
 * 选中省份
 */
function infoProvince_check(){
    $('.search_tip').html('');
    var infoProvince = $('#infoProvince').val(); //省份
    var infoCity = $('#infoCity').val();  //城市
    var infoCounty = $('#infoCounty').val(); //街道
    if(infoProvince != null && infoCity !=null && infoCounty!=null){
        var bussAddrId = infoProvince+','+infoCity+','+infoCounty+',';
        $('.bussAddrId').val(bussAddrId);
    }
    
}
var choosedp="";
var choosedc="";
var chooseda="";
/**
 * 点击省份加载市
 */
function chooseP(proid){
	$('.area-box-lv1').hide();
    $('.area-box-lv2').show();
	choosedp = $("#pro"+proid).text();
	$("#readypro").html("");
	$("#readypro").html(choosedp);
	$("#provinceCh").val(proid);
	loadCity(proid);
}
/**
 * 点击市加载区
 */
function chooseC(cid){
	 $('.area-box-lv2').hide();
     $('.area-box-lv3').show();
	choosedc = $("#ci"+cid).text();
	$("#readycity").html("");
	$("#readycity").html(choosedp+choosedc);
	loadDistrict(cid);
	$("#cityCh").val(cid);
}
/**
 * 点击区
 */
function chooseA(aid){
	chooseda = $("#ar"+aid).text();
	$("#readycity").html("");
	$("#readycity").html(choosedp+choosedc+chooseda);
	$("#countyCh").val(aid);
	$(".choose_area").html("");
	$(".choose_area").html(choosedp+choosedc+chooseda);
	$('.area-box-lv3').hide();
	$('.opacity-bg-3').remove();
    $('.screen').hide();
}
function back1(){
	$('.area-box-lv2').hide();
    $('.area-box-lv1').show();
    $("#readypro").html("");
	$("#readypro").html(choosedp);
	$("#cityCh").val("");
}
function back2(){
	$("#readycity").html("");
	$("#readycity").html(choosedp+choosedc);
	$('.area-box-lv3').hide();
    $('.area-box-lv2').show();
}