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
    <link href="<%=basePath %>css/select2.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
      <link href="<%=basePath %>css/style_new.css" rel="stylesheet">
      <link rel="stylesheet" href="<%=basePath%>js/kindeditor/themes/default/default.css" />
      <link rel="stylesheet" href="<%=basePath%>js/kindeditor/plugins/code/prettify.css" />
      <script src="<%=basePath%>js/kindeditor/kindeditor.js"></script>
      <script src="<%=basePath%>js/kindeditor/lang/zh_CN.js"></script>
      <script src="<%=basePath%>js/kindeditor/plugins/code/prettify.js"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    	.cate_set_list{
    		height:350px;
    	}
        .form-control {display:block;}
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
                <input type="hidden" name="token_val" class="token_val" value="${token }" id="token_val"/>
          <div id="catepanel">
               <h2 class="main_title">${pageNameChild}&nbsp;<small><a href="javascript:void(0);" onclick="$('#HelpAddGoodsTips').modal('show')" style="diaplay:block; float:right; padding-right: 12px">查看帮助<i class="icon iconfont">&#xe611;</i></a></small></h2>

            <div class="goods_cate_choose">

              <div class="cate_choose_area">

                <div class="cate_set container-fluid">

                  <div class="row">

                    <div class="col-xs-8 cate_set_column">
                      <div class="cate_set_item">
                        <input type="hidden" id="CSRFToken" name="CSRFToken" value="${token}"/>
                        <div class="cate_set_cont">
                          <div class="cate_search">
                              <input type="hidden" id="parentId1" >
                            <input type="text" class="cate_search_box" placeholder="输入名称查找" id="search_name1">
                            <a href="javascript:;" onclick="findFirstGoodsCate();"><span class="glyphicon glyphicon-search"></span></a>
                          </div>
                          <div class="cate_set_list" id="cate_list1" >

                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="col-xs-8 cate_set_column">
                      <div class="cate_set_item">

                        <div class="cate_set_cont">
                          <div class="cate_search">
                              <input type="hidden" id="parentId2">
                            <input type="text" class="cate_search_box" placeholder="输入名称查找" id="search_name2">
                            <a href="javascript:;" onclick="findSecondGoodsCate();"><span class="glyphicon glyphicon-search"></span></a>
                          </div>
                          <div class="cate_set_list" id="cate_list2">

                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="col-xs-8 cate_set_column">
                      <div class="cate_set_item">

                        <div class="cate_set_cont">
                          <div class="cate_search">
                              <input type="hidden" name="catId" id="parentId3" class="ch_goods_cate">
                            <input type="text" class="cate_search_box" placeholder="输入名称查找" id="search_name3">
                            <a href="javascript:;" onclick="findThirdGoodsCate()"><span class="glyphicon glyphicon-search"></span></a>
                          </div>
                          <div class="cate_set_list" id="cate_list3">

                          </div>
                        </div>
                      </div>
                    </div>

                  </div>

                </div>

                <div class="cate_choosed">
                  <p>当前选择的是：
                    <strong id="productName1"></strong>
                    <strong id="productName2"></strong>
                    <strong id="productName3"></strong>
                  </p>
                </div>
              </div>

              <div class="text-center">
                <button type="button" class="btn btn-lg btn-primary" onclick="panelNext('#catepanel','#panel1');">确认选择类目并进入下一步</button>
                <button type="button" class="btn btn-lg btn-default" onclick="window.location.href='findAllGoods.htm?menuId=3&menuParentId=7&myselfId=18'">返回列表</button>
              </div>
            </div>

          </div>
          <!-- end -->

          <div id="panel1" style="display:none;">
            <div class="common_info">
              <ul class="common_info_tabs">
                <li class="active"><a href="javascript:void(0);">基本信息</a></li>
                <li><a href="javascript:void(0);" onclick="panelNext('#panel1','#panel2');">详细信息</a></li>
                <%--<li><a href="javascript:void(0);">详细介绍</a></li>--%>
                <%--<li><a href="javascript:void(0);">关联商品</a></li>--%>
              </ul>
              <div class="common_info_cont">

                  <div class="add_good_item">
                    <h4>基本信息</h4>
                          <form  id="goods_info_form" action="" method="post">
                          <div class="add_good_item_cont">
                              <span class="label_text"><span class="text-danger">*</span>商品标题：</span>
                              <div class="add_good_form">
                                  <div class="w500 inline_block">
                                      <input type="text" name="goodsNameTemp" class="form-control name_input required" maxlength="120" minlength="3" value="${importGoods.goodsName }" onblur="$('.name_input').val($(this).val())">
                                  </div>
                              </div>
                          </div>

                          <div class="add_good_item_cont">
                              <span class="label_text">商品副标题：</span>
                              <div class="add_good_form">
                                  <div class="w500 inline_block">

                                      <textarea name="desInputTemp" class="form-control des_input" rows="5" onblur="$('.des_input').text($(this).val())">${importGoods.goodsSubtitle }</textarea>

                                  </div>
                              </div>
                          </div>

						 <div class="add_good_item_cont">
                              <span class="label_text">京东详细：</span>
                              <div class="add_good_form">
                                  <div class="input-group" style="width:500px;">
                                      <%--<span class="input-group-addon" style="line-height: 20px;" >￥</span>--%>
                                      <input type="text" class="form-control httpUrl" value=""  >

                                     
                                  </div> <a href="javascript:;" class="pro_weight_tip_jd help_tips" style="position:absolute; left:650px; top:0;">
                                  <i class="icon iconfont">&#xe611;</i>
                              </a>

                                    <button style="margin-top: 5px;" type="button" class="btn btn-info" onclick="loadDetail(this);">加载</button>
                              </div>
                          </div>
                          <div class="add_good_item_cont">
                              <span class="label_text">商品标签：</span>
                              <div class="add_good_form">
                                  <div class="w500 inline_block">
                                      <c:forEach items="${tagList }" var="tag">
                                          <label class="checkbox-inline"> <input type="checkbox" name="goods_tags" style="margin-top:3px;" class="goods_tag" value="${tag.tagId }">${tag.tagName }</label>
                                      </c:forEach>

                                  </div>
                              </div>
                          </div>

                          <div class="add_good_item_cont">
                              <span class="label_text">服务支持：</span>
                              <div class="add_good_form">
                                  <div class="w500 inline_block">
                                      <c:forEach items="${support}" var="supp" varStatus="i">
                                          <label class="checkbox-inline">
                                              <input type="checkbox" name="ProSupTemp"  class="pro_supp pro_supp" value="${supp.id }" onclick="$('.pro_supp${i.count}').click()">${supp.serviceName }
                                          </label>
                                      </c:forEach>
                                  </div>
                              </div>
                          </div>
                          <div class="add_good_item_cont">
                              <span class="label_text"><span class="text-danger">*</span>销售价格：</span>
                              <div class="add_good_form">
                                  <div class="input-group w200">
                                      <%--<span class="input-group-addon" style="line-height: 20px;" >￥</span>--%>
                                      <input type="text" name="proPriceTemp" class="form-control pro_price sml_input required cash" value="${importGoods.goodsPrice }" onblur="changeAllPrice(this)">
                                  </div>
                              </div>
                          </div>
                          <div class="add_good_item_cont">
                              <span class="label_text"><span class="text-danger"></span>成本价格：</span>
                              <div class="add_good_form">
                                  <div class="input-group w200">
                                      <%--<span class="input-group-addon" style="line-height: 20px;" >￥</span>--%>
                                      <input type="text" name="proCostPriceTemp" class="form-control pro_cost_price  cash" value="${importGoods.goodsCostPrice }" onblur="$('.pro_cost_price').val($(this).val())">
                                  </div>
                              </div>
                          </div>
                          <div class="add_good_item_cont">
                              <span class="label_text">市场价格：</span>
                              <div class="add_good_form">
                                  <div class="input-group w200">
                                      <%--<span class="input-group-addon" style="line-height: 20px;" >￥</span>--%>
                                      <input type="text" name="proMarkPriceTemp" class="form-control pro_mark_price cash"  value="${importGoods.goodsMarketPrice }" onblur="$('.pro_mark_price').val($(this).val())">
                                  </div>
                              </div>
                          </div>
                          <div class="add_good_item_cont">
                              <span class="label_text"><span class="text-danger">*</span>重量：</span>
                              <div class="add_good_form" style="position:relative;">
                                  <input type="text" name="proWeightTemp" class="form-control pro_weight required digits w200" maxlength="8" onblur="$('.pro_weight').val($(this).val())">
                                  <a href="javascript:;" class="pro_weight_tip help_tips" style="position:absolute; left:210px; top:0;">
                                      <i class="icon iconfont">&#xe611;</i>
                                  </a>

                              </div>
                          </div>

                          <div class="add_good_item_cont">
                              <span class="label_text">商品状态：</span>
                              <div class="add_good_form">
                                  <div class="input-group w600">
                                      <label class="checkbox-inline">
                                          <input type="checkbox" name="showListP" class="show_list" value="" onclick="$('.show_list_temp').click();"> 列表显示
                                      </label>
                                      <label class="checkbox-inline">
                                          <!--<input type="checkbox" name="showListP" class="show_mobile" value="" onclick="$('.show_mobile_temp').click();"> 移动版显示-->
                                          <input type="hidden" name="showListP" class="show_mobile" value="1">
                                      </label>
                                  </div>
                              </div>
                          </div>

                          <div class="add_good_item_cont" style="display:none">
                              <span class="label_text"><span class="text-danger">*</span>是否包邮：</span>
                              <div class="add_good_form">
                                  <div class="input-group w600">
                                      <label class="radio-inline">
                                          <input type="radio" name="postFree" class="isMailBay" value="1" onclick="$('.post_free_yes').click();"> 包邮
                                      </label>
                                      <label class="radio-inline">
                                          <input type="radio" name="postFree" class="isMailBay" value="0" checked="checked" onclick="$('.post_free_not').click();"> 买家承担运费
                                      </label>
                                  </div>
                              </div>
                          </div>
                          <div class="add_good_item_cont">
                              <span class="label_text"><span class="text-danger">*</span>是否上架：</span>
                              <div class="add_good_form">
                                  <div class="input-group w600">
                                      <label class="radio-inline">
                                          <input type="radio" name="up" class="pro_status"  value="1" onclick="$('.up_yes').click();"> 立即上架
                                      </label>
                                      <label class="radio-inline">
                                          <input type="radio"name="up" class="pro_status" value="0" checked="checked" onclick="$('.up_not').click();"> 下架
                                      </label>
                                  </div>
                              </div>
                          </div>
                          <div class="add_good_item_cont" style="display:none;">
                              <span class="label_text"><span class="text-danger">*</span>参与会员折扣：</span>
                              <div class="add_good_form">
                                  <div class="input-group w600">
                                      <label class="radio-inline">
                                          <input type="radio" name="customerDisc" class="customer_discount" value="1" onclick="$('.customer_discount_yes').click();"> 是
                                      </label>
                                      <label class="radio-inline">
                                          <input type="radio" name="customerDisc" class="customer_discount" value="0" checked="checked" onclick="$('.customer_discount_not').click();"> 否
                                      </label>
                                  </div>
                              </div>
                          </div>
                          </form>
                        <div class="add_good_item_cont">
                          <span class="label_text"><span class="text-danger">*</span>商品品牌：</span>
                          <div class="add_good_form">
                            <div class="w200">
                              <div class="input-group">
                             <!--    <input type="text" class="form-control" id="brand">
                                <div class="input-group-btn">
                                  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                    <span class="caret"></span>
                                  </button>
                                  <div class="dropdown-menu dropdown-menu-right" role="menu">
                                  </div>
                                </div> -->
                                <select class="inline required" data-live-search="true" name="brandId" id="goods_brand">
                                    <c:forEach items="${brandList }" var="brand">
                                    <option value="${brand.brandId }">${brand.brandName }</option>
                                    </c:forEach>
                                </select>
                                <!-- /btn-group -->
                              </div>
                            </div>
                          </div>
                        </div>  
                         
                        <div class="add_good_item_cont">
                          <span class="label_text"><span class="text-danger">*</span>商品属性：</span>
                          <div class="add_good_form">
                            <div class="w450 inline_block">
                              <table class="table table-bordered table-hover paramTables" id="attribute">
                                <thead>
                                <!-- <tr>
                                  <th>参数名</th>
                                  <th>参数值</th>
                                </tr> -->
                                </thead>
                                <tbody>

                                </tbody>
                              </table>
                            </div>
                          </div>
                        </div>

                      <div class="add_good_item_cont">
                          <span class="label_text"><span class="text-danger"></span>商品参数：</span>
                          <div class="add_good_form">
                              <div class="w450 inline_block">
                                  <table class="table table-bordered table-hover paramTables" id="parameter">
                                      <thead>
                                      <!-- <tr>
                                        <th>参数名</th>
                                        <th>参数值</th>
                                      </tr> -->
                                      </thead>
                                      <tbody>

                                      </tbody>
                                  </table>
                              </div>
                          </div>
                      </div>
                        <div class="add_good_item_cont">
                          <span class="label_text"><span class="text-danger">*</span>规格：</span>
                          <div class="add_good_form">
                            <div class="w600 inline_block type_spec">



                            </div>
                          </div>
                        </div>


                      <div class="add_good_item_cont">
                          <span class="label_text">关联商品：</span>
                          <div class="add_good_form">
                              <div class="w600 inline_block">
                                  <button type="button" class="btn btn-info" onclick="showAddGoodsRelModal()">添加关联商品</button>
                                  <table class="table table-bordered table-hover mt20" id="select_rel_goods">
                                    <thead style="display: none;">
                                        <tr>
                                            <td width="80px">商品图片</td>
                                            <td width="200px">商品编号</td>
                                            <td width="450px">商品名称</td>
                                            <td width="60px">操作</td>
                                        </tr>
                                    </thead>
                                      <tbody></tbody>
                                  </table>
                              </div>
                          </div>
                      </div>

                      <div class="add_good_item_cont">
                          <span class="label_text">PC版详情：</span>
                          <textarea name="goods_desc" cols="100" rows="8" style="width: 100%; height: 200px; visibility: hidden;"></textarea>
                          <textarea style="display:none" name="goodsDetailDesc" id="saveGoodsDesc"></textarea>
                          <form class="save_goods_desc" action="newUploadSaveGoodsDesc.htm?CSRFToken=${token }" method="post" target="hidden_frame" enctype="multipart/form-data">
                              <input type="hidden" class="token_val" value="${token }" />
                              <input type="hidden" class="new_goods_id" name="goodsId" value="">
                              <input type="hidden" class="goods_desc" name="goodsDetailDesc" value="">
                              <input type="hidden" class="goods_mobile_desc" name="goodsMobileDesc" value="">
                          </form>
                      </div>
                      <div class="add_good_item_cont">
                          <span class="label_text">移动版详情：</span>
                          <textarea name="mobile_desc" cols="100" rows="8" style="width: 100%; height: 200px; visibility: hidden;"></textarea>
                          <textarea style="display:none" name="goodsMobileDesc" id="saveGoodsMobileDesc"></textarea>
                      </div>




                      <div class="add_good_item_cont">
                          <span class="label_text">seo标题：</span>
                          <div class="add_good_form">
                              <div class="w500 inline_block">

                                  <textarea name="desInputTemp" class="form-control seo_title" rows="5">${importGoods.seoTit }</textarea>

                              </div>
                          </div>
                      </div>

                      <div class="add_good_item_cont">
                          <span class="label_text">seo关键字：</span>
                          <div class="add_good_form">
                              <div class="w500 inline_block">

                                  <textarea name="desInputTemp" class="form-control seo_key" rows="5">${importGoods.seoKey }</textarea>

                              </div>
                          </div>
                      </div>

                      <div class="add_good_item_cont">
                          <span class="label_text">seo描述：</span>
                          <div class="add_good_form">
                              <div class="w500 inline_block">

                                  <textarea name="desInputTemp" class="form-control seo_des" rows="5">${importGoods.seoDes }</textarea>

                              </div>
                          </div>
                      </div>

                  </div>

                  <div class="add_good_sep"><div></div></div>

                  <div class="text-center mt20">
                      <button type="button" class="btn btn-lg btn-default" onclick="panelTop('#catepanel','#panel1');">上一步</button>
                      <button type="button" class="btn btn-primary btn-lg" onclick="panelNext('#panel1','#panel2');">进入下一步</button>
                  </div>
              </div>
            </div>

          </div>

        <!-- end -->

         <div id="panel2" style="display:none;">
           <div class="common_info">
              <ul class="common_info_tabs">
                <li><a href="javascript:void(0);" onclick="panelTop('#panel1','#panel2');">基本信息</a></li>
                <li class="active"><a href="javascript:void(0);">详细信息</a></li>
                <%--<li><a href="javascript:void(0);">详细介绍</a></li>--%>
                <%--<li><a href="javascript:void(0);">关联商品</a></li>--%>
              </ul>
              <div class="common_info_cont">

                <ul class="nav nav-tabs dinfo_tabs" role="tablist">
                 <!--  <li role="presentation" class="active"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">白色 S</a></li> -->
                </ul>
                <div class="tab-content dinfo_wp">

                </div>

                <div class="demo_box" style="display:none;">

                  <div role="tabpanel" class="tab-pane" id="">
                    <div class="common_form p20 common_form_max">
                      <form class="form-horizontal" method="post">
                        <div class="form-group">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>商品编号：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <input type="text" class="form-control no_input p_code" value="${importGoods.goodsNo }" maxlength="32" minlength="10" onblur="checkProNo(this);" >
                              <a href="javascript:;" onclick="generateProNo(this)">生成编号</a>
                            <input type="hidden" class="exist_flag" value="0">
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                          <div class="form-group">
                              <label class="control-label col-sm-3">商品条形码：</label>
                              <div class="col-sm-1"></div>
                              <div class="col-sm-6">
                                  <input type="text" class="form-control barcode_input" value="" maxlength="64" >
                              </div>
                              <div class="col-sm-3"></div>
                          </div>
                        <div class="form-group">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>商品标题：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-14">
                            <input type="text" class="form-control name_input required" maxlength="50" minlength="3" value="${importGoods.goodsName }"onblur="checkProductForm()">
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;color:gray;">
                          <label class="control-label col-sm-3">商品副标题：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <textarea class="form-control des_input" rows="5">${importGoods.goodsSubtitle }</textarea>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3">服务支持：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-10">

                          <c:forEach items="${support}" var="supp" varStatus="i">
                           <label class="checkbox-inline">
                          	 <input type="checkbox"  class="pro_supp pro_supp${i.count}" value="${supp.id }">${supp.serviceName }
                          </label>
						</c:forEach>

                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>销售价格：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <div class="input-group w200">
                              <span class="input-group-addon">￥</span>
                              <input type="text" class="form-control pro_price sml_input required isNoInteger" value="${importGoods.goodsPrice }">
                            </div>
                          </div>
                          <div class="col-sm-3">

                          </div>
                        </div>
                        <div class="form-group">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>商品定价：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-10">
                            <table class="table table-hover table-bordered">
                              <thead>
                              <tr>
                                <th>仓库</th>
                                <th>库存</th>
                                <th>销售价</th>
                              </tr>
                              </thead>
                              <tbody>

							 <c:if test="${fn:length(wareHouse)==0}">
							 <tr>	<td colspan="3">
											<span style="color:red;">请先到<a href="queryWareHouseByPageBean.htm">仓库列表</a>，添加仓库 </span>
										</td>
									</tr>
							 </c:if>
                              <c:forEach items="${wareHouse}" var="ware">
									<tr>
										<td>
										<input type="hidden" class="ware_id" name="wareId" value="${ware.wareId }"/>${ware.wareName }
										</td>
										<td style="text-align:left;">
										<input class="form-control w100 ware_stock required digits" type="text" value="0" maxlength="6" onblur="checkProductForm()"/>
										</td>
										<td style="text-align:left;">
										<input type="text" name="productPrices" value="${importGoods.goodsPrice }" class="form-control w100
										 ware_price required cash" onblur="checkProductForm()">
										</td>
									</tr>
								</c:forEach>
                             <!--此Form只为验证使用-->
                                     <tr>
                                         <td>
                                             批量设置
                                         </td>
                                         <td>
                                             <input class="form-control w100 batc_set_stock required number" type="text" value="0" style="display: none;" />
                                             <a class="batch_set_stock_ctrl" href="javascript:;" onclick="displayBatchSetStock(this)">设置库存</a>
                                             <a class="do_batch_set_stock_ctrl" href="javascript:;" onclick="doBatchSetStock(this)" style="display: none;">确定</a>
                                             <a class="cancel_batch_set_stock_ctrl" href="javascript:;" onclick="hideBatchSetStock(this)" style="display: none;">取消</a>
                                         </td>
                                         <td>
                                             <input class="form-control w100 batch_set_price required cash" type="text" value="0.00" style="display: none;">
                                             <a class="batch_set_price_ctrl" href="javascript:;" onclick="displayBatchSetPrice(this)">设置价格</a>
                                             <a class="do_batch_set_price_ctrl" href="javascript:;" onclick="doBatchSetPrice(this)" style="display: none;">确定</a>
                                             <a class="cancel_batch_set_price_ctrl" href="javascript:;" onclick="hideBatchSetPrice(this)" style="display: none;">取消</a>
                                         </td>
                                     </tr>
                                 <tr>
                                     <td colspan="3">
                                         <a class="copy_ware_to_other_spec_ctrl" href="javascript:;" onclick="copyWareToOtherSpec(this)">将此设置应用到其他货品</a>
                                     </td>
                                 </tr>
                              </tbody>
                            </table>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>成本价格：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <div class="input-group w200">
                              <span class="input-group-addon">￥</span>
                              <input type="text" class="form-control pro_cost_price isNoInteger" value="${importGoods.goodsCostPrice }">
                            </div>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>市场价格：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <div class="input-group w200">
                              <span class="input-group-addon">￥</span>
                              <input type="text" class="form-control pro_mark_price isNoInteger"  value="${importGoods.goodsMarketPrice }">
                            </div>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>重量：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <div class="input-group w200">
                              <input type="text" class="form-control pro_weight required digits">
                              <span class="input-group-addon">克</span>
                            </div>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>商品图片：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <button type="button" class="btn btn-default chooseProimg">编辑商品图片</button>
                          </div>
                            <div class="col-sm-3">
                                <a href="javascript:;" class="productsImg help_tips">
                                    <i class="icon iconfont">&#xe611;</i>
                                </a>
                            </div>
                        </div>
                        <div class="form-group">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>已选择图片：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-14 choose_imgs"></div>
                          <div class="col-sm-3"></div>
                        </div>

                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3">商品状态：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <label class="checkbox-inline">
                              <input type="checkbox"  class="show_list show_list_temp" value=""> 类目页推荐
                            </label>
                            <label class="checkbox-inline">
                              <input type="checkbox"  class="show_mobile show_mobile_temp" value=""> 手机版推荐
                            </label>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>是否包邮：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <label class="radio-inline">
                              <input type="radio" name="isMailBay" class="isMailBay post_free_yes" value="1"> 包邮
                            </label>
                            <label class="radio-inline">
                              <input type="radio" name="isMailBay" class="isMailBay post_free_not" value="0" checked="checked"> 买家承担运费
                            </label>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>是否上架：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <label class="radio-inline">
                              <input type="radio" name="pro_status" class="pro_status up_yes"  value="1"> 立即上架
                            </label>
                            <label class="radio-inline">
                              <input type="radio" name="pro_status" class="pro_status up_not" value="0" checked="checked"> 下架
                            </label>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                        <div class="form-group" style="display: none;">
                          <label class="control-label col-sm-3"><span class="text-danger">*</span>参与会员折扣：</label>
                          <div class="col-sm-1"></div>
                          <div class="col-sm-6">
                            <label class="radio-inline">
                              <input type="radio" name="inlineRadioOptions3" class="customer_discount customer_discount_yes" value="1"> 是
                            </label>
                            <label class="radio-inline">
                              <input type="radio" name="inlineRadioOptions3" class="customer_discount customer_discount_not" value="0" checked="checked"> 否
                            </label>
                          </div>
                          <div class="col-sm-3"></div>
                        </div>
                      </form>
                    </div>
                  </div>

                </div>
                <div class="add_good_sep">
                  <div></div>
                </div>

                <div class="text-center mt20 mb20">

                  <%--<button type="button" class="btn btn-primary btn-lg" onclick="panelNext('#panel2','#panel3');">进入下一步</button>--%>
                      <button type="button" class="btn btn-lg btn-default" onclick="panelTop('#panel1','#panel2');">上一步</button>
                      <button type="button" class="btn btn-primary btn-lg" onclick="saveProduct();" id="fbsp">发布商品</button>
                </div>

              </div>

            </div>


         </div>
        <!-- end -->
        <div id="panel3" style="display:none;">
          <div class="common_info">
              <ul class="common_info_tabs">
                <li><a href="javascript:void(0);">基本信息</a></li>
                <li><a href="javascript:void(0);">详细信息</a></li>
                <%--<li class="active"><a href="javascript:void(0);">详细介绍</a></li>--%>
                <%--<li><a href="javascript:void(0);">关联商品</a></li>--%>
              </ul>
              <div class="common_info_cont">

                <%--<form role="form">
                  <h4>PC版详情</h4>
                  <div class="summernotedesc"></div>
                  <h4>移动版详情</h4>
                  <div class="summernotemobile"></div>
                </form>--%>

                <div class="add_good_sep">
                  <div></div>
                </div>

                <div class="text-center mt20">
                  <button type="button" class="btn btn-primary btn-lg" onclick="panelNext('#panel3','#panel4');">进入下一步</button>
                <button type="button" class="btn btn-lg btn-default" onclick="panelTop('#panel2','#panel3');">上一步</button>
                </div>



        </div>

        <!-- end -->

        <div id="panel4" style="display:none;">
           <div class="common_info">
              <ul class="common_info_tabs">
                <li><a href="javascript:void(0);">基本信息</a></li>
                <li><a href="javascript:void(0);">详细信息</a></li>
                <%--<li><a href="javascript:void(0);">详细介绍</a></li>--%>
                <%--<li class="active"><a href="javascript:void(0);">关联商品</a></li>--%>
              </ul>
              <div class="common_info_cont">

                <div class="mb20">
                  <button type="button" class="btn btn-info" onclick="$('#linkGoods').modal('show')">添加关联商品</button>
                  <button type="button" class="btn btn-info">取消关联</button>
                </div>

                <table class="table table-striped table-hover" id="aboutGoodsList1">
                  <thead>
                  <tr>
                    <th><input type="checkbox"></th>
                    <th>商品图片</th>
                    <th>商品名称</th>
                    <th>商品编号</th>
                    <th>商品分类</th>
                    <th>商品品牌</th>
                  </tr>
                  </thead>
                  <tbody>

                  </tbody>
                </table>

                <div class="add_good_sep">
                  <div></div>
                </div>

                <div class="text-center mt20">
                  <button type="button" class="btn btn-primary btn-lg" onclick="saveProduct();">发布商品</button>
                  <button type="button" class="btn btn-lg btn-default" onclick="panelTop('#panel3','#panel4');">上一步</button>
                </div>

              </div>

            </div>


        </div>
        <!-- end -->







          </div>

        </div>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="addSEO"  role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">添加SEO设置</h4>
          </div>
          <div class="modal-body">
            <form role="form" class="form-horizontal">
              <div class="form-group">
                <label class="control-label col-sm-5">SEO标题：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-16">
                  <input type="text" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-sm-5">SEO关键字：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-16">
                  <input type="text" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-sm-5">是否启用：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-16">
                  <label class="radio-inline">
                    <input type="radio" name="open" id="open1" value="option1" checked> 是
                  </label>
                  <label class="radio-inline">
                    <input type="radio" name="open" id="open2" value="option2"> 否
                  </label>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-sm-5">SEO描述：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-16">
                  <textarea class="form-control" rows="5"></textarea>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary">保存</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div>
      </div>
    </div>


    <!-- Modal -->
    <div class="modal fade" id="addGoodsRelModal"  role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加关联商品</h4>
                </div>
                <div class="modal-body">
                    <form role="form" class="form-inline" >
                        <div class="mb20">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">商品编号</span>
                                    <input type="text" class="form-control" placeholder="商品编号" id="goods_no">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">商品名称</span>
                                    <input type="text" class="form-control" placeholder="商品名称" id="goods_name">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">商品品牌</span>
                                    <select   class="form-control w150 " name="goodsBrandId" data-live-search="false" id="goodsBrandId">
                                        <option value="">选择品牌</option>
                                        <c:forEach items="${brandList }" var="brand">
                                            <option value="${brand.brandId }"
                                                    <c:if test="${searchBean.goodsBrandId==brand.brandId }">
                                                        selected="selected"
                                                    </c:if>
                                                    >${brand.brandName }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group"  style="padding:10px 0px;">
                                <button type="button" class="btn btn-info" onclick="queryGoodsByParam(1)">搜索</button>
                            </div>
                            <table class="table table-striped table-hover" id="aboutGoodsList">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" onclick="allunchecked(this,'aboutGoodsIdSelect')"></th>
                                    <th width="75px">商品图片</th>
                                    <th>商品编号</th>
                                    <th width="300px">商品名称</th>
                                    <th>商品分类</th>
                                    <th>商品品牌</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>

                            <div class="table_foot">
                                <div class="table_pagenav pull-right">
                                    <div class="meneame" id="pageFoot">

                                    </div>
                                </div>

                                <div class="clr"></div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveRelGoods()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 保存商品的表单 -->
	<form class="save_goods_info" action="newUploadGood.htm?CSRFToken=${token }" method="post" target="hidden_frame" enctype="multipart/form-data">
        <input type="hidden" name="importGoodsId" value="${importGoodsId}"/>
        <input type="hidden" name="importGoodsIdArray" value=""/>
	</form>


<!-- 模拟无刷新上传图片用到的 -->
	<iframe name="hidden_frame" style="display:none"></iframe>

	<input type="hidden" class="flag_saved" value="0">




 <div class="modal fade" id="HelpAddGoodsTips"  role="dialog">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">添加商品</h4>
          </div>
          <div class="modal-body">
            <div class="modal-article">
              <p><em>1.</em>商品配置好后，开始添加商品，首先选择分类，该分类就是在“商品分类”中配置好的一二三级分类</p>
              <img src="./images/syshelp/up_goods1.png" alt="">
              <p><em>2.</em>进入下一步后填写商品基本信息</p>
              <img src="./images/syshelp/up_goods2.png" alt="">
              <img src="./images/syshelp/up_goods3.png" alt="">
              <img src="./images/syshelp/up_goods4.png" alt="">
			  <p><em>3.</em>点击下一步添加相应规格货品信息</p>
              <img src="./images/syshelp/up_goods5.png" alt="">
			  <p>点击发布商品，商品添加成功</p>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=basePath %>js/bootstrap.min.js"></script>
    <script src="<%=basePath %>js/summernote.min.js"></script>
    <script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
    <script src="<%=basePath %>js/bootstrap-select.min.js"></script>
    <script src="<%=basePath %>js/bootstrap-suggest.js"></script>
    <script src="<%=basePath %>js/common.js"></script>
    <script src="<%=basePath %>js/common/common_alert.js"></script>
    <script src="<%=basePath %>js/common/common_checked.js"></script>
    <script src="<%=basePath %>js/common/common_date.js"></script>
    <script src="<%=basePath %>js/goods/goods_upload.js"></script>
    <script src="<%=basePath %>js/select2.min.js"></script>

    <script>
      $(function(){
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


        /* 为选定的select下拉菜单开启搜索提示 */

        $('select[data-live-search="true"]').select2();
        //这是普通的带搜索框下拉菜单，只可用于纯英文或纯中文选项


        /* 为选定的select下拉菜单开启搜索提示 END */

       /* 富文本编辑框 */
        $('.summernotedesc').summernote({
            height: 300,
            tabsize: 2,
            lang: 'zh-CN',
            onImageUpload: function(files, editor, $editable) {
                sendFile(files,editor,$editable);
            }
        });


        /* 富文本编辑框 */
        $('.summernotemobile').summernote({
            height: 300,
            tabsize: 2,
            lang: 'zh-CN',
            onImageUpload: function(files, editor, $editable) {
                sendFile(files,editor,$editable);
            }
        });

        $('.spec_set input').change(function(){
          if($(this).is(':checked')){
            $(this).parent().parent().next().slideDown('fast');
          }
          else {
            $(this).parent().parent().next().slideUp('fast');
          }
        });


      });

      function panelNext(hidepanel,showpanel){
          if(showpanel=='#panel1'){
              //验证选择分类
              if(checkCate()){
                  loadTypeParam();
              }else{
                  showNoDeleteConfirmAlert('请选择三级分类！(如果没有分类请先创建分类)');
                  return ;
              }

          }

          if(showpanel=='#panel2'){
              if(!$("#goods_info_form").valid()) {
                  return;
              }
              if(checkTypeSpec()){
                  loadProduct();
              }else{
                  showNoDeleteConfirmAlert('请选择规格和对应规格数据！');
                  return ;
              }




          }

          if(showpanel=='#panel3'){
              if(!checkForm()){
                 return;
              }
          }

          if(showpanel=='#panel4'){
                  searchGuanlian();
          }
          $(hidepanel).hide();
          $(showpanel).show();
        
      }


      function panelTop(showpanel,hidepanel){
    	  $(showpanel).show();
          $(hidepanel).hide();
      
      }


      function searchGuanlian(){

          /*根据选中分类加载相关商品*/
          $.get("findGoodByCatId.htm?catId=" +$('#parentId3').val()+"&CSRFToken=${token}", function (data)
          {
              var aboutGoodsHtml = "";
              if (data.length > 0)
              {
                  for (var i = 0; i < data.length; i++)
                  {
                      aboutGoodsHtml = aboutGoodsHtml + "<tr><td><input type='checkbox' class='ch_about' name='aboutGoodsId' value='" + data[i].goodsId + "'/></td>" + "<td><img width='50' height='50' src=" + data[i].goodsImg + " /></td><td>" + data[i].goodsName + "...</td><td title='"+data[i].goodsNo+"'>" + data[i].goodsNo + "</td>";
                      aboutGoodsHtml = aboutGoodsHtml + "<td>" + data[i].goodsType.typeName + "</td><td>" + data[i].goodsBrand.brandName + "</td></tr>";
                  }
              }
              else
              {
                  aboutGoodsHtml = aboutGoodsHtml + "<tr><td colspan='6'>选择的分类下暂时还没有商品</td></tr>";
              }
              $("#aboutGoodsList tbody").html(aboutGoodsHtml);
          });


          }
      //判断分类
      function checkCate(){
          if($("#parentId3").val()==''){
              return false;
          }else{
              return true;
          }
      }

      //判断规格
      function checkTypeSpec(){
          var $typelength=$(".type_spec:checked").length;
          if($typelength==0){
              return false;
           }else{

               var type_spec = $(".type_spec:checked");

               for(var i=0;i<type_spec.length;i++){
                   var openspec = $(".openSpecValue_"+type_spec[i].value);
                   var f=0;
                   for(var j=0;j<openspec.length;j++){
                       if(openspec[j].checked==true){
                           f=1;
                       }
                   }
                   if(f==0){
                       return false;
                   }
               }

              return true;
           }

      }


      function checkGoodsForm(){
          var f =true;
          var forms =  $("#goods_info_form");
          for(var i=0;i<forms.length;i++){
              var requireds = $(forms[i]).find(".required");
              for(var j=0;j<requireds.length;j++){
                  if(requireds[j].value==''){
                      $(requireds[j]).addClass('error');
                      $(requireds[j]).next('.error').remove();
                      $(requireds[j]).after('<label class="error">不能为空</label>');
                      f=f&&false;
                  }else{
                      $(requireds[j]).removeClass('error');
                      $(requireds[j]).next('.error').remove();
                  }
              }

              if(f){
                  var numbers = $(forms[i]).find(".number");
                  var numberReg =/^[0-9]+[.]{0,1}[0-9]{0,2}$/;
                  for(var j=0;j<numbers.length;j++){
                      if(numbers[j].value!=''&&(numberReg.test(numbers[j].value))){
                          $(requireds[j]).removeClass('error');
                          $(requireds[j]).next('.error').remove();
                      }else{
                          $(numbers[j]).addClass('error');
                          $(numbers[j]).next('.error').remove();
                          $(numbers[j]).after('<label class="error">请输入合法的数字</label>');
                          f=f&&false;
                      }
                  }
              }


              if(f){
                  var digits = $(forms[i]).find(".digits");
                  var digitsReg = /^[0-9]+$/;
                  for(var j=0;j<digits.length;j++){
                      if(digits[j].value!=''&&(digitsReg.test(digits[j].value))){
                          $(requireds[j]).removeClass('error');
                          $(requireds[j]).next('.error').remove();
                      }else{
                          $(digits[j]).addClass('error');
                          $(digits[j]).next('.error').remove();
                          $(digits[j]).after('<label class="error">请输入整数</label>');
                          f=f&&false;
                      }
                  }
              }

              var s =  $(forms).find(".choose_imgs").html();
              if(s==''){
                  showNoDeleteConfirmAlert('请添加对应货品的图片！');
                  f=f&&false;
              }


              if(f){
                  var exist_flag = $(forms[i]).find(".exist_flag").val();
                  if(exist_flag=='0'){
                      $(exist_flag).removeClass('error');
                      $(exist_flag).next('.error').remove();
                  }else{
                      $(exist_flag).addClass('error');
                      $(exist_flag).next('.error').remove();
                      $(exist_flag).after('<label class="error">商品编号不正确</label>');
                      f=f&&false;
                  }
              }

          }
          return f;
      }


function loadDetail(obj){
	var httpUrl = $(obj).parents('.add_good_form').find(".httpUrl").val();
	 $.ajax({
			url: "jdtriggeraddgoods.htm", 
			context: document.body, 
			 type: 'POST',
			 data:{httpUrl:httpUrl},
			success: function(data){
				
					if(data!=""&&data!=null){
						
						
						$(obj).parents('.add_good_item').next().find(".品牌").find(".type_param_val").val("ceshi");
						
						for(var i=0;i<data.outPrams.length;i++){
							try{
								$(obj).parents('.add_good_item').find(".paramTables").find("."+data.outPrams[i].outParam).find(".type_param_val").val(data.outPrams[i].outValue);	
							}catch(e){

							}
						}
						
						$(".name_input").val(data.goodName);
						$('.seo_title').val(data.seo.seoTitle);
						$('.seo_key').val(data.seo.seoKeywords);
						$('.seo_des').val(data.seo.seoDescription);
						var htm = '';
						for(var j=0;j<data.imgList.length;j++){
							htm+='<img src="'+data.imgList[j].imgUrl+'"/>';
						}
						var htmm = '';
						for(var j=0;j<data.mimgList.length;j++){
							htmm+='<img src="'+data.mimgList[j].imgUrl+'"/>';
						}
						editor1.html(htm);
						editor2.html(htmm);
					}  
					
				}


});
}


    </script>
  </body>
</html>
