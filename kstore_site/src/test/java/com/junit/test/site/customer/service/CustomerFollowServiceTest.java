package com.junit.test.site.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.site.customer.bean.CustomerFollow;
import com.ningpai.site.customer.mapper.CustomerFollowMapper;
import com.ningpai.site.customer.service.CustomerFollowService;
import com.ningpai.site.customer.service.impl.CustomerFollowServiceImpl;
import com.ningpai.site.customer.vo.GoodsBean;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.site.goods.vo.GoodsProductVo;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.util.PageBean;

/**
 *  收藏商品Service测试
 * @author djk
 *
 */
public class CustomerFollowServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private CustomerFollowService customerFollowService = new CustomerFollowServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerFollowMapper> customerFollowMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<DefaultAddressService> defaultAddressServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ProductWareService> productWareServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductService> goodsProductServiceMock;
    
    /**
     * 查询收藏记录测试
     */
    @Test
    public void testSelectCustomerFollow()
    {
    	GoodsProductVo goodsProductVo  = new GoodsProductVo();
    	
    	ProductWare productWare = new ProductWare();
    	CustomerFollow customerFollow = new CustomerFollow();
    	customerFollow.setGoodsId(1L);
    	customerFollow.setGood(new GoodsBean());
    	List<Object> customerFollows  = new ArrayList<>();
    	customerFollows.add(customerFollow);
    	PageBean pb = new PageBean();
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("customerId", 1L);
    	customerFollowMapperMock.returns(1L).selectCustomerFollowCount(1L);
        paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        customerFollowMapperMock.returns(customerFollows).selectCustFollowByCustId(paramMap);
        productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 0L);
        goodsProductServiceMock.returns(goodsProductVo).queryProductByProductId(1L);
    	assertEquals(1, customerFollowService.selectCustomerFollow(paramMap, pb).getList().size());
    }
    
    
    /**
     * 查询收藏记录测试  是第三方的场景
     */
    @Test
    public void testSelectCustomerFollow2()
    {
    	GoodsProductVo goodsProductVo  = new GoodsProductVo();
    	
    	goodsProductVo.setIsThird("1");
    	
    	ProductWare productWare = new ProductWare();
    	CustomerFollow customerFollow = new CustomerFollow();
    	customerFollow.setGoodsId(1L);
    	customerFollow.setGood(new GoodsBean());
    	List<Object> customerFollows  = new ArrayList<>();
    	customerFollows.add(customerFollow);
    	PageBean pb = new PageBean();
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("customerId", 1L);
    	customerFollowMapperMock.returns(1L).selectCustomerFollowCount(1L);
        paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        customerFollowMapperMock.returns(customerFollows).selectCustFollowByCustId(paramMap);
        productWareServiceMock.returns(productWare).queryProductWareByProductIdAndDistinctId(1L, 0L);
        goodsProductServiceMock.returns(goodsProductVo).queryProductByProductId(1L);
    	assertEquals(1, customerFollowService.selectCustomerFollow(paramMap, pb).getList().size());
    }
    
    /**
     * 商品列表专用 查询当前会员是否
     */
    @Test
    public void testSelectCustomerFollowForList()
    {
    	Map<String, Object> map = new HashMap<>();
    	List<String> lists = new ArrayList<>();
    	lists.add("1");
    	customerFollowMapperMock.returns(lists).selectCustFollowByCustIdForList(map);
    	assertEquals(1, customerFollowService.selectCustomerFollowForList(map).size());
    }
    
    /**
     * 取消关注测试
     */
    @Test
    public void testDeleteFollow()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("followId", 1L);
        map.put("customerId", 1L);
        customerFollowMapperMock.returns(1).deleteByPrimaryKey(map);
    	assertEquals(1, customerFollowService.deleteFollow(1L, 1L));
    }
}
