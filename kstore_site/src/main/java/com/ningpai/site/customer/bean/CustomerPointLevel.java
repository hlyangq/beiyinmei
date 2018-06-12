package com.ningpai.site.customer.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员等级实体类
 * Created by houyichang on 2016/1/14.
 */
public class CustomerPointLevel {
    /**
     * 会员等级id
     * */
    private Long pointLevelId;

    /**
     * 等级名称
     * */
    private String pointLevelName;

    /**
     * 所需最低积分
     * */
    private String pointNeed;

    /**
     * 等级折扣
     * */
    private BigDecimal pointDiscount;

    /**
     * 是否默认
     * 0否 1是
     * */
    private String isDefault;

    /**
     * 创建时间
     * */
    private Date createTime;

    /**
     * 更新时间
     * */
    private Date modifiedTime;

    /**
     * 删除时间
     * */
    private Date delTime;

    /**
     * 删除标记
     * 0未删除 1已删除
     * */
    private String delFlag;

    public Long getPointLevelId() {
        return pointLevelId;
    }

    public void setPointLevelId(Long pointLevelId) {
        this.pointLevelId = pointLevelId;
    }

    public String getPointLevelName() {
        return pointLevelName;
    }

    public void setPointLevelName(String pointLevelName) {
        this.pointLevelName = pointLevelName;
    }

    public String getPointNeed() {
        return pointNeed;
    }

    public void setPointNeed(String pointNeed) {
        this.pointNeed = pointNeed;
    }

    public BigDecimal getPointDiscount() {
        return pointDiscount;
    }

    public void setPointDiscount(BigDecimal pointDiscount) {
        this.pointDiscount = pointDiscount;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    /**
     * 创建时间
     * */
    public Date getCreateTime() {
        return createTime==null?null: (Date) createTime.clone();
    }
    /**
     * 创建时间
     * */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime==null?null: (Date) createTime.clone();
    }
    /**
     * 更新时间
     * */
    public Date getModifiedTime() {
        return modifiedTime==null?null: (Date) modifiedTime.clone();
    }
    /**
     * 更新时间
     * */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime==null?null: (Date) modifiedTime.clone();
    }
    /**
     * 删除时间
     * */
    public Date getDelTime() {
        return delTime==null?null: (Date) delTime.clone();
    }
    /**
     * 删除时间
     * */
    public void setDelTime(Date delTime) {
        this.delTime = delTime==null?null: (Date) delTime.clone();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
