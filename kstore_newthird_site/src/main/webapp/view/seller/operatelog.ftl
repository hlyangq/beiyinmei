<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商城第三方后台-操作日志</title>
<#assign basePath=request.contextPath>

<link rel="stylesheet" href="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap-datepicker/1.4.0/css/bootstrap-datepicker.min.css">
<link href="${basePath}/css/material.css" rel="stylesheet">
<link href="${basePath}/css/ripples.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${basePath}/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/third.css"/>

<script type="text/javascript" src="${basePath}/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="http://cdn.staticfile.org/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script src="${basePath}/js/ripples.min.js"></script>
<script src="${basePath}/js/material.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-datepicker/1.4.0/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-datepicker/1.4.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/js/third.js"></script>
<script type="text/javascript" src="${basePath}/js/app.js"></script>
<script type="text/javascript" src="${basePath}/js/sell/express.js"></script>
</head>

<body>
<#-- 引入头部 -->
<#include "../index/indextop.ftl">
<div class="wp">
    <div class="ui-sidebar">
        <div class="sidebar-nav">
            <#import "../index/indexleft.ftl" as leftmenu>
            <@leftmenu.leftmenu '${basePath}' />
        </div>
    </div>

    <div class="app show_text" style="display: none;"">
        <div class="app-container">
            <ol class="breadcrumb">
                <li>您所在的位置</li>
                <li>我的店铺</li>
                <li class="active" style="color: #07d;" >操作日志</li>
            </ol>

            <div class="app-content">
                <div>
                    <ul class="nav nav-tabs">
                        <li role="presentation" class="active">
                            <a href="javascript:;">操作日志</a>
                            <span><b></b></span>
                            <span><b></b></span>
                        </li>
                    </ul>
                    <div class="search-block">
                        <form action="operateloglist.htm" class="operateloglist" method="post">
                            <div class="filter-groups">
                                <div class="control-group">
                                    <label class="control-label">操作内容：</label>
                                    <div class="controls">
                                        <input class="form-control" name="opContent" id="opContent"
                                        <#if operationLog.opContent??>
                                               value="${operationLog.opContent}"
                                        <#else>
                                               value=""
                                        </#if> type="text"/>
                                    </div>
                                </div>
                                <div class="control-group lg-group">
                                    <label class="control-label">时间：</label>
                                    <div class="controls">
                                        <input type="text" name="startTime" id="startTime" value="${startTime!''}" class="form-control sm-input datepicker" data-provide="datepicker"/>
                                        ~
                                        <input type="text" name="endTime" id="endTime" value="${endTime!''}" class="form-control sm-input datepicker" data-provide="datepicker"/>
                                    </div>
                                </div>
                            </div>
                            <div class="search-operation">
                                <button class="btn btn-primary btn-sm"  onclick="queryLogList()" type="button">查询<i class="glyphicon glyphicon-search"></i></button>
                                <button class="btn btn-default btn-sm"  onclick="resetThirdLog()"  type="button">重置<i class="glyphicon glyphicon-refresh"></i></button>
                            </div>
                        </form>
                    </div>
                    <div class="cfg-content">
                        <div class="ops-bar">
                            <div class="btn-group export-btn">
                                <#--<a type="button" class="btn btn-sm btn-primary" href="javascript:;" onclick="exportAllOrder()">导出日志</a>-->
                                <a type="button"
                                   class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false" onclick="menu_btn(this)">
                                    导出日志<span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu" role="menu" style="min-width: 90px;font-size:12px;padding:2px 0;">
                                    <li>
                                        <a href="javascript:;" onclick="exportExcel(15)">半个月内</a>
                                    </li>
                                    <li>
                                        <a href="javascript:;" onclick="exportExcel(30)">一个月内</a>
                                    </li>
                                    <li>
                                        <a href="javascript:;" onclick="exportExcel(90)">三个月内</a>
                                    </li>
                                    <li>
                                        <a href="javascript:;" onclick="exportExcel(0)">所有</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>操作人</th>
                                <th>操作时间</th>
                                <th>操作内容</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#if (pageBean.list?size!=0)>
                                    <#list pageBean.list as log>
                                    <tr>
                                        <td>${log.opName}</td>
                                        <td>${log.opTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>${log.opContent}</td>
                                    </tr>
                                    </#list>
                                </#if>
                            </tbody>
                        </table>
                        <div class="footer-operation">
		                 <#import "../pagination/pageBean.ftl" as page>
		                 <@page.pagination pageBean />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="back-to-top">
    <a href="javascript:;"><i></i>返回顶部</a>
</div>

</div>
<script type="text/javascript" src="${basePath}/js/navmenu/navmenu.js"></script>
<#import "../common/selectindex.ftl" as selectindex>
<@selectindex.selectindex "${n!''}" "${l!''}" />
<script>
    $(function(){
    	$.material.init();
        //重置搜索
        $('.datepicker').datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            language: 'zh-CN'
        })
    });

    /*用于控制显示div层  先显示头部和左边 稍后在显示里面的内容*/
    function show(){
        $(".show_text").fadeOut(1000).fadeIn(1000);
    }
    setTimeout("show()",1000);

    function resetThirdLog(){
        $(".operateloglist")[0].reset();
        $("#opContent").val("");
        $("#startTime").val("" );
        $("#endTime").val("");
    }
    function queryLogList(){
        $(".operateloglist").submit();
    }
    function exportExcel(obj){
        if((obj != null || obj != "") && ! isNaN(obj)){
            var url = 'exportlogexcel.htm?days='+obj;
            location.href=url;
        }
    }
</script>
</body>
</html>
