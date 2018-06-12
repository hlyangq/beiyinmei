/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
/**
 * 
 */
package com.ningpai.mobile.service;

import com.ningpai.mobile.bean.MobPageCate;
import com.ningpai.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * SERVICE-页面分类
 * 
 * @author NINGPAI-Daiyitian
 * @since 2014年7月24日上午10:37:04
 */
public interface MobPageCateServcie {

    /**
     * 查询PC端展示的专题页
     * @param pb
     * @param param
     * @return
     */
    PageBean selectByPageBean(PageBean pb, Map<String,Object> param);

    /**
     * 根据主键查询
     * @param pageCateId
     * @return
     */
    MobPageCate getPageCate(Long pageCateId);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int delete(Long[] ids);

    /**
     * 添加
     * 
     * @param record
     * @return
     */
    int savePageCate(MobPageCate record);

    /**
     * 修改
     * 
     * @param record
     * @return
     */
    int updatePageCate(MobPageCate record);

    /**
     * 查询所有专题
     * 
     * @return
     */
    List<MobPageCate> selectAll(Map<String,Object> param);

}
