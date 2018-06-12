package com.ningpai.goods.service;

import com.ningpai.goods.bean.EsGoodsInfo;
import com.ningpai.goods.util.CommonConditions;
import com.ningpai.goods.util.SearchPageBean;

import java.util.List;
import java.util.Map;

/**
 * 商品索引Service
 * 
 * @author ggn
 *
 */
public interface GoodsElasticSearchService {

    /**
     * 查询所有商品信息简历索引
     * 
     * @return int
     */
    int createGoodsIndexToEs();

    /**
     * 插入单个索引
     * 
     * @param goodsId
     *            商品ID
     * @return int
     */
    int insertOneGoodsIndexToEs(Long goodsId);

    /**
     * 修改 索 引
     *
     * @param goodsId
     * @return int
     */
    int updateOneGoodsIndexToEs(Long goodsId);

    /**
     * 修改 索 引
     *
     * @param goodsId
     * @return int
     */
    int updateThirdOneGoodsIndexToEs(Long goodsId);

    /**
     * 批量删除
     * 
     * @param list
     * @return int
     */
    int batchDeleteGoodsIndexToEs(List<Long> list);

    /**
     * 批量删除
     * 
     * @param goodsInfoId
     * @return int
     */
    int deleteGoodsIndexToEs(Long goodsInfoId);

    /**
     * 根据 关键词\品牌名称\分类\扩展参数\排序 进行商品查询
     * 
     * @param pageBean
     *            分页数据包装工具类
     * @param wareIds
     *            仓库ID列表
     * @param indices
     *            索引
     * @param types
     *            类型
     * @param keyWords
     *            输入的关键词
     * @param brands
     *            品牌名称
     * @param cats
     *            分类名称
     * @param params
     *            扩展参数
     * @param sort
     *            排序字段
     * @param priceMax
     *            价格过滤的上限
     * @param priceMin
     *            价格过滤的最小值
     * @param thirdId
     *            第三方ID
     * @param thirdCats
     *              第三方分类ID
     * @param showStock
     *              只显示有货
     * @param showMobile 手机端显示
     * @param isThird 是否为第三方商品 0:否,1:是
     * @param inMarketing 在营销中
     * @return 查询结果数据 {@link Map} {pageBean:{@link SearchPageBean},params:}
     */
    Map<String, Object> searchGoods(SearchPageBean<EsGoodsInfo> pageBean,
            Long[] wareIds, String[] indices, String[] types, String keyWords,
            String[] brands, String[] cats, String[] params, String sort,
            String priceMin, String priceMax, Long thirdId,String[] thirdCats,String showStock,String showMobile,String isThird, String inMarketing);

    /**
     * 根据 关键词\品牌名称\分类\扩展参数\排序 进行商品查询
     *
     * @param pageBean
     *            分页数据包装工具类
     * @param wareIds
     *            仓库ID列表
     * @param indices
     *            索引
     * @param types
     *            类型
     * @param keyWords
     *            输入的关键词
     * @param brands
     *            品牌名称
     * @param cats
     *            分类名称
     * @param params
     *            扩展参数
     * @param sort
     *            排序字段
     * @param priceMax
     *            价格过滤的上限
     * @param priceMin
     *            价格过滤的最小值
     * @param thirdId
     *            第三方ID
     * @param thirdCats
     *              第三方分类ID
     * @param showStock
     *              只显示有货
     * @param showMobile 手机端显示
     * @param isThird 是否为第三方商品 0:否,1:是
     * @return 查询结果数据 {@link Map} {pageBean:{@link SearchPageBean},params:}
     */
    Map<String, Object> searchGoods(SearchPageBean<EsGoodsInfo> pageBean,
                                    Long[] wareIds, String[] indices, String[] types, String keyWords,
                                    String[] brands, String[] cats, String[] params, String sort,
                                    String priceMin, String priceMax, Long thirdId,String[] thirdCats,String showStock,String showMobile,String isThird);

    /**
     * 根据 关键词\品牌名称\分类\扩展参数\排序 进行商品查询
     * @param pageBean 分页工具类
     * @param indices 索引
     * @param types 类型
     * @param conditions 查询条件包装
     * @return 查询结果 {pageBean:{@link SearchPageBean},params:}
     */
    Map<String,Object> searchGoods(SearchPageBean<EsGoodsInfo> pageBean,String[] indices,String[] types,CommonConditions conditions);

    /**
     * 获取拼写建议
     * @return 建议的字符串列表
     */
    List<String> getCompletionSuggest(String keyWords);


    int deleteGoodsByThirdId(String[] storeInfoIds);
}
