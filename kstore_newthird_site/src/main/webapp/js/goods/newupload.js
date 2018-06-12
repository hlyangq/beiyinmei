product_image = null;
$(function () {

    $("#dialog-about").hide();

    /* 加载第三方授权分类 */
    getAllGrandCateForThird();
    /* 加载品牌 */
    addGoodsBrand();
    /*绑定事件*/
    bindEvent();
});


/*保存商品*/
var num=0;
function up_goods_info(){
    var isProNoValid = true;
    var pro_box = $(".dinfo_wp .tab-pane");
    if (null != pro_box && pro_box.length > 0) {
        for (var j = 0; j < pro_box.length; j++) {
            var _pro = $(pro_box[j]);
            isProNoValid &= checkProNo(_pro.find(".no_input"));
            isProNoValid &= checkProBarcode(_pro.find(".barcode_input"));
        }
    }
    if (!isProNoValid || !checkProductForm() || !checkImage()) {
        return;
    }

    $(".save_goods_info").append("<input type='hidden' name='catId' value=" + $(".ch_goods_cate").val() + " >");
    var name = $($(".name_input")[0]).val();
    $(".save_goods_info").append("<input type='hidden' name='goodsName' value='" + name + "' >");
    $(".save_goods_info").append("<input type='hidden' name='goodsSubtitle' value=" + $($(".des_input")[0]).val() + " >");
    $(".save_goods_info").append("<input type='hidden' name='goodsNo' value=" + $($(".no_input")[0]).val() + " >");
    $(".save_goods_info").append("<input type='hidden' name='brandId' value=" + $("#goods_brand").val() + " >");
    $(".save_goods_info").append("<input type='hidden' name='goodsPrice' value=" + $($(".goodsPrice")[0]).val() + " >");
    $(".save_goods_info").append("<input type='hidden' name='goodsAdded' value="+$("input:radio[name='shelves']:checked").val() + " >");
    $(".save_goods_info").append("<input type='hidden' name='goodsImg' value=" + $($(".choose_pro_img")[0]).attr("src") + " >");
    /*$(".save_goods_info").append("<input type='hidden' name='goodsDeno' value="+$(".dw_input").val()+" >");*/
    $(".save_goods_info").append("<input type='hidden' name='goodsSeoTitle' value=" + name + " >");
    $(".save_goods_info").append("<input type='hidden' name='goodsSeoKeyword' value=" + name + " >");
    $(".save_goods_info").append("<input type='hidden' name='goodsSeoDesc' value=" + $($(".des_input")[0]).val() + " >");
    /* 第三方分类 */
    $(".save_goods_info").append(
        "<input type='hidden' name='goodsThirdCateId' value="
        + $(".ch_third_catid").val() + " >");
    var tags = $(".goods_tag:checked");
    if (null != tags && tags.length > 0) {
        for (var i = 0; i < tags.length; i++) {
            $(".save_goods_info").append("<input type='hidden' name='tags' value=" + $(tags[i]).val() + " >");
        }
    }

    var type_param = $(".type_param");
    if (null != type_param && type_param.length > 0) {
        for (var i = 0; i < type_param.length; i++) {
            $(".save_goods_info").append("<input type='hidden' name='paramId' value=" + $(type_param[i]).val() + " >");
            $(".save_goods_info").append("<input type='hidden' name='paramValue' value=" + $($(".type_param_val")[i]).val() + " >");
        }
    }
    var type_expand_param = $(".type_expand_param");
    if (null != type_expand_param && type_expand_param.length > 0) {
        for (var i = 0; i < type_expand_param.length; i++) {
            $(".save_goods_info").append("<input type='hidden' name='expandParamId' value=" + $(type_expand_param[i]).val() + " >");
            $(".save_goods_info").append("<input type='hidden' name='expandParamValue' value=" + $($(".type_expand_sel")[i]).val() + " >");
        }
    }
    var specs = $(".type_spec:checked");
    if (null != specs && specs.length > 0) {
        for (var i = 0; i < specs.length; i++) {
            $(".save_goods_info").append("<input type='hidden' name='specs' value=" + $(specs[i]).val() + " >");
        }
    }
    var specVal = $(".check_spec:checked");
    if (null != specVal && specVal.length > 0) {
        for (var i = 0; i < specVal.length; i++) {
            $(".save_goods_info").append("<input type='hidden' name='specsValue' value=" + $(specVal[i]).attr("spec_value_id") + "-" + $(specVal[i]).attr("spec_id") + " >");
            $(".save_goods_info").append("<input type='hidden' name='specsValueImg' value=" + $($(".up_spec_img_src")[i]).val() + " >");
            /*保存规格别名*/
            $(".save_goods_info").append("<input type='hidden' name='specsValueRemark' value=" + $($(specVal[i])).val() + " >");
        }
    }
    var ch_about = $(".ch_about:checked");
    if (null != ch_about && ch_about.length > 0) {
        for (var i = 0; i < ch_about.length; i++) {
            $(".save_goods_info").append("<input type='hidden' name='about' value=" + $(ch_about[i]).val() + " >");
        }
    }
    /*验证通过并且数据加载到隐藏域中,提交表单*/
    if(num==0){
        num+=1;
        ////只允许提交一次  就禁掉 onclick事件
        $(".info_sub").removeAttr("onclick");
        $(".save_goods_info").submit();
        $(".goods_desc").val($('.summernotedesc').code());
        $(".goods_mobile_desc").val($('.summernotemobile').code());
    }

}



function call_save_goods(data) {
    if (parseFloat(data) > 0) {
        /* 保存完基本信息之后保存详细介绍,完成之后保存货品信息 */
        $(".new_goods_id").val(data);
        $(".save_goods_desc").submit();
    } else {
        showNoDeleteConfirmAlert('出现异常,请重试!');
    }
}

/**
 * @param data : 商品ID
 * @param isThirdAuditUs: 平台商家商品审核开关
 */
function call_save_desc(data) {
    savePro(data);
}

/* 绑定事件 */
function bindEvent() {
    $(".qf_box:first").show();
    $(".spec_list li").each(function () {
    });
    $(".spec_list li").each(function (n) {
        var _this = $(this);
        _this.find("input[type='checkbox']").change(function () {
            if ($(this).prop("checked") == true) {
                $(".spec_box:eq(" + n + ")").show();
            } else {
                $(".spec_box:eq(" + n + ")").hide();
                $(".openSpecValue_" + $(this).val()).prop("checked", false);
                $(".spec_img_form_" + $(this).val()).remove();
            }
            ;
        });
    });
    $(".dw_box a").click(function () {
    });
    $(".dw_box a").click(function () {
        var _this = $(this);
        _this.parent().prev("input[type='text']").val(_this.text());
    });

    // $(".qf_box:first").show();
    // $(".prev_step").click(function(){});
    $(".prev_step").click(function () {
        /*
         * $("body,html").animate({ scrollTop: 0 },0);
         * $(".dinfo_tabs").html(""); $(".dinfo_wp").html("");
         * $(".qf_box:last").hide(); $(".qf_box:first").show();
         * $(".scg_dl").show();
         */
    });
    $(".sb_wp input[type='checkbox']").change(function () {
    });
    $(".sb_wp input[type='checkbox']").change(function () {
        $(".spec_box").each(function () {
            var _this = $(this);
            if (_this.find("input:checkbox[checked]").length > 0) {
                _this.attr("name", "ok");
            } else {
                _this.attr("name", "no");
            }
            ;
        });
    });
    $(".sb_wp input[type='checkbox']").change(function () {
    });
    $(".sb_wp input[type='checkbox']").change(function () {
        $(".new_box").each(function () {
            var _this = $(this);
            if (_this.find("input:checked").length > 0) {
                _this.attr("name", "ok");
            } else {
                _this.attr("name", "no");
            }
            ;
        });
    });
    // $("#create_gds").click(function(){});
    $("#create_gds").click(function () {
        $("body,html").animate({
            scrollTop: 0
        }, 0);
        // if(checkBase()){
        /*$(".name_input").val("");
         $(".des_input").val("");*/
        $(".dinfo_tabs").html("");
        $(".dinfo_wp").html("");
        test();
        $(".qf_box:first").hide();
        $(".scg_dl").hide();
        $(".qf_box:last").show();
        // }
    });

    /* 当选择规格图片的按钮发生改变时触发 */
    $(".spec_img").change(function () {
    });
    $(".spec_img").change(function () {
        $(".specValue_" + $(this).attr("spec_val_id")).submit();
    });

    /* 当选择货品图片的按钮发生改变时触发 */
    $(".up_pro_img").change(function () {
    });
    $(".up_pro_img").change(function () {
        product_image = $(this);
        $(product_image).parent().submit();
    });

    /* 选择图片 */
//	$(".choose_img_btn").click(function() {
//	});
    $(".choose_img_btn").click(function () {
        art.dialog.open('queryImageManageByChoose.htm?size=10000', {
            lock: true,
            width: '800px',
            height: '400px',
            title: '选择图片'
        });
    });
}


// 用于存放不满足图片大小的图片位置
var errorIds = new Array();

// 用于存放符合条件的图片url
var rightUrls = new Array();

// 加载图片
var imgLoad = function (url, callback) {

    var img = new Image();
    img.src = url;

    if (img.complete) {
        callback(url,img.width, img.height);
    } else {
        img.onload = function () {
            callback(url,img.width, img.height);
            img.onload = null;
        };
    };
};

// 批量加载图片
var imgLoadbath = function (url,urls, callback,currentIndex,allSize) {

    var img = new Image();
    img.src = url;

    if (img.complete) {
        callback(urls,img.width, img.height,currentIndex,allSize);
    } else {
        img.onload = function () {
            callback(urls,img.width, img.height,currentIndex,allSize);
            img.onload = null;
        };
    };
};



// 获得图片的宽度和长度回调 单张图片
function callSaveChoooseImage(url, width, height) {
    if (width < 350 || height < 350) {
        showTipAlert('货品的图片尺寸过小！建议尺寸350*350以上');
        return;
    }

    $(".dinfo_wp")
        .find(".active")
        .find(".choose_imgs")
        .append(
        '<li class="inline"><a class="del_ts" href="javascript:;" onclick="del_ts(this)"><i class="glyphicon glyphicon-remove-sign"></i></a><input type="hidden" class="choose_img" value=' + url + '><a href="javascript:;"><img alt="" class="choose_pro_img" src=' + url + ' width="100" height="100" /></a></li>');

    showConfirmAlert('要将这些图片应用到其他货品吗?', 'copyImageToOtherSpec(\'' + url + '\')');
}

// 获得图片的宽度和长度回调 多张图片
function callSaveChoooseImageBath(urls, width, height,currentIndex,allSize) {
    if (width < 350 || height < 350) {

        errorIds.push(currentIndex+1);
    }else
    {
        rightUrls.push(urls[currentIndex]);
    }

    // 判断自己是否是最后一个如果是最后一个 则上传图片 如果不是 则继续调用下一个判断图片
    if (currentIndex == allSize - 1) {

        if (errorIds.length == 0) {
            var paths = urls.toString().split(",");
            for (var i = 0; i < paths.length; i++) {
                var path3 = paths[i];
                $(".dinfo_wp")
                    .find(".active")
                    .find(".choose_imgs")
                    .append(
                    '<li class="inline"><a class="del_ts" href="javascript:;" onclick="del_ts(this)"><i class="glyphicon glyphicon-remove-sign"></i></a><input type="hidden" class="choose_img" value=' + path3 + '><a href="javascript:;"><img alt="" class="choose_pro_img" src=' + path3 + ' width="100" height="100" /></a></li>');
            }
            showConfirmAlert('要将这些图片应用到其他货品吗?', 'copyImageToOtherSpec(\'' + urls + '\')');
        } else {
            if (errorIds.length == urls.length)
            {
                showTipAlert('上传的所有图片尺寸过小！建议尺寸350*350以上');
            }else
            {
                for (var i = 0; i < rightUrls.length; i++) {
                    var path3 = rightUrls[i];
                    $(".dinfo_wp")
                        .find(".active")
                        .find(".choose_imgs")
                        .append(
                        '<li class="inline"><a class="del_ts" href="javascript:;" onclick="del_ts(this)"><i class="glyphicon glyphicon-remove-sign"></i></a><input type="hidden" class="choose_img" value=' + path3 + '><a href="javascript:;"><img alt="" class="choose_pro_img" src=' + path3 + ' width="100" height="100" /></a></li>');
                }
                showConfirmAlert('第'+errorIds+'张图片尺寸过小！建议尺寸350*350以上,要将符合的图片应用到其他货品吗?', 'copyImageToOtherSpec(\'' + rightUrls + '\')');
            }
        }

    }else
    {
        imgLoadbath(urls[currentIndex+1], urls, callSaveChoooseImageBath,currentIndex+1,allSize)
    }
}
// 保存选择的图片信息
function saveChoooseTrademark(path) {


    errorIds = new Array();

    rightUrls = new Array();
    if (path.toString().indexOf(",") > -1) {
        var paths = path.toString().split(",");
        imgLoadbath(paths[0],paths,callSaveChoooseImageBath,0,paths.length);
    } else {
        imgLoad(path, callSaveChoooseImage);
    }
}

function del_ts(obj) {
    $(obj).parent().remove();
}

/* 点击复制到全部名称 */
function copyAllSubTitle(obj) {
    $(".des_input").val($(obj).parent().find(".des_input").val());
}

function cp_all(t) {
    $(".choose_img_btn").unbind('click');
    var _t = 0;
    for (var i = 1; i < $(".dinfo_wp .dinfo_box").length; i++) {
        _t = $(t).parents(".dinfo_box").find("dl").clone();
        _t.find(".nameWp input[type='radio']").attr("name", "_rad" + i);
        _t.find(".customerDiscount input[type='radio']").attr("name", "_discount" + i);
        _t.find(".p_code").val("");
        $(".dinfo_wp .dinfo_box:eq(" + i + ") dl").remove();
        $(".dinfo_wp .dinfo_box:eq(" + i + ")").prepend(_t);
        $(t).parents(".dinfo_box").find(".sec_des").click();
    }
    ;
    $(".choose_img_btn").click(function () {
        art.dialog.open('queryImageManageByChoose.htm?size=10000', {
            lock: true,
            width: '800px',
            height: '400px',
            title: '选择图片'
        });
    });
}

function test() {
    /* 如果只选择开启了一个规格 */
    var ch_spec = $(".type_spec:checked");
    if (ch_spec.length == 1) {
        $(".dinfo_tabs").html("");
        $(".dinfo_wp").html("");
        var spec_id = $(ch_spec[0]).val();
        var spec_vals = $(".openSpecValue_" + spec_id);
        if (spec_vals.length > 0) {
            for (var i = 0; i < spec_vals.length; i++) {
                if ($(spec_vals[i]).prop("checked")) {
                    var _tabs = '<li role="presentation"';
                    _tabs += '><a href="#tab' + (i + 1) + '" aria-controls="tab' + (i + 1) + '" role="tab" data-toggle="tab">' + $(spec_vals[i]).val();
                    +'</a></li> ';
                    var hidearea = "<input type='hidden' class='product_spec' value=" + $(spec_vals[i]).attr("spec_id") + "-" + $(spec_vals[i]).attr("spec_value_id") + "-" + $(spec_vals[i]).val() + ">";
                    var ct = $(".demo_box .tab-pane").clone();
                    ct.removeClass("demo_box");
                    $(".dinfo_tabs").append(_tabs);

                    ct.attr("id", "tab" + (i + 1));
                    ct.append(hidearea);
                    $(".dinfo_wp").append(ct);

                    $("#tab"+(i+1)).find(".name_input").each(function(){
                        $(this).val($(this).val()+'('+$(spec_vals[i]).val()+')');
                    });

                    $("#tab" + (i + 1)).find(".batch_set_stock_ctrl").attr("spe_id", i + 1);
                    $("#tab" + (i + 1)).find(".do_batch_set_stock_ctrl").attr("spe_id", i + 1);
                    $("#tab" + (i + 1)).find(".cancel_batch_set_stock_ctrl").attr("spe_id", i + 1);
                    $("#tab" + (i + 1)).find(".copy_ware_to_other_spec_ctrl").attr("spe_id", i + 1);

                    $("#tab" + (i + 1)).find(".batch_set_price_ctrl").attr("spe_id", i + 1);
                    $("#tab" + (i + 1)).find(".do_batch_set_price_ctrl").attr("spe_id", i + 1);
                    $("#tab" + (i + 1)).find(".cancel_batch_set_price_ctrl").attr("spe_id", i + 1);

                    $("#tab" + (i + 1)).find(".batc_set_stock").attr("id", "batc_set_stock" + (i + 1));
                    $("#tab" + (i + 1)).find(".batch_set_price").attr("id", "batc_set_price" + (i + 1));

                    $("#tab" + (i + 1)).find(".batch_set_price_ctrl").attr("id", "batch_set_price_ctrl" + (i + 1));
                    $("#tab" + (i + 1)).find(".batch_set_stock_ctrl").attr("id", "batch_set_stock_ctrl" + (i + 1));
                    $("#tab" + (i + 1)).find(".do_batch_set_stock_ctrl").attr("id", "do_batch_set_stock_ctrl" + (i + 1));
                    $("#tab" + (i + 1)).find(".cancel_batch_set_stock_ctrl").attr("id", "cancel_batch_set_stock_ctrl" + (i + 1));
                    $("#tab" + (i + 1)).find(".do_batch_set_price_ctrl").attr("id", "do_batch_set_price_ctrl" + (i + 1));
                    $("#tab" + (i + 1)).find(".cancel_batch_set_price_ctrl").attr("id", "cancel_batch_set_price_ctrl" + (i + 1));

                    $('.productsImg').popover({
                        html: true,
                        content: '<div style="width:300px;">最低350*350px，建议800*800px</div>',
                        trigger: 'hover'
                    });


                    /*  ct.find(".nameWp input").attr("name","_rad"+ct.index());
                     ct.find(".customerDiscount input").attr("name","_discount"+ct.index());*/
                }
            }
        }
    } else {
        var empArray2 = new Array();
        var empArray3 = new Array();
        var emp = 0;
        var emp1 = $(".type_spec").length;
        $(".dinfo_tabs").html("");
        $(".dinfo_wp").html("");
        for (var i = 0; i < emp1; i++) {
            if ($("#emp" + i).attr("name") != "ok") {
                continue;
            }
            var arrayA = new Array();

            $("#emp" + i + "[name ='ok'] input:checked").each(
                function () {
                    arrayA.push($(this).val() + "-" + $(this).attr("spec_id") + "-"
                    + $(this).attr("spec_value_id") + "_"
                    + $(this).val() + "-");
                });

            // $("#emp" + i + "[name ='ok']").attr("name", "no");

            while ($("#emp" + (i + 1)).attr("name") == "no") {
                i++;
            }

            if (i < $(".type_spec").length - 1 && emp == 0) {
                emp = 1;
                i++;
                var arrayB = new Array();
                $("#emp" + i + "[name = 'ok'] input:checked").each(
                    function () {

                        arrayB.push($(this).val() + "-" + $(this).attr("spec_id") + "-"
                        + $(this).attr("spec_value_id") + "_"
                        + $(this).val() + "-");

                    });

                // $("#emp" + i + "[name ='ok']").attr("name", "no");

                for (var j = 0; j < arrayA.length; j++) {
                    var arrayAValue = arrayA[j];

                    for (var k = 0; k < arrayB.length; k++) {
                        var arrayBValue = arrayB[k];
                        empArray2.push(arrayAValue + arrayBValue);
                    }

                }

            } else {

                if (empArray2.length != 0 && empArray3.length == 0) {

                    for (var j = 0; j < arrayA.length; j++) {
                        var arrayAValue = arrayA[j];

                        for (var k = 0; k < empArray2.length; k++) {
                            var arrayBValue = empArray2[k];
                            empArray3.push(arrayAValue + arrayBValue);
                        }

                    }

                    empArray2 = new Array();
                } else {

                    for (var j = 0; j < arrayA.length; j++) {

                        var arrayAValue = arrayA[j];

                        for (var k = 0; k < empArray3.length; k++) {
                            var arrayBValue = empArray3[k];
                            empArray2.push(arrayAValue + arrayBValue);
                        }

                    }
                    empArray3 = new Array();
                }

            }
        }

        if (empArray2.length == 0) {
            for (var x = 0; x < empArray3.length; x++) {
                var titles = empArray3[x].split("-");
                var title = "";
                var hidearea = "";
                for (var i = 0; i < titles.length; i++) {
                    if (i == 0) {
                        title += titles[i];
                    } else if (i % 3 == 0) {
                        title += titles[i];
                        hidearea += "<input type='hidden' class='product_spec' value="
                        + titles[i - 2] + "-" + titles[i - 1].split("_")[0] + "-"
                        + titles[i - 1].split("_")[1] + ">";
                    }
                }
                var _tabs = '<li role="presentation"';
                _tabs += '><a href="#tab' + (x + 1) + '" aria-controls="tab' + (x + 1) + '" role="tab" data-toggle="tab">' + title + '</a></li> ';
                var ct = $(".demo_box .tab-pane").clone();
                ct.removeClass("demo_box");
                $(".dinfo_tabs").append(_tabs);
                ct.attr("id", "tab" + (x + 1));
                ct.append(hidearea);
                $(".dinfo_wp").append(ct);

                $("#tab"+(i+1)).find(".name_input").each(function(){
                    $(this).val($(this).val()+'('+title+')');
                });

                $("#tab" + (i + 1)).find(".copy_ware_to_other_spec_ctrl").attr("spe_id", i + 1);
                $("#tab" + (i + 1)).find(".batch_set_stock_ctrl").attr("spe_id", i + 1);
                $("#tab" + (i + 1)).find(".do_batch_set_stock_ctrl").attr("spe_id", i + 1);
                $("#tab" + (i + 1)).find(".cancel_batch_set_stock_ctrl").attr("spe_id", i + 1);

                $("#tab" + (i + 1)).find(".batch_set_price_ctrl").attr("spe_id", i + 1);
                $("#tab" + (i + 1)).find(".do_batch_set_price_ctrl").attr("spe_id", i + 1);
                $("#tab" + (i + 1)).find(".cancel_batch_set_price_ctrl").attr("spe_id", i + 1);

                $("#tab" + (i + 1)).find(".batc_set_stock").attr("id", "batc_set_stock" + (i + 1));
                $("#tab" + (i + 1)).find(".batch_set_price").attr("id", "batc_set_price" + (i + 1));

                $("#tab" + (i + 1)).find(".batch_set_price_ctrl").attr("id", "batch_set_price_ctrl" + (i + 1));
                $("#tab" + (i + 1)).find(".batch_set_stock_ctrl").attr("id", "batch_set_stock_ctrl" + (i + 1));
                $("#tab" + (i + 1)).find(".do_batch_set_stock_ctrl").attr("id", "do_batch_set_stock_ctrl" + (i + 1));
                $("#tab" + (i + 1)).find(".cancel_batch_set_stock_ctrl").attr("id", "cancel_batch_set_stock_ctrl" + (i + 1));
                $("#tab" + (i + 1)).find(".do_batch_set_price_ctrl").attr("id", "do_batch_set_price_ctrl" + (i + 1));
                $("#tab" + (i + 1)).find(".cancel_batch_set_price_ctrl").attr("id", "cancel_batch_set_price_ctrl" + (i + 1));

                $('.productsImg').popover({
                    html: true,
                    content: '<div style="width:300px;">最低350*350px，建议800*800px</div>',
                    trigger: 'hover'
                });
            }

        } else {
            for (var x = 0; x < empArray2.length; x++) {
                var titles = empArray2[x].split("-");
                var title = "";
                var hidearea = "";
                for (var i = 0; i < titles.length; i++) {
                    if (i == 0) {
                        title += titles[i];
                    } else if (i % 3 == 0) {
                        title += titles[i];
                        hidearea += "<input type='hidden' class='product_spec' value="
                        + titles[i - 2] + "-" + titles[i - 1].split("_")[0] + "-"
                        + titles[i - 1].split("_")[1] + ">";
                    }
                }
                var _tabs = '<li role="presentation"';
                _tabs += '><a href="#tab' + (x + 1) + '" aria-controls="tab' + (x + 1) + '" role="tab" data-toggle="tab">' + title + '</a></li> ';
                var ct = $(".demo_box .tab-pane").clone();
                ct.removeClass("demo_box");
                $(".dinfo_tabs").append(_tabs);
                ct.attr("id", "tab" + (x + 1));
                ct.append(hidearea);
                $(".dinfo_wp").append(ct);

                $("#tab"+(x+1)).find(".name_input").each(function(){
                    $(this).val($(this).val()+'('+title+')');
                });

                $("#tab" + (x + 1)).find(".copy_ware_to_other_spec_ctrl").attr("spe_id", x + 1);
                $("#tab" + (x + 1)).find(".batch_set_stock_ctrl").attr("spe_id", x + 1);
                $("#tab" + (x + 1)).find(".do_batch_set_stock_ctrl").attr("spe_id", x + 1);
                $("#tab" + (x + 1)).find(".cancel_batch_set_stock_ctrl").attr("spe_id", x + 1);

                $("#tab" + (x + 1)).find(".batch_set_price_ctrl").attr("spe_id", x + 1);
                $("#tab" + (x + 1)).find(".do_batch_set_price_ctrl").attr("spe_id", x + 1);
                $("#tab" + (x + 1)).find(".cancel_batch_set_price_ctrl").attr("spe_id", x + 1);

                $("#tab" + (x + 1)).find(".batc_set_stock").attr("id", "batc_set_stock" + (x + 1));
                $("#tab" + (x + 1)).find(".batch_set_price").attr("id", "batc_set_price" + (x + 1));

                $("#tab" + (x + 1)).find(".batch_set_price_ctrl").attr("id", "batch_set_price_ctrl" + (x + 1));
                $("#tab" + (x + 1)).find(".batch_set_stock_ctrl").attr("id", "batch_set_stock_ctrl" + (x + 1));
                $("#tab" + (x + 1)).find(".do_batch_set_stock_ctrl").attr("id", "do_batch_set_stock_ctrl" + (x + 1));
                $("#tab" + (x + 1)).find(".cancel_batch_set_stock_ctrl").attr("id", "cancel_batch_set_stock_ctrl" + (x + 1));
                $("#tab" + (x + 1)).find(".do_batch_set_price_ctrl").attr("id", "do_batch_set_price_ctrl" + (x + 1));
                $("#tab" + (x + 1)).find(".cancel_batch_set_price_ctrl").attr("id", "cancel_batch_set_price_ctrl" + (x + 1));
                $('.productsImg').popover({
                    html: true,
                    content: '<div style="width:300px;">最低350*350px，建议800*800px</div>',
                    trigger: 'hover'
                });
            }

        }
    }
    addEvent();
    defaultActive();
};

// 点击切换
function ctabs(t1, t2, t3) {
    $("." + t1).find("li:first").addClass("cur");
    $("." + t2).find("." + t3 + ":first").show().addClass("show");
    $("." + t1 + " li").each(function (n) {
        var current = $(this);
        $(this).find("a").click(function () {
            var cur = $(this);
            $("." + t1).find("li.cur").removeClass("cur");
            $("." + t2).find("." + t3 + ".show").hide().removeClass("show");
            current.addClass("cur");
            $("." + t2 + " ." + t3 + ":eq(" + n + ")").show().addClass("show");
        });
    });
};

/* 获取第三方授权分类 */
function getAllGrandCateForThird() {
    if ($(".fir_list li").length <= 0) {
        $.get("queryAllGrandCateForThird.htm", function (data) {
            if (null != data && data.length > 0) {
                $(".fir_list").html("");
                for (var i = 0; i < data.length; i++) {
                    var catName = data[i].catName+"";
                    if(catName.length > 8){
                        $(".fir_list").append(
                            " <li><a onclick='loadTypeParam(this);' role-id=" + data[i].catId
                            + " href='javascript:;'>" + data[i].catName.substring(0,8) + "</a></li>");
                    }else {
                        $(".fir_list").append(
                            " <li><a onclick='loadTypeParam(this);' role-id=" + data[i].catId
                            + " href='javascript:;'>" + data[i].catName + "</a></li>");
                    }
                }
            }
        });
    }
}
/* 加载第二级分类 */
function loadSecCate(catId, obj) {
    $($(obj).parent()).addClass('cur').siblings().removeClass('cur');
    $(".sec_search").val("");
    $(".thi_search").val("");
    $.get("querySonCateByCatId.htm?catId=" + catId + "&CSRFToken=" + $(".token_val").val(),
        function (data) {
            if (null != data && data.length > 0) {
                $(".sec_list").html("");
                $(".thi_list").html("");
                for (var i = 0; i < data.length; i++) {
                    $(".sec_list").append(
                        " <li><a role-id=" + data[i].catId + " onclick='loadThiCate("
                        + data[i].catId + ",this)' href='javascript:;'>"
                        + data[i].catName + "</a></li>");
                }
            }
        });
}
/* 加载第三级分类 */
function loadThiCate(catId, obj) {
    $($(obj).parent()).addClass('cur').siblings().removeClass('cur');
    $(".thi_search").val("");
    allThirdThirdCat = null;
    $.get("querySonCateByCatId.htm?catId=0&CSRFToken=" + $(".token_val").val(), function (data) {
        if (null != data && data.length > 0) {
            $(".thi_list").html("");
            for (var i = 0; i < data.length; i++) {
                /* 点击事件写在goods_list.js中 */
                $(".thi_list").append(
                    " <li><a onclick='loadTypeParam(this);' role-id=" + data[i].catId
                    + " href='javascript:;'>" + data[i].catName + "</a></li>");
            }
        }
    });
}

/* 搜索类目 */
var allFirstThirdCat = new Array();
function searchCate() {
    /* 如果一级分类的全局变量为空 */
    if (null == allFirstThirdCat || allFirstThirdCat.length <= 0) {
        allFirstThirdCat = $(".fir_list li");
    }
    /* 循环去匹配记录 */
    if (null != allFirstThirdCat && allFirstThirdCat.length > 0) {
        var searchList = new Array();
        for (var i = 0; i < allFirstThirdCat.length; i++) {
            var second = $(allFirstThirdCat[i]);
            if ($($(second).find("a")).html().indexOf($.trim($(".fir_search").val())) >= 0) {
                searchList.push($(second));
            }
        }
        /* 如果查询参数是空,就显示全部 */
        if ($.trim($(".fir_search").val()) == "" || $(".fir_search").val() == null) {
            searchList = allFirstThirdCat;
        }
        /* 清空分类 */
        $(".fir_list").html("");
        /* 如果搜索到的记录不为空,就添加到页面 */
        for (var i = 0; i < searchList.length; i++) {
            if($(searchList[i]).find("a").html().length > 8){
                $(".fir_list").append(
                    " <li><a onclick='loadTypeParam(this)' role-id=" + $(searchList[i]).find("a").attr("role-id") + " href='javascript:;'>" + $(searchList[i]).find("a").html().substring(0,8)
                    + "</a></li>");
            }
            $(".fir_list").append(
                " <li><a onclick='loadTypeParam(this)' role-id=" + $(searchList[i]).find("a").attr("role-id") + " href='javascript:;'>" + $(searchList[i]).find("a").html()
                + "</a></li>");
        }
    }
}

/* 根据选中的第三级分类加载类型参数 */
function loadTypeParam(obj) {
    $(".fir_search").val($(obj).parent().text());
    /* 设置选中样式并且加载类型参数 */
    $($(obj).parent()).addClass('cur').siblings().removeClass('cur');
    $(".ch_goods_cate").val($(obj).attr("role-id"));

    /* 加载类型中的参数 */
    $.get(
        "queryTypeVoByCatId.htm?catId=" + $(obj).attr("role-id") + "&CSRFToken="
        + $(".token_val").val(),
        function (data) {
            var expandParam = data.expandParams;
            var params = data.params;
            var specs = data.specVos;
            var expandParamHtml = "";
            if (expandParam.length > 0) {
                for (var i = 0; i < expandParam.length; i++) {
                    if (expandParam[i].valueList.length > 0) {
                        if(i%3==0) {
                            expandParamHtml = expandParamHtml + "<tr>";
                        }
                        expandParamHtml = expandParamHtml
                        + "<td width='80'><b class='text-danger'>*</b>"
                        + expandParam[i].expandparamName
                        + "</td><td>"
                        + "<input type='hidden' class='type_expand_param' name='expandParamId' value='"
                        + expandParam[i].expandparamId
                        + "'>"
                        + "<select class='form-control type_expand_sel' name='expandparamValue'>";
                        for (var k = 0; k < expandParam[i].valueList.length; k++) {
                            expandParamHtml = expandParamHtml + "<option value='"
                            + expandParam[i].valueList[k].expandparamValueId
                            + "'>"
                            + expandParam[i].valueList[k].expandparamValueName
                            + "</option>";
                        }
                        ;
                        expandParamHtml = expandParamHtml + "</select></td>";
                        if((i+1)%3==0 || i==expandParam.length-1) {
                            expandParamHtml = expandParamHtml + "</tr>";
                        }
                    } else {
                        expandParamHtml = expandParamHtml
                        + "<tr><td>您选择的商品分类下没有扩展参数!</td></tr>";
                    }
                }
            }
            $("#attribute tbody").html(expandParamHtml);
            /* 扩展参数 END */
            var paramHtml = "";
            if (params.length > 0) {
                for (var i = 0; i < params.length; i++) {
                    if (i % 3 == 0) {
                        paramHtml = paramHtml + "<tr>";
                    }
                    paramHtml = paramHtml
                    + "<td><span class='text-danger'></span>"
                    + params[i].paramName
                    + "</td><td class="+params[i].paramName+"><input type='hidden' class='type_param' name='paramId' value='"
                    + params[i].paramId
                    + "'/><input type='text' value='' class='text type_param_val form-control' name='paramValue'/></td>";
                    if ((i + 1) % 3 == 0 || i == params.length - 1) {
                        paramHtml = paramHtml + "</tr>";
                    }
                }
            } else {
                paramHtml = paramHtml + "您选择的商品分类下没有详细参数!";
            }
            /* 详细参数END */
            $("#parameter tbody").html(paramHtml);

            /* 加载规格信息 */
            if (specs.length > 0) {
                var specHtml = "";
                for (var i = 0; i < specs.length; i++) {
                    specHtml += "<div class='check-box spec_set checkbox checkbox-primary'>";
                    specHtml += "<label class='choose-label spec_list clearfix'>";
                    specHtml += "<input class='type_spec' type='checkbox' value="
                    + specs[i].goodsSpec.specId
                    + " />"
                    + specs[i].goodsSpec.specName + "</label></div>";
                    specHtml += "<div class='spec_set_details' id='emp"+i+"'>";
                    specHtml += "<table class='table'>";
                    specHtml += "<tbody>";
                    var spleng = specs[i].goodsSpec.specDetails.length;
                    for (var j = 0; j < specs[i].goodsSpec.specDetails.length; j++) {
                        if (j % 3 == 0) {
                            specHtml += "<tr>";
                        }
                        specHtml += '<td><label style="" class="choose-label"><input type="checkbox" class="check_spec openSpecValue_'+ specs[i].goodsSpec.specId + '" spec_id="' + specs[i].goodsSpec.specId + '" spec_value_id="' + specs[i].goodsSpec.specDetails[j].specDetailId + '" value="' + specs[i].goodsSpec.specDetails[j].specDetailName + '"  onclick="checkSpecValue(this);">'
                        + '<input class="form-control" type="text" value="'+ specs[i].goodsSpec.specDetails[j].specDetailName +'" onchange="changeValueId(this)"/>'
                      + '</label></td>';
                        if (spleng == j) {
                            specHtml += ' </tr>';
                        } else if (spleng >= 3) {
                            if ((j + 1) % 3 == 0) {
                                specHtml += ' </tr>';
                            }
                        }
                    }
                    specHtml += ' </tbody></table></div>';
                }
                $(".type_spec").html(specHtml);
                $.material.init();
            } else {
                $(".type_spec").html("<p class='no_spec'>没有规格</p>");
            }
            $('.spec_set input').change(function () {
                if ($(this).is(':checked')) {
                    $(this).parent().parent().next().attr('name', 'ok');
                    $(this).parent().parent().next().slideDown('fast');
                }
                else {
                    $(this).parent().parent().next().attr('name', 'no');
                    $(this).parent().parent().next().slideUp('fast');
                }
            });
            /* 绑定事件 */
            bindEvent();
            tx();
        });
}
/* 点击开启规格值的时候 */
function checkSpecValue(obj) {
    if ($(obj).prop("checked")) {
        $(obj)
            .parent().parent()
            .append(
            "<form action='uploadImgSingle.htm' method='post' target='hidden_frame'  enctype='multipart/form-data'  class='specValue_"
            + $(obj).attr("spec_value_id")
            + " spec_img_form_"
            + $(obj).attr("spec_id")
            + "'>"
            + "<div class='spec_value_img'>"
            + "<a href='javascript:;' class='add_img'></a>"
            + "<input type='hidden' name='specValId' value="
            + $(obj).attr('spec_value_id')
            + ">"
            + "<input type='hidden' class='up_spec_img_src up_spec_img_src_"
            + $(obj).attr("spec_value_id")
            + "' value=''>"
            + "<input type='file' name='specImg' spec_val_id="
            + $(obj).attr("spec_value_id")
            + " class='spec_img spec_img_"
            + $(obj).attr("spec_value_id") + "' />"
            + "</div>"
            + "</form>"
        );
        bindEvent();
    } else {
        $(".up_spec_img_" + $(obj).attr("spec_value_id")).remove();
        $(".specValue_" + $(obj).attr("spec_value_id")).remove();
    }
}


/* 上传规格图片回调 */
function specImgSucc(msg) {
    if (msg == "111") {
        openDialog("不支持的图片格式");
    } else {
        var id = msg.substring(msg.indexOf("-") + 1, msg.length);
        var url = msg.substring(0, msg.indexOf("-"));
        // 如果该规格已经上传过图片就替换已经上传的图片
        if ($(".up_spec_img_" + id)[0]) {
            $(".up_spec_img_" + id).attr("src", url);
        } else {
            $(".specValue_" + id).before(
                "<img src=" + url + " class='value_img up_spec_img_" + id
                + "'/>");
        }
        $(".up_spec_img_src_" + id).val(url);
    }
}
/* 上传货品图片SUCC */
function productImgSucc(msg) {
    if (msg == "111") {
        openDialog("不支持的图片格式");
    } else {
        var params = msg.split("-");
        $(product_image)
            .parent()
            .before(
            "<img class='vm up_pro_img' src="
            + params[1]
            + "  width='50' height='50' /><input type='hidden' class='product_up_img' value="
            + params[0] + ">");
    }
}

/* 循环保存货品信息 */
function savePro(goodsId) {
    var pro_box = $(".dinfo_wp .tab-pane");
    if (null != pro_box && pro_box.length > 0) {
        for (var j = 0; j < pro_box.length; j++) {
            var _pro = $(pro_box[j]);
            if (_pro.find(".product_spec").length > 0) {
                var param = "";
                param += "goodsId=" + goodsId;
                param += "&goodsInfoItemNo=" + _pro.find(".no_input").val();
                param += "&goodsInfoBarcode=" + _pro.find(".barcode_input").val();
                param += "&goodsInfoName=" + encodeURI(_pro.find(".name_input").val());
                param += "&goodsInfoSubtitle=" + encodeURI(_pro.find(".des_input").val());
                param += "&goodsInfoPreferPrice=" + _pro.find(".pro_price").val();
                param += "&goodsInfoCostPrice=" + _pro.find(".pro_cost_price").val();
                param += "&goodsInfoMarketPrice=" + _pro.find(".pro_mark_price").val();
                param += "&goodsInfoStock=" + _pro.find(".pro_stock").val();
                param += "&goodsInfoWeight=" + _pro.find(".pro_weight").val();
                param += "&goodsInfoImgId=" + $(_pro.find(".choose_pro_img")[0]).attr("src");
                /*获取选择的商品状态*/
                var sta = _pro.find(".pro_status:checked").val();

                param += "&goodsInfoAdded=" + sta;

                /*拼接规格信息*/
                var product_spec = _pro.find(".product_spec");
                if (null != product_spec && product_spec.length > 0) {
                    for (var i = 0; i < product_spec.length; i++) {
                        var specVal = $(product_spec[i]).val().split("-");
                        /*拼接规格信息以及规格值信息*/
                        param += "&specId=" + specVal[0] + "&specDetailId=" + specVal[1] + "&specRemark=" + specVal[2];
                    }
                }
                /*获取是否参加会员折扣*/
                var discount = _pro.find(".customer_discount:checked").val();
                param += "&isCustomerDiscount=" + discount;
                /*获取是否显示在列表页*/
                if (_pro.find(".show_list").prop("checked")) {
                    param += "&showList=1";
                } else {
                    param += "&showList=0";
                }
                /*获取是否显示在手机版*/
                if (_pro.find(".show_mobile").prop("checked")) {
                    param += "&showMobile=1";
                } else {
                    param += "&showMobile=0";
                }

                /*获取选择的服务支持*/
                var supp = $(".pro_supp:checked");
                if (null != supp && supp.length > 0) {
                    for (var i = 0; i < supp.length; i++) {
                        param += "&support=" + $(supp[i]).val();
                    }
                }
                /*拼接图片参数*/
                var imgs = _pro.find(".choose_img");
                if (null != imgs && imgs.length > 0) {
                    for (var i = 0; i < imgs.length; i++) {
                        param += "&image=" + $(imgs[i]).val();
                    }
                }
                /*保存分舱信息*/
                /*
                 var wares = _pro.find(".ware_id");
                 if (null != wares && wares.length > 0) {
                 for (var i = 0; i < wares.length; i++) {
                 param += "&ware=" + $(wares[i]).val();
                 param += "&wareStock=" + $(_pro.find(".ware_stock")[i]).val();
                 param += "&warePrice=" + $(_pro.find(".ware_price")[i]).val();
                 }
                 }*/
                var isMailBay = _pro.find(".isMailBay:checked");

                param += "&isMailBay=" + $(isMailBay).val();


                param += "&CSRFToken=" + $(".token_val").val();
                $.ajax({
                    type: 'post',
                    url: 'tNewUploadProduct.htm',
                    data: param,
                    async: false,
                    success: function (data) {
                        if (data > 0) {

                        } else {

                        }
                    }
                });
            }
        }

    }
    /**
     * 平台是否开启商家审核开关 进行不同的页面跳转
     */
    var isThirdAuditUsed = $("#isThirdAuditUsed").val();
    var onOffFlag = $("#onOffFlag").val();
    if(onOffFlag == '0'){
        //审核关闭 直接跳转到 在售商品列表
        saveGoodsOk("upthirdgoods.html?n=3&l=27", "thirdgoodsmanager.html?n=3&l=43", "添加商品成功！");
    }else{
        //审核开启 跳转到审核商品列表
        saveGoodsOk("upthirdgoods.html?n=3&l=27", "auditgoodslist.html?n=3&l=99", "添加商品成功！");
    }



}

/**
 * 添加商品成功 使用
 * @param addUrl 继续添加
 */
function saveGoodsOk(addUrl, listUrl, tips) {
    $("#modalDialog").remove();
    var confirmDialog =
        '<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog">' +
        '    <div class="modal-dialog">' +
        '        <div class="modal-content">' +
        '            <div class="modal-header">' +
        '                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
        '               <h4 class="modal-title">系统提示</h4>' +
        '           </div>' +
        '           <div class="modal-body">';
    if (tips != '' && tips != undefined) {
        confirmDialog += tips;
    } else {
        confirmDialog += '添加成功，继续添加商品吗？';
    }
    confirmDialog += '           </div>' +
    '           <div class="modal-footer">' +
    '               <button type="button" class="btn btn-primary" onclick="jumpUrl(\'' + addUrl + '\');">继续添加</button>' +
    '               <button type="button" class="btn btn-default" onclick="jumpUrl(\'' + listUrl + '\');">商品列表</button>' +
    '           </div>' +
    '       </div>' +
    '   </div>' +
    '</div>';
    $(document.body).append(confirmDialog);
    $('#modalDialog').on('hidden.bs.modal', function (e) {
        jumpUrl(listUrl);
    });
    $('#modalDialog').modal('show');
}

function jumpUrl(url) {
    window.location.href = url;
}

/**
 * 验证添加商品基本信息
 */
function checkBase() {
    var bool = true;
    if (!$(".ch_goods_cate").val() > 0) {
        openDialog("请选择商品分类!");
        bool = false;
    }
    /*
     * bool = checkLengthForJQ($(".dw_input"),$(".dw_input_Tips"),"计价单位不能为空!") &&
     * bool;
     * if(checkLengthForJQ($(".dw_input"),$(".dw_input_Tips"),"计价单位不能为空!")){
     * bool = checkSymbForJQ($(".dw_input"),$(".dw_input_Tips")) && bool; }
     */
    /* 如果选择过了分类就验证是否选择规格 */
    if ($(".ch_goods_cate").val() > 0) {
        if ($(".type_spec:checked").length <= 0 || $(".check_spec:checked").length <= 0) {
            openDialog("请至少选择一个商品规格!");
            bool = false;
        } else {
            var spe_text = $(".spe_text");
            for (var i = 0; i < spe_text.length; i++) {
                bool = checkLengthForJQ($(spe_text[i]), $(".spec_remark_input_Tips"), "规格值不能为空!")
                && bool;
                if (checkLengthForJQ($(spe_text[i]), $(".spec_remark_input_Tips"), "规格值不能为空!")) {
                    bool = checkSymbForJQ($(spe_text[i]), $(".spec_remark_input_Tips")) && bool;
                }
            }
        }
    }
    return bool;
}

/* 验证货品等信息 */
function checkProInfo() {
    /* 如果已经点击过保存就返回false */
    if ($(".flag_saved").val() == "1") {
        return false;
    } else {
        $(".flag_saved").val("1");
        var bool = true;
        /* 获取每一个TAB页 */
        var pro_box = $(".dinfo_wp").find(".dinfo_box");
        if (null != pro_box && pro_box.length > 0) {
            /* 逐个验证每一个TAB */
            for (var _i = 0; _i < pro_box.length; _i++) {
                var _pro = $(pro_box[_i]);
                bool = checkLength(_pro.find(".no_input"), "　商品编号", _pro.find(".no_input_Tips"),
                    10, 32)
                && bool;
                if (checkLength(_pro.find(".no_input"), "　商品编号", _pro.find(".no_input_Tips"), 10,
                        32)) {
                    bool = checkRegexp(_pro.find(".no_input"), /^[0-9]+$/, "　商品编号输入格式不正确.", _pro
                        .find(".no_input_Tips"))
                    && bool;
                }
                bool = checkLength(_pro.find(".name_input"), "商品名称", _pro.find(".name_input_Tips"),
                    3, 50)
                && bool;
                if (!stripscript(_pro.find(".name_input").val())) {
                    updateTips("商品名称不能有 &或#　符!", _pro.find(".name_input_Tips"));
                    bool = false && bool;
                }
                if (!stripscript(_pro.find(".des_input").val())) {
                    updateTips("副标题不能有 &或#　符!", _pro.find(".des_input_Tips"));
                    bool = false && bool;
                }
                /* if (checkLength(_pro.find(".name_input"), "商品名称", _pro.find(".name_input_Tips"), 3,
                 * 50)) {
                 *	bool = checkSymbForJQ(_pro.find(".name_input"), _pro.find(".name_input_Tips"))
                 *			&& bool;
                 *}
                 */
                bool = checkSymbForJQ(_pro.find(".des_input"), _pro.find(".des_input_Tips"))
                && bool;
                bool = checkRegexp(_pro.find(".pro_price"), /^[0-9]+[.]{0,1}[0-9]{0,2}$/,
                    "　销售价格输入格式不正确.", _pro.find(".pro_price_Tips"))
                && bool;
                    bool = checkRegexp(_pro.find(".pro_cost_price"), /^[0-9]+[.]{0,1}[0-9]{0,2}$/,
                            "　成本价格输入格式不正确.", _pro.find(".pro_cost_price_Tips"))
                        && bool;

                bool = checkRegexp(_pro.find(".pro_mark_price"), /^[0-9]+[.]{0,1}[0-9]{0,2}$/,
                    "　市场价格输入格式不正确.", _pro.find(".pro_mark_price_Tips"))
                && bool;
                bool = checkRegexp(_pro.find(".pro_stock"), /^[0-9]+$/, "　库存输入格式不正确.", _pro
                    .find(".pro_stock_Tips"))
                && bool;
                bool = checkLength($(".goods_deno"), "计价单位", $(".goods_deno_tips"), 1, 5) && bool;
                /* 验证是否上传图片 */

                var images = _pro.find(".choose_img");
                if (null != images && images.length > 0) {
                    updateTipsSucc(_pro.find(".pro_image_Tips"), _pro.find(".pro_image_Tips"));
                } else {
                    updateTips("请至少选择一张图片!", _pro.find(".pro_image_Tips"));
                    bool = false && bool;
                }


                /* 验证分仓信息 */
                // var stock=_pro.find(".ware_stock");
                // if(null != stock && stock.length>0){
                // for(var i =0;i<stock.length;i++){
                // var _sto=$(stock[i]);
                // bool = checkRegexp(_sto, /^[0-9]+$/, "库存必须为正整数.",
                // _sto.parent().parent().find(".pro_ware_Tips") ) && bool;
                // }
                // }
                // var price=_pro.find(".ware_price");
                // if(null != price && price.length>0){
                // for(var i =0;i<price.length;i++){
                // var _price=$(price[i]);
                // bool = checkRegexp(_price, /^[0-9]+[.]{0,1}[0-9]{0,2}$/,
                // "价格输入格式不正确.",
                // _price.parent().parent().find(".pro_ware_pri_Tips") ) &&
                // bool;
                // }
                // }
                // /* 验证分仓信息 END */
                /* 判断编号是否已经存在 */
                if (_pro.find(".exist_flag").val() == "1") {
                    _pro.find(".no_input").addClass("ui-state-error");
                    _pro.find(".no_input_Tips").addClass("ui-state-highlight");
                    _pro.find(".no_input_Tips").text("名称或编号已经存在!");
                    bool = false && bool;
                }
                /* 如果验证不通过就显示当前TAB */
                if (!bool) {
                    location.hash = "anchor_top";
                    $(".show_title").text("商品信息验证未通过！");
                    dia(1);
                    show_tab(_pro.parent());
                }
            }
        }
        if (!bool) {
            $(".flag_saved").val("0");
        }
        return bool;
    }
}
/*验证条形码*/
function checkProBarcode(obj) {
    var isValid = true;
    var numandletterReg = /^([0-9][0-9]*(\.[0-9]{1,2})?|0\.(?!0+$)[0-9]{1,2})$/;
    if($(obj).val() != ''){
        if (!(numandletterReg.test($(obj).val()))) {
            $(obj).addClass('error');
            $(obj).next('.error').remove();
            $(obj).after('<label class="error">请输入数字</label>');
            isValid = false;
            return isValid;
        }
    }
    return isValid;
}

/* 验证货品编号是否已经存在 */
function checkProNo(obj) {


    if ($(obj).val().length > 32 || $(obj).val().length < 10) {
        $(obj).addClass('error');
        $(obj).next('.error').remove();
        $(obj).after('<label class="error">货品编号 长度必须要10字符~32字符之间</label>');
        return false;
    }


    var isValid = true;
    if ($(obj).val() != '' && $(obj).val() != undefined) {
        $(obj).removeClass('error');
        $(obj).next('.error').remove();
    } else {
        $(obj).addClass('error');
        $(obj).next('.error').remove();
        $(obj).after('<label class="error">不能为空</label>');
        isValid = false;
        return isValid;
    }
    var numandletterReg = /^[0-9a-zA-Z]+$/;
    if ($(obj).val() != '' && (numandletterReg.test($(obj).val()))) {
        $(obj).removeClass('error');
        $(obj).next('.error').remove();
    } else {
        $(obj).addClass('error');
        $(obj).next('.error').remove();
        $(obj).after('<label class="error">请输入字母或数字</label>');
        isValid = false;
        return isValid;
    }
    /*验证货品编号是否已经存在*/
    if ($(obj).val() != undefined && $(obj).val().trim().length > 0) {
        var param = "checkProductNo.htm?productNo=" + $(obj).val() + "&time=" + Math.random();
        $.ajax({
            type: 'post',
            url: param,
            async: false,
            success: function (data) {
                if (!data) {
                    $(obj).addClass('error');
                    $(obj).next('.error').remove();
                    $(obj).after('<label class="error">编号已经存在</label>');
                    $(obj).parent().find(".exist_flag").val("1");
                    isValid = false;
                } else {
                    /*验证当前输入的是否有重复*/
                    var inputs = $(".dinfo_wp .no_input");
                    var count = 0;
                    for (var i = 0; i < inputs.length; i++) {
                        var no = $(inputs[i]).val();
                        if ($(obj).val() == no) {
                            count = parseInt(count) + parseInt(1);
                        }
                    }
                    /*如果数量大于1,其中有一个是自身*/
                    if (parseInt(count) > 1) {
                        $(obj).addClass('error');
                        $(obj).next('.error').remove();
                        $(obj).after('<label class="error">编号已经使用</label>');
                        $(obj).parent().find(".exist_flag").val("1");
                        isValid = false;
                    } else {
                        $(obj).removeClass('error');
                        $(obj).next('.error').remove();
                        $(obj).parent().find(".exist_flag").val("0");
                    }
                }
            }
        });
    }
    return isValid;
}
/* 设置库房默认价格 */
function price_default(obj) {
    if (/^[0-9]+[.]{0,1}[0-9]{0,2}$/.test($(obj).val())) {
        $(obj).parent().parent().find(".ware_price").val($(obj).val());
    }
}

//检查js特殊字符 “&”
function stripscript(s) {
    var regexp = new RegExp("[&#]");
    if (regexp.test(s)) {
        return false;
    } else {
        return true;
    }

}

/* 开启弹出框 */
function openDialog(tipValue) {
    $(".dia_tip").text(tipValue);
    $("#dialog-tip").dialog({
        resizable: false,
        height: 150,
        width: 270,
        modal: true,
        autoOpen: true,
        buttons: {
            "确定": function () {
                $(this).dialog("close");
            }
        }
    });
    $("#dialog-tip").dialog("open");
}

/* 根据传递的tab对象显示对应的tab窗体 */
function show_tab(tab_box) {
    // $(".dinfo_wp").find(".dinfo_box").hide();
    $(tab_box).show();
    var _n = tab_box.index() - 1;
    $(".dinfo_tabs li").removeClass("cur");
    $(".dinfo_tabs li:eq(" + _n + ")").addClass("cur");
}

function changeValueId(obj) {
    $(obj.parentNode.firstChild).val($(obj).val());
}

function tx() {
    $(".spec_cont input[type='checkbox']").each(function () {
        var _this = $(this);
        var _v = _this.next("span").text();
        var _text = '<input class="spe_text" onchange="changeValueId(this)"  type="text" value="' + _v + '" />';
        _this.change(function () {
            if (_this.prop("checked") == true) {
                _this.next("span").hide();
                _this.after(_text);
            } else {
                _this.next().next("span").show().text(_this.next("input").val());
                _this.next("input").remove();

            }
        });
    });
}

function choose_img(c) {
    if ($(c).hasClass("choose")) {
        $(c).removeClass("choose");
    } else {
        $(c).addClass("choose");
    }
};

/*-------------------------------------------------------------------*/
// 根据jq对象验证特殊字符，将调试显示到页面中
function checkSymbForJQ(inputobj, Tipobj) {
    var regexp = new RegExp("[''\\[\\]<>?!]");
    if (regexp.test($(inputobj).val())) {
        $(inputobj).addClass("ui-state-error");
        updateTips("输入的内容包含特殊字符!", $(Tipobj));
        return false;
    } else {
        $(inputobj).removeClass("ui-state-error");
        updateTipsSucc(null, $(Tipobj));
        return true;
    }
}

/*--------------------------------------------------------------------------------------------------------*/

function addGoodsBrand() {
    $("#goods_brand").chosen();
}

//反选
function unSelectAll(obj) {
    var checkboxs = document.getElementsByName(obj);
    for (var i = 0; i < checkboxs.length; i++) {
        var e = checkboxs[i];
        e.checked = !e.checked;
    }
    for (var j = 0; j < checkboxs.length; j++) {
        if (checkboxs[j].checked) {
            $(checkboxs[j]).parent().parent().addClass("trbcak");
        } else {
            $(checkboxs[j]).parent().parent().removeClass("trbcak");
        }
    }
}

// 全选
function selectAlls(obj) {
    var checkboxs = document.getElementsByName(obj);
    for (var i = 0; i < checkboxs.length; i++) {
        var e = checkboxs[i];
        e.checked = true;
        $(e).parent().parent().addClass("trbcak");
    }
}

function showAddGoodsRelModal() {
    queryGoodsByParam(1);
    $("#addGoodsRelModal").modal("show");
}


//第三方加载京东详情
function loadJdDetails(obj){
    var httpUrl = $(".httpUrl").val();
    $.ajax({
        url: "jdtriggeraddgoods.htm",
        context: document.body,
        type: 'POST',
        data:{httpUrl:httpUrl},
        success: function(data){
            if(data!=""&&data!=null){
                for(var i=0;i<data.outPrams.length;i++){
                    try{
                        $("#parameter").find("."+data.outPrams[i].outParam).find(".type_param_val").val(data.outPrams[i].outValue);
                    }catch(e){
                        continue;
                    }
                }
                $(".name_input").val(data.goodName);
                var htm = '';
                for(var j=0;j<data.imgList.length;j++){
                    htm+='<img src="'+data.imgList[j].imgUrl+'"/>';
                }
                var htmm = '';
                for(var j=0;j<data.mimgList.length;j++){
                    htmm+='<img src="'+data.mimgList[j].imgUrl+'"/>';
                }
                $(".summernotedesc").code(htm);
                $(".summernotemobile").code(htmm);
            }

        }
    });
}

function queryGoodsByParam(pageNo) {
    var goodsName = $("#goods_name").val();
    var goodsNo = $("#goods_no").val();
    var goodsBrandId = $("#goodsBrandId").val();
    /*根据选中分类加载相关商品*/
    $.get("queryThirdGoodsByParamAjax.htm?goodsCateId=" + $('#parentId3').val() + "&pageNo=" + pageNo + "&goodsNo=" + goodsNo + "&goodsName=" + goodsName + "&goodsBrandId=" + goodsBrandId + "&showFlag=1", function (data) {
        var aboutGoodsHtml = "";
        var list = data.list;
        if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                aboutGoodsHtml = aboutGoodsHtml + "<tr><td><input type='checkbox' goods_name='"+list[i].goodsName+"' goods_no='"+list[i].goodsNo+"' goods_img='"+list[i].goodsImg+"' class='form-control ch_about' name='aboutGoodsIdSelect' value='" + list[i].goodsId + "'/></td>" + "<td><img width='50' height='50' src=" + list[i].goodsImg + " /></td><td title='"+list[i].goodsNo+"'>" + list[i].goodsNo + "</td><td>" + list[i].goodsName + "</td>";
                aboutGoodsHtml = aboutGoodsHtml + "<td>" + list[i].goodsType.typeName + "</td><td>" + list[i].goodsBrand.brandName + "</td></tr>";
            }
        }
        else {
            aboutGoodsHtml = aboutGoodsHtml + "<tr><td colspan='6'>选择的分类下暂时还没有商品</td></tr>";
        }
        $("#aboutGoodsList tbody").html(aboutGoodsHtml);


        /*添加页脚*/
        $(".pagination").html("");
        var foot = "";
        if (data.pageNo == 1) {
            foot += '<li class="disabled"> <a href="javascript:;" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li> ';
        } else {
            foot += "<li><a href='javascript:;' onclick='queryGoodsByParam(" + data.prePageNo + ")'><span aria-hidden='true'>&laquo;</span></a></li>";
        }
        var i = 1;
        for (var l = data.startNo; l <= data.endNo; l++) {
            if ((i - 1 + data.startNo) == data.pageNo) {
                foot = foot + '<li class="active"><a href="javascript:;"> ' + data.pageNo + ' </a></li>';
            }
            else {
                foot = foot + "<li><a onclick='queryGoodsByParam(" + (i - 1 + data.startNo) + ")' href='javascript:;'>" + (i - 1 + data.startNo) + "</a></li>";
            }
            i++;
        }
        foot = foot + "<li><a onclick='queryGoodsByParam(" + data.nextPageNo + ")' href='javascript:;'><span aria-hidden='true'>&raquo;</span></a></li>";
        /*添加tfoot分页信息*/
        $(".pagination").html(foot);
    });

}

function saveRelGoods() {
    var html = '';
    var selecteOne = false;
    $("input[name=aboutGoodsIdSelect]:checkbox").each(function () {
        if ($(this).is(':checked') == true) {
            selecteOne = true;
            if ($("#selectedGoods" + $(this).val()) == undefined || $("#selectedGoods" + $(this).val()).length <= 0) {
                html += '<tr id="rel_goods_tr' + $(this).val() + '">' +
                '   <td><img src="' + $(this).attr("goods_img") + '" width="50px"></td>' +
                '   <td><input type="hidden" name="aboutGoodsId" value="' + $(this).val() + '"/><input type="hidden" id="selectedGoods' + $(this).val() + '" value="' + $(this).val() + '"/>' +
                '       <span>' + $(this).attr("goods_no") + '</span>' +
                '   </td>' +
                '   <td><span>' + $(this).attr("goods_name") + '</span></td>' +
                '   <td><span><a href="javascript:;" onclick="$(\'#rel_goods_tr' + $(this).val() + '\').remove();">删除</a></span></td>' +
                '</tr>'
            }
        }
    });
    if (!selecteOne) {
        showTipAlert("至少选择一个商品，才能保存！");
        return;
    }
    $("#select_rel_goods tbody").append(html);
    $("#select_rel_goods thead").show();
    $("#addGoodsRelModal").modal("hide");
}

/**
 * 全选反选
 * @param obj
 * @param name
 */
function allunchecked(obj, name) {
    if (obj.checked) {
        $("input[name='" + name + "']").each(function () {
            this.checked = true;
        });
    } else {
        $("input[name='" + name + "']").each(function () {
            this.checked = false;
        });
    }
}

/**
 * 生成编号
 * @param obj
 */
function generateProNo(obj) {
    var proNo = parseInt(new Date().format("yyyyMMddhhmmss") + "1");
    var proNoInput = $(".dinfo_wp").find(".active").find(".p_code");
    $(proNoInput).val(proNo);
    showConfirmAlert("要生成其他货品的编号吗?", "generateOtherProNo(" + proNo + ")");
    checkProNo($(proNoInput));
}

function generateOtherProNo(proNo) {
    var proNoInput = $(".dinfo_wp").find(".tab-pane").not(".active").find(".p_code");
    for (var i = 0; i < proNoInput.length; i++) {
        $(proNoInput[i]).val(proNo + (i + 1));
    }
    $("#modalDialog").modal("hide");
}

function displayBatchSetStock(obj) {
    var spe_id = $(obj).attr("spe_id");
    $("#batc_set_stock" + spe_id).css("display", "inline-block");
    $(obj).hide();
    $("#do_batch_set_stock_ctrl" + spe_id).show();
    $("#cancel_batch_set_stock_ctrl" + spe_id).show();
}

function displayBatchSetPrice(obj) {
    var spe_id = $(obj).attr("spe_id");
    $("#batc_set_price" + spe_id).css("display", "inline-block");
    $(obj).hide();
    $("#do_batch_set_price_ctrl" + spe_id).show();
    $("#cancel_batch_set_price_ctrl" + spe_id).show();
}

function hideBatchSetStock(obj) {
    var spe_id = $(obj).attr("spe_id");
    $("#batc_set_stock" + spe_id).hide();
    $("#batch_set_stock_ctrl" + spe_id).show();

    $("#do_batch_set_stock_ctrl" + spe_id).hide();
    $("#cancel_batch_set_stock_ctrl" + spe_id).hide();
}

function hideBatchSetPrice(obj) {
    var spe_id = $(obj).attr("spe_id");
    $("#batc_set_price" + spe_id).hide();
    $("#batch_set_price_ctrl" + spe_id).show();

    $("#do_batch_set_price_ctrl" + spe_id).hide();
    $("#cancel_batch_set_price_ctrl" + spe_id).hide();
}

function doBatchSetStock(obj) {
    var spe_id = $(obj).attr("spe_id");
    var stock = $("#batc_set_stock" + spe_id).val();
    var digitsReg = /^[0-9]+$/;
    if (stock != '' && (digitsReg.test(stock))) {
        $(obj).removeClass('error');
        $(obj).prev('.error').remove();
    } else {
        $(obj).addClass('error');
        $(obj).prev('.error').remove();
        $(obj).before('<label class="error">请输入整数</label>');
        return;
    }
    $("#tab" + spe_id).find(".ware_stock").each(function () {
        $(this).val(stock);
    })
    checkProductForm();
    hideBatchSetStock(obj);
}

function doBatchSetPrice(obj) {
    var spe_id = $(obj).attr("spe_id");
    var price = $("#batc_set_price" + spe_id).val();
    var numberReg = /^[0-9]+[.]{0,1}[0-9]{0,2}$/;
    if (price != '' && (numberReg.test(price))) {
        $(obj).removeClass('error');
        $(obj).prev('.error').remove();
    } else {
        $(obj).addClass('error');
        $(obj).prev('.error').remove();
        $(obj).before('<label class="error">请输入数字</label>');
        return;
    }
    $("#tab" + spe_id).find(".ware_price").each(function () {
        $(this).val(price);
    })
    checkProductForm();
    hideBatchSetPrice(obj);
}

function copyWareToOtherSpec(obj) {
    var spe_id = $(obj).attr("spe_id");
    var from_pro = $("#tab" + spe_id);
    var pro_box = $(".dinfo_wp .tab-pane");
    if (null != pro_box && pro_box.length > 0) {
        for (var j = 0; j < pro_box.length; j++) {
            var _pro = $(pro_box[j]);
            var wares = _pro.find(".ware_id");
            if (null != wares && wares.length > 0) {
                for (var i = 0; i < wares.length; i++) {
                    $(_pro.find(".ware_stock")[i]).val(from_pro.find(".ware_stock")[i].value);
                    $(_pro.find(".ware_price")[i]).val(from_pro.find(".ware_price")[i].value);
                }
            }
        }
    }
    showTipAlert("操作成功，已将此设置应用到其他货品！");
}

function showTipAlert(tip) {
    $("#modalDialog").remove();
    var dialogHtml =
        '<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" style="z-index:99999;">' +
        '    <div class="modal-dialog">' +
        '        <div class="modal-content">' +
        '            <div class="modal-header">' +
        '                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
        '               <h4 class="modal-title">操作提示</h4>' +
        '           </div>' +
        '           <div class="modal-body" style="text-align: center;">' +
        tip +
        '           </div>' +
        '           <div class="modal-footer">' +
        '               <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="$(\'#modalDialog\').modal(\'hide\');">确定</button>' +
        '           </div>' +
        '       </div>' +
        '   </div>' +
        '</div>';
    $(document.body).append(dialogHtml);
    $('#modalDialog').modal('show');
}

function addEvent() {
    $(".chooseProimg").click(function () {
        i = 1;
        art.dialog.open('queryImageManageByChoose.htm?size=10000', {
            lock: true,
            opacity: 0.3,
            width: '900px',
            height: '620px',
            title: '选择图片'
        });
    });

}

function defaultActive() {
    $(".dinfo_tabs li:first-child").addClass("active");
    $(".dinfo_wp .tab-pane:first-child").addClass("active");
}

function checkProductForm() {
    var f = true;
    var forms = $(".dinfo_wp .form-horizontal");
    for (var i = 0; i < forms.length; i++) {
        var requireds = $(forms[i]).find(".required");


        for (var j = 0; j < requireds.length; j++) {
            if (requireds[j].value == '') {
                $(requireds[j]).addClass('error');
                $(requireds[j]).next('.error').remove();
                $(requireds[j]).after('<label class="error">不能为空</label>');
                f = f && false;
            } else {
                $(requireds[j]).removeClass('error');
                $(requireds[j]).next('.error').remove();
            }
        }
        var numbers = $(forms[i]).find(".number");
        var numberReg = /^[0-9]+[.]{0,1}[0-9]{0,2}$/;
        for (var j = 0; j < numbers.length; j++) {
            if (numbers[j].value != '' && (numberReg.test(numbers[j].value))) {
                $(numbers[j]).removeClass('error');
                $(numbers[j]).next('.error').remove();
            } else {
                $(numbers[j]).addClass('error');
                $(numbers[j]).next('.error').remove();
                $(numbers[j]).after('<label class="error">请输入合法的数字</label>');
                f = f && false;
            }
        }


        var digits = $(forms[i]).find(".digits");
        var digitsReg = /^[0-9]+$/;
        for (var j = 0; j < digits.length; j++) {
            if (digits[j].value != '' && (digitsReg.test(digits[j].value))) {
                $(digits[j]).removeClass('error');
                $(digits[j]).next('.error').remove();
            } else {
                $(digits[j]).addClass('error');
                $(digits[j]).next('.error').remove();
                $(digits[j]).after('<label class="error">请输入整数</label>');
                f = f && false;
            }
        }

        var exist_flag = $(forms[i]).find(".exist_flag").val();
        if (exist_flag == '0') {
            $(exist_flag).removeClass('error');
            $(exist_flag).next('.error').remove();
        } else {
            $(exist_flag).addClass('error');
            $(exist_flag).next('.error').remove();
            $(exist_flag).after('<label class="error">商品编号不正确</label>');
            f = f && false;
        }

    }
    return f;
}

function changeAllPrice(obj) {
    if ($(obj).val() != '' && /^([0-9][0-9]*(\.[0-9]{1,2})?|0\.(?!0+$)[0-9]{1,2})$/.test($(obj).val())) {
        var panes = $(".tab-pane");
        var cprice = $(obj).val();
        if (panes.find(".ware_price").length != 0) {
            panes.find(".ware_price").each(function () {
                $(this).val(cprice);
            });
        }
        //panes.find(".pro_mark_price").val(cprice);
        $('.pro_price').val($(obj).val());
        $(".goodsPrice").val($(obj).val());
    } else {
        //showTipAlert('请输入正确的价格！');
    }
}

function copyImageToOtherSpec(path) {
    if (path.toString().indexOf(",") > -1) {
        var paths = path.toString().split(",");
        for (var i = 0; i < paths.length; i++) {
            var path2 = paths[i];
            $(".dinfo_wp").find(".tab-pane").not(".active").find(".choose_imgs").append('<li class="inline"><a class="del_ts" href="javascript:;" onclick="del_ts(this)"><i class="glyphicon glyphicon-remove-sign"></i></a><input type="hidden" class="choose_img" value=' + path2 + '><a href="javascript:;"><img alt="" class="choose_pro_img" src=' + path2 + ' width="100" height="100" /></a></li>');
        }
    } else {
        $(".dinfo_wp").find(".tab-pane").not(".active").find(".choose_imgs").append('<li class="inline"><a class="del_ts" href="javascript:;" onclick="del_ts(this)"><i class="glyphicon glyphicon-remove-sign"></i></a><input type="hidden" class="choose_img" value=' + path + '><a href="javascript:;"><img alt="" class="choose_pro_img" src=' + path + ' width="100" height="100" /></a></li>');
    }
    $("#modalDialog").modal("hide");
}

function checkImage() {
    var f = true;
    var forms = $(".tab-pane");
    for (var i = 0; i < forms.length - 1; i++) {
        var s = $(forms[i]).find(".choose_imgs").html();
        if (s == '') {
            showTipAlert('请添加对应货品的图片！');
            f = f && false;
        }

    }
    return f;
}