package com.ningpai.customer.dao.impl;

import com.ningpai.customer.bean.TradeInfo;
import com.ningpai.customer.dao.TradeInfoMapper;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 会员预存款明细/交易明细数据层实现
 * Created by CHENLI on 2016/9/18.
 */
@Repository
public class TradeInfoMapperImpl extends BasicSqlSupport implements TradeInfoMapper {
    /**
     * 查询会员预存款明细/交易明细列表总条数，用于分页
     *
     * @param depositInfoVo
     * @return
     */
    @Override
    public Long selectTotalTradeInfo(DepositInfoVo depositInfoVo) {
        return this.selectOne("com.ningpai.customer.dao.TradeInfoMapper.selectTotalTradeInfo",depositInfoVo);
    }

    /**
     * 查询会员预存款明细/交易明细列表
     *
     * @param depositInfoVo if 如果有customerID，则查询该会员所有交易明细
     *                      else 查询平台所有明细
     * @return List<Object>
     */
    @Override
    public List<Object> selectTradeInfoList(DepositInfoVo depositInfoVo) {
        return this.selectList("com.ningpai.customer.dao.TradeInfoMapper.selectTradeInfoList",depositInfoVo);
    }

    /**
     * 增加一条交易记录
     *
     * @param tradeInfo
     * @return
     */
    @Override
    public int insertTradeInfo(TradeInfo tradeInfo) {
        return this.insert("com.ningpai.customer.dao.TradeInfoMapper.insertTradeInfo",tradeInfo);
    }

    /**
     * 批量更新审核提现单，修改提现单状态
     * @param tradeInfo
     * @return
     */
    @Override
    public int batchUpdateTradeInfo(TradeInfo tradeInfo) {
        return this.update("com.ningpai.customer.dao.TradeInfoMapper.batchUpdateTradeInfo",tradeInfo);
    }

    /**
     * 根据ID查询交易信息
     *
     * @param depositInfoVo
     * @return
     */
    @Override
    public TradeInfo getTradeInfoById(DepositInfoVo depositInfoVo) {
        return this.selectOne("com.ningpai.customer.dao.TradeInfoMapper.selectTradeInfoList",depositInfoVo);
    }

    /**
     * 审核提现单，修改提现单状态
     * @param tradeInfo
     * @return
     */
    @Override
    public int updateTradeInfo(TradeInfo tradeInfo) {
        return this.update("com.ningpai.customer.dao.TradeInfoMapper.updateTradeInfo",tradeInfo);
    }
}
