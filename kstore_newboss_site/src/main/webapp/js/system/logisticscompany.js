$(function(){
    /* 为选定的select下拉菜单开启搜索提示 END */
    $("#addCompanyForm").validate();
    $("#searchCompanyForm").validate();
    $("#editCompanyForm").validate();
});
/**
 * 添加物流公司
 */
var num=0;
function submitAddCompanyForm() {
    var CSRFToken = $("#CSRFToken").val();
    if( $("#addCompanyForm").valid()&&num==0){
         $.ajax({
             url:'ajaxCheckLogisticsCompany.htm?CSRFToken='+CSRFToken+'&name='+$("input[name='name']").val(),
             success:function(data) {
                if(data == true){
                    num+=1;
                    $("#addCompanyForm").submit();
                }else{
                    showTipAlert("已存在相同的物流公司名称！");
                    return;
                }
             }
         });
     }
}
/**
 * 搜索物流公司
 */
function searchCompany() {
    $("#searchCompanyForm").submit();
}
/**
 * 弹框显示物流公司编辑框
 * @param companyId 物流公司id
 */
function showEditCompanyForm(companyId) {
    $("#logComId").val(companyId);
    var CSRFToken = $("#CSRFToken").val();
    $.ajax({
        url:'selectLogisticsCompanyById.htm?CSRFToken='+CSRFToken+'&companyId='+companyId,
        success:function(data) {
            //填充值
            $("#oldName").val(data.name);
            $("#name").val(data.name);
            $("#code").val(data.code);
            $("#kuaidi100Code").val(data.kuaidi100Code);
            $("#comUrl").val(data.comUrl);
            $("#queryUrl").text(data.queryUrl);
            $("#sort").val(data.sort);
            if(data.usedStatus==1) {
                $("#open1").click();
            } else {
                $("#open2").click();
            }
        }
    });
    $('#editCompany').modal('show')
}
/**
 * 确定修改物流公司信息
 */
function submitEditCompanyForm() {
    var CSRFToken = $("#CSRFToken").val();
    if($("#name").val() == $("#oldName").val()){
        $("#editCompanyForm").submit();
    }else{
        $.ajax({
            url:'ajaxCheckLogisticsCompany.htm?CSRFToken='+CSRFToken+'&name='+$("#name").val(),
            success:function(data) {
                if(data == true){
                    num+=1;
                    $("#editCompanyForm").submit();
                }else{
                    showTipAlert("已存在相同的物流公司名称！");
                    return;
                }
            }
        });
    }

}

/**
 * 修改物流公司启用状态
 * @param logComId 物流公司主键id
 */
function changeUserdStatus(logComId){
    var CSRFToken = $("#CSRFToken").val();
    location.href = "changeUserdStatucForLogCom.htm?CSRFToken="+CSRFToken+"&logComId="+logComId;
}