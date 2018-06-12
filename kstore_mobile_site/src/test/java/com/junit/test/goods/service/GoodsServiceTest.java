package com.junit.test.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.common.lucene.main.LuceneIKUtil;
import com.ningpai.goods.bean.GoodsBrand;
import com.ningpai.goods.bean.GoodsSpecDetail;
import com.ningpai.goods.bean.GoodsTypeExpandParamValue;
import com.ningpai.goods.vo.GoodsSpecVo;
import com.ningpai.goods.vo.GoodsTypeSpecVo;
import com.ningpai.m.goods.dao.GoodsMapper;
import com.ningpai.m.goods.service.GoodsCateService;
import com.ningpai.m.goods.service.GoodsService;
import com.ningpai.m.goods.service.impl.GoodsServiceImpl;
import com.ningpai.m.goods.util.GoodsSiteSearchBean;
import com.ningpai.m.goods.vo.GoodsCateVo;
import com.ningpai.m.goods.vo.GoodsDetailVo;
import com.ningpai.m.goods.vo.GoodsListScreenVo;
import com.ningpai.m.goods.vo.GoodsTypeBrandVo;
import com.ningpai.m.goods.vo.GoodsTypeExpandParamVo;
import com.ningpai.m.goods.vo.GoodsTypeVo;
import com.ningpai.util.PageBean;

/**
 * 商品Service测试
 * @author djk
 *
 */
public class GoodsServiceTest extends UnitilsJUnit3 {
    /**
     * 需要测试的接口类
     */
    @TestedObject
    private GoodsService goodsService = new GoodsServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<LuceneIKUtil> luceneIKUtilMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsMapper> goodsMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsCateService> goodsCateServiceMock;

    /**
     * 分页辅助类
     */
    private PageBean pb = new PageBean();

    /**
     * 查询商品的帮助Bean
     */
    private GoodsSiteSearchBean searchBean = new GoodsSiteSearchBean();

    /**
     * 查询商品列表测试
     */
    @Test
    public void testSearchGoods() {
        List<String> productIds = new ArrayList<>();
        productIds.add("1");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("productIds", productIds);
        searchBean.setTitle("1");
        luceneIKUtilMock.returns(resultMap).dirceotry("1");
        assertEquals(0, goodsService.searchGoods(pb, searchBean).getList().size());
    }

    /**
     * 查询最新的三条记录测试
     */
    @Test
    public void testQueryTopThreeNew() {
        assertEquals(0, goodsService.queryTopThreeNew(1L).size());
    }

    /**
     *  根据参数和分类ID查询商品列表测试
     */
    @Test
    public void testSearchGoods2() {
        GoodsCateVo goodsCateVo = new GoodsCateVo();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchBean", searchBean);
        goodsCateServiceMock.returns(goodsCateVo).queryCateById(1L);
        assertEquals(0, goodsService.searchGoods(pb, searchBean, 1L).getList().size());
    }

    /**
     * 根据扩展参数和分类ID查询商品列表测试
     */
    @Test
    public void testSearchGoods3()
    {
        String[] params = {"b1","e1","s1"};
        assertEquals(0, goodsService.searchGoods(pb, searchBean, 1L, params, "1").getList().size());
    }
    
    /**
     * 根据商品ID查询商品的详细信息测试
     */
    @Test
    public void testQueryGoodsDetailVoByGoodsId() {
        goodsMapperMock.returns(new GoodsDetailVo()).queryGoodsDetailVoByGoodsId(1L);
        assertNotNull(goodsService.queryGoodsDetailVoByGoodsId(1L));
    }

    /**
     * 根据商品详细信息返回默认的货品信息测试
     */
    @Test
    public void testReturnDefaultGoodsProduct() {
        assertNotNull(goodsService.returnDefaultGoodsProduct(new GoodsDetailVo()));
    }

    /**
     * 根据传递的参数和类型Vo计算已经选择的条件测试
     * 参数为b 的场景
     */
    @Test
    public void testCalcScreenParam() {
        GoodsBrand goodsBrand = new GoodsBrand();
        goodsBrand.setBrandId(1L);
        GoodsTypeBrandVo goodsTypeBrandVo = new GoodsTypeBrandVo();
        goodsTypeBrandVo.setBrand(goodsBrand);
        List<GoodsTypeBrandVo> brands = new ArrayList<>();
        brands.add(goodsTypeBrandVo);
        GoodsTypeVo goodsTypeVo = new GoodsTypeVo();
        goodsTypeVo.setBrands(brands);
        String[] params = { "b1" };
        assertEquals(1, goodsService.calcScreenParam(params, goodsTypeVo).size());
    }

    /**
     * 根据传递的参数和类型Vo计算已经选择的条件测试
     * 参数为e的场景
     */
    @Test
    public void testCalcScreenParam2() {
        GoodsTypeExpandParamValue goodsTypeExpandParamValue = new GoodsTypeExpandParamValue();
        goodsTypeExpandParamValue.setExpandparamValueId(1L);
        List<GoodsTypeExpandParamValue> valueList = new ArrayList<>();
        valueList.add(goodsTypeExpandParamValue);
        GoodsTypeExpandParamVo goodsTypeExpandParamVo = new GoodsTypeExpandParamVo();
        goodsTypeExpandParamVo.setValueList(valueList);
        goodsTypeExpandParamVo.setExpandparamId(1L);
        List<GoodsTypeExpandParamVo> expandParams = new ArrayList<>();
        expandParams.add(goodsTypeExpandParamVo);
        String[] params = { "e1" };
        GoodsTypeVo goodsTypeVo = new GoodsTypeVo();
        goodsTypeVo.setExpandParams(expandParams);
        assertEquals(1, goodsService.calcScreenParam(params, goodsTypeVo).size());
    }

    /**
     * 根据传递的参数和类型Vo计算已经选择的条件测试
     * 
     */
    @Test
    public void testCalcScreenParam3() {
        List<Object> lists = new ArrayList<>();
        GoodsSpecDetail specDetails = new GoodsSpecDetail();
        specDetails.setSpecDetailId(1L);
        lists.add(specDetails);
        GoodsSpecVo goodsSpec = new GoodsSpecVo();
        goodsSpec.setSpecDetails(lists);
        goodsSpec.setSpecId(1L);
        GoodsTypeSpecVo goodsTypeSpecVo = new GoodsTypeSpecVo();
        goodsTypeSpecVo.setGoodsSpec(goodsSpec);
        List<GoodsTypeSpecVo> specVos = new ArrayList<>();
        specVos.add(goodsTypeSpecVo);
        GoodsTypeVo goodsTypeVo = new GoodsTypeVo();
        goodsTypeVo.setSpecVos(specVos);
        String[] params = { "s1" };
        assertEquals(1, goodsService.calcScreenParam(params, goodsTypeVo).size());
    }

    /**
     * 根据计算好的选中的参数和类型Vo计算vo中需要显示的信息测试
     * 类型是品牌的场景
     */
    @Test
    public void testCalcTypeVo() {
        GoodsListScreenVo goodsListScreenVo = new GoodsListScreenVo();
        goodsListScreenVo.setType("brand");
        GoodsTypeVo typeVo = new GoodsTypeVo();
        List<GoodsListScreenVo> screenList = new ArrayList<>();
        screenList.add(goodsListScreenVo);
        assertNotNull(goodsService.calcTypeVo(screenList, typeVo));
    }
    
    /**
     * 根据计算好的选中的参数和类型Vo计算vo中需要显示的信息测试
     * 类型是扩展参数的场景
     */
    @Test
    public void testCalcTypeVo2() {
        GoodsTypeExpandParamVo goodsTypeExpandParamVo = new GoodsTypeExpandParamVo();
        goodsTypeExpandParamVo.setExpandparamId(1L);
        List<GoodsTypeExpandParamVo> expandParams = new ArrayList<>();
        expandParams.add(goodsTypeExpandParamVo);
        GoodsListScreenVo goodsListScreenVo = new GoodsListScreenVo();
        goodsListScreenVo.setType("expand");
        goodsListScreenVo.setParentId("1");
        GoodsTypeVo typeVo = new GoodsTypeVo();
        typeVo.setExpandParams(expandParams);
        List<GoodsListScreenVo> screenList = new ArrayList<>();
        screenList.add(goodsListScreenVo);
        assertNotNull(goodsService.calcTypeVo(screenList, typeVo));
    }
    
    /**
     * 根据计算好的选中的参数和类型Vo计算vo中需要显示的信息测试
     * 类型是spec的场景
     */
    @Test
    public void testCalcTypeVo3() {
        GoodsSpecVo goodsSpec = new GoodsSpecVo();
        goodsSpec.setSpecId(1L);
        GoodsTypeSpecVo goodsTypeSpecVo = new GoodsTypeSpecVo();
        goodsTypeSpecVo.setGoodsSpec(goodsSpec);
        List<GoodsTypeSpecVo> specVos = new ArrayList<>();
        specVos.add(goodsTypeSpecVo);
        GoodsListScreenVo goodsListScreenVo = new GoodsListScreenVo();
        goodsListScreenVo.setType("spec");
        goodsListScreenVo.setParentId("1");
        GoodsTypeVo typeVo = new GoodsTypeVo();
        typeVo.setSpecVos(specVos);
        List<GoodsListScreenVo> screenList = new ArrayList<>();
        screenList.add(goodsListScreenVo);
        assertNotNull(goodsService.calcTypeVo(screenList, typeVo));
    }

}
