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
    <title>注册营销</title>

    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/select2.min.css" rel="stylesheet">
    <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">
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

           <h2 class="main_title">${pageNameChild}</h2>


<div class="common_data p20">
			  <div class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <strong>注意!</strong> 注册营销用来设置一个活动期间，新用户注册有送积分和优惠劵的活动，在不了解情况下请联系我们的工作人员进行修改。
                    </div>
            <div class="common_form p20">
       			<c:if test="${flag !=1}">
            
              <form class="form-horizontal">
                <div class="form-group">
                  <label class="control-label col-sm-6">活动开始时间：</label>
                  <div class="col-sm-1"></div>
                  <div class="col-sm-14 form_item">
                    <span class="form_value">${parameter.startTime}</span>
                  </div>
                  <div class="col-sm-3"></div>
                </div>
                <div class="form-group">
                  <label class="control-label col-sm-6">活动结束时间：</label>
                  <div class="col-sm-1"></div>
                  <div class="col-sm-14 form_item">
                    <span class="form_value">${parameter.endTime} </span>
                  </div>
                  <div class="col-sm-3"></div>
                </div>
              <%--  <div class="form-group">
                  <label class="control-label col-sm-6">注册人数：</label>
                  <div class="col-sm-1"></div>
                  <div class="col-sm-14 form_item">
                    <span class="form_value">${parameter.registerNum}</span>
                  </div>
                  <div class="col-sm-3"></div>
                </div>--%>
                <div class="form-group">
                  <label class="control-label col-sm-6">启用状态：</label>
                  <div class="col-sm-1"></div>
                  <div class="col-sm-14 form_item">
                    <span class="form_value">
	  					 <c:if test="${parameter.isUsed == 0}"> <span class="label label-default">未启用</span></c:if>
	                    <c:if test="${parameter.isUsed == 1}"> <span class="label label-success">启用</span></c:if>
					</span>
                  </div>
                  <div class="col-sm-3"></div>
                </div>

               <div class="form-group">
                  <label class="control-label col-sm-6">送优惠券：</label>
                  <div class="col-sm-1"></div>
                  <div class="col-sm-14 form_item">
                      <span class="form_value">${parameter.couponName}</span>
                  </div>
                  <div class="col-sm-3"></div>
               </div>




                <div class="form-group">
                  <label class="control-label col-sm-6">送积分：</label>
                  <div class="col-sm-1"></div>
                  <div class="col-sm-14 form_item">
                    <span class="form_value">${parameter.registerIntegral}</span>
                  </div>
                  <div class="col-sm-3"></div>
                </div>
              

    			<div class="form-group">
                  <label class="control-label col-sm-6"></label>
                  <div class="col-sm-1"></div>
                  <div class="col-sm-14 form_item">
                    <span class="form_value">  <button type="button" class="btn btn-primary form_sure" onclick="toupdate();">修改</button></span>
                  </div>
                  <div class="col-sm-3"></div>
                </div>
              
              </form>
           
      		  </c:if>
      		  
      		  <c:if test="${flag == 1 }">
      		   <form class="form-horizontal" action="updateRegisterCoupon.htm?CSRFToken=${token}" method="post" id="addForm">
            
              <div class="form-group">
                <label class="control-label col-sm-5"><span class="text-danger">*</span>活动开始时间：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-8">
                    <div class="input-group date form_datetime" id="startpicker">
                      <input class="form-control required" type="text" value="${parameter.startTime}" name="startTime"
                             id="startTime" readonly>
                      <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-sm-5"><span class="text-danger">*</span>活动结束时间：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-8">
                    <div class="input-group date form_datetime" id="endpicker">
                       <input class="form-control dateError" type="text" value="${parameter.endTime}" name="endTime"
                              id="endTime" readonly>
                      <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                </div>
                <div class="col-sm-3"></div>
              </div>
              <%--<div class="form-group">
                <label class="control-label col-sm-5"><span class="text-danger">*</span>注册人数：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-8">
                  <input type="text" class="form-control required digits"  name="registerNum" id="registerNum" value="${parameter.registerNum}">
                </div>
                <div class="col-sm-3"></div>
              </div>--%>
              <div class="form-group">
                <label class="control-label col-sm-5">启用状态：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-4">
                  <label class="radio-inline">
                    <input type="radio" name="isUsed"  value="1" <c:if test="${parameter.isUsed == 1}">checked="checked"</c:if>>  <span class="label label-success">启用</span>
                  </label>
                  <label class="radio-inline">
                    <input type="radio"  name="isUsed"  value="0" <c:if test="${parameter.isUsed == 0}">checked="checked"</c:if> ><span class="label label-default">不启用</span>
                  </label>
                  
                </div>
                <div class="col-sm-3"></div>
              </div>

              <div class="form-group">
                   <label class="control-label col-sm-5">送优惠券：</label>
                   <div class="col-sm-1"></div>
                   <div class="col-sm-10">
                       <select class="form-control" name="registerCouponId" id="registerCoupon" >
                           <option value="0">选择优惠券</option>
                           <c:forEach items="${coupon }" var="coupon">
                               <option value="${coupon.couponId }" <c:if test="${parameter.registerCouponId==coupon.couponId }"> selected="selected"</c:if>>${coupon.couponName }</option>
                           </c:forEach>
                       </select>
                   </div>
                   <div class="col-sm-3"></div>
               </div>

             
            

              <div class="form-group">
                <label class="control-label col-sm-5">送积分：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-4">
                  <input type="text" class="form-control"
                          onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
                         name="registerIntegral" id="registerIntegral" value="${parameter.registerIntegral}">
                </div>
              </div>      
           
           
            </form>
            
             <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="update();">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:history.go(-1);">取消</button>
          </div>
      		  </c:if>
      		    
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
     <script src="<%=basePath %>js/report/report.js"></script>
    <script src="<%=basePath %>/js/select2.min.js"></script>
    <script type="text/javascript">


        $(function(){

		$("#addForm").validate();
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

            /*下面是时间选择器开始时间不能大于结束时间设置  START*/
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

        /* 为选定的select下拉菜单开启搜索提示 */
        $('select[data-live-search="true"]').select2();
    });




    function toupdate(){
        window.location.href="registerMarketing.htm?flag=1";
    }
    
    function update(){
        if($("#addForm").valid()){

            $("#addForm").submit();
        }
    }
    

    </script>
  </body>
</html>
