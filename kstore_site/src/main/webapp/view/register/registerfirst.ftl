<!DOCTYPE html>
<html>
<head lang="zh">
  <meta charset="UTF-8">
  <title>注册页-${topmap.systembase.bsetName}</title>
<#assign basePath=request.contextPath>
  <link rel="stylesheet" href="http://kstore.qianmi.com/index_seven/css/style.css">
  <link rel="stylesheet" href="${basePath}/css/iCheck/custom.css">
  <link rel="stylesheet" href="${basePath}/css/receive.m.css">
  <script src="${basePath}/js/jquery.js"></script>
  <script type="text/javascript" src="${basePath}/js/register.js"></script>
    <style>
        /* 弹窗 */
        .mask {width:100%; height:100%; background:#000; opacity:0.5; filter:alpha(opacity=50); position:fixed; top:0; left:0; z-index:9998; display:none;}
        .dialog {position:fixed; background:#fff; z-index:9999; width:440px; min-height:230px; padding:5px; display:none;}
        .dia_tit {height:30px; line-height:30px; padding:0 20px; font-family:microsoft YaHei; font-size:14px; color:#fff;}
        .dia_close {width:15px; height:15px; background:url(${basePath}/images/dia_close.png) no-repeat; margin-top:7px;}
        .dia_intro em {display:inline-block; font-family:microsoft YaHei; font-size:18px; color:#575757; -webkit-transform:rotate(-10deg); -moz-transform:rotate(-10deg);}
        .go_shopping, .go_pay {display:inline-block; zoom:1; *display:inline; width:100px; height:28px; text-align:center; line-height:27px; font-family:microsoft YaHei; font-size:14px; color:#fff!important; margin:0 5px;}
        .go_shopping {background:url(/kssite/images/grey_btn.gif) no-repeat;}
        .go_pay {background:url(/kssite/images/org_btn.gif) no-repeat;}
        .agreement_dia {width:910px; height:490px; border:5px solid rgba(238,238,238,.5); padding:0;}
        .agreement_dia .dia_tit {background:#eee; text-align:center; font-size:14px; font-weight:700; color:#666;}
        .agreement_dia .dia_close {position:absolute; top:8px; right:20px; margin-top:0; background:url(${basePath}/images/agree_close.gif) no-repeat;}
        .agreement_wp {height:360px; overflow-y:scroll; padding:0 20px;}
        .agreement_wp h4 {font-weight:700; line-height:180%;}
        .agreement_wp p {line-height:180%;}
        .agree_btn {display:inline-block; zoom:1; *display:inline; width:200px; height:30px; line-height:29px; background:url(${basePath}/images/agree_btn.gif) no-repeat; font-family:microsoft YaHei; font-size:16px; color:#fff!important;}
        .bluee{color:#005aa0;}
        #login_name{ width: 18px;height: 18px;background: url(images/user.png) no-repeat; position: absolute; top: 10px; left: 10px;}
        #login_code{ width: 18px;height: 22px;background: url(images/code.png) no-repeat; position: absolute; top: 8px; left: 10px;}
        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px white inset;
            -webkit-text-fill-color: #333;
        }
        .n_rg{ position: absolute; width: 74px; height: 38px; line-height: 38px; top:0px; left: 10px; color: #666; font-size: 14px;}
        .n_row .n_text {
            width: 100%;
            height: 36px;
            border: 1px solid #d3d3d3;
            border-radius: 3px;
            line-height: 36px;
            text-indent: 74px;
            *width:250px;
            *text-indent:0;
        }
        .n_row .form_tips {
            display: none;
            position: absolute;
            left: 0;
            top: 41px;
            height: 16px;
            line-height: 16px;
            padding-left: 25px;
            background: url(images/tips_icon.png) no-repeat left top;
            color: #969696;
        }
    </style>
</head>
<body class="grey_bg">
<div class="container">
    <input type="hidden"  id="basePath" value="${basePath}"/>
    <div class="mini_head">
    <h1 class="logo">
    <a href="${topmap.systembase.bsetAddress}">
        <img id="reg_pic" alt="" src="" style="height:40px;width:165px;"/>
    </a>
      <span>欢迎注册</span>
    </h1>
  </div>
  <p class="login_tip">
    我已经注册，马上
    <a href="${basePath}/login.html">登录 &gt;</a>
  </p>
  <div class="user_box">

    <div class="user_flow step3">
      <div class="step active">
        <i>1</i>
        <span>设置登录名</span>
      </div>
      <div class="step">
        <i>2</i>
        <span>设置用户信息</span>
      </div>
      <div class="step">
        <i>3</i>
        <span>注册成功</span>
      </div>
    </div>

    <div class="reg_form">
    <form action="${basePath}/registerFirst.htm" method="post" id="registerFirstForm">
      <input type="hidden" name="custId" id="custId"/>
      <div class="reg_form_item <#if mobileError??>error</#if>">
        <label class="for_text">手机号码</label>
        <div class="item">
          <input type="text" class="text long" placeholder="请输入手机号码" id="mobileInput" name="mobile">
          <i class="correct" style="display: none"></i>
          <p class="tips mobile_info" <#if mobileError??><#else>style="display: none"</#if>><#if mobileError??>${msg!''}<#else>请输入正确的手机号码</#if></p>
        </div>
      </div>
      <div class="reg_form_item <#if codeError??>error</#if>">
        <label class="for_text">验证码</label>
        <div class="item">
          <input type="text" class="text medium" placeholder="请输入图片验证码" name="checkCode" id="checkCode">
          <div class="vCode">
            <img id="checkCodeImg" src="${basePath}/patchca.htm" onclick="this.src=this.src+'?'+Math.random(); " alt="验证码">
            <a id="checkCodeA" href="javascript:void(0)">换一张</a>
          </div>
          <p class="tips code_info <#if codeError??>error</#if>" <#if codeError??><#else>style="display: none"</#if>><#if codeError??>${msg!''}<#else>请输入验证码</#if></p>
        </div>
      </div>
      <div class="reg_form_item">
        <label class="for_text">&nbsp;</label>
        <div class="item">
          <div class="agreement_confirm">
            <label>
              <input type="checkbox" class="i-checks" checked id="readme">
              我已阅读并同意<a href="javascript:void(0)" onclick="showpro();" id="protocol">《商城用户注册协议》</a>
            </label>
          </div>
            <p class="tips pro_info" style="display: none">请接受服务条款</p>
        </div>
      </div>
    </form>
      <div class="reg_form_item">
        <label class="for_text">&nbsp;</label>
        <div class="item">
          <button type="submit" class="sub_btn" onclick="submitFirst()">同意协议并注册</button>
        </div>
      </div>
    </div>

  </div>
</div>
<div class="mask" style="display:none"></div>
<div class="dialog dia1 agreement_dia" style="display:none">
    <div class="dia_tit">
    ${(topmap.systembase.bsetName)!''}用户协议
        <a class="dia_close" href="javascript:;" onclick="cls()"></a>
    </div><!--/dia_tit-->
    <div class="dia_cont">
        <div class="agreement_wp mt15">
        <#if (topmap.systembase.bsetUseragreement)??>
        ${(topmap.systembase.bsetUseragreement)!''}
        <#else>
            <h4>福气商城用户注册协议</h4>
            <p>本协议是用户（以下简称“您”）与福气商城网站（网址：包括但不限于demo.ningpai.com等，简称“本站”）所有者及其关联公司（以下简称为“福气商城”）之间就福气商城网站服务等相关事宜所订立的契约，请您仔细阅读本注册协议，您点击"同意并继续"按钮后，即视为您接受并同意遵守本协议的约定。</p>
            <h4>第1条 本站服务条款的确认和接纳</h4>
            <p>1.1本站的各项电子服务的所有权和运作权归福气商城所有。您同意所有注册协议条款并完成注册程序，才能成为本站的正式用户。您确认：本协议条款是处理双方权利义务的依据，始终有效，法律另有强制性规定或双方另有特别约定的，依其规定或约定。</p>
            <p>1.2您点击同意本协议的，即视为您确认自己具有享受本站服务、下单购物等相应的权利能力和行为能力，能够独立承担法律责任。</p>
            <p>1.3您确认，如果您在18周岁以下，您只能在父母或其他监护人的监护参与下才能使用本站。</p>
            <p>1.4福气商城保留在中华人民共和国大陆地区施行之法律允许的范围内独自决定拒绝服务、关闭用户账户、清除或编辑内容或取消订单的权利。</p>
            <p>1.5您使用本站提供的服务时，应同时接受适用于本站特定服务、活动等的准则、条款和协议（以下统称为“其他条款”）；如果以下使用条件与“其他条款”有不一致之处，则以“其他条款”为准。</p>
            <p>1.6为表述便利，商品和服务简称为“商品”或“货物”。</p>
            <h4>第2条 本站服务</h4>
            <p>2.1福气商城通过互联网依法为您提供互联网信息等服务，您在完全同意本协议及本站相关规定的情况下，方有权使用本站的相关服务。</p>
            <p>2.2您必须自行准备如下设备和承担如下开支：（1）上网设备，包括并不限于电脑或者其他上网终端、调制解调器及其他必备的上网装置；（2）上网开支，包括并不限于网络接入费、上网设备租用费、手机流量费等。</p>
            <p>2.2.1上网设备，包括并不限于电脑或者其他上网终端、调制解调器及其他必备的上网装置；</p>
            <h4>第1条 本站服务条款的确认和接纳</h4>
            <p>1.1本站的各项电子服务的所有权和运作权归福气商城所有。您同意所有注册协议条款并完成注册程序，才能成为本站的正式用户。您确认：本协议条款是处理双方权利义务的依据，始终有效，法律另有强制性规定或双方另有特别约定的，依其规定或约定。</p>
            <p>1.2您点击同意本协议的，即视为您确认自己具有享受本站服务、下单购物等相应的权利能力和行为能力，能够独立承担法律责任。</p>
            <p>1.3您确认，如果您在18周岁以下，您只能在父母或其他监护人的监护参与下才能使用本站。</p>
            <p>1.4福气商城保留在中华人民共和国大陆地区施行之法律允许的范围内独自决定拒绝服务、关闭用户账户、清除或编辑内容或取消订单的权利。</p>
            <p>1.5您使用本站提供的服务时，应同时接受适用于本站特定服务、活动等的准则、条款和协议（以下统称为“其他条款”）；如果以下使用条件与“其他条款”有不一致之处，则以“其他条款”为准。</p>
            <p>1.6为表述便利，商品和服务简称为“商品”或“货物”。</p>
            <h4>第2条 本站服务</h4>
            <p>2.1福气商城通过互联网依法为您提供互联网信息等服务，您在完全同意本协议及本站相关规定的情况下，方有权使用本站的相关服务。</p>
            <p>2.2您必须自行准备如下设备和承担如下开支：（1）上网设备，包括并不限于电脑或者其他上网终端、调制解调器及其他必备的上网装置；（2）上网开支，包括并不限于网络接入费、上网设备租用费、手机流量费等。</p>
            <p>2.2.1上网设备，包括并不限于电脑或者其他上网终端、调制解调器及其他必备的上网装置；</p>
        </#if>
        </div><!--/agreement_wp-->
        <div class="mt20 tc" style="text-align: center"><a class="agree_btn" href="javascript:;" onclick="agreepro();">同意并继续</a></div>
    </div><!--/dia_cont-->
</div>
<#if (topmap.temp)??>
    <#if (topmap.temp.tempId==1)>
        <#include "../index/bottom.ftl">

    <#else>
        <#include "../index/newbottom.ftl" />
    </#if>
</#if>


<script src="${basePath}/js/jquery.min.js"></script>
<script src="${basePath}/js/icheck.min.js"></script>
<script>
  $(function(){
    $(".i-checks").iCheck({
      checkboxClass:"icheckbox_square-green",
      radioClass:"iradio_square-green",
    });
      win();
      $(window).resize(function(){
          win();
      });

      $.ajax({
          url: 'loadlogo.htm',
          success: function(data){
              $("#reg_pic").prop("src",data.logo.bsetLogo);
          }
      });
  });
  function dia(n){
      $(".mask").fadeIn();
      $(".dia"+n).fadeIn();
  };
  function cls(){
      $(".dialog").fadeOut();
      $(".mask").fadeOut();
  };
  function showpro(){
      dia(1);
  }
  function win(){
      var _wd = $(window).width();
      var _hd = $(window).height();
      $(".dialog").css("top",(_hd - $(".dialog").height())/2).css("left",(_wd - $(".dialog").width())/2);
  };
  function agreepro(){
      $(".icheckbox_square-green").addClass("checked");
      cls();
  }

</script>

</body>
</html>