package com.junit.test.site.thirdseller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.site.thirdseller.dao.ThirdCateMapper;
import com.ningpai.site.thirdseller.service.ThirdCateService;
import com.ningpai.site.thirdseller.service.impl.ThirdCateServiceImpl;
import com.ningpai.site.thirdseller.vo.ThirdCateVo;

/**
 * 第三方店铺分类Service实现测试
 * @author djk
 *
 */
public class ThirdCateServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private ThirdCateService thirdCateService = new ThirdCateServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ThirdCateMapper> thirdCateMapperMock;
    
    /**
     * 第三方商家分类Vo
     */
    private ThirdCateVo thirdCateVo = new ThirdCateVo();
    
    /**
     * 第三方商家分类Vo
     */
    private ThirdCateVo thirdCateVo2 = new ThirdCateVo();
    
    /**
     * 所有第三方商家分类Vo集合
     */
    private List<ThirdCateVo> allThirdCateVoLists = new ArrayList<>();
    /**
     * 第三方商家分类Vo集合
     */
    private List<ThirdCateVo> thirdCateVoLists = new ArrayList<>();
    
    @Override
    protected void setUp() throws Exception 
    {
    	thirdCateVo2.setCatParentId(1L);
    	thirdCateVo2.setCatId(2L);
    	thirdCateVo.setCatId(1L);
    	allThirdCateVoLists.add(thirdCateVo2);
    	thirdCateVoLists.add(thirdCateVo);
    }
    
    /**
     * 根据id获取单个店铺的信息测试
     */
    @Test
    public void testSelectByCustomerId()
    {
    	thirdCateMapperMock.returns(1L).selectByCustomerId(1L);
    	Long result = 1L;
    	assertEquals(result, thirdCateService.selectByCustomerId(1L));
    }
    
    /**
     * 查询所有的分类信息测试
     */
    @Test
    public void testGetAllCalcThirdCate()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("thirdId", 1L);
    	thirdCateMapperMock.returns(thirdCateVoLists).queryAllThirdCate(map);
    	thirdCateMapperMock.returns(allThirdCateVoLists).queryAllCateForList(1L);
    	assertEquals(1, thirdCateService.getAllCalcThirdCate(1L).size());
    }
    
    /**
     * 查询该商家是否被删除测试
     */
    @Test
    public void testFindStoreFlag()
    {
    	thirdCateMapperMock.returns("1").findStoreFlag(1L);
    	assertEquals("1", thirdCateService.findStoreFlag(1L));
    }
    
    /**
     * 计算第三方店家的分类关系测试
     */
    @Test
    public void testCalcCateVo()
    {
    	assertEquals(1, thirdCateService.calcCateVo(1L, allThirdCateVoLists).size());
    }
    
    /**
     * 根据第三方ID。分类ID 递归得到所有子级分类集合测试
     */
    @Test
    public void testCalcCateVo2()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("thirdId", 1L);
        thirdCateMapperMock.returns(allThirdCateVoLists).queryAllThirdCate(paramMap);
    	assertEquals(2, thirdCateService.calcCateVo(1L, 1L).size());
    }
    
    
    /**
     * 查询所有 第三方分类测试
     */
    @Test
    public void testQueryAllThirdCate()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("thirdId", 1L);
        thirdCateMapperMock.returns(thirdCateVoLists).queryAllThirdCate(paramMap);
    	assertEquals(1, thirdCateService.queryAllThirdCate(1L).size());
    }
    
    /**
     * 据类型ID查询VO信息,仅是查询当前分类本身以及父分类测试
     */
    @Test
    public void testQueryThirdCateById()
    {
    	thirdCateMapperMock.returns(thirdCateVo).selectByPrimaryKey(1L);
    	assertNotNull(thirdCateService.queryThirdCateById(1L));
    }
    
    /**
     * 根据父类id查询子级分类测试
     */
    @Test
    public void testueryThirdCateByPraentCateId()
    {
    	thirdCateMapperMock.returns(thirdCateVo).selectByPrimaryKey(1L);
    	assertNotNull(thirdCateService.queryThirdCateByPraentCateId(1L, 1L));
    }
}
