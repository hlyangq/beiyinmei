package com.ningpai.scheduling;

import com.ningpai.customer.bean.TradeInfo;
import com.ningpai.customer.service.TradeInfoService;
import com.ningpai.deposit.service.DepositService;
import com.ningpai.deposit.service.WithdrawService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DepositTask {

    private Logger LOGGER = Logger.getLogger(DepositTask.class);

    @Autowired
    DepositService depositService;

    @Autowired
    WithdrawService withdrawService;

    @Autowired
    TradeInfoService tradeInfoService;

    //59 59 23 ? * *

    /**
     * 每天24点将密码错误次数重置为0
     */
    @Scheduled(cron = "59 59 23 ? * *")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void resetDepositPasswordCount() {
        LOGGER.info("===resetDepositPasswordCount===");
        depositService.resetDepositPasswordCount();
    }

    @Scheduled(cron = "59 59 23 ? * *")
    public void confirmWithdraw() {
        Integer result = withdrawService.confirmTask();
        LOGGER.info("========提现 自动确认收款=========共=" + result + "条");
    }
}
