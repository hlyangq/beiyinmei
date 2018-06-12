package com.junit.test.site.returns.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.BackOrderLog;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.dao.BackOrderLogMapper;
import com.ningpai.order.dao.BackOrderMapper;
import com.ningpai.order.service.OrderService;
import com.ningpai.site.returns.bean.BackOrderGeneral;
import com.ningpai.site.returns.dao.ReturnGoodsMapper;
import com.ningpai.site.returns.service.ReturnGoodsService;
import com.ningpai.site.returns.service.impl.ReturnGoodsServiceImpl;

/**
 * 退货记录测试
 * @author djk
 *
 */
public class ReturnGoodsServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private ReturnGoodsService returnGoodsService = new ReturnGoodsServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ReturnGoodsMapper> returnGoodsMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderService> orderServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<BackOrderMapper> backOrderMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<BackOrderLogMapper> backOrderLogMapperMock;
    
    /**
     * 保存退单物流信息测试
     */
    @Test
    public void testSaveBackOrderGeneral()
    {
    	Order order = new Order();
    	order.setBusinessId(1L);
    	List<BackOrder> orders = new ArrayList<>();
    	orders.add(new BackOrder());
    	returnGoodsMapperMock.returns(orders).selectBackOrderId("1");
    	returnGoodsMapperMock.returns(1).saveGeneral(new BackOrderGeneral());
    	orderServiceMock.returns(order).getPayOrderByCode("1");
    	returnGoodsMapperMock.returns(1).updateOrder(order);
    	backOrderMapperMock.returns(1).updateByPrimaryKeySelective(new BackOrder());
    	backOrderLogMapperMock.returns(1).insert(new BackOrderLog());
    	assertEquals(1, returnGoodsService.saveBackOrderGeneral("1", "1", "1"));
    }
    
    /**
     * 新增一条退货记录 ，更改交易表状态退单金额，更新商品信息表测试
     */
    @Test
    public void testSaveReturnGoodsDetail()
    {
    	List<OrderGoods> orderGoods = new ArrayList<>();
    	orderGoods.add(new OrderGoods());
    	Order order = new Order();
    	order.setOrderId(1L);
    	returnGoodsMapperMock.returns(order).selectOrderById(1L);
    	returnGoodsMapperMock.returns(1).saveBackOrder(new BackOrder());
    	returnGoodsMapperMock.returns(1).updateOrder(order);
    	returnGoodsMapperMock.returns(orderGoods).selectOrderGoodsById(1L);
    	returnGoodsMapperMock.returns(1).updateOrderGoods(new OrderGoods());
    	Boolean result = true;
    	assertEquals(result, returnGoodsService.saveReturnGoodsDetail(1L, "1", 1L));
    }
}
