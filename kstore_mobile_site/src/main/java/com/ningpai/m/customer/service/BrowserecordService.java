/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.util.PageBean;

/**
 * 浏览记录接口
 * 
 * @author qiyuanyuan
 * @version 0.0.1
 */
public interface BrowserecordService {
    /**
     * 查询浏览记录
     * 
     * @param customerId
     *            会员编号
     * @param pb
     *         分页
     * @return PageBean
     */
    PageBean selectBrowserecord(Long customerId,PageBean pb);

    /**
     * 根据主键删除
     *
     * @param likeId
     *            浏览编号
     * @return 0失败 1成功
     */
    int deleteByPrimaryKey(Long likeId,Long customerId);
    /**
     * 根据货品编号删除
     *
     * @param goodInfoId
     *           货品编号
     * @return 0失败 1成功
     */
    int deleteByGoodsInfoId(Long goodInfoId,Long customerId);
    
    /**
     * 添加浏览记录
     * @param request
     * @param response
     * @param productId
     * @return
     */
    int addBrowerecord(HttpServletRequest request, HttpServletResponse response, Long productId);
    
    /**
     * 查询浏览记录数目
     * @param parmaMap 
     *          查询参数
     * @return Long
     */
    Long browereCount(Long customerId);
    
    /**
     * 从cook添加到
     * @param request
     * @return
     */
    int loadBrowerecord(HttpServletRequest request);
    
}
