/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 * @Description: np_charge_withdraw表的实体bean
 * @author chenp
 * @date 2016-10-14 17:25:07
 * @version V1.0  
 */
public class ChargeWithdraw implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	*
	*/
	private Long id;

	/**
	*交易记录Id
	*/
	private Long tradeInfoId;

	/**
	*客户ID
	*/
	private Long customerId;

	/**
	*收款银行
	*/
	private Long receivingBank;

	/**
	*银行名称
	*/
	private String bankName;

	/**
	*收款账号
	*/
	private String bankNo;

	/**
	*户名
	*/
	private String accountName;

	/**
	*联系人号码
	*/
	private String contactMobile;

	/**
	*提现金额
	*/
	private BigDecimal amount;

	/**
	*申请时间
	*/
	private Date createTime;

	/**
	*申请备注
	*/
	private String remark;

	/**
	 * 银行信息
	 */
	private BankInfo bankInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id; 
	}

	public Long getTradeInfoId() {
		return tradeInfoId;
	}

	public void setTradeInfoId(Long tradeInfoId) {
		this.tradeInfoId = tradeInfoId; 
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId; 
	}

	public Long getReceivingBank() {
		return receivingBank;
	}

	public void setReceivingBank(Long receivingBank) {
		this.receivingBank = receivingBank; 
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName; 
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo; 
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName; 
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile; 
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount; 
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime; 
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark; 
	}

	public BankInfo getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(BankInfo bankInfo) {
		this.bankInfo = bankInfo;
	}
}