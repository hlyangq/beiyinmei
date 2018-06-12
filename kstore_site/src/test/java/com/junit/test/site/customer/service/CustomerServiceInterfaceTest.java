package com.junit.test.site.customer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ningpai.customer.bean.*;
import com.ningpai.customer.dao.CustomerConsumeMapper;
import com.ningpai.customer.dao.CustomerPointMapper;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.common.bean.Sms;
import com.ningpai.common.dao.SmsMapper;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.dao.CustomerInfoMapper;
import com.ningpai.customer.dao.InsideLetterMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.goods.vo.GoodsProductVo;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.Order;
import com.ningpai.order.dao.BackOrderMapper;
import com.ningpai.order.dao.OrderMapper;
import com.ningpai.other.bean.StreetBean;
import com.ningpai.site.customer.mapper.CustomerFollowMapper;
import com.ningpai.site.customer.mapper.CustomerMapper;
import com.ningpai.site.customer.mapper.CustomerMapperSite;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.service.impl.CustomerServiceMapper;
import com.ningpai.site.customer.vo.CityBean;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.site.customer.vo.DistrictBean;
import com.ningpai.site.customer.vo.ProvinceBean;
import com.ningpai.site.order.bean.ExchangeCusmomerPoint;
import com.ningpai.system.bean.SystemsSet;
import com.ningpai.util.PageBean;

/**
 * 会员服务接口测试
 * @author djk
 *
 */
public class CustomerServiceInterfaceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private CustomerServiceInterface customerServiceInterface = new CustomerServiceMapper();
    
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
    private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<OrderMapper> orderMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CouponService> couponServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<SmsMapper> smsMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerMapperSite> customerMapperSiteMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<InsideLetterMapper> insideLetterMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<BackOrderMapper> backOrderMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerFollowMapper> customerFollowMapperMock;

    @InjectIntoByType
    private Mock<CustomerPointMapper> customerPointMapperMock;

    @InjectIntoByType
    private Mock<CustomerConsumeMapper> customerConsumeMapperMock;
    
    /**
     * 分页辅助类
     */
    private PageBean pb = new PageBean();
    
    /**
     * 地区bean
     */
    private DistrictBean districtBean = new DistrictBean();
    
    /**
     * 地区bean集合
     */
    private List<DistrictBean> districtBeanLists = new ArrayList<>();
    
    /**
     * 省份bean
     */
    private ProvinceBean provinceBean = new ProvinceBean();
    
    /**
     * 省份bean集合
     */
    private List<ProvinceBean> provinceBeanLists = new ArrayList<>();
    
    /**
     * 会员详细信息VO
     */
    private CustomerAllInfo customerAllInfo = new CustomerAllInfo();
    
    
    @Override
    protected void setUp() throws Exception
    {
    	provinceBeanLists.add(provinceBean);
    	districtBeanLists.add(districtBean);
    }
    
    /**
     * 查询当前会员的退单信息测试 
     */
    @Test
    public void testQueryAllBackMyOrders()
    {
    	List<Object> backOrders = new ArrayList<>();
    	BackOrder backOrder = new BackOrder();
    	backOrder.setBackGoodsIdAndSum("3918,1-");
    	backOrders.add(backOrder);
    	Map<String, Object> paramMap = new HashMap<>();
    	customerMapperMock.returns(1L).searchTotalCountBack(paramMap);
        paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        customerMapperMock.returns(backOrders).queryAllMyBackOrders(paramMap);
        backOrderMapperMock.returns(new GoodsProductVo()).selectGoodsById(3918L);
    	assertEquals(1, customerServiceInterface.queryAllBackMyOrders(paramMap, pb).getList().size());
    }
    
    /**
     * 根据ID获取单个区县对象测试
     */
    @Test
    public void testSelectDistrictBeanById()
    {
    	customerMapperMock.returns(districtBean).selectDistrictBeanById(1L);
    	assertNotNull(customerServiceInterface.selectDistrictBeanById(1L));
    }
    
    /**
     * 根据主键获取单个的省份信息测试
     */
    @Test
    public void testSelectprovinceByPid()
    {
    	customerMapperMock.returns(provinceBean).selectprovinceByPid(1L);
    	assertNotNull(customerServiceInterface.selectprovinceByPid(1L));
    }
    
    /**
     * 是否允许退单测试
     */
    @Test
    public void testGetIsBackOrder()
    {
    	customerMapperMock.returns(new SystemsSet()).getIsBackOrder();
    	assertNotNull(customerServiceInterface.getIsBackOrder());
    }
    
    /**
     *  根据会员编号查询会员信息测试
     */
    @Test
    public void testQueryCustomerById()
    {
    	customerMapperMock.returns(null).queryCustomerById(1L);
    	assertNull(customerServiceInterface.queryCustomerById(1L));
    }
    
    /**
     * 根据会员编号查找订单信息测试
     */
    @Test
    public void testQueryMyOrders()
    {
    	Map<String, Object> paramMap = new HashMap<>();
    	customerMapperMock.returns(1L).searchTotalCountO(paramMap);
    	paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        customerMapperMock.returns(lists).queryMyOrders(paramMap);
    	assertEquals(1, customerServiceInterface.queryMyOrders(paramMap, pb).getList().size());
    }
    
    /**
     * 查询所有订单测试
     */
    @Test
    public void testQueryAllMyOrders()
    {
    	Map<String, Object> paramMap = new HashMap<>();
    	customerMapperMock.returns(1L).searchTotalCount(paramMap);
    	paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        customerMapperMock.returns(lists).queryAllMyOrders(paramMap);
        assertEquals(1, customerServiceInterface.queryAllMyOrders(paramMap, pb).getList().size());
    }
    
    /**
     * 根据订单编号查找订单信息测试
     */
    @Test
    public void testQueryOrderByCustIdAndOrderId()
    {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", 1L);
        paramMap.put("orderId", 1L);
        customerMapperMock.returns(new Object()).queryOrderByParamMap(paramMap);
    	assertNotNull(customerServiceInterface.queryOrderByCustIdAndOrderId(1L, 1L));
    }
    
    /**
     * 根据订单编号查找订单收货地址测试
     */
    @Test
    public void testQueryCustomerAddressById()
    {
    	customerMapperMock.returns(new Object()).queryCustomerAddressById(1L);
    	assertNotNull(customerServiceInterface.queryCustomerAddressById(1L));
    }
    
    /**
     * 根据会员编号查找会员详细信息测试
     */
    @Test
    public void testQueryCustomerInfoById()
    {
    	customerMapperMock.returns(new Object()).queryCustomerInfoById(1L);
    	assertNotNull(customerServiceInterface.queryCustomerInfoById(1L));
    }
    
    /**
     * 修改会员信息测试
     */
    @Test
    public void testModifyCustomerInfo()
    {
    	customerMapperMock.returns(1).modifyCustomerInfo(customerAllInfo);
    	assertEquals(1, customerServiceInterface.modifyCustomerInfo(customerAllInfo, "1"));
    }
    
    /**
     * 确认收货测试
     */
    @Test
    public void testComfirmOfGooods()
    {
    	customerMapperMock.returns(1).comfirmOfGooods(1L);
    	customerMapperMock.returns(1).modifyOrderStatusToSuccess(1L);
    	Integer result = 2;
    	assertEquals(result, customerServiceInterface.comfirmOfGooods(1L));
    }
    
    /**
     * 查询所有省份 用于添加会员页面填充省份测试
     */
    @Test
    public void testSelectAllProvince()
    {
    	customerMapperMock.returns(provinceBeanLists).selectAllProvince();
    	assertEquals(1, customerServiceInterface.selectAllProvince().size());
    }
    
    /**
     * 根据省份编号 查询所有城市测试
     */
    @Test
    public void testSelectAllCityByPid()
    {
    	List<CityBean> lists = new ArrayList<>();
    	lists.add(new CityBean());
    	customerMapperMock.returns(lists).selectAllCityByPid(1L);
    	assertEquals(1, customerServiceInterface.selectAllCityByPid(1L).size());
    }
    
    /**
     * 根据城市编号 查询所有区县测试
     */
    @Test
    public void testSelectAllDistrictByCid()
    {
    	List<DistrictBean> lists = new ArrayList<>();
    	lists.add(new DistrictBean());
    	customerMapperMock.returns(lists).selectAllDistrictByCid(1L);
    	assertEquals(1, customerServiceInterface.selectAllDistrictByCid(1L).size());
    }
    
    /**
     * 按区县编号获取对应街道集合测试
     */
    @Test
    public void testGetAllStreetByDid()
    {
    	List<StreetBean> lists = new ArrayList<>();
    	lists.add(new StreetBean());
    	customerMapperMock.returns(lists).getAllStreetByDid(1L);
    	assertEquals(1, customerServiceInterface.getAllStreetByDid(1L).size());
    }
    
    /**
     *  根据会员编号查找会员详细信息测试
     */
    @Test
    public void testQueryCustomerByCustomerId()
    {
    	customerMapperMock.returns(null).queryCustomerByCustomerId(1L);
    	assertNull(customerServiceInterface.queryCustomerByCustomerId(1L));
    }
    
    /**
     * 验证用户密码测试
     */
    @Test
    public void testCheckCustomerPassword()
    {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("customerId", 1L);
        map.put("password", "1");
        customerMapperMock.returns(new Customer()).queryLoginInfoByCustomerId(1L);
    	assertEquals(0, customerServiceInterface.checkCustomerPassword(1L, "1"));
    }
    
    /**
     * 修改用户密码测试
     */
    @Test
    public void testUpdateByPrimaryKey()
    {
    	customerMapperMock.returns(1).updateByPrimaryKeySelective(customerAllInfo);
    	assertEquals(1, customerServiceInterface.updateByPrimaryKey(customerAllInfo));
    }
    
    /**
     * 根据用户编号查找用户的收货地址测试
     */
    @Test
    public void testQueryAddressByCustomerId()
    {
    	customerMapperMock.returns(customerAllInfo).queryAddressByCustomerId(1L);
    	assertNotNull(customerServiceInterface.queryAddressByCustomerId(1L));
    }
    
    /**
     * 根据用户编号查找用户的收货地址测试
     */
    @Test
    public void testQueryAddressByCustomerId2()
    {
    	Map<String, Object> map = new HashMap<>();
    	customerMapperMock.returns(1L).queryAddressCountByCustomerId(1L);
    	map.put("customerId", 1L);
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        customerMapperMock.returns(lists).queryAddressByCustomerId(map);
    	assertEquals(1, customerServiceInterface.queryAddressByCustomerId(1L, pb).getList().size());
    }
    
    /**
     * 添加收货地址测试
     */
    @Test
    public void testAddCustomerAddress()
    {
    	customerMapperMock.returns(1).addCustomerAddress(new CustomerAddress());
    	assertEquals(1, customerServiceInterface.addCustomerAddress(new CustomerAddress()));
    }
    
    /**
     * 删除收货地址
     */
    @Test
    public void testDeleteCustAddress()
    {
    	customerMapperMock.returns(1).deleteCustAddress(1L);
    	assertEquals(1, customerServiceInterface.deleteCustAddress(1L));
    }
    
    /**
     * 根据地址编号查找收货地址测试
     */
    @Test
    public void testQueryCustAddress()
    {
    	customerMapperMock.returns(new CustomerAddress()).queryCustAddress(1L);
    	assertNotNull(customerServiceInterface.queryCustAddress(1L));
    }
    
    /**
     * 修改会员收货地址测试
     */
    @Test
    public void testModifyCustAddress()
    {
    	customerMapperMock.returns(1).modifyCustAddress(new CustomerAddress());
    	assertEquals(1, customerServiceInterface.modifyCustAddress(new CustomerAddress()));
    }
    
    /**
     * 修改默认地址 将之前的默认地址改为非默认 并且将当前地址改为默认地址测试
     */
    @Test
    public void testModifyIsDefaultAddress()
    {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	customerMapperMock.returns(1).cancelDefaultAddress("1");
    	customerMapperMock.returns(null).queryCustAddress(1L);
        paramMap.put("addressId", "1");
        paramMap.put("customerId", "1");
        customerMapperMock.returns(1).setDefaultAddress(paramMap);
    	assertEquals(1, customerServiceInterface.modifyIsDefaultAddress(new MockHttpServletRequest(), "1", "1"));
    }
    
    /**
     * 根据会员编号查询相应的会员积分明细测试
     */
    @Test
    public void testSelectAllCustomerPoint()
    {
    	Map<String, Object> paramMap = new HashMap<>();
    	customerMapperMock.returns(1L).queryPointRcCount(paramMap);
        paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        customerMapperMock.returns(lists).queryAllPointRc(paramMap);
    	assertEquals(1, customerServiceInterface.selectAllCustomerPoint(paramMap, pb).getList().size());
    }
    
    /**
     * 根据会员编号查询相应的总会员积分测试
     */
    @Test
    public void testSelectTotalPointByCid()
    {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", 1L);
        customerMapperMock.returns(1L).selectTotalPointByCid(paramMap);
    	Long result = 1L;
    	assertEquals(result, customerServiceInterface.selectTotalPointByCid(1L));
    }
    
    /**
     * 根据会员编号查询相应的会员收藏测试
     */
    @Test
    public void testSelectAllCustomerFollow()
    {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	customerMapperMock.returns(1L).queryFollowRcCount(paramMap);
    	paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        customerMapperMock.returns(lists).queryAllFollowRc(paramMap);
    	assertEquals(1, customerServiceInterface.selectAllCustomerFollow(paramMap, pb).getList().size());
    }
    
    /**
     * 查询通知内容数量 如 :待处理订单数量...
     */
    @Test
    public void testSelectNotice()
    {
    	assertEquals(11, customerServiceInterface.selectNotice(1L).size());
    }
    
    /**
     * 取消订单测试
     */
    @Test
    public void testCancelOrder()
    {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setInfoPointSum(1);
    	ExchangeCusmomerPoint cusmomerPoint = new ExchangeCusmomerPoint();
    	cusmomerPoint.setCustomerId(1L);
    	cusmomerPoint.setExchangePoint(1L);
    	CustomerPoint customerPoint = new CustomerPoint();
    	customerPoint.setPointSum(1L);
    	Order order = new Order();
        order.setPayId(1L);
        order.setOrderPrice(new BigDecimal("1"));
    	order.setOrderCode("1");
        order.setBusinessId(0L);
        order.setOrderIntegral(1L);
        order.setCustomerId(1L);
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", 1L);
        paramMap.put("reason", "A");
        customerMapperMock.returns(1).cancelOrder(paramMap);
        orderMapperMock.returns(order).orderDetail(1L);
        CustomerPoint cusPoint = new CustomerPoint();
        cusPoint.setPointDetail("订单取消返回积分");
        cusPoint.setPoint(10);
        cusPoint.setPointType("1");
        cusPoint.setDelFlag("0");
        cusPoint.setCustomerId(1L);
        customerInfoMapperMock.returns(customerInfo).selectCustInfoById(1L);
        customerPointMapperMock.returns(1).insertSelective(cusPoint);
        customerMapperMock.returns(cusmomerPoint).selectExchangeByOrderCode("1");
        customerMapperMock.returns(customerPoint).selectCustomerPointByCustomerId(1L);
        couponServiceMock.returns(1).updateCustomerPoint(customerPoint);
        customerConsumeMapperMock.returns(1).insertSelective(new CustomerConsume());
    	assertEquals(0, customerServiceInterface.cancelOrder(1L, "A"));
    }
    
    /**
     * 查询会员基本信息测试
     */
    @Test
    public void testSelectByPrimaryKey()
    {
    	customerMapperMock.returns(customerAllInfo).selectByPrimaryKey(1L);
    	assertNotNull(customerServiceInterface.selectByPrimaryKey(1L));
    }
    
    /**
     *  删除订单测试
     */
    @Test
    public void testDelOrder()
    {
    	customerMapperMock.returns(1).delOrder(1L);
    	assertEquals(1, customerServiceInterface.delOrder(1L));
    }
    
    /**
     * 修改密码 邮箱 手机
     */
    @Test
    public void testModifyPem()
    {
    	customerMapperMock.returns(new Customer()).queryLoginInfoByCustomerId(null);
    	customerMapperMock.returns(1).updateByPrimaryKeySelective(customerAllInfo);
    	assertEquals(0, customerServiceInterface.modifyPem(new MockHttpServletRequest(), "a", "pwd"));
    }

    
    /**
     * 修改密码 邮箱 手机
     * 测试当type是email手机的场景
     */
    @Test
    public void testModifyPem3(){
    	customerMapperMock.returns(1).modifyCustomerInfo(customerAllInfo);
    	assertEquals(1, customerServiceInterface.modifyPem(new MockHttpServletRequest(), "a", "email"));
    }
    
    /**
     * 发送邮件测试
     */
    @Test
    public void testSendEamil()
    {
    	assertEquals(0, customerServiceInterface.sendEamil(new MockHttpServletRequest(), "a"));
    }
    
    /**
     * 发送手机验证码测试
     */
    @Test
    public void testSendPost()
    {
    	Customer customer = new Customer();
    	long time = new Date().getTime()+(60 * 2000);
    	
    	customer.setAeadTime(new Date(time));
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.getSession().setAttribute("uId", 1L);
    	request.getSession().setAttribute("cust", customer);
    	smsMapperMock.returns(new Sms()).selectSms();
    	customerMapperMock.returns(customer).selectCaptcha(1L);
    	assertEquals(-1, customerServiceInterface.sendPost(request, "51"));
    }
    
     /**
      * 验证手机验证码测试
      */
     @Test
     public void testGetMCode()
     {
    	 Customer customer = new Customer();
    	 customer.setAeadTime(new Date());
    	 customer.setCaptcha("1234");
    	 customerAllInfo.setIsMobile("0");
    	 MockHttpServletRequest request = new MockHttpServletRequest();
    	 request.getSession().setAttribute("customerId", 1L);
     	 request.getSession().setAttribute("uId", 1L);
     	 customerMapperMock.returns(customer).selectCaptcha(1L);
         customerMapperMock.returns(1).updateSmsCaptcha(customer);
         customerMapperMock.returns(customerAllInfo).selectByPrimaryKey(1L);
    	 assertEquals(1, customerServiceInterface.getMCode(request, "1234"));
     }
    
    /**
     * 验证邮箱有效测试
     */
    @Test
    public void testVerifyCheckCode()
    {
       MockHttpServletRequest request = new MockHttpServletRequest();
       request.getSession().setAttribute("customerId", 1L);
       customerAllInfo.setIsEmail("0");
       customerMapperMock.returns(customerAllInfo).selectByPrimaryKey(1L);
	   assertEquals(0, customerServiceInterface.verifyCheckCode(request, 1L, "123"));
    }
    
    /**
     * 修改会员详细信息测试
     */
    @Test
    public void testUpdateCustInfoByPrimaryKey()
    {
    	customerMapperMock.returns(1).updateCustInfoByPrimaryKeySelective(customerAllInfo);
    	assertEquals(1, customerServiceInterface.updateCustInfoByPrimaryKey(customerAllInfo));
    }
    
    /**
     * 确认收货测试
     */
    @Test
    public void testComfirmofGoods()
    {
    	customerMapperMock.returns(1).comfirmOfGooods(1L);
    	assertEquals(1, customerServiceInterface.comfirmofGoods(1L));
    }
    
    /**
     * 检查用户名存在性测试
     */
    @Test
    public void testCheckUsernameFlag()
    {
    	customerMapperMock.returns(1L).checkexistsByCustName("1");
    	Long result = 1L;
    	assertEquals(result, customerServiceInterface.checkUsernameFlag("1"));
    }
    
    /**
     * 根据用户名查询用户简单信息测试
     */
    @Test
    public void testSelectCustomerByUname()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("username", "a");
        customerMapperSiteMock.returns(customerAllInfo).selectCustomerByUname(paramMap);
    	assertNotNull(customerServiceInterface.selectCustomerByUname("a"));
    }
    
    /**
     * 修改找回密码Code测试
     */
    @Test
    public void testUpdateFindPwdCode()
    {
    	customerMapperMock.returns(1).updateSmsCaptcha(customerAllInfo);
    	assertEquals(1, customerServiceInterface.updateFindPwdCode(customerAllInfo));
    }
    
    /**
     * 验证邮件测试
     */
    @Test
    public void testValidPwdEmail()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("username", "1");
    	Customer customer = new Customer();
    	customer.setCustomerUsername("1");
    	customer.setPwdAeadTime(new Date());
    	customer.setPwdCaptcha("123");
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	customerMapperMock.returns(customer).selectCaptcha(1L);
    	customerMapperSiteMock.returns(customerAllInfo).selectCustomerByUname(paramMap);
    	assertEquals(1, customerServiceInterface.validPwdEmail(request, "123", 1L));
    }
    
    /**
     * 验证邮箱存在性测试
     */
    @Test
    public void testCheckEmailExist()
    {
    	customerMapperMock.returns(1L).checkEmailExist("a");
    	Long result = 1L;
    	assertEquals(result, customerServiceInterface.checkEmailExist("a"));
    }
 
    /**
     * 验证手机存在性测试
     */
    @Test
    public void testCheckMobileExist()
    {
    	customerMapperMock.returns(1L).checkMobileExist("123");
    	Long result = 1L;
    	assertEquals(result, customerServiceInterface.checkMobileExist("123"));
    }
    
    /**
     * 验证会员是否存在某订单测试
     */
    @Test
    public void testCheckexistsByIdAndCode()
    {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", 1L);
        paramMap.put("orderCode", "1");
        customerMapperMock.returns(1L).checkexistsByIdAndCode(paramMap);
    	Long result = 1L;
    	assertEquals(result, customerServiceInterface.checkexistsByIdAndCode(1L, "1"));
    }
    
    /**
     * 查询可以投诉的订单测试
     */
    @Test
    public void testQueryOrdersForComplain()
    {
    	Map<String, Object> paramMap = new HashMap<>();
    	customerMapperMock.returns(1L).queryCountForComplain(paramMap);
    	paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        customerMapperMock.returns(lists).queryordersforcomplain(paramMap);
        
    	assertEquals(1, customerServiceInterface.queryOrdersForComplain(paramMap, pb).getList().size());
    }
    
    /**
     * 查询用户所有获得积分总和测试
     */
    @Test
    public void testQuerySumByCustId()
    {
    	customerMapperMock.returns(1).querySumByCustId(1L);
    	assertEquals(1, customerServiceInterface.querySumByCustId(1L));
    }
    
    /**
     * 根据客户id修改客户等级测试
     */
    @Test
    public void testUpdCustLevel()
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pointLevelId",1L);
        map.put("pointLevelName","a");
        map.put("customerId",1L);
        customerMapperMock.returns(1).updCustLevel(map);
    	assertEquals(1, customerServiceInterface.updCustLevel(1L, "a", 1L));
    }
}
