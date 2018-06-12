<%--
  Created by IntelliJ IDEA.
  User: NP-Heh
  Date: 2015/3/31
  Time: 13:06
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
    <%--<link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">--%>
    <%--<link href="<%=basePath %>css/summernote.css" rel="stylesheet">--%>
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
      <link href="<%=basePath %>css/select2.min.css" rel="stylesheet">
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

                <h2 class="main_title">${pageNameChild} <small>(共${fn:length(groupVo.productList)}条)</small></h2>

                <div class="common_data p20">

                    <div class="data_ctrl_area mb20">
                        <div class="data_ctrl_search pull-right"></div>
                        <div class="data_ctrl_brns pull-left">

                            <%--<button type="button" class="btn btn-info" onclick="showDeleteBatchConfirmAlert('deleteGroupProductForm','productIds')">--%>
                                <%--<i class="glyphicon glyphicon-trash"></i> 取消--%>
                            <%--</button>--%>
                            <%--<button type="button" class="btn btn-info" onclick="window.location.href='findAllGroup.htm'">--%>
                                <%--<i class="icon iconfont">&#xe614;</i> 返回商品组合列表--%>
                            <%--</button>--%>
                        </div>
                        <div class="clr"></div>
                    </div>
                    <form action="updateGroup.htm" id="updateGroupForm" method="post" class="form-horizontal">


                        <%--KKK 组合基本信息--%>
                            <div class="mb20 mt50 fs15">
                                基本信息
                            </div>

                            <input type="hidden" name="CSRFToken" value="${token}"/>

                            <input type="hidden" name="groupId" value="${groupVo.groupId}">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                    <span class="text-danger">*</span>组合名称：
                                </label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control required specstr" name="groupName"
                                           id="group_name" minlength="2" maxlength="16" value="${groupVo.groupName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                    <span class="text-danger">*</span>组合类型：
                                </label>
                                <div class="col-sm-6">
                                    <label class="radio-inline">
                                        <input type="radio" name="groupPrefertype" id="groupPrefertype_00"
                                               onclick="changGroupType(0)"
                                               <c:if test="${groupVo.groupPrefertype eq '0'}">
                                               checked="checked"</c:if> value="0"> 特惠套装
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="groupPrefertype" id="groupPrefertype_01"
                                        <c:if test="${groupVo.groupPrefertype eq '1'}">
                                               checked="checked"</c:if>
                                               onclick="changGroupType(1)" value="1"> 人气组合
                                    </label>
                                </div>
                            </div>

                            <%--KKK 组合 货品信息--%>
                            <div class="mb20 mt50 fs15">
                                组合商品
                            </div>


                            <button type="button" class="btn btn-info mb20"   style="margin-left: 50px;" onclick="chooseProduct(${groupVo.groupId});">
                                <i class="glyphicon glyphicon-plus "></i> 添加
                            </button>

                            <table class="table table-striped table-hover" style="margin-left: 50px;">
                                    <thead>
                                    <tr>
                                        <th width="50"><input  class="ignore" type="checkbox" onclick="selectAll('productIds',this)"></th>
                                        <th>图片</th>
                                        <th width="300px">货品名称</th>
                                        <th>规格值</th>
                                        <th>货号</th>
                                        <th>销售价</th>
                                        <th>数量</th>
                                        <th width="150">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${groupVo.productList }" var="product" varStatus="sta">
                                        <tr>
                                            <td><input class="ignore" type="checkbox" name="productIds" value="${product.productId }"></td>
                                            <td><img alt="" height="50" src="${product.productDetail.goodsInfoImgId }"></td>
                                            <td>${product.productDetail.goodsInfoName}</td>
                                            <td>
                                                <c:forEach items="${product.productDetail.specVo }" var="spec"
                                                           varStatus="sta2">
                                                    ${spec.spec.specName }:${spec.specValueRemark } <br/>
                                                </c:forEach>
                                            </td>
                                            <td>${product.productDetail.goodsInfoItemNo}</td>
                                            <td>
                                                <span class="minPrice">${product.minPrice}</span>
                                                ~
                                                <span class="maxPrice">${product.maxPrice}</span>
                                            </td>
                                            <%--<c:if test="${product.productNum == null}">--%>
                                                <%--<td>1</td>--%>
                                            <%--</c:if>--%>
                                            <%--<c:if test="${product.productNum != null}">--%>
                                                <td>${product.productNum}</td>
                                            <%--</c:if>--%>
                                            <td>
                                                <div class="btn-group">
                                                    <button type="button" class="btn btn-default"
                                                            onclick="showDeleteOneConfirmAlert('delGroupReleProduct.htm?CSRFToken=${token}&groupId=${groupVo.groupId}&productId=${product.productId }')">
                                                        取消选择
                                                    </button>
                                                    <button type="button" class="btn btn-default dropdown-toggle"
                                                            data-toggle="dropdown">
                                                        <span class="caret"></span>
                                                        <span class="sr-only">Toggle Dropdown</span>
                                                    </button>
                                                    <ul class="dropdown-menu" role="menu">
                                                        <li><a href="${basicAddress}/item/${product.productId}.html"
                                                               target="_blank">预览</a></li>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            <p style="margin-left: 50px;">已添加 ${fn:length(groupVo.productList)}件商品,总价 ￥<span id="minSumPrice"></span>~￥<span id="maxSumPrice"></span>
                            </p>

                            <div id="preferAmountEditor">
                                <div class="mb20 mt50 fs15">
                                    优惠金额
                                </div>
                                <div class="form-group group_preferamountType">
                                    <label class="col-sm-2 control-label">
                                        <span class="text-danger">*</span>优惠额度：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control required isNoInteger"
                                               name="groupPreferamount" id="group_preferamount"
                                               value="${groupVo.groupPreferamount}">
                                    </div>
                                </div>
                            </div>


                            <div class="modal-footer">
                                <button type="button" onclick="saveGroup()" class="btn btn-primary">保存</button>
                                <button type="button" class="btn btn-default"  onclick="window.location.href='findAllGroup.htm'">取消</button>
                            </div>
                    </form>
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
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加货品</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-inline" id="saveGroupReleProduct" action="saveGroupReleProduct.htm" method="post">
                    <input type="hidden" name="CSRFToken" id="CSRFToken" value="${token}">
                    <input type="hidden" name="groupId" id="groupId" value="${groupVo.groupId}">
                    <div class="mb20">
                        <div class="form-group">
                            <div class="input-group">
                            <input type="text" class="form-control"  placeholder="货品编号" id="s_product_no">
                        </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                            <input type="text" class="form-control"  placeholder="货品名称" id="s_product_name">
                        </div>
                        </div>
                         <div class="form-group">
                                <div class="input-group">
                                     <span class="input-group-addon">商品品牌</span>
                                     <select   class="cate_selector w150 " name="goodsBrandId" data-live-search="true" id="goodsBrandId">
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
                              <div class="form-group">
                                <div class="input-group">
                                     <span class="input-group-addon">一级分类</span>
                                     <select class="cate_selector w150"  data-live-search="true" name="oneCateId" onchange="chooseCateOne(this);">
				                        <option value="">选择分类</option>
				                        <c:forEach items="${oneCateList }" var="cate">
				                               <option value="${cate.catId}"  <c:if test="${oneCateId==cate.catId }">selected="selected" </c:if> >${cate.catName}</option>
				                        </c:forEach>
				                    </select>
                                </div>
                            </div>
                            
                             
                                 <div class="form-group">
                                <div class="input-group">
                                     <span class="input-group-addon">二级分类</span>
                                     <select class="cate_selector w150"  data-live-search="true"  id="twoCateId" name="twoCateId" onchange="chooseCateTwo(this);">
				                        <option value="">选择分类</option>
				                        <c:forEach items="${twoCateList }" var="cate">
				                               <option value="${cate.catId}"  <c:if test="${twoCateId==cate.catId }">selected="selected" </c:if> >${cate.catName}</option>
				                        </c:forEach>
				                    </select>
                                </div>
                            </div>
                             
                                 <div class="form-group">
                                <div class="input-group">
                                     <span class="input-group-addon">三级分类</span>
                                     <select class="cate_selector w150"  data-live-search="true" name="goodsCateId" id="threeCateId">
				                        <option value="">选择分类</option>
				                        <c:forEach items="${threeCateList }" var="cate">
				                               <option value="${cate.catId}"  <c:if test="${searchBean.goodsCateId==cate.catId }">selected="selected" </c:if> >${cate.catName}</option>
				                        </c:forEach>
				                    </select>
                                </div>
                            </div>
                            
                            <%--<div class="form-group"  style="padding-top:10px;">--%>
                                <%--<div class="input-group">--%>
                                     <%--<span class="input-group-addon">是否有货</span>--%>
                                     <%--<select class="cate_selector w150"  data-live-search="true" name="haveStock" id="haveStock">--%>
				                        <%--<option value="-1">请选择</option>--%>
				                       	<%--<option value="1"  <c:if test="${searchBean.haveStock=='1' }">selected="selected" </c:if>>有货</option>--%>
				                       	<%--<option value="0"  <c:if test="${searchBean.haveStock=='0' }">selected="selected" </c:if>>无货</option>--%>
				                    <%--</select>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <div class="form-group"  style="padding-top:10px;">
                            <button type="button" class="btn btn-info" onclick="selectGroupProduct(${groupVo.groupId},1)">搜索</button>
                        </div>
                    </div>
                    <table class="table table-striped table-hover" id="productAddList">
                        <thead>
                        <tr>
                            <th><input type="checkbox" onclick="selectAll('productId',this)"></th>
                            <th>货品图片</th>
                            <th>货品编号</th>
                            <th width="200px">货品名称</th>
                            <th>货品规格</th>
                            <th>货品分类</th>
                            <th>数量</th>
                            <th>所属品牌</th>
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
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="submitSaveGroupProductForm();">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<%--<script src="<%=basePath %>js/summernote.min.js"></script>--%>
<%--<script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>--%>
<script src="<%=basePath %>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/select2.min.js"></script>

<script src="<%=basePath %>js/lodash.min.js"></script>
<script src="<%=basePath %>js/lodash.fp.min.js"></script>



<script src="<%=basePath %>js/common/jquery.validate_extend.js"></script>
<script src="<%=basePath %>js/common.js"></script>
<script src="<%=basePath %>js/common/common_alert.js"></script>

<script src="<%=basePath %>js/system/system_common.js"></script>
<script src="<%=basePath %>js/goods/goods_group_product.js"></script>
<script type="text/javascript">

    //搜索框 validate
    $("#updateGroupForm").validate({
//        ignore: 'input[checkbox]',
        ignore: '.ignore',
                rules: {
                    groupPreferamount: {
                        le: $('#minSumPrice')
                    }
                },
                messages: {
                    groupPreferamount: {
                        le: "优惠金额不得高于商品最低总价"
                    }
                }
            }
    );


    function saveGroup() {
        var $form = $("#updateGroupForm");
        if ($form.find('[name=groupPrefertype]:checked').val() == "0") {
//            $('#groupPreferamount').val(0);
            if ($('#updateGroupForm').valid()) {
                $form.submit();
            }
        }
        else {
            $form.submit();
        }
    }


    $(function () {
        const fp = _.noConflict();

        var calPrice = fp.reduce(
                function (sum, a) {
                    return sum + parseInt(
                                    _.multiply($(a).text(), $(a).parents('td').next().text())
                            )
                }, 0);
        var minSumPrice = calPrice($('.minPrice'));
        var maxSumPrice = calPrice($('.maxPrice'));
//        console.log(minSumPrice);
//        console.log(maxSumPrice);

        $('#minSumPrice').text(minSumPrice);
        $('#minSumPrice').val(minSumPrice);
        $('#maxSumPrice').text(maxSumPrice);
        $('#maxSumPrice').val(maxSumPrice);

        if ($('[name=groupPrefertype]:checked').val() == 1) {
            $("#preferAmountEditor").hide();
            $("#group_preferamount").removeClass("required number");
        }

    });


    /*进行特惠价格的隐藏*/
    function changGroupType(type) {
        if (type == 1) {
            $("#preferAmountEditor").hide();
            $("#group_preferamount").removeClass("required number");
        } else {
            $("#group_preferamount").addClass("required number");
            $("#preferAmountEditor").show();
        }
    }


$(function(){
	 $('select[data-live-search="true"]').select2();
});


function chooseCateOne(obj){
	if($(obj).val()!=''){
	 $.ajax({
	        url:'querySonCateByCatIdAndName.htm?CSRFToken=${token}&catId='+$(obj).val(),
	        success: function (data) {
	        	
	            var html = '<option  value="">选择分类</option>';
	            for(var i=0;i<data.length;i++) {
	                var cate = data[i];
	                
	                html += '<option  value="'+cate.catId+'"';
	                if(i==0){
	                	html+=' selected="selected"';
	                }
	                html+='>'+cate.catName+'</option>';
	                
	            }
	            $("#twoCateId").html(html);
	            $('select[data-live-search="true"]').select2();
	            chooseCateTwo($("#twoCateId"));
	        }
	 });
	}else{
		 $("#twoCateId").html('<option value="">选择分类</option>');
		 $("#threeCateId").html('<option value="">选择分类</option>');
		 $('select[data-live-search="true"]').select2();
	}
}

function chooseCateTwo(obj){
	if($(obj).val()!=''){
	 $.ajax({
	        url:'querySonCateByCatIdAndName.htm?CSRFToken=${token}&catId='+$(obj).val(),
	        success: function (data) {
	            var html = '<option  value="">选择分类</option>';
	            for(var i=0;i<data.length;i++) {
	                var cate = data[i];
	                
	                html += '<option  value="'+cate.catId+'"';
	                if(i==0){
	                	html+=' selected="selected"';
	                }
	                html+='>'+cate.catName+'</option>';
	                
	            }
	            $("#threeCateId").html(html);
	            $('select[data-live-search="true"]').select2();
	        }
	 });
	}else{
		 $("#threeCateId").html('<option value="">选择分类</option>');
		 $('select[data-live-search="true"]').select2();
	}
}
</script>
</body>
</html>

