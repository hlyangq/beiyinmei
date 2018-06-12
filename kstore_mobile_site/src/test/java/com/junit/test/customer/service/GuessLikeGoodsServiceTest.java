package com.junit.test.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.m.customer.bean.Browserecord;
import com.ningpai.m.customer.mapper.BrowserecordMapper;
import com.ningpai.m.customer.service.GuessLikeGoodsService;
import com.ningpai.m.customer.service.impl.GuessLikeGoodsServiceImpl;
import com.ningpai.m.goods.dao.GoodsMapper;
import com.ningpai.m.goods.dao.GoodsProductMapper;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.system.service.DefaultAddressService;

/**
 * 猜你喜欢业务层接口测试
 * @author djk
 *
 */
public class GuessLikeGoodsServiceTest extends UnitilsJUnit3 {
	
	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private GuessLikeGoodsService goodsService = new GuessLikeGoodsServiceImpl();
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<BrowserecordMapper> browserecordMapperMock;
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<ProductWareService> productWareServiceMock;
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<GoodsProductService> goodsProductServiceMock;
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<GoodsMapper> goodsMapperMock;
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<GoodsProductMapper> goodsProductMapperMock;
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<DefaultAddressService> defaultAddressServiceMock;
	
	/**
	 * 计算猜你喜欢商品测试
	 * 不是第三方的场景
	 */
	@Test
	public void testGuessLikeGoodsList()
	{
		List<Long> cateIds = new ArrayList<Long>();
		cateIds.add(0L);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("catIds", cateIds);
        map.put("rowCount", 100);
		GoodsProduct goodsProductT = new GoodsProduct();
		goodsProductT.setGoodsId(1L);
		goodsProductT.setGoodsInfoId(1L);
		List<GoodsProduct> goodsProducts = new ArrayList<GoodsProduct>();
		goodsProducts.add(goodsProductT);
		Browserecord browserecord = new Browserecord(); 
		browserecord.setGoodsId(1);
		List<Object> browereList  = new ArrayList<>();
		browereList.add(browserecord);
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("customerId", 1L);
        paramMap.put("isMobile", 1);
        paramMap.put("startRowNum", 0);
        paramMap.put("endRowNum", 10);
        browserecordMapperMock.returns(browereList).selectBrowserecord(paramMap);
        goodsProductServiceMock.returns(goodsProductT).queryByGoodsInfoDetail(1L);
        goodsProductMapperMock.returns(goodsProducts).queryGoodsInfoByCatIds(map);
        productWareServiceMock.returns(new ProductWare()).queryProductWareByProductIdAndDistinctId(1L, 0L);
		assertEquals(0, goodsService.guessLikeGoodsList(1L).size());
	}
	
	

	/**
	 * 计算猜你喜欢商品测试
	 * 是第三方的场景
	 */
	@Test
	public void testGuessLikeGoodsList1()
	{
		List<Long> cateIds = new ArrayList<Long>();
		cateIds.add(0L);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("catIds", cateIds);
        map.put("rowCount", 100);
		GoodsProduct goodsProductT = new GoodsProduct();
		goodsProductT.setGoodsId(1L);
		goodsProductT.setIsThird("1");
		goodsProductT.setGoodsInfoId(1L);
		List<GoodsProduct> goodsProducts = new ArrayList<GoodsProduct>();
		goodsProducts.add(goodsProductT);
		Browserecord browserecord = new Browserecord(); 
		browserecord.setGoodsId(1);
		List<Object> browereList  = new ArrayList<>();
		browereList.add(browserecord);
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("customerId", 1L);
        paramMap.put("isMobile", 1);
        paramMap.put("startRowNum", 0);
        paramMap.put("endRowNum", 10);
        browserecordMapperMock.returns(browereList).selectBrowserecord(paramMap);
        goodsProductServiceMock.returns(goodsProductT).queryByGoodsInfoDetail(1L);
        goodsProductMapperMock.returns(goodsProducts).queryGoodsInfoByCatIds(map);
		assertEquals(0, goodsService.guessLikeGoodsList(1L).size());
	}
}
