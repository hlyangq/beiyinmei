package com.junit.test.site.marketingrush.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.goods.bean.Goods;
import com.ningpai.goods.bean.GoodsBrand;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.bean.MarketingRush;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.OrderVice;
import com.ningpai.order.service.OrderViceService;
import com.ningpai.other.bean.CityBean;
import com.ningpai.other.bean.DistrictBean;
import com.ningpai.other.bean.ProvinceBean;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.goods.bean.GoodsDetailBean;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.site.goods.vo.GoodsProductVo;
import com.ningpai.site.marketingrush.service.MarketingRushService;
import com.ningpai.site.marketingrush.service.impl.MarketingRushServiceImpl;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.service.PayService;

/**
 *  抢购功能接口测试
 * @author djk
 *
 */
public class MarketingRushServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private MarketingRushService marketingRushService = new MarketingRushServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<PayService> payServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderViceService> orderViceServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductService> goodsProductServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<MarketingService> marketingServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerServiceInterface> customerServiceInterfaceMock;
    
    /**
     * 抢购订单提交测试
     */
    @Test
    public void testSubMarketingRushOrder()
    {
    	List<MarketingRush> rushs = new ArrayList<>();
    	
    	CustomerAddress customerAddress = new CustomerAddress();
    	customerAddress.setProvince(new ProvinceBean());
    	customerAddress.setCity(new CityBean());
    	customerAddress.setDistrict(new DistrictBean());
    	MarketingRush marketingRush = new MarketingRush();
    	marketingRush.setRushDiscount(new BigDecimal(1));
    	GoodsBrand goodsBrand = new GoodsBrand();
    	goodsBrand.setBrandId(1L);
    	GoodsDetailBean detailBean = new GoodsDetailBean();
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoods(goods);
    	goodsProductVo.setGoodsInfoStock(1L);
    	goodsProductVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	detailBean.setProductVo(goodsProductVo);
    	detailBean.setBrand(goodsBrand);
    	Marketing marketing = new Marketing();
    	rushs.add(marketingRush);
    	marketing.setRushs(rushs);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.getSession().setAttribute("distinctId", "1");
    	goodsProductServiceMock.returns(detailBean).queryDetailBeanByProductId(1L, 1L);
    	marketingServiceMock.returns(marketing).selectRushMarket(1L, 5L, 1L, 1L);
    	customerServiceInterfaceMock.returns(customerAddress).queryCustAddress(1L);
    	assertNotNull(marketingRushService.subMarketingRushOrder(request,1L, "1", "1", "1", 1L, 1L));
    }
    
    /**
     * 获取支付宝付款请求测试
     */
    @Test
    public void testPayMarketingRushOrder()
    {
    	OrderVice order  = new OrderVice();
    	order.setOrderPrice(new BigDecimal(1));
    	order.setGoodsInfoId(1L);
    	Pay pay = new Pay();
    	pay.setPayType("1");
    	payServiceMock.returns(pay).findByPayId(1L);
    	orderViceServiceMock.returns(order).payOrder("1");
    	goodsProductServiceMock.returns(new GoodsProductVo()).queryProductByProductId(1L);
    	assertNotNull(marketingRushService.payMarketingRushOrder("1", 1L));
    }
}
