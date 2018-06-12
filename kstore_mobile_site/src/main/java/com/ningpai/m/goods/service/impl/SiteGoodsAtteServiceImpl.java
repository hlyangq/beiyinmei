/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ningpai.m.customer.bean.CustomerFollow;
import com.ningpai.m.customer.mapper.CustomerFollowMapper;
import org.springframework.stereotype.Service;

import com.ningpai.m.goods.bean.SiteGoodsAtte;
import com.ningpai.m.goods.dao.SiteGoodsAtteMapper;
import com.ningpai.m.goods.service.SiteGoodsAtteService;

/**
 * 商品关注Service实现
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月12日 下午4:38:32
 * @version 1.0
 */
@Service("SiteGoodsAtteService")
public class SiteGoodsAtteServiceImpl implements SiteGoodsAtteService {
    private SiteGoodsAtteMapper goodsAtteMapper;

    @Resource(name = "customerFollowMapperSite")
    private CustomerFollowMapper customerFollowMapper;

    /**
     * 保存商品关注信息
     *
     * @param custId
     *            会员ID
     * @param productId
     *            货品ID
     */
    public int saveGoodsAtte(Long custId, Long productId) {
        if (!this.checkAtte(custId, productId)) {
            // 创建关注对象并保存
            SiteGoodsAtte siteGoodsAtte = new SiteGoodsAtte();
            try {
                siteGoodsAtte.setCustId(custId);
                siteGoodsAtte.setProductId(productId);
                return this.goodsAtteMapper.saveGoodsAtte(siteGoodsAtte);
            } finally {
                siteGoodsAtte = null;
            }
        } else {
            return -1;
        }
    }

    /**
     * 新关注商品流程
     * 商品详情页收藏处使用
     *
     * @param custId
     * @param productId
     * @param districtId
     * @param goodsprice
     * @return
     * @author houyichang 2015/10/26
     */
    @Override
    public int newsaveGoodsAtte(Long custId, Long productId, Long districtId, BigDecimal goodsprice) {

        Map<String, Object> map = new HashMap<>();
        map.put("custId", custId);
        map.put("productId", productId);

        CustomerFollow customerFollow = this.customerFollowMapper.queryByCustIdAndProId(map);
        // 关注货品为空时添加
        if (null == customerFollow) {
            // 创建关注对象并保存
            SiteGoodsAtte siteGoodsAtte = new SiteGoodsAtte();
            try {
                siteGoodsAtte.setCustId(custId);// 用户ID
                siteGoodsAtte.setProductId(productId); // 商品ID
                siteGoodsAtte.setDistrictId(districtId); // 区域ID
                siteGoodsAtte.setFollowPrice(goodsprice); // 收藏的价格
                return this.goodsAtteMapper.saveGoodsAtte(siteGoodsAtte);
            } finally {
                siteGoodsAtte = null;
            }
        }

        //这是商品详情页关注商品信息

        // 删除关注的商品
        map.put("customerId", custId);
        map.put("followId", customerFollow.getFollowId());
        this.customerFollowMapper.deleteByPrimaryKey(map);

        return -1;
    }

    /**
     * 查询商品是否已经被关注
     *
     * @param custId
     *            会员ID
     * @param productId
     *            货品ID
     * @return boolean 表示已经关注过 false 表示未被关注
     */
    public boolean checkAtte(Long custId, Long productId) {
        Map<String, Long> map = new HashMap<String, Long>();
        try {
            map.put("custId", custId);
            map.put("productId", productId);
            return this.goodsAtteMapper.queryAtteHistByCustIdAndProId(map) > 0 ? true : false;
        } finally {
            map = null;
        }
    }

    /**
     * 商品加入购物车
     *
     * @param custId
     * @param productId
     * @param districtId
     * @param goodsprice
     */
    @Override
    public int addGoodsCollection(Long custId, Long productId, Long districtId, BigDecimal goodsprice) {

        Map<String, Object> map = new HashMap<>();
        map.put("custId", custId);
        map.put("productId", productId);


        // 同时把这个商品从购物车里面删除
        customerFollowMapper.deleteShoppingCart(map);

        CustomerFollow customerFollow = this.customerFollowMapper.queryByCustIdAndProId(map);
        // 关注货品为空时添加 否则 不做任何处理
        if (null == customerFollow) {
            // 创建关注对象并保存
            SiteGoodsAtte siteGoodsAtte = new SiteGoodsAtte();
            try {
                siteGoodsAtte.setCustId(custId);// 用户ID
                siteGoodsAtte.setProductId(productId); // 商品ID
                siteGoodsAtte.setDistrictId(districtId); // 区域ID
                siteGoodsAtte.setFollowPrice(goodsprice); // 收藏的价格
                return this.goodsAtteMapper.saveGoodsAtte(siteGoodsAtte);
            } finally {
                siteGoodsAtte = null;
            }
        }
        return 1;
    }

    public SiteGoodsAtteMapper getGoodsAtteMapper() {
        return goodsAtteMapper;
    }

    @Resource(name = "SiteGoodsAtteMapper")
    public void setGoodsAtteMapper(SiteGoodsAtteMapper goodsAtteMapper) {
        this.goodsAtteMapper = goodsAtteMapper;
    }

}
