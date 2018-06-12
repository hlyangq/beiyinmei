function showesGooodcates(){
    $("#edGoods .edGoodsTips").html('确定要从E店宝同步商品分类吗?');
    $('#edGoods').modal('show')
}

function tongbu(obj){
    $("#edGoods .edGoodsTips").html('正在同步...');
    $.ajax({
        type: "POST",
        url: "sysgoodscategory.htm",
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