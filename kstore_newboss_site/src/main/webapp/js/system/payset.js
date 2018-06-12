$(function(){
	    /* 富文本编辑框 */
	    $('.summernote').summernote({
	        height: 300,
	        tabsize: 2,
	        lang: 'zh-CN',
	        onImageUpload: function(files, editor, $editable) {
	            sendFile(files,editor,$editable);
	        }
	    });
	
    /* 表单项的值点击后转换为可编辑状态 */
    $('.form_value').click(function(){
        var formItem = $(this);
        if(!$('.form_btns').is(':visible')) {
            formItem.parent().addClass('form_open');
            $('.form_btns').show();
            $('.form_btns').css({
                'left': formItem.parent().position().left + (formItem.next().find(".form-control").width()==null?formItem.next().width():formItem.next().find(".form-control").width())+65 + 'px',
                'top': formItem.parent().position().top - 30 + 'px'
            });
            $('.form_sure,.form_cancel').click(function () {
                $('.form_btns').hide();
                formItem.parent().removeClass('form_open');
            });
        }
    });

    $("#editForm1").validate();
    $("#editForm2").validate();
    $("#editForm3").validate();
});
/**
 * 修改启用状态
 * @param payId 支付接口id
 */
function changeUserdStatus(payId){
    var CSRFToken = $("#CSRFToken").val();
    location.href = "updateUserdStatusForPay.htm?CSRFToken="+CSRFToken+"&payId="+payId;
}
/**
 * 修改默认状态
 * @param payId 支付接口id
 */
function changeDefault(payId){
    var CSRFToken = $("#CSRFToken").val();
    location.href = "changeDefaultForPay.htm?CSRFToken="+CSRFToken+"&payId="+payId;
}
/**
 * 弹框显示支付接口设置
 * @param payType 支付类型
 * @param payId 支付接口id
 */
function showEditForm(payType,payId) {
    if(payType == 5 || payType == 6){
        $("#payId5").val(payId);
        $.ajax({
            type: "POST",
            url: "findpayone.htm?CSRFToken=" + $("#CSRFToken").val(),
            data: "payid=" + payId,
            success: function (data) {
                //填充值
                $("#up_payid5").val(data.payId);
                // $("#up_payname5").val(data.payName);
                // $("#up_payname5").attr("readonly",true)//将input元素设置为readonly
                if (data.isOpen == 1) {
                    $("#open53").click();
                    if (data.payDefault == 1) {
                        $("#open51").click();
                    } else {
                        $("#open52").click();
                    }
                } else {
                    $("#open54").click();
                    $("#defaultDiv").hide();
                }
                $("#editModal5").modal("show");
            }
        });
    }else {
        $("#payId" + payType).val(payId);
        $.ajax({
            type: "POST",
            url: "findpayone.htm?CSRFToken=" + $("#CSRFToken").val(),
            data: "payid=" + payId,
            success: function (data) {
                //填充值
                $("#up_payid" + payType).val(data.payId);
                // $("#up_payname" + payType).val(data.payName).attr("readonly",true);
                $("#up_apikey" + payType).val(data.apiKey);
                $("#up_secrect" + payType).val(data.secretKey);
                $("#up_account" + payType).val(data.payAccount);
                $("#up_payurl" + payType).val(data.payUrl);
                $("#up_backurl" + payType).val(data.backUrl);
                $("#up_comment" + payType).val(data.payComment);

                $("#up_partner" + payType).val(data.partner);
                $("#up_partnerKey" + payType).val(data.partnerKey);

                // $("#preview_pic" + payType).attr("src", data.payImage);
                $("#payHelp").html(data.payHelp);
                $("#payHelp").code(data.payHelp);

                if (data.isOpen == 1) {
                    $("#open" + payType + "3").click();
                    if (data.payDefault == 1) {
                        $("#open" + payType + "1").click();
                    } else {
                        $("#open" + payType + "2").click();
                    }
                } else {
                    $("#open" + payType + "4").click();
                    $("#defaultDiv" + payType).hide();
                }
                $("#editModal" + payType).modal("show");
            }
        });
    }
}
/**
 * 当启用该支付方式时，显示是否设置默认
 * @param payType 支付类型
 * @param isAble 1 启用， 0 不启用
 */
function showDefultRadio(payType,isAble){
    if(payType == 0){
        if(isAble == 0){
            $("#defaultDiv").hide();
        }else {
            $("#defaultDiv").show();
        }
    }else{
        if(isAble == 0){
            $("#defaultDiv"+payType).hide();
        }else {
            $("#defaultDiv"+payType).show();
        }
    }
}
/**
 * 改变启用状态
 * @param payId 支付方式id
 */
function submitEditForm(payType) {
    // $("#editForm"+payType).submit();
    var formName;
    var CSRFToken = $("#CSRFToken").val();
    if(payType == 0){
        formName = "editForm5";
    }else{
        formName = "editForm" + payType;
    }
    var formData = new FormData($("#"+formName)[0]);
    $.ajax({
        type:"POST",
        data:formData,
        url:"updatepay.htm?CSRFToken="+CSRFToken,
        processData: false,
        cache: false,
        contentType: false,
        success:function (data) {
            showTipAlert(data['msg']);
            $('#modalDialog').on('hidden.bs.modal', function () {
                window.location.reload();
            })
        }
    });
}

function editpayhelp(payType){
	var payId =  $("#payId"+payType).val();
	$("#helpPayId").val(payId);
	$('#rightinfo').modal('show');
}

function savePayHelp(textid,obj){
	var CSRFToken = $("#CSRFToken").val();
	var sHtml = $("#"+textid).code();
    var arg = {};
    arg[textid] = sHtml;
    arg['payId'] =$("#helpPayId").val();
    arg['CSRFToken']=CSRFToken;
    $.ajax({
        url:'updatepayhelp.htm',
        type:'POST',
        data:arg,
        success:function(result) {
            $(obj).next().click();
        }
    });
}
