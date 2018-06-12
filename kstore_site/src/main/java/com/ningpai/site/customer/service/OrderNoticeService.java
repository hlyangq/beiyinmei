/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.site.customer.service;

import com.ningpai.site.customer.bean.OrderNotice;
import com.ningpai.util.PageBean;


/**
 * 手机端订单消息通知Service
 * 
 * @author QIANMI-ZHANGWENCHANG
 * @since 2015年10月20日
 */
public interface OrderNoticeService {

    /**
     * 查询订单通知消息
     * 
     * @param customerId
     *            会员编号
     * @param pb
     *         分页
     * @return PageBean
     */
    PageBean selectOrderNotice(Long customerId,PageBean pb);
    
    /**
     * 查询未读订单通知数量
     */
    Long getIsNotReadCount(Long customerId);
    
    /**
     * 标记为已读
     * @param noticeId
     * @return
     */
    Long readNotice(Long noticeId);
    
    /**
     * 添加通知消息
     * @param record
     * @return
     */
    int addNotice(OrderNotice record);
}
