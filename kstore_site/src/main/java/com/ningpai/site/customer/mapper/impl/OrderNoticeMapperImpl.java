/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.site.customer.mapper.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ningpai.site.customer.bean.OrderNotice;
import com.ningpai.site.customer.mapper.OrderNoticeMapper;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * 订单通知dao层实现类
 * @see com.ningpai.m.customer.dao.OrderNoticeMapper
 * @author QIANMI-ZHANGWENCHANG
 * @since 2015年10月20日
 */
@Repository("orderNoticeMapperSite")
public class OrderNoticeMapperImpl extends BasicSqlSupport implements
        OrderNoticeMapper {
    /*
     * 删除
     * (non-Javadoc)
     * @see com.ningpai.m.customer.mapper.OrderNoticeMapper#deleteByPrimaryKey(com.ningpai.m.customer.bean.OrderNotice)
     */
    @Override
    public int deleteByPrimaryKey(OrderNotice record) {
        return this.update("com.ningpai.site.customer.dao.OrderNoticeMapper.deleteByPrimaryKeySelective", record);
    }
    
    /*
     * 添加
     * (non-Javadoc)
     * @see com.ningpai.m.customer.mapper.OrderNoticeMapper#insertSelective(com.ningpai.m.customer.bean.OrderNotice)
     */
    @Override
    public int insertSelective(OrderNotice record) {
        return this.insert("com.ningpai.site.customer.dao.OrderNoticeMapper.insertSelective", record);
    }
    
    /*
     * 修改
     * (non-Javadoc)
     * @see com.ningpai.m.customer.mapper.OrderNoticeMapper#updateByPrimaryKeySelective(com.ningpai.m.customer.bean.OrderNotice)
     */
    @Override
    public int updateByPrimaryKeySelective(OrderNotice record) {
        return this.update("com.ningpai.site.customer.dao.OrderNoticeMapper.updateByPrimaryKeySelective", record);
    }

    /*
     * 查询全部订单通知
     * (non-Javadoc)
     * @see com.ningpai.m.customer.mapper.OrderNoticeMapper#queryOrderNotice(java.util.Map)
     */
    @Override
    public List<Object> queryOrderNotice(Map<String, Object> paramMap) {
        return this.selectList("com.ningpai.site.customer.dao.OrderNoticeMapper.selectList", paramMap);
    }

    /*
     * 查询通知个数
     * (non-Javadoc)
     * @see com.ningpai.m.customer.mapper.OrderNoticeMapper#queryNoticeCount(java.lang.Long)
     */
    @Override
    public Long queryNoticeCount(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.site.customer.dao.OrderNoticeMapper.selectNoticeCount", paramMap);
    }

    /*
     * 标记为已读
     * (non-Javadoc)
     * @see com.ningpai.m.customer.mapper.OrderNoticeMapper#readedNotice(com.ningpai.m.customer.bean.OrderNotice)
     */
    @Override
    public int readedNotice(OrderNotice record) {
        return this.update("com.ningpai.site.customer.dao.OrderNoticeMapper.updateByPrimaryKeySelective", record);
    }

    /*
     * 查询未读通知
     * (non-Javadoc)
     * @see com.ningpai.m.customer.mapper.OrderNoticeMapper#selectNotRead(java.lang.Long)
     */
    @Override
    public Long selectNotRead(Long customerId) {
        return this.selectOne("com.ningpai.site.customer.dao.OrderNoticeMapper.findNoticeCount", customerId);
    }

}
