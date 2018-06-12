package com.ningpai.goods.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ningpai.goods.bean.*;
import com.ningpai.goods.util.GoodsMarketingCodeUtil;
import com.ningpai.goods.vo.ThirdStoreInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ningpai.goods.dao.GoodsElasticMapper;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * 商品索引查询接口实现类
 * 
 * @author ggn
 *
 */
@Repository("GoodsElasticMapper")
public class GoodsElasticMapperImpl extends BasicSqlSupport implements
        GoodsElasticMapper {

    /**
     * 查询所有商品信息
     */
    @Override
    public List<EsGoodsInfo> selectGoodsElasticList(Long goodsId) {
        // 查询商品信息
        return this
                .selectList("com.qianmi.web.dao.EsGoodsInfoMapper.selectGoodsElasticList",goodsId);
    }

    /**
     * 查询所有商品信息(根据商品id)
     *
     * @param goodsId
     * @return List
     */
    @Override
    public List<EsGoodsInfo> selectGoodsElasticListById(Long goodsId) {
        // 查询商品信息
        return this
                .selectList("com.qianmi.web.dao.EsGoodsInfoMapper.selectGoodsElasticListById", goodsId);
    }

    /**
     * 查询总数
     * @return
     */
    @Override
    public Integer selectGoodsElasticListCount() {
        return this
                .selectOne("com.qianmi.web.dao.EsGoodsInfoMapper.selectGoodsElasticListCount");
    }

    /**
     * 分页查询
     * @param start
     * @param end
     * @return List
     */
    @Override
    public List<EsGoodsInfo> selectGoodsElasticListByPage(int start, int end) {
        // 查询商品信息
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("start",start);
        paramMap.put("end",end);
        return this
                .selectList("com.qianmi.web.dao.EsGoodsInfoMapper.selectGoodsElasticListByPage", paramMap);
    }

    /**
     * 查询分类
     * @param catId
     * @return
     */
    @Override
    public EsGoodsCategory selectGoodsCateList(Long catId) {
        return this
                .selectOne("com.qianmi.web.dao.EsGoodsCategoryMapper.selectGoodsCateListById", catId);
    }

    /**
     * 查询第三方分类
     * @param catId
     * @return EsThirdCate
     */
    @Override
    public EsThirdCate selectGoodsThirdCateList(Long catId) {

        return this
                .selectOne("com.qianmi.web.dao.EsThirdCateMapper.selectGoodsThirdCateListById", catId);
    }

    /**
     * 查询评价总数以及好评数量
     *
     * @param goodsInfoId
     * @return
     */
    @Override
    public ProductCommentUtilBean queryCommentCountAndScoreByProductId(Long goodsInfoId) {
        return this
                .selectOne("com.qianmi.web.dao.EsGoodsInfoMapper.queryCommentCountAndScoreByProductId", goodsInfoId);
    }

    /**
     * 查询商品的促销
     *
     * @param goodsInfoId 商品id
     * @return
     */
    @Override
    public List<GoodsMarketingCodeUtil> selectProductMarket(Long goodsInfoId) {
        return this
                .selectList("com.qianmi.web.dao.EsGoodsInfoMapper.selectProductMarket", goodsInfoId);
    }

    /**
     * 根据
     *
     * @param goodsInfoId
     * @param brandId
     * @param cateId
     * @return
     */
    @Override
    public List<EsMarketing> selectMarketingByGoodsInfoId(Long goodsInfoId, Long brandId, Long cateId) {
        List<EsMarketing> marketList = new ArrayList<EsMarketing>();
        // 查询促销范围
        List<EsMarketingScope> scopeList = queryMarketingScopes(goodsInfoId, brandId, cateId);

        //团购促销中间变量
        EsMarketing groupMarketing=null;
        Long group=0L;
        //折扣促销中间变量
        EsMarketing zhekouMarketing=null;
        Long zhekou=0L;
        if (!CollectionUtils.isEmpty(scopeList)) {
            for (EsMarketingScope scope : scopeList) {
                EsMarketing marketing = querySimpleMarketingById(scope.getMarketingId());

                if (null != marketing) {
                    //取修改时间最新的团购
                    if("10".equals(marketing.getCodexType())){
                        if(marketing.getModTime().getTime()>group){
                            group=marketing.getModTime().getTime();
                            groupMarketing=marketing;
                        }
                    }
                    //取修改时间最新的折扣
                    else if("15".equals(marketing.getCodexType())){
                        if(marketing.getModTime().getTime()>zhekou){
                            zhekou=marketing.getModTime().getTime();
                            zhekouMarketing=marketing;
                        }
                    }
                    else{
                        marketList.add(marketing);
                    }

                }
            }
        }
        if(zhekouMarketing!=null){
            marketList.add(zhekouMarketing);
        }
        if(groupMarketing!=null){
            marketList.add(groupMarketing);
        }

        return marketList;
    }

    @Override
    public ThirdStoreInfo selectThirdStoreInfo(Long storeId) {
        return this
                .selectOne("com.qianmi.web.dao.EsThirdStoreInfoMapper.selectByPrimaryKey", storeId);
    }

    /**
     * 查询促销范围
     *
     * @param goodsInfoId
     * @param brandId
     * @param cateId
     * @return
     */
    private List<EsMarketingScope> queryMarketingScopes(Long goodsInfoId, Long brandId, Long cateId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsInfoId", goodsInfoId);
        paramMap.put("brandIdP", brandId);
        paramMap.put("cateId", cateId);
        // 查询所在范围的促销
        return selectList("com.ningpai.search.dao.EsGoodsMarketingMapper.selectMarketScopeByMapMay", paramMap);

    }

    /**
     * 查询简要的促销信息
     *
     * @param marketingId
     * @return
     */
    public EsMarketing querySimpleMarketingById(Long marketingId) {
        return selectOne("com.ningpai.search.dao.EsGoodsMarketingMapper.marketingDetail", marketingId);
    }
}
