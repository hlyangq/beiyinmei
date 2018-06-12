/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
/**
 * 
 */
package com.ningpai.mobile.dao.impl;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.mobile.bean.MobCateBar;
import com.ningpai.mobile.dao.MobCateBarMapper;
import com.ningpai.mobile.vo.MobCateBarVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO实现类-移动版分类导航
 * 
 * @author NINGPAI-WangHaiYang
 * @since 2014年8月19日下午1:38:34
 */
@Repository("MobCateBarMapper")
public class MobCateBarMapperImpl extends BasicSqlSupport implements MobCateBarMapper {

    /**
     */
    @Override
    public List<ChannelAdver> selectStoreListImage(String userStatus) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_status", userStatus);
        return this.selectList("com.ningpai.channel.dao.ChannelAdverMapper.selectStoreListImage", map);
    }

    /**
     * 查询移动端抢购页面
     *
     * @param map 参数
     * @return  返回抢购广告信息
     */
    @Override
    public List<Object> selectStoreListQianggouImage(Map<String, Object> map) {
        return this.selectList("com.ningpai.channel.dao.ChannelAdverMapper.selectStoreListQianggouImage", map);
    }

    /**
     */
    @Override
    public int deleteByPrimaryKey(Long cateBarId) {
        return this.delete("com.ningpai.mobile.dao.MobCateBarMapper.deleteByPrimaryKey", cateBarId);
    }

    /**
     */
    @Override
    public int insert(MobCateBar record) {

        return this.insert("com.ningpai.mobile.dao.MobCateBarMapper.insert", record);
    }

    /**
     */
    @Override
    public int insertSelective(MobCateBar record) {

        return this.insert("com.ningpai.mobile.dao.MobCateBarMapper.insertSelective", record);
    }

    /**
     */
    @Override
    public int updateByPrimaryKeySelective(MobCateBar record) {

        return this.update("com.ningpai.mobile.dao.MobCateBarMapper.updateByPrimaryKeySelective", record);
    }

    /**
     */
    @Override
    public int updateByPrimaryKey(MobCateBar record) {

        return this.update("com.ningpai.mobile.dao.MobCateBarMapper.updateByPrimaryKey", record);
    }

    /**
     */
    @Override
    public MobCateBar selectByPrimaryKey(Long cateBarId) {

        return this.selectOne("com.ningpai.mobile.dao.MobCateBarMapper.selectByPrimaryKey", cateBarId);
    }

    /**
     */
    @Override
    public List<MobCateBar> selectByParentId(Long parentId) {

        return this.selectList("com.ningpai.mobile.dao.MobCateBarMapper.selectByParentId", parentId);
    }

    /**
     */
    @Override
    public int selectCountByCateId(Long cateId) {

        return this.selectOne("com.ningpai.mobile.dao.MobCateBarMapper.selectCountByCateId");
    }

    /**
     */
    @Override
    public List<MobCateBarVo> selectAll() {

        return this.selectList("com.ningpai.mobile.dao.MobCateBarMapper.selectAll");
    }

    @Override
    public List<MobCateBar> selectAllForMobChoose() {
        return this.selectList("com.ningpai.mobile.dao.MobCateBarMapper.selectAllForMobChoose");
    }

    /**
     */
    @Override
    public int selectCountByPb() {

        return this.selectOne("com.ningpai.mobile.dao.MobCateBarMapper.selectCountByPb");
    }

    /**
     * 查询抢购广告数量
     *
     * @return 返回抢购广告数量
     */
    @Override
    public int selectStoreListQianggouImageCount() {
        return this.selectOne("com.ningpai.channel.dao.ChannelAdverMapper.selectStoreListQianggouImageCount");
    }

    /**
     */
    @Override
    public List<Object> selectAllByPb(Map<String, Object> map) {

        return this.selectList("com.ningpai.mobile.dao.MobCateBarMapper.selectAllByPb", map);
    }
    /**
     * 查询所有未删除、已启用的一级分类导航，返回List，前台调用不包含子分类
     *
     * @return List<MobCateBar>
     */
    @Override
    public List<MobCateBar> selectOneMobCate() {
        return this.selectList("com.ningpai.mobile.dao.MobCateBarMapper.selectOneMobCate");
    }
    /**
     * 根据ID查询分类导航和它的子分类集合
     *
     * @param mobcatebarId 分类编号
     * @return List<MobCateBarVo>
     */
    @Override
    public List<MobCateBarVo> queryMobcateBarById(Long mobcatebarId) {
        return this.selectList("com.ningpai.mobile.dao.MobCateBarMapper.selectCByParentId",mobcatebarId);
    }

    /**
     * 根据父分类导航ID查询显示的未删除的子分类导航分类
     *
     * @param mobCateBarId 父分类导航id
     * @return 子分类导航集合
     */
    @Override
    public List<MobCateBarVo> queryUsingMobCateBar(Long mobCateBarId) {
        return this.selectList("com.ningpai.mobile.dao.MobCateBarMapper.queryUsingMobCateBar", mobCateBarId);
    }

    /**
     * 查询移动端轮播打广告列表
     *
     * @param map
     * @return
     */
    @Override
    public List<Object> selectTurnsAdvertsList(Map<String, Object> map) {
        return selectList("com.ningpai.channel.dao.ChannelAdverMapper.selectStoreListImageByPages", map);
    }

    /**
     * 查询轮播打广告总数
     *
     * @param map
     * @return
     */
    @Override
    public int selectTurnsAdvertsCount(Map<String, Object> map) {
        return selectOne("com.ningpai.channel.dao.ChannelAdverMapper.selectStoreListImageCount", map);
    }

    /**
     * 根据父Id删除
     */
    @Override
    public int deleteByParentId(Long parentId) {
        return this.delete("com.ningpai.mobile.dao.MobCateBarMapper.deleteByParentId", parentId);
    }
}
