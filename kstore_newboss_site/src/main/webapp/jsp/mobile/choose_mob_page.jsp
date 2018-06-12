<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
      <link href="<%=basePath %>css/style_new.css" rel="stylesheet">
	<style type="text/css">
        .current_template,.template_list{margin-bottom:20px;}
        .current_template h4,.template_list h4{border-left:2px solid #0099FF;padding-left:10px;font-size:14px;}
        .current_template .body{position:relative;border:1px solid #ddd;padding:10px;}
        .current_template .body .pre_info{margin-left:20px;}
        .current_template .body .pre_info h5{margin-top:0;font-size:16px;}
        .current_template .body .pre_btns{position:absolute;width:100px;bottom:10px;left:210px;}

        .template_item_mobile{position:relative;float:left;margin:0 10px 10px 0;padding:10px;border:1px solid #ddd;}
        .template_item_mobile .ctrl{position:absolute;left:0;bottom:0;width:100%;height:50px;background:rgba(0,0,0,0.5);padding:10px 0;}
        .template_item_mobile .ctrl p{color:#fff;text-indent:10px;}
        .template_item_mobile .ctrl .ctrl_btns{display:none;}
        .add-template a {display:block; width:180px; height:240px; background:url(<%=basePath %>images/mb_add.png) no-repeat center center;}
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

              <h2 class="main_title">选择模板</h2>

              <div class="common_data p20">
                 <ul class="template-list clearfix">
                     <li>
                         <div class="template-box">
                             <div class="phone-box">
                                 <strong>空白模板</strong>
                             </div>
                             <div class="template-mask">
                                 <button type="button" class="btn btn-primary" onclick="window.location.href='addMobPage.htm?CSRFToken=${token}'">使用该模板</button>
                             </div>
                         </div>
                         <span class="template-name">空白模板</span>
                     </li>
                    <c:forEach items="${pb.list}" var="homePage">
                        <li>
                            <div class="template-box">
                                <div class="phone-box">
                                    <img src="${homePage.homeImg}" alt="">
                                </div>
                                <div class="template-mask">
                                    <button type="button" class="btn btn-primary" onclick="window.location.href='addMobPage.htm?tempId=${homePage.homepageId}&CSRFToken=${token }'">使用该模板</button>
                                </div>
                            </div>
                            <span class="template-name">${homePage.title}</span>
                        </li>
                    </c:forEach>
                 </ul>
              </div>
            </div>
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
    <script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
    <script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="<%=basePath %>js/common.js"></script>
   <script src="<%=basePath %>js/common/common_alert.js"></script>
    <script type="text/javascript">
  	$(function(){
  		/*$('.template_item_mobile').mouseenter(function() {
			$(this).find('.ctrl p').hide();
			$(this).find('.ctrl .ctrl_btns').show();
		});
		$('.template_item_mobile').mouseleave(function() {
			$(this).find('.ctrl p').show();
			$(this).find('.ctrl .ctrl_btns').hide();
		});*/
        <c:if test="${not empty msg}">
         showTipAlert("${msg}");
        </c:if>
  	});
    </script>
  </body>
</html>
