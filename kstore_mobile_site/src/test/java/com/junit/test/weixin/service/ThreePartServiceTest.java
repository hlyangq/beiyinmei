package com.junit.test.weixin.service;

import com.ningpai.m.weixin.bean.ThreePart;
import com.ningpai.m.weixin.dao.ThreePartMapper;
import com.ningpai.m.weixin.service.ThreePartService;
import com.ningpai.m.weixin.service.impl.ThreePartServiceImpl;
import com.ningpai.util.PageBean;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wtp on 2016/3/30.
 */
public class ThreePartServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private ThreePartService threePartService = new ThreePartServiceImpl();

    /**
     * 模拟MOCK
     */
    @InjectIntoByType
    private Mock<ThreePartMapper> threePartMapperMock;

    /**
     * 查询第三方信息
     */
    @Test
    public void testSelectThreePart() {
        ThreePart threePart = new ThreePart();
        threePartMapperMock.returns(threePart).selectThreePart("1");
        assertNotNull(threePartService.selectThreePart("1"));
    }

    /**
     * 插入第三方
     */
    @Test
    public void testInsertThreePart() {
        ThreePart threePart = new ThreePart();
        threePartMapperMock.returns(1).insertThreePart(threePart);
        assertNotNull(threePartService.insertThreePart(threePart));
    }
}
