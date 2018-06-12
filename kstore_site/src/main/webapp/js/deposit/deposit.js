(function ($) {
    $.deposit = { };
    var restpath = '/deposit/';
    $.deposit.nameEx = 'depositApi';
    $.extend($.deposit,
        {
            //账户详情
            depositDetail:function(){

            }
        }
    );
})(jQuery);


(function ($) {
    $.trade = { };
    var restpath = '/';
    $.trade.nameEx = 'tradeApi';
    $.extend($.trade,
        {
            //交易记录
            tradeList:function(before3month){
                var self = this;
                var promise = $.ajax({
                    url:restpath + "tradelist",
                    data:{
                        'before3month':before3month||'',
                    }
                });
                return promise;
            }
        }
    );
})(jQuery);
