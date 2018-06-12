package com.junit.test.site.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.site.customer.service.BrowserecordService;
import com.ningpai.site.goods.dao.GoodsProductMapper;
import com.ningpai.site.goods.service.BrowerService;
import com.ningpai.site.goods.service.impl.BrowerServiceImpl;
import com.ningpai.site.goods.vo.GoodsProductVo;


/**
 * 浏览记录Service测试
 * @author djk
 *
 */
public class BrowerServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private BrowerService browerService = new BrowerServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<BrowserecordService> browserecordServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductMapper> goodsProductMapperMock;
   
    /**
     * 模拟 request
     */
    private MockHttpServletRequest request = new MockHttpServletRequest();
    
    /**
     * 模拟response
     */
    private MockHttpServletResponse response = new MockHttpServletResponse();
    
    @Override
    protected void setUp() throws Exception 
    {
    	Cookie cookies = new Cookie("_npstore_browpro", "s2e,s1e");
    	request.setCookies(cookies);
    }
    /**
     * 保存浏览记录测试
     */
    @Test
    public void testSaveBrowerHis()
    {
    	request.getSession().setAttribute("customerId", null);
    	try 
    	{
    		assertEquals(false, browerService.saveBrowerHis(request, response, 1L));
		} catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    /**
     * 保存浏览记录测试
     * session中有用户信息的场景
     */
    @Test
    public void testSaveBrowerHis2()
    {
    	request.getSession().setAttribute("customerId", 1L);
    	try 
    	{
    		assertEquals(false, browerService.saveBrowerHis(request, response, 1L));
		} catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    /**
     * 获取浏览的历史记录测试
     */
    @Test
    public void testLoadBrowHist()
    {
    	request.getSession().setAttribute("customerId", null);
    	List<GoodsProductVo> proList = new ArrayList<>();
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoodsInfoId(1L);
    	proList.add(goodsProductVo);
    	List<Long> list = new ArrayList<>();
    	list.add(2L);
    	list.add(1L);
        Map<String, Object> proMap = new HashMap<String, Object>();
        proMap.put("productIds", list);
        
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("type", 2);
    	goodsProductMapperMock.returns(proList).queryProductsByProductIds(proMap);
    	assertEquals(2, browerService.loadBrowHist(request).size());
    }
    
    /**
     * 获取浏览的历史记录测试
     * session 中有用户信息的场景
     */
    @Test
    public void testLoadBrowHist2()
    {
    	request.getSession().setAttribute("customerId", 1L);
    	assertEquals(2, browerService.loadBrowHist(request).size());
    }
    
    
}
