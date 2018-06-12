package com.ningpai.marketing.bean;

/**
 * Created by Zhoux on 2015/4/1.
 */
public class RegisterMarketing {
    /**
     * id
     */
    private Long id;
    /**
     * 注册人数
     */
    private Long registerNum;
    /**
     * 是否使用
     */
    private String isUsed;
    /**
     * 注册 优惠券id
     */
    private Long registerCouponId;
    /**
     * 注册 积分
     */
    private Long registerIntegral;
    /**
     * 删除标记
     */
    private String delFlag;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 优惠券名称
     */
    private String couponName;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Long registerNum) {
        this.registerNum = registerNum;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public Long getRegisterCouponId() {
        return registerCouponId;
    }

    public void setRegisterCouponId(Long registerCouponId) {
        this.registerCouponId = registerCouponId;
    }

    public Long getRegisterIntegral() {
        return registerIntegral;
    }

    public void setRegisterIntegral(Long registerIntegral) {
        this.registerIntegral = registerIntegral;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}
