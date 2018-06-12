package com.ningpai.goods.bean;

public class ArrivalNotice {
    //主键ID
    private Long noticeId;
    //货品ID
    private Long goodsInfoId;
    //手机
    private String infoMobile;
    //邮箱
    private String infoEmail;
    //是否已经发送
    private String noticeSturts;
    //区县ID
    private Long districtId;
    //仓库ID
    private Long wareId;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getGoodsInfoId() {
        return goodsInfoId;
    }

    public void setGoodsInfoId(Long goodsInfoId) {
        this.goodsInfoId = goodsInfoId;
    }

    public String getInfoMobile() {
        return infoMobile;
    }

    public void setInfoMobile(String infoMobile) {
        this.infoMobile = infoMobile;
    }

    public String getInfoEmail() {
        return infoEmail;
    }

    public void setInfoEmail(String infoEmail) {
        this.infoEmail = infoEmail;
    }

    public String getNoticeSturts() {
        return noticeSturts;
    }

    public void setNoticeSturts(String noticeSturts) {
        this.noticeSturts = noticeSturts;
    }

    public Long getDistrictId() { return districtId; }

    public void setDistrictId(Long districtId) { this.districtId = districtId; }

    public Long getWareId() { return wareId; }

    public void setWareId(Long wareId) { this.wareId = wareId; }
}