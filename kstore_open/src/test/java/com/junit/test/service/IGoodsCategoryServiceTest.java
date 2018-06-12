package com.junit.test.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.api.dao.IGoodsCategoryMapper;
import com.ningpai.api.service.IGoodsCategoryService;
import com.ningpai.api.service.impl.GoodsCategoryServiceImpl;

/**
 * 开放接口--商品分类测试
 * @author djk
 *
 */
public class IGoodsCategoryServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private IGoodsCategoryService iGoodsCategoryService = new GoodsCategoryServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<IGoodsCategoryMapper> iGoodsCategoryMapperMock;

    /**
     * 获取订单列表测试
     */
    @Test
    public void testList() {
        assertEquals("{\"item\":[],\"total_results\":0}", iGoodsCategoryService.list(null, null));
    }

}
