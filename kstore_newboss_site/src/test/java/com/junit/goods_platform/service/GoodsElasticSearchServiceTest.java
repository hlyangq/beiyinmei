package com.junit.goods_platform.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.io.annotation.FileContent;
import org.unitils.mock.Mock;

import com.ningpai.goods.bean.EsGoodsCategory;
import com.ningpai.goods.bean.EsGoodsInfo;
import com.ningpai.goods.bean.EsThirdCate;
import com.ningpai.goods.dao.GoodsElasticMapper;
import com.ningpai.goods.service.GoodsElasticSearchService;
import com.ningpai.goods.service.impl.GoodsElasticSearchServiceImpl;
import com.ningpai.goods.util.CommonConditions;
import com.ningpai.goods.util.SearchPageBean;
import com.ningpai.searchplatform.response.IndexSearchResponse;
import com.ningpai.searchplatform.service.IndexService;
import com.ningpai.searchplatform.service.SearchService;

/**
 * 商品索引接口测试
 * @author djk
 *
 */
public class GoodsElasticSearchServiceTest extends UnitilsJUnit3 {

    @TestedObject
    private GoodsElasticSearchService goodsElasticSearchService = new GoodsElasticSearchServiceImpl();

    @InjectIntoByType
    private Mock<GoodsElasticMapper> goodsElasticMapperMock;

    @InjectIntoByType
    private Mock<IndexService> indexServiceMock;

    @InjectIntoByType
    private Mock<SearchService> searchServiceMock;

    @FileContent("json.js")
    private String json;

    /**
     * 查询所有商品信息简历索引测试
     */
    @Test
    public void testCreateGoodsIndexToEs() {
        EsThirdCate esThirdCate = new EsThirdCate();
        esThirdCate.setCatParentId(1L);
        EsGoodsCategory esGoodsCategory = new EsGoodsCategory();
        esGoodsCategory.setCatParentId(1L);
        EsGoodsInfo esGoodsInfo = new EsGoodsInfo();
        esGoodsInfo.setCatId(1L);
        esGoodsInfo.setThirdCateId(1L);
        esGoodsInfo.setGoodsInfoId(1L);
        List<EsGoodsInfo> egoodslist = new ArrayList<>();
        egoodslist.add(esGoodsInfo);
        goodsElasticMapperMock.returns(1).selectGoodsElasticListCount();
        goodsElasticMapperMock.returns(egoodslist).selectGoodsElasticListByPage(0, 5);
        goodsElasticMapperMock.returns(esGoodsCategory).selectGoodsCateList(1L);
        goodsElasticMapperMock.returns(esThirdCate).selectGoodsThirdCateList(1L);
        assertEquals(1, goodsElasticSearchService.createGoodsIndexToEs());
    }

    /**
     * 插入单个索引测试
     */
    @Test
    public void testInsertOneGoodsIndexToEs() {
        List<EsGoodsInfo> egoodslist = new ArrayList<>();
        egoodslist.add(new EsGoodsInfo());
        goodsElasticMapperMock.returns(egoodslist).selectGoodsElasticList(1L);
        assertEquals(0, goodsElasticSearchService.insertOneGoodsIndexToEs(1L));
    }

    /**
     * 修改 索 引测试
     */
    @Test
    public void testUpdateOneGoodsIndexToEs() {
        List<EsGoodsInfo> egoodslist = new ArrayList<>();
        egoodslist.add(new EsGoodsInfo());
        goodsElasticMapperMock.returns(egoodslist).selectGoodsElasticList(1L);
        assertEquals(0, goodsElasticSearchService.updateOneGoodsIndexToEs(1L));
    }

    /**
     * 批量删除测试
     */
    @Test
    public void testBatchDeleteGoodsIndexToEs() {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        assertEquals(0, goodsElasticSearchService.batchDeleteGoodsIndexToEs(list));
    }

    /**
     * 单个删除测试
     */
    @Test
    public void testDeleteGoodsIndexToEs() {
        assertEquals(0, goodsElasticSearchService.deleteGoodsIndexToEs(1L));
    }

    /**
     * 根据 关键词\品牌名称\分类\扩展参数\排序 进行商品查询测试
     */
    @Test
    public void testSearchGoods() {

        SearchPageBean<EsGoodsInfo> pageBean = new SearchPageBean<>();
        Long[] wareIds = { 1L };
        String[] indices = { "1" };
        String[] types = { "1" };
        String keyWords = "1";
        String[] brands = { "1" };
        String[] cats = { "1" };
        String[] params = { "1:111" };
        String sort = "11D";
        String priceMin = "1";
        String priceMax = "1";
        Long thirdId = 1L;
        String[] thirdCats = { "1" };
        String showStock = "1";
        String showMobile = "1";
        String isThird = "1";
        IndexSearchResponse response = new IndexSearchResponse();
        searchServiceMock.returns(response).search(null);
        assertEquals(
                1,
                goodsElasticSearchService.searchGoods(pageBean, wareIds, indices, types, keyWords, brands, cats,
                        params, sort, priceMin, priceMax, thirdId, thirdCats, showStock, showMobile, isThird).size());
    }

    /**
     * 根据 关键词\品牌名称\分类\扩展参数\排序 进行商品查询 测试
     */
    @Test
    public void testSearchGoods2() {
         SearchPageBean<EsGoodsInfo> pageBean = new SearchPageBean<>();
         String[] indices = {"1"};
         String[] types = {"1"};
         CommonConditions conditions = new CommonConditions();
         Long [] ids = {1L};
         conditions.setWareIds(ids);
         String[] brands = {"1"};
         conditions.setBrands(brands);
         String[] params = {"1"};
         conditions.setParams(params);
         String[] thirdCats = {"1"};
         conditions.setThirdCats(thirdCats);
         IndexSearchResponse response = new IndexSearchResponse();
         searchServiceMock.returns(response).search(null);
         assertEquals(1, goodsElasticSearchService.searchGoods(pageBean, indices, types, conditions).size());
    }
}
