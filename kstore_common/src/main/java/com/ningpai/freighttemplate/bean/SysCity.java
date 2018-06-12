package com.ningpai.freighttemplate.bean;



import java.util.Date;

/**
 * 城市 2014-12-17
 * @author ggn
 *
 */
public class SysCity {
    /*
     *市ID
     */
    private Long cityId;
    /*
     *市名称
     */
    private String cityName;
    /*
     *排序
     */
    private Long citySort;
    /*
     *省ID
     */
    private Long provinceId;
    /*
     *创建时间
     */
    private Date createTime;
    /*
     *修改时间
     */
    private Date modifyTime;
    /*
     *删除标记
     */
    private String delFlag;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCitySort() {
        return citySort;
    }

    public void setCitySort(Long citySort) {
        this.citySort = citySort;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * 获取创建时间
     * */
    public Date getCreateTime() {
        return (Date) createTime.clone();
    }
    /**
     * 设置创建时间
     * */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime==null?null: (Date) createTime.clone();
    }
    /**
     * 获取修改时间
     * */
    public Date getModifyTime()
    {
        return modifyTime == null ? null :  (Date) modifyTime.clone();
    }
    /**
     * 设置修改时间
     * */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime==null?null: (Date) modifyTime.clone();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
