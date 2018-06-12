package com.ningpai.m.panicbuying.service;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.m.panicbuying.bean.MarketingRushUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * PanicBuying
 *
 * 抢购 业务 接口
 * @author djk
 * @date 2016/1/18
 */
public interface PanicBuyingService {

    /**
     * 查询 抢购信息
     * @return 返回抢购信息列表
     */
    List<MarketingRushUtil> queryMarketingRushUtils(HttpServletRequest request);

    /**
     * 查询抢购的广告图片
     *
     * @return 返回抢购的广告图片
     */
    List<ChannelAdver> queryChannelAdvers();
}
