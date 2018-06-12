package com.ningpai.site.customer.deposit.service;

import com.ningpai.deposit.bean.Deposit;
import com.ningpai.deposit.bean.Trade;
import com.ningpai.deposit.bean.TradeExample;
import com.ningpai.deposit.mapper.TradeMapper;
import com.ningpai.site.customer.deposit.bean.TradeConst;
import com.ningpai.util.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang on 2016/9/19.
 */
@Service
public class TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    //引用总账服务，充值时会调用
    @Autowired
    private SiteDepositService depositService;


    public PageBean pageTrade(Map<String, Object> paramMap, PageBean pb) {
        Long count = tradeMapper.tradeCount(paramMap);
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("pageSize", pb.getPageSize());
        List<Object> result = tradeMapper.tradeList(paramMap);
        pb.setList(result);
        return pb;
    }


    @Transactional
    public Integer updateTradeStatusByOrderCode(Map<String, Object> paramMap) {
        Integer result = this.tradeMapper.updateTradeByOrderCode(paramMap);
        return result;
    }

    @Transactional
    public Integer updateById(Map<String, Object> paramMap) {
        Integer result = tradeMapper.updateById(paramMap);
        return result;
    }

    @Transactional
    public Integer updateByIdAndStatus(Trade trade, Long id,Long customerId, String curStatus) {
        TradeExample example = new TradeExample();
        example.createCriteria().andIdEqualTo(id)
                .andCustomerIdEqualTo(customerId)
                .andOrderStatusEqualTo(curStatus);
        Integer result = tradeMapper.updateByExampleSelective(trade, example);
        return result;
    }

    /**
     * 在线充值之后的回调更新
     *
     * @param paramMap
     */
    @Transactional
    public void recharge(Map<String, Object> paramMap) {
        //paramMap,{customerId="xxx",status="6",orderCode="R1216446465"}
        BigDecimal orderPrice = null;
        Deposit deposit = null;
        //if(orderPrice != null){
        Map<String, Object> qParam = new HashMap<>();
        qParam.put("customerId", paramMap.get("customerId"));
        //查询总账信息
        deposit = depositService.getDeposit(qParam);
        //}

        Object _orderCode = paramMap.get("orderCode");
        String orderCode = String.valueOf(_orderCode);
        Trade trade = null;
        if (StringUtils.isNotBlank(orderCode)) {
            trade = tradeMapper.findByOrderCodeAndCustomerId(paramMap);
        }

        String _staus = null;
        if (trade != null) {
            _staus = trade.getOrderStatus();
            //非充值中状态，直接返回
            if(!TradeConst.TYPE_ORDER_STATUS_RECHARGING.equals(_staus)){
                return;
            }
        }

        BigDecimal preDeposit = BigDecimal.ZERO;
        if (deposit != null) {
            preDeposit = deposit.getPreDeposit();
        }
        //更新状态将trade的status改为6
        if (trade != null) {//获取充值金额
            orderPrice = trade.getOrderPrice();
            Map<String, Object> tParam = new HashMap<>();
            tParam.put("customerId", paramMap.get("customerId"));
            tParam.put("orderCode", orderCode);
            tParam.put("status", "6");
            BigDecimal currentPrice = preDeposit;
            currentPrice = currentPrice.add(orderPrice);
            tParam.put("currentPrice", currentPrice);
            //更新状态和currentPrice,将当前余额也保存在trade表中
            this.tradeMapper.updateTradeByOrderCode(tParam);
        }

        //更新一下总账信息
        if (deposit != null) {
            Map<String, Object> tParam = new HashMap<>();
            tParam.put("customerId", paramMap.get("customerId"));
            preDeposit = deposit.getPreDeposit();
            //当前总账的可用余额加上，这一笔网上充值的金额，后更新总账
            preDeposit = preDeposit.add(orderPrice);
            tParam.put("preDeposit", preDeposit);
            //更新总账
            depositService.updateDeposit(tParam);
        }
    }

    @Transactional
    public Integer saveTrade(Trade trade) {
        Integer result = this.tradeMapper.saveTrade(trade);
        return result;
    }

    public Trade findByOrderCodeAndCustomerId(Map<String, Object> paramMap) {
        //paramMap,{customerId="xxx",orderCode="R1216446465"}
        Trade trade = tradeMapper.findByOrderCodeAndCustomerId(paramMap);
        return trade;
    }

    public Trade findByOrderCode(String orderCode) {
        //paramMap,{customerId="xxx",orderCode="R1216446465"}
        Trade trade = tradeMapper.findByOrderCode(orderCode);
        return trade;
    }

    public Trade findById(Long id) {
        Trade trade = tradeMapper.selectById(id);
        return trade;
    }


    public Trade findByIdAndCustomerId(Long id, Long customerId) {
        TradeExample example = new TradeExample();
        example.createCriteria().andIdEqualTo(id)
                .andCustomerIdEqualTo(customerId);
        return tradeMapper.selectByExample(example).get(0);
    }
}
