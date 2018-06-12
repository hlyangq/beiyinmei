package com.ningpai.customer.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易记录表
 * Created by CHENLI on 2016/9/12.
 */
public class TradeInfo implements Serializable{
    /**
     * 序列化
     */
    private static final long serialVersionUID = 2720046274324736181L;
    /**
     * 交易记录ID，自增长
     */
    private  Long id;
    /**
     * 会员编号
     */
    private  Long customerId;
    /**
     *  交易单号,包含:订单编号、充值单号、退款单号、提现单号
     */
    private String orderCode;
    /**
     *  交易类型 0在线充值 1订单退款 2线下提现 3订单消费
     */
    private String orderType;
    /**
     *  提现状态 0【提现】待审核 1【提现】已打回 2【提现】已通过待打款 3【提现】已打款待确认 4【提现】已完成 5未支付 6充值成功 8已取消
     */
    private String orderStatus;
    /**
     *  交易金额
     */
    private BigDecimal orderPrice;
    /**
     *  当前余额
     */
    private BigDecimal currentPrice;
    /**
     *  备注
     */
    private String tradeRemark;
    /**
     *  修改时间
     */
    private Date updateTime;
    /**
     *  删除标志，enum(0,1)
     */
    private String delFlag;
    /**
     *  交易时间
     */
    private Date createTime;
    /**
     *  操作人
     */
    private Long createPerson;
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
     * 交易来源
     */
    private String tradeSource;

    /**
     * 收入
     */
    private BigDecimal income;
    /**
     * 支出
     */
    private BigDecimal cost;

    private String customerUsername;
    /**
     * 打款备注
     */
    private String payRemark;

    /**
     * 批量删除用
     */
    Long[] tradeInfoIds;

    /**
     * 交易记录修改前状态
     */
    private String preOrderStatus;

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

    public String getTradeSource() {
        return tradeSource;
    }

    public void setTradeSource(String tradeSource) {
        this.tradeSource = tradeSource;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public Long[] getTradeInfoIds() {
        return tradeInfoIds;
    }

    public void setTradeInfoIds(Long[] tradeInfoIds) {
        this.tradeInfoIds = tradeInfoIds;
    }

    public String getPreOrderStatus() {
        return preOrderStatus;
    }

    public void setPreOrderStatus(String preOrderStatus) {
        this.preOrderStatus = preOrderStatus;
    }
}
