package com.junit.test.site.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.common.lucene.main.LuceneIKUtil;
import com.ningpai.goods.bean.GoodsBrand;
import com.ningpai.goods.bean.GoodsSpecDetail;
import com.ningpai.goods.bean.GoodsTypeExpandParamValue;
import com.ningpai.goods.service.GoodsElasticSearchService;
import com.ningpai.goods.vo.GoodsSpecVo;
import com.ningpai.goods.vo.GoodsTypeSpecVo;
import com.ningpai.site.goods.dao.GoodsMapper;
import com.ningpai.site.goods.service.GoodsCateService;
import com.ningpai.site.goods.service.GoodsService;
import com.ningpai.site.goods.service.impl.GoodsServiceImpl;
import com.ningpai.site.goods.util.GoodsSiteSearchBean;
import com.ningpai.site.goods.vo.GoodsDetailVo;
import com.ningpai.site.goods.vo.GoodsListScreenVo;
import com.ningpai.site.goods.vo.GoodsTypeBrandVo;
import com.ningpai.site.goods.vo.GoodsTypeExpandParamVo;
import com.ningpai.site.goods.vo.GoodsTypeVo;
import com.ningpai.site.thirdseller.bean.ThirdGoodsSearchBean;
import com.ningpai.site.thirdseller.dao.ThirdCateMapper;
import com.ningpai.site.thirdseller.service.ThirdCateService;
import com.ningpai.site.thirdseller.vo.ThirdCateVo;
import com.ningpai.util.PageBean;

/**
 *  商品Service测试
 * @author djk
 *
 */
public class GoodsServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private GoodsService goodsService = new GoodsServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsMapper> goodsMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsCateService> goodsCateServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ThirdCateService> thirdCateServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ThirdCateMapper> thirdCateMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<LuceneIKUtil> luceneIKUtilMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsElasticSearchService> goodsElasticSearchServiceMock;
    
    /**
     * 分页辅助类
     */
    private PageBean pb = new PageBean();
    
    /**
     * 查询商品的帮助Bean
     */
    private GoodsSiteSearchBean searchBean = new GoodsSiteSearchBean();
    
    /**
     * 第三方商品搜索bean
     */
    private ThirdGoodsSearchBean thirdGoodsSearchBean = new ThirdGoodsSearchBean();
    
    
    /**
     * 根据货品的Id 查询商品分类测试
     */
    @Test
    public void testSearchGoodsCatIdByGoodsInfoId()
    {
    	goodsMapperMock.returns(1L).searchGoodsCatIdByGoodsInfoId(1L);
    	Long result = 1L;
    	assertEquals(result, goodsService.searchGoodsCatIdByGoodsInfoId(1L));
    }
    
    /**
     * 查询商品列表测试
     */
    @Test
    public void testSearchGoods()
    {
    	Map<String, Object> resultMap = new HashMap<>();
    	List<String> lists = new ArrayList<>();
    	lists.add("1");
    	resultMap.put("productIds", lists);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("distinctId", searchBean.getDistinctId());
    	map.put("productIds", lists);
    	luceneIKUtilMock.returns(resultMap).dirceotry(searchBean.getTitle());
    	map.put("searchBean", searchBean);
    	goodsMapperMock.returns(1).searchGoodsCount(map);
    	map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        List<Object> listss = new ArrayList<>();
        listss.add(new Object());
        goodsMapperMock.returns(listss).searchGoods(map);
    	assertEquals(1, goodsService.searchGoods(pb, searchBean).getList().size());
    }
    
    
    /**
     * 查询最新的三条记录测试
     */
    @Test
    public void testQueryTopThreeNew()
    {
    	List<Long> catIds = new ArrayList<Long>();
    	List<Object> list = new ArrayList<>();
    	list.add(new Object());
    	Map<String, Object> map = new HashMap<String, Object>();
    	goodsCateServiceMock.returns(null).calcAllSonCatIds(null, catIds);
    	map.put("catIds", catIds);
    	goodsMapperMock.returns(list).queryNewInfoTopThree(map);
    	assertEquals(1, goodsService.queryTopThreeNew(1L).size());
    }
    
    /**
     * 根据参数和分类ID查询商品列表测试
     */
    @Test
    public void testSearchGoods2()
    {
    	List<Object> list = new ArrayList<>();
    	list.add(new Object());
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("searchBean", searchBean);
    	map.put("catIds", new ArrayList<Long>());
    	goodsMapperMock.returns(1).searchTotalCountByCatId(map);
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        goodsMapperMock.returns(list).queryGoodsListByCatId(map);
    	assertEquals(1, goodsService.searchGoods(pb, searchBean, 1L).getList().size());
    }
    
    /**
     * 根据扩展参数和分类ID查询商品列表测试
     */
    @Test
    public void testSearchGoods3()
    {
    	List<Object> list = new ArrayList<>();
    	list.add(new Object());
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("searchBean", searchBean);
    	map.put("catId", 1L);
    	map.put("brandId", "1");
    	map.put("distinctId", "1");
    	map.put("catIds", new ArrayList<Long>());
    	goodsMapperMock.returns(1).searchTotalCountByCatId(map);
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        goodsMapperMock.returns(list).queryGoodsListByCatId(map);
    	String[] params = {"b1"};
    	assertEquals(1, goodsService.searchGoods(pb, searchBean, 1L, params, "1").getList().size());
    }
    
    /**
     * 根据扩展参数和分类ID查询商品列表测试
     * 参数是e的场景
     */
    @Test
    public void testSearchGoods31()
    {
    	List<Object> list = new ArrayList<>();
    	list.add(new Object());
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("searchBean", searchBean);
    	map.put("catId", 1L);
    	map.put("brandId", "1");
    	map.put("distinctId", "1");
    	map.put("catIds", new ArrayList<Long>());
    	goodsMapperMock.returns(1).searchTotalCountByCatId(map);
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        goodsMapperMock.returns(list).queryGoodsListByCatId(map);
    	String[] params = {"e1"};
    	assertEquals(0, goodsService.searchGoods(pb, searchBean, 1L, params, "1").getList().size());
    }
    
    /**
     * 根据扩展参数和分类ID查询商品列表测试
     * 参数是s的场景
     */
    @Test
    public void testSearchGoods32()
    {
    	List<Object> list = new ArrayList<>();
    	list.add(new Object());
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("searchBean", searchBean);
    	map.put("catId", 1L);
    	map.put("brandId", "1");
    	map.put("distinctId", "1");
    	map.put("catIds", new ArrayList<Long>());
    	goodsMapperMock.returns(1).searchTotalCountByCatId(map);
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        goodsMapperMock.returns(list).queryGoodsListByCatId(map);
    	String[] params = {"s1"};
    	assertEquals(0, goodsService.searchGoods(pb, searchBean, 1L, params, "1").getList().size());
    }
    
    /**
     * 根据扩展参数和分类ID查询商品列表测试
     */
    @Test
    public void testSearchGood()
    {
    	List<Object> list = new ArrayList<>();
    	list.add(new Object());
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("searchBean", searchBean);
    	map.put("catId", 1L);
    	map.put("brandId", "1");
    	map.put("distinctId", "1");
    	map.put("catIds", new ArrayList<Long>());
    	goodsMapperMock.returns(1).searchTotalCountByCatId(map);
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        goodsMapperMock.returns(list).queryGoodsListByPid(map);
    	String[] params = {"b1"};
    	assertEquals(1, goodsService.searchGood(pb, searchBean, 1L, params, "1").getList().size());
    }
    
    /**
     * 根据扩展参数和分类ID查询商品列表测试
     */
    @Test
    public void testSearchGoods4()
    {
    	List<String> productIds = new ArrayList<>();
    	productIds.add("1");
    	Map<String, Object> resultMap = new HashMap<>();
    	resultMap.put("productIds", productIds);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("distinctId", searchBean.getDistinctId());
    	map.put("productIds", productIds);
    	map.put("brandId", "1");
    	String[] params = {"b1"};
    	map.put("searchBean", searchBean);
    	luceneIKUtilMock.returns(resultMap).dirceotry(searchBean.getTitle());
    	goodsMapperMock.returns(1).searchTotalCount(map);
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        goodsMapperMock.returns(lists).searchGoods(map);
    	assertEquals(1, goodsService.searchGoods(pb, searchBean, params).getList().size());
    }
    
    /**
     * 根据商品ID查询商品的详细信息测试
     */
    @Test
    public void testQueryGoodsDetailVoByGoodsId()
    {
    	goodsMapperMock.returns(new GoodsDetailVo()).queryGoodsDetailVoByGoodsId(1L);
    	assertNotNull(goodsService.queryGoodsDetailVoByGoodsId(1L));
    }
    
    /**
     * 根据商品详细信息返回默认的货品信息测试
     */
    @Test
    public void testReturnDefaultGoodsProduct()
    {
    	assertNotNull(goodsService.returnDefaultGoodsProduct(new GoodsDetailVo()));
    }
    
    /**
     * 根据传递的参数和类型Vo计算已经选择的条件测试
     */
    @Test
    public void testCalcScreenParam()
    {
    	GoodsTypeVo goodsTypeVo = new GoodsTypeVo();
    	List<GoodsTypeBrandVo> brands = new ArrayList<>();
    	GoodsTypeBrandVo goodsTypeBrandVo = new GoodsTypeBrandVo();
    	GoodsBrand brand = new GoodsBrand();
    	brand.setBrandId(1L);
    	goodsTypeBrandVo.setBrand(brand);
    	brands.add(goodsTypeBrandVo);
    	goodsTypeVo.setBrands(brands);
    	String [] params ={"b1"};
    	assertEquals(1, goodsService.calcScreenParam(params,goodsTypeVo).size());
    }
    
    /**
     * 根据传递的参数和类型Vo计算已经选择的条件测试
     * 参数是e的场景
     */
    @Test
    public void testCalcScreenParam2()
    {
    	GoodsTypeExpandParamValue goodsTypeExpandParamValue = new GoodsTypeExpandParamValue();
    	goodsTypeExpandParamValue.setExpandparamValueId(1L);
    	goodsTypeExpandParamValue.setExpandparamId(1L);
    	List<GoodsTypeExpandParamValue> valueList = new ArrayList<>();
    	valueList.add(goodsTypeExpandParamValue);
    	GoodsTypeExpandParamVo goodsTypeExpandParamVo = new GoodsTypeExpandParamVo();
    	goodsTypeExpandParamVo.setExpandparamId(1L);
    	goodsTypeExpandParamVo.setValueList(valueList);
    	GoodsTypeVo goodsTypeVo = new GoodsTypeVo();
    	List<GoodsTypeExpandParamVo> expandParams = new ArrayList<>();
    	expandParams.add(goodsTypeExpandParamVo);
    	goodsTypeVo.setExpandParams(expandParams);
    	String [] params ={"e1"};
    	assertEquals(1, goodsService.calcScreenParam(params,goodsTypeVo).size());
    }
    
    /**
     * 根据传递的参数和类型Vo计算已经选择的条件测试
     * 参数是s的场景
     */
    @Test
    public void testCalcScreenParam3()
    {
    	List<Object> specDetails = new ArrayList<>();
    	GoodsSpecDetail specDetail = new GoodsSpecDetail();
    	specDetail.setSpecDetailId(1L);
    	specDetails.add(specDetail);
    	GoodsSpecVo goodsSpec = new GoodsSpecVo();
    	goodsSpec.setSpecId(1L);
    	goodsSpec.setSpecDetails(specDetails);
    	GoodsTypeSpecVo goodsTypeSpecVo = new GoodsTypeSpecVo();
    	goodsTypeSpecVo.setGoodsSpec(goodsSpec);
    	List<GoodsTypeSpecVo> specVos = new ArrayList<>();
    	specVos.add(goodsTypeSpecVo);
    	GoodsTypeVo goodsTypeVo = new GoodsTypeVo();
    	String [] params ={"s1"};
    	goodsTypeVo.setSpecVos(specVos);
    	assertEquals(1, goodsService.calcScreenParam(params,goodsTypeVo).size());
    }
    
    /**
     * 根据计算好的选中的参数和类型Vo计算vo中需要显示的信息测试
     */
    @Test
    public void testCalcTypeVo()
    {
    	List<GoodsListScreenVo> screenList = new ArrayList<>();
    	GoodsListScreenVo goodsListScreenVo = new GoodsListScreenVo();
    	goodsListScreenVo.setType("brand");
    	screenList.add(goodsListScreenVo);
    	assertNotNull(goodsService.calcTypeVo(screenList, new GoodsTypeVo()));
    }
    
    /**
     * 根据计算好的选中的参数和类型Vo计算vo中需要显示的信息测试
     * 测试type为expand的场景
     */
    @Test
    public void testCalcTypeVo2()
    {
    	GoodsTypeExpandParamVo goodsTypeExpandParamVo = new GoodsTypeExpandParamVo();
    	List<GoodsTypeExpandParamVo> expandParams = new ArrayList<>();
    	goodsTypeExpandParamVo.setExpandparamId(1L);
    	expandParams.add(goodsTypeExpandParamVo);
    	GoodsTypeVo goodsTypeVo = new GoodsTypeVo();
    	goodsTypeVo.setExpandParams(expandParams);
    	List<GoodsListScreenVo> screenList = new ArrayList<>();
    	GoodsListScreenVo goodsListScreenVo = new GoodsListScreenVo();
    	goodsListScreenVo.setType("expand");
    	goodsListScreenVo.setParentId("1");
    	screenList.add(goodsListScreenVo);
    	assertNotNull(goodsService.calcTypeVo(screenList, goodsTypeVo));
    }
    
    
    /**
     * 根据计算好的选中的参数和类型Vo计算vo中需要显示的信息测试
     * 测试type为spec的场景
     */
    @Test
    public void testCalcTypeVo3()
    {
    	GoodsSpecVo goodsSpec = new GoodsSpecVo();
    	goodsSpec.setSpecId(1L);
    	GoodsTypeSpecVo goodsTypeSpecVo = new GoodsTypeSpecVo();
    	goodsTypeSpecVo.setGoodsSpec(goodsSpec);
    	goodsTypeSpecVo.setTypeSpecId(1L);
    	List<GoodsTypeSpecVo> specVos = new ArrayList<>();
    	specVos.add(goodsTypeSpecVo);
    	GoodsTypeVo goodsTypeVo = new GoodsTypeVo();
    	goodsTypeVo.setSpecVos(specVos);
    	List<GoodsListScreenVo> screenList = new ArrayList<>();
    	GoodsListScreenVo goodsListScreenVo = new GoodsListScreenVo();
    	goodsListScreenVo.setType("spec");
    	goodsListScreenVo.setParentId("1");
    	screenList.add(goodsListScreenVo);
    	assertNotNull(goodsService.calcTypeVo(screenList, goodsTypeVo));
    }
    /**
     * 参数查询第三方店铺商品列表测试
     */
    @Test
    public void testThirdGoodsList()
    {
    	List<Object> lists = new ArrayList<>();
    	lists.add(new Object());
    	List<String> productIds = new ArrayList<>();
    	productIds.add("1");
    	List<String> cateIds = new ArrayList<>();
    	cateIds.add("1");
    	cateIds.add("1");
    	cateIds.add("1");
    	Map<String, Object> resultMap  = new HashMap<>();
    	resultMap.put("productIds", productIds);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("thirdCateIds", cateIds);
    	map.put("thirdId", "1");
    	map.put("searchBean", thirdGoodsSearchBean);
    	map.put("productIds", productIds);
    	pb.setPageSize(20);
    	goodsMapperMock.returns(1).searchGoods(map);
    	map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
    	ThirdCateVo thirdCate = new ThirdCateVo();
    	thirdCate.setCatParentId(0L);
    	List<ThirdCateVo> cateVos = new ArrayList<>();
    	ThirdCateVo thirdCateVo = new ThirdCateVo();
    	thirdCateVo.setCatParentId(0L);
    	thirdCateVo.setCatId(1L);
    	cateVos.add(thirdCateVo);
    	thirdCate.setCateVos(cateVos);
    	thirdGoodsSearchBean.setCateId(1L);
    	thirdCateMapperMock.returns(thirdCate).selectByPrimaryKey(1L);
    	thirdCateServiceMock.returns(thirdCate).queryThirdCateByPraentCateId(null, 1L);
    	luceneIKUtilMock.returns(resultMap).dirceotry(thirdGoodsSearchBean.getTitle());
    	goodsMapperMock.returns(lists).serchThirdGoods(map);
    	assertEquals(1, goodsService.thirdGoodsList(pb, thirdGoodsSearchBean, 1L, 1L).getList().size());
    }
    
    /**
     * 参数查询第三方店铺商品列表测试
     */
    @Test
    public void testThirdGoodsListEs()
    {
    	Map<String, Object> result = new HashMap<>();
    	result.put("1", 1);
    	thirdGoodsSearchBean.setCateId(1L);
       	ThirdCateVo thirdCate = new ThirdCateVo();
    	thirdCate.setCatParentId(0L);
    	List<ThirdCateVo> cateVos = new ArrayList<>();
    	ThirdCateVo thirdCateVo = new ThirdCateVo();
    	thirdCateVo.setCatParentId(0L);
    	thirdCateVo.setCatId(1L);
    	cateVos.add(thirdCateVo);
    	thirdCate.setCateVos(cateVos);
    	thirdCateMapperMock.returns(thirdCate).selectByPrimaryKey(1L);
    	thirdCateServiceMock.returns(thirdCate).queryThirdCateByPraentCateId(null, 1L);
    	assertEquals(0, goodsService.thirdGoodsListEs(null, thirdGoodsSearchBean, 1L, 1L, "1").size());
    }
    
    /**
     * 根据品牌ID查询该品牌的货品测试
     */
    @Test
    public void testSelectProductsByBrandId()
    {
    	List<Object> lists = new ArrayList<>();
    	lists.add(new Object());
    	Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("startRowNum", pb.getStartRowNum());
        paraMap.put("endRowNum", pb.getEndRowNum());
        paraMap.put("brandId", 1L);
        paraMap.put("distinctId", 1L);
        goodsMapperMock.returns(lists).selectProductsByBrandId(paraMap);
        goodsMapperMock.returns(1).selectProductsCountByBrandId(paraMap);
    	assertEquals(1, goodsService.selectProductsByBrandId(pb, 1L, 1L).getList().size());
    }
    
    /**
     * 根据品牌ID查出分类ID集合测试
     */
    @Test
    public void testSelectCatIdByBrandId()
    {
    	List<Long>  lists = new ArrayList<>();
    	lists.add(1L);
    	goodsMapperMock.returns(lists).selectCatIdByBrandId(1L);
    	assertEquals(lists, goodsService.selectCatIdByBrandId(1L));
    }
}
