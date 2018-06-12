package com.junit.test.login.service;

import com.ningpai.customer.dao.CustomerAddressMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.customer.service.BrowserecordService;
import com.ningpai.m.login.service.LoginService;
import com.ningpai.m.login.service.impl.LoginServiceImpl;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wtp on 2016/3/30.
 */
public class LoginServiceTest extends UnitilsJUnit3 {
    /**
     * 需要测试的接口类
     */
    @TestedObject
    private LoginService loginService = new LoginServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerMapper> customerMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerAddressMapper> customerAddressMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<ShoppingCartService> shoppingCartServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<BrowserecordService> browserecordServiceMock;

    /**
     * 验证用户
     */
    @Test
    public void testCheckCustomerExists() throws UnsupportedEncodingException {
        Customer customerN = new Customer();
        customerN.setCustomerUsername("djk");
        customerN.setIsFlag("0");
        customerN.setLoginTime(new Date());
        customerN.setCustomerPassword("df3df118d1fe024c3448d755eaf49946");
        customerN.setUniqueCode("1");
        customerN.setSaltVal("2a682475eb3d301e46a5d617982f27827ffffeac0bf9f7ee183f021d2ba9bf91");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uType", "username");
        paramMap.put("username", "djk");
        customerMapperMock.returns(customerN).selectCustomerByCustNameAndType(paramMap);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.getSession().setAttribute("PATCHCA", "123");
        assertEquals(1, loginService.checkCustomerExists(request, response, "djk", "qqq111", "123"));
    }


}
