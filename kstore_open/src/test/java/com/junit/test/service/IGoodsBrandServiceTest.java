package com.junit.test.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.api.dao.IGoodsBrandMapper;
import com.ningpai.api.service.IGoodsBrandService;
import com.ningpai.api.service.impl.GoodsBrandServiceImpl;

/**
 * 开放接口--商品品牌列表测试
 * @author djk
 *
 */
public class IGoodsBrandServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private IGoodsBrandService iGoodsBrandService = new GoodsBrandServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<IGoodsBrandMapper> iGoodsBrandMapperMock;

    /**
     * 获取订单列表测试
     */
    @Test
    public void testList() {
        assertEquals("{\"item\":[],\"total_results\":0}", iGoodsBrandService.list(null, null));
    }
}
