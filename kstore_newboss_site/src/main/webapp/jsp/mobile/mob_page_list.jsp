<%--
  Created by IntelliJ IDEA.
  User: NP-Heh
  Date: 2015/4/1
  Time: 16:34
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
<%@ taglib uri="kstore/el-common" prefix="el" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>/css/select2.min.css" rel="stylesheet">
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
                <input type="hidden" value="${token}" class="token_val"/>
                <h2 class="main_title">${pageNameChild} <small>(共${pb.rows}条)</small></h2>

                <div class="common_data p20">
                    <div class="filter_area">
                        <input type="hidden" value="searchForm" id="formId">
                        <input type="hidden" value="queryMobPageList.htm" id="formName">
                        <form role="form" class="form-inline" id="searchForm" action="queryMobPageList.htm">
                            <input type="hidden" name="CSRFToken" id="CSRFToken" value="${token}">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">页面名称</span>
                                    <input type="text" class="form-control" name="title" value="${search.title}">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">页面分类</span>
                                    <select class="form-control cate_selector w150"  name="pageCateId">
                                        <option value="" >全部</option>
                                        <c:forEach items="${pageCateList}" var="cate">
                                            <option value="${cate.pageCateId}"  <c:if test="${cate.pageCateId==search.pageCateId}">selected="selected"</c:if>>
                                                <c:choose>
                                                    <c:when test="${fn:length(cate.name) > 10}"><c:out value="${fn:substring(cate.name ,0,10)}" />...</c:when>
                                                    <c:otherwise><c:out value="${cate.name}"/></c:otherwise>
                                                </c:choose>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">页面状态</span>
                                    <select class="form-control cate_selector w150"  name="pageState">
                                        <option value="" >全部</option>
                                        <option value="1"  <c:if test="${search.pageState==1}">selected="selected"</c:if>>已上架</option>
                                        <option value="0"  <c:if test="${search.pageState==0}">selected="selected"</c:if>>待上架</option>
                                    </select>
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
                            <button type="button" class="btn btn-info" onclick="toChoose()">
                                <i class="glyphicon glyphicon-plus"></i> 添加
                            </button>
                            <button type="button" class="btn btn-info"  onclick="delMores()">
                                <i class="glyphicon glyphicon-trash"></i> 删除
                            </button>
                        </div>
                        <div class="clr"></div>
                    </div>
                    <form id="deleBrandForm" action="batchDelBrand.htm?CSRFToken=${token }" method="post">
                    <input type="hidden" value="${pb.pageNo}" name="pageNo"/>
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th width="10"><input type="checkbox" onclick="allunchecked(this,'homePageIds')"></th>
                            <th>页面名称</th>
                            <th>页面分类</th>
                            <th>排序号</th>
                            <th>页面状态</th>
                            <th>创建时间</th>
                            <th>更新时间</th>
                            <th width="300">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pb.list}" var="homePage">
                        <tr>
                            <td><input type="checkbox" name="homePageIds" value="${homePage.homepageId }"/></td>
                            <td><c:out value="${homePage.title}"/></td>

                            <c:set var="pageCateName" value="无" scope="page"></c:set>
                            <c:set var="relPageCateName" value="无" scope="page"></c:set>
                            <c:forEach items="${pageCateList}" var="cate">
                                <c:if test="${cate.pageCateId==homePage.pageCateId}">
                                    <c:set var="pageCateName" value="${el:limitString(cate.name,10)}" scope="page"></c:set>
                                    <c:set var="relPageCateName" value="${cate.name}" scope="page"></c:set>
                                </c:if>
                            </c:forEach>
                            <td title='<c:out value="${relPageCateName}"/>'>
                                <c:out value="${pageCateName}"/>
                            </td>
                            <td><span class="switch-edit" contenteditable="false" data_id="${homePage.homepageId}" max="9999" oldValue="${homePage.sort}"><c:out value="${homePage.sort}"/></span></td>
                            <td>
                                <c:choose>
                                    <c:when test="${homePage.pageState == 1}">已上架</c:when>
                                    <c:otherwise>待上架</c:otherwise>
                                </c:choose>
                            </td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${homePage.createDate }"/></td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${homePage.updateDate }"/></td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default" onclick="toEdit(${homePage.homepageId})">
                                        <i class="glyphicon glyphicon-cog"></i>
                                        编辑
                                    </button>
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <c:choose>
                                            <c:when test="${homePage.temp2 == 1}">
                                                <li><a href="${siteAddress}" target="_blank">预览</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href="${siteAddress}page/${homePage.homepageId}" target="_blank">预览</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                        <li><a href="javascript:;" onclick="delOne(${homePage.homepageId})">删除</a></li>
                                    </ul>
                                </div>

                                <c:choose>
                                    <c:when test="${homePage.temp2 == 1}">
                                        <div class="copy-wrap">
                                            <button type="button" class="btn btn-link copy-btn" copy-url="${siteAddress}">
                                                <i class="glyphicon glyphicon-link"></i>
                                                复制链接
                                            </button>
                                        </div>
                                        <span class="current-home">主页</span>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="copy-wrap">
                                            <button type="button" class="btn btn-link copy-btn" copy-url="${siteAddress}page/${homePage.homepageId}">
                                                <i class="glyphicon glyphicon-link"></i>
                                                复制链接
                                            </button>
                                        </div>
                                        <button type="button" class="btn btn-link" onclick="toMain(${homePage.homepageId},${homePage.merchantId})">
                                            <i class="glyphicon glyphicon-home"></i>
                                            设为主页
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    </form>
                    <c:import url="../page/searchPag.jsp">
                        <c:param name="pageBean" value="${pb }"/>
                    </c:import>
                </div>

            </div>
        </div>
    </div>
</div>

<!-- Toast -->
<div class="toast-box">
    <div class="toast-mask">
        <div class="toast-dialog">复制链接成功</div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/summernote.min.js"></script>
<script src="<%=basePath%>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath%>js/jquery.zclip.js"></script>
<script src="<%=basePath%>js/bootstrap-select.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script src="<%=basePath%>js/common/common_alert.js"></script>
<script src="<%=basePath%>js/common/common_checked.js"></script>
<script src="<%=basePath %>/js/select2.min.js"></script>
<script>
    $(function(){
        // 点击切换文本可编辑状态
        $('.switch-edit').click(function(){
            $(this).attr('contenteditable', true).focus();
        });
        $('.switch-edit').blur(function(){
            var self = $(this);
            var maxNum = self.attr("max");
            maxNum = parseInt(maxNum);
            var sortNo = self.text();
            var oldValue = self.attr("oldValue");

            if(!/^\d+$/.test(sortNo)){
                showTipAlert("排序号必须为整数");
                self.text(oldValue);
                return;
            }

            sortNo = parseInt(sortNo);
            if(sortNo > maxNum){
                showTipAlert("排序号不可超过"+maxNum);
                self.text(oldValue);
                return;
            }

            var cateId = self.attr("data_id");
            $.ajax({
                url:'updateMobHomePageForShare.htm?CSRFToken=${token}&homepageId='+cateId+'&sort='+sortNo,
                async:false,
                success: function (data) {
                    if(data) {
                        showTipAlert("操作成功",function(){
                            window.location.href=window.location.href;
                        });
                    }else{
                        showTipAlert("操作失败");
                        self.text(oldValue);
                    }
                },
                error:function () {
                    showTipAlert("操作失败");
                    self.text(oldValue);
                }
            });
            // console.log($(this).text()); // $(this).text()取值
        });
        // 复制
        $('.copy-btn').zclip({
            path: '<%=basePath %>js/ZeroClipboard.swf',
            copy: function() {
                return $(this).attr("copy-url");
            },
            afterCopy: function() {
                zclipSwitch('toast-box');
            }
        });
    });

    function toChoose() {
        window.location.href='chooseMobPage.htm';
    }

    function toEdit(id) {
        window.location.href='setMobHomePage.htm?homePageId='+id;
    }

    function toMain(id,merchantId) {
        window.location.href='openHomePage.htm?merchantId='+merchantId+'&homePageId='+id;
    }

    function delOne(id) {
        id = $.trim(id);
        if(id == ''){
            showTipAlert("请至少选择一条记录！");
            return;
        }
        $.ajax({
            url:'isUsed.htm?CSRFToken=${token}&homePageIds='+id,
            async:false,
            success: function (data) {
                if(data!=null && data.res == 1) {//已使用
                    showConfirmAlert('所选择的页面正在使用中，删除会导致前端页面显示异常，是否删除？','deleteOne("'+id+'")');
                }else if(data!=null && data.res == -1){
                    showTipAlert("所选择的页面是首页不允许删除");
                }else{
                    showConfirmAlert('确定要删除此页面吗？',"deleteOne('"+id+"')");
                }
            }
        });
    }

    function deleteOne(id) {
        id = $.trim(id);
        if(id == ''){
            showTipAlert("请至少选择一条记录！");
            return;
        }
        $.ajax({
            url:'deleteMobPage.htm?CSRFToken=${token}&homePageIds='+id,
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

    function delMores() {
        var checkboxs = $(":checkbox[name='homePageIds']");
        var oneSelect = false;
        var idstr = "";
        for(var j = 0; j < checkboxs.length; j++) {
            if($(checkboxs[j]).is(':checked')==true) {
                oneSelect = true;
                idstr += "homePageIds="+ $(checkboxs[j]).val() +"&";
            }
        }
        if(!oneSelect) {
            showTipAlert("请选择一条记录！");
            return;
        }
        idstr = idstr.substr(0,idstr.length-1);
        $.ajax({
            url:'isUsed.htm?CSRFToken=${token}',
            data:idstr,
            async:false,
            success: function (data) {
                if(data!=null && data.res == 1) {//已使用
                    showConfirmAlert('所选择的页面正在使用中，删除会导致前端页面显示异常，是否删除？','deleteMore("'+idstr+'")');
                }else if(data!=null && data.res == -1){
                    showTipAlert("所选择的页面是首页不允许删除");
                }else{
                    showConfirmAlert('确定要删除所选中的页面吗？',"deleteMore('"+idstr+"')");
                }
            }
        });
    }

    function deleteMore(idstr) {
        $.ajax({
            url:'deleteMobPage.htm?CSRFToken=${token}',
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
    }
</script>

</body>
</html>

