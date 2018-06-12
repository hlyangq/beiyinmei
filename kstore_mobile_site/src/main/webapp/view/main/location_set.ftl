<!DOCTYPE html>
<html lang="zh-CN">
<#assign basePath=request.contextPath>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${basePath}/css/ui-dialog.css"/>
    <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
    <script src="${basePath}/js/jquery-1.10.1.js"></script>
    <script src="${basePath}/js/pageAction.js"></script>
    <script src="${basePath}/js/dialog-min.js"></script>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=409dcf8c6daa616fb02680e692a95123"></script>
    <script src="${basePath}/js/jquery.js"></script>
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
</head>
<body onload="watchPosition()">

<div class="safe_list">
    <div class="safe_item">
        <h4>我们会根据您的位置，安排最近的门店送货。</h4>
    </div>
    <div class="safe_item">
        <h4 id="adressHtml"><em class="iconfont icon-location red"></em></h4>
    </div>
</div>
<form action="main.html" method="post">
    <div class="search_location">
        <input onkeyup="placeSearch()" type="text" id ="keyword" class="text" placeholder="填写您的地址">
        <input type="hidden" value="" id="pr_province">
        <input type="hidden" value="" id="pr_city">
        <input type="hidden" value="" id="pr_district">
        <input type="hidden" value="" id="pr_pstreet_number">
        <input type="hidden" value="" id="pr_street">
        <input type="hidden" value="" id="pr_business">
        <input type="hidden" value="0" id="status">
        <button onclick="placeSearch()" type="submit">搜索</button>
    </div>
</form>

<div id="result1" name="result1"></div>
<#include "../common/smart_menu.ftl"/>


<script>

    $(function(){



    });

    /* 获取地理位置 */
    var waitinglocation;
    function getLocation(){
        waitinglocation = dialog({title:'正在获取位置……'}).showModal();
        var options={
            enableHighAccuracy:true,
            maximumAge:500
        };
        if(navigator.geolocation){
            //浏览器支持geolocation
            navigator.geolocation.getCurrentPosition(onSuccess,onError,options);

        }else{
            //浏览器不支持geolocation
        }
    }

    //成功时
    function onSuccess(position){
        //返回用户位置
        //经度
        var longitude =position.coords.longitude;
        //纬度
        var latitude = position.coords.latitude;

        waitinglocation.close().remove();
        var showlocation = dialog({
            title : '确认当前位置',
            content : '<i class="iconfont icon-location"></i> 您当前的坐标' + longitude + '|'+ latitude,
            okValue : '确定',
            cancelValue : '重新填写',
            ok : function(){

                return true;
            },
            cancel : function(){
                window.location.href="";
            }
        }).showModal();

    }

    //输入提示框鼠标滑过时的样式
    function openMarkerTipById(pointid, thiss) {  //根据id打开搜索结果点tip
        thiss.style.background = '#CAE1FF';
    }

    //失败时
    function onError(error){
        switch(error.code){
            case 1:
                waitinglocation.close().remove();
                $('body').append('<div class="tip-box"><div class="tip-body"><i class="failed"></i><h3>位置服务被拒绝！</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },2200);
                break;

            case 2:
                waitinglocation.close().remove();
                $('body').append('<div class="tip-box"><div class="tip-body"><i class="failed"></i><h3>获取地址失败！</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },2200);
                break;

            case 3:
                $('body').append('<div class="tip-box"><div class="tip-body"><i class="failed"></i><h3>获取信息超时！</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },2200);
                break;

            case 4:
                $('body').append('<div class="tip-box"><div class="tip-body"><i class="failed"></i><h3>未知错误！</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },2200);
                break;
        }

    }

</script>


<script type="text/javascript">
    var map = new AMap.Map("mapContainer", {
        resizeEnable: true
    });
    var marker = new Array();
    var windowsArr = new Array();
    var mapObj = new AMap.Map("mapContainer", {
        resizeEnable: true,
        resizeEnable: true,
        center: [116.397428, 39.90923],//地图中心点
        zoom: 13,//地图显示的缩放级别
        keyboardEnable: false
    });
    //基本地图加载
    function placeSearch(){
        var MSearch;
        AMap.service(["AMap.PlaceSearch"], function() {
            MSearch = new AMap.PlaceSearch({ //构造地点查询类
                pageSize:10,
                pageIndex:1,
                city:$("#pr_city").val() //城市
            });
            //关键字查询
            MSearch.search($("#keyword").val(), function(status, result){
                if(status === 'complete' && result.info === 'OK'){
                    keywordSearch_CallBack(result);
                }
            });
        });
    }

    //从输入提示框中选择关键字并查询
    function selectResult(x,y,index) {
        if (index < 0) {
            return;
        }


        if (navigator.userAgent.indexOf("MSIE") > 0) {
            document.getElementById("keyword").onpropertychange = null;
            document.getElementById("keyword").onfocus = focus_callback;
        }
        var text = document.getElementById("divid" + (index + 1)).innerHTML.replace(/<[^>].*?>.*<\/[^>].*?>/g, "");
        //截取输入提示的关键字部分
        document.getElementById("keyword").value = text;
        document.getElementById("result1").style.display = "none";
        windowsArr = [];
        marker = [];
        mapObj.clearMap();
        mapObj.setFitView();
        var arr=new Array();
        arr.push(x);
        arr.push(y);
        geocoder(arr);
    }

    //定位选择输入提示关键字
    function focus_callback() {
        if (navigator.userAgent.indexOf("MSIE") > 0) {
            document.getElementById("keyword").onpropertychange = autoSearch;
        }
    }

    //输入提示框鼠标移出时的样式
    function onmouseout_MarkerStyle(pointid, thiss) {  //鼠标移开后点样式恢复
        thiss.style.background = "";
    }

    //回调函数
    function keywordSearch_CallBack(data) {
        var resultStr = "";
        var poiArr = data.poiList.pois;

        if (poiArr && poiArr.length > 0) {
            var resultCount = poiArr.length;
            for (var i = 0; i < resultCount; i++) {
                var lngX = poiArr[i].location.getLng();
                var latY = poiArr[i].location.getLat();
                resultStr += "<div id='divid" + (i + 1) + "' onmouseover='openMarkerTipById(" + (i + 1)
                        + ",this)' onclick='selectResult("+lngX+","+latY+","+i+")' onmouseout='onmouseout_MarkerStyle(" + (i + 1)
                        + ",this)' style=\"font-size: 13px;cursor:pointer;padding:5px 5px 5px 5px;\">" + poiArr[i].name + "  <span style='color:#C1C1C1;'>" + poiArr[i].address + "</span></div>";
            }
        } else {
            resultStr = " π__π 亲,人家找不到结果!<br />要不试试：<br />1.请确保所有字词拼写正确<br />2.尝试不同的关键字<br />3.尝试更宽泛的关键字";
        }
        document.getElementById("result1").curSelect = -1;
        document.getElementById("result1").tipArr = poiArr;
        document.getElementById("result1").innerHTML = resultStr;
        document.getElementById("result1").style.display = "block";
        map.setFitView();
    }

    function openMarkerTipById1(pointid, thiss) {  //根据id 打开搜索结果点tip
        thiss.style.background = '#CAE1FF';
        windowsArr[pointid].open(map, marker[pointid]);
    }
    function onmouseout_MarkerStyle(pointid, thiss) { //鼠标移开后点样式恢复
        thiss.style.background = "";
    }
</script>

<script type="text/javascript">
    var map, geolocation;
    //加载地图，调用浏览器定位服务
    map = new AMap.Map('mapContainer', {
        resizeEnable: true
    });
    map.plugin('AMap.Geolocation', function() {
        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            maximumAge: 2000,           //定位结果缓存0毫秒，默认：0
            convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
            showButton: true,        //显示定位按钮，默认：true
            buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
            buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
            showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
            panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
            zoomToAccuracy: true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
        });
        map.addControl(geolocation);
        AMap.event.addListenerOnce(geolocation, 'complete', onComplete);//返回定位信息
        AMap.event.addListenerOnce(geolocation, 'error', onError);      //返回定位出错信息
    });
    //获取当前位置信息
    function getCurrentPosition() {
        geolocation.getCurrentPosition();
    }
    ;
    //监控当前位置并获取当前位置信息
    function watchPosition() {
        geolocation.watchPosition();
    }
    ;
    //解析定位结果
    function onComplete(data) {
        if($("#status").val()=="0"){
            //已知点坐标
            var obj = new Array();
            obj.push(data.position.getLng());
            obj.push(data.position.getLat());
            $("#status").attr("value","1");
            geocoder(obj);

        }

    }
    ;
    //解析定位错误信息
    function onError(data) {
        var str = '<p>定位失败</p>';
        str += '<p>错误信息：'
        switch (data.info) {
            case 'PERMISSION_DENIED':
                str += '浏览器阻止了定位操作';
                break;
            case 'POSI  TION_UNAVAILBLE':
                str += '无法获得当前位置';
                break;
            case 'TIMEOUT':
                str += '定位超时';
                break;
            default:
                str += '未知错误';
                break;
        }
        str += '</p>';
        result.innerHTML = str;
    }
</script>

<script type="text/javascript">
    var map = new AMap.Map("mapContainer", {
        resizeEnable: true,
        center: [116.397428, 39.90923],//地图中心点
        zoom: 13 //地图显示的缩放级别
    });

    var lnglatXY;


    function geocoder(obj) {
        lnglatXY=obj;
        var MGeocoder;
        //加载地理编码插件
        AMap.service(["AMap.Geocoder"], function() {
            MGeocoder = new AMap.Geocoder({
                radius: 1000,
                extensions: "all"
            });
            //逆地理编码
            MGeocoder.getAddress(lnglatXY, function(status, result) {
                if (status === 'complete' && result.info === 'OK') {
                    geocoder_CallBack(result);
                }
            });
        });
        //加点
        var marker = new AMap.Marker({
            map: map,
            position: lnglatXY
        });
        map.setFitView();
    }
    //鼠标划过显示相应点
    var marker;
    function onMouseOver(e) {
        var coor = e.split(','),
                lnglat = [coor[0], coor[1]];
        if (!marker) {
            marker = new AMap.Marker({
                map: map,
                position: lnglat
            });
        } else {
            marker.setPosition(lnglat);
        }
        map.setFitView();
    }
    //回调函数
    function geocoder_CallBack(data) {
        var resultStr = "";
        var poiinfo = "";
        var address;
        //返回地址描述
        var province =data.regeocode.addressComponent.province;
        var city = data.regeocode.addressComponent.city;
        var district =data.regeocode.addressComponent.district
        var street_number  =data.regeocode.addressComponent.streetNumber
        var street  =data.regeocode.addressComponent.street
        var business  =data.regeocode.addressComponent.street


        $.ajax({
            type: "POST",
            url: "saveaddress.htm",
            data: "province="+province+"&city="+city+"&district="+district+"&streetNumber="+street_number+"&street="+street+"&business="+business,
            success: function(msg){
            }
        });



        $("#pr_province").val(province);
        $("#pr_city").val(city);
        $("#pr_district").val(district);
        $("#pr_pstreet_number").val(street_number);
        $("#pr_street").val(street);
        $("#pr_business").val(business);
        address =province+city+district+street+street_number ;
        $("#adressHtml").html('<em class="iconfont icon-location red"></em>当前位置：'+address);
    }
</script>
</body>
</html>