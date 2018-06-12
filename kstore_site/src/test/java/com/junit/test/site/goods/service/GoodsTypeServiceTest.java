package com.junit.test.site.goods.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.site.goods.dao.GoodsTypeMapper;
import com.ningpai.site.goods.service.GoodsTypeService;
import com.ningpai.site.goods.service.impl.GoodsTypeServiceImpl;
import com.ningpai.site.goods.vo.GoodsTypeVo;

/**
 * 商品类型Service测试
 * @author djk
 *
 */
public class GoodsTypeServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsTypeMapper> goodsTypeMapperMock;
    
    /**
     * 根据分类ID查询类型的Vo实体测试
     */
    @Test
    public void testQueryGoodsTypeByCatId()
    {
    	goodsTypeMapperMock.returns(new GoodsTypeVo()).queryTypeVoByCatId(1L);
    	assertNotNull(goodsTypeService.queryGoodsTypeByCatId(1L));
    }
}
