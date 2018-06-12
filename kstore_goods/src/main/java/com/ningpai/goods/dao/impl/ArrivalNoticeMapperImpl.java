package com.ningpai.goods.dao.impl;

import com.ningpai.goods.bean.ArrivalNotice;
import com.ningpai.goods.dao.ArrivalNoticeMapper;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wangtp on 2016/5/25.
 */
@Repository("ArrivalNoticeMapper")
public class ArrivalNoticeMapperImpl extends BasicSqlSupport implements ArrivalNoticeMapper {
    /**
     * 根据主键删除
     *
     * @param noticeId
     *
     * @return 删除的行数
     */
    @Override
    public int deleteByPrimaryKey(Long noticeId) {
        return this.delete("com.ningpai.goods.dao.ArrivalNoticeMapper.deleteByPrimaryKey", noticeId);
    }

    /**
     * 插入商品信息(全属性 不推荐)
     *
     * @param record
     *            商品实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 最新插入的主键ID {@link java.lang.Integer}
     */
    @Override
    public int insert(ArrivalNotice record) {
        return this.insert("com.ningpai.goods.dao.ArrivalNoticeMapper.insert", record);
    }

    /**
     * 插入商品信息(可选属性 推荐)
     *
     * @param record
     *            商品实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 最新插入的主键ID {@link java.lang.Integer}
     */
    @Override
    public int insertSelective(ArrivalNotice record) {
        return this.insert("com.ningpai.goods.dao.ArrivalNoticeMapper.insertSelective", record);
    }

    /**
     * 根据主键查询商品信息
     *
     * @param noticeId
     *            商品主键ID {@link java.lang.Long}
     * @return 查询到的商品实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     */
    @Override
    public ArrivalNotice selectByPrimaryKey(Long noticeId) {
        return this.selectOne("com.ningpai.goods.dao.ArrivalNoticeMapper.selectByPrimaryKey", noticeId);
    }

    /**
     * 更新商品信息 （可选属性 推荐）
     *
     * @param record
     *            需要更新的商品实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    @Override
    public int updateByPrimaryKeySelective(ArrivalNotice record) {
        return this.update("com.ningpai.goods.dao.ArrivalNoticeMapper.updateByPrimaryKeySelective", record);
    }

    /**
     * 更新商品信息 （全属性 不推荐）
     *
     * @param record
     *            需要更新的商品实体 {@link com.ningpai.goods.bean.ArrivalNotice}
     * @return 受影响的行数 {@link java.lang.Integer}
     */
    @Override
    public int updateByPrimaryKey(ArrivalNotice record) {
        return this.update("com.ningpai.goods.dao.ArrivalNoticeMapper.updateByPrimaryKey", record);
    }

    /**
     * 验证手机存在性
     * @param paraMap
     *            目标手机 {@link String}
     * @return
     */
    @Override
    public Long slelctArrivalNotice(Map<String, Object> paraMap) {
        return this.selectOne("com.ningpai.goods.dao.ArrivalNoticeMapper.slelctArrivalNotice", paraMap);
    }

    @Override
    public List<ArrivalNotice> slelctGoodsInfoIdArrivalNotice(Map<String, Object> paraMap) {
        return this.selectList("com.ningpai.goods.dao.ArrivalNoticeMapper.slelctGoodsInfoIdArrivalNotice", paraMap);
    }

    @Override
    public int queryTotalCountToProductArrivalNotice(Map<String, Object> map) {
        return this
                .selectOne(
                        "com.ningpai.goods.dao.ArrivalNoticeMapper.queryTotalCountToProductArrivalNotice",
                        map);
    }

    @Override
    public List<Object> queryByProductIdArrivalNotice(Map<String, Object> map) {
        return this.selectList(
                "com.ningpai.goods.dao.ArrivalNoticeMapper.queryByProductIdArrivalNotice", map);
    }

}
