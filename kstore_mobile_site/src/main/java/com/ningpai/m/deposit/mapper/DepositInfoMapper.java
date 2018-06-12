/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.mapper;

import com.ningpai.m.deposit.bean.DepositInfo;

import java.util.Map;

import java.util.List;

/**  
 * @Description: np_deposit_info的dao:
 * @author chenp
 * @date 2016-10-09 11:04:09
 * @version V1.0  
 */
public interface DepositInfoMapper {

	/**
	* 插入，空属性不会插入
	* 参数:pojo对象  
	* 返回:添加个数  
	* @date 2016-10-09 11:04:09
	*/
	int insertSelective(DepositInfo record);

	/**
	 * 更新预存款信息
	 * @param depositInfo
	 * @return
	 */
	int updateDepositInfo(DepositInfo depositInfo);

	/**
	 * 根据用户id查询预存款信息
	 * @param customerId 用户id
	 * @return 预存款
	 */
	DepositInfo selectDepositByCustId(Long customerId);
}