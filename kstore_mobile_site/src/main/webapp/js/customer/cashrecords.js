/**
 * Created by chenpeng on 2016/10/17.
 */
var pageNo=1;
var basePath = $("#basePath").val();

$(".tabs a").on('touchstart mousedown',function(e){
    e.preventDefault();
    $(".tabs .active").removeClass('active');
    $(this).addClass('active');
    $("#type").val($(this).attr("data-val"));
    clearInitialization();
    showNextPage();
});

$(window).scroll(function(){
    /*if($(this).scrollTop() >= ($('body').height() - $(window).height())){
     showNextPage();
     }*/
    if($(this).scrollTop() >= ($('.income_list').height() - $(window).height())){
        showNextPage();
    }
});


//清除操作
function clearInitialization(){
    pageNo = 0;
    $("#status").val(0);
    $('.income_list').html('');
    $(".tips").html('');
    $('#showmore').hide();
}
function showNextPage(){
    if($("#status").val()==1){
        return;
    }
    pageNo++;
    $.ajax({
        url: basePath+'/member/getwithdrawrecords.htm?pageNo='+pageNo+'&type='+$("#type").val(),
        type: 'GET',
        dataType: 'text',
        async:'false',
        //beforeSend: showLoadingImg,
        error: showFailure,
        success:showResponse
    });

}


function showResponse(responseData) {
    var returnjson = eval("(" +responseData+")");
    var nextpagehtml = '';
    if(returnjson.pb.list !=null && returnjson.pb.list.length >0){
        for(var i=0; i<returnjson.pb.list.length; i++) {
            var trade =  returnjson.pb.list[i];
            nextpagehtml += '<div class="box"><div class="cont">';
            nextpagehtml += '<a href='+basePath+'/member/towtradeinfodetail.html?tradeId='+trade.id +'>';
            if(trade.createTime!=''&&trade.createTime!=null){
                var create = new Date(trade.createTime);
                var createStr = create.format("yyyy-MM-dd hh:mm:ss");
                nextpagehtml += '<div class="left"><p>'+ trade.orderCode +'</p> <p class="light">'+ createStr +' </p></div>';
            }
            var orderstatus='';
            if(trade.orderStatus == '0'){
                orderstatus = "待审核";
            }else if(trade.orderStatus == '1'){
                orderstatus = "已打回";
            }else if(trade.orderStatus == '2'){
                orderstatus = "已通过";
            }else if(trade.orderStatus == '3'){
                orderstatus = "待确认";
            }else if(trade.orderStatus == '4'){
                orderstatus = "已完成";
            }else if(trade.orderStatus == '8'){
                orderstatus = "已取消";
            }
            nextpagehtml += '<div class="right"> <p class="red">'+ orderstatus +'</p> <p class="red">￥'+ trade.orderPrice.toFixed(2) +'</p> </div>';
            nextpagehtml += ' </a></div>';
            if(trade.orderStatus == '0'){
                nextpagehtml += '<div class="btns"> <a href="javascript:;" onclick="showUpdateDia(0,'+ trade.id +')" >取消申请</a> </div>';
            }else if(trade.orderStatus == '3'){
                nextpagehtml += '<div class="btns"> <a href="javascript:;" onclick="showUpdateDia(3,'+ trade.id +')" >确认收款</a> </div>';
            }
            nextpagehtml +=' </div>';
        }
        if(nextpagehtml != null && nextpagehtml != ""){
            $('.income_list').append(nextpagehtml );
            $newElems =$(".income_list .income_item");
            // 渐显新的内容
            $newElems.animate({ opacity: 1 });
            /*if(returnjson.pb.nextPageNo==pageNo){
             clearInterval(iIntervalId);
             $('#showmore').hide();
             }else{
             /!*	 $('#showmore').html('<a class="handle" href="javascript:show()">显示更多结果</a>');
             $('#showmore').show();*!/
             $('#showmore').html(' <img src='+basePath+'/images/loading.gif /><span>加载中……</span>');
             $('#showmore').show();
             }*/
        }
        /*else{
            $('#showmore').html('<a class="handle" href="javascript:show()">已无更多结果</a>');
        }*/

    }else if(pageNo == 1){
        $('.income_list').html('');
        $(".tips").html('<div class="no_tips" id="no"><p>没有交易记录！</p> </div>');
    }else {
        $("#status").val(1);
    }
    /*else{
        $("#status").val(1);
        $('#showmore').html('<a class="handle" href="javascript:show()">已无更多结果</a>');
        $('#showmore').show();
    }*/

}

function showFailure() {
    $('#showmore').html('<font color=red> 获取查询数据出错 </font>');
}


function showUpdateDia(type,tradeInfoId){
    var return_msg;
    var updateDia;
    var cancelDia;
    if('0' == type){
        return_msg = '确定要取消申请么?';
        cancelDia = dialog({
            id: 'deposit-frozen',
            width : 260,
            content : '<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a onclick="updateApply('+ type+','+tradeInfoId+')" id="depositok" class="ok" >确定</a><a href="javascript:;" onclick="closeDialog();" class="cancel" style="margin-left: 4%;">再看看</a></div></div>'
        });
        cancelDia.reset();
        cancelDia.showModal();
    }else if('3' == type){
        return_msg = '确定收款后,冻结的预存款将被扣除,是否确认已收到打款?';
        updateDia = dialog({
            id: 'deposit-frozen',
            width : 260,
            content : '<div style="padding:0 5px"><p class="tc">'+ return_msg +'</p></div><div class="dia-buttons"><a href="javascript:;" onclick="closeDialog();" class="cancel">取消</a><a onclick="updateApply('+ type+','+tradeInfoId+')" id="depositok" class="ok" style="margin-left: 4%;">确定</a></div></div>'
        });
        updateDia.reset();
        updateDia.showModal();
    }


}

function updateApply(type, tradeInfoId){
    $.ajax({
        url:basePath+'/updatetradeinfostatus.htm',
        type:'post',
        data:{'type': type,'tradeInfoId': tradeInfoId},
        success:function(result){
            var return_msg;
            if(result== true){
                if('0' == type){
                    return_msg = '取消申请成功';
                }else if('3' == type){
                    return_msg = '确认收款成功';
                }
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+return_msg +'</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                    clearInitialization();
                    showNextPage();
                    dialog.getCurrent().remove();

                },1000);
            }else{
                if('0' == type){
                    return_msg = '取消申请失败';
                }else if('3' == type){
                    return_msg = '确认收款失败';
                }
                $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>'+return_msg +'</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                    clearInitialization();
                    showNextPage();
                    dialog.getCurrent().remove();
                },1000);
            }
        }
    });
}

function closeDialog(){
    dialog.getCurrent().close();
}