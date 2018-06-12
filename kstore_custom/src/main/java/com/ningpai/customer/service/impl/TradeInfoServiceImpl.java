package com.ningpai.customer.service.impl;

import com.ningpai.customer.bean.TradeInfo;
import com.ningpai.customer.dao.TradeInfoMapper;
import com.ningpai.customer.service.TradeInfoService;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.deposit.mapper.TradeMapper;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 会员预存款明细/交易明细服务层
 * Created by CHENLI on 2016/9/18.
 */
@Service
public class TradeInfoServiceImpl implements TradeInfoService {
    /**
     * 日志
     * */
    public static final MyLogger LOGGER = new MyLogger(DepositInfoServiceImpl.class);
    @Autowired
    private TradeInfoMapper tradeInfoMapper;

    /**
     * 查询会员预存款明细/交易明细列表总条数，用于分页
     *
     * @param depositInfoVo
     * @return
     */
    @Override
    public Long selectTotalTradeInfo(DepositInfoVo depositInfoVo) {
        Long result = null;
        LOGGER.info("查询会员预存款明细总数据条数...");
        try {
            result = tradeInfoMapper.selectTotalTradeInfo(depositInfoVo);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return result;
    }

    /**
     * 查询会员预存款明细/交易明细列表
     *
     * @param depositInfoVo if 如果有customerID，则查询该会员所有交易明细
     *                      else 查询平台所有明细
     * @param pageBean
     * @return PageBean
     */
    @Override
    public PageBean selectTradeInfoList(DepositInfoVo depositInfoVo, PageBean pageBean) {
        //会员预存款明细/交易明细列表总条数
        Long rowNos = selectTotalTradeInfo(depositInfoVo);
        pageBean.setRows(rowNos.intValue());

        depositInfoVo.setStartRowNum(pageBean.getStartRowNum());
        depositInfoVo.setEndRowNum(pageBean.getEndRowNum());
        List<Object> tradeInfoList;
        try {
            LOGGER.info("查询会员预存款明细列表...");
            tradeInfoList =tradeInfoMapper.selectTradeInfoList(depositInfoVo);
            if (CollectionUtils.isNotEmpty(tradeInfoList)){
                pageBean.setList(tradeInfoList);
            }
            pageBean.setObjectBean(depositInfoVo);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return pageBean;
    }

    /**
     * 增加一条交易记录
     *
     * @param tradeInfo
     * @return
     */
    @Override
    @Transactional
    public int insertTradeInfo(TradeInfo tradeInfo) {

        return tradeInfoMapper.insertTradeInfo(tradeInfo);
    }

    /**
     * 审核提现单，修改提现单状态
     *
     * @param tradeInfo
     * @return
     */
    @Override
    @Transactional
    public int updateTradeInfo(TradeInfo tradeInfo) {
        tradeInfo.setUpdateTime(new Date());
        tradeInfo.setTradeSource("提现");

        return tradeInfoMapper.updateTradeInfo(tradeInfo);
    }
    /**
     * 批量更新审核提现单，修改提现单状态
     *
     * @param tradeInfo
     * @return
     */
    @Override
    @Transactional
    public int batchUpdateTradeInfo(TradeInfo tradeInfo) {
        tradeInfo.setUpdateTime(new Date());
        tradeInfo.setTradeSource("提现");
        return tradeInfoMapper.batchUpdateTradeInfo(tradeInfo);
    }

    /**
     * 根据ID查询交易信息
     *
     * @param tradeInfoId
     * @return
     */
    @Override
    public TradeInfo getTradeInfoById(Long tradeInfoId) {
        TradeInfo tradeInfo = new TradeInfo();
        try{
            LOGGER.info("根据ID查询交易信息");
            DepositInfoVo depositInfo = new DepositInfoVo();
            depositInfo.setTradeInfoId(tradeInfoId);
            depositInfo.setEndRowNum(1);
            tradeInfo = tradeInfoMapper.getTradeInfoById(depositInfo);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return tradeInfo;
    }
}
