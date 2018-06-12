<!DOCTYPE html>
<html>
<head>
 <#assign basePath=request.contextPath>
  <meta charset="UTF-8">
  <title>会员中心 - 我的账户</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta content="telephone=no" name="format-detection">
  <link rel="stylesheet" href="${basePath}/css/style.css"/>
  <script src="${basePath}/js/jquery-1.10.1.js"></script>
  <script src="${basePath}/js/idangerous.swiper.js"></script>
    <script src="${basePath}/js/customer/mycollections.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery.zclip.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery.zclip.js"></script>
</head>
<body>

<div class="member_info">
    <input type="hidden" id="basePath" value="">
  <dl>
    <dt>用户名</dt>
    <dd>
      <a href="#">
        <span>${cust.customerUsername!''}</span>
      </a>
    </dd>
  </dl>
  <dl>
    <dt>会员级别</dt>
    <dd>
      <a href="#">
        <span class="label">${cust.pointLevelName!''}</span>
      </a>
    </dd>
  </dl>
  <dl>
    <dt>昵称</dt>
    <dd>
      <a href="${basePath}/changenickname.html">
        <span>${cust.customerNickname!''}</span>
        <i class="ion-ios-arrow-right"></i>
      </a>
    </dd>
  </dl>
  <dl>
    <dt>姓名</dt>
    <dd>
      <a href="${basePath}/changename.html">
        <span>${cust.infoRealname!''}</span>
        <i class="ion-ios-arrow-right"></i>
      </a>
    </dd>
  </dl>
  <dl>
    <dt>性别</dt>
    <dd>
      <a href="javascript:;" class="gender_set">
        <span><#if cust.infoGender??>
                <#switch cust.infoGender>
                <#case '0'>
                                                                保密
                <#break>
                <#case '1'>
                                                               男
                <#break>
                <#case '2'>
                                                                女
                <#break>
                <#default>
                </#switch>
              </#if>
        </span>
        <i class="ion-ios-arrow-right"></i>
      </a>
    </dd>
  </dl>
  <dl>
    <dt>手机号码</dt>
    <dd>
      <a href="<#--${basePath}/phonevalidate1.html-->javascript:;">
        <span><#if cust.infoMobile??&&cust.infoMobile!=''>${cust.infoMobile!''}<#else>未绑定</#if></span>
        <#--<i class="ion-ios-arrow-right"></i>-->
      </a>
    </dd>
  </dl>
  <dl>
    <dt>QQ号码</dt>
    <dd>
      <a href="${basePath}/changeqq.htm">
        <span>${cust.infoQQ!''}</span>
        <i class="ion-ios-arrow-right"></i>
      </a>
    </dd>
  </dl>
    <dl>
        <dt>推荐注册</dt>
        <dd>
            <a href="javascript:;" class="recommend_register">
                <i class="ion-ios-arrow-right"></i>
                <input type="hidden" id="customer_id" value="${customerId!''}">
            </a>
        </dd>
    </dl>
</div>

<div class="member_info">
  <dl>
    <dt>收货地址管理</dt>
    <dd>
      <a href="${basePath}/addresslist.html">
        <i class="ion-ios-arrow-right"></i>
      </a>
    </dd>
  </dl>
  <dl>
    <dt>账号安全</dt>
    <dd>
      <a href="${basePath}/accountsafe.html">
        <i class="ion-ios-arrow-right"></i>
      </a>
    </dd>
  </dl>
  <#--
  <dl>
    <dt>账号绑定</dt>
    <dd>
      <a href="${basePath}/phonevalidate4.htm">
        <i class="ion-ios-arrow-right"></i>
      </a>
    </dd>
  </dl>
  -->
</div>

<div class="p10 mb50">
  <a href="#" class="btn btn-full" id="loginout">退出当前登录</a>
</div>

<div class="gender_choose" style="display:none;">
  <a href="${basePath}/changegender.html?gender=1" class="btn btn-full-grey">
    <i class="iconfont icon-unie040"></i> 男
  </a>
  <a href="${basePath}/changegender.html?gender=2" class="btn btn-full-grey">
    <i class="iconfont icon-unie041"></i> 女
  </a>
  <a href="${basePath}/changegender.html?gender=0" class="btn btn-full-grey">
    <i class="iconfont icon-mibao"></i> 保密
  </a>
  <a href="javascript:;" class="btn btn-full gender_close">取消</a>
</div>

<div class="recommend_choose" style="display:none;">
    <div class="member_form">
            <dl>
                <dt>选中复制</dt>
                <dd>
                    <input type="text" id="content" readonly="readonly">
                </dd>
            </dl>
            <p class="help_block" style="display:none;" id="nickName_msg"></p>
            <p class="help_block">亲！如果您的好友成功注册会员，会有积分送给您哦！</p>
    </div>
    <a href="javascript:;" class="btn btn-full recommend_close">取消</a>
</div>
<#include '/common/smart_menu.ftl' />
<script>
  $(function(){

    /* 选择性别 */
    $('.gender_set').click(function(){
      if($('.opacity-bg-3').length == 0){
          $('body').append('<div class="opacity-bg-3"></div>');
          $('.gender_choose').show();
      }
    });

    /* 推荐注册 */
    $('.recommend_register').click(function(){
      var basePath = $('#basePath').val();
      var str = window.location.href;
      var b = str.indexOf('/',str.indexOf('/',str.indexOf('/')+2)+1);
    //		var a = str.indexOf("//")+2;
      var local = str.substring(0,b);
      var customerId = $('#customer_id').val();
      if(customerId==null||customerId==""){
      }else{
          //给会员id加密
          var b = new Base64();
          var result = 'customer_id='+customerId;
          var str = b.encode('"'+result+'"');
          var url = local+basePath+'/register.html?'+str;
          $('#content').val(url);
      }
      if($('.opacity-bg-3').length == 0){
          $('body').append('<div class="opacity-bg-3"></div>');
          $('.recommend_choose').show();
      }

        //复制成功弹出复制成功窗口
        $('#copy').zclip({
            path:'/js/ZeroClipboard.swf',
            copy:function(){
                return $("#content").html();
            },
            afterCopy:function(){
                $('.opacity-bg-3').remove();
                $('.recommend_choose').hide();
            }
        });
    });

    $('a.gender_close').click(function(){
    $('.opacity-bg-3').remove();
    $('.gender_choose').hide();
    });

    $('a.recommend_close').click(function(){
      $('.opacity-bg-3').remove();
      $('.recommend_choose').hide();
    });

  });


  $("#loginout").click(function(){
            window.location.href="${basePath}/logout.html";
        });
        
  $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");
</script>
</body>
</html>