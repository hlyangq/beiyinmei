/**
 * 模板廣告列表js
 * Created by NingPai-liangck on 2015/5/19.
 */

//根据传过来的对象验证是否为数字（已經移到common js中）
/*function checkNumAndDialog(inputobj){
    if (isNaN($("#"+inputobj).val() )) {
        $("#"+inputobj).next().html("请填写正确格式的数字");
        $("#"+inputobj).focus();
        return false;
    }
    else {
        $("#"+inputobj).next().html("");
        return true;
    }
}*/

/*验证表单*/
function validateForm(){
    var flag = true;
    //验证广告名称
    if($("#up_adverName").val()==""){
        $("#up_adverName").next().addClass("error");
        $("#up_adverName").next().html("请填写广告名称");
        $("#up_adverName").focus();
        flag = flag && false;
    }else{
        $("#up_adverName").next().html("");
        flag = flag && true;
    }
    //验证广告图片
    if($("#up_imgSrc").val()==""){
        if($("#imageSrc").val()==""){
            $("#up_imgSrc").next().addClass("error");
            $("#up_imgSrc").next().html( "请选择广告图片" );
            $("#up_imgSrc").focus();
            flag = false  && flag;
        }else{
            $("#up_imgSrc").next().html("");
            flag = true && flag;
        }
    }else{
        $("#up_imgSrc").next().html("");
        flag = true && flag;
    }
    //验证排序
    if($("#adverSort").val()==""){
        $("#adverSort").next().addClass("error");
        $("#adverSort").next().html( "请填写正确格式的数字" );
        $("#adverSort").focus();
        flag = flag && false;
    }else{
        flag = flag && checkNumAndDialog("adverSort");
    }
    if($("#up_des").val().length > 200){
        $("#up_des").next().addClass("error");
        $("#up_des").next().html("广告描素不能超过200字！");
        $("#up_des").focus();
        flag = flag && false;
    }else{
        $("#up_des").next().html("");
        flag = flag && true;
    }
    return flag;
}

function changeFile(){
    var imgsrc = $("#up_imgSrc").val();
    $("#imageSrc").val(imgsrc);
    $("#adverPath").hide();
}

/*填充form表单数据*/
function fillFormData(adverId){
    /*請求地址*/
    var queryUrl=$("#basePath").val()+"/getChannelAdverById.htm";
    /*获取数据*/
    $.getJSON(queryUrl,{channelAdverId:adverId},function(data){
        /*填充表单数据*/
        if(data!=null){
            $("#up_barId").val(data.channelAdverId);
            $("#up_adverName").val(data.adverName);
            $("#up_temp2").val(data.temp2);
            $("#imageSrc").val(data.adverPath);
            $("#adverPath").attr("src",data.adverPath).show();
            $("#up_adverHref").val(data.adverHref);
            $("#adverSort").val(data.adverSort);
            $("input[name='userStatus'][value='"+data.userStatus+"']").attr("checked",true);
            $("#up_des").val(data.des);
        }
    });
}


/*重置表單*/
function resetForm(){
    document.getElementById("adverInfoForm").reset();
    $("#up_adverName").next().html("");
    $("#up_imgSrc").next().html( "" );
    $("#adverSort").next().html( "" );
}

$(function(){

    /*添加按鈕*/
    $("#createAdertBtn").click(function(){
        resetForm();
        $(".advet-info-title").html("添加广告");
        $("#adverPath").hide();
        $("#adverInfoForm").attr("action",$("#basePath").val()+"/createThirdTempAdver.htm");
    });

    /*修改按钮*/
    $(".modify-btn").click(function(){
        resetForm();
        $(".advet-info-title").html("修改广告");
        $("#adverInfoForm").attr("action",$("#basePath").val()+"/updateThirdTempAdver.htm");
        fillFormData($(this).attr("data-key"));
    });

    /*保存按钮*/
    var num=0;
    $("#save").click(function(){
        if(validateForm()&&num==0){
            num+=1;
            $("#adverInfoForm").submit();
        }
    });

    /*刪除按鈕*/
    $(".delete-btn").click(function(){
        /*设置确定按钮的样式，用于事件控制*/
        $("#tip-submit-btn").removeClass("muilty-delete").addClass("single-delete");
        $("#deleteKey").val($(this).attr("data-key"));
    });

    /*单个删除*/
    /*$(".single-delete").on("click",function(){
        alert("hello");
        //$("#singleDeleteForm").attr("action",$("#basePath").val()+"/deleteThirdTempAdver.htm").submit();
    });*/
    $("div.modal-footer").on("click","button.single-delete",function(){
        $("#singleDeleteForm").attr("action",$("#basePath").val()+"/deleteThirdTempAdver.htm").submit();
    });

    /*批量删除按钮*/
    $("#muilty-delete-btn").click(function(){
        if(checkSelect("channelAdverId")){//检查是否选中了行记录
            /*设置确定按钮的样式，用于事件控制*/
            $("#tip-submit-btn").removeClass("single-delete").addClass("muilty-delete");
            $("#delete-tip").modal('show');
        }else{//如果未选中,弹出提示框
            $("#select-tip").modal('show');
        }
    });
    /*批量删除*/
    $("div.modal-footer").on("click","button.muilty-delete",function(){
        $("#muilt-delete-form").attr("action",$("#basePath").val()+"/deleteThirdTempAdver.htm").submit();
    });
});

//修改按钮
function updateInfo(data){
    //location.href="showThirdTempAdver.htm?channelAdverId="+data+"&tempId="+$("#tempId").val()+"&atId="+$("#atId").val();
}
