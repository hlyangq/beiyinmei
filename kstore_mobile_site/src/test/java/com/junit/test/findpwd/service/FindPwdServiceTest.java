package com.junit.test.findpwd.service;

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

import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.findpwd.service.FindPwdService;
import com.ningpai.m.findpwd.service.impl.FindPwdServiceImpl;

/**
 * 手机端忘记密码Service测试
 * 
 * @author djk
 *
 */
public class FindPwdServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private FindPwdService findPwdService = new FindPwdServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerMapper> customerMapperMock;

    /**
     * 修改密码测试 手机号码的场景
     */
    @Test
    public void testForGetPwd() {
        Customer customerN = new Customer();
        customerN.setUniqueCode("1");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("mcCode", 1);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uType", "mobile");
        paramMap.put("username", "15195812239");
        customerMapperMock.returns(customerN).selectCustomerByCustNameAndType(paramMap);

        assertNotNull(findPwdService.forGetPwd(request,response, "1", "15195812239", "1"));

    }

    /**
     * 修改密码测试 邮箱的场景
     */
    @Test
    public void testForGetPwd2() {
        Customer customerN = new Customer();
        customerN.setUniqueCode("1");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("mcCode", 1);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uType", "email");
        paramMap.put("username", "15195812239@qq");
        customerMapperMock.returns(customerN).selectCustomerByCustNameAndType(paramMap);

        assertNotNull(findPwdService.forGetPwd(request,response, "1", "15195812239@qq", "1"));

    }

    /**
     * 修改密码测试 用户名的场景
     */
    @Test
    public void testForGetPwd3() {
        Customer customerN = new Customer();
        customerN.setUniqueCode("1");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("mcCode", 1);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uType", "username");
        paramMap.put("username", "djk");
        customerMapperMock.returns(customerN).selectCustomerByCustNameAndType(paramMap);
        assertNotNull(findPwdService.forGetPwd(request,response, "1", "djk", "1"));

    }

    /**
     * 验证用户测试 普通用户的场景
     */
    @Test
    public void testCheckCustomerExists() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uType", "username");
        paramMap.put("username", "djk");
        customerMapperMock.returns(new Customer()).selectCustomerByNamePwdAndType(paramMap);
        MockHttpServletRequest request = new MockHttpServletRequest();
        try {
            assertEquals(1, findPwdService.checkCustomerExists(request, new MockHttpServletResponse(), "djk"));
        } catch (Exception e) {
        }
    }

    /**
     * 验证用户测试 手机号码的场景
     */
    @Test
    public void testCheckCustomerExists2() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uType", "mobile");
        paramMap.put("username", "15195812239");
        customerMapperMock.returns(new Customer()).selectCustomerByNamePwdAndType(paramMap);
        MockHttpServletRequest request = new MockHttpServletRequest();
        try {
            assertEquals(1, findPwdService.checkCustomerExists(request, new MockHttpServletResponse(), "15195812239"));
        } catch (Exception e) {
        }
    }

    /**
     * 验证用户测试 邮箱的场景
     */
    @Test
    public void testCheckCustomerExists3() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uType", "email");
        paramMap.put("username", "15195812239@qq");
        customerMapperMock.returns(new Customer()).selectCustomerByNamePwdAndType(paramMap);
        MockHttpServletRequest request = new MockHttpServletRequest();
        try {
            assertEquals(1,
                    findPwdService.checkCustomerExists(request, new MockHttpServletResponse(), "15195812239@qq"));
        } catch (Exception e) {
        }
    }
}
