package com.junit.test.site.threepart.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.site.threepart.bean.ThreePart;
import com.ningpai.site.threepart.dao.ThreePartMapper;
import com.ningpai.site.threepart.service.ThreePartService;
import com.ningpai.site.threepart.service.impl.ThreePartServiceImpl;

/**
 * 第三方信息 测试
 * @author djk
 *
 */
public class ThreePartServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private ThreePartService threePartService = new ThreePartServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ThreePartMapper> threePartMapperMock;
    
    /**
     * 第三部分
     */
    private ThreePart threePart = new ThreePart();
    
    /**
     * 查询第三方信息测试
     */
    @Test
    public void testSelectThreePart()
    {
    	threePartMapperMock.returns(threePart).selectThreePart("1");
    	assertNotNull(threePartService.selectThreePart("1"));
    }
    
    /**
     * 插入第三方测试
     */
    @Test
    public void testInsertThreePart()
    {
    	threePartMapperMock.returns(1).insertThreePart(threePart);
    	assertEquals(1, threePartService.insertThreePart(threePart));
    }
}
