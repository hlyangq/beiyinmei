<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商家订单收入</title>

    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

    <![endif]-->

</head>
<body>
<!-- 引用头 -->
<jsp:include page="../page/header.jsp"></jsp:include>
<div class="page_body container-fluid">
    <div class="row">
        <!-- 引用左边 -->
        <jsp:include page="../page/left.jsp"></jsp:include>

        <div class="col-lg-20 col-md-19 col-sm-18 main">
            <jsp:include page="../page/left2.jsp"></jsp:include>
            <div class="main_cont">
                 <jsp:include page="../page/breadcrumbs.jsp"></jsp:include>

                <h2 class="main_title">${pageNameChild} - ${store.storeName}结算周期为每月[${store.billingCycle}]号 <small>(共${pb.rows }条)</small></h2>

                <div class="common_data p20">
                    <div class="filter_area">

                    </div>
                    <div class="data_ctrl_area mb20">
                        <div class="data_ctrl_search pull-right"></div>
                        <div class="data_ctrl_brns pull-left">
                            <%--<button type="button" class="btn btn-info" onclick="$('#outReport').modal('show')">--%>
                                <%--<i class="glyphicon glyphicon-log-out"></i> 导出报表--%>
                            <%--</button>--%>
                            <button type="button" class="btn btn-info" onclick="history.go(-1);">
                                <i class="icon iconfont">&#xe614;</i> 返回
                            </button>
                        </div>
                        <div class="clr"></div>
                    </div>

                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th width="50"><input name="storeIds"  onclick="allunchecked(this,'reportIds')" type="checkbox"></th>
                            <th>商品编号</th>
                            <th>状态</th>
                            <th>商品名称</th>
                            <th>商品原价</th>
                            <th>佣金</th>
                            <th>佣金分类</th>
                        </tr>
                        </thead>
                        <tbody>
                    <c:forEach items="${pb.list}" var="orderGoods" varStatus="i">
                        <tr>
                            <td><input type="checkbox" name="reportIds"  value="${order.orderId }"></td>
                            <td>${orderGoods.goodsInfoItemNo }</td>
                            <td>

                                <c:if test="${orderGoods.backFlag=='0'}">
                                    <a href="javascript:;"><span class="label label-success">已完成</span></a>
                                </c:if>
                                <c:if test="${orderGoods.backFlag==1}">
                                    <a href="javascript:;"><span class="label label-danger">退款完成</span></a>
                                </c:if>
                            </td>
                            <td>${orderGoods.goodsInfoName }</td>
                            <td><fmt:formatNumber value="${orderGoods.goodsBackPrice }"  pattern="0.00"/></td>
                            <td><fmt:formatNumber value="${orderGoods.caRateProduc }"  pattern="0.00"/></td>
                            <td>${orderGoods.catName }</td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>



                </div>

            </div>
        </div>
    </div>
</div>



<!-- Modal -->
<div class="modal fade" id="outReport"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">导出报表</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal"  action="exportCateReport.htm" method="post" id="expReportForm">
                    <input type="hidden" name="CSRFToken" value="${token}">
                    <input type="hidden" name="storeId" value="${storeId}">
                    <input type="hidden" id="today" value="${dateMap.today}">

                    <div class="form-group">
                        <label class="control-label col-sm-5">时间段：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <label class="radio-inline">
                                <input type="radio" name="time" value="0" time_end="${dateMap.recentOneMonth }" class="time_exp" checked="checked"> 近一个月
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="time" value="1" time_end="${dateMap.recentThreeMonth }" class="time_exp"> 近三个月
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="time" value="2" time_end="${dateMap.recentSixMonth }" class="time_exp"> 近半年
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">开始时间：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-8">
                            <div class="input-group date form_date" id="startpicker">
                                <input class="form-control" type="text" id="startTime" name="startDate"
                                       value="${dateMap.recentOneMonth }" readonly>
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                        <div class="col-sm-3"></div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">结束时间：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-8">
                            <div class="input-group date form_date" id="endpicker">
                                <input class="form-control" type="text" id="endTime" name="endDate"
                                       value="${dateMap.today}" readonly>
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                        <div class="col-sm-3"></div>
                    </div>
                    </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="excReport()">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="reportDetails"  role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">报表详细信息</h4>
            </div>
            <div class="modal-body" id="outdetailcatereport">
                <h4 id="t10"></h4>
                <table class="table table-bordered table-striped">
                    <tbody>
                    <tr>
                        <td>报表时间：</td>
                        <td colspan="3" id="t9"></td>
                    </tr>
                    <tr>
                        <td>商户名：</td>
                        <td id="t1"></td>
                        <td>公司名称：</td>
                        <td id="t2"></td>
                    </tr>
                    <tr>
                        <td>分类名称：</td>
                        <td id="t3"></td>
                        <td>类目扣率：</td>
                        <td id="t4"></td>
                    </tr>
                    <tr>
                        <td>总流水：</td>
                        <td id="t5"></td>
                        <td>结算状态：</td>
                        <td id="t6">
                        </td>
                    </tr>
                    <tr>
                        <td>总订单优惠：</td>
                        <td id="t7"></td>
                        <td>总商品优惠：</td>
                        <td id="t8"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="doPrint()">打印</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script src="<%=basePath %>js/summernote.min.js"></script>
<script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath %>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath %>js/common.js"></script>
<script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<script src="<%=basePath %>js/common/common_alert.js"></script>
<script src="<%=basePath %>js/common/common_checked.js"></script>
<script type="text/javascript" src="<%=basePath%>js/report/report.js"></script>
<script>

    var flag=null;
    function openReportDetail(reportId) {
        flag=reportId;
        $('#reportDetails').modal('show')
        $.ajax({
            url:'catereportDetail.htm?reportId='+reportId+"&CSRFToken="+$("#token").val(),
            success:function(data) {
                $("#t1").html(data.storeName);
                $("#t2").html(data.companyName);
                $("#t3").html(data.cateName);
                $("#t4").html(data.cateRate);
                $("#t5").html(data.totalOrderMoney+"元");
                var settleStatus=data.settleStatus;
                $("#t6").empty();
                if(settleStatus==0){
                    $("#t6").append("<a href='javascript:;'><span class='label label-success'></span></a>");
                }if(settleStatus==1){
                    $("#t6").append("<a href='javascript:;'><span class='label label-danger'></span></a>") ;
                }
                $("#t7").html(data.totalOrderPrePrice+"元");
                $("#t8").html(data.totalGoodsPrePrice+"元");
                var statTime=new Date(data.startTime);
                var month=statTime.getMonth();
                month=month+1;
                if(parseInt(month) < 10) {
                    month = "0" + month;
                }
                statTime=statTime.getFullYear()+"-"+month+"-"+statTime.getDate();
                var endTime=new Date(data.endTime);
                var endmonth=endTime.getMonth();
                endmonth=endmonth+1;
                if(parseInt(endmonth) < 10) {
                    endmonth = "0" + endmonth;
                }
                endTime=endTime.getFullYear()+"-"+endmonth+"-"+endTime.getDate();

                $("#t9").html(statTime+"~"+endTime);

                $("#t10").html("报表明细("+statTime+"~"+endTime+")");



            }
        });
    }

    //打印分类对账
    function doPrint() {
        var url='reportDetail.htm?reportId='+flag+"&CSRFToken="+$("#token").val();
        var newWindow=window.open(url,"_blank");//打印窗口要换成页面的url
        var css = '<style>.opr{display:none;}</style>'
        var docStr = $("#outdetailcatereport").html()+$("head").html()+css;
        newWindow.document.write(docStr);
        //newWindow.document.append(CSS);
        //$(CSS).appendTo(newWindow.document);
        //newWindow.close();
        $("#outReport").hide();
        newWindow.print();
    }

    $(function(){


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
</script>
</body>
</html>
