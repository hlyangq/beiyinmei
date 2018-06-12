package com.junit.test.site.shoppingcart.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.address.service.ShoppingCartAddressService;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.dao.CustomerPointLevelMapper;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.freighttemplate.bean.FreightExpress;
import com.ningpai.freighttemplate.bean.FreightExpressAll;
import com.ningpai.freighttemplate.bean.FreightTemplate;
import com.ningpai.freighttemplate.dao.FreightExpressMapper;
import com.ningpai.freighttemplate.dao.FreightTemplateMapper;
import com.ningpai.goods.bean.Goods;
import com.ningpai.goods.bean.GoodsBrand;
import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.dao.GoodsProductMapper;
import com.ningpai.goods.dao.ProductWareMapper;
import com.ningpai.goods.service.GoodsGroupService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.vo.GoodsGroupReleProductVo;
import com.ningpai.goods.vo.GoodsGroupVo;
import com.ningpai.goods.vo.GoodsProductDetailVo;
import com.ningpai.marketing.bean.FullbuyNoCountMarketing;
import com.ningpai.marketing.bean.FullbuyNoDiscountMarketing;
import com.ningpai.marketing.bean.LimitBuyMarketing;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.bean.PreDiscountMarketing;
import com.ningpai.marketing.dao.FullbuyNoCountMarketingMapper;
import com.ningpai.marketing.dao.FullbuyNoDiscountMarketingMapper;
import com.ningpai.marketing.dao.MarketingMapper;
import com.ningpai.marketing.dao.PreDiscountMarketingMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.service.OrderService;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.goods.bean.GoodsDetailBean;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.site.goods.vo.GoodsProductVo;
import com.ningpai.site.login.service.LoginService;
import com.ningpai.site.order.service.SiteOrderService;
import com.ningpai.site.shoppingcart.bean.ShoppingCart;
import com.ningpai.site.shoppingcart.dao.ShoppingCartMapper;
import com.ningpai.site.shoppingcart.service.ShoppingCartService;
import com.ningpai.site.shoppingcart.service.impl.ShoppingCartServiceImpl;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.dao.BasicSetMapper;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.system.service.DistrictService;
import com.ningpai.util.PageBean;

/**
 * 购物车service测试
 * @author djk
 *
 */
public class ShoppingCartServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();




    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ShoppingCartAddressService> shoppingCartAddressServiceMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerServiceInterface> customerServiceInterfaceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerServiceMapper> customerServiceMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<LoginService> loginServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ShoppingCartMapper> shoppingCartMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<MarketingService> marketingServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<MarketingMapper> marketingMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<PreDiscountMarketingMapper> preDiscountMarketingMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductService> goodsProductServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CouponService> couponServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsGroupService> goodsGroupServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ProductWareMapper> productWareMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductMapper> goodsProductMapperMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ProductWareService> productWareServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<SiteOrderService> siteOrderServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderService> orderServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<DefaultAddressService> defaultAddressServiceMock;
    

    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerPointLevelMapper> customerPointLevelMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<FullbuyNoCountMarketingMapper> fullbuyNoCountMarketingMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<FullbuyNoDiscountMarketingMapper> fullbuyNoDiscountMarketingMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<FreightTemplateMapper> freightTemplateMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<FreightExpressMapper> freightExpressMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<BasicSetMapper> basicSetMapperMock;

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;
    
    /**
     * 分页对象
     */
    private PageBean pageBean = new PageBean();
    
    /**
     * 模拟httpServletRequest
     */
    private MockHttpServletRequest request = new MockHttpServletRequest();
    
    @Override
    protected void setUp() throws Exception 
    {
    	MockHttpSession session = new MockHttpSession();
    	session.setAttribute("customerId", 1L);
    	session.setAttribute("distinctId", "1");
    	session.setAttribute("chProvince", "a");
    	session.setAttribute("chCity", "b");
    	session.setAttribute("chDistinct", "c");
    	request.setSession(session);
    }
    
    
    /**
     * 修改会员积分测试
     */
    @Test
    public void testUpdatePoint()
    {
    	CustomerPoint customerPoint = new CustomerPoint();
    	customerPoint.setPointSum(10L);
    	couponServiceMock.returns(customerPoint).selectCustomerPointByCustomerId(1L);
    	couponServiceMock.returns(1).updateCustomerPoint(customerPoint);
    	assertEquals(1, shoppingCartService.updatePoint(1L, 1L));
    }
    
    /**
     * 查询购物车测试
     */
    @Test
    public void testShoppingCart()
    {
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsBrand goodsBrand = new GoodsBrand();
    	goodsBrand.setBrandId(1L);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoodsInfoId(1L);
    	goodsProductVo.setGoods(goods);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setProductVo(goodsProductVo);
    	goodsDetailBean.setBrand(goodsBrand);
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setMarketingActivityId(1L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setDistinctId(1L);
    	List<Object> shoplist = new ArrayList<>();
    	shoplist.add(shoppingCart);
	    Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", 1L);
        shoppingCartMapperMock.returns(1).shoppingCartCount(paramMap);
        paramMap.put("startRowNum", 0);
        paramMap.put("endRowNum", 20);
        shoppingCartMapperMock.returns(shoplist).shoppingCart(paramMap);
        goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
    	assertEquals(1, shoppingCartService.shoppingCart(pageBean, request).getList().size());
    }
    
    /**
     * mini购物车测试
     */
    @Test
    public void testMiniShoppingCart()
    {
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsBrand goodsBrand = new GoodsBrand();
    	goodsBrand.setBrandId(1L);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoods(goods);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setBrand(goodsBrand);
    	goodsDetailBean.setProductVo(goodsProductVo);
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setDistinctId(1L);
    	shoppingCart.setMarketingId(1L);
    	List<ShoppingCart> shoplist = new ArrayList<>();
    	shoplist.add(shoppingCart);
    	shoppingCartMapperMock.returns(shoplist).shoppingCartMini(1L);
    	goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
    	
    	assertNotNull(shoppingCartService.miniShoppingCart(request));
    }
    
    /**
     * 删除购物车商品测试
     */
    @Test
    public void testDelShoppingCartById()
    {
    	MockHttpServletResponse response = new MockHttpServletResponse();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("shoppingCartId", 1L);
        map.put("customerId", 1L);
        shoppingCartMapperMock.returns(1).delShoppingCartById(map);
    	assertEquals(1, shoppingCartService.delShoppingCartById(1L, 1L, request, response));
    }
    
    /**
     * 修改购买数量测试
     */
    @Test
    public void testChangeShoppingCartById()
    {
    	shoppingCartMapperMock.returns(1).changeShoppingCartById(new ShoppingCart());
    	assertEquals(1, shoppingCartService.changeShoppingCartById(1L, 1L));
    }
    
    /**
     * 修改优惠测试
     */
    @Test
    public void testChangeShoppingCartMarket()
    {
    	shoppingCartMapperMock.returns(1).changeShoppingCartMarket(new ShoppingCart());
    	assertEquals(1, shoppingCartService.changeShoppingCartMarket(1L, 1L, 1L));
    }
    
    /**
     * 修改优惠测试
     */
    @Test
    public void testChangeShoppingCartMarket2()
    {
    	shoppingCartMapperMock.returns(1).changeShoppingCartMarket(new ShoppingCart());
    	assertEquals(1, shoppingCartService.changeShoppingCartMarket(1L, 1L, 1L, request, new MockHttpServletResponse()));
    }
    
    /**
     * 根据购物车里面的货品查询是否存在包邮的促销活动 返回list集合,不包邮的购物车测试
     */
    @Test
    public void testGetNobaoyouShoppingcarts()
    {
    	PreDiscountMarketing preDiscountMarketing = new PreDiscountMarketing();
    	preDiscountMarketing.setDiscountPrice(new BigDecimal(1));
    	preDiscountMarketing.setDiscountInfo(new BigDecimal(1));
    	Map<String, Object> mapGoods = new HashMap<String, Object>();
        mapGoods.put("marketingId", 1L);
        mapGoods.put("goodsId", 1L);
    	GoodsProduct goodsProduct = new GoodsProduct();
    	goodsProduct.setGoodsInfoPreferPrice(new BigDecimal(1));
    	Marketing marketing = new Marketing();
    	marketing.setShippingMoney(new BigDecimal(2L));
    	marketing.setMarketingId(1L);
    	List<Marketing> marketingList = new ArrayList<>();
    	marketingList.add(marketing);
    	List<ShoppingCart> cartList = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setThirdId(0L);
    	shoppingCart.setMarketgoodsPrice(new BigDecimal(1L));
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setGoodsNum(1L);
    	cartList.add(shoppingCart);
    	Map<String, Object> map = new HashMap<>();
        map.put("goodsId", 1L);
        map.put("codeType", "12");
        marketingMapperMock.returns(marketingList).queryMarketingByGoodIdAndtypeList(map);
        goodsProductMapperMock.returns(goodsProduct).queryByGoodsInfoDetail(1L);
        marketingMapperMock.returns(marketing).marketingDetail(1L);
        preDiscountMarketingMapperMock.returns(preDiscountMarketing).selectByMarketId(mapGoods);
    	assertEquals(1, shoppingCartService.getNobaoyouShoppingcarts(cartList,1L).size());
    }
    
    
    /**
     * 得到各个商家的运费测试
     */
    @Test
    public void testGetEverythirdExpressPrice()
    {
    	FreightExpressAll freightExpressAll = new FreightExpressAll();
    	freightExpressAll.setExpressStart(1L);
    	freightExpressAll.setExpressPostage(new BigDecimal(10));
    	freightExpressAll.setExpressPostageplus(new BigDecimal(10));
    	freightExpressAll.setExpressPlusN1(2L);
    	freightExpressAll.setExpressArea("1,2");
    	List<FreightExpressAll> freightExpressAlls = new ArrayList<>();
    	freightExpressAlls.add(freightExpressAll);
    	List<FreightExpress> fes = new ArrayList<>();
    	FreightExpress fe = new FreightExpress();
    	fe.setFreightExpressAll(freightExpressAlls);
    	fes.add(fe);
    	FreightTemplate ft = new FreightTemplate();
    	ft.setFreightMethods("0");
    	ft.setFreightTemplateId(1L);
        GoodsProductVo goodsProduct = new GoodsProductVo();
        goodsProduct.setGoodsInfoWeight(new BigDecimal(1));
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("freightIsDefault", "1");
        paramMap.put("freightThirdId", 1L);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setThirdId(1L);
        shoppingCart.setGoodsNum(2L);
        shoppingCart.setGoodsInfoId(1L);
    	List<ShoppingCart> cartList = new ArrayList<>();
    	cartList.add(shoppingCart);
    	goodsProductServiceMock.returns(goodsProduct).queryProductByProductId(1L);
    	freightTemplateMapperMock.returns(ft).selectFreightTemplateSubOrder(paramMap);
    	freightExpressMapperMock.returns(fes).selectTemplateExpress(1L);
    	assertEquals(new BigDecimal(20), shoppingCartService.getEverythirdExpressPrice(1L, 1L,cartList));
    }
    @Test
    public void testGetNewExpressPrice()
    {
    	Map<String, Object> map = new HashMap<>();
    	map.put("bossSumPrice", 1d);
    	map.put("sumOldPrice", 1d);
    	map.put("sumPrice", 1d);
        map.put("cusZhekouPrice", 1d);
        map.put("bossPrePrice", 1d);
    	List<ShoppingCart> shopdata = new ArrayList<>();
        GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
        GoodsBrand goodsBrand = new GoodsBrand();
        goodsBrand.setBrandId(1L);
        Goods goods = new Goods();
        goods.setCatId(1L);
        GoodsProductVo goodsProductVo = new GoodsProductVo();
        goodsProductVo.setGoodsInfoPreferPrice(BigDecimal.ONE);
        goodsProductVo.setGoodsInfoWeight(BigDecimal.ONE);
        goodsProductVo.setGoods(goods);
        goodsDetailBean.setProductVo(goodsProductVo);
        goodsDetailBean.setBrand(goodsBrand);
        CustomerAllInfo customerAllInfo = new CustomerAllInfo();
        customerAllInfo.setInfoPointSum(0);
    	ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setGoodsInfoId(1L);
        shoppingCart.setGoodsNum(1L);
        shoppingCart.setThirdId(1L);
        shoppingCart.setGoodsDetailBean(goodsDetailBean);
    	shopdata.add(shoppingCart);
    	Long[] cartIds = {1L};
    	Customer cus = new Customer();
    	CustomerPointLevel cuspointLevel = new CustomerPointLevel();
    	cuspointLevel.setPointDiscount(new BigDecimal(1));
    	cus.setPointLevelId(1L);
        CustomerPoint customerPoint = new CustomerPoint();
        customerPoint.setPointSum(1L);
    	shoppingCartMapperMock.returns(shopdata).shopCartListByIds(Arrays.asList(cartIds));
    	siteOrderServiceMock.returns(map).getEveryparamMap(cartIds, 1L);
        goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L,1L);
        goodsProductServiceMock.returns(goodsProductVo).queryProductByProductId(1L);
        couponServiceMock.returns(customerPoint).selectCustomerPointByCustomerId(1L);
        customerPointServiceMapperMock.returns(BigDecimal.ONE).getCustomerDiscountByPoint(1);
    	customerServiceInterfaceMock.returns(customerAllInfo).queryCustomerById(1L);
    	customerPointLevelMapperMock.returns(cuspointLevel).selectByPrimaryKey(1L);
        customerPointServiceMapperMock.returns(new BigDecimal(1)).getCustomerDiscountByPoint(0);
        assertEquals(9, shoppingCartService.getNewExpressPrice(request, 1L, 1L, cartIds).size());
    }


    /**
     * 计算运费-不同地区的货品价格以及库存测试
     * 套装的场景
     */
    @Test
    public void testGetNewExpressPrice2()
    {
        GoodsGroupReleProductVo goodsGroupReleProductVo = new GoodsGroupReleProductVo();
        goodsGroupReleProductVo.setProductId(1L);
        goodsGroupReleProductVo.setProductNum(1L);
        List<GoodsGroupReleProductVo> goodsGroupReleProductVos = new ArrayList<>();
        goodsGroupReleProductVos.add(goodsGroupReleProductVo);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	List<GoodsProductVo> goodsProducts = new ArrayList<>();
        GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
        GoodsBrand goodsBrand = new GoodsBrand();
        Goods goods = new Goods();
        goods.setCatId(1L);
        goodsBrand.setBrandId(1L);
        goodsDetailBean.setBrand(goodsBrand);
        goodsProductVo.setGoodsInfoId(1L);
        goodsProductVo.setGoodsInfoPreferPrice(BigDecimal.ONE);
        goodsProductVo.setGoodsInfoWeight(BigDecimal.ONE);
        goodsProductVo.setGoods(goods);
        goodsDetailBean.setProductVo(goodsProductVo);
    	goodsProducts.add(goodsProductVo);
    	GoodsGroupVo goodsGroupVo = new GoodsGroupVo();
    	goodsGroupVo.setThirdId(0L);
        goodsGroupVo.setProductList(goodsGroupReleProductVos);
    	Map<String, Object> map = new HashMap<>();
    	map.put("bossSumPrice", 1d);
    	map.put("sumOldPrice", 1d);
    	map.put("sumPrice", 1d);
        map.put("cusZhekouPrice", 1d);
        map.put("bossPrePrice", 1d);
    	List<ShoppingCart> shopdata = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setThirdId(0L);
        shoppingCart.setGoodsInfoId(1L);
        shoppingCart.setGoodsNum(1L);
//    	shoppingCart.setFitId(1L);
        shoppingCart.setGoodsDetailBean(goodsDetailBean);
    	shopdata.add(shoppingCart);
    	Long[] cartIds = {1L};
    	CustomerAllInfo cus = new CustomerAllInfo();
        cus.setInfoPointSum(0);
    	CustomerPointLevel cuspointLevel = new CustomerPointLevel();
    	cuspointLevel.setPointDiscount(new BigDecimal(1));
    	cus.setPointLevelId(1L);
        CustomerPoint customerPoint = new CustomerPoint();
        customerPoint.setPointSum(1L);
    	shoppingCartMapperMock.returns(shopdata).shopCartListByIds(Arrays.asList(cartIds));
    	goodsGroupServiceMock.returns(goodsGroupVo).queryVoByPrimaryKey(1L);
    	goodsProductServiceMock.returns(goodsProducts).queryDetailByGroupId(1L);
    	siteOrderServiceMock.returns(map).getEveryparamMap(cartIds, 1L);
        goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L,1L);
        goodsProductServiceMock.returns(goodsProductVo).queryProductByProductId(1L);
        couponServiceMock.returns(customerPoint).selectCustomerPointByCustomerId(1L);
        customerPointServiceMapperMock.returns(BigDecimal.ONE).getCustomerDiscountByPoint(1);
        customerServiceInterfaceMock.returns(cus).queryCustomerById(1L);
    	customerPointLevelMapperMock.returns(cuspointLevel).selectByPrimaryKey(1L);
        customerPointServiceMapperMock.returns(new BigDecimal(1)).getCustomerDiscountByPoint(0);
    	assertEquals(9, shoppingCartService.getNewExpressPrice(request, 1L, 1L, cartIds).size());
    }
    
    /**
     * 根据所选择的商品进入订单确认查询测试
     * 不是套装的场景
     */
    @Test
    public void testSubOrder()
    {
    	GoodsBrand brand = new GoodsBrand();
    	brand.setBrandId(1L);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setThirdId(1L);
    	productVo.setGoods(goods);
    	productVo.setGoodsInfoStock(1L);
    	productVo.setGoodsInfoPreferPrice(new BigDecimal(1));
    	FullbuyNoCountMarketing fullbuyNoCountMarketing = new FullbuyNoCountMarketing();
    	fullbuyNoCountMarketing.setMarketingId(1L);
    	fullbuyNoCountMarketing.setCountCondition(0L);
    	fullbuyNoCountMarketing.setCountNo(1L);
    	fullbuyNoCountMarketing.setCountMoney(new BigDecimal(1));
    	FullbuyNoDiscountMarketing fullbuyNoDiscountMarketing = new FullbuyNoDiscountMarketing();
    	fullbuyNoDiscountMarketing.setMarketingId(1L);
    	fullbuyNoDiscountMarketing.setCountCondition(0L);
    	fullbuyNoDiscountMarketing.setPackagesNo(1L);
    	Marketing marketing  = new Marketing();
    	marketing.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	marketing.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setProductVo(productVo);
    	goodsDetailBean.setBrand(brand);
    	List<Long> list = new ArrayList<Long>();
    	list.add(1L);
    	List<ShoppingCart> shoplist = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsDetailBean(goodsDetailBean);
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setOrderMarketingId(1L);
    	shoplist.add(shoppingCart);
    	shoppingCart.setMarketingId(1L);
    	Long[] box = {1L};
    	shoppingCartMapperMock.returns(shoplist).shoppingCartListByIds(list);
    	goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
    	marketingServiceMock.returns(marketing).marketingDetail(1L,1L);
    	marketingServiceMock.returns(marketing).marketingDetail(1L);
    	marketingServiceMock.returns(marketing).marketingDetail(1L);
    	
    	Long[] marketingId = {1L};
    	Long[] thirdId = {1L};
    	ShoppingCartWareUtil wareUtil = new ShoppingCartWareUtil();
    	wareUtil.setDistrictId(1L);
    	CustomerPoint customerPoint  = new CustomerPoint();
    	goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
    	couponServiceMock.returns(customerPoint).selectCustomerPointByCustomerId(1L);
    	couponServiceMock.returns(new PointSet()).selectPointSet();
    	assertEquals(7, shoppingCartService.subOrder(request, box, marketingId, thirdId, wareUtil, 1L).size());
    }
    
    /**
     * 添加购物车测试
     */
    @Test
    public void testAddShoppingCart()
    {
    	Marketing marketing = new Marketing();
    	marketing.setMarketingId(1L);
    	marketing.setCodexType("15");
    	List<Marketing> markList = new ArrayList<>();
    	markList.add(marketing);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoods(goods);
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsInfoId(1L);
    	GoodsBrand brand = new GoodsBrand();
    	brand.setBrandId(1L);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setBrand(brand);
    	goodsDetailBean.setProductVo(productVo);
    	try 
    	{
    		shoppingCartMapperMock.returns(12).selectSumByCustomerId(1L);
    		shoppingCartMapperMock.returns(0).selectCountByReady(shoppingCart);
    		goodsProductServiceMock.returns(productVo).getGoodsProductVoWithGoods(1L);
    		marketingServiceMock.returns(markList).selectMarketingByGoodsInfoId(1L, 1L, 1L);
    		shoppingCartMapperMock.returns(1).addShoppingCart(shoppingCart);
        	assertEquals(0, shoppingCartService.addShoppingCart(shoppingCart, request, new MockHttpServletResponse()));
    	} catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }

    /**
     * 查询购物车购买的商品信息测试
     */
    @Test
    public void testSearchByProduct()
    {
    	FullbuyNoCountMarketing fullbuyNoCountMarketing = new FullbuyNoCountMarketing();
    	fullbuyNoCountMarketing.setMarketingId(1L);
    	fullbuyNoCountMarketing.setCountCondition(0L);
    	fullbuyNoCountMarketing.setCountNo(1L);
    	FullbuyNoDiscountMarketing fullbuyNoDiscountMarketing = new FullbuyNoDiscountMarketing();
    	fullbuyNoDiscountMarketing.setMarketingId(1L);
    	fullbuyNoDiscountMarketing.setCountCondition(0L);
    	fullbuyNoDiscountMarketing.setPackagesNo(1L);
    	Marketing marketing  = new Marketing();
    	marketing.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	marketing.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	List<Long> list = new ArrayList<Long>();
    	list.add(1L);
    	List<ShoppingCart> shoplist = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoplist.add(shoppingCart);
    	shoppingCart.setMarketingId(1L);
    	Long[] box = {1L};
    	shoppingCartMapperMock.returns(shoplist).shoppingCartListByIds(list);
    	goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
    	marketingServiceMock.returns(marketing).marketingDetail(1L,1L);
    	marketingServiceMock.returns(marketing).marketingDetail(1L);
    	marketingServiceMock.returns(marketing).marketingDetail(1L);
    	assertEquals(1, shoppingCartService.searchByProduct(request, box).size());
    }
    
    /**
     *  删除已经购买商品测试
     */
    @Test
    public void testDeleteShoppingCartByIds()
    {
    	Long[] shoppingCartId = {1L};
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        shoppingCartMapperMock.returns(1).deleteShoppingCartByIds(list);
    	assertEquals(1, shoppingCartService.deleteShoppingCartByIds(request, shoppingCartId));
    }
    
    /**
     * 加载cookie中的购物车信息测试
     */
    @Test
    public void testLoadCookShopCar()
    {
    	Cookie cookie1 = new Cookie("_npstore_shopcar","131-12&132");
    	Cookie cookie2 = new Cookie("_npstore_mId","31e1e2e1e312121");
    	Cookie [] cookies = {cookie1,cookie2};
    	request.setCookies(cookies);
    	try
    	{
    	  	assertEquals(1, shoppingCartService.loadCookShopCar(request).size());
		} catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    /**
     * 从cook添加到购物车测试
     */
    @Test
    public void testLoadCoodeShopping()
    {
    	Cookie cookie1 = new Cookie("_npstore_shopcar","131-12&132");
    	Cookie cookie2 = new Cookie("_npstore_mId","31e1e2e1e312121");
    	Cookie [] cookies = {cookie1,cookie2};
    	request.setCookies(cookies);
    	shoppingCartAddressServiceMock.returns(new ShoppingCartWareUtil()).loadAreaFromRequest(request);

    	assertEquals(1, shoppingCartService.loadCoodeShopping(request));
    }
    
    /**
     * 删除cookie中的购物车数据测试
     */
    @Test
    public void testDelCookShopCar()
    {
    	try 
    	{
    	   	Cookie cookie1 = new Cookie("_npstore_shopcar","13,1-12&132");
        	Cookie cookie2 = new Cookie("_npstore_mId","31e1e2e1e312121");
        	Cookie [] cookies = {cookie1,cookie2};
        	request.setCookies(cookies);
    		assertEquals(0, shoppingCartService.delCookShopCar(1L, request, new MockHttpServletResponse()));
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    /**
     * 修改要选中的订单优惠测试
     */
    @Test
    public void testchangeShoppingCartOrderMarket()
    {
    	assertEquals(0, shoppingCartService.changeShoppingCartOrderMarket(new ShoppingCart()));
    }
    
    /**
     * 查询购物车内容测试
     */
    @Test
    public void testSelectShoppingCart()
    {
    	FullbuyNoCountMarketing fullbuyNoCountMarketing = new FullbuyNoCountMarketing();
    	fullbuyNoCountMarketing.setMarketingId(1L);
    	fullbuyNoCountMarketing.setCountCondition(1L);
    	fullbuyNoCountMarketing.setCountNo(1L);
    	FullbuyNoDiscountMarketing fullbuyNoDiscountMarketing = new FullbuyNoDiscountMarketing();
    	fullbuyNoDiscountMarketing.setPackagesNo(1L);
    	fullbuyNoDiscountMarketing.setCountCondition(1L);
    	fullbuyNoDiscountMarketing.setMarketingId(1L);
    	Marketing mark = new Marketing();
    	mark.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	mark.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	mark.setMarketingEnd(new Date(new Date().getTime()+1000000L));
    	List<Marketing> marketingList = new ArrayList<>();
    	marketingList.add(mark);
    	LimitBuyMarketing limitBuyMarketing = new LimitBuyMarketing();
    	limitBuyMarketing.setLimitCount(1L);
    	Marketing marketing = new Marketing();
    	marketing.setLimitBuyMarketing(limitBuyMarketing);
    	marketing.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	marketing.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	GoodsBrand brand = new GoodsBrand();
    	brand.setBrandId(1L);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoodsInfoStock(1L);
    	productVo.setGoodsInfoId(1L);
    	productVo.setGoods(goods);
    	goodsDetailBean.setProductVo(productVo);
    	goodsDetailBean.setBrand(brand);
    	List<Object> shoplist  = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setMarketingActivityId(1L);
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setShoppingCartId(1L);
    	shoplist.add(shoppingCart);
    	ShoppingCartWareUtil cartWareUtil = new ShoppingCartWareUtil();
    	cartWareUtil.setDistrictId(1L);
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("customerId", 1L);
    	shoppingCartMapperMock.returns(1).shoppingCartCount(paramMap);
        paramMap.put("startRowNum", 0);
        paramMap.put("endRowNum", 20);
        shoppingCartMapperMock.returns(shoplist).shoppingCart(paramMap);
        goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
        marketingServiceMock.returns(1L).queryByCreatimeMarketings(1L, 3L, 1L, 1L);
        marketingServiceMock.returns(marketing).marketingDetail(1L);
        orderServiceMock.returns(0L).selectGoodsInfoCount(1L, 1L, null);
        marketingServiceMock.returns(marketingList).selectMarketingByGoodsInfoId(1L, 1L, 1L);
        marketingServiceMock.returns(mark).marketingDetail(1L,1L);
        marketingServiceMock.returns(mark).marketingDetailByActive(mark, 1L, false);
        marketingServiceMock.returns(mark).marketingDetail(1L);
        assertEquals(1, shoppingCartService.selectShoppingCart(request, cartWareUtil, pageBean, new MockHttpServletResponse()).getList().size());
    }
    
    /**
     * 查询购物车内容测试
     * 测session中有_npstore_shopstatus的流程
     */
    @Test
    public void testSelectShoppingCart2()
    {
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	MockHttpSession session = new MockHttpSession();
    	session.setAttribute("customerId", 1L);
    	session.setAttribute("_npstore_shopstatus", "1e1");
    	request.setSession(session);
    	FullbuyNoCountMarketing fullbuyNoCountMarketing = new FullbuyNoCountMarketing();
    	fullbuyNoCountMarketing.setMarketingId(1L);
    	fullbuyNoCountMarketing.setCountCondition(1L);
    	fullbuyNoCountMarketing.setCountNo(1L);
    	FullbuyNoDiscountMarketing fullbuyNoDiscountMarketing = new FullbuyNoDiscountMarketing();
    	fullbuyNoDiscountMarketing.setPackagesNo(1L);
    	fullbuyNoDiscountMarketing.setCountCondition(1L);
    	fullbuyNoDiscountMarketing.setMarketingId(1L);
    	Marketing mark = new Marketing();
    	mark.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	mark.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	mark.setMarketingEnd(new Date(new Date().getTime()+1000000L));
    	List<Marketing> marketingList = new ArrayList<>();
    	marketingList.add(mark);
    	LimitBuyMarketing limitBuyMarketing = new LimitBuyMarketing();
    	limitBuyMarketing.setLimitCount(1L);
    	Marketing marketing = new Marketing();
    	marketing.setLimitBuyMarketing(limitBuyMarketing);
    	marketing.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	marketing.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	GoodsBrand brand = new GoodsBrand();
    	brand.setBrandId(1L);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoodsInfoStock(1L);
    	productVo.setGoodsInfoId(1L);
    	productVo.setGoods(goods);
    	goodsDetailBean.setProductVo(productVo);
    	goodsDetailBean.setBrand(brand);
    	List<Object> shoplist  = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setMarketingActivityId(1L);
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setShoppingCartId(1L);
    	shoplist.add(shoppingCart);
    	ShoppingCartWareUtil cartWareUtil = new ShoppingCartWareUtil();
    	cartWareUtil.setDistrictId(1L);
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("customerId", 1L);
    	shoppingCartMapperMock.returns(1).shoppingCartCount(paramMap);
        paramMap.put("startRowNum", 0);
        paramMap.put("endRowNum", 20);
        shoppingCartMapperMock.returns(shoplist).shoppingCart(paramMap);
        goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
        marketingServiceMock.returns(1L).queryByCreatimeMarketings(1L, 3L, 1L, 1L);
        marketingServiceMock.returns(marketing).marketingDetail(1L);
        orderServiceMock.returns(0L).selectGoodsInfoCount(1L, 1L, null);
        marketingServiceMock.returns(marketingList).selectMarketingByGoodsInfoId(1L, 1L, 1L);
        marketingServiceMock.returns(mark).marketingDetail(1L,1L);
        marketingServiceMock.returns(mark).marketingDetailByActive(mark, 1L, false);
        marketingServiceMock.returns(mark).marketingDetail(1L);
        assertEquals(1, shoppingCartService.selectShoppingCart(request, cartWareUtil, pageBean, new MockHttpServletResponse()).getList().size());
    }
    
    
    /**
     * 查询购物车内容测试
     * 测是套装的场景
     */
    @Test
    public void testSelectShoppingCart3()
    {
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoodsInfoStock(1L);
    	productVo.setGoodsInfoId(1L);
    	goodsDetailBean.setProductVo(productVo);
    	GoodsProductDetailVo productDetail = new GoodsProductDetailVo();
    	productDetail.setGoodsInfoId(1L);
    	GoodsGroupReleProductVo goodsGroupReleProductVo = new GoodsGroupReleProductVo();
    	goodsGroupReleProductVo.setProductDetail(productDetail);
    	List<GoodsGroupReleProductVo> goodsGroupReleProductVos = new ArrayList<>();
    	goodsGroupReleProductVos.add(goodsGroupReleProductVo);
    	GoodsGroupVo goodsGroupVo = new GoodsGroupVo();
    	goodsGroupVo.setProductList(goodsGroupReleProductVos);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	MockHttpSession session = new MockHttpSession();
    	session.setAttribute("customerId", 1L);
    	session.setAttribute("_npstore_shopstatus", "1e1");
    	request.setSession(session);
    	FullbuyNoCountMarketing fullbuyNoCountMarketing = new FullbuyNoCountMarketing();
    	fullbuyNoCountMarketing.setMarketingId(1L);
    	fullbuyNoCountMarketing.setCountCondition(1L);
    	fullbuyNoCountMarketing.setCountNo(1L);
    	FullbuyNoDiscountMarketing fullbuyNoDiscountMarketing = new FullbuyNoDiscountMarketing();
    	fullbuyNoDiscountMarketing.setPackagesNo(1L);
    	fullbuyNoDiscountMarketing.setCountCondition(1L);
    	fullbuyNoDiscountMarketing.setMarketingId(1L);
    	Marketing mark = new Marketing();
    	mark.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	mark.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	mark.setMarketingEnd(new Date(new Date().getTime()+1000000L));
    	List<Marketing> marketingList = new ArrayList<>();
    	marketingList.add(mark);
    	LimitBuyMarketing limitBuyMarketing = new LimitBuyMarketing();
    	limitBuyMarketing.setLimitCount(1L);
    	Marketing marketing = new Marketing();
    	marketing.setLimitBuyMarketing(limitBuyMarketing);
    	marketing.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	marketing.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	List<Object> shoplist  = new ArrayList<>();
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setMarketingActivityId(1L);
    	shoppingCart.setFitId(1L);
    	shoppingCart.setMarketingId(1L);
    	shoppingCart.setGoodsNum(1L);
    	shoppingCart.setGoodsInfoId(1L);
    	shoppingCart.setShoppingCartId(1L);
    	shoplist.add(shoppingCart);
    	ShoppingCartWareUtil cartWareUtil = new ShoppingCartWareUtil();
    	cartWareUtil.setDistrictId(1L);
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("customerId", 1L);
    	shoppingCartMapperMock.returns(1).shoppingCartCount(paramMap);
        paramMap.put("startRowNum", 0);
        paramMap.put("endRowNum", 20);
        shoppingCartMapperMock.returns(shoplist).shoppingCart(paramMap);
        goodsGroupServiceMock.returns(goodsGroupVo).queryVoByPrimaryKey(1L);
        goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
        marketingServiceMock.returns(1L).queryByCreatimeMarketings(1L, 3L, 1L, 1L);
        marketingServiceMock.returns(marketing).marketingDetail(1L);
        orderServiceMock.returns(0L).selectGoodsInfoCount(1L, 1L, null);
        marketingServiceMock.returns(marketingList).selectMarketingByGoodsInfoId(1L, 1L, 1L);
        marketingServiceMock.returns(mark).marketingDetail(1L,1L);
        marketingServiceMock.returns(mark).marketingDetailByActive(mark, 1L, false);
        marketingServiceMock.returns(mark).marketingDetail(1L);
        assertEquals(1, shoppingCartService.selectShoppingCart(request, cartWareUtil, pageBean, new MockHttpServletResponse()).getList().size());
    }
    
    /**
     * 查询购物车内容测试
     * 测用户未登陆的情况
     */
    @Test
    public void testSelectShoppingCart4()
    {
    	FullbuyNoCountMarketing fullbuyNoCountMarketing = new FullbuyNoCountMarketing();
    	fullbuyNoCountMarketing.setMarketingId(1L);
    	fullbuyNoCountMarketing.setCountCondition(1L);
    	fullbuyNoCountMarketing.setCountNo(1L);
    	FullbuyNoDiscountMarketing fullbuyNoDiscountMarketing = new FullbuyNoDiscountMarketing();
    	fullbuyNoDiscountMarketing.setPackagesNo(1L);
    	fullbuyNoDiscountMarketing.setCountCondition(1L);
    	fullbuyNoDiscountMarketing.setMarketingId(1L);
    	Marketing mark = new Marketing();
    	mark.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	mark.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	mark.setMarketingEnd(new Date(new Date().getTime()+1000000L));
     	GoodsBrand brand = new GoodsBrand();
    	brand.setBrandId(1L);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	LimitBuyMarketing limitBuyMarketing = new LimitBuyMarketing();
    	limitBuyMarketing.setLimitCount(1L);
    	Marketing marketing = new Marketing();
    	marketing.setLimitBuyMarketing(limitBuyMarketing);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoodsInfoStock(1L);
    	productVo.setGoodsInfoId(1L);
    	productVo.setGoods(goods);
    	goodsDetailBean.setBrand(brand);
    	goodsDetailBean.setProductVo(productVo);
       	ShoppingCartWareUtil cartWareUtil = new ShoppingCartWareUtil();
    	cartWareUtil.setDistrictId(1L);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	MockHttpSession session = new MockHttpSession();
    	request.setSession(session);
    	Cookie cookie1 = new Cookie("_npstore_shopcar","131-12&132");
    	Cookie cookie2 = new Cookie("_npstore_mId","31e1e2e1e312121");
    	Cookie [] cookies = {cookie1,cookie2};
    	request.setCookies(cookies);
    	goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(null, 1L);
        marketingServiceMock.returns(1L).queryByCreatimeMarketings(1L, 3L, 1L, 1L);
        marketingServiceMock.returns(marketing).marketingDetail(1L);
        marketingServiceMock.returns(mark).marketingDetail(1L,31L);
        assertEquals(1, shoppingCartService.selectShoppingCart(request, cartWareUtil, pageBean, new MockHttpServletResponse()).getList().size());
    }
    
    /**
     * 查询购物车内容测试
     * 测用户未登陆的情况并且session中有_npstore_shopstatus的流程
     */
    @Test
    public void testSelectShoppingCart5()
    {
    	FullbuyNoCountMarketing fullbuyNoCountMarketing = new FullbuyNoCountMarketing();
    	fullbuyNoCountMarketing.setMarketingId(1L);
    	fullbuyNoCountMarketing.setCountCondition(1L);
    	fullbuyNoCountMarketing.setCountNo(1L);
    	FullbuyNoDiscountMarketing fullbuyNoDiscountMarketing = new FullbuyNoDiscountMarketing();
    	fullbuyNoDiscountMarketing.setPackagesNo(1L);
    	fullbuyNoDiscountMarketing.setCountCondition(1L);
    	fullbuyNoDiscountMarketing.setMarketingId(1L);
    	Marketing mark = new Marketing();
    	mark.setFullbuyNoCountMarketing(fullbuyNoCountMarketing);
    	mark.setFullbuyNoDiscountMarketing(fullbuyNoDiscountMarketing);
    	mark.setMarketingEnd(new Date(new Date().getTime()+1000000L));
     	GoodsBrand brand = new GoodsBrand();
    	brand.setBrandId(1L);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	LimitBuyMarketing limitBuyMarketing = new LimitBuyMarketing();
    	limitBuyMarketing.setLimitCount(1L);
    	Marketing marketing = new Marketing();
    	marketing.setLimitBuyMarketing(limitBuyMarketing);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoodsInfoStock(1L);
    	productVo.setGoodsInfoId(1L);
    	productVo.setGoods(goods);
    	goodsDetailBean.setBrand(brand);
    	goodsDetailBean.setProductVo(productVo);
       	ShoppingCartWareUtil cartWareUtil = new ShoppingCartWareUtil();
    	cartWareUtil.setDistrictId(1L);
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	MockHttpSession session = new MockHttpSession();
      	session.setAttribute("_npstore_shopstatus", "1e1");
    	request.setSession(session);
    	
    	Cookie cookie1 = new Cookie("_npstore_shopcar","131-12&132");
    	Cookie cookie2 = new Cookie("_npstore_mId","31e1e2e1e312121");
    	Cookie [] cookies = {cookie1,cookie2};
    	request.setCookies(cookies);
    	goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(null, 1L);
        marketingServiceMock.returns(1L).queryByCreatimeMarketings(1L, 3L, 1L, 1L);
        marketingServiceMock.returns(marketing).marketingDetail(1L);
        marketingServiceMock.returns(mark).marketingDetail(1L,31L);
        assertEquals(1, shoppingCartService.selectShoppingCart(request, cartWareUtil, pageBean, new MockHttpServletResponse()).getList().size());
    }
    


    /**
     * 删除优惠分组下单个商品并添加其他商品到购物车内测试
     */
    @Test
    public void testDelGoodsGroupByS()
    {
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	MockHttpSession session = new MockHttpSession();
    	session.setAttribute("customerId", 1L);
    	request.setSession(session);
    	GoodsProductDetailVo goodsProductDetailVo = new GoodsProductDetailVo();
    	goodsProductDetailVo.setGoodsInfoId(1L);
    	GoodsGroupReleProductVo goodsGroupReleProductVo = new GoodsGroupReleProductVo();
    	goodsGroupReleProductVo.setProductDetail(goodsProductDetailVo);
    	List<GoodsGroupReleProductVo> vos = new ArrayList<>();
    	vos.add(goodsGroupReleProductVo);
    	GoodsGroupVo goodsGroupVo = new  GoodsGroupVo();	
    	goodsGroupServiceMock.returns(goodsGroupVo).queryVoByPrimaryKey(1L);
    	goodsGroupVo.setProductList(vos);
    	assertEquals(0, shoppingCartService.delGoodsGroupByS(1L, 1L, 1L, request, new MockHttpServletResponse()));
    }
    
    /**
     * 更改商品选中状态测试
     */
    @Test
    public void testChangeShopStatus()
    {
     	MockHttpServletRequest request = new MockHttpServletRequest();
    	MockHttpSession session = new MockHttpSession();
    	session.setAttribute("_npstore_shopstatus", "1e1");
    	request.setSession(session);
    	assertEquals("1", shoppingCartService.changeShopStatus(1L, "1", request, new MockHttpServletResponse()));
    }

    /**
     * 批量更改商品选中状态测试
     */
    @Test
    public void testChangeShopStatusByParam()
    {
    	Long[] shoppingId = {1L};
    	assertEquals("1", shoppingCartService.changeShopStatusByParam(shoppingId, "1", request, new MockHttpServletResponse()));
    }
    
    /**
     * 立即加入购物车测试
     */
    @Test
    public void testSelectLastId()
    {
    	Marketing marketing = new Marketing();
    	marketing.setMarketingId(1L);
    	marketing.setCodexType("15");
    	List<Marketing> markList = new ArrayList<>();
    	markList.add(marketing);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsProductVo productVo = new GoodsProductVo();
    	productVo.setGoods(goods);
    	ShoppingCart shoppingCart = new ShoppingCart();
    	shoppingCart.setGoodsInfoId(1L);
    	GoodsBrand brand = new GoodsBrand();
    	brand.setBrandId(1L);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setBrand(brand);
    	goodsDetailBean.setProductVo(productVo);
    	shoppingCartMapperMock.returns(12).selectSumByCustomerId(1L);
		shoppingCartMapperMock.returns(0).selectCountByReady(shoppingCart);
		goodsProductServiceMock.returns(productVo).getGoodsProductVoWithGoods(1L);
		marketingServiceMock.returns(markList).selectMarketingByGoodsInfoId(1L, 1L, 1L);
		shoppingCartMapperMock.returns(1).addShoppingCart(shoppingCart);
		shoppingCartMapperMock.returns(1L).selectLastId(shoppingCart);
    	Long result = 1L;
    	assertEquals(result, shoppingCartService.selectLastId(shoppingCart, new MockHttpServletResponse(), request));
    }
    
    /**
     * 限购测试
     */
    @Test
    public void testForPurchasing()
    {
    	Date date = new Date();
    	LimitBuyMarketing limitBuyMarketing = new LimitBuyMarketing();
    	limitBuyMarketing.setLimitCount(1L);
    	Marketing marketing = new Marketing();
    	marketing.setMarketingBegin(date);
    	marketing.setLimitBuyMarketing(limitBuyMarketing);
    	GoodsBrand goodsBrand = new GoodsBrand();
    	goodsBrand.setBrandId(1L);
    	Goods goods = new Goods();
    	goods.setCatId(1L);
    	GoodsProductVo goodsProductVo = new GoodsProductVo();
    	goodsProductVo.setGoods(goods);
    	goodsProductVo.setGoodsInfoId(1L);
    	goodsProductVo.setGoodsInfoStock(1L);
    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
    	goodsDetailBean.setBrand(goodsBrand);
    	goodsDetailBean.setProductVo(goodsProductVo);
    	marketingServiceMock.returns(1L).queryByCreatimeMarketings(1L, 3L, 1L, 1L);
    	marketingServiceMock.returns(marketing).marketingDetail(1L);
    	orderServiceMock.returns(1L).selectGoodsInfoCount(1L, 1L, date);
    	assertNotNull(shoppingCartService.forPurchasing(goodsDetailBean, 1L));
    }
    
    /**
     * 判断手机号是否是用户名或者已绑定测试
     */
    @Test
    public void testIsSowMobel()
    {
    	request.getSession().setAttribute("is_temp_cust", "0");
    	customerServiceInterfaceMock.returns(1L).checkMobileExist("1");
    	long result = 1L;
    	assertEquals(result, shoppingCartService.isSowMobel("1", request));
    }
    
    /**
     * 查询购物商品信息测试
     */
    @Test
    public void testShopCartListByIds()
    {
    	List<ShoppingCart> shoppingCarts = new ArrayList<>();
    	shoppingCarts.add(new ShoppingCart());
    	List<Long> list = new ArrayList<>();
    	list.add(1L);
    	shoppingCartMapperMock.returns(shoppingCarts).shopCartListByIds(list);
    	assertEquals(1, shoppingCartService.shopCartListByIds(list).size());
    }
    
    /**
     * 新商品结算页面测试
     */
//    @Test
//    public void testSubshopCartMap()
//    {
//    	FreightTemplate ft = new FreightTemplate();
//    	Marketing marketing = new Marketing();
//    	List<Marketing> marketingList = new ArrayList<>();
//    	marketingList.add(marketing);
//    	Goods goods = new Goods();
//    	goods.setCatId(1L);
//    	GoodsProductVo productVo = new GoodsProductVo();
//    	productVo.setGoods(goods);
//    	GoodsBrand brand = new GoodsBrand();
//    	brand.setBrandId(1L);
//    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
//    	goodsDetailBean.setBrand(brand);
//    	goodsDetailBean.setProductVo(productVo);
//    	StoreTemp storeTemp = new StoreTemp();
//    	storeTemp.setThirdId(0L);
//    	List<StoreTemp> storeList = new ArrayList<>();
//    	storeList.add(storeTemp);
//    	CustomerAllInfo customer = new CustomerAllInfo();
//    	customer.setPointLevelId(1L);
//        customer.setInfoPointSum(0);
//    	ShoppingCart shoppingCart = new ShoppingCart();
//    	shoppingCart.setGoodsInfoId(1L);
//    	shoppingCart.setDistinctId(1L);
//    	List<ShoppingCart> shoplist = new ArrayList<>();
//    	shoplist.add(shoppingCart);
//
//    	Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("freightIsDefault", "1");
//        paramMap.put("freightThirdId", 0L);
//        freightTemplateMapperMock.returns(ft).selectFreightTemplateSubOrder(paramMap);
//    	Long[] box = {1L};
//    	shoppingCartMapperMock.returns(shoplist).shopCartListByIds(Arrays.asList(box));
//    	shoppingCartMapperMock.returns(storeList).selectStoreTempByshopcartIds(Arrays.asList(box));
//    	shoppingCartServiceMock.returns(new ShoppingCartWareUtil()).loadAreaFromRequest(request);
//    	goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
//    	marketingServiceMock.returns(marketingList).selectMarketingByGoodsInfoId(1L, 1L, 1L);
//    	couponServiceMock.returns(new PointSet()).selectPointSet();
//        CustomerPoint customerPoint = new CustomerPoint();
//        customerPoint.setPointSum(1L);
//    	couponServiceMock.returns(customerPoint).selectCustomerPointByCustomerId(1L);
//    	customerServiceInterfaceMock.returns(customer).queryCustomerById(1L);
//        customerPointServiceMapperMock.returns(new BigDecimal(1)).getCustomerDiscountByPoint(0);
//    	assertEquals(8, shoppingCartService.subshopCartMap(request, box, new MockHttpServletResponse(), new ShoppingCartWareUtil(), new PointSet()).size());
//    }
    
    /**
     * 新产品使用购物车测试
     */
//    @Test
//    public void testShopCartMap()
//    {
//    	PreDiscountMarketing preDiscountMarketing = new PreDiscountMarketing();
//    	preDiscountMarketing.setGoodsId(1L);
//    	preDiscountMarketing.setDiscountInfo(new BigDecimal(1));
//    	List<PreDiscountMarketing> lists = new ArrayList<>();
//    	lists.add(preDiscountMarketing);
//    	Marketing marketing = new Marketing();
//    	marketing.setPreDiscountMarketings(lists);
//    	List<Marketing> marketings = new ArrayList<>();
//    	marketings.add(marketing);
//    	Goods goods = new Goods();
//    	goods.setCatId(1L);
//    	GoodsBrand goodsBrand = new GoodsBrand();
//    	goodsBrand.setBrandId(1L);
//    	GoodsProductVo goodsProductVo = new GoodsProductVo();
//    	goodsProductVo.setGoodsInfoPreferPrice(new BigDecimal(1));
//    	goodsProductVo.setGoods(goods);
//    	goodsProductVo.setGoodsInfoStock(1L);
//    	GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
//    	goodsDetailBean.setBrand(goodsBrand);
//    	goodsDetailBean.setProductVo(goodsProductVo);
//    	List<ShoppingCart> shoplist = new ArrayList<>();
//    	ShoppingCart shoppingCart = new ShoppingCart();
//    	shoppingCart.setGoodsInfoId(1L);
//    	shoppingCart.setDistinctId(1L);
//    	shoppingCart.setGoodsDetailBean(goodsDetailBean);
//    	shoplist.add(shoppingCart);
//    	ShoppingCartWareUtil wareUtil = new ShoppingCartWareUtil();
//    	Map<String, Object> upMap = new HashMap<String, Object>();
//        upMap.put("distinctId", wareUtil.getDistrictId());
//        upMap.put("customerId", 1L);
//        shoppingCartMapperMock.returns(1).updShoppingCartHouseByCstId(upMap);
//        shoppingCartMapperMock.returns(shoplist).selectShoppingCartListByCustomerId(1L);
//        goodsProductServiceMock.returns(goodsDetailBean).queryDetailBeanByProductId(1L, 1L);
//        marketingServiceMock.returns(marketings).selectMarketingByGoodsInfoId(1L, 1L, 1L);
//    	assertEquals(3, shoppingCartService.shopCartMap(request, wareUtil, new MockHttpServletResponse()).size());
//    }
}
