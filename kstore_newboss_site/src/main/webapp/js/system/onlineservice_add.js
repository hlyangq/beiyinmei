$(function(){
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

    /* 为选定的select下拉菜单开启搜索提示 */
    $('select[data-live-search="true"]').select2();
    /* 为选定的select下拉菜单开启搜索提示 END */

    /* 富文本编辑框 */
    $('.summernote').summernote({
        height: 300,
        tabsize: 2,
        lang: 'zh-CN'
    });

    /* 下面是表单里面的填写项提示相关的 */
    $('.zhuantiseokw').popover({
        content : '默认为文章名称(最大字数75)',
        trigger : 'hover'
    });
    $('.zhuantiseodesc').popover({
        content : '默认为文章名称(最大字数255)',
        trigger : 'hover'
    });



    /* 高级搜索 */
    $('.advanced_search').popover({
        html : true,
        title : '高级搜索',
        content : $('.advanced_search_cont').html(),
        trigger : 'click',
        placement : 'bottom'
    }).click(function(){
        $('select[data-live-search="true"]').select2();
    });

    /* 下面是表单里面的日期时间选择相关的 */
    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:00:00',
        weekStart : 1,
        autoclose : true,
        language : 'zh-CN',
        pickerPosition : 'bottom-left',
        todayBtn : true,
        viewSelect : 'hour'
    });
    $('.form_date').datetimepicker({
        format : 'yyyy-mm-dd',
        weekStart : 1,
        autoclose : true,
        language : 'zh-CN',
        pickerPosition : 'bottom-left',
        minView : 2,
        todayBtn : true
    });
    /* 下面是表单里面的日期时间选择相关的 END */


    $(document).on('click','#submitKSTForm',function(){
        var flag = true;
        var kstEffectiveTerminalstr='';
        $('input[name="kstEffectiveTerminal"]:checked').each(function () {
            kstEffectiveTerminalstr+=$(this).val()+",";
        });
        var pcOperation = $("#pcOperation").val();
        var appOperation = $("#appOperation").val();
        var len=kstEffectiveTerminalstr.length;
        var isuseing= $('input[name="isuseing"]:checked').val();
        if (isuseing == '0'){//启用kst
            if(len>0){//已选择生效终端
                $("#addKSTEffectiveTerminal_tips").hide();
                //判断提交数据
                if(kstEffectiveTerminalstr.indexOf('1') !=-1){
                    if(/^<script type="text\/javascript" src="http:\/\/z1-pcok6\.kuaishangkf\.cn\/bs\/ks\.j\?cI=\d+&fI=\d+" charset="utf-8"><\/script>$/.test(pcOperation)){
                        $("#pcOperation_tips").hide();
                        flag = flag && true;
                    }else{
                        updateTips("代码不合规！请重新输入", "pcOperation_tips");
                        $("#pcOperation_tips").show();
                        flag = flag && false;
                    }
                }
                if(!flag) return;
                if(kstEffectiveTerminalstr.indexOf('2')!=-1 || kstEffectiveTerminalstr.indexOf('3')!=-1){
                    if(/^http:\/\/z1-pcok6\.kuaishangkf\.cn\/bs\/im\.htm\?cas=\d+___\d+&fi=\d+&ism=1$/.test(appOperation)){
                        $("#appOperation_tips").hide();
                        flag = flag && true;
                    }else{
                        updateTips("代码不合规！请重新输入", "appOperation_tips");
                        $("#appOperation_tips").show();
                        flag = flag && false;
                    }
                }
                if(!flag) return;

                $("#addKSTEffectiveTerminal_tips").hide();
                if(kstEffectiveTerminalstr.indexOf('1')!=-1){ //生效终端选择了pc
                    //pc生效代码不为空
                    if (pcOperation != "") {
                        $("#addPcOperation_tips").hide();
                        flag = flag && true;
                    }else{//pc生效代码为空
                        updateTips("pc生效代码不能为空", "addPcOperation_tips");
                        $("#addPcOperation_tips").show();
                        flag = flag && false;
                    }
                }
                if(!flag) return;
                if(kstEffectiveTerminalstr.indexOf('2')!=-1 || kstEffectiveTerminalstr.indexOf('3')!=-1){//生效终端选择了app或者移动版
                    if(appOperation != ""){
                        $("#addAppOperation_tips").hide();
                        flag = flag && true;
                    }else{
                        updateTips("app生效代码不能为空", "addAppOperation_tips");
                        $("#addAppOperation_tips").show();
                        flag = flag && false;
                    }
                }
                if(!flag) return;
            }else{//未选择生效终端，报错
                updateTips("必须选择生效终端", "addKSTEffectiveTerminal_tips");
                $("#addKSTEffectiveTerminal_tips").show();
                flag = flag && false;
            }
        }
        if(!flag) return;
        if(flag) {
            if( $(".trueSwitch_KST").val() != '1_Y'){
                if(isuseing == '0' && $(".onlineSwitch_QQ").val()=='Y'){
                    $('#chooseOnlineService').modal('show');
                    $('#chooseMsg').text("如果您选择快商通，则已存在的QQ客服将不再生效！");
                    return;
                }else{
                    $('#chooseOnlineService').modal('hide');
                }
            }
            $("#onlineSaveForKST").submit();
        }
    });

    $('#addIphone').button().click(function(){
        var addTel=$("#serviceTel").clone();
        $("#online_form").html(addTel.show());
        $('#editCustomerServiceForQQ').modal('show');
    });

    $('#addWW').button().click(function() {
        var addWW=$("#serviceWW").clone();
        $("#online_form").html(addWW.show());
    })
});

//编辑在线客服
function editCustomerService(openType){
    if(openType=='QQ'){
        $.ajax({
            type : 'POST',
            url : 'readQQ.htm',
            async : false,
            success : function(data) {
                var html = '<div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close">'
                    +'<span aria-hidden="true">&times;</span></button><h4 class="modal-title" >编辑QQ客服</h4></div>'
                    +'<div class="modal-body" ><form class="form-horizontal" id="onlineSaveForQQ" action="addOnLineService.htm?CSRFToken='
                    +$("#CSRFToken").val()+'" method="post" >'
                    +'<input type="hidden" name="qqSite" class="qqSite" value="'+data.effectiveTerminal+'" /> '
                    +'     <input id="onLineServiceIdQQ" type="hidden" value="'+data.onLineServiceId +'" name="onLineServiceId"> '
                    +'     <div class="form-group"><label class="control-label col-sm-5">启用开关：</label><div class="col-sm-1"></div> '
                    +'     <div class="col-sm-16"><input class="onlineSwitch_QQ" type="hidden"  name="onlineSwitchQQ" value="'+data.onlineIndex+'" /> '
                    +'     <input class="trueSwitch_QQ" type="hidden" value="" /><label class="radio-inline"> '
                    +'     <input name="onlineIndex" id="onlineIndex_Y" type="radio" value="Y" ';
                    if(data.onlineIndex == 'Y' && $('.onlineSwitch_KST').val() == 1){
                        html+= ' checked ';
                    }
                    html += '>启用</input></label><label class="radio-inline"><input name="onlineIndex" id="onlineIndex_N" type="radio" value="N" ';
                        if(data.onlineIndex == "N" || data.onlineIndex == null || data.onlineIndex == ""){
                            html+= ' checked ';
                        }
                        html+=' >禁用 </input></label></div></div><div class="form-group"><label class="control-label col-sm-5">生效终端：</label> '
                            +'<div class="col-sm-1"></div><div class="col-sm-16"><label class="checkbox-inline"><input name="effectiveTerminal"  id="effectiveTerminal1" type="checkbox" value="1"';
                        if(data.effectiveTerminal.indexOf("1")>=0){
                            html+= ' checked ';
                        }
                        html+=' />PC</label><label class="checkbox-inline"><input name="effectiveTerminal"  id="effectiveTerminal2" type="checkbox" value="2"';
                        if(data.effectiveTerminal.indexOf("2")>=0){
                            html+= ' checked ';
                        }
                        html+=' />APP</label><label class="checkbox-inline"><input name="effectiveTerminal"  id="effectiveTerminal3" type="checkbox" value="3"';
                        if(data.effectiveTerminal.indexOf("3")>=0){
                            html+= ' checked ';
                        }
                        html+= ' />移动版</label><div style="height: 5px;"><span generated="true" id="addQQEffectiveTerminal_tips" class="error" data-index="" style="color:#a94442;"></span>'
                               +'</div></div></div><div class="form-group"><label class="control-label col-sm-5">客服标题：</label><div class="col-sm-1"></div>'
                               +'<div class="col-sm-5"><input  type="text" class="form-control qqNumber" style="width: 90px" name="title"  id="title" value="'+data.title+'">'
                               +'<span generated="true" id="addQQTitle_tips" class="error" data-index="" style="color:#a94442;"></span></div></div>'
                               +'<form class="form-horizontal"><div class="form-group"><label class="control-label col-sm-5">客服列表：</label>'
                               +'<div class="col-sm-1"></div><div class="col-sm-4"><button type="button" class="btn btn-primary" id="addQQ" onclick="addOneQQ()">添加客服</button>'
                               +'</div><div class="col-sm-10"><a href="javascript:;" class="kflb help_tips"><i class="icon iconfont">&#xe611;</i></a>'
                               +'<span generated="true" id="addQQCustom_tips" class="error" data-index="" style="color:#a94442;"></span></div></div>'
                               +'<div class="form-group qqService" id="online_form" style="height:150px;overflow-y:auto;">'
                               +'<input type="hidden" id="onLineServiceListCount" value="'+data.itemList.length+'" />'
                               +'<input type="hidden" value="'+data.onLineServiceId+'" name="onLineServiceId">';
                                if(data.itemList.length > 0){
                                    for(var i = 0; i < data.itemList.length; i++){
                                        if(data.itemList[i].contactType == 0){
                                            html+='<input type="hidden" name="itemIndex" value="'+i+'"/>'
                                            +'<div class="form_group tips"  id="form_'+data.itemList[i].onLineServiceItemId+'" name="QQ">'
                                            +'<input type="hidden" class="serviceItems" name="serviceItems" value="'+data.itemList[i].contactNumber+'-'+data.itemList[i].name +'"  />'
                                            +'<label class="control-label col-sm-5"></label><div class="col-sm-1"></div>'
                                            +'<div id="item_'+data.itemList[i].onLineServiceItemId+'">'
                                            +'<input type="hidden" value="'+data.itemList[i].onLineServiceItemId+'" name="onLineServiceItemId" id="onLineServiceItemId">'
                                            +'<input type="hidden" value="'+data.itemList[i].onLineServiceId+'" name="onLineServiceId" id="onLineServiceId">'
                                            +'<label class="control-label col-sm-3">客服账号：</label><div class="col-sm-4">'
                                            +'<input type="text" class="form-control sNumber" name="sNumber" id="sNumber" readonly= "readonly" lang="'+data.itemList[i].onLineServiceItemId+'" value="'+data.itemList[i].contactNumber+'">'
                                            +'</div><label class="control-label col-sm-3">客服昵称：</label><div class="col-sm-4">'
                                            +'<input type="text" class="form-control" name="sName" id="sName" readonly= "readonly" value="'+data.itemList[i].name+'">'
                                            +'</div><div class="control-label col-sm-3"><a onclick="saveQQ(this);" href="javascript:void(0);">编辑</a>&nbsp;&nbsp;'
                                            +'<a onclick="removeItem(this);" href="javascript:void(0);">删除</a></div></br></br><label class="control-label col-sm-8"></label>'
                                            +'<div class="col-sm-1"></div><div style="height: 15px;"><span generated="true" class="error addQQNumerAndName_tips" data-index="" style="color:#a94442;"></span>'
                                            +'</div></div></div>';
                                        }
                                    }
                                }
                                html+='</div><input type="hidden" name="onlineSwitchKST" value="'+$(".onlineSwitch_KST").val()+'" />'
                                    +'<input type="hidden" value="'+$("#onLineServiceIdKST").val()+'" name="shangId">'
                                    +'</form></div><div class="modal-footer"><input type="button" class="btn btn-primary" onclick="saveServiceForQQ();" id="submitQQForm"  value="确认"/>'
                                    +'<input type="button" class="btn btn-default" value="取消" onclick="cacleSubmit(\'QQ\');"/></div></div></div>';
                $("#editCustomerServiceForQQ").html(html);
            }
        });
        $('#editCustomerServiceForQQ').modal('show');
        $('.kflb').popover({
            html : true,
            content : '<p>1.最多添加20条客服信息</p><p>2.客服昵称不超过6个字符</p>',
            trigger : 'hover'
        });
    }else if(openType=='KST'){

        $.ajax({
            type : 'POST',
            url : 'readKst.htm',
            async : false,
            success : function(data) {
                var html ='<div class="modal-dialog"><div class="modal-content"><div class="modal-header">'
                    +'<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
                    +'<span aria-hidden="true">&times;</span></button>'
                    +'<h4 class="modal-title" >编辑快商通客服</h4></div><div class="modal-body">'
                    +'<form class="form-horizontal" id="onlineSaveForKST" action="updateKuaiShang.htm?CSRFToken='
                    +$("#CSRFToken").val()+'" method="post" enctype="multipart/form-data" >'
                    +'<input type="hidden" name="kstSite" class="kstSite" value="'+data.kstEffectiveTerminal+'" />'
                    +'<input id="onLineServiceIdForKST" type="hidden" value="'+data.shangId+'" name="shangId"><div class="form-group">'
                    +'<label class="control-label col-sm-4">启用开关：</label><div class="col-sm-1"></div><div class="col-sm-10">'
                    +'<input class="onlineSwitch_KST" type="hidden" name="onlineSwitchKST" value="'+data.isuseing+'" />'
                    +'<input class="trueSwitch_KST" type="hidden" value="" /><label class="radio-inline">'
                    +'<input name="isuseing" id="isuseing_0" type="radio"  value="0" ';
                if(data.isuseing == "0" && $('.onlineSwitch_QQ').val() == "N"){
                    html+=' checked ';
                }
                html+='/>启用</label><label class="radio-inline">'
                    +'<input name="isuseing" id="isuseing_1" type="radio"  value="1" ';
                if(data.isuseing == "1" || data.isuseing == null || data.isuseing == ""){
                    html+=' checked ';
                }
                html+=' />禁用</label></div></div><div class="form-group"><label class="control-label col-sm-4">生效终端：</label>'
                    +' <div class="col-sm-1"></div><div class="col-sm-10">'
                    +'<label class="checkbox-inline"><input name="kstEffectiveTerminal"  onclick="chooseEffectiveTerminal()" id="kstEffectiveTerminal1" type="checkbox" value="1" ';
                    if(data.kstEffectiveTerminal.indexOf("1")>=0){
                        html+=' checked ';
                    }
                    html+=' />PC</label><label class="checkbox-inline"><input name="kstEffectiveTerminal"  onclick="chooseEffectiveTerminal()" id="kstEffectiveTerminal2" type="checkbox" value="2" ';
                    if(data.kstEffectiveTerminal.indexOf("2")>=0){
                        html+=' checked ';
                    }
                    html+='/>APP</label><label class="checkbox-inline"><input name="kstEffectiveTerminal"  onclick="chooseEffectiveTerminal()" id="kstEffectiveTerminal3" type="checkbox" value="3" ';
                    if(data.kstEffectiveTerminal.indexOf("3")>=0){
                        html+=' checked ';
                    }
                    html+=' />移动版</label></div><span generated="true" id="addKSTEffectiveTerminal_tips" class="error" data-index="" style="color:#a94442;display:inline-block;padding-top:7px;"></span>'
                        +'</div><div class="form-group"><label class="control-label col-sm-4">客户终端下载：</label><div class="col-sm-1"></div>'
                        +'<div class="col-sm-3" style="padding-top: 7px;"><a href="http://www.kuaishang.cn/download.html" target=_blank>客户终端下载</a>'
                        +'</div> <div class="col-sm-2"><a href="javascript:;" class="kfzd help_tips"><i class="icon iconfont">&#xe611;</i></a></div></div><div class="form-group';
                    if(data.kstEffectiveTerminal.indexOf("1")< 0){
                        html+=' none ';
                    }
                    html+=' " id="pc_operation" ><label class="control-label col-sm-4">PC端生效代码：</label><div class="col-sm-1"></div><div class="col-sm-17">'
                    +'<textarea class="ke-textarea form-control" id="pcOperation" name="operation" style="width:408px;height:100px;">'+data.operation+'</textarea>'
                    +'</div><div class="col-sm-1"><a href="javascript:;" class="pcsx help_tips"><i class="icon iconfont">&#xe611;</i></a>'
                    +'<span generated="true" id="addPcOperation_tips" class="error" data-index="" style="color:#a94442;"></span></div></br>'
                    +'<div style="height: 40px;width: 300px"><span generated="true" id="pcOperation_tips" class="error" data-index="" style="color:#a94442;clear:both;padding:10px 0 0 125px;"></span>'
                    +'</div></div><div class="form-group ';
                    if(data.kstEffectiveTerminal.indexOf("2")<0 && data.kstEffectiveTerminal.indexOf("3")<0){
                        html+=' none ';
                    }
                    html+=' " id="app_operation"><label class="control-label col-sm-4">APP_移动端生效代码：</label><div class="col-sm-1"></div><div class="col-sm-17">'
                    +'<textarea class="ke-textarea form-control" id="appOperation" name="appOperation" style="width:408px;height:100px;">'+data.appOperation+'</textarea>'
                    +'</div><div class="col-sm-1"><a href="javascript:;" class="appsx help_tips"><i class="icon iconfont">&#xe611;</i></a>'
                    +'<span generated="true" id="addAppOperation_tips" class="error" data-index="" style="color:#a94442;"></span>'
                    +'</div></br><div style="height: 40px;width: 300px">'
                    +'<span generated="true" id="appOperation_tips" class="error" data-index="" style="color:#a94442;clear:both;padding:10px 0 0 125px;"></span>'
                    +'</div></div><input type="hidden" value="'+$(".onLineServiceIdForQQ").val()+'" name="onLineServiceId">'
                    +'<input type="hidden" name="onlineSwitchQQ" value="'+$(".onlineSwitch_QQ").val()+'" />'
                    +'</form></div><div class="modal-footer"><input type="button" id="submitKSTForm" class="btn btn-primary" value="确认"/>'
                    +'<input type="button" class="btn btn-default" value="取消" onclick="cacleSubmit(\'KST\');"/>'
                    +'</div></div></div>';
                    $("#editCustomerServiceForKST").html(html);
            }
        });
        $('#editCustomerServiceForKST').modal('show');
        $('.kfzd').popover({
            html : true,
            content : '<p>点击下载客服终端，快速解决用户疑问</p>',
            trigger : 'hover'
        });
        $('.pcsx').popover({
            html : true,
            content : '<p>生效终端选择PC端，需要填写PC端生效代码</p>',
            trigger : 'hover'
        });
        $('.appsx').popover({
            html : true,
            content : '<p>生效终端选择APP或者移动版，需要填写APP或者移动版生效代码</p>',
            trigger : 'hover'
        });
    }
}


function cacleBtn(){
    $("#serviceQQ").remove();
}

function cacleSubmit(cacleType) {
    if(cacleType=='QQ'){
        $('#editCustomerServiceForQQ').modal('hide');
    }else if(cacleType=='KST'){
        $('#editCustomerServiceForKST').modal('hide');
    }
}

var del_item;
//验证添加QQ
function checkQQ(){

    var flag = true;

    var qqNo = $("#addQQNo").val();
    var qqName = $("#addQQName").val();

    if(qqNo != ""){
        if (/^[1-9]\d{4,9}$/.test(qqNo)) {
            if (checkSpecSymb("addQQNo", "addQQ_tips")) {
                //判断是否有重复
                var qqnos = new Array();
                qqnos = $(".sNumber");
                for (var i = 0; i < qqnos.length; i++) {
                    if (qqNo == qqnos[i].value) {
                        updateTips("QQ号码有重复！", "addQQ_tips");
                        flag = false && flag;
                        $("#addQQ_tips").show();
                        break;
                    }else{
                        $("#addQQ_tips").hide();
                    }
                }
            }else {
                updateTips("QQ号码包含特殊字符！", "addQQ_tips");
                flag = false && flag;
                $("#addQQ_tips").show();
            }
        }else{
            updateTips("客服账号请输入5-10位数字", "addQQ_tips");
            flag = false && flag;
            $("#addQQ_tips").show();
        }
    }else{
        updateTips("客服账号不能为空！","addQQ_tips");
        flag = false && flag;
        $("#addQQ_tips").show();
    }
    if(!flag) return flag;
    if(qqName != "") {
        //判断qq格式、名称是否有特殊字符
        if (/[\u4e00-\u9fa5a-zA-Z\d]{1,6}/.test(qqName)) {
            if (!checkSpecSymb("addQQName", "addQQ_tips")) {
                updateTips("QQ号码或客服名称包含特殊字符！", "addQQ_tips");
                flag = false && flag;
                $("#addQQ_tips").show();
            }
        } else {
            updateTips("QQ昵称应为小于6位的字符", "addQQ_tips");
            flag = false && flag;
            $("#addQQ_tips").show();
        }
    }else{
        updateTips("昵称不能为空！","addQQ_tips");
        flag = false && flag;
        $("#addQQ_tips").show();
    }
    return flag;
}

//更新错误提示框的状态
function updateTips( t, tip) {
    $("#"+tip) .text( t ) .addClass( "ui-state-highlight" );
}

function updateTipsByClass( t, tip) {
    $(tip).parents(".tips").find(".addQQNumerAndName_tips").text(t).addClass( "ui-state-highlight" );
}

/* 删除操作 */
function removeItem(item) {
    $("#delete_one").modal('show');
    del_item=item;
}
function deleteItem() {
    //var parentNode=item.parentNode.parentNode.parentNode;
    $(del_item).parents(".form_group").remove();
    $("#delete_one").modal('hide');
}
function deleteChoose(){
    var onlineIndex = $('input[name="onlineIndex"]:checked').val();
    var isuseing = $('input[name="isuseing"]:checked').val();
    var onlineSwitch_QQ = $(".onlineSwitch_QQ").val();
    var onlineSwitch_KST = $(".onlineSwitch_KST").val();
    if(onlineIndex != onlineSwitch_QQ){
        $("#onlineIndex_N").prop("checked","checked");
    }
    if(isuseing != onlineSwitch_KST){
        $("#isuseing_1").prop("checked",true);
    }
}
function changeChoose(){
    var onlineIndex = $('input[name="onlineIndex"]:checked').val();
    var isuseing = $('input[name="isuseing"]:checked').val();
    var onlineSwitch_QQ = $(".onlineSwitch_QQ").val();
    var onlineSwitch_KST = $(".onlineSwitch_KST").val();
    if(onlineIndex == "Y" && onlineSwitch_KST == '0'){
        $("#onlineIndex_Y").prop("checked","checked");
        $(".trueSwitch_QQ").val("0_N");
        $(".trueSwitch_KST").val("0_N");
        $('#chooseOnlineService').modal('hide');
        saveServiceForQQ();
    }else if(isuseing == "0" && onlineSwitch_QQ == 'Y'){
        $("#onlineIndex_N").prop("checked","checked");
        $(".trueSwitch_KST").val("1_Y");
        $(".trueSwitch_QQ").val("1_Y");
        $('#chooseOnlineService').modal('hide');
        $('#submitKSTForm').click();
    }else{
        $(".trueSwitch_QQ").val("1_N");
        $(".trueSwitch_KST").val("1_N");
    }
}

//验证特殊字符，将调试显示到页面中
function checkSpecSymb(inputobj,Tipobj){
    var regexp=new RegExp("[''\\[\\]<>?\\\\!]");
    if (regexp.test( $("#"+inputobj).val() ) ) {
        $("#"+inputobj).addClass( "ui-state-error");
        $("#"+Tipobj).text("输入的内容包含特殊字符!").addClass( "ui-state-highlight");
        return false;
    }else {
        $("#"+inputobj).removeClass( "ui-state-error");
        $("#"+Tipobj).text("").removeClass( "ui-state-highlight");
        return true;
    }
}

function saveServiceForQQ(){

    var effectiveTerminalstr='';
    $('input[name="effectiveTerminal"]:checked').each(function () {
        effectiveTerminalstr+=$(this).val()+",";
    });
    var len=effectiveTerminalstr.length;
    if($(".saveFlag").val() == '1' || $(".updateFlag").val() == '1'){
        $("a:contains('保存')").each(function(){
            $(this).parent().nextAll().find(".addQQNumerAndName_tips").text("请先保存客服数据再提交！");
        })
        return;
    }
    var flag = true;
    var title = $("#title").val().trim();
    var count=0;
    $('div[name="QQ"]').each(function(){
        count++;
    });
    var onlineIndex= $('input[name="onlineIndex"]:checked').val();
    if(onlineIndex=='Y') {
        if(len<=0){
            updateTips("启用时请选择生效终端！", "addQQEffectiveTerminal_tips");
            flag = false && flag;
            $("#addQQEffectiveTerminal_tips").show();
        }
        if(title!=""){
            if (!/^[a-zA-Z0-9\u4e00-\u9fa5]*$/.test(title)) {
                updateTips("标题不支持非法字符!", "addQQTitle_tips");
                flag = false && flag;
                $("#addQQTitle_tips").show();
            }else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]{1,4}$/.test(title)){
                updateTips("标题不超过4个字符!", "addQQTitle_tips");
                flag = false && flag;
                $("#addQQTitle_tips").show();
            }else{
                if(count!=0){
                    $("#addQQCustom_tips").hide();
                    flag = true && flag;
                }else{
                    updateTips("启用该服务时客服至少存在一个！", "addQQCustom_tips");
                    flag = false && flag;
                    $("#addQQCustom_tips").show();
                    return;
                }
            }
        }else{
            updateTips("标题不能为空！", "addQQTitle_tips");
            flag = false && flag;
            $("#addQQTitle_tips").show();
            return;
        }

    }else{
        if(title == ""){
            $("#title").val(" ");
        }
        $("#addQQCustom_tips").hide();
        flag = true && flag;
    }
    if(flag){
        if( $(".trueSwitch_QQ").val() != '0_N'){
            if ($('input:radio[name="onlineIndex"]:checked').val() == 'Y' && $(".onlineSwitch_KST").val() == '0') {
                $('#chooseOnlineService').modal('show');
                $('#chooseMsg').text("如果您选择QQ客服，则已存在的快商通将不再生效！");
                return;
            } else {
                $('#chooseOnlineService').modal('hide');
            }
        }
        $("#onlineSaveForQQ").submit();
    }else{
        return;
    }
}
