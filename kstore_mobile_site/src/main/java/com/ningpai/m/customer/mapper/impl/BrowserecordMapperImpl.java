/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.mapper.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ningpai.m.customer.bean.Browserecord;
import com.ningpai.m.customer.mapper.BrowserecordMapper;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * @see com.ningpai.site.customer.mapper.BrowserecordMapper
 * @author qiyuanyuan
 * @version 0.0.1
 */
@Repository("browserecordMapper")
public class BrowserecordMapperImpl extends BasicSqlSupport implements
        BrowserecordMapper {
    /**
     * 根据主键删除
     * @param map
     *            浏览编号
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Map<String, Object> map) {
        return this
                .update("com.ningpai.m.customer.dao.BrowserecordMapper.deleteByPrimaryKey",
                        map);
    }

    /**
     * 插入数据
     * @param record
     *            要插入的数据的对象 {@link com.ningpai.site.customer.bean.Browserecord}
     * @return
     */
    @Override
    public int insert(Browserecord record) {
        return 0;
    }

    /**
     * 按条件插入数据
     * @param record
     *            要插入的数据的对象 {@link com.ningpai.site.customer.bean.Browserecord}
     * @return
     */
    @Override
    public int insertSelective(Browserecord browserecord) {
        return this.insert("com.ningpai.m.customer.dao.BrowserecordMapper.insertSelective", browserecord);
    }

    /**
     * 按照主键编号查找
     * @param likeId
     *            浏览编号
     * @return
     */
    @Override
    public Browserecord selectByPrimaryKey(Long likeId) {
        return null;
    }

    /**
     * 按条件修改信息
     * @param record
     *            要修改的对象 {@link com.ningpai.site.customer.bean.Browserecord}
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Browserecord record) {
        return 0;
    }

    /**
     * 根据主键编号修改信息
     * @param record
     *            要修改的对象 {@link com.ningpai.site.customer.bean.Browserecord}
     * @return
     */
    @Override
    public int updateByPrimaryKey(Browserecord record) {
        return 0;
    }

    /**
     * 查询浏览记录
     * @param customerId
     *            会员编号
     * @return
     */
    @Override
    public List<Object> selectBrowserecord(Map<String, Object> paramMap) {
        return this
                .selectList(
                        "com.ningpai.m.customer.dao.BrowserecordMapper.selectBrowserecord",
                        paramMap);
    }

    /**
     * 查询浏览记录数目
     * @param parmaMap 
     *          查询参数
     * @return Long
     */
    @Override
    public Long selectBrowserecordCount(Map<String, Object> paramaMap) {
        return this.selectOne("com.ningpai.m.customer.dao.BrowserecordMapper.selectBrowserecordCount",paramaMap);
    }

    /**
     * 修改浏览记录
     * @param browserecord 
     *          浏览对象
     * @return int
     */
    @Override
    public int updataBrowereById(Browserecord browserecord) {
        return this.update("com.ningpai.m.customer.dao.BrowserecordMapper.updateBrowereById", browserecord);
    }

    /**
     * 根据条件查询浏览记录
     * @param paramMap
     *          查询参数
     * @return 对象
     */
    @Override
    public Browserecord selectByBrowereId(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.m.customer.dao.BrowserecordMapper.selectByBrowereId",paramMap);
    }

}
