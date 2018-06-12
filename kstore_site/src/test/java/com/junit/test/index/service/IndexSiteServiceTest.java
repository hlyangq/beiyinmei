package com.junit.test.index.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.channel.bean.ChannelStorey;
import com.ningpai.channel.bean.ChannelStoreyGoods;
import com.ningpai.channel.bean.ChannelStoreyTag;
import com.ningpai.channel.bean.ChannelTrademark;
import com.ningpai.channel.service.ChannelAdverService;
import com.ningpai.channel.service.ChannelStoreyGoodsService;
import com.ningpai.channel.service.ChannelStoreyService;
import com.ningpai.channel.service.ChannelStoreyTagService;
import com.ningpai.channel.service.ChannelTrademarkService;
import com.ningpai.goods.bean.GoodsCate;
import com.ningpai.index.service.IndexSiteService;
import com.ningpai.site.goods.service.GoodsCateService;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.temp.service.ClassifyBarCateService;
import com.ningpai.temp.service.ClassifyBarQuickService;
import com.ningpai.temp.service.ClassifyBarService;
import com.ningpai.temp.vo.ClassifyBarVo;

/**
 * SERVICE-首页业务逻辑接口 获取首页楼层数据测试
 * @author djk
 *
 */
public class IndexSiteServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
	@TestedObject
	private IndexSiteService indexSiteService;
	
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
	private Mock<GoodsProductService> goodsProductServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ClassifyBarService> classifyBarServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ClassifyBarCateService> cassifyBarCateServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ClassifyBarQuickService> classifyBarQuickServiceMock;
    
    /**
     * 初始化首页分类导航数据测试
     */
    @Test
    public void testGetClassifyBar()
    {
    	List<ChannelTrademark> brandList = new ArrayList<>();
    	brandList.add(new ChannelTrademark());
    	List<ChannelAdver> adverList = new ArrayList<>();
    	adverList.add(new ChannelAdver());
    	ClassifyBarVo classifyBarVo = new ClassifyBarVo();
    	List<ClassifyBarVo> classifyBar = new ArrayList<>();
    	classifyBarVo.setClassifyBarId(1L);
    	classifyBar.add(classifyBarVo);
    	List<ChannelAdver> boxAdverList = new ArrayList<>();
    	boxAdverList.add(new ChannelAdver());
    	List<ChannelTrademark> boxBrandList = new ArrayList<>();
    	boxBrandList.add(new ChannelTrademark());
    	channelAdverServiceMock.returns(boxAdverList).selectchannelAdverByParamForSite(null, 1L, null, null, 161L, 151L, null, "1", null, null);
    	channelTrademarkServiceMock.returns(boxBrandList).selectChannelTrademarkByParamForSite(null, 1L, null, null, null, "1", null);
    	classifyBarServiceMock.returns(classifyBar).selectClassifyBarByParamSite(1L, null, null);
    	channelAdverServiceMock.returns(adverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, "1", null, null, null);
    	channelTrademarkServiceMock.returns(brandList).selectChannelTrademarkByParamForSite(null, null, null, null,"1", null, null);
    	assertNotNull(indexSiteService.getClassifyBar(1L));
    }
    
    /**
     * 初始化首页楼层数据测试
     */
    @Test
    public void testGetStoreys()
    {
    	List<ChannelTrademark> tagBrandList = new ArrayList<>();
    	tagBrandList.add(new ChannelTrademark());
    	List<ChannelAdver> tagAdverList = new ArrayList<>();
    	tagAdverList.add(new ChannelAdver());
    	List<ChannelStoreyGoods> tagGoodsList  = new ArrayList<>();
    	tagGoodsList.add(new ChannelStoreyGoods());
    	List<ChannelStoreyTag> tagList = new ArrayList<>();
    	tagList.add(new ChannelStoreyTag());
    	List<ChannelTrademark> brandList = new ArrayList<>();
    	brandList.add(new ChannelTrademark());
    	List<ChannelAdver> adverList = new ArrayList<>();
    	adverList.add(new ChannelAdver());
    	List<ChannelStoreyGoods> storeyGoodsList = new ArrayList<>();
    	storeyGoodsList.add(new ChannelStoreyGoods());
    	List<GoodsCate> listcate = new ArrayList<>();
    	listcate.add(new GoodsCate());
    	listcate.add(new GoodsCate());
    	List<ChannelStorey> storeyList  = new ArrayList<>();
    	storeyList.add(new ChannelStorey());
    	channelStoreyServiceMock.returns(storeyList).selectchannelStoreyByParamForSite(null, 1L, null);
    	goodsCateServiceMock.returns(listcate).queryCatIdsByCatId(null);
    	channelStoreyGoodsServiceMock.returns(storeyGoodsList).selectchannelStoreyGoodsByParamForSite(null, null, null);
    	channelAdverServiceMock.returns(adverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, null, "0", null, null);
    	channelTrademarkServiceMock.returns(brandList).selectChannelTrademarkByParamForSite(null, null, null, null, null, null, null);
    	channelStoreyTagServiceMock.returns(tagList).selectchannelStoreyTagByParamForSite(null, null, null);
    	channelStoreyGoodsServiceMock.returns(tagGoodsList).selectchannelStoreyGoodsByParamForSite(null, null, null);
    	channelAdverServiceMock.returns(tagAdverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, null,
                "0", null, null);
    	channelTrademarkServiceMock.returns(tagBrandList).selectChannelTrademarkByParamForSite(null, null, null, null, null, null,
                null);
    	assertNotNull(indexSiteService.getStoreys(1L));
    }
    
    /**
     * 根据模板ID，获取页面标签列表测试
     */
    @Test
    public void testGetTagListForTempId()
    {
    	List<ChannelTrademark> tagBrandList = new ArrayList<>();
    	tagBrandList.add(new ChannelTrademark());
    	List<ChannelAdver> tagAdverList = new ArrayList<>();
    	tagAdverList.add(new ChannelAdver());
    	List<ChannelStoreyGoods> tagGoodsList = new ArrayList<>();
    	tagGoodsList.add(new ChannelStoreyGoods());
    	List<ChannelStoreyTag>  lists = new ArrayList<>();
    	lists.add(new ChannelStoreyTag());
    	channelStoreyTagServiceMock.returns(lists).selectchannelStoreyTagByParamForSite(null, 1L, null);
    	channelStoreyGoodsServiceMock.returns(tagGoodsList).selectchannelStoreyGoodsByParamForSite(null, null, null);
    	channelAdverServiceMock.returns(tagAdverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, null,
                "0", null, null);
    	channelTrademarkServiceMock.returns(tagBrandList).selectChannelTrademarkByParamForSite(null, null, null, null, null, null,
                null);
    	assertEquals(1, indexSiteService.getTagListForTempId(1L).size());
    }
}
