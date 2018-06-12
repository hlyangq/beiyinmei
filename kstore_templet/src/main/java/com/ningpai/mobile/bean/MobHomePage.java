package com.ningpai.mobile.bean;

import java.util.Date;

/**
 * @ClassName: MobHomePage
 * @Description: 实体类-移动版首页-可视化配置
 * @author Wanghy
 * @date 2014年10月11日 上午11:59:37
 * 
 */
public class MobHomePage {
    /** 编号 */
    private Long homepageId;
    /** 商家ID */
    private Long merchantId;
    /** 第三方店铺ID */
    private Long storeId;
    /** 店铺名称 */
    private String title;
    /** 店铺Logo */
    private String logo;
    /** 转发到朋友圈的内容 */
    private String friendsDesc;
    /** 转发给朋友的内容 */
    private String friendDesc;
    /** 页面简介 */
    private String homeDesc;
    /** 页面预览图片地址 */
    private String homeImg;
    /** 作者 */
    private String author;
    /** xml字符串 */
    private String doc;
    /** xml字符串备份 */
    private String docBac;
    /**
     * 1:第三方识别标记;0:newboss移动版识别标记
     * */
    private String isThird;
    /** 修改人ID */
    private Long updateUserId;
    /** 修改时间 */
    private Date updateDate;
    /** 扩展字段1 (内容变更token) */
    private String temp1;
    /** 扩展字段2 (是否首页：0否；1是) */
    private String temp2;
    /** 扩展字段3 (模板标识：0页面类；1模板类)*/
    private String temp3;
    /** 扩展字段4 (禁用状态：0可用；1禁用)*/
    private String temp4;
    /** 扩展字段5 */
    private String temp5;

    /** 页面分类*/
    private Long pageCateId;

    /** 页面排序*/
    private Integer sort;

    /** 页面状态：0:草稿；1上架中*/
    private Integer pageState;

    /** 修改时间 */
    private Date createDate;

    /**
     * 获取homepageId
     *
     * @see #homepageId
     * @return the homepageId
     */
    public Long getHomepageId() {
        return homepageId;
    }

    /**
     * 设置homepageId
     *
     * @see #homepageId
     * @param homepageId
     *            the homepageId to set
     */
    public void setHomepageId(Long homepageId) {
        this.homepageId = homepageId;
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

    /**
     * 获取doc
     *
     * @see #doc
     * @return the doc
     */
    public String getDoc() {
        return doc;
    }

    /**
     * 设置doc
     *
     * @see #doc
     * @param doc
     *            the doc to set
     */
    public void setDoc(String doc) {
        this.doc = doc;
    }

    /**
     * 获取docBac
     *
     * @see #docBac
     * @return the docBac
     */
    public String getDocBac() {
        return docBac;
    }

    /**
     * 设置docBac
     *
     * @see #docBac
     * @param docBac
     *            the docBac to set
     */
    public void setDocBac(String docBac) {
        this.docBac = docBac;
    }

    /**
     * 获取updateUserId
     *
     * @see #updateUserId
     * @return the updateUserId
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置updateUserId
     *
     * @see #updateUserId
     * @param updateUserId
     *            the updateUserId to set
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 获取updateDate
     *
     * @see #updateDate
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate==null?null:(Date) updateDate.clone();
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * 设置updateDate
     *
     * @see #updateDate
     * @param updateDate
     *            the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate == null ? null : (Date) updateDate.clone();
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
     * 获取temp4
     * 
     * @see #temp4
     * @return the temp4
     */
    public String getTemp4() {
        return temp4;
    }

    /**
     * 设置temp4
     * 
     * @see #temp4
     * @param temp4
     *            the temp4 to set
     */
    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    /**
     * 获取temp5
     * 
     * @see #temp5
     * @return the temp5
     */
    public String getTemp5() {
        return temp5;
    }

    /**
     * 设置temp5
     * 
     * @see #temp5
     * @param temp5
     *            the temp5 to set
     */
    public void setTemp5(String temp5) {
        this.temp5 = temp5;
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

    /**
     * 获取logo
     * 
     * @see #logo
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置logo
     * 
     * @see #logo
     * @param logo
     *            the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取friendsDesc
     * 
     * @see #friendsDesc
     * @return the friendsDesc
     */
    public String getFriendsDesc() {
        return friendsDesc;
    }

    /**
     * 设置friendsDesc
     * 
     * @see #friendsDesc
     * @param friendsDesc
     *            the friendsDesc to set
     */
    public void setFriendsDesc(String friendsDesc) {
        this.friendsDesc = friendsDesc;
    }

    /**
     * 获取friendDesc
     * 
     * @see #friendDesc
     * @return the friendDesc
     */
    public String getFriendDesc() {
        return friendDesc;
    }

    /**
     * 设置friendDesc
     * 
     * @see #friendDesc
     * @param friendDesc
     *            the friendDesc to set
     */
    public void setFriendDesc(String friendDesc) {
        this.friendDesc = friendDesc;
    }

    /**
     * 获取homeDesc
     * 
     * @see #homeDesc
     * @return the homeDesc
     */
    public String getHomeDesc() {
        return homeDesc;
    }

    /**
     * 设置homeDesc
     * 
     * @see #homeDesc
     * @param homeDesc
     *            the homeDesc to set
     */
    public void setHomeDesc(String homeDesc) {
        this.homeDesc = homeDesc;
    }

    /**
     * 获取homeImg
     * 
     * @see #homeImg
     * @return the homeImg
     */
    public String getHomeImg() {
        return homeImg;
    }

    /**
     * 设置homeImg
     * 
     * @see #homeImg
     * @param homeImg
     *            the homeImg to set
     */
    public void setHomeImg(String homeImg) {
        this.homeImg = homeImg;
    }

    /**
     * 获取author
     * 
     * @see #author
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置author
     * 
     * @see #author
     * @param author
     *            the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getPageCateId() {
        return pageCateId;
    }

    public void setPageCateId(Long pageCateId) {
        this.pageCateId = pageCateId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPageState() {
        return pageState;
    }

    public void setPageState(Integer pageState) {
        this.pageState = pageState;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
