package com.junit.test.site.thirdseller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;




import com.ningpai.site.thirdseller.bean.CollectionSeller;
import com.ningpai.site.thirdseller.bean.ThirdStoreInfo;
import com.ningpai.site.thirdseller.dao.CollectionSellerMapper;
import com.ningpai.site.thirdseller.service.CollectionSellerService;
import com.ningpai.site.thirdseller.service.impl.CollectionSellerServiceImpl;
import com.ningpai.util.PageBean;

/**
 * 店铺集合 测试
 * @author djk
 *
 */
public class CollectionSellerServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private CollectionSellerService collectionSellerService = new CollectionSellerServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CollectionSellerMapper> collectionSellerMapperMock;
    
    /**
     * 根据会员Id查询第三方店铺测试
     */
    @Test
    public void testSelectStoreByCustomerId()
    {
    	collectionSellerMapperMock.returns(new ThirdStoreInfo()).selectStoreByCustomerId(1L);
    	assertNotNull(collectionSellerService.selectStoreByCustomerId(1L));
    }
    
    /**
     * 收藏此商家测试
     */
    @Test
    public void testAddCollectionSeller()
    {
    	CollectionSeller collectionSeller = new CollectionSeller();
    	collectionSellerMapperMock.returns(0).selectCollectionSeller(collectionSeller);
    	collectionSellerMapperMock.returns(1).addCollectionSeller(collectionSeller);
    	assertEquals(1, collectionSellerService.addCollectionSeller(new CollectionSeller()));
    }
    
    /**
     * 查询商家列表测试
     */
    @Test
    public  void testsSellerMyFollw()
    {
    	PageBean pageBean = new PageBean();
        Map<String,Object> paramMap =new HashMap<String, Object>();
        paramMap.put("collectionCustomerId", 1L);
        collectionSellerMapperMock.returns(1).sellerMyFollwCount(paramMap);
        paramMap.put("startRowNum",pageBean.getStartRowNum());
        paramMap.put("endRowNum", pageBean.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        collectionSellerMapperMock.returns(lists).sellerMyFollwList(paramMap);
    	assertEquals(1, collectionSellerService.sellerMyFollw(1L, pageBean).getList().size());
    }
    
    /**
     * 删除收藏测试
     */
    @Test
    public void testDelMyFollw()
    {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("collectionCustomerId", 1L);
        paramMap.put("collectionSellerId", 1L);
        collectionSellerMapperMock.returns(1).delMyFollw(paramMap);
    	assertEquals(1, collectionSellerService.delMyFollw(1L, 1L));
    }
    
}
