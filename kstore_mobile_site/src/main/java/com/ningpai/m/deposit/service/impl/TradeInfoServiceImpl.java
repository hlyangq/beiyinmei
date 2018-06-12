package com.ningpai.m.deposit.service.impl;

import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.deposit.bean.DepositInfo;
import com.ningpai.m.deposit.bean.TradeInfo;
import com.ningpai.m.deposit.mapper.DepositInfoMapper;
import com.ningpai.m.deposit.mapper.TradeInfoMapper;
import com.ningpai.m.deposit.service.TradeInfoService;
import com.ningpai.m.deposit.vo.TradeInfoVo;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.wxpay.utils.TenpayUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 预存款service实现
 *
 * Created by chenpeng on 2016/10/8.
 */
@Service("tradeInfoServiceM")
public class TradeInfoServiceImpl implements TradeInfoService {

    public static final MyLogger LOGGER = new MyLogger(TradeInfoServiceImpl.class);

    /**
     *
     */
    @Resource(name = "TradeInfoMapper")
    private TradeInfoMapper tradeInfoMapper;

    @Resource(name = "DepositInfoMapperM")
    private DepositInfoMapper depositInfoMapper;
    /**
     * 分页查询用户预存款交易记录
     *
     * @param pageBean 分页
     * @param map      查询条件
     * @return 分页
     */
    @Override
    public PageBean queryTradeInfo(PageBean pageBean, Map<String, Object> map) {
        pageBean.setPageSize(8);
        pageBean.setRows(tradeInfoMapper.queryTradeInfoPageRows(map).intValue());
        map.put(CustomerConstants.STARTNUM, pageBean.getStartRowNum());
        map.put(CustomerConstants.ENDNUM, pageBean.getEndRowNum());
        //分页查询用户预存款交易记录
        pageBean.setList(tradeInfoMapper.queryTradeInfoPage(map));
        return pageBean;
    }

    /**
     * 生成预付款交易记录
     *
     * @param customerId 用户id
     * @param orderPrice 交易金额
     * @param orderType  交易类型（0在线充值 1订单退款 2线下提现 3订单消费）
     * @return 预付款交易记录
     */
    @Override
    public TradeInfo insertSelective(Long customerId, BigDecimal orderPrice, String orderType,String payType,String remark, DepositInfo depositInfo) {
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setCustomerId(customerId);
        tradeInfo.setOrderPrice(orderPrice);
        tradeInfo.setOrderType(orderType);
        tradeInfo.setOrderCode(genenrateTradeCode(payType));
        tradeInfo.setCreatePerson(customerId);
        tradeInfo.setCreateTime(new Date());
        tradeInfo.setDelFlag("0");
        tradeInfo.setCurrentPrice(depositInfo.getPreDeposit().add(depositInfo.getFreezePreDeposit()));
        if("0".equals(orderType)){
            tradeInfo.setOrderStatus("5");
        }else if("2".equals(orderType)){
            tradeInfo.setOrderStatus("0");
        }
        if (StringUtils.isNotEmpty(remark)){
            tradeInfo.setTradeRemark(remark);
        }

        if (tradeInfoMapper.insertSelective(tradeInfo) > 0)
            return tradeInfo;
        return null;
    }

    /**
     * 预付款交易单号
     * @param orderType 交易蕾西
     * @return 交易单号
     */
    private String genenrateTradeCode(String orderType){
        String tradeCode ="";
        // 四位随机数
        String nonceStr = TenpayUtil.buildRandom(4) + "";
        // 年月日时分秒
        String currTime = TenpayUtil.getCurrTime();
        //充值单
        if ("0".equals(orderType)){
            tradeCode = "R" + currTime + nonceStr;
        }else if("2".equals(orderType)){
            //提现单
            tradeCode = "W" + currTime + nonceStr;
        }
        return tradeCode;
    }

    /**
     * 根据交易编号查询交易记录
     *
     * @param tradeNo 交易编号
     * @return
     */
    @Override
    public TradeInfo selectByTradeNo(String tradeNo) {
        return tradeInfoMapper.selectByTradeNo(tradeNo);
    }

    @Override
    public int updateTradeInfo(TradeInfo tradeInfo) {
        return tradeInfoMapper.updateTradeInfo(tradeInfo);
    }

    /**
     * 生成预付款交易记录
     *
     * @param tradeInfo@return 预付款交易记录
     */
    @Override
    public int insertTradeInfo(TradeInfo tradeInfo) {
        return tradeInfoMapper.insertSelective(tradeInfo);
    }

    /**
     * 分页查询用户预存款提现交易记录
     *
     * @param pageBean 分页
     * @param map      查询条件
     * @return 分页
     */
    @Override
    public PageBean queryWithdraw(PageBean pageBean, Map<String, Object> map) {
        pageBean.setPageSize(8);
        pageBean.setRows(tradeInfoMapper.queryWithdrawPageRows(map).intValue());
        map.put(CustomerConstants.STARTNUM, pageBean.getStartRowNum());
        map.put(CustomerConstants.ENDNUM, pageBean.getEndRowNum());
        //分页查询用户预存款交易记录
        pageBean.setList(tradeInfoMapper.queryWithdrawPage(map));
        return pageBean;
    }

    /**
     * 根据交易记录id查询交易记录信息
     *
     * @param id 交易记录id
     * @return 交易记录详细
     */
    @Override
    public TradeInfoVo queryTradeInfoById(Long id) {
        TradeInfoVo tradeInfoVo = tradeInfoMapper.queryTradeInfoById(id);
         return tradeInfoVo;
    }

    /**
     * 修改提现状态（取消申请/确认收款）
     *
     * @param customerId  用户id
     * @param tradeInfoId 交易记录id
     * @param type        修改类型（0取消申请1确认收款）
     * @return true：成功 false: 失败
     */
    @Override
    @Transactional
    public boolean updateTradeInfoStatus(Long customerId, Long tradeInfoId, String type) {
        boolean result = false;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("customerId", customerId);
        map.put("type", type);
        map.put("tradeInfoId", tradeInfoId);
        if ("0".equals(type)){
            map.put("orderStatus", "8");
        }else if("3".equals(type)){
            DepositInfo depositInfo = depositInfoMapper.selectDepositByCustId(customerId);
            TradeInfoVo tradeInfoVo = tradeInfoMapper.queryTradeInfoById(tradeInfoId);
            map.put("currentPrice",depositInfo.getFreezePreDeposit().add(depositInfo.getPreDeposit()).subtract(tradeInfoVo.getOrderPrice()));
            map.put("orderStatus", "4");
        }
        //修改提现状态（取消申请/确认收款）
        int num = tradeInfoMapper.updateTradeInfoStatus(map);
        LOGGER.info("修改提现状态============================num:"+num);
        if (num >0){
            //更新预存款金额
            updateDeposit(type, customerId, tradeInfoId);
            result = true;
        }
        return result;
    }

    private int updateDeposit(String type,Long customerId, Long tradeInfoId){
        DepositInfo depositInfo = depositInfoMapper.selectDepositByCustId(customerId);
        TradeInfoVo tradeInfoVo = tradeInfoMapper.queryTradeInfoById(tradeInfoId);
        DepositInfo updateDeposit =  new DepositInfo();
        updateDeposit.setCustomerId(customerId);
        //取消申请
        if ("0".equals(type)){
            //冻结金额减去提现金额
            updateDeposit.setFreezePreDeposit(depositInfo.getFreezePreDeposit().subtract(tradeInfoVo.getOrderPrice()));
            //可用余额加上提现金额
            updateDeposit.setPreDeposit(depositInfo.getPreDeposit().add(tradeInfoVo.getOrderPrice()));
        }else if("3".equals(type)){
            //确认收款
            //冻结金额减去提现金额
            updateDeposit.setFreezePreDeposit(depositInfo.getFreezePreDeposit().subtract(tradeInfoVo.getOrderPrice()));
        }
        return depositInfoMapper.updateDepositInfo(updateDeposit);
    }

    /**
     * 根据交易记录id和会员id查询
     *
     * @param id         交易记录id
     * @param customerId 会员id
     * @return 交易记录详细
     */
    @Override
    public TradeInfoVo queryByIdAndCusId(Long id, Long customerId) {
        TradeInfoVo tradeInfo = new TradeInfoVo();
        if (id != null && customerId != null){
            tradeInfo.setCustomerId(customerId);
            tradeInfo.setId(id);
            return tradeInfoMapper.queryByIdAndCusId(tradeInfo);
        }
        return tradeInfo;
    }
}
