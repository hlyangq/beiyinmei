package com.ningpai.customer.service;

import com.ningpai.customer.bean.ChargeWithdraw;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.util.PageBean;

/**
 * 会员提现service接口
 * Created by CHENLI on 2016/10/8.
 */
public interface ChargeWithdrawService {
    /**
     * 查询提现记录总数
     * @param depositInfo
     * @return
     */
    Long selectTotalChargeWithdraw(DepositInfoVo depositInfo);

    /**
     * 查询提现记录列表
     * @param depositInfo
     * @return
     */
    PageBean selectChargeWithdrawList(DepositInfoVo depositInfo ,PageBean pageBean);

    /**
     * 根据提现记录ID查询该条提现信息
     * @param withdrawId
     * @return
     */
    ChargeWithdraw selectChargeWithdrawById(Long withdrawId);
}
