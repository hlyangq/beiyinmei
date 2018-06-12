/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.site.customer.mapper;

import java.util.List;
import java.util.Map;

import com.ningpai.site.customer.bean.OrderNotice;

/**
 * 订单通知dao层接口
 *
 * 
 */
public interface OrderNoticeMapper {
    /**
     * 删除
     * 
     * @param noticeId
     * @return 0 失败 1成功
     */
    int deleteByPrimaryKey(OrderNotice record);

    /**
     * 添加
     * 
     * @param record
     * @return 0 失败 1成功
     */
    int insertSelective(OrderNotice record);

    /**
     * 修改
     * 
     * @param record
     * @return 0 失败 1成功
     */
    int updateByPrimaryKeySelective(OrderNotice record);

    /**
     * 查询全部订单通知
     * 
     * @param paramMap
     * @return {@link List<Object>}
     */
    List<Object> queryOrderNotice(Map<String, Object> paramMap);

    /**
     * 查询通知个数
     * 
     * @param customerId
     * @return 通知个数
     */
    Long queryNoticeCount(Map<String, Object> paramMap);

    /**
     * 标记为已读
     */
    int readedNotice(OrderNotice record);
    
    /**
     * 查询未读通知
     * @param customerId
     * @return
     */
    Long selectNotRead(Long customerId);
}
