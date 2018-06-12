package com.junit.test.index.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.channel.bean.ChannelStorey;
import com.ningpai.channel.bean.ChannelStoreyGoods;
import com.ningpai.channel.bean.ChannelStoreyTag;
import com.ningpai.channel.bean.ChannelTrademark;
import com.ningpai.channel.bean.GoodsSiteSearchBean;
import com.ningpai.channel.service.ChannelAdverService;
import com.ningpai.channel.service.ChannelStoreyGoodsService;
import com.ningpai.channel.service.ChannelStoreyService;
import com.ningpai.channel.service.ChannelStoreyTagService;
import com.ningpai.channel.service.ChannelTrademarkService;
import com.ningpai.goods.bean.GoodsCate;
import com.ningpai.index.service.ChannelSiteService;
import com.ningpai.site.goods.service.GoodsCateService;
import com.ningpai.temp.service.ClassifyBarService;
import com.ningpai.temp.vo.ClassifyBarVo;
import com.ningpai.util.PageBean;

/**
 * SERVICE-首页业务逻辑接口 获取首页楼层数据测试
 * @author djk
 *
 */
public class ChannelSiteServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
	@TestedObject
	private ChannelSiteService channelSiteService = new ChannelSiteService();
    
	/**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ChannelStoreyService> channelStoreyServiceMock;
    
	/**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ChannelStoreyGoodsService> channelStoreyGoodsServiceMock;
    
	/**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ChannelAdverService> channelAdverServiceMock;
    
	/**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ChannelTrademarkService> channelTrademarkServiceMock;
    
	/**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ChannelStoreyTagService> channelStoreyTagServiceMock;
    
	/**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsCateService> goodsCateServiceMock;
    
	/**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ClassifyBarService> classifyBarServiceMock;
    
    /**
     * 通过楼层标签id查询商品列表测试
     */
    @Test
    public void testSelectStoreyTagProductsByTagId()
    {
    	List<ChannelStoreyGoods> tagGoodsList = new ArrayList<>();
    	tagGoodsList.add(new ChannelStoreyGoods());
    	channelStoreyGoodsServiceMock.returns(tagGoodsList).selectchannelStoreyGoodsByParamForSite(null, 1L, null);
    	assertEquals(1, channelSiteService.selectStoreyTagProductsByTagId(1L).size());
    }
    
    /**
     * 初始化频道分类导航数据测试
     */
    @Test
    public void testGetClassifyBar()
    {
        List<ChannelTrademark> boxBrandList  = new ArrayList<>();
        boxBrandList.add(new ChannelTrademark());
    	
    	List<ChannelAdver> boxAdverList = new ArrayList<>();
    	boxAdverList.add(new ChannelAdver());
    	
    	ClassifyBarVo classifyBarVo = new ClassifyBarVo();
    	classifyBarVo.setClassifyBarId(1L);
    	List<ClassifyBarVo> classifyBar = new ArrayList<>();
    	classifyBar.add(classifyBarVo);
    	
    	List<ChannelAdver> adverList = new ArrayList<>();
    	adverList.add(new ChannelAdver());
    	
    	List<ChannelTrademark> brandList = new ArrayList<>();
    	brandList.add(new ChannelTrademark());
    	
    	channelAdverServiceMock.returns(boxAdverList).selectchannelAdverByParamForSite(1L, null, null, null, 161L, 151L, null, "1", null, null);
    	channelTrademarkServiceMock.returns(boxBrandList).selectChannelTrademarkByParamForSite(1L, null, null, null, null, "1", null);
    	classifyBarServiceMock.returns(classifyBar).selectClassifyBarByParamSite(null, 1L, null);
    	channelAdverServiceMock.returns(adverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, "1", null,
                null, null);
    	channelTrademarkServiceMock.returns(brandList).selectChannelTrademarkByParamForSite(null, null, null, null, "1", null, null);
    	assertNotNull(channelSiteService.getClassifyBar(1L, 1L));
    }
    
    /**
     * 初始化首页楼层数据测试
     */
    @Test
    public void testGetStoreys()
    {
    	List<ChannelStorey> storeyList = new ArrayList<>();
    	storeyList.add(new ChannelStorey());
    	List<GoodsCate> listcate = new ArrayList<>();
    	listcate.add(new GoodsCate());
    	listcate.add(new GoodsCate());
    	List<ChannelStoreyGoods> storeyGoodsList  = new ArrayList<>();
    	ChannelStoreyGoods channelStoreyGoods = new ChannelStoreyGoods();
    	channelStoreyGoods.setGoodsproductNo("1");
    	channelStoreyGoods.setGoodsproductPrice(new BigDecimal(1));
    	channelStoreyGoods.setGoodsproductId(1L);
    	storeyGoodsList.add(channelStoreyGoods);
    	List<ChannelAdver> adverList = new ArrayList<>();
    	adverList.add(new ChannelAdver());
    	List<ChannelTrademark> brandList = new ArrayList<>();
    	brandList.add(new ChannelTrademark());
    	List<ChannelStoreyTag> tagList = new ArrayList<>();
    	tagList.add(new ChannelStoreyTag());
    	channelStoreyServiceMock.returns(storeyList).selectchannelStoreyByParamForSite(1L, null, null);
    	goodsCateServiceMock.returns(listcate).queryCatIdsByCatId(null);
    	channelStoreyGoodsServiceMock.returns(storeyGoodsList).selectchannelStoreyGoodsByParamForSite(null, null, null);
    	channelAdverServiceMock.returns(adverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, null, "0", null, null);
    	channelTrademarkServiceMock.returns(brandList).selectChannelTrademarkByParamForSite(null, null,null, null, null, null, null);
    	channelStoreyTagServiceMock.returns(tagList).selectchannelStoreyTagByParamForSite(null, null, null);
    	assertNotNull(channelSiteService.getStoreys(1L, 1L));
    }
    
    /**
     * 
     */
    @Test
    public void testGetChannelStoreys()
    {
    	List<ChannelStoreyTag> tagList = new ArrayList<>();
    	tagList.add(new ChannelStoreyTag());
    	List<ChannelTrademark> brandList =new ArrayList<>();
    	brandList.add(new ChannelTrademark());
    	List<ChannelAdver> adverList = new ArrayList<>();
    	adverList.add(new ChannelAdver());
    	List<ChannelStoreyGoods> storeyGoodsList = new ArrayList<>();
    	storeyGoodsList.add(new ChannelStoreyGoods());
    	List<GoodsCate> listcate = new ArrayList<>();
    	listcate.add(new GoodsCate());
    	listcate.add(new GoodsCate());
    	List<ChannelStorey> storeyList = new ArrayList<>();
    	storeyList.add(new ChannelStorey());
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	GoodsSiteSearchBean searchBean = new GoodsSiteSearchBean();
    	PageBean pageBean = new PageBean();
    	channelStoreyServiceMock.returns(storeyList).selectchannelStoreyByParamForSite(1L, null, null);
    	goodsCateServiceMock.returns(listcate).queryCatIdsByCatId(null);
    	channelStoreyGoodsServiceMock.returns(storeyGoodsList).selectchannelStoreyGoodsByParamForChannelSite(request, null, null, null,
                searchBean, pageBean);
    	channelAdverServiceMock.returns(adverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, null, "0", null, null);
    	channelTrademarkServiceMock.returns(brandList).selectChannelTrademarkByParamForSite(null, null, null, null, null, null, null);
    	channelStoreyTagServiceMock.returns(tagList).selectchannelStoreyTagByParamForSite(null, null, null);
    	assertNotNull(channelSiteService.getChannelStoreys(request, 1L, 1L, searchBean, pageBean));
    }
}
