/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.mapper;

import java.util.List;
import java.util.Map;

import com.ningpai.m.customer.bean.Browserecord;

/**
 * 浏览记录Mapper
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年4月12日 下午4:33:55
 * @version 0.0.1
 */
public interface BrowserecordMapper {

    /**
     * 根据主键删除
     * 
     * @param map
     *            浏览编号
     * @return 0失败 1成功
     */
    int deleteByPrimaryKey(Map<String,Object> map);

    /**
     * 插入数据
     * 
     * @param record
     *            要插入的数据的对象 {@link com.ningpai.site.customer.bean.Browserecord}
     * @return 0失败 1成功
     */
    int insert(Browserecord record);

    /**
     * 按条件插入数据
     * 
     * @param record
     *            要插入的数据的对象 {@link com.ningpai.site.customer.bean.Browserecord}
     * @return 0失败 1成功
     */
    int insertSelective(Browserecord record);

    /**
     * 按照主键编号查找
     * 
     * @param likeId
     *            浏览编号
     * @return Browserecord {@link com.ningpai.site.customer.bean.Browserecord}
     */
    Browserecord selectByPrimaryKey(Long likeId);

    /**
     * 按条件修改信息
     * 
     * @param record
     *            要修改的对象 {@link com.ningpai.site.customer.bean.Browserecord}
     * @return 0失败 1成功
     */
    int updateByPrimaryKeySelective(Browserecord record);

    /**
     * 根据主键编号修改信息
     * 
     * @param record
     *            要修改的对象 {@link com.ningpai.site.customer.bean.Browserecord}
     * @return 0失败 1成功
     */
    int updateByPrimaryKey(Browserecord record);

    /**
     * 查询浏览记录
     * 
     * @param parmaMap
     *            参数
     * @return List<Browserecord> {@link java.util.List}
     */
    List<Object> selectBrowserecord(Map<String,Object> paramaMap);
    
    /**
     * 查询浏览记录数目
     * @param parmaMap 
     *          查询参数
     * @return Long
     */
    Long selectBrowserecordCount(Map<String,Object> paramaMap);
    
    /**
     * 修改浏览记录
     * @param browserecord 
     *          浏览对象
     * @return int
     */
    int updataBrowereById(Browserecord browserecord);
    
    /**
     * 根据条件查询浏览记录
     * @param paramMap
     *          查询参数
     * @return 对象
     */
    Browserecord selectByBrowereId(Map<String, Object> paramMap);

}
