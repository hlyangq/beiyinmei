package com.ningpai.customer.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预存款信息表
 * Created by CHENLI on 2016/9/12.
 */
public class DepositInfo implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -5168326900514408649L;

    /**
     * 预存款id
     */
    private Long id;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 可用预存款
     */
    private BigDecimal preDeposit;
    /**
     * 冻结预存款
     */
    private BigDecimal freezePreDeposit;
    /**
     * 预存款支付密码
     */
    private String payPassword;
    /**
     * 预存款支付密码错误次数
     */
    private int passwordErrorCount;
    /**
     * 预存款错误时间
     */
    private Date passwordTime;

    private String customerUsername;

    private String customerImg;

    /**
     * 会员总资产预存款
     */
    private BigDecimal totalDeposit;
    /**
     * 用户状态
     */
    private String isFlag;

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

    public int getPasswordErrorCount() {
        return passwordErrorCount;
    }

    public void setPasswordErrorCount(int passwordErrorCount) {
        this.passwordErrorCount = passwordErrorCount;
    }

    public Date getPasswordTime() {
        return passwordTime;
    }

    public void setPasswordTime(Date passwordTime) {
        this.passwordTime = passwordTime;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCustomerImg() {
        return customerImg;
    }

    public void setCustomerImg(String customerImg) {
        this.customerImg = customerImg;
    }

    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
    }
    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }
}