/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.mapper.impl;
import com.ningpai.m.deposit.vo.TradeInfoVo;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;

import com.ningpai.m.deposit.bean.TradeInfo;
import com.ningpai.m.deposit.mapper.TradeInfoMapper;

/**  
 * @Description: np_trade_info的dao的实现类:
 * @author chenp
 * @date 2016-09-26 16:42:50
 * @version V1.0  
 */
@Repository("TradeInfoMapper") 
public class TradeInfoMapperImpl extends BasicSqlSupport implements TradeInfoMapper {

	/*
	* (non-Javadoc)
	* @see com.ningpai.m.deposit.mapper.TradeInfoMapper#insertSelective(com.ningpai.m.deposit.bean.TradeInfo)
	*/
	@Override
	public int insertSelective(TradeInfo record) {
		return this.insert("com.ningpai.m.deposit.mapper.TradeInfoMapper.insertSelective", record);
	}

	/**
	 * 根据交易记录展示条件（全部／收入/支出）和分页条件查询列表
	 *
	 * @param map@return 预存款交易分页记录
	 */
	@Override
	public List<Object> queryTradeInfoPage(Map<String, Object> map) {
		return this.selectList("com.ningpai.m.deposit.mapper.TradeInfoMapper.queryTradeInfoPage", map);
	}

	/**
	 * 根据交易记录展示条件（全部／收入/支出）查询列表总数
	 *
	 * @param map@return 总数
	 */
	@Override
	public Long queryTradeInfoPageRows(Map<String, Object> map) {
		return this.selectOne("com.ningpai.m.deposit.mapper.TradeInfoMapper.queryTradeInfoPageRows", map);
	}

	/**
	 * 根据交易编号查询交易记录
	 *
	 * @param tradeNo 交易编号
	 * @return 交易记录
	 */
	@Override
	public TradeInfo selectByTradeNo(String tradeNo) {
		return this.selectOne("com.ningpai.m.deposit.mapper.TradeInfoMapper.queryTradeInfoByTradeNo", tradeNo);
	}

	/**
	 * 更新交易记录
	 *
	 * @param record
	 * @return
	 */
	@Override
	public int updateTradeInfo(TradeInfo record) {
		return this.update("com.ningpai.m.deposit.mapper.TradeInfoMapper.updateTradeInfo", record);
	}

	/**
	 * 根据交易记录展示条件（全部／待审核/待确认/已打回/已完成 ）和分页条件查询列表
	 *
	 * @param map@return 预存款交易分页记录
	 */
	@Override
	public List<Object> queryWithdrawPage(Map<String, Object> map) {
		return this.selectList("com.ningpai.m.deposit.mapper.TradeInfoMapper.queryWithdrawPage", map);
	}

	/**
	 * 根据交易记录展示条件（全部／待审核/待确认/已打回/已完成 ）查询列表总数
	 *
	 * @param map@return 总数
	 */
	@Override
	public Long queryWithdrawPageRows(Map<String, Object> map) {
		return this.selectOne("com.ningpai.m.deposit.mapper.TradeInfoMapper.queryWithdrawPageRows", map);
	}

	/**
	 * 根据交易记录id查询交易记录信息
	 *
	 * @param id 交易记录id
	 * @return 交易记录详细
	 */
	@Override
	public TradeInfoVo queryTradeInfoById(Long id) {
		return this.selectOne("com.ningpai.m.deposit.mapper.TradeInfoMapper.queryTradeInfoById", id);
	}

	/**
	 * 修改提现状态（取消申请/确认收款）
	 *
	 * @param map
	 * @return
	 */
	@Override
	public int updateTradeInfoStatus(Map<String, Object> map) {
		return this.update("com.ningpai.m.deposit.mapper.TradeInfoMapper.updateTradeInfoStatus", map);
	}

	/**
	 * 根据用户id和交易记录id查询
	 *
	 * @param tradeInfo
	 * @return
	 */
	@Override
	public TradeInfoVo queryByIdAndCusId(TradeInfo tradeInfo) {
		return this.selectOne("com.ningpai.m.deposit.mapper.TradeInfoMapper.queryByIdAndCusId", tradeInfo);
	}
}