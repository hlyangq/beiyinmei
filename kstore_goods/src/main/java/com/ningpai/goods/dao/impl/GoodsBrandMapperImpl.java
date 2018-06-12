/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.goods.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ningpai.goods.bean.GoodsBrand;
import com.ningpai.goods.dao.GoodsBrandMapper;
import com.ningpai.goods.util.SelectBean;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * 商品品牌DAO实现
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2013年12月16日 下午7:44:27
 * @version
 */
@Repository("GoodsBrandMapper")
public class GoodsBrandMapperImpl extends BasicSqlSupport implements
        GoodsBrandMapper {
    /**
     * 根据主键删除商品品牌
     *
     * @param map
     *            品牌主键ID {@link java.lang.Long}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    public int deleteByPrimaryKey(Map<String, String> map) {
        return this.update(
                "com.ningpai.goods.dao.GoodsBrandMapper.deleteByPrimaryKey",
                map);
    }

    /**
     * 插入一条商品品牌（全属性）
     *
     * @param record
     *            商品品牌实体 {@link com.ningpai.goods.bean.GoodsBrand}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    public int insert(GoodsBrand record) {
        return this.insert("com.ningpai.goods.dao.GoodsBrandMapper.insert",
                record);
    }

    /**
     * 插入一条商品品牌(可选属性,包括详细介绍)
     *
     * @param record
     *            商品品牌实体 {@link com.ningpai.goods.bean.GoodsBrand}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    public int insertSelective(GoodsBrand record) {
        return this.insert(
                "com.ningpai.goods.dao.GoodsBrandMapper.insertSelective",
                record);
    }

    /**
     * 根据主键查询品牌
     *
     * @param brandId
     *            品牌主键ID {@link java.lang.Long}
     * @return 商品品牌实体 {@link com.ningpai.goods.bean.GoodsBrand}
     */
    public GoodsBrand selectByPrimaryKey(Long brandId) {
        return this.selectOne(
                "com.ningpai.goods.dao.GoodsBrandMapper.selectByPrimaryKey",
                brandId);
    }

    /**
     * 更新商品品牌(可选属性)
     *
     * @param record
     *            商品品牌实体 {@link com.ningpai.goods.bean.GoodsBrand}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    public int updateByPrimaryKeySelective(GoodsBrand record) {
        return this
                .update("com.ningpai.goods.dao.GoodsBrandMapper.updateByPrimaryKeySelective",
                        record);
    }

    /**
     * 更新商品品牌(全属性)
     *
     * @param record
     *            商品品牌实体 {@link com.ningpai.goods.bean.GoodsBrand}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    public int updateByPrimaryKeyWithBLOBs(GoodsBrand record) {
        return this
                .update("com.ningpai.goods.dao.GoodsBrandMapper.updateByPrimaryKeyWithBLOBs",
                        record);
    }

    /**
     * 更新商品品牌(不更新商品介绍)
     *
     * @param record
     *            商品品牌实体 {@link com.ningpai.goods.bean.GoodsBrand}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    public int updateByPrimaryKey(GoodsBrand record) {
        return this.update(
                "com.ningpai.goods.dao.GoodsBrandMapper.updateByPrimaryKey",
                record);
    }

    /**
     * 查询所有的记录数
     *
     * @return {@link java.lang.Integer}
     */
    public int queryTotalCount() {
        return this
                .selectOne("com.ningpai.goods.dao.GoodsBrandMapper.queryTotalCount");
    }

    /**
     * 根据分页参数查询分页列表
     *
     * @param map
     *            包括开始的条数和结束的条数
     * @return 查询到的实体列表 {@link com.ningpai.goods.bean.GoodsBrand}
     *         {@link java.util.List}
     */
    public List<Object> queryByPageBean(Map<String, Integer> map) {
        return this.selectList(
                "com.ningpai.goods.dao.GoodsBrandMapper.queryByPageBean", map);
    }

    /**
     * 查询所有的商品品牌
     *
     * @return 查询到的商品品牌的集合 {@link java.util.List}
     *         {@link com.ningpai.goods.bean.GoodsBrand}
     */
    public List<GoodsBrand> queryAllBrand() {
        return this
                .selectList("com.ningpai.goods.dao.GoodsBrandMapper.queryAllBrand");
    }

    /**
     * 分页查询总行数
     *
     * @param selectBean
     * @return int
     */
    public int searchTotalCount(SelectBean selectBean) {

        return this.selectOne(
                "com.ningpai.goods.dao.GoodsBrandMapper.searchTotalCount",
                selectBean);
    }

    /**
     * 分页查询信息
     *
     * @param map
     * @return List
     */
    public List<Object> searchAllBrand(Map<String, Object> map) {
        return this.selectList(
                "com.ningpai.goods.dao.GoodsBrandMapper.searchAllBrand", map);
    }

    /**
     * 根据List查询多个商品品牌
     *
     * @param list
     * @return List
     */
    public List<Object> selectProductBrandList(List<Long> list) {
        return this
                .selectList(
                        "com.ningpai.goods.dao.GoodsBrandMapper.selectProductBrandList",
                        list);
    }

    /**
     * 查询所有brand
     *
     * @return List
     */
    public List<GoodsBrand> queryAllBrandList() {
        return this
                .selectList("com.ningpai.goods.dao.GoodsBrandMapper.queryAllBrandList");
    }

    /**
     * 根据名称查询商品品牌
     *
     * @return 查询到的商品品牌的集合 {@link java.util.List}
     *         {@link com.ningpai.goods.bean.GoodsBrand}
     */
    @Override
    public List<GoodsBrand> queryallbrandbyName(String brandName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("brandName", brandName);
        return this.selectList(
                "com.ningpai.goods.dao.GoodsBrandMapper.queryallbrandbyName",
                map);
    }

    /**
     * 根据品牌名称查询行数
     *
     * @param brandName
     *            待查询的行数　{@link java.lang.String}
     * @return 查询到的行数{@link java.lang.Integer}
     */
    public int queryByBrandName(String brandName) {
        return this.selectOne(
                "com.ningpai.goods.dao.GoodsBrandMapper.queryByBrandName",
                brandName);
    }

    /**
     * 根据品牌名称查询行数
     *
     * @param brandName
     *            待查询的行数　{@link java.lang.String}
     * @return 查询到的行数{@link java.lang.Integer}
     */
    public int selectByBrandName(String brandName) {
        return this.selectOne(
                "com.ningpai.goods.dao.GoodsBrandMapper.selectByBrandName",
                brandName);
    }
    /**
     * 根据品牌名称查询商品品牌
     * @param brandName 品牌名称
     * @return 商品品牌
     */
    @Override
    public GoodsBrand selectGoodsBrandByBrandName(String brandName){
        return this.selectOne(
                "com.ningpai.goods.dao.GoodsBrandMapper.selectGoodsBrandByBrandName",
                brandName);
    }

    /**
     * 根据品牌名称查询申请自定义品牌记录数
     *
     * @param third     第三方id
     * @param brandName 平铺名称
     * @return 返回记录数
     */
    @Override
    public int selectApplyBrandByBrandName(Long third, String brandName) {
        Map<String,Object> params = new HashMap<>();
        params.put("thirdId",third);
        params.put("barndName",brandName);
        return this.selectOne(
                "com.ningpai.goods.dao.GoodsBrandMapper.selectApplyBrandByBrandName",
                params);
    }

    /**
     * 根据品牌名称查询行数（后台添加品牌时调用）
     *
     * @param brandName
     *            品牌名称
     * @return int 查询到的行数 1有记录 0是没记录
     * @author houyichang 2015/6/30
     */
    @Override
    public int queryBrandByBrandName(String brandName) {
        return this.selectOne(
                "com.ningpai.goods.dao.GoodsBrandMapper.queryBrandByBrandName",
                brandName);
    }

    /**
     * 查询商品品牌关联的商品总记录数
     *
     * @param barndId 商品品牌id
     * @return 返回商品品牌关联的商品总记录数
     */
    @Override
    public int queryBrandLinkedCount(Long barndId) {
        return this.selectOne(
                "com.ningpai.goods.dao.GoodsBrandMapper.queryBrandLinkedCount",
                barndId);
    }
}
