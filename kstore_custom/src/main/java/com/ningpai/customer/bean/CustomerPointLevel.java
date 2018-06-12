package com.ningpai.customer.bean;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Pattern;

/**
 * 会员等级信息
 * 
 * @author NINGPAI-zhangqiang
 * @since 2013年12月17日 下午4:21:50
 * @version
 */
public class CustomerPointLevel {
    // 等级编号
    private Long pointLelvelId;
    // 等级名称
    @Pattern(regexp = "[^\\<\\>]*")
    private String pointLevelName;
    // 所需积分
    @Pattern(regexp = "[0-9\\,]+")
    private String pointNeed;
    // 折扣
    private BigDecimal pointDiscount;
    // 是否默认
    private String isDefault;
    // 创建时间
    private Date createTime;
    // 修改时间
    private Date modifiedTime;
    // 删除时间
    private Date delTime;
    // 删除标记
    private String delFlag;

    /**
     * 积分最小值
     */
    private Long minPoint;

    /**
     * 积分最大值
     */
    private Long maxPoint;

    public Long getPointLelvelId() {
        return pointLelvelId;
    }

    public void setPointLelvelId(Long pointLelvelId) {
        this.pointLelvelId = pointLelvelId;
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
     *获取创建时间
     * */
    public Date getCreateTime() {
        if (this.createTime != null) {
            return new Date(this.createTime.getTime());
        } else {
            return null;
        }
    }
    /**
     *设置创建时间
     * */
    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            Date timeTemp = (Date) createTime.clone();
            if (timeTemp != null) {
                this.createTime = timeTemp;
            }
        }
    }
    /**
     *获取修改时间
     * */
    public Date getModifiedTime() {
        if (this.modifiedTime != null) {
            return new Date(this.modifiedTime.getTime());
        } else {
            return null;
        }
    }
    /**
     *设置修改时间
     * */
    public void setModifiedTime(Date modifiedTime) {
        if (modifiedTime != null) {
            Date timeTemp = (Date) modifiedTime.clone();
            if (timeTemp != null) {
                this.modifiedTime = timeTemp;
            }
        }
    }
    /**
     *获取删除时间
     * */
    public Date getDelTime() {
        if (this.delTime != null) {
            return new Date(this.delTime.getTime());
        } else {
            return null;
        }
    }
    /**
     *设置修改时间
     * */
    public void setDelTime(Date delTime) {
        if (delTime != null) {
            Date timeTemp = (Date) delTime.clone();
            if (timeTemp != null) {
                this.delTime = timeTemp;
            }
        }
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


    /**
     * 判断 输入的积分是否在当前区间内
     *
     * @param point
     * @return 如果输入的积分 在当前区间内 则返回true  否则返回false
     * 判断规则是[0,1000) 包含前面的不包含后面的
     */
    public boolean isPointInThisRegion(int point) {

        if (StringUtils.isEmpty(this.pointNeed)) {
            return false;
        }

        try {
            String[] sRegin = this.pointNeed.split("~");

            return Integer.valueOf(sRegin[0]) <= point && point < Integer.valueOf(sRegin[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 设置积分的最大值和最小值
     */
    public void setMinAndMaxPoint() {
        if (StringUtils.isEmpty(this.pointNeed)) {
            return;
        }

        try {
            String[] sRegin = this.pointNeed.split("~");
            this.minPoint = Long.valueOf(sRegin[0]);
            this.maxPoint = Long.valueOf(sRegin[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Long getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(Long minPoint) {
        this.minPoint = minPoint;
    }

    public Long getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(Long maxPoint) {
        this.maxPoint = maxPoint;
    }
}
