package com.junit.test.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.m.customer.bean.Browserecord;
import com.ningpai.m.customer.mapper.BrowserecordMapper;
import com.ningpai.m.customer.service.BrowserecordService;
import com.ningpai.m.customer.service.impl.BrowserecordServiceImpl;
import com.ningpai.m.customer.vo.GoodsBean;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.util.PageBean;

/**
 * 浏览记录接口测试
 * 
 * @author djk
 *
 */
public class BrowserecordServiceTest extends UnitilsJUnit3 {

	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private BrowserecordService browserecordService = new BrowserecordServiceImpl();

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
	private Mock<DefaultAddressService> defaultAddressServiceMock;

	/**
	 * 查询浏览记录测试 第三方货品的场景
	 */
	@Test
	public void testSelectBrowserecord() {
		GoodsProductVo goodsProductVo = new GoodsProductVo();
		goodsProductVo.setIsThird("1");
		Browserecord browserecord = new Browserecord();
		browserecord.setGoods(new GoodsBean());
		browserecord.setGoodsId(1);
		List<Object> browereList = new ArrayList<>();
		browereList.add(browserecord);
		PageBean pb = new PageBean();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerId", 1L);
		paramMap.put("isMobile", 1);
		paramMap.put("startRowNum", pb.getStartRowNum());
		paramMap.put("endRowNum", 5);
		browserecordMapperMock.returns(browereList)
				.selectBrowserecord(paramMap);
		goodsProductServiceMock.returns(goodsProductVo)
				.queryProductByProductId(1L);
		assertEquals(1, browserecordService.selectBrowserecord(1L, pb)
				.getList().size());
	}

	/**
	 * 查询浏览记录测试 不是第三方货品的场景
	 */
	@Test
	public void testSelectBrowserecord1() {
		GoodsProductVo goodsProductVo = new GoodsProductVo();
		goodsProductVo.setIsThird("0");
		Browserecord browserecord = new Browserecord();
		browserecord.setGoods(new GoodsBean());
		browserecord.setGoodsId(1);
		List<Object> browereList = new ArrayList<>();
		browereList.add(browserecord);
		PageBean pb = new PageBean();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerId", 1L);
		paramMap.put("isMobile", 1);
		paramMap.put("startRowNum", pb.getStartRowNum());
		paramMap.put("endRowNum", 5);
		browserecordMapperMock.returns(browereList)
				.selectBrowserecord(paramMap);
		goodsProductServiceMock.returns(goodsProductVo)
				.queryProductByProductId(1L);
		productWareServiceMock.returns(new ProductWare())
				.queryProductWareByProductIdAndDistinctId(1L, 0L);
		assertEquals(1, browserecordService.selectBrowserecord(1L, pb)
				.getList().size());
	}

	/**
	 * 根据主键删除测试
	 */
	@Test
	public void testDeleteByPrimaryKey() {
		Map<String, Object> map = new HashMap<>();
		map.put("likeId", 1L);
		map.put("customerId", 1L);
		browserecordMapperMock.returns(1).deleteByPrimaryKey(map);
		assertEquals(1, browserecordService.deleteByPrimaryKey(1L, 1L));
	}

	/**
	 * 根据货品编号删除测试
	 */
	@Test
	public void testDeleteByGoodsInfoId() {
		Map<String, Object> map = new HashMap<>();
		map.put("goodInfoId", 1L);
		map.put("customerId", 1L);
		browserecordMapperMock.returns(1).deleteByPrimaryKey(map);
		assertEquals(1, browserecordService.deleteByGoodsInfoId(1L, 1L));
	}

	/**
	 * 添加浏览记录测试 用户登录的场景 之前有浏览记录的场景
	 */
	@Test
	public void testAddBrowerecord() {

		Browserecord browserecord = new Browserecord();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", 1L);
		map.put("goodsId", 1L);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(new MockHttpSession());
		request.getSession().setAttribute("customerId", 1L);
		browserecordMapperMock.returns(browserecord).selectByBrowereId(map);
		browserecordMapperMock.returns(1).updataBrowereById(browserecord);
		assertEquals(1, browserecordService.addBrowerecord(request,
				new MockHttpServletResponse(), 1L));
	}

	/**
	 * 添加浏览记录测试 用户登录的场景 之前没有浏览记录的场景
	 */
	@Test
	public void testAddBrowerecord1() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", 1L);
		map.put("goodsId", 1L);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(new MockHttpSession());
		request.getSession().setAttribute("customerId", 1L);
		browserecordMapperMock.returns(1).insertSelective(new Browserecord());
		assertEquals(1, browserecordService.addBrowerecord(request,
				new MockHttpServletResponse(), 1L));
	}

	/**
	 * 添加浏览记录测试 没有用户登录的场景 cookie存在的情况
	 */
	@Test
	public void testAddBrowerecord2() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Cookie cookies = new Cookie("_npstore_browpro", "1,s1e");
		request.setCookies(cookies);
		assertEquals(0, browserecordService.addBrowerecord(request,
				new MockHttpServletResponse(), 1L));
	}

	/**
	 * 添加浏览记录测试 没有用户登录的场景 cookie不存在的情况
	 */
	@Test
	public void testAddBrowerecord3() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertEquals(0, browserecordService.addBrowerecord(request,
				new MockHttpServletResponse(), 1L));
	}

	/**
	 * 查询浏览记录数目测试
	 */
	@Test
	public void testBrowereCount() {
		// 查询参数Map集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerId", 1L);
		paramMap.put("isMobile", "1");
		browserecordMapperMock.returns(1L).selectBrowserecordCount(paramMap);
		Long result = 1L;
		assertEquals(result, browserecordService.browereCount(1L));
	}

	/**
	 * 从cook添加到测试 不存在浏览记录册场景
	 */
	@Test
	public void testLoadBrowerecord() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Cookie cookies = new Cookie("_npstore_browpro", "1,1e");
		request.setCookies(cookies);
		assertEquals(0, browserecordService.loadBrowerecord(request));
	}

	/**
	 * 从cook添加到测试 存在记录的场景
	 */
	@Test
	public void testLoadBrowerecord2() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("goodsId", 1);
		paramMap.put("customerId", null);
		MockHttpServletRequest request = new MockHttpServletRequest();
		Cookie cookies = new Cookie("_npstore_browpro", "1,1e");
		request.setCookies(cookies);
		browserecordMapperMock.returns(new Browserecord()).selectByBrowereId(
				paramMap);
		assertEquals(0, browserecordService.loadBrowerecord(request));
	}
}
