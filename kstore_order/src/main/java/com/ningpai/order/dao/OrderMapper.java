/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.order.dao;

import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderExpress;
import com.ningpai.order.bean.OrderGoods;

import java.util.List;
import java.util.Map;

/**
 * @author NINGPAI-LIH 订单接口
 */
public interface OrderMapper {

    /**
     * 删除单个退单信息
     * 
     * @param backOrderId
     *            退单Id
     * @param customerId
     *            当前会员ID
     * @return
     */
    int deleteBackOrderById(Long backOrderId, Long customerId);

    /**
     * 根据时间查询订单信息
     * 
     * @param map
     * @return
     */
    List<Order> selectOrderListByTime(Map<String, Object> map);

    /**
     * 第三方根据时间查询订单信息
     *
     * @param map
     * @return
     */
    List<Object> selectThirdOrderListByTime(Map<String, Object> map);

    /**
     * 第三方根据时间查询订单信息
     *
     * @param map
     * @return
     */
    List<Object> selectThirdOrderListTime(Map<String, Object> map);

    /**
     * 查询订单列表
     *
     * @param paramMap
     *            {@link java.util.Map}
     * @return int
     */
    int selectThirdOrderListByTimeCount(Map<String, Object> paramMap);

    /**
     * 根据本日订单总数
     * 
     * @param
     * @return
     */
    int selectOrderCountByCurdate();

    /**
     * 查询订单列表
     * 
     * @param paramMap
     *            {@link java.util.Map}
     * @return int
     */
    int searchOrderCount(Map<String, Object> paramMap);

    /**
     * 查询订单总数
     * 
     * @param paramMap
     *            {@link java.util.Map}
     * @return List
     */
    List<Object> searchOrderList(Map<String, Object> paramMap);

    /**
     * 查询订单主表详细内容
     * 
     * @param orderId
     *            {@link Long}
     * @return Order
     */
    Order orderDetail(Long orderId);

    /**
     * 修改修改订单发货
     * 
     * @param orderId
     *            {@link Long}
     * @return int
     */
    int sendOrder(Long orderId);

    /**
     * 插入订单
     * 
     * @param order
     * @return int
     */
    int insertOrder(Order order);

    /**
     * 查询刚插入ID
     * 
     * @return Long
     */
    Long selectLastId();

    /**
     * 确认支付
     * 
     * @param orderId
     * @return int
     */
    int payOrder(Long orderId);

    /**
     * 余额支付后改状态与payId
     * @param paramMap
     * @return
     */
    int updateOrderStatusAndPayId(Map<String,Object> paramMap);
    /**
     * 查询订单
     * 
     * @param orderCode
     * @return Order
     */
    Order getPayOrderByCode(String orderCode);

    /**
     * 修改订单状态根据主键
     * 
     * @param order
     * @return
     */
    int updateByPrimaryKeySelective(Order order);

    /**
     * 更改订单状态
     * 
     * @param map
     *            参数
     * @param map
     *            .orderStatus 要修改的订单状态
     * @param map
     *            .list 要修改的订单id
     */
    void updateOrderStatusByOrderIds(Map<String, Object> map);

    /**
     * 根据orderId数组查询订单数据
     * 
     * @param orderIds
     * @return 订单数据
     */
    List<Order> selectOrderList(List<Long> orderIds);

    /**
     * 前台　查询所有已发货的订单
     * 
     * @return
     */
    List<Order> receiptConfirmation();

    /**
     * 查询前十条近一个月新加订单
     * 
     * @return 前十条近一个月内新加订单
     */
    List<Order> selectNewOrderByParam();

    /**
     * 近一个月新加订单总数
     * 
     * @return 近一个月新加订单总数
     */
    Long selectNewOrderCountByParam();

    /**
     * 更新最近一个月新订单为已查看
     */
    void updateOrdreNewStauts(Long orderId);

    /**
     * 根据订单状态id查询订单
     * 
     * @return
     */
    List<Object> searchOrderListByOrderIds(Map<String, Object> map);

    /**
     * 根据订单状态id查询订单总行数
     * 
     * @param map
     * @return
     */
    int searchOrderCountByOrderIds(Map<String, Object> map);

    /**
     * 根据订单id集合查询订单
     * 
     * @param map
     *            订单id集合
     * @return
     */
    List<Object> searchOrderListByOrderIdList(Map<String, Object> map);

    /**
     * 根据订单id集合查询订单总行数
     * 
     * @param map
     *            订单id集合
     * @return
     */
    int searchOrderCountByOrderIdsList(Map<String, Object> map);

    /**
     * 修改订单金额
     * 
     * @param map
     *            price订单要修改金额 orderId要修改的订单id
     */
    void modifyOrderPrice(Map<String, Object> map);

    /**
     * 修改订单的出库状态
     * 
     * @param order
     *            订单状态
     * @return
     */
    int updateSetCargoStatusByOrderId(Order order);

    /**
     * 修改第三方订单的出库状态
     *
     * @param order
     *            订单状态
     * @return
     */
    int updateSetCargoStatusByThirdOrderId(Order order);

    /**
     * 批量修改订单出库状态
     * 
     * @param map
     * @return
     */
    int updateSetCargoStatusByOrderIds(Map<String, Object> map);

    /**
     * 批量修改第三方订单出库状态
     *
     * @param map
     * @return
     */
    int updateSetCargoStatusByThirdOrderIds(Map<String, Object> map);

    /**
     * 根据参数查询订单列表
     * 
     * @param paramMap
     *            查询所用的参数
     * @return
     */
    List<Order> selectByParam(Map<String, Object> paramMap);

    /**
     * 根据时间分组查询每天销售量
     * 
     * @param map
     * @return
     */
    List<Order> querySaleCountByDay(Map<String, Object> map);

    /**
     * 根据时间分组查询每天的销售额
     * 
     * @param map
     * @return
     */
    List<Order> querySaleMoneyByDay(Map<String, Object> map);

    /**
     * 查询未付款订单根据后台设定时间
     *
     * @param map
     * @return
     */
    List<Order> queryOrderCountByDay(Map<String, Object> map);
    /**
     * 删除订单
     * 
     * @param orderId
     * @return
     */
    int deleteOrderById(Long orderId);

    /**
     * 查询第三方订单的数量
     * 
     * @param
     * @return
     */
    int searchThirdOrderCount(Map<String, Object> paramMap);

    /**
     * 查询第三方订单
     * 
     * @param paramMap
     * @return
     */
    List<Object> searchThirdOrderList(Map<String, Object> paramMap);

    /**
     * 自动更新未付款订单七天后作废
     */
    void updateStatusByCreateTime(Long time);

    /**
     * 新自动更新未付款订单根据后台设定时间
     * @param map
     */
    void updateStatusByCancleOrder(Map<String, Object> map);

    /**
     * 根据总订单号查询订单信息
     * 
     * @param orderOldCode
     * @return
     */
    List<Order> getPayOrderByOldCode(String orderOldCode);

    /**
     * 根据订单id更改订单状态
     * 
     * @param param
     * @return
     */
    int updateStatusBackById(Map<String, Object> param);

    /**
     * 根据订单编号，商家编号 修改订单状态
     * 
     * @param order
     * @return
     */
    int updateOrderStatusByorderId(Order order);

    /**
     * 定时任务更改状态
     * @param order
     * @return
     */
    int updateOrderStatusByorderIdFortask(Order order);

    /**
     * 查询所有订单信息 此方法供后台订单列表中的导出订单列表功能使用
     *
     * @author houyichang 2015/8/14
     * @return
     * */
    List<Order> queryAllOrderList();
    /**
     * 查询直营店订单的物流信息
     * @param orderId 订单编号
     * @return
     */
    OrderExpress queryOrderExpress(Long orderId);

    /**
     * 自动确认收货　查询所有在线支付的已发货订单
     *
     * @return
     */
    List<Order> autoAeceiptConfirmation();

    /**
     * 查询所有订单信息 此方法供后台订单列表中的导出订单列表功能使用
     *
     * @author chenp 2016/03/05
     * @return
     * */
    List<Order> queryBossOrderList(Order order);

    /**
     * 查询选中的boss订单信息 此方法供后台订单列表中的导出选中订单功能使用
     *
     * @return 订单集合
     * @param paramMap 订单id数组
     */
    List<Order> queryCheckedBossOrderList(Map<String,Object> paramMap);

    /**
     * 查询选中的店铺订单信息 此方法供后台订单列表中的导出选中订单功能使用
     *
     * @return 订单集合
     * @param paramMap 订单id数组
     */
    List<Order> queryCheckedBusinessIdOrderList(Map<String,Object> paramMap);

    /**
     * 查询所有订单信息 此方法供后台订单列表中的导出订单列表功能使用
     *
     * @author chenp 2016/03/05
     * @return
     * */
    List<Order> queryBusinessIdOrderList(Order order);

    /**
     * 查询所有订单信息 此方法供后台订单列表中的导出订单列表功能使用
     *
     * @author chenp 2016/03/05
     * @return
     * */
    List<Order> queryThirdOrderList();

    /**
     * 查询订单（单表查询）
     * @param orderId 订单id
     * @return 返回订单
     */
    Order querySimpleOrder(Long orderId);

    /**
     * 取消订单
     *
     * @param paramMap
     *            查询条件
     * @return 0失败 1 成功
     */
    int cancelOrder(Map<String, Object> paramMap);

    /**
     * 发货时更新订单状态和发货时间
     *
     * @param order 订单对象
     * @return 0失败 1 成功
     */
    int updateOrderStatus(Order order);

    /**
     * 根据订单id查询仓库id
     * @param orderId
     * @return int
     */
    Long queryRepositoryId(Long orderId);

    /**
     * 根据订单id和会员id查询订单详细
     * @param map(orderId:订单id customerId:会员id)
     * @return 订单详细
     */
    Order orderDetailByMap(Map<String, Object> map);

    /**
     * 根据 会员id,订单id 查询订单信息
     * @param customerId 会员id
     * @param orderId 订单id
     * @return 订单信息
     */
    Order queryOrderByCustomerIdAndOrderId(Long customerId, Long orderId);

    /**
     * 根据订单id, 订单商品id 查询订单商品信息
     *
     * @param orderId      订单id
     * @param orderGoodsId 订单商品id
     * @return
     */
    public OrderGoods queryOrderGoodsByOrderIdAndOrderGoodsId(Long orderId, Long orderGoodsId);

    /**
     * 确认收货更改状态
     * @param order
     * @return
     */
    int comfirmOrder(Order order);
}
