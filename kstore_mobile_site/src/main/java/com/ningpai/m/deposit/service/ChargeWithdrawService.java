package com.ningpai.m.deposit.service;

import com.ningpai.m.deposit.bean.BankInfo;
import com.ningpai.m.deposit.bean.ChargeWithdraw;
import com.ningpai.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * 提现service
 * Created by chenpeng on 2016/10/14.
 */
public interface ChargeWithdrawService {

    /**
     * 查询收款银行信息
     * @return 收款银行信息
     */
    List<BankInfo> selectBankInfoList();

    /**
     * 生成提现申请记录和交易记录
     * @param record 提现申请记录
     * @return
     */
    int insertWithdraw(ChargeWithdraw record);

}
