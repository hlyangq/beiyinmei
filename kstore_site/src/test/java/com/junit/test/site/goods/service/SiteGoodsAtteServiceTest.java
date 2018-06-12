package com.junit.test.site.goods.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.site.customer.mapper.CustomerFollowMapper;
import com.ningpai.site.goods.bean.SiteGoodsAtte;
import com.ningpai.site.goods.dao.SiteGoodsAtteMapper;
import com.ningpai.site.goods.service.SiteGoodsAtteService;
import com.ningpai.site.goods.service.impl.SiteGoodsAtteServiceImpl;

/**
 * 商品关注测试
 * @author djk
 *
 */
public class SiteGoodsAtteServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private SiteGoodsAtteService siteGoodsAtteService = new SiteGoodsAtteServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<SiteGoodsAtteMapper> siteGoodsAtteMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerFollowMapper> customerFollowMapperMock;
    
    /**
     * 保存商品关注信息测试
     */
    @Test
    public void testSaveGoodsAtte()
    {
    	Map<String, Long> map = new HashMap<String, Long>();	
        map.put("custId", 1L);
        map.put("productId", 1L);
        siteGoodsAtteMapperMock.returns(0).queryAtteHistByCustIdAndProId(map);
        siteGoodsAtteMapperMock.returns(1).saveGoodsAtte(new SiteGoodsAtte());
        assertEquals(1, siteGoodsAtteService.saveGoodsAtte(1L, 1L, 1L, new BigDecimal(1)));
    }
    
    /**
     * 新关注商品流程测试
     */
    @Test
    public void testNewsaveGoodsAtte()
    {
    	Map<String, Object> map = new HashMap<>();
        map.put("custId", 1L);
        map.put("productId", 1L);
        customerFollowMapperMock.returns(null).queryByCustIdAndProId(map);
        siteGoodsAtteMapperMock.returns(1).saveGoodsAtte(new SiteGoodsAtte());
    	assertEquals(1, siteGoodsAtteService.newsaveGoodsAtte(1L, 1L, 1L, new BigDecimal(1)));
    }
    
    /**
     * 查询商品是否已经被关注测试
     */
    @Test
    public void testCheckAtte()
    {
    	Map<String, Long> map = new HashMap<String, Long>();
    	map.put("custId", 1L);
        map.put("productId", 1L);
        siteGoodsAtteMapperMock.returns(1).queryAtteHistByCustIdAndProId(map);        
    	assertEquals(true, siteGoodsAtteService.checkAtte(1L, 1L));
    }
}
