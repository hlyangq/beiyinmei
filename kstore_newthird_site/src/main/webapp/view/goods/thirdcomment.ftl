<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>商品管理</title>
<#assign basePath=request.contextPath>
    <link rel="stylesheet" href="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link href="http://cdn.bootcss.com/bootstrap-datepicker/1.4.0/css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="${basePath}/css/material.css" rel="stylesheet">
    <link href="${basePath}/css/ripples.css" rel="stylesheet">
    <link rel="stylesheet" href="${basePath}/css/style.css"/>
    <style>
        .inquiry_box {
            margin-left: 0 !important;
            margin-right: 0 !important;
            margin-bottom: 10px;
        }

        .input-group[class*=col-] {
            float: left !important;
            padding-right: 10px !important;
        }
    </style>
</head>
<body>

<#--引入头部-->
<#include "../index/indextop.ftl">

<div class="wp">
    <div class="ui-sidebar">
        <div class="sidebar-nav">
            <div class="sidebar-nav">
            <#import "../index/indexleft.ftl" as leftmenu>
            <@leftmenu.leftmenu '${basePath}' />
            </div>
        </div>
    </div>

    <div class="app show_text" style="display: none;"
    ">
    <div class="app-container">
        <ol class="breadcrumb">
            <li>您所在的位置</li>
            <li>商品管理</li>
            <li class="active" style="color: #07d;">评论列表</a></li>
        </ol>

        <div class="app-content">
            <input type="hidden" id="myStoreName" value="${storeName!''}">

            <div class="search-block">
                <form action="querybycommentthird.html" class="searchThirdConsultList" method="post">
                    <div class="filter-groups">
                        <div class="control-group">
                            <label class="control-label">发表人：</label>

                            <div class="controls">
                                <#if comment ??>
                                    <input type="text" class="form-control" name="customerNickname" value="${comment.customerNickname!''}">
                                 <#else>
                                     <input type="text" class="form-control" name="customerNickname">
                                 </#if>

                            </div>
                        </div>
                        <div class="control-group lg-group">
                            <label class="control-label">商品名称：</label>

                            <div class="controls">

                                <#if comment ??>
                                    <input type="text" class="form-control" name="goodsName" value="${comment.goodsName!''}">
                                <#else>
                                    <input type="text" class="form-control" name="goodsName">
                                </#if>
                            </div>
                        </div>
                        <div class="search-operation">
                            <button class="btn btn-primary btn-sm" onclick="queryConsultList()" type="button">查询<i
                                    class="glyphicon glyphicon-search"></i></button>
                        </div>
                </form>
            </div>
            <div class="cfg-content">
                <form id="batchDeleteConsultForm" method="post" action="deleteComment.htm">
                    <table class="table">
                        <thead>
                        <tr>
                           <!-- <th><input type="checkbox" onclick="selectAll(this,'parameterIds')"/></th>-->
                            <th>商品名称</th>
                            <th>发表人</th>
                            <th>内容</th>
                            <th>评分</th>
                            <th>前台显示</th>
                            <th>发表时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if (pageBean.list?size) &gt; 0 >
                            <#list pageBean.list as comment>
                            <tr>
                                <!-- <td><input type="checkbox" value="${comment.commentId}" name="parameterIds"
                                           class="ch_goods"/></td> -->
                                <td title="${comment.goodsName}">
                                    <div class="data_item">
                                        <img height="60" src='${comment.goodsImg }'/>

                                        <p title="${comment.goodsName}">
                                            <#if comment.goodsName?? &&comment.goodsName?length gte 10>
                                            ${comment.goodsName?substring(0,10)}...
                                            <#else>
                                            ${comment.goodsName}
                                            </#if>
                                        </p>
                                    </div>
                                </td>

                                <td title="${comment.customerNickname}">
                                    <#if comment.customerNickname?? &&comment.customerNickname?length gte 10 >
                                    ${comment.customerNickname?substring(0,10)}...
                                    <#else>
                                    ${comment.customerNickname}
                                    </#if>
                                </td>

                                <td title="${comment.commentContent!''}">
                                    <#if comment.commentContent?? &&comment.commentContent?length gte 10 >
                                    ${comment.commentContent?substring(0,10)}...
                                    <#else>
                                    ${comment.commentContent!''}
                                    </#if>
                                </td>

                                <td title="${comment.commentScore!''}">
                                    ${comment.commentScore!''}星
                                </td>

                                <td>
                                    <#if comment.isDisplay == "0">
                                        <span class="label label-default"
                                              id="comment_display${comment.commentId}">否</span>
                                    </#if>
                                    <#if comment.isDisplay == "1">
                                        <span class="label label-success"
                                              id="comment_display${comment.commentId}">是</span>
                                    </#if>
                                </td>
                                <td>
                                    <#if comment.publishTime??>${comment.publishTime?string("yyyy-MM-dd HH:mm:ss") }</#if>
                                </td>
                                <td>
                                    <div class="btn-group">

                                        <a type="button" class="btn btn-primary btn-sm"
                                           onclick="showConsultDetail(${comment.commentId})">查看</a>
                                       <!-- <a type="button"
                                           class="btn btn-primary btn-sm dropdown-toggle"
                                           data-toggle="dropdown"
                                           aria-expanded="false"
                                           onclick="menu_btn(this)">
                                            <span class="caret"></span>
                                        </a>

                                        <ul class="dropdown-menu" role="menu">
                                            <li>
                                                <a href="javascript:;" onclick="del(${comment.commentId});"
                                                   data-toggle="modal">删除</a>
                                            </li>
                                        </ul>-->
                                    </div>
                                </td>
                            </tr>
                            </#list>
                        </#if>
                        </tbody>
                    </table>
                </form>
                <div class="footer-operation">
                <#if pageBean??>
                    <#if (pageBean.list?size) &gt; 0 >
                        <div class="ops-right">
                            <nav>
                                <ul class="pagination">
                                    <li>
                                        <a href="javascript:;" aria-label="Previous" onclick="changePageNo(this);"
                                           data-role="${pageBean.prePageNo}">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <#if (pageBean.startNo?number>1)>
                                        <li><a href="javascript:;">1</a></li>
                                    </#if>
                                    <#list pageBean.startNo?number .. pageBean.endNo as item>
                                        <li <#if item == pageBean.pageNo>class="active"</#if>><a href="javascript:;"
                                                                                                 onclick="changePageNo(this);"
                                                                                                 data-role="${item}">${item}</a>
                                        </li>
                                    </#list>
                                    <#if (pageBean.totalPages?number>pageBean.endNo)>
                                        <li><a href="javascript:;" onclick="changePageNo(this);"
                                               data-role="${pageBean.totalPages}">${pageBean.totalPages}</a></li>
                                    </#if>
                                    <li>
                                        <a href="javascript:;" aria-label="Next" onclick="changePageNo(this);"
                                           data-role="${pageBean.nextPageNo}">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </#if>
                </#if>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>

<#include "../common/leftfooter.ftl">


<!-- Modal -->
<div class="modal fade" id="scanAdvisory" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">评论详情</h4>
            </div>
            <div class="modal-body">
                <div class="rec_example">
                    <div class="recommend_edit" id="consult_detail">
                        <img class="good_img" alt="" src="images/good_1.jpg">

                        <div class="recommend_cont">
                            <h4>苹果（APPLE）iPhone 6 A1589 16G版 4G手机（银色）TD-LTE/TD-SCDMA/GSM</h4>

                            <p>发表人：<em class="text-info">时过境迁x：</em></p>

                            <p>
                                <span>时间：2014-11-04 14:30:32</span>
                                <%--<span>IP：220.95.224.121</span>--%>
                            </p>
                        </div>
                        <div class="recommend_cont">
                            <p>内容：<a role="button" class="btn btn-sm btn-default">取消显示</a></p>

                            <p>另外一家店铺和这个是同款，人家5099包邮，我们同事就是在那家买的，和我在这家买的质量一模一样，我买贵了！</p>
                        </div>
                        <div class="reply_form" style="width:70%">
                            <p>回复 <em class="text-info">时过境迁x</em> ：</p>

                            <div>
                                <input type="text" class="form-control pull-left w200">
                                <button type="button" class="btn btn-info">回复</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="delConsult" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <input type="hidden" id="parameterIds" value=""/>

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <div class="form-item error">
                    <label class="control-label">确认删除吗？</label>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal" onclick="cls()">取消</button>
                <button class="btn btn-primary" type="button" data-dismiss="modal" id="tip-submit-btn"
                        onclick="delConsult();">确定
                </button>
            </div>
        </div>
    </div>
</div>

<#--没有选中行提示框-->
<div class="modal fade" id="select-tip" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <div class="form-item error">
                    <label class="control-label">请至少选择一行！</label>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" type="button" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="delete-tip" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <div class="form-item error">
                    <label class="control-label">确认删除吗？</label>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal" onclick="cls()">取消</button>
                <button class="btn btn-primary" type="button" data-dismiss="modal" id="tip-submit-btn"
                        onclick="delConsultIds();">确定
                </button>
            </div>
        </div>
    </div>
</div>

<script src="http://cdn.staticfile.org/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script src="${basePath}/js/ripples.min.js"></script>
<script src="${basePath}/js/material.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap-datepicker/1.4.0/js/bootstrap-datepicker.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap-datepicker/1.4.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="${basePath}/js/app.js"></script>
<script type="text/javascript" src="${basePath}/js/third.js"></script>
<script type="text/javascript" src="${basePath}/js/common.js"></script>
<script type="text/javascript" src="${basePath}/js/common/common_alert.js"></script>
<script type="text/javascript" src="${basePath}/js/navmenu/navmenu.js"></script>

<script>

    /*用于控制显示div层  先显示头部和左边 稍后在显示里面的内容*/
    function show() {
        $(".show_text").fadeOut(1000).fadeIn(1000);
    }
    setTimeout("show()", 1000);


    /**
     * 更改页数
     */
    function changePageNo(obj) {
        $(".searchThirdConsultList").append("<input type='hidden' name='pageNo' value='" + $(obj).attr("data-role") + "' />").submit();
    }


    function showConsultDetail(consultId) {

        var name = $("#myStoreName").val()+"店铺";
        $.ajax({
            url: 'queryByCommentIdAjax.htm?commentId=' + consultId + '&CSRFToken=' + $("#CSRFToken").val(),
            success: function (data) {
                var comment = data.comment;
                var replist = data.replist;

                var html =
                        '<img class="good_img" alt="" src="' + comment.goodsImg + '">' +
                        '<div class="recommend_cont">' +
                        '   <h4>' + comment.goodsName + '</h4>' +
                        '   <p>发表人：<em class="text-info">' + comment.customerNickname + '</em></p>' +
                        '   <p>' +
                        '       <span>时间：' + format(comment.publishTime, "yyyy-MM-dd HH:mm:ss") + '</span>' +
                        '   </p>' +
                        '</div>' +
                        '<div class="recommend_cont">';
                if (comment.isDisplay == '1') {
                    html += '   <p>内容：</p>';
                } else {
                    html += '   <p>内容：</p>';
                }

                html +=
                        '   <p>' + comment.commentContent + '</p>' +
                        '</div>' +
                        '<div class="reply_form" style="width:90%">' +
                        '<p></p>';
                for (var i = 0; i < replist.length; i++) {
                    html += '   <p>' + format(replist[i].publishTime, "yyyy-MM-dd HH:mm:ss") + ' <em class="text-info">'+replist[i].customerNickname+'</em> 回复 <em class="text-info">' + comment.customerNickname + '</em> ：' + replist[i].commentContent;
                    html += '</p>';
                }

                html += '   <div>' +
                '       <form id="replyForm"><input type="hidden" name="customerId" value="' + comment.customerId + '"/>' +
                '       <input type="hidden" name="commentId" value="' + comment.commentId + '"/>' +
                '       <input type="hidden" name="customerNickname" value='+name+' />' +
                '       <input type="text" maxlength="200" class="form-control pull-left w200" name="commentContent" id="commentContent">' +
                '       <button type="button" class="btn btn-info" onclick="submitReplyForm(\'' + comment.customerNickname + '\',' + comment.commentId + ')">回复</button></form>' +
                '   </div>' +
                '</div>';
                $("#consult_detail").html(html);
            }
        });
        $('#scanAdvisory').modal('show');
    }

    //根据条件查询订单信息
    function queryConsultList() {
        $('.searchThirdConsultList').submit();
    }

    var format = function (time, format) {
        var t = new Date(time);
        var tf = function (i) {
            return (i < 10 ? '0' : '') + i
        };
        return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
            switch (a) {
                case 'yyyy':
                    return tf(t.getFullYear());
                    break;
                case 'MM':
                    return tf(t.getMonth() + 1);
                    break;
                case 'mm':
                    return tf(t.getMinutes());
                    break;
                case 'dd':
                    return tf(t.getDate());
                    break;
                case 'HH':
                    return tf(t.getHours());
                    break;
                case 'ss':
                    return tf(t.getSeconds());
                    break;
            }
            ;
        });
    };

    /**
     * 修改咨询的显示状态
     * @param obj
     */
    function updateCommentDisplay(obj) {
        var display = $(obj).attr("display");
        var commentId = $(obj).attr("comment_id");
        $.ajax({
            url: $(obj).attr("url"),
            success: function (data) {
                if (display == '1') {
                    $(obj).attr("url", $(obj).attr("url").replace("isDisplay=1", "isDisplay=0"));
                    $(obj).text("点击取消显示到商品页");
                    $(obj).removeClass("btn-default");
                    $(obj).addClass("btn-success");
                    $(obj).attr("display", "0");

                    $("#comment_display" + commentId).removeClass("label-default");
                    $("#comment_display" + commentId).addClass("label-success");
                    $("#comment_display" + commentId).text("是");
                } else {
                    $(obj).attr("url", $(obj).attr("url").replace("isDisplay=0", "isDisplay=1"));
                    $(obj).text("点击显示到商品页");
                    $(obj).removeClass("btn-success");
                    $(obj).addClass("btn-default");
                    $(obj).attr("display", "1");

                    $("#comment_display" + commentId).removeClass("label-success");
                    $("#comment_display" + commentId).addClass("label-default");
                    $("#comment_display" + commentId).text("否");
                }
            }
        });
    }

    /**
     * 咨询回复
     * @param customerNickname 用户昵称
     */
    var num = 0;
    function submitReplyForm(customerNickname, commentId) {
        var name = $("#myStoreName").val()+"店铺";
        var commentContent = $("#commentContent").val();
        if (containSpecial(commentContent)) {
            showTipAlert("内容包含特殊字符");
            return null;
        }
        if (commentContent != '') {
            num += 1;
            $.ajax({
                url: 'addCommReplay.htm?new=1&CSRFToken=' + $("#CSRFToken").val() + '&' + $("#replyForm").serialize(),
                success: function (data) {
                    $(".reply_form").find("p").first().before('<p>' + format(new Date().getTime(), "yyyy-MM-dd HH:mm:ss") + ' <em class="text-info">'+name+' </em> 回复 <em class="text-info">' + customerNickname + '</em> ：' + commentContent + '</p>');
                    $("#commentContent").val("");
                }
            });
        }
    }

    /**
     * 修改咨询回复的显示状态
     * @param obj
     */
    function updateCommentRepDisplay(obj) {
        var display = $(obj).attr("display");
        $.ajax({
            url: $(obj).attr("url"),
            success: function (data) {
                if (display == '1') {
                    $(obj).attr("url", $(obj).attr("url").replace("isDisplay=1", "isDisplay=0"));
                    $(obj).text("点击取消显示");
                    $(obj).removeClass("btn-default");
                    $(obj).addClass("btn-success");
                    $(obj).attr("display", "0");
                } else {
                    $(obj).attr("url", $(obj).attr("url").replace("isDisplay=0", "isDisplay=1"));
                    $(obj).text("点击显示");
                    $(obj).removeClass("btn-success");
                    $(obj).addClass("btn-default");
                    $(obj).attr("display", "1");
                }
            }
        });
    }

    function containSpecial(s) {
        var containSpecial = RegExp(/[(\ )(\#) (\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=) (\[)(\])(\{)(\})(\|)(\\)(\')(\")(\/) (\<)(\>)(\)]+/);
        return ( containSpecial.test(s) );
    }

    function delConsult() {
        var id = $("#parameterIds").val();
        var token = $("#CSRFToken").val();
        $.ajax({
            url: 'deleteComment.htm?parameterIds=' + id + '&CSRFToken=' + token,
            success: function (data) {
                window.location.href = "initcommentthird.html";
            }
        });
    }


    function del(id) {
        $("#parameterIds").val(id);
        $('#delConsult').modal('show');
    }

    /**
     * 批量删除咨询
     */
    function delThirdConsults() {
        var bool = false;
        var checks = $(".ch_goods");
        var checkGroup = new Array();
        for (var i = 0; i < checks.length; i++) {
            var e = checks[i];
            if (e.checked == true) {
                bool = true;
                checkGroup.push(e);
            }
        }
        if (bool == false) {
            $(".show_title").text("请至少选择一行数据!");
            $('#select-tip').modal('show');
            return null;
        }
        $('#delete-tip').modal('show');
    }

    // 批量删除咨询
    function delConsultIds() {
        var url = $("#batchDeleteConsultForm").attr("action") + "?" + $("#batchDeleteConsultForm").serialize();
        var token = $("#CSRFToken").val();
        $.ajax({
            url: url,
            success: function (data) {
                window.location.href = "initcommentthird.html";
            }
        });
    }

</script>
<#import "../common/selectindex.ftl" as selectindex>
<@selectindex.selectindex "${n!''}" "${l!''}" />
</body>
</html>