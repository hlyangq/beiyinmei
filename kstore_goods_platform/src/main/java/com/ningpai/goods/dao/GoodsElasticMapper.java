package com.ningpai.goods.dao;

import java.util.List;

import com.ningpai.goods.bean.*;
import com.ningpai.goods.util.GoodsMarketingCodeUtil;
import com.ningpai.goods.vo.ThirdStoreInfo;

/**
 * 商品索引Dao接口
 * 
 * @author ggn
 *
 */
public interface GoodsElasticMapper {

    /**
     * 查询所有商品信息
     * 
     * @return List
     */
    List<EsGoodsInfo> selectGoodsElasticList(Long goodsId);

    /**
     * 查询所有商品信息(根据商品id)
     *
     * @return List
     */
    List<EsGoodsInfo> selectGoodsElasticListById(Long goodsId);
    /**
     * 查询所有商品信息总数
     *
     * @return Integer
     */
    Integer selectGoodsElasticListCount();

    /**
     * 分页查询
     * @param start
     * @param end
     * @return List
     */
    List<EsGoodsInfo> selectGoodsElasticListByPage(int start,int end);

    /**
     * 查询分类
     * @param catId
     * @return EsGoodsCategory
     */
    EsGoodsCategory selectGoodsCateList(Long catId);

    /**
     * 查询第三方分类catId
     * @param catId
     * @return EsThirdCate
     */
    EsThirdCate selectGoodsThirdCateList(Long catId);

    /**
     * 查询评价总数以及好评数量
     * @param goodsInfoId
     * @return
     */
    ProductCommentUtilBean queryCommentCountAndScoreByProductId(Long goodsInfoId);

    /**
     * 查询商品的促销
     * @param goodsInfoId 商品id
     * @return
     */
    List<GoodsMarketingCodeUtil> selectProductMarket(Long goodsInfoId);

    /**
     * 根据
     * @param goodsInfoId
     * @param brandId
     * @param cateId
     * @return
     */
    List<EsMarketing> selectMarketingByGoodsInfoId(Long goodsInfoId, Long brandId, Long cateId);

    /**
     * 查询店铺信息
     */
    ThirdStoreInfo selectThirdStoreInfo(Long storeId);
}
