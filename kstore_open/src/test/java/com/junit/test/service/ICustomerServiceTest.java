package com.junit.test.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.api.bean.OCustomerAllInfo;
import com.ningpai.api.dao.ICustomerMapper;
import com.ningpai.api.service.ICustomerService;
import com.ningpai.api.service.impl.CustomerServiceImpl;

/**
 * 开放接口-会员service测试
 * @author djk
 *
 */
public class ICustomerServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private ICustomerService iCustomerService = new CustomerServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ICustomerMapper> iCustomerMapperMock;

    /**
     * 获取会员列表测试
     */
    @Test
    public void testList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", 1);
        map.put("end", 15);
        assertEquals("{\"item\":[],\"total_results\":0}", iCustomerService.list(null, null));
    }

    /**
     *  获取会员详情测试
     */
    @Test
    public void testGet() {
        OCustomerAllInfo oCustomerAllInfo = new OCustomerAllInfo();
        iCustomerMapperMock.returns(oCustomerAllInfo).get("a");
        assertEquals("{}", iCustomerService.get("a"));
    }

    /**
     *  检查用户是否存在测试
     *  用户名是姓名的场景
     */
    @Test
    public void testCheckExistsByCustNameAndType() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("uType", "username");
        paramMap.put("username", "a");
        Long result = 1L;
        iCustomerMapperMock.returns(1L).checkExistsByCustNameAndType(paramMap);
        assertEquals(result, iCustomerService.checkExistsByCustNameAndType("a"));
    }

    /**
     *  检查用户是否存在测试
     *  用户名是邮箱的场景
     */
    @Test
    public void testCheckExistsByCustNameAndType2() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("uType", "email");
        paramMap.put("username", "a@q");
        Long result = 1L;
        iCustomerMapperMock.returns(1L).checkExistsByCustNameAndType(paramMap);
        assertEquals(result, iCustomerService.checkExistsByCustNameAndType("a@q"));
    }

    /**
     *  检查用户是否存在测试
     *  用户名是手机号码的场景
     */
    @Test
    public void testCheckExistsByCustNameAndType3() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("uType", "mobile");
        paramMap.put("username", "15195812239");
        Long result = 1L;
        iCustomerMapperMock.returns(1L).checkExistsByCustNameAndType(paramMap);
        assertEquals(result, iCustomerService.checkExistsByCustNameAndType("15195812239"));
    }

    /**
     * 验证邮箱存在性测试
     */
    @Test
    public void testCheckEmailExist() {
        Long result = 1L;
        iCustomerMapperMock.returns(1L).checkEmailExist("1");
        assertEquals(result, iCustomerService.checkEmailExist("1"));
    }

    /**
     *  验证手机存在性测试
     */
    @Test
    public void testCheckMobileExist() {
        Long result = 1L;
        iCustomerMapperMock.returns(1L).checkMobileExist("1");
        assertEquals(result, iCustomerService.checkMobileExist("1"));
    }

    /**
     * 添加会员测试
     * 姓名的场景
     */
    @Test
    public void testAddCustomer() {
        iCustomerMapperMock.returns(1).addCustomer(new OCustomerAllInfo());
        assertEquals(1, iCustomerService.addCustomer("1111111", "1", "1", "1", "1"));
    }

    /**
     * 添加会员测试
     * 邮箱的场景
     */
    @Test
    public void testAddCustomer2() {
        iCustomerMapperMock.returns(1).addCustomer(new OCustomerAllInfo());
        assertEquals(1, iCustomerService.addCustomer("1231@qq", "1", "1", "1", "1"));
    }

    /**
     * 添加会员测试
     * 手机的场景
     */
    @Test
    public void testAddCustomer3() {
        iCustomerMapperMock.returns(1).addCustomer(new OCustomerAllInfo());
        assertEquals(1, iCustomerService.addCustomer("15195812239", "1", "1", "1", "1"));
    }
}
