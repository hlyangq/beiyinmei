package com.junit.test.goods.service;

import com.ningpai.m.customer.bean.CustomerFollow;
import com.ningpai.m.customer.mapper.CustomerFollowMapper;
import com.ningpai.m.customer.vo.CustomerAllInfo;
import com.ningpai.m.goods.bean.SiteGoodsAtte;
import com.ningpai.m.goods.dao.SiteGoodsAtteMapper;
import com.ningpai.m.goods.service.impl.SiteGoodsAtteServiceImpl;
import com.ningpai.m.goods.service.SiteGoodsAtteService;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wtp on 2016/3/23.
 */
public class SiteGoodsAtteServiceTset extends UnitilsJUnit3 {
    /**
     * 需要测试的接口类
     */
    @TestedObject
    private SiteGoodsAtteService siteGoodsAtteService = new SiteGoodsAtteServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerFollowMapper> customerFollowMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<SiteGoodsAtteMapper> goodsAtteMapperMock;

    /**
     * 查保存商品关注信息
     */
    @Test
    public void testSaveGoodsAtte() {
        SiteGoodsAtte siteGoodsAtte = new SiteGoodsAtte();
        siteGoodsAtte.setCustId(1L);
        siteGoodsAtte.setProductId(1L);
        goodsAtteMapperMock.returns(1).saveGoodsAtte(siteGoodsAtte);
        assertEquals(1,siteGoodsAtteService.saveGoodsAtte(1L,1L));
    }

    /**
     * 查保存商品关注信息
     */
    @Test
    public void testNewsaveGoodsAtte() {
        Map<String, Object> map = new HashMap<>();
        map.put("custId", 1L);
        map.put("productId", 1L);
        CustomerFollow customerFollow = new CustomerFollow();
        customerFollowMapperMock.returns(customerFollow).queryByCustIdAndProId(map);
        SiteGoodsAtte siteGoodsAtte = new SiteGoodsAtte();
        goodsAtteMapperMock.returns(1).saveGoodsAtte(siteGoodsAtte);
        map.put("customerId", 1L);
        map.put("followId", 1L);
        customerFollowMapperMock.returns(1).deleteByPrimaryKey(map);
        assertEquals(-1, siteGoodsAtteService.newsaveGoodsAtte(1L, 1L, 1L, new BigDecimal(1)));
    }

    /**
     * 查保存商品关注信息
     */
    @Test
    public void testCheckAtte() {
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("custId", 1L);
        map.put("productId", 1L);
        goodsAtteMapperMock.returns(1).queryAtteHistByCustIdAndProId(map);
        assertEquals(true,siteGoodsAtteService.checkAtte(1L, 1L));
    }

    /**
     * 查保存商品关注信息
     */
    @Test
    public void testAddGoodsCollection() {
        Map<String, Object> map = new HashMap<>();
        map.put("custId", 1L);
        map.put("productId", 1L);
        customerFollowMapperMock.returns(1).deleteShoppingCart(map);
        CustomerFollow customerFollow = new CustomerFollow();
        customerFollowMapperMock.returns(customerFollow).queryByCustIdAndProId(map);
        SiteGoodsAtte siteGoodsAtte = new SiteGoodsAtte();
        goodsAtteMapperMock.returns(1).saveGoodsAtte(siteGoodsAtte);
        assertEquals(1,siteGoodsAtteService.addGoodsCollection(1L, 1L, 1L, new BigDecimal(1)));
    }
}
