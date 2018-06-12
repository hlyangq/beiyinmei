package com.junit.test.site.goods.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.comment.bean.Comment;
import com.ningpai.goods.bean.Goods;
import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.bean.WareHouse;
import com.ningpai.goods.dao.GoodsRelatedGoodsMapper;
import com.ningpai.goods.dao.GoodsReleExpandParamMapper;
import com.ningpai.goods.dao.GoodsReleParamMapper;
import com.ningpai.goods.dao.GoodsReleTagMapper;
import com.ningpai.goods.pub.GoodsPub;
import com.ningpai.goods.service.GoodsBrandService;
import com.ningpai.goods.service.GoodsProductSuppService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.vo.GoodsListVo;
import com.ningpai.goods.vo.GoodsRelatedGoodsVo;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.bean.PreDiscountMarketing;
import com.ningpai.marketing.dao.PreDiscountMarketingMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.site.customer.service.GoodsCommentService;
import com.ningpai.site.goods.dao.GoodsProductMapper;
import com.ningpai.site.goods.service.GoodsCateService;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.site.goods.service.impl.GoodsProductServiceImpl;
import com.ningpai.site.goods.vo.GoodsProductVo;
import com.ningpai.site.goods.vo.ListFinalBuyVo;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.thirdaudit.service.AuditService;
import com.ningpai.util.PageBean;

/**
 * 货品Service 测试
 * @author djk
 *
 */
public class GoodsProductServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private GoodsProductService goodsProductService = new GoodsProductServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductMapper> goodsProductMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsCateService> goodsCateServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsReleTagMapper> goodsReleTagMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsBrandService> goodsBrandServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsReleExpandParamMapper> goodsReleExpandParamMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsReleParamMapper> goodsReleParamMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsRelatedGoodsMapper> goodsRelatedGoodsMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ProductWareService> productWareServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductSuppService> goodsProductSuppServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<MarketingService> marketServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsPub> goodsPub;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<DefaultAddressService> defaultAddressServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<AuditService> auditServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsCommentService> goodsCommentServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<PreDiscountMarketingMapper> preDiscountMarketingMapperMock;
    
    /**
     * 货品信息
     */
    private GoodsProduct goodsProduct = new GoodsProduct();
    
    /**
     * 货品信息集合
     */
    private List<GoodsProduct> goodsProductLists = new ArrayList<>();
    
    @Override
    protected void setUp() throws Exception 
    {
    	goodsProductLists.add(goodsProduct);
    }
    
    /**
     * 新增积分兑换 对象测试
     */
    @Test
    public void testInsertExchangeCusmomer()
    {
    	Map<String, Object> map = new HashMap<>();
    	goodsProductMapperMock.returns(1).insertExchangeCusmomer(map);
    	assertEquals(1, goodsProductService.insertExchangeCusmomer(map));
    }
    
    /**
     * 根据商品ID查询货品列表测试
     */
    @Test
    public void testQueryAllProductListByGoodsId()
    {
    	List<Object> lists = new ArrayList<>();
    	lists.add(new Object());
    	goodsProductMapperMock.returns(lists).queryProductByGoodsId(1L);
    	assertEquals(1, goodsProductService.queryAllProductListByGoodsId(1L).size());
    }
    
    /**
     * 根据货品ID查询货品信息测试
     * @return
     */
    @Test
    public void testQueryProductByProductId()
    {
    	goodsProductMapperMock.returns(new GoodsProductVo()).queryPrductByProductId(1L);
    	assertNotNull(goodsProductService.queryProductByProductId(1L));
    }
    
    /**
     * 根据分类ID查询货品信息的集合测试
     */
    @Test
    public void testQueryTopSalesByCatIds()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("catId", 1L);
        map.put("rowCount", 10);
        goodsProductMapperMock.returns(goodsProductLists).queryTopSalesInfoByCatIds(map);
    	assertEquals(1, goodsProductService.queryTopSalesByCatIds(1L, 10).size());
    }
    
    /**
     * 查询最近一月该分类下的热销商品测试
     */
    @Test
    public void testQueryHotSalesTopSix()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("catId", 1L);
        map.put("rowCount", 10);
        goodsProductMapperMock.returns(goodsProductLists).queryHotSalesByCatId(map);
    	assertEquals(1, goodsProductService.queryHotSalesTopSix(1L, 10).size());
    }
    
    /**
     * 查询最近一月该分类下同一价格的热销商品测试
     */
    @Test
    public void testQueryHotSalesByCatIdandBrand()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
      	map.put("catId", 1L);
        map.put("rowCount", 10);
        map.put("brandId",1L);
        goodsProductMapperMock.returns(goodsProductLists).queryHotSalesByCatIdandBrand(map);
    	assertEquals(1, goodsProductService.queryHotSalesByCatIdandBrand(1L, 10, 1L).size());
    }
    
    /**
     * 查询最近一月该分类下同一价格的热销商品测试
     */
    @Test
    public void testQueryHotSalesByCatIdandPrice()
    {
    	BigDecimal b = new BigDecimal(1);
    	Map<String, Object> map = new HashMap<String, Object>();
      	map.put("catId", 1L);
        map.put("rowCount", 10);
        map.put("goodsPreferPrice", b);
        goodsProductMapperMock.returns(goodsProductLists).queryHotSalesByCatIdandPrice(map);
    	assertEquals(1, goodsProductService.queryHotSalesByCatIdandPrice(1L, 10, b).size());
    }
    
    /**
     * 根据分类ID查询最新上架的货品测试
     */
    @Test
    public void testQueryTopNewByCatIds()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("catIds", new ArrayList<Long>());
        map.put("rowCount", 10);
        goodsProductMapperMock.returns(goodsProductLists).queryTopNewInfoByCatIds(map);
    	assertEquals(1, goodsProductService.queryTopNewByCatIds(1L, 10).size());
    }
    
    /**
     *  不同地区货品价格以及库存不同 结算页面用测试
     */
    @Test
    public void testQueryGoodsByproductIdAndDistinctId()
    {
    	ProductWare productWare = new ProductWare();
    	productWare.setWarePrice(new BigDecimal(1));
    	PreDiscountMarketing preDiscountMarketing = new PreDiscountMarketing();
    	preDiscountMarketing.setDiscountInfo(new BigDecimal(1));
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	goodsProductVo.setThirdId(0L);
    	Map<String, Object> para = new HashMap<>();
        para.put("marketingId", 1L);
        para.put("goodsId", 1L);
    	Marketing mark = new Marketing();
    	mark.setMarketingId(1L);
    	marketServiceMock.returns(mark).marketingDetail(1L,1L);
    	preDiscountMarketingMapperMock.returns(preDiscountMarketing).selectByMarketId(para);
    	goodsProductMapperMock.returns(goodsProductVo).queryDetailByProductId(1L);
    	productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 1L);
    	assertNotNull(goodsProductService.queryGoodsByproductIdAndDistinctId(1L, 1L, 1L,1L));
    }
    
    /**
     * 根据货品ID查询测试
     */
    @Test
    public void testQueryDetailBeanByProductId()
    {
    	Comment comment = new Comment();
    	comment.setCscoreAvg(new BigDecimal(1.5));
    	comment.setAttscoteAvg(new BigDecimal(1.5));
    	comment.setDscoreAvg(new BigDecimal(1.5));
    	List<GoodsListVo> releatedGoods = new ArrayList<>();
    	releatedGoods.add(new GoodsListVo());
    	GoodsRelatedGoodsVo goodsRelatedGoodsVo = new GoodsRelatedGoodsVo();
    	goodsRelatedGoodsVo.setReleatedGoods(releatedGoods);
    	ProductWare productWare = new ProductWare();
    	productWare.setWarePrice(new BigDecimal(1));
    	List<GoodsRelatedGoodsVo> relaProducts = new ArrayList<>();
    	relaProducts.add(goodsRelatedGoodsVo);
     	GoodsProductVo goodsProductVo = new GoodsProductVo();
     	goodsProductVo.setGoodsId(1L);
     	goodsProductVo.setIsThird("1");
     	goodsProductVo.setThirdId(0L);
     	goodsProductVo.setGoods(new Goods());
     	goodsProductMapperMock.returns(goodsProductVo).queryDetailByProductId(1L);
     	goodsRelatedGoodsMapperMock.returns(relaProducts).queryAllByGoodsId(1L);
    	productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 1L);
    	goodsPub.returns(null).getGoodsGroupService().queryGroupVoListWithOutProductId(1L);
    	goodsCommentServiceMock.returns(comment).selectSellerComment(0L);
    	assertNotNull(goodsProductService.queryDetailBeanByProductId(1L, 1L));
    }
    
    /**
     * 保存商品咨询测试
     */
    @Test
    public void testSaveProductCommentAsk()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("ip", null);
        map.put("type", 1);
        map.put("comment", "1");
        map.put("custId", 1L);
        map.put("productId", 1L);
        map.put("thirdId", 1L);
        map.put("isDisplay", 1L);
        goodsProductMapperMock.returns(1).saveAskComment(map);
        goodsProductMapperMock.returns(1L).queryThirdIdByGoodsInfoId(1L);
        assertEquals(0, goodsProductService.saveProductCommentAsk(1, "1", 1L, 1L, new MockHttpServletRequest()));
    }
    
    /**
     * 根据分类ID查询最终购买的列表测试
     */
    @Test
    public void testBrowCatFinalBuyAndPrecent()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("catId", 1L);
        map.put("rowCount", 10L);
        goodsProductMapperMock.returns(new ArrayList<ListFinalBuyVo>()).browCatFinalBuyAndPrecent(map);
    	assertEquals(0, goodsProductService.browCatFinalBuyAndPrecent(1L, 10L).size());
    }
    
    /**
     * 进行对比测试
     */
    @Test
    public void testExecCompProduct()
    {
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
     	goodsProductVo.setGoods(new Goods());
    	List<Long> productIds = new ArrayList<>();
    	productIds.add(1L);
    	goodsProductMapperMock.returns(goodsProductVo).queryDetailByProductId(1L);
    	assertEquals(1, goodsProductService.execCompProduct(productIds).size());
    }
    
    /**
     * 进行对比,传递请求测试
     */
    @Test
    public void testExecCompProduct2()
    {
      	ProductWare productWare = new ProductWare();
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
     	goodsProductVo.setGoods(new Goods());
     	goodsProductVo.setIsThird("0");
    	List<Long> productIds = new ArrayList<>();
    	productIds.add(1L);
    	goodsProductMapperMock.returns(goodsProductVo).queryDetailByProductId(1L);
    	defaultAddressServiceMock.returns(null).getDefaultIdService();
    	productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 1L);
    	assertEquals(1, goodsProductService.execCompProduct(productIds, new MockHttpServletRequest()).size());
    }
    
    /**
     *  根据货品ID和查询行数查询销售最高的几条记录测试 
     */
    @Test
    public void testQueryTopSalesByProductId()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", 1L);
        map.put("rowCount", 10);
        goodsProductMapperMock.returns(new ArrayList<GoodsProduct>()).queryTopSalesInfoByProductId(map);
    	assertEquals(0, goodsProductService.queryTopSalesByProductId(1L, 10).size());
    }
    
    /**
     * 查询团购商品列表测试
     */
    @Test
    public void testSelectGrouponList()
    {
    	GoodsProductVo v2 = new GoodsProductVo();
    	v2.setThirdId(0L);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setMarketingId(1L);
    	goodsProductVo.setGoodsInfoId(1L);
    	List<GoodsProductVo> productVos = new ArrayList<>();
    	productVos.add(goodsProductVo);
    	PageBean pb = new PageBean();
    	goodsProductMapperMock.returns(1).selectGrouponCount();
	    Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", pb.getStartRowNum());
        map.put("number", pb.getEndRowNum());
        goodsProductMapperMock.returns(productVos).selectGrouponList(map);
        marketServiceMock.returns(new Marketing()).selectGrouponMarket(1L);
        goodsProductMapperMock.returns(v2).queryDetailByProductId(1L);
        
    	assertEquals(1, goodsProductService.selectGrouponList(pb, new MockHttpServletRequest()).getList().size());
    }
    
    /**
     * 根据货品ID 查询这个货品是否是上架状态以及未删除状态测试
     */
    @Test
    public void testQueryProductByGoodsInfoId()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", 1L);
        goodsProductMapperMock.returns(1).queryProductByGoodsInfoId(map);
    	assertEquals(1, goodsProductService.queryProductByGoodsInfoId(1L));
    }
    
    /**
     * 查询抢购秒杀商品列表测试
     */
    @Test
    public void testSelectMarketingRushList()
    {
    	Marketing marketing = new Marketing();
    	marketing.setMarketingBegin(new Date());
    	marketing.setMarketingEnd(new Date());
    	GoodsProductVo v2 = new GoodsProductVo();
    	v2.setThirdId(0L);
    	List<GoodsProductVo> productVos = new ArrayList<>();
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
      	goodsProductVo.setMarketingId(1L);
    	goodsProductVo.setGoodsInfoId(1L);
    	productVos.add(goodsProductVo);
    	PageBean pb = new PageBean();
    	goodsProductMapperMock.returns(1).selectMarketingRushCount();
	    Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", pb.getStartRowNum());
        map.put("number", pb.getEndRowNum());
        goodsProductMapperMock.returns(productVos).selectMarketingRushList(map);
        marketServiceMock.returns(marketing).selectRushMarket(1L);
        goodsProductMapperMock.returns(v2).queryDetailByProductId(1L);
        assertEquals(1, goodsProductService.selectMarketingRushList(pb, new MockHttpServletRequest()).getList().size());
    }
    
    /**
     * 比较时间测试
     */
    @Test
    public void testCompareTime()
    {
    	assertEquals("1", goodsProductService.compareTime(new MockHttpServletRequest(), new Date(), new Date()));
    }
    
    /**
     * 根据组合id查询商品id测试
     */
    @Test
    public void testQueryDetailByGroupId()
    {
    	goodsProductMapperMock.returns(new ArrayList<GoodsProductVo>()).queryDetailByGroupId(1L);
    	assertEquals(0, goodsProductService.queryDetailByGroupId(1L).size());
    }
    
    /**
     * 根据地区ID查询关联的仓库ID测试
     */
    @Test
    public void testSelectWareIdByDistinctId()
    {
    	WareHouse wareHouse = new WareHouse();
    	wareHouse.setWareId(1L);
    	productWareServiceMock.returns(wareHouse).findWare(1L);
    	Long result = 1L;
    	assertEquals(result, goodsProductService.selectWareIdByDistinctId(1L));
    }
}
