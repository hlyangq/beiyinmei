var CSRFToken = $("#CSRFToken").val();
$(function(){
    var formItem,oldValue,inputId,attrType;
    /* 表单项的值点击后转换为可编辑状态 */
    $('.form_value').click(function(){
        if(formItem!=undefined) {
            $('.form_cancel').click();
        }
        formItem = $(this);
        oldValue = $(this).text();
        inputId = $(this).attr("attr_id");
        attrType = $(this).attr("attr_type");
        if(!$('.form_btns').is(':visible')) {
            formItem.parent().addClass('form_open');
            $('.form_btns').show();
            $('.form_btns').css({
                'left': formItem.parent().position().left + (formItem.next().find(".form-control").width()==null?formItem.next().width():formItem.next().find(".form-control").width())+65 + 'px',
                'top': formItem.parent().position().top - 30 + 'px'
            });
            //给输入控件加上验证的class
            $("#"+inputId).addClass($("#"+inputId).attr("clazz"));
        }
    });
    $('.form_cancel').click(function () {
        if("radio"==attrType) {
            if(oldValue.trim()=='否') {
                $("input[name="+inputId+"]:eq(1)").click();
            } else {
                $("input[name="+inputId+"]:eq(0)").click();
            }
        } else {
            $("#"+inputId).val(oldValue);
        }
        $('.form_btns').hide();
        formItem.parent().removeClass('form_open');
        //将输入框去掉验证
        $("#"+inputId).removeClass($("#"+inputId).attr("clazz"));
    });
    $('.form_sure').click(function () {
        if(!$("#editEmailServerForm").valid()) return;
        var serverid = $("#serverid").val();
        var newValue = '';
        var informId = '';
        if("radio"==attrType) {
            newValue =$('input[name="'+inputId+'"]:checked').val();
        } else {
            newValue = $("#"+inputId).val();
        }
        if(inputId == 'bindInfo' || inputId == 'validateInfo' || inputId == 'findInfo'){
            informId = $("#"+inputId).prev().val();
            inputId = 'informText';
        }
        var url = 'updatemail.htm?'+inputId+'='+newValue+"&serverid="+serverid+"&CSRFToken="+CSRFToken;
        if(informId != ''){
            url += "&informId="+informId;
        }
        $.ajax({
            url: url,
            success:function(data) {
                if("radio"==attrType) {
                    $(formItem).html(newValue=='0'?'<span class="label label-default">否</span>':'<span class="label label-success">是</span>');
                    oldValue = newValue=='0'?'否':'是';
                } else {
                    if(formItem.attr("attr_id")=='smtppass'){
                        $(formItem).text("********");
                    }
                    else{
                        $(formItem).text(newValue);
                    }

                    oldValue = newValue;
                }
            }
        });
        $('.form_btns').hide();
        formItem.parent().removeClass('form_open');
    });

    $('.validateInfoTips').popover({
        content : '验证邮箱的邮件内容，其中必须包括“-name”(会员名称)和“-url”(链接)',
        trigger : 'hover'
    });
    $('.findInfoTips').popover({
        content : '找回密码的邮件内容，其中必须包括“-name”(会员名称)和“-url”(链接)',
        trigger : 'hover'
    });
    $('.bindInfoTips').popover({
        content : '绑定邮箱的邮件内容，其中必须包括“-name”(会员名称)和“-url”(链接)',
        trigger : 'hover'
    });
});