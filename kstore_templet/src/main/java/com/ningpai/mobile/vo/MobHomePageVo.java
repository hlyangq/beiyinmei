package com.ningpai.mobile.vo;

import com.ningpai.mobile.bean.MobHomePage;
import com.ningpai.util.PageBean;

import java.util.Date;

/**
 * @ClassName: MobHomePage
 * @Description: 实体类-移动版首页-可视化配置
 * @author Wanghy
 * @date 2014年10月11日 上午11:59:37
 * 
 */
public class MobHomePageVo{
    private Long homePageId;

    /** 商家ID */
    private Long merchantId;
    /** 第三方店铺ID */
    private Long storeId;
    /** 店铺名称 */
    private String title;
    /**
     * 1:第三方识别标记;0:newboss移动版识别标记
     * */
    private String isThird;
    /** 扩展字段1 (内容变更token) */
    private String temp1;
    /** 扩展字段2 (是否首页：0否；1是) */
    private String temp2;
    /** 扩展字段3 (模板标识：0页面类；1模板类)*/
    private String temp3;
    /** 扩展字段4 (禁用状态：0可用；1禁用)*/
    private String temp4;

    /** 页面分类*/
    private Long pageCateId;

    /** 页面状态：0:草稿；1上架中*/
    private Integer pageState;

    private Long[] homePageIds;

    private String exsisSubjectUrl;//用于删除前检测该页面是否被使用

    private Long tempId;

    private MobHomePage mobHomePage;

    // 分页开始的条数
    private int startRowNum;
    // 分页结束的条数
    private int endRowNum;


    public Long getHomePageId() {
        return homePageId;
    }

    public void setHomePageId(Long homePageId) {
        this.homePageId = homePageId;
    }

    /**
     * 获取merchantId
     *
     * @see #merchantId
     * @return the merchantId
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * 设置merchantId
     *
     * @see #merchantId
     * @param merchantId
     *            the merchantId to set
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getIsThird() {
        return isThird;
    }

    public void setIsThird(String isThird) {
        this.isThird = isThird;
    }

    /**
     * 获取temp1
     * 
     * @see #temp1
     * @return the temp1
     */
    public String getTemp1() {
        return temp1;
    }

    /**
     * 设置temp1
     * 
     * @see #temp1
     * @param temp1
     *            the temp1 to set
     */
    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    /**
     * 获取temp2
     * 
     * @see #temp2
     * @return the temp2
     */
    public String getTemp2() {
        return temp2;
    }

    /**
     * 设置temp2
     * 
     * @see #temp2
     * @param temp2
     *            the temp2 to set
     */
    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    /**
     * 获取temp3
     * 
     * @see #temp3
     * @return the temp3
     */
    public String getTemp3() {
        return temp3;
    }

    /**
     * 设置temp3
     * 
     * @see #temp3
     * @param temp3
     *            the temp3 to set
     */
    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }

    /**
     * 获取title
     * 
     * @see #title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title
     * 
     * @see #title
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPageCateId() {
        return pageCateId;
    }

    public void setPageCateId(Long pageCateId) {
        this.pageCateId = pageCateId;
    }

    public Integer getPageState() {
        return pageState;
    }

    public void setPageState(Integer pageState) {
        this.pageState = pageState;
    }

    public Long[] getHomePageIds() {
        return homePageIds;
    }

    public void setHomePageIds(Long[] homePageIds) {
        this.homePageIds = homePageIds;
    }

    public String getExsisSubjectUrl() {
        return exsisSubjectUrl;
    }

    public void setExsisSubjectUrl(String exsisSubjectUrl) {
        this.exsisSubjectUrl = exsisSubjectUrl;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    public MobHomePage getMobHomePage() {
        return mobHomePage;
    }

    public void setMobHomePage(MobHomePage mobHomePage) {
        this.mobHomePage = mobHomePage;
    }

    public String getTemp4() {
        return temp4;
    }

    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    public int getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public int getEndRowNum() {
        return endRowNum;
    }

    public void setEndRowNum(int endRowNum) {
        this.endRowNum = endRowNum;
    }
}
