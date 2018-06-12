<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商品预览详细</title>

    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">body {
        background: none;
    }</style>
</head>
<body>

<div class="common_info common_tabs mt20">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#tab1" class="pointTab" aria-controls="tab1" role="tab"
                                                  data-toggle="tab">商品介绍</a></li>
        <li role="presentation"><a href="#tab2" aria-controls="tab2" class="pointTab" role="tab"
                                   data-toggle="tab">规格参数</a></li>
        <li role="presentation"><a href="#tab3" aria-controls="tab3" class="pointTab" role="tab"
                                   data-toggle="tab">货品信息</a></li>
    </ul>
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="tab1">
            <div class="rec_example">
                <div class="recommend_edit" style="padding-left: 0px">
                    <div class="container-fluid">
                        <div class="row" style="border-bottom: 1px solid #A4AEB9">
                            <fmt:formatDate value="${goods.goodsUptime}" pattern="yyyy-MM-dd HH:mm:ss" var="goodsUptime"/>
                            <div class="col-sm-12">
                                <p>商品名称：${goods.goodsName}
                                </p>
                            </div>
                            <div class="col-sm-12">
                                <p>商品编号： ${goods.goodsNo}</p>
                            </div>
                            <div class="col-sm-12">
                                <p>上架时间： ${goodsUptime}</p>
                            </div>
                            <div class="col-sm-12">
                                <p>所属商家： ${goods.goodsBeloName}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <p>商品属性：</p>
                            </div>
                        </div>
                        <div class="row">
                            <c:forEach items="${expandParamVos}" var="expandParam" varStatus="i">
                                <div class="col-sm-12">
                                    <p>${expandParam.expandParamVo.expandparamName}：${expandParam.expangparamValue.expandparamValueName}</p>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="row" style="border-bottom: 1px solid #A4AEB9">
                            <div class="col-sm-12">
                                <p>品牌描述：${goodsBrand.brandDesc}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <p>PC版详情：</p>
                            </div>
                        </div>
                        <div class="row" >
                            <div class="col-sm-12">
                                ${goods.goodsDetailDesc}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="tab2">
            <div class="rec_example">
                <div class="recommend_edit" style="padding-left: 0px">
                    <div class="container-fluid">
                        <div class="row">
                            <c:forEach items="${releParamVos}" var="releParam" varStatus="i">
                                <c:if test="${releParam.paramValue!=''}">
                                    <div class="col-sm-12">
                                        <p>${releParam.param.paramName}：${releParam.paramValue}</p>
                                    </div>
                                </c:if>
                            </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="tab3">
            <c:forEach items="${goodsProductVos}" var="goodsProductVo" varStatus="i">
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#protab"+i class="pointTab" aria-controls="protab"+i role="tab"
                                                              data-toggle="tab">${goodsProductVo.goodsInfoItemNo}</a></li>
                </ul>
                <div role="tabpanel" class="tab-pane" id="protab"+i>
                    <div class="rec_example">
                        <div class="recommend_edit" style="padding-left: 0px">
                            <div class="container-fluid">
                                <div class="row">
                                    <fmt:formatDate value="${goods.goodsUptime}" pattern="yyyy-MM-dd HH:mm:ss" var="goodsUptime"/>
                                    <div class="col-sm-12">
                                        <p>货品编号：${goodsProductVo.goodsInfoItemNo}</p>
                                    </div>
                                    <div class="col-sm-12">
                                        <p>货品名称：${goodsProductVo.goodsInfoName}</p>
                                    </div>
                                    <c:if test="${goodsProductVo.goodsInfoSubtitle!=''}">
                                        <div class="col-sm-12">
                                            <p>货品副标题：${goodsProductVo.goodsInfoSubtitle}</p>
                                        </div>
                                    </c:if>
                                    <div class="col-sm-12">
                                        <p>货品库存：${goodsProductVo.goodsInfoStock}</p>
                                    </div>
                                    <div class="col-sm-12">
                                        <p>销售价：${goodsProductVo.goodsInfoPreferPrice}</p>
                                    </div>
                                    <c:if test="${goodsProductVo.goodsInfoCostPrice!=''}">
                                        <div class="col-sm-12">
                                            <p>成本价：${goodsProductVo.goodsInfoCostPrice}</p>
                                        </div>
                                    </c:if>
                                    <div class="col-sm-12">
                                        <p>市场价：${goodsProductVo.goodsInfoMarketPrice}</p>
                                    </div>
                                    <div class="col-sm-12">
                                        <p>重量：${goodsProductVo.goodsInfoWeight}</p>
                                    </div>
                                    <div class="col-sm-12">
                                        <c:if test="${goodsProductVo.showList==1}"><p>列表显示：是</p></c:if>
                                        <c:if test="${goodsProductVo.showList==0}"><p>列表显示：否</p></c:if>
                                    </div>
                                    <div class="col-sm-12">
                                        <c:if test="${goodsProductVo.showMobile==0}"><p>移动端显示：否</p></c:if>
                                        <c:if test="${goodsProductVo.showMobile==1}"><p>移动端显示：是</p></c:if>
                                    </div>
                                    <c:forEach items="${goodsProductVo.specVo}" var="specVo" varStatus="i">
                                        <div class="col-sm-12">
                                            <p>${specVo.spec.specName}：${specVo.specValueRemark}</p>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </c:forEach>

        </div>
</div>

</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath %>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.js"></script>
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script src="<%=basePath %>js/summernote.min.js"></script>
<script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath %>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/common/common_alert.js"></script>
<script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.zclip.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.zclip.js"></script>
<script type="text/javascript">

    $(function () {

    });

</script>
</body>
</html>



   