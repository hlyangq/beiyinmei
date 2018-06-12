/*
 * Copyright 2013 NINGPAI, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.system.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 帮助中心实体类
 * 
 * @author NINGPAI-LiHaoZe
 * @since 2013年12月20日 下午2:21:05
 * @version 1.0
 */
public class HelpCenter implements Serializable
{
	
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 5613521677447573192L;
	/*
     *  帮助中心ID
     */
    private Long helpId;
    /* 
     * 帮助中心title
     */
    @NotNull
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String helpTitle;
    /* 
     * 作者
     */
    @NotNull
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String helpAuthor;
    /* 
     * 帮助中心排序
     */
    private Integer helpSort;
    /*
     *  分类ID
     */
    private Long helpcateId;
    /* 
     * 是否在底部
     */
    private String isFoot;
    /* 
     * 创建时间
     */
    private Date createTime;
    /* 
     * 修改时间
     */
    private Date modifyTime;
    /* 
     * 删除标记
     */
    private String delFlag;
    /*
     *  帮助内容
     */
    private String helpContent;
    /*
     *  图标
     */
    @Pattern(regexp = "[^\\<\\>]+|()")
    private String helpImg;
    /* 
     * 首页显示 0 不显示 1显示
     */
    private String homeShow;

    public String getHelpImg() {
        return helpImg;
    }

    public void setHelpImg(String helpImg) {
        this.helpImg = helpImg;
    }

    public String getHomeShow() {
        return homeShow;
    }

    public void setHomeShow(String homeShow) {
        this.homeShow = homeShow;
    }

    public String getHelpAuthor() {
        return helpAuthor;
    }

    public void setHelpAuthor(String helpAuthor) {
        this.helpAuthor = helpAuthor;
    }

    public Long getHelpId() {
        return helpId;
    }

    public void setHelpId(Long helpId) {
        this.helpId = helpId;
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    public Integer getHelpSort() {
        return helpSort;
    }

    public void setHelpSort(Integer helpSort) {
        this.helpSort = helpSort;
    }

    public Long getHelpcateId() {
        return helpcateId;
    }

    public void setHelpcateId(Long helpcateId) {
        this.helpcateId = helpcateId;
    }

    public String getIsFoot() {
        return isFoot;
    }

    public void setIsFoot(String isFoot) {
        this.isFoot = isFoot;
    }

    /**
     * 时间
     * @return
     */
    public Date getCreateTime() {
        if (this.createTime != null) {
            return new Date(this.createTime.getTime());
        } else {
            return null;
        }
    }

    /**
     * 时间
     * @return
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
     * 时间
     * @return
     */
    public Date getModifyTime() {
        if (this.modifyTime != null) {
            return new Date(this.modifyTime.getTime());
        } else {
            return null;
        }
    }

    /**
     * 时间
     * @return
     */
    public void setModifyTime(Date modifyTime) {
        if (modifyTime != null) {
            Date tEmp = (Date) modifyTime.clone();
            if (tEmp != null) {
                this.modifyTime = tEmp;
            }
        }
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getHelpContent() {
        return helpContent;
    }

    public void setHelpContent(String helpContent) {
        this.helpContent = helpContent;
    }
}
