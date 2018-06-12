//重置
function resetReportForm() {
	$(".form-control").val("");
}
//查询
function queryReport(){
    //查询结束时间不能早于开始时间
    if($("#start").val() != "" && $("#end").val() != ""){
        if(parseInt($("#end").val()) < parseInt($("#start").val())){
            $('#select-tip').modal('show');
            return null;
        }
    }
	$("#reportForm").attr("action","queryCheckReport.html");
	$("#reportForm").submit();
}
//导出
function exportReport() {
	$("#reportForm").attr("action","exportThirdReport.htm");
	$("#reportForm").submit();
}