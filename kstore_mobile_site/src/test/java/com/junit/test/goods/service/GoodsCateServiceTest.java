package com.junit.test.goods.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.m.goods.dao.GoodsCateMapper;
import com.ningpai.m.goods.service.GoodsCateService;
import com.ningpai.m.goods.service.impl.GoodsCateServiceImpl;
import com.ningpai.m.goods.vo.GoodsBreadCrumbVo;
import com.ningpai.m.goods.vo.GoodsCateVo;

/**
 * 商品分类serivce接口测试
 * @author djk
 *
 */
public class GoodsCateServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private GoodsCateService goodsCateService = new GoodsCateServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsCateMapper> goodsCateMapperMock;

    /**
     * 查询所有的商品分类测试
     */
    @Test
    public void testQueryAllCate() {
        GoodsCateVo topParent = new GoodsCateVo();
        topParent.setCatId(1L);
        GoodsCateVo goodsCateVo = new GoodsCateVo();
        goodsCateVo.setCatParentId(1L);
        goodsCateVo.setCatId(2L);
        List<GoodsCateVo> allCate = new ArrayList<>();
        allCate.add(goodsCateVo);
        goodsCateMapperMock.returns(topParent).queryTopParent();
        goodsCateMapperMock.returns(allCate).queryAllCate();
        assertNotNull(goodsCateService.queryAllCate());
    }

    /**
     * 根据分类ID查询分类信息,并且计算好所有的子级关系测试
     */
    @Test
    public void testQueryCateById() {
        GoodsCateVo topParent = new GoodsCateVo();
        topParent.setCatId(1L);
        GoodsCateVo goodsCateVo = new GoodsCateVo();
        goodsCateVo.setCatParentId(1L);
        goodsCateVo.setCatId(2L);
        List<GoodsCateVo> allCate = new ArrayList<>();
        allCate.add(goodsCateVo);
        goodsCateMapperMock.returns(topParent).queryCateVoByCatId(1L);
        goodsCateMapperMock.returns(allCate).queryAllCate();
        assertNotNull(goodsCateService.queryCateById(1L));
    }

    /**
     * 根据类型ID查询VO信息,仅是查询当前分类本身以及父分类测试
     */
    @Test
    public void testQueryCateByCatId() {
        goodsCateMapperMock.returns(new GoodsCateVo()).queryCateVoByCatId(1L);
        assertNotNull(goodsCateService.queryCateByCatId(1L));
    }

    /**
     *  计算出分类下所有子分类的ID测试
     */
    @Test
    public void testCalcAllSonCatIds() {
        GoodsCateVo cateVos = new GoodsCateVo();
        cateVos.setCatId(2L);
        List<GoodsCateVo> list = new ArrayList<>();
        list.add(cateVos);
        List<Long> catIds = new ArrayList<>();
        catIds.add(1L);
        GoodsCateVo cateVo = new GoodsCateVo();
        cateVo.setCateVos(list);
        cateVo.setCatId(1L);
        assertNull(goodsCateService.calcAllSonCatIds(cateVo, catIds));
    }

    /**
     *  根据分类ID查询下一级的子分类Id集合测试
     */
    @Test
    public void testQueryCatIdsByCatId() {
        assertEquals(1, goodsCateService.queryCatIdsByCatId(1L).size());
    }

    /**
     * 根据分类ID查询分类和父分类信息测试
     */
    @Test
    public void testQueryCateAndParCateByCatId() {
        goodsCateMapperMock.returns(new GoodsCateVo()).queryCateAndParCateByCatId(1L);
        assertNotNull(goodsCateService.queryCateAndParCateByCatId(1L));
    }

    /**
     * 根据分类ID查询面包屑辅助Bean测试
     */
    @Test
    public void testQueryBreadCrubByCatId() {
        goodsCateMapperMock.returns(new GoodsBreadCrumbVo()).queryBreadCrubByCatId(1L);
        assertNotNull(goodsCateService.queryBreadCrubByCatId(1L));
    }

    /**
     * 根据分类ID计算列表页的URL测试
     */
    @Test
    public void testCalcCatUrl() {
        assertEquals("0-1", goodsCateService.calcCatUrl(1L, "1"));
    }

    /**
     * 根据分类ID计算列表页的URL测试
     */
    @Test
    public void testCalcCatUrl2() {
        assertEquals("0-0", goodsCateService.calcCatUrl(1L, "2"));
    }
}
