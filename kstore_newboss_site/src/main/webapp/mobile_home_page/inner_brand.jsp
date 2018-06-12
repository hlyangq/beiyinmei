<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!--专题页列表页-->
<style>
    #brandModal .modal-dialog{background:#fff;border-radius:3px;}

    #brandModal .nav-tabs>li>a{padding:3px 10px;font-size:14px;border:0;border-left:1px solid #ccc;}
    #brandModal .nav-tabs li:first-child a{border-left:0;}
    #brandModal .nav-tabs>li.active>a{font-weight:bold;}
    #brandModal .nav-tabs>li>a:hover{}
    #brandModal .close{position:absolute;right:10px;top:20px;}
</style>
<div id="brandModal" class="modal fade">
    <div class="modal-dialog modal-lg" >
        <input type="hidden" id="linkId">
                <div class="modal-header pr">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">选择品牌</h4>
                </div>
                <div class="modal-body" id="brands" style="max-height:300px;overflow-y: scroll;">
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th width="15%">品牌Logo</th>
                            <th width="25%">品牌名称</th>
                            <th>品牌别名</th>
                            <th width="22%">
                                <!-- <form class="form-search fr">
                                </form> -->
                                <input type="text" id="chooseBrandSearch" placeholder="请输入品牌名称" class="input-medium search-query" style="color:black;">
                                <input type="submit" class="btn btn-default btn-sm" onclick="queryMobBrand(1)" value="搜索">
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
    </div>
</div><!--/modal-->

<script>
    //Ajax获取移动版货品
    function queryMobBrand(pageNo){
        var url = "ajaxFindAllBrand.htm?CSRFToken=${token}";
        var name = $("#chooseBrandSearch").val();
        if(name){
            url = url + "&brandName="+name;
        }
        if(pageNo){
            url = url + "&pageNo="+pageNo;
        }
        $.ajax({
            url : url,
            async : false,
            success : function(data){
                //清空
                $("#brands").find("tbody").html("");

                if(data.pb == null){
                    return
                }

                //重新赋值
                var address = data.address;
                if(data.pb.list != null){
                    for(var i=0;i<data.pb.list.length;i++){
                        var subject = data.pb.list[i];
                        var html = '<tr>';
                        html = html+'<td><img src="'+subject.brandLogo+'" height="36px"/></td>';
                        html = html+'<td>'+subject.brandName+'</td>';
                        html = html +'<td>'+ subject.brandNickname + '<span class="link" style="display: none">'+address+'listByBrand/'+subject.brandId + '.html</span></td>'
                        html = html+'<td class="tr"><button class="ct-choose" onclick="chooseBrand(this);">选择</button></td></tr>'
                        $("#brands").find("tbody").append(html);
                    }
                }
                //设置分页
                //设置当前页码 pageNo
                //起始页码startNo大于1显示“《”
                //结束页码endNo小于总页数totalPages显示“》”
                var pagination = $("#brandModal").find(".pagination");
                $(pagination).html("");
                var pageHtml = '';
                if((data.pb.pageNo-2)>1){
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobBrand('+(data.pb.pageNo-2)+')">&laquo;</a></li>';
                }
                for(var i=2;i>0;i--){
                    var up = (data.pb.pageNo-i);
                    if(up>0){
                        pageHtml = pageHtml+'<li><a href="#" onclick="queryMobBrand('+up+')">'+up+'</a></li>';

                    }
                }
                pageHtml = pageHtml+'<li class="active"><a href="#">'+data.pb.pageNo+'</a></li>';
                for(var i=0;i<(data.pb.totalPages-data.pb.pageNo) && i<2;i++){
                    var down = (data.pb.pageNo+(i+1));
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobBrand('+down+')">'+down+'</a></li>';
                }
                if((data.pb.pageNo+2)<data.pb.totalPages){
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobBrand('+(data.pb.pageNo+2)+')">&raquo;</a></li>';
                }
                $(pagination).html(pageHtml);
            }
        });
        initTable();

    }

    function chooseBrand(t){
        var _cont = $(t).parents("tr").find(".link").text(),
                _tit = $(t).parents("tr").find("td:eq(1)").text();
        $("#"+$("#linkId").val()).val(_cont);
        $("#"+$("#linkId").val()).parents(".form_group").next(".nlink-wp").show().find(".sel-tags").html("<em>"+_tit+"</em>");
        $(".close").click();
    };

   /* $(function(){
        $('.nav-tabs a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        });
    });*/
</script>
