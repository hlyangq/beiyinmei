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

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.channel.bean.ChannelStorey;
import com.ningpai.channel.bean.ChannelStoreyGoods;
import com.ningpai.channel.bean.ChannelStoreyTag;
import com.ningpai.channel.dao.ChannelStoreyMapper;
import com.ningpai.channel.service.ChannelAdverService;
import com.ningpai.channel.service.ChannelStoreyGoodsService;
import com.ningpai.channel.service.ChannelStoreyTagService;
import com.ningpai.site.thirdseller.bean.ThirdStoreInfo;
import com.ningpai.site.thirdseller.dao.ThirdStoreInfoMapper;
import com.ningpai.site.thirdseller.service.ThirdSellerSiteService;
import com.ningpai.site.thirdseller.service.impl.ThirdSellerSiteServiceImpl;

/**
 * 第三方店铺首页测试
 * @author djk
 *
 */
public class ThirdSellerSiteServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private ThirdSellerSiteService thirdSellerSiteService = new ThirdSellerSiteServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ChannelStoreyMapper> channelStoreyMapperMock;
    
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
    private Mock<ChannelStoreyTagService> channelStoreyTagServiceMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<ThirdStoreInfoMapper> thirdStoreInfoMapperMock;
    
    /**
     * 第三方店铺首页楼层数据测试
     */
    @Test
    public void testGetStoreys()
    {
    	List<ChannelStoreyTag> tagList = new ArrayList<>();
    	tagList.add(new ChannelStoreyTag());
    	List<ChannelStoreyGoods> storeyGoodsList = new ArrayList<>();
    	storeyGoodsList.add(new ChannelStoreyGoods());
    	List<ChannelAdver> adverList = new ArrayList<>();
    	adverList.add(new ChannelAdver());
    	ChannelStorey channelStorey = new ChannelStorey();
    	List<ChannelStorey> storeyList = new ArrayList<>();
    	storeyList.add(channelStorey);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("channelId", null);
        map.put("tempId", 1L);
        map.put("temp1", 1L);
        channelStoreyMapperMock.returns(storeyList).selectchannelStoreyByParamForSite(map);
        channelAdverServiceMock.returns(adverList).selectchannelAdverByParamForSite(null, null, null, null, 161L, 151L, null, "0", null, null);
        channelStoreyGoodsServiceMock.returns(storeyGoodsList).selectchannelStoreyGoodsByParamForSite(null, null, null);
        channelStoreyTagServiceMock.returns(tagList).selectchannelStoreyTagByParamForSite(null, null, null);
        assertNotNull(thirdSellerSiteService.getStoreys(1L, 1L));
    }
    
    /**
     * 根据店铺Id查询店铺信息测试
     */
    @Test
    public void testSelectByThirdId()
    {
    	thirdStoreInfoMapperMock.returns(new ThirdStoreInfo()).selectByThirdId(1L);
    	assertNotNull(thirdSellerSiteService.selectByThirdId(1L));
    }
}
