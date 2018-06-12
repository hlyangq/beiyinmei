<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">

    <style type="text/css">
    body{background:none}
    div.meneame a{
    border: #ddd 1px solid;
        padding: 10px 10px 10px 15px; 
        background-position: 50% bottom;
        color: #555;
        margin-right: 3px;
        text-decoration: none;
    }
    </style>
    <script src="<%=basePath %>js/jquery.min.js"></script>
</head>
<body>
        <!-- <h2 class="main_title">商品品牌<small>(共${pb.rows}条)</small></h2> -->

    <div class="common_data p20">
        <div class="filter_area">
            <input id="maxChoose" name="maxChoose" type="hidden" value="1" />
            <form role="form" class="form-inline" id="searchForm" action="queryAllBrandForChoose.htm" method="post">
                <input type="hidden" value="searchForm" id="formId">
                <input type="hidden" name="CSRFToken" id="CSRFToken" value="${token}">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">品牌名称</span>
                        <input type="text" class="form-control" name="brandName" value="${selectBean.brandName}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">品牌别名</span>
                        <input type="text" class="form-control" name="brandNickname" value="${selectBean.brandNickname}">
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">搜索</button>
                </div>
            </form>
        </div>
        
        <div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th width="10"><input type="checkbox" onclick="allunchecked(this,'brandIds')"></th>
                    <th>品牌LOGO</th>
                    <th>品牌名称</th>
                    <th>品牌别名</th>
                    <th>品牌排序</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pb.list}" var="brand">
                <tr>
                    <td><input type="checkbox" name="brandIds" value="${brand.brandId }"/></td>
                    <td id="brandLogo_${brand.brandId}" ><img alt="" src="${brand.brandLogo}" height="36px"></td>
                    <td id="brandName_${brand.brandId}">${brand.brandName}</td>
                    <td id="brandNickname_${brand.brandId}">${brand.brandNickname}</td>
                    <td id="brandSort_${brand.brandId}">${brand.brandSort}</td>
                </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="table_foot">
                <c:import url="../page/searchPag.jsp">
                    <c:param name="pageBean" value="${pb}"/>
                    <c:param name="path" value="../"></c:param>
                </c:import>
            </div>
        </div>
    </div>



<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script src="<%=basePath %>js/jquery.ztree.core-3.5.min.js"></script>
<script src="<%=basePath %>js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="<%=basePath %>js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/artDialog4.1.7/artDialog.source.js?skin=default"></script>
<script type="text/javascript" src="<%=basePath%>js/artDialog4.1.7/plugins/iframeTools.js"></script>

<script style="text/javascript">
$(function(){
    var parent = art.dialog.parent;             // 父页面window对象
    var api = art.dialog.open.api;    //          art.dialog.open扩展方法
    if (!api) return;

    api.title('选择商品')
        // 自定义按钮
        .button(
            {
                name: '保存',
                callback: function () {
                    var win = art.dialog.open.origin;//来源页面
                    //获取可选择的长度
                    var size = $("#maxChoose").val()>0?$("#maxChoose").val():1;
                    var flag = checkSelected('brandIds',size);
                    
                    if(flag==1){
                        //获取productId集合
                        var brandIds = $("input[name='brandIds']:checked");
                        //获取图片集合
                        var brands = [];
                        for(var i=0;i<brandIds.length;i++){
                            var id = $(brandIds[i]).val();
                            brands.push({
                                'id': id,
                                'name': $("#brandName_" + id).text(),
                                'nickName': $("#brandNickname_" + id).text()
                            });
                        }
                        win.window.saveChoooseBrand(brands, api.config.id);
                        art.dialog.close();
                    }else if(flag==0){
                        art.dialog.alert('选择的品牌大于'+size+'个！');
                        return false;
                    }else{
                        art.dialog.alert('请选择品牌！');
                        return false;
                    }
                },
                focus: true
            },
            {
                name: '取消',
                callback: function () {
                    art.dialog.close();
                }
            }
        );
});

/**
 * 检查是否选中一行
 * @param _objId      checkbox节点name属性名
 * @param _modifyFlag 标识符值
 * NINGPAI_xiaomm
 * 2014-03-04 14:22
 **/
function checkSelected(_objId, size){
    checkedList = new Array();
    $("input[name='"+_objId+"']:checked").each(function() {
        checkedList.push($(this).val());
    });
    if(checkedList.length > 0){
        if(checkedList.length <= size){
            return 1;
        }else{
            return 0;
        }
    }else{
        return -1;
    }
}; 

/**
 * 全选反选
 * @param obj
 * @param name
 */
function allunchecked(obj,name){
      if(obj.checked){
              $("input[name='"+name+"']").each(function(){
                 this.checked=true;  
              });  
      }else{
          $("input[name='"+name+"']").each(function(){
                this.checked=false;  
           });  
      }
  }

</script>
</body>
</html>

