<!DOCTYPE html>
<html>
<head>
    <#assign basePath=request.contextPath>
  <meta charset="UTF-8">
  <title>会员中心 - 我的账户</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.min.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
  <script src="${basePath}/js/idangerous.swiper.js"></script>
</head>
<body>

<form action="${basePath}/orderaddaddress.htm" id="addForm" method="post">
<div class="member_form">
    <input type="hidden" id="basePath"  value="${basePath}"/>
    <input type="hidden" name="flag" value="${flag!''}"/>
    <input type='hidden' id="provinceCh" name="infoProvince"/>
    <input type='hidden' id="cityCh" name="infoCity"/>
    <input type='hidden' id="countyCh" name="infoCounty"/>
  <dl>
    <dt>收货人</dt>
    <dd>
      <input type="text" class="text" name="addressName" id="addressName" placeholder="收货人"/>
    </dd>
  </dl>
  <dl>
    <dt>手机号码</dt>
    <dd>
      <input type="text" class="text" name="addressMoblie" id="addressMoblie" placeholder="手机号码"/>
    </dd>
  </dl>
  <dl>
    <dt>所在地区</dt>
    <dd>
      <a href="javascript:;" class="choose_area" onclick="loadProvice('');">
        <span class="local">请选择</span>
        <i class="ion-ios-arrow-right"></i>
      </a>
    </dd>
  </dl>
  <dl>
    <dt>详细地址</dt>
    <dd>
      <textarea rows="4" class="text" name="addressDetail" id="addressDetail" placeholder="详细地址"></textarea>
    </dd>
  </dl>
  <dl class="check_item">
    <dt>设为默认</dt>
    <dd>
      <div class="fr mr15">
        <div class="checkbox selected" id="defaultDiv">
          <input type="hidden" id="isDefault1" name="isDefault" value="1"/>
        </div>
      </div>
    </dd>
  </dl>
</div>
</form>
<div class="p10">
  <div class="col-12">
    <div class="pr15">
      <a href="javascript:;" class="btn btn-full-grey" onClick="javascript :history.back(-1);">取消</a>
    </div>
  </div>
  <div class="col-12">
    <div class="pl15">
      <a href="javascript:;" class="btn btn-full" onclick="checkForm()" style="line-height: inherit;">保存</a>
    </div>
  </div>
</div>

<div class="screen area-box-lv1" style="display:none;">
  <div class="screen-header">
    <a class="back" href="javascript:;" id="back">取消</a>
    请选择省
  </div>
  <div class="screen-cont">
    <div class="lists province">
      <dl id="prodl">
      </dl>
    </div>
  </div>
</div>

<div class="screen area-box-lv2" style="display:none;">
  <div class="screen-header">
    <a class="back" href="javascript:;" onclick="back1();"><i class="back-icon"></i></a>
    请选择市
  </div>
  <div class="current-area">
    已选择：
    <span id="readypro"></span>
  </div>
  <div class="screen-cont">
    <div class="lists city">
      <dl id="citydl">
      </dl>
    </div>
  </div>
</div>

<div class="screen area-box-lv3" style="display:none;">
  <div class="screen-header">
    <a class="back" href="javascript:;" onclick="back2();"><i class="back-icon"></i></a>
    请选择地区
  </div>
  <div class="current-area">
    已选择：
    <span id="readycity"></span>
  </div>
  <div class="screen-cont">
    <div class="lists district">
      <dl id="areadl">
      </dl>
    </div>
  </div>
</div>

<#include '/common/smart_menu.ftl' />

<script>

$(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");
  $(function(){

    /* 选择性别 */
    $('.gender_set').click(function(){
      if($('.opacity-bg-3').length == 0){
        $('body').append('<div class="opacity-bg-3"></div>');
        $('.gender_choose').show();
      }
    });
    $('a.gender_close').click(function(){
      $('.opacity-bg-3').remove();
      $('.gender_choose').hide();
    });

    /* 选择地区 */
    $('.choose_area').click(function(){
      $('body').append('<div class="opacity-bg-3"></div>');
      $('.area-box-lv1').show();
    });
    
    $('#back').click(function(){
      $('.opacity-bg-3').remove();
      $('.screen').hide();
      $("#provinceCh").val("");
    });
    
    /* 滑动选框 */
    $('.checkbox').click(function(){
      $(this).toggleClass('selected');
       if ($('#defaultDiv').hasClass('selected')) {
            $("#isDefault1").val('1');
      }else{
            $("#isDefault1").val('0');
      }
    });
  });
  //验证表单
      function checkForm(){
           var bl=true;
          //收件人姓名
          if( $("#addressName").val().trim()==''|| $("#addressName").val()==null){
               $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写收货人</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
              bl=false;
              return;
          }else if(! /^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test( $("#addressName").val())){
               $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>收货人不能包含特殊字符</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
              bl=false;
              return;
          }
          //收件人电话
          if($("#addressMoblie").val().trim()==''||$("#addressMoblie").val()==null){
              $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写手机号码</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
              bl=false;
              return;
          }else if(!(/^(13|14|15|18|17)\d{9}$/.test( $("#addressMoblie").val()))){
               $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>手机号格式错误</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
              bl=false;
              return;
          }
          if($(".local").html()=='请选择'){
             $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请选择地区</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
              bl=false;
              return;
          }
          //详细地址
          if($("#addressDetail").val().trim()=='' ||$("#addressDetail").val()==null){
               $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>请填写详细地址</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
              bl=false;
              return;
          }else if($("#addressDetail").val().trim().length>100){
               $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>详细地址请不要多于100字符</h3></div></div>');
                setTimeout(function(){
                    $('.tip-box').remove();
                },1000);
              bl=false;
              return;
          }
          if(bl){
              //添加收件地址
              $("#addForm").submit();
          }
      }
</script>
</body>
<script src="${basePath}/js/customer/myacountaddress.js"></script>
</html>