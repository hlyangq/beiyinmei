package com.junit.test.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.api.bean.OOrderAllInfo;
import com.ningpai.api.dao.IOrderMapper;
import com.ningpai.api.service.IOrderService;
import com.ningpai.api.service.impl.OrderServiceImpl;

/**
 * 开放接口--订单service测试
 * @author djk
 *
 */
public class IOrderServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private IOrderService iOrderService = new OrderServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<IOrderMapper> iOrderMapperMock;
    
    /**
     * 订单列表测试
     */
    @Test
    public void testList()
    {
        assertEquals("{\"item\":[],\"total_results\":0}", iOrderService.list(null, null));
    }
    
    /**
     * 订单详情测试
     */
    @Test
    public void testGet()
    {
        iOrderMapperMock.returns(new OOrderAllInfo()).get("a");
        assertEquals("{}", iOrderService.get("a"));
    }
    
}
