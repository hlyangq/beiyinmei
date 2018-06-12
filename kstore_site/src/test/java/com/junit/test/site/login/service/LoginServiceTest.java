package com.junit.test.site.login.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.dao.CustomerAddressMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.site.customer.mapper.CustomerMapper;
import com.ningpai.site.login.service.LoginService;
import com.ningpai.site.login.service.impl.LoginServiceImpl;
import com.ningpai.site.shoppingcart.service.ShoppingCartService;

/**
 * 登录Service测试
 * @author djk
 *
 */
public class LoginServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private LoginService loginService = new LoginServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerMapper> customerMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerAddressMapper> customerAddressMapperMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ShoppingCartService> shoppingCartServiceMock;
    
    /**
     * 验证用户测试
     */
    @Test
    public void testCheckCustomerExists()
    {
    	Customer customer = new Customer();
    	customer.setIsFlag("0");
    	customer.setLoginTime(new Date());
    	customer.setCustomerPassword("b93283a81cda5e1f5cb0f15797fd1f89");
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("uType", "username");
    	paramMap.put("username", "djk");
    	customerMapperMock.returns(customer).selectCustomerByCustNameAndType(paramMap);
    	paramMap.put("password", "123");
    	customerMapperMock.returns(customer).selectCustomerByNamePwdAndType(paramMap);

    	MockHttpServletRequest request = new MockHttpServletRequest();

    	assertEquals(2, loginService.checkCustomerExists(request, "djk", "123"));
    }
}
