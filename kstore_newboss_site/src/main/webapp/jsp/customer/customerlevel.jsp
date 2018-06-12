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
    <title>会员等级列表</title>

    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
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

            <h2 class="main_title">${pageNameChild} <small>(共${pageBean.rows }条)</small></h2>
			<form action="initPointLevel.htm" method="post" id="searchForm">
			  	 <input type="hidden" value="searchForm" id="formId">
                  <input type="hidden" value="initPointLevel.htm" id="formName">
			</form>
            <div class="common_data p20">

            <div class="data_ctrl_area mb20">
              <div class="data_ctrl_search pull-right"></div>
              <div class="data_ctrl_brns pull-left">
                <button type="button" class="btn btn-info" onclick="addlevel();">
                  <i class="glyphicon glyphicon-plus"></i> 添加
                </button>
                <button type="button" class="btn btn-info" onclick="delalllevel();">
                  <i class="glyphicon glyphicon-trash"></i> 全部删除
                </button>
              </div>
              <div class="clr"></div>
            </div>
		<form action="deleteAllNewPointLevel.htm?CSRFToken=${token}" method="post" id="delForm">
            <input type="hidden" name="CSRFToken" value="${token}">
            <table class="table table-striped table-hover">
            <thead>
            <tr>
              <th>等级名称</th>
              <th>所需积分</th>
              <th>折扣率</th>
              <th width="150">操作</th>
            </tr>
            </thead>
            <tbody>
            
            <c:forEach items="${pageBean.list}" var="pointlevel" varStatus="i">
	            <tr>
	              <td>${pointlevel.pointLevelName }</td>
	              <td>${pointlevel.pointNeed }</td>
	              <td>${pointlevel.pointDiscount }</td>
	              <td>
	                <div class="btn-group">
	                  <button type="button" class="btn btn-default" onclick="editlevel(${pointlevel.pointLelvelId });">编辑</button>
	                </div>
	              </td>
	            </tr>
          </c:forEach>
         
         
            </tbody>
            </table>
		</form>
            <div class="table_foot">
             	<c:import url="../page/searchPag.jsp">
				     <c:param name="pageBean" value="${pageBean }"/>
				     <c:param name="path" value="../"></c:param>
				</c:import>
            </div>

            </div>

          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="addMemberLevel"  role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">添加会员等级</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal" action="" method="post" id="addForm">
              <input type="hidden" name="CSRFToken" value="${token}">
              <div class="form-group">
                <label class="control-label col-sm-5"><span class="text-danger">*</span>等级名称：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-8">
                <input type="hidden" name="pointLelvelId" value="" id="pointLelvelId"/>
                  <input type="text" class="form-control required" name="pointLevelName" id="pointLevelName">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-sm-5"><span class="text-danger">*</span>折扣率：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-5">
                  <input type="text" class="form-control w100 required" id="spinner" name="pointDiscount" max="1" min="0">
                </div>
                <div class="col-sm-3">
                  <a href="javascript:;" class="zhekoulv help_tips">
                    <i class="icon iconfont">&#xe611;</i>
                  </a>
                </div>
              </div>
              <div class="form-group" id="mypoint">
                <label class="control-label col-sm-5"><span class="text-danger">*</span>所需积分：</label>
                <div class="col-sm-1"></div>
                <div class="col-sm-3">
                  <input type="text" readonly="true"  class="form-control digits required"  name="pointNeed" id="pointNeed" value="" >
                </div>
                <div class="col-sm-1 text-center">~</div>
                <div class="col-sm-3">
                  <input type="text"   class="form-control digits required"  name="pointNeed1" id="pointNeed1" value="">
                </div>
                <div class="col-sm-3">
                  <a href="javascript:;" class="suoxujifen help_tips">
                    <i class="icon iconfont">&#xe611;</i>
                  </a>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="subForm();">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
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
    <script src="<%=basePath %>js/common.js"></script>
    <script src="<%=basePath %>js/common/common_alert.js"></script>
    <script src="<%=basePath %>js/common/common_checked.js"></script>
    <script src="<%=basePath %>/js/select2.min.js"></script>
    <script>
      $(function(){
          $("#addForm").validate();
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
        /* 为选定的select下拉菜单开启搜索提示 END */

        /* 富文本编辑框 */
        $('.summernote').summernote({
          height: 300,
          tabsize: 2,
          lang: 'zh-CN'
        });

        /* 选择规格值 */
        $('.spec_set input').change(function(){
          if($(this).is(':checked')){
            $(this).parent().parent().next().slideDown('fast');
          }
          else {
            $(this).parent().parent().next().slideUp('fast');
          }
        });

        /* 下面是表单里面的填写项提示相关的 */
        $('.zhekoulv').popover({
          content : '有效值0~1,如果输入0.85,表示该会员等级以销售价85折购买商品',
          trigger : 'hover'
        });
        $('.morendengji').popover({
          content : '如果选择"是",顾客注册会员时,初始等级为当前等级',
          trigger : 'hover'
        });
        $('.suoxujifen').popover({
          content : '按积分升级时,会员积分达到此标准后会自动升级为当前等级，注意等级计算时不包含后面区间的积分',
          trigger : 'hover'
        });
      });
      
      function addlevel(){

          $.ajax({
              type: 'post',
              url: '<%=basePath %>getmaxpoint.htm',
              success: function (data) {
                 $("#pointNeed").val(data);
              }
          })

          num=0;
          $('#addForm').attr('action', 'addPointLevel.htm?CSRFToken=${token}');
          $("#pointLevelName").val('');
	 	 $("#pointLelvelId").val('');
	 	 $("#spinner").val('');
	 	 $('#isDefaultNo').prop('checked', true);
	 	 $("#pointNeed").val('');
	 	 $("#pointNeed1").val('');
	 	  $("#addMemberLevel").find(".modal-title").text("添加会员等级");
          $("#mypoint").css('display','block');
         $('#addMemberLevel').modal('show');
      }
      var num=0;
      function subForm() {

        if ($("#addForm").valid()) {
          $.ajax({
            type: 'post',
            url: 'validatePointLevel.htm',
            data: $("#addForm").serialize(),
            success: function (data) {
              var html = '';
              if (data == 0) {
                $("#addForm").submit();
              } else if (data == 1) {
                html = '<label for="pointNeed" generated="true" class="error">开始积分必须小于结束积分</label>'
                $("#pointNeed").addClass("error");
                $("#pointNeed").parent().append(html);
              } else if (data == 2) {
                html = '<label for="pointLevelName" generated="true" class="error">会员名称已存在</label>'
                $("#pointLevelName").addClass("error");
                $("#pointLevelName").parent().append(html);
              } else if (data == 3) {
                html = '<label for="pointNeed" generated="true" class="error">积分范围已存在，不可设置</label>'
                $("#pointNeed").addClass("error");
                $("#pointNeed").parent().append(html);
              }else if (data == 33) {
                html = '<label for="pointNeed" generated="true" class="error">积分范围已存在，不可设置</label>'
                $("#pointNeed1").addClass("error");
                $("#pointNeed1").parent().append(html);
              } else if (data == 4) {
                html = '<label for="spinner" generated="true" class="error">会员等级高的折扣率必须小于会员等级低的折扣率</label>'
                $("#spinner").addClass("error");
                $("#spinner").parent().append(html);
              }
            }
          })

        }
      }
      function editlevel(pointLevelId){
          $('#addForm').attr('action', 'updatePointLevel.htm?CSRFToken=${token}');
      	   doSearchPointLevel(pointLevelId);
      	  $("#addMemberLevel").find(".modal-title").text("编辑会员等级");
          $("#mypoint").css('display','none');
          $('#addMemberLevel').modal('show');
      }
      
      
  	/*ajax 通过ID查询并塞值到会员等级修改页面*/
  	function doSearchPointLevel(pointLevelId){
  		$.post("queryPointLevelById.htm?CSRFToken=${token}"+"&pointLevelId="+pointLevelId,function(data){
  			 	 $("#pointLevelName").val(data.pointLevelName);
  			 	 $("#pointLelvelId").val(data.pointLelvelId);
  			 	 $("#spinner").val(data.pointDiscount);
  			 	 if(data.isDefault==0){
  			 		$('#isDefaultNo').prop('checked', true);
  			 	 }else{
  			 		$('#isDefaultYes').prop('checked', true);
  			 	 }
  			 	 $("#is_d_hide").val(data.isDefault);
  			 	 $("#pointNeed").val(data.pointNeed.split("~")[0]);
  			 	 $("#pointNeed1").val(data.pointNeed.split("~")[1]);
  		});
  	}
  	
	
    
    //删除
    function dellevel(pointLevelId){
      $.ajax({
        type:'post',
        url:'deletePointLevelValidate.htm',
        data:'CSRFToken=${token}&pointLevelId='+pointLevelId,
        success:function(data){
          if(data==0){
            showDeleteOneConfirmAlert('deleteNewPointLevel.htm?CSRFToken=${token}&pointLevelId='+pointLevelId,'确定要删除此等级吗？');
          }else{
            showNoDeleteConfirmAlert("会员等级已被占用，不允许删除！");
          }
        }
      })

    }
    
    //删除
    function delalllevel(){
        $("#modalDialog").remove();
        var confirmDialog =
                '<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog">'+
                '    <div class="modal-dialog">'+
                '        <div class="modal-content">'+
                '            <div class="modal-header">'+
                '                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
                '               <h4 class="modal-title">删除提示</h4>'+
                '           </div>'+
                '           <div class="modal-body">';
            confirmDialog +='确认要删除全部记录吗？';
        confirmDialog += '           </div>'+
        '           <div class="modal-footer">'+
        '             <button type="button" class="btn btn-primary" onclick="doDeleteBatchPoint()">确定</button>'+
        '               <button type="button" class="btn btn-default" data-dismiss="modal" onclick="$(\'#modalDialog\').modal(\'hide\');">取消</button>'+
        '           </div>'+
        '       </div>'+
        '   </div>'+
        '</div>';
        $(document.body).append(confirmDialog);
        $('#modalDialog').modal('show');


    }

        function doDeleteBatchPoint()
        {
            $.ajax({
                type:'post',
                url:'deleteallcustomerpointlevel.htm',
                success:function(data){
                    location.reload();
                }
            })
        }

    
    </script>
  </body>
</html>
