package com.junit.test.site.groupon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.goods.bean.Goods;
import com.ningpai.goods.bean.GoodsBrand;
import com.ningpai.marketing.bean.Groupon;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.dao.GrouponMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.OrderVice;
import com.ningpai.order.dao.OrderViceMapper;
import com.ningpai.order.service.OrderViceService;
import com.ningpai.other.bean.CityBean;
import com.ningpai.other.bean.DistrictBean;
import com.ningpai.other.bean.ProvinceBean;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.goods.bean.GoodsDetailBean;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.site.goods.vo.GoodsProductVo;
import com.ningpai.site.groupon.service.GrouponService;
import com.ningpai.site.groupon.service.impl.GrouponServiceImpl;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.service.PayService;
import com.ningpai.util.PageBean;

/**
 *  团购功能接口测试
 * @author djk
 *
 */
public class GrouponServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private GrouponService  grouponService = new GrouponServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GrouponMapper> grouponMapperMock;
    
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
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderViceMapper> orderViceMapperMock;
    
    /**
     * 团购订单提交测试
     */
    @Test
    public void testSubGrouponOrder()
    {
    	CustomerAddress customerAddress = new CustomerAddress();
    	customerAddress.setProvince(new ProvinceBean());
    	customerAddress.setCity(new CityBean());
    	customerAddress.setDistrict(new DistrictBean());
    	Groupon groupon = new Groupon();
    	groupon.setGrouponDiscount(new BigDecimal(1));
    	Marketing marketing = new Marketing();
    	marketing.setGroupon(groupon);
    	GoodsBrand goodsBrand = new GoodsBrand();
    	goodsBrand.setBrandId(1L);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoods(goods);
    	goodsProductVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	goodsProductVo.setGoodsInfoStock(1L);
    	GoodsDetailBean detailBean = new GoodsDetailBean();
    	detailBean.setProductVo(goodsProductVo);
    	detailBean.setBrand(goodsBrand);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.getSession().setAttribute("distinctId", "1");
    	goodsProductServiceMock.returns(detailBean).queryDetailBeanByProductId(1L, 1L);
    	marketingServiceMock.returns(marketing).selectGrouponMarket(1L, 4L, 1L, 1L);
    	customerServiceInterfaceMock.returns(customerAddress).queryCustAddress(1L);
    	assertNotNull(grouponService.subGrouponOrder(request, 1L, "1", "1", "1", 1L, 1L));
    }
    
    /**
     * 获取支付宝付款请求测试
     */
    @Test
    public void testPayGrouponOrder()
    {
    	OrderVice order  = new OrderVice();
    	order.setOrderPrice(new BigDecimal(1));
    	order.setGoodsInfoId(1L);
    	Pay pay = new Pay();
    	pay.setPayType("1");
    	payServiceMock.returns(pay).findByPayId(1L);
    	orderViceServiceMock.returns(order).payOrder("1");
    	goodsProductServiceMock.returns(new GoodsProductVo()).queryProductByProductId(1L);
    	assertNotNull(grouponService.payGrouponOrder("1", 1L));
    }
    
    /**
     * 增加团购参与人数测试
     */
    @Test
    public void testAddGroupCount()
    {
    	grouponMapperMock.returns(1).updateCountByMarkertId(1L);
    	assertEquals(1, grouponService.addGroupCount(1L));
    }
    
    /**
     * 获取订单集合测试
     */
    @Test
    public void testSelectOrderList()
    {
    	PageBean pb = new PageBean();
    	Map<String, Object> paramMap = new HashMap<>();
    	orderViceMapperMock.returns(1L).selectOrderViceCount(paramMap);
        paramMap.put("startRowNum",pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        orderViceMapperMock.returns(lists).selectOrderViceList(paramMap);
    	assertEquals(1, grouponService.selectOrderList(paramMap, pb).getList().size());
    }
    
}
