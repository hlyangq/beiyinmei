package com.junit.custom.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.bean.CustomerInfo;
import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.dao.CustomerAddressMapper;
import com.ningpai.customer.dao.CustomerInfoMapper;
import com.ningpai.customer.dao.CustomerMapper;
import com.ningpai.customer.dao.CustomerPointLevelMapper;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.customer.service.impl.CustomerServiceMapperImpl;
import com.ningpai.customer.vo.CustomerStatisticVo;
import com.ningpai.other.bean.CityBean;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.other.bean.DistrictBean;
import com.ningpai.other.bean.OrderInfoBean;
import com.ningpai.other.bean.ProvinceBean;
import com.ningpai.other.bean.StreetBean;
import com.ningpai.other.util.SelectBean;
import com.ningpai.util.PageBean;

/**
 * 会员服务处理接口测试
 * @author djk
 *
 */
public class CustomerServiceMapperTest extends UnitilsJUnit3
{
    /**
     * 需要测试的Service
     */
    @TestedObject
	private CustomerServiceMapper customerServiceMapper = new CustomerServiceMapperImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
	private Mock<CustomerMapper> customerMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
	private Mock<CustomerInfoMapper> customerInfoMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
	private Mock<CustomerAddressMapper> customerAddressMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
	private Mock<CustomerPointLevelMapper> customerPointLevelMapperMock;
    
    private Customer customer = new Customer();
    
    /**
     * 禁用/启用商家员工测试
     */
    @Test
    public void testModifyEmpToDisableThird()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("custId", 1L);
        paramMap.put("flag", "1");
        paramMap.put("thirdId", 1L);
        customerMapperMock.returns(1).modifyEmpToDisableThird(paramMap);
    	assertEquals(1, customerServiceMapper.modifyEmpToDisableThird(1L,"1", 1L));
    }
    
    /**
     * 删除商家员工测试
     */
    @Test
    public void testDeleteCustomerThird()
    {
    	String[] custId = {"1"};
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", custId);
        paramMap.put("thirdId", 1L);
        customerMapperMock.returns(1).deleteCustomerThird(paramMap);
    	assertEquals(1, customerServiceMapper.deleteCustomerThird(custId, 1L));
    }
    
    /**
     * 修改会员信息 登陆错误的次数测试
     */
    @Test
    public void testUpdateCusErrorCount()
    {
    	customerMapperMock.returns(1).updateCusErrorCount(customer);
    	assertEquals(1, customerServiceMapper.updateCusErrorCount(customer));
    }
    
    /**
     * 修改会员信息 达到登陆错误次数 就插入锁定时间测试
     */
    @Test
    public void testUpdateCusLock()
    {
    	customerMapperMock.returns(1).updateCusLock(customer);
    	assertEquals(1, customerServiceMapper.updateCusLock(customer));
    }
    
    /**
     * 
     */
    @Test
    public void testGetCustomerByUsername()
    {
    	customerMapperMock.returns(customer).getCustomerByUsername("a");
    	assertNotNull(customerServiceMapper.getCustomerByUsername("a"));
    }
    
    /**
     *  删除商家会员为普通会员测试
     */
    @Test
    public void testDeleteStore()
    {
    	String[] ids = {"1"};
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", ids);
        customerMapperMock.returns(1).deleteStore(map);
    	assertEquals(1, customerServiceMapper.deleteStore(ids));
    }
    
    /**
     * 修改会员状态为商家测试
     */
    @Test
    public void testUpdateStatus()
    {
    	customerMapperMock.returns(1).updateStatus(1);
    	assertEquals(1, customerServiceMapper.updateStatus(1));
    }
    
    /**
     *  查询单个会员信息 详细测试
     */
    @Test
    public void testSelectByPrimaryKey()
    {
    	customerMapperMock.returns(null).selectByPrimaryKey(1L);
    	assertNull(customerServiceMapper.selectByPrimaryKey(1L));
    }
    
    /**
     * 根据会员id查询出所有订单信息测试
     */
    @Test
    public void testSelectBycustomerIds()
    {
    	customerMapperMock.returns(new ArrayList<CustomerAllInfo>()).selectBycustomerIds(null);
    	assertEquals(0, customerServiceMapper.selectBycustomerIds(null).size());
    }
    
    /**
     *  查询所用会员用户 分页测试
     */
    @Test
    public void testSelectAllCustmer()
    {
    	Map<String, Integer> paramMap = new HashMap<>();
    	
    	 PageBean pageBean = new PageBean();
    	 paramMap.put("startRowNum", pageBean.getStartRowNum());
         paramMap.put("endRowNum", pageBean.getEndRowNum());
         customerMapperMock.returns(new ArrayList<>()).selectCustomerByLimit(paramMap);
    	 assertEquals(0, customerServiceMapper.selectAllCustmer(pageBean).getList().size());
    }
    
    /**
     * 添加会员测试
     */
    @Test
    public void testAddCustomer()
    {
    	CustomerAllInfo allinfo = new CustomerAllInfo();
    	allinfo.setCustomerUsername("15195812239");
    	customerMapperMock.returns(1).addCustomer(allinfo);
    	customerPointLevelMapperMock.returns(new CustomerPointLevel()).selectDefaultCustomerLevel();
    	assertEquals(1, customerServiceMapper.addCustomer(allinfo));
    }
    
    /**
     * 第三方注册用户测试
     */
    @Test
    public void testAddThirdCustomer()
    {
    	CustomerAllInfo allinfo = new CustomerAllInfo();
    	allinfo.setCustomerUsername("15195812239");
    	customerPointLevelMapperMock.returns(new CustomerPointLevel()).selectDefaultCustomerLevel();
    	customerMapperMock.returns(1).addCustomer(allinfo);
    	assertEquals(1, customerServiceMapper.addThirdCustomer(allinfo));
    }
    
    /**
     * 查询会员对应的商家id测试
     */
    @Test
    public void testFindStore()
    {
    	customerMapperMock.returns("1").findStoreId("1");
    	assertEquals("1", customerServiceMapper.findStore("1"));
    }
    
    /**
     * 删除会员测试
     */
    @Test
    public void testDeleteCustomer()
    {
    	String[] customerId = {"1"};
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("customerId", customerId);
    	customerMapperMock.returns(1).deleteCustomerByIds(paramMap);
    	assertEquals(1, customerServiceMapper.deleteCustomer(customerId));
    }
    
    /**
     * 查询会员类型测试
     */
    @Test
    public void testSelectStatus()
    {
    	customerMapperMock.returns("1").selectStatus("1");
    	assertEquals("1", customerServiceMapper.selectStatus("1"));
    }
    
    /**
     * 修改会员信息测试
     */
    @Test
    public void testUpdateCustomer()
    {
    	CustomerAllInfo allinfo = new CustomerAllInfo();
    	customerMapperMock.returns(1).updateByPrimaryKeySelective(allinfo);
    	customerInfoMapperMock.returns(1).updateByPrimaryKeySelective(allinfo);
    	assertEquals(1, customerServiceMapper.updateCustomer(allinfo));
    }
    
    /**
     *  检查会员名是否存在测试
     */
    @Test
    public void testSelectCustomerByName()
    {
    	customerMapperMock.returns(1L).selectCustomerByName("1");
    	Long result = 1L;
    	assertEquals(result, customerServiceMapper.selectCustomerByName("1"));
    }

    /**
     * 第三方忘记密码  判定用户存在与否时使用测试
     */
    @Test
    public void testSelectCustomerByNameForThird()
    {
    	customerMapperMock.returns(1L).selectCustomerByNameForThird("1");
    	Long result = 1L;
    	assertEquals(result, customerServiceMapper.selectCustomerByNameForThird("1"));
    }
    
    /**
     * 根据订单编号查找订单信息测试
     */
    @Test
    public void testSelectCustomerOrder()
    {
    	customerMapperMock.returns(new CustomerAllInfo()).selectCustomerOrder(1L);
    	assertNotNull(customerServiceMapper.selectCustomerOrder(1L));
    }
    
    /**
     *  查询所有省份 用于添加会员页面填充省份测试
     */
    @Test
    public void testSelectAllProvince()
    {
    	customerMapperMock.returns(new ArrayList<ProvinceBean>()).selectAllProvince();
    	assertEquals(0, customerServiceMapper.selectAllProvince().size());
    }
    
    /**
     * 根据省份编号 查询所有城市测试
     */
    @Test
    public void testSelectAllCityByPid()
    {
    	customerMapperMock.returns(new ArrayList<CityBean>()).selectAllCityByPid(1L);
    	assertEquals(0, customerServiceMapper.selectAllCityByPid(1L).size());
    }
    
    /**
     * 查询所有城市 测试
     */
    @Test
    public void testSelectAllCity()
    {
    	customerMapperMock.returns(new ArrayList<CityBean>()).selectAllCity();
    	assertEquals(0, customerServiceMapper.selectAllCity().size());
    }
    
    /**
     * 查询所有区县测试
     */
    @Test
    public void testSelectAllDistrictByCid()
    {
    	customerMapperMock.returns(new ArrayList<DistrictBean>()).selectAllDistrictByCid(1L);
    	assertEquals(0, customerServiceMapper.selectAllDistrictByCid(1L).size());
    }
    

    /**
     *  查询所有区县测试
     */
    @Test
    public void testSelectAllDistrict()
    {
    	customerMapperMock.returns(new ArrayList<DistrictBean>()).selectAllDistrict();
    	assertEquals(0, customerServiceMapper.selectAllDistrict().size());
    }
    
    /**
     * 按条件查询会员测试
     */
    @Test
    public void testSelectCustmerByAllInfo()
    {
    	Map<String, Integer> paramMap = new HashMap<>();
    	CustomerAllInfo customerAllInfo = new CustomerAllInfo();
    	PageBean pageBean = new PageBean();
        paramMap.put("startRowNum", pageBean.getStartRowNum());
        paramMap.put("endRowNum", pageBean.getEndRowNum());
        customerMapperMock.returns(new ArrayList<>()).selectCustmerByAllInfoFilterThird(customerAllInfo);
    	assertEquals(0, customerServiceMapper.selectCustmerByAllInfo(customerAllInfo, pageBean).getList().size());
    }
    
    /**
     * 按区县编号获取对应街道集合测试
     */
    @Test
    public void testGetAllStreetByDid()
    {
    	customerMapperMock.returns(new ArrayList<StreetBean>()).getAllStreetByDid(1L);
    	assertEquals(0, customerServiceMapper.getAllStreetByDid(1L).size());
    }
    
    /**
     * 查询订单信息测试
     */
    @Test
    public void testQueryByDetail()
    {
    	customerMapperMock.returns(new OrderInfoBean()).queryByDetail(1L);
    	assertNotNull(customerServiceMapper.queryByDetail(1L));
    }
    
    /**
     * 查询会员信息测试
     */
    @Test
    public void testQueryCustomerInfo()
    {
    	customerMapperMock.returns(new CustomerAllInfo()).queryCustomerInfo(1L);
    	assertNotNull(customerServiceMapper.queryCustomerInfo(1L));
    }
    
    /**
     * 根据会员编号查找对应的默认收货地址测试
     */
    @Test
    public void testQueryDefaultAddr()
    {
    	customerAddressMapperMock.returns(new CustomerAddress()).selectDefaultAddr(1L);
    	assertNotNull(customerServiceMapper.queryDefaultAddr(1L));
    }
    
    /**
     * 禁用/启用用户测试
     */
    @Test
    public void testModifyEmpToDisable()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("flag", "1");
        paramMap.put("custId", 1L);
        customerMapperMock.returns(1).modifyEmpToDisable(paramMap);
    	assertEquals(1, customerServiceMapper.modifyEmpToDisable(1L, "1"));
    }
    
    /**
     *  查询用户上一次收货地址测试
     */
    @Test
    public void testSelectByCIdFirst()
    {
    	customerAddressMapperMock.returns(new CustomerAddress()).selectByCIdFirst(1L);
    	assertNotNull(customerServiceMapper.selectByCIdFirst(1L));
    }
    
    /**
     * 根据会员编号修改会员测试
     */
    @Test
    public void testUpdateByPrimaryKeySelective()
    {
    	CustomerAllInfo record = new CustomerAllInfo();
    	customerMapperMock.returns(1).updateByPrimaryKeySelective(record);
    	assertEquals(1, customerServiceMapper.updateByPrimaryKeySelective(record));
    }
    
    /**
     * 根据订单编号查找订单信息测试
     */
    @Test
    public void testQueryOrderByOrderId()
    {
    	customerMapperMock.returns(new Object()).queryOrderByOrderId(1L);
    	assertNotNull(customerServiceMapper.queryOrderByOrderId(1L));
    }
    
    /**
     * 检查用户是否存在测试
     */
    @Test
    public void testCheckExistsByCustNameAndType()
    {
    	 Map<String, Object> paramMap = new HashMap<String, Object>();
    	 paramMap.put("uType", "username");
    	 paramMap.put("username", "1");
    	Long result = 1L;
    	customerMapperMock.returns(1L).checkExistsByCustNameAndType(paramMap);
    	assertEquals(result, customerServiceMapper.checkExistsByCustNameAndType("1"));
    }
    
    /**
     * 根据用户名查询用户测试
     */
    @Test
    public void testCustomer()
    {
    	customerMapperMock.returns(new Customer()).customer("1");
    	assertNotNull(customerServiceMapper.customer("1"));
    }

    /**
     * 根据用户名查询用户测试
     */
    @Test
    public void testCustomerList()
    {
    	customerMapperMock.returns(new ArrayList<>()).customerList("1");
    	assertEquals(0, customerServiceMapper.customerList("1").size());
    }
    
    /**
     * 查询邮箱测试
     */
    @Test
    public void testEmail()
    {
    	customerInfoMapperMock.returns(new CustomerInfo()).email(1L);
    	assertNotNull(customerServiceMapper.email(1L));
    }
    
    /**
     * 查询手机测试
     */
    @Test
    public void testMobile()
    {
    	customerInfoMapperMock.returns(new CustomerInfo()).mobile(1L);
    	assertNotNull(customerServiceMapper.mobile(1L));
    }
    
    /**
     * 修改会员信息测试
     */
    @Test
    public void testUpdateCus()
    {
    	customerMapperMock.returns(1).updateCus(customer);
    	assertEquals(1, customerServiceMapper.updateCus(customer));
    }
    
    /**
     * 根据用户名查询用户信息测试
     */
    @Test
    public void testSelectCustomerByUserName()
    {
    	customerMapperMock.returns(customer).selectCustomerByUserName("1");
    	assertNotNull(customerServiceMapper.selectCustomerByUserName("1"));
    }
    
    /**
     * 根据时间统计会员个数测试
     */
    @Test
    public void testSelectCountByTime()
    {
    	customerMapperMock.returns(new ArrayList<CustomerStatisticVo>()).selectCountByTime();
    	assertEquals(0, customerServiceMapper.selectCountByTime().size());
    }
    
    /**
     * 根据地区统计会员个数测试
     */
    @Test
    public void testSelectCountByAddress()
    {
    	customerMapperMock.returns(new ArrayList<CustomerStatisticVo>()).selectCountByAddress();
    	assertEquals(0, customerServiceMapper.selectCountByAddress().size());
    }
    
    /**
     * 修改会员信息
     */
    @Test
    public void testSetCustomer()
    {
    	CustomerAllInfo allinfo = new CustomerAllInfo();
    	customerMapperMock.returns(1).updateByPrimaryKeySelective(allinfo);
    	assertEquals(1, customerServiceMapper.setCustomer(allinfo));
    }
    
    /**
     * 更新第三方的密码测试
     */
    @Test
    public void testUpdatePassword()
    {
    	Map<String, Object> map = new HashMap<>();
    	customerMapperMock.returns(1).updateThirdPassword(map);
    	assertEquals(1, customerServiceMapper.updatePassword(map));
    }
    
    /**
     * 设置会员类型测试
     */
    @Test
    public void testSetCustomer2()
    {
    	String[] customerId = {"1"};
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("isSiteManager", "1");
        customerMapperMock.returns(1).setCustomerByIds(paramMap);
    	assertEquals(1, customerServiceMapper.setCustomer(customerId, "1"));
    }
    
    /**
     * 
     */
    @Test
    public void testQueryCustomerRank()
    {
    	PageBean pageBean = new PageBean();
    	SelectBean selectBean = new SelectBean();
    	 Map<String, Object> paraMap = new HashMap<String, Object>();
         paraMap.put("startTime", "1");
         paraMap.put("endTime", "1");
         paraMap.put("startRowNum", pageBean.getStartRowNum());
         paraMap.put("endRowNum", pageBean.getEndRowNum());
         customerMapperMock.returns(new ArrayList<>()).queryCustomerRank(paraMap);
         assertEquals(0, customerServiceMapper.queryCustomerRank(pageBean, selectBean, "1", "1").getList().size());
         
    }
    
    /**
     * 查询会员等级情况测试
     */
    @Test
    public void testQueryCusLevleInfo()
    {
    	customerMapperMock.returns(new ArrayList<>()).queryCusLevleInfo();
    	assertEquals(0, customerServiceMapper.queryCusLevleInfo().size());
    }
    
    /**
     * 查询信息测试
     */
    @Test
    public void testQueryCusAndOrderInfo()
    {
    	PageBean pageBean = new PageBean();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("startRowNum", pageBean.getStartRowNum());
        paraMap.put("endRowNum", pageBean.getEndRowNum());
        paraMap.put("pointLevelId", 1L);
        paraMap.put("customerNickname", "a");
        customerMapperMock.returns(new ArrayList<>()).queryCusAndOrderInfo(paraMap);
    	assertEquals(0, customerServiceMapper.queryCusAndOrderInfo(pageBean, 1L, "a").getList().size());
    }
    
    /**
     *  查询会员，咨询，评论，晒单的数量测试
     */
    @Test
    public void testGetCustomerCount()
    {
    	customerMapperMock.returns(1L).selectCustmerSize(null);
    	assertEquals(1, customerServiceMapper.getCustomerCount().size());
    }
    
    /**
     *  手动更新会员等级测试
     */
    @Test
    public void testUpCusLevel()
    {
    	Map<String, Object> paraMap = new HashMap<>();
    	customerInfoMapperMock.returns(1).upCusLevel(paraMap);
    	assertEquals(1, customerServiceMapper.upCusLevel(paraMap));
    }
    
    /**
     * 查询商家员工用户名是否存在测试
     */
    @Test
    public void testCheckUsernameExitOrNot()
    {
    	Map<String, Object> map = new HashMap<>();
    	customerMapperMock.returns(1).checkUsernameExitOrNot(map);
    	Integer re = 1;
    	assertEquals(re, customerServiceMapper.checkUsernameExitOrNot(map));
    }
    
    /**
     *  验证邮箱存在性测试
     */
    @Test
    public void testCheckEmailExist()
    {
    	customerMapperMock.returns(1L).checkEmailExist("a");
    	Long result =1L;
    	assertEquals(result, customerServiceMapper.checkEmailExist("a"));
    }
    
    /**
     * 验证手机存在性测试
     */
    @Test
    public void testCheckMobileExist()
    {
    	customerMapperMock.returns(1L).checkMobileExist("a");
    	Long result =1L;
    	assertEquals(result, customerServiceMapper.checkMobileExist("a"));
    }
    
    /**
     *  查询最新的会员信息测试
     */
    @Test
    public void testSelectNewCustoer()
    {
    	customerMapperMock.returns(new ArrayList<>()).selectCustmerNewLimit();
    	assertEquals(0, customerServiceMapper.selectNewCustoer().size());
    }
    
    /**
     * 查询所有会员测试
     */
    @Test
    public void testselectCustomerAllInfomation()
    {
    	customerMapperMock.returns(new ArrayList<>()).selectCustomerAllInfomation();
    	assertEquals(0, customerServiceMapper.selectCustomerAllInfomation().size());
    }
    
    /**
     * 修改密码信息,使用新的密码测试
     */
    @Test
    public void testUpdatePwdInfo()
    {
    	customerMapperMock.returns(customer).queryLoginInfoByCustomerId(1L);
    	customerMapperMock.returns(1).updatePwdInfo(customer);
    	assertEquals(true, customerServiceMapper.updatePwdInfo(1L, "a"));
    }

    /**
     * 根据会员id集合查询会员（导出选中会员）
     */
    @Test
    public void testQueryListForExportByCustomerIds() {
        Long[] customerIds = {1L};
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("customerIds", customerIds);
        customerMapperMock.returns(new ArrayList<>()).queryListForExportByCustomerIds(map);
        assertNotNull(customerServiceMapper.queryListForExportByCustomerIds(customerIds));
    }

    /**
     * 根据用户名和类型查询用户信息
     */
    @Test
    public void testGetCustomerByUsernameType() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("uType", "email");
        param.put("username", "52423@qq.com");
        customerMapperMock.returns(new Customer()).getCustomerByUsernameMap(param);
        assertNotNull(customerServiceMapper.getCustomerByUsernameType("52423@qq.com"));
    }


    /**
     * 根据用户名和类型查询用户信息
     */
    @Test
    public void testGetCustomerByUsernameType2() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("uType", "mobile");
        param.put("username", "15195812239");
        customerMapperMock.returns(new Customer()).getCustomerByUsernameMap(param);
        assertNotNull(customerServiceMapper.getCustomerByUsernameType("15195812239"));
    }

    /**
     * 根据用户名和类型查询用户信息
     */
    @Test
    public void testGetCustomerByUsernameType3() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("uType", "username");
        param.put("username", "djk");
        customerMapperMock.returns(new Customer()).getCustomerByUsernameMap(param);
        assertNotNull(customerServiceMapper.getCustomerByUsernameType("djk"));
    }

    /**
     * 根据会员id查询会员记录数
     */
    @Test
    public void testSelectCustCount() {
        customerMapperMock.returns(1).selectCustCount(1L);
        assertEquals(1, customerServiceMapper.selectCustCount(1L));
    }

    /**
     * 根据街道id 查询街道信息
     */
    @Test
    public void testQueryStreetBeanById() {
        customerMapperMock.returns(new StreetBean()).queryStreetBeanById("1");
        assertNotNull(customerServiceMapper.queryStreetBeanById("1"));
    }

    @Test
    public void testSelectByCusIdAndAddrId() {
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("customerId", 1L);
        map.put("addressId", 1L);
        customerAddressMapperMock.returns(new CustomerAddress()).selectByCusIdAndAddrId(map);
        assertNotNull(customerServiceMapper.selectByCusIdAndAddrId(1L, 1L));
    }

}
