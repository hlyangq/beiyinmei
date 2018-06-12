package com.ningpai.deposit.mapper;

import com.ningpai.deposit.bean.Trade;
import com.ningpai.deposit.bean.TradeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeMapper {
    int countByExample(TradeExample example);

    int deleteByExample(TradeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Trade record);

    int insertSelective(Trade record);

    List<Trade> selectByExample(TradeExample example);

    Trade selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Trade record, @Param("example") TradeExample example);

    int updateByExample(@Param("record") Trade record, @Param("example") TradeExample example);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKey(Trade record);


    /**
     * 条件查询交易记录
     * @param paramMap
     * @return
     */
    List<Object> tradeList(Map<String, Object> paramMap);

    /**
     * 计数
     * @param paramMap
     * @return
     */
    Long tradeCount(Map<String, Object> paramMap);


    /**
     * 更新状态
     * @param paramMap
     * @return
     */
    int updateTradeByOrderCode(Map<String, Object> paramMap);

    /**
     * 新增一个交易记录
     * @param trade
     * @return
     */
    int saveTrade(Trade trade);

    /**
     * 订单Id,[customerId]查找交易记录
     * @param paramMap
     * @return
     */
    Trade findByOrderCodeAndCustomerId(Map<String, Object> paramMap);

    /**
     * 订单Id 查找交易记录
     * @param orderCode
     * @return
     */
    Trade findByOrderCode(String orderCode);

    Trade selectById(Long id);

    Integer updateById(Map<String, Object> paramMap);
}