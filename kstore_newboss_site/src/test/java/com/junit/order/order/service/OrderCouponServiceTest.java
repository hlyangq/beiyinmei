package com.junit.order.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.coupon.service.CouponNoService;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerConsume;
import com.ningpai.customer.dao.CustomerConsumeMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.goods.service.GoodsProductService;
import com.ningpai.goods.util.CalcStockUtil;
import com.ningpai.goods.vo.GoodsProductDetailViewVo;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.bean.OrderGoodsInfoCoupon;
import com.ningpai.order.dao.OrderGoodsInfoCouponMapper;
import com.ningpai.order.dao.OrderGoodsMapper;
import com.ningpai.order.dao.OrderMapper;
import com.ningpai.order.service.OrderCouponService;
import com.ningpai.order.service.OrderService;
import com.ningpai.order.service.impl.OrderCouponServiceImpl;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.PointSetService;

public class OrderCouponServiceTest extends UnitilsJUnit3
{
	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private OrderCouponService orderCouponService = new OrderCouponServiceImpl();


	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<OrderService> orderServiceMock;
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<OrderGoodsMapper> orderGoodsMapperMock;
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<GoodsProductService> goodsProductServiceMock;
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<MarketingService> marketingServiceMock;

    /**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CouponNoService> couponNoServiceMock;
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CouponService> couponServiceMock;
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<OrderGoodsInfoCouponMapper> orderGoodsInfoCouponMapperMock;
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<OrderMapper> orderMapperMock;
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerConsumeMapper> customerConsumeMapperMock;
	
	/**
	 * 订单主表 
	 */
	private Order order = new Order();
	
	/**
	 *  订单商品信息表集合
	 */
	private List<OrderGoods> orderGoodsList = new ArrayList<>();
	
	/**
	 *  订单商品信息表
	 */
	private OrderGoods orderGoods = new OrderGoods();

    /**
     * 优惠券券码
     */
    private CouponNo couponNo = new CouponNo();
	
	/**
	 * 订单商品 送优惠券测试
	 */
	private List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = new ArrayList<>();
	
	/**
	 * 订单商品 送优惠券
	 */
	private OrderGoodsInfoCoupon orderGoodsInfoCoupon = new OrderGoodsInfoCoupon();
	
	/**
	 *  模拟MOCK
	 */
	@InjectIntoByType
	private Mock<PointSetService> pointSetServiceMock;
	@Override
	protected void setUp() throws Exception 
	{
		orderGoodsInfoCoupon.setCouponId(1L);
		orderGoodsInfoCoupon.setCouponNo("1");
		orderGoodsInfoCouponList.add(orderGoodsInfoCoupon);
		orderGoods.setGoodsMarketingId(1L);
		orderGoods.setHaveCouponStatus("1");
		orderGoods.setGoodsInfoNum(1L);
		orderGoods.setGoodsInfoId(1L);
		orderGoods.setOrderGoodsId(1L);
		orderGoods.setGoodsMarketingId(1L);
		orderGoods.setDistinctId(1L);
		orderGoodsList.add(orderGoods);
		order.setOrderPrice(new BigDecimal(1));
		order.setPayId(1L);
        order.setOrderId(1L);
        order.setCouponNo("111");
        couponNo.setCodeGetTime(new Date());
	}
	
	
	/**
	 *  根据订单id赠送优惠劵信息
	 */
	@Test
	public void testModifyCouponByOrderId()
	{
		order.setBusinessId(0L);
		PointSet pointSet  = new PointSet();
		pointSet.setIsOpen("1");
		orderMapperMock.returns(order).orderDetail(1L);
		orderGoodsMapperMock.returns(orderGoodsList).selectOrderGoodsList(1L);
		goodsProductServiceMock.returns(new GoodsProductDetailViewVo()).queryViewVoByProductId(1L);
		marketingServiceMock.returns(new Marketing()).marketingDetail(1L);
		orderGoodsInfoCouponMapperMock.returns(orderGoodsInfoCouponList).selectOrderGoodsInfoCoupon(1L);
		couponServiceMock.returns(new Coupon()).searchCouponById(1L);
		couponServiceMock.returns(1).giveCusCoupon("1", 1L);
		pointSetServiceMock.returns(pointSet).findPointSet();
		customerPointServiceMapperMock.returns(1).addIntegralByType(1L, "6",1D,"");
		customerConsumeMapperMock.returns(1).insertSelective(new CustomerConsume());
		assertEquals(0, orderCouponService.modifyCouponByOrderId(1L, 1L));
	}
	
	/**
	 * 根据订单id查询到优惠劵劵码，修改优惠劵
	 */
	@Test
	public void testModifyCouponStatus()
	{
		List<CalcStockUtil> list = new ArrayList<>();
		list.add(new CalcStockUtil());
		orderServiceMock.returns(order).getPayOrder(1L);
		//couponServiceMock.returns(1).returnCouponNo("1");
		orderGoodsMapperMock.returns(orderGoodsList).selectOrderGoodsList(1L);
		goodsProductServiceMock.returns(new GoodsProductDetailViewVo()).queryViewVoByProductId(1L);
		marketingServiceMock.returns(new Marketing()).marketingDetail(1L);
		orderGoodsInfoCouponMapperMock.returns(orderGoodsInfoCouponList).selectOrderGoodsInfoCoupon(1L);
		couponServiceMock.returns(new Coupon()).searchCouponById(1L);
		couponServiceMock.returns(1).modifyNoStatus("1", "0");
		goodsProductServiceMock.returns(1).plusStock(list);
		assertEquals(0, orderCouponService.modifyCouponStatus(1L));
		
	}

    /**
     * 退货流程确认收货使用
     */
    @Test
    public void testModifyStock(){
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        orderGoodsList.add(orderGoods);
        orderGoodsMapperMock.returns(orderGoodsList).selectOrderGoodsList(1L);
        goodsProductServiceMock.returns(new GoodsProductDetailViewVo()).queryViewVoByProductId(1L);
        marketingServiceMock.returns(new Marketing()).marketingDetail(1L);
        List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = new ArrayList<>();
        orderGoodsInfoCouponList.add(orderGoodsInfoCoupon);
        orderGoodsInfoCouponMapperMock.returns(orderGoodsInfoCouponList).selectOrderGoodsInfoCoupon(1L);
        couponServiceMock.returns(new Coupon()).searchCouponById(1L);
        couponServiceMock.returns(1).modifyNoStatus("1","0");
        goodsProductServiceMock.returns(1).plusStock(new ArrayList<CalcStockUtil>());
        assertEquals(0,orderCouponService.modifyStock(1L));
    }

    /**
     * 根据订单id退货时退库存
     */
    @Test
    public void testModifyStocks(){
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        orderGoodsList.add(orderGoods);
        orderGoodsMapperMock.returns(orderGoodsList).selectOrderGoodsList(1L);
        goodsProductServiceMock.returns(new GoodsProductDetailViewVo()).queryViewVoByProductId(1L);
        goodsProductServiceMock.returns(1).plusStock(new ArrayList<CalcStockUtil>());
        assertEquals(0, orderCouponService.modifyStocks(1L));
    }

    /**
     * 订单取消时，修改订单使用的优惠券状态
     */
    @Test
    public void testModifyCouponStatusNew(){

        orderServiceMock.returns(order).getPayOrder(1L);
        couponNoServiceMock.returns(couponNo).selectCouponNoByCode("111");
        couponServiceMock.returns(1).modifyNoStatus("111","1");
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        orderGoodsList.add(orderGoods);
        orderGoodsMapperMock.returns(orderGoodsList).selectOrderGoodsList(1L);
        goodsProductServiceMock.returns(new GoodsProductDetailViewVo()).queryViewVoByProductId(1L);
        goodsProductServiceMock.returns(1).plusStock(new ArrayList<CalcStockUtil>());
        assertEquals(0, orderCouponService.modifyCouponStatusNew(1L));
    }


}
