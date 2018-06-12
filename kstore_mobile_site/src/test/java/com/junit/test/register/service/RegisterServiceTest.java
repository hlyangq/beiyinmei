package com.junit.test.register.service;

import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.regsiter.controller.service.RegisterService;
import com.ningpai.m.regsiter.controller.service.impl.RegisterServiceImpl;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wtp on 2016/3/30.
 */
public class RegisterServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private RegisterService registerService = new RegisterServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerMapper> customerMapperMock;

    /**
     * 验证用户
     */
    @Test
    public void testCheckCustomerExists() throws UnsupportedEncodingException {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uType", "username");
        paramMap.put("username", "djk");
        customerMapperMock.returns(new Customer()).selectCustomerByNamePwdAndType(paramMap);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertEquals(1, registerService.checkCustomerExists(request, response, "djk", "123"));
    }
}
