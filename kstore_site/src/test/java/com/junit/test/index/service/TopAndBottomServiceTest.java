package com.junit.test.index.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.ningpai.system.mobile.service.MobSiteBasicService;
import com.ningpai.system.service.*;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.ModelAndView;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.channel.service.ChannelAdverService;
import com.ningpai.channel.service.ChannelBarService;
import com.ningpai.channel.service.ChannelGoodsService;
import com.ningpai.channel.service.ChannelTrademarkService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.hotsearch.service.HotSearchService;
import com.ningpai.index.service.IndexSiteService;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.information.service.InformationService;
import com.ningpai.redis.RedisAdapter;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.system.bean.BasicSet;
import com.ningpai.system.bean.OnLineService;
import com.ningpai.temp.bean.SysTemp;
import com.ningpai.temp.bean.TempToken;
import com.ningpai.temp.service.TempService;
import com.ningpai.temp.service.TempTokenService;

/**
 *  SERVICE-获取头部、底部信息测试
 * @author djk
 *
 */
public class TopAndBottomServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
	@TestedObject
	private TopAndBottomService topAndBottomService = new TopAndBottomService();
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<BasicSetService> basicSetServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ISeoConfBiz> iSeoConfBizMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<TempService> tempServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<IndexSiteService> indexSiteServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ChannelBarService> channelBarServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<HotSearchService> hotSearchServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<HelpCateService> helpCateServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<HelpCenterService> helpCenterServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ChannelAdverService> channelAdverServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<InformationService> informationServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ChannelGoodsService> channelGoodsServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<TempTokenService> tempTokenServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<CustomerServiceInterface> customerServiceInterfaceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<IOnLineServiceItemBiz> iOnLineServiceItemBizMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<IOnLineServiceBiz> iOnLineServiceBizMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<CouponService> couponServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ChannelTrademarkService> channelTrademarkServiceMock;
    
	/**
     * 模拟
     */
    @InjectIntoByType
    private Mock<RedisAdapter> redisAdapterMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<MobSiteBasicService> mobSiteBasicServiceMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<AuthService> authServiceMock;
    
    private MockHttpServletRequest request = new MockHttpServletRequest();
    @Override
    protected void setUp() throws Exception 
    {
    	MockHttpSession session = new MockHttpSession();
    	request.setSession(session);
    	topAndBottomService.setRequest(request);
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为8的情况
     * token 校验失败的情况
     */
    @Test
    public void testGetTopAndBottomWithTempId8()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(8);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为8的情况
     * token 校验成功的场景
     */
    @Test
    public void testGetTopAndBottomWithTempId81()
    {
    	createGetTopAndBottomTokenOk(8);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为9的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId9()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(9);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为9的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId91()
    {
    	createGetTopAndBottomTokenOk(9);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为10的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId10()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(10);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为10的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId101()
    {
    	createGetTopAndBottomTokenOk(10);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    
    /**
     * 新加载头部和底部信息测试 temp 为11的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId11()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(11);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为11的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId111()
    {
    	createGetTopAndBottomTokenOk(11);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为12的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId12()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(12);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为12的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId121()
    {
    	createGetTopAndBottomTokenOk(12);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为13的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId13()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(13);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为13的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId131()
    {
    	createGetTopAndBottomTokenOk(13);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    
    /**
     * 新加载头部和底部信息测试 temp 为14的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId14()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(14);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为14的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId141()
    {
    	createGetTopAndBottomTokenOk(14);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    
    /**
     * 新加载头部和底部信息测试 temp 为15的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId15()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(15);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为15的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId151()
    {
    	createGetTopAndBottomTokenOk(15);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    
    /**
     * 新加载头部和底部信息测试 temp 为16的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId16()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(16);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为16的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId161()
    {
    	createGetTopAndBottomTokenOk(16);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    
    /**
     * 新加载头部和底部信息测试 temp 为17的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId17()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(17);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为17的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId171()
    {
    	createGetTopAndBottomTokenOk(17);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为18的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId18()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(18);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为18的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId181()
    {
    	createGetTopAndBottomTokenOk(18);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载头部和底部信息测试 temp 为Other的情况
     * token 校验失败的情况
     */
    public void testGetTopAndBottomWithTempId1Other()
    {
    	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(0);
    	createHtml();
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    /**
     * 新加载头部和底部信息测试 temp 为Other的情况
     * token 校验成功的情况
     */
    public void testGetTopAndBottomWithTempId1Other1()
    {
    	createGetTopAndBottomTokenOk(0);
    	assertNotNull(topAndBottomService.getTopAndBottom(new ModelAndView()));
    }
    
    /**
     * 新加载底部信息 temp 为19的情况 
     */
    public void testgetBottom19()
    {
      	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(19);
    	createHtml();
    	assertNotNull(topAndBottomService.getBottom(new ModelAndView()));
    }
    
    /**
     * 新加载底部信息 temp 为19的情况 
     * token 校验成功的情况
     */
    public void testgetBottom191()
    {
    	createGetTopAndBottomTokenOk(19);
    	assertNotNull(topAndBottomService.getBottom(new ModelAndView()));
    }
    
    
    /**
     * 新加载底部信息 temp 为20的情况 
     */
    public void testgetBottom20()
    {
      	request.getSession().getServletContext().setAttribute("token", "");
    	createSysTemp(20);
    	createHtml();
    	assertNotNull(topAndBottomService.getBottom(new ModelAndView()));
    }
    
    /**
     * 新加载底部信息 temp 为20的情况 
     * token 校验成功的情况
     */
    public void testgetBottom201()
    {
    	createGetTopAndBottomTokenOk(20);
    	assertNotNull(topAndBottomService.getBottom(new ModelAndView()));
    }
    /**
     * 校验token session中有token的情况
     */
    @Test
    public void testCheckToken()
    {
    	TempToken tempToken = new TempToken();
    	tempToken.setToken("12");
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	ServletContext servletContext = new MockServletContext();
    	servletContext.setAttribute("token", "1");
    	HttpSession session = new MockHttpSession(servletContext);
    	request.setSession(session);
    	tempTokenServiceMock.returns(tempToken).selectTokenByType("1");
    	assertEquals(false, topAndBottomService.checkToken(request));
    }
    
    /**
     *校验token session中没有token的情况 并且数据库里面也没有存token
     */
    @Test
    public void testCheckToken2()
    {
    	assertEquals(false, topAndBottomService.checkToken(new MockHttpServletRequest()));
    }
    
    
    /**
     *校验token session中没有token的情况 并且数据库里面有存token
     */
    @Test
    public void testCheckToken3()
    {
    	TempToken tempToken = new TempToken();
    	tempToken.setToken("12");
    	tempTokenServiceMock.returns(tempToken).selectTokenByType("1");
    	assertEquals(false, topAndBottomService.checkToken(new MockHttpServletRequest()));
    }
    
    /**
     * 新加载简易头部和尾部信息测试
     */
    @Test
    public void testGetSimpleTopAndBottom()
    {
    	ModelAndView mav = new ModelAndView();
    	assertNotNull(topAndBottomService.getSimpleTopAndBottom(mav));
    }
    
    /**
     * 生成静态页面的公共逻辑
     */
    private void createHtml()
    {
    	OnLineService onLineService = new OnLineService();
    	onLineService.setTitle("a");
    	onLineService.setOnlineIndex("a");
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("pendingNum", 1);
    	map.put("noReadNum", 1);
    	map.put("reduceNum", 1);
    	BasicSet basicSet = new BasicSet();
    	basicSet.setBsetAddress("a");
    	basicSet.setBsetLogo("a");
    	ChannelAdver channelAdver = new ChannelAdver();
    	channelAdver.setAdverHref("a");
    	channelAdver.setAdverPath("a");
    	List<ChannelAdver> pageAdvList = new ArrayList<>();
    	pageAdvList.add(channelAdver);
    	channelAdverServiceMock.returns(pageAdvList).selectchannelAdverByParamForSite(null, 8L, null, null,161L, 151L, null, "0", null,
                "0");
    	basicSetServiceMock.returns(basicSet).findBasicSet();
    	customerServiceInterfaceMock.returns(map).selectNotice(null);
    	channelAdverServiceMock.returns(pageAdvList).selectchannelAdverByParamForSite(null, 8L, null, null, 161L, 151L, null, "0", null, "0");
    	iOnLineServiceBizMock.returns(onLineService).selectSetting();
    	
    }

    /**
     * 构造验证获取头部和底部  检验token成功的场景数据
     */
    private void createGetTopAndBottomTokenOk(int tempId)
    {
    	createSysTemp(tempId);
    	TempToken tempToken = new TempToken();
    	tempToken.setToken("1");
    	request.getSession().getServletContext().setAttribute("token", "1");
    	tempTokenServiceMock.returns(tempToken).selectTokenByType("1");
    }
    
    /**
     * 构造创建SysTemp对象的场景数据
     * @param tempId
     */
    private void createSysTemp(int tempId)
    {
     	SysTemp sysTemp = new SysTemp();
    	sysTemp.setTempId(tempId);
    	sysTemp.setExpFleid1("1");
    	tempServiceMock.returns(sysTemp).getCurrTemp();
    }
}
