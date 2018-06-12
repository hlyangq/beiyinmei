package com.junit.test.customer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.comment.bean.Comment;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.m.customer.bean.CustomerPoint;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.customer.mapper.CustomerOrderMapper;
import com.ningpai.m.customer.service.CustomerOrderService;
import com.ningpai.m.customer.service.impl.CustomerOrderServiceImpl;
import com.ningpai.m.customer.vo.GoodsBean;
import com.ningpai.order.bean.Order;
import com.ningpai.order.dao.OrderMapper;
import com.ningpai.util.PageBean;

/**
 * 手机端会员订单Service测试
 * 
 * @author djk
 *
 */
public class CustomerOrderServiceTest extends UnitilsJUnit3 {
	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private CustomerOrderService customerOrderService = new CustomerOrderServiceImpl();

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerOrderMapper> customerOrderMapperMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<OrderMapper> orderMapperMock;
	
	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<CustomerMapper> customerMapperMock;

	/**
	 * 查询所有订单测试
	 */
	@Test
	public void testQueryAllMyOrders() {
		PageBean pb = new PageBean();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startRowNum", pb.getStartRowNum());
		paramMap.put("endRowNum", pb.getEndRowNum());
		customerOrderMapperMock.returns(new ArrayList<>()).queryAllMyOrders(
				paramMap);
		assertEquals(0, customerOrderService.queryAllMyOrders(paramMap, pb)
				.getList().size());
	}

	/**
	 * 根据订单编号查找订单信息测试
	 */
	@Test
	public void testQueryOrderByCustIdAndOrderId() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerId", 1L);
		paramMap.put("orderId", 1L);
		customerOrderMapperMock.returns(new Object()).queryOrderByParamMap(
				paramMap);
		assertNotNull(customerOrderService.queryOrderByCustIdAndOrderId(1L, 1L));
	}

	/**
	 * 查询通知内容数量 如 :待处理订单数量...测试
	 */
	@Test
	public void testSelectNotice() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerId", 1L);
		paramMap.put("flag", 0);
		paramMap.put("flag", 1);
		assertEquals(2, customerOrderService.selectNotice(1L).size());
	}

	/**
	 * 取消订单测试
	 */
	@Test
	public void testCancelOrder() {
		CustomerPoint customerPoint = new CustomerPoint();
		customerPoint.setPointSum(1L);
        Map<String, Object> pointparamMap = new HashMap<String, Object>();
        pointparamMap.put("customerId",1L);
		Order order  = new Order();
		order.setOrderIntegral(1L);
		order.setCustomerId(1L);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", 1L);
		paramMap.put("reason", "a");
		customerOrderMapperMock.returns(1).cancelOrder(paramMap);
		orderMapperMock.returns(order).orderDetail(1L);
		customerMapperMock.returns(customerPoint).selectCustomerPointByCustomerId(pointparamMap);
		assertEquals(0, customerOrderService.cancelOrder(1L, "a"));
	}

	/**
	 * 确认收货测试
	 */
	@Test
	public void testComfirmofGoods() {
		customerOrderMapperMock.returns(1).comfirmofGoods(1L);
		assertEquals(1, customerOrderService.comfirmofGoods(1L));
	}

	/**
	 * 添加商品评论测试
	 */
	@Test
	public void testAddGoodsComment() {
		customerOrderMapperMock.returns(1).addGoodsComment(new Comment());
		assertEquals(1, customerOrderService.addGoodsComment(new Comment()));
	}

	/**
	 * 添加商品评论测试 评分为1的场景
	 */
	@Test
	public void testAddGoodsComment1() {
		Comment comment = new Comment();
		comment.setCommentScore(new BigDecimal(1));
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertEquals(0,
				customerOrderService.addGoodsComment(request, comment, 1L));
	}

	/**
	 * 添加商品评论测试 评分为2的场景
	 */
	@Test
	public void testAddGoodsComment2() {
		Comment comment = new Comment();
		comment.setCommentScore(new BigDecimal(2));
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertEquals(0,
				customerOrderService.addGoodsComment(request, comment, 1L));
	}

	/**
	 * 添加商品评论测试 评分为4的场景
	 */
	@Test
	public void testAddGoodsComment3() {
		Comment comment = new Comment();
		comment.setCommentScore(new BigDecimal(4));
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertEquals(0,
				customerOrderService.addGoodsComment(request, comment, 1L));
	}

	/**
	 * 根据订单id和商品id查询待评价商品测试
	 */
	@Test
	public void testSelectByOrderIdAndGoodsId() {
		// 定义Map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerId", 1L);
		paramMap.put("goodsInfoId", 1L);
		paramMap.put("orderId", 1L);
		paramMap.put("commentId", 1L);
		customerOrderMapperMock.returns(new GoodsBean())
				.selectByOrderIdAndGoodsId(paramMap);
		assertNotNull(customerOrderService.selectByOrderIdAndGoodsId(1L, 1L,
				1L, 1L));
	}

	/**
	 * 根据orderId查询货品测试
	 */
	@Test
	public void testSelectByOrderId() {
		customerOrderMapperMock.returns(new ArrayList<>()).selectByOrderId(1l);
		assertEquals(0, customerOrderService.selectByOrderId(1L).size());
	}

	/**
	 * 根据订单类型查询订单数量测试
	 */
	@Test
	public void testSearchOrderCountByCount() {
		Long result = 1L;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", "1");
		customerOrderMapperMock.returns(1L).searchTotalCount(paramMap);
		assertEquals(result, customerOrderService.searchOrderCountByCount("1"));
	}

    /**
     * 查询所有待付款订单的数量
     */
    @Test
    public void testQueryNoMoneyOrderCount() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", "1");
        paramMap.put("customerId",1L);
        paramMap.put("date",null);
        customerOrderMapperMock.returns(1L).searchTotalCount(paramMap);
        assertNotNull(customerOrderService.queryNoMoneyOrderCount("", "1", 1L));
    }

    /**
     * 查询所有待收货订单的总量
     */
    @Test
    public void testQueryNoShowHuoOrderCount() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", "1");
        paramMap.put("customerId",1L);
        paramMap.put("date",null);
        customerOrderMapperMock.returns(1L).searchTotalCount(paramMap);
        assertNotNull(customerOrderService.queryNoShowHuoOrderCount("", "1", 1L));
    }

    /**
     * 查询所有待评价订单的总量
     */
    @Test
    public void testQueryNoCommentOrderCount() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", "1");
        paramMap.put("customerId",1L);
        paramMap.put("date",null);
        customerOrderMapperMock.returns(1L).searchTotalCount(paramMap);
        assertNotNull(customerOrderService.queryNoCommentOrderCount("", "1", 1L));
    }

    /**
     * 查询所有待评价订单的总量
     */
    @Test
    public void testQueryCancleOrderCount() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", "1");
        paramMap.put("customerId",1L);
        paramMap.put("date",null);
        customerOrderMapperMock.returns(1L).searchTotalCount(paramMap);
        assertNotNull(customerOrderService.queryCancleOrderCount("", "1", 1L));
    }
}
