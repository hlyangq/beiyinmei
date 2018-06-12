<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="kstore/el-common" prefix="el" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>APP首页配置</title>

    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">
    <link href="<%=basePath %>css/style_new.css" rel="stylesheet">

    <link rel="stylesheet" href="<%=basePath %>css/appsite/vue-swipe.css">
    <link rel="stylesheet" href="<%=basePath %>css/appsite/ui-dialog.css">
    <link rel="stylesheet" href="<%=basePath %>css/appsite/app_layout.m.css">
    <link rel="stylesheet" href="<%=basePath %>css/appsite/font-awesome.min.css">

    <script src="<%=basePath %>js/appsite/jquery.min.js"></script>

    <style type="text/css">
        /*.current_template, .template_list {*/
        /*margin-bottom: 20px;*/
        /*}*/

        .current_template h4, .template_list h4 {
            border-left: 2px solid #0099FF;
            padding-left: 10px;
            font-size: 14px;
        }

        .current_template .body {
            position: relative;
            border: 1px solid #ddd;
            padding: 10px;
        }

        .current_template .body .pre_info {
            margin-left: 20px;
        }

        .current_template .body .pre_info h5 {
            margin-top: 0;
            font-size: 16px;
        }

        .current_template .body .pre_btns {
            position: absolute;
            width: 100px;
            bottom: 10px;
            left: 210px;
        }

        /*.template_item_mobile {*/
        /*position: relative;*/
        /*float: left;*/
        /*margin: 0 10px 10px 0;*/
        /*padding: 10px;*/
        /*border: 1px solid #ddd;*/
        /*}*/

        .template_item_mobile .ctrl {
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;
            height: 50px;
            background: rgba(0, 0, 0, 0.5);
            padding: 10px 0;
        }

        .template_item_mobile .ctrl p {
            color: #fff;
            text-indent: 10px;
        }

        .template_item_mobile .ctrl .ctrl_btns {
            display: none;
        }

        .add-template a {
            display: block;
            width: 180px;
            height: 240px;
            background: url(<%=basePath %>images/mb_add.png) no-repeat center center;
        }
    </style>
    <script type="text/javascript">
        var appData = ${el:toJsonString(data)};
        console.log('appData--->', JSON.stringify(appData));
    </script>
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
                <div class="common_data" style="padding-left:20px;">
                    <div class="layout_box">
                        <div class="layout_head"></div>
                        <div class="layout_cont" id="layoutCont">
                            <!-- 轮播区域  -->
                            <div id="sliders" class="cont_box ui-state-disabled">
                                <div class="top_line">
                                    <img alt="" src="<%=basePath%>images/appsite/top_area.png" />
                                </div>

                                <swipe class="swiper-container app_top_banner">
                                    <swipe-item v-for="e in sliders" class="swiper-slide">
                                        <img alt="" v-bind:src="e.img" />
                                    </swipe-item>
                                </swipe>

                                <div class="edit_btns">
                                    <a href="javascript:;" class="edit">编辑</a>
                                </div>

                                <div class="edit_area">
                                    <s></s>
                                    <a href="javascript:;" class="close">×</a>
                                    <div class="edit_form">
                                        <a href="javascript:;" class="add_img" v-on:click="addImage($index, 'sliders')">[+]添加一个图片</a>
                                        <ul class="img_list">
                                            <li v-bind:class="{'item_error': e.error}" v-for="e in sliders">
                                                <div class="img_item">
                                                    <span class="num">{{$index + 1}}</span>
                                                    <img alt="" v-bind:src="e.img">
                                                    <a href="javascript:;" class="img_edit" v-on:click="chooseImage('sliders['+ $index +']')">重新上传</a>
                                                    <div class="img_link">
                                                        <div>
                                                            <span>链接类型：</span>
                                                            <div class="app-select-bar">
                                                                <div v-if="e.action != 'Theme1'">
                                                                    <strong v-if="!e.action || e.action == ''  || !e.actionParam || (!e.actionParam.searchParam && !e.actionParam.goodsInfoId) || e.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                </div>
                                                                <strong v-if="e.actionParam && e.actionParam.searchParam && e.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                <strong v-if="e.actionParam && e.actionParam.searchParam && e.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                <strong v-if="e.actionParam && e.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                <%--<strong v-if="e.action == 'Theme1'" class="app-selected-text">主题1</strong>--%>
                                                                <ul class="app-select-box">
                                                                    <li v-bind:class="{'selected': !e.action || e.action == '' || !e.actionParam || (!e.actionParam.searchParam && !e.actionParam.goodsInfoId) || e.actionParam.searchParam.searchText}">
                                                                        <a href="javascript:;" onClick="chooseAction(0, 'sliders['+ {{$index}} + ']')">请选择</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': e.actionParam && e.actionParam.searchParam && e.actionParam.searchParam.cates}">
                                                                        <a href="javascript:;" onClick="chooseAction(1, 'sliders['+ {{$index}} + ']')">商品分类</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': e.actionParam && e.actionParam.searchParam && e.actionParam.searchParam.brands}">
                                                                        <a href="javascript:;" onClick="chooseAction(2, 'sliders['+ {{$index}} + ']')">商品品牌</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': e.actionParam && e.actionParam.goodsInfoId}">
                                                                        <a href="javascript:;" onClick="chooseAction(3, 'sliders['+ {{$index}} + ']')">单个商品</a>
                                                                    </li>
                                                                   <%-- <li v-bind:class="{'selected': e.action == 'Theme1'}">
                                                                        <a href="javascript:;" onClick="chooseAction(4, 'sliders['+ {{$index}} + ']')">主题1</a>
                                                                    </li>--%>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <p>
                                                            <span>链接内容：</span>
                                                            <template v-if="e.action == 'GoodsDetail'">
                                                                <span><font color='blue'>{{e.actionParam.goodsInfoId}}</font></span>
                                                            </template>
                                                            <template v-if="e.actionParam && e.actionParam.searchParam">
                                                                <span><font color='blue'>{{e.actionParam.searchParam.cates}}</font></span>
                                                            </template>
                                                            <template v-if="e.actionParam && e.actionParam.searchParam">
                                                                <span><font color='blue'>{{e.actionParam.searchParam.brands}}</font></span>
                                                            </template>
                                                            <%--<template v-if="e.action == 'Theme1'">
                                                                <span><font color='blue'>{{"主题1"}}</font></span>
                                                            </template>--%>
                                                        </p>
                                                    </div>
                                                </div>
                                                <a class="delete_item" href="javascript:;" v-on:click="deleteImg($parent.$index, $index, 'sliders')">×</a>
                                            </li>

                                        </ul>
                                        <a href="javascript:;" class="btn btn_green" onClick="handleSubmit(this, 'sliders')">保存</a>
                                        <label style="vertical-align:-2px; margin-left:10px; font-weight:500;">建议尺寸（720*396）</label>
                                    </div>
                                </div>
                            </div>

                            <!-- 一级广告栏 -->
                            <div class="cont_box ui-state-disabled">
                                <div class="img_recommend row">

                                    <div v-for="e in adverts" class="col-12">
                                        <a href="javascript:;">
                                            <img alt="" v-bind:src="e.img" height="100"/>
                                        </a>
                                    </div>

                                </div>
                                <div class="edit_btns">
                                    <a href="javascript:;" class="edit">编辑</a>
                                </div>
                                <div class="edit_area">
                                    <s></s>
                                    <a href="javascript:;" class="close">×</a>
                                    <div class="edit_form">
                                        <a href="javascript:;" class="add_img" v-on:click="addImage($index, 'adverts')">[+]添加一个图片</a>
                                        <ul class="img_list">

                                            <li v-bind:class="{'item_error': e.error}" v-for="e in adverts">
                                                <div class="img_item">
                                                    <span class="num">{{$index + 1}}</span>
                                                    <img alt="" v-bind:src="e.img" />
                                                    <a href="javascript:;" class="img_edit" v-on:click="chooseImage('adverts['+ $index +']')">重新上传</a>
                                                    <div class="img_link">
                                                        <div>
                                                            <span>链接类型：</span>
                                                            <div class="app-select-bar">
                                                                <strong v-if="!e.action || e.action == '' || !e.actionParam || (!e.actionParam.searchParam && !e.actionParam.goodsInfoId) || e.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                <strong v-if="e.actionParam && e.actionParam.searchParam && e.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                <strong v-if="e.actionParam && e.actionParam.searchParam && e.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                <strong v-if="e.actionParam && e.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                <ul class="app-select-box">
                                                                    <li v-bind:class="{'selected': !e.action || e.action == '' || !e.actionParam || (!e.actionParam.searchParam && !e.actionParam.goodsInfoId) || e.actionParam.searchParam.searchText}">
                                                                        <a href="javascript:;" onClick="chooseAction(0, 'adverts['+ {{$index}} + ']')">请选择</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': e.actionParam && e.actionParam.searchParam && e.actionParam.searchParam.cates}">
                                                                        <a href="javascript:;" onClick="chooseAction(1, 'adverts['+ {{$index}} + ']')">商品分类</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': e.actionParam && e.actionParam.searchParam && e.actionParam.searchParam.brands}">
                                                                        <a href="javascript:;" onClick="chooseAction(2, 'adverts['+ {{$index}} + ']')">商品品牌</a>
                                                                    </li>
                                                                    <li v-bind:class="{'selected': e.actionParam && e.actionParam.goodsInfoId}">
                                                                        <a href="javascript:;" onClick="chooseAction(3, 'adverts['+ {{$index}} + ']')">单个商品</a>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <p>
                                                            <span>链接内容：</span>
                                                            <template v-if="e.action == 'GoodsDetail'">
                                                                <span><font color='blue'>{{e.actionParam.goodsInfoId}}</font></span>
                                                            </template>
                                                            <template v-if="e.actionParam && e.actionParam.searchParam">
                                                                <span><font color='blue'>{{e.actionParam.searchParam.cates}}</font></span>
                                                            </template>
                                                            <template v-if="e.actionParam && e.actionParam.searchParam">
                                                                <span><font color='blue'>{{e.actionParam.searchParam.brands}}</font></span>
                                                            </template>
                                                        </p>
                                                    </div>
                                                </div>
                                                <a class="delete_item" href="javascript:;" v-on:click="deleteImg($parent.$index, $index, 'adverts')">×</a>
                                            </li>

                                        </ul>
                                        <div class="img_sample">
                                            <a href="javascript:;" class="show_sample">查看图片广告示例 <i class="icon-angle-right"></i></a>
                                            <div style="display:none">
                                                <img alt="" src="<%=basePath%>images/appsite/img_type1_sample.jpg">
                                            </div>
                                        </div>
                                        <a href="javascript:;" class="btn btn_green" onClick="handleSubmit(this, 'adverts')">保存</a>
                                        <label style="vertical-align:-2px; margin-left:10px; font-weight:500;">建议尺寸（359*160）</label>
                                    </div>
                                </div>
                            </div>

                            <!-- 楼层 -->
                            <template v-for="e in floors">

                                <template v-if="e.template == 'RushGroupBuy'">
                                    <!-- 抢购 -->
                                    <div class="cont_box">
                                        <div class="floor">
                                            <h2>抢购/团购</h2>
                                            <div class="special_buying_box">
                                                <img src="<%=basePath%>images/appsite/rush_group_buy_cont.jpg" alt="" width="100%">
                                            </div>
                                        </div>

                                        <div class="edit_btns">
                                            <a href="javascript:;" class="delete" v-on:click="removeModule($index)">删除</a>
                                        </div>
                                    </div>
                                </template>

                                <template v-if="e.template == '1'">
                                    <!-- 模板1 -->
                                    <div class="cont_box">
                                        <div class="floor">
                                            <h2>{{e.text}}</h2>
                                            <div class="spelling_ads type1 row">
                                                <div class="col-12">
                                                    <a href="javascript:;">
                                                        <img alt="" v-bind:src="e.adverts[0].img" height="201">
                                                    </a>
                                                </div>
                                                <div class="col-12">
                                                    <a href="javascript:;">
                                                        <img alt="" v-bind:src="e.adverts[1].img" height="100">
                                                    </a>
                                                    <a href="javascript:;">
                                                        <img alt="" v-bind:src="e.adverts[2].img" height="100">
                                                    </a>
                                                </div>
                                            </div>
                                            <template v-if="e.banners && e.banners.length > 0">
                                                <swipe class="swiper-container banner_box">
                                                    <swipe-item v-for="item in e.banners" class="swiper-slide">
                                                        <img alt="" v-bind:src="item.img" />
                                                    </swipe-item>
                                                </swipe>
                                            </template>
                                        </div>

                                        <div class="edit_btns" floor-index={{$index}}>
                                            <a href="javascript:;" class="edit">编辑</a>
                                            <a href="javascript:;" class="delete" v-on:click="removeModule($index)">删除</a>
                                        </div>

                                        <div class="edit_area">
                                            <s></s>
                                            <a href="javascript:;" class="close">×</a>
                                            <div class="edit_form">
                                                <div class="form_item">
                                                    <label>楼层标题：</label>
                                                    <input type="text" v-model="e.text">
                                                </div>
                                                <ul class="img_list">

                                                    <li v-bind:class="{'item_error': item.error}" v-for="item in e.adverts">
                                                        <div class="img_item">
                                                            <span class="num">{{$index + 1}}</span>
                                                            <img alt="" v-bind:src="item.img" />
                                                            <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].adverts['+ $index +']')">重新上传</a>
                                                            <div class="img_link">
                                                                <div>
                                                                    <span>链接类型：</span>
                                                                    <div class="app-select-bar">
                                                                        <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                        <ul class="app-select-box">
                                                                            <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">请选择</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品分类</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品品牌</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">单个商品</a>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                                <p>
                                                                    <span>链接内容：</span>
                                                                    <template v-if="item.action == 'GoodsDetail'">
                                                                        <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                    </template>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </li>

                                                </ul>
                                                <div class="img_sample">
                                                    <a href="javascript:;" class="show_sample">查看图片广告示例 <i class="icon-angle-right"></i></a>
                                                    <div style="display:none">
                                                        <img alt="" src="<%=basePath%>images/appsite/img_type2_sample.jpg">
                                                    </div>
                                                </div>
                                                <div class="add_img_box">
                                                    <a href="javascript:;" class="add_img" v-on:click="addImage($index)">[+]添加一个轮播图片</a>
                                                    <ul class="img_list">

                                                        <li v-bind:class="{'item_error': item.error}" v-for="item in e.banners">
                                                            <div class="img_item">
                                                                <span class="num">{{$index + 1}}</span>
                                                                <img alt="" v-bind:src="item.img">
                                                                <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].banners['+ $index +']')">重新上传</a>
                                                                <div class="img_link">
                                                                    <div>
                                                                        <span>链接类型：</span>
                                                                        <div class="app-select-bar">
                                                                            <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                            <ul class="app-select-box">
                                                                                <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                    <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">请选择</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                    <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品分类</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                    <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品品牌</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                    <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">单个商品</a>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                    <p>
                                                                        <span>链接内容：</span>
                                                                        <template v-if="item.action == 'GoodsDetail'">
                                                                            <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                        </template>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            <a class="delete_item" href="javascript:;" v-on:click="deleteImg($parent.$index, $index)">×</a>
                                                        </li>

                                                    </ul>
                                                </div>
                                                <a href="javascript:;" class="btn btn_green" onClick="handleSubmit(this, 'floors', {{$index}})">保存</a>
                                                <label style="vertical-align:-2px; margin-left:10px; font-weight:500;">建议尺寸 左（360*322）右（360*160）下（720*200）</label>
                                            </div>
                                        </div>
                                    </div>

                                </template>

                                <template v-if="e.template == '2'">
                                    <!-- 模板2 -->
                                    <div class="cont_box">
                                        <div class="floor">
                                            <h2>{{e.text}}</h2>
                                            <div class="spelling_ads type2 row">
                                                <div class="col-12">
                                                    <a href="javascript:;">
                                                        <img alt="" v-bind:src="e.adverts[0].img" height="200">
                                                    </a>
                                                </div>
                                                <div class="col-12 row">
                                                    <a href="javascript:;">
                                                        <img alt="" v-bind:src="e.adverts[1].img" height="100">
                                                    </a>
                                                    <div class="col-12">
                                                        <a href="javascript:;">
                                                            <img alt="" v-bind:src="e.adverts[2].img" height="100">
                                                        </a>
                                                    </div>
                                                    <div class="col-12">
                                                        <a href="javascript:;">
                                                            <img alt="" v-bind:src="e.adverts[3].img" height="100">
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                            <template v-if="e.banners && e.banners.length > 0">
                                                <swipe class="swiper-container banner_box">
                                                    <swipe-item v-for="item in e.banners" class="swiper-slide">
                                                        <img alt="" v-bind:src="item.img" />
                                                    </swipe-item>
                                                </swipe>
                                            </template>
                                        </div>

                                        <div class="edit_btns" floor-index={{$index}}>
                                            <a href="javascript:;" class="edit">编辑</a>
                                            <a href="javascript:;" class="delete" v-on:click="removeModule($index)">删除</a>
                                        </div>

                                        <div class="edit_area">
                                            <s></s>
                                            <a href="javascript:;" class="close">×</a>
                                            <div class="edit_form">
                                                <div class="form_item">
                                                    <label>楼层标题：</label>
                                                    <input type="text" v-model="e.text">
                                                </div>
                                                <ul class="img_list">

                                                    <li v-bind:class="{'item_error': item.error}" v-for="item in e.adverts">
                                                        <div class="img_item">
                                                            <span class="num">{{$index + 1}}</span>
                                                            <img alt="" v-bind:src="item.img" />
                                                            <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].adverts['+ $index +']')">重新上传</a>
                                                            <div class="img_link">
                                                                <div>
                                                                    <span>链接类型：</span>
                                                                    <div class="app-select-bar">
                                                                        <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                        <ul class="app-select-box">
                                                                            <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">请选择</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品分类</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品品牌</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">单个商品</a>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                                <p>
                                                                    <span>链接内容：</span>
                                                                    <template v-if="item.action == 'GoodsDetail'">
                                                                        <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                    </template>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </li>

                                                </ul>
                                                <div class="img_sample">
                                                    <a href="javascript:;" class="show_sample">查看图片广告示例 <i class="icon-angle-right"></i></a>
                                                    <div style="display:none">
                                                        <img alt="" src="<%=basePath%>images/appsite/img_type3_sample.jpg">
                                                    </div>
                                                </div>
                                                <div class="add_img_box">
                                                    <a href="javascript:;" class="add_img" v-on:click="addImage($index)">[+]添加一个轮播图片</a>
                                                    <ul class="img_list">

                                                        <li v-bind:class="{'item_error': item.error}" v-for="item in e.banners">
                                                            <div class="img_item">
                                                                <span class="num">{{$index + 1}}</span>
                                                                <img alt="" v-bind:src="item.img">
                                                                <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].banners['+ $index +']')">重新上传</a>
                                                                <div class="img_link">
                                                                    <div>
                                                                        <span>链接类型：</span>
                                                                        <div class="app-select-bar">
                                                                            <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                            <ul class="app-select-box">
                                                                                <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                    <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">请选择</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                    <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品分类</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                    <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品品牌</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                    <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">单个商品</a>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                    <p>
                                                                        <span>链接内容：</span>
                                                                        <template v-if="item.action == 'GoodsDetail'">
                                                                            <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                        </template>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            <a class="delete_item" href="javascript:;" v-on:click="deleteImg($parent.$index, $index)">×</a>
                                                        </li>

                                                    </ul>
                                                </div>
                                                <a href="javascript:;" class="btn btn_green" onClick="handleSubmit(this, 'floors', {{$index}})">保存</a>
                                                <label style="vertical-align:-2px; margin-left:10px; font-weight:500;">建议尺寸 左（360*322）右（360*160）右下（178*160）下（720*200）</label>
                                            </div>
                                        </div>
                                    </div>
                                </template>

                                <template v-if="e.template == '3'">
                                    <!-- 模板3 -->
                                    <div class="cont_box">
                                        <div class="floor">
                                            <h2>{{e.text}}</h2>
                                            <div class="theme_box">
                                                <div class="top row">
                                                    <div class="col-12">
                                                        <a href="javascript:;">
                                                            <img alt="" v-bind:src="e.adverts[0].img" height="127" />
                                                        </a>
                                                    </div>
                                                    <div class="col-12">
                                                        <a href="javascript:;">
                                                            <img alt="" v-bind:src="e.adverts[1].img" height="127" />
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="net row">
                                                    <div v-for="item in e.adverts" v-if="$index > 1" class="col-8">
                                                        <a href="javascript:;">
                                                            <img alt="" v-bind:src="item.img" height="124" />
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                            <template v-if="e.banners && e.banners.length > 0">
                                                <swipe class="swiper-container banner_box">
                                                    <swipe-item v-for="item in e.banners" class="swiper-slide">
                                                        <img alt="" v-bind:src="item.img" />
                                                    </swipe-item>
                                                </swipe>
                                            </template>
                                        </div>

                                        <div class="edit_btns" floor-index={{$index}}>
                                            <a href="javascript:;" class="edit">编辑</a>
                                            <a href="javascript:;" class="delete" v-on:click="removeModule($index)">删除</a>
                                        </div>

                                        <div class="edit_area">
                                            <s></s>
                                            <a href="javascript:;" class="close">×</a>
                                            <div class="edit_form">
                                                <div class="form_item">
                                                    <label>楼层标题：</label>
                                                    <input type="text" v-model="e.text">
                                                </div>
                                                <ul class="img_list">

                                                    <li v-bind:class="{'item_error': item.error}" v-for="item in e.adverts">
                                                        <div class="img_item">
                                                            <span class="num">{{$index + 1}}</span>
                                                            <img alt="" v-bind:src="item.img">
                                                            <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].adverts['+ $index +']')">重新上传</a>
                                                            <div class="img_link">
                                                                <div>
                                                                    <span>链接类型：</span>
                                                                    <div class="app-select-bar">
                                                                        <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                        <ul class="app-select-box">
                                                                            <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">请选择</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品分类</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品品牌</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">单个商品</a>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                                <p>
                                                                    <span>链接内容：</span>
                                                                    <template v-if="item.action == 'GoodsDetail'">
                                                                        <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                    </template>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </li>

                                                </ul>
                                                <div class="img_sample">
                                                    <a href="javascript:;" class="show_sample">查看图片广告示例 <i class="icon-angle-right"></i></a>
                                                    <div style="display:none">
                                                        <img alt="" src="<%=basePath%>images/appsite/img_type4_sample.jpg">
                                                    </div>
                                                </div>

                                                <div class="add_img_box">
                                                    <a href="javascript:;" class="add_img" v-on:click="addImage($index)">[+]添加一个轮播图片</a>
                                                    <ul class="img_list">

                                                        <li v-bind:class="{'item_error': item.error}" v-for="item in e.banners">
                                                            <div class="img_item">
                                                                <span class="num">{{$index + 1}}</span>
                                                                <img alt="" v-bind:src="item.img">
                                                                <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].banners['+ $index +']')">重新上传</a>
                                                                <div class="img_link">
                                                                    <div>
                                                                        <span>链接类型：</span>
                                                                        <div class="app-select-bar">
                                                                            <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                            <ul class="app-select-box">
                                                                                <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                    <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">请选择</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                    <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品分类</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                    <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品品牌</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                    <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">单个商品</a>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                    <p>
                                                                        <span>链接内容：</span>
                                                                        <template v-if="item.action == 'GoodsDetail'">
                                                                            <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                        </template>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            <a class="delete_item" href="javascript:;" v-on:click="deleteImg($parent.$index, $index)">×</a>
                                                        </li>

                                                    </ul>
                                                </div>
                                                <a href="javascript:;" class="btn btn_green" onClick="handleSubmit(this, 'floors', {{$index}})">保存</a>
                                                <label style="vertical-align:-2px; margin-left:10px; font-weight:500;">建议尺寸 上（330*200）下（239*200）</label>
                                            </div>
                                        </div>
                                    </div>
                                </template>

                                <template v-if="e.template == '4'">
                                    <!-- 模板4 -->
                                    <div class="cont_box">
                                        <div class="floor">
                                            <h2>{{e.text}}</h2>
                                            <div class="brands_box row">
                                                <div class="left_brand col-9">
                                                    <a href="javascript:;">
                                                        <img alt="" v-bind:src="e.adverts[0].img" />
                                                    </a>
                                                </div>
                                                <div class="brands_cont col-6">
                                                    <div class="brands_list">
                                                        <a v-for="item in e.adverts" v-if="$index > 0 && $index < 5" href="javascript:;">
                                                            <img alt="" v-bind:src="item.img" height="38" />
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="right_brand col-9">
                                                    <a href="javascript:;"><img alt="" v-bind:src="e.adverts[5].img" height="105" /></a>
                                                    <a href="javascript:;"><img alt="" v-bind:src="e.adverts[6].img" height="105" /></a>
                                                </div>
                                            </div>
                                        </div>
                                        <template v-if="e.banners && e.banners.length > 0">
                                            <swipe class="swiper-container banner_box">
                                                <swipe-item v-for="item in e.banners" class="swiper-slide">
                                                    <img alt="" v-bind:src="item.img" />
                                                </swipe-item>
                                            </swipe>
                                        </template>

                                        <div class="edit_btns" floor-index={{$index}}>
                                            <a href="javascript:;" class="edit">编辑</a>
                                            <a href="javascript:;" class="delete" v-on:click="removeModule($index)">删除</a>
                                        </div>

                                        <div class="edit_area">
                                            <s></s>
                                            <a href="javascript:;" class="close">×</a>
                                            <div class="edit_form">
                                                <div class="form_item">
                                                    <label>楼层标题：</label>
                                                    <input type="text" v-model="e.text">
                                                </div>
                                                <ul class="img_list">

                                                    <li v-bind:class="{'item_error': item.error}" v-for="item in e.adverts">
                                                        <div class="img_item">
                                                            <span class="num">{{$index + 1}}</span>
                                                            <img alt="" v-bind:src="item.img">
                                                            <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].adverts['+ $index +']')">重新上传</a>
                                                            <div class="img_link">
                                                                <div>
                                                                    <span>链接类型：</span>
                                                                    <div class="app-select-bar">
                                                                        <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                        <ul class="app-select-box">
                                                                            <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">请选择</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品分类</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品品牌</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">单个商品</a>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                                <p>
                                                                    <span>链接内容：</span>
                                                                    <template v-if="item.action == 'GoodsDetail'">
                                                                        <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                    </template>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </li>

                                                </ul>
                                                <div class="img_sample">
                                                    <a href="javascript:;" class="show_sample">查看图片广告示例 <i class="icon-angle-right"></i></a>
                                                    <div style="display:none">
                                                        <img alt="" src="<%=basePath%>images/appsite/img_type5_sample.jpg">
                                                    </div>
                                                </div>

                                                <div class="add_img_box">
                                                    <a href="javascript:;" class="add_img" v-on:click="addImage($index)">[+]添加一个轮播图片</a>
                                                    <ul class="img_list">

                                                        <li v-bind:class="{'item_error': item.error}" v-for="item in e.banners">
                                                            <div class="img_item">
                                                                <span class="num">{{$index + 1}}</span>
                                                                <img alt="" v-bind:src="item.img">
                                                                <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].banners['+ $index +']')">重新上传</a>
                                                                <div class="img_link">
                                                                    <div>
                                                                        <span>链接类型：</span>
                                                                        <div class="app-select-bar">
                                                                            <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                            <ul class="app-select-box">
                                                                                <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                    <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">请选择</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                    <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品分类</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                    <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品品牌</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                    <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">单个商品</a>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                    <p>
                                                                        <span>链接内容：</span>
                                                                        <template v-if="item.action == 'GoodsDetail'">
                                                                            <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                        </template>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            <a class="delete_item" href="javascript:;" v-on:click="deleteImg($parent.$index, $index)">×</a>
                                                        </li>

                                                    </ul>
                                                </div>
                                                <a href="javascript:;" class="btn btn_green" onClick="handleSubmit(this, 'floors', {{$index}})">保存</a>
                                                <label style="vertical-align:-2px; margin-left:10px; font-weight:500;">建议尺寸 左（226*320）中（208*81）右（226*155）</label>
                                            </div>
                                        </div>
                                    </div>
                                </template>

                                <template v-if="e.template == '5'">
                                    <!-- 模板5 -->
                                    <div class="cont_box">
                                        <div class="floor">
                                            <h2>{{e.text}}</h2>
                                            <div class="top_discover row">
                                                <div v-for="item in e.adverts" v-if="$index < 3" class="col-8">
                                                    <a href="javascript:;"><img v-bind:src="item.img" alt="" height="129"></a>
                                                </div>
                                            </div>

                                            <ul class="discover_list row">
                                                <li v-bind:class="{'item_error': item.error}" v-for="item in e.adverts" v-if="$index > 2" class="col-12">
                                                    <div class="dis_item_cont">
                                                        <a href="javascript:;">
                                                            <img v-bind:src="item.img" alt="" height="190">
                                                            <p class="item_title">{{item.text}}</p>
                                                        </a>
                                                        <p class="item_price">&yen; {{item.price}}</p>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                        <template v-if="e.banners && e.banners.length > 0">
                                            <swipe class="swiper-container banner_box">
                                                <swipe-item v-for="item in e.banners" class="swiper-slide">
                                                    <img alt="" v-bind:src="item.img" />
                                                </swipe-item>
                                            </swipe>
                                        </template>

                                        <div class="edit_btns" floor-index={{$index}}>
                                            <a href="javascript:;" class="edit">编辑</a>
                                            <a href="javascript:;" class="delete" v-on:click="removeModule($index)">删除</a>
                                        </div>

                                        <div class="edit_area">
                                            <s></s>
                                            <a href="javascript:;" class="close">×</a>
                                            <div class="edit_form">
                                                <div class="form_item">
                                                    <label>楼层标题：</label>
                                                    <input type="text" v-model="e.text">
                                                </div>
                                                <ul class="img_list">
                                                    <li v-bind:class="{'item_error': item.error}" v-for="item in e.adverts" v-if="$index < 3">
                                                        <div class="img_item">
                                                            <span class="num">{{$index + 1}}</span>
                                                            <img alt="" v-bind:src="item.img">
                                                            <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].adverts['+ $index +']')">重新上传</a>
                                                            <div class="img_link">
                                                                <div>
                                                                    <span>链接类型：</span>
                                                                    <div class="app-select-bar">
                                                                        <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText" class="app-selected-text">请选择</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                        <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                        <ul class="app-select-box">
                                                                            <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || item.actionParam.searchParam.searchText}">
                                                                                <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">请选择</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品分类</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品品牌</a>
                                                                            </li>
                                                                            <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">单个商品</a>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                                <p>
                                                                    <span>链接内容：</span>
                                                                    <template v-if="item.action == 'GoodsDetail'">
                                                                        <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                    </template>
                                                                    <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                        <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                    </template>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <div class="img_sample">
                                                    <a href="javascript:;" class="show_sample">查看图片广告示例 <i class="icon-angle-right"></i></a>
                                                    <div style="display:none">
                                                        <img alt="" src="<%=basePath%>images/appsite/img_type6_sample.jpg">
                                                    </div>
                                                </div>
                                                <div class="add_img_box">
                                                    <a href="javascript:;" class="add_img" onClick="addGood({{$index}})">[+]添加商品</a>
                                                    <ul class="img_list">
                                                        <li v-bind:class="{'item_error': item.error}" v-for="item in e.adverts" v-if="$index > 2">
                                                            <div class="img_item">
                                                                <span class="num">{{$index - 2}}</span>
                                                                <img alt="" v-bind:src="item.img">
                                                                <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].adverts['+ $index +']')">重新上传</a>
                                                                <div class="img_link">
                                                                    <div>
                                                                        <span>链接类型：</span>
                                                                        <div class="app-select-bar">
                                                                            <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || (item.actionParam.searchParam && item.actionParam.searchParam.searchText)" class="app-selected-text">请选择</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                            <ul class="app-select-box">
                                                                                <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || (item.actionParam.searchParam && item.actionParam.searchParam.searchText)}">
                                                                                    <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">请选择</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                    <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品分类</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                    <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">商品品牌</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                    <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].adverts['+ {{$index}} + ']')">单个商品</a>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                    <p>
                                                                        <span>链接内容：</span>
                                                                        <template v-if="item.action == 'GoodsDetail'">
                                                                            <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                        </template>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            <a class="delete_item" href="javascript:;" v-on:click="deleteGood($parent.$index, $index)">×</a>
                                                        </li>
                                                    </ul>
                                                </div>

                                                <div class="add_img_box" style="border-top:none">
                                                    <a href="javascript:;" class="add_img" v-on:click="addImage($index)">[+]添加一个轮播图片</a>
                                                    <ul class="img_list">

                                                        <li v-bind:class="{'item_error': item.error}" v-for="item in e.banners">
                                                            <div class="img_item">
                                                                <span class="num">{{$index + 1}}</span>
                                                                <img alt="" v-bind:src="item.img">
                                                                <a href="javascript:;" class="img_edit" v-on:click="chooseImage('floors['+$parent.$index+'].banners['+ $index +']')">重新上传</a>
                                                                <div class="img_link">
                                                                    <div>
                                                                        <span>链接类型：</span>
                                                                        <div class="app-select-bar">
                                                                            <strong v-if="!item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || (item.actionParam.searchParam && item.actionParam.searchParam.searchText)" class="app-selected-text">请选择</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates" class="app-selected-text">商品分类</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands" class="app-selected-text">商品品牌</strong>
                                                                            <strong v-if="item.actionParam && item.actionParam.goodsInfoId" class="app-selected-text">单个商品</strong>
                                                                            <ul class="app-select-box">
                                                                                <li v-bind:class="{'selected': !item.action || item.action == '' || !item.actionParam || (!item.actionParam.searchParam && !item.actionParam.goodsInfoId) || (item.actionParam.searchParam && item.actionParam.searchParam.searchText)}">
                                                                                    <a href="javascript:;" onClick="chooseAction(0, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">请选择</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.cates}">
                                                                                    <a href="javascript:;" onClick="chooseAction(1, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品分类</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.searchParam && item.actionParam.searchParam.brands}">
                                                                                    <a href="javascript:;" onClick="chooseAction(2, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">商品品牌</a>
                                                                                </li>
                                                                                <li v-bind:class="{'selected': item.actionParam && item.actionParam.goodsInfoId}">
                                                                                    <a href="javascript:;" onClick="chooseAction(3, 'floors['+{{$parent.$index}}+'].banners['+ {{$index}} + ']')">单个商品</a>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                    <p>
                                                                        <span>链接内容：</span>
                                                                        <template v-if="item.action == 'GoodsDetail'">
                                                                            <span><font color='blue'>{{item.actionParam.goodsInfoId}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.cates}}</font></span>
                                                                        </template>
                                                                        <template v-if="item.actionParam && item.actionParam.searchParam">
                                                                            <span><font color='blue'>{{item.actionParam.searchParam.brands}}</font></span>
                                                                        </template>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            <a class="delete_item" href="javascript:;" v-on:click="deleteImg($parent.$index, $index)">×</a>
                                                        </li>

                                                    </ul>
                                                </div>
                                                <a href="javascript:;" class="btn btn_green" onClick="handleSubmit(this, 'floors', {{$index}})">保存</a>
                                                <label style="vertical-align:-2px; margin-left:10px; font-weight:500;">建议尺寸 上（240*240）商品（360*360）</label>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </template>

                            <!-- 编辑 -->
                            <div class="ad_blocks clearfix">
                                <div class="blocks_item">
                                    <input type="button" class="btn btn_green add_box type0" v-on:click="addRushGroupBuy()" value="抢购/团购"/>
                                    <img src="<%=basePath%>images/appsite/rush_group_buy.jpg" alt="">
                                </div>
                                <div class="blocks_item">
                                    <input type="button" class="btn btn_green add_box type1" v-on:click="addTemplate01()" value="楼层(类型一)"/>
                                    <img src="<%=basePath%>images/appsite/template01.jpg" alt="">
                                </div>
                                <div class="blocks_item">
                                    <input type="button" class="btn btn_green add_box type2" v-on:click="addTemplate02()" value="楼层(类型二)"/>
                                    <img src="<%=basePath%>images/appsite/template02.jpg" alt="">
                                </div>
                                <div class="blocks_item">
                                    <input type="button" class="btn btn_green add_box type3" v-on:click="addTemplate03()" value="楼层(类型三)"/>
                                    <img src="<%=basePath%>images/appsite/template03.jpg" alt="">
                                </div>
                                <div class="blocks_item">
                                    <input type="button" class="btn btn_green add_box type4" v-on:click="addTemplate04()" value="楼层(类型四)"/>
                                    <img src="<%=basePath%>images/appsite/template04.jpg" alt="">
                                </div>
                                <div class="blocks_item">
                                    <input type="button" class="btn btn_green add_box type5" v-on:click="addTemplate05()" value="楼层(类型五)"/>
                                    <img src="<%=basePath%>images/appsite/template05.jpg" alt="">
                                </div>
                            </div>

                        </div>

                        <div class="save_template">
                            <input type="hidden" name="appData" />
                            <input type="button" id="saveBtn" class="btn btn_green" value="保存配置" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<script src="<%=basePath %>js/bootstrap.min.js"></script>
<script src="<%=basePath %>js/summernote.min.js"></script>
<script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
<script src="<%=basePath %>js/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath %>js/common.js"></script>
<script src="<%=basePath %>js/common/common_alert.js"></script>

<script src="<%=basePath %>js/appsite/vue.js"></script>
<script src="<%=basePath %>js/appsite/vue-swipe.js"></script>
<script src="<%=basePath %>js/appsite/jquery-ui.js"></script>
<script src="<%=basePath %>js/appsite/dialog-min.js"></script>
<script src="<%=basePath %>js/appsite/app.js"></script>
<script type="text/javascript">
    $(function(){
        //点击保存配置,提交数据
        $("#saveBtn").click(function(){
            // 参数校验
            if(vm.sliders){
                var hasErr = false;
                vm.sliders.forEach(function(e, i){
                    if(!e.img || e.img == "") {
                        hasErr = true;
                        return;
                    }
                });
                if(hasErr){
                    art.dialog.alert('顶部轮播图不允许存在为空的图片！');
                    return;
                }
            }
            if(vm.adverts){
                var hasErr = false;
                vm.adverts.forEach(function(e, i){
                    if(!e.img || e.img == "") {
                        hasErr = true;
                        return;
                    }
                });
                if(hasErr){
                    art.dialog.alert('顶部广告区不允许存在为空的图片！');
                    return;
                }
            }
            if(vm.floors){
                var hasErr = false;
                var errMsg = '';
                vm.floors.forEach(function(floor, idx){
                    var text = floor.text;
                    if(floor.adverts){
                        floor.adverts.forEach(function(e, i){
                            if(!e.img || e.img == "") {
                                hasErr = true;
                                errMsg = '["'+text+'"]楼层广告区不允许存在为空的图片！';
                                return;
                            }
                        });
                    }
                    if(hasErr){return;}
                    if(floor.banners){
                        floor.banners.forEach(function(e, i){
                            if(!e.img || e.img == "") {
                                hasErr = true;
                                errMsg = '["'+text+'"]楼层轮播区不允许存在为空的图片！';
                                return;
                            }
                        });
                    }
                });
                if(hasErr){
                    art.dialog.alert(errMsg);
                    return;
                }
            }

            if(vm.floors && vm.floors.length < 1){
                art.dialog.alert('至少需要包含1个楼层');
                return;
            }

            $("#saveBtn").prop('disabled', true);
            art.dialog.confirm('保存后将影响app首页的展现，请确认是否保存？', function(){
                var dataStr = JSON.stringify(vm.$data);
                console.log('-----save ----', dataStr);

                $.ajax({
                    url: '<%=basePath%>saveAppIndex.htm?CSRFToken=${token}',
                    context: document.body,
                    method: 'post',
                    data: dataStr,
                    contentType: 'application/json',
                    // processData: false,
                    // dataType: 'json',
                    success:function(resp){
                        console.log('---success-->', resp);
                        if(resp === 'success'){
                            alert('保存成功');
                            window.document.location = './setAppHomePage.htm';
                        }else{
                            alert('保存失败，请稍后再试！');
                        }
                        $("#saveBtn").prop('disabled', false);
                    },
                    error: function(resp){
                        console.log('----fail--->', resp);
                        alert('保存失败，请稍后再试！');
                        $("#saveBtn").prop('disabled', false);
                    }
                });
            }, function(){
                $("#saveBtn").prop('disabled', false);
                // art.dialog.tips('你取消了操作');
            });
        });
    });

    //添加商品
    function addGood(floorIndex){
        productChooseWindow('floors['+floorIndex+'].adverts', 10);
    }

    //修改Action
    function chooseAction(val, dataPath) {
        console.log('---chooseAction--->', val, dataPath);

        //action变化时，先清空actionParam
        vm.$set(dataPath + '.actionParam', {});

        if(val === 0){
            vm.$set(dataPath + '.action', '');
        }else if(val === 1){
            vm.$set(dataPath + '.action', 'GoodsList');
            catChooseWindow(dataPath);
        }else if(val === 2){
            vm.$set(dataPath + '.action', 'GoodsList');
            brandChooseWindow(dataPath);
        }else if(val === 3){
            vm.$set(dataPath + '.action', 'GoodsDetail');
            productChooseWindow(dataPath);
        }else if(val === 4) {
            vm.$set(dataPath + '.action', 'Theme1');
        }
    }

    //图片选择window
    function imageChooseWindow(sourceId){
        art.dialog.open('<%=basePath%>queryImageManageByPbAndCidForChoose.htm?CSRFToken=${token}', {
            id: sourceId,
            lock: true,
            width: '800px',
            height: '400px',
            title: '选择图片',
            top:'30%',
            fixed:true
        });
    }
    // 商品选择window
    function productChooseWindow(sourceId, maxChoose){
        maxChoose = maxChoose || 1;
        art.dialog.open('<%=basePath%>queryMobProductForGoods.htm?CSRFToken=${token}&size=' + maxChoose, {
            id: sourceId,
            lock: true,
            width: '900px',
            height: '400px',
            title: '选择商品',
            top:'30%',
            fixed: true
        });

    }

    // 分类选择window
    function catChooseWindow(sourceId){
        art.dialog.open('<%=basePath%>queryMobCateBarForChoose.htm?CSRFToken=${token}', {
            id: sourceId,
            lock: true,
            width: '400px',
            height: '400px',
            title: '选择分类',
            top:'30%',
            fixed: true
        });
    }

    // 品牌选择window
    function brandChooseWindow(sourceId){
        art.dialog.open('<%=basePath%>queryAllBrandForChoose.htm?CSRFToken=${token}', {
            id: sourceId,
            lock: true,
            width: '800px',
            height: '400px',
            title: '选择品牌',
            top:'30%',
            fixed: true
        });
    }

    // 保存选择图片
    function saveChoooseImage(path, sourceId){
        console.log('------save image-----', path, sourceId);

        vm.$set(sourceId + '.img', jQuery.isArray(path) ? path[0] : path);
    }

    // 保存选择的商品信息
    function saveChoooseProduct(ids, imgs, nos, names, titles, prices, sourceId){
        console.log('-----save product-----', ids, imgs, nos, names, titles, prices, sourceId);
        var floors=sourceId.match(/floors\[\d\]/ig)+"";
        var floors_index=floors.match(/\d/ig);
        var adverts=sourceId.match(/adverts\[\d\]/ig)+"";
        var adverts_index=adverts.match(/\d/ig);
        if(sourceId.endsWith('adverts')){ //添加商品
            var advertsData = JSON.parse(JSON.stringify(vm.$get(sourceId)||[]));
            var goodsLength = vm.$get(sourceId).length;
            for(var i=0; i<ids.length; i++){
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
        }else if(sourceId.match(/adverts\[\d\]/ig) && adverts_index>2 &&  vm.$get("floors["+floors_index+"].template")=="5"){
            vm.$set(sourceId + '.id',ids[0]);
            vm.$set(sourceId + '.text', names[0]);
            vm.$set(sourceId + '.img', imgs[0]);
            vm.$set(sourceId + '.action',"GoodsDetail");
            vm.$set(sourceId + '.ordering', adverts_index[0]);
            vm.$set(sourceId + '.price', prices[0]);
            vm.$set(sourceId + '.actionParam', {'goodsInfoId': ids[0]});
        }else{
            vm.$set(sourceId + '.actionParam', {'goodsInfoId': ids[0]});
        }

    }

    // 保存选择的分类信息
    function saveChoooseCat(cats, sourceId){
        console.log('-----save cat-----', cats, sourceId);
        vm.$set(sourceId + '.actionParam', {"searchParam": { "cates": [cats[0].text]}});
    }

    // 保存选择的品牌信息
    function saveChoooseBrand(brands, sourceId){
        console.log('-----save brand-----', brands, sourceId);
        vm.$set(sourceId + '.actionParam', {"searchParam": { "brands": [brands[0].name]}});
    }
</script>
</body>
</html>
