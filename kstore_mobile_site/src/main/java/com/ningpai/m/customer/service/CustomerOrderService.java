/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ningpai.comment.bean.Comment;
import com.ningpai.m.customer.vo.GoodsBean;
import com.ningpai.util.PageBean;

/**
 * 手机端会员订单Service
 * 
 * @author jiping
 * @since 2014年8月20日 下午1:50:35
 * @version 0.0.1
 */
public interface CustomerOrderService {
    /**
     * 查询所有订单
     * 
     * @param paramMap
     *            查询订单条件
     * @return PageBean
     */
    PageBean queryAllMyOrders(Map<String, Object> paramMap, PageBean pb);

    /**
     * 根据订单编号查找订单信息
     * 
     * @param orderId
     *            订单编号
     * @return OrderInfoBean
     */
    Object queryOrderByCustIdAndOrderId(Long orderId, Long customerId);

    /**
     * 查询通知内容数量 如 :待处理订单数量...
     * 
     * @param customerId
     *            会员编号
     * @return Map<String,Object> {@link java.util.Map}
     */
    Map<String, Object> selectNotice(Long customerId);

    /**
     * 取消订单
     * 
     * @param orderId
     *            订单Id
     * @return 0失败 1成功
     */
    int cancelOrder(Long orderId, String reason);

    /**
     * 确认收货
     * 
     * @param orderId
     *            订单编号
     * @return
     */
    int comfirmofGoods(Long orderId);

    /**
     * 添加商品评论
     * 
     * @param comment
     *            评论信息 {@link Comment}
     * @return 评论编号
     */
    int addGoodsComment(Comment comment);

    /**
     * 添加商品评论
     * 
     * @param request
     * @param comment
     *            评论实体 {@link Comment}
     * @return 0失败 1成功
     */
    int addGoodsComment(HttpServletRequest request, Comment comment, Long orderGoodsId);
    
    /**
     * 根据订单id和商品id查询待评价商品
     * @param customerId 
     *          用户id
     * @param goodsId
     *          货品id
     * @param orderId
     *          订单id
     * @param commentId
     *          评价id
     * @return 对象
     */
    GoodsBean selectByOrderIdAndGoodsId(Long customerId,Long goodsId,Long orderId,Long commentId);
    
    /**
     * 根据orderId查询货品
     * 
     * @param orderId
     *           订单id
     * @return List
     */
    List<GoodsBean> selectByOrderId(Long orderId);
    
    /**
     * 根据订单类型查询订单数量
     * @param orderType 
     *          订单类型
     * @return Long
     */
    Long searchOrderCountByCount(String orderType);

    /**
     * 查询所有待付款订单的数量
     *
     * @author houyichang 2015/12/26
     * */
    int queryNoMoneyOrderCount(String paramString,String type,Long customerId);

    /**
     * 查询所有待收货订单的总量
     *
     * @author houyichang 2015/12/26
     * */
    int queryNoShowHuoOrderCount(String paramString,String type,Long customerId);

    /**
     * 查询所有待评价订单的总量
     *
     * @author houyichang 2015/12/26
     * */
    int queryNoCommentOrderCount(String paramString,String type,Long customerId);

    /**
     * 查询所有退款退货订单的总量
     *
     * @author houyichang 2015/12/26
     * */
    int queryCancleOrderCount(String paramString,String type,Long customerId);
}
