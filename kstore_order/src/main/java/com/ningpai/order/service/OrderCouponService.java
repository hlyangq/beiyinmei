/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.order.service;

/**
 * 订单优惠劵
 * 
 * @author NINGPAI-LIH
 * @since 2014年7月31日10:15:38
 * 
 */

public interface OrderCouponService {

    /**
     * 根据订单id赠送优惠劵信息
     * 
     * @param orderId
     *            订单id
     * @param customerId
     *            用户id
     * @return
     */
    int modifyCouponByOrderId(Long orderId, Long customerId);

    /**
     * 根据订单id查询到优惠劵劵码，修改优惠劵
     * 
     * @param orderId
     *              订单id
     * @return
     */
    int modifyCouponStatus(Long orderId);

    /**
     * 退货流程确认收货使用
     *
     * @param orderId
     *              订单id
     * @return
     */
    int modifyStock(Long orderId);

    /**
     * 根据订单id退货时退库存
     *
     * @param orderId
     *              订单id
     * @return
     */
    int modifyStocks(Long orderId);

    /**
     * 订单取消时，修改订单使用的优惠券状态（新）
     * @param orderId
     * @return
     */
    int modifyCouponStatusNew(Long orderId);
}
