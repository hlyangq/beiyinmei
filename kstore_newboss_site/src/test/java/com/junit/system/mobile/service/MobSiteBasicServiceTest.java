package com.junit.system.mobile.service;

import com.ningpai.redis.RedisAdapter;
import com.ningpai.system.mobile.bean.MobSiteBasic;
import com.ningpai.system.mobile.dao.MobSiteBasicMapper;
import com.ningpai.system.mobile.service.impl.MobSiteBasicServiceImpl;
import com.ningpai.system.mobile.service.MobSiteBasicService;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

/**
 * SERVICE-移动版站点基础设置接口测试
 *
 * @author wangtp
 */
public class MobSiteBasicServiceTest extends UnitilsJUnit3 {
    /**
     * 需要测试的接口类
     */
    @TestedObject
    private MobSiteBasicService mobSiteBasicService = new MobSiteBasicServiceImpl();

    /**
     *  模拟MOCK
     */
    @InjectIntoByType
    Mock<MobSiteBasicMapper> mobSiteBasicMapperMock;

    /**
     *  模拟MOCK
     */
    @InjectIntoByType
    Mock<RedisAdapter> redisAdapterMock;

    /**
     * 添加站点信息
     */
    @Test
    public void testSaveMobSiteBasic(){
        MobSiteBasic mobSiteBasic = new MobSiteBasic();
        mobSiteBasicMapperMock.returns(1).insertSelective(mobSiteBasic);
        assertEquals(1, mobSiteBasicService.saveMobSiteBasic(mobSiteBasic));
    }

    /**
     * 修改站点信息
     */
    @Test
    public void testUpdateMobSiteBasic(){
        MobSiteBasic mobSiteBasic = new MobSiteBasic();
        mobSiteBasicMapperMock.returns(1).updateByPrimaryKeySelective(mobSiteBasic);
        assertEquals(1, mobSiteBasicService.updateMobSiteBasic(mobSiteBasic));

    }

    /**
     * 查询当前站点信息
     */
    @Test
    public void testSelectCurrMobSiteBasic(){
        MobSiteBasic mobSiteBasic = new MobSiteBasic();

        mobSiteBasicMapperMock.returns(mobSiteBasic).selectCurr();
        assertNotNull(mobSiteBasicService.selectCurrMobSiteBasic(new MockHttpServletRequest()));
    }
}
