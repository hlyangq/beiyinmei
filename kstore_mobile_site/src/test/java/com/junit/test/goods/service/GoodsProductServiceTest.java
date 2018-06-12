package com.junit.test.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.goods.bean.Goods;
import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.bean.WareHouse;
import com.ningpai.goods.dao.GoodsRelatedGoodsMapper;
import com.ningpai.goods.dao.GoodsReleExpandParamMapper;
import com.ningpai.goods.dao.GoodsReleParamMapper;
import com.ningpai.goods.dao.GoodsReleTagMapper;
import com.ningpai.goods.pub.GoodsPub;
import com.ningpai.goods.service.GoodsBrandService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.service.impl.GoodsGroupServiceImpl;
import com.ningpai.goods.vo.GoodsListVo;
import com.ningpai.goods.vo.GoodsRelatedGoodsVo;
import com.ningpai.m.goods.dao.GoodsProductMapper;
import com.ningpai.m.goods.service.GoodsCateService;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.service.impl.GoodsProductServiceImpl;
import com.ningpai.m.goods.vo.GoodsProductVo;

/**
 * 货品Service测试
 * @author djk
 *
 */
public class GoodsProductServiceTest extends UnitilsJUnit3 {
    /**
     * 需要测试的接口类
     */
    @TestedObject
    private GoodsProductService goodsProductService = new GoodsProductServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsProductMapper> goodsProductMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsCateService> goodsCateServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsRelatedGoodsMapper> goodsRelatedGoodsMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsReleTagMapper> goodsReleTagMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsBrandService> goodsBrandServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsReleExpandParamMapper> goodsReleExpandParamMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsReleParamMapper> goodsReleParamMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsRelatedGoodsMapper> godsRelatedGoodsMapperMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<ProductWareService> productWareServiceMock;

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsPub> goodsPubMock;

    /**
     * 根据商品ID查询货品列表测试
     */
    @Test
    public void testQueryAllProductListByGoodsId() {
        assertEquals(0, goodsProductService.queryAllProductListByGoodsId(1L).size());
    }

    /**
     * 根据货品ID查询货品信息测试
     */
    @Test
    public void testQueryProductByProductId() {
        goodsProductMapperMock.returns(new GoodsProductVo()).queryPrductByProductId(1L);
        assertNotNull(goodsProductService.queryProductByProductId(1L));
    }

    /**
     * 根据分类ID查询货品信息的集合测试
     */
    @Test
    public void testQueryTopSalesByCatIds() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("catId", 1L);
        map.put("rowCount", 1);
        goodsProductMapperMock.returns(new ArrayList<>()).queryTopSalesInfoByCatIds(map);
        assertEquals(0, goodsProductService.queryTopSalesByCatIds(1L, 1).size());
    }

    /**
     * 查询最近一月该分类下的热销商品测试
     */
    @Test
    public void testQueryHotSalesTopSix() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("catId", 1L);
        map.put("rowCount", 1);
        goodsProductMapperMock.returns(new ArrayList<>()).queryHotSalesByCatId(map);
        assertEquals(0, goodsProductService.queryHotSalesTopSix(1L, 1).size());
    }

    /**
     * 根据分类ID查询最新上架的货品测试
     */
    @Test
    public void testQueryTopNewByCatIds() {
        assertEquals(0, goodsProductService.queryTopNewByCatIds(1L, 1).size());
    }

    /**
     * 根据货品ID查询测试
     */
    @Test
    public void testQueryDetailBeanByProductId() {

        List<GoodsListVo> releatedGoods = new ArrayList<>();
        releatedGoods.add(new GoodsListVo());
        GoodsRelatedGoodsVo goodsRelatedGoodsVo = new GoodsRelatedGoodsVo();
        goodsRelatedGoodsVo.setGoodsId(1L);
        goodsRelatedGoodsVo.setReleatedGoods(releatedGoods);
        List<GoodsRelatedGoodsVo> relaProducts = new ArrayList<>();
        relaProducts.add(goodsRelatedGoodsVo);
        GoodsProductVo productVo = new GoodsProductVo();
        productVo.setGoods(new Goods());
        productVo.setIsThird("0");
        productVo.setGoodsId(1L);
        goodsProductMapperMock.returns(productVo).queryDetailByProductId(1L);
        goodsRelatedGoodsMapperMock.returns(relaProducts).queryAllByGoodsId(1L);
        goodsPubMock.returns(new GoodsGroupServiceImpl()).getGoodsGroupService();
        assertNotNull(goodsProductService.queryDetailBeanByProductId(1L, 1L));
    }

    /**
     * 根据货品ID查询测试
     */
    @Test
    public void testQueryDetailBeanByProductId2() {

        List<GoodsListVo> releatedGoods = new ArrayList<>();
        releatedGoods.add(new GoodsListVo());
        GoodsRelatedGoodsVo goodsRelatedGoodsVo = new GoodsRelatedGoodsVo();
        goodsRelatedGoodsVo.setGoodsId(1L);
        goodsRelatedGoodsVo.setReleatedGoods(releatedGoods);
        List<GoodsRelatedGoodsVo> relaProducts = new ArrayList<>();
        relaProducts.add(goodsRelatedGoodsVo);
        GoodsProductVo productVo = new GoodsProductVo();
        productVo.setGoods(new Goods());
        productVo.setIsThird("0");
        productVo.setGoodsId(1L);
        goodsProductMapperMock.returns(productVo).queryDetailByProductId(1L);
        goodsRelatedGoodsMapperMock.returns(relaProducts).queryAllByGoodsId(1L);
        goodsPubMock.returns(new GoodsGroupServiceImpl()).getGoodsGroupService();
        productWareServiceMock.returns(new ProductWare()).queryProductWareByProductIdAndDistinctId(1L, 1L);
        assertNotNull(goodsProductService.queryDetailBeanByProductId(1L, 1L));
    }

    /**
     * 保存商品咨询测试
     */
    @Test
    public void testSaveProductCommentAsk() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", 1);
        map.put("comment", "1");
        map.put("custId", 1L);
        map.put("productId", 1L);
        goodsProductMapperMock.returns(1).saveAskComment(map);
        assertEquals(1, goodsProductService.saveProductCommentAsk(1, "1", 1L, 1L));
    }

    /**
     * 根据分类ID查询最终购买的列表测试
     */
    @Test
    public void testBrowCatFinalBuyAndPrecent() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("catId", 1L);
        map.put("rowCount", 1L);
        goodsProductMapperMock.returns(new ArrayList<>()).browCatFinalBuyAndPrecent(map);
        assertEquals(0, goodsProductService.browCatFinalBuyAndPrecent(1L, 1L).size());
    }

    /**
     * 进行对比测试
     */
    @Test
    public void testExecCompProduct() {
        GoodsProductVo goodsProductVo = new GoodsProductVo();
        goodsProductVo.setGoods(new Goods());
        List<Long> productIds = new ArrayList<>();
        productIds.add(1L);
        goodsProductMapperMock.returns(goodsProductVo).queryDetailByProductId(1L);
        assertEquals(1, goodsProductService.execCompProduct(productIds).size());
    }

    /**
     * 进行对比,传递请求测试
     */
    @Test
    public void testExecCompProduct2() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("distinctId", 1L);
        GoodsProductVo goodsProductVo = new GoodsProductVo();
        goodsProductVo.setGoods(new Goods());
        goodsProductVo.setIsThird("0");
        List<Long> productIds = new ArrayList<>();
        productIds.add(1L);
        goodsProductMapperMock.returns(goodsProductVo).queryDetailByProductId(1L);
        assertEquals(1, goodsProductService.execCompProduct(productIds, request).size());
    }

    /**
     * 进行对比,传递请求测试
     */
    @Test
    public void testExecCompProduct3() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("distinctId", 1L);
        GoodsProductVo goodsProductVo = new GoodsProductVo();
        goodsProductVo.setGoods(new Goods());
        goodsProductVo.setIsThird("0");
        goodsProductVo.setGoodsInfoId(1L);
        List<Long> productIds = new ArrayList<>();
        productIds.add(1L);
        goodsProductMapperMock.returns(goodsProductVo).queryDetailByProductId(1L);
        productWareServiceMock.returns(new ProductWare()).queryProductWareByProductIdAndDistinctId(1L, 1L);
        assertEquals(1, goodsProductService.execCompProduct(productIds, request).size());
    }

    /**
     * 根据货品ID和查询行数查询销售最高的几条记录测试
     */
    @Test
    public void testQueryTopSalesByProductId() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", 1L);
        map.put("rowCount", 1);
        goodsProductMapperMock.returns(new ArrayList<>()).queryTopSalesInfoByProductId(map);
        assertEquals(0, goodsProductService.queryTopSalesByProductId(1L, 1).size());
    }

    /**
     * 新增兑换积分测试
     */
    @Test
    public void testInsertExchangeCusmomer() {
        goodsProductMapperMock.returns(1).insertExchangeCusmomer(null);
        assertEquals(1, goodsProductService.insertExchangeCusmomer(null));
    }

    /**
     * 根据地区ID查询关联的仓库ID测试
     */
    @Test
    public void testSelectWareIdByDistinctId() {
        WareHouse wareHouse = new WareHouse();
        wareHouse.setWareId(1L);
        productWareServiceMock.returns(wareHouse).findWare(1L);
        Long result = 1L;
        assertEquals(result, goodsProductService.selectWareIdByDistinctId(1L));
    }

    /**
     * 查询货品信息，不关联查询其他信息测试
     */
    @Test
    public void testQueryByGoodsInfoDetail() {
        goodsProductMapperMock.returns(new GoodsProduct()).queryByGoodsInfoDetail(1L);
        assertNotNull(goodsProductService.queryByGoodsInfoDetail(1L));
    }

    /**
     *  查询货品，按销量降序测试
     */
    @Test
    public void testQueryInfosOrderBySales() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rowCount", 1L);
        goodsProductMapperMock.returns(new ArrayList<>()).queryTopSalesInfos(map);
        assertEquals(0, goodsProductService.queryInfosOrderBySales(1L).size());
    }

    /**
     *  根据货品ID查询测试
     */
    @Test
    public void testQueryDetailByProductId() {
        List<GoodsListVo> releatedGoods = new ArrayList<>();
        releatedGoods.add(new GoodsListVo());
        GoodsRelatedGoodsVo goodsRelatedGoodsVo = new GoodsRelatedGoodsVo();
        goodsRelatedGoodsVo.setGoodsId(1L);
        goodsRelatedGoodsVo.setReleatedGoods(releatedGoods);
        List<GoodsRelatedGoodsVo> relaProducts = new ArrayList<>();
        relaProducts.add(goodsRelatedGoodsVo);
        GoodsProductVo productVo = new GoodsProductVo();
        productVo.setGoods(new Goods());
        productVo.setIsThird("0");
        productVo.setGoodsId(1L);
        goodsProductMapperMock.returns(productVo).queryDetailByProductId(1L);
        goodsRelatedGoodsMapperMock.returns(relaProducts).queryAllByGoodsId(1L);
        goodsPubMock.returns(new GoodsGroupServiceImpl()).getGoodsGroupService();
        assertNotNull(goodsProductService.queryDetailByProductId(1L, 1L));
    }

    /**
     *  根据货品ID查询测试
     */
    @Test
    public void testQueryDetailByProductId2() {
        List<GoodsListVo> releatedGoods = new ArrayList<>();
        releatedGoods.add(new GoodsListVo());
        GoodsRelatedGoodsVo goodsRelatedGoodsVo = new GoodsRelatedGoodsVo();
        goodsRelatedGoodsVo.setGoodsId(1L);
        goodsRelatedGoodsVo.setReleatedGoods(releatedGoods);
        List<GoodsRelatedGoodsVo> relaProducts = new ArrayList<>();
        relaProducts.add(goodsRelatedGoodsVo);
        GoodsProductVo productVo = new GoodsProductVo();
        productVo.setGoods(new Goods());
        productVo.setIsThird("0");
        productVo.setGoodsId(1L);
        goodsProductMapperMock.returns(productVo).queryDetailByProductId(1L);
        goodsRelatedGoodsMapperMock.returns(relaProducts).queryAllByGoodsId(1L);
        goodsPubMock.returns(new GoodsGroupServiceImpl()).getGoodsGroupService();
        productWareServiceMock.returns(new ProductWare()).queryProductWareByProductIdAndDistinctId(1L, 1L);
        assertNotNull(goodsProductService.queryDetailByProductId(1L, 1L));
    }

    /**
     *  查询货品的分仓价格
     */
    @Test
    public void testQueryDetailBeanByProductIdForGroupon() {
        GoodsProductVo productVo = new GoodsProductVo();
        productVo.setThirdId(0L);
        goodsProductMapperMock.returns(productVo).queryDetailByProductId(1L);
        ProductWare productWare = new ProductWare();
        productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 1L);
        assertNotNull(goodsProductService.queryDetailBeanByProductIdForGroupon(1L, 1L));
    }

    /**
     *  根据货品ID 查询这个货品是否是上架状态以及未删除状态
     */
    @Test
    public void testQueryProductByGoodsInfoId() {
        Map<String, Object> map = new HashMap<String, Object>();
        // 填充货品号
        map.put("productId", 1L);
        goodsProductMapperMock.returns(1).queryProductByGoodsInfoId(map);
        assertEquals(1, goodsProductService.queryProductByGoodsInfoId(1L));
    }

}
