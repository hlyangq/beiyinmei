/**
 * Created by chenpeng on 2016/10/8.
 */
var pageNo=1;
var basePath = $("#basePath").val();
var iIntervalId = null;

$(".tabs a").on('touchstart mousedown',function(e){
    e.preventDefault();
    $(".tabs .active").removeClass('active');
    $(this).addClass('active');
    $("#type").val($(this).attr("data-val"));
    clearInitialization();
    showNextPage();
});

//清除操作
function clearInitialization(){
    pageNo = 0;
    $("#status").val(0);
    $('.income_list').html('');
    $(".tips").html('');
    $('#showmore').hide();
}
$(window).scroll(function(){
    /*if($(this).scrollTop() >= ($('body').height() - $(window).height())){
        showNextPage();
    }*/
    if($(this).scrollTop() >= ($('.income_list').height() - $(window).height())){
        showNextPage();
    }
});

function showNextPage(){
    if($("#status").val()==1){
        return;
    }
    pageNo++;
    $.ajax({
        url: basePath+'/customer/getCustomerTradeInfo.html?pageNo='+pageNo+'&type='+$("#type").val(),
        type: 'GET',
        dataType: 'text',
        async:'false',
        //beforeSend: showLoadingImg,
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

function showResponse(responseData) {
    var returnjson = eval("(" +responseData+")");
    var nextpagehtml = '';
    if(returnjson.pb.list !=null && returnjson.pb.list.length >0){
        for(var i=0; i<returnjson.pb.list.length; i++) {
            var trade =  returnjson.pb.list[i];
            nextpagehtml += '<div class="income_item"><dl>';
            if(trade.orderType == '0'){
                nextpagehtml += '<dt><span>在线充值 </span></dt>';
                nextpagehtml += '<dd><span class="green">+'
                nextpagehtml += ''+ trade.orderPrice.toFixed(2)+''
                nextpagehtml +=  '</span></dd>';
            }else if(trade.orderType == '1'){
                nextpagehtml += '<dt><span>订单退款 </span></dt>';
                nextpagehtml += '<dd><span class="green">+'
                nextpagehtml += ''+ trade.orderPrice.toFixed(2)+''
                nextpagehtml +=  '</span></dd>';

            }else if(trade.orderType == '2'){
                nextpagehtml += '<dt><span>线下提现 </span></dt>';
                nextpagehtml += '<dd><span class="red">-'
                nextpagehtml += ''+ trade.orderPrice.toFixed(2)+''
                nextpagehtml +=  '</span></dd>';

            }else if(trade.orderType == '3'){
                nextpagehtml += '<dt><span>订单消费 </span></dt>';
                nextpagehtml += '<dd><span class="red">-'
                nextpagehtml += ''+ trade.orderPrice.toFixed(2)+''
                nextpagehtml +=  '</span></dd>';

            }

            nextpagehtml += '</dl><dl> <dt><span class="light">';
            if(trade.createTime!=''&&trade.createTime!=null){
                var create = new Date(trade.createTime);
                var createStr = create.format("yyyy-MM-dd hh:mm:ss");
                nextpagehtml +=''+createStr+'';
            }
            nextpagehtml += '</span></dt><dd><span class="light">';
            if(trade.orderStatus != null){
                if(trade.orderStatus == '0'){
                    nextpagehtml += '待审核';
                }else if(trade.orderStatus == '1'){
                    nextpagehtml += '打回';
                }else if(trade.orderStatus == '2'){
                    nextpagehtml += '已通过';
                }else if(trade.orderStatus == '3'){
                    nextpagehtml += '确认';
                }else if(trade.orderStatus == '4'){
                    nextpagehtml += '已完成';
                }else if(trade.orderStatus == '5'){
                    nextpagehtml += '未支付';
                }else if(trade.orderStatus == '6'){
                    nextpagehtml += '充值成功';
                }else if(trade.orderStatus == '7'){
                    nextpagehtml += '充值失败';
                }else if(trade.orderStatus == '8'){
                    nextpagehtml += '已取消';
                }
            }else if(trade.orderType == '3'){
                nextpagehtml += '已完成';
            }
            nextpagehtml += '</span></dd></dl></div>';

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
        }else{
            $('#showmore').html('<a class="handle" href="javascript:show()">已无更多结果</a>');
        }

    }else if(pageNo == 1){
        $('.income_list').html('');
        $(".tips").html('<div class="no_tips" id="no"><p>没有交易记录！</p> </div>');
    }else{
        $("#status").val(1);
        $('#showmore').html('<a class="handle" href="javascript:show()">已无更多结果</a>');
        $('#showmore').show();
    }

}