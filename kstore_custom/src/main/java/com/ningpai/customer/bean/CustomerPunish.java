package com.ningpai.customer.bean;

import java.util.Date;

/**
 * 商家处罚实体类
 * 
 * @author Zhangsl
 *
 */
public class CustomerPunish {
    /**
     * id
     */
    private Long id;
    /**
     * 规则
     */
    private String rule;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;
    /**
     * 删除标示
     */
    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 获取创建时间
     * */
    public Date getCreateTime() {
        return createTime==null?null: (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取修改时间
     * */
    public Date getModifiedTime() {
        return modifiedTime==null?null: (Date) modifiedTime.clone();
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
