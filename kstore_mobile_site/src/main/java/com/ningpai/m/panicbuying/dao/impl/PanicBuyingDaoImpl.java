package com.ningpai.m.panicbuying.dao.impl;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.panicbuying.dao.PanicBuyingDao;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PanicBuyingDaoImpl
 * 抢购数据库实现类
 * @author djk
 * @date 2016/1/18
 */

@Repository("panicBuyingDao")
public class PanicBuyingDaoImpl extends BasicSqlSupport implements PanicBuyingDao {

    /**
     * 查询抢购的货品
     *
     * @return 返回参与抢购的货品
     */
    @Override
    public List<GoodsProductVo> queryGoodsProductVos() {
        return this.selectList("com.ningpai.m.panicbuying.dao.PanicBuyingDao.queryGoodsProductVos");
    }

    /**
     * 查询抢购的广告
     *
     * @return 返回抢购的广告
     */
    @Override
    public List<ChannelAdver> queryChannelAdvers() {
        return this.selectList("com.ningpai.m.panicbuying.dao.PanicBuyingDao.queryChannelAdvers");
    }
}
