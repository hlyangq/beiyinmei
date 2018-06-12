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
    <title>支付方式</title>

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
                <input type="hidden" name="CSRFToken" id="CSRFToken" value="${token}">
                <div class="box_method p20">
                    <c:forEach  var="pay" items="${pb.list }" varStatus="status">
                        <div class="method_item <c:if test="${pay.isOpen eq 0 }">disabled</c:if> ">
                            <h4>${pay.payName }</h4>
                            <img src="${pay.payImage }" alt="">
                            <div class="bar">
                                <div class="links">
                                    <a href="#" onclick="showEditForm(${pay.payType},${pay.payId})">编辑</a>
                                    <c:if test="${pay.payType==1}">
                                        <a href="javascript:;" onclick="$('#multiArticle${pay.payType}').modal('show')">帮助</a>
                                    </c:if>
                                    <c:if test="${pay.payType==2}">
                                        <a href="javascript:;" onclick="$('#multiArticle${pay.payType}').modal('show')">帮助</a>
                                    </c:if>
                                    <c:if test="${pay.payType==3}">
                                        <a href="javascript:;" onclick="$('#multiArticle${pay.payType}').modal('show')">帮助</a>
                                    </c:if>
                                    <%--<c:if test="${pay.payType==4}">
                                        <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2853573863&site=qq&menu=yes">帮助</a>
                                    </c:if>--%>
                                </div>
                                <c:if test="${pay.isOpen == 1 }">
                                    <div class="status">已启用</div>
                                </c:if>
                                <c:if test="${pay.isOpen == 0 }">
                                    <div class="status">未启用</div>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="clr"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 支付宝 editModal1中的1表示支付类型是支付宝-->
<div class="modal fade" id="editModal1"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑支付接口</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" method="post" action="updatepay.htm?CSRFToken=${token}" enctype="multipart/form-data" id="editForm1">
                    <input type="hidden" name="payId" id="payId1" />
                    <%--<div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>支付名称：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="payName" id="up_payname1" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">选择图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <p class="pt5"><input type="file" name="netLogo"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">预览图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <img height="50" alt="" src="" id="preview_pic1" style="background-color:#0086CF">
                        </div>
                    </div>
                    --%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">支付问题描述：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <button type="button" class="btn btn-default" onclick="editpayhelp(1)">查看并修改</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>Api-Key：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="apiKey" id="up_apikey1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>Secret-Key：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="secretKey" id="up_secrect1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>收款账号：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="payAccount" id="up_account1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>后台回调地址：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required" name="payUrl" id="up_payurl1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>前台回调地址：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required" name="backUrl" id="up_backurl1">
                        </div>
                    </div>
                   <%-- <div class="form-group">
                        <label class="control-label col-sm-5">支付类型：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <select class="form-control" data-live-search="true" name="payType">
                                <option value="1">支付宝</option>
                            </select>
                        </div>
                    </div>--%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">手机支付回调：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control specstr" name="payComment" id="up_comment1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">是否启用：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open13" value="1" onclick="showDefultRadio(1,1)"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open14" value="0" onclick="showDefultRadio(1,0)" checked> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="defaultDiv1">
                        <label class="control-label col-sm-5">是否设为默认：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open11" value="1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open12" value="0" checked> 否
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submitEditForm(1)">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 银联 editModal2中的2表示支付类型是银联-->
<div class="modal fade" id="editModal2"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑支付接口</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" method="post" action="updatepay.htm?CSRFToken=${token}" enctype="multipart/form-data" id="editForm2">
                    <input type="hidden" name="payId" id="payId2" />
                    <%--<div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>支付名称：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="payName" id="up_payname2" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">选择图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <p class="pt5"><input type="file" name="netLogo"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">预览图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <img height="50" alt="" src="" id="preview_pic2" style="background-color:#0086CF">
                        </div>
                    </div>
                    --%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">支付问题描述：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <button type="button" class="btn btn-default" onclick="editpayhelp(2)">查看并修改</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>Api-Key：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="apiKey" id="up_apikey2">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>收款账号：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="payAccount" id="up_account2">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>后台回调地址：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required" name="payUrl" id="up_payurl2">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>前台回调地址：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required" name="backUrl" id="up_backurl2">
                        </div>
                    </div>
                    <%--<div class="form-group">
                        <label class="control-label col-sm-5">支付类型：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <select class="form-control" data-live-search="true">
                                <option>银联在线</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">备注：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control specstr" name="payComment" id="up_comment2">
                        </div>
                    </div>--%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">是否启用：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open23" value="1" onclick="showDefultRadio(2,1)"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open24" value="0" onclick="showDefultRadio(2,0)" checked> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="defaultDiv2">
                        <label class="control-label col-sm-5">是否设为默认：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open21" value="1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open22" value="0" checked> 否
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submitEditForm(2)">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 微信 editModal3中的3表示支付类型是微信-->
<div class="modal fade" id="editModal3"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑支付接口</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" method="post" action="updatepay.htm?CSRFToken=${token}" enctype="multipart/form-data" id="editForm3">
                    <input type="hidden" name="payId" id="payId3" />
                    <%--<div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>支付名称：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="payName" id="up_payname3" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">选择图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <p class="pt5"><input type="file" name="netLogo"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">预览图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <img height="50" alt="" src="" id="preview_pic3" style="background-color:#0086CF">
                        </div>
                    </div>
                    --%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">支付问题描述：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <button type="button" class="btn btn-default" onclick="editpayhelp(3)">查看并修改</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>公众号AppID：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="apiKey" id="up_apikey3">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>AppSecret：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="secretKey" id="up_secrect3">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>API密钥：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="partner" id="up_partner3">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>商户标识：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="partnerKey" id="up_partnerKey3">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>通知URL：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required" name="backUrl" id="up_backurl3">
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-sm-5">支付类型：</label>--%>
                        <%--<div class="col-sm-1"></div>--%>
                        <%--<div class="col-sm-16">--%>
                            <%--<select class="form-control" data-live-search="true">--%>
                                <%--<option value="3">微信支付</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-sm-5">备注：</label>--%>
                        <%--<div class="col-sm-1"></div>--%>
                        <%--<div class="col-sm-16">--%>
                            <%--<input type="text" class="form-control specstr" name="payComment" id="up_comment3">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">是否启用：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open33" value="1" onclick="showDefultRadio(3,1)"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open34" value="0" onclick="showDefultRadio(3,0)" checked> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="defaultDiv3">
                        <label class="control-label col-sm-5">是否设为默认：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open31" value="1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open32" value="0" checked> 否
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submitEditForm(3)">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 千米收银台 editModal4中的3表示支付类型是千米收银台-->
<div class="modal fade" id="editModal4"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑支付接口</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" method="post" action="updatepay.htm?CSRFToken=${token}" enctype="multipart/form-data" id="editForm4">
                    <input type="hidden" name="payId" id="payId4" />
                    <%--<div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>支付名称：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="payName" id="up_payname4" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">选择图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <p class="pt5"><input type="file" name="netLogo"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">预览图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <img height="50" alt="" src="" id="preview_pic4" style="background-color:#0086CF">
                        </div>
                    </div>
                    --%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">支付问题描述：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <button type="button" class="btn btn-default" onclick="editpayhelp(4)">查看并修改</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>购买方编号：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="payAccount" id="up_account4">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>上级编号：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="secretKey" id="up_secrect4">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>key：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="apiKey" id="up_apikey4">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>域名：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required" name="payUrl" id="up_payurl4">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>通知URL：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required" name="backUrl" id="up_backurl4">
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-sm-5">支付类型：</label>--%>
                        <%--<div class="col-sm-1"></div>--%>
                        <%--<div class="col-sm-16">--%>
                            <%--<select class="form-control" data-live-search="true">--%>
                                <%--<option value="4">千米收银台</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-sm-5">备注：</label>--%>
                        <%--<div class="col-sm-1"></div>--%>
                        <%--<div class="col-sm-16">--%>
                            <%--<input type="text" class="form-control specstr" name="payComment" id="up_comment4">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">是否启用：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open43" value="1" onclick="showDefultRadio(4,1)"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open44" value="0" onclick="showDefultRadio(4,0)" checked> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="defaultDiv4">
                        <label class="control-label col-sm-5">是否设为默认：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open41" value="1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open42" value="0" checked> 否
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submitEditForm(4)">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 货到付款|预存款支付 editModal4中的5表示支付类型是预存款支付，6表示支付类型是货到付款-->
<div class="modal fade" id="editModal5"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑支付接口</h4>
            </div>
            <div class="modal-body">
                <%--<form role="form" class="form-horizontal" method="post" action="updatepay.htm?CSRFToken=${token}" enctype="multipart/form-data" id="editForm5">--%>
                <form role="form" class="form-horizontal" enctype="multipart/form-data" id="editForm5">
                    <input type="hidden" name="payId" id="payId5" />
                    <%--<div class="form-group">
                        <label class="control-label col-sm-5"><span style="color: red;">*</span>支付名称：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="payName" id="up_payname5" >
                        </div>
                    </div>--%>
                    <div class="form-group">
                        <label class="control-label col-sm-5">是否启用：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open53" value="1" onclick="showDefultRadio(0,1)"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="isOpen" id="open54" value="0" onclick="showDefultRadio(0,0)" checked> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="defaultDiv">
                        <label class="control-label col-sm-5">是否设为默认：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open51" value="1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="payDefault" id="open52" value="0" checked> 否
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submitEditForm(0)">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<!--支付宝帮助,payType = 1-->
<div class="modal fade" id="multiArticle1"  role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">支付宝接口配置流程</h4>
            </div>
            <div class="modal-body">
                <div class="modal-article">
                    <p><em>1.</em>首先到支付宝服务市场注册支付宝账号（<a href="https://app.alipay.com/market/index.htm" target="_blank">https://app.alipay.com/market/index.htm</a>），注册成功后，到产品中心分别申请手机网站支付和即时到账服务</p>
                    <img src="./images/syshelp/zfbydzf.jpg" alt="">
                    <img src="./images/syshelp/zfbjsdz.jpg" alt="">
                    <p><em>2.</em>成功添加后，进入商户服务</p>
                    <img src="./images/syshelp/zfb2.png" alt="">
                    <p><em>3.</em>根据上图获取PID和KEY，然后到后台修改支付宝信息，如下图</p>
                    <img src="./images/syshelp/newzhifubao.jpg" alt="">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!--微信支付帮助,payType = 3-->
<div class="modal fade" id="multiArticle3"  role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">微信支付</h4>
            </div>
            <div class="modal-body">
                <div class="modal-article">
                    <p><em>1.</em>首先要申请<b>微信服务号</b>，申请成功后申请微信支付，微信支付申请成功后，用户会收到一份邮件，内容如下：</p>
                    <img src="./images/syshelp/img_04.png" alt="">
                    <p><em>2.</em> 然后用户通过上图提供的商户平台登录账号和商户平台登录密码登录商户平台（<a href="https://mch.weixin.qq.com">https://mch.weixin.qq.com</a>），登录后到账户设置中设置秘钥，密码必须是32位。</p>
                    <p><em>3.</em> 到后台修改微信支付接口，标注红色箭头的地方改成自己的</p>
                    <img src="./images/syshelp/wxzfjk1.png" alt="">
                    <p><em>4.</em>最后到微信公众号后台进行对应修改，点击微信支付中的开发配置，添加支付授权目录，格式如下</p>
                    <img src="./images/syshelp/kfpz.jpg" alt="">
                    <img src="./images/syshelp/zfsqml.jpg" alt="">
                    <p>最后点击保存，微信支付配置成功了</p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!--银联支付帮助,payType = 2-->
<div class="modal fade" id="multiArticle2"  role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">银联支付</h4>
            </div>
            <div class="modal-body">
                <div class="modal-article">
                    <p>申请<b>企业网银支付</b>（<a href="https://static.95516.com/static/merchant/service/index.html" target="_blank">https://static.95516.com/static/merchant/service/index.html</a>）</p>
                    <img src="./images/syshelp/img_yl_001.jpg" alt="">
                    <img src="./images/syshelp/img_yl_002.jpg" alt="">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="rightinfo"  role="dialog">
    <input type="hidden" id="helpPayId">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑支付问题描述</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal">
                    <div class="summernote" id="payHelp"></div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="savePayHelp('payHelp',this)">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/summernote.min.js"></script>
<script src="<%=basePath%>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script src="<%=basePath%>js/common/common_alert.js"></script>
<script src="<%=basePath%>js/system/system_common.js"></script>
<script src="<%=basePath%>js/system/payset.js"></script>
<script src="<%=basePath%>js/system/payment.js"></script>
</body>
</html>
