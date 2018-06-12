$(function ()
{

	$(".tabsBox").each(function(){
		var w_h = $(window).height();
		var cur = $(this);
		cur.height(w_h-190);
		cur.attr("data-height",w_h-190);
		cur.slimScroll({
			color: '#a1b2bd',
	        height: cur.attr("data-height")
		});
	});
	$(window).resize(function(){
		$(".tabsBox").each(function(){
			var w_h = $(window).height();
			var cur = $(this);
			cur.height(w_h-190);
			cur.attr("data-height",w_h-190);
			cur.slimScroll({
				destroy: true
			});
			cur.slimScroll({
				color: '#a1b2bd',
		        height: cur.attr("data-height")
			});
		});
	});
	
	if($(".buttonFlag").val()=="0"){
		$( "button" ).button({
		      icons: {
		        primary: ""
		      }
		});
	}else{
		$( "button:first" ).button({
		      icons: {
		        primary: "ui-icon-plus"
		      }
		    }).next().button({
		      icons: {
		        primary: "ui-icon-circle-minus"
		      }
		    }).next().button({
			  icons: {
				   primary: "ui-icon-plus"
			  }
			}).next().button({
			  icons: {
				   primary: "ui-icon-minus"
			  }
			});
		$(".smallSearch").button({
			  icons:{
					primary:"ui-icon-search"
				}
		});
	}
    //搜索选择
    $(".search_sel span").text($(".search_sel select").find("option:selected").text());
    $(".search_sel select").change(function () 
    {
        var val = $(this).find("option:selected").text();
        $(".search_sel span").text(val);
        $(".menu_list").each(function ()
        {
            var cur = $(this);
            cur.find("dd:last").css('border-bottom', 'none');
        });
        /*$(".sec_tr").each(function(){
        $(this).find("td:eq(2)").css('text-indent','20px');
        });*/
        function createFrame(url) 
        {
            var s = '<iframe name="view_frame" scrolling="no" frameborder="0"    src="' + url + '" style="width:1100px;height:600px;"></iframe>';
            return s;
        }
    });
    $("#update_goodsInfoItemNo").focus(function(){
        $(this).removeClass("error");
        $(this).siblings("label").remove();
    });
});

function sec(e){
	var c_name = e.parents("tr").attr("class");
	var n_name = e.parents("tr").next("tr").attr("class");
	var cn = n_name.substring(7,13);
	var num = c_name.substring(12,13);
	var n = e.parents("tr").nextUntil("tr[class="+c_name+"]").length;
	var d = e.parents("tr").index();
	if(e.hasClass("open_sec")) {
		e.removeClass("open_sec").addClass("close_sec");
		for(var i=1;i<=n;i++) {
			var a = d+i;
			var tr = e.parents("tbody").find("tr:eq("+a+")");
			if($(tr).hasClass(cn)) {
				tr.show();
			};		
		};
	} else {
		 e.removeClass("close_sec").addClass("open_sec");
		 for(var i=1;i<=n;i++) {
				var a = d+i;
				var tr = e.parents("tbody").find("tr:eq("+a+")");
				if(tr.attr("class").substring(12,13) > num) {
					tr.hide();
					tr.find(".close_sec").removeClass("close_sec").addClass("open_sec");
				};
				
			};
	};
};

tabCounter = 2;
function addTab(title, url) 
{
    var tabTemplate = "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close' ></span></li>";
    var tabs = $("#tabs").tabs();
    var label = title || "Tab " + tabCounter, id = "tabs-" + tabCounter, li = $(tabTemplate .replace(/#\{href\}/g, 
    "#" + id).replace(/#\{label\}/g, label));
    tabs.find(".ui-tabs-nav").append(li);
    tabs.append("<div id='" + id + "'>" + createFrame(url) + "</div>");
    tabs.tabs("refresh");
    tabCounter++;
    tabs.bind("keyup", function (event) 
    {
        if (event.altKey && event.keyCode === $.ui.keyCode.BACKSPACE) 
        {
            var panelId = tabs.find(".ui-tabs-active").remove().attr( "aria-controls");
            $("#" + panelId).remove();
            tabs.tabs("refresh");
        }
    });
    tabs.delegate("span.ui-icon-close", "click", function () 
    {
        var panelId = $(this).closest("li").remove().attr("aria-controls");
        $("#" + panelId).remove();
        tabs.tabs("refresh");
    });
}
//验证特殊字符，将调试显示到页面中
function checkSpecSymb(inputobj,Tipobj){
	var regexp=new RegExp("[''\\[\\]<>?!]");
	if (regexp.test( $("#"+inputobj).val() ) ) {
		$("#"+inputobj).addClass( "ui-state-error" );
		updateTips( "输入的内容包含特殊字符!", $("."+Tipobj) );
		return false;
	}else {
		$("#"+inputobj).removeClass( "ui-state-error" );
		updateTipsSucc(null,$("."+Tipobj));
		return true;
	}
}


//验证特殊字符，将调试返回给调用者
function checkSpecSymbNew(inputobj){
	var regexp=new RegExp("[''\\[\\]<>?!]");
	if (regexp.test( $("#"+inputobj).val() ) ) {
		$("#"+inputobj).addClass( "ui-state-error" );
		updateTips( "输入的内容包含特殊字符!");
		return false;
	}else {
		$("#"+inputobj).removeClass( "ui-state-error" );
		updateTipsSucc(null);
		return true;
	}
}
//根据jq对象验证特殊字符，将调试显示到页面中
function checkSymbForJQ(inputobj,Tipobj){
	 var regexp=new RegExp("[''\\[\\]<>?!]");
	 if (regexp.test( $(inputobj).val() ) ) {
		 $(inputobj).addClass( "ui-state-error" );
         updateTips( "输入的内容包含特殊字符!", $(Tipobj) );
         return false;
     }else {
    	 $(inputobj).removeClass( "ui-state-error" );
     	 updateTipsSucc(null,$(Tipobj));
         return true;
     }
}
function checkSpecSymbAndDialog(inputobj,dialogName,tipValue){
	var regexp=new RegExp("[''\\[\\]<>?!]");
	 if (regexp.test( $("#"+inputobj).val() ) ) {
		 $("#"+inputobj).addClass( "ui-state-error" );
		 $("#inputTipValue").text(tipValue);
		 $("#"+dialogName).dialog(
			        {
			            resizable : false, height : 150, width : 270, modal : true, autoOpen : true, buttons : {
			                "确定" : function () 
			                {
			                    $(this).dialog("close");
			                }
			            }
		});
		$("#"+dialogName).dialog("open");
        return false;
    }
    else {
    	$("#"+inputobj).removeClass( "ui-state-error" );
        return true;
    }
}
//根据传过来的对象验证
function checkSymbAndDialog(inputobj,dialogName,tipValue){
	var regexp=new RegExp("[''\\[\\]<>?!]");
	 if (regexp.test( $(inputobj).val() ) ) {
		 $(inputobj).addClass( "ui-state-error" );
		 $("#inputTipValue").text(tipValue);
		 $(dialogName).dialog(
			        {
			            resizable : false, height : 150, width : 270, modal : true, autoOpen : true, buttons : {
			                "确定" : function () 
			                {
			                    $(this).dialog("close");
			                }
			            }
		});
		$(dialogName).dialog("open");
        return false;
    }
    else {
    	$(inputobj).removeClass( "ui-state-error" );
        return true;
    }
}
//根据传过来的对象验证长度
function checkLengthAndDialog(inputobj,dialogName,tipValue){
	 if ($(inputobj).val().trim().length<=0 || $(inputobj).val().trim()=='' ) {
		 $(inputobj).addClass( "ui-state-error" );
		 $("#inputTipValue").text(tipValue);
		 $(dialogName).dialog(
			        {
			            resizable : false, height : 150, width : 270, modal : true, autoOpen : true, buttons : {
			                "确定" : function () 
			                {
			                    $(this).dialog("close");
			                }
			            }
		});
		$(dialogName).dialog("open");
        return false;
    }
    else {
    	$(inputobj).removeClass( "ui-state-error" );
        return true;
    }
}

//根据传过来的JQ对象验证长度
function checkLengthForJQ(inputobj,Tipobj,tipValue){
	 if ($(inputobj).val().trim().length<=0 || $(inputobj).val().trim()=='' ) {
		 $(inputobj).addClass( "ui-state-error" );
		 updateTips( tipValue, $(Tipobj) );
	     return false;
	 }else {
		 $(inputobj).removeClass( "ui-state-error" );
	 	 updateTipsSucc(null,$(Tipobj));
	     return true;
	 }
}
	 
//根据传过来的对象验证是否为数字
function checkNumAndDialog(inputobj,dialogName,tipValue){
	 if (isNaN($(inputobj).val() ) ) {
		 $(inputobj).addClass( "ui-state-error" );
		 $("#inputTipValue").text(tipValue);
		 $(dialogName).dialog(
			        {
			            resizable : false, height : 150, width : 270, modal : true, autoOpen : true, buttons : {
			                "确定" : function () 
			                {
			                    $(this).dialog("close");
			                }
			            }
		});
		$(dialogName).dialog("open");
        return false;
    }
    else {
    	$(inputobj).removeClass( "ui-state-error" );
        return true;
    }
}
//更新错误提示框的状态
function updateTips( t, tip) 
{	
	if(null != tip){
		tip .text( t ) .addClass( "ui-state-highlight" );
	}
}
//更新错误提示框的状态
function updateTipsSucc(obj,tips) 
{
    $(tips) .text("") .removeClass( "ui-state-highlight" );
    $(tips).removeClass( "ui-state-highlight");
    $(obj).removeClass("ui-state-error");
}

/*验证是否已经存在的通用方法*/
/**
 * url  验证控制器
 * objName  字段名称
 * obj   input控件
 * objTips  提示框class名称
 * flag  是新添加  还是修改
 * oldObj 如果是修改 传过来旧值 如果输入的和旧值相同 就不验证
 * */
function checkExists(url,objName,obj,objTips,flag,oldObj){
	if(flag==0 && $(obj).val()!="" && $(obj).val()!=null){
		$.get(url+".htm?"+objName+"="+$(obj).val(),function(data){
			if(data==false){
                showError(obj);
				updateTips("名称或编号已经存在!",$("."+objTips));
				$(".checkExistsFlag").val(0);
			}else{
				updateTipsSucc($(obj),$("."+objTips));
                removeError(obj);
                $(".checkExistsFlag").val(1);
			}
		});
	}else if(flag==1 && $(obj).val()!="" && $(obj).val()!=null){
		if($("."+oldObj).val()!=$(obj).val()){
			$.get(url+".htm?"+objName+"="+$(obj).val(),function(data){
				if(data==false){
					//$(obj).addClass("ui-state-error");
                    showError(obj);
                    updateTips("名称或编号已经存在!",$("."+objTips));
					$(".checkExistsFlag").val(0);
				}else{
					updateTipsSucc($(obj),$("."+objTips));
                    removeError(obj);
                    $(".checkExistsFlag").val(1);
				}
			});
		}else{
			updateTipsSucc($(obj),$("."+objTips));
            $(obj).removeClass("error");
            $(".checkExistsFlag").val(1);
		}
	}
}

function showError(obj){
    $(obj).addClass("error");
    $(obj).parent("div").append("<label class='error'>货品编号已存在！</label>")
}

function removeError(obj){
    $(obj).removeClass("error");
}

/*验证货品是否可以添加*/
function selProductParam(objTips,flag,up_remark_id,sel_id){
    var type = $("#addOrUpdate").val();
	var params=$(".tb_select");
	/*标记为0表示新添加*/
	if(flag==0){
		var url="checkParam.htm?goodsId="+$("#goodsId").val()+"&paramLength="+$("#paramLength").val();
		for(var i=0;i<params.length;i++){
			url+="&paramIds="+$(params[i]).val();
		}
		$.ajax({
			type: 'post',
			url:url,
			async:false,
			success: function(data) {
				if(data){
					updateTipsSucc(null,$("."+objTips));
					$(".checkProdcutExists").val(1);
				}else{
					updateTips("所选参数已经生成货品,请重新选择!",$("."+objTips));
					$(".checkProdcutExists").val(0);
				}
			}
			});
		
	}else{
		/*如果是修改*/
		/*获取参数*/
		var valueRemark=$("#"+ sel_id +" option:selected").text();
		$("."+up_remark_id).attr("value",valueRemark);
		var updateSelParam=$(".updateSelParam");
		var oldparams=$(".oldParam");
		var oldParams=new Array(oldparams.length);
		/*循环获得的值*/
		for(var i=0;i<oldparams.length;i++){
			oldParams[i]=$(oldparams[i]).val();
		}
		/*拍戏*/
		oldParams.sort();
		/*获得选中的值*/
		var selParams=new Array(params.length);
		for(var i=0;i<updateSelParam.length;i++){
			selParams[i]=$(updateSelParam[i]).val();
		}
		/*排序*/
		selParams.sort();
		/*拼接成字符串并比较*/
		var oldParamStr="";
		var nowParamStr="";
		for(var i=0;i<oldParams.length;i++){
			oldParamStr+=oldParams[i];
		}
		for(var i=0;i<selParams.length;i++){
			nowParamStr+=selParams[i];
		}
		/*end*/
		/*如果选中的和选择的一样  就取消提示*/
		if(oldParamStr==nowParamStr && type != "0"){
			updateTipsSucc(null,$("."+objTips));
			$(".checkProdcutExists").val(1);
		}else{
			var url="checkParam.htm?goodsId="+$("#goodsId").val()+"&paramLength="+$("#paramLength").val();
			for(var i=0;i<updateSelParam.length;i++){
				url+="&paramIds="+$(updateSelParam[i]).val();
			}
			$.ajax({
				type: 'post',
				url:url,
				async:false,
				success: function(data) {
					if(data){
						updateTipsSucc(null,$("."+objTips));
						$(".checkProdcutExists").val(1);
					}else{
						updateTips("所选参数已经生成货品,请重新选择!",$("."+objTips));
						$(".checkProdcutExists").val(0);
						showTipAlert("所选参数已经生成货品,请重新选择!");
					}
				}
				});
		}
	}
}
/*验证长度*/
function checkLength( o, n, tip, min, max )
{
	if ( o.val().trim().length > max || o.val().trim().length < min )
	{
		if(min==32){
			o.addClass( "ui-state-error" );
			updateTips( n + " 长度必须是 " + max + "字符." , tip);
		}else{
			o.addClass( "ui-state-error" );
			updateTips( n + " 长度必须在 " + min + "字符 ~ " + max + "字符之间." , tip);
		}

		return false;
	}
	else {
		o.removeClass( "ui-state-error" );
		updateTipsSucc(null,tip);
		return true;
	}
}

/*验证长度 只返回成功或者失败*/
function checkLengthNew( o, n, min, max )
{
	if ( o.val().trim().length > max || o.val().trim().length < min )
	{
		if(min==32){
			o.addClass( "ui-state-error" );
			updateTips( n + " 长度必须是 " + max + "字符.");
		}else{
			o.addClass( "ui-state-error" );
			updateTips( n + " 长度必须在 " + min + "字符 ~ " + max + "字符之间.");
		}

		return false;
	}
	else {
		o.removeClass( "ui-state-error" );
		updateTipsSucc(null);
		return true;
	}
}

/*正则验证*/
function checkRegexp( o, regexp, n, tip )
{
	if ( !( regexp.test( o.val() ) ) ) {
		o.addClass( "ui-state-error" );
		updateTips( n, tip );
		return false;
	}
	else {
		o.removeClass( "ui-state-error" );
		updateTipsSucc(null,tip);
		return true;
	}
}

/*正则验证 new*/
function checkRegexpNew( o, regexp)
{
    if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        return false;
    }
    else {
    	o.removeClass( "ui-state-error" );
    	updateTipsSucc(null);
        return true;
    }
}

/*验证非空*/
function checkNull( obj,objTips ) 
{
    if ( $(obj).val().trim() == null || $(obj).val().trim() == "" ) {
    	obj.addClass( "ui-state-error" );
        updateTips( "不能输入空字符!", objTips );
        return false;
    }
    else {
    	obj.removeClass( "ui-state-error" );
    	updateTipsSucc(null,objTips);
        return true;
    }
}

/*初始化排序控件*/
function spin(){
	$(".sort_spinner").spinner({
		spin : function(event, ui) {
			 if (ui.value < 0) {
				$(this).spinner("value", 0);
				return false;
			}
		},
		step: 1,
	    numberFormat: "n"
	});
}
