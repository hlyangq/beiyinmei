
/*
 * 更改页数
 */
function changePageNo(obj){
	$(".searchThirdOrderList").append("<input type='hidden' name='pageNo' value='"+$(obj).attr("data-role")+"' />").submit();
}

function modifyThirdbkorderbyId(orderId,sta){
	$(".up_order_sta").val(orderId);
	var options = $(".old_order_sta").find("option");
	for(var i =0;i<options.length;i++){
		if($(options[i]).val()==sta){
			$(options[i]).prop("selected","selected");
			break;
		}
	}
	 dia(4);
}

//查询状态改变
function changTbl(obj){
	resetThirdOrder();
	$(".tabStatus").prop("value",obj);
	$(".searchThirdOrderList").submit();
}
//根据条件查询订单信息
function queryOrderList(){
    $('.searchThirdOrderList').submit();
}

//重置表单
function resetThirdOrder(){
	$(".searchThirdOrderList")[0].reset();
	$(".iq_text").prop("value","");
    $(".form-control").prop("value","");
}

//提交表单
function subThirdOrderSta(){
	$(".up_third_back_order_sta").submit();
}




$(function(){
	//隐藏审核提示
	$('.backOrderOption').hide();
	if($(".tabStatus").val()==1){
		$(".tb_backOrderr").addClass("cur");
	}
});

//取消订单
function delBackOrder(backOrderId){
	$('#quedingbackOrderId').val(backOrderId);
}


//取消订单 对话框 确定
function deleteOrder(){
	$('.up_delete_order_sta').submit();
}


//订单审核
function fnModifyBackOrder(backOrderId){
	$('#updateOrderCheck').val(backOrderId);
}

//退款审核
function fnModifyBackOrderMoney(backOrderId){
	$('#returnMoneyId').val(backOrderId);
}
//确认收货
function getGoods(backOrderId){
    $("#backmoney").val("");
    $("#backmoney").removeClass("ui-state-error");
    $("#backmoney").parent().next().text("");
    $("#backmoney").parent().next().hide();
	$('#backOrderId').val(backOrderId);
}
//确认收货 确定按钮
function querenshouhuo(){
    if($("#confirmBackCheck").val() == '4' || $("#confirmBackCheck").val() == ""){
        if($("#backmoney").val() == ""){
            $("#backmoney").addClass("ui-state-error");
            $("#backmoney").parent().next().text("请填写退款金额！");
            $("#backmoney").parent().next().show();
            return;
        }else{
            if(/^([0-9][0-9]*(\.[0-9]{1,2})?|0\.(?!0+$)[0-9]{1,2})$/.test($("#backmoney").val())){

            }else{
                $("#backmoney").addClass("ui-state-error");
                $("#backmoney").parent().next().text("请填写正确的退款金额！");
                $("#backmoney").parent().next().show();
                return;
            }
            //验证退款金额
            if(!checkbackMoney()){
                return;
            }
            $("#confirmBackCheck").val('4');
        }
    }else{
        $("#confirmBackCheck").val('7');
    }
    $("#backFlag3").val(3);
    $('.up_queren_order_sta').submit();
}

function checkbackMoney(){
    var result = false;
    var backorderid = $("#backOrderId").val();
    var money = $("#backmoney").val();
    if(money !=""){
        $.ajax({
            type: 'post',
            url: 'checkpriceback.htm?backOrderId='+ backorderid,
            async: false,
            success: function(data){
                if(money > data.backPrice){
                    $("#backmoney").addClass("ui-state-error");
                    $("#backmoney").parent().next().text("输入金额必须小于等于退款金额！");
                    $("#backmoney").parent().next().show();
                }else{
                    $("#backmoney").removeClass("ui-state-error");
                    $("#backmoney").parent().next().text("");
                    $("#backmoney").parent().next().hide();
                    result = true;
                }
            }
        })
    }
    return result;
}

//审核退款确认按钮
function returnMoneySub(){
    $("#backFlag2").val(2);
    $(".up_third_backOrder_money").submit();
}

function showPrice(id){
    if(id == 0){
        $("#confirmBackCheck").val('4');
        $("#priceDiv").show();
    }else{
        $("#backmoney").removeClass("ui-state-error");
        $("#backmoney").parent().next().hide();
        $("#confirmBackCheck").val('7');
        $("#priceDiv").hide();
    }
}


//更改对话框确定按钮
function subBackOrder(){
    $("#backFlag1").val(1);
	$(".up_third_order_sta").submit();
}




//更新订单状态信息
function upOrderSta(orderId,sta){
	$(".ordersta").removeClass("none");
	$(".up_order_sta").val(orderId);
	var options = $(".old_order_sta").find("option");
	for(var i =0;i<options.length;i++){
		if($(options[i]).val()==sta){
			$(options[i]).prop("selected","selected");
			break;
		}
	}
	//如果是未付款
	if(sta==0){
		$(".option1").addClass("none");
		$(".option2").addClass("none");
		$(".option0").addClass("none");
		$(".option3").addClass("none");
	}else if(sta==1){
		$(".option0").addClass("none");
		$(".option1").addClass("none");
		$(".option3").addClass("none");
		$(".option4").addClass("none");
		$(".option6").addClass("none");
	}else if(sta==2){
		$(".option0").addClass("none");
		$(".option1").addClass("none");
		$(".option4").addClass("none");
		$(".option6").addClass("none");
	}else if(sta==3){
		$(".option0").addClass("none");
		$(".option1").addClass("none");
		$(".option2").addClass("none");
		$(".option4").addClass("none");
		$(".option6").addClass("none");
	}else if(sta == 4){
		$(".option0").addClass("none");
		$(".option1").addClass("none");
		$(".option2").addClass("none");
		$(".option3").addClass("none");
		$(".option6").addClass("none");
	}
	dia(4);
}







