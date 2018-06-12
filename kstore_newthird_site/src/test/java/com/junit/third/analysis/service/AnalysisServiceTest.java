package com.junit.third.analysis.service;

import com.ningpai.third.analysis.bean.OCustomerFollow;
import com.ningpai.third.analysis.bean.OOrder;
import com.ningpai.third.analysis.dao.AnalysisServiceMapper;
import com.ningpai.third.analysis.service.AnalysisService;
import com.ningpai.third.analysis.service.impl.AnalysisServiceImpl;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AnalysisServiceTest
 *
 * @author djk
 * @date 2016/3/28
 */
public class AnalysisServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的类
     */
    @TestedObject
    private AnalysisService analysisService = new AnalysisServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<AnalysisServiceMapper> analysisServiceMapperMock;

    /**
     * 查询收藏商品列表测试
     */
    @Test
    public void testSelectThirdFollowGoods() {

        OCustomerFollow oCustomerFollow = new OCustomerFollow();
        oCustomerFollow.setGoodsId(1L);
        // 装载查询条件
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 商家ID
        paramMap.put("thirdId", 1L);
        // 起始时间
        paramMap.put("startTime", "");
        // 终止日期
        paramMap.put("endTime", " 23:59:59");
        paramMap.put("catId", 1L);

        List<OCustomerFollow> glist = new ArrayList<>();
        glist.add(oCustomerFollow);
        analysisServiceMapperMock.returns(glist).selectThirdFollowGoods(paramMap);
        paramMap.put("goodsInfoId", 1L);
        analysisServiceMapperMock.returns(glist).selectThirdFollowGoodsCount(paramMap);
        assertNotNull(analysisService.selectThirdFollowGoods(1L, "", "", 1L));
    }


    /**
     * 根据商家ID查询订单量测试
     */
    @Test
    public void testQueryCountByDay() {
        // 装载查询条件
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 商家ID
        paramMap.put("businessId", 1L);
        analysisServiceMapperMock.returns(new ArrayList<>()).queryCountByDay(paramMap);
        assertNotNull(analysisService.queryCountByDay(1L));
    }

    /**
     * 根据商家ID查询一段时间内不成功的订单测试
     */
    @Test
    public void testQueryNoSuccCountByDay() {

        // 用来装载查询条件
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 开始日期
        paramMap.put("startTime", "");
        // 结束日期
        paramMap.put("endTime", "");
        // 商家ID
        paramMap.put("businessId", 1L);
        analysisServiceMapperMock.returns(new OOrder()).queryNoSuccCountByDay(paramMap);
        assertNotNull(analysisService.queryNoSuccCountByDay("", "", 1L));
    }

    /**
     * 根据商家ID查询一段时间内成功的订单测试
     */
    @Test
    public void testQuerySuccCountByTime() {

        // 用来装载查询条件
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 起始日期
        paramMap.put("startTime", "");
        // 结束日期
        paramMap.put("endTime", "");
        // 商家ID
        paramMap.put("businessId", 1L);
        analysisServiceMapperMock.returns(new OOrder()).querySuccCountByTime(paramMap);
        assertNotNull(analysisService.querySuccCountByTime("", "", 1L));
    }

}

