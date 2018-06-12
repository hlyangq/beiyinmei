/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.mapper;

import com.ningpai.m.deposit.bean.BankInfo;

import java.util.Map;

import java.util.List;

/**  
 * @Description: np_bank_info的dao:
 * @author chenp
 * @date 2016-10-14 17:25:07
 * @version V1.0  
 */
public interface BankInfoMapper {

	/**
	* 插入，空属性不会插入
	* 参数:pojo对象  
	* 返回:添加个数  
	* @date 2016-10-14 17:25:07
	*/
	int insertSelective(BankInfo record);

	/**
	 * 查询银行信息
	 * @return 可用的银行信息
	 */
	List<BankInfo> selectBankInfo();

	/**
	 * 根据银行id查询银行信息
	 * @param id 银行id
	 * @return 银行信息
	 */
	BankInfo selectById(Long id);
}