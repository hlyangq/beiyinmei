package com.junit.test.panicbuying.service;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.panicbuying.dao.PanicBuyingDao;
import com.ningpai.m.panicbuying.service.PanicBuyingService;
import com.ningpai.m.panicbuying.service.impl.PanicBuyingServiceImpl;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.system.service.DefaultAddressService;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wtp on 2016/3/30.
 */
public class PanicBuyingServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private PanicBuyingService panicBuyingService = new PanicBuyingServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<DefaultAddressService> addressServiceMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<PanicBuyingDao> panicBuyingDaoMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductService> productServiceMock;

    /**
     * 查询 抢购信息
     */
    @Test
    public void testQueryMarketingRushUtils() {
        List<GoodsProductVo> goodsProductVo=new ArrayList<GoodsProductVo>();
        panicBuyingDaoMock.returns(goodsProductVo).queryGoodsProductVos();
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertNotNull(panicBuyingService.queryMarketingRushUtils(request));
    }

    /**
     * 查询 抢购信息
     */
    @Test
    public void testQueryChannelAdvers() {
        List<ChannelAdver> channelAdver=new ArrayList<ChannelAdver>();
        panicBuyingDaoMock.returns(channelAdver).queryChannelAdvers();
        assertNotNull(panicBuyingService.queryChannelAdvers());
    }
}
