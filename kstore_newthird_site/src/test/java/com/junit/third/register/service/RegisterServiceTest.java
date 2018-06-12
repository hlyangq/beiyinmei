package com.junit.third.register.service;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.third.register.mapper.RegisterMapper;
import com.ningpai.third.register.service.RegisterService;
import com.ningpai.third.register.service.impl.RegisterServiceImpl;

/**
 * 商家注册 获取手机验证码测试
 * @author djk
 *
 */
public class RegisterServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private RegisterService registerService = new RegisterServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<RegisterMapper> registerMapperMock;
    
    /**
     * 
     */
    @Test
    public void testSendPost()
    {
    	registerMapperMock.returns(null).selectSms();
    	assertEquals(0, registerService.sendPost(new MockHttpServletRequest(), "15195812239"));
    }
    
    /**
     *  新发送手机验证码测试
     */
    @Test
    public void testNewsendPost()
    {
    	registerMapperMock.returns(null).selectSms();
    	assertEquals(0, registerService.newsendPost(new MockHttpServletRequest(), "15195812239"));
    }
}
