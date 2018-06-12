package com.ningpai.customer.dao.impl;

import com.ningpai.customer.bean.DepositInfo;
import com.ningpai.customer.dao.DepositInfoMapper;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 平台会员预存款数据层实现
 * Created by CHENLI on 2016/9/14.
 */
@Repository
public class DepositInfoMapperImpl extends BasicSqlSupport implements DepositInfoMapper {
    /**
     * 查询平台会员预存款总额
     */
    @Override
    public Map selectTotalDesposit() {
        return this.selectOne("com.ningpai.customer.dao.DepositInfoMapper.selectTotalDesposit");
    }

    /**
     * 查询会员资金信息列表总条数，用于分页
     *
     * @param depositInfo
     */
    @Override
    public Long selectTotalDespositInfo(DepositInfoVo depositInfo) {
        return this.selectOne("com.ningpai.customer.dao.DepositInfoMapper.selectTotalDespositInfo",depositInfo);
    }

    /**
     * @param depositInfo
     */
    @Override
    public List<Object> selectDespositInfoList(DepositInfoVo depositInfo) {
        return this.selectList("com.ningpai.customer.dao.DepositInfoMapper.selectDespositInfoList",depositInfo);
    }

    /**
     * 根据会员IDcustomerID查询单条会员信息
     *
     * @param depositInfo
     * @return DepositInfo
     */
    @Override
    public DepositInfo selectDepositInfoById(DepositInfoVo depositInfo) {
        return this.selectOne("com.ningpai.customer.dao.DepositInfoMapper.selectDespositInfoList",depositInfo);
    }

    /**
     * 根据会员ID查询会员信息，用于修改数据
     * @param customerId
     * @return
     */
    @Override
    public DepositInfo selectDepositByCustId(Long customerId) {
        return this.selectOne("com.ningpai.customer.dao.DepositInfoMapper.selectDepositByCustId",customerId);
    }

    /**
     * 修改会员预存款信息
     *
     * @param depositInfo
     * @return
     */
    @Override
    public int updateDeposit(DepositInfo depositInfo) {
        return this.update("com.ningpai.customer.dao.DepositInfoMapper.updateDeposit",depositInfo);
    }
}
