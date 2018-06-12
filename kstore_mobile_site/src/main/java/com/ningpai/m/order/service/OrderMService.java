/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.m.order.service;

import com.ningpai.m.order.bean.OrderAddress;
import com.ningpai.m.order.util.OrderContainerUtil;
import com.ningpai.order.bean.Order;
import com.ningpai.system.bean.Pay;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 订单支付service
 * 
 * @author NINGPAI-LIH
 * 
 */
public interface OrderMService {

    /**
     * 新流程保存订单
     * 
     * @param duiHuanJiFen
     * @param invoiceType
     * @param invoiceTitle
     * @param request
     * @param shoppingCartId
     * @param typeId
     * @param orderAddress
     * @param deliveryPointId
     * @return
     * @throws UnsupportedEncodingException
     */
    @Transactional
    List<Order> newsubmitOrder(Long duiHuanJiFen, String invoiceType, String invoiceTitle, HttpServletRequest request, Long[] shoppingCartId, Long typeId,
            OrderAddress orderAddress, Long deliveryPointId, String[] presentScopeId) throws UnsupportedEncodingException;

    /**
     * 确认支付
     * 
     * @param orderId
     * @return orderId
     */
    int payOrder(Long orderId);

    /**
     * 查询订单信息
     * 
     * @param orderId
     * @return Order
     */
    Order getPayOrder(Long orderId);

    /**
     * 查询订单根据COde
     * 
     * @param orderCode
     * @return Order
     */
    Order getPayOrderByCode(String orderCode);

    /**
     * 查询订单包裹表
     * 
     * @param orderId
     *            订单id
     * @return 订单所属包裹的运单号
     */
    List<OrderContainerUtil> getExpressNo(Long orderId);

    /**
     * 微信支付工具类
     * 
     * @param request
     * @param response
     * @param order
     * @param pay
     * @param goodsName
     * @return
     */
    Map<String, Object> getWXUrl(HttpServletRequest request, HttpServletResponse response, Order order, Pay pay, String goodsName);

    /**
     * 查询主订单下的所有子订单
     * @param orderCode  主订单编号
     * @return List<Order>
     */
    List<Order> queryOrderByOrderOldCode(String orderCode);

    /**
     * 直营店订单支付成功短信通知
     * @param order
     * @return
     */
    boolean paySuccessSendSms(Order order);

    /**
     * 确认支付
     *
     * @param orderId 订单id
     * @param payId 支付id
     * @return orderId
     */
    int paySuccessUpdateOrder(Long orderId,String status, Long payId);

    /**
     * 确认收货更新状态
     * @param order
     * @param customerId
     * @return
     */
    int comfirmOrder(Order order, Long customerId);
}
