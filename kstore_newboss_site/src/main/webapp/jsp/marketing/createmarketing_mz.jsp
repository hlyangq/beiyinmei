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
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .spanweight{
            display: inline-block;
            max-width: 100%;
            margin-bottom: 5px;
            font-weight: bold;
            color:#a94442;
        }

    </style>
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

                <h2 class="main_title">添加${pageNameChild}&nbsp;<small><a href="javascript:void(0)"  onclick="$('#mjo').modal('show')" style="diaplay:block; float:right; padding-right: 12px">查看帮助<i class="icon iconfont">&#xe611;</i></a></small></h2>

                <div class="common_info common_tabs mt20">
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">满赠促销</a></li>
                        <%--<li role="presentation"><a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">类目促销</a></li>
                        <li role="presentation"><a href="#tab3" aria-controls="tab2" role="tab" data-toggle="tab">品牌促销</a></li>--%>
                    </ul>
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="tab1">
                            <div class="common_form common_form_lg p20">
                                <form class="form-horizontal" action="doaddpresentmarketing.htm?CSRFToken=${token}"  id="addFormOne" method="post">
                                    <input type="hidden" name="status" class="f_status" value="0">
                                    <input type="hidden" id="isAlls" value="0" name="isAll" >
                                    <div class="form-group">
                                        <label class="control-label col-sm-4"><span class="text-danger">*</span>促销名称：</label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control required specstr" name="marketingName" maxlength="40">
                                        </div>
                                        <div class="col-sm-3">
                                            <a href="javascript:;" class="cuxiaomingchen help_tips">
                                                <i class="icon iconfont">&#xe611;</i>
                                            </a>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-4">满赠类型：<input type="hidden" name="marketingType" value="0"/></label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-8">
                                            <label class="radio-inline">
                                                <input type="radio" class="codexchoose" name="codexchoose" checked="checked" value="0"/>
                                                满金额赠
                                            </label>
                                            <label class="radio-inline">
                                                <input type="radio" class="codexchoose" name="codexchoose" value="1"/>
                                                满数量赠
                                            </label>
                                            <input type="hidden" name="codexType" value="6" />
                                            <input type="hidden" name="codexId" value="6" />
                                            <input type="hidden" name="presentType" id="presentType" value="0" />
                                        </div>
                                    </div>
                                    <div class="form-group codexDiv count fullcash codexDiv0 one">
                                        <label class="control-label col-sm-4"><span class="text-danger">*</span>设置规则：</label>
                                        <div class="col-sm-1"><label class="radio-inline">满</label></div>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-2">
                                            <input type="text" name="fullPrice" id="" class="form-control fullPriceNewAdd required money" maxlength="8">
                                        </div>
                                        <div class="col-sm-1"><label class="radio-inline">元</label></div>
                                        <div class="col-sm-4">
                                            <div style="padding-left: 20px;">
                                                <button type="button" class="btn btn-info pull-left" onclick="chooseProductForGift(1,1);">添加赠品</button>
                                                <span class="spanweight gifterror"></span>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <select name="presentMode">
                                                <option selected value="0">默认全赠</option>
                                                <option value="1">可选一种</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group codexDiv0 fullcash one">
                                        <label class="control-label col-sm-4"></label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-19">
                                            <table class="table table-striped table-hover table-bordered" style="margin-bottom:0px;">
                                                <thead style="top:0;">
                                                <tr>
                                                    <th width="100">货品图片</th>
                                                    <th width="100">货品规格</th>
                                                    <th width="150">货品编号</th>
                                                    <th width="300">货品名称</th>
                                                    <th width="100">赠送数量</th>
                                                    <th width="100">操作</th>
                                                </tr>
                                            </table>
                                            <div style="max-height:300px;overflow-y:auto;position:relative;">

                                                <table class="table table-striped table-hover table-bordered" id="fullcash1">
                                                    <tbody style="">

                                                    </tbody>
                                                </table>

                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group addChoose addChoose0 one addMore" >
                                        <div class="col-sm-4"></div>
                                        <div class="col-sm-8"><label class="radio-inline" style="color:blue" onclick="addmore(this,0)">+添加多级促销<span style="color:red;">（最多只能添加三级）</span></label></div>
                                    </div>

                                    <div class="form-group codexDiv countSecond fullcount codexDiv1 two" style="display:none;">
                                        <label class="control-label col-sm-4"><span class="text-danger">*</span>设置规则：</label>
                                        <div class="col-sm-1"><label class="radio-inline">满</label></div>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-2">
                                            <input type="text" name="fullPrice" class="form-control fullNewAdd" maxlength="8">
                                        </div>
                                        <div class="col-sm-1"><label class="radio-inline">件</label></div>
                                        <div class="col-sm-4">
                                            <div style="padding-left: 20px;">
                                                <button type="button" class="btn btn-info pull-left" onclick="chooseProductForGift(2,1);">添加赠品</button>
                                                <span class="spanweight giftcounterror"></span>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <select name="presentMode">
                                                <option selected value="0">默认全赠</option>
                                                <option value="1">可选一种</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group codexDiv1 two" style="display:none;">
                                        <label class="control-label col-sm-4"></label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-19">
                                            <table class="table table-striped table-hover table-bordered" style="margin-bottom:0px;">
                                                <thead style="top:0;">
                                                <tr>
                                                    <th width="100">货品图片</th>
                                                    <th width="100">货品规格</th>
                                                    <th width="150">货品编号</th>
                                                    <th width="300">货品名称</th>
                                                    <th width="100">赠送数量</th>
                                                    <th width="100">操作</th>
                                                </tr>
                                            </table>
                                            <div style="max-height:300px;overflow-y:auto;position:relative;">

                                                <table class="table table-striped table-hover table-bordered" id="fullcount1">
                                                    <tbody style="">

                                                    </tbody>
                                                </table>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group addChoose addChoose1 two addMore" style="display:none;">
                                        <div class="col-sm-4"></div>
                                        <div class="col-sm-8"><label class="radio-inline" style="color:blue" onclick="addmore(this,1)">+添加多级促销<span style="color:red;">（最多只能添加三级）</span></label></div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-4"><span class="text-danger">*</span>选择货品：</label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-4">
                                            <button type="button" class="btn btn-info" onclick="chooseProductWithClear();">选择参加促销的货品</button>
                                            <span id="ps" class="spanweight"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-4">已选择货品：</label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-19">
                                            <table class="table table-striped table-hover table-bordered" style="margin-bottom:0px;">
                                                <thead style="top:0;">
                                                <tr>
                                                    <th width="100">货品图片</th>
                                                    <th width="100">货品规格</th>
                                                    <th width="150">货品编号</th>
                                                    <th width="300">货品名称</th>
                                                    <th width="100">操作</th>
                                                </tr>
                                            </table>
                                            <div style="max-height:300px;overflow-y:auto;position:relative;">

                                                <table class="table table-striped table-hover table-bordered" id="readproduct">
                                                    <tbody style="">

                                                    </tbody>
                                                </table>

                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-4"><span class="text-danger">*</span>开始时间：</label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-9">
                                            <div class="input-group date form_datetime w200" id="startpicker">
                                                <input class="form-control required" type="text" id="startTime" value="" readonly
                                                       name="sTime">
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-4"><span class="text-danger">*</span>结束时间：</label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-9">
                                            <div class="input-group date form_datetime w200" id="endpicker">
                                                <input class="form-control required" type="text" id="endTime" value="" readonly
                                                       name="eTime">
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-4">促销描述：<input type="hidden" name="marketingDes" id="marketingDes"/></label>
                                        <div class="col-sm-1"></div>
                                        <div class="col-sm-19">
                                            <div class="summernote"></div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-5 col-sm-9">
                                            <button type="button" class="btn btn-primary" onclick="subFormOne(this);">保存</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="chooseGoods"  role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" name="cancel"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">选择货品</h4>
            </div>
            <div class="modal-body">
                <div class="mb10">
                    <form class="form-inline" id="searchGoodsInfo" action="" method="post" >
                        <input type="hidden" name="pageNo" id="pageNo" value=""/>
                        <input type="hidden" name="pageSize" id="pageSize" value=""/>
                        <div class="form-group">
                            商品名称：<input type="text" class="form-control" placeholder="商品名称"  name="goodsName" id="goodsName">
                        </div>
                        <div class="form-group">
                            商品编号：<input type="text" class="form-control" placeholder="商品编号"  name="goodsNo" id="goodsNo">
                        </div>
                        <div class="form-group">
                            货品编号：<input type="text" class="form-control" placeholder="货品编号" name="goodsInfoItemNo" id="goodsInfoItemNo">
                        </div>
                        <div class="form-group">
                            货品售价：<input type="text" class="form-control money" id="lowgoodsInfoPrice" name="lowGoodsInfoPrice">
                            -<input type="text" class="form-control money"  id="highgoodsInfoPrice" name="highGoodsInfoPrice">
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-info" onclick="chooseProduct();">搜索</button>
                        </div>
                    </form>

                </div>
                <table class="table table-striped table-hover table-bordered" id="productAddList">
                    <thead>
                    <tr>
                        <th width="50"><input type="checkbox" onchange="chooseAllPro(this);" id="chooseAllPro"></th>
                        <th width="100">货品图片</th>
                        <th width="100">货品规格</th>
                        <th width="150">货品编号</th>
                        <th>货品名称</th>
                        <th>价格</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
                <div class="table_foot" id="productAddList_table_foot">
                    <div class="table_pagenav pull-right">
                        <div class="meneame">

                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" name="cancel">取消</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="chooseGoodsForGift"  role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" name="cancel"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">选择赠品</h4>
            </div>
            <div class="modal-body">
                <div class="mb10">
                    <form class="form-inline" id="searchGoodsInfoG" action="" method="post" >
                        <input type="hidden" name="pageNo" id="pageNoG" value=""/>
                        <input type="hidden" name="pageSize" id="pageSizeG" value=""/>
                        <div class="form-group">
                            商品名称：<input type="text" class="form-control" placeholder="商品名称"  name="goodsName" id="goodsNameG">
                        </div>
                        <div class="form-group">
                            商品编号：<input type="text" class="form-control" placeholder="商品编号"  name="goodsNo" id="goodsNoG">
                        </div>
                        <div class="form-group">
                            货品编号：<input type="text" class="form-control" placeholder="货品编号" name="goodsInfoItemNo" id="goodsInfoItemNoG">
                        </div>
                        <div class="form-group">
                            货品售价：<input type="text" class="form-control money" id="lowgoodsInfoPriceG" name="lowGoodsInfoPrice">
                            -<input type="text" class="form-control money"  id="highgoodsInfoPriceG" name="highGoodsInfoPrice">
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-info" id="searchBtn">搜索</button>
                        </div>
                    </form>

                </div>
                <table class="table table-striped table-hover table-bordered" id="productAddListG">
                    <thead>
                    <tr>
                        <th width="50"><input type="checkbox" id="chooseAllProG"></th>
                        <th width="100">货品图片</th>
                        <th width="100">货品规格</th>
                        <th width="150">货品编号</th>
                        <th>货品名称</th>
                        <th>价格</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
                <div class="table_foot" id="productAddList_table_footG">
                    <div class="table_pagenav pull-right">
                        <div class="meneame">

                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" name="cancel">取消</button>
            </div>
        </div>
    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="mjo" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加满赠促销</h4>
            </div>
            <div class="modal-body">
                <div class="modal-article">
                    <p><em>1.</em>添加满赠促销，满赠促销包含【满金额赠】和【满数量赠】，添加信息如下图</p>
                    <img src="<%=basePath %>/images/syshelp/mz.png" alt="">
                    <p><em>2.</em>保存成功后，该促销添加成功</p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="errorT"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">

                        <p style="text-align:center;"> 减值应当小于满值！</p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script src="<%=basePath %>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath %>js/summernote.min.js"></script>
<script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath %>js/jquery.ztree.core-3.5.min.js"></script>
<script src="<%=basePath %>js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="<%=basePath %>js/common.js"></script>
<script src="<%=basePath%>js/common/common_alert.js"></script>
<script>


    $('.main_cont').scroll(function(){
        $('.datetimepicker').hide();
    });



    $(function(){
        $("#addFormOne").validate();
        $("#addFormTwo").validate();
        $("#addFormThree").validate();
        /* 表单项的值点击后转换为可编辑状态 */
        $('.form_value').click(function(){
            var formItem = $(this);
            if(!$('.form_btns').is(':visible')) {
                formItem.parent().addClass('form_open');
                $('.form_btns').show();
                $('.form_btns').css({
                    'left': formItem.next().offset().left + 70 + 'px',
                    'top': formItem.next().offset().top - 30 + 'px'
                });
                $('.form_sure,.form_cancel').click(function () {
                    $('.form_btns').hide();
                    formItem.parent().removeClass('form_open');
                });
            }
        });


        $(".codexchoose").change(function(){
            var type = $(this).val();
            $("#presentType").val(type);
            if(type == '0'){
                $(".one").show();
                $(".two").hide();
                $(this).parents(".tab-pane").find('.codexDiv0').show();
                $(this).parents(".tab-pane").find('.addChoose0').show();
                $(this).parents(".tab-pane").find('.codexDiv1').hide();
                $(this).parents(".tab-pane").find('.addChoose1').hide();
                $(this).parents(".tab-pane").find(".codexDiv0").find(".fullPriceNewAdd").addClass("required").addClass("money");
                $(this).parents(".tab-pane").find(".codexDiv1").find(".fullNewAdd").removeAttr("name").removeClass("required").removeClass("money");
                $(this).parents(".tab-pane").find(".codexDiv0").find(".fullPriceNewAdd").attr("name","fullPrice");
                $(this).parents(".tab-pane").find(".codexDiv1").find("select").removeAttr("name");
                $(this).parents(".tab-pane").find(".codexDiv1").find(".present").removeAttr("name");
                $(this).parents(".tab-pane").find(".codexDiv0").find("input.present").each(function(){
                    $(this).attr("name",$(this).prev("input").val());
                });
                $(this).parents(".tab-pane").find(".codexDiv0").find("select").attr("name","presentMode");
            }
            if(type == '1'){
                $(".one").hide();
                $(".two").show();
                $(this).parents(".tab-pane").find('.codexDiv1').show();
                $(this).parents(".tab-pane").find('.addChoose1').show();
                $(this).parents(".tab-pane").find('.codexDiv0').hide();
                $(this).parents(".tab-pane").find('.addChoose0').hide();
                $(this).parents(".tab-pane").find(".codexDiv1").find(".fullNewAdd").addClass("required").addClass("money");
                $(this).parents(".tab-pane").find(".codexDiv0").find(".fullPriceNewAdd").removeAttr("name").removeClass("required").removeClass("money");
                $(this).parents(".tab-pane").find(".codexDiv1").find(".fullNewAdd").attr("name","fullPrice");
                $(this).parents(".tab-pane").find(".codexDiv0").find("select").removeAttr("name");
                $(this).parents(".tab-pane").find(".codexDiv0").find(".present").removeAttr("name");
                $(this).parents(".tab-pane").find(".codexDiv1").find("input.present").each(function(){
                    $(this).attr("name",$(this).prev("input").val());
                });
                $(this).parents(".tab-pane").find(".codexDiv1").find("select").attr("name","presentMode");
            }

        });

        /* 为选定的select下拉菜单开启搜索提示 */
        $('select[data-live-search="true"]').selectpicker();
        /* 为选定的select下拉菜单开启搜索提示 END */

        /* 下面是表单里面的日期时间选择相关的 */
        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:00',
            weekStart : 1,
            autoclose : true,
            language : 'zh-CN',
            pickerPosition : 'bottom-left',
            todayBtn : true,
            viewSelect : 'hour'
        });
        $('.form_date').datetimepicker({
            format : 'yyyy-mm-dd',
            weekStart : 1,
            autoclose : true,
            language : 'zh-CN',
            pickerPosition : 'bottom-left',
            minView : 2,
            todayBtn : true
        });
        /* 下面是表单里面的日期时间选择相关的 END */


        /*下面是时间选择器开始时间不能大于结束时间设置  START*/
        //单品促销开始结束时间配置
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        $('#endpicker').datetimepicker('setStartDate', startTime);
        $('#startpicker').datetimepicker('setEndDate', endTime);
        $('#endpicker')
                .datetimepicker()
                .on('show', function (ev) {
                    startTime = $("#startTime").val();
                    endTime = $("#endTime").val();
                    $('#endpicker').datetimepicker('setStartDate', startTime);
                    $('#startpicker').datetimepicker('setEndDate', endTime);
                });
        $('#startpicker')
                .datetimepicker()
                .on('show', function (ev) {
                    endTime = $("#endTime").val();
                    startTime = $("#startTime").val();
                    $('#startpicker').datetimepicker('setEndDate', endTime);
                    $('#endpicker').datetimepicker('setStartDate', startTime);
                });
        /*下面是时间选择器开始时间不能大于结束时间设置  END*/


        /*下面是时间选择器开始时间不能大于结束时间设置  START*/
        // 类目促销开始结束时间配置
        var startTime2 = $("#startTime2").val();
        var endTime2 = $("#endTime2").val();
        $('#endpicker2').datetimepicker('setStartDate', startTime2);
        $('#startpicker2').datetimepicker('setEndDate', endTime2);
        $('#endpicker2')
                .datetimepicker()
                .on('show', function (ev) {
                    startTime2 = $("#startTime2").val();
                    endTime2 = $("#endTime2").val();
                    $('#endpicker2').datetimepicker('setStartDate', startTime2);
                    $('#startpicker2').datetimepicker('setEndDate', endTime2);
                });
        $('#startpicker2')
                .datetimepicker()
                .on('show', function (ev) {
                    endTime2 = $("#endTime2").val();
                    startTime2 = $("#startTime2").val();
                    $('#startpicker2').datetimepicker('setEndDate', endTime2);
                    $('#endpicker2').datetimepicker('setStartDate', startTime2);
                });
        /*下面是时间选择器开始时间不能大于结束时间设置  END*/


        /*下面是时间选择器开始时间不能大于结束时间设置  START*/
        //品牌开始结束时间配置
        var startTime3 = $("#startTime3").val();
        var endTime3 = $("#endTime3").val();
        $('#endpicker3').datetimepicker('setStartDate', startTime3);
        $('#startpicker3').datetimepicker('setEndDate', endTime3);
        $('#endpicker3')
                .datetimepicker()
                .on('show', function (ev) {
                    startTime3 = $("#startTime3").val();
                    endTime3 = $("#endTime3").val();
                    $('#endpicker3').datetimepicker('setStartDate', startTime3);
                    $('#startpicker3').datetimepicker('setEndDate', endTime3);
                });
        $('#startpicker3')
                .datetimepicker()
                .on('show', function (ev) {
                    endTime3 = $("#endTime3").val();
                    startTime3 = $("#startTime3").val();
                    $('#startpicker3').datetimepicker('setEndDate', endTime3);
                    $('#endpicker3').datetimepicker('setStartDate', startTime3);
                });
        /*下面是时间选择器开始时间不能大于结束时间设置  END*/

        /* 富文本编辑框 */
        $('.summernote').summernote({
            height: 300,
            tabsize: 2,
            lang: 'zh-CN',
            onImageUpload: function(files, editor, $editable) {
                sendFile(files,editor,$editable);
            }
        });

        /* 富文本编辑框 */
        $('.summernoteTwo').summernote({
            height: 300,
            tabsize: 2,
            lang: 'zh-CN',
            onImageUpload: function(files, editor, $editable) {
                sendFile(files,editor,$editable);
            }
        });

        /* 富文本编辑框 */
        $('.summernoteThree').summernote({
            height: 300,
            tabsize: 2,
            lang: 'zh-CN',
            onImageUpload: function(files, editor, $editable) {
                sendFile(files,editor,$editable);
            }
        });

        /* 下面是关于树形菜单 END */

        /* 下面是表单里面的填写项提示相关的 */
        $('.cuxiaomingchen').popover({
            content : '促销名称',
            trigger : 'hover'
        });


    });



    function chooseProductWithClear(){

        // 清空搜索框
        $("#goodsName").val('');
        $("#goodsNo").val('');
        $("#goodsInfoItemNo").val('');
        $("#lowgoodsInfoPrice").val('');
        $("#highgoodsInfoPrice").val('');

        doAjax(1,8);
        $('#chooseGoods').modal('show');
    }
    /*num:1满金额赠2满数量赠*/
    function chooseProductForGift(num,number){
        // 清空搜索框
        $("#goodsNameG").val('');
        $("#goodsNoG").val('');
        $("#goodsInfoItemNoG").val('');
        $("#lowgoodsInfoPriceG").val('');
        $("#highgoodsInfoPriceG").val('');
        doAjaxG(1,8,num,number);
        $("#searchBtn").attr("onclick","chooseProductG("+num+","+number+")");
        $("#chooseAllProG").attr("onchange","chooseAllProG(this,"+num+","+number+")");
        $('#chooseGoodsForGift').modal('show');
    }


    function chooseProduct(){
        doAjax(1,8);
        $('#chooseGoods').modal('show');
    }

    function chooseProductG(num,number){
        doAjaxG(1,8,num,number);
        $('#chooseGoodsForGift').modal('show');
    }

    /*点击添加货品的时候*/

    function doAjax(pageNo, pageSize)
    {
        $("#pageNo").val(pageNo),
                $("#pageSize").val(pageSize),
                $("#chooseAllPro").attr("checked",false);
        $.ajax({
            url:"queryProductForFullbuyPresent.htm",
            data:$("#searchGoodsInfo").serialize(),
            async:true,
            success:function(data){
                var list = data.list;
                var productListHtml = "";
                for (var i = 0; i < list.length; i++)
                {
                    productListHtml = productListHtml + "<tr>" +"<td class='tc'><input type='checkbox' class='productId' name='productId' onclick='addpro(this);'";

                    var pro =  document.getElementsByName("goodsIdP");
                    for(var j=0;j<pro.length;j++){
                        if(pro[j].value==list[i].goodsInfoId){
                            productListHtml = productListHtml +' checked="checked" ';
                        }
                    }
                    productListHtml = productListHtml+" value='" + list[i].goodsInfoId + "'/></td>";
                    productListHtml+='<td><img src="'+list[i].goodsInfoImgId+'" class="goodsImg"  width="50" height="50"/></td>';
                    productListHtml+= "<td  class='goodsTag' >" ;
                    if (list[i].specVo.length > 0)
                    {
                        for (var k = 0; k < list[i].specVo.length; k++)
                        {
                            productListHtml = productListHtml + list[i].specVo[k].spec.specName;
                            productListHtml = productListHtml + ":" + ((list[i].specVo[k].specValueRemark!=null)?(list[i].specVo[k].specValueRemark):(list[i].specVo[k].goodsSpecDetail.specDetailName)) + "<br/>";
                        }
                    }
                    productListHtml = productListHtml + "</td>" + "<td class='goodsNo'>" + list[i].goodsInfoItemNo+ "</td>" + "<td  class='goodsName' title='"+list[i].goodsInfoName+"' >" + list[i].goodsInfoName + "</td>"+"<td class='goodsInfoPreferPrice'>"+list[i].goodsInfoPreferPrice  + "</tr>";
                }
                $("#productAddList tbody").html(productListHtml);
                if($("#productAddList tbody").find("input[checked=checked]").length == 0){
                    $("button[name=cancel]").attr("onclick","removeAllTr()");
                }else{
                    $("button[name=cancel]").removeAttr("onclick");
                }
                $("input[type=button]").button();
                /*添加页脚*/
                $("#productAddList .meneame").html("");
                var foot = "";
                var i = 1;
                for (var l = data.startNo; l <= data.endNo; l++)
                {
                    if ((i - 1 + data.startNo) == data.pageNo)
                    {
                        foot = foot + "<span class='current'> " + (i - 1 + data.startNo) + "</span>";
                    }
                    else
                    {
                        foot = foot + "<a onclick='doAjax(" + (i - 1 + data.startNo) + "," + (data.pageSize) + ")' href='javascript:void(0)'>" + (i - 1 + data.startNo) + "</a>";
                    }
                    i++;
                }
                foot = foot + "";
                /*添加tfoot分页信息*/
                $("#productAddList_table_foot .meneame").html(foot);
            }
        });

    }

    /*添加赠品使用*/
    function doAjaxG(pageNo, pageSize,num,number)
    {
        $("#pageNoG").val(pageNo),
                $("#pageSizeG").val(pageSize),
                $("#chooseAllProG").attr("checked",false);
        $.ajax({
            url:"newqueryProductForCoupon.htm",
            data:$("#searchGoodsInfoG").serialize(),
            async:true,
            success:function(data){
                var list = data.list;
                var productListHtml = "";
                for (var i = 0; i < list.length; i++)
                {
                    productListHtml = productListHtml + "<tr>" +"<td class='tc'><input type='checkbox' class='productId' name='productId' onclick='addproG(this,"+num+","+number+");'";
                    var pro =  $("#fullcash"+number).find("input[name=goodsIdPG"+number+"]");
                    for(var j=0;j<pro.length;j++){
                        if(pro[j].value==list[i].goodsInfoId){
                            productListHtml = productListHtml +' checked="checked" ';
                        }
                    }
                    productListHtml = productListHtml+" value='" + list[i].goodsInfoId + "'/></td>";
                    productListHtml+='<td><img src="'+list[i].goodsInfoImgId+'" class="goodsImg"  width="50" height="50"/></td>';
                    productListHtml+= "<td  class='goodsTag' >" ;
                    if (list[i].specVo.length > 0)
                    {
                        for (var k = 0; k < list[i].specVo.length; k++)
                        {
                            productListHtml = productListHtml + list[i].specVo[k].spec.specName;
                            productListHtml = productListHtml + ":" + ((list[i].specVo[k].specValueRemark!=null)?(list[i].specVo[k].specValueRemark):(list[i].specVo[k].goodsSpecDetail.specDetailName)) + "<br/>";
                        }
                    }
                    productListHtml = productListHtml + "</td>" + "<td class='goodsNo'>" + list[i].goodsInfoItemNo+ "</td>" + "<td  class='goodsName' title='"+list[i].goodsInfoName+"' >" + list[i].goodsInfoName + "</td>"+"<td class='goodsInfoPreferPrice'>"+list[i].goodsInfoPreferPrice  + "</tr>";
                }
                $("#productAddListG tbody").html(productListHtml);
//                if($("#productAddListG tbody").find("input[checked=checked]").length == 0){
//                    $("button[name=cancel]").attr("onclick","removeAllTr()");
//                }else{
                    $("button[name=cancel]").removeAttr("onclick");
//                }
                $("input[type=button]").button();
                /*添加页脚*/
                $("#productAddListG .meneame").html("");
                var foot = "";
                var i = 1;
                for (var l = data.startNo; l <= data.endNo; l++)
                {
                    if ((i - 1 + data.startNo) == data.pageNo)
                    {
                        foot = foot + "<span class='current'> " + (i - 1 + data.startNo) + "</span>";
                    }
                    else
                    {
                        foot = foot + "<a onclick='doAjaxG(" + (i - 1 + data.startNo) + "," + (data.pageSize) + "," + num + "," + number +")' href='javascript:void(0)'>" + (i - 1 + data.startNo) + "</a>";
                    }
                    i++;
                }
                foot = foot + "";
                /*添加tfoot分页信息*/
                $("#productAddList_table_footG .meneame").html(foot);
            }
        });

    }
    /*添加货品的时候 分页*/
    /*改变每页显示的行数*/
    function changePageShow()
    {
        doAjax( 1, $("#list_size").val());
    }
    /*跳转到某一页*/
    function changeShowPage( pageSize)
    {
        doAjax( $("#list_pageno").val(), pageSize);
    }


    function addpro(obj){
        var productId=$(obj).val();
        var goodsImg=$(obj).parents("tr").find(".goodsImg").attr("src");
        var goodsNo=$(obj).parents("tr").find(".goodsNo").text();
        var goodsName=$(obj).parents("tr").find(".goodsName").text();
        var goodsTag=$(obj).parents("tr").find(".goodsTag").html();
        if(obj.checked==true){
            var htm = "<tr id='goods_tr_"+productId+"'>";
            htm+='<td width="92"><img src="'+goodsImg+'" width="50" height="50"/><input type="hidden" name="goodsIdP" id="goods_Id_'+productId+'" value="'+productId+'"/></td>';
            htm+='<td width="98">'+goodsTag+'</td>';
            htm+='<td width="120">'+goodsNo+'</td>';
            htm+='<td  width="300">'+goodsName+'</td>';
            htm+='<td width="70"><button onclick="removeTr(this);">移除</button></td>';
            htm+="</tr>";
            $("#readproduct tbody").append(htm);
        }else{
            $("#goods_tr_"+productId).remove();

        }
    }

    function addproG(obj,count,number){
        var productId=$(obj).val();
        var goodsImg=$(obj).parents("tr").find(".goodsImg").attr("src");
        var goodsNo=$(obj).parents("tr").find(".goodsNo").text();
        var goodsName=$(obj).parents("tr").find(".goodsName").text();
        var goodsTag=$(obj).parents("tr").find(".goodsTag").html();
        if(obj.checked==true){
            var htm = "<tr id='goods_tr_"+productId+"'>";
            htm+='<td width="100"><img src="'+goodsImg+'" width="50" height="50"/><input type="hidden" class="form-control present" name="goodsIdPG'+number+'" id="goods_Id_'+productId+'" value="'+productId+'"/></td>';
            htm+='<td width="100">'+goodsTag+'</td>';
            htm+='<td width="150">'+goodsNo+'</td>';
            htm+='<td  width="300">'+goodsName+'</td>';
            htm+='<td  width="100"><input type="hidden" value="scopeNum'+number+'"/><input type="text" class="form-control valid required integer present" value="1" name="scopeNum'+number+'"/></td>';
            htm+='<td width="100"><button onclick="removeTr(this);">移除</button></td>';
            htm+="</tr>";
            if(count == 1){
                $("#fullcash"+number+" tbody").append(htm);
            }else{
                $("#fullcount"+number+" tbody").append(htm);
            }
        }else{
            $("#goods_tr_"+productId).remove();

        }
    }

    function removeTr(obj){
        $(obj).parents("tr").remove();
    }

    function removeAllTr(){
        $("#readproduct").find("tr").remove();
    }

    function chooseAllPro(obj){
        if(obj.checked){
            $("#productAddList").find("input[name='productId']").each(function(){
                this.checked=true;
                $("#readproduct").find("#goods_tr_"+$(this).val()).remove();
                addpro(this);
            });
        }else{
            $("#productAddList").find("input[name='productId']").each(function(){
                this.checked=false;
                addpro(this);
            });
        }
    }

    function chooseAllProG(obj,num,number){
        if(obj.checked){
            $("#productAddListG").find("input[name='productId']").each(function(){
                this.checked=true;
                if(num==1){
                    $("#fullcash"+number).find("#goods_tr_"+$(this).val()).remove();
                }else{
                    $("#fullcount"+number).find("#goods_tr_"+$(this).val()).remove();
                }
                addproG(this,num,number);
            });
        }else{
            $("#productAddListG").find("input[name='productId']").each(function(){
                this.checked=false;
                addproG(this,num,number);
            });
        }
    }


    function subFormOne(obj){
        //保存货品促销
        var num=0;
        var count=0;
        var f = true;
        //0满金额赠1满数量赠
        var presentType=$("#addFormOne").find("input[name='presentType']").val();
        if(presentType == "0"){
            $(".codexDiv0").each(function(){
                if($(this).find("table").length > 0){
                    count++;
                    if($("#fullcash"+count).find("tr").length == 0){
                        $("#fullcash"+count).parents(".form-group").prev().find(".gifterror").addClass("error").html("请选择赠品");
                        f=false&&f;
                    }else if($("#fullcash"+count).find("tr").length > 20){
                        $("#fullcash"+count).parents(".form-group").prev().find(".gifterror").addClass("error").html("赠品最多20种");
                        f=false&&f;
                    }else{
                        $("#fullcash"+count).parents(".form-group").prev().find(".gifterror").removeClass("error").html("");
                    }
                }
            })
        }else{
            $(".codexDiv1").each(function(){
                if($(this).find("table").length > 0){
                    count++;
                    if($("#fullcount"+count).find("tr").length == 0){
                        $("#fullcount"+count).parents(".form-group").prev().find(".giftcounterror").addClass("error").html("请选择赠品");
                        f=false&&f;
                    }else if($("#fullcount"+count).find("tr").length > 20){
                        $("#fullcount"+count).parents(".form-group").prev().find(".giftcounterror").addClass("error").html("赠品最多20种");
                        f=false&&f;
                    }else{
                        $("#fullcount"+count).parents(".form-group").prev().find(".giftcounterror").removeClass("error").html("");
                    }
                }
            })
        }
        var pro =  document.getElementsByName("goodsIdP");
        if(pro.length==0){
            $("#ps").html('请选择货品');
            $("#ps").addClass("error");
            num++;
            f=false&&f;
        }else{
            f=true&&f;
            $("#ps").html('');
        }


        if($("#addFormOne").valid()&&f){
            var manVal = new Array();
            var codexchoose = $("input[name='codexchoose']:checked").val();
            //满金额赠
            if(codexchoose == "0"){
                $(".fullPriceNewAdd").each(function(){
                    manVal.push(parseFloat($(this).val()));
                });
                if(manVal.length > 1){
                    if(manVal[0] == manVal[1]){
                        showTipAlert("满赠促销满多少的价钱不能相同！");
                        return;
                    }
                }
                if(manVal.length > 2){
                    if(manVal[0] == manVal[2]){
                        showTipAlert("满赠促销满多少的价钱不能相同！");
                        return;
                    }
                    if(manVal[1] == manVal[2]){
                        showTipAlert("满赠促销满多少的价钱不能相同！");
                        return;
                    }
                }
            }else if(codexchoose == "1"){
                $(".fullNewAdd").each(function(){
                    manVal.push(parseFloat($(this).val()));
                });
                if(manVal.length > 1){
                    if(manVal[0] == manVal[1]){
                        showTipAlert("满赠促销满多少的件数不能相同！");
                        return;
                    }
                }
                if(manVal.length > 2){
                    if(manVal[0] == manVal[2]){
                        showTipAlert("满赠促销满多少的件数不能相同！");
                        return;
                    }
                    if(manVal[1] == manVal[2]){
                        showTipAlert("满赠促销满多少的件数不能相同！");
                        return;
                    }
                }
            }


            var codexType=$("#addFormOne").find("input[name='codexType']").val();
            if(codexType=='6'){
                $(".fullcount").find("select").attr("name","");
            }else if(codexType=='16'){
                $(".fullcash").find("select").attr("name","");
            }

            if(num==0){
                num+=1;
                $("#marketingDes").val($(".summernote").code());
                $("#addFormOne").submit();
            }
        }

    }

    function checkIsAll(obj){
        if(obj.checked==true){
            $("#isAlls").val(1);
            $("#towCate").hide();
        }else{
            $("#isAlls").val(0);
            $("#towCate").show();
        }
    }

    function subFormTwo(obj){
        var cus =  $(obj).parents(".tab-pane").find("input[name='lelvelId']:checked");
        var f = true;
        if(!$("#isallCate").prop('checked')){
            var pro =  document.getElementsByName("cateIdp");
            if(pro.length==0){
                $("#pc").html('请选择范围');
                $("#pc").addClass("error");
                f=false&&f;
            }else{
                f=true&&f;
                $("#pc").html('');
            }
        }


        if($("#addFormTwo").valid()&&f){
            $("#marketingDesTwo").val($(".summernoteTwo").code());
            $("#addFormTwo").submit();
        }

    }


    /*处理时间格式化*/
    function timeStamp2String(time)
    {
        var date = new Date(parseFloat(time));
        var datetime = new Date();
        datetime.setTime(date);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
        var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
        return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
    }

    /**
     * 全选反选
     * @param obj
     * @param name
     */
    function allunchecked(obj,name){
        if(obj.checked){
            $(obj).parents(".tab-pane").find($("input[name='"+name+"']")).each(function(){
                this.checked=true;
            });
        }else{
            $(obj).parents(".tab-pane").find($("input[name='"+name+"']")).each(function(){
                this.checked=false;
            });
        }
    }
var num=1;
var count=1;
    function addmore(obj,type){
        var length =$(obj).parents(".tab-pane").find(".count").size();
//        if(length == 2){
//            $(obj).parent().parent().hide();
//        }
        var secondLength =$(obj).parents(".tab-pane").find(".countSecond").size();
        if(type==0 && length <3){
            num+=1;
            var html='<div class="form-group codexDiv count fullcash'+num+' codexDiv'+type+'">';
            html+='<label class="control-label col-sm-4"><span class="text-danger">*</span></label>';
            html+='<div class="col-sm-1"><label class="radio-inline">满</label></div>';
            html+='<div class="col-sm-1"></div>';
            html+='<div class="col-sm-2">';
            html+= '     <input type="text" name="fullPrice" id="fullPrice'+num+'"class="form-control fullPriceNewAdd required money" maxlength="8">';
            html+= '</div>';
            html+= '<div class="col-sm-1"><label class="radio-inline">元</label></div>';
            html+= '<div class="col-sm-4"><div style="padding-left: 20px;"><button type="button" class="btn btn-info pull-left" onclick="chooseProductForGift(1,'+num+');">添加赠品</button><span class="spanweight gifterror"></span></div></div>';
            html+= '<div class="col-sm-4"><select name="presentMode"><option selected value="0">默认全赠</option><option value="1">可选一种</option></select></div>';
            html+='<div class="col-sm-4" onclick="dellevel(this)"><label class="radio-inline" style="color:blue">删除本级促销</label></div></div>';
            html+= '</div>';
            html+= '<div class="form-group codexDiv'+type+' fullcash'+num+'">';
            html+=      '<label class="control-label col-sm-4"></label>';
            html+=      '<div class="col-sm-1"></div>';
            html+=      '<div class="col-sm-19">';
            html+=      '<table class="table table-striped table-hover table-bordered" style="margin-bottom:0px;">';
            html+=          '<thead style="top:0;">';
            html+=          '<tr>';
            html+=              '<th width="100">货品图片</th><th width="100">货品规格</th><th width="150">货品编号</th><th width="300">货品名称</th><th width="100">赠送数量</th><th width="100">操作</th>';
            html+=          '</tr>';
            html+=      '</table>';
            html+=      '<div style="max-height:300px;overflow-y:auto;position:relative;">';
            html+=          '<table class="table table-striped table-hover table-bordered" id="fullcash'+num+'">';
            html+=              '<tbody style=""></tbody>';
            html+=          '</table>';
            html+=      '</div>';
            html+='</div>';
            html+='</div>';
            $(obj).parents(".tab-pane").find(".count:last").next().after(html);
        }

        if(type==1 && secondLength <3){
            count+=1;
            var html='<div class="form-group codexDiv countSecond fullcount'+count+' codexDiv'+type+'">';
            html+='<label class="control-label col-sm-4"><span class="text-danger">*</span></label>';
            html+='<div class="col-sm-1"><label class="radio-inline">满</label></div>';
            html+='<div class="col-sm-1"></div>';
            html+='<div class="col-sm-2">';
            html+= '     <input type="text" name="fullPrice" id="fullPrice'+count+'"  class="form-control fullNewAdd required money" maxlength="8">';
            html+= '</div>';
            html+='<div class="col-sm-1"><label class="radio-inline">件</label></div>';
            html+= '<div class="col-sm-4"><div style="padding-left: 20px;"><button type="button" class="btn btn-info pull-left" onclick="chooseProductForGift(2,'+count+');">添加赠品</button><span class="spanweight giftcounterror"></span></div></div>';
            html+= '<div class="col-sm-4"><select name="presentMode"><option selected value="0">默认全赠</option><option value="1">可选一种</option></select></div>';
            html+='<div class="col-sm-4" onclick="dellevel(this)"><label class="radio-inline" style="color:blue">删除本级促销</label></div></div>';
            html+= '</div>';
            html+= '<div class="form-group codexDiv'+type+' fullcount'+count+'">';
            html+=      '<label class="control-label col-sm-4"></label>';
            html+=      '<div class="col-sm-1"></div>';
            html+=      '<div class="col-sm-19">';
            html+=      '<table class="table table-striped table-hover table-bordered" style="margin-bottom:0px;">';
            html+=          '<thead style="top:0;">';
            html+=          '<tr>';
            html+=              '<th width="100">货品图片</th><th width="100">货品规格</th><th width="150">货品编号</th><th width="300">货品名称</th><th width="100">操作</th>';
            html+=          '</tr>';
            html+=      '</table>';
            html+=      '<div style="max-height:300px;overflow-y:auto;position:relative;">';
            html+=          '<table class="table table-striped table-hover table-bordered" id="fullcount'+count+'">';
            html+=              '<tbody style=""></tbody>';
            html+=          '</table>';
            html+=      '</div>';
            html+='</div>';
            html+='</div>';
            $(obj).parents(".tab-pane").find(".countSecond:last").next().after(html);
        }
    }
    function dellevel(obj){
        $(obj).parent().next("div").remove();
        $(obj).parent().remove();
    }

    function checkLelvel(obj,name){
        var count =0;
        var clength = $(obj).parents(".tab-pane").find($("input[name='"+name+"']")).length;
        $(obj).parents(".tab-pane").find($("input[name='"+name+"']")).each(function(){
            if($(this).prop("checked")){
                count++;
            }
            if(!$(this).prop("checked")){
                $(obj).parents(".tab-pane").find($("input[name='onCheck']")).prop("checked",false);
            }
        });
        if(count == clength){
            $(obj).parents(".tab-pane").find($("input[name='onCheck']")).prop("checked",true);
        }
    }
</script>
</body>
</html>
