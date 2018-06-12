/*
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.mobile.service.impl;

import com.ningpai.mobile.bean.MobHomePage;
import com.ningpai.mobile.dao.MobHomePageMapper;
import com.ningpai.mobile.service.MobHomePageService;
import com.ningpai.mobile.vo.MobHomePageVo;
import com.ningpai.util.PageBean;
import com.ningpai.util.xml.XmlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SERVICE实现类-移动版可视化配置首页
 *
 * @ClassName: MobHomePageServiceImpl
 * @Description: 移动版可视化配置首页的SERVICE实现
 * @author Wanghy
 * @date 2014年10月17日 上午10:02:33
 *
 */
@Service("MobHomePageService")
public class MobHomePageServiceImpl implements MobHomePageService {

    static final String xmlFilePath = "mobile_home_page/xml/9gdemo.xml";

    @Resource(name = "MobHomePageMapper")
    private MobHomePageMapper mobHomePageMapper;

    @Override
    public void createHomePage(MobHomePage homePage) {
        homePage.setTemp2("0");
        homePage.setCreateDate(new Date());
        homePage.setUpdateDate(new Date());
        homePage.setUpdateUserId(getLoginUserId());
        this.mobHomePageMapper.insertSelective(homePage);
    }

    @Override
    public void updateHomePage(MobHomePage homePage) {
        homePage.setUpdateDate(new Date());
        homePage.setUpdateUserId(getLoginUserId());
        this.mobHomePageMapper.updateByPrimaryKeySelective(homePage);
    }

    @Override
    public MobHomePage selectHomePageByMerchantId(Long merchantId) {
        MobHomePage homePage = mobHomePageMapper.selectByMerchantId(merchantId);
        if (null == homePage) {
            // 第二个参数判断是否为第三方,第三个参数为第三方的店铺id
            initHomePage(merchantId, null, null);
            homePage = mobHomePageMapper.selectByMerchantId(merchantId);
        }
        return homePage;
    }

    @Override
    public MobHomePage initHomePage(Long merchantId, String isThird, Long storeId) {
        XmlUtil xmlUtil = new XmlUtil();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String filePath = request.getSession().getServletContext().getRealPath("/")+"/";
        String fileName = filePath + xmlFilePath;
        Document document = xmlUtil.parserXml(fileName);

        String xmlStr = xmlUtil.document2Str(document);
        MobHomePage homePage = new MobHomePage();
        homePage.setDoc(xmlStr);
        homePage.setDocBac(xmlStr);
        homePage.setMerchantId(merchantId);
        homePage.setUpdateDate(new Date());
        homePage.setUpdateUserId(getLoginUserId());
        if (StringUtils.isNotEmpty(isThird)) {
            homePage.setIsThird(isThird);
            homePage.setStoreId(storeId);
        }
        createHomePage(homePage);
        return homePage;
    }

    @Override
    public void makeHtml() {
        XmlUtil xmlUtil = new XmlUtil();
        xmlUtil.Transform("xmlFileName", "xslFileName", "htmlFileName");
    }

    @Override
    public void deleteHomePageById(Long homepageId) {
        mobHomePageMapper.deleteByPrimaryKey(homepageId);

    }

    @Override
    public MobHomePage getHomePageById(Long homepageId) {

        return mobHomePageMapper.selectByPrimaryKey(homepageId);
    }

    @Override
    public MobHomePage selectThirdMob(Long storeId) {

        return mobHomePageMapper.selectThirdMob(storeId);
    }

    @Override
    public void openHomePageByHIdAndMId(Long homepageId, Long merchantId) {

        mobHomePageMapper.updateByMerchantId(merchantId);
        MobHomePage mobHomePage = new MobHomePage();
        mobHomePage.setHomepageId(homepageId);
        mobHomePage.setMerchantId(merchantId);
        mobHomePage.setUpdateDate(new Date());
        mobHomePageMapper.updateByhomepageIdAndMerchantId(mobHomePage);

    }

    @Override
    public List<MobHomePage> selectAllUnstatusByMerchantId(Long merchantId) {

        return mobHomePageMapper.selectAllUnstatusByMerchantId(merchantId);
    }

    @Override
    public MobHomePage getCurrHomePageByMerchantId(Long merchantId) {

        return mobHomePageMapper.queryCurrHomePageByMerchantId(merchantId);
    }

    /*
     * 根据商家ID获取该商家当前使用的模板信息
     * 
     * @Description: 根据商家ID获取该商家当前使用的模板信息
     * 
     * @param storeId
     * 
     * @return
     */
    @Override
    public MobHomePage getCurrHomePageByStoreId(Long storeId) {
        return mobHomePageMapper.getCurrHomePageByStoreId(storeId);
    }

    /*
     * 获取当前操作的用户ID
     */
    private Long getLoginUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (Long) request.getAttribute("loginUserId");
    }

    @Override
    public PageBean selectMobPageByPb(MobHomePageVo vo, PageBean pageBean) throws Exception {
        int rows = this.mobHomePageMapper.selectCount(vo);
        pageBean.setRows(rows);
        if(rows > 0){
            vo.setStartRowNum(pageBean.getStartRowNum());
            vo.setEndRowNum(pageBean.getEndRowNum());
            pageBean.setList(this.mobHomePageMapper.selectByPb(vo));
        }
        return pageBean;
    }

    /**
     * 检测页面是否被使用
     * @Title: deleteMobPage
     * @Description: 根据ID检测页面是否被使用
     * @param homePageIds
     * @return 0:未使用,-1:该页面有主页,1:已使用
     */
    public int isUsed(Long[] homePageIds) throws Exception{
        MobHomePageVo vo = new MobHomePageVo();
        int count = 0;
        //检测页面是否被其他页面使用
        for(Long id : homePageIds){
            MobHomePage page = this.mobHomePageMapper.selectByPrimaryKey(id);
            if(page != null && "1".equals(page.getTemp2())){
                return -1;
            }
            vo.setExsisSubjectUrl("page/".concat(String.valueOf(id)).concat("htm"));
            count += this.mobHomePageMapper.selectCount(vo);
        }
        if(count>0){
            return 1;
        }
        return 0;
    }

    /**
     * 根据ID删除页面设置
     * @Title: deleteMobPage
     * @Description: 根据ID删除页面设置
     * @param homePageIds
     * @return 0:该页面被其他页面使用,-1:该页面有主页,1:删除成功
     */
    @Transactional
    public int deleteMobPage(Long[] homePageIds) throws Exception{
        for(Long id : homePageIds){
            this.mobHomePageMapper.deleteByPrimaryKey(id);
        }
        return 1;
    }

    /**
     * 新增页面设置
     * @param vo
     * @return 0:新增失败,>0:新增成功
     */
    @Transactional
    public Long addMobPage(MobHomePageVo vo) throws Exception{
        Long tempId = vo.getTempId();
        MobHomePage homePage = new MobHomePage();
        if(tempId != null && tempId.longValue() > 0){
            homePage = this.mobHomePageMapper.selectByPrimaryKey(tempId);
            homePage.setHomepageId(null);
        }else{
            XmlUtil xmlUtil = new XmlUtil();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String filePath = request.getSession().getServletContext().getRealPath("/")+"/";
            String fileName = filePath + xmlFilePath;
            Document document = xmlUtil.parserXml(fileName);
            String xmlStr = xmlUtil.document2Str(document);
            homePage.setDoc(xmlStr);
            homePage.setDocBac(xmlStr);
        }
        homePage.setCreateDate(new Date());
        homePage.setSort(0);
        homePage.setTitle("");
        homePage.setIsThird(vo.getIsThird());
        homePage.setStoreId(vo.getStoreId());
        homePage.setTemp2("0");
        homePage.setTemp3("0");
        homePage.setTemp4("1");//新增时候先禁用，点击保存草稿或发布时改为可用
        homePage.setMerchantId(vo.getMerchantId());
        homePage.setUpdateDate(new Date());
        homePage.setUpdateUserId(getLoginUserId());
        homePage.setAuthor("");
        homePage.setHomeDesc("");
        homePage.setFriendDesc("");
        homePage.setHomeImg("");
        homePage.setLogo("");
        this.mobHomePageMapper.insertSelective(homePage);
        if(homePage.getHomepageId()!=null){
            return homePage.getHomepageId();
        }

        return 0l;
    }
}
