$(function() {
    $("#statusFlag").val($(".flagli .active").find("a").attr("data-type"));
    //切换tab页
    $('#charge_tabs a').click(function() {
        $that = $(this);
        $that.parent().addClass('active');
        $that.parent().siblings().removeClass('active');

        $("#statusFlag").val($(".flagli .active").find("a").attr("data-type"));
        $("#searchForm").submit();
    });
    //上传图片前预览
    $("#payImg").uploadPreview({ Img: "ImgPr", Width: 120, Height: 120 });

    /**
     * 还可输入XX个字
     */
    $("#rejectionTitle").keyup(function (element) {
        $(".textarea_tips").html("您还可以输入<b>"+(200 - $(this).val().length)+"</b>字");
    });
    $("#payRemark").keyup(function (element) {
        $(".textarea_tips").html("您还可以输入<b>"+(200 - $(this).val().length)+"</b>字");
    });
    $("#sendBackTxt").keyup(function (element) {
        $(".textarea_tips").html("您还可以输入<b>"+(200 - $(this).val().length)+"</b>字");
    });
    $("#payBillNum").blur(function (element) {
        var payBillNum = $("#payBillNum").val();
        if(payBillNum == null || payBillNum == ""){
            $("#payBillNumTip").text("付款单号不能为空");
            flag = false;
        }else{
            var partern = /^[0-9a-zA-Z]{6,30}$/;
            if(partern.exec(payBillNum)) {
                $("#payBillNumTip").text("");
            }else {
                $("#payBillNumTip").text("请输入正确的付款单号");
                flag = false;
            }
        }
    });
});

/**
 * 弹出批量操作选择模态层
 * @param statusType
 */
function showBatchUpdateModal(statusType) {
    var withdrawIds = [];
    $("input[name=withdrawId]:checked").each(function () {
        withdrawIds.push($(this).val());
    });
    if (withdrawIds.length > 0) {
        $("#updateStatus").val(statusType);
        if(statusType == 1){//打回
            $("#batchTip").find("h4").text("批量打回");
            $("#batchTitle").find("p").text("您已选择"+withdrawIds.length+"条提现单，请填写打回原因");
            $("#sendBackDiv").show();
        }else{//通过
            $("#batchTip").find("h4").text("批量通过");
            $("#batchTitle").find("p").text("您已选择"+withdrawIds.length+"条提现单，请确认是否审核通过");
            $("#sendBackDiv").hide();
        }
        $('#batchUpdate').modal('show');
    } else {
        showTipAlert("请至少选择一笔单据进行操作!");
    }
}

/**
 * 批量通过或批量打回
 */
function batchUpdateStatus() {
    var withdrawIds = [];
    $("input[name=withdrawId]:checked").each(function () {
        withdrawIds.push($(this).val());
    });
    if (withdrawIds.length > 0) {
        var statusType = $("#updateStatus").val();
        var sendBack = "审核通过";
        if(statusType ==1){//打回
            if(checkReject("sendBackTxt","sendBackTip")==true){
                    //打回原因
                    sendBack = $("#sendBackTxt").val();
                    batchUpdateChargeWithdraw(statusType,withdrawIds,sendBack);
            }else{
                return false;
            }
        }else{//通过
            batchUpdateChargeWithdraw(statusType,withdrawIds,sendBack);
        }
    } else {
        showTipAlert("请至少选择一笔单据进行操作!");
    }
}
/**
 * 批量修改提现单状态
 * @param statusType
 * @param withdrawIds
 */
function batchUpdateChargeWithdraw(statusType,withdrawIds,sendBack) {
    var token = $("#CSRFToken").val();
    var result = "操作成功！";
    $.ajax({
        type:'post',
        url:'batchUpdateChargeWithdrawStatus.htm?CSRFToken='+token,
        data:{"orderStatus":statusType,"withdrawIds":withdrawIds,"sendBack":sendBack},
        cache:false,
        dataType:'json',
        success:function(data){
            if(data == 0){
                result = "操作失败！";
            }
            showTipAlert(result);
            $('#modalDialog').on('hidden.bs.modal', function () {
                window.location.reload();
            })
        }
    });
}
/**
 * 查看提现单信息
 * @param withdrawId
 */
function viewChargeWithdrawInfo(withdrawId) {
    var token = $("#CSRFToken").val();
    var orderStatus;
    $.ajax({
        type:'post',
        url:'selectChargeWithdrawById.htm?CSRFToken='+token,
        data:"withdrawId=" + withdrawId,
        cache:false,
        success:function(data){
            $("#tradeInfoId").val(data.tradeInfoId);
            $("#orderCode").text(data.orderCode);
            $("#createTime").text(data.createTime == null?"无":timeObject(data.createTime));
            $("#customerUserName").text(data.customerUsername);
            $("#bankName").text(data.bankName == null?"无":(data.bankName).substring(0,17));
            $("#bankNo").text(data.bankNo == null?"无":(data.bankNo));
            $("#accountName").text(data.accountName == null?"无":(data.accountName).substring(0,17));
            $("#contactMobile").text(data.contactMobile == null?"无":data.contactMobile);
            $("#amount").text(data.amount == null?"¥0.00":"¥"+parseFloat(data.amount).toFixed(2));
            $("#remark").html(data.remark == null || data.remark ==""?"无":data.remark);
            //$("#applyDiv").find("span").text(data.createTime == null?"":timeObject(data.createTime));

            //待审核
            orderStatus = data.orderStatus;
            if(orderStatus == 0){
                $("#agreeDiv").show();
                $("#agreeDiv").find("h4").text("通过");
                $("#agreeDiv").find("span").text("");
                $("#agreeDiv").removeClass('active');

                $("#preConfirmDiv").show();
                $("#preConfirmDiv").find("span").text("");
                $("#preConfirmDiv").removeClass('active');

                $("#completeDiv").show();
                $("#completeDiv").removeClass('active');
                $("#completeDiv").find("h4").text("已完成");
                $("#completeDiv").find("span").text("");

                $("#rejectLi").hide();
                $("#payTxtDiv").hide();

                $("#noPic").hide();
                $("#showPic").hide();

                $("#agreeBtn").show();
                $("#sendBack").show();
                $("#payMoney").hide();
                $("#submitBtn").hide();
                $("#paySubmitBtn").hide();
                $("#showPayDiv").hide();
                $("#singleRejection").hide();
            }else if(orderStatus == 1){//打回
                $("#agreeDiv").show();
                $("#agreeDiv").addClass('active');
                $("#agreeDiv").find("h4").text("打回");
                // $("#agreeDiv").find("span").text(data.updateTime == null?"":timeObject(data.updateTime));

                $("#preConfirmDiv").show();
                $("#preConfirmDiv").find("span").text("");
                $("#preConfirmDiv").removeClass('active');
                $("#completeDiv").show();

                $("#completeDiv").removeClass('active');
                $("#completeDiv").find("span").text("");
                $("#completeDiv").find("h4").text("已完成");

                $("#singleRejection").hide();
                $("#noPic").hide();
                $("#showPic").hide();

                $("#rejectLi").show();
                $("#reviewedRemark").text(data.reviewedRemark == null?"无":data.reviewedRemark);
                $("#payTxtDiv").hide();

                $("#paySubmitBtn").hide();
                $("#showPayDiv").hide();
                $("#agreeBtn").hide();
                $("#sendBack").hide();
                $("#payMoney").hide();
                $("#submitBtn").hide();
            }else if(orderStatus == 2){//通过
                $("#agreeDiv").show();
                $("#agreeDiv").addClass('active');
                $("#agreeDiv").find("h4").text("通过");
                // $("#agreeDiv").find("span").text(data.updateTime == null?"":timeObject(data.updateTime));

                $("#preConfirmDiv").show();
                $("#preConfirmDiv").removeClass('active');
                $("#preConfirmDiv").find("span").text("");

                $("#completeDiv").show();
                $("#completeDiv").removeClass('active');
                $("#completeDiv").find("span").text("");
                $("#completeDiv").find("h4").text("已完成");

                $("#singleRejection").hide();

                $("#noPic").hide();
                $("#showPic").hide();

                $("#rejectLi").hide();
                $("#payTxtDiv").hide();
                $("#paySubmitBtn").hide();
                $("#showPayDiv").hide();
                $("#agreeBtn").hide();
                $("#sendBack").hide();
                $("#payMoney").show();
                $("#submitBtn").hide();
            }else if(orderStatus == 3){//待确认
                $("#agreeDiv").show();
                $("#agreeDiv").addClass('active');
                $("#agreeDiv").find("h4").text("通过");
                $("#agreeDiv").find("span").text("");

                $("#preConfirmDiv").show();
                // $("#preConfirmDiv").find("span").text(data.updateTime == null?"":timeObject(data.updateTime));
                $("#preConfirmDiv").addClass('active');

                $("#completeDiv").show();
                $("#completeDiv").removeClass('active');
                $("#completeDiv").find("span").text("");
                $("#completeDiv").find("h4").text("已完成");

                $("#singleRejection").hide();
                $("#rejectLi").hide();
                $("#payTxtDiv").show();

                $("#payBillNumTxt").text(data.payBillNum == null?"无":data.payBillNum);
                $("#payRemarkTxt").text(data.payRemark == null || data.payRemark == ""?"无":data.payRemark);

                var img = data.certificateImg;
                if(img == null || img == ""){
                    $("#noPic").show();
                    $("#showPic").hide();
                }else{
                    $("#noPic").hide();
                    $("#showPic").show();
                    $("#certificateImg").attr("src",data.certificateImg);
                }

                $("#paySubmitBtn").hide();
                $("#showPayDiv").hide();
                $("#agreeBtn").hide();
                $("#sendBack").hide();
                $("#payMoney").hide();
                $("#submitBtn").hide();
            }else if(orderStatus == 4){//已完成
                $("#agreeDiv").show();
                $("#agreeDiv").find("h4").text("通过");
                $("#agreeDiv").addClass('active');
                $("#agreeDiv").find("span").text("");

                $("#preConfirmDiv").show();
                $("#preConfirmDiv").addClass('active');
                $("#preConfirmDiv").find("span").text("");

                $("#completeDiv").show();
                $("#completeDiv").find("h4").text("已完成");
                $("#completeDiv").addClass('active');
                // $("#completeDiv").find("span").text(data.updateTime == null?"":timeObject(data.updateTime));

                $("#singleRejection").hide();

                $("#rejectLi").hide();

                $("#payTxtDiv").show();
                $("#payBillNumTxt").text(data.payBillNum == null?"无":data.payBillNum);
                $("#payRemarkTxt").text(data.payRemark == null?"无":data.payRemark);

                var img = data.certificateImg;
                if(img == null || img == ""){
                    $("#noPic").show();
                    $("#showPic").hide();
                }else{
                    $("#noPic").hide();
                    $("#showPic").show();
                    $("#certificateImg").attr("src",data.certificateImg);
                }

                $("#paySubmitBtn").hide();
                $("#showPayDiv").hide();
                $("#agreeBtn").hide();
                $("#sendBack").hide();
                $("#payMoney").hide();
                $("#submitBtn").hide();
            }else if(orderStatus == 8) {//已取消
                $("#agreeDiv").hide();

                $("#preConfirmDiv").hide();

                $("#completeDiv").show();
                $("#completeDiv").find("h4").text("已取消");
                $("#completeDiv").addClass('active');
                // $("#completeDiv").find("span").text(data.updateTime == null?"":timeObject(data.updateTime));

                $("#singleRejection").hide();

                $("#noPic").hide();
                $("#showPic").hide();

                $("#rejectLi").hide();
                $("#payTxtDiv").hide();
                $("#paySubmitBtn").hide();
                $("#showPayDiv").hide();
                $("#agreeBtn").hide();
                $("#sendBack").hide();
                $("#payMoney").hide();
                $("#submitBtn").hide();
            }
            $("#dialog1").modal("show");
        }
    });
}
/**
 * 显示打回div
 */
function rejectionFun(){
    $("#singleRejection").show();
    $("#agreeBtn").hide();
    $("#sendBack").hide();
    $("#payMoney").hide();
    $("#submitBtn").show();
}
function checkReject(rejectReason,errorTip) {
    var result = true;
    var reason = $("#"+rejectReason).val();
    if(reason == null || reason == ""){
        $("#"+errorTip).text("打回原因不能为空");
        result = false;
    }else{
        $("#"+errorTip).text("");
        if(reason.length > 200){
            $("#"+errorTip).text("打回原因不能超过200字");
            result = false;
        }else {
            $("#"+errorTip).text("");
        }
    }
    return result;
}
/**
 * 单条审核通过，审核打回
 */
function singleSubmit(statusType){
    var rejectionTitle = "审核通过";
    var tradeInfoId = $("#tradeInfoId").val();
    if(statusType == "1"){//打回

        if(checkReject("rejectionTitle","rejectionTip")==true){
            rejectionTitle = $("#rejectionTitle").val();
            updateSingleStatus(statusType,tradeInfoId,rejectionTitle);
        }else{
            return false;
        }
    }else{//通过
        updateSingleStatus(statusType,tradeInfoId,rejectionTitle);
    }
}
/**
 * 单条审核通过，审核打回
 * @param statusType
 * @param tradeInfoId
 * @param rejectionTitle
 */
function updateSingleStatus(statusType,tradeInfoId,rejectionTitle) {
    var token = $("#CSRFToken").val();
    var result = "操作成功！";
    $.ajax({
        type:'post',
        url:'updateChargeWithdrawStatus.htm?CSRFToken='+token,
        data:{"orderStatus":statusType,"tradeInfoId":tradeInfoId,"sendBack":rejectionTitle},
        cache:false,
        dataType:'json',
        success:function(data){
            if(data == 0){
                result = "操作失败！";
            }
            showTipAlert(result);
            $('#modalDialog').on('hidden.bs.modal', function () {
                window.location.reload();
            })
        }
    });
}
/**
 * 显示打款div
 */
function showPayDiv() {
    $("#showPayDiv").show();
    $("#payBillNum").val("");
    $("#ImgPr").attr("src","");
    $("#payRemark").val("");
    $("#payBillNumTip").text("");
    $("#payRemarkTip").text("");
    $("#updateId").val($("#tradeInfoId").val());

    $(".tips").show();
    $(".imgs").hide();

    $("#singleRejection").hide();
    $("#agreeBtn").hide();
    $("#sendBack").hide();
    $("#payMoney").hide();
    $("#paySubmitBtn").show();
    $("#submitBtn").hide();
}
/**
 * 打款
 */
function payMoneyFunc(){
    if(checkPayFrom()==true){
        var result = "打款成功";
        var token = $("#CSRFToken").val();
        // var tradeInfoId = $("#tradeInfoId").val();
        var formData = new FormData($("#payForm")[0]);
        $.ajax({
            type:"POST",
            data:formData,
            // url:"updateWithdrawPayStatus.htm?CSRFToken="+token+"?tradeInfoId="+tradeInfoId,
            url:"updateWithdrawPayStatus.htm?CSRFToken="+token,
            processData: false,
            cache: false,
            contentType: false,
            success:function (data) {
                if(data == 0){
                    result = "打款失败！";
                }
                showTipAlert(result);
                $('#modalDialog').on('hidden.bs.modal', function () {
                    window.location.reload();
                });
            }
        });
    }else{
        return false;
    }
}
//打款验证是否都填写了
function checkPayFrom() {
    var flag = true;
    var payBillNum = $("#payBillNum").val();
    if(payBillNum == null || payBillNum == ""){
        $("#payBillNumTip").text("付款单号不能为空");
        flag = false;
    }else{
        $("#payBillNumTip").text("");
        var partern = /^[0-9a-zA-Z]{6,30}$/;
        if(partern.exec(payBillNum)) {
            $("#payBillNumTip").text("");
        }else {
            $("#payBillNumTip").text("请输入正确的付款单号");
            flag = false;
        }
    }
    var payRemark = $("#payRemark").val();
    if(payRemark != null || payRemark != ""){
        if(payRemark.length > 200){
            $("#payRemarkTip").text("付款备注不能超过200字");
            flag = false;
        }else {
            $("#payRemarkTip").text("");
        }
    }
    return flag;
}

//转换时间格式
function timeObject(obj){
    var date = "/Date(" +notNull(obj)+")/";
    var time = new Date(parseInt(date.replace("/Date(", "").replace(")/", ""), 10));
    var result = time.getFullYear() + "-" + (time.getMonth() + 1 < 10 ? "0" + (time.getMonth() + 1) : time.getMonth() + 1) + "-" + (time.getDate() < 10 ? "0" + time.getDate() : time.getDate()) + " " + (time.getHours() < 10 ? "0" + time.getHours() : time.getHours()) + ":" + (time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes())+ ":" + (time.getSeconds() < 10 ? "0" + time.getSeconds() : time.getSeconds());
    return result;
}
//判断数据是否为空为空返回“”
function notNull(obj){
    if(obj != null && obj != undefined){
        return obj;
    }else{
        return "";
    }
}
/**
 * 点击查看大图
*/
function showBigPic(){
    var imgUrl = $("#certificateImg")[0].src;
    $("#voucher01").attr("href",imgUrl);
}

jQuery.fn.extend({
    uploadPreview: function (opts) {
        var _self = this,
        _this = $(this);
        opts = jQuery.extend({
            Img: "ImgPr",
            Width: 100,
            Height: 100,
            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function () {
                $(".tips").hide();
                $(".imgs").show();
            }
        }, opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function () {
            if (this.value) {
                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
                    this.value = "";
                    return false
                }
                if (/msie/.test(navigator.userAgent.toLowerCase())) {
                    try {
                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                    } catch (e) {
                        var src = "";
                        var obj = $("#" + opts.Img);
                        var div = obj.parent("div")[0];
                        _self.select();
                        if (top != self) {
                            window.parent.document.body.focus()
                        } else {
                            _self.blur()
                        }
                        src = document.selection.createRange().text;
                        document.selection.empty();
                        obj.hide();
                        obj.parent("div").css({
                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                            'width': opts.Width + 'px',
                            'height': opts.Height + 'px'
                        });
                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
                    }
                } else {
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                }
                opts.Callback()
            }
        })
    }
});