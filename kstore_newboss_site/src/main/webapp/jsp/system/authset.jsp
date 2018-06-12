<%--
  Created by IntelliJ IDEA.
  User: NP-Heh
  Date: 2015/3/18
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
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
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css">
    <link href="<%=basePath%>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath%>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath%>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
      .modal-article {padding: 0 10px; height: 400px; overflow-y: scroll;}
      .modal-article p {line-height: 180%; font-size: 16px; position: relative; padding-left: 25px;}
      .modal-article p em {font-style: normal; position: absolute; top: 0; left: 0;}
      .modal-article img {display: block; width: 100%; margin: 10px auto 20px;}
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

                <h2 class="main_title">${pageNameChild} <small>(共${pb.rows}条)</small></h2>
                <div class="box_data container-fluid p20">
                    <div class="row">
                    <c:forEach var="auth" items="${pb.list }" varStatus="status">
                        <fmt:formatDate value="${auth.authUpdated }" var="cdate" type="both"/>
                        <div class="col-sm-6">
                            <div class="box_item">
                                <h2>${auth.authName }</h2>
                                <img class="box_icon" alt="" src="${auth.authPic }">
                                <c:if test="${auth.authShow=='1' }">
                                    <span class="label label-success">已启用</span>
                                </c:if>
                                <c:if test="${auth.authShow=='0' }">
                                    <span class="label label-default">已停用</span>
                                </c:if>
                               <c:if test="${status.count<4 }">
                                <a href="javascript:void(0);" onclick="$('#multiArticle${status.count}').modal('show')">查看帮助</a>
                                </c:if>
                                <p style="padding:0px;"></p>
                              <%--   <p style="width:195px;word-break:break-word;" >AppKey：${auth.authClientSecret }</p>
                                <p style="width:195px;word-break:break-word;" title="${auth.authRedirectUri }">回调地址：${auth.authRedirectUri }</p> --%>
                                <div class="box_edit">
                                    <a href="javascript:;" onclick="showAuthUpdate(${auth.authId},${status.count})">编辑</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                        <%--<div class="col-sm-6">
                            <div class="box_add">
                                <a href="javascript:;"  onclick="$('#editPayment').modal('show')">
                                    <i class="glyphicon glyphicon-plus"></i> 添加支付方式
                                </a>
                            </div>
                        </div>--%>
                    </div>
                </div>


            </div>

        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="editLoginAPI"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑登录接口</h4>
            </div>
            <form role="form" class="form-horizontal" method="post" action="updateauth.htm?CSRFToken=${token}"  enctype="multipart/form-data" id="editAuthsetForm">
                <input type="hidden" id="CSRFToken" value="${token}"/>
            <div class="modal-body">
                    <input type="hidden" name="CSRFToken" value="${token}">
                    <input type="hidden" name="authId" id="up_authid">
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color:red;">*</span>第三方名称：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="authName" id="up_name" >
                        </div>
                    </div>
                    <div class="form-group" id="special">
                        <label class="control-label col-sm-5">接口申请码：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control specstr" name="authClientCode" id="up_code" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color:red;">*</span>AppID：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required specstr" name="authClientId" id="up_clientid">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color:red;">*</span>AppSecret：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control" name="authClientSecret" id="up_secret">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5"><span style="color:red;">*</span>回调地址：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <input type="text" class="form-control required url" name="authRedirectUri" id="up_uri">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">选择图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <p class="pt5">
                                <input type="file" name="partPic"  id="partPic" onchange="previewImage(this)"/>
                            </p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5">预览图标：</label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-12">
                            <div id="preview"></div>
                        </div>
                    </div>
                   
                    <div class="form-group">
                        <label class="control-label col-sm-5">是否启用： <input type="hidden" name="authSort"  id="up_authsort"></label>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-16">
                            <label class="radio-inline">
                                <input type="radio" name="authShow" id="open1" value="1" checked> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="authShow" id="open2" value="0"> 否
                            </label>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
            </form>
        </div>
    </div>
</div>






  <div class="modal fade" id="multiArticle1"  role="dialog">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新浪微博第三方登录配置流程</h4>
          </div>
          <div class="modal-body">
            <div class="modal-article">
              <p><em>1.</em>先注册微博开放平台账号（<a href="http://open.weibo.com">http://open.weibo.com</a>），注册成功后，登录后点击“网站接入WEB”</p>
              <img src="./images/syshelp/img_x01.png" alt="">
			  <img src="./images/syshelp/img_x02.png" alt="">
              <p><em>2.</em>根据网站要求，提交相应的审核，添加信息参考下面的内容</p>
              <img src="./images/syshelp/img_x03.png" alt="">
              <p><em>3.</em>提交审核并通过后，记住AppKey 和AppSecret，到产品后台的接口管理-登录接口管理，去对应修改新浪微博接口配置信息，修改如下图：</p>
              <img src="./images/syshelp/wbjk.jpg" alt="">
			  <p>修改完后点击保存，就可以进行新浪微博登录了</p>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>
    
    
     <div class="modal fade" id="multiArticle2"  role="dialog">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">腾讯QQ第三方登录配置</h4>
          </div>
          <div class="modal-body">
            <div class="modal-article">
              <p><em>1.</em>首先先注册腾讯QQ互联（<a href="http://connect.qq.com">http://connect.qq.com</a>），注册成功后，登录后点击“网站接入”</p>
              <img src="./images/syshelp/img_q01.png" alt="">
			  <img src="./images/syshelp/img_q02.png" alt="">
              <img src="./images/syshelp/img_q03.png" alt="">
              <img src="./images/syshelp/img_q04.png" alt="">
			  <p>回调地址的格式为：http://域名/afterloginqq.html,</p>
              <p><em>2.</em>申请应用审核通过后，用户可以获得AppKey和AppSecret，如下图</p>
              <img src="./images/syshelp/img_q05.png" alt="">
              <p><em>3.</em>最后到后台编辑修改腾讯QQ登录接口，修改如下</p>
              <img src="./images/syshelp/qqjk.jpg" alt="">
			  <p>修改成功后，就可以进行QQ网站登录了</p>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>
    
    
 <div class="modal fade" id="multiArticle3"  role="dialog">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">微信授权配置</h4>
          </div>
          <div class="modal-body">
            <div class="modal-article">
              <p><em>1.</em>首先到公众号后台进行菜单配置，添加自定义菜单名称，选择“跳转网页”，输入页面地址后面添加/getwxcode1.htm</p>
              <img src="./images/syshelp/wxdl3.png" alt="">
              <p><em>2.</em>微信菜单配置好后，需要进行微商城接口设置，主要是调取用户信息进入微商城。 进入微信后台--接口权限，点击修改“网页授权获取用户基本信息”，如图</p>
              <img src="./images/syshelp/wxdl1.png" alt="">
              <p><em>3.</em>添加授权回调页面域名</p>
              <img src="./images/syshelp/wxdl2.png" alt="">
              <p><em>4.</em>接下来到后台编辑微信登录接口，标注红色箭头的地方改成自己的</p>
              <img src="./images/syshelp/wxdljk1.png" alt="">
              <p><em>5.</em>AppID和AppSecret对应微信公众号后台位置如下图所示</p>
              <img src="./images/syshelp/img_17.png" alt="">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>
    
    
    
     <div class="modal fade" id="multiArticle4"  role="dialog">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">淘宝登录配置</h4>
          </div>
          <div class="modal-body">
            <div class="modal-article">
              <p><em>1.</em>首先注册登录淘宝开发平台（<a href="http://open.taobao.com">http://open.taobao.com</a>），进入控制台提交相应信息</p>
              <img src="./images/syshelp/img_t01.png" alt="">
              <p><em>2.</em>提交信息成功后，点击下一步，开始创建应用</p>
              <img src="./images/syshelp/img_t02.png" alt="">
              <p><em>3.</em>创建应用名称</p>
              <img src="./images/syshelp/img_t03.png" alt="">
			  <p><em>4.</em>创建成功后，您会在添加的邮箱中获得淘宝开放平台提供的AppKey和AppSecret，到后台的“淘宝登录接口”对应修改这两个值</p>
              <img src="./images/syshelp/img_t04.png" alt="">
			  <p><em>5.</em>最后到网站对该应用进行提交上线</p>
              <img src="./images/syshelp/img_t05.png" alt="">
			  <p>按照要求提交相应信息，该应用就能正式上线，前台就能通过淘宝登录了</p>
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
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/summernote.min.js"></script>
<script src="<%=basePath%>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script src="<%=basePath%>js/common/common_alert.js"></script>
<script src="<%=basePath%>js/common/preview_image.js"></script>
<script src="<%=basePath%>js/system/authset.js"></script>
<script src="<%=basePath%>js/system/system_common.js"></script>
<script>

</script>
</body>
</html>

