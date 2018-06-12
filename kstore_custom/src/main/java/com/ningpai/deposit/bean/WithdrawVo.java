package com.ningpai.deposit.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by youzipi on 16/10/9.
 */

public class WithdrawVo {

    /**
     * =====
     * info
     * =====
     */
    private Long id;

    private String orderCode;

    private Date createTime;

    /**
     * 审核备注  包括审核驳回和审核成功的备注
     */
    private String reviewedRemark = "";
    /**
     *
     */
    private String payRemark = "";
    /**
     * 打款凭证 图片
     */
    private String certificateImg = "";
    /**
     * 付款单号
     */
    private String payBillNum = "";

    private String orderStatus;


    /**
     * 用户ID
     */
    private Long customerId;
    /**
     * 开户银行ID
     */
    private Long receivingBank;
    /**
     * 开户银行名称
     */
    private String bankName;
    /**
     * 收款账号
     */
    private String bankNo;
    /**
     * 户名
     */
    private String accountName;

    /**
     * 联系人手机号
     */
    private String contactMobile;
    /**
     * 提现金额
     */
    private BigDecimal amount;
    /**
     * 支付密码
     */
    private String payPwd;
    /**
     * 备注
     */
    private String remark = "";

    private BigDecimal currentPrice;

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
