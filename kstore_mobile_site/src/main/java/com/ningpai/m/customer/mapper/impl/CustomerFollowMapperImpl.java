/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.mapper.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ningpai.m.customer.bean.CustomerFollow;
import com.ningpai.m.customer.mapper.CustomerFollowMapper;
import com.ningpai.m.goods.util.ProductCommentUtilBean;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * @see com.ningpai.site.customer.mapper.CustomerFollowMapper
 * @author NINGPAI-zhangqiang
 * @since 2014年4月14日 下午2:29:11
 * @version 0.0.1
 */
@Repository("customerFollowMapperSite")
public class CustomerFollowMapperImpl extends BasicSqlSupport implements
        CustomerFollowMapper {

    /**
     * 根据货品ID获取 该货品的评分
     * @param productId
     * @return
     */
    @Override
    public ProductCommentUtilBean queryCommentCountAndScoreByProductId(Long productId) {
        return this.selectOne("com.ningpai.m.customer.dao.CustomerFollowMapper.queryCommentCountAndScoreByProductId",productId);
    }

    /**
     * 根据主键删除
     * @param map
     *            收藏编号
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Map<String, Object> map) {
        return this
                .update("com.ningpai.m.customer.dao.CustomerFollowMapper.deleteByPrimaryKey",
                        map);
    }

    /**
     * 插入数据 要插入的数据的对象
     * @param record
     * @return
     */
    @Override
    public int insert(CustomerFollow record) {
        return 0;
    }

    /**
     * 按条件插入数据
     * @param record
     *            要插入的数据的对象
     * @return
     */
    @Override
    public int insertSelective(CustomerFollow record) {
        return 0;
    }

    /**
     * 按照主键编号查找
     * @param followId
     *            收藏编号
     * @return
     */
    @Override
    public CustomerFollow selectByPrimaryKey(Long followId) {
        return null;
    }

    @Override
    public CustomerFollow queryByCustIdAndProId(Map<String,Object> map) {
        return this.selectOne("com.ningpai.m.customer.dao.CustomerFollowMapper.queryByCustIdAndProId",
                map);
    }

    /**
     * 按条件修改信息
     * @param record
     *            要修改的对象
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(CustomerFollow record) {
        return 0;
    }

    /**
     * 根据主键编号修改信息
     * @param record
     *            要修改的对象
     * @return
     */
    @Override
    public int updateByPrimaryKey(CustomerFollow record) {
        return 0;
    }

    /**
     * 查询按条件查询消费记录
     * @param paramMap
     *            查询条件
     * @return
     */
    @Override
    public List<Object> selectCustFollowByCustId(Map<String, Object> paramMap) {
        return this
                .selectList(
                        "com.ningpai.m.customer.dao.CustomerFollowMapper.selectCustFollowByCustId",
                        paramMap);
    }

    /**
     * 查询收藏记录
     * 根据 会员id以及货品id
     *
     * @param paramMap
     * @author houyichang 2015/11/3
     */
    @Override
    public int selectCUstFollowByCustIdAndProId(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.m.customer.dao.CustomerFollowMapper.selectCustFollowByCustIdAndProId",paramMap);
    }

    /**
     * 商品列表专用 查询当前会员是否
     * @param paramMap
     * @return
     */
    @Override
    public List<String> selectCustFollowByCustIdForList(Map<String, Object> paramMap) {
        return this
                .selectList(
                        "com.ningpai.m.customer.dao.CustomerFollowMapper.selectCustFollowByCustIdForList",
                        paramMap);
    }

    /**
     * 查询收藏总数
     * @param customerId
     *            会员编号
     * @return
     */
    @Override
    public Long selectCustomerFollowCount(Long customerId) {
        return this
                .selectOne(
                        "com.ningpai.m.customer.dao.CustomerFollowMapper.selectCustFollowCount",
                        customerId);
    }

    /**
     * 查询货品评价的数目
     */
    @Override
    public int selectCommByGoodsId(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.m.customer.dao.CustomerFollowMapper.selectAllGoodsCommentCount", paramMap);
    }

    /**
     * 删除购物车里面的商品
     *
     * @param paramMap
     * @return
     */
    @Override
    public int deleteShoppingCart(Map<String, Object> paramMap) {
        return this.update("com.ningpai.m.customer.dao.CustomerFollowMapper.deleteShoppingCart", paramMap);
    }

}
