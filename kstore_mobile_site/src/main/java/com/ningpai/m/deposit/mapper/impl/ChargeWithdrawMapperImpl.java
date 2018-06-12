/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.mapper.impl;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;

import com.ningpai.m.deposit.bean.ChargeWithdraw;
import com.ningpai.m.deposit.mapper.ChargeWithdrawMapper;

/**  
 * @Description: np_charge_withdraw的dao的实现类:
 * @author chenp
 * @date 2016-10-14 17:25:07
 * @version V1.0  
 */
@Repository("ChargeWithdrawMapper") 
public class ChargeWithdrawMapperImpl extends BasicSqlSupport implements ChargeWithdrawMapper {

	/*
	* (non-Javadoc)
	* @see com.ningpai.m.deposit.mapper.ChargeWithdrawMapper#insertSelective(com.ningpai.m.deposit.bean.ChargeWithdraw)
	*/
	@Override
	public int insertSelective(ChargeWithdraw record) {
		return this.insert("com.ningpai.m.deposit.mapper.ChargeWithdrawMapper.insertSelective", record);
	}
}