package com.junit.test.site.giftshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ningpai.customer.bean.CustomerPoint;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.customer.bean.CustomerInfo;
import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.dao.CustomerInfoMapper;
import com.ningpai.customer.dao.CustomerPointMapper;
import com.ningpai.customer.service.PointLevelServiceMapper;
import com.ningpai.site.giftshop.bean.Gift;
import com.ningpai.site.giftshop.bean.GiftOrder;
import com.ningpai.site.giftshop.dao.GiftOrderMapper;
import com.ningpai.site.giftshop.dao.GiftShopSiteMapper;
import com.ningpai.site.giftshop.service.GiftOrderService;
import com.ningpai.site.giftshop.service.impl.GiftOrderServiceImpl;
import com.ningpai.site.giftshop.vo.GiftOrderVo;
import com.ningpai.util.PageBean;

/**
 * 积分商城赠品订单Service接口测试
 * @author djk
 *
 */
public class GiftOrderServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private GiftOrderService giftOrderService = new GiftOrderServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GiftOrderMapper> giftOrderMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerPointMapper> customerPointMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerInfoMapper> customerInfoMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<PointLevelServiceMapper> pointLevelServiceMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GiftShopSiteMapper> giftShopSiteMapperMock;
    
    /**
     * 赠品订单
     */
    private GiftOrderVo giftOrderVo = new GiftOrderVo();
    
    /**
     * 积分兑换赠品订单实体
     */
    private GiftOrder giftOrder = new GiftOrder();
    
    /**
     * 会员详细信息
     */
    private CustomerInfo customerInfo = new CustomerInfo();
    
    /**
    * 会员等级信息
    */
    private CustomerPointLevel customerPointLevel = new CustomerPointLevel();
    
    /**
     * 会员等级信息集合
     */
    private List<CustomerPointLevel> customerPointLevelLists = new ArrayList<>();
    
    
    @Override
    protected void setUp() throws Exception 
    {
    	customerPointLevel.setPointNeed("0~10");
    	customerPointLevelLists.add(customerPointLevel);
    	customerInfo.setInfoPointSum(1);
    	giftOrder.setTemp1("1");
    	giftOrder.setOrderIntegral(1L);
    	giftOrder.setCustomerId(1L);
    	
    }
    
//    /**
//     * 提交订单测试
//     */
//    @Test
//    public void testSubOrder()
//    {
//        Gift gift = new Gift();
//        gift.setDelFlag("0");
//        gift.setGiftIntegral(1L);
//        giftShopSiteMapperMock.returns(gift).queryDetailByGiftId(1L);
//    	giftOrderMapperMock.returns(1).insertSelective(giftOrder);
//        giftShopSiteMapperMock.returns(1).updateGIftById(gift);
//        CustomerPoint customerPoint = new CustomerPoint();
//        customerPoint.setPoint(1);
//        customerPointMapperMock.returns(1).insertSelective(customerPoint);
//    	customerInfoMapperMock.returns(customerInfo).selectCustInfoById(1L);
//    	pointLevelServiceMapperMock.returns(customerPointLevelLists).selectAllPointLevel();
//    	assertEquals(1, giftOrderService.subOrder(giftOrder, 1L));
//    }
    
    /**
     * 查询积分兑换商品排行测试
     */
    @Test
    public void testOrderList()
    {
    	List<GiftOrderVo> lists = new ArrayList<>();
    	lists.add(giftOrderVo);
    	giftOrderMapperMock.returns(lists).orderVoList();
    	assertEquals(1, giftOrderService.orderList().size());
    }
    
    /**
     * 前台查询积分兑换列表测试
     */
    @Test
    public void testQueryGiftOrder()
    {
    	PageBean pb = new PageBean();
    	Map<String, Object> paramMap = new HashMap<>();
    	giftOrderMapperMock.returns(1).giftOrderCount(paramMap);
    	paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        giftOrderMapperMock.returns(lists).giftOrderList(paramMap);
    	assertEquals(1, giftOrderService.queryGiftOrder(pb, paramMap).getList().size());
    }
    
    /**
     *  修改订单信息测试
     */
    @Test
    public void testUpdateOrderVice()
    {
    	giftOrderMapperMock.returns(giftOrder).selectByPrimaryKey(1L);
    	giftOrderMapperMock.returns(1).updateByPrimaryKeySelective(giftOrder);
    	assertEquals(1, giftOrderService.updateOrderVice(1L));
    }
    
    /**
     * 订单详情测试
     */
    @Test
    public void testOrderDetail()
    {
    	giftOrderMapperMock.returns(giftOrderVo).selectByOrderId(1L);
    	assertNotNull(giftOrderService.orderDetail(1L));
    }
    
}
