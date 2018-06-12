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
  <style>
      .address_list .address_item p {
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          text-overflow: ellipsis;
          overflow: hidden;
          display: block;
          height:22px;
          white-space: nowrap;
      }
  </style>
</head>
<body>
<div class="address_list">
   <#if addresses??>
       <input type="hidden" value="${addresses?size}" id="mynum">
    <#if addresses?size!=0>
     <#list addresses as address>
  <div class="address_item">
    <a href="${basePath}/toupdateAddress.html?addressId=${address.addressId}">
      <h4>
        <span>${address.addressName!''}</span>
        <span>${address.addressMoblie!''}</span>
      </h4>
      <p>
        <#if address.isDefault=='1'><span>【默认】</span></#if>
        ${address.province.provinceName!''}${address.city.cityName!''}${address.district.districtName!''}
        <#if address.street??>${address.street.streetName!''}</#if>${address.addressDetail!''}
      </p>
      <a href="javascript:;" class="edit_btn"><i class="ion-edit"></i></a>
    </a>
    <a href="${basePath}/delAddress.html?addressId=${address.addressId}" class="delete_btn">删除</a>
  </div>
   </#list>
   <#else>
        <div class="content" id="no">
      <div class="no_tips">
        <p>木有地址哦</p>
      </div>
    </div>
   </#if>
 </#if>
</div>

<div class="bottom-fixed p10 mb50">
  <a href="javascript:;" class="btn btn-full" onclick="mychecknum()">+ 新增收货地址</a>
</div>
<#include '/common/smart_menu.ftl' />
<script>
  $(function(){

      /* 编辑地址 */
      $('a.edit_btn').click(function(){
          if($(this).find('i').hasClass('ion-edit')){
              $(this).parent().animate({
                  marginLeft: '-77px'
              });
              $(this).find('i').attr('class','ion-arrow-return-right');
          }
          else{
              $(this).parent().animate({
                  marginLeft: 0
              });
              $(this).find('i').attr('class','ion-edit');
          }

      });


  });
  $(".bar-bottom a").removeClass("selected");
      $(".bar-bottom a:eq(3)").addClass("selected");


  function mychecknum() {
      var num = $("#mynum").val();
      if (num>=10) {
          $('body').append('<div class="tip-box" style="z-index:99999"><div class="tip-body"><h3>收货地址最多10个</h3></div></div>');
          setTimeout(function(){
              $('.tip-box').remove();
          },1000);
      }else
      {
          window.location.href ="${basePath}/toAddAddress.html";
      }

  }
</script>
</body>
</html>