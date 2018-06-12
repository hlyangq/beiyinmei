package com.ningpai.m.backorder.dao;

import java.util.List;
import java.util.Map;

import com.ningpai.customer.bean.Customer;
import com.ningpai.m.backorder.bean.BackOrderGeneral;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderGoods;

/***
 * 退货记录 
 * @author qiyuanyuan
 *
 */
public interface ReturnGoodsMapper {
    
    /***
     * 保存退单物流单号
     * @param general
     * @return
     */
    int saveGeneral(BackOrderGeneral general);
    /***
     * 根据订单号查询退单对象
     * @param orderNo
     * @return
     */
    List<BackOrder> selectBackOrderId(String orderNo);
    
    /***
     * 更新订单商品信息对象
     * @param orderGoods
     * @return
     */
    int updateOrderGoods(OrderGoods orderGoods);
    
    /***
     * 根据订单ID获取订单商品信息对象
     * @param orderGoodsId
     * @return
     */
    List<OrderGoods> selectOrderGoodsById(Long orderGoodsId);
    /**
     * 根据id获取单个的交易记录对象
     * @param orderId
     * @return
     */
    Order selectOrderById(Long orderId);
    
    /**
     * 新增一条退货记录
     * @param returnGoods
     * @return
     */
    int saveBackOrder(BackOrder backOrder);
    
    /**
     * 根据ID查询会员用户名
     * @param userId
     * @return
     */
    Customer selectCustomerById(Long userId);
    
    /***
     * 更改交易记录(Order)的状态
     * @param order
     * @return
     */
    int updateOrder(Order order);
    /**
     * 查询该会员下面的 所有退单信息
     *
     * @param paramMap
     *            查询订单条件
     * @return
     */
    List<Object> queryAllMyBackOrders(Map<String, Object> paramMap);

    /**
     * 获取当前会员 退单的数据条数 zhanghl
     * @param map
     * @return
     */
    Long searchTotalCountBack(Map<String,Object> map);
    
}
