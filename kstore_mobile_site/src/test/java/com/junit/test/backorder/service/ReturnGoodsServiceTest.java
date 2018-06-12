package com.junit.test.backorder.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.m.backorder.bean.BackOrderGeneral;
import com.ningpai.m.backorder.dao.ReturnGoodsMapper;
import com.ningpai.m.backorder.service.ReturnGoodsService;
import com.ningpai.m.backorder.service.impl.ReturnGoodsServiceImpl;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.BackOrderLog;
import com.ningpai.order.bean.Order;
import com.ningpai.order.dao.BackOrderLogMapper;
import com.ningpai.order.dao.BackOrderMapper;
import com.ningpai.order.service.OrderService;
import com.ningpai.util.PageBean;

/**
 * 退货记录测试
 * 
 * @author djk
 *
 */
public class ReturnGoodsServiceTest extends UnitilsJUnit3 {

	/**
	 * 需要测试的接口类
	 */
	@TestedObject
	private ReturnGoodsService returnGoodsService = new ReturnGoodsServiceImpl();

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<ReturnGoodsMapper> returnGoodsMapperMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<OrderService> orderServiceMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<BackOrderMapper> backOrderMapperMock;

	/**
	 * 模拟MOCK
	 */
	@InjectIntoByType
	private Mock<BackOrderLogMapper> backOrderLogMapperMock;

	/**
	 * 保存退单物流信息测试 business id 为0的情况
	 */
	@Test
	public void testSaveBackOrderGeneral() {
		Order order = new Order();
		order.setBusinessId(0L);
		BackOrder backOrder = new BackOrder();
		List<BackOrder> orders = new ArrayList<BackOrder>();
		orders.add(backOrder);
		returnGoodsMapperMock.returns(orders).selectBackOrderId("1");
		returnGoodsMapperMock.returns(1).saveGeneral(new BackOrderGeneral());
		orderServiceMock.returns(order).getPayOrderByCode("1");
		returnGoodsMapperMock.returns(1).updateOrder(order);
		backOrderMapperMock.returns(1).updateByPrimaryKeySelective(backOrder);
		backOrderLogMapperMock.returns(1).insert(new BackOrderLog());
		assertEquals(1, returnGoodsService.saveBackOrderGeneral("1", "1", "1"));
	}

	/**
	 * 保存退单物流信息测试 business id 不为0的情况
	 */
	@Test
	public void testSaveBackOrderGeneral1() {
		Order order = new Order();
		order.setBusinessId(1L);
		BackOrder backOrder = new BackOrder();
		List<BackOrder> orders = new ArrayList<BackOrder>();
		orders.add(backOrder);
		returnGoodsMapperMock.returns(orders).selectBackOrderId("1");
		returnGoodsMapperMock.returns(1).saveGeneral(new BackOrderGeneral());
		orderServiceMock.returns(order).getPayOrderByCode("1");
		returnGoodsMapperMock.returns(1).updateOrder(order);
		backOrderMapperMock.returns(1).updateByPrimaryKeySelective(backOrder);
		backOrderLogMapperMock.returns(1).insert(new BackOrderLog());
		assertEquals(1, returnGoodsService.saveBackOrderGeneral("1", "1", "1"));
	}

	/**
	 * 新增一条退货记录 ，更改交易表状态退单金额，更新商品信息表测试
	 */
	@Test
	public void testSaveReturnGoodsDetail() {

		Order order = new Order();
		returnGoodsMapperMock.returns(order).selectOrderById(1L);
		returnGoodsMapperMock.returns(1).saveBackOrder(new BackOrder());
		Boolean result = false;
		assertEquals(result,
				returnGoodsService.saveReturnGoodsDetail(1L, "1", 0L));
	}

	/**
	 * 查询当前会员的退单信息测试
	 */
	@Test
	public void testQueryAllBackOrders() {
		BackOrder backOrder = new BackOrder();
		backOrder.setBackGoodsIdAndSum("1,1-1,2");
		List<Object> backOrders = new ArrayList<>();
		backOrders.add(backOrder);
		Map<String, Object> paramMap = new HashMap<>();
		PageBean pageBean = new PageBean();
		paramMap.put("startRowNum", pageBean.getStartRowNum());
		paramMap.put("endRowNum", pageBean.getEndRowNum());
		returnGoodsMapperMock.returns(backOrders)
				.queryAllMyBackOrders(paramMap);
		assertEquals(1,
				returnGoodsService.queryAllBackOrders(paramMap, pageBean)
						.getList().size());
	}

}
