package com.ningpai.temp.bean;

import java.util.Date;

/**
 * 快捷导航
 * 
 * @author ggn
 *
 */
public class ClassifyBarQuick {
    /**
     * 快捷导航ID
     */
    private Long classifybarQuickId;
    /**
     * 快捷导航 导航ID
     */
    private Long classifybarId;
    /**
     * 快捷导航 分类ID
     */
    private Long cateId;
    /**
     * 快捷导航 导航名称
     */
    private String cateName;
    /**
     * 快捷导航 是否删除
     */
    private String delflag;
    /**
     * 快捷创建人ID
     */
    private Long createUserId;
    /**
     * 快捷创建时间
     */
    private Date createDate;
    /**
     * 快捷导航修改人ID
     */
    private Long updateUserId;
    /**
     * 快捷导航修改时间
     */
    private Date updateDate;
    /** 它的一级父商品分类ID */
    private String temp1;
    /**
     * 备用
     */
    private String temp2;
    /**
     * 备用
     */
    private String temp3;
    /**
     * 备用
     */
    private String temp4;
    /**
     * 备用
     */
    private String temp5;

    public Long getClassifybarQuickId() {
        return classifybarQuickId;
    }

    public void setClassifybarQuickId(Long classifybarQuickId) {
        this.classifybarQuickId = classifybarQuickId;
    }

    public Long getClassifybarId() {
        return classifybarId;
    }

    public void setClassifybarId(Long classifybarId) {
        this.classifybarId = classifybarId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    /**
     * 获取创建时间
     * */
    public Date getCreateDate() {
        return createDate==null?null: (Date) createDate.clone();
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
    /**
     * 获取修改时间
     * */
    public Date getUpdateDate() {
        return updateDate==null?null: (Date) updateDate.clone();
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }

    public String getTemp4() {
        return temp4;
    }

    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    public String getTemp5() {
        return temp5;
    }

    public void setTemp5(String temp5) {
        this.temp5 = temp5;
    }
}
