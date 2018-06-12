package com.junit.test.site.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.order.bean.Order;
import com.ningpai.site.order.service.IPayService;
import com.ningpai.site.order.service.SiteOrderService;
import com.ningpai.site.order.service.impl.PayServiceImpl;
import com.ningpai.system.bean.Pay;

/**
 * 支付用service测试
 * @author djk
 *
 */
public class IPayServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private IPayService tIPayService = new PayServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<SiteOrderService> siteOrderServiceMock;
    
    /**
     * 支付宝支付测试
     */
    @Test
    public void testGetAlipay()
    {
    	Order order1 = new Order();
    	Order order2 = new Order();
    	order2.setOrderPrice(new BigDecimal(1));
    	order2.setOrderLinePay("1");
    	order1.setOrderPrice(new BigDecimal(1));
    	order1.setOrderLinePay("1");
    	List<Order> orders = new ArrayList<>();
    	orders.add(order1);
    	orders.add(order2);
    	Pay p = new Pay();
    	Order order = new Order();
    	order.setOrderOldCode("1");
		order.setOrderCode("A");
    	siteOrderServiceMock.returns(orders).getPayOrderByOldCode("1");
    	assertNotNull(tIPayService.getAlipay(p, order, "a", 1L, BigDecimal.ONE));
    }
}
