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
            <li class="active" style="color: #07d;">晒单列表</a></li>
        </ol>

        <div class="app-content">

            <input type="hidden" id="myStoreName" value="${storeName}">
            <div class="search-block">
                <form action="initsharelistthird.html" class="searchThirdShareList" method="post">
                    <div class="filter-groups">
                        <div class="control-group">
                            <label class="control-label">发表人：</label>

                            <div class="controls">
                                <#if share ??>
                                    <input type="text" class="form-control" name="customerName" value="${share.customerName!''}">
                                 <#else>
                                     <input type="text" class="form-control" name="customerName">
                                 </#if>

                            </div>
                        </div>
                        <div class="control-group lg-group">
                            <label class="control-label">商品名称：</label>

                            <div class="controls">

                                <#if share ??>
                                    <input type="text" class="form-control" name="goodsName" value="${share.goodsName!''}">
                                <#else>
                                    <input type="text" class="form-control" name="goodsName">
                                </#if>
                            </div>
                        </div>
                        <div class="search-operation">
                            <button class="btn btn-primary btn-sm" onclick="queryShareList()" type="button">查询<i
                                    class="glyphicon glyphicon-search"></i></button>
                        </div>
                </form>
            </div>
            <!-- <button class="btn btn-primary btn-xs" type="button" onclick="delThirdShares()">删除</button> -->

            <div class="cfg-content">
                <form id="batchDeleteShareForm" method="post" action="deleteshare.htm">
                    <table class="table">
                        <thead>
                        <tr>
                            <!--<th><input type="checkbox" onclick="selectAll(this,'parameterIds')"/></th> -->
                            <th>商品名称</th>
                            <th>发表人</th>
                            <th>内容</th>
                            <th>前台显示</th>
                            <th>发表时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if (pageBean.list?size) &gt; 0 >
                            <#list pageBean.list as share>
                            <tr>
                                <!--<td><input type="checkbox" value="${share.shareId}" name="parameterIds"
                                           class="ch_goods"/></td> -->
                                <td title="${share.goodsName}">
                                    <div class="data_item">
                                        <img height="60" src='${share.goodsImg}'/>

                                        <p title="${share.goodsName}">
                                            <#if share.goodsName?? &&share.goodsName?length gte 10>
                                            ${share.goodsName?substring(0,10)}...
                                            <#else>
                                            ${share.goodsName}
                                            </#if>
                                        </p>
                                    </div>
                                </td>

                                <td title="${share.customerName}">
                                    <#if share.customerName?? &&share.customerName?length gte 10 >
                                    ${share.customerName?substring(0,10)}...
                                    <#else>
                                    ${share.customerName}
                                    </#if>
                                </td>

                                <td title="${share.shareContent!''}">
                                    <#if share.shareContent?? &&share.shareContent?length gte 10 >
                                    ${share.shareContent?substring(0,10)}...
                                    <#else>
                                    ${share.shareContent!''}
                                    </#if>
                                </td>

                                <td>
                                    <#if share.isDisplay == "0">
                                        <span class="label label-default"
                                              id="share_display${share.shareId}">否</span>
                                    </#if>
                                    <#if share.isDisplay == "1" ||share.isDisplay == "2" >
                                        <span class="label label-success"
                                              id="share_display${share.shareId}">是</span>
                                    </#if>
                                </td>
                                <td>
                                    <#if share.createTime??>${share.createTime?string("yyyy-MM-dd HH:mm:ss") }</#if>
                                </td>
                                <td>
                                    <div class="btn-group">

                                        <a type="button" class="btn btn-primary btn-sm"
                                           onclick="showShareDetail(${share.shareId})">查看</a>
                                       <!-- <a type="button"
                                           class="btn btn-primary btn-sm dropdown-toggle"
                                           data-toggle="dropdown"
                                           aria-expanded="false"
                                           onclick="menu_btn(this)">
                                            <span class="caret"></span>
                                        </a>

                                        <ul class="dropdown-menu" role="menu">
                                            <li>
                                                <a href="javascript:;" onclick="del(${share.shareId});"
                                                   data-toggle="modal">删除</a>
                                            </li>
                                        </ul> -->
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



<div class="modal fade" id="delShare" role="dialog" aria-hidden="true">
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
                        onclick="delShare();">确定
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
                        onclick="delShareIds();">确定
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="scanAdvisory"  role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">晒单详情</h4>
            </div>
            <div class="modal-body">
                <div class="rec_example">
                    <div class="recommend_edit" id="share_detail">
                        <img class="good_img" alt="" src="images/good_1.jpg">
                        <div class="recommend_cont">
                            <h4>苹果（APPLE）iPhone 6 A1589 16G版 4G手机（银色）TD-LTE/TD-SCDMA/GSM</h4>
                            <p>
                                <i class="glyphicon glyphicon-star orange"></i>
                                <i class="glyphicon glyphicon-star orange"></i>
                                <i class="glyphicon glyphicon-star orange"></i>
                                <i class="glyphicon glyphicon-star orange"></i>
                                <i class="glyphicon glyphicon-star-empty orange"></i>
                            </p>
                            <p>发表人：<em class="text-info">时过境迁x：</em></p>
                            <p>
                                <span>时间：2014-11-04 14:30:32</span>
                                <span>IP：220.95.224.121</span>
                            </p>
                        </div>
                        <div class="recommend_cont">
                            <p>内容：
                                <a role="button" class="btn btn-sm btn-default">取消显示</a>
                                <a role="button" class="btn btn-sm btn-default">显示到首页</a>
                            </p>
                            <p>另外一家店铺和这个是同款，人家5099包邮，我们同事就是在那家买的，和我在这家买的质量一模一样，我买贵了！</p>
                        </div>
                        <div class="mt20">
                            <p>晒单：
                                <img class="img-thumbnail" alt="" src="images/good_1.jpg" width="100">
                                <img class="img-thumbnail" alt="" src="images/good_1.jpg" width="100">
                                <img class="img-thumbnail" alt="" src="images/good_1.jpg" width="100">
                            </p>
                        </div>
                    </div>
                </div>
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
        $(".searchThirdShareList").append("<input type='hidden' name='pageNo' value='" + $(obj).attr("data-role") + "' />").submit();
    }

    //根据条件查询订单信息
    function queryShareList() {
        $('.searchThirdShareList').submit();
    }


    function delShare() {
        var id = $("#parameterIds").val();
        var token = $("#CSRFToken").val();
        $.ajax({
            url: 'deleteshare.htm?parameterIds=' + id + '&CSRFToken=' + token,
            success: function (data) {
                window.location.href = "initsharelistthird.html";
            }
        });
    }


    function del(id) {
        $("#parameterIds").val(id);
        $('#delShare').modal('show');
    }

    /**
     * 批量删除晒单
     */
    function delThirdShares() {
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

    // 批量删除晒单
    function delShareIds() {
        var url = $("#batchDeleteShareForm").attr("action") + "?" + $("#batchDeleteShareForm").serialize();
        var token = $("#CSRFToken").val();
        $.ajax({
            url: url,
            success: function (data) {
                window.location.href = "initsharelistthird.html";
            }
        });
    }



    function showShareDetail(shareId) {
        var name = $("#myStoreName").val()+"店铺";
        $.ajax({
            url:'sharedetailAjaxNew.htm?shareId='+shareId+'&CSRFToken='+$("#CSRFToken").val(),
            success: function (data) {
                bs=data.bs;
                var share=data.shareVo
                var shareReply=data.shareReply;
                var html =
                        '<img class="good_img" alt="" src="' + share.goodsImg + '">' +
                        '<div class="recommend_cont">' +
                        '   <h4>' + share.goodsName + '</h4>' +
                        '   <p>发表人：<em class="text-info">' + share.customerName + '</em></p>' +
                        '   <p>' +
                        '       <span>时间：'+format(share.createTime,"yyyy-MM-dd HH:mm:ss")+'</span>' +
                        '   </p>' +
                        '</div>' +
                        '<div class="recommend_cont">';
                if(share.isDisplay=='0') {
                    html +=
                            '<p>内容：' +
                            '' +
                            '' +
                            '</p>';
                }

                if (share.isDisplay == '1') {
                    html +=
                            '<p>内容：' +
                            '' +
                            '' +
                            '</p>';
                }
                if(share.isDisplay=='2') {
                    html +=
                            '<p>内容：' +
                            '' +
                            '';
                    '</p>';

                }

                html +=
                        '   <p>' + share.shareContent + '</p>' +
                        '</div>'+
                        '<div class="reply_form" style="width:90%">'+
                        '<p></p>';
                if(null != shareReply && shareReply.length != 0){
                    for (var i = 0; i < shareReply.length; i++) {
                        html += '   <p>'+format(shareReply[i].createTime,"yyyy-MM-dd HH:mm:ss") ;
                        if(shareReply[i].customerId==null){
                            html+=' <em class="text-info"> '+name+' </em> 回复 <em class="text-info">'+share.customerName;
                        }else{
                            html+= '<em class="text-info"> '+shareReply[i].customer.customerNickname+' </em> 回复 <em class="text-info">'+share.customerName;
                        }
                        html+= '</em> ：' + shareReply[i].replyContent;
                        html += '</p>';
                    }
                }

                html += '   <div>' +
                '       <form id="replyForm" method="post"><input type="hidden" name="customerId" value="'+share.customerId +'"/>' +
                '       <input type="hidden" name="shareId" value="'+share.shareId +'"/>' +
                '       <input type="hidden" name="customerName" value="商城 " />'+
                '       <input type="hidden" name="replayName" value='+name+'></input>'+
                '       <input type="text" maxlength="200" class="form-control pull-left w200" name="replyContent" id="replyContent">' +
                '       <button type="button" class="btn btn-info" onclick="submitShareReplyForm(\''+share.customerName+'\','+share.shareId+')">回复</button></form>' +
                '   </div>' +
                '</div>';

                html +=
                        '<div class="mt20 od_list ">'+
                        '    <p>晒单：';
                for(var i=0;i<share.imageList.length;i++) {
                    html += '<a href="javascript:;"  data-img='+share.imageList[i].imageName+'><img class="img-thumbnail" alt="" src="'+share.imageList[i].imageName+'" width="100px" height="80px"></a><b></b>';
                }
                html +=
                        '    </p>'+
                        '</div> <div class="photo_viewer"><img alt="" src="" /></div>';

                $("#share_detail").html(html);
                $('#scanAdvisory').modal('show');
                initImgClick();

            }
        });

    }


    var format = function(time, format){
        var t = new Date(time);
        var tf = function(i){return (i < 10 ? '0' : '') + i};
        return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
            switch(a){
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
            };
        });
    };


    /**
     * 修改晒单回复的显示状态
     * @param obj
     */
    function updateShareRepDisplay(obj) {
        var display = $(obj).attr("display");
        $.ajax({
            url:$(obj).attr("url"),
            success:function(data) {
                if(display == '1') {
                    $(obj).attr("url",$(obj).attr("url").replace("isDisplay=1","isDisplay=0"));
                    $(obj).text("点击取消显示");
                    $(obj).removeClass("btn-default");
                    $(obj).addClass("btn-success");
                    $(obj).attr("display","0");
                } else {
                    $(obj).attr("url",$(obj).attr("url").replace("isDisplay=0","isDisplay=1"));
                    $(obj).text("点击显示");
                    $(obj).removeClass("btn-success");
                    $(obj).addClass("btn-default");
                    $(obj).attr("display","1");
                }
            }
        });
    }

    /**
     * 晒单回复
     * @param customerNickname 用户昵称
     */
    function submitShareReplyForm(customerNickname,shareId) {
        var name = $("#myStoreName").val()+"店铺";
        var commentContent = $("#commentContent").val();
        var replyContent = $("#replyContent").val();
        if(containSpecial(replyContent)){
            showTipAlert("内容包含特殊字符");
            return null;
        }
        if(replyContent=='') return;
        $.ajax({
            url:'addShareReplay.htm?CSRFToken='+$("#CSRFToken").val()+'&'+$("#replyForm").serialize(),
            type:'post',
            success:function(data) {
                $(".reply_form").find("p").first().before('<p>'+format(new Date().getTime(),"yyyy-MM-dd HH:mm:ss")+' <em class="text-info"> '+name+' </em> 回复 <em class="text-info">'+customerNickname+ '</em> ：'+replyContent+'</p>');
                $("#replyContent").val("");
            }
        });

    }



    function containSpecial( s ) {
        var containSpecial = RegExp(/[(\ )(\#) (\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=) (\[)(\])(\{)(\})(\|)(\\)(\')(\")(\/) (\<)(\>)(\)]+/);
        return ( containSpecial.test(s) );
    }


    /**
     * 修改晒单的显示状态
     * @param obj
     */
    function updateShareDisplay(obj) {
        var display = $(obj).attr("display");
        var shareId = $(obj).attr("share_id");
        $.ajax({
            url:$(obj).attr("url"),
            success:function(data) {
                if(display=='0') {
                    $(obj).attr("url",$(obj).attr("url").replace("isDisplay=1","isDisplay=0"));
                    $(obj).text("点击取消显示到商品页");
                    $(obj).removeClass("btn-default");
                    $(obj).addClass("btn-success");
                    $(obj).attr("display","1");

                    $("#share_display"+shareId).removeClass("label-default");
                    $("#share_display"+shareId).addClass("label-success");
                    $("#share_display"+shareId).text("是");

                    $("#index_display_btn").show();
                } else if(display=='1') {
                    $(obj).attr("url",$(obj).attr("url").replace("isDisplay=0","isDisplay=1"));
                    $(obj).text("点击显示到商品页");
                    $(obj).removeClass("btn-success");
                    $(obj).addClass("btn-default");
                    $(obj).attr("display","0");

                    $("#index_display_btn").removeClass("btn-success");
                    $("#index_display_btn").addClass("btn-default");
                    $("#index_display_btn").text("点击显示到首页");
                    $("#index_display_btn").attr("display","2");
                    $("#index_display_btn").hide();

                    $("#share_display"+shareId).removeClass("label-success");
                    $("#share_display"+shareId).addClass("label-default");
                    $("#share_display"+shareId).text("否");
                } else if(display=='2') {
                    $(obj).attr("url",$(obj).attr("url").replace("isDisplay=2","isDisplay=1"));
                    $(obj).text("点击取消显示到首页");
                    $(obj).removeClass("btn-default");
                    $(obj).addClass("btn-success");
                    $(obj).attr("display","3");
                } else if(display=='3') {
                    $(obj).attr("url",$(obj).attr("url").replace("isDisplay=1","isDisplay=2"));
                    $(obj).text("点击显示到首页");
                    $(obj).removeClass("btn-success");
                    $(obj).addClass("btn-default");
                    $(obj).attr("display","2");
                }
            }
        });
    }

    /**
     * 批量显示到首页
     */
    function batchDisplayToIndex() {
        $("#shareListForm").attr("action","updatesharetoindex.htm")
        showAjaxDeleteBatchConfirmAlert('shareListForm','parameterIds','确实要将这些记录显示到首页吗？');
    }



    /*初始化晒单中的图片的点击事件*/
    function initImgClick(){
        //评论晒单
        $(".od_list").each(function(){
            var _this = $(this);
            _this.find("a").click(function(){
                if($(this).hasClass("cur")) {
                    $(this).removeClass("cur");
                    _this.next(".photo_viewer").hide().width(0).height(0);
                    _this.next(".photo_viewer").find("img").attr("src","").width(0).height(0);
                } else {
                    var _src = $(this).attr("data-img");
                    var img_url = _src + "?" + Date.parse(new Date());
                    var _img = new Image();
                    _img.src = img_url;
                    var nw = _this.next(".photo_viewer").width();
                    var nh = _this.next(".photo_viewer").height();
                    _this.find(".cur").removeClass("cur");
                    $(this).addClass("cur");
                    _this.next(".photo_viewer").show().width(nw).height(nh);
                    _this.next(".photo_viewer").find("img").attr("src",_src);
                    _img.onload = function(){
                        var nw = _img.width + 8;
                        var nh = _img.height + 8;
                        if(nw > 490){
                            nw = 490;
                        }if(nh>430){
                            nh=430;
                        }
                        _this.next(".photo_viewer").find("img").animate({
                            width: nw - 8,
                            height: nh - 8
                        },500);
                        _this.next(".photo_viewer").animate({
                            width: nw,
                            height: nh
                        },500);
                    };
                };
            });
            _this.next(".photo_viewer").click(function(){
                _this.find(".cur").removeClass("cur");
                $(this).hide().width(0).height(0);
                $(this).find("img").attr("src","").width(0).height(0);
            });
        });
    }
</script>
<#import "../common/selectindex.ftl" as selectindex>
<@selectindex.selectindex "${n!''}" "${l!''}" />
</body>
</html>