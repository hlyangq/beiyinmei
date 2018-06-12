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
import com.ningpai.channel.bean.GoodsCate;
import com.ningpai.channel.service.ChannelAdverService;
import com.ningpai.channel.service.ChannelStoreyGoodsService;
import com.ningpai.channel.service.ChannelStoreyService;
import com.ningpai.channel.service.ChannelStoreyTagService;
import com.ningpai.channel.service.GoodsCateService;
import com.ningpai.index.service.ThirdIndexSiteService;
import com.ningpai.temp.service.ClassifyBarService;
import com.ningpai.temp.vo.ClassifyBarVo;

/**
 * SERVICE-第三方商家店铺数据获取业务测试
 * @author djk
 *
 */
public class ThirdIndexSiteServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
	@TestedObject
	private ThirdIndexSiteService thirdIndexSiteService = new ThirdIndexSiteService();
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ChannelStoreyService> channelStoreyServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ChannelStoreyTagService> channelStoreyTagServiceMock;
	
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
	private Mock<GoodsCateService> goodsCateServiceMock;
	
	/**
     * 模拟
     */
    @InjectIntoByType
	private Mock<ClassifyBarService> classifyBarServiceMock;
    
    /**
     *  初始化首页分类导航数据测试
     */
    @Test
    public void testGetClassifyBar()
    {
    	List<ClassifyBarVo> classifyBar = new ArrayList<>();
    	classifyBar.add(new ClassifyBarVo());
    	classifyBarServiceMock.returns(classifyBar).selectClassifyBarByParamSite(1L, null, "1");
    	assertNotNull(thirdIndexSiteService.getClassifyBar(1L,"1"));
    }
    
    @Test
    public void testGetStoreys()
    {
    	List<ChannelAdver> tagAdverList = new ArrayList<>();
    	tagAdverList.add(new ChannelAdver());
    	List<ChannelStoreyGoods> tagGoodsList = new ArrayList<>();
    	tagGoodsList.add(new ChannelStoreyGoods());
    	List<ChannelStoreyTag> tagList = new ArrayList<>();
    	tagList.add(new ChannelStoreyTag());
    	List<ChannelAdver> adverList = new ArrayList<>();
    	adverList.add(new ChannelAdver());
    	List<ChannelStoreyGoods> storeyGoodsList = new ArrayList<>();
    	storeyGoodsList.add(new ChannelStoreyGoods());
    	List<GoodsCate> listcate = new ArrayList<>();
    	listcate.add(new GoodsCate());
    	List<ChannelStorey> storeyList = new ArrayList<>();
    	storeyList.add(new ChannelStorey());
    	channelStoreyServiceMock.returns(storeyList).selectchannelStoreyByParamForSite(null, 1L, "1");
    	goodsCateServiceMock.returns(listcate).queryGoosCateByParentId(null);
    	channelStoreyGoodsServiceMock.returns(storeyGoodsList).selectchannelStoreyGoodsByParamForSite(null, null, null);
    	channelAdverServiceMock.returns(adverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, null, "0", null, null);
    	channelStoreyTagServiceMock.returns(tagList).selectchannelStoreyTagByParamForSite(null, null, null);
    	channelStoreyGoodsServiceMock.returns(tagGoodsList).selectchannelStoreyGoodsByParamForSite(null, null, null);
    	channelAdverServiceMock.returns(tagAdverList).selectchannelAdverByParamForSite(null, null, null,null, 161L, 151L, null,
                "0", null, null);
    	assertNotNull(thirdIndexSiteService.getStoreys(1L, "1"));
    }
    
    
}
