/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 * @Description: np_trade_info表的实体bean
 * @author chenp
 * @date 2016-09-26 16:41:50
 * @version V1.0  
 */
public class TradeInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	*
	*/
	private Long id;

	/**
	*客户ID
	*/
	private Long customerId;

	/**
	*交易单号包含订单编号、充值单号、退款单号、提现单号
	*/
	private String orderCode;

	/**
	*交易类型 0在线充值 1订单退款 2线下提现 3订单消费
	*/
	private String orderType;

	/**
	*提现状态 0待审核 1打回 2通过 3确认 4已完成 5充值中 6充值成功 7充值失败
	*/
	private String orderStatus;

	/**
	*交易金额
	*/
	private BigDecimal orderPrice;

	/**
	*当前余额
	*/
	private BigDecimal currentPrice;

	/**
	*备注
	*/
	private String tradeRemark;

	/**
	*
	*/
	private Date updateTime;

	/**
	*
	*/
	private String delFlag;

	/**
	*交易时间
	*/
	private Date createTime;

	/**
	*操作人Id
	*/
	private Long createPerson;

	/**
	*审核备注  包括审核驳回和审核成功的备注
	*/
	private String reviewedRemark;

	/**
	*打款凭证
	*/
	private String certificateImg;

	/**
	*付款单号
	*/
	private String payBillNum;

	/**
	*交易来源
	*/
	private String tradeSource;

	/**
	 * 打款备注
	 */
	private String payRemark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id; 
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId; 
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode; 
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType; 
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus; 
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice; 
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice; 
	}

	public String getTradeRemark() {
		return tradeRemark;
	}

	public void setTradeRemark(String tradeRemark) {
		this.tradeRemark = tradeRemark; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag; 
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime; 
	}

	public Long getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(Long createPerson) {
		this.createPerson = createPerson; 
	}

	public String getReviewedRemark() {
		return reviewedRemark;
	}

	public void setReviewedRemark(String reviewedRemark) {
		this.reviewedRemark = reviewedRemark; 
	}

	public String getCertificateImg() {
		return certificateImg;
	}

	public void setCertificateImg(String certificateImg) {
		this.certificateImg = certificateImg; 
	}

	public String getPayBillNum() {
		return payBillNum;
	}

	public void setPayBillNum(String payBillNum) {
		this.payBillNum = payBillNum; 
	}

	public String getTradeSource() {
		return tradeSource;
	}

	public void setTradeSource(String tradeSource) {
		this.tradeSource = tradeSource; 
	}

	public String getPayRemark() {
		return payRemark;
	}

	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}
}