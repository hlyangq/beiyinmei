package com.ningpai.customer.dao.impl;

import com.ningpai.customer.bean.ChargeWithdraw;
import com.ningpai.customer.dao.ChargeWithdrawMapper;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会员提现数据层实现
 * Created by CHENLI on 2016/10/8.
 */
@Repository
public class ChargeWithdrawMapperImpl extends BasicSqlSupport implements ChargeWithdrawMapper{
    /**
     * 查询提现记录总数
     *
     * @param depositInfo
     * @return
     */
    @Override
    public Long selectTotalChargeWithdraw(DepositInfoVo depositInfo) {
        return this.selectOne("com.ningpai.customer.dao.ChargeWithdrawMapper.selectTotalChargeWithdraw",depositInfo);
    }

    /**
     * 查询提现记录列表
     *
     * @param depositInfo
     * @return
     */
    @Override
    public List<Object> selectChargeWithdrawList(DepositInfoVo depositInfo) {
        return this.selectList("com.ningpai.customer.dao.ChargeWithdrawMapper.selectChargeWithdrawList",depositInfo);
    }

    /**
     * 根据提现记录ID查询该条提现信息
     *
     * @param depositInfo
     * @return
     */
    @Override
    public ChargeWithdraw selectChargeWithdrawById(DepositInfoVo depositInfo) {
        return this.selectOne("com.ningpai.customer.dao.ChargeWithdrawMapper.selectChargeWithdrawList",depositInfo);
    }
}
