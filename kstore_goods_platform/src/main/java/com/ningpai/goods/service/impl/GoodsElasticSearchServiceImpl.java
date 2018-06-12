package com.ningpai.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ningpai.goods.bean.EsGoodsCategory;
import com.ningpai.goods.bean.EsGoodsInfo;
import com.ningpai.goods.bean.EsMarketing;
import com.ningpai.goods.bean.EsThirdCate;
import com.ningpai.goods.dao.GoodsElasticMapper;
import com.ningpai.goods.service.GoodsElasticSearchService;
import com.ningpai.goods.util.CommonConditions;
import com.ningpai.goods.util.GoodsIndexConstant;
import com.ningpai.goods.util.SearchPageBean;
import com.ningpai.goods.vo.BrandVo;
import com.ningpai.goods.vo.ExpandParamVo;
import com.ningpai.goods.vo.ParamValueVo;
import com.ningpai.goods.vo.ThirdStoreInfo;
import com.ningpai.searchplatform.bean.ESSource;
import com.ningpai.searchplatform.enumeration.ESBoolQueryType;
import com.ningpai.searchplatform.enumeration.ESQueryWay;
import com.ningpai.searchplatform.query.AggregationDefineBean;
import com.ningpai.searchplatform.query.IQueryItemUnit;
import com.ningpai.searchplatform.query.QueryItem;
import com.ningpai.searchplatform.request.IndexCreateRequest;
import com.ningpai.searchplatform.request.IndexSearchRequest;
import com.ningpai.searchplatform.response.IndexSearchResponse;
import com.ningpai.searchplatform.service.IndexService;
import com.ningpai.searchplatform.service.SearchService;
import com.ningpai.searchplatform.util.IkAnalzyerUtil;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 商品索引接口实现类
 * <p/>
 * <p/>
 * ps:改前请看业务逻辑
 *
 * @author liangck
 */
@Service("GoodsElasticSearchService")
@Scope()
public class GoodsElasticSearchServiceImpl implements GoodsElasticSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsElasticSearchServiceImpl.class);

    private static final String BRAND = "brand";
    private static final String PARAMLIST = "paramList";
    private static final String CHECKWARE = "checkWare";
    private static final String GOODSINFONAME = "goodsInfoName";

    private static final String GOODSINFOCREATETIME = "goodsInfoCreateTime";

    // pring注入
    @Resource(name = "GoodsElasticMapper")
    private GoodsElasticMapper goodsElasticMapper;

    // 索引生成service
    @Resource
    private IndexService indexService;

    // 搜索service
    @Autowired
    private SearchService searchService;

    /**
     * 价格排序脚本
     **/
    private static final String sort_script = "def price=_source.goodsInfoPreferPrice;if(!checkWare){return price;};def wares=_source.wareList;if(wares&&wares.size()>0){for (ware in wares){if(ware.wareId==checkWare){return ware.warePrice;}}};return price;";

    /**
     * 过滤掉无库存
     **/
    private static final String filter_script = "if (!checkWare){return _source.goodsInfoStock>0;};def wares = _source.wareList;for (ware in wares){if (ware.wareId==checkWare){return ware.wareStock>0;}};return _source.goodsInfoStock>0;";

    /**
     * 分类格式
     *
     * @param catId
     * @return List
     */
    public List<EsGoodsCategory> cateUtil(Long catId) {
        if (catId != null) {
            List<EsGoodsCategory> list = new ArrayList<EsGoodsCategory>();
            // 查询当前分类ID 三级
            EsGoodsCategory es = goodsElasticMapper.selectGoodsCateList(catId);

            if (es != null && es.getCatParentId() != null && es.getCatParentId() != 0) {
                // 查询上级分类ID 二级
                EsGoodsCategory es1 = goodsElasticMapper.selectGoodsCateList(es.getCatParentId());
                if (es1 != null && es1.getCatParentId() != null && es1.getCatParentId() != 0) {
                    // 查询上级分类ID 一级
                    EsGoodsCategory es2 = goodsElasticMapper.selectGoodsCateList(es1.getCatParentId());
                    if (es2 != null && es1.getCatParentId() != null) {
                        list.add(es2);
                    }
                }
                list.add(es1);
            }
            list.add(es);
            return list;
        } else {
            return new ArrayList<EsGoodsCategory>();
        }

    }

    /**
     * 第三方分类格式
     *
     * @param catId
     * @return
     */
    public List<EsThirdCate> thirdCateUtil(Long catId) {
        if (catId != null) {
            List<EsThirdCate> list = new ArrayList<EsThirdCate>();
            // 查询当前分类ID 三级
            EsThirdCate es = goodsElasticMapper.selectGoodsThirdCateList(catId);

            if (es != null && es.getCatParentId() != null && es.getCatParentId() != 0) {
                // 查询上级分类ID 二级
                EsThirdCate es1 = goodsElasticMapper.selectGoodsThirdCateList(es.getCatParentId());
                if (es1 != null && es1.getCatParentId() != null && es1.getCatParentId() != 0) {
                    // 查询上级分类ID 一级
                    EsThirdCate es2 = goodsElasticMapper.selectGoodsThirdCateList(es1.getCatParentId());
                    if (es2 != null && es1.getCatParentId() != null) {
                        list.add(es2);
                    }
                }
                list.add(es1);
            }
            list.add(es);
            return list;
        } else {
            return new ArrayList<EsThirdCate>();
        }

    }

    /**
     * 查询所有商品并且生产索引
     */
    @Override
    public int createGoodsIndexToEs() {
        LOGGER.info("elastic search 生成索引,查询商品数据...");
        // 构造索引请求
        IndexCreateRequest request = new IndexCreateRequest();
        // 申明文档List
        List<ESSource> sourceList = new ArrayList<ESSource>();
        // 查询所有商品总数
        Integer listCount = goodsElasticMapper.selectGoodsElasticListCount();
        int temp = 5;
        for (int i = 0; i < listCount; i = i + temp) {
            int start = i;
            // 查询商品信息
            List<EsGoodsInfo> egoodslist = goodsElasticMapper.selectGoodsElasticListByPage(start, temp);
            // 判断查询信息是否为null
            if (egoodslist != null && !egoodslist.isEmpty()) {
                // 循环数据
                for (EsGoodsInfo goodsInfo : egoodslist) {
                    // 请求资源 source
                    ESSource source = new ESSource();
                    // 商品分类
                    goodsInfo.setCateList(cateUtil(goodsInfo.getCatId()));
                    // 商家分类
                    goodsInfo.setThirdCateList(thirdCateUtil(goodsInfo.getThirdCateId()));
                    // 设置是否在营销中 -liangck 2015年4月27日 19:52
                    goodsInfo.setInMarketing(isInMarketing(goodsInfo));
                    //设置拼写建议
                    goodsInfo.initSuggest();
                    try {
                        //到期时间
                        goodsInfo.setExpiryTime(isExpiryTime(goodsInfo));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // 请求资源中需要生成文档的数据
                    source.setDocument(goodsInfo);
                    // 设置文档的ID
                    source.setId(goodsInfo.getGoodsInfoId().toString());
                    sourceList.add(source);
                }
            }
        }
        // 设置请求的数据
        request.setSources(sourceList);
        // 请求中需要生成索引的文档的 Class
        request.setEsMapType(EsGoodsInfo.class);
        // 生成的索引库名
        request.setIndex(GoodsIndexConstant.PRODUCT_INDEX_NAME());

        // 生成索引
        try {
            LOGGER.info("elastic search 调用接口创建索引...");
            indexService.createIndex(request);
            LOGGER.info("elastic search 创建索引成功...");
            return 1;
        } catch (Exception e) {
            LOGGER.error("创建索引失败...", e);
        }

        return 0;
    }

    /**
     * 该货品是否在营销中
     * @param goodsInfo
     * @return
     */
    private String isInMarketing(EsGoodsInfo goodsInfo) {
        List<EsMarketing> marketings = goodsElasticMapper.selectMarketingByGoodsInfoId(goodsInfo.getGoodsInfoId(), goodsInfo.getBrandId(), goodsInfo.getCatId());
        return CollectionUtils.isEmpty(marketings) ? "0" : "1";
    }

    /**
     * 该货品是否在营销中
     * @param goodsInfo
     * @return
     */
    private Date isExpiryTime(EsGoodsInfo goodsInfo) throws ParseException {
        Long storeid = goodsInfo.getThirdId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2099-12-31 00:00:00");
        if(storeid != null){
            if(storeid.equals(0L)){
                return date;
            }else{
                ThirdStoreInfo stireInFo = goodsElasticMapper.selectThirdStoreInfo(storeid);
                if(stireInFo.getExpiryTime()!=null){
                    return stireInFo.getExpiryTime();
                }else{
                    return date;
                }
            }
        }else{
            return date;
        }
    }

    /**
     * 插入 单 个索引
     *
     * @see com.ningpai.goods.service.GoodsElasticSearchService#insertOneGoodsIndexToEs(java.lang.Long)
     */
    @Override
    public int insertOneGoodsIndexToEs(Long goodsId) {
        if (goodsId != null) {
            // 查询商品信息
            LOGGER.info("elastic search 查询单条数据,创建索引文档...");
            List<EsGoodsInfo> egoodslist = goodsElasticMapper.selectGoodsElasticList(goodsId);
            // 执行插入索引
            if (egoodslist != null && !egoodslist.isEmpty()) {
                // 循环数据
                for (EsGoodsInfo goodsInfo : egoodslist) {
                    // 商品分类
                    goodsInfo.setCateList(cateUtil(goodsInfo.getCatId()));
                    // 商家分类
                    goodsInfo.setThirdCateList(thirdCateUtil(goodsInfo.getThirdCateId()));
                    // 设置是否在营销中 -liangck 2015年4月27日 19:52
                    goodsInfo.setInMarketing(isInMarketing(goodsInfo));
                    //到期时间
                    try {
                        goodsInfo.setExpiryTime(isExpiryTime(goodsInfo));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //设置拼写建议
                    goodsInfo.initSuggest();
                }
                LOGGER.info("elastic search 调用接口创建单条索引文档...");
                indexService.batchAddDoc(GoodsIndexConstant.PRODUCT_INDEX_NAME(), EsGoodsInfo.class.getSimpleName(), egoodslist);
                LOGGER.info("elastic search 创建单条文档成功...");
            }
        }
        return 0;
    }

    /**
     * 修改索引信息
     *
     * @see com.ningpai.goods.service.GoodsElasticSearchService#updateOneGoodsIndexToEs(java.lang.Long)
     */
    @Override
    public int updateOneGoodsIndexToEs(Long goodsId) {
        if (goodsId != null) {
            // 查询商品信息
            LOGGER.info("elastic sesarch 查询单条数据,修改索引文档...");
            List<EsGoodsInfo> egoodslist = goodsElasticMapper.selectGoodsElasticList(goodsId);
            if (egoodslist != null && !egoodslist.isEmpty()) {
                // 循环数据
                for (EsGoodsInfo goodsInfo : egoodslist) {
                    // 商品分类
                    goodsInfo.setCateList(cateUtil(goodsInfo.getCatId()));
                    // 商家分类
                    goodsInfo.setThirdCateList(thirdCateUtil(goodsInfo.getThirdCateId()));
                    // 设置是否在营销中 -liangck 2015年4月27日 19:52
                    goodsInfo.setInMarketing(isInMarketing(goodsInfo));
                    //到期时间
                    try {
                        goodsInfo.setExpiryTime(isExpiryTime(goodsInfo));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //设置拼写建议
                    goodsInfo.initSuggest();
                }
                // 执行修改索引
                LOGGER.info("elastic search 调用接口,修改单条文档...");
                indexService.batchUpdateDoc(GoodsIndexConstant.PRODUCT_INDEX_NAME(), EsGoodsInfo.class.getSimpleName(), egoodslist);
                LOGGER.info("elastic search 修改单条文档成功...");
            }

        }
        return 0;
    }

    /**
     * 修改 索 引(第三方)
     *
     * @param goodsId
     * @return int
     */
    @Override
    public int updateThirdOneGoodsIndexToEs(Long goodsId) {
        if (goodsId != null) {
            // 查询商品信息
            LOGGER.info("elastic sesarch 查询单条数据,修改索引文档...");
            List<EsGoodsInfo> egoodslist = goodsElasticMapper.selectGoodsElasticListById(goodsId);
            if (egoodslist != null && !egoodslist.isEmpty()) {
                // 循环数据
                for (EsGoodsInfo goodsInfo : egoodslist) {
                    // 商品分类
                    goodsInfo.setCateList(cateUtil(goodsInfo.getCatId()));
                    // 商家分类
                    goodsInfo.setThirdCateList(thirdCateUtil(goodsInfo.getThirdCateId()));
                    // 设置是否在营销中 -liangck 2015年4月27日 19:52
                    goodsInfo.setInMarketing(isInMarketing(goodsInfo));
                    //到期时间
                    try {
                        goodsInfo.setExpiryTime(isExpiryTime(goodsInfo));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //设置拼写建议
                    goodsInfo.initSuggest();
                }
                // 执行修改索引
                LOGGER.info("elastic search 调用接口,修改单条文档...");
                indexService.batchUpdateDoc(GoodsIndexConstant.PRODUCT_INDEX_NAME(), EsGoodsInfo.class.getSimpleName(), egoodslist);
                LOGGER.info("elastic search 修改单条文档成功...");
            }

        }
        return 0;
    }

    /**
     * 根据 关键词\品牌名称\扩展参数\排序 进行商品查询
     *
     * @param pageBean   分页工具
     * @param wareIds    仓库ID列表
     * @param indices    索引
     * @param types      类型
     * @param keyWords   输入的关键词
     * @param brands     品牌名称
     * @param cats       分类
     * @param params     扩展参数
     * @param sort       排序字段
     * @param priceMax   价格过滤的最大值
     * @param priceMin   价格过滤的下限
     * @param thirdId    第三方ID
     * @param thirdCats  第三方分类ID
     * @param showStock  是否只显示有货
     * @param showMobile 是否手机端显示
     * @param isThird    是否为第三方商品 1:是,0:否
     * @return 查询结果数据
     */
    @Override
    public Map<String, Object> searchGoods(SearchPageBean<EsGoodsInfo> pageBean, Long[] wareIds, String[] indices, String[] types, String keyWords, String[] brands, String[] cats,
                                           String[] params, String sort, String priceMin, String priceMax, Long thirdId, String[] thirdCats, String showStock, String showMobile, String isThird, String inMarketing) {
        // 构造请求数据
        IndexSearchRequest searchRequest = new IndexSearchRequest(indices, types);
        // 添加查询条件

        // 关键词查询 must match
        if (keyWords != null && !"".equals(keyWords)) {
            searchRequest.addQueryItems(buildKeyWordsQueryItems(keyWords));
        }

        // 搜索上架商品
        searchRequest.addQueryItem("goodsInfoAdded", "1", ESBoolQueryType.MUST, ESQueryWay.TERM);

        // 是否列表显示
        searchRequest.addQueryItem("showList", "1", ESBoolQueryType.MUST, ESQueryWay.TERM);

        if (showMobile != null) {
            // 手机端显示
            searchRequest.addQueryItem("showMobile", "1", ESBoolQueryType.MUST, ESQueryWay.TERM);
        }

        if (isThird != null && !"".equals(isThird)) {
            // 是否为第三方: 0:boss, 1:第三方
            searchRequest.addQueryItem("isThird", isThird, ESBoolQueryType.MUST, ESQueryWay.TERM);
        }

        // 第三方ID
        if (thirdId != null) {
            searchRequest.addQueryItem("thirdId", String.valueOf(thirdId), ESBoolQueryType.MUST, ESQueryWay.TERM);
        }

        // 是否在营销中
        if (inMarketing != null && !"".equals(inMarketing)) {
            searchRequest.addQueryItem("inMarketing", inMarketing, ESBoolQueryType.MUST, ESQueryWay.TERM);
        }

        // 分类查询
        if (cats != null) {
            for (String cat : cats) {
                searchRequest.addSingleNestedTermField("cateList", "cateList.catId", cat, ESBoolQueryType.MUST, ESQueryWay.TERM);
            }
        }

        // 第三方分类查询
        if (thirdCats != null) {
            for (String thirdCat : thirdCats) {
                searchRequest.addSingleNestedTermField("thirdCateList", "thirdCateList.catId", thirdCat, ESBoolQueryType.MUST, ESQueryWay.TERM);
            }
        }

        // 品牌查询
        if (brands != null) {
            for (String brand : brands) {
                searchRequest.addSingleNestedTermField(BRAND, "brandName", brand, ESBoolQueryType.MUST, ESQueryWay.TERM);
            }
        }
        // 扩展参数
        if (params != null) {
            for (String param : params) {
                String[] paramArr = param.split(":");
                if (paramArr != null && paramArr.length >= 1 && paramArr[0] != null) {
                    // nested查询条件列表
                    List<IQueryItemUnit> queryItemUnitList = new ArrayList<>();
                    // searchRequest.addSingleNestedTermField(PARAMLIST,
                    // "paramList.expandparamName", paramArr[0],
                    // ESBoolQueryType.must, ESQueryWay.term);
                    // 扩展属性名称
                    queryItemUnitList.add(new QueryItem(ESBoolQueryType.MUST, ESQueryWay.TERM, "expandparamName", paramArr[0]));
                    if (paramArr.length >= 2 && paramArr[1] != null && !"".equals(paramArr[1])) {
                        // searchRequest.addSingleNestedTermField(PARAMLIST,
                        // "paramList.expandparamValueName", paramArr[1],
                        // ESBoolQueryType.must, ESQueryWay.term);
                        queryItemUnitList.add(new QueryItem(ESBoolQueryType.MUST, ESQueryWay.TERM, "expandparamValueName", paramArr[1]));
                    }
                    // 放入nested查询条件
                    searchRequest.addNestedTermField(PARAMLIST, queryItemUnitList);
                }
            }
        }

        // 根据仓库筛选
        /*
         * if (wareIds != null) { for (Long wareId : wareIds) {
         * searchRequest.addSingleNestedTermField("wareList", "wareId",
         * String.valueOf(wareId), ESBoolQueryType.MUST, ESQueryWay.TERM); } }
         */

        // 只显示有货
        if (showStock != null && "0".equals(showStock)) {
            searchRequest.addFilter(FilterBuilders.scriptFilter(filter_script).addParam(CHECKWARE, wareIds == null ? null : wareIds[0]));
        }

        // 价格过滤
        if ((priceMax != null && !"".equals(priceMax)) && (priceMin != null && !"".equals(priceMin))) {
            RangeFilterBuilder rangeFilterBuilder = FilterBuilders.rangeFilter("goodsInfoPreferPrice");
            if (priceMin != null) {
                rangeFilterBuilder.from(priceMin);
            }
            if (priceMax != null) {
                BigDecimal price = new BigDecimal(priceMax);
                price = price.add(new BigDecimal("1"));
                priceMax = price.toString();
                rangeFilterBuilder.to(priceMax);
            }
            rangeFilterBuilder.includeLower(true).includeUpper(false);
            searchRequest.addFilter(rangeFilterBuilder);
        }

        //店铺时间过滤
        searchRequest.addFilter(FilterBuilders.rangeFilter("expiryTime").from(new Date()));

        // 排序
        if (sort != null && !"".equals(sort)) {
            switch (sort) {
                case "11D":// 价格升序
                    // searchRequest.addSort("goodsInfoPreferPrice", SortOrder.ASC);
                    searchRequest.addSort(new ScriptSortBuilder(sort_script, "number").param(CHECKWARE, wareIds == null ? null : wareIds[0]).order(SortOrder.ASC));
                    break;
                case "1D":// 价格降序
                    // searchRequest.addSort("goodsInfoPreferPrice",
                    // SortOrder.DESC);
                    searchRequest.addSort(new ScriptSortBuilder(sort_script, "number").param(CHECKWARE, wareIds == null ? null : wareIds[0]).order(SortOrder.DESC));
                    break;
                case "2D":// 销量降序
                    searchRequest.addSort("saleCount", SortOrder.DESC);
                    break;
                case "22D":// 销量升序
                    searchRequest.addSort("saleCount", SortOrder.ASC);
                    break;
                case "33D":// 创建时间升序
                    searchRequest.addSort(GOODSINFOCREATETIME, SortOrder.ASC);
                    break;
                case "3D":// 创建时间降序
                    searchRequest.addSort(GOODSINFOCREATETIME, SortOrder.DESC);
                    break;
                case "44D":// 收藏生序
                    searchRequest.addSort("collectionCount", SortOrder.ASC);
                    break;
                case "4D":// 收藏降序
                    searchRequest.addSort("collectionCount", SortOrder.DESC);
                    break;
                default:
                    // throw new IllegalStateException();
                    break;
            }
        }
        //添加按照时间排序
//        searchRequest.addSort(GOODSINFOCREATETIME, SortOrder.DESC);

        // 添加聚合信息 品牌/分类/扩展属性
        searchRequest.addNestedAgg(BRAND, new AggregationDefineBean("brandName"));
        // searchRequest.addNestedAgg("cateList", new
        // AggregationDefineBean("catName"));
        AggregationDefineBean paramNameAgg = new AggregationDefineBean("expandparamName");
        paramNameAgg.setSubAgg(new AggregationDefineBean("expandparamValueName"));
        searchRequest.addNestedAgg(PARAMLIST, paramNameAgg);

        // 设置分页条件
        searchRequest.setPageNum(pageBean.getPageNo());
        searchRequest.setPageSize(pageBean.getPageSize());
        // 设置高亮
        searchRequest.setHighLightField(GOODSINFONAME);
        searchRequest.setHighLightPreTag("<em style='color:red'>");
        searchRequest.setHighLightSufTag("</em>");

        // 执行查询,获取查询结果
        IndexSearchResponse response = null;
        try {
            response = searchService.search(searchRequest);
        } catch (Exception e) {
            LOGGER.error("调用查询service出错咯...", e);
            Map<String, Object> resultmap = new HashMap<>();
            resultmap.put("pb", pageBean);
            return resultmap;
        }
        LOGGER.info("response result:" + response.getResultDataJsonString());
        // 如果不需要设置高亮,可直接采用下面这句
        // List<EsGoodsInfo> goodsInfoList =
        // response.getResultDataBeans(EsGoodsInfo.class);
        // 获取设置了商品名称高亮后的商品列表数据
        List<EsGoodsInfo> goodsInfoList = processOriginHits(response.getOriginHits());
        LOGGER.info("convert to bean success .. the data list size is:" + goodsInfoList.size());
        // 放入分页数据
        pageBean.setData(goodsInfoList);
        pageBean.setRows(response.getTotalCount());
        // 结果数据
        Map<String, Object> resultMap = new HashMap<>();
        // 分页数据
        resultMap.put("pb", pageBean);
        // 放入聚合数据
        processAggResult(resultMap, response.getResultAgg(), brands, params);
        return resultMap;
    }

    /**
     * 根据查询关键词构建查询单元 (切分成单字)
     *
     * @param keyWords 关键词
     * @return
     */
    private List<QueryItem> buildKeyWordsQueryItems(String keyWords) {
        List<String> analyzedWords = IkAnalzyerUtil.segmentPhraseByStandardAnalyzer(keyWords);
        List<QueryItem> queryItems = new ArrayList<>();
        for (String word : analyzedWords) {
            queryItems.add(new QueryItem(ESBoolQueryType.MUST, ESQueryWay.MATCH, GOODSINFONAME, word));
        }
        return queryItems;
    }

    /**
     * 从查询的源结果数据中处理数据得到查询商品列表,主要添加了设置商品名称高亮的操作.
     *
     * @param hits 查询的源结果
     * @return 处理后的商品列表
     */
    private List<EsGoodsInfo> processOriginHits(SearchHits hits) {
        List<EsGoodsInfo> goodsInfoList = new ArrayList<>();

        if (hits == null) {
            return goodsInfoList;
        }
        for (SearchHit hit : hits) {
            // 得到单个商品数据
            String goodsJson = hit.getSourceAsString().replace("[\"{", "[{").replace("}\"]", "}]").replace("\\", "").replace("}\"", "}").replace("\"{", "{");
            EsGoodsInfo esGoodsInfo = JSON.parseObject(goodsJson, EsGoodsInfo.class);
            // 获取高亮字段
            Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
            if (highlightFieldMap != null && highlightFieldMap.containsKey(GOODSINFONAME)) {
                HighlightField nameField = highlightFieldMap.get(GOODSINFONAME);
                Text[] nameTexts = nameField.fragments();
                // 高亮名称
                StringBuffer name = new StringBuffer();
                for (Text text : nameTexts) {
                    name.append(text);
                }
                // 设置高亮商品名称
                if (!"".equals(name.toString())) {
                    esGoodsInfo.setHighLightName(name.toString());
                }
            }
            //商品评价
            esGoodsInfo.setCommentUtilBean(goodsElasticMapper.queryCommentCountAndScoreByProductId(esGoodsInfo.getGoodsInfoId()));
            esGoodsInfo.setCodeUtils(goodsElasticMapper.selectProductMarket(esGoodsInfo.getGoodsInfoId()));

            goodsInfoList.add(esGoodsInfo);
        }
        return goodsInfoList;
    }

    /**
     * 处理聚合数据,并且过滤掉已选的参数
     *
     * @param resultMap
     * @param aggResult
     * @param brandArr  已选的品牌
     * @param paramArr  已选的扩展参数
     */
    private void processAggResult(Map<String, Object> resultMap, Object aggResult, String[] brandArr, String[] paramArr) {

        if (null == aggResult) {
            return;
        }

        // 将聚合结果转化为可操作的json对象
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(aggResult));
        // 处理扩展参数
        if (jsonObject.containsKey(PARAMLIST)) {
            List<ExpandParamVo> paramVoList = new ArrayList<>();
            Object params = jsonObject.get(PARAMLIST);
            JSONArray paramArray = null;
            if (params instanceof JSONArray) {
                // 转化为jsonArray
                paramArray = (JSONArray) params;
            } else {
                //只有一个param,构造array
                paramArray = new JSONArray(Arrays.asList((Object) params));
            }
            for (Iterator<?> ite = paramArray.iterator(); ite.hasNext(); ) {
                Object paramObj = ite.next();
                if (paramObj instanceof JSONObject) {
                    JSONObject paramJson = (JSONObject) paramObj;
                    for (Map.Entry<String, Object> entry : paramJson.entrySet()) {
                        // 判断当前的扩展参数是否与已选的相同
                        boolean selected = false;
                        if (paramArr != null && paramArr.length > 0) {
                            for (String paramSelected : paramArr) {
                                if (paramSelected.split(":")[0].equals(entry.getKey())) {
                                    selected = true;
                                    break;
                                }
                            }
                        }
                        // 未选中,则放入聚合结果
                        if (!selected) {
                            ExpandParamVo paramVo = new ExpandParamVo(entry.getKey());
                            if (entry.getValue() instanceof List) {
                                List<String> paramValues = (List<String>) entry.getValue();
                                for (String paramValue : paramValues) {
                                    paramVo.addParamValue(new ParamValueVo(paramValue));
                                }
                            } else {
                                paramVo.addParamValue(new ParamValueVo(entry.getValue().toString()));
                            }
                            //根据double值对扩展参数值数组进行排序
                            sortParamValWithDoubleVal(paramVo);
                            paramVoList.add(paramVo);
                        }
                    }
                }
            }
            resultMap.put("params", paramVoList);
        }
        // 处理品牌
        if (jsonObject.containsKey(BRAND)) {
            List<BrandVo> brandVoList = new ArrayList<>();
            // 获取品牌信息
            Object brandObj = jsonObject.get(BRAND);
            // 已选择的品牌转换为列表
            List<String> brandList;
            if (brandArr == null || brandArr.length == 0) {
                brandList = new ArrayList<>();
            } else {
                brandList = Arrays.asList(brandArr);
            }

            if (brandObj instanceof JSONArray) {
                JSONArray brands = (JSONArray) brandObj;
                for (Object branObj : brands.toArray()) {
                    if (!brandList.contains(branObj)) {
                        brandVoList.add(new BrandVo(String.valueOf(branObj)));
                    }
                }
            } else if (brandObj instanceof JSONObject) {
                JSONObject brand = (JSONObject) brandObj;
                if (!brand.isEmpty() && !brandList.contains(brandObj.toString())) {
                    brandVoList.add(new BrandVo(brandObj.toString()));
                }
            } else {
                if (!brandList.contains(brandObj.toString())) {
                    brandVoList.add(new BrandVo(brandObj.toString()));
                }
            }

            resultMap.put("brands", brandVoList);
        }
    }

    /**
     * 根据 关键词\品牌名称\分类\扩展参数\排序 进行商品查询
     *
     * @param pageBean   分页数据包装工具类
     * @param wareIds    仓库ID列表
     * @param indices    索引
     * @param types      类型
     * @param keyWords   输入的关键词
     * @param brands     品牌名称
     * @param cats       分类名称
     * @param params     扩展参数
     * @param sort       排序字段
     * @param priceMin   价格过滤的最小值
     * @param priceMax   价格过滤的上限
     * @param thirdId    第三方ID
     * @param thirdCats  第三方分类ID
     * @param showStock  只显示有货
     * @param showMobile 手机端显示
     * @param isThird    是否为第三方商品 0:否,1:是
     * @return 查询结果数据 {@link Map} {pageBean:{@link SearchPageBean},params:}
     */
    @Override
    public Map<String, Object> searchGoods(SearchPageBean<EsGoodsInfo> pageBean, Long[] wareIds, String[] indices, String[] types, String keyWords, String[] brands, String[] cats, String[] params, String sort, String priceMin, String priceMax, Long thirdId, String[] thirdCats, String showStock, String showMobile, String isThird) {
        return searchGoods(pageBean, wareIds, indices, types, keyWords, brands, cats, params, sort, priceMin, priceMax, thirdId, thirdCats, showStock, showMobile, isThird, null);
    }

    /**
     * 根据 关键词\品牌名称\分类\扩展参数\排序 进行商品查询
     *
     * @param pageBean   分页工具类
     * @param indices    索引
     * @param types      类型
     * @param conditions 查询条件包装
     * @return 查询结果 {pageBean:{@link SearchPageBean},params:}
     */
    @Override
    public Map<String, Object> searchGoods(SearchPageBean<EsGoodsInfo> pageBean, String[] indices, String[] types, CommonConditions conditions) {
        // 查询条件为空
        if (conditions == null) {
            return searchGoods(pageBean, null, indices, types, null, null, null, null, null, null, null, null, null, null, null, null, null);
        }
        // 设置查询条件
        return searchGoods(pageBean, conditions.getWareIds(), indices, types, conditions.getKeyWords(), conditions.getBrands(), conditions.getCats(), conditions.getParams(),
                conditions.getSort(), conditions.getPriceMin(), conditions.getPriceMax(), conditions.getThirdId(), conditions.getThirdCats(), conditions.getShowStock(),
                conditions.getShowMobile(), conditions.getIsThird(), conditions.getInMarketing());
    }

    /**
     * 批量删除
     *
     * @see com.ningpai.goods.service.GoodsElasticSearchService#(java.lang.Long)
     */
    @Override
    public int batchDeleteGoodsIndexToEs(List<Long> list) {
        // 批量删除索引
        indexService.batchDeleteDocForList(GoodsIndexConstant.PRODUCT_INDEX_NAME(), EsGoodsInfo.class.getSimpleName(), list);
        return 0;
    }

    /**
     * 单个删除
     *
     * @see com.ningpai.goods.service.GoodsElasticSearchService#deleteGoodsIndexToEs(java.lang.Long)
     */
    @Override
    public int deleteGoodsIndexToEs(Long goodsInfoId) {
        // 删除索引
        indexService.deleteDoc(GoodsIndexConstant.PRODUCT_INDEX_NAME(), EsGoodsInfo.class.getSimpleName(), String.valueOf(goodsInfoId));
        return 0;
    }

    /**
     * 根据价格等的double值来对聚合后的扩展参数值进行排序
     *
     * @param expandParamVo 扩展参数名{@link ExpandParamVo},取出其中的扩展参数值列表{@link ParamValueVo}进行排序
     */
    private void sortParamValWithDoubleVal(ExpandParamVo expandParamVo) {
        Collections.sort(expandParamVo.getValueVoList(), new Comparator<ParamValueVo>() {
            @Override
            public int compare(ParamValueVo o1, ParamValueVo o2) {
                Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
                Matcher matcher = pattern.matcher(o1.getValueName());
                double v11 = 0;
                if (matcher.find()) {
                    v11 = Double.valueOf(matcher.group());
                }
                double v12 = 0;
                if (matcher.find()) {
                    v12 = Double.valueOf(matcher.group());
                }
                Matcher matcher1 = pattern.matcher(o2.getValueName());
                double v21 = 0;
                if (matcher1.find()) {
                    v21 = Double.valueOf(matcher1.group());
                }
                double v22 = 0;
                if (matcher1.find()) {
                    v22 = Double.valueOf(matcher1.group());
                }

                return v11 > v21 ? 1 : v11 == v21 ? v12 > v22 ? 1 : v12 == v22 ? 0 : -1 : -1;
            }
        });
    }

    /**
     * 获取拼写建议
     *
     * @return 建议的字符串列表
     */
    @Override
    public List<String> getCompletionSuggest(String keyWords) {
        return searchService.getCompletionSuggest(GoodsIndexConstant.PRODUCT_INDEX_NAME(), keyWords, "suggest");
    }

    @Override
    public int deleteGoodsByThirdId(String[] storeInfoIds) {
        BoolQueryBuilder queryBuilder  = QueryBuilders.boolQuery();
        for (String  storeId : storeInfoIds){
             queryBuilder.should(QueryBuilders.matchQuery("thirdId", storeId));
        }
        indexService.deleteByQuery(GoodsIndexConstant.PRODUCT_INDEX_NAME(),GoodsIndexConstant.PRODUCT_TYPE, queryBuilder);
        return 1;
    }
}
