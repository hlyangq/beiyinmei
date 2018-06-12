/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.marketing.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 满赠促销信息
 * 
 * @author ggn 2014-03-25
 * 
 */
public class FullbuyPresentMarketing {
    /*
     * 满赠ID
     */
    private Long fullbuyPresentMarketingId;
    /*
     * 促销ID
     */
    private Long marketingId;
    /*
     * 满多少
     */
    private BigDecimal fullPrice;
    /*
     * 赠品id
     */
    private Long productId;
    /*
     * 是否删除
     */
    private String delFlag;
    /**
     * 赠送类型0金额1件数
     */
    private String presentType;

    /**
     * 赠品类型0商品1优惠券
     */
    private String giftType;

    /**
     * 赠送方式0全赠1一种赠送
     */
    private String presentMode;

    /**
     * 满赠促销赠品list
     */
    private List<FullbuyPresentScope> fullbuyPresentScopes;

    public Long getFullbuyPresentMarketingId() {
        return fullbuyPresentMarketingId;
    }

    public void setFullbuyPresentMarketingId(Long fullbuyPresentMarketingId) {
        this.fullbuyPresentMarketingId = fullbuyPresentMarketingId;
    }

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPresentType() {
        return presentType;
    }

    public void setPresentType(String presentType) {
        this.presentType = presentType;
    }

    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    public String getPresentMode() {
        return presentMode;
    }

    public void setPresentMode(String presentMode) {
        this.presentMode = presentMode;
    }

    public List<FullbuyPresentScope> getFullbuyPresentScopes() {
        return fullbuyPresentScopes;
    }

    public void setFullbuyPresentScopes(List<FullbuyPresentScope> fullbuyPresentScopes) {
        this.fullbuyPresentScopes = fullbuyPresentScopes;
    }
}
