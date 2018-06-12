package com.ningpai.customer.dao;

import com.ningpai.customer.bean.ChargeWithdraw;
import com.ningpai.customer.vo.DepositInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会员提现数据层接口
 * Created by CHENLI on 2016/10/8.
 */
@Repository
public interface ChargeWithdrawMapper {
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
    List<Object> selectChargeWithdrawList(DepositInfoVo depositInfo);

    /**
     * 根据提现记录ID查询该条提现信息
     * @param depositInfo
     * @return
     */
    ChargeWithdraw selectChargeWithdrawById(DepositInfoVo depositInfo);
}
