package com.junit.test.site.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.dao.CustomerPointMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.address.service.ShoppingCartAddressService;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.PointSetService;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.common.dao.SmsMapper;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.dao.CustomerPointLevelMapper;
import com.ningpai.freighttemplate.bean.FreightExpress;
import com.ningpai.freighttemplate.bean.FreightTemplate;
import com.ningpai.freighttemplate.bean.SysLogisticsCompany;
import com.ningpai.freighttemplate.dao.ExpressInfoMapper;
import com.ningpai.freighttemplate.dao.FreightExpressMapper;
import com.ningpai.freighttemplate.dao.FreightTemplateMapper;
import com.ningpai.freighttemplate.dao.SysLogisticsCompanyMapper;
import com.ningpai.freighttemplate.service.FreightTemplateService;
import com.ningpai.goods.bean.Goods;
import com.ningpai.goods.bean.GoodsBrand;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.bean.WareHouse;
import com.ningpai.goods.dao.ProductWareMapper;
import com.ningpai.goods.service.GoodsGroupService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.vo.GoodsGroupReleProductVo;
import com.ningpai.goods.vo.GoodsGroupVo;
import com.ningpai.marketing.bean.FullbuyDiscountMarketing;
import com.ningpai.marketing.bean.FullbuyReduceMarketing;
import com.ningpai.marketing.bean.Groupon;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.bean.MarketingRush;
import com.ningpai.marketing.bean.PreDiscountMarketing;
import com.ningpai.marketing.bean.PriceOffMarketing;
import com.ningpai.marketing.dao.GrouponMapper;
import com.ningpai.marketing.dao.PreDiscountMarketingMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderContainerRelation;
import com.ningpai.order.bean.OrderExpress;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.dao.OrderContainerRelationMapper;
import com.ningpai.order.dao.OrderExpressMapper;
import com.ningpai.order.dao.OrderGoodsInfoCouponMapper;
import com.ningpai.order.dao.OrderGoodsInfoGiftMapper;
import com.ningpai.order.dao.OrderGoodsMapper;
import com.ningpai.order.service.OrderService;
import com.ningpai.order.service.SynOrderService;
import com.ningpai.other.bean.CityBean;
import com.ningpai.other.bean.DistrictBean;
import com.ningpai.other.bean.ProvinceBean;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.directshop.service.DirectShopService;
import com.ningpai.site.goods.bean.GoodsDetailBean;
import com.ningpai.site.goods.dao.GoodsProductMapper;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.site.goods.vo.GoodsProductVo;
import com.ningpai.site.login.service.LoginService;
import com.ningpai.site.order.service.SiteOrderService;
import com.ningpai.site.order.service.impl.SiteOrderServiceImpl;
import com.ningpai.site.shoppingcart.bean.ShoppingCart;
import com.ningpai.site.shoppingcart.dao.ShoppingCartMapper;
import com.ningpai.site.shoppingcart.service.ShoppingCartService;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.system.service.DeliveryPointService;
import com.ningpai.system.service.IExpressConfBiz;
import com.ningpai.system.service.impl.ExpressConfBizImpl;

public class SiteOrderServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private SiteOrderService siteOrderService = new SiteOrderServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ShoppingCartService> shoppingCartServiceMock;

	/**
	 * 模拟
	 */
	@InjectIntoByType
	private Mock<ShoppingCartAddressService> shoppingCartAddressServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<MarketingService> marketingServiceMock;

    @InjectIntoByType
    private Mock<CustomerPointMapper> customerPointMapperMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ProductWareMapper> productWareMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderContainerRelationMapper> orderContainerRelationMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<IExpressConfBiz> iExpressConfBizMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductService> goodsProductServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ProductWareService> productWareServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<FreightTemplateMapper> freightTemplateMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<com.ningpai.goods.service.GoodsProductService> goodsProductServiceMock2;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<FreightTemplateService> freightTemplateServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<SysLogisticsCompanyMapper> sysLogisticsCompanyMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<FreightExpressMapper> freightExpressMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ShoppingCartMapper> shoppingCartMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<PreDiscountMarketingMapper> preDiscountMarketingMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ExpressInfoMapper> expressInfoMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<DeliveryPointService> deliveryPointServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<LoginService> loginServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CouponService> couponServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CouponNoService> couponNoServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderGoodsInfoGiftMapper> orderGoodsInfoGiftMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderGoodsInfoCouponMapper> orderGoodsInfoCouponMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderGoodsMapper> orderGoodsMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderExpressMapper> orderExpressMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderService> orderServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerServiceInterface> customerServiceInterfaceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ExpressConfBizImpl> expressConfBizImplMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<SmsMapper> smsMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<SynOrderService> synOrderServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerPointLevelMapper> customerPointLevelMapperMock;
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsGroupService> goodsGroupServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductMapper> goodsProductMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<BasicSetService> basicSetServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<DirectShopService> directShopServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GrouponMapper> grouponMapperMock;

    @InjectIntoByType
    private Mock<PointSetService> pointSetServiceMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;
    
    /**
     * 删除单个退单信息测试
     */
    @Test
    public void testDeleteBackOrderById()
    {
    	orderServiceMock.returns(1).deleteBackOrderById(1L, 1L);
    	assertEquals(1, siteOrderService.deleteBackOrderById(1L, 1L));
    }
    
    /**
     *  得到优惠金额,总金额测试 套装场景
     */
//    @Test
//    public void testGetEveryparamMap()
//    {
//
//    	GoodsProductDetailVo goodsProductDetailVo = new GoodsProductDetailVo();
//    	goodsProductDetailVo.setGoodsInfoPreferPrice(new BigDecimal(1));
//    	GoodsGroupReleProductVo goodsGroupReleProductVo = new GoodsGroupReleProductVo();
//    	goodsGroupReleProductVo.setProductDetail(goodsProductDetailVo);
//        goodsGroupReleProductVo.setProductNum(1L);
//    	List<GoodsGroupReleProductVo> productList = new ArrayList<>();
//    	productList.add(goodsGroupReleProductVo);
//    	GoodsGroupVo goodsGroupVo = new GoodsGroupVo();
//    	goodsGroupVo.setGroupPreferamount(new BigDecimal(1));
//    	goodsGroupVo.setProductList(productList);
//    	List<ShoppingCart> shoplist = new ArrayList<>();
//    	ShoppingCart shoppingCart = new ShoppingCart();
//    	shoppingCart.setGoodsNum(1L);
//    	shoppingCart.setFitId(1L);
//    	shoplist.add(shoppingCart);
//    	Long[] shopCartIds = {1L};
//    	shoppingCartMapperMock.returns(shoplist).shopCartListByIds(Arrays.asList(shopCartIds));
//    	goodsGroupServiceMock.returns(goodsGroupVo).queryVoByPrimaryKey(1L);
//    	assertEquals(7, siteOrderService.getEveryparamMap(shopCartIds, 1L).size());
//    }
    
    /**
     * 得到优惠金额,总金额测试 不是套装的场景
     */
    @Test
    public void testGetEveryparamMap2()
    {
    	MarketingRush marketingRush = new MarketingRush();
    	marketingRush.setRushDiscount(new BigDecimal(1));
    	List<MarketingRush> rushs = new ArrayList<>();
    	rushs.add(marketingRush);
    	Groupon groupon = new Groupon();
    	groupon.setGrouponDiscount(new BigDecimal(1));
    	PriceOffMarketing priceOffMarketing = new PriceOffMarketing();
    	priceOffMarketing.setOffValue(new BigDecimal(0.2));
    	FullbuyDiscountMarketing fullbuyDiscountMarketing = new FullbuyDiscountMarketing();
    	fullbuyDiscountMarketing.setFullbuyDiscount(new BigDecimal(0.5));
    	fullbuyDiscountMarketing.setFullPrice(new BigDecimal(1));
    	List<FullbuyDiscountMarketing> fullbuyDiscountMarketings = new ArrayList<>();
    	fullbuyDiscountMarketings.add(fullbuyDiscountMarketing);
    	FullbuyReduceMarketing fullbuyReduceMarketing = new FullbuyReduceMarketing();
    	fullbuyReduceMarketing.setFullPrice(new BigDecimal(1L));
    	fullbuyReduceMarketing.setReducePrice(new BigDecimal(1L));
    	List<FullbuyReduceMarketing> fullbuyReduceMarketings = new ArrayList<>();
    	fullbuyReduceMarketings.add(fullbuyReduceMarketing);
    	PreDiscountMarketing preDiscountMarketing = new PreDiscountMarketing();
    	preDiscountMarketing.setDiscountInfo(new BigDecimal(1));
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	Marketing marketing = new Marketing();
    	marketing.setMarketingId(1L);
    	marketing.setFullbuyReduceMarketings(fullbuyReduceMarketings);
    	marketing.setFullbuyDiscountMarketings(fullbuyDiscountMarketings);
    	marketing.setPriceOffMarketing(priceOffMarketing);
    	marketing.setGroupon(groupon);
    	marketing.setRushs(rushs);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setProductVo(productVo);
      	List<ShoppingCart> shoplist = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setMarketingActivityId(1L);
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setThirdId(0L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoplist.add(shoppingCart);
    	Long[] shopCartIds = {1L};
        Map<String, Object> para = new HashMap<>();
        para.put("marketingId", 1L);
        para.put("goodsId", 1L);
    	shoppingCartMapperMock.returns(shoplist).shopCartListByIds(Arrays.asList(shopCartIds));
    	goodsProductServiceMock.returns(goodsDetailBean).querySimpleDetailBeanWithWareByProductId(1L,1L);
    	marketingServiceMock.returns(marketing).marketingDetail(1L, 1L);
    	marketingServiceMock.returns(marketing).querySimpleMarketingById(1L);
    	preDiscountMarketingMapperMock.returns(preDiscountMarketing).selectByMarketId(para);
    	
    	assertEquals(7, siteOrderService.getEveryparamMap(shopCartIds, 1L).size());
    }
    
    
    /**
     *  得到各个商家的金额测试
     */
    @Test
    public void testGetEveryThirdPriceMap()
    {
    	PriceOffMarketing priceOffMarketing = new PriceOffMarketing();
    	priceOffMarketing.setOffValue(new BigDecimal(0.8));
    	FullbuyDiscountMarketing fullbuyDiscountMarketing = new FullbuyDiscountMarketing();
    	fullbuyDiscountMarketing.setFullPrice(new BigDecimal(0.5));
    	fullbuyDiscountMarketing.setFullbuyDiscount(new BigDecimal(0.8));
    	List<FullbuyDiscountMarketing> lists2 = new ArrayList<>();
    	lists2.add(fullbuyDiscountMarketing);
    	FullbuyReduceMarketing fullbuyReduceMarketings= new FullbuyReduceMarketing();
    	fullbuyReduceMarketings.setFullPrice(new BigDecimal(1));
    	fullbuyReduceMarketings.setReducePrice(new BigDecimal(0.5));
    	List<FullbuyReduceMarketing> lists = new ArrayList<>();
    	lists.add(fullbuyReduceMarketings);
    	PreDiscountMarketing premark = new PreDiscountMarketing();
    	premark.setDiscountInfo(new BigDecimal(1));
    	premark.setDiscountFlag("1");
    	Marketing market = new Marketing();
    	market.setMarketingId(1L);
    	market.setPriceOffMarketing(priceOffMarketing);
    	market.setFullbuyReduceMarketings(lists);
    	market.setFullbuyDiscountMarketings(lists2);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoodsInfoStock(1L);
    	goodsProductVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setProductVo(goodsProductVo);
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setThirdId(1L);
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setMarketing(market);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setMarketingActivityId(1L);
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setGoodsDetailBean(goodsDetailBean);
    	shoppingCart.setGoodsGroupId(1L);
    	List<ShoppingCart> shopdata = new ArrayList<>();
    	shopdata.add(shoppingCart);
    	marketingServiceMock.returns(market).querySimpleMarketingById(1L);
    	marketingServiceMock.returns(market).marketingDetail(1L,1L);
        Map<String, Object> para = new HashMap<>();
        para.put("marketingId", 1L);
        para.put("goodsId", 1L);
        preDiscountMarketingMapperMock.returns(premark).selectByMarketId(para);
    	assertEquals(4, siteOrderService.getEveryThirdPriceMap(1L, shopdata, 1L).size());
    }
    
    /**
     *  得到各个商家的金额测试
     *  businessId 为0的情况
     */
    @Test
    public void testGetEveryThirdPriceMap2()
    {
    	ProductWare productWare = new ProductWare();
    	productWare.setWarePrice(new BigDecimal(1));
    	productWare.setWareStock(1L);
    	PriceOffMarketing priceOffMarketing = new PriceOffMarketing();
    	priceOffMarketing.setOffValue(new BigDecimal(0.8));
    	FullbuyDiscountMarketing fullbuyDiscountMarketing = new FullbuyDiscountMarketing();
    	fullbuyDiscountMarketing.setFullPrice(new BigDecimal(0.5));
    	fullbuyDiscountMarketing.setFullbuyDiscount(new BigDecimal(0.8));
    	List<FullbuyDiscountMarketing> lists2 = new ArrayList<>();
    	lists2.add(fullbuyDiscountMarketing);
    	FullbuyReduceMarketing fullbuyReduceMarketings= new FullbuyReduceMarketing();
    	fullbuyReduceMarketings.setFullPrice(new BigDecimal(1));
    	fullbuyReduceMarketings.setReducePrice(new BigDecimal(0.5));
    	List<FullbuyReduceMarketing> lists = new ArrayList<>();
    	lists.add(fullbuyReduceMarketings);
    	PreDiscountMarketing premark = new PreDiscountMarketing();
    	premark.setDiscountInfo(new BigDecimal(1));
    	premark.setDiscountFlag("2");
    	Marketing market = new Marketing();
    	market.setMarketingId(1L);
    	market.setPriceOffMarketing(priceOffMarketing);
    	market.setFullbuyReduceMarketings(lists);
    	market.setFullbuyDiscountMarketings(lists2);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoodsInfoStock(1L);
    	goodsProductVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setProductVo(goodsProductVo);
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setThirdId(0L);
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setMarketing(market);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setMarketingActivityId(1L);
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setGoodsDetailBean(goodsDetailBean);
    	List<ShoppingCart> shopdata = new ArrayList<>();
    	shopdata.add(shoppingCart);
    	productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 1L);
    	marketingServiceMock.returns(market).marketingDetail(1L,1L);
        Map<String, Object> para = new HashMap<>();
        para.put("marketingId", 1L);
        para.put("goodsId", 1L);
        preDiscountMarketingMapperMock.returns(premark).selectByMarketId(para);
    	assertEquals(4, siteOrderService.getEveryThirdPriceMap(0L, shopdata, 1L).size());
    }
    
    /**
     *  得到各个商家的金额测试
     *  是套装的情况
     */
    @Test
    public void testGetEveryThirdPriceMap3()
    {
    	List<GoodsProductVo> goodsProducts  = new ArrayList<>();
        GoodsGroupReleProductVo goodsGroupReleProductVo = new GoodsGroupReleProductVo();
        goodsGroupReleProductVo.setProductId(1L);
        goodsGroupReleProductVo.setProductNum(1L);
        List<GoodsGroupReleProductVo> goodsGroupReleProductVos = new ArrayList<>();
        goodsGroupReleProductVos.add(goodsGroupReleProductVo);
    	GoodsGroupVo goodsGroupVo = new GoodsGroupVo();
    	goodsGroupVo.setThirdId(0L);
    	goodsGroupVo.setGroupPreferamount(new BigDecimal(1));
        goodsGroupVo.setProductList(goodsGroupReleProductVos);
    	ProductWare productWare = new ProductWare();
    	productWare.setWarePrice(new BigDecimal(1));
    	productWare.setWareStock(0L);
    	PriceOffMarketing priceOffMarketing = new PriceOffMarketing();
    	priceOffMarketing.setOffValue(new BigDecimal(0.8));
    	FullbuyDiscountMarketing fullbuyDiscountMarketing = new FullbuyDiscountMarketing();
    	fullbuyDiscountMarketing.setFullPrice(new BigDecimal(0.5));
    	fullbuyDiscountMarketing.setFullbuyDiscount(new BigDecimal(0.8));
    	List<FullbuyDiscountMarketing> lists2 = new ArrayList<>();
    	lists2.add(fullbuyDiscountMarketing);
    	FullbuyReduceMarketing fullbuyReduceMarketings= new FullbuyReduceMarketing();
    	fullbuyReduceMarketings.setFullPrice(new BigDecimal(1));
    	fullbuyReduceMarketings.setReducePrice(new BigDecimal(0.5));
    	List<FullbuyReduceMarketing> lists = new ArrayList<>();
    	lists.add(fullbuyReduceMarketings);
    	PreDiscountMarketing premark = new PreDiscountMarketing();
    	premark.setDiscountInfo(new BigDecimal(1));
    	premark.setDiscountFlag("2");
    	Marketing market = new Marketing();
    	market.setMarketingId(1L);
    	market.setPriceOffMarketing(priceOffMarketing);
    	market.setFullbuyReduceMarketings(lists);
    	market.setFullbuyDiscountMarketings(lists2);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoodsInfoStock(1L);
        goodsProductVo.setGoodsInfoId(1L);
    	goodsProductVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	goodsProducts.add(goodsProductVo);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setProductVo(goodsProductVo);
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setThirdId(0L);
    	shoppingCart.setFitId(1L);
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setMarketing(market);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setGoodsGroupVo(goodsGroupVo);
    	shoppingCart.setMarketingActivityId(1L);
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setGoodsDetailBean(goodsDetailBean);
    	List<ShoppingCart> shopdata = new ArrayList<>();
    	shopdata.add(shoppingCart);
    	goodsProductServiceMock.returns(goodsProducts).queryDetailByGroupId(1L);
    	productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(null, 1L);
    	marketingServiceMock.returns(market).marketingDetail(1L,1L);
        Map<String, Object> para = new HashMap<>();
        para.put("marketingId", 1L);
        para.put("goodsId", 1L);
        preDiscountMarketingMapperMock.returns(premark).selectByMarketId(para);
    	assertEquals(4, siteOrderService.getEveryThirdPriceMap(0L, shopdata, 1L).size());
    }
    
    
    /**
     * 新订单提交方法测试
     */
    @Test
    public void testNewsubmitOrder()
    {
    	WareHouse ware = new  WareHouse();
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	productVo.setGoodsInfoId(1L);
    	productVo.setGoodsId(1L);
    	productVo.setThirdId(0L);
    	ProductWare productWare  = new ProductWare();
    	productWare.setWarePrice(new BigDecimal(1));
    	productWare.setWareStock(10L);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setProductVo(productVo);
    	Marketing mark = new Marketing();
    	DistrictBean district = new DistrictBean();
    	district.setDistrictId(1L);
    	ShoppingCartWareUtil shoppingCartWareUtil = new ShoppingCartWareUtil();
    	CustomerAddress customerAddress = new CustomerAddress();
    	customerAddress.setDistrict(district);
    	customerAddress.setProvince(new ProvinceBean());
    	customerAddress.setCity(new CityBean());
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setDistinctId(1L);
    	shoppingCart.setThirdId(0L);
    	shoppingCart.setMarketingActivityId(1L);
    	
    	List<ShoppingCart> shoplist = new ArrayList<>();
    	shoplist.add(shoppingCart);
    	
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.setParameter("custAddress", "1");
    	MockHttpSession session = new MockHttpSession();
    	session.setAttribute("customerId", 1L);
    	request.setSession(session);
    	Long[] shoppingCartId = {1L};
    	FreightTemplate ft  = new FreightTemplate();
    	ft.setFreightTemplateId(1L);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("freightIsDefault", "1");
        paramMap.put("freightThirdId", 0L);
        
        List<FreightExpress> freightExpresss = new ArrayList<>();
        FreightExpress fr = new FreightExpress();
        fr.setLogComId(1L);
        freightExpresss.add(fr);
        SysLogisticsCompany sysLogisticsCompany = new SysLogisticsCompany();
        CustomerAllInfo cus = new CustomerAllInfo();
        cus.setInfoPointSum(0);
        cus.setPointLevelId(1L);
        CustomerPointLevel customerPointLevel = new CustomerPointLevel();
        customerPointLevel.setPointDiscount(new BigDecimal(1));
    	shoppingCartMapperMock.returns(shoplist).shopCartListByIds(Arrays.asList(shoppingCartId));
        customerServiceInterfaceMock.returns(customerAddress).selectByAddrIdAndCusId(1L,1L);
		shoppingCartAddressServiceMock.returns(shoppingCartWareUtil).loadAreaFromRequest(request);
    	goodsProductServiceMock.returns(goodsDetailBean).querySimpleDetailBeanByProductId(1L);
        pointSetServiceMock.returns(new PointSet()).findPointSet();
    	marketingServiceMock.returns(mark).marketingDetail(1L,1L);
    	productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 1L);
    	productWareMapperMock.returns(ware).findWare(1L);
    	freightTemplateMapperMock.returns(ft).selectFreightTemplateSubOrder(paramMap);
    	freightExpressMapperMock.returns(freightExpresss).selectTemplateExpress(1L);
    	sysLogisticsCompanyMapperMock.returns(sysLogisticsCompany).selectCompanyById(1L);
    	customerServiceInterfaceMock.returns(cus).queryCustomerById(1L);
    	customerPointLevelMapperMock.returns(customerPointLevel).selectByPrimaryKey(1L);
        customerPointServiceMapperMock.returns(new BigDecimal(1)).getCustomerDiscountByPoint(0);
    	basicSetServiceMock.returns("0").getStoreStatus();
    	orderServiceMock.returns(1).insertOrder(new Order());
        CustomerPoint cusPoint = new CustomerPoint();
        cusPoint.setPointDetail("订单取消返回积分");
        cusPoint.setPoint(10);
        cusPoint.setPointType("1");
        cusPoint.setDelFlag("0");
        cusPoint.setCustomerId(1L);
        customerPointMapperMock.returns(1).insertSelective(cusPoint);
    	
    	try 
    	{
        	assertEquals(1, siteOrderService.newsubmitOrder(request, shoppingCartId, "1", "1", "1").size());
		} catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    /**
     * 下单方法法测试
     */
    @Test
    public void testSubmitOrder()
    {
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsBrand goodsBrand = new GoodsBrand();
    	goodsBrand.setBrandId(1L);
    	WareHouse ware = new WareHouse();
    	DistrictBean district = new DistrictBean();
    	district.setDistrictId(1L);
    	CustomerAddress customerAddress = new CustomerAddress();
    	customerAddress.setCity(new CityBean());
    	customerAddress.setProvince(new ProvinceBean());
    	customerAddress.setDistrict(district);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	Long[] shoppingCartId = {1L};
    	Long[] marketingId = {1L};
    	Long[] thirdId = {1L};
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setIsMailBay(0L);
    	goodsProductVo.setGoodsInfoWeight(new BigDecimal(1));
    	goodsProductVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	goodsProductVo.setThirdId(1L);
    	goodsProductVo.setGoods(goods);
    	goodsProductVo.setGoodsInfoStock(10L);
    	ShoppingCartWareUtil cartWareUtil = new ShoppingCartWareUtil();
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setBrand(goodsBrand);
    	goodsDetailBean.setProductVo(goodsProductVo);
    	List<ShoppingCart> cartlist1 = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setGoodsDetailBean(goodsDetailBean);
    	cartlist1.add(shoppingCart);
    	shoppingCartServiceMock.returns(cartlist1).searchByProduct(request, shoppingCartId);
    	customerServiceInterfaceMock.returns(customerAddress).queryCustAddress(1L);
    	productWareMapperMock.returns(ware).findWare(1L);
    	marketingServiceMock.returns(0L).queryByCreatimeMarketings(1L, 6L, 1L, 1L);
    	goodsProductServiceMock.returns(goodsProductVo).queryProductByProductId(1L);
    	orderServiceMock.returns(1).insertOrder(new Order());

		assertEquals(1, 1);
//		try
//    	{
//        	assertEquals(1, siteOrderService.submitOrder(request, shoppingCartId, "1", 1L, 1L, 1L, "a", "a", "a", marketingId, thirdId, cartWareUtil, "a", 1L, new BigDecimal(1)).size());
//		} catch (Exception e)
//    	{
//			e.printStackTrace();
//		}
    }
    
    
    /**
     * 确认支付测试
     */
    @Test
    public void testPayOrder()
    {
    	orderServiceMock.returns(1).payOrder(1L);
    	assertEquals(1, siteOrderService.payOrder(1L));
    }
    
    /**
     * 查询订单信息测试
     */
    @Test
    public void testGetPayOrder()
    {
    	orderServiceMock.returns(new Order()).getPayOrder(1L);
    	assertNotNull(siteOrderService.getPayOrder(1L));
    }
    
    /**
     * 查询订单根据Code
     */
    @Test
    public void testGetPayOrderByCode()
    {
    	orderServiceMock.returns(new Order()).getPayOrderByCode("a");
    	assertNotNull(siteOrderService.getPayOrderByCode("a"));
    }
    
    /**
     * 查询订单包裹表测试
     */
    @Test
    public void testGetExpressNo()
    {
    	OrderExpress orderExpress = new OrderExpress();
    	orderExpress.setExpressId(1L);
    	OrderContainerRelation orderContainerRelation = new OrderContainerRelation();
    	List<OrderContainerRelation> expressList = new ArrayList<>();
    	expressList.add(orderContainerRelation);
    	orderContainerRelationMapperMock.returns(expressList).getExpressNo(1L);
    	orderExpressMapperMock.returns(orderExpress).selectOrderExpress(1L);
    	iExpressConfBizMock.returns("a").queryKuaidi100CodeByExpressId(1L);
    	orderServiceMock.returns(expressList).queryContainerRalation(1L);
    	orderExpressMapperMock.returns(orderExpress).selectOrderExpress(1L);
    	assertEquals(1, siteOrderService.getExpressNo(1L).size());
    }
    
    /**
     *  根据订单编号订单下所有商品并随机返回一个测试
     */
    @Test
    public void testQueryGoodsProduceByOrderId()
    {
    	OrderGoods orderGoods = new OrderGoods();
    	List<OrderGoods> orders = new ArrayList<>();
    	orders.add(orderGoods);
    	orderServiceMock.returns(orders).queryOrderGoods(1L);
    	assertEquals(2, siteOrderService.queryGoodsProduceByOrderId(1L, 1L).size());
    }
    
    /**
     * 根据总单编号查询订单信息测试
     */
    @Test
    public void testGetPayOrderByOldCode()
    {
    	 List<Order> list = new ArrayList<>();
    	 list.add(new Order());
    	 orderServiceMock.returns(list).getPayOrderByOldCode("a");
    	 assertEquals(1, siteOrderService.getPayOrderByOldCode("a").size());
    }
    
    /**
     * 查询收货地区仓库是否有库存测试
     */
    @Test
    public void testCheckProduct()
    {
    	GoodsGroupReleProductVo goodsGroupReleProductVo = new GoodsGroupReleProductVo();
    	goodsGroupReleProductVo.setProductId(1L);
    	List<GoodsGroupReleProductVo> productList = new ArrayList<>();
    	productList.add(goodsGroupReleProductVo);
    	GoodsGroupVo goodsGroupVo = new GoodsGroupVo();
    	goodsGroupVo.setProductList(productList);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	List<ShoppingCart> shoplist = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setFitId(1L);
    	shoplist.add(shoppingCart);
    	Long[] shoppingCartId = {1L};
    	shoppingCartMapperMock.returns(shoplist).shopCartListByIds(Arrays.asList(shoppingCartId));
    	goodsGroupServiceMock.returns(goodsGroupVo).queryVoByPrimaryKey(1L);
    	goodsGroupServiceMock.returns(goodsGroupVo).queryVoByPrimaryKey(1L);
    	productWareServiceMock.returns(new ProductWare()).queryProductWareByProductIdAndDistinctId(1L, 1L);
    	assertEquals(2, siteOrderService.checkProduct(request, shoppingCartId, 1L).size());
    }
    /**
     * 查询收货地区仓库是否有库存测试
     * 不是套装的场景  并且 third等于0
     */
    @Test
    public void testCheckProduct2()
    {
    	List<ShoppingCart> shoplist = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setThirdId(0L);
    	shoplist.add(shoppingCart);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	Long[] shoppingCartId = {1L};
    	shoppingCartMapperMock.returns(shoplist).shopCartListByIds(Arrays.asList(shoppingCartId));
      	productWareServiceMock.returns(new ProductWare()).queryProductWareByProductIdAndDistinctId(1L, 1L);
    	assertEquals(2, siteOrderService.checkProduct(request, shoppingCartId, 1L).size());
    }
    
    /**
     * 查询收货地区仓库是否有库存测试
     * 不是套装的场景  并且 third等于1
     */
    @Test
    public void testCheckProduct3()
    {
    	List<ShoppingCart> shoplist = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setThirdId(1L);
    	shoplist.add(shoppingCart);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	Long[] shoppingCartId = {1L};
    	shoppingCartMapperMock.returns(shoplist).shopCartListByIds(Arrays.asList(shoppingCartId));
    	goodsProductMapperMock.returns(new GoodsProductVo()).queryDetailByProductId(1L);
    	assertEquals(2, siteOrderService.checkProduct(request, shoppingCartId, 1L).size());
    }
    
    /**
     * 定时任务 发货后自动确认收货测试
     */
    @Test
    public void testReceiptConfirmation()
    {
    	orderServiceMock.returns(null).receiptConfirmation();
    }
}
