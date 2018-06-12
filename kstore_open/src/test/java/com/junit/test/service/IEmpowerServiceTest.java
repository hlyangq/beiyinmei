package com.junit.test.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.api.bean.OEmpower;
import com.ningpai.api.dao.IEmpowerMapper;
import com.ningpai.api.service.IEmpowerService;
import com.ningpai.api.service.impl.EmpowerServiceImpl;
import com.ningpai.api.util.MD5Util;

/**
 * 开放接口--获取秘钥测试
 * @author djk
 *
 */
public class IEmpowerServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private IEmpowerService iEmpowerService = new EmpowerServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<IEmpowerMapper> iEmpowerMapperMock;

    /**
     * 获取秘钥测试
     */
    @Test
    public void testGetKey() {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("YYYYMMddHHmm");
        String date = bartDateFormat.format(new Date());
        OEmpower ower = new OEmpower();
        ower.setAppKey("1");
        iEmpowerMapperMock.returns(ower).getKey("a");
        assertNotNull(iEmpowerService.getKey("a", MD5Util.md5Hex("a" + date + "1"), date));
    }
}
