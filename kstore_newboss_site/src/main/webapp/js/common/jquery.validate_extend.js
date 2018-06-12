/**
 * Created by youzipi on 2016/1/11.
 */
/*global $, jQuery, alert*/
/**
 * ===example===:
 *
 *
 $.validator.addMethod("le",
 function (value, element, param) {
       //return parseInt(value) > parseInt($(small).val());
       var target = $(param).unbind(".validate-equalTo").bind("blur.validate-equalTo", function() {
           $(element).valid();
       });
       return parseInt(value) >= parseInt(target.val());
   }, "Max must be greater than min"
 );
 *
 *
 */
/**
 * 大于
 * param:比较控件name属性
 */
jQuery.validator.addMethod("gt", function (value, element, param) {
    var flag = true;
    //var element2 = jQuery("[name='" + param + "']");
    var element2 = $(param);
    if (value != "" && element2.val() != ""
        && !(parseFloat(value) > parseFloat(element2.val()))) {
        flag = false;
    }
    return flag;
}, jQuery.validator.format("必须大于{0}"));
/**
 * 大于等于
 * param:比较控件name属性
 */
jQuery.validator.addMethod("ge", function (value, element, param) {
    var flag = true;
    var element2 = $(param);
    if (value != "" && element2.val() != ""
        && !(parseFloat(value) >= parseFloat(element2.val()))
    ) {
        flag = false;
    }
    return flag;
}, jQuery.validator.format("必须大于等于{0}"));
/**
 * 小于
 */
jQuery.validator.addMethod("lt", function (value, element, param) {
    var flag = true;
    var element2 = $(param);
    if (value != "" && element2.val() != ""
        && !(parseFloat(value) < parseFloat(element2.val()))) {
        flag = false;
    }
    return flag;
}, jQuery.validator.format("必须小于{0}"));
/**
 * 小于等于
 */
jQuery.validator.addMethod("le", function (value, element, param) {
    var flag = true;
    var element2 = $(param);
    if (value != "" && element2.val() != ""
        && !(parseFloat(value) <= parseFloat(element2.val()))) {
        flag = false;
    }
    return flag;
}, jQuery.validator.format("必须小于等于{0}"));

jQuery.validator.addMethod("isMobile", function (value, element) {
    var length = value.length;
    var mobile = /^0?(13|15|17|18|14)[0-9]{9}$/;
    return (length == 11 && mobile.exec(value));
}, "请输入正确的手机号码");


jQuery.validator.addMethod("date_ge", function (value, element, param) {
    var flag = true;
    var element2 = $(param);
    if (value != "" && element2.val() != ""
        && !(value >= element2.val())
    ) {
        flag = false;
    }
    return flag;
}, jQuery.validator.format("结束时间 不能 小于开始时间"));

jQuery.validator.addMethod("specchar", function (value, element) {
    //var regexp=/^[^#?$"\<\[\]%&\\\<\>]*$/;
    var regexp=/^[\w\u4e00-\u9fa5]*$/;
    console.log(value+'字符判断'+regexp.test( value));
    return this.optional(element) || regexp.test( value);
}, "不允许含有特殊字符");