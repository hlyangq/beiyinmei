/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.mapper;

import com.ningpai.m.deposit.bean.TradeInfo;
import com.ningpai.m.deposit.vo.TradeInfoVo;
import com.ningpai.util.PageBean;

import java.util.Map;

import java.util.List;

/**  
 * @Description: np_trade_info的dao:
 * @author chenp
 * @date 2016-09-26 16:41:50
 * @version V1.0  
 */
public interface TradeInfoMapper {

	/**
	* 插入，空属性不会插入
	* 参数:pojo对象  
	* 返回:添加个数  
	* @date 2016-09-26 16:41:50
	*/
	int insertSelective(TradeInfo record);

	/**
	 * 根据交易记录展示条件（全部／收入/支出）和分页条件查询列表
	 * @param map(type:0全部1收入2支出,
	 * 			  startRowNum:开始行数,
	 * 			  endRowNum:结束行数)
	 * @return 预存款交易分页记录
	 */
	List<Object> queryTradeInfoPage(Map<String, Object> map);

	/**
	 * 根据交易记录展示条件（全部／收入/支出）查询列表总数
	 * @param map(type:0全部1收入2支出)
	 * @return 总数
	 */
	Long queryTradeInfoPageRows(Map<String, Object> map);

	/**
	 * 根据交易编号查询交易记录
	 * @param tradeNo 交易编号
	 * @return 交易记录
	 */
	TradeInfo selectByTradeNo(String tradeNo);

	/**
	 * 更新交易记录
	 * @param record
	 * @return
	 */
	int updateTradeInfo(TradeInfo record);

	/**
	 * 根据交易记录展示条件（全部／待审核/待确认/已打回/已完成 ）和分页条件查询列表
	 * @param map(type:0全部1待审核2待确认3已打回4已完成
	 * 			  startRowNum:开始行数,
	 * 			  endRowNum:结束行数)
	 * @return 预存款交易分页记录
	 */
	List<Object> queryWithdrawPage(Map<String, Object> map);

	/**
	 * 根据交易记录展示条件（全部／待审核/待确认/已打回/已完成 ）查询列表总数
	 * @param map(type:0全部1待审核2待确认3已打回4已完成)
	 * @return 总数
	 */
	Long queryWithdrawPageRows(Map<String, Object> map);

	/**
	 * 根据交易记录id查询交易记录信息
	 * @param id 交易记录id
	 * @return 交易记录详细
	 */
	TradeInfoVo queryTradeInfoById(Long id);

	/**
	 * 修改提现状态（取消申请/确认收款）
	 * @param map
	 * @return
	 */
	int updateTradeInfoStatus(Map<String, Object> map);

	/**
	 * 根据用户id和交易记录id查询
	 * @param tradeInfo
	 * @return
	 */
	TradeInfoVo queryByIdAndCusId(TradeInfo tradeInfo);
}