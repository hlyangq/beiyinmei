/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.order.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.order.bean.Order;

/**
 * 订单提交service
 * 
 * @author NINGPAI-LIH
 * @since 2014年9月4日20:33:57
 * 
 */
public interface OrderPayService {

    /**
     * 根据订单id查询订单商品信息
     * 
     * @param orderId
     * @return
     */
    Order queryGoodsProducts(Long orderId);

    /**
     * 推送订单成功消息
     * 
     * @param orderId
     * @return
     */
    void sendOrderRe(Order order, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据订单id查询订单商品信息(不包含更新订单是否查看状态为已查看状态)
     *
     * @param orderId
     * @return
     */
    Order newQueryGoodsProducts(Long orderId);

}
