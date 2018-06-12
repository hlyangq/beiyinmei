package com.ningpai.order.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BackGoods {
    private Long backGoodsId;

    private Long orderId;

    private Long backOrderId;

    private BigDecimal backGoodsPrice;

    private Long goodsInfoId;

    private Long businessId;

    private Long catId;

    private Long goodsInfoNum;

    private String delFlag;

    private Date backGoodsTime;

    private String backSturts;

    /**
     * 类目扣率
     */
    private BigDecimal cateRate;

    public Long getBackGoodsId() {
        return backGoodsId;
    }

    public void setBackGoodsId(Long backGoodsId) {
        this.backGoodsId = backGoodsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBackOrderId() {
        return backOrderId;
    }

    public void setBackOrderId(Long backOrderId) {
        this.backOrderId = backOrderId;
    }

    public Long getGoodsInfoId() {
        return goodsInfoId;
    }

    public void setGoodsInfoId(Long goodsInfoId) {
        this.goodsInfoId = goodsInfoId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getGoodsInfoNum() {
        return goodsInfoNum;
    }

    public void setGoodsInfoNum(Long goodsInfoNum) {
        this.goodsInfoNum = goodsInfoNum;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getBackGoodsTime() {
        return backGoodsTime;
    }

    public void setBackGoodsTime(Date backGoodsTime) {
        this.backGoodsTime = backGoodsTime;
    }

    public String getBackSturts() {
        return backSturts;
    }

    public void setBackSturts(String backSturts) {
        this.backSturts = backSturts;
    }

    public BigDecimal getBackGoodsPrice() {
        return backGoodsPrice;
    }

    public void setBackGoodsPrice(BigDecimal backGoodsPrice) {
        this.backGoodsPrice = backGoodsPrice;
    }

    public BigDecimal getCateRate() {
        return cateRate;
    }

    public void setCateRate(BigDecimal cateRate) {
        this.cateRate = cateRate;
    }
}