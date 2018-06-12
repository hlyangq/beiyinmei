
function synupdategood(goodsNo){
    $("#goodsNo").val(goodsNo);

}

function esupdate(){
    $.ajax({
        type: "POST",
        url: "synupdategoodsimport.htm?goodsNo="+$("#goodsNo").val(),
        data: {"CSRFToken":$(".token_val").val()},
        dataType: "json",
        success: function(data){
            if(data.status==1){
                $("#esupdateGoodsTips").html("更新成功");

            }else{
                $("#esupdateGoodsTips").html("更新失败");
            }
        }
    });
}

function showesGooods(){
    $("#edGoods .edGoodsTips").html('确定要从E店宝同步商品吗?');
    $('#edGoods').modal('show')
}

function showupdateesGoods(obj){
    $("#esupdateGoodsTips").html("确定要从e店宝更新此商品吗？");
    $("#edupdateGoods").modal('show');
    $("#goodsNo").val(obj);
}

function tongbu(obj){
    $("#edGoods .edGoodsTips").html('正在同步...');
    $.ajax({
        type: "POST",
        url: "syngoodsimport.htm",
        data: {"CSRFToken":$(".token_val").val()},
        dataType: "json",
        success: function(data){
            if(data.status==1){
                $("#edGoods .edGoodsTips").html('同步成功,共同步'+data.message+"条数据");
            }else{
                $("#edGoods .edGoodsTips").html('同步失败');
            }
        }
    });
}