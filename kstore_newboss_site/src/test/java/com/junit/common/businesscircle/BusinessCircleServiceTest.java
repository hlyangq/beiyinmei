package com.junit.common.businesscircle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.businesscircle.bean.BusinessCircle;
import com.ningpai.businesscircle.dao.BusinessCircleMapper;
import com.ningpai.businesscircle.service.BusinessCircleService;
import com.ningpai.businesscircle.service.impl.BusinessCircleServiceImpl;
import com.ningpai.util.PageBean;

/**
 * 商圈接口service测试类
 * @author djk
 *
 */
public class BusinessCircleServiceTest extends UnitilsJUnit3
{

    /**
     * 需要测试的接口类
     */
    @TestedObject
	private BusinessCircleService businessCircleService = new BusinessCircleServiceImpl();
    
    /**
     * 模拟Mock
     */
    @InjectIntoByType
    private Mock<BusinessCircleMapper> businessCircleMapperMock;
    
    /**
     * 商圈实体类
     */
    private BusinessCircle businessCircle = new BusinessCircle();
    
    /**
     * 添加商圈测试
     */
    @Test
    public void testAddBusinessCircle()
    {
    	businessCircleMapperMock.returns(1).addBusinessCircle(businessCircle);
    	assertEquals(1, businessCircleService.addBusinessCircle(businessCircle));
    }
    
    /**
     * 修改商圈的开启状态测试
     */
    @Test
    public void testUpdateBusinessCircleById()
    {
    	businessCircleMapperMock.returns(1).updatebusinessCircleIsOpen(businessCircle);
    	assertEquals(1, businessCircleService.updateBusinessCircleById(1L, "1"));
    }
    
    /**
     * 删除商圈测试
     */
    @Test
    public void testDelBusinessCircle()
    {
    	businessCircleMapperMock.returns(1).delBusinessCircleById(1L);
    	assertEquals(1, businessCircleService.delBusinessCircle(1L));
    }
    
    /**
     * 修改商圈测试
     */
    @Test
    public void testUpdateBusinessCircle()
    {
    	businessCircleMapperMock.returns(1).updateBusinessCircle(businessCircle);
    	assertEquals(1, businessCircleService.updateBusinessCircle(businessCircle));
    }
	
    /**
     * 根据商圈名字获得商圈信息测试
     */
    @Test
    public void testFindBusinessCircleByName()
    {
    	businessCircleMapperMock.returns(businessCircle).findBusinessCircleByName("a");
    	assertNotNull(businessCircleService.findBusinessCircleByName("a"));
    }
    
    /**
     * 绑定商圈测试
     */
    @Test
    public void testBindBusinessCircle()
    {
    	businessCircleMapperMock.returns(businessCircle).findBusinessCircleById(1L);
    	businessCircleMapperMock.returns(1).updateBusinessCircle(businessCircle);
    	assertEquals(1, businessCircleService.bindBusinessCircle(1L, 1L));
    }
    
    /**
     * 获得未分配的商圈
     */
    @Test
    public void testGetAll()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessCircleIsOpen", "1");
        map.put("businessCircleThirdId", "1");
        businessCircleMapperMock.returns(new ArrayList<Object>()).findBusinessCirclesByMap(map);
    	assertEquals(0, businessCircleService.getAll("1", "1").size());
    }
    
    /**
     * 查询商圈信息(根据条件)分页测试
     */
    @Test
    public void testFindBusinessCircles()
    {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	PageBean pb = new PageBean();
    	map.put("businessCircleName", "%" + "1" + "%");
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        businessCircleMapperMock.returns( new ArrayList<>()).findBusinessCircles(map);
    	assertEquals(0, businessCircleService.findBusinessCircles(pb, "1,1", "1").getList().size());
    }
    
    /**
     * 查询商圈信息(根据条件)分页测试
     */
    @Test
    public void testFindBusinessCircles2()
    {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	PageBean pb = new PageBean();
    	map.put("provinceName", "%" + "1" + "%");
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        businessCircleMapperMock.returns( new ArrayList<>()).findBusinessCircles(map);
    	assertEquals(0, businessCircleService.findBusinessCircles(pb, "2,2", "1").getList().size());
    }
    
    /**
     * 查询商圈信息(根据条件)分页测试
     */
    @Test
    public void testFindBusinessCircles3()
    {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	PageBean pb = new PageBean();
    	map.put("cityName", "%" + "1" + "%");
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        businessCircleMapperMock.returns( new ArrayList<>()).findBusinessCircles(map);
    	assertEquals(0, businessCircleService.findBusinessCircles(pb, "3,3", "1").getList().size());
    }
    
    /**
     * 根据商家编号查询商圈信息测试
     */
    @Test
    public void testFindBusinessCircleByThirdId()
    {
    	businessCircleMapperMock.returns(businessCircle).findBusinessCircleById(1L);
    	assertNotNull(businessCircleService.findBusinessCircleByThirdId(1L));
    }
    
    /**
     * 根据商圈Id 查询商圈信息测试
     */
    @Test
    public void testFindBusinessCircleByBusinessCircleId()
    {
    	businessCircleMapperMock.returns(businessCircle).findBusinessCircleByBusinessCircleId(1L);
    	assertNotNull(businessCircleService.findBusinessCircleByBusinessCircleId(1L));
    }
}
