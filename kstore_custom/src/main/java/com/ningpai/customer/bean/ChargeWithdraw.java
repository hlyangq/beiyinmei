package com.ningpai.customer.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现申请表
 * Created by CHENLI on 2016/9/12.
 */
public class ChargeWithdraw implements Serializable{
    /**
     * 序列化
     */
    private static final long serialVersionUID = 6836246195351592152L;

    /**
     * 提现申请id
     */
    private Long id;
    /**
     * 交易记录Id
     */
    private Long tradeInfoId;
    /**
     * 会员id
     */
    private Long customerId;
    /**
     * 户名
     */
    private String accountName;
    /**
     * 收款账号
     */
    private String bankNo;
    /**
     * 联系人号码
     */
    private String contactMobile;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 收款银行
     */
    private Long receivingBank;
    /**
     * 提现金额
     */
    private BigDecimal amount;
    /**
     * 申请备注
     */
    private String remark;
    /**
     *  交易单号,包含:订单编号、充值单号、退款单号、提现单号
     */
    private String orderCode;
    /**
     *  提现状态 提现状态 0待审核 1打回 2通过 3确认 4已完成 5充值中 6充值成功 7充值失败
     */
    private String orderStatus;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 会员名
     */
    private String customerUsername;

    /**
     * 付款单号
     */
    private String payBillNum;
    /**
     *审核备注  包括审核驳回和审核成功的备注
     */
    private String reviewedRemark;
    /**
     * 打款凭证 图片
     */
    private String certificateImg;
    /**
     * 打款备注
     */
    private String payRemark;

    private Date updateTime;

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

    public Long getTradeInfoId() {
        return tradeInfoId;
    }

    public void setTradeInfoId(Long tradeInfoId) {
        this.tradeInfoId = tradeInfoId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getPayBillNum() {
        return payBillNum;
    }

    public void setPayBillNum(String payBillNum) {
        this.payBillNum = payBillNum;
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

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
