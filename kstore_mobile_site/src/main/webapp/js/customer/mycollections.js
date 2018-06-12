var pageNo=1;
var iIntervalId = null;
var basePath = $("#basePath").val();
$(function(){
    window.onload=function(){
        $("#status").val(0);
    }
});
function show(){
    if($("#status").val()==1){
        return;
    }
	 pageNo++;
  $.ajax({
    url: basePath+'/allmycollection.htm?pageNo='+pageNo,
    type: 'GET',
    dataType: 'text',
    async:'false',
	beforeSend: showLoadingImg,
    error: showFailure,
    success:showResponse
  });
}

function showLoadingImg() {
	 $('#showmore').html(' <img src='+basePath+'/images/loading.gif /><span>加载中……</span>');
	 $('#showmore').show();
}

function showFailure() {
	 $('#showmore').html('<font color=red> 获取查询数据出错 </font>');
}
//根据ajax取出来的json数据转换成html
function showResponse(responseData) {
		 var returnjson = eval("(" +responseData+")");	
		 var nextpagehtml = '';
    if(returnjson.list !=null && returnjson.list.length >0){
		 for(var i=0; i<returnjson.list.length; i++) {
			 var follow =  returnjson.list[i];
		    nextpagehtml +='  <div class="list-item pro-item';
		    if(follow.good.goodStock <= 0){
		    	nextpagehtml +=' sell-out';
		    }
		    nextpagehtml +=' " id="collection'+follow.followId+'">';
		    nextpagehtml +='  <a href="'+basePath+'/item/'+follow.goodsId+'.html">';             
		    nextpagehtml += '<div class="propic">';  
		    nextpagehtml += '<img width="100" height="100"  alt='+follow.good.goodsName+' title='+follow.good.goodsName+' src='+follow.good.goodsImg+' />';
		    nextpagehtml += '</div>';  
		    nextpagehtml += '<div class="prodesc">';  
		    nextpagehtml += '<h3 class="title">';  
		    var goodsname=follow.good.goodsName;
		    if(follow.good.goodsName.length > 50){
		    	goodsname = goodsname.substring(0,50);
		    }
		    nextpagehtml += ''+goodsname+'';  
		    nextpagehtml += '</h3>';  
		    nextpagehtml += '<p class="price">&yen;&nbsp;<span>'; 
		    var goodsprice = 0.00;
		    if(follow.good.goodsPrice !='' && follow.good.goodsPrice != null){
		      goodsprice = follow.good.goodsPrice.toFixed(2);
		    }
		    nextpagehtml += ''+goodsprice+'';  
		    nextpagehtml += '</span></p>';  
		    nextpagehtml += '<p class="comm">';  
		    nextpagehtml += '<span class="item-percent">好评<span>'+follow.productComment+'</span>%</span>';  
		    nextpagehtml += '<span class="item-pnum"><span>'+follow.good.commentCount+'</span>人评价</span>';  
		    nextpagehtml += '</p>';  
		    nextpagehtml += '</div>';  
		    nextpagehtml += '</a>';
             /* nextpagehtml += '<div class="proctrl">';
		   nextpagehtml +='<a href="javascript:void(0);" class="addcart_btn"  data-val="';
		    var stockflag =0;
		    if(follow.good.goodStock  !='' && follow.good.goodStock >0){
		    	stockflag =1;
		    }
		    nextpagehtml += ''+stockflag+'" data-pro="'+follow.goodsId+'"  onclick="addCar(this)">加入<br>购物车</a>'; 
		    nextpagehtml +='<a href="javascript:void(0);" class="del_btn" onclick="cancelcollection(this,'+follow.followId+')">取消<br>收藏</a>';*/
		    nextpagehtml +='<a href="javascript:void(0);" class="del-btn" onclick="cancelcollection(this,'+follow.followId+')"><i class="ion-ios-trash"></i></a>';
		 /*   nextpagehtml += '</div>';*/
		    nextpagehtml += '</div>';  
	 } 
	 if(nextpagehtml != null && nextpagehtml != ""){
	 $('#items').append(nextpagehtml ); 
	  $newElems =$("#items .list-item");
	     // 渐显新的内容
	     $newElems.animate({ opacity: 1 });
	     if(returnjson.nextPageNo==pageNo){
	    	 clearInterval(iIntervalId);
	    	 $('#showmore').hide();
	     }else{
	    	// $('#showmore').html('<a class="handle" href="javascript:show()">显示更多结果</a>');
	    	 $('#showmore').html(' <img src='+basePath+'/images/loading.gif /><span>加载中……</span>');
	    	 $('#showmore').show();
	     }
	 }else{
		 $('#showmore').html('<a class="handle" href="javascript:show()">已无更多结果</a>');
	 }
    }else{
        $("#status").val(1);
        $('#showmore').html('<a class="handle" href="javascript:show()">已无更多结果</a>');
    }
 // 当前页码数小于3页时继续显示更多提示框
/* if(page < 2) {
   $('#showmore').html('<a class="handle" href="javascript:show()">显示更多结果</a>');
   clearInterval(iIntervalId);
   $('#showmore').hide();
 }*/
//bdShare.fn.init();
	// loadcollection();
}

function cancelcollection(obj,id){
	$.ajax({
		url:basePath+"/cancelcollection.htm",
		data:"followId="+id,
		type:"post",
		success:function(data){
			if(data.flag>0){
				$("#collection"+id).remove();
				if(data.myCollectionCount <= 0){
					$("#no").show();
				}
			}
		}
	});
	
}

function loadcollection(){
	
	/* 滑动显示编辑按钮 */
	for(var i = 0;i<$('.pro-item').length;i++){
		var obj = $('.pro-item')[i];
		var startX = 0;
		obj.addEventListener('touchstart',function(e){
			startX = e.targetTouches[0].clientX;
		},false);
		obj.addEventListener('touchmove',function(e){
			var moveX = e.changedTouches[0].clientX - startX;
			console.log(moveX);
			if(moveX < 0 && moveX >= -112){
				this.style.marginLeft = moveX + 'px';
			}
			else if((parseInt(this.style.marginLeft) + moveX < 0) && moveX > 0){
				var oldLeft = parseInt(this.style.marginLeft);
				this.style.marginLeft = oldLeft + moveX + 'px';
			}
		},false);
		obj.addEventListener('touchend',function(e){
			if(parseInt(this.style.marginLeft) > -66){
				this.style.marginLeft = '0';
			}
			else{
				this.style.marginLeft = '-112px';
			}
		},false);
	}
}

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