<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!--专题页列表页-->
<style>
    #subjectModal .modal-dialog{background:#fff;border-radius:3px;}

    #subjectModal .nav-tabs>li>a{padding:3px 10px;font-size:14px;border:0;border-left:1px solid #ccc;}
    #subjectModal .nav-tabs li:first-child a{border-left:0;}
    #subjectModal .nav-tabs>li.active>a{font-weight:bold;}
    #subjectModal .nav-tabs>li>a:hover{}
    #subjectModal .close{position:absolute;right:10px;top:20px;}
</style>
<div id="subjectModal" class="modal fade">
    <div class="modal-dialog modal-lg" >
        <input type="hidden" id="linkId">
            <div class="modal-header pr">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">选择页面</h4>
            </div>
                <div class="modal-body" id="subjects" style="max-height:300px;overflow-y: scroll;">
                    <form action="" class="form-inline">
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon">页面名称</span>
                                <input type="text" class="form-control" id="chooseSubjectSearch"  placeholder="请输入页面名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon">页面分类</span>
                                <div class="form-control form-select" style="padding:0;">
                                    <select class="form-control" style="border:none;background:transparent;box-shadow:none;height:100%;min-width:170px;width: 179px;" id="choosePageCate">
                                        <option value="">不限</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary" type="button" onclick="queryMobSubject(1)">搜索</button>
                        </div>
                    </form>
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th width="45%">页面名称</th>
                            <th>页面分类</th>
                            <th>是否主页</th>
                            <th width="22%">
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
    //重新赋值
    //Ajax获取移动版货品
    function queryMobSubject(pageNo){
        var url = "ajaxQueryMobPageList.htm?CSRFToken=${token}&pageState=1";
        var name = $("#chooseSubjectSearch").val();
        var pageCate = $("#choosePageCate").val();

        if(name){//根据分类id查询
            url = url + "&title="+name;
        }
        if(pageNo){//根据货品名称查询
            url = url + "&pageNo="+pageNo;
        }
        if(pageCate!=''){//根据分类查询
            url = url + "&pageCateId="+pageCate;
        }
        $.ajax({
            url : url,
            async : false,
            success : function(data){
                //页面分类显未
                var cateList = data.pageCateList;
                var cateHtml = '<option value="">不限</option>';
                if(cateList != null){
                    for(var j in cateList){
                        var c = cateList[j];
                        var cateName = c.name;
                        if(cateName.length > 10){
                            cateName = cateName.substr(0,10)+"...";
                        }

                        if(pageCate!='' && pageCate == c.pageCateId){
                            cateHtml += '<option value="'+c.pageCateId+'" selected>'+cateName+'</option>';
                        }else{
                            cateHtml += '<option value="'+c.pageCateId+'">'+cateName+'</option>';
                        }
                    }
                    $("#choosePageCate").html(cateHtml);
                }

                //清空
                $("#subjects").find("tbody").html("");
                if(data.pb == null){
                    return;
                }

                //重新赋值
                var address = data.siteAddress;
                if(data.pb.list != null){
                    for(var i=0;i<data.pb.list.length;i++){
                        var subject = data.pb.list[i];
                        var html = '<tr><td>'+subject.title+'</td>';

                        var cateName = "无";
                        var realCateName = "无";
                        if(cateList!=null){
                            for(var j in cateList){
                                var c = cateList[j];
                                var t_cateName = c.name;
                                if(t_cateName.length > 10){
                                    t_cateName = t_cateName.substr(0,10)+"...";
                                }
                                if(c.pageCateId == subject.pageCateId){
                                    cateName = t_cateName;
                                    realCateName = c.name;
                                }
                            }
                        }
                        html = html + '<td title="'+realCateName+'">'+cateName+'</td>';
                        var isIndex = "否";
                        if(subject.temp2 == '1'){
                            isIndex = "是";
                        }
                        html = html + '<td>'+isIndex;

                        var hrefAddress =  address+'page/'+ subject.homepageId + '.html';
                        if(subject.temp2 == '1'){
                            hrefAddress = address;
                        }
                        html = html +'<span class="link" style="display: none;">'+ hrefAddress + '</span></td>';
                        html = html+'<td class="tr"><button class="ct-choose" onclick="cc(this);">选择</button></td></tr>';
                        $("#subjects").find("tbody").append(html);
                    }
                }
                //设置分页
                //设置当前页码 pageNo
                //起始页码startNo大于1显示“《”
                //结束页码endNo小于总页数totalPages显示“》”
                var pagination = $("#subjectModal").find(".pagination");
                $(pagination).html("");
                var pageHtml = '';
                if((data.pb.pageNo-2)>1){
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobSubject('+(data.pb.pageNo-2)+')">&laquo;</a></li>';
                }
                for(var i=2;i>0;i--){
                    var up = (data.pb.pageNo-i);
                    if(up>0){
                        pageHtml = pageHtml+'<li><a href="#" onclick="queryMobSubject('+up+')">'+up+'</a></li>';

                    }
                }
                pageHtml = pageHtml+'<li class="active"><a href="#">'+data.pb.pageNo+'</a></li>';
                for(var i=0;i<(data.pb.totalPages-data.pb.pageNo) && i<2;i++){
                    var down = (data.pb.pageNo+(i+1));
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobSubject('+down+')">'+down+'</a></li>';
                }
                if((data.pb.pageNo+2)<data.pb.totalPages){
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobSubject('+(data.pb.pageNo+2)+')">&raquo;</a></li>';
                }
                $(pagination).html(pageHtml);
            }
        });
        initTable();

    }

   /* $(function(){
        $('.nav-tabs a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        });
    });*/
</script>
