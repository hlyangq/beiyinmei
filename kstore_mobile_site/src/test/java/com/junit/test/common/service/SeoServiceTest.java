package com.junit.test.common.service;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.common.service.impl.SeoServiceImpl;
import com.ningpai.system.bean.BasicSet;
import com.ningpai.system.mobile.service.MobSiteBasicService;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.system.service.ISeoConfBiz;

/**
 * SEO接口测试
 * 
 * @author djk
 *
 */
public class SeoServiceTest extends UnitilsJUnit3 {

	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private SeoService seoService = new SeoServiceImpl();

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<ISeoConfBiz> iSeoConfBizMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<BasicSetService> basicSetServiceMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<MobSiteBasicService> mobSiteBasicServiceMock;

	/**
	 * 获取当前的seo信息测试
	 */
	@Test
	public void testGetCurrSeo() {
		RequestAttributes requestAttributes = new ServletRequestAttributes(
				new MockHttpServletRequest());
		RequestContextHolder.setRequestAttributes(requestAttributes);
		BasicSet basicSet = new BasicSet();
		basicSet.setBsetName("a");
		basicSetServiceMock.returns(basicSet).findBasicSet();
		assertNotNull(seoService.getCurrSeo(new ModelAndView()));
	}
}
