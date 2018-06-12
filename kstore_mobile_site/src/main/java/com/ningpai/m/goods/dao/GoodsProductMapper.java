/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.dao;

import java.util.List;
import java.util.Map;

import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.goods.vo.ListFinalBuyVo;

/**
 * 货品信息Dao
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年1月20日 上午10:52:57
 * @version 1.0
 */
public interface GoodsProductMapper {
    /**
     * 根据商品ID查询所有的货品
     * 
     * @param goodsId
     * @return 查询到的货品列表
     */
    List<Object> queryProductByGoodsId(Long goodsId);

    /**
     * 根据货品id查询货品详细信息
     * @param goodsInfoId
     * @return
     */
    GoodsProduct queryByGoodsInfoDetail(Long goodsInfoId);

    /**
     * 根据套装id查询所有商品
     * @param groupId
     * @return
     */
    List<GoodsProductVo> queryDetailByGroupId(Long groupId);

    /**
     * 根据货品ID查询货品信息
     * 
     * @return 查询到的货品信息
     */
    GoodsProductVo queryPrductByProductId(Long productId);

    /**
     * 根据参数查询销量最高的货品
     * 
     * @param map
     *            参数 catIds 分类ID的集合 rowCount 查询的行数
     * @return 查询到的集合
     */
    List<GoodsProduct> queryTopSalesInfoByCatIds(Map<String, Object> map);

    /**
     * 根据分类ID查询最近一月的热销商品
     * 
     * @param map
     *            参数 catId
     * @return 查询到的集合{@link List}
     */
    List<GoodsProduct> queryHotSalesByCatId(Map<String, Object> map);

    /**
     * 根据参数查询最新上架的货品
     * 
     * @param map
     *            catIds 分类ID的集合 rowCount 查询的行数
     * @return 查询到的集合
     */
    List<GoodsProduct> queryTopNewInfoByCatIds(Map<String, Object> map);

    /**
     * 根据货品ID查询货品详细信息
     * 
     * @param productId
     *            货品ID {@link Long}
     * @return 查询到的货品详细信息 {@link com.ningpai.site.goods.GoodsProductVo}
     */
    GoodsProductVo queryDetailByProductId(Long productId);

    /**
     * 根据商品ID查询生成的第一个货品
     * 
     * @param goodsId
     *            商品ID {@link Long}
     * @return 查询到的货品信息 {@link GoodsProduct}
     */
    GoodsProduct queryFirstProductByGoodsId(Long goodsId);

    /**
     * 根据货品ID查询属于同一组合下的货品
     * 
     * @param productId
     *            货品ID {@link Long}
     * @return 查询到的货品集合 {@link GoodsProduct}
     */
    List<GoodsProduct> queryGroupProductByProductId(Long productId);

    /**
     * 根据货品ID的数组查询货品集合
     * 
     * @param map
     *            货品Id数组
     * @return 查询到的货品集合
     */
    List<GoodsProductVo> queryProductsByProductIds(Map<String, Object> map);

    /**
     * 保存商品浏览记录
     * 
     * @return 插入的行数
     */
    int saveGoodsBrow(Map<String, Object> map);

    /**
     * 保存商品咨询信息
     * 
     * @param map
     *            参数
     * @return 保存是否成功
     */
    int saveAskComment(Map<String, Object> map);

    /**
     * 根据分类ID查询最终购买的商品以及百分比
     * 
     * @param map
     *            参数
     * @return 查询到的辅助Bean的集合
     */
    List<ListFinalBuyVo> browCatFinalBuyAndPrecent(Map<String, Object> map);

    /**
     * 根据货品ID查询最近一个月销量最高的几件货品
     * 
     * @param map
     *            参数
     * @return 查询到的货品集合
     */
    List<GoodsProduct> queryTopSalesInfoByProductId(Map<String, Object> map);

    /**
     * 新增兑换积分
     * @param map
     * @return
     */
    int insertExchangeCusmomer(Map<String, Object> map);
    
    /**
     * 根据分类id查询货品
     * @return
     */
    List<GoodsProduct> queryGoodsInfoByCatIds(Map<String, Object> map);
    
    /**
     * 查询货品，按销量降序
     * @param map
     * @return
     */
    List<GoodsProduct> queryTopSalesInfos(Map<String, Object> map);
    
    /**
     * 根据分类ID查询10条不重复的货品id
     * @param map
     * @return
     */
    List<Long> queryGoodsInfoIdsByCatIds(Map<String, Object> map);
    
    /**
     * 根据货品ID查询货品
     * @param map
     * @return
     */
    List<GoodsProduct> queryGoodsInfoByGoodInfoIds(Map<String, Object> map);

    /**
     *  根据货品ID 查询这个货品是否是上架状态以及未删除状态
     * @param map
     * @return
     */
    int queryProductByGoodsInfoId(Map<String, Object> map);

    /**
     * 根据货品IDS查询货品集合
     * @param productIds 货品ids
     * @return 货品集合
     */
    List<GoodsProduct> queryByProductIds(List<String> productIds);
}
