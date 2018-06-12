package com.junit.test.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.m.customer.mapper.CustomerAddressMapper;
import com.ningpai.m.customer.service.CustomerAddressService;
import com.ningpai.m.customer.service.impl.CustomerAddressServiceImpl;

/**
 * 收货地址测试
 * 
 * @author djk
 *
 */
public class CustomerAddressServiceTest extends UnitilsJUnit3 {

	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private CustomerAddressService customerAddressService = new CustomerAddressServiceImpl();

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerAddressMapper> customerAddressMapperMock;

	/**
	 * 根据会员编号查询会员收货地址测试
	 */
	@Test
	public void testQueryCustomerAddress() {
		customerAddressMapperMock.returns(new ArrayList<>())
				.queryCustomerAddress(1L);
		assertEquals(0, customerAddressService.queryCustomerAddress(1L).size());
	}

	/**
	 * 根据会员编号和地址编号查找会员收货地址测试
	 */
	@Test
	public void testQueryCustomerAddressById() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("addressId", 1L);
		paramMap.put("customerId", 1L);
		customerAddressMapperMock.returns(new CustomerAddress())
				.queryCustomerAddressById(paramMap);
		assertNotNull(customerAddressService.queryCustomerAddressById(1L, 1L));
	}

	/**
	 * 根据会员id查询默认地址测试
	 */
	@Test
	public void testQueryDefaultAddr() {
		customerAddressMapperMock.returns(new CustomerAddress())
				.selectDefaultAddr(1L);
		assertNotNull(customerAddressService.queryDefaultAddr(1L));
	}

	/**
	 * 修改收货地址测试
	 */
	@Test
	public void testUpdateAddress() {
		customerAddressMapperMock.returns(1).updateAddress(
				new CustomerAddress());
		assertEquals(1,
				customerAddressService.updateAddress(new CustomerAddress(), 1L));
	}

	/**
	 * 修改用户的地址为 0 默认测试
	 */
	@Test
	public void testUpdateAddressDef() {
		customerAddressMapperMock.returns(1).updateAddressDef(1l);
		assertEquals(1, customerAddressService.updateAddressDef(1L));
	}

	/**
	 * 添加收货地址测试
	 */
	@Test
	public void testAddAddress() {
		customerAddressMapperMock.returns(1).addAddress(new CustomerAddress());
		assertEquals(1,
				customerAddressService.addAddress(new CustomerAddress(), 1L));
	}

	/**
	 * 根据会员id查询上次选中的收货地址测试
	 */
	@Test
	public void testSelectByCIdFirst() {
		customerAddressMapperMock.returns(new CustomerAddress())
				.selectByCIdFirst(1L);
		assertNotNull(customerAddressService.selectByCIdFirst(1L));
	}
}
