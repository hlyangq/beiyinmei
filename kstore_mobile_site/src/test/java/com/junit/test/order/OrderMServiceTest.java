package com.junit.test.order;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.bean.CouponFullReduction;
import com.ningpai.coupon.bean.CouponStraightDown;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.freighttemplate.bean.FreightExpress;
import com.ningpai.freighttemplate.bean.FreightTemplate;
import com.ningpai.freighttemplate.bean.SysLogisticsCompany;
import com.ningpai.freighttemplate.dao.ExpressInfoMapper;
import com.ningpai.freighttemplate.dao.FreightExpressMapper;
import com.ningpai.freighttemplate.dao.FreightTemplateMapper;
import com.ningpai.freighttemplate.dao.SysLogisticsCompanyMapper;
import com.ningpai.freighttemplate.service.FreightTemplateService;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.GoodsGroupService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.vo.GoodsGroupReleProductVo;
import com.ningpai.goods.vo.GoodsGroupVo;
import com.ningpai.goods.vo.GoodsProductDetailVo;
import com.ningpai.m.common.bean.Sms;
import com.ningpai.m.common.dao.SmsMapper;
import com.ningpai.m.customer.service.CustomerAddressService;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.directshop.bean.DirectShop;
import com.ningpai.m.directshop.service.DirectShopService;
import com.ningpai.m.goods.bean.GoodsDetailBean;
import com.ningpai.m.goods.dao.GoodsProductMapper;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.order.bean.OrderAddress;
import com.ningpai.m.order.service.OrderMService;
import com.ningpai.m.order.service.impl.OrderMServiceImpl;
import com.ningpai.m.shoppingcart.bean.ShoppingCart;
import com.ningpai.m.shoppingcart.dao.ShoppingCartMapper;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.bean.MarketingRush;
import com.ningpai.marketing.bean.PreDiscountMarketing;
import com.ningpai.marketing.dao.RushCustomerMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderContainerRelation;
import com.ningpai.order.bean.OrderExpress;
import com.ningpai.order.dao.*;
import com.ningpai.order.service.OrderService;
import com.ningpai.other.bean.CityBean;
import com.ningpai.other.bean.DistrictBean;
import com.ningpai.other.bean.ProvinceBean;
import com.ningpai.system.bean.DeliveryPoint;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.system.service.DeliveryPointService;
import com.ningpai.system.service.IExpressConfBiz;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.math.BigDecimal;
import java.util.*;

/**
 * OrderMServiceTest
 *
 * @author djk
 * @date 2016/3/31
 */
public class OrderMServiceTest extends UnitilsJUnit3 {


    /**
     * 需要测试的接口类
     */
    @TestedObject
    private OrderMService orderMService = new OrderMServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerAddressService> customerAddressServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<ShoppingCartService> shoppingCartServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsProductMapper> goodsProductMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<FreightTemplateMapper> freightTemplateMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderContainerRelationMapper> orderContainerRelationMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<IExpressConfBiz> iExpressConfBizMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<ProductWareService> productWareServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<ShoppingCartMapper> shoppingCartMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<com.ningpai.goods.service.GoodsProductService> goodsProductServiceMock1;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderService> orderServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderExpressMapper> orderExpressMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderGoodsMapper> orderGoodsMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderGoodsInfoCouponMapper> orderGoodsInfoCouponMapperMockl;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderGoodsInfoGiftMapper> orderGoodsInfoGiftMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<SysLogisticsCompanyMapper> sysLogisticsCompanyMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CouponService> couponServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CouponNoService> couponNoServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<ExpressInfoMapper> expressInfoMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerService> customerServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<DeliveryPointService> deliveryPointServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<MarketingService> marketingServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsGroupService> goodsGroupServiceMock;



    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsProductService> goodsProductServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<FreightExpressMapper> freightExpressMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<DirectShopService> directShopServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<BasicSetService> basicSetServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<SmsMapper> smsMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<RushCustomerMapper> rushCustomerMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;

    /**
     * 提交订单 （上门自提 没有运费）(非套装) (使用优惠卷为直降)
     */
    @Test
    public void testNewsubmitOrder() {
        DirectShop directShop  = new DirectShop();
        List<DirectShop> directShops = new ArrayList<>();
        directShops.add(directShop);
        PointSet pointSet = new PointSet();
        pointSet.setConsumption(new BigDecimal(1));
        CouponStraightDown couponStraightDown = new CouponStraightDown();
        couponStraightDown.setDownPrice(new BigDecimal(0.5));
        Coupon coupon = new Coupon();
        coupon.setBusinessId(0L);
        coupon.setCouponRulesType("1");
        coupon.setCouponStraightDown(couponStraightDown);
        CustomerPoint customerPoint = new CustomerPoint();
        customerPoint.setPointSum(1L);
        Map<String,Object> map = new HashMap<>();
        map.put("sumPrice",1D);
        map.put("sumOldPrice",2D);
        map.put("bossSumPrice",2D);
        map.put("stock",1);
        CityBean city = new CityBean();
        city.setCityId(1L);
        DeliveryPoint point = new DeliveryPoint();
        point.setDistrictId(1L);
        List<MarketingRush> rushs = new ArrayList<>();
        rushs.add(new MarketingRush());
        PreDiscountMarketing preDiscountMarketing = new PreDiscountMarketing();
        preDiscountMarketing.setDiscountPrice(new BigDecimal(1));
        GoodsProductVo productVo = new GoodsProductVo();
        productVo.setGoodsInfoPreferPrice(new BigDecimal(1));
        productVo.setGoodsInfoId(1L);
        productVo.setThirdId(0L);
        ProductWare productWare = new ProductWare();
        productWare.setWarePrice(new BigDecimal(1));
        productWare.setWareStock(1L);
        Marketing zhekoumarketing = new Marketing();
        zhekoumarketing.setPreDiscountMarketing(preDiscountMarketing);
        Marketing marketing = new Marketing();
        marketing.setRushs(rushs);
        GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
        goodsDetailBean.setProductVo(productVo);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setMarketingId(2L);
        shoppingCart.setMarketingActivityId(1L);
        shoppingCart.setGoodsNum(1L);
        shoppingCart.setThirdId(0L);
        shoppingCart.setGoodsInfoId(1L);
        shoppingCart.setDistinctId(1L);
        List<ShoppingCart> cartlist = new ArrayList<>();
        cartlist.add(shoppingCart);
        DistrictBean district = new DistrictBean();
        district.setDistrictId(1L);
        CustomerAddress ca = new CustomerAddress();
        ca.setDistrict(district);
        ca.setCity(city);
        Long[] shoppingCartId = {1L};
        OrderAddress orderAddress = new OrderAddress();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("customerId",1L);
        request.setSession(session);
        request.setParameter("codeNo", "123");
        request.setParameter("addressId", "1");
        request.setParameter("ch_pay","1");
        try {
            customerAddressServiceMock.returns(ca).queryDefaultAddr(1L);
            shoppingCartMapperMock.returns(cartlist).shopCartListByIds(Arrays.asList(shoppingCartId));
            goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
            marketingServiceMock.returns(marketing).marketingDetail(1L, 1L);
            marketingServiceMock.returns(zhekoumarketing).marketingDetail(2L, 1L);
            productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 1L);
            deliveryPointServiceMock.returns(point).getDeliveryPoint(1L);
            shoppingCartServiceMock.returns(map).getEveryThirdPriceMap(0L, cartlist, 1L);
            couponServiceMock.returns(customerPoint).selectCustomerPointByCustomerId(1L);
            customerPointServiceMapperMock.returns(new BigDecimal(1)).getCustomerDiscountByPoint(1);
            couponServiceMock.returns(coupon).selectCouponByCodeNo("123");
            couponServiceMock.returns(pointSet).selectPointSet();
            basicSetServiceMock.returns("0").getStoreStatus();
            directShopServiceMock.returns(directShops).queryDirectShopList(1L);
            orderServiceMock.returns(1).insertOrder(new Order());
            assertNotNull(orderMService.newsubmitOrder(1L, "1", "djk", request, shoppingCartId, 1L, orderAddress, 1L,null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交订单 （有运费） (使用优惠卷为满减) (套装)
     */
    @Test
    public void testNewsubmitOrder2() {
        SysLogisticsCompany sysLogisticsCompany = new SysLogisticsCompany();
        sysLogisticsCompany.setLogComId(1L);
        sysLogisticsCompany.setName("djk");
        FreightExpress fe = new FreightExpress();
        fe.setLogComId(1L);
        fe.setSysLogisticsCompany(sysLogisticsCompany);
        List<FreightExpress> freightExpresses = new ArrayList<>();
        freightExpresses.add(fe);
        FreightTemplate ft = new FreightTemplate();
        ft.setFreightTemplateId(1L);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("freightIsDefault", "1");
        paramMap.put("freightThirdId", 0L);
        GoodsProductDetailVo productDetail = new GoodsProductDetailVo();
        productDetail.setGoodsInfoId(1L);
        productDetail.setGoodsId(1L);
        GoodsGroupReleProductVo goodsGroupReleProductVo = new GoodsGroupReleProductVo();
        goodsGroupReleProductVo.setProductDetail(productDetail);
        List<GoodsGroupReleProductVo> productList = new ArrayList<>();
        productList.add(goodsGroupReleProductVo);
        GoodsGroupVo goodsGroupVo = new GoodsGroupVo();
        goodsGroupVo.setProductList(productList);
        goodsGroupVo.setThirdId(0L);
        goodsGroupVo.setGroupPreferamount(new BigDecimal(0));
        DirectShop directShop = new DirectShop();
        List<DirectShop> directShops = new ArrayList<>();
        directShops.add(directShop);
        PointSet pointSet = new PointSet();
        pointSet.setConsumption(new BigDecimal(1));
        CouponFullReduction couponFullReduction = new CouponFullReduction();
        couponFullReduction.setReductionPrice(new BigDecimal(1));
        Coupon coupon = new Coupon();
        coupon.setBusinessId(0L);
        coupon.setCouponRulesType("2");
        coupon.setCouponFullReduction(couponFullReduction);
        CustomerPoint customerPoint = new CustomerPoint();
        customerPoint.setPointSum(1L);
        Map<String, Object> map = new HashMap<>();
        map.put("sumPrice", 1D);
        map.put("sumOldPrice", 2D);
        map.put("bossSumPrice", 2D);
        map.put("stock", 1);
        CityBean city = new CityBean();
        city.setCityId(1L);
        DeliveryPoint point = new DeliveryPoint();
        point.setDistrictId(1L);
        List<MarketingRush> rushs = new ArrayList<>();
        rushs.add(new MarketingRush());
        PreDiscountMarketing preDiscountMarketing = new PreDiscountMarketing();
        preDiscountMarketing.setDiscountPrice(new BigDecimal(1));
        List<GoodsProductVo> goodsProducts = new ArrayList<>();
        GoodsProductVo productVo = new GoodsProductVo();
        productVo.setGoodsInfoPreferPrice(new BigDecimal(1));
        productVo.setGoodsInfoId(1L);
        productVo.setThirdId(0L);
        productVo.setGoodsInfoPreferPrice(new BigDecimal(10));
        goodsProducts.add(productVo);
        ProductWare productWare = new ProductWare();
        productWare.setWarePrice(new BigDecimal(1));
        productWare.setWareStock(1L);
        Marketing zhekoumarketing = new Marketing();
        zhekoumarketing.setPreDiscountMarketing(preDiscountMarketing);
        Marketing marketing = new Marketing();
        marketing.setRushs(rushs);
        GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
        goodsDetailBean.setProductVo(productVo);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setMarketingId(2L);
        shoppingCart.setMarketingActivityId(1L);
        shoppingCart.setGoodsNum(1L);
        shoppingCart.setThirdId(0L);
        shoppingCart.setGoodsInfoId(1L);
        shoppingCart.setDistinctId(1L);
        shoppingCart.setFitId(1L);
        shoppingCart.setGoodsGroupVo(goodsGroupVo);
        List<ShoppingCart> cartlist = new ArrayList<>();
        cartlist.add(shoppingCart);
        DistrictBean district = new DistrictBean();
        district.setDistrictId(1L);
        ProvinceBean province = new ProvinceBean();
        province.setProvinceName("djk");
        CustomerAddress ca = new CustomerAddress();
        ca.setProvince(province);
        ca.setDistrict(district);
        ca.setCity(city);
        Long[] shoppingCartId = {1L};
        OrderAddress orderAddress = new OrderAddress();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("customerId", 1L);
        request.setSession(session);
        request.setParameter("codeNo", "123");
        request.setParameter("addressId", "1");
        request.setParameter("ch_pay", "0");
        try {
            customerAddressServiceMock.returns(ca).queryDefaultAddr(1L);
            shoppingCartMapperMock.returns(cartlist).shopCartListByIds(Arrays.asList(shoppingCartId));
            goodsGroupServiceMock.returns(goodsGroupVo).queryVoByPrimaryKey(1L);
            goodsProductMapperMock.returns(goodsProducts).queryDetailByGroupId(1L);
            marketingServiceMock.returns(marketing).marketingDetail(1L, 1L);
            marketingServiceMock.returns(zhekoumarketing).marketingDetail(2L, 1L);
            productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 1L);
            deliveryPointServiceMock.returns(point).getDeliveryPoint(1L);
            freightExpressMapperMock.returns(freightExpresses).selectTemplateExpress(1L);
            sysLogisticsCompanyMapperMock.returns(new SysLogisticsCompany()).selectCompanyById(1L);
            shoppingCartServiceMock.returns(map).getEveryThirdPriceMap(0L, cartlist, 1L);
            couponServiceMock.returns(customerPoint).selectCustomerPointByCustomerId(1L);
            customerPointServiceMapperMock.returns(new BigDecimal(1)).getCustomerDiscountByPoint(1);
            couponServiceMock.returns(coupon).selectCouponByCodeNo("123");
            couponServiceMock.returns(pointSet).selectPointSet();
            basicSetServiceMock.returns("0").getStoreStatus();
            directShopServiceMock.returns(directShops).queryDirectShopList(1L);
            orderServiceMock.returns(1).insertOrder(new Order());
            freightTemplateMapperMock.returns(ft).selectFreightTemplateSubOrder(paramMap);
            assertNotNull(orderMService.newsubmitOrder(1L, "1", "djk", request, shoppingCartId, 1L, orderAddress, 1L, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPayOrder() {
        orderServiceMock.returns(1).payOrder(1L);
        assertEquals(1, orderMService.payOrder(1L));
    }


    @Test
    public void testGetPayOrder() {
        orderServiceMock.returns(new Order()).getPayOrder(1L);
        assertNotNull(orderMService.getPayOrder(1L));
    }


    @Test
    public void testGetPayOrderByCode() {
        orderServiceMock.returns(new Order()).getPayOrderByCode("1");
        assertNotNull(orderMService.getPayOrderByCode("1"));
    }


    @Test
    public void testGetExpressNo() {
        OrderExpress orderExpress = new OrderExpress();
        orderExpress.setExpressId(1L);
        OrderContainerRelation orderContainerRelation = new OrderContainerRelation();
        List<OrderContainerRelation> expressList = new ArrayList<>();
        expressList.add(orderContainerRelation);
        orderContainerRelationMapperMock.returns(expressList).getExpressNo(1L);
        orderExpressMapperMock.returns(orderExpress).selectOrderExpress(1L);
        iExpressConfBizMock.returns("1").queryKuaidi100CodeByExpressId(1L);
        orderServiceMock.returns(expressList).queryContainerRalation(1L);
        orderExpressMapperMock.returns(orderExpress).selectOrderExpress(1L);

        assertNotNull(orderMService.getExpressNo(1L));
    }


    @Test
    public void testGetWXUrl() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("openid","1");
        request.setSession(session);
        request.setParameter("userId","1");
        Order order = new Order();
        order.setOrderPrice(new BigDecimal(10.5));
        Pay pay = new Pay();
        pay.setApiKey("adfa");
        pay.setSecretKey("adfsa");
        pay.setPartner("a");
        pay.setBackUrl("http://www.baidu.com");
        orderMService.getWXUrl(request, response, order, pay,"1");
    }

    @Test
    public void testQueryOrderByOrderOldCode() {
        orderServiceMock.returns(new ArrayList<>()).getPayOrderByOldCode("1");
        assertNotNull(orderMService.queryOrderByOrderOldCode("1"));
    }


    @Test
    public void testPaySuccessSendSms() {
        Sms sms = new Sms();
        sms.setLoginName("a");
        sms.setSmsKind("a");
        sms.setPassword("a");
        sms.setHttpUrl("http://sh2.ipyy.com/sms.aspx");
        DirectShop directShop = new DirectShop();
        directShop.setDirectShopTel("15195812239");
        Order order = new Order();
        order.setBusinessId(1L);
        order.setDirectType("1");
        basicSetServiceMock.returns("0").getStoreStatus();
        directShopServiceMock.returns(directShop).selectInfoById(1L);
        smsMapperMock.returns(sms).selectSms();
        assertTrue(orderMService.paySuccessSendSms(order));
    }

}
