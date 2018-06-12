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

import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.m.customer.bean.CustomerFollow;
import com.ningpai.m.customer.mapper.CustomerFollowMapper;
import com.ningpai.m.customer.service.CustomerFollowService;
import com.ningpai.m.customer.service.impl.CustomerFollowServiceImpl;
import com.ningpai.m.customer.vo.GoodsBean;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.util.PageBean;

/**
 * 收藏商品Service测试
 * 
 * @author djk
 */
public class CustomerFollowServiceTest extends UnitilsJUnit3 {

	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private CustomerFollowService customerFollowService = new CustomerFollowServiceImpl();

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerFollowMapper> customerFollowMapperMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<DefaultAddressService> defaultAddressServiceMock;

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
	 * 查询收藏记录测试 不是第三方的场景
	 */
	@Test
	public void testSelectCustomerFollow() {
		GoodsProductVo goodsProductVo = new GoodsProductVo();
		goodsProductVo.setIsThird("0");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startRowNum", 0);
		paramMap.put("endRowNum", 15);
		paramMap.put("customerId", 1L);
		CustomerFollow customerFollow = new CustomerFollow();
		customerFollow.setGoodsId(1L);
		customerFollow.setGood(new GoodsBean());
		List<Object> customerFollows = new ArrayList<>();
		customerFollows.add(customerFollow);
		PageBean pb = new PageBean();
		customerFollowMapperMock.returns(customerFollows)
				.selectCustFollowByCustId(paramMap);
		goodsProductServiceMock.returns(goodsProductVo)
				.queryProductByProductId(1L);
		productWareServiceMock.returns(new ProductWare()).queryProductWareByProductIdAndDistinctId(1L, 0L);
		assertEquals(1, customerFollowService.selectCustomerFollow(1L, pb).getList().size());
	}
	
	/**
	 * 查询收藏记录测试 是第三方的场景
	 */
	@Test
	public void testSelectCustomerFollow2() {
		GoodsProductVo goodsProductVo = new GoodsProductVo();
		goodsProductVo.setIsThird("1");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startRowNum", 0);
		paramMap.put("endRowNum", 15);
		paramMap.put("customerId", 1L);
		CustomerFollow customerFollow = new CustomerFollow();
		customerFollow.setGoodsId(1L);
		customerFollow.setGood(new GoodsBean());
		List<Object> customerFollows = new ArrayList<>();
		customerFollows.add(customerFollow);
		PageBean pb = new PageBean();
		customerFollowMapperMock.returns(customerFollows)
				.selectCustFollowByCustId(paramMap);
		goodsProductServiceMock.returns(goodsProductVo)
				.queryProductByProductId(1L);
		assertEquals(1, customerFollowService.selectCustomerFollow(1L, pb).getList().size());
	}

	/**
	 * 商品列表专用 查询当前会员是否测试
	 */
	@Test
	public void testSelectCustomerFollowForList() {
		customerFollowMapperMock.returns(new ArrayList<String>())
				.selectCustFollowByCustIdForList(new HashMap<String, Object>());
		assertEquals(
				0,
				customerFollowService.selectCustomerFollowForList(
						new HashMap<String, Object>()).size());
	}

	/**
	 * 取消关注测试
	 */
	@Test
	public void testDeleteFollow() {
		Map<String, Object> map = new HashMap<>();
		map.put("followId", 1L);
		map.put("customerId", 1L);
		customerFollowMapperMock.returns(1).deleteByPrimaryKey(map);
		assertEquals(1, customerFollowService.deleteFollow(1L, 1L));
	}

	/**
	 * 查询我的收藏数目测试
	 */
	@Test
	public void testMyCollectionsCount() {
		customerFollowMapperMock.returns(1L).selectCustomerFollowCount(1L);
		Long result = 1L;
		assertEquals(result, customerFollowService.myCollectionsCount(1L));
	}
}
