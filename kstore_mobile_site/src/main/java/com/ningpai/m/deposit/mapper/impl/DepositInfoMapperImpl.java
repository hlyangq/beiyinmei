/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.mapper.impl;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;

import com.ningpai.m.deposit.bean.DepositInfo;
import com.ningpai.m.deposit.mapper.DepositInfoMapper;

/**  
 * @Description: np_deposit_info的dao的实现类:
 * @author chenp
 * @date 2016-10-09 11:04:09
 * @version V1.0  
 */
@Repository("DepositInfoMapperM")
public class DepositInfoMapperImpl extends BasicSqlSupport implements DepositInfoMapper {

	/*
	* (non-Javadoc)
	* @see com.ningpai.m.deposit.mapper.DepositInfoMapper#insertSelective(com.ningpai.m.deposit.bean.DepositInfo)
	*/
	@Override
	public int insertSelective(DepositInfo record) {
		return this.insert("com.ningpai.m.deposit.mapper.DepositInfoMapper.insertSelective", record);
	}

	/**
	 * 更新预存款信息
	 *
	 * @param depositInfo
	 * @return
	 */
	@Override
	public int updateDepositInfo(DepositInfo depositInfo) {
		return this.update("com.ningpai.m.deposit.mapper.DepositInfoMapper.updateDepositInfo",depositInfo);
	}

	/**
	 * 根据用户id查询预存款信息
	 *
	 * @param customerId 用户id
	 * @return 预存款
	 */
	@Override
	public DepositInfo selectDepositByCustId(Long customerId) {
		return this.selectOne("com.ningpai.m.deposit.mapper.DepositInfoMapper.selectDepositByCustId", customerId);
	}
}