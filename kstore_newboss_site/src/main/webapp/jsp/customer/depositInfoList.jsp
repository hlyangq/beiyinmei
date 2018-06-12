<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
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
    <title>会员资金</title>

    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">
    <link href="<%=basePath %>css/paystyle.css" rel="stylesheet">
    <link href="<%=basePath %>css/paystyle_new.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- 引用头 -->
<jsp:include page="../page/header.jsp"></jsp:include>
<div class="page_body container-fluid">
    <div class="row">
        <jsp:include page="../page/left.jsp"></jsp:include>
        <div class="col-lg-20 col-md-19 col-sm-18 main">
            <jsp:include page="../page/left2.jsp"></jsp:include>
            <div class="main_cont">
                <jsp:include page="../page/breadcrumbs.jsp"></jsp:include>
                <h2 class="main_title">${pageNameChild}</h2>
                <div class="data_overview row">
                    <div class="col-md-8">
                        <div class="data_overview_box">
                            <h4>预存款总额</h4>
                            <p class="number">${result.totalDeposit}</p>
                            <p class="unit">(单位：元)</p>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="data_overview_box">
                            <h4>可用预存款</h4>
                            <p class="number red">${result.usableDeposit}</p>
                            <p class="unit">(单位：元)</p>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="data_overview_box">
                            <h4>冻结预存款</h4>
                            <p class="number">${result.totalFreezeDeposit}</p>
                            <p class="unit">(单位：元)</p>
                        </div>
                    </div>
                </div>
                <div class="common_data p20">
                    <div class="filter_area mb10">
                        <form role="form" class="form-inline" action="initDepositInfoList.htm?CSRFToken=${token }" id="searchForm">
                            <input type="hidden" value="searchForm" id="formId">
                            <input type="hidden" value="${pageBean.url}" id="formName">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">用户名</span>
                                    <input type="text" class="form-control" id="customerName" name="customerName" value="${pageBean.objectBean.customerName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">账户状态</span>
                                    <select class="form-control cate_selector" id="customerStatus" name="customerStatus">
                                        <option value="" <c:if test="${empty pageBean.objectBean.customerStatus }">selected="selected"</c:if>>选择状态</option>
                                        <option value="0" <c:if test="${not empty pageBean.objectBean.customerStatus && pageBean.objectBean.customerStatus==0 }">selected="selected"</c:if>>正常</option>
                                        <option value="1" <c:if test="${not empty pageBean.objectBean.customerStatus && pageBean.objectBean.customerStatus==1 }">selected="selected"</c:if>>冻结</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">搜索</button>
                            </div>
                        </form>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th>头像</th>
                            <th>用户名</th>
                            <th>状态</th>
                            <th>预存款总额</th>
                            <th>可用预存款</th>
                            <th>冻结预存款</th>
                            <th width="150">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${fn:length(pageBean.list)<=0}">
                            <tr>
                                <td style="text-align: center;" colspan="7">暂无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageBean.list}" var="deposit" varStatus="i">
                            <tr>
                                <td><img class="img_circle" src="<c:if test="${deposit.customerImg!='' }">${deposit.customerImg}</c:if><c:if test="${deposit.customerImg==null||deposit.customerImg==''}"><%=basePath %>images/default_head.jpg</c:if>"  height="50" width="50" /></td>
                                <td>
                                    <c:if test="${fn:length(deposit.customerUsername)>12 }">
                                        ${fn:substring(deposit.customerUsername,0,11) }...
                                    </c:if>
                                    <c:if test="${fn:length(deposit.customerUsername)<=11 }">
                                        ${deposit.customerUsername}
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${deposit.isFlag ==0}">正常</c:if>
                                    <c:if test="${deposit.isFlag ==1}">冻结</c:if>
                                </td>
                                <td>&yen;<c:if test="${deposit.totalDeposit ==0 or deposit.totalDeposit == null}">0.00</c:if>${deposit.totalDeposit}</td>
                                <td>&yen;<c:if test="${deposit.preDeposit ==0 or deposit.preDeposit == null}">0.00</c:if>${deposit.preDeposit}</td>
                                <td>&yen;<c:if test="${deposit.freezePreDeposit ==0 or deposit.freezePreDeposit == null}">0.00</c:if>${deposit.freezePreDeposit}</td>
                                <td>
                                    <a href="javascript:;" onclick="openProduct(${deposit.customerId})" class="scan_member">查看</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:import url="../page/searchPag.jsp">
                        <c:param name="pageBean" value="${pageBean }"/>
                        <c:param name="path" value="../"></c:param>
                    </c:import>
                </div>
                <form method="post" id="openDepositCustomerForm" action="selectDepositInfoById.htm">
                    <input type="hidden" name="pageNos" value="${pageBean.pageNo}"/>
                    <input name="customerId" value="" type="hidden"/>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function openProduct(customerId){
        $("#openDepositCustomerForm").find("input[name='customerId']").val(customerId);
        $("#openDepositCustomerForm").submit();
    }
</script>
</body>
</html>