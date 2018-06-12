package com.ningpai.thirdaudit.bean;

import java.util.Date;

/**
 * 应用品牌
 * */
public class ApplyBrandVo {
    private Long applyBrandId;

    private String applyBrandName;

    private String applyBrandPic;

    private String applyBrandDelFlag;

    private Date applyBrandCreateTime;

    private Date applyModifyTime;

    private Long applyThirdId;

    private String applyUrl;

    private String applyStatus;

    private String applyCertificate;

    private String applyRefusalReason;

    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyCertificate() {
        return applyCertificate;
    }

    public void setApplyCertificate(String applyCertificate) {
        this.applyCertificate = applyCertificate;
    }

    public String getApplyRefusalReason() {
        return applyRefusalReason;
    }

    public void setApplyRefusalReason(String applyRefusalReason) {
        this.applyRefusalReason = applyRefusalReason;
    }

    public Long getApplyBrandId() {
        return applyBrandId;
    }

    public void setApplyBrandId(Long applyBrandId) {
        this.applyBrandId = applyBrandId;
    }

    public String getApplyBrandName() {
        return applyBrandName;
    }

    public void setApplyBrandName(String applyBrandName) {
        this.applyBrandName = applyBrandName;
    }

    public String getApplyBrandPic() {
        return applyBrandPic;
    }

    public void setApplyBrandPic(String applyBrandPic) {
        this.applyBrandPic = applyBrandPic;
    }

    public String getApplyBrandDelFlag() {
        return applyBrandDelFlag;
    }

    public void setApplyBrandDelFlag(String applyBrandDelFlag) {
        this.applyBrandDelFlag = applyBrandDelFlag;
    }
    /**
     * 获取创建时间
     * */
    public Date getApplyBrandCreateTime() {
        return applyBrandCreateTime==null?null: (Date) applyBrandCreateTime.clone();
    }

    public void setApplyBrandCreateTime(Date applyBrandCreateTime) {
        this.applyBrandCreateTime = applyBrandCreateTime;
    }
    /**
     * 获取修改时间
     * */
    public Date getApplyModifyTime() {
        return applyModifyTime==null?null: (Date) applyModifyTime.clone();
    }

    public void setApplyModifyTime(Date applyModifyTime) {
        this.applyModifyTime = applyModifyTime;
    }

    public Long getApplyThirdId() {
        return applyThirdId;
    }

    public void setApplyThirdId(Long applyThirdId) {
        this.applyThirdId = applyThirdId;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }
}
