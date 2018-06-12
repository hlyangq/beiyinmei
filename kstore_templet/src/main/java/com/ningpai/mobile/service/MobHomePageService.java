/*
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.mobile.service;

import com.ningpai.mobile.bean.MobHomePage;
import com.ningpai.mobile.vo.MobHomePageVo;
import com.ningpai.util.PageBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SERVICE-移动版可视化配置首页
 * 
 * @ClassName: MobHomePageService
 * @Description: 移动版可视化配置首页的SERVICE
 * @author Wanghy
 * @date 2014年10月17日 上午10:00:03
 */
public interface MobHomePageService {
    /**
     * 添加移动版可视化配置首页
     * 
     * @Title: createHomePage
     * @Description: 添加移动版可视化配置首页
     * @param homePage
     */
    void createHomePage(MobHomePage homePage);

    /**
     * 修改移动版可视化配置首页
     * 
     * @Title: updateHomePage
     * @Description: 修改移动版可视化配置首页
     * @param homePage
     */
    void updateHomePage(MobHomePage homePage);

    /**
     * 根据商家ID查询
     * 
     * @Title: selectHomePageByMerchantId
     * @Description: 根据商家ID查询
     * @param merchantId
     * @return
     */
    MobHomePage selectHomePageByMerchantId(Long merchantId);

    /**
     * 初始化商家的HomePage
     *
     * @param merchantId
     *  @param isThird
     */
    MobHomePage initHomePage(Long merchantId,String isThird,Long storeId);

    /**
     * 生成HTML
     * 
     * @Title: makeHtml
     * @Description: 生成HTML
     */
    void makeHtml();

    /**
     * 根据商家ID查询该商家的所有模板
     * 
     * @Title: selectAllHomePageByMerchantId
     * @Description: 根据商家ID查询该商家的所有模板
     * @param merchantId
     * @return
     */
    List<MobHomePage> selectAllUnstatusByMerchantId(Long merchantId);

    /**
     * 根据ID获取模板信息
     * 
     * @Title: getHomePageById
     * @Description: 根据ID获取模板信息
     * @param homepageId
     * @return
     */
    MobHomePage getHomePageById(Long homepageId);

    /**
     * 根据ID删除模板信息
     * 
     * @Title: deleteHomePageById
     * @Description: 根据ID删除模板信息
     * @param homepageId
     */
    void deleteHomePageById(Long homepageId);

    /**
     * 查询第三方移动模板信息
     *
     * @return
     */
    MobHomePage selectThirdMob(Long storeId);

    /**
     * 启用模板
     * 
     * @Title: openHomePageByHIdAndMId
     * @Description: 根据商家ID和模板ID找到模板，修改启用状态，并把该商家下的其他模板禁用，启用状态字段使用temp2：0不启用 1启用
     * @param homepageId
     *            模板ID
     * @param merchantId
     *            商家ID
     */
    void openHomePageByHIdAndMId(Long homepageId, Long merchantId);

    /**
     * 根据商家ID获取该商家当前使用的模板信息
     * 
     * @Title: getCurrHomePageByMerchantId
     * @Description: 根据商家ID获取该商家当前使用的模板信息
     * @param merchantId
     * @return
     */
    MobHomePage getCurrHomePageByMerchantId(Long merchantId);
    /**
     * 根据商家ID获取该商家当前使用的模板信息
     *
     * @Description: 根据商家ID获取该商家当前使用的模板信息
     * @param storeId
     * @return
     */
    MobHomePage getCurrHomePageByStoreId(Long storeId);

    /**
     * 分页查询页面设置
     * @param vo
     * @return
     * @throws Exception
     */
    public PageBean selectMobPageByPb(MobHomePageVo vo, PageBean pageBean) throws Exception;

    /**
     * 检测页面是否被使用
     * @Title: deleteMobPage
     * @Description: 根据ID检测页面是否被使用
     * @param homePageIds
     * @return 0:未使用,-1:该页面有主页,1:已使用
     */
    public int isUsed(Long[] homePageIds) throws Exception;

    /**
     * 根据ID删除页面设置
     * @Title: deleteMobPage
     * @Description: 根据ID删除页面设置
     * @param homePageIds
     * @return 0:该页面被其他页面使用,-1:该页面有主页,1:删除成功
     */
    public int deleteMobPage(Long[] homePageIds) throws Exception;

    /**
     * 新增页面设置
     * @param vo
     * @return 0:新增失败,>0:新增成功
     */
    public Long addMobPage(MobHomePageVo vo) throws Exception;
}
