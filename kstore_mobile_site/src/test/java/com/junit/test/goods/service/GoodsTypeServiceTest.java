package com.junit.test.goods.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.m.goods.dao.GoodsTypeMapper;
import com.ningpai.m.goods.service.GoodsTypeService;
import com.ningpai.m.goods.service.impl.GoodsTypeServiceImpl;
import com.ningpai.m.goods.vo.GoodsTypeVo;

/**
 *  商品类型Service测试
 * @author djk
 *
 */
public class GoodsTypeServiceTest extends UnitilsJUnit3 {
    /**
     * 需要测试的接口类
     */
    @TestedObject
    private GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<GoodsTypeMapper> GoodsTypeMapperMock;
    
    /**
     * 根据分类ID查询类型的Vo实体测试 
     */
    @Test
    public void testQueryGoodsTypeByCatId()
    {
        GoodsTypeMapperMock.returns(new GoodsTypeVo()).queryTypeVoByCatId(1L);
        assertNotNull(goodsTypeService.queryGoodsTypeByCatId(1L));
    }
}
