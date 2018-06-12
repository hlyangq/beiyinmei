<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <!-- Bootstrap -->
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css">
    <link href="<%=basePath%>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath%>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath%>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/select2.min.css" rel="stylesheet">
    <link href="<%=basePath%>css/style.css" rel="stylesheet">
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
                    <form role="form" class="form-inline" action="selectDepositInfoDetail.htm?CSRFToken=${token }" method="post"
                          id="searchForm">
                        <input type="hidden" value="searchForm" id="formId">
                        <input type="hidden" value="${pageBean.url}" id="formName">
                        <div class="filter_area">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">交易类型</span>
                                    <select class="form-control cate_selector" id="orderType" name="orderType">
                                        <option value=""
                                                <c:if test="${empty pageBean.objectBean.orderType }">selected="selected"</c:if>>
                                            全部
                                        </option>
                                        <option value="0"
                                                <c:if test="${not empty pageBean.objectBean.orderType && pageBean.objectBean.orderType==0 }">selected="selected"</c:if>>
                                            在线充值
                                        </option>
                                        <option value="1"
                                                <c:if test="${not empty pageBean.objectBean.orderType && pageBean.objectBean.orderType==1 }">selected="selected"</c:if>>
                                            订单退款
                                        </option>
                                        <option value="2"
                                                <c:if test="${not empty pageBean.objectBean.orderType && pageBean.objectBean.orderType==2 }">selected="selected"</c:if>>
                                            线下提现
                                        </option>
                                        <option value="3"
                                                <c:if test="${not empty pageBean.objectBean.orderType && pageBean.objectBean.orderType==3 }">selected="selected"</c:if>>
                                            订单消费
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">用户名</span>
                                    <input type="text" class="form-control" id="customerName" name="customerName" value="${pageBean.objectBean.customerName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">交易单号</span>
                                    <input type="text" class="form-control" name="orderCode"
                                           value="${pageBean.objectBean.orderCode}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">交易状态</span>
                                    <select class="form-control cate_selector" id="orderStatus" name="orderStatus">
                                        <option value=""
                                                <c:if test="${empty pageBean.objectBean.orderStatus }">selected="selected"</c:if>>
                                            全部
                                        </option>
                                        <option value="0"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==0 }">selected="selected"</c:if>>
                                            待审核
                                        </option>
                                        <option value="1"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==1 }">selected="selected"</c:if>>
                                            打回
                                        </option>
                                        <option value="2"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==2 }">selected="selected"</c:if>>
                                            通过
                                        </option>
                                        <option value="3"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==3 }">selected="selected"</c:if>>
                                            待确认
                                        </option>
                                        <option value="4"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==4 }">selected="selected"</c:if>>
                                            已完成
                                        </option>
                                        <option value="5"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==5 }">selected="selected"</c:if>>
                                            未支付
                                        </option>
                                        <option value="6"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==6 }">selected="selected"</c:if>>
                                            充值成功
                                        </option>
                                        <%--<option value="7"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==7 }">selected="selected"</c:if>>
                                            充值失败
                                        </option>--%>
                                        <option value="8"
                                                <c:if test="${not empty pageBean.objectBean.orderStatus && pageBean.objectBean.orderStatus==8 }">selected="selected"</c:if>>
                                            已取消
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group date form_datetime w300" id="startpicker">
                                    <span class="input-group-addon">开始时间</span>
                                    <input class="form-control" type="text" style="width:165px;" id="startTime" name="startTime"
                                           value="${startTime}" readonly>
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span>
                                    <span class="input-group-addon"><span
                                            class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group date form_datetime w300" id="endpicker">
                                    <span class="input-group-addon">结束时间</span>
                                    <input class="form-control" type="text" style="width:165px;" value="${endTime}"
                                           name="endTime" id="endTime" readonly>
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span>
                                    <span class="input-group-addon"><span
                                            class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">搜索</button>
                            </div>
                        </div>
                    </form>
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th>会员用户名</th>
                            <th>交易类型</th>
                            <th>交易时间</th>
                            <th>收入</th>
                            <th>支出</th>
                            <th>交易状态</th>
                            <th>当前预存款</th>
                            <th>交易单号</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${fn:length(pageBean.list)<=0 }">
                            <tr>
                                <td style="text-align: center;" colspan="8">暂无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageBean.list }" var="trade" varStatus="i">
                            <tr>
                                <td>
                                    <c:if test="${fn:length(trade.customerUsername)>12 }">
                                        ${fn:substring(trade.customerUsername,0,11) }...
                                    </c:if>
                                    <c:if test="${fn:length(trade.customerUsername)<=11 }">
                                        ${trade.customerUsername}
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${trade.orderType == 0}">在线充值</c:if>
                                    <c:if test="${trade.orderType == 1}">订单退款</c:if>
                                    <c:if test="${trade.orderType == 2}">线下提现</c:if>
                                    <c:if test="${trade.orderType == 3}">订单消费</c:if>
                                </td>
                                <td>
                                    <fmt:formatDate value="${trade.createTime }" pattern="yyyy-MM-dd HH:mm:ss" var="createTime" />
                                        ${createTime}
                                </td>
                                <td style="color: #1BB974;"><c:if test="${trade.income !=0 && trade.income != null}">+${trade.income}</c:if></td>
                                <td style="color: red;"><c:if test="${trade.cost !=0 && trade.cost != null}">-${trade.cost}</c:if></td>
                                <td>
                                    <c:if test="${trade.orderStatus == 0}">待审核</c:if>
                                    <c:if test="${trade.orderStatus == 1}">打回</c:if>
                                    <c:if test="${trade.orderStatus == 2}">通过</c:if>
                                    <c:if test="${trade.orderStatus == 3}">待确认</c:if>
                                    <c:if test="${trade.orderStatus == 4}">已完成</c:if>
                                    <c:if test="${trade.orderStatus == 5}">未支付</c:if>
                                    <c:if test="${trade.orderStatus == 6}">充值成功</c:if>
                                    <%--<c:if test="${trade.orderStatus == 7}">充值失败</c:if>--%>
                                    <c:if test="${trade.orderStatus == 8}">已取消</c:if>
                                    <%--<c:if test="${trade.orderStatus == null or trade.orderStatus eq ''}">已完成</c:if>--%>
                                </td>
                                <td><c:if test="${trade.currentPrice ==0 or trade.currentPrice == null}">0.00</c:if>${trade.currentPrice}</td>
                                <td>${trade.orderCode}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:import url="../page/searchPag.jsp">
                        <c:param name="pageBean" value="${pageBean }"/>
                    </c:import>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/summernote.min.js"></script>
<script src="<%=basePath%>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath%>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script src="<%=basePath %>js/common/common_alert.js"></script>
<script src="<%=basePath %>js/common/common_checked.js"></script>
<script src="<%=basePath %>js/customer/commonDate.js"></script>
<script src="<%=basePath %>js/select2.min.js"></script>
</body>
</html>