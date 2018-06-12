package com.ningpai.mobile.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @ClassName: MobPageCate
 * @Description: 实体类-移动版首页-页面分类
 * @author daiyitian
 * @date 2014年10月11日 上午11:59:37
 * 
 */
public class MobPageCate {
    /** 编号 */
    private Long pageCateId;
    /** 名称 */
    @NotNull
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String name;
    /** 备注 */
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+|()")
    private String remark;
    /** 删除标记（0：未删除；1：已删除） */
    private String delflag;
    /** 创建人ID */
    private Long createUserId;
    /** 创建时间 */
    private Date createDate;
    /** 修改人ID */
    private Long updateUserId;
    /** 修改时间 */
    private Date updateDate;

    /** 移动端页面数 */
    private int homePageNum;

    public Long getPageCateId() {
        return pageCateId;
    }

    public void setPageCateId(Long pageCateId) {
        this.pageCateId = pageCateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getCreateDate() {
        return createDate;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getHomePageNum() {
        return homePageNum;
    }

    public void setHomePageNum(int homePageNum) {
        this.homePageNum = homePageNum;
    }
}
