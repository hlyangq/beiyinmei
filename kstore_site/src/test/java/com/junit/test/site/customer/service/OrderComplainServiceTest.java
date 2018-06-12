package com.junit.test.site.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.site.customer.bean.OrderComplain;
import com.ningpai.site.customer.mapper.OrderComplainMapper;
import com.ningpai.site.customer.service.OrderComplainService;
import com.ningpai.site.customer.service.impl.OrderComplainServiceImpl;
import com.ningpai.util.PageBean;

/**
 * 订单投诉Service测试
 * @author djk
 *
 */
public class OrderComplainServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private OrderComplainService orderComplainService = new OrderComplainServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderComplainMapper> orderComplainMapperMock;
    
    /**
     * 添加订单投诉测试
     */
    @Test
    public void testAddComplain()
    {
    	orderComplainMapperMock.returns(1).insertSelective(new OrderComplain());
    	assertEquals(1, orderComplainService.addComplain(new OrderComplain()));
    }
    
    /**
     * 查询投诉列表测试
     */
    @Test
    public void testQueryComplainList()
    {
    	Map<String, Object> paramMap = new HashMap<>();
    	
    	PageBean pb = new PageBean();
    	paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
    	orderComplainMapperMock.returns(1L).searchComplainCount(paramMap);
    	orderComplainMapperMock.returns(lists).selectComplainList(paramMap);
    	assertEquals(1, orderComplainService.queryComplainList(paramMap, pb).getList().size());
    	
    }
}
