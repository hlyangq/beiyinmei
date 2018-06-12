<%--
  Created by IntelliJ IDEA.
  User: zhangjin
  Date: 2016/6/19
  Time: 上午10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="kstore/el-common" prefix="el" %>

<html>
<head lang="zh_CN">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>主题页配置</title>

    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">

    <link rel="stylesheet" href="<%=basePath %>css/swiper-3.3.1.min.css">
    <link rel="stylesheet" href="<%=basePath %>css/appsite/app_layout.m.css">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath %>css/appsite/ui-dialog.css">
    <link rel="stylesheet" href="<%=basePath %>css/appsite/tabs.css">
    <link rel="stylesheet" href="<%=basePath %>css/appsite/topic-iterm.css">

    <script src="<%=basePath %>js/appsite/jquery.min.js"></script>

    <script>
        var appData = ${el:toJsonString(data)};
    </script>
    <style>
        .aui_state_lock {top: 20%!important;}
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
            <div class="main_count">
                <jsp:include page="../page/breadcrumbs.jsp"></jsp:include>
                <div class="common_data" style="padding-left:20px;">
                    <div class="layout_box">
                        <div class="layout_head"></div>
                        <div class="layout_cont" id="layoutThemeCont">
                            <!-- 第一块广告图位置-->
                            <div class="cont_box ui-state-disabled">
                                <div class="topic_banner">
                                    <img v-if="advert.img && advert.img != ''" :src="advert.img">
                                    <div v-if="!advert || !advert.img || advert.img == ''" style="width: 450px; height: 450px; background: #ddd">
                                    </div>
                                </div>

                                <div class="edit_btns">
                                    <a href="javascript:;" class="edit">编辑</a>
                                </div>
                                <div class="edit_area">
                                    <s></s>
                                    <a href="javascript:;" class="close">×</a>
                                    <div class="edit_form">
                                        <ul class="img_list">

                                            <li>
                                                <div class="img_item">
                                                    <img alt="" :src="advert.img" />
                                                    <a href="javascript:;" class="img_edit" v-on:click="chooseImage('advert')">重新上传</a>
                                                    <div class="img_link">
                                                        <div>
                                                            <span>链接类型：</span>
                                                            <div class="app-select-bar">
                                                                <strong v-if="!advert || advert.img == '' || !advert.actionParam || (!advert.actionParam.searchParam && !advert.actionParam.goodsInfoId) || advert.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                <strong v-if="advert.actionParam && advert.actionParam.searchParam && advert.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                <strong v-if="advert.actionParam && advert.actionParam.searchParam && advert.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                <strong v-if="advert.actionParam && advert.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                <ul class="app-select-box">
                                                                    <li v-bind:class="{'selected': !advert || advert.img == '' || !advert.actionParam || !advert.actionParam.searchParam}">
                                                                        <a href="javascript:;" v-on:click="chooseAction(0, 'advert')">请选择</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': advert.actionParam && advert.actionParam.searchParam && advert.actionParam.searchParam.cates">
                                                                        <a href="javascript:;" v-on:click="chooseAction(1, 'advert')">商品分类</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': advert.actionParam && advert.actionParam.searchParam && advert.actionParam.searchParam.brands">
                                                                        <a href="javascript:;" v-on:click="chooseAction(2, 'advert')">商品品牌</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': advert.actionParam && advert.actionParam.goodsInfoId">
                                                                        <a href="javascript:;" v-on:click="chooseAction(3, 'advert')">单个商品</a>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <p>
                                                            <span>链接内容：</span>
                                                            <template v-if="advert.action == 'GoodsDetail'">
                                                                <span><font color='blue'>{{advert.actionParam.goodsInfoId}}</font></span>
                                                            </template>
                                                            <template v-if="advert.actionParam && advert.actionParam.searchParam">
                                                                <span><font color='blue'>{{advert.actionParam.searchParam.cates}}</font></span>
                                                            </template>
                                                            <template v-if="advert.actionParam && advert.actionParam.searchParam">
                                                                <span><font color='blue'>{{advert.actionParam.searchParam.brands}}</font></span>
                                                            </template>
                                                        </p>
                                                    </div>
                                                </div>
                                            </li>

                                        </ul>

                                        <a href="javascript:;" class="btn btn_green" onClick="themeModule.handleSubmit(this, 'adverts', 0)">保存</a>
                                    </div>
                                </div>
                            </div>

                            <style scoped>
                                .topic_banner img {
                                    width: 100%;
                                }
                            </style>

                            <!-- 菜单 -->
                            <template v-if="floors.length > 1">
                                <div class="topic-tabs">
                                    <ul class="tabs-list swiper-wrapper">
                                        <li class="swiper-slide" v-for="floor in floors">
                                            <a>
                                                {{ floor.text }}
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </template>

                            <!-- floors -->
                            <template v-for="f in floors">

                                <div class="cont_box" :id="'f'+$index">
                                    <div class="topic-item" :style="{backgroundColor: f.backgroundColor}">
                                        <h3 class="item-title" :style="{backgroundColor: f.secondColor}">{{ f.text }}</h3>
                                        <div class="item-content">
                                            <div class="item-box" v-for="advert in f.adverts" :style="{backgroundColor: f.secondColor}">
                                                <div class="item-img">
                                                    <img :src="advert.img" alt="">
                                                </div>
                                                <div class="item-info">
                                                    <p class="item-name">{{ advert.text }}</p>
                                                    <div class="bt-bar">
                                                        <p class="item-price">&yen;&nbsp;<strong>{{ advert.price }}</strong></p>
                                                        <span class="buy-btn" :style="{color: f.secondColor}">立即购买</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="edit_btns">
                                        <a href="javascript:;" class="edit">编辑</a>
                                        <a href="javascript:;" class="delete" @click="removeModule($index)">删除</a>
                                    </div>
                                    <div class="edit_area">
                                        <s></s>
                                        <a href="javascript:;" class="close">×</a>
                                        <div class="edit_form">
                                            <div class="form-group">
                                                <label class="control-label">选择主题色:</label>
                                                <input type="color" v-model="f.backgroundColor" :value="f.backgroundColor">
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label">选择辅助色:</label>
                                                <input type="color" v-model="f.secondColor" :value="f.secondColor">
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label">标题:</label>
                                                <input type="text" :value="f.text" v-model="f.text">
                                            </div>
                                            <a href="javascript:;" class="add_img" onClick="themeModule.addGood({{$index}})">[+]添加一个商品</a>
                                            <ul class="img_list">

                                                <li v-for="advert in f.adverts">
                                                    <div class="img_item">
                                                        <span class="num">{{$index + 1}}</span>
                                                        <img alt="" :src="advert.img" />
                                                        <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].adverts['+ $index +']')">重新上传</a>
                                                        <div class="img_link">
                                                            <span>链接类型：</span>
                                                            <div class="app-select-bar">
                                                                <strong v-if="!advert || advert.img == '' || !advert.actionParam || (!advert.actionParam.searchParam && !advert.actionParam.goodsInfoId) || advert.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                <strong v-if="advert.actionParam && advert.actionParam.searchParam && advert.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                <strong v-if="advert.actionParam && advert.actionParam.searchParam && advert.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                <strong v-if="advert.actionParam && advert.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                <ul class="app-select-box">
                                                                    <li v-bind:class="{'selected': !advert || advert.img == '' || !advert.actionParam || !advert.actionParam.searchParam}">
                                                                        <a href="javascript:;" v-on:click="chooseAction(0, 'floors['+$parent.$index+'].adverts['+ $index +']')">请选择</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': advert.actionParam && advert.actionParam.searchParam && advert.actionParam.searchParam.cates">
                                                                        <a href="javascript:;" v-on:click="chooseAction(1, 'floors['+$parent.$index+'].adverts['+ $index +']')">商品分类</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': advert.actionParam && advert.actionParam.searchParam && advert.actionParam.searchParam.brands">
                                                                        <a href="javascript:;" v-on:click="chooseAction(2, 'floors['+$parent.$index+'].adverts['+ $index +']')">商品品牌</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': advert.actionParam && advert.actionParam.goodsInfoId">
                                                                        <a href="javascript:;" v-on:click="chooseAction(3, 'floors['+$parent.$index+'].adverts['+ $index +']')">单个商品</a>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                            <p>
                                                                <span>链接内容：</span>
                                                                <template v-if="advert.action == 'GoodsDetail'">
                                                                    <span><font color='blue'>{{advert.actionParam.goodsInfoId}}</font></span>
                                                                </template>
                                                                <template v-if="advert.actionParam && advert.actionParam.searchParam">
                                                                    <span><font color='blue'>{{advert.actionParam.searchParam.cates}}</font></span>
                                                                </template>
                                                                <template v-if="advert.actionParam && advert.actionParam.searchParam">
                                                                    <span><font color='blue'>{{advert.actionParam.searchParam.brands}}</font></span>
                                                                </template>
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <a class="delete_item" href="javascript:;" @click="deleteImg($parent.$index, $index)">×</a>
                                                </li>

                                            </ul>

                                            <a href="javascript:;" class="btn btn_green" onClick="themeModule.handleSubmit(this, 'floors', {{$index}})">保存</a>
                                        </div>
                                    </div>
                                </div>
                            </template>

                            <div class="ad_blocks clearfix">
                                <div class="blocks_item">
                                    <a href="javascript:;" class="btn btn_green add_box type0" @click="addTemplate()">添加专题模块</a>
                                </div>
                            </div>

                        </div>

                        <div class="save_template">
                            <input type="hidden" name="appData" />
                            <input type="button" id="saveBtn" class="btn btn_green" value="保存配置" />
                        </div>
                        <input type="hidden" value="${token}"  id="token">
                        <input type="hidden" value="<%=basePath %>"  id="basePath">
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>


<script src="<%=basePath %>js/appsite/vue.js"></script>
<script src="<%=basePath %>js/appsite/swiper.jquery.min.js"></script>
<script src="<%=basePath %>js/appsite/jquery-ui.js"></script>
<script src="<%=basePath %>js/appsite/dialog-min.js"></script>
<script src="<%=basePath %>js/appsite/theme.js"></script>
<script>
    // 保存选择图片
    var saveChoooseImage = function (path, sourceId) {
        console.log('------save image-----', path, sourceId + '.img');

        vm.$set(sourceId + '.img', jQuery.isArray(path) ? path[0] : path);
    };

    // 保存选择的商品信息
    var saveChoooseProduct = function (ids, imgs, nos, names, titles, prices, sourceId) {
        console.log('-----save product-----', ids, imgs, nos, names, titles, prices, sourceId);
        if (sourceId.endsWith('adverts')) { //添加商品
            var advertsData = JSON.parse(JSON.stringify(vm.$get(sourceId) || []));
            var goodsLength = vm.$get(sourceId).length;
            for (var i = 0; i < ids.length; i++) {
                var goodData = {
                    "id": ids[i],
                    "text": names[i],
                    "img": imgs[i],
                    "action": "GoodsDetail",
                    "actionParam": {'goodsInfoId': ids[i]},
                    "ordering": goodsLength + 1 + i,
                    "price": prices[i]
                };
                advertsData.push(goodData);
            }
            vm.$set(sourceId, advertsData);
        } else {
            vm.$set(sourceId + '.actionParam', {'goodsInfoId': ids[0]});
        }
    };

    // 保存选择的分类信息
    var saveChoooseCat = function (cats, sourceId) {
        console.log('-----save cat-----', cats, sourceId);
        vm.$set(sourceId + '.actionParam', {"searchParam": {"cates": [cats[0].text]}});
    };

    // 保存选择的品牌信息
    var saveChoooseBrand = function (brands, sourceId) {
        console.log('-----save brand-----', brands, sourceId);
        vm.$set(sourceId + '.actionParam', {"searchParam": {"brands": [brands[0].name]}});
    };
</script>
</body>
</html>
