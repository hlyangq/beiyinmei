/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.mobile.service;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.mobile.bean.MobCateBar;
import com.ningpai.mobile.vo.MobCateBarVo;
import com.ningpai.util.PageBean;

import java.util.List;

/**
 * SERVICE-移动版分类导航
 * 
 * @author NINGPAI-WangHaiYang
 * @since 2014年8月19日上午11:25:36
 */
public interface MobCateBarService {

    /**
     * 查询移动端店铺页轮播大广告
     * 
     * @return 获取的移动端轮播大广告图片集合
     * @author zhanghl
     */
    List<ChannelAdver> selectStoreListImage(String userStatus);

    /**
     * 分页查询店铺街轮播大广告
     * @param pb
     * @param useStatus
     * @return
     */
    PageBean selectStoreListImageByPages(PageBean pb, String useStatus);

    /**
     * 查询移动端 抢购广告
     *
     * @return 返回抢购广告
     */
    PageBean selectStoreListQianggouImage(PageBean pb);

    /**
     * 查询未删除的分类导航分页数据，返回分页工具bean
     * 
     * @param pb
     * @return
     */
    PageBean selectMobCateBarByPb(PageBean pb);

    /**
     * 查询所有未删除、已启用的分类导航，返回List，前台调用
     * 
     * @return
     */
    List<MobCateBarVo> selectMobCateBarForSite();

    /**
     * 查询所有未删除、已启用的分类导航，返回List，后台选择用
     * 
     * @return
     */
    List<MobCateBar> selectMobCateBarForMobChoose();

    /**
     * 根据ID查询分类导航
     * 
     * @param mobcatebarId
     * @return
     */
    MobCateBar selectMobcateBarById(Long mobcatebarId);

    /**
     * 添加分类导航
     * 
     * @param mobCateBar
     * @return
     */
    int createMobCateBar(MobCateBar mobCateBar);

    /**
     * 修改分类导航，层级不可变，设置为不启用时，它的子分类也要设置为不启用
     * 
     * @param mobCateBar
     * @return
     */
    int updateMobCateBar(MobCateBar mobCateBar);

    /**
     * 删除分类导航，删除时要级联删除它的子分类
     * 
     * @param mobCateBarId
     * @return
     */
    int deleteMobCateBar(Long mobCateBarId);

    /**
     * 验证分类导航的唯一性，用关联的商品分类ID验证
     * 
     * @param cateId
     * @return
     */
    boolean checkMobCateBarIsOnly(Long cateId);

    /**
     * 验证是否可删除分类导航
     * 
     * @param mobCateBarId
     * @return
     */
    boolean checkDelete(Long mobCateBarId);

    /**
     * 修改分类导航的启用状态
     * 
     * @param mobCateBarId
     * @return
     */
    int changeUserdStatus(Long mobCateBarId);

    /**
     * 查询所有未删除、已启用的一级分类导航，返回List，前台调用
     *
     * @return List<MobCateBar>
     */
    List<MobCateBar> selectOneMobCate();

    /**
     * 根据ID查询它的子分类集合
     *
     * @param mobcatebarId
     * @return List<MobCateBarVo>
     */
    List<MobCateBarVo>  queryMobcateBarById(Long mobcatebarId);

    /**
     * 根据父分类导航ID查询显示的未删除的子分类导航分类
     * @param mobCateBarId 父分类导航id
     * @return  子分类导航集合
     */
    List<MobCateBarVo> queryUsingMobCateBar(Long mobCateBarId);
}
