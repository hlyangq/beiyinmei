package com.ningpai.deposit.service;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.deposit.bean.*;
import com.ningpai.deposit.mapper.TradeMapper;
import com.ningpai.util.web.ReturnJsonBuilder;
import com.ningpai.util.web.ReturnJsonBuilderFactory;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by youzipi on 16/10/14.
 */
@Service
public class WithdrawService {

    private Logger LOGGER = Logger.getLogger(WithdrawService.class);

    @Autowired
    TradeMapper tradeMapper;

    @Autowired
    DepositService depositService;

    /**
     * 确认收款
     *
     * @param id
     * @return
     */
    @Transactional
    public JSONObject confirm(Long id) {

        Trade trade = tradeMapper.selectById(id);

        if (trade == null || trade.getOrderStatus().equals(TradeStatus.REMITTED)) {
            return ReturnJsonBuilderFactory
                    .builder()
                    .code("1")
                    .msg("确认收款失败")
                    .build();
        }

        Long customerId = trade.getCustomerId();
        Deposit deposit = depositService.findByCustomerId(customerId);

        // 消耗 冻结预存款
        consumeDeposit(deposit, trade.getOrderPrice());

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", TradeStatus.CONFIRMED);
        BigDecimal currentPrice = deposit.getFreezePreDeposit()
                .add(deposit.getPreDeposit())
                .subtract(trade.getOrderPrice());
        map.put("currentPrice", currentPrice);
        Integer result = tradeMapper.updateById(map);

        return new ReturnJsonBuilder()
                .code("0")
                .msg("success")
                .build();
    }

    /**
     * 定时 确认 提现申请
     *
     * @return
     */
    public Integer confirmTask() {
        TradeExample example = new TradeExample();
        example.createCriteria()
                .andOrderStatusEqualTo(TradeStatus.REMITTED)
                .andOrderTypeEqualTo(TradeType.WITHDRAW)
                .andUpdateTimeLessThan(DateUtils.addDays(new Date(), -7));
//        del_flag ??
        Trade newTrade = new Trade();
        newTrade.setOrderStatus(TradeStatus.CONFIRMED);
        List<Trade> tradeList = tradeMapper.selectByExample(example);
        int count = 0;
        for (Trade trade : tradeList) {
            JSONObject returnJson = confirm(trade.getId());
            if (returnJson.get("code").equals("0")) {
                count++;
            }
        }
        LOGGER.info("[定时任务]预存款提现,自动确认收款,共更新交易记录" + tradeList.size() + "条");
        LOGGER.info("[定时任务]成功" + count + "条");
        return count;
    }


    public Integer updateByIdAndStatus(Trade trade,Long id,String status){
        TradeExample example = new TradeExample();
        example.createCriteria()
                .andIdEqualTo(id)
                .andOrderStatusEqualTo(status);
        return tradeMapper.updateByExample(trade,example);
    }


    /**
     * 消耗 冻结预存款 【确认收款】
     *
     * @param deposit
     * @param amount
     */
    protected Integer consumeDeposit(Deposit deposit, BigDecimal amount) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", deposit.getId());
        BigDecimal freezePreDeposit = deposit.getFreezePreDeposit().subtract(amount);
        paramMap.put("freezePreDeposit", freezePreDeposit);
        if(freezePreDeposit.compareTo(BigDecimal.ZERO) == 0){
            paramMap.put("freezePreDeposit", "0");
        }
            return depositService.updateDeposit(paramMap);
    }

}
