<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    pageContext.setAttribute("path",path);
%>
<style>
    label.error1 {
        color: #a94442;
        margin-top: 5px;
        position: absolute;
        top: 110px;
        left: 10px;
    }
</style>
<!--图片导航模块-->

<!-- 图片导航(移动建站修改) -->
<div id="imagesNavEdit" class="edit_area cube_edit" style="display: none;">
    <div class="arrow"></div>
    <div class="edit_cont">
        <form action="" id="imageNavForm" class="form-cube">
            <div class="choose-img-box">
                <strong class="img-num-tip">选择图片导航的按钮个数</strong>
                <div class="choose-img-num">
                    <label><input type="radio" name="num" value="2">2</label>
                    <label><input type="radio" name="num" value="3">3</label>
                    <label><input type="radio" name="num" checked value="4">4</label>
                    <label><input type="radio" name="num" value="5">5</label>
                </div>
                <p style="color: #999;">建议图片尺寸: 160px × 160px&nbsp;&nbsp;&nbsp;支持图片格式: jpg,jpeg,png,bmp,gif</p>
            </div>

            <!--编辑器内容-->
            <div id="chooseSlide" class="slides_wrap">

            </div>
            <div class="app-submit" align="center" style="margin-top: 15px;">
                <input type="button" value=" 确 定 " name="saveImagesNav1" onclick="saveImagesNav()">
            </div>
        </form>
    </div>
</div>

<div style="display: none" id="cube_pic_nav_edit">
    <!-- <div class="slide clearfix" id="pic_nav_slide_#no#">
        <a href="javascript:;" class="view_img #img_add_status# fl">
            #view_img_info#
            <span style="display:#img_upload_dislpay#">重新上传</span>
        </a>
        <input type="hidden" id="nav_img_#no#_hidden" value="#img_hidden#" name="img_nav_hidden"/>
        <label class="error1" style="display:none;position: absolute;top: 110px;left: 10px;">请添加图片</label>
        <dl class="form_group nlink-wp fl clearfix">
            <dt class="dt-box">图片名称：</dt>
            <dd class="dd-box">
                <input type="text" id="img_name_#no#" name="imgnav_name" class="form-control" value="#img_name#" maxlength="5" />
            </dd>
        </dl>
        <dl class="form_group nlink-wp fl clearfix">
            <dt class="dt-box">链接地址：</dt>
            <dd class="dd-box">
                <div class="lk-sel lk-chs">
                    <span class="sel-word">功能链接</span>
                    <ul>
                        <li #sel_lk-01#><a data-href="lk-01">功能链接</a></li>
                        <li #sel_lk-02#><a data-href="lk-02">自定义链接</a></li>
                    </ul>
                </div>
                <div class="lk-sel gn-chs">
                    <span class="sel-word">--请选择--</span>
                    <ul>
                        <li #choose_lk_empty#><a data-choose="lk_empty">--请选择--</a></li>
                        <li #choose_lk_brands#><a data-href="choose-brands" data-choose="lk_brands">品牌</a></li>
                        <li #choose_lk_thirds#><a data-href="choose-thirds" data-choose="lk_thirds">店铺</a></li>
                        <li #choose_lk_goods#><a data-href="choose-goods" data-choose="lk_goods">分类/商品</a></li>
                        <li #choose_lk_subject#><a data-href="choose-subject" data-choose="lk_subject">页面</a></li>
                        <%--<li #choose_lk_allproduct#><a data-href="list/allproduct.html" data-choose="lk_allproduct">所有商品</a></li>--%>
                        <%--<li #choose_lk_main#><a data-href="main.html" data-choose="lk_goods">微店首页</a></li>--%>
                        <li #choose_lk_cates#><a data-href="cates.html" data-choose="lk_cates">分类列表</a></li>
                        <li #choose_lk_myorder#><a data-href="customer/myorder.html" data-choose="lk_myorder">我的订单</a></li>
                        <li #choose_lk_cart#><a data-href="myshoppingmcart.html" data-choose="lk_cart">购物车</a></li>
                        <%--<li #choose_lk_coupon#><a data-href="customer/coupon.html" data-choose="lk_coupon">优惠券</a></li>--%>
                        <li #choose_lk_center#><a data-href="customercenter.html" data-choose="lk_center">账户中心</a></li>
                        <%--<li #choose_lk_news#><a data-href="inforlist.html" data-choose="lk_news">新闻中心</a></li>--%>
                        <%--<li #choose_lk_address#><a data-href="customer/address.html" data-choose="lk_address">收货地址</a></li>--%>
                        <li #choose_lk_buy#><a data-href="panicbuyinglist.html" data-choose="lk_buy">抢购</a></li>
                        <li #choose_lk_store#><a data-href="storeliststreet.html" data-choose="lk_store">店铺街</a></li>
                    </ul>
                </div>
                <input type="text" class="form-control custom-input" id="pic_nav_slide_ImgNavHref#no#" name="imgnav_href"  value="#href_value#" style="margin-top: 10px;display:#input_img_nav_display#">
            </dd>
        </dl>
        <dl class="nlink-wp fl clearfix none" style="display:#tag_img_nav_display#">
            <dt class="dt-box">已选择：</dt>
            <dd class="dd-box">
                <span class="sel-tags choose_tag"><em>#choose_tag#</em></span>
                <a href="javascript:;" class="del-tags">删除</a>
            </dd>
        </dl>
    </div> -->
</div>

<div style="display: none" id="cube_pic_nav_temp">
    <!-- <div class="app_item app_cube app_selected">
        <div class="img_nav item" id="imagenav#no#">
            <div class="image-nav-bar">

            </div>
        </div>
        <div class="app_edit">
            <div class="app_btns">
                <a href="javascript:;" class="edit" onclick="editImagesNav(this)">编辑</a>
                <a href="javascript:;" class="edit" onclick="removeImageNav(this)">删除</a>
            </div>
        </div>
    </div> -->
</div>

<div style="display: none" id="cube_pic_nav_info">
    <!--<a class="img-nav-item" href="#data-href#" data-img="#data-img#" data-name="#data-name#" data-chs="#data-chs#" data-chs2="#data-chs2#" data-tag="#data-tag#" ><img src="#data-img#"/><span>#data-name#</span></a>-->
</div>


<!-- /图片导航(移动建站修改) -->
<script type="text/javascript">
    // 添加图片导航(移动建站修改)
    function addImagesNav() {
        desitory();
        hidenOtherEdit();
        //监听事件
        bindCheckBox();
        clickImage();
        picNav_initFormRule();

        var navNum = 4;
        var editInfoStr = '';
        var editHtml = trimComment($("#cube_pic_nav_edit").html());
        for (var i = 1; i <= navNum; i++) {
            editInfoStr += addNewNav(i,editHtml);
        }
        $("#imagesNavEdit #chooseSlide").html(editInfoStr);

        $("#imagesNavEdit").find(":radio[value='"+navNum+"']").click();

        //显示
        $("#imagesNavEdit").show();

        //模板增加空内容
        var no = $("div[id^='pic_nav_slide_']").size()+1;//新增id
        var html = trimComment($("#cube_pic_nav_temp").html());
        var param = {};
        param['no'] = no;
        html = putKeyValue(html,param);
        $("#ip_cont").append(html);

        //重新定位添加组件，让其往下移动
        pluginAdd_offset();

        for (var i = 1; i <= $("#imagesNavEdit #chooseSlide .slide").length; i++) {
            var _id = 'pic_nav_slide_ImgNavHref' + i;
            rbundLink(_id);
        }
    }

    //编辑图片导航窗口初始加载
    function editImagesNav(obj) {
        desitory();
        hidenOtherEdit();
        picNav_initFormRule();
        var appitem = $(obj).parents(".app_item");
        appitem.addClass("app_selected");

        var navNum = appitem.find(".img-nav-item").size();
        var editInfoStr = '';
        var editHtml = trimComment($("#cube_pic_nav_edit").html());

        if(navNum > 0){
            appitem.find(".img-nav-item").each(function(i,t){
                var data_src = $.trim($(t).attr("data-img"));
                var data_name = $.trim($(t).attr("data-name"));
                var data_chs = $.trim($(t).attr("data-chs"));
                var data_chs2 = $.trim($(t).attr("data-chs2"));
                var data_tag = $.trim($(t).attr("data-tag"));
                var data_href = $.trim($(t).attr("href"));

                var param = {}
                param['no'] = (i+1);
                param['img_hidden'] = data_src;
                param['view_img_info'] = '<img src="'+data_src+'" id="nav_img_'+(i+1)+'" alt="">';
                param['img_name'] = data_name;
                param['sel_'+data_chs] = 'class="s-selected"';
                param['choose_'+data_chs2] = 'class="s-selected"';
                param['href_value'] = data_href;
                param['choose_tag'] = data_tag;
                param['img_upload_dislpay'] = 'block';
                //如果是自定义一项
                if(data_chs == 'lk-02'){
                    param['input_img_nav_display'] = 'inline-block';
                    param['tag_img_nav_display'] = 'none';
                }else{
                    param['input_img_nav_display'] = 'none';
                    param['tag_img_nav_display'] = 'block';
                }
                editInfoStr += putKeyValue(editHtml,param);
            });
        }else{
            //如果编辑内容为空，默认为4
            navNum = 4;
            for (var i = 1; i <= navNum; i++) {
                editInfoStr += addNewNav(i,editHtml);
            }
        }


        $("#imagesNavEdit #chooseSlide").html(editInfoStr);
        $("#imagesNavEdit").find(":radio[value='"+navNum+"']").click();

        //监听事件
        bindCheckBox();
        clickImage();


        var _h = appitem.offset().top - 150;
        $(".edit_area").css("top",_h);

        //显示
        $("#imagesNavEdit").show();
        for (var i = 1; i <= $("#imagesNavEdit #chooseSlide .slide").length; i++) {
            var _id = 'pic_nav_slide_ImgNavHref' + i;
            rbundLink(_id);
        }

    }

    function picNav_initFormRule() {
        $('#imageNavForm').validate({
            debug:true,
            onkeyup:null,
            rules:{
                imgnav_name:{
                    specchar:true
                },
                imgnav_href:{
                    required:true
                }
            },
            messages:{
                imgnav_name:{
                    specchar:'不允许含有特殊字符'
                },
                imgnav_href:{
                    required:'请设置图片链接'
                },
            }
        });
    }
    
    //图片导航的确定按钮
    function saveImagesNav(){
        var real_num = $("#chooseSlide .slide").size();
        var editHtml = trimComment($("#cube_pic_nav_info").html());
        var editInfoStr = "";
        var isSuccess = 1;
        for(var i = 1;i<=real_num;i++){
            var slide = $("#pic_nav_slide_"+i);
            var imgStr = $.trim(slide.find("#nav_img_"+i+"_hidden").val());
            var imgName = $.trim(slide.find("#img_name_"+i).val());
            var lk_chs = $.trim(slide.find(".lk-chs").find("li.s-selected a").attr("data-href"));
            var gn_chs = $.trim(slide.find(".gn-chs").find("li.s-selected a").attr("data-choose"));
            var tag_name = $.trim(slide.find(".choose_tag em").text());
            var havHref = $.trim(slide.find("#pic_nav_slide_ImgNavHref"+i).val());

            if(imgStr == ''){
                slide.find("#nav_img_"+i+"_hidden").next("label").show();//显示图片上传不允许为空错误信息
                isSuccess = 0;
            }

            //填充生成HTML
            var params = {};
            params['data-img'] = imgStr;
            params['data-name'] = imgName;
            params['data-chs'] = lk_chs;
            params['data-chs2'] = gn_chs;
            params['data-tag'] = tag_name;
            params['data-href'] = havHref;
            editInfoStr += putKeyValue(editHtml,params);
        }

        if(!$('#imageNavForm').valid()){
            isSuccess = 0;
        }

        if(isSuccess){
            $("#ip_cont").find(".app_selected").find(".image-nav-bar").html(editInfoStr);
            $("#imagesNavEdit").hide();
        }
    }

    //销毁
    function desitory() {
        $("#chooseSlide").empty();
    }

    //单选框事件监听
    function bindCheckBox() {
        var radio = $("#imagesNavEdit .choose-img-num :radio");
        radio.off("change");
        radio.change(function(){
            var num = $(this).val();
            var real_num = $("#chooseSlide .slide").size();
            //如果选中数大于实际数，则增加录入信息，否则删除录入信息
            if(num > real_num){
                var editInfoStr = '';
                var editHtml = trimComment($("#cube_pic_nav_edit").html());
                for (var i = real_num+1; i <= num; i++) {
                    editInfoStr += addNewNav(i,editHtml);
                }
                $("#chooseSlide").append(editInfoStr);

                for(var i = real_num+1; i <= num; i++){
                    var _id = 'pic_nav_slide_ImgNavHref' + i;
                    rbundLink(_id);
                }
            } else {
                for (var i = real_num; i > num; i--) {
                    $("#chooseSlide").find("#pic_nav_slide_"+i).remove();
                }
            }
        });
    }

    //选择图片事件监听
    function clickImage(){
        $("#imagesNavEdit").off("click",".view_img");
        $("#imagesNavEdit").on("click",".view_img",function () {
            var hid = $(this).find("img").attr("id");
            art.dialog.open('${basth}queryImageManageByPbAndCidForChoose.htm?CSRFToken=${token}&size=1', {
                id: hid,
                lock: true,
                width: '800px',
                height: '400px',
                title: '选择图片'
            });
        });
    }

    //让其他小窗口关掉，清空选中项
    function hidenOtherEdit(){
        //隐藏
        $("#advEdit").hide();
        $("#cubeEdit").hide();
        $("#rollAdvEdit").hide();
        $("#textEdit").hide();
        $("#fullRollEdit").hide();
        $("#basicEdit").hide();
        $("#goodsEdit").hide();
        $("#blankBoxEdit").hide();
        $("#ip_cont").find(".app_selected").removeClass("app_selected");
    }

    function trimComment(html){
        return replaceAll(replaceAll(html,'<!--',''),'-->','');
    }

    function pluginAdd_offset(){
        //定位
        //$("#ip_cont").find(".app_selected").removeClass("app_selected");
        var _h = $(".plugin_add").offset().top - 120;
        $(".edit_area").css("top", _h);
    }

    //新增新的导航HTML
    function addNewNav(i,editHtml){
        var param = {}
        param['no'] = i;
        param['view_img_info'] = '<img src="${path}/mobile_home_page/images/plus.png" id="nav_img_'+i+'" alt="">';
        param['img_add_status'] = 'add_img';
        param['sel_lk-01'] = 'class="s-selected"';
        param['choose_lk_empty'] = 'class="s-selected"';
        param['input_img_nav_display'] = 'none';
        param['tag_img_nav_display'] = 'none';
        param['img_upload_dislpay'] = 'none';
        return putKeyValue(editHtml,param);
    }

    function putKeyValue(html,obj){
        var t_html = html;
        if(obj!=null){
            for(var key in obj){
                t_html = replaceAll(t_html,'#'+key+'#',obj[key]);
            }
        }

        //将其他没替换的都设为空
        t_html = replaceAll(t_html,'#(\\w)*#','');
        return t_html;
    }

    //删除
    function removeImageNav(obj){
        hidenOtherEdit();
        $(obj).parents(".app_item").remove();
        $("#imagesNavEdit").hide();
    }
</script>