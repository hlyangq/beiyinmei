package com.ningpai.customer.vo;

import java.util.Date;

/**
 * Created by CHENLI on 2016/9/13.
 */
public class DepositInfoVo {

    /**
     * 提现ID
     */
    private Long withdrawId;
    /**
     * 交易ID
     */
    private Long tradeInfoId;

    private Long customerId;
    /**
     * 会员用户名
     */
    private String customerName;
    /**
     * 会员真实姓名
     */
    private String infoRealName;
    /**
     * 会员手机号
     */
    private String customerMobile;
    /**
     *会员邮箱
     */
    private String customerEmail;
    /**
     * 会员账户状态
     */
    private String customerStatus;
    /**
     * 交易类型 0在线充值 1订单退款 2线下提现 3订单消费
     */
    private String orderType;

    /**
     * 交易单号
     */
    private String orderCode;
    /**
     * 交易开始时间
     */
    private Date startDate;
    /**
     * 交易结束时间
     */
    private Date endDate;
    /**
     *  提现状态 0待审核 1打回 2通过 3确认 4已完成 5充值中 6充值成功 7充值失败
     */
    private String orderStatus;
    /**
     * 分页开始的条数
     */
    private int startRowNum;
    /**
     * 分页结束的条数
     */
    private int endRowNum;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInfoRealName() {
        return infoRealName;
    }

    public void setInfoRealName(String infoRealName) {
        this.infoRealName = infoRealName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public int getEndRowNum() {
        return endRowNum;
    }

    public void setEndRowNum(int endRowNum) {
        this.endRowNum = endRowNum;
    }

    public Long getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Long withdrawId) {
        this.withdrawId = withdrawId;
    }

    public Long getTradeInfoId() {
        return tradeInfoId;
    }

    public void setTradeInfoId(Long tradeInfoId) {
        this.tradeInfoId = tradeInfoId;
    }
}
