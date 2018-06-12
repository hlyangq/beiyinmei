package com.ningpai.m.deposit.service.impl;

import com.ningpai.m.deposit.bean.BankInfo;
import com.ningpai.m.deposit.bean.ChargeWithdraw;
import com.ningpai.m.deposit.bean.DepositInfo;
import com.ningpai.m.deposit.bean.TradeInfo;
import com.ningpai.m.deposit.mapper.BankInfoMapper;
import com.ningpai.m.deposit.mapper.ChargeWithdrawMapper;
import com.ningpai.m.deposit.mapper.DepositInfoMapper;
import com.ningpai.m.deposit.service.ChargeWithdrawService;
import com.ningpai.m.deposit.service.DepositInfoService;
import com.ningpai.m.deposit.service.TradeInfoService;
import com.ningpai.util.MyLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 提现申请servic实现
 * Created by chenpeng on 2016/10/17.
 */
@Service("chargeWithdrawServiceM")
public class ChargeWithdrawServiceImpl implements ChargeWithdrawService {

    public static final MyLogger LOGGER = new MyLogger(ChargeWithdrawServiceImpl.class);

    @Resource(name = "BankInfoMapper")
    private BankInfoMapper bankInfoMapper;

    @Resource(name = "ChargeWithdrawMapper")
    private ChargeWithdrawMapper chargeWithdrawMapper;

    /**
     * 预存款service实现
     */
    @Resource(name = "tradeInfoServiceM")
    private TradeInfoService tradeInfoService;

    @Resource(name = "DepositInfoMapperM")
    private DepositInfoMapper depositInfoMapper;

    /**
     * 查询收款银行信息
     *
     * @return 收款银行信息
     */
    @Override
    public List<BankInfo> selectBankInfoList() {
        return bankInfoMapper.selectBankInfo();
    }

    /**
     * 生成提现申请记录和交易记录
     *
     * @param record 提现申请记录
     * @return 提现记录id(0:异常，大于0:正常)
     */
    @Override
    @Transactional
    public int insertWithdraw(ChargeWithdraw record) {
        try{
            //预存款可用余额和冻结金额
            DepositInfo depositInfo = updateWithdrawDeposit(record);
            //生成交易记录
            TradeInfo tradeInfo = tradeInfoService.insertSelective(record.getCustomerId(), record.getAmount(), "2", "2", record.getRemark(),depositInfo);

            //交易记录id
            record.setTradeInfoId(tradeInfo.getId());
            record.setCreateTime(new Date());
            if(record.getReceivingBank()!=-1){
                BankInfo bankInfo = bankInfoMapper.selectById(record.getReceivingBank());
                if (bankInfo != null){
                    record.setBankName(bankInfo.getBankName());
                }
            }
            chargeWithdrawMapper.insertSelective(record);
            return tradeInfo.getId().intValue();
        }catch (Exception e){
            LOGGER.info("生成提现申请记录和交易记录异常,用户id:"+record.getCustomerId());
            return 0;
        }
    }

    /**
     * 提现申请预存款修改
     * @param customerId
     * @param record
     */
    private DepositInfo updateWithdrawDeposit(ChargeWithdraw record){
        DepositInfo depositInfo  =depositInfoMapper.selectDepositByCustId(record.getCustomerId());
        DepositInfo updateDepositInfo = new DepositInfo();
        updateDepositInfo.setFreezePreDeposit(depositInfo.getFreezePreDeposit().add(record.getAmount()));
        updateDepositInfo.setPreDeposit(depositInfo.getPreDeposit().subtract(record.getAmount()));
        updateDepositInfo.setCustomerId(record.getCustomerId());
        depositInfoMapper.updateDepositInfo(updateDepositInfo);
        return updateDepositInfo;
    }
}
