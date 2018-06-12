/*
 *商品模块通用JS 
 */
/*商品筛选点击展示方式等*/
/**
 * todo
 * @param object
 */
function sort(object) {
  var curNode = $("#sort");

  var curValue = curNode.val();
  var toggleVal = $(object).attr("val");
  var nextVal;

  if (toggleVal != curValue) {
    nextVal = toggleVal;
  }
  else {
    // 2D -> 22D
    nextVal = toggleVal[0] + toggleVal;
  }

  curNode.val(nextVal);
  pageIndex = 0;
  fetchServerData();
}

function changeSearch(obj, objName, valu) {
  //如果点击的对象已经是当前选中的 就不进行操作
  if (objName == "searchSort") {
    var sort = $("#" + objName);
    if (sort.val() == "") {
      sort.val(valu);
    } else if (valu == "2D") {
      if (sort.val() == "2D") {
        sort.val("22D");
      } else {
        sort.val(valu);
      }
    } else if (valu == "22D") {
      if (sort.val() == "22D") {
        sort.val("2D");
      } else {
        sort.val(valu);
      }
    } else if (valu == "1D") {
      if (sort.val() == "1D") {
        sort.val("11D");
      } else {
        sort.val(valu);
      }
    } else if (valu == "11D") {
      if (sort.val() == "11D") {
        sort.val("1D");
      } else {
        sort.val(valu);
      }
    } else if (valu == "3D") {
      if (sort.val() == "3D") {
        sort.val("33D");
      } else {
        sort.val(valu);
      }
    } else if (valu == "33D") {
      if (sort.val() == "33D") {
        sort.val("3D");
      } else {
        sort.val(valu);
      }
    } else if (valu == "4D") {
      if (sort.val() == "4D") {
        sort.val("44D");
      } else {
        sort.val(valu);
      }
    } else if ($(this).attr("val") == "44D") {
      if (sort.val() == "44D") {
        sort.val("4D");
      } else {
        sort.val(valu);
      }
    }
    $("#pageBeanShowPage").val(1);

  }
  if (valu == "-1") {
    if (searchCheckShowStock.checked) {
      $("#" + objName).val(1);
    }
    else {
      $("#" + objName).val(0);
    }
    $("#pageBeanShowPage").val(1);

  } else if (valu == "-11") {
    $("#" + objName).val($(obj).val());
    $("#pageBeanShowPage").val(1);
  } else {
    if (objName != "searchSort") {
      $("#" + objName).val(valu);
    } else {
      if (!$(obj).hasClass("checked")) {
        $("#" + objName).val(valu);
      }
    }

  }
  //获取到参数列表中所有的选中的节点，放在隐藏域中，提交查询表单
  var params = $(".selected");
  for (var i = 0; i < params.length; i++) {
    if ($(params[i]).find("a").hasClass("param")) {
      $("#searchForm").append("<input type='hidden' name=params value='" + $(params[i]).find("a").attr("id") + "' />");
    }
  }
  $("#searchForm").submit();
}

//点击确定按钮事件
function confirmButton() {
  if ($("#priceMin").val() == "" && $("#priceMax").val() == "") {
    return;
  }
  $("#pageBeanShowPage").attr("value", "1");
  $("#searchForm").submit();
}



$(function () {
  /*加载面包屑信息*/
  $.get("../loadgoodsbreadcrumb/" + $(".bread_crumb_cat_id").val(), function (data) {

    if ($(".bread_crumb_cat_id").attr("data-role") == "list") {
      $(".index_url").after(">&nbsp;<span>" + data.catName + "</span>");
    } else {
      takeFirstCat(data);
      $(".index_url").after("&nbsp;>&nbsp;<a href='../list/" + data.catId + "-" + $(".first_catId").val() + ".html'>" + data.catName + "</a>");
    }
    loadBreadCrumb(data.parentCat);

    var cateId = $(".bread_crumb_cat_id").val();
    var firstcateId = $(".first_catId").val();
    //品牌的链接
    if ($(".aboutbrand").length > 0) {
      $(".aboutbrand").each(function () {
        var flag = $(this).attr("href");
        $(this).attr("href", flag + cateId + "-" + firstcateId + ".html?brands=" + $(this).text())
      });
    }

    //分类的链接
    if ($(".aboutcate").length > 0) {
      $(".aboutcate").each(function () {
        var flag = $(this).attr("href");
        $(this).attr("href", flag + "-" + firstcateId + ".html");
      });
    }
  });

  $(".info_tip_submit2").click(function () {
    cls();
  });


  //立即购买方法
  $(".go_buy").click(function () {
    if (parseFloat($(this).attr("product_stock")) <= 0) {
      /*初始化弹框样式*/
      $(".info_tip_content2").html("库存不足！");
      $(".info_tip_img2").attr("src", "../images/outstock.png");
      $(".info_tip_cancel2").attr("href", "javascript:;").hide();
      $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");

      dia(2);
      return 0;
    }

    var buy_num = parseFloat($(".product_buy_num").val());


    /*验证输入的购买数量大于库存*/
    if (buy_num > parseFloat($(this).attr("product_stock"))) {
      /*初始化弹框样式*/
      $(".info_tip_content2").html("购买数量超出剩余库存!!");
      $(".info_tip_img2").attr("src", "../images/outstock.png");
      $(".info_tip_cancel2").attr("href", "javascript:;").hide();
      $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
      dia(2);
      return 0;
    }


    if ($(this).attr("distinct_id") == "") {
      /*初始化弹框样式*/
      $(".info_tip_content2").html("请选择地区！");
      $(".info_tip_img2").attr("src", "../images/ch_distinct.png");
      $(".info_tip_cancel2").attr("href", "javascript:;").hide();
      $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
      dia(2);
      return 0;
    }
    var num = $(".product_buy_num").val();
    var productId = $(this).attr("product_id");
    var productStock = $(this).attr("product_stock");
    var params = "goodsNum=" + num;
    params += "&productId=" + productId;
    params += "&productStock=" + productStock;

    if ($(this).attr("distinct_id") > 0) {
      params += "&distinctId=" + $(this).attr("distinct_id");
    }
    /*请求加入购物车控制器*/
    $.post("../goods/addProductToShopCarNew.html?" + params, function (data) {
      if (data >= 1) {
        //加入购物车成功后
        window.location.href = "../order/newsuborder.html?box=" + data;
      } else if (data == -2) {
        /*初始化弹框样式*/
        $(".info_tip_title").html("立即结算");
        $(".info_tip_content").html("该抢购商品已达到限购次数！");
        $(".info_tip_img").attr("src", "../images/error.png");
        $(".info_tip_cancel").attr("href", "javascript:;").hide();
        $(".info_tip_submit").attr("href", "javascript:;").html("确定").show().unbind("click");
        $(".info_tip_submit").click(function () {
          cls();
        });
        dia(1);
      } else if (data == -1) {
        /*初始化弹框样式*/
        $(".info_tip_title").html("立即结算");
        $(".info_tip_content").html("立即结算失败,购物车达到上限");
        $(".info_tip_img").attr("src", "../images/error.png");
        $(".info_tip_cancel").attr("href", "javascript:;").hide();
        $(".info_tip_submit").attr("href", "javascript:;").html("确定").show().unbind("click");
        $(".info_tip_submit").click(function () {
          cls();
        });
        dia(1);
      } else if (data == 0) {
        /*初始化弹框样式*/
        $(".info_tip_title").html("立即结算");
        $(".info_tip_content").html("立即结算失败,请输入正确的数量");
        $(".info_tip_img").attr("src", "../images/error.png");
        $(".info_tip_cancel").attr("href", "javascript:;").hide();
        $(".info_tip_submit").attr("href", "javascript:;").html("确定").show().unbind("click");
        $(".info_tip_submit").click(function () {
          cls();
        });
        dia(1);
      } else if (data == -3) {
        /*初始化弹框样式*/
        $(".info_tip_content2").html("购买数量超出剩余库存!!");
        $(".info_tip_img2").attr("src", "../images/outstock.png");
        $(".info_tip_cancel2").attr("href", "javascript:;").hide();
        $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
        $(".info_tip_submit2").click(function () {
          cls();
        });
        dia(2);
        return 0;
      } else {
        /*初始化弹框样式*/
        $(".info_tip_title").html("立即结算");
        $(".info_tip_content").html("立即结算失败,请重试");
        $(".info_tip_img").attr("src", "../images/error.png");
        $(".info_tip_cancel").attr("href", "javascript:;").hide();
        $(".info_tip_submit").attr("href", "javascript:;").html("确定").show().unbind("click");
        $(".info_tip_submit").click(function () {
          cls();
        });
        dia(1);
      }
    });
  });

  $(".add-cart-btn").click(function () {
    $(".add_cart").click();
  });

  /*加入购物车绑定事件*/
  $(".add_cart").click(function () {
    if (parseFloat($(this).attr("product_stock")) <= 0) {
      /*初始化弹框样式*/
      $(".info_tip_content2").html("库存不足！");
      $(".info_tip_img2").attr("src", "../images/outstock.png");
      $(".info_tip_cancel2").attr("href", "javascript:;").hide();
      $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
      $(".info_tip_submit2").click(function () {
        cls();
      });
      dia(2);
      return 0;
    }

    var buy_num = parseFloat($(".product_buy_num").val());

    /*验证输入的购买数量不能超过99*/
    //if(buy_num > parseFloat(99)){
    //	/*初始化弹框样式*/
    //	$(".info_tip_content2").html("购买数量不能超过99!!");
    //	$(".info_tip_img2").attr("src","../images/outstock.png");
    //	$(".info_tip_cancel2").attr("href","javascript:;").hide();
    //	$(".info_tip_submit2").attr("href","javascript:;").show().html("确定").unbind("click");
    //	$(".info_tip_submit2").click(function(){
    //		cls();
    //	});
    //	dia(2);
    //	return 0;
    //}

    /*验证输入的购买数量大于库存*/
    if (buy_num > parseFloat($(this).attr("product_stock"))) {
      /*初始化弹框样式*/
      $(".info_tip_content2").html("购买数量超出剩余库存!!");
      $(".info_tip_img2").attr("src", "../images/outstock.png");
      $(".info_tip_cancel2").attr("href", "javascript:;").hide();
      $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
      $(".info_tip_submit2").click(function () {
        cls();
      });
      dia(2);
      return 0;
    }


    if ($(this).attr("distinct_id") == "") {
      /*初始化弹框样式*/
      $(".info_tip_content2").html("请选择地区！");
      $(".info_tip_img2").attr("src", "../images/ch_distinct.png");
      $(".info_tip_cancel2").attr("href", "javascript:;").hide();
      $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
      $(".info_tip_submit2").click(function () {
        cls();
      });
      dia(2);
      return 0;
    }
    var num = $(".product_buy_num").val();
    var productId = $(this).attr("product_id");
    var productStock = $(this).attr("product_stock");
    var params = "goodsNum=" + num;
    params += "&productId=" + productId;
    params += "&productStock=" + productStock;

    if ($(this).attr("distinct_id") > 0) {
      params += "&distinctId=" + $(this).attr("distinct_id");
    }
    /*请求加入购物车控制器*/
    $.post("../goods/addProductToShopCarNew.html?" + params, function (data) {
      if (data >= 1) {
        /*初始化弹框样式*/
        $(".info_tip_title").html("加入购物车");
        $(".info_tip_content").html("加入购物车成功！");
        $(".info_tip_img").attr("src", "../images/cart_img.png");
        $(".info_tip_cancel").attr("href", "javascript:;").html("继续购物").show();
        $(".info_tip_submit").attr("href", "../myshoppingcart.html").html("立即结算").show().unbind("click");
        $(".info_tip_cancel").click(function () {
          location.reload();
          cls();
        });
        dia(1);
        if ($(".dia1").find("a:first").css("background-image") != "none") {
          $(".dia1").find(".dia_close").html("");
        }
      } else if (data == -1) {
        /*初始化弹框样式*/
        $(".info_tip_title").html("加入购物车");
        $(".info_tip_content").html("加入购物车失败,购物车达到上限 ");
        $(".info_tip_img").attr("src", "../images/error.png");
        $(".info_tip_cancel").attr("href", "javascript:;").hide();
        $(".info_tip_submit").attr("href", "javascript:;").html("确定").show().unbind("click");
        $(".info_tip_submit").click(function () {
          cls();
        });
        dia(1);
        if ($(".dia1").find("a:first").css("background-image") != "none") {
          $(".dia1").find(".dia_close").html("");
        }
      } else if (data == 0) {
        /*初始化弹框样式*/
        $(".info_tip_title").html("加入购物车");
        $(".info_tip_content").html("加入购物车失败,请输入正确的数量 ");
        $(".info_tip_img").attr("src", "../images/error.png");
        $(".info_tip_cancel").attr("href", "javascript:;").hide();
        $(".info_tip_submit").attr("href", "javascript:;").html("确定").show().unbind("click");
        $(".info_tip_submit").click(function () {
          cls();
        });
        dia(1);
        if ($(".dia1").find("a:first").css("background-image") != "none") {
          $(".dia1").find(".dia_close").html("");
        }
      } else if (data == -3) {
        /*初始化弹框样式*/
        $(".info_tip_content2").html("购买数量超出剩余库存!!");
        $(".info_tip_img2").attr("src", "../images/outstock.png");
        $(".info_tip_cancel2").attr("href", "javascript:;").hide();
        $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
        $(".info_tip_submit2").click(function () {
          cls();
        });
        dia(2);
        return 0;
      } else {
        /*初始化弹框样式*/
        $(".info_tip_title").html("加入购物车");
        $(".info_tip_content").html("加入购物车失败,请重试");
        $(".info_tip_img").attr("src", "../images/error.png");
        $(".info_tip_cancel").attr("href", "javascript:;").hide();
        $(".info_tip_submit").attr("href", "javascript:;").html("确定").show().unbind("click");
        $(".info_tip_submit").click(function () {
          cls();
        });
        dia(1);
        if ($(".dia1").find("a:first").css("background-image") != "none") {
          $(".dia1").find(".dia_close").html("");
        }
      }
    });
  });

  //到货通知
  $(".arrival_notice").click(function () {

    var url = "../goods/loginArrivalNotice.html";
    $.ajax({
      type: 'post',
      url: url,
      async: false,
      success: function (data) {
        if (data == 1) {
          var infoMobile = $("#infoMobile").val("");
          var infoEmail = $("#infoEmail").val("");
          $(".infoMobile").hide();
          $(".infoEmail").hide();
          dia(22);
        }
        if (data == 0) {
          window.location.href = "/login.html";
        }
      }
    });
  });
  /*勾选组合中的货品的时候触发*/
  $(".check_group_product").change(function () {

    if ($(this).attr("checked")) {
      //获取当前选中的价格和市场价格
      var groupPrice = $(".group_prefer_price_" + $(this).attr("data-group")).text();
      var marketPrice = $(".group_market_price_" + $(this).attr("data-group")).text();
      //选中的货品的价格
      var productPrice = $(this).attr("data-price");
      var productNum = $(this).attr("data-sum");
      var proMarketPrice = $(this).attr("data-market");
      /*计算价格*/
      var totalPrice = parseFloat(groupPrice) + parseFloat(Number(productPrice) * Number(productNum));
      var totalMarket = parseFloat(proMarketPrice) + parseFloat(marketPrice);
      $(".group_prefer_price_" + $(this).attr("data-group")).text(totalPrice.toFixed(2));
      $(".group_market_price_" + $(this).attr("data-group")).text(totalMarket.toFixed(2));
    } else {
      //获取当前选中的价格和市场价格
      var groupPrice = $(".group_prefer_price_" + $(this).attr("data-group")).text();
      var marketPrice = $(".group_market_price_" + $(this).attr("data-group")).text();
      //选中的货品的价格
      var productPrice = $(this).attr("data-price");
      var productNum = $(this).attr("data-sum");
      var proMarketPrice = $(this).attr("data-market");
      /*计算价格*/
      var totalPrice = parseFloat(groupPrice) - parseFloat(Number(productPrice) * Number(productNum));
      var totalMarket = parseFloat(marketPrice) - parseFloat(proMarketPrice);
      $(".group_prefer_price_" + $(this).attr("data-group")).text(totalPrice.toFixed(2));
      $(".group_market_price_" + $(this).attr("data-group")).text(totalMarket.toFixed(2));
    }
  });

  /*点击购买优惠套装*/
  $(".buy_pre_group").click(function () {
    var fitId = $(this).attr("data-group");
    var distinctId = $(".ch_distinctId").val();
    var url = "../goods/buyPrePackage.html?fitId=" + fitId + "&distinctId=" + distinctId;
    $.ajax({
      type: 'post',
      url: url,
      async: false,
      success: function (data) {
        if (data > -2 && data <= 0) {
          /*初始化弹框样式*/
          $(".info_tip_content2").html("系统繁忙,请重试！");
          $(".info_tip_img2").attr("src", "../images/error.png");
          $(".info_tip_cancel2").attr("href", "javascript:;").hide();
          $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
          $(".info_tip_submit2").click(function () {
            cls();
          });
          dia(2);
        } else if (data <= -2) {
          /*初始化弹框样式*/
          $(".info_tip_content2").html("当前套装可购买的数量已达上限！");
          $(".info_tip_img2").attr("src", "../images/error.png");
          $(".info_tip_cancel2").attr("href", "javascript:;").hide();
          $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
          $(".info_tip_submit2").click(function () {
            cls();
          });
          dia(2);
        } else {
          /*初始化弹框样式*/
          $(".info_tip_title").html("加入购物车");
          $(".info_tip_content").html("加入购物车成功！");
          $(".info_tip_img").attr("src", "../images/cart_img.png");
          $(".info_tip_cancel").attr("href", "javascript:;").html("继续购物").show();
          $(".info_tip_submit").attr("href", "../myshoppingcart.html").html("立即结算").show().unbind("click");
          $(".info_tip_cancel").click(function () {
            cls();
          });
          dia(1);
        }
      }
    });
  });

  /*点击购买组合*/
  $(".buy_pak_group").click(function () {
    var groupId = $(this).attr("data-group");
    var goodsNum = $("#nowPro").val();
    var distinctId = $(".ch_distinctId").val();
    var products = $(".check_group_product_" + groupId);
    /*先购买当前商品*/
    var url = "../goods/addProductToShopCar.html?productId=" + $("#productId").val() + "&goodsNum=" + goodsNum + "&distinctId=" + distinctId;
    $.ajax({
      type: 'post',
      url: url,
      async: false,
      success: function (data) {
        if (!data) {
          /*初始化弹框样式*/
          $(".info_tip_content2").html("系统繁忙,请重试！");
          $(".info_tip_img2").attr("src", "../images/error.png");
          $(".info_tip_cancel2").attr("href", "javascript:;").hide();
          $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
          $(".info_tip_submit2").click(function () {
            cls();
          });
          dia(2);
          return false;
        }
      }
    });
    /*循环获取所有的选中的商品并添加到购物车*/
    for (var i = 0; i < products.length; i++) {
      if ($(products[i]).attr("checked")) {
        var url = "../goods/addProductToShopCar.html?productId=" + $(products[i]).val() + "&goodsNum=" + $(products[i]).attr("data-sum") + "&distinctId=" + distinctId;
        $.ajax({
          type: 'post',
          url: url,
          async: false,
          success: function (data) {
            if (!data) {
              /*初始化弹框样式*/
              $(".info_tip_content2").html("系统繁忙,请重试！");
              $(".info_tip_img2").attr("src", "../images/error.png");
              $(".info_tip_cancel2").attr("href", "javascript:;").hide();
              $(".info_tip_submit2").attr("href", "javascript:;").show().html("确定").unbind("click");
              $(".info_tip_submit2").click(function () {
                cls();
              });
              dia(2);
              return false;
            }
          }
        });
      }
    }
    $(".check_group_product").each(function () {
      $(this).attr("checked", false);
    });
    location.href = "../myshoppingcart.html";
  });
  /*/*搜索页加入购物车绑定事件,若更换搜索页,可删除*/
  $(".add_shop_cart").click(function () {
    var num = 1;
    var productId = $(this).attr("data-role");
    var params = "goodsNum=" + num;
    params += "&productId=" + productId;
    /*请求加入购物车控制器*/
    $.post("../goods/addProductToShopCar.html?" + params, function (data) {
      if (data) {
        /*初始化弹框样式*/
        $(".info_tip_title").html("加入购物车");
        $(".info_tip_content").html("加入购物车成功！");
        $(".info_tip_img").attr("src", "../images/cart_img.png");
        $(".info_tip_cancel").attr("href", "javascript:;").html("继续购物").show();
        $(".info_tip_submit").attr("href", "../myshoppingcart.html").html("立即结算").show().unbind("click");
        $(".info_tip_cancel").click(function () {
          cls();
        });
        dia(1);
      } else {
        /*初始化弹框样式*/
        $(".info_tip_title").html("加入购物车");
        $(".info_tip_content").html("加入购物车失败,请重试");
        $(".info_tip_img").attr("src", "../images/error.png");
        $(".info_tip_cancel").attr("href", "javascript:;").hide();
        $(".info_tip_submit").attr("href", "javascript:;").html("确定").show().unbind("click");
        $(".info_tip_submit").click(function () {
          cls();
        });
        dia(1);
      }
    });
  });
  /*初始化已经选择的城市*/
  loadInit();
});
/*初始化已经选择的城市*/
function loadInit() {
  $.ajax({
    type: 'post',
    url: "../getAllProvince.htm",
    async: false,
    success: function (data) {
      var provinceHtml = "";
      for (var i = 0; i < data.length; i++) {
        provinceHtml += "<li><a class='check_province'  onClick='loadCity(" + data[i].provinceId + ",this);' href='javascript:;'>" + data[i].provinceName + "</a></li>";
      }
      $(".province_list").html(provinceHtml);
    }
  });
  var provinces = $(".check_province");
  for (var i = 0; i < provinces.length; i++) {
    if ($(provinces[i]).html() == $(".ch_province").val()) {
      var click = $(provinces[i]).attr("onclick");
      var pid = click.substring(9, click.indexOf(","));
      var url = "../getAllCityByPid.htm?provinceId=" + pid;
      $.ajax({
        type: 'post',
        url: url,
        async: false,
        success: function (data) {
          var cityHtml = "";
          for (var i = 0; i < data.length; i++) {
            cityHtml += "<li><a class='check_city'  onClick='loadDistinct(" + data[i].cityId + ",this);' href='javascript:;'>" + data[i].cityName + "</a></li>";
          }
          $(".city_list").html(cityHtml);
        }
      });
    }
  }
  var citys = $(".check_city");
  for (var i = 0; i < citys.length; i++) {
    if ($(citys[i]).html() == $(".ch_city").val()) {
      var click = $(citys[i]).attr("onclick");
      var cid = click.substring(13, click.indexOf(","));
      var url = "../getAllDistrictByCid.htm?cityId=" + cid;
      $.ajax({
        type: 'post',
        url: url,
        async: false,
        success: function (data) {
          var distinctHtml = "";
          for (var i = 0; i < data.length; i++) {
            distinctHtml += "<li><a class='check_distinct'  onClick='checkDistinct(" + data[i].districtId + ",this);' href='javascript:;'>" + data[i].districtName + "</a></li>";
          }
          $(".distinct_list").html(distinctHtml);
        }
      });
    }
  }
}


/*根据点击的省份加载城市*/
function loadCity(pid, pro) {
  //$(".ch_address").val("");
  $(".ch_province").val($(pro).text());
  $(".province_text").text($(pro).text());
  $(".show_province").removeClass("cur");
  $(".show_city").addClass("cur");
  $(".city_text").click();
  var cityNamess;
  var cityIdss;

  $.ajax({
    url: "../getAllCityByPid.htm?provinceId=" + pid,
    type: "post",
    async: false,
    success: function (data) {
      var cityHtml = "";
      $(".ch_city").val(data[0].cityName);
      $(".city_text").text(data[0].cityName);
      cityNamess = data[0].cityName;
      cityIdss = data[0].cityId;
      for (var i = 0; i < data.length; i++) {
        cityHtml += "<li><a class='check_city'  onClick='loadDistinct(" + data[i].cityId + ",this);' href='javascript:;'>" + data[i].cityName + "</a></li>";
      }
      $(".city_list").html(cityHtml);

      $.ajax({
        url: "../getAllDistrictByCid.htm?cityId=" + cityIdss,
        type: "post",
        async: false,
        success: function (datas) {
          var distinctHtml = "";

          $(".ch_distinct").val(datas[0].districtName);
          $(".distinct_text").text(datas[0].districtName);
          /*当点击最后一级区县的时候   把之前选择的省份,城市和区县信息拼装成一个字符串并提交到后台控制器*/
          $(".ch_address").val($(".ch_province").val() + cityNamess + datas[0].districtName);
          $(".ch_distinctId").val(datas[0].districtId);

          for (var i = 0; i < datas.length; i++) {
            distinctHtml += "<li><a class='check_distinct'  onClick='checkDistinct(" + datas[i].districtId + ",this);' href='javascript:;'>" + datas[i].districtName + "</a></li>";
          }
          $(".distinct_list").html(distinctHtml);
        }
      });
    }
  });

}
/*根据点击的城市加载区县*/
function loadDistinct(cid, city) {
  $(".ch_city").val($(city).text());
  $(".city_text").text($(city).text());
  $(".show_city").removeClass("cur");
  $(".show_distinct").addClass("cur");
  $(".distinct_text").click();
  $.get("../getAllDistrictByCid.htm?cityId=" + cid, function (data) {
    var distinctHtml = "";
    for (var i = 0; i < data.length; i++) {
      distinctHtml += "<li><a class='check_distinct'  onClick='checkDistinct(" + data[i].districtId + ",this);' href='javascript:;'>" + data[i].districtName + "</a></li>";
    }
    $(".distinct_list").html(distinctHtml);
  });
}
/*点击区县的时候*/
function checkDistinct(dId, dis) {
  $(".ch_distinct").val($(dis).text());
  $(".distinct_text").text($(dis).text());
  /*当点击最后一级区县的时候   把之前选择的省份,城市和区县信息拼装成一个字符串并提交到后台控制器*/
  $(".ch_address").val($(".ch_province").val() + $(".ch_city").val() + $(".ch_distinct").val());
  $(".ch_distinctId").val(dId);
  $("#paramGoodsForm").submit();
}

function checkMobile() {
  var infoMobile = $("#infoMobile").val();
  if (!infoMobile) {
    $(".infoMobile").show();
    $(".infoMobile").html("请输入您的手机号码");
    return;
  }
  var re = /^1{1}[3,4,5,8,7]{1}\d{9}$/;
  if (!re.test(infoMobile)) {
    $(".infoMobile").show();
    $(".infoMobile").html("请输入正确的手机号码");
  } else {
    $(".infoMobile").hide();
    return;
  }
}

function checkEmail() {
  var infoEmail = $("#infoEmail").val();
  if (infoEmail) {
    var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    if (infoEmail.replace(/[^\x00-\xff]/g, "**").length <= 4 || !reg.test(infoEmail) || infoEmail.length > 50) {
      $(".infoEmail").show();
      $(".infoEmail").html("请输入正确的邮箱");
    } else {
      $(".infoEmail").hide();
    }
  }
}

/**
 添加到货通知
 */
function arrivalNotice() {
  var goodsInfoId = $("#productId").val();
  var infoMobile = $("#infoMobile").val();
  var infoEmail = $("#infoEmail").val();
  var districtId = $("#disId").val();
  if (!infoMobile) {
    $(".infoMobile").show();
    $(".infoMobile").html("请输入您的手机号码");
    return false;
  }
  var re = /^1{1}[3,4,5,8,7]{1}\d{9}$/;
  if (!re.test(infoMobile)) {
    $(".infoMobile").show();
    $(".infoMobile").html("请输入正确的手机号码");
    return false;
  }
  if (infoEmail) {
    var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    if (infoEmail.replace(/[^\x00-\xff]/g, "**").length <= 4 || !reg.test(infoEmail) || infoEmail.length > 50) {
      $(".infoEmail").show();
      $(".infoEmail").html("请输入正确的邮箱");
      return false;
    }
  }
  var datas = "1=1";
  datas += "&goodsInfoId=" + goodsInfoId;
  datas += "&infoMobile=" + infoMobile;
  datas += "&infoEmail=" + infoEmail;
  datas += "&districtId=" + districtId;
  jQuery.ajax({
    type: "post",
    url: "../goods/addArrivalNotice.html",
    data: datas,
    success: function (date) {
      //已存在
      if (date == 2) {
        $(".infoMobile").show();
        $(".infoMobile").html("您输入的手机已经被添加过到货通知了");
      }
      //已存在
      if (date == 3) {
        $(".infoEmail").show();
        $(".infoEmail").html("您输入的邮箱已经被添加过到货通知了");
      }
      //添加成功
      if (date == 1) {
        $(".infoMobile").hide();
        $(".infoMobile").hide();
        $("#infoMobile").html("");
        $("#infoEmail").html("");
        cls()
      }
      if (date == 4) {
        window.location.href = "/login.html";
      }
      //添加失败
      if (date == 0) {

      }
    }
  });
}

/*商品详情页点击规格值*/
var allProductList = new Array();
var tempList = new Array();
/**
 * 加载所属商品下的所有的货品并初始化规格值按钮
 * @param productList 货品列表
 */
function loadAllProduct(productList) {
  allProductList = productList;
  /*如果货品信息为空或者长度为0，设置所有的规格值为空*/
  if (allProductList == null || allProductList.length == 0) {
    $("._sku").addClass("disabled");
  }
  else {
    //控制规格值得按钮
    controlSpecBtn(allProductList, null, true, false);
  }
}
/*点击规格值的时候*/
function clickSpecDetail(obj, bool) {
  var self = $(obj);
  if (self.hasClass('disabled')) {
    return false;
  } else {
    //选中自己，兄弟节点取消选中
    self.addClass('selected').siblings().removeClass('selected');
  }
  /*删除保存的以选中记录,并添加新的*/
  $(".sel_spec_" + $(obj).attr("data-parent")).html("“" + $(obj).attr("title") + "”");

  self.append("<i></i>");
  var specList = $("._sku");
  var selSpecList = new Array();
  /*获取已经选中的规格值*/
  for (var i = 0; i < specList.length; i++) {
    if ($(specList[i]).hasClass("selected")) {
      selSpecList.push(specList[i]);
    }
  }
  var tempProductList = new Array();
  /*根据已经选中的规格值循环所有的货品筛选出可以被选中的货品*/
  var allRunCount = 0;
  for (var j = 0; j < allProductList.length; j++) {
    //获取到货品的关联的规格
    var goodsSpec = allProductList[j].specVo;
    var count = 0;
    //循环货品的规格去选中的规格中匹配
    for (var k = 0; k < goodsSpec.length; k++) {
      for (var i = 0; i < selSpecList.length; i++) {
        //如果当前循环的规格和循环到的货品的规格相匹配就给标记+1
        if (goodsSpec[k].goodsSpecDetail.specDetailId == $(selSpecList[i]).attr("data-value")) {
          count = count - 1 + 2;
        }
      }
    }
    /*如果匹配的数量大于等于已经选中的数量则说明完全匹配，跳转到货品详情页*/
    if (count >= selSpecList.length) {

      if ($("#allSpecLength").val() == count) {
        $("#paramGoodsForm").attr("action", allProductList[j].goodsInfoId + ".html");
        /*保存货品信息*/
        $("#paramGoodsForm").submit();
      }
      tempProductList.push(allProductList[j]);
    } else {
      //如果匹配的数量小于已经选中的数量则给标记+1
      allRunCount += 1;
    }
  }
  //如果标记等于所有的货品的长度说明没有匹配的货品，就保留当前选中的按钮，重新计算其他可选的按钮
  if (allRunCount == allProductList.length) {
    //选中自己，其他的全部取消选中
    $("._sku").removeClass('selected');
    /*self.removeClass("disable");*/
    //取消完所有的选中之后选中当前传递的对象
    self.addClass('selected');
    self.append("<i></i>");
    //再次调用当前的方法进行计算
    clickSpecDetail(self, false);
    //设置控制按钮的方法循环执行计算可选按钮的标记为true
    bool = true;
  }
  /*控制规格值按钮*/
  controlSpecBtn(tempProductList, obj, false, bool);
}
/*控制规格值按钮*/
function controlSpecBtn(tempProductList, obj, bool, checkRunAgain) {
  var specList = $("._sku");
  var canSelSpec = new Array();
  /*已经获取到匹配的货品取出所有的关联的规格值*/
  for (var i = 0; i < tempProductList.length; i++) {
    //循环可选择的货品的集合获取所有的可选择的规格按钮
    var goodsSpec = tempProductList[i].specVo;
    for (var k = 0; k < goodsSpec.length; k++) {
      canSelSpec.push(goodsSpec[k].goodsSpecDetail.specDetailId);
    }
  }
  /*控制页面中的按钮*/
  for (var i = 0; i < specList.length; i++) {
    var count = 0;
    //循环去可选按钮中匹配是否存在
    for (var k = 0; k < canSelSpec.length; k++) {
      if (canSelSpec[k] == $(specList[i]).attr("data-value")) {
        count = count - 1 + 2;
      }
    }
    //如果循环到的按钮匹配可选按钮的数量为0，则标记为不可选中，否则就是可选中
    if (count == 0) {
      if ($(".spec_name").length > 1) {
        $(specList[i]).addClass("disabled");
      } else {
        $(specList[i]).attr('style', 'display:none');
      }
      //$(specList[i]).remove();
      /*删除保存的以选中记录,并添加新的*/
    }
    else {
      $(specList[i]).removeClass("disabled");
    }
  }
  //如果传递过来的对象不为空并且，再次执行计算按钮的标记为真，并且方法标记为false时执行以下方法，重新计算可选择的按钮
  if (!bool && obj != null && checkRunAgain) {
    $(obj).removeClass("disabled");
    clickSpecDetail(obj, false);
  }
}
/*把已经选择的属性置为选中状态*/
function loadChooseParam() {
  var params = $(".chooseParamId");
  var specList = $("._sku");
  var spec = "";
  if (params.length > 0) {
    specList.each(function () {
      $(this).removeClass("selected");
    });
    for (var i = 0; i < params.length; i++) {
      for (var k = 0; k < specList.length; k++) {
        if ($(specList[k]).attr("data-value") == $(params[i]).val()) {
          $(specList[k]).addClass("selected");
          $(specList[k]).append("<i></i>");
          spec += "<b class=sel_spec_" + $(specList[k]).attr("data-parent") + ">“" + $(specList[k]).attr("title") + "”</b> , ";

        }
      }
    }
    if (spec.length > 1) {
      spec = spec.substring(0, spec.length - 2);
    }
    $(".check_param").append(spec);
  }
//	else{
//		if(allProductList.length>0){
//			 var goodsSpec = allProductList[0].specVo;
//			 for (var j= 0; j < goodsSpec.length; j++){
//				 for (var k = 0; k< specList.length; k++) {
//					 if (goodsSpec[j].goodsSpecDetail.specDetailId==$(specList[k]).attr("data-value")){
//						 $(specList[k]).addClass("selected");
//					 }
//				 }
//		    }
//		}
//	}
}


//自定义乘法函数
function myaccMul(arg1, arg2) {
  if (arg1 == null) {
    return;
  }
  if (arg2 == null) {
    return;
  }
  var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
  try {
    m += s1.split(".")[1].length;
  }
  catch (e) {
  }
  try {
    m += s2.split(".")[1].length;
  }
  catch (e) {
  }
  return (Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)).toFixed(3);


}

//乘法函数
function accMul(arg1, arg2) {
  if (arg1 == null) {
    return;
  }
  if (arg2 == null) {
    return;
  }
  var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
  try {
    m += s1.split(".")[1].length;
  }
  catch (e) {
  }
  try {
    m += s2.split(".")[1].length;
  }
  catch (e) {
  }
  return Math.round(Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m) * 100) / 100;
}

function newAccMul(arg1, arg2) {
  var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
  try {
    m += s1.split(".")[1].length
  } catch (e) {
  }
  try {
    m += s2.split(".")[1].length
  } catch (e) {
  }
  return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}

// 给Number类型增加一个newmul方法
Number.prototype.newmul = function (arg) {
  return newAccMul(arg, this);
}
/**
 * 加载商品的促销信息
 */
function loadGoodsMark() {
  $.get("../gmarket/" + $("#productId").val() + "-" + $("#brandId").val() + "-" + $("#catId").val() + ".html", function (data) {

    if (null == data || data.length <= 0) {

      if ($("#isCustomerDiscount").val() == '1') {
        $(".product_market").append("<em class='sales_label' id='cut'>会员折扣</em>");

      } else {
        $(".product_market").append("<em class='sales_label'>无</em>");
      }

      return;
    } else {
      var count = 0;
      for (var i = 0; i < data.length; i++) {
        if (data[i].codexType == 1 || data[i].codexType == 4 || data[i].codexType == 5 ||
          data[i].codexType == 8 || data[i].codexType == 13 || data[i].codexType == 14) {
          count++;
        }
      }
      //当存在两种及两种以上的促销时，加入购物车可以修改订单促销信息，所以这种情况引掉“立即购买”
      if (data.length > 1 && count > 1) {
        $(".go_buy").remove();
      }
      var num = 0;
      var count = 0;
      var isgroup = 0;
      for (var i = 0; i < data.length; i++) {
        var marketing = data[i];
        //直降促销
        if (marketing.codexType == 1) {

          $(".product_market").append("<em class='sales_label'>" + marketing.marketingName + "</em>");

          $(".product_market").append("<span class='promotion_info'>直降" + marketing.priceOffMarketing.offValue + "元</span><br/>");


        } else if (marketing.codexType == 15 && isgroup != 1) {
          //如果是折扣促销
          if (marketing.preDiscountMarketings != null && marketing.preDiscountMarketings.length != 0) {
            for (var j = 0; j < marketing.preDiscountMarketings.length; j++) {
              //获取折扣价格 获取最后一位
              var zkprice = (marketing.preDiscountMarketings[j].discountInfo).toString().substring(2, 3);
              if ($("#productId").val() == marketing.preDiscountMarketings[j].goodsId) {

                var discountFlag = marketing.preDiscountMarketings[j].discountFlag;

                var price = 0.00;
                price = myaccMul(marketing.preDiscountMarketings[j].discountInfo, $("#followPrice").val()).toString();
                //抹掉分
                if (discountFlag == '1') {
                  //不采用四舍五入
                  if (price.length - price.indexOf(".") + 1 >= 2) {
                    price = price.substring(0, price.indexOf(".") + 2) + "0";
                  }
                } else if (discountFlag == '2') {
                  if (price.length - price.indexOf(".") + 1 >= 1) {
                    price = price.substring(0, price.indexOf(".") + 1) + "00";
                  }
                } else {
                  if (price.length - price.indexOf(".") + 1 >= 3) {
                    price = price.substring(0, price.indexOf(".") + 3);
                  }
                }

                if (num == 0) {
                  $(".main_price").html("<span>￥ </span>" + price);
                  //  $("#zk_price").text("（"+zkprice+"折）");
                  $(".cxyj").show();
                }
                num++;

              }

            }

          }
          //满减促销
        } else if (marketing.codexType == 5) {

          if (marketing.fullbuyReduceMarketings != null && marketing.fullbuyReduceMarketings.length != 0) {

            $(".product_market").append("<em class='sales_label'>" + marketing.marketingName + "</em>");
            var str = "";
            for (var j = 0; j < marketing.fullbuyReduceMarketings.length; j++) {

              str += "满" + marketing.fullbuyReduceMarketings[j].fullPrice + "减" + marketing.fullbuyReduceMarketings[j].reducePrice + " , ";


            }
            if (str.length >= 2) {
              str = "<span class='promotion_info'>" + str.substring(0, str.length - 2) + "</span><br/>";
            }
            $(".product_market").append(str);
          }
          //满金额赠促销
        } else if (marketing.codexType == 6) {

          if (marketing.fullbuyPresentMarketings != null && marketing.fullbuyPresentMarketings.length != 0) {

            $(".product_market").append("<em class='sales_label'>" + marketing.marketingName + "</em>");
            var str = "";
            var scos = "";
            var all = "";
            for (var j = 0; j < marketing.fullbuyPresentMarketings.length; j++) {
              for (var k = 0; k < marketing.fullbuyPresentMarketings[j].fullbuyPresentScopes.length; k++) {
                scos += "<a target='_blank' href=" + $("#basePathVal").val() + "/item/" + marketing.fullbuyPresentMarketings[j].fullbuyPresentScopes[k].scopeId + ".html><img style='vertical-align:middle;' src=" + marketing.fullbuyPresentMarketings[j].fullbuyPresentScopes[k].goodsProduct.goodsInfoImgId + " width='24px' height='24px'></a>&nbsp;&nbsp;<span style='color: #FF0000'>X" + marketing.fullbuyPresentMarketings[j].fullbuyPresentScopes[k].scopeNum + "</span>&nbsp;";
              }
              var tp = "";
              if (marketing.fullbuyPresentMarketings[j].presentType == 0) {
                tp = "元"
              } else {
                tp = "件"
              }
              if (j != marketing.fullbuyPresentMarketings.length - 1) {
                str = "满" + marketing.fullbuyPresentMarketings[j].fullPrice + tp + "赠&nbsp;&nbsp;" + scos + "，";
              } else {
                str = "满" + marketing.fullbuyPresentMarketings[j].fullPrice + tp + "赠&nbsp;&nbsp;" + scos + "<span>（赠完即止）</span>";
              }
              scos = "";
              all += str;
            }
            //if (str.length >= 2) {
            //    str = "<span class='promotion_info'>" + str.substring(0, str.length - 2) + "</span><br/>";
            //}
            $(".product_market").addClass("w400");
            $(".product_market").append(all);
          }

        } else if (marketing.codexType == 8) {

          if (marketing.fullbuyDiscountMarketings != null && marketing.fullbuyDiscountMarketings.length != 0) {

            $(".product_market").append("<em class='sales_label'>" + marketing.marketingName + "</em>");

            var zhe = "";
            for (var j = 0; j < marketing.fullbuyDiscountMarketings.length; j++) {
              zhe += "满" + marketing.fullbuyDiscountMarketings[j].fullPrice + "打" + ((marketing.fullbuyDiscountMarketings[j].fullbuyDiscount * 100) / 10) + "折 , ";

            }
            if (zhe.length >= 2) {
              zhe = "<span class='promotion_info'>" + zhe.substring(0, zhe.length - 2) + "</span><br/>";
            }

            $(".product_market").append(zhe);
          }
        }
        //团购促销
        else if (marketing.codexType == 10 && count == 0) {
          $(".product_market").append("<em class='sales_label'>" + marketing.marketingName + "</em>");
          $(".product_market").append("<span class='promotion_info'>" + Number(marketing.groupon.grouponDiscount).newmul(10) + "折团购</span><br/>");
          $(".cxyj").show();
          $("#priceflr").html("团&nbsp;&nbsp;购&nbsp;&nbsp;价：");
          $(".main_price").html("<span>¥ </span>" + accMul(marketing.groupon.grouponDiscount, $("#mprice").val()));
          count++;
          isgroup = 1;
        }
        //抢购促销
        else if (marketing.codexType == 11) {
          $(".product_market").append("<em class='sales_label'>" + marketing.marketingName + "</em>");

          $(".product_market").append("<span class='promotion_info'>" + accMul(marketing.rushs[0].rushDiscount, 10) + "折抢购</span><br/>");
          $(".cxyj").show();
          $("#priceflr").html("抢&nbsp;&nbsp;购&nbsp;&nbsp;价：");
          $(".main_price").html("<span>¥ </span>" + accMul(marketing.rushs[0].rushDiscount, $("#mprice").val()));
        }


        else if (marketing.codexType == 12) {

          $(".baoyou").html("支持" + marketing.shippingMoney + "免运费");
        }

      }
    }
  });
}

/*取出第一级分类ID*/
function takeFirstCat(cat) {
  if (cat.parentCat != null && cat.parentCat.catName == "所有") {
    $(".first_catId").val(cat.catId);
  }
  if (cat.parentCat != null) {
    takeFirstCat(cat.parentCat);
  }
}

/*递归加载面包屑信息*/
function loadBreadCrumb(cat) {
  if (cat != null && cat.parentCat != null && cat.parentCat.catId == 0) {
    //$(".index_url").after("<em><a href='../list/jumplist.html?catId="+cat.catId+"&level=1'>"+cat.catName+"</a></em>");
    $(".index_url").after("<em><a href='../list/" + cat.catId + "-" + $(".first_catId").val() + ".html'>" + cat.catName + "</a></em>");
  } else if (cat != null && cat.catName != "所有") {
    $(".index_url").after("&nbsp;>&nbsp;<a href='../list/" + cat.catId + "-" + $(".first_catId").val() + ".html'>" + cat.catName + "</a>");
  }
  if (cat.parentCat != null) {
    loadBreadCrumb(cat.parentCat);
  }
}

