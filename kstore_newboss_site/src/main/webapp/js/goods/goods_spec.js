var  path = $("#mypath").val();
$(function(){

    $("#saveSpecForm").validate();
    $("#updateSpecDetailForm").validate();
    /* 为选定的select下拉菜单开启搜索提示 */
    $('select[data-live-search="true"]').select2();
    /* 为选定的select下拉菜单开启搜索提示 END */

    /* 下面是表单里面的填写项提示相关的 */
    $('.guigemingchen').popover({
        content : '展示商品详情页规格名称(为了前台样式对齐比如:颜色;版本;两个字组合为标准)',
        trigger : 'hover'
    });
    $('.guigebeizhu').popover({
        content : '在后台添加商品类型时使用',
        trigger : 'hover'
    });

    /* 双击编辑分类 */
    $('.cate_item').dblclick(function(){
        $('#cateEdit').modal('show');
    });

});

function showEditSpec(specId) {
    /*ajax 通过ID查询并塞值到修改页面*/
    $.get("queryCateVoById.htm?CSRFToken="+$("#CSRFToken").val()+"&specId=" + specId, function (data)
    {
        $("#update_specId").val(data.specId);
        $("#update_spec_name").val(data.specName);
        $("#update_remark").val(data.specRemark);
        $("#update_nickname").val(data.specNickname);
        $("#specShowmode"+data.specShowmode).click();
    });
    $("#editSpecification").modal("show");
}

var detailIndex = 0;
function querySpecDetail(specId) {

    $("#specId").val(specId);
    /*ajax 通过ID查询并塞值到修改页面*/
    $.get("queryCateVoById.htm?CSRFToken="+$("#CSRFToken").val()+"&specId=" + specId, function (data) {
        /*设置规格值*/
        var html = "";
        for (var i = 0; i < data.specDetails.length; i++) {
            detailIndex ++;
            html+=
                '<tr id="spec_detail_'+data.specDetails[i].specDetailId+'">'+
                '   <input type="hidden" name="detail_specId" class="update_specId" value="' + data.specDetails[i].specId + '"/>'+
                '   <input type="hidden" name="specDetailId" value="' + data.specDetails[i].specDetailId + '"/>'+
                '   <input type="hidden" name="specDetailDelflag" class="update_specDetailDelFlag" value="' + data.specDetails[i].specDetailDelflag + '"/>'+
                '   <td><input type="text" class="form-control required specstr" name="specDetailName" value="'+ data.specDetails[i].specDetailName + '"></td>'+
                '   <td><input type="text" class="form-control digits required" name="specDetailSort" value="' + data.specDetails[i].specDetailSort + '" id="specDetailSort'+detailIndex+'"></td>'+
                '   <td><button type="button" class="btn btn-default" onclick="deleteOneSpecDetail(\'_'+data.specDetails[i].specDetailId+'\')">删除</button></td>'+
                '</tr>';
        }
        $("#spec_detail_table tbody").html(html);
        $('#manageSpecification').modal('show')
    });
}

function addOneSpecDetail() {
    detailIndex ++;
    var specId = $("#specId").val();
   var  html =
        '<tr id="spec_detail'+detailIndex+'">'+
        '   <input type="hidden" name="detail_specId" class="update_specId" value="' +specId + '"/>'+
        '   <input type="hidden" name="specDetailId" value="-1"/>'+
        '   <input type="hidden" name="specDetailDelflag" class="update_specDetailDelFlag" value="0"/>'+
        '   <td><input type="text" class="form-control required specstr" name="specDetailName"></td>'+
        '   <td><input type="text" class="form-control required digits" name="specDetailSort" value="0" id="specDetailSort'+detailIndex+'"></td>'+
        '   <td><button type="button" class="btn btn-default" onclick="deleteOneSpecDetail('+detailIndex+')">删除</button></td>'+
        '</tr>';
    $("#spec_detail_table tbody").append(html);
}


function deleteOneSpecDetail(index) {

    if($("#spec_detail"+index).find("input[name=specDetailId]").val()=='-1') {
        $("#spec_detail"+index).remove();
    } else {
        var id = index.substr(1);
        // 是否可以删除的标识
        var result = 0;
        // 这边是说 删除以后的 则需要判断 是否可以删除
        $.ajax({
            url: path + "/isspecvaluecandelete.htm?specvalueId=" + id,
            async: false,
            success: function (data) {
                if (data == 1) {
                    result = data;
                }
            }
        });

        // 1表示不能删除
        if (result == 1) {
            showTipAlert('该规格值已经关联了商品无法删除');
            return;
        }


        $("#spec_detail"+index).hide();
        $("#spec_detail"+index).find("input[name=specDetailDelflag]").val(1);
    }
}

function batchDeleteSpecDetail() {

    var id = $("#specId").val();

    // 是否可以删除的标识
    var result = 0;
    // 这边是说 删除以后的 则需要判断 是否可以删除
    $.ajax({
        url: path + "/isspeccandeleteone.htm?specId=" + id,
        async: false,
        success: function (data) {
            if (data == 1) {
                result = data;
            }
        }
    });

    // 1表示不能删除
    if (result == 1) {
        showTipAlert('删除的规格值中有规格值已经被商品关联无法删除');
        return;
    }



    $("input[name=specDetailId]").each(function () {
        if($("#spec_detail_"+$(this).val()).find("input[name=specDetailId]").val()=='-1') {
            $("#spec_detail_"+$(this).val()).remove();
        } else {
            $("#spec_detail_"+$(this).val()).hide();
            $("#spec_detail_"+$(this).val()).find("input[name=specDetailDelflag]").val(1);
        }
    });
}

function specBatcDelete(deleteFormId, name) {
    var chestr="";
    var checkboxs = $("input[name="+name+"]");
    var oneSelect = false;
    for(var j = 0; j < checkboxs.length; j++) {
        if($(checkboxs[j]).is(':checked')==true) {
            oneSelect = true;
            chestr+=checkboxs[j].value+","
        }
    }
    if(!oneSelect) {
        showTipAlert("请至少选择一条记录！");
        return;
    }

    // 是否可以删除的标识
    var result = 0;

    // ajax 调用接口 判断商品品牌是否关联了商品
    $.ajax({
        url: path+"/isspeccandelete.htm?specIds=" + chestr,
        async: false,
        success: function (data) {
            if (data == 1) {
                result = data;
            }
        }
    });

    // 1表示不能删除
    if (result == 1) {
        showTipAlert('删除的规格中有规格的规格值已经关联了商品无法删除');
        return;
    }

    showDeleteBatchConfirmAlert(deleteFormId,name);
}

function specDel(deleteUrl,id)
{

    // 是否可以删除的标识
    var result = 0;

    // ajax 调用接口 判断商品品牌是否关联了商品
    $.ajax({
        url: path+"/isspeccandeleteone.htm?specId=" + id,
        async: false,
        success: function (data) {
            if (data == 1) {
                result = data;
            }
        }
    });

    // 1表示不能删除
    if (result == 1) {
        showTipAlert('删除的规格有规格值已经关联了商品无法删除');
        return;
    }

    showDeleteOneConfirmAlert(deleteUrl);

}
