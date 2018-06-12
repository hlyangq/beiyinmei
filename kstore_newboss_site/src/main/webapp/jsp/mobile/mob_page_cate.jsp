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
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>


    <!-- Bootstrap -->
    <link href="<%=basePath %>/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>/css/font-awesome.min.css">
    <link href="<%=basePath %>/iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>/css/style.css" rel="stylesheet">
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
<input type="hidden" value="queryMobPageCate" id="formId">
<input type="hidden" value="queryMobPageCateByPageBean.htm" id="formName">
<div class="page_body container-fluid">
    <div class="row">
        <!-- 引用昨天导航 -->
        <jsp:include page="../page/left.jsp"></jsp:include>

        <div class="col-lg-20 col-md-19 col-sm-18 main">
            <jsp:include page="../page/left2.jsp"></jsp:include>
            <div class="main_cont">
                <jsp:include page="../page/breadcrumbs.jsp"></jsp:include>

                <h2 class="main_title">${pageNameChild} <small>(共${pb.rows}条)</small></h2>
                <div class="common_data p20">

                    <div class="filter_area">
                        <form role="form" class="form-inline" id="queryMobPageCate" method="post">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">分类名称</span>
                                        <input type="text" value="${sname}" name="name" class="form-control" >
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">搜索</button>
                            </div>
                        </form>
                    </div>
                    <div class="data_ctrl_area mb20">
                        <div class="data_ctrl_search pull-right"></div>
                        <div class="data_ctrl_brns pull-left">
                            <button type="button" class="btn btn-info" onclick="$('#addSpecialTopic').modal('show')">
                                <i class="glyphicon glyphicon-plus"></i> 添加
                            </button>
                            <button type="button" class="btn btn-info" onclick="deleteMore()">
                                <i class="glyphicon glyphicon-trash"></i> 删除
                            </button>
                        </div>
                        <div class="clr"></div>
                    </div>
                    <table class="table table-striped table-hover" >
                        <thead>
                        <tr>
                            <th>
                                <input type="checkbox" onclick="allunchecked(this,'pageCateIds')">

                            </th>
                            <th>分类名称</th>
                            <th>页面数</th>
                            <th>更新时间</th>
                            <th width="150">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="pageCate" items="${pb.list }" varStatus="status">
                        <tr>
                            <td><input type="checkbox" name="pageCateIds"  value="${pageCate.pageCateId }"/><input type="hidden" value="${pageCate.homePageNum}"/></td>
                            <td title='<c:out value="${pageCate.name}"/>'>
                                <c:choose>
                                    <c:when test="${fn:length(pageCate.name) > 10}"><c:out value="${fn:substring(pageCate.name ,0,10)}" />...</c:when>
                                    <c:otherwise><c:out value="${pageCate.name}"/></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                ${pageCate.homePageNum}
                            </td>
                            <td>
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${pageCate.updateDate }"/>
                            </td>
                            <td>
                                <div class="btn-group">

                                    <button type="button" class="btn btn-default" onclick="updatePageCate(${pageCate.pageCateId })">
                                        <i class="glyphicon glyphicon-cog"></i>
                                        编辑
                                    </button>
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <c:choose>
                                            <c:when test="${pageCate.homePageNum > 0}">
                                                <li><a href="javascript:;" onclick="showConfirmAlert('当前分类有页面，如点击删除会引起页面分类为空，确认删除？','deleteOne(${pageCate.pageCateId})')">删除</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href="javascript:;" onclick="showConfirmAlert('确定要删除此分类吗？','deleteOne(${pageCate.pageCateId})')">删除</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:import url="../page/searchPag.jsp">
                        <c:param name="pageBean" value="${pb }"/>
                    </c:import>

                </div>

            </div>
        </div>
    </div>
</div>

<%--修改页面分类--%>
<div class="modal fade" id="updateSpecialTopic"  role="dialog">
    <div class="modal-dialog modal-lg">
        <form class="form-horizontal" id="pageCateUpForm" method="post" action="updateMobPageCate.htm" >
            <input type="hidden" name="CSRFToken" id="CSRFToken" value="${token}">
            <input type="hidden" name="pageCateId" id="up_pageCateId" value=""/>
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 id="update_title" class="modal-title">修改页面分类</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="control-label col-sm-4"><span class="text-danger">*</span>分类名称：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-10">
                            <input type="text" id="up_name" class="form-control required className" name="name" maxlength="50">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">备注：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-10">
                            <textarea name="remark" id="up_remark" class="form-control classRemark" rows="5" maxlength="255"></textarea>
                        </div>
                        <div class="col-sm-3">
                            <a href="javascript:;" class="zhuantiseodesc help_tips">
                                <i class="icon iconfont">&#xe611;</i>
                            </a>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="updateCate()">确定</button>
                    <button type="button" onclick="cls()" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>


<%--新增页面分类--%>
<div class="modal fade" id="addSpecialTopic"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <form role="form" class="form-horizontal" id="addCateForm"  method="post" action="addMobPageCate.htm" >
            <input type="hidden" name="CSRFToken"  value="${token}">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4  class="modal-title">添加页面分类</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="control-label col-sm-4"><span class="text-danger">*</span>分类名称：</label>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-10">
                        <input type="text" class="form-control required className" name="name" maxlength="50">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">备注：</label>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-10">
                        <textarea name="remark" class="form-control classRemark" rows="5" maxlength="255"></textarea>
                    </div>
                    <div class="col-sm-3">
                        <a href="javascript:;" class="zhuantiseodesc help_tips">
                            <i class="icon iconfont">&#xe611;</i>
                        </a>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="addCate()">确定</button>
                <button type="button" onclick="cls()" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
        </form>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath %>js/common/common_checked.js"></script>
<script src="<%=basePath %>/js/bootstrap.min.js"></script>
<script src="<%=basePath %>/js/common.js"></script>
<script src="<%=basePath %>js/common/common_alert.js"></script>
<script src="<%=basePath %>js/common/common_checked.js"></script>

<script>
    $(function(){
        $('.zhuantiseodesc').popover({
            content : '最大字数255',
            trigger : 'hover'
        });
    });

    function updatePageCate(cateId){
        $.post("ajaxShowMobPageCate.htm?CSRFToken=${token}", { pageCateId:cateId },
                function(data){
                    $('#up_pageCateId').val(data.pageCateId);
                    $('#up_name').val(data.name);
                    $('#up_remark').val(data.remark);
                });
        $('#updateSpecialTopic').modal('show');
    }

    function addCate(){
        if($("#addCateForm").valid()){
            $("#addCateForm").submit();
        }
    }

    function updateCate(){
        if($("#pageCateUpForm").valid()){
            $("#pageCateUpForm").submit();
        }
    }

    function cls() {
        $(".className").val('');
        $(".classRemark").val('');
    }

    function deleteOne(id) {
        id = $.trim(id);
        if(id == ''){
            showTipAlert("请至少选择一条记录！");
            return;
        }
        $.ajax({
            url:'delMobPageCate.htm?CSRFToken=${token}&ids='+id,
            async:false,
            success: function (data) {
                if(data!=null && data.res == 1){
                    showTipAlert("删除成功");
                    window.location.href=window.location.href;
                }else{
                    showTipAlert("操作失败");
                }
            }
        });
    }

    function deleteMore() {
        var checkboxs = $(":checkbox[name='pageCateIds']:checked");
        if(checkboxs.size()<1) {
            showTipAlert("请选择一条记录！");
            return;
        }

        var idstr = "";
        var isHavPage = false;

        $(checkboxs).each(function (i,p) {
            idstr += "ids="+ $(p).val() +"&";
            if($(p).next(":hidden").val()>0){
                isHavPage = true;
            }
        });
        idstr = idstr.substr(0,idstr.length-1);

        if(isHavPage){
            simpleConfirm("所要删除的分类下有页面，如点击删除会引起页面分类为空，确认删除？",function(){
                $.ajax({
                    url:'delMobPageCate.htm?CSRFToken=${token}',
                    data:idstr,
                    async:false,
                    success: function (data) {
                        if(data!=null && data.res >= 1){
                            showTipAlert("删除成功");
                            window.location.href=window.location.href;
                        }else{
                            showTipAlert("操作失败");
                        }
                    }
                });
            });
        }else{
            simpleConfirm("确定要删除所选中的分类吗？",function(){
                $.ajax({
                    url:'delMobPageCate.htm?CSRFToken=${token}',
                    data:idstr,
                    async:false,
                    success: function (data) {
                        if(data!=null && data.res >= 1){
                            showTipAlert("删除成功");
                            window.location.href=window.location.href;
                        }else{
                            showTipAlert("操作失败");
                        }
                    }
                });
            });
        }
    }
</script>
</body>
</html>
