package com.junit.test.customer.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.m.customer.bean.OrderNotice;
import com.ningpai.m.customer.mapper.OrderNoticeMapper;
import com.ningpai.m.customer.service.OrderNoticeService;
import com.ningpai.m.customer.service.impl.OrderNoticeServiceImpl;
import com.ningpai.util.PageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手机端订单消息通知Service测试
 * 
 * @author djk
 *
 */
public class OrderNoticeServiceTest extends UnitilsJUnit3 {

	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private OrderNoticeService orderNoticeService = new OrderNoticeServiceImpl();

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<OrderNoticeMapper> orderNoticeMapperMock;

	/**
	 * 查询订单通知消息测试
	 */
	@Test
	public void testSelectOrderNotice() {
		PageBean pb = new PageBean();
		assertEquals(0, orderNoticeService.selectOrderNotice(1L, pb).getList()
				.size());
	}

	/**
	 * 查询未读订单通知数量测试
	 */
	@Test
	public void testGetIsNotReadCount() {
		orderNoticeMapperMock.returns(1L).selectNotRead(1L);
		Long result = 1L;
		assertEquals(result, orderNoticeService.getIsNotReadCount(1L));
	}

	/**
	 * 标记为已读测试
	 */
	@Test
	public void testReadNotice() {
		Long result = 0L;
		assertEquals(result, orderNoticeService.readNotice(1L));
	}

	/**
	 * 添加通知消息测试
	 */
	@Test
	public void testAddNotice() {
		orderNoticeMapperMock.returns(1).insertSelective(new OrderNotice());
		assertEquals(1, orderNoticeService.addNotice(new OrderNotice()));
	}

    /**
     * 添加通知消息测试
     */
    @Test
    public void testSelectOrderNotices() {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("customerId", 1L);
        List<Object> orderNotices = new ArrayList<Object>();
        orderNoticeMapperMock.returns(orderNotices).selectListNoPage(paramMap);
        assertNotNull( orderNoticeService.selectOrderNotices(1L));
    }
}
