package com.junit.test.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.api.bean.OGoodsProduct;
import com.ningpai.api.dao.IGoodsProductMapper;
import com.ningpai.api.service.IGoodsProductService;
import com.ningpai.api.service.impl.GoodsProductServiceImpl;

/**
 * 开放接口 - 商品service类测试
 * @author djk
 *
 */
public class IGoodsProductServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private IGoodsProductService iGoodsProductService = new GoodsProductServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<IGoodsProductMapper> iGoodsProductMapperMock;

    /**
     * 获取货品列表测试
     */
    @Test
    public void testList() {
        assertEquals("{\"item\":[],\"total_results\":0}", iGoodsProductService.list("1", 1L, null, null));
    }

    /**
     * 根据货品编号查询货品信息测试
     */
    @Test
    public void testGet() {
        iGoodsProductMapperMock.returns(new OGoodsProduct()).get("a");
        assertEquals("{}", iGoodsProductService.get("a"));
    }
}
