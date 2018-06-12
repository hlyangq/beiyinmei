<%--suppress BadExpressionStatementJS --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">

    <link rel="stylesheet" href="<%=basePath %>css/appsite/jstree_style.min.css" />
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/appsite/jstree.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/artDialog4.1.7/artDialog.source.js?skin=default"></script>
    <script type="text/javascript" src="<%=basePath%>js/artDialog4.1.7/plugins/iframeTools.js"></script>

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

    <script>
        var cats =${data} || [];

        var menus_1 = [];

        cats.forEach((cat)=>{
          if(cat.parentId === 0){
              var menu = {"id": ""+cat.cateBarId, "parent": '#', "text":cat.name};
              menus_1.push(menu);
            }
        });

        var menus_2 = [];
        menus_1.forEach((menu)=>{
            cats.forEach((cat)=>{
                if(cat.parentId+"" === menu.id){
                    menus_2.push({"id": ""+cat.cateBarId, "parent": menu.id, "text":cat.name})
                }
            });
        });

        var menus_3 = [];
        menus_2.forEach(menu=>{
            cats.forEach((cat)=>{
                if(cat.parentId+"" === menu.id && cat.parentId != 0){
                    menus_3.push({"id": ""+cat.cateBarId, "parent": menu.id, "text":cat.name})
                }
            });
        });

        var menus = [].concat(menus_1).concat(menus_2).concat(menus_3);
    </script>

</head>
<body>
<div class="row">
    <div id="using_json_2" class="demo">
    </div>
</div>
<script>
    $(function() {
        var selectCats=[];
        $('#using_json_2').on('changed.jstree', function (e, data) {
            selectCats=[];
            for (var i = 0; i < data.selected.length; i++) {
                var node = data.instance.get_node(data.selected[i]);
                selectCats.push({'id': node.id, 'text': node.text});
            }
        }).jstree({
            'core': {
                'multiple': false,
                'data': menus,
                'check_callback': function (operation, node, node_parent, node_position, more) {
                    console.log(operation, node, node_parent, node_position, more);
                }
            }
        });

        var parent = art.dialog.parent,				// 父页面window对象
                api = art.dialog.open.api;	// 			art.dialog.open扩展方法
        if (!api) return;

        // 操作对话框
        api.title('选择分类')
            // 自定义按钮
            .button(
            {
                name: '保存',
                callback: function () {
                    var win = art.dialog.open.origin;//来源页面

                    if(selectCats.length > 0){
                        win.window.saveChoooseCat(selectCats, api.config.id);
                        art.dialog.close();
                    }else{
                        art.dialog.alert('请选择分类！');
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
</script>
</body>
</html>
