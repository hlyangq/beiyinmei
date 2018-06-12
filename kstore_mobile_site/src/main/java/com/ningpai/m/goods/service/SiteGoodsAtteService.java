/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.service;

import java.math.BigDecimal;

/**
 * 商品关注Service
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月12日 下午4:36:35
 * @version 1.0
 */
public interface SiteGoodsAtteService {
    /**
     * 保存商品关注信息
     * 
     * @param custId
     *            会员ID
     * @param productId
     *            货品ID
     */
    int saveGoodsAtte(Long custId, Long productId);

    /**
     * 新关注商品流程
     * 商品详情页收藏处使用
     *
     * @author houyichang 2015/10/26
     * @param custId
     * @param productId
     * @param districtId
     * @param goodsprice
     * @return
     */
    int newsaveGoodsAtte(Long custId, Long productId, Long districtId,
                         BigDecimal goodsprice);

    /**
     * 查询商品是否已经被关注
     * 
     * @param custId
     *            会员ID
     * @param productId
     *            货品ID
     * @return boolean 表示已经关注过 false 表示未被关注
     */
    boolean checkAtte(Long custId, Long productId);

    /**
     * 商品加入购物车
     * @param custId
     * @param productId
     * @param districtId
     * @param goodsprice
     */
    int addGoodsCollection(Long custId, Long productId, Long districtId,
                            BigDecimal goodsprice);
}
