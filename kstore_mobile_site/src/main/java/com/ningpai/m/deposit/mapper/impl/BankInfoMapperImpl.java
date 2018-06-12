/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.mapper.impl;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;

import com.ningpai.m.deposit.bean.BankInfo;
import com.ningpai.m.deposit.mapper.BankInfoMapper;

/**  
 * @Description: np_bank_info的dao的实现类:
 * @author chenp
 * @date 2016-10-14 17:25:07
 * @version V1.0  
 */
@Repository("BankInfoMapper") 
public class BankInfoMapperImpl extends BasicSqlSupport implements BankInfoMapper {

	/*
	* (non-Javadoc)
	* @see com.ningpai.m.deposit.mapper.BankInfoMapper#insertSelective(com.ningpai.m.deposit.bean.BankInfo)
	*/
	@Override
	public int insertSelective(BankInfo record) {
		return this.insert("com.ningpai.m.deposit.mapper.BankInfoMapper.insertSelective", record);
	}

	/**
	 * 查询银行信息
	 *
	 * @return 可用的银行信息
	 */
	@Override
	public List<BankInfo> selectBankInfo() {
		return this.selectList("com.ningpai.m.deposit.mapper.BankInfoMapper.selectList");
	}

	/**
	 * 根据银行id查询银行信息
	 *
	 * @param bankInfoId 银行id
	 * @return 银行信息
	 */
	@Override
	public BankInfo selectById(Long id) {
		return this.selectOne("com.ningpai.m.deposit.mapper.BankInfoMapper.selectByPrimaryKey", id);
	}
}