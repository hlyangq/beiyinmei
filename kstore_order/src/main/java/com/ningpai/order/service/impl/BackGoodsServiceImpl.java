package com.ningpai.order.service.impl;

import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.order.bean.BackGoods;
import com.ningpai.order.dao.BackGoodsMapper;
import com.ningpai.order.service.BackGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wtp on 2016/5/31.
 */
@Service("backGoodsService")
public class BackGoodsServiceImpl extends BasicSqlSupport implements BackGoodsService {

    @Resource(name = "BackGoodsMapper")
    private BackGoodsMapper backGoodsMapper;
    /**
     * 根据主键删除
     *
     * @param backGoodsId
     *
     * @return 删除的行数
     */
    @Override
    public int deleteByPrimaryKey(Long backGoodsId) {
        return backGoodsMapper.deleteByPrimaryKey(backGoodsId);
    }

    /**
     * 插入商品信息(全属性 不推荐)
     *
     * @param record
     *            商品实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 最新插入的主键ID {@link Integer}
     */
    @Override
    public int insert(BackGoods record) {
        return backGoodsMapper.insert(record);

    }

    /**
     * 插入商品信息(可选属性 推荐)
     *
     * @param record
     *            商品实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 最新插入的主键ID {@link Integer}
     */
    @Override
    public int insertSelective(BackGoods record) {
        return backGoodsMapper.insertSelective(record);

    }

    /**
     * 根据主键查询商品信息
     *
     * @param backGoodsId
     *            商品主键ID {@link Long}
     * @return 查询到的商品实体 {@link com.ningpai.order.bean.BackGoods}
     */
    @Override
    public BackGoods selectByPrimaryKey(Long backGoodsId) {
        return backGoodsMapper.selectByPrimaryKey(backGoodsId);
    }

    /**
     * 更新商品信息 （可选属性 推荐）
     *
     * @param record
     *            需要更新的商品实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 受影响的行数 {@link Integer}
     */
    @Override
    public int updateByPrimaryKeySelective(BackGoods record) {
        return backGoodsMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 更新商品信息 （全属性 不推荐）
     *
     * @param record
     *            需要更新的商品实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 受影响的行数 {@link Integer}
     */
    @Override
    public int updateByPrimaryKey(BackGoods record) {
        return backGoodsMapper.updateByPrimaryKey(record);
    }
}
