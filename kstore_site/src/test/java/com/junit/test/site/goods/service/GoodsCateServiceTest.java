package com.junit.test.site.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.goods.bean.GoodsCate;
import com.ningpai.goods.dao.GoodsTypeBrandMapper;
import com.ningpai.site.goods.dao.GoodsCateMapper;
import com.ningpai.site.goods.service.GoodsCateService;
import com.ningpai.site.goods.service.impl.GoodsCateServiceImpl;
import com.ningpai.site.goods.vo.GoodsBreadCrumbVo;
import com.ningpai.site.goods.vo.GoodsCateVo;

/**
 * 商品分类serivce接口测试
 * @author djk
 *
 */
public class GoodsCateServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private GoodsCateService goodsCateService = new GoodsCateServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsCateMapper> goodsCateMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsTypeBrandMapper> goodsTypeBrandMapperMock;
    
    /**
     * 商品分类实体类
     */
    private GoodsCate goodsCate = new GoodsCate();
    
    /**
     *  商品分类VO
     */
    private GoodsCateVo goodsCateVo = new GoodsCateVo();
    
    /**
     * 商品分类VO集合
     */
    private List<GoodsCateVo> allgoodsCateVos = new ArrayList<>();
    
    @Override
    protected void setUp() throws Exception 
    {
    	goodsCateVo.setCatParentId(0L);
    	goodsCateVo.setCatId(1L);
    	allgoodsCateVos.add(goodsCateVo);
    }
    
    
    /**
     *  查询3级分类的第一个分类测试
     */
    @Test
    public void testFindCid()
    {
    	goodsCateMapperMock.returns(goodsCate).findCid(1L);
    	assertNotNull(goodsCateService.findCid(1L));
    }
    
    /**
     * 查询分类测试
     */
    @Test
    public void testFindCatGrade()
    {
    	goodsCateMapperMock.returns(1).findCatGrade(1L);
    	assertEquals(1, goodsCateService.findCatGrade(1L));
    }
    
    /**
     * 查询所有的商品分类测试
     */
    @Test
    public void testQueryAllCate()
    {
    	GoodsCateVo topGoodsCateVo = new GoodsCateVo();
    	topGoodsCateVo.setCatId(0L);
    	goodsCateMapperMock.returns(topGoodsCateVo).queryTopParent();
    	goodsCateMapperMock.returns(allgoodsCateVos).queryAllCate();
    	
    	assertNotNull(goodsCateService.queryAllCate());
    }
    
    /**
     * 根据分类ID查询分类信息,并且计算好所有的子级关系测试
     */
    @Test
    public void testQueryCateById()
    {
    	GoodsCateVo topGoodsCateVo = new GoodsCateVo();
    	topGoodsCateVo.setCatId(0L);
    	goodsCateMapperMock.returns(topGoodsCateVo).queryCateVoByCatId(1L);
    	goodsCateMapperMock.returns(allgoodsCateVos).queryAllCate();
    	assertNotNull(goodsCateService.queryCateById(1L));
    }
    
    /**
     * 根据类型ID查询VO信息,仅是查询当前分类本身以及父分类测试
     */
    @Test
    public void testQueryCateByCatId()
    {
    	goodsCateMapperMock.returns(goodsCateVo).queryCateVoByCatId(1L);
    	assertNotNull(goodsCateService.queryCateByCatId(1L));
    }
    
    /**
     * 计算出分类下所有子分类的ID测试
     */
    @Test
    public void testCalcAllSonCatIds()
    {
    	List<GoodsCateVo> allgoodsCateVos = new ArrayList<>();
    	GoodsCateVo g1 = new GoodsCateVo();
    	g1.setCatId(2L);
    	allgoodsCateVos.add(g1);
    	goodsCateVo.setCateVos(allgoodsCateVos);
    	List<Long> catIds = new ArrayList<>();
    	assertEquals(null, goodsCateService.calcAllSonCatIds(goodsCateVo, catIds));
    }
    
    /**
     * 根据分类ID查询下一级的子分类Id集合测试
     */
    @Test
    public void testQueryCatIdsByCatId()
    {
    	goodsCateMapperMock.returns(allgoodsCateVos).querySonCateByCatId(1L);
    	goodsCateMapperMock.returns(goodsCate).queryCateByPrimaryKey(1L);
    	assertEquals(2, goodsCateService.queryCatIdsByCatId(1L).size());
    }
    
    /**
     * 返回根据分类ID查询下一级的子分类Id集合以及测试
     */
    @Test
    public void testQueryGoodsCateAndBrandsByCatId()
    {
    	goodsCateMapperMock.returns(goodsCateVo).queryCateVoByCatId(1L);
    	assertEquals(2, goodsCateService.queryGoodsCateAndBrandsByCatId(1L).size());
    }
    
    /**
     * 根据分类ID查询分类和父分类信息测试
     */
    @Test
    public void testQueryCateAndParCateByCatId()
    {
    	goodsCateMapperMock.returns(goodsCateVo).queryCateAndParCateByCatId(1L);
    	assertNotNull(goodsCateService.queryCateAndParCateByCatId(1L));
    }
    
    /**
     * 根据分类ID查询面包屑辅助Bean测试
     */
    @Test
    public void testQueryBreadCrubByCatId()
    {
    	goodsCateMapperMock.returns(new GoodsBreadCrumbVo()).queryBreadCrubByCatId(1L);
    	assertNotNull(goodsCateService.queryBreadCrubByCatId(1L));
    }
    
    /**
     * 根据分类ID计算列表页的URL测试
     */
    @Test
    public void testCalcCatUrl()
    {
    	goodsCateMapperMock.returns(1L).queryFirstCatIdByFirstCatId(1L);
    	assertEquals("1-1", goodsCateService.calcCatUrl(1L, "1"));
    }
    
    /**
     * 根据分类ID计算列表页的URL测试
     * level是2 的场景
     */
    @Test
    public void testCalcCatUrl2()
    {
    	assertEquals("0-0", goodsCateService.calcCatUrl(1L, "2"));
    }
    
    /**
     * 根据分类Name查询面包屑辅助Bean测试
     */
    @Test
    public void testQueryBreadCrubByCatName()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("catName", "a");
        goodsCateMapperMock.returns(new GoodsBreadCrumbVo()).queryBreadCrubByCatName(map);
    	assertNotNull(goodsCateService.queryBreadCrubByCatName("a"));
    }
}
