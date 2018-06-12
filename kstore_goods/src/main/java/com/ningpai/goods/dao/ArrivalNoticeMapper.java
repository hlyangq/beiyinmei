package com.ningpai.goods.dao;


import com.ningpai.goods.bean.ArrivalNotice;

import java.util.List;
import java.util.Map;

public interface ArrivalNoticeMapper {
    /**
     * 根据主键删除
     *
     * @param noticeId
     *
     * @return 删除的行数
     */
    int deleteByPrimaryKey(Long noticeId);

    /**
     * 插入商品信息(全属性 不推荐)
     *
     * @param record
     *            实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 最新插入的主键ID {@link java.lang.Integer}
     */
    int insert(ArrivalNotice record);

    /**
     * 插入商品信息(可选属性 推荐)
     *
     * @param record
     *            实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 最新插入的主键ID {@link java.lang.Integer}
     */
    int insertSelective(ArrivalNotice record);

    /**
     * 根据主键查询商品信息
     *
     * @param noticeId
     *            主键ID {@link java.lang.Long}
     * @return 查询到的实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     */
    ArrivalNotice selectByPrimaryKey(Long noticeId);

    /**
     * 更新商品信息 （可选属性 推荐）
     *
     * @param record
     *            需要更新的实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    int updateByPrimaryKeySelective(ArrivalNotice record);

    /**
     * 更新商品信息 （全属性 不推荐）
     *
     * @param record
     *            需要更新的实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    int updateByPrimaryKey(ArrivalNotice record);

    /**
     * 根据实体类查询
     *
     * @param paraMap
     *            需要查询的实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 0不存在 1存在
     */
    Long slelctArrivalNotice(Map<String, Object> paraMap);

    /**
     * 根据货品ID查询到货通知
     *
     * @param paraMap
     *            需要查询的实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 0不存在 1存在
     */
    List<ArrivalNotice> slelctGoodsInfoIdArrivalNotice(Map<String, Object> paraMap);

    /**
     * 查询货品所有的关注信息行数
     *
     * @param map
     *            封装的参数 {@link java.util.Map}
     * @return 查询到的行数 {@link java.lang.Integer}
     */
    int queryTotalCountToProductArrivalNotice(Map<String, Object> map);

    /**
     * 根据货品Id查询关注的列表
     *
     * @param map
     *            参数 {@link java.util.Map}
     * @return 查询到的结果 {@link java.util.List}
     */
    List<Object> queryByProductIdArrivalNotice(Map<String, Object> map);
}