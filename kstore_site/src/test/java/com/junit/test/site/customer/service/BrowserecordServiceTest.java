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

import com.ningpai.site.customer.bean.Browserecord;
import com.ningpai.site.customer.mapper.BrowserecordMapper;
import com.ningpai.site.customer.service.BrowserecordService;
import com.ningpai.site.customer.service.impl.BrowserecordServiceImpl;

/**
 *  浏览记录接口
 * @author djk
 *
 */
public class BrowserecordServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private BrowserecordService browserecordService = new BrowserecordServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<BrowserecordMapper> browserecordMapperMock;
    
    /**
     * 查询浏览记录
     */
    @Test
    public void testSelectBrowserecord()
    {
    	List<Browserecord> lists = new ArrayList<>();
    	lists.add(new Browserecord());
    	browserecordMapperMock.returns(lists).selectBrowserecord(1L);
    	assertEquals(1, browserecordService.selectBrowserecord(1L).size());
    }
    
    /**
     * 根据主键删除测试
     */
    @Test
    public void testDeleteByPrimaryKey()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("likeId", 1L);
        map.put("customerId", 1L);
        browserecordMapperMock.returns(1).deleteByPrimaryKey(map);
    	assertEquals(1, browserecordService.deleteByPrimaryKey(1L, 1L));
    }
    
    /**
     * 根据货品编号删除测试
     */
    @Test
    public void testDeleteByGoodsInfoId()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("goodInfoId", 1L);
        map.put("customerId", 1L);
        browserecordMapperMock.returns(1).deleteByPrimaryKey(map);
    	assertEquals(1, browserecordService.deleteByGoodsInfoId(1L, 1L));
    }
}
