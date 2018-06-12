package com.junit.test.order;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.goods.service.GoodsProductService;
import com.ningpai.goods.vo.GoodsProductDetailViewVo;
import com.ningpai.m.order.service.OrderPayService;
import com.ningpai.m.order.service.impl.OrderPayServiceImpl;
import com.ningpai.m.weixin.util.WXSendMSG;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderExpress;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.bean.OrderGoodsInfoCoupon;
import com.ningpai.order.dao.OrderExpressMapper;
import com.ningpai.order.dao.OrderGoodsInfoCouponMapper;
import com.ningpai.order.dao.OrderGoodsMapper;
import com.ningpai.order.dao.OrderMapper;
import com.ningpai.order.service.OrderService;
import com.ningpai.other.bean.CustomerAllInfo;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderPayServiceTest
 *
 * @author djk
 * @date 2016/3/31
 */
public class OrderPayServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private OrderPayService orderPayService = new OrderPayServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerServiceMapper> customerServiceMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderService> orderServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderMapper> orderMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderGoodsMapper> orderGoodsMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<MarketingService> marketingServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsProductService> goodsProductServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderExpressMapper> orderExpressMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderGoodsInfoCouponMapper> orderGoodsInfoCouponMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CouponService> couponServiceMock;


    @Test
    public void testQueryGoodsProducts() {
        OrderGoodsInfoCoupon orderGoodsInfoCoupon = new OrderGoodsInfoCoupon();
        orderGoodsInfoCoupon.setCouponId(1L);
        List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = new ArrayList<>();
        orderGoodsInfoCouponList.add(orderGoodsInfoCoupon);
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderGoodsId(1L);
        orderGoods.setGoodsInfoId(1L);
        orderGoods.setGoodsMarketingId(1L);
        orderGoods.setHaveCouponStatus("1");
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        orderGoodsList.add(orderGoods);
        Order order =new Order();
        orderMapperMock.returns(order).orderDetail(1L);
        orderGoodsMapperMock.returns(orderGoodsList).selectOrderGoodsList(1L);
        orderExpressMapperMock.returns(new OrderExpress()).selectOrderExpress(1L);
        goodsProductServiceMock.returns(new GoodsProductDetailViewVo()).queryViewVoByProductId(1L);
        marketingServiceMock.returns(new Marketing()).marketingDetail(1L);
        orderGoodsInfoCouponMapperMock.returns(orderGoodsInfoCouponList).selectOrderGoodsInfoCoupon(1L);
        couponServiceMock.returns(new Coupon()).searchCouponById(1L);
        assertNotNull(orderPayService.queryGoodsProducts(1L));
    }


    @Test
    public void testNewQueryGoodsProducts() {
        OrderGoodsInfoCoupon orderGoodsInfoCoupon = new OrderGoodsInfoCoupon();
        orderGoodsInfoCoupon.setCouponId(1L);
        List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = new ArrayList<>();
        orderGoodsInfoCouponList.add(orderGoodsInfoCoupon);
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderGoodsId(1L);
        orderGoods.setGoodsInfoId(1L);
        orderGoods.setGoodsMarketingId(1L);
        orderGoods.setHaveCouponStatus("1");
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        orderGoodsList.add(orderGoods);
        Order order =new Order();
        orderMapperMock.returns(order).orderDetail(1L);
        orderGoodsMapperMock.returns(orderGoodsList).selectOrderGoodsList(1L);
        orderExpressMapperMock.returns(new OrderExpress()).selectOrderExpress(1L);
        goodsProductServiceMock.returns(new GoodsProductDetailViewVo()).queryViewVoByProductId(1L);
        marketingServiceMock.returns(new Marketing()).marketingDetail(1L);
        orderGoodsInfoCouponMapperMock.returns(orderGoodsInfoCouponList).selectOrderGoodsInfoCoupon(1L);
        couponServiceMock.returns(new Coupon()).searchCouponById(1L);
        assertNotNull(orderPayService.newQueryGoodsProducts(1L));
    }

}
