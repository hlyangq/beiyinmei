/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.goods.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 营销信息
 * 
 * @author NINGPAI-LIH
 * @since 2014年3月19日上午10:56:02
 */
public class EsMarketing {
    /**
     * 营销ID
     */
    private Long marketingId;
    /**
     * 营销名称
     */
    @Length(min = 1, max = 225, message = "促销名称在1-20之间")
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String marketingName;
    /**
     * 营销描述
     */
    private String marketingDes;
    /**
     * 营销类型
     */
    private String marketingType;
    /**
     * 开始时间
     */
    private Date marketingBegin;
    /**
     * 结束时间
     */
    private Date marketingEnd;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间按
     */
    private Date modTime;
    /**
     * 标记
     */
    private String flag;
    /**
     * 促销类型名称
     */
    private String codexName;
    /**
     * 促销ID
     */
    private Long codexId;
    /**
     * 促销类型
     */
    private String codexType;

    /**
     * 是否叠加
     */
    private String isRepeat;

    /**
     * 第三方标示
     */
    private Long businessId;

    /**
     * 是否全场促销
     */
    private String isAll;

    /**
     * 店铺标示
     */
    private Long thirId;

    /**
     * 优惠分组id
     */
    private Long groupId;

    /**
     * 优惠分组名称
     */
    private String preferentialName;

    /**
     * 所属商家名称
     */
    private String infoRealname;

    /**
     * 促销标示 0：分类促销 1：品牌促销 3：sku促销
     */
    private String scopeType;

    /**
     * 附加赠送类型 (0:积分 1：优惠券)
     */
    private String addGiveType;

    /**
     * 积分
     */
    private Integer giveIntegral;

    /**
     * 优惠券
     */
    private Long couponId;

    /**
     * 活动站点设置(0：平台 1：移动端 2：全部)
     */
    private String activeSiteType;

    /**
     * 优惠券名称
     */
    private String couponName;


    /**
     * 满多少钱包邮
     */
    private BigDecimal shippingMoney;

    /**
     * 分组
     */
    private String marketGroupId;

    /**
     * 该会员下可以购买该抢购的货品数量
     */
    private Integer customerbuynum;

    public Integer getCustomerbuynum() {
        return customerbuynum;
    }

    public void setCustomerbuynum(Integer customerbuynum) {
        this.customerbuynum = customerbuynum;
    }

    public String getMarketGroupId() {
        return marketGroupId;
    }

    public void setMarketGroupId(String marketGroupId) {
        this.marketGroupId = marketGroupId;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getInfoRealname() {
        return infoRealname;
    }

    public void setInfoRealname(String infoRealname) {
        this.infoRealname = infoRealname;
    }

    public Long getThirId() {
        return thirId;
    }

    public void setThirId(Long thirId) {
        this.thirId = thirId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getPreferentialName() {
        return preferentialName;
    }

    public void setPreferentialName(String preferentialName) {
        this.preferentialName = preferentialName;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(String isRepeat) {
        this.isRepeat = isRepeat;
    }

    public Long getCodexId() {
        return codexId;
    }

    public void setCodexId(Long codexId) {
        this.codexId = codexId;
    }

    public String getCodexType() {
        return codexType;
    }

    public void setCodexType(String codexType) {
        this.codexType = codexType;
    }

    public String getCodexName() {
        return codexName;
    }

    public void setCodexName(String codexName) {
        this.codexName = codexName;
    }

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }

    public String getMarketingDes() {
        return marketingDes;
    }

    public void setMarketingDes(String marketingDes) {
        this.marketingDes = marketingDes;
    }

    public String getMarketingType() {
        return marketingType;
    }

    public void setMarketingType(String marketingType) {
        this.marketingType = marketingType;
    }

    /**
     * 开始时间
     * 
     * @return Date
     */
    public Date getMarketingBegin() {
        if (this.marketingBegin != null) {
            return new Date(this.marketingBegin.getTime());
        }
        return null;
    }

    /**
     * 开始时间
     * 
     * @param marketingBegin
     */
    public void setMarketingBegin(Date marketingBegin) {
        if (marketingBegin != null) {
            Date tEmp = (Date) marketingBegin.clone();
            if (tEmp != null) {
                this.marketingBegin = tEmp;
            }
        }
    }

    /**
     * 结束时间
     * 
     * @return
     */
    public Date getMarketingEnd() {
        if (this.marketingEnd != null) {
            return new Date(this.marketingEnd.getTime());
        }
        return null;
    }

    /**
     * 结束时间
     * 
     * @param marketingEnd
     */
    public void setMarketingEnd(Date marketingEnd) {
        if (marketingEnd != null) {
            Date tEmp = (Date) marketingEnd.clone();
            if (tEmp != null) {
                this.marketingEnd = tEmp;
            }
        }
    }

    /**
     * 创建时间
     * 
     * @return
     */
    public Date getCreateTime() {
        if (this.createTime != null) {
            return new Date(this.createTime.getTime()); // 正确值
        } else {
            return null;
        }
    }

    /**
     * 创建时间
     * 
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            Date tEmp = (Date) createTime.clone();
            if (tEmp != null) {
                this.createTime = tEmp;
            }
        }
    }

    /**
     * 修改时间
     * 
     * @return
     */
    public Date getModTime() {
        if (this.modTime != null) {
            return new Date(this.modTime.getTime()); // 正确值
        } else {
            return null;
        }
    }

    /**
     * 修改时间
     * 
     * @param modTime
     */
    public void setModTime(Date modTime) {
        if (modTime != null) {
            Date tEmp = (Date) modTime.clone();
            if (tEmp != null) {
                this.modTime = tEmp;
            }
        }
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIsAll() {
        return isAll;
    }

    public void setIsAll(String isAll) {
        this.isAll = isAll;
    }

    public String getAddGiveType() {
        return addGiveType;
    }

    public void setAddGiveType(String addGiveType) {
        this.addGiveType = addGiveType;
    }

    public Integer getGiveIntegral() {
        return giveIntegral;
    }

    public void setGiveIntegral(Integer giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getActiveSiteType() {
        return activeSiteType;
    }

    public void setActiveSiteType(String activeSiteType) {
        this.activeSiteType = activeSiteType;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public BigDecimal getShippingMoney() {
        return shippingMoney;
    }

    public void setShippingMoney(BigDecimal shippingMoney) {
        this.shippingMoney = shippingMoney;
    }

}
