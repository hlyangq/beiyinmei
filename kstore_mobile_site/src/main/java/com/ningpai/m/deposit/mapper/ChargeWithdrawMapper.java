/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.mapper;

import com.ningpai.m.deposit.bean.ChargeWithdraw;

import java.util.Map;

import java.util.List;

/**  
 * @Description: np_charge_withdraw的dao:
 * @author chenp
 * @date 2016-10-14 17:25:07
 * @version V1.0  
 */
public interface ChargeWithdrawMapper {

	/**
	* 插入，空属性不会插入
	* 参数:pojo对象  
	* 返回:添加个数  
	* @date 2016-10-14 17:25:07
	*/
	int insertSelective(ChargeWithdraw record);

}