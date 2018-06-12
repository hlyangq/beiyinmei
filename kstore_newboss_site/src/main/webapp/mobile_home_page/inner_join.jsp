<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
	<style>
	#ctModal .modal-dialog{background:#fff;border-radius:3px;}
	#ctModal .nav-tabs{border:0;display:block;height:25px;line-height:25px;}
	
	#ctModal .nav-tabs>li>a{padding:3px 10px;font-size:14px;border:0;border-left:1px solid #ccc;}
	#ctModal .nav-tabs li:first-child a{border-left:0;}
	#ctModal .nav-tabs>li.active>a{font-weight:bold;}
	#ctModal .nav-tabs>li>a:hover{}
	#ctModal .close{position:absolute;right:10px;top:20px;}
	</style>
	<div id="ctModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<input type="hidden" id="linkId">
		
		<div class="tab-content">
		<div class="modal-header pr">
			<ul class="nav-tabs">
				<li class="active"><a href="#cate" data-toggle="tab" onclick="queryMobCateBar()">分类</a></li>
				<li><a href="#goods" data-toggle="tab" onclick="queryMobProduct()">商品</a></li>
			</ul><!--/nav-->
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		</div><!--/modal-header-->
			<div class="tab-pane active" id="cate">
				<div class="modal-body">
					<table class="ct-tbs w table table-striped table-hover">
						<thead>
							<tr>
								<th width="30%">分类名称</th>
								<th width="30%"></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table><!--/ct-tbs-->
				</div><!--/modal-body-->
			
			</div><!--/sort-->

			<div class="tab-pane" id="goods">
				<div class="modal-body" style="max-height:300px;overflow-y: scroll;">
					<table class="ct-tbs w table table-striped table-hover">
						<thead>
							<tr>
								
								<th width="45%">商品名称</th>
								<th>商品图片</th>
								<th>规格</th>
								<th style="display: none">链接地址</th>
								<th style="width:200px;">
									<!-- <form class="form-search fr">
									</form> -->
								    <input type="text" id="chooseGoodsSearch" placeholder="请输入商品名" class="input-medium search-query" style="color:black;">
								    <input type="submit" class="btn btn-default btn-sm" onclick="queryMobProduct()" value="搜索">
								</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table><!--/ct-tbs-->
				</div><!--/modal-body-->
				<div class="modal-footer">
					<ul class="pagination pagination-sm pagination-right">
					</ul><!--/pagination-->
				</div><!--/modal-footer-->
			</div><!--/goods-->
		</div><!--/tab-content-->	
	</div>
	</div><!--/modal-->

	<script>
		//Ajax获取移动版分类
		function queryMobCateBar(){
			$.ajax({
				url : "ajaxQueryMobCateBarForSite.htm",
				async : false,
				success : function(data){
					//清空
					var $tbody = $("#cate").find("tbody");
					$tbody.html("");
					//重新赋值
					for(var i=0;i<data.length;i++){
						var html = '<tr class="level-1">'
						+'<td>';
						if(data[i].childs.length>0){
							html = html +'<a class="level-op level-show" href="javascript:;"></a>'
										+'<a class="level-op level-hide" href="javascript:;"></a>';
						}
						html = html +data[i].name+'</td>';
						var t_cateId = $.trim(data[i].cateBarId);
						html = html +'<td style="text-align: center;"><span class="link" style="display: none">'+'listByCateBar/'+t_cateId+'</span></td><td class="tr">';
						html = html +'<button class="ct-choose" onclick="cc(this);">选择</button>';
						html = html +'</td></tr>';
						if(data[i].childs.length>0){
							for(var j=0;j<data[i].childs.length;j++){
								html = html + '<tr class="level-2"><td>'
                                if(data[i].childs[j].childs.length>0){
                                    html = html +'<a class="level-op level-show2 " href="javascript:;"></a>'
                                    +'<a class="level-op level-hide2" href="javascript:;"></a>';
                                }

                                html = html +data[i].childs[j].name+'</td>'
                                html = html +'<td><span class="link" style="display: none">'+'listByCateBar/'+data[i].childs[j].cateBarId+'</span></td><td class="tr">';
                                if(data[i].childs[j].grade=='2'){
                                    html = html +'<button class="ct-choose" onclick="gradetwo(this);">选择</button>';
                                }

                                html = html +'</td></tr>';

                                  if(data[i].childs[j].childs.length>0){
                                      for(var h=0;h<data[i].childs[j].childs.length;h++){
                                          html = html + '<tr class="level-3">'
                                          +'<td>'+data[i].childs[j].childs[h].name+'</td>'
                                          +'<td><span class="link" style="display: none">'+'listByCateBar/'+data[i].childs[j].childs[h].cateBarId+'</span></td>'
                                          +'<td class="tr"><button class="ct-choose" onclick="cc(this);">选择</button></td></tr>';
                                      }
                                  }
							}
						}
						$tbody.append(html);
					}
					
				}
			});
			initTable();
		}
		
		//Ajax获取移动版货品
		function queryMobProduct(pageNo){
			var url = "ajaxQueryMobProductForInnerJoin.htm?1=1";
			var name = $("#chooseGoodsSearch").val();
			if(name){//根据分类id查询
				url = url + "&pname="+name;
			}
			if(pageNo){//根据货品名称查询
				url = url + "&pageNo="+pageNo;
			}
			$.ajax({
				url : url,
				async : false,
				success : function(data){
					//清空
					$("#goods").find("tbody").html("");
					//重新赋值
					for(var i=0;i<data.pb.list.length;i++){
						var goods = data.pb.list[i];
						var html = '<tr>'
						
						+'<td>'+goods.goodsInfoName+'</td>'
						+'<td><img alt="" src="'+goods.goodsInfoImgId+'" width="50px" height="50px"></td>';
                        var specs="";
						for(var j=0;j<goods.specVo.length;j++){
                            specs = specs+goods.specVo[j].spec.specName+':';
                            if(goods.specVo[j].specValueRemark!=null){
                                specs+=goods.specVo[j].specValueRemark+'<br/>';
                            }else{
                                specs+=goods.specVo[j].goodsSpecDetail.specDetailName+'<br/>';
                            }
                          ;
//							+':'+goods.specVo[i].goodsSpecDetail.specDetailName;
						}
                        html = html +'<td>'+specs+'</td>';
						html = html +'</td><td style="display: none"><span class="link">item/'+goods.goodsInfoId+'.html</span></td>'
                        html = html+'<td class="tr"><button class="ct-choose" onclick="cc(this);">选择</button></td></tr>'
						$("#goods").find("tbody").append(html);
					}
					//设置分页
					//设置当前页码 pageNo
					//起始页码startNo大于1显示“《”
					//结束页码endNo小于总页数totalPages显示“》”
					var pagination = $("#goods").find(".pagination");
					$(pagination).html("");
					var pageHtml = '';
					if((data.pb.pageNo-2)>1){
						pageHtml = pageHtml+'<li><a href="#" onclick="queryMobProduct('+(data.pb.pageNo-2)+')">&laquo;</a></li>';
					}
					for(var i=2;i>0;i--){
						var up = (data.pb.pageNo-i);
						if(up>0){
							pageHtml = pageHtml+'<li><a href="#" onclick="queryMobProduct('+up+')">'+up+'</a></li>';
							
						}
					}
					pageHtml = pageHtml+'<li class="active"><a href="#">'+data.pb.pageNo+'</a></li>';
					for(var i=0;i<(data.pb.totalPages-data.pb.pageNo) && i<2;i++){
						var down = (data.pb.pageNo+(i+1));
						pageHtml = pageHtml+'<li><a href="#" onclick="queryMobProduct('+down+')">'+down+'</a></li>';
					}
					if((data.pb.pageNo+2)<data.pb.totalPages){
						pageHtml = pageHtml+'<li><a href="#" onclick="queryMobProduct('+(data.pb.pageNo+2)+')">&raquo;</a></li>';
					}
					$(pagination).html(pageHtml);
				}
			});
			initTable();
				
		}
		
		$(function(){
			$('.nav-tabs a').click(function (e) {
				e.preventDefault();
				$(this).tab('show');
			});
		});		
	</script>
