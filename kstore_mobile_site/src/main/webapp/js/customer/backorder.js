var pageNo=1;
var iIntervalId = null;
var basePath = $("#basePath").val();
var type = $("#type").val();
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
    url: basePath+'/allmyorder.htm?pageNo='+pageNo+'&type='+type,
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
		 if(returnjson.pb.list!=null && returnjson.pb.list.length >0 ){
			 for(var i=0; i<returnjson.pb.list.length; i++) {
				 var backorder =  returnjson.pb.list[i];
				 nextpagehtml +='<div class="order-item">';
				 nextpagehtml +='<div class="order-number">';
				 nextpagehtml +='<div class="list-item">';
				 nextpagehtml +='<h3 class="item-head"><label for="">退单号：</label><span>';
				 nextpagehtml +=''+backorder.backOrderCode+'';
				 nextpagehtml +='</span>';
				 
				 nextpagehtml +='<span class="curValue text-them">';
				 if(backorder.backCheck != null && backorder.backCheck != ''){
					 if(backorder.backCheck == "0"){
						 nextpagehtml +='退货审核';
					 }else if(backorder.backCheck =="1"){
						 nextpagehtml +='同意退货';
					 }else if(backorder.backCheck=="2"){
						 nextpagehtml +='拒绝退货';
					 }else if(backorder.backCheck=="3" ){
						 nextpagehtml +='待商家收货';
					 }else if(backorder.backCheck=="4"){
						 nextpagehtml +='退单结束';
					 }else if(backorder.backCheck=="6"){
						 nextpagehtml +='退款审核';
					 }else if(backorder.backCheck=="7"){
						 nextpagehtml +='拒绝退款';
					 }else if(backorder.backCheck=="8"){
						 nextpagehtml +='拒绝收货';
					 }else if(backorder.backCheck=="9"){
						 nextpagehtml +='待填写物流信息';
					 }else if(backorder.backCheck=="10"){
						 nextpagehtml +='退款成功';
					 }
				 }
				 nextpagehtml +='</span></h3>';
				 nextpagehtml +='</div></div>';
				 nextpagehtml +='<div class="order-info">';
				 nextpagehtml +='<div class="list-body-box">';
                 if(backorder.orderGoodslistVo !=null){

                     if(backorder.orderGoodslistVo.length > 2){
                         nextpagehtml +='<div class="list-item">';
                         nextpagehtml +='<div class="box-body">';
                         nextpagehtml +='<ul>';
                         for(var n=0;n<backorder.orderGoodslistVo.length;n++){
                             var backgoods = backorder.orderGoodslistVo[n];
                             nextpagehtml +='<li>';
                             nextpagehtml +='<a title="'+backgoods.goodsInfoName+'" href="'+basePath+'/item/'+backgoods.goodsInfoId+'.html">';
                             nextpagehtml +='<img width="78" height="78" title="'+backgoods.goodsInfoName+'" alt="'+backgoods.goodsInfoName+'" src="'+backgoods.goodsInfoImgId+'" />';
                             nextpagehtml +='</a>';
                             nextpagehtml +='</li>';
                         }
                         nextpagehtml +='</ul>';
                         nextpagehtml +='</div>';
                         nextpagehtml +='</div>';
                     }else{
                         for(var m=0;m<backorder.orderGoodslistVo.length;m++){
                             var backgoods = backorder.orderGoodslistVo[m];
                             nextpagehtml +='<div class="list-item">';
                             nextpagehtml +='<a title="'+backgoods.goodsInfoName+'" href="'+basePath+'/item/'+backgoods.goodsInfoId+'.html">';
                             nextpagehtml +='<div class="pro-item">';
                             nextpagehtml +='<div class="propic">';
                             nextpagehtml +='<img width="78" height="78" title="'+backgoods.goodsInfoName+'" alt="'+backgoods.goodsInfoName+'" src="'+backgoods.goodsInfoImgId+'" />';
                             nextpagehtml +='</div>';
                             nextpagehtml +='<div class="prodesc">';
                             nextpagehtml +=' <h3 class="title">';
                             nextpagehtml +=''+backgoods.goodsInfoName+'';
                             nextpagehtml +='</h3>';
                             nextpagehtml +='<p class="price">¥&nbsp;<span class="num">';
                             nextpagehtml +=''+backgoods.goodsInfoPreferPrice.toFixed(2)+'';
                             nextpagehtml +='</span></p>';
                             nextpagehtml +='<span class="pro-num">x';
                             var num = 0;
                             if(backorder.backNum != null && backorder.backNum !=''){
                                 num = backorder.backNum;
                             }
                             nextpagehtml +=''+num+'';
                             nextpagehtml +='</span>';
                             nextpagehtml +=' </div>';
                             nextpagehtml +=' </div>';
                             nextpagehtml +='</a>';
                             nextpagehtml +='</div>';
                         }
                     }
                 }
				 nextpagehtml +='</div>';
				 nextpagehtml +='</div>';
				 nextpagehtml +='<div class="order-bottom">';
				 nextpagehtml +='<div class="list-item">';
				 nextpagehtml +='<h3 class="item-head">';
				 nextpagehtml +='<label for="">退款金额：</label><span class="pay-money">￥';
				 var moneyPaid = 0.00;
				 if(backorder.backPrice != null && backorder.backPrice != ''){
					 moneyPaid = backorder.backPrice.toFixed(2);
				 }
				 
				 nextpagehtml +=''+moneyPaid+'';
				 nextpagehtml +='</span>';
				 nextpagehtml +='</h3>';
				 nextpagehtml +='<div class="too-btn">';
				 if(backorder.backCheck !=null && backorder.backCheck!=''){
					 if(backorder.backCheck=="9"){
						 nextpagehtml +='<a class="btn fill-flow" href="javascript:void(0)" onclick="expressInfo('+backorder.orderCode+')" style="width:100px">填写物流信息</a>';
					 }
				 }
				 if(backorder.isBack != null && backorder.isBack !=''){
					 if(backorder.isBack == "2"){
						 nextpagehtml +='<a class="btn" href="'+basePath+'/customer/backorderpricedetail-'+backorder.order.orderId+'.html">退款详情</a><br/>';
					 }else{
						 nextpagehtml +='<a class="btn" href="'+basePath+'/customer/backordergoodsdetail-'+backorder.order.orderId+'.html">退货详情</a><br/>';
					 }
				 }
				 nextpagehtml +='</div>';
				 nextpagehtml +='</div>';
				 nextpagehtml +='</div>';
				 nextpagehtml +='</div>';
			 } 
			 
			 if(nextpagehtml != null && nextpagehtml != ""){
				 $('#items').append(nextpagehtml ); 
				 $newElems =$("#items .order-item");
				 // 渐显新的内容
				 $newElems.animate({ opacity: 1 });
				 if(returnjson.nextPageNo==pageNo){
					 clearInterval(iIntervalId);
					 $('#showmore').hide();
				 }else{
					 $('#showmore').html('<a class="handle" href="javascript:show()">显示更多结果</a>');
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
}

//物流名称失去焦点
function wuliuname(){
	var value = $("#wlname").val();//物流名称
	if(value==''||value.length<0){
		$("#yanzhengname").addClass("red");
		$("#yanzhengname").html("&nbsp;<img src='../images/red_cha.png'/>物流公司名称不能为空！");
	}else{
		$("#yanzhengname").html("");
	}
}


//物流单号失去焦点
function wuliudanhao(){
    var reg = /^[0-9a-zA-Z]+$/;
	var no = $("#wlno").val(); //物流单号

	if(no==''||no.length<0){
		$("#yanzhengno").addClass("red");
		$("#yanzhengno").html("&nbsp;<img src='../images/red_cha.png'/>物流公司单号不能为空！");

	}else{
        if(!reg.test(no)){
            $("#yanzhengno").addClass("red");
            $("#yanzhengno").html("&nbsp;<img src='../images/red_cha.png'/>只能为数字或字母！");
        }else{
            $("#yanzhengno").html("");
        }

	}
}

//验证物流信息
function checkValue(value,no){
    var reg = /^[0-9]|[a-z]|[A-Z]+$/;
	var result = 0;
	if(value==''||value.length<0){
		$("#yanzhengname").addClass("red");
		$("#yanzhengname").html("&nbsp;<img src='../images/red_cha.png'/>物流公司名称不能为空！");
		result=1;
	}
	if(no==''||no.length<0){
		$("#yanzhengno").addClass("red");
		$("#yanzhengno").html("&nbsp;<img src='../images/red_cha.png'/>物流公司单号不能为空！");
		result=1;
	}else{
        if(!reg.test(no)){
            $("#yanzhengno").addClass("red");
            $("#yanzhengno").html("&nbsp;<img src='../images/red_cha.png'/>只能为数字或字符！");
        }else{
            $("#yanzhengno").html("");
        }
    }
	return result;
}

//提交物流信息
function quedingwl(flag){//判断是我的订单跳转还是会员中心首页跳转
	var subflag = 0;
	var wlname = $("#wlname").val();//物流名称
	var wlno = $("#wlno").val(); //物流单号
	var orderNo = $("#orderNo").val(); //订单号
	if(checkValue(wlname,wlno)==0){//验证物流信息
		//var index = url.substring(url.length-6,url.length-5);//截取当前的页数
		//var yueindex = url.substring(url.length-10,url.length-9);//截取一月前订单
		if(subflag == 0){
				$.ajax({
					//提交数据的类型 POST GET
					type:"POST",
					//提交的网址
					url:basePath+"/saveMBackOrderGeneral.htm",
					//提交的数据
					data:{wlname:wlname,wlno:wlno,orderNo:orderNo},
					async:false,
					//返回数据的格式
					datatype: "post",
					//成功返回之后调用的函数             
					success:function(data){
						subflag = 1;
						if(data==1){
							window.location.href =basePath+'/customer/myorder-6-1.html';
						}else if(data==0){
							window.location.href =basePath+'/loginm.html';
						}
					}
				});
		}
	}else{
		$("#fillForm").showModal();
	}
}

function expressInfo(orderNo){
	 $("#orderNo").val(orderNo);
       var fillFlow = dialog({
           width : 300,
           title : '填写物流信息',
           content : $('.fill-flow-form'),
           okValue : '保存',
           cancelValue : '取消',
           ok : function(){
               quedingwl('00');
           },
           cancel : function(){
               return true;
           }
       });
       fillFlow.showModal();
   }
function addCar(id){
	$.ajax({
		url:basePath+'/addproducttoshopcarbyorderid.htm?orderId='+id,
		type:'post',
		success:function(data){
			if(data>0){
				window.location.href = basePath+"/myshoppingmcart.html";
			}
		}
	});
}