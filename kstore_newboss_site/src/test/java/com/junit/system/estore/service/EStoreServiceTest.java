package com.junit.system.estore.service;

import com.ningpai.system.estore.bean.EStore;
import com.ningpai.system.estore.dao.impl.EStoreMapperImpl;
import com.ningpai.system.estore.service.impl.EStoreServiceImpl;
import com.ningpai.system.estore.service.EStoreService;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

/**
 * E店宝servie接口测试类
 * @author wangtp
 *
 */
public class EStoreServiceTest  extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private EStoreService estoreService = new EStoreServiceImpl();

    /**
     *  模拟MOCK
     */
    @InjectIntoByType
    Mock<EStoreMapperImpl> estoreMapperImplMock;

    private static EStore estore =  new EStore();

    /**
     * 查询E店宝信息
     */
    @Test
    public void testFindEStore(){
        estoreMapperImplMock.returns(estore).findEStore();
        assertNotNull(estoreService.findEStore());
    }

    @Test
    public void testUpdateEStore(){
        estoreMapperImplMock.returns(1).updateByPrimaryKeySelective(estore);
        assertEquals(1,estoreService.updateEStore(estore));
    }
}
