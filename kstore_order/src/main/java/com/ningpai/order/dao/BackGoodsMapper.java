package com.ningpai.order.dao;


import com.ningpai.order.bean.BackGoods;

public interface BackGoodsMapper {

    /**
     * 根据主键删除
     *
     * @param backGoodsId
     *
     * @return 删除的行数
     */
    int deleteByPrimaryKey(Long backGoodsId);

    /**
     * 插入商品信息(全属性 不推荐)
     *
     * @param record
     *            实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 最新插入的主键ID {@link java.lang.Integer}
     */
    int insert(BackGoods record);

    /**
     * 插入商品信息(可选属性 推荐)
     *
     * @param record
     *            实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 最新插入的主键ID {@link java.lang.Integer}
     */
    int insertSelective(BackGoods record);

    /**
     * 根据主键查询商品信息
     *
     * @param backGoodsId
     *            主键ID {@link java.lang.Long}
     * @return 查询到的实体 {@link com.ningpai.order.bean.BackGoods}
     */
    BackGoods selectByPrimaryKey(Long backGoodsId);

    /**
     * 更新商品信息 （可选属性 推荐）
     *
     * @param record
     *            需要更新的实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    int updateByPrimaryKeySelective(BackGoods record);

    /**
     * 更新商品信息 （全属性 不推荐）
     *
     * @param record
     *            需要更新的实体 {@link com.ningpai.order.bean.BackGoods}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    int updateByPrimaryKey(BackGoods record);
}