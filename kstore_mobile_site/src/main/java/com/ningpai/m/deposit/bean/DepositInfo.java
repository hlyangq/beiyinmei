/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 * @Description: np_deposit_info表的实体bean
 * @author chenp
 * @date 2016-10-09 11:04:09
 * @version V1.0  
 */
public class DepositInfo implements Serializable {

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
	*预存款
	*/
	private BigDecimal preDeposit;

	/**
	*冻结预存款
	*/
	private BigDecimal freezePreDeposit;

	/**
	*预存款支付密码
	*/
	private String payPassword;

	/**
	*预存款支付密码错误次数
	*/
	private Integer passwordErrorCount;

	/**
	*预存款错误时间
	*/
	private Date passwordTime;

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

	public BigDecimal getPreDeposit() {
		return preDeposit;
	}

	public void setPreDeposit(BigDecimal preDeposit) {
		this.preDeposit = preDeposit; 
	}

	public BigDecimal getFreezePreDeposit() {
		return freezePreDeposit;
	}

	public void setFreezePreDeposit(BigDecimal freezePreDeposit) {
		this.freezePreDeposit = freezePreDeposit; 
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword; 
	}

	public Integer getPasswordErrorCount() {
		return passwordErrorCount;
	}

	public void setPasswordErrorCount(Integer passwordErrorCount) {
		this.passwordErrorCount = passwordErrorCount; 
	}

	public Date getPasswordTime() {
		return passwordTime;
	}

	public void setPasswordTime(Date passwordTime) {
		this.passwordTime = passwordTime; 
	}
}