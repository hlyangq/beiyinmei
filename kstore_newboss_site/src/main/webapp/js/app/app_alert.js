/**
 * 提示框
 * @param tip 提示内容
 */
function showTipAlert(tip) {
    $("#modalDialog").remove();
    var dialogHtml =
        '<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" style="z-index:99999;">'+
        '    <div class="modal-dialog">'+
        '        <div class="modal-content">'+
        '            <div class="modal-header">'+
        '                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
        '               <h4 class="modal-title">操作提示</h4>'+
        '           </div>'+
        '           <div class="modal-body" style="text-align: center;">'+
        tip+
        '           </div>'+
        '           <div class="modal-footer">'+
        '               <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="$(\'#modalDialog\').modal(\'hide\');">确定</button>'+
        '           </div>'+
        '       </div>'+
        '   </div>'+
        '</div>';
    $(document.body).append(dialogHtml);
    $('#modalDialog').modal('show');
}
/**
 * 简单的确认框，返回true或false
 */
function showConfirmAlert(tips,functionName) {
    $('#modalDialog').remove();
    var confirmDialog =
        '<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog">'+
        '    <div class="modal-dialog">'+
        '        <div class="modal-content">'+
        '            <div class="modal-header">'+
        '                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
        '               <h4 class="modal-title">操作提示</h4>'+
        '           </div>'+
        '           <div class="modal-body">'
        +tips+
        '           </div>'+
        '           <div class="modal-footer">'+
        '             <button type="button" class="btn btn-primary" onclick="'+functionName+'">确定</button>'+
        '               <button type="button" class="btn btn-default" data-dismiss="modal" onclick="$(\'#modalDialog\').modal(\'hide\');">关闭</button>'+
        '           </div>'+
        '       </div>'+
        '   </div>'+
        '</div>';
    $(document.body).append(confirmDialog);
    $('#modalDialog').modal('show');
}
