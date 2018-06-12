package com.junit.test.site.giftshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.site.giftshop.bean.Gift;
import com.ningpai.site.giftshop.bean.GiftCate;
import com.ningpai.site.giftshop.dao.GiftShopSiteMapper;
import com.ningpai.site.giftshop.service.GiftShopSiteService;
import com.ningpai.site.giftshop.service.impl.GiftShopSiteServiceImpl;
import com.ningpai.site.giftshop.vo.GiftSearchVo;
import com.ningpai.util.PageBean;

/**
 * 赠品分类service接口测试
 * @author djk
 *
 */
public class GiftShopSiteServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private GiftShopSiteService giftShopSiteService = new GiftShopSiteServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GiftShopSiteMapper> giftShopSiteMapperMock;
    
    /**
     * 赠品分类
     */
    private GiftCate giftCate = new GiftCate();
    
    /**
     * 赠品分类集合
     */
    private List<GiftCate> giftCateList = new ArrayList<>();
    
    /**
     * 赠品分类集合
     */
    private List<GiftCate> allCateList = new ArrayList<>();
    
    /**
     *  积分商城商品搜索
     */
    private GiftSearchVo giftSearchVo = new GiftSearchVo();
    
    /**
     * 分页辅助类
     */
    private PageBean pb = new PageBean();
    
    @Override
    protected void setUp() throws Exception 
    {
    	GiftCate giftCate2 = new GiftCate();
    	giftCate2.setGiftParentId(1L);
    	giftCate2.setGiftCateId(2L);
    	giftCate.setGiftCateId(1L);
    	allCateList.add(giftCate2);
    	giftCateList.add(giftCate);
    }
    
    /**
     * 查询赠品分类测试
     */
    @Test
    public void testSearchGiftCate()
    {
    	giftShopSiteMapperMock.returns(giftCateList).selectGiftParentCateList();
    	giftShopSiteMapperMock.returns(allCateList).selectAllCate();
    	
    	assertEquals(1, giftShopSiteService.searchGiftCate().size());
    }
    
    /**
     * 查询赠品列表测试
     */
    @Test
    public void testSearchGiftList()
    {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startIntegral", giftSearchVo.getStartIntegral());
        paramMap.put("endIntegral", giftSearchVo.getEndIntegral());
        giftShopSiteMapperMock.returns(1).searchGiftListCount(paramMap);
        paramMap.put("start", pb.getStartRowNum());
        paramMap.put("number", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        giftShopSiteMapperMock.returns(lists).searchGiftList(paramMap);
        assertNotNull(giftShopSiteService.searchGiftList(giftSearchVo, pb).getList().size());
    }
    
    /**
     * 根据赠品Id查询赠品详情测试
     */
    @Test
    public void testSelectByGiftId()
    {
    	giftShopSiteMapperMock.returns(new Gift()).queryDetailByGiftId(1L);
    	assertNotNull(giftShopSiteService.selectByGiftId(1L));
    }
    
    /**
     * 根据分类Id查询分类并且计算好所有的子级关系测试
     */
    @Test
    public void testSelectByParentId()
    {
    	giftShopSiteMapperMock.returns(giftCate).selectByCateId(1L);
    	giftShopSiteMapperMock.returns(allCateList).selectAllCate();
    	assertNotNull(giftShopSiteService.selectByParentId(1L));
    }
    
    /**
     * 根据类型ID查询分类信息,仅是查询当前分类本身以及父分类
     */
    @Test
    public void testSelectByCateId()
    {
    	giftShopSiteMapperMock.returns(giftCate).selectByCateId(1L);
    	assertNotNull(giftShopSiteService.selectByCateId(1L));
    }
    
    /**
     * 根据父分类ID查询第一个子分类测试
     */
    @Test
    public void testSelectSonCateId()
    {
    	giftShopSiteMapperMock.returns(1L).querysCateIdBypCateId(1L);
    	assertEquals("1-1", giftShopSiteService.selectSonCateId(1L));
    }
    
}
