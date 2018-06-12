package com.junit.test.directshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.m.directshop.bean.DirectShop;
import com.ningpai.m.directshop.dao.DirectshopMapper;
import com.ningpai.m.directshop.service.DirectShopService;
import com.ningpai.m.directshop.service.impl.DirectShopServiceImpl;

/**
 * 直营店Service接口测试 测试a
 * 
 * @author djk
 *
 */
public class DirectShopServiceTest extends UnitilsJUnit3 {
	
	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private DirectShopService directShopService = new DirectShopServiceImpl();
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<DirectshopMapper> directshopMapperMock;
	
	/**
	 * 根据直营店ID查询直营店信息 测试
	 */
	@Test
	public void testSelectInfoById()
	{
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("directShopStatus", "1");
	    paramMap.put("directShopId", 1L);
	    directshopMapperMock.returns(new DirectShop()).selectInfoById(paramMap);
		assertNotNull(directShopService.selectInfoById(1L));
	}
	
	/**
	 * 根据区县id查询直营店列表测试
	 */
	@Test
	public void testQueryDirectShopList()
	{
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("directShopStatus", "1");
	    paramMap.put("countyId", 1L);
	    directshopMapperMock.returns(new ArrayList<>()).directShops(paramMap);
		assertEquals(0, directShopService.queryDirectShopList(1L).size());
	}
}
