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
    <title>提现管理</title>

    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/select2.min.css" rel="stylesheet">
    <link href="<%=basePath %>js/artDialog4.1.7/skins/simple.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">

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
                <div class="common_data p20">
                    <div class="alert alert-warning alert-dismissible fade in" role="alert">
                        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                        <strong>提示：</strong> 管理员打款后，如果用户在7天内没有确认收款，系统将自动确认收款，并扣减提现金额。
                    </div>
                    <div class="filter_area mb10">
                        <input type="hidden" name="CSRFToken" id="CSRFToken" value="${token}">
                        <form role="form" class="form-inline" action="initChargeWithdrawList.htm?CSRFToken=${token }" id="searchForm">
                            <input type="hidden" value="searchForm" id="formId">
                            <input type="hidden" value="${pageBean.url}" id="formName">
                            <input type="hidden" name="status" value="${status}" id="statusFlag"/>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">用户名</span>
                                    <input type="text" class="form-control" id="customerName" name="customerName" value="${pageBean.objectBean.customerName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">提现单编号</span>
                                    <input type="text" class="form-control" name="orderCode"
                                           value="${pageBean.objectBean.orderCode}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">搜索</button>
                            </div>
                        </form>
                    </div>
                    <div class="data_ctrl_area mb20">
                        <div class="data_ctrl_brns pull-left">
                            <button type="button" class="btn btn-info" id="showBox1" onclick="showBatchUpdateModal(2);">
                                通过
                            </button>
                            <button type="button" class="btn btn-info" id="showBox3" onclick="showBatchUpdateModal(1);">
                                打回
                            </button>
                        </div>
                        <div class="clr"></div>
                    </div>
                    <div class="table_tabs" id="charge_tabs">
                        <ul class="flagli">
                            <!--交易类型 0在线充值 1订单退款 2线下提现 3订单消费-->
                            <!--提现状态 0待审核 1打回 2通过 3待确认 4已完成 8已取消-->
                            <li class="<c:if test="${status == null or status == ''}">active</c:if>">
                                <a href="javascript:;" data-type="">全部</a>
                            </li>
                            <li class="<c:if test="${status=='0'}">active</c:if>">
                                <a href="javascript:;" data-type="0">待审核</a>
                            </li>
                            <li class="<c:if test="${status=='1'}">active</c:if>">
                                <a href="javascript:;" data-type="1">打回</a>
                            </li>
                            <li class="<c:if test="${status=='2'}">active</c:if>">
                                <a href="javascript:;" data-type="2">已通过</a>
                            </li>
                            <li class="<c:if test="${status=='3'}">active</c:if>">
                                <a href="javascript:;" data-type="3">待确认</a>
                            </li>
                            <li class="<c:if test="${status=='4'}">active</c:if>">
                                <a href="javascript:;" data-type="4">已完成</a>
                            </li>
                            <li class="<c:if test="${status=='8'}">active</c:if>">
                                <a href="javascript:;" data-type="8">已取消</a>
                            </li>
                        </ul>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th width="10"><input type="checkbox" onclick="allunchecked(this,'withdrawId');"></th>
                            <th>提现单编号</th>
                            <th>用户名</th>
                            <th>收款银行</th>
                            <th>收款账号</th>
                            <th>户名</th>
                            <th>提现金额</th>
                            <th>申请时间</th>
                            <th>状态</th>
                            <th width="150">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:if test="${fn:length(pageBean.list)<=0}">
                                <tr>
                                    <td style="text-align: center;" colspan="10">暂无数据...</td>
                                </tr>
                            </c:if>
                            <c:forEach items="${pageBean.list}" var="charge" varStatus="i">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${charge.orderStatus == 0}">
                                                <input type="checkbox" name="withdrawId" value="${charge.tradeInfoId}">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="checkbox" disabled="true">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${charge.orderCode}</td>
                                    <td title="${charge.customerUsername}">
                                        <c:choose>
                                            <c:when test="${fn:length(charge.customerUsername)>12 }">
                                                ${fn:substring(charge.customerUsername,0,11) }...
                                            </c:when>
                                            <c:otherwise>${charge.customerUsername}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td title="${charge.bankName}">
                                        <c:choose>
                                            <c:when test="${fn:length(charge.bankName)>12 }">
                                                ${fn:substring(charge.bankName,0,11) }...
                                            </c:when>
                                            <c:otherwise>${charge.bankName}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td title="${charge.bankNo}">
                                        <c:choose>
                                            <c:when test="${fn:length(charge.bankNo)>12 }">
                                                ${fn:substring(charge.bankNo,0,11) }...
                                            </c:when>
                                            <c:otherwise>${charge.bankNo}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td title="${charge.accountName}">
                                        <c:choose>
                                            <c:when test="${fn:length(charge.accountName)>12 }">
                                                ${fn:substring(charge.accountName,0,11) }...
                                            </c:when>
                                            <c:otherwise>${charge.accountName}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${charge.amount}</td>
                                    <td>
                                        <fmt:formatDate value="${charge.createTime}" pattern="yyyy-MM-dd HH:mm:ss" var="createTime" />
                                            ${createTime}
                                    </td>
                                    <td>
                                        <c:if test="${charge.orderStatus == 0}">待审核</c:if>
                                        <c:if test="${charge.orderStatus == 1}">打回</c:if>
                                        <c:if test="${charge.orderStatus == 2}">通过</c:if>
                                        <c:if test="${charge.orderStatus == 3}">待确认</c:if>
                                        <c:if test="${charge.orderStatus == 4}">已完成</c:if>
                                        <c:if test="${charge.orderStatus == 8}">已取消</c:if>
                                    </td>
                                    <td>
                                        <div class="btn-group">
                                            <a href="javascript:;" onclick="viewChargeWithdrawInfo(${charge.id});" class="scan_member">查看</a>
                                        </div>
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
            </div>
        </div>
    </div>
</div>
<!--批量通过与打回-->
<div class="modal fade" id="batchUpdate"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" id="batchTip">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">批量通过</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal"  action="" method="post">
                    <input type="hidden" id="updateStatus"/>
                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-10" id="batchTitle">
                            <p></p>
                        </div>
                    </div>
                    <div class="form-group" id="sendBackDiv">
                        <label class="control-label col-md-4"><b>*</b> 打回原因：</label>
                        <div class="col-md-12">
                            <textarea class="form-control" rows="3" id="sendBackTxt" maxlength="200"></textarea>
                            <label id="sendBackTip" class="error"></label>
                        </div>
                        <div class="col-md-8">
                            <span class="textarea_tips">您还可以输入<b>200</b>字</span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="batchUpdateStatus();">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!--查看，通过，打回，提交-->
<div class="modal fade" id="dialog1">
    <div class="modal-dialog" style="width:680px;">
        <div class="modal-content">
            <div class="modal-header" id="dialogTitle">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">查看</h4>
            </div>
            <div class="modal-body">
                <div class="flow_inbox">
                    <div class="active" id="applyDiv">
                        <h4>提交申请</h4>
                        <span></span>
                    </div>
                    <div id="agreeDiv">
                        <h4>通过</h4>
                        <span></span>
                    </div>
                    <div id="preConfirmDiv">
                        <h4>待确认</h4>
                        <span></span>
                    </div>
                    <div id="completeDiv">
                        <h4>已完成</h4>
                        <span></span>
                    </div>
                    <p class="clr"></p>
                </div>
                <div class="data_list_col2">
                    <ul>
                        <li>
                            <span>提现单编号：</span>
                            <p id="orderCode"></p>
                        </li>
                        <li>
                            <span>申请时间：</span>
                            <p id="createTime"></p>
                        </li>
                        <li>
                            <span>用户名：</span>
                            <p id="customerUserName"></p>
                        </li>
                        <li>
                            <span>收款银行：</span>
                            <p id="bankName"></p>
                        </li>
                        <li>
                            <span>收款账号：</span>
                            <p id="bankNo"></p>
                        </li>
                        <li>
                            <span>户名：</span>
                            <p id="accountName"></p>
                        </li>
                        <li>
                            <span>联系人号码：</span>
                            <p id="contactMobile"></p>
                        </li>
                        <li>
                            <span>提现金额：</span>
                            <p id="amount" class="red"></p>
                        </li>
                        <li>
                            <span>备注：</span>
                            <p style="word-wrap: break-word;" id="remark"></p>
                        </li>
                        <li id="rejectLi">
                            <span>打回原因：</span>
                            <p id="reviewedRemark" style="word-wrap: break-word;"></p>
                        </li>
                    </ul>
                </div>
                <%--<div>
                    <span style="width:80px;float:left;text-align:right;font-weight:bold">备注：</span>
                    <p style="width:80%;float:left;padding-left:4px;word-wrap: break-word;" id="remark"></p>
                </div>
                <div id="rejectLi">
                    <span style="width:80px;float:left;text-align:right;font-weight:bold">打回原因：</span>
                    <p style="width:80%;float:left;padding-left:4px;word-wrap: break-word;" id="reviewedRemark"></p>
                </div>--%>
                <div class="data_list_col2" id="payTxtDiv">
                    <ul>
                        <li>
                            <span>付款单号：</span>
                            <p id="payBillNumTxt"></p>
                        </li>
                        <li>
                            <span>付款备注：</span>
                            <p style="word-wrap: break-word;" id="payRemarkTxt"></p>
                        </li>
                        <li>
                            <span>付款凭证：</span>
                            <p id="noPic">无</p>
                            <p id="showPic">
                                <a href="javascript:;" onclick="showBigPic()" class="show_voucher" id="voucher01" target="_blank">
                                    <img id="certificateImg" alt="">
                                    <span onclick="showBigPic()">点击查看大图</span>
                                </a>
                            </p>
                        </li>
                    </ul>
                </div>
                <div class="form_inbox form-horizontal" id="singleRejection">
                    <div class="form-group">
                        <label class="control-label col-md-4"><b>*</b> 打回原因：</label>
                        <div class="col-md-12">
                            <textarea class="form-control" rows="3" id="rejectionTitle" maxlength="200"></textarea>
                            <label id="rejectionTip" class="error"></label>
                        </div>
                        <div class="col-md-8">
                            <span class="textarea_tips">您还可以输入<b>200</b>字</span>
                        </div>
                    </div>
                </div>
                <div class="form_inbox form-horizontal" id="showPayDiv">
                    <form role="form" class="form-horizontal" method="post" enctype="multipart/form-data" id="payForm">
                        <input type="hidden" id="updateId" name="tradeInfoId"/>
                        <div class="form-group">
                            <label class="control-label col-md-4"><b>*</b> 付款单号：</label>
                            <div class="col-md-8">
                                <input type="text" id="payBillNum" name="payBillNum" class="form-control">
                                <label id="payBillNumTip" class="error"></label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-4">上传凭证：</label>
                            <div class="col-md-19">
                                <div class="voucher_upload">
                                    <div class="tips">上传付款凭证<br>如，银行汇款单</div>
                                    <div class="imgs">
                                        <div class="img_item">
                                            <img id="ImgPr" width="120" height="120" alt="上传付款凭证如，银行汇款单"/>
                                        </div>
                                    </div>
                                    <div class="upload">
                                        <input type="file" id="payImg" name="payImg">
                                        <p>支持jpg、jpeg、gif、png、bmp格式文件，不超过4M</p>
                                    </div>
                                    <div class="clr"></div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-4">备注：</label>
                            <div class="col-md-12">
                                <textarea class="form-control" id="payRemark" name="payRemark" rows="3" maxlength="200"></textarea>
                                <label id="payRemarkTip" class="error"></label>
                            </div>
                            <div class="col-md-8">
                                <span class="textarea_tips">您还可以输入<b>200</b>字</span>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="buttons">
                    <input type="hidden" id="tradeInfoId"/>
                    <button type="button" class="btn btn-primary" id="agreeBtn" onclick="singleSubmit(2);">通过</button>
                    <button type="button" class="btn btn-primary" id="sendBack" onclick="rejectionFun()">打回</button>
                    <button type="button" class="btn btn-primary" id="payMoney" onclick="showPayDiv();">打款</button>
                    <button type="button" class="btn btn-primary" id="submitBtn" onclick="singleSubmit(1)">提交</button>
                    <button type="button" class="btn btn-primary" id="paySubmitBtn" onclick="payMoneyFunc()">提交</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script src="<%=basePath %>js/summernote.min.js"></script>
<script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath %>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/common.js"></script>
<script src="<%=basePath %>js/common/common_checked.js"></script>
<script src="<%=basePath %>js/common/common_alert.js"></script>
<script src="<%=basePath %>js/customer/chargeWithdrawList.js"></script>
</body>
</html>
