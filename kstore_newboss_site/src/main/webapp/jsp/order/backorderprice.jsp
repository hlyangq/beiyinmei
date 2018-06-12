<%--
  Created by IntelliJ IDEA.
  User: Zhoux
  Date: 2015/6/16
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-cn">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>退单详情</title>
    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>

    <![endif]-->

    <style>
        li{list-style:none;}
        .sf-tit {margin:20px 0;}
        .sf-item {margin-bottom:20px;margin-top:20px;}
        .sf-item input[type="radio"] {vertical-align:middle;margin-right:5px;}
        .sf-item label {margin-right:20px;}
        .sf-item input[type="text"] {border:1px solid #ccc;width:150px;height:25px;}
        .sf-btns input {padding:5px 10px;}
        .sf-item textarea {vertical-align:top;border:1px solid #ccc;width:250px;height:50px;}
        .sf-btns {padding-left:75px;}
        .track-info{padding-left:300px;color:#afafaf}
        .track-info .track-date,.track-info i,.track-info .track-cont{display:inline-block;*display:inline;*zoom:1;vertical-align:middle;vertical-align:bottom}
        .track-info .track-date{width:160px;text-align:right}
        .track-info i{background:url(../images/track-bg.png) no-repeat left bottom;width:14px;height:40px;margin:0 20px}
        .track-info .current .track-date{color:#e64f25}
        .track-info .current .track-cont{color:#333}
        .track-info .current .track-cont em{color:#e64f25;font-style:normal}
        .track-info .latest-track i{background:url(../images/track-circle.png) no-repeat;width:14px;height:14px}
    </style>
</head>
<body>
<div class="mb20 container-fluid">
    <table class="table table-striped table-hover table-bordered">
        <thead>
        <tr>
            <th>退款原因</th>
            <th>申请凭据</th>
            <th>退款金额</th>
            <th>问题说明</th>
            <th>上传凭证</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <c:if test="${backorder.backReason eq '1'}">不想买了</c:if>
                <c:if test="${backorder.backReason eq '2'}">收货人信息有误</c:if>
                <c:if test="${backorder.backReason eq '3'}">未按指定时间发货</c:if>
                <c:if test="${backorder.backReason eq '4'}">其他</c:if>
            </td>
            <td>
                <c:if test="${backorder.applyCredentials eq '1'}">有发票</c:if>
                <c:if test="${backorder.applyCredentials eq '2'}">有质检报告</c:if>
                <c:if test="${backorder.applyCredentials eq '3'}">无任何凭据</c:if>
            </td>
            <td>${backorder.backPrice}</td>
            <c:if test="${fn:length(backorder.backRemark) gt '6'}">
                <td title="${backorder.backRemark}">${fn:substring(backorder.backRemark,0 ,6)}...</td>
            </c:if>
            <c:if test="${fn:length(backorder.backRemark) le '6'}">
                <td>${backorder.backRemark}</td>
            </c:if>
            <td>
                <c:forEach items="${backorder.imgs }" var="img">
                    <c:if  test="${img != ''}">
                    <li><a href="${img}" target="_blank"><img src="${img}" alt="" style="width:30px;height:30px;"/></a></li>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="sf-tit"><b>操作日志</b></div>
    <div class="item-cont">
        <div class="track-info"  style="padding-left: 130px;">
            <c:if test="${backOrderLogs ne null}">
                <c:forEach items="${backOrderLogs}" var="backOrderLog">
                    <c:if test="${backOrderLog.backLogStatus eq '9'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">拒绝退款 (操作：平台)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                    <c:if test="${backOrderLog.backLogStatus eq '8'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">退款${backorder.backPrice}元成功 (操作：平台)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                    <c:if test="${backOrderLog.backLogStatus eq '7'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">退款${backorder.backPrice}元成功 (操作：平台)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                    <c:if test="${backOrderLog.backLogStatus eq '6'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">拒绝收货 (操作：平台)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                    <c:if test="${backOrderLog.backLogStatus eq '5'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">确认收货(操作：平台)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                    <c:if test="${backOrderLog.backLogStatus eq '4'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">填写快递信息(操作：顾客)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                    <c:if test="${backOrderLog.backLogStatus eq '3'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">拒绝退单(操作：平台)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                    <c:if test="${backOrderLog.backLogStatus eq '2'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">同意退单(操作：平台)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                    <c:if test="${backOrderLog.backLogStatus eq '1'}">
                        <p>
                            <span class="track-date"><fmt:formatDate value="${backOrderLog.backLogTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            <i></i>
                            <span class="track-cont">申请退单审核(操作：顾客)<c:if test="${backOrderLog.backRemark != null}">留言：${backOrderLog.backRemark}</c:if></span>
                        </p>
                    </c:if>
                </c:forEach>
            </c:if>
        </div>
    </div>

<c:if test="${businessId eq '0'}">
    <form id="submitForm" action="newsavebackorderdetail.htm" type="post" method="post">
        <div class="sf-tit"><b>操作（重要）</b></div>
        <div class="sf-item">
            <c:if test="${backorder.isBack eq '2'}">
                <c:if test="${backorder.backCheck eq '6'}">
                    <label><input type="radio" id="backLogStatus1" checked="checked" name="backLogStatus" class="backLogStatus" value="8">同意退款</label>
                    <label><input type="radio" id="backLogStatus2" name="backLogStatus" class="backLogStatus" value="9">拒绝退款</label>

                    <%--<p class="back_price">--%>
                        <%--<label>*退款金额:</label>--%>
                        <%--<span><input type="text" name="backPrice" style="width:100px;"--%>
                                     <%--onkeyup="if(isNaN(value))execCommand('undo')"--%>
                                     <%--onafterpaste="if(isNaN(value))execCommand('undo')"--%>
                                <%--></span>--%>
                    <%--</p>--%>

                    <span class="remark_contents" style="color:red;"></span>
                    <div class="sf-item">
                        <span>给客户留言：</span>
                        <textarea class="customerRemark" name="backRemark" maxlength="255"></textarea>
                    </div>
                    <input type="hidden" name="backOrderId" value="${backorder.backOrderId}">
                    <input type="hidden" name="orderId" value="${orderId}">
                    <div class="sf-btns">
                        <input type="button" value="保存" onclick="saveorderprice();">
                        <input type="button" value="取消" onclick="closeprice();">
                    </div>
                </c:if>
            </c:if>
        </div>
    </form>
    <input type="hidden" class="flag_saved" value="0">
</c:if>
</div>
<script src="<%=basePath %>js/jquery.min.js"></script>
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script src="<%=basePath %>js/summernote.min.js"></script>
<script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath %>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath %>js/common.js"></script>
<script src="<%=basePath %>js/order/backorderlist.js"></script>
<script>

    $("input[name=backRemark]").keypress(function (event){
        if(event.keyCode==13) {
            event.preventDefault();
        }
    })

</script>
<script>
    $(function(){

        $('#mainFrame').load(function(){
            alert($('#mainFrame').contents());
        });

        $('#backLogStatus2').click(function(){
            $(".remark_contents").text("");
            $('.back_price').hide();
        });
        $('#backLogStatus1').click(function(){
            $(".remark_contents").text("");
            $('.back_price').show();
        });
    });
</script>
</body>
</html>
