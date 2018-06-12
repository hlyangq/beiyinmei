package com.ningpai.customer.service;

import com.ningpai.customer.bean.DepositInfo;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.util.PageBean;

import java.util.Map;

/**
 * 平台会员预存款service接口
 * Created by CHENLI on 2016/9/14.
 */

public interface DepositInfoService {
    /**
     * 平台会员预存款总额
     * 如果有customerID，则查询该会员的总金额
     * 否则查询平台会员预存款总额
     */
    Map selectTotalDesposit();

    /**
     * 查询会员资金信息列表总条数，用于分页
     */
    Long selectTotalDespositInfo(DepositInfoVo depositInfo);

    /**
     * 查询会员资金信息列表
     */
    PageBean selectDepositInfoList(DepositInfoVo depositInfo,PageBean pageBean);

    /**
     * 根据会员IDcustomerID查询单条会员信息
     * @param customerId
     * @return DepositInfo
     */
    DepositInfo selectDepositInfoById(Long customerId);

    /**
     * 根据会员ID查询会员信息，用于修改数据
     * @param customerId
     * @return
     */
    DepositInfo selectDepositByCustId(Long customerId);
    /**
     * 修改会员预存款信息
     * @param depositInfo
     * @return
     */
    int updateDeposit(DepositInfo depositInfo);
}
