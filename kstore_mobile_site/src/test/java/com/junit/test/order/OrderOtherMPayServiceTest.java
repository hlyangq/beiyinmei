package com.junit.test.order;

import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.m.order.service.OrderOtherMPayService;
import com.ningpai.m.order.service.impl.OrderOtherMPayServiceImpl;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderOtherPay;
import com.ningpai.order.bean.OrderOtherPaySchedule;
import com.ningpai.order.service.OrderOtherPayScheduleService;
import com.ningpai.order.service.OrderOtherPayService;
import com.ningpai.order.service.OrderService;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

/**
 * OrderOtherMPayServiceTest
 *
 * @author djk
 * @date 2016/3/30
 */
public class OrderOtherMPayServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private OrderOtherMPayService orderOtherMPayService = new OrderOtherMPayServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderOtherPayScheduleService> orderOtherPayScheduleServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderOtherPayService> orderOtherPayServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<OrderService> orderServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<CustomerServiceMapper> customerServiceMapperMock;

    @Test
    public void testUpdateOrderOtherPay() {
        OrderOtherPaySchedule orderOtherPaySchedule = new OrderOtherPaySchedule();
        orderOtherPaySchedule.setOrderRemark("test");
        orderOtherPaySchedule.setOrderCode("1");
        MockHttpServletRequest request = new MockHttpServletRequest();
        orderOtherPayScheduleServiceMock.returns(new OrderOtherPaySchedule()).selectOrderOtherPayScheduleByOrderCode("1");
        orderOtherMPayService.updateOrderOtherPay(orderOtherPaySchedule, request);
    }

    @Test
    public void testUpdateOrderOtherPay2() {
        OrderOtherPaySchedule orderOtherPaySchedule = new OrderOtherPaySchedule();
        orderOtherPaySchedule.setOrderRemark("test");
        orderOtherPaySchedule.setOrderCode("1");
        MockHttpServletRequest request = new MockHttpServletRequest();
        orderOtherPayScheduleServiceMock.returns(null).selectOrderOtherPayScheduleByOrderCode("1");
        orderOtherMPayService.updateOrderOtherPay(orderOtherPaySchedule, request);
    }

    /**
     * 根据订单id查询代付信息以及代付成功信息测试
     */
    @Test
    public void testSelectOtherPay() {
        orderServiceMock.returns(new Order()).orderDetail(1L);
        assertNotNull(orderOtherMPayService.selectOtherPay(1L));
    }


    /**
     * 根据订单id查询单人代付信息测试
     */
    @Test
    public void testSelectOtherPaySingle() {
        Order order = new Order();
        order.setOrderCode("1");
        orderServiceMock.returns(order).orderDetail(1L);
        orderOtherPayScheduleServiceMock.returns(new OrderOtherPaySchedule()).selectOrderOtherPayScheduleByOrderCode("1");
        assertNotNull(orderOtherMPayService.selectOtherPaySingle(1L));
    }

    /**
     * 修改或添加单人代付信息测试
     */
    @Test
    public void testUpdateOrderOtherSingle() {
        OrderOtherPay orderOtherPay = new OrderOtherPay();
        orderOtherPay.setOrderRemark("1");
        MockHttpServletRequest request = new MockHttpServletRequest();
        orderOtherMPayService.updateOrderOtherSingle(orderOtherPay, 1L, request);
    }

    /**
     * 修改或添加单人代付信息测试
     */
    @Test
    public void testUpdateOrderOtherSingle2() {
        OrderOtherPay orderOtherPay = new OrderOtherPay();
        orderOtherPay.setOrderRemark("1");
        MockHttpServletRequest request = new MockHttpServletRequest();
        orderOtherPayServiceMock.returns(new OrderOtherPay()).queryOtherPayByOrderCode(orderOtherPay);
        orderOtherMPayService.updateOrderOtherSingle(orderOtherPay, 1L, request);
    }
}
