package com.ningpai.m.deposit.service;

import com.ningpai.m.deposit.bean.DepositInfo;
import com.ningpai.m.deposit.bean.TradeInfo;
import com.ningpai.m.deposit.vo.TradeInfoVo;
import com.ningpai.util.PageBean;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 预存款交易记录service
 * Created by chenpeng on 2016/10/8.
 */
public interface TradeInfoService {

    /**
     * 分页查询用户预存款交易记录
     * @param pageBean 分页
     * @param map 查询条件
     * @return 分页
     */
    PageBean queryTradeInfo(PageBean pageBean, Map<String, Object> map);

    /**
     * 生成预付款交易记录
     * @param customerId 用户id
     * @param orderPrice 交易金额
     * @param orderType 交易类型（0在线充值 1订单退款 2线下提现 3订单消费）
     * @return 预付款交易记录
     */
    TradeInfo insertSelective(Long customerId, BigDecimal orderPrice,String orderType,String payType,String remark, DepositInfo depositInfo);

    /**
     * 根据交易编号查询交易记录
     * @param tradeNo 交易编号
     * @return
     */
    TradeInfo selectByTradeNo(String tradeNo);

    int updateTradeInfo(TradeInfo tradeInfo);

    /**
     * 生成预付款交易记录
     * @param tradeInfo 预存款
     * @return 预付款交易记录
     */
    int insertTradeInfo(TradeInfo tradeInfo);

    /**
     * 分页查询用户预存款提现交易记录
     * @param pageBean 分页
     * @param map 查询条件
     * @return 分页
     */
    PageBean queryWithdraw(PageBean pageBean, Map<String, Object> map);

    /**
     * 根据交易记录id查询交易记录信息
     * @param id 交易记录id
     * @return 交易记录详细
     */
    TradeInfoVo queryTradeInfoById(Long id);

    /**
     * 修改提现状态（取消申请/确认收款）
     * @param customerId 用户id
     * @param tradeInfoId 交易记录id
     * @param type 修改类型（0取消申请1确认收款）
     * @return true：成功 false: 失败
     */
    boolean updateTradeInfoStatus(Long customerId, Long tradeInfoId, String type);

    /**
     * 根据交易记录id和会员id查询
     * @param id 交易记录id
     * @param customerId 会员id
     * @return 交易记录详细
     */
    TradeInfoVo queryByIdAndCusId(Long id,Long customerId);

}
