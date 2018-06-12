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
    <link rel="<%=basePath %>stylesheet" href="css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">
    <link href="<%=basePath %>css/vsStyle/jquery.treeTable.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<style>
    .main_cont{
        background: #fff;
        overflow-y: visible;
    }
    .lianjie{
        padding-left: 160px;
        color: red;
    }
</style>
<body>
<!-- 引用头 -->
<jsp:include page="../page/header.jsp"></jsp:include>
<div class="container-fluid page_body">
    <input type="hidden" value="adv_form" id="formId">
    <form role="form" action="storelistqianggouimage.htm" method="post"  id="adv_form" class="form-inline"></form>
    <div class="row">
        <jsp:include page="../page/left.jsp"></jsp:include>
        <div class="col-lg-20 col-md-19 col-sm-18 main">
            <jsp:include page="../page/left2.jsp"></jsp:include>
            <div class="main_cont">
                <jsp:include page="../page/breadcrumbs.jsp"></jsp:include>

                <h2 class="main_title">抢购广告</h2>

                <div class="common_info order_details mt20">
                    <div role="tabpanel" class="tab-pane" id="tab4">
                        <div class="data_ctrl_area mb20">
                            <div class="data_ctrl_search pull-right"></div>
                            <div class="data_ctrl_brns pull-left">
                                <button type="button" class="btn btn-info" onclick="add_image()">
                                    <i class="glyphicon glyphicon-plus"></i> 添加
                                </button>
                                <button type="button" class="btn btn-info" onclick="doAjaxShowDeleteBatchConfirmAlert('delForm','adverId','deletetempadverajax.htm',16)">
                                    <i class="glyphicon glyphicon-trash"></i> 批量删除
                                </button>
                            </div>
                            <div class="clr"></div>
                        </div>
                        <form id="delForm">
                            <table class="table table-striped table-hover">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" onclick="allunchecked(this,'adverId')" ></th>
                                    <th>广告名称</th>
                                    <th>图片</th>
                                    <th>链接地址</th>
                                    <th>排序</th>
                                    <th>是否启用</th>
                                    <th>广告描述</th>
                                    <th width="150">操作</th>
                                </tr>
                                </thead>
                                <input type="hidden" name="CSRFToken" value="${token}"/>
                                <c:if test="${not empty pb.list}">
                                    <c:forEach var="channelAdvers" items="${pb.list}">
                                        <tbody id="adverHtml">
                                            <tr>
                                                <td><input type="checkbox" name="adverId" value="${channelAdvers.channelAdverId}"></td>
                                                <td title='<c:out value="${channelAdvers.adverName}"/>'><c:out value="${el:limitString(channelAdvers.adverName,10)}"/></td>
                                                <td><img src="${channelAdvers.adverPath}" alt="${channelAdvers.adverName}" height="50" width="160"/></td>
                                                <td>${channelAdvers.adverHref}</td>
                                                <td>${channelAdvers.adverSort}</td>
                                                <td>

                                                    <c:if test="${channelAdvers.userStatus==1}">
                                                        <span class="label label-success">是</span>
                                                    </c:if>
                                                    <c:if test="${channelAdvers.userStatus==0}">
                                                        <span class="label label-default">否</span>
                                                    </c:if>
                                                </td>
                                                <td>${channelAdvers.des}</td>
                                                <td>
                                                    <div class="btn-group">
                                                    <button type="button" class="btn btn-default" onclick="showAdver('${channelAdvers.channelAdverId}')">编辑</button>
                                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                                        <span class="caret"></span>
                                                        <span class="sr-only">Toggle Dropdown</span>
                                                    </button>
                                                    <ul class="dropdown-menu" role="menu">
                                                        <li><a href="javascript:void(0);" onclick="delAdverById('deletetempadverajax.htm?adverId='+${channelAdvers.channelAdverId})">删除</a></li>
                                                    </ul>
                                                </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </c:forEach>
                                </c:if>
                            </table>
                        </form>
                        <c:import url="../page/searchPag.jsp">
                            <c:param name="pageBean" value="${pb}"/>
                            <c:param name="path" value="../"></c:param>
                        </c:import>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="add_Success"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="modal-body" style="text-align: center;">添加成功！</label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button"   class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="update_Success"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="modal-body" style="text-align: center;">修改成功！</label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button"   class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="addSlideAd"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="showAdverTitle">添加广告</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="subForm_Adver">
                    <input type="hidden" name="CSRFToken" value="${token}"/>
                    <input type="hidden" name="temp5" id="up_temp1" value="10">
                    <input type="hidden" name="channelAdverId" id="upChannelAdverId"/>
                    <div class="form-group">
                        <label class="control-label col-sm-6"><span style="color: red;">*</span>广告名称：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <input type="text" id="up_adverName" name="adverName" class="form-control required specstr">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-6"><span style="color: red;">*</span>广告图片：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <img alt="" id="adverPath_y" src="<%=basePath %>images/default_head.jpg" height="50"> <input type="button"   onclick="saveImg(3)" value="选择"/>
                            <input name="adverPath" id="adverPath" class="required" type="hidden" value="">
                            <p class="image-tip">建议图片宽度: 900 x 620像素 <br/>支持图片格式: jpg,jpeg,png,bmp,gif</p>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-6"><span style="color: red;">*</span>广告链接地址：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <input type="text" id="up_adverHerf" name="adverHref" class="form-control required url">
                        </div>
                        <sapn></sapn>
                    </div>
                    <p class="lianjie">注:链接地址以https开头,例:https://www.baidu.com/</p>
                    <div class="form-group">
                        <label class="control-label col-sm-6"><span style="color: red;">*</span>排序：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-4">
                            <input type="text" id="up_adverSort"
                                   onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                                   onafterpaste="this.value=this.value.replace(/[^\d]/"

                                   name="adverSort" class="form-control w100 digits  isNumber  required specstr">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-6">是否启用：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <label class="radio-inline">
                                <input type="radio" id="up_adverStatus1" name="userStatus" checked="checked" value="1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" id="up_adverStatus0" name="userStatus" value="0"> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-6">广告描述：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <textarea rows="5" id="up_adverDes" name="des" class="form-control"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="subAdverButton" onclick="submitAdver()" class="btn btn-primary">确定</button>
                <button type="button"   class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="addFloorBrands"  role="dialog"></div>

<input type="hidden" id="CSRFToken" value="${token}"/>

<input type="hidden" id="imgPath" value="">

<input type="hidden" id="imgPathValue" value="">
<input type="hidden" id="isAdver" value="">
<input type="hidden" id="isBrand" value="">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script src="<%=basePath %>js/summernote.min.js"></script>
<script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath %>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath %>js/jquery.ztree.core-3.5.min.js"></script>
<script src="<%=basePath %>js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="<%=basePath %>js/jqtreetable.js"></script>
<script src="<%=basePath %>js/common.js"></script>
<script src="<%=basePath %>/js/common/common_alert.js"></script>
<script src="<%=basePath %>/js/common/common_temp_alert.js"></script>
<script src="<%=basePath %>/js/common/common_checked.js"></script>
<script src="<%=basePath %>/js/temp/tag.js"></script>
<script>
    /**
     * 去添加广告之前
     */
    function add_image(){
        $('#showAdverTitle').html("添加广告");

        $('#up_adverName').val('');       //图片名称
        $("#adverPath_y").attr("src", ''); //图片路径用于显示
        $("#adverPath").val(''); //图片路径用于保存
        $('#up_adverSort').val('');       //排序
        $('#up_adverHerf').val('');       //排序
        $("#up_adverStatus1").prop("checked", "checked");
        $('#up_adverDes').val('');      //广告描述
        $('#upChannelAdverId').val(''); //广告ID
        $('#subAdverButton').attr("onclick", "submitAdver()");
        $('#addSlideAd').modal('show')


    }


    var num=0;
    function submitAdver(){
        if($("#subForm_Adver").valid()&&num==0){
            num+=1;
            $.ajax({
                cache: true,
                type: "POST",
                url:"createtempadverajax.htm",
                data:$('#subForm_Adver').serialize(),// 你的formid
                async: false,
                success: function(data) {
                    if(data==1){
                        //隐藏掉添加大广告页面
                        $('#addSlideAd').modal('hide');
                        //显示提示窗
                        $('#add_Success').modal('show');
                        location.href='storelistqianggouimage.htm';
                    }
                }
            });
        }

    }


    function saveImg(obj){
        art.dialog.open('queryImageManageByPbAndCidForChoose.htm?CSRFToken='+$(CSRFToken).val()+'&size=10000', {
            lock: true,
            opacity:0.3,
            width: '900px',
            height: '620px',
            title: '选择图片'
        });
    }


    function saveChoooseImage(url) {
        if(typeof (url) != 'string') {
            url = url[0];
        }

        $("#adverPath_y").attr("src", url);
        $("#adverPath").val(url);
    }

    /*删除单个广告信息*/
    function delAdverById(adverIdUrl){
        showAjaxDeleteConfirmAlertByTemp(adverIdUrl,16);
    }

    function showAdver(channelAdverId){
        $.post("showtempadverajax.htm", {channelAdverId: channelAdverId},
                function (data) {
                    $('#up_adverName').val(data.adverName);       //图片名称
                    $("#adverPath_y").attr("src", data.adverPath); //图片路径用于显示
                    $("#adverPath").val(data.adverPath); //图片路径用于保存
                    $('#up_adverHerf').val(data.adverHref);       //图片链接地址
                    $('#up_adverSort').val(data.adverSort);       //排序
                    if (data.userStatus == 1) {  //是否启用
                        $("#up_adverStatus1").prop("checked", "checked");
                    } else {
                        $("#up_adverStatus0").prop("checked", "checked");
                    }
                    $('#up_adverDes').val(data.des);      //广告描述
                    $('#upChannelAdverId').val(data.channelAdverId); //广告ID
                    $('#subAdverButton').attr("onclick", "updateAdver()"); //指定请求的action
                    $('#showAdverTitle').html('编辑广告');
                    $('#addSlideAd').modal('show');
                });
    }

    /**修改广告**/
    function updateAdver() {

        if ($("#subForm_Adver").valid()) {
            $.ajax({
                cache: true,
                type: "POST",
                url: "updatetempadverajax.htm",
                data: $('#subForm_Adver').serialize(),// 你的formid
                async: false,
                error: function (request) {
                    showTipAlert("Connection error");
                },
                success: function (data) {
                    if (data == 1) {
                        $('#addSlideAd').modal('hide');
                        $('#update_Success').modal('show');
                        location.href = 'storelistqianggouimage.htm';
                    }
                }
            });
        }
    }
</script>
</body>
</html>

