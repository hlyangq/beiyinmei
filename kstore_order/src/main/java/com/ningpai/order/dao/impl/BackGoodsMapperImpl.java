package com.ningpai.order.dao.impl;

import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.order.bean.BackGoods;
import com.ningpai.order.dao.BackGoodsMapper;
import org.springframework.stereotype.Service;

/**
 * Created by wtp on 2016/5/31.
 */
@Service("BackGoodsMapper")
public class BackGoodsMapperImpl  extends BasicSqlSupport implements BackGoodsMapper{
    /**
     * 根据主键删除
     *
     * @param backGoodsId
     *
     * @return 删除的行数
     */
    @Override
    public int deleteByPrimaryKey(Long backGoodsId) {
        return this.delete("com.ningpai.web.dao.BackGoodsMapper.deleteByPrimaryKey", backGoodsId);
    }

    /**
     * 插入商品信息(全属性 不推荐)
     *
     * @param record
     *            商品实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 最新插入的主键ID {@link java.lang.Integer}
     */
    @Override
    public int insert(BackGoods record) {
        return this.insert("com.ningpai.web.dao.BackGoodsMapper.insert", record);
    }

    /**
     * 插入商品信息(可选属性 推荐)
     *
     * @param record
     *            商品实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 最新插入的主键ID {@link java.lang.Integer}
     */
    @Override
    public int insertSelective(BackGoods record) {
        return this.insert("com.ningpai.web.dao.BackGoodsMapper.insertSelective", record);
    }

    /**
     * 根据主键查询商品信息
     *
     * @param backGoodsId
     *            商品主键ID {@link java.lang.Long}
     * @return 查询到的商品实体 {@link com.ningpai.order.bean.BackGoods}
     */
    @Override
    public BackGoods selectByPrimaryKey(Long backGoodsId) {
        return this.selectOne("com.ningpai.web.dao.BackGoodsMapper.selectByPrimaryKey", backGoodsId);
    }

    /**
     * 更新商品信息 （可选属性 推荐）
     *
     * @param record
     *            需要更新的商品实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    @Override
    public int updateByPrimaryKeySelective(BackGoods record) {
        return this.update("com.ningpai.web.dao.BackGoodsMapper.updateByPrimaryKeySelective", record);
    }

    /**
     * 更新商品信息 （全属性 不推荐）
     *
     * @param record
     *            需要更新的商品实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    @Override
    public int updateByPrimaryKey(BackGoods record) {
        return this.update("com.ningpai.web.dao.BackGoodsMapper.updateByPrimaryKey", record);
    }
}
