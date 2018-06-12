/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
/**
 *
 */
package com.ningpai.mobile.service.impl;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.channel.bean.GoodsCate;
import com.ningpai.channel.dao.GoodsCateMapper;
import com.ningpai.mobile.bean.MobCateBar;
import com.ningpai.mobile.dao.MobCateBarMapper;
import com.ningpai.mobile.service.MobCateBarService;
import com.ningpai.mobile.vo.MobCateBarVo;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SERVICE实现类-移动版分类导航
 *
 * @author NINGPAI-WangHaiYang
 * @since 2014年8月19日下午1:56:18
 */
@Service("MobCateBarService")
public class MobCateBarServiceImpl implements MobCateBarService {

    @Resource(name = "MobCateBarMapper")
    private MobCateBarMapper mobCateBarMapper;

    @Resource(name = "ChannelGoodsCateMapper")
    private GoodsCateMapper goodsCateMapper;

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(MobCateBarServiceImpl.class);

    /**
     */
    @Override
    public List<ChannelAdver> selectStoreListImage(String userStatus) {
        return this.mobCateBarMapper.selectStoreListImage(userStatus);
    }

    /**
     * 分页查询店铺街轮播大广告
     *
     * @param pb
     * @param useStatus
     * @return
     */
    @Override
    public PageBean selectStoreListImageByPages(PageBean pb, String useStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("useStatus", useStatus);
        pb.setRows(mobCateBarMapper.selectTurnsAdvertsCount(map));
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        pb.setList(mobCateBarMapper.selectTurnsAdvertsList(map));
        return pb;
    }

    /**
     * 查询移动端 抢购广告
     *
     * @return 返回抢购广告
     */
    @Override
    public PageBean selectStoreListQianggouImage(PageBean pb) {
        Map<String, Object> map = new HashMap<String, Object>();
        pb.setRows(this.mobCateBarMapper.selectStoreListQianggouImageCount());
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        pb.setList(this.mobCateBarMapper.selectStoreListQianggouImage(map));
        return pb;
    }

    /**
     */
    @Override
    public PageBean selectMobCateBarByPb(PageBean pb) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pb.setRows(this.mobCateBarMapper.selectCountByPb());
            map.put("startRowNum", pb.getStartRowNum());
            map.put("endRowNum", pb.getEndRowNum());
            pb.setList(this.mobCateBarMapper.selectAllByPb(map));
        } catch (Exception e) {
            LOGGER.error("移动版分页查询错误：=>" ,e);
        }
        return pb;
    }

    /**
     */
    @Override
    public List<MobCateBarVo> selectMobCateBarForSite() {

        return this.mobCateBarMapper.selectAll();
    }

    @Override
    public List<MobCateBar> selectMobCateBarForMobChoose() {
        return this.mobCateBarMapper.selectAllForMobChoose();
    }

    /**
     */
    @Override
    public MobCateBar selectMobcateBarById(Long mobcatebarId) {
        return this.mobCateBarMapper.selectByPrimaryKey(mobcatebarId);
    }

    /**
     */
    @Override
    public int createMobCateBar(MobCateBar mobCateBar) {
        int n = -1;
        Date date = new Date();
        mobCateBar.setCreateDate(date);
        mobCateBar.setUpdateDate(date);

        n = this.mobCateBarMapper.insertSelective(mobCateBar);
        // 一级分类，级联添加它的子分类
        if (mobCateBar.getGrade() == 1) {
            List<GoodsCate> list = goodsCateMapper.queryGoosCateByParentId(mobCateBar.getCateId());
            if (null != list && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    MobCateBar child = new MobCateBar();
                    child.setCateId(list.get(i).getCatId());
                    child.setParentId(mobCateBar.getCateBarId());
                    child.setName(list.get(i).getCatName());
                    child.setGrade(2);
                    child.setSort(i + 1);
                    child.setIsUsing("1");
                    child.setDelflag("0");
                    child.setCreateDate(date);
                    child.setUpdateDate(date);
                    this.mobCateBarMapper.insertSelective(child);
                }
            }
        }
        return n;
    }

    /**
     */
    @Override
    public int updateMobCateBar(MobCateBar mobCateBar) {
        Date date = new Date();
        MobCateBar t_mobCateBar = this.mobCateBarMapper.selectByPrimaryKey(mobCateBar.getCateBarId());
        String isUsing = mobCateBar.getIsUsing();

        //如果分类改动有变化，删除所有子分类，将新分类id换上
        if(mobCateBar.getGrade() == 1 && ((t_mobCateBar.getCateId() == null && mobCateBar.getCateId() != null) ||  t_mobCateBar.getCateId().compareTo(mobCateBar.getCateId()) != 0 )){
            List<MobCateBar> childs = this.mobCateBarMapper.selectByParentId(mobCateBar.getCateBarId());
            if(CollectionUtils.isNotEmpty(childs)){
                for(MobCateBar child:childs){
                    this.mobCateBarMapper.deleteByParentId(child.getCateBarId());
                }
                this.mobCateBarMapper.deleteByParentId(mobCateBar.getCateBarId());
            }
            List<GoodsCate> cateList = goodsCateMapper.queryGoosCateByParentId(mobCateBar.getCateId());
            if (CollectionUtils.isNotEmpty(cateList)) {
                for (int i = 0; i < cateList.size(); i++) {
                    MobCateBar child = new MobCateBar();
                    child.setCateId(cateList.get(i).getCatId());
                    child.setParentId(mobCateBar.getCateBarId());
                    child.setName(cateList.get(i).getCatName());
                    child.setGrade(2);
                    child.setSort(i + 1);
                    child.setIsUsing(isUsing);
                    child.setDelflag("0");
                    child.setCreateDate(date);
                    child.setUpdateDate(date);
                    this.mobCateBarMapper.insertSelective(child);
                }
            }
        }else if ("0".equals(isUsing)) { // 设置为不启用时，它的子分类也要设置为不启用
            List<MobCateBar> list = this.mobCateBarMapper.selectByParentId(mobCateBar.getCateBarId());
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    MobCateBar cateBar = list.get(i);
                    cateBar.setIsUsing("0");
                    cateBar.setUpdateDate(date);
                    this.mobCateBarMapper.updateByPrimaryKeySelective(cateBar);
                }
            }
        }
        mobCateBar.setUpdateDate(date);
        return this.mobCateBarMapper.updateByPrimaryKeySelective(mobCateBar);
    }

    /**
     */
    @Override
    public int deleteMobCateBar(Long mobCateBarId) {
        Date date = new Date();
        MobCateBar mobCateBar = this.mobCateBarMapper.selectByPrimaryKey(mobCateBarId);
        mobCateBar.setDelflag("1");
        mobCateBar.setUpdateDate(date);
        List<MobCateBar> list = this.mobCateBarMapper.selectByParentId(mobCateBar.getCateBarId());
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                MobCateBar cateBar = list.get(i);
                cateBar.setDelflag("1");
                cateBar.setUpdateDate(date);
                this.mobCateBarMapper.updateByPrimaryKeySelective(cateBar);
            }
        }
        return this.mobCateBarMapper.updateByPrimaryKeySelective(mobCateBar);
    }

    /**
     */
    @Override
    public boolean checkMobCateBarIsOnly(Long cateId) {

        return this.mobCateBarMapper.selectCountByCateId(cateId) > 0 ? false : true;
    }

    /**
     */
    @Override
    public boolean checkDelete(Long mobCateBarId) {
        boolean b = true;
        List<MobCateBar> list = this.mobCateBarMapper.selectByParentId(mobCateBarId);
        if (null != list && !list.isEmpty()) {
            b = false;
        }
        return b;
    }

    /**
     */
    @Override
    public int changeUserdStatus(Long mobCateBarId) {
        Date date = new Date();
        MobCateBar mobCateBar = this.mobCateBarMapper.selectByPrimaryKey(mobCateBarId);
        String status = "0".equals(mobCateBar.getIsUsing()) ? "1" : "0";
        mobCateBar.setIsUsing(status);
        // 设置为不启用时，它的子分类也要设置为不启用
        if (mobCateBar.getGrade() == 1 && "0".equals(mobCateBar.getIsUsing())) {
            List<MobCateBar> list = this.mobCateBarMapper.selectByParentId(mobCateBar.getCateBarId());
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    MobCateBar cateBar = list.get(i);
                    cateBar.setIsUsing("0");
                    cateBar.setUpdateDate(date);
                    this.mobCateBarMapper.updateByPrimaryKeySelective(cateBar);
                }
            }
        }
        mobCateBar.setUpdateDate(date);
        return this.mobCateBarMapper.updateByPrimaryKeySelective(mobCateBar);
    }
    /**
     * 查询所有未删除、已启用的一级分类导航，返回List，前台调用
     *
     * @return List<MobCateBar>
     */
    @Override
    public List<MobCateBar> selectOneMobCate() {
        return this.mobCateBarMapper.selectOneMobCate();
    }
    /**
     * 根据ID查询它的子分类集合
     *
     * @param mobcatebarId 父级分类编号
     * @return List<MobCateBarVo>
     */
    @Override
    public List<MobCateBarVo> queryMobcateBarById(Long mobcatebarId) {
        return this.mobCateBarMapper.queryMobcateBarById(mobcatebarId);
    }

    /**
     * 根据父分类导航ID查询显示的未删除的子分类导航分类
     *
     * @param mobCateBarId 父分类导航id
     * @return 子分类导航集合
     */
    @Override
    public List<MobCateBarVo> queryUsingMobCateBar(Long mobCateBarId) {
        return this.mobCateBarMapper.queryUsingMobCateBar(mobCateBarId);
    }
}
