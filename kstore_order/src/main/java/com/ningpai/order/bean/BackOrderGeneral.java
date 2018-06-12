package com.ningpai.order.bean;

import java.util.Date;

/**
 * 退单物流类
 * 
 * @author zhanghailong
 *
 */
public class BackOrderGeneral {
    /**
     * 退单物流Id
     */
    private Long ogisticsId;
    /**
     * 退单物流单号
     */
    private String ogisticsNo;
    /**
     * 退单物流名称
     */
    private String ogisticsName;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 退单Id
     */
    private Long backOrderId;

    /**
     * 备注
     */
    private String temp;

    public BackOrderGeneral() {

    }

    public Long getOgisticsId() {
        return ogisticsId;
    }

    public void setOgisticsId(Long ogisticsId) {
        this.ogisticsId = ogisticsId;
    }

    public String getOgisticsNo() {
        return ogisticsNo;
    }

    public void setOgisticsNo(String ogisticsNo) {
        this.ogisticsNo = ogisticsNo;
    }

    public String getOgisticsName() {
        return ogisticsName;
    }

    public void setOgisticsName(String ogisticsName) {
        this.ogisticsName = ogisticsName;
    }
    /**
     * 获取时间
     * */
    public Date getCreatTime() {
        return creatTime==null?null: (Date) creatTime.clone();
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Long getBackOrderId() {
        return backOrderId;
    }

    public void setBackOrderId(Long backOrderId) {
        this.backOrderId = backOrderId;
    }

}
