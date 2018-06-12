$(function(){

    /* 下面是表单里面的日期时间选择相关的 */
    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:00',
        weekStart : 1,
        autoclose : true,
        language : 'zh-CN',
        pickerPosition : 'bottom-left',
        todayBtn : true,
        viewSelect : 'hour'
    });
    $('.form_date').datetimepicker({
        format : 'yyyy-mm-dd',
        maxDate:new Date(),
        weekStart : 1,
        autoclose : true,
        language : 'zh-CN',
        pickerPosition : 'bottom-left',
        minView : 2,
        todayBtn : true
    });
    /* 下面是表单里面的日期时间选择相关的 END */

    /*下面是时间选择器开始时间不能大于结束时间设置  START*/
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    $('#endpicker').datetimepicker('setStartDate', startTime);
    $('#startpicker').datetimepicker('setEndDate', endTime);
    $('#endpicker')
        .datetimepicker()
        .on('show', function (ev) {
            startTime = $("#startTime").val();
            endTime = $("#endTime").val();
            $('#endpicker').datetimepicker('setStartDate', startTime);
            $('#startpicker').datetimepicker('setEndDate', endTime);
        });
    $('#startpicker')
        .datetimepicker()
        .on('show', function (ev) {
            endTime = $("#endTime").val();
            startTime = $("#startTime").val();
            $('#startpicker').datetimepicker('setEndDate', endTime);
            $('#endpicker').datetimepicker('setStartDate', startTime);
        });
    /*下面是时间选择器开始时间不能大于结束时间设置  END*/
});