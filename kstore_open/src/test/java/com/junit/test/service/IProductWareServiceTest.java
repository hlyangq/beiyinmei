package com.junit.test.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.api.dao.IProductWareMapper;
import com.ningpai.api.service.IProductWareService;
import com.ningpai.api.service.impl.ProductWareServiceImpl;

/**
 * 开放接口-商品库存测试
 * @author djk
 *
 */
public class IProductWareServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private IProductWareService iProductWareService = new ProductWareServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<IProductWareMapper> iProductWareMapperMock;
    
    /**
     *  根据货号查询库存测试
     */
    @Test
    public void testGet()
    {
        assertEquals("{\"item\":[],\"total_results\":0}", iProductWareService.get("a"));
    }
    
    /**
     * 添加库存测试
     */
    @Test
    public void testAddStock()
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("goodsInfoItemNo","1");
        map.put("identifyId","1");
        map.put("count",1L);
        iProductWareMapperMock.returns(1).addStock(map);
        assertEquals(1, iProductWareService.addStock("1", "1", 1L));
    }
    
    /**
     * 删除库存测试
     */
    @Test
    public void testMinStock()
    { 
        //设置容器
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("goodsInfoItemNo","1");
        map.put("identifyId","1");
        map.put("count",1L);
        iProductWareMapperMock.returns(1).minStock(map);
        assertEquals(1, iProductWareService.minStock("1", "1", 1L));
    }
}
