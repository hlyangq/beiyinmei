/**
  * Created by youzipi on 16/10/18.
  */

/*global jQuery, alert,dialog*/

function cancelWithdraw(id) {
  dialog({
    title: '提示信息',
    content: '确定要取消提现?',
    width: 250,
    // height: 60,
    okValue: '确定',
    cancelValue: '再看看',
    ok: function () {
      cancelRequest(id);
    },
    cancel: function () {
      return true;
    }
  }).showModal();
}
function confirmWithdraw(id) {
  dialog({
    title: '提示信息',
    content: '确认收款后，冻结的预存款将被扣除，是否确认已收到打款？',
    // skin: 'warning',
    width: 250,
    // height: 60,
    okValue: '确定',
    cancelValue: '取消',
    ok: function () {
      confirmRequest(id);
    },
    cancel: function () {
      return true;
    }
  }).showModal();
}

/**
 * private
 * 
 */


function cancelRequest(id) {
  $.ajax({
    url: '/deposit/withdraw/cancel.htm',
    type: 'post',
    data: {'id': id},
  }).done(function (data) {
    switch (data.code) {
      case '0':
        location.href = '/deposit/withdraw-list.htm';
        break;
      case '1':
        dialog({
          title: '提示信息',
          content: data.msg,
          // skin: 'warning',
          width: 250,
          height: 60,
          okValue: '确定',
          ok: function () {
            return true;
          }
        }).showModal();
        break;

    }
  });
}

function confirmRequest(id) {
  $.ajax({
    url: '/deposit/withdraw/confirm.htm',
    type: 'post',
    data: {'id': id},
  }).done(function (data) {
    switch (data.code) {
      case '0':
        location.href = '/deposit/withdraw-list.htm';
        break;
      case '1':
        dialog({
          title: '提示信息',
          content: data.msg,
          // skin: 'warning',
          width: 250,
          height: 60,
          okValue: '确定',
          ok: function () {
            return true;
          }
        }).showModal();
        break;
    }
  });
}