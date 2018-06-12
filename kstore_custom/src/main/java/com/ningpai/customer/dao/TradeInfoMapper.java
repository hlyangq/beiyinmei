package com.ningpai.customer.dao;

import com.ningpai.customer.bean.TradeInfo;
import com.ningpai.customer.vo.DepositInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 会员预存款明细/交易明细数据层接口
 * Created by CHENLI on 2016/9/18.
 */
public interface TradeInfoMapper {
    /**
     * 查询会员预存款明细/交易明细列表总条数，用于分页
     * @param depositInfoVo
     * @return
     */
    Long selectTotalTradeInfo(DepositInfoVo depositInfoVo);

    /**
     * 查询会员预存款明细/交易明细列表
     * @param depositInfoVo
     * if 如果有customerID，则查询该会员所有交易明细
     * else 查询平台所有明细
     * @return  List<Object>
     */
    List<Object> selectTradeInfoList(DepositInfoVo depositInfoVo);

    /**
     * 增加一条交易记录
     * @param tradeInfo
     * @return
     */
    int insertTradeInfo(TradeInfo tradeInfo);

    /**
     * 审核提现单，修改提现单状态
     * @param tradeInfo
     * @return
     */
    int updateTradeInfo(TradeInfo tradeInfo);
    /**
     * 批量更新审核提现单，修改提现单状态
     * @param tradeInfo
     * @return
     */
    int batchUpdateTradeInfo(TradeInfo tradeInfo);

    /**
     * 根据ID查询交易信息
     * @param depositInfoVo
     * @return
     */
    TradeInfo getTradeInfoById(DepositInfoVo depositInfoVo);

}
