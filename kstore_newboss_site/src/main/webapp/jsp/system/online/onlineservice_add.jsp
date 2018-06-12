<%--
  Created by IntelliJ IDEA.
  User: Zhoux
  Date: 2015/3/25
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>在线客服</title>
    <!-- Bootstrap -->
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css">
    <link href="<%=basePath%>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath%>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath%>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath%>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/select2.min.css" rel="stylesheet">
    <link href="<%=basePath%>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">
    <style>
        input[type="radio"], input[type="checkbox"] {margin: 1px 0 0;}
    </style>
</head>
<body>
<!-- 引用头 -->
<jsp:include page="../../page/header.jsp"></jsp:include>

<div class="container-fluid page_body">
    <div class="row">
        <jsp:include page="../../page/left.jsp"></jsp:include>
        <div class="col-lg-20 col-md-19 col-sm-18 main">
            <jsp:include page="../../page/left2.jsp"></jsp:include>
            <div class="main_cont">
                <jsp:include page="../../page/breadcrumbs.jsp"></jsp:include>
                <h2 class="main_title">${pageNameChild}</h2>
                <div class="common_form common_form_max p20">
                    <div class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <strong>注意!</strong> 管理员可为网站添加配置客服联系方式，用于首页显示。
                    </div>

                    <div class="box_method p20">
                        <div class="method_item <c:if test='${onLineService.onlineIndex == "N"}'>disabled</c:if>">
                            <h4>QQ客服</h4>
                            <img src="images/online_qq.png"/>
                            <div class="bar">
                                <div class="links">
                                    <a href="javascript:;" onclick="editCustomerService('QQ');">编辑</a>
                                </div>
                                <div class="status">
                                    <c:if test='${onLineService.onlineIndex == "Y" && kstList.isuseing == "1"}'>已启用</c:if>
                                    <c:if test='${onLineService.onlineIndex == "N" || onLineService.onlineIndex == "" || onLineService.onlineIndex == null}'>未启用</c:if>
                                </div>
                            </div>
                        </div>

                        <div class="method_item <c:if test='${kstList.isuseing == "1"}'>disabled</c:if>">
                            <h4>快商通客服</h4>
                            <img src="images/kuaishangtong.png"/>
                            <div class="bar">
                                <div class="links">
                                    <a href="javascript:;" onclick="editCustomerService('KST')">编辑</a>
                                </div>
                                <div class="status">
                                    <c:if test='${kstList.isuseing == "0" && onLineService.onlineIndex == "N"}'>已启用</c:if>
                                    <c:if test='${kstList.isuseing == "1" || kstList.isuseing == "" || kstList.isuseing == null}'>未启用</c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <c:if test="${msg !=null && msg!='' }"><input type="hidden" class="errorMessage" value="${msg}"/></c:if>
                <!-- Modal -->
                <div class="modal fade" id="editCustomerServiceForQQ" role="dialog">

                </div>

                <!-- Modal -->
                <div class="modal fade" id="editCustomerServiceForKST" role="dialog">

                </div>

            </div>
        </div>
    </div>
</div>
</div>
</div>

<%--切换客服系统弹出框--%>
<div class="modal fade" id="chooseOnlineService"  role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 style="text-align:center;" class="modal-title">客服提示</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <p style="text-align:center;" id="chooseMsg"></p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary"  onclick="changeChoose()"  type="button">确定</button>
                <button class="btn btn-primary"  onclick="deleteChoose()"  type="button" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="errorT"  role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 style="text-align:center;" class="modal-title">错误提示</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <p style="text-align:center;"> <c:if test="${msg !=null && msg!='' }">${msg}</c:if></p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>

<%--删除弹出框--%>
<div class="modal fade" id="delete_one" role="dialog" aria-hidden="true" >
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <div class="form-item" style="text-align:center;">
                    <label class="control-label" id="lableText" > 确认删除吗？</label>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary"  onclick="deleteItem()"  type="button">确定</button>
                <button class="btn btn-primary" type="button" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--<div class="modal fade" id="QQCountError"  role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 style="text-align:center;" class="modal-title">错误提示</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group" >
                        <p style="text-align:center;">最多添加20条客服信息！</p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>--%>

<div class="form-group" id="serviceTel" style="display: none">
    <form action="addOnLineServiceItem.htm" method="post">
        <input type="hidden" name="CSRFToken" value="${token}">
        <input type="hidden" value="${onLineService.onLineServiceId }" name="onLineServiceId">
        <label class="control-label col-sm-offset-3 col-sm-2">客服电话：</label>
        <input type="hidden" value="1" class="stType" name="contactType">
        <div class="col-sm-4">
            <input type="text" class="form-control" name="contactNumber" value="" id="addTelNo">
        </div>
        <label class="control-label col-sm-2">客服名称：</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" name="name" value="" id="addTelName">
        </div>
        <div class="col-sm-1"></div>
        <div class="col-sm-6">
            <input type="submit" class="btn btn-primary" value="提交">
            <span generated="true" id="addTel_tips" class="error" data-index=""></span>
        </div>
    </form>
</div>

<div class="form-group" id="serviceWW" style="display: none">
    <form action="addOnLineServiceItem.htm" method="post">
        <input type="hidden" name="CSRFToken" value="${token}">
        <input type="hidden" value="${onLineService.onLineServiceId }" name="onLineServiceId">
        <label class="control-label col-sm-offset-3 col-sm-2">客服旺旺：</label>
        <input type="hidden" value="2" class="stType" name="contactType">
        <div class="col-sm-4">
            <input type="text" class="form-control" name="contactNumber" value="" id="addWWNo">
        </div>
        <label class="control-label col-sm-2">客服名称：</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" name="name" value="" id="addWWName">
        </div>
        <div class="col-sm-1"></div>
        <div class="col-sm-6">
            <input type="submit" class="btn btn-primary" value="提交">
            <span generated="true" id="addWW_tips" class="error" data-index=""></span>
        </div>
    </form>
</div>


<!-- Modal -->
<div class="modal fade" id="addSpecialTopic" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">添加专题</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-sm-4">专题标题：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-10">
                            <input type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">SEO关键字：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="5"></textarea>
                        </div>
                        <div class="col-sm-3">
                            <a href="javascript:;" class="zhuantiseokw help_tips">
                                <i class="icon iconfont">&#xe611;</i>
                            </a>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">SEO描述：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="5"></textarea>
                        </div>
                        <div class="col-sm-3">
                            <a href="javascript:;" class="zhuantiseodesc help_tips">
                                <i class="icon iconfont">&#xe611;</i>
                            </a>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">去除头尾部：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">是否启用：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="inlineRadioOptions2" id="inlineRadio3" value="option1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="inlineRadioOptions2" id="inlineRadio4" value="option2"> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">背景图片：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <p class="pt5"><input type="file"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">预览图片：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <img alt="" src="images/kstore_logo.jpg">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">专题内容：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-17">
                            <div class="summernote"></div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="changePackage" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">更换包裹</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-sm-6">选择装箱单:</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-11">
                            <select class="form-control">
                                <option>请选择</option>
                                <option>装箱单1</option>
                                <option>装箱单2</option>
                                <option>装箱单3</option>
                            </select>
                        </div>
                        <div class="col-sm-6"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>

            </div>
        </div>
    </div>
</div>

<div class="advanced_search_cont none">
    <div class="advanced_search_form">
        <form class="form-horizontal">
            <div class="form-group">
                <label class="control-label col-sm-7">收货人：</label>
                <div class="col-sm-15">
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-7">联系电话：</label>
                <div class="col-sm-15">
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-7 col-sm-15">
                    <button type="submit" class="btn btn-primary btn-sm">确认搜索</button>
                </div>
            </div>
        </form>
    </div>
</div>
<input type="hidden" name="CSRFToken" id="CSRFToken" value="${token}"/>
<input class="onlineSwitch_KST" type="hidden" name="onlineSwitchKST" value="${kstList.isuseing}" />
<input id="onLineServiceIdKST" type="hidden" value="${kstList.shangId }" name="shangId">
<input class="onLineServiceIdForQQ" type="hidden" value="${onLineService.onLineServiceId }" name="onLineServiceId">
<input class="onlineSwitch_QQ" type="hidden" name="onlineSwitchQQ" value="${onLineService.onlineIndex}" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins)
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/summernote.min.js"></script>
<script src="<%=basePath%>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath%>js/bootstrap-select.min.js"></script>
<script src="<%=basePath%>js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath%>js/system/onlineservice_add.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script src="<%=basePath %>js/common/common_alert.js"></script>
<script src="<%=basePath %>js/select2.min.js"></script>
<script>
    $(function(){
        if($('.errorMessage').val() != null){
            $('#errorT').modal('show');
        }
    });

    function addOneQQ() {
        var count=0;
        $('div[name="QQ"]').each(function(){
            count++;
        });
        if(count<20){
            $(".qqService").append('<div class="form_group tips" id="form_'+"#"+'" name="QQ">'
                    +'<input type="hidden" class="serviceItems" name="serviceItems" value=""  />'
                    +'<label class="control-label col-sm-5"></label>'
                    +'<div class="col-sm-1"></div>'
                    +'<div id="item_'+"#"+'">'
                    +'<input type="hidden" value="0" name="onLineServiceId">'
                    +'<input type="hidden" value="0" name="onLineServiceItemId">'
                    +'<input type="hidden" value="" name="sNumber" id="sNumber">'
                    +'<input type="hidden" value="" name="sName" id="sName">'
                    +'<label class="control-label col-sm-3">客服账号：</label>'
                    +'<div class="col-sm-4">'
                    +'<input type="text" class="form-control sNumber"  id="sNumber" value="" >'
                    +'</div><label class="control-label col-sm-3">客服昵称：</label>'
                    +'<div class="col-sm-4">'
                    +'<input type="text" class="form-control"  id="sName"  value="" >'
                    +'</div><div class="control-label col-sm-3">'
                    +'&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="saveQQ(this);" href="javascript:void(0);">保存</a>'
                    +'&nbsp;&nbsp;<a onclick="removeItem(this);" href="javascript:void(0);">删除</a>'
                    +'</div></br></br><label class="control-label col-sm-8"></label><div class="col-sm-1"></div>'
                    +'<div style="height: 15px;">'
                    +'<input type="hidden" class="saveFlag" value="1"/>'
                    +'<span generated="true" class="error addQQNumerAndName_tips" data-index="" style="color:#a94442;"></span></div>');
        }else{
            updateTips("最多添加20个客服！","addQQCustom_tips");
        }
        $("#submitQQForm").attr("onclick","saveServiceForQQ();");
    }

    function saveQQ(item) {
        if($(item).html()=="保存"){
            $(item).parent().prev().prev().prev().children().attr("class","form-control");
            var sName = $(item).parent().prev().children().val();
            var sNumber = $(item).parent().prev().prev().prev().children().val();
            var flag = true;

            if(sNumber != ""){
                //判断qq格式、名称是否有特殊字符
                if (/^[1-9]\d{4,9}$/.test(sNumber)) {
                    if (checkSpecSymb("addQQNo", "span")) {
                        //判断是否有重复
                        var qqnos = new Array();
                        qqnos = $(".sNumber");
                        for (var i = 0; i < qqnos.length; i++) {
                            if (sNumber == qqnos[i].value ) {
                                updateTipsByClass("QQ号码有重复！",item);
                                flag = flag && false;
                                break;
                            }else{
                                updateTipsByClass("",item);
                            }
                        }
                    }else {
                        updateTipsByClass("客服账号包含特殊字符！", item);
                        flag = flag && false;
                    }
                }else{
                    updateTipsByClass("客服账户请输入5-10位数字", item);
                    flag = flag && false;
                }
            }else{
                updateTipsByClass("客服账户不能为空！",item );
                flag = flag && false;
            }
            if(!flag) return flag;
            if(sName != "") {
                if (! /^[a-zA-Z0-9\u4e00-\u9fa5]*$/.test(sName)) {
                    updateTipsByClass("客服昵称不支持非法字符！", item);
                    flag = flag && false;
                }else if(! /^[a-zA-Z0-9\u4e00-\u9fa5]{1,6}$/.test(sName)){
                    updateTipsByClass("客服昵称应小于6位字符！", item);
                    flag = flag && false;
                }
            }else{
                updateTipsByClass("客服昵称不能为空！",item);
                flag = flag && false;
            }
            if(flag){
                $(item).html('编辑');
                $(item).parent().prev().prev().prev().children().attr("class","form-control sNumber");
                $(item).parent().prev().children().attr("readonly","readonly");
                $(item).parent().prev().prev().prev().children().attr("readonly","readonly");
                $(item).parents(".form_group").find(".serviceItems").val($(item).parent().prev().prev().prev().children().val()+"-"+$(item).parent().prev().children().val());
                $(item).parents(".tips").find(".updateFlag").remove();
                if($(item).parents(".tips").find(".saveFlag").val()!=null){
                    $(".saveFlag").remove();
                }
                updateTipsByClass("",item);
                return;
            }else{
                $(item).parent().prev().prev().prev().children().attr("class","form-control sNumber");
                return flag;
            }
        }else{
            $(item).html('保存');
            $(item).parent().prev().children().removeAttr("readonly","readonly");
            $(item).parent().prev().prev().prev().children().removeAttr("readonly","readonly");
            //$("#updateFlag").remove();
            $(item).parents(".tips").find(".addQQNumerAndName_tips").parent().append("<input type='hidden' class='updateFlag' value='1'/>");
            //updateTipsByClass("请先保存客服数据再提交！",item);
        }
    }

    function chooseEffectiveTerminal() {
            var kstEffectiveTerminalstr='';
            $('input[name="kstEffectiveTerminal"]:checked').each(function () {
                kstEffectiveTerminalstr+=$(this).val()+",";
            });
            $(".kstSite").val(kstEffectiveTerminalstr);

            if($("#kstEffectiveTerminal1").is(":checked")){
                $("#pc_operation").show();
            }else{
                $("#pc_operation").hide();
            }
            if($("#kstEffectiveTerminal2").is(":checked")||$("#kstEffectiveTerminal3").is(":checked")){
                $("#app_operation").show();
            }else{
                $("#app_operation").hide();
            }
    }
</script>
</body>
</html>
