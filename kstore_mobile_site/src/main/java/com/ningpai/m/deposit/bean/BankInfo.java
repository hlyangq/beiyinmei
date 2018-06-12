/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.deposit.bean;

import java.io.Serializable;

/**  
 * @Description: np_bank_info表的实体bean
 * @author chenp
 * @date 2016-10-14 17:25:07
 * @version V1.0  
 */
public class BankInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	*
	*/
	private Long id;

	/**
	*银行名称
	*/
	private String bankName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id; 
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName; 
	}
}