package com.junit.test.customer.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.m.common.dao.SmsMapper;
import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.service.impl.CustomerServiceImpl;
import com.ningpai.m.customer.vo.CustomerAllInfo;
import com.ningpai.m.customer.vo.OrderInfoBean;
import com.ningpai.util.PageBean;

/**
 * 手机端会员Service测试
 * 
 * @author djk
 *
 */
public class CustomerServiceTest extends UnitilsJUnit3 {
	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private CustomerService customerService = new CustomerServiceImpl();

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerMapper> customerMapperMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<SmsMapper> smsMapperMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;

	/**
	 * 查询会员基本信息测试
	 */
	@Test
	public void testSelectByPrimaryKey() {
		customerMapperMock.returns(new CustomerAllInfo())
				.selectByPrimaryKey(1L);
		assertNotNull(customerService.selectByPrimaryKey(1L));
	}

	/**
	 * 发送手机验证码测试
	 */
	@Test
	public void testSendPost() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertEquals(0, customerService.sendPost(request, "15195812239"));
	}

	/**
	 * 验证手机验证码测试
	 */
	@Test
	public void testGetMCode() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(new MockHttpSession());
		request.getSession().setAttribute("mcCode", 1);
		assertEquals(1, customerService.getMCode(request, "1"));
	}

	/**
	 * 修改密码测试
	 */
	@Test
	public void testupdateCusomerPwd() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(new MockHttpSession());
		request.getSession().setAttribute("userMobile", "1");
		customerMapperMock.returns(1).updateCusomerPwd(new CustomerAllInfo());
		assertEquals(1, customerService.updateCusomerPwd(request, "1"));
	}

	/**
	 * 修改会员测试
	 */
	@Test
	public void testUpdateCustomer() {
		customerMapperMock.returns(1).updateByPrimaryKeySelective(
				new Customer());
		assertEquals(1, customerService.updateCustomer(new Customer()));
	}

	/**
	 * 修改会员测试
	 */
	@Test
	public void testUpdateCustomerInfo() {
		customerMapperMock.returns(1).updateCustomerInfo(new CustomerAllInfo());
		assertEquals(1,
				customerService.updateCustomerInfo(new CustomerAllInfo()));
	}

	/**
	 * 根据会员编号查询相应的会员积分明细测试
	 */
	@Test
	public void testSelectAllCustomerPoint() {
		PageBean pb = new PageBean();
		assertEquals(0, customerService.selectAllCustomerPoint(1L, pb, 1L, "1")
				.getList().size());
	}

	/**
	 * 修改密码测试
	 */
	@Test
	public void testModifyPassword() {
		CustomerAllInfo customerInfo = new CustomerAllInfo();
		customerInfo.setCustomerPassword("de682cc3e40a84e86565ccb88ed7c0b0");
		customerInfo.setUniqueCode("1");
		customerInfo.setSaltVal("1");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(new MockHttpSession());
		customerMapperMock.returns(customerInfo).selectByPrimaryKey(null);
		assertEquals(0, customerService.modifyPassword(request,
				new MockHttpServletResponse(), "1", "1"));
	}

	/**
	 * 根据会员编号查询相应的会员折扣测试
	 */
	@Test
	public void testQueryCustomerPointDiscountByCustId() {
		customerMapperMock.returns(new BigDecimal(1))
				.queryCustomerPointDiscountByCustId(1L);
		assertNotNull(customerService.queryCustomerPointDiscountByCustId(1L));
	}

	/**
	 * 根据订单编号查找订单信息测试
	 */
	@Test
	public void testQueryOrderByCustIdAndOrderId() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerId", 1L);
		paramMap.put("orderId", 1L);
		customerMapperMock.returns(new OrderInfoBean()).queryOrderByParamMap(
				paramMap);
		assertNotNull(customerService.queryOrderByCustIdAndOrderId(1L, 1L));
	}

    /**
     * 安全验证身份
     */
    @Test
    public void testcheckIdentity() {
        CustomerAllInfo customerInfo = new CustomerAllInfo();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("PATCHCA", "1562");
        request.getSession().setAttribute("customerId", 1L);
        customerMapperMock.returns(customerInfo).selectByPrimaryKey(1L);
        assertNotNull(customerService.checkIdentity(request, "1562", "de682cc3e40a84e86565ccb88ed7c0b0"));
    }
}
