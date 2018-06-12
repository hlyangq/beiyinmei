package com.ningpai.m.panicbuying.dao;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.m.goods.vo.GoodsProductVo;

import java.util.List;

/**
 * PanicBuyingDao
 * 抢购数据库接口
 * @author djk
 * @date 2016/1/18
 */
public interface PanicBuyingDao {

    /**
     * 查询抢购的货品
     * @return  返回参与抢购的货品
     */
    List<GoodsProductVo> queryGoodsProductVos();

    /**
     * 查询抢购的广告
     *
     * @return 返回抢购的广告
     */
    List<ChannelAdver> queryChannelAdvers();
}
