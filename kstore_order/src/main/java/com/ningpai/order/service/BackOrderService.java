/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.order.service;

import com.ningpai.order.bean.BackGoods;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.BackOrderGeneral;
import com.ningpai.util.PageBean;

/**
 * @author ggn 退单信息
 */
public interface BackOrderService {

    /**
     * 退单信息列表
     * 
     * @param pb
     * @param bkOrder
     * @return PageBean
     */
    PageBean backOrderList(PageBean pb, BackOrder bkOrder, String startTime,
            String endTime);

    /**
     * 查询BackOrderdetail
     * 
     * @param backOrderId
     * @return BackOrder
     */
    BackOrder detail(Long backOrderId);

    /**
     * 查询退单信息根据退单编号（包括订单信息）
     *
     * @param backOrderId
     * @return BackOrder
     */
    BackOrder selectBackOrderByBackOrderId(Long backOrderId);
    /**
     * 退单详细信息
     * 
     * @param backOrderId
     * @return
     */
    BackOrder backdetail(Long backOrderId, Long orderId);

    /**
     * 当用户退单,后台同意后将把订单完成的积分奖励扣除掉 会员积分列表将增加一条扣除积分信息 会员消费列表将增加一条订单退掉的信息.
     * 
     * @param orderId
     * @return
     */
    int reducePointOrderBack(Long orderId);


    /**
     * 售中退款返还积分
     *
     * @param orderId
     * @return
     */
    int reducePointOrderBackNew(Long orderId);

    /**
     * 修改退单审核状态
     * 
     * @param backId
     * @param backCheck
     * @return
     */
    int modifyBackOrderByCheck(Long backId, String backCheck);

    /**
     * 修改退单信息
     *
     * @param backOrder
     * @return
     */
    int modifyBackBeanCheck(BackOrder backOrder);

    /**
     * 修改第三方后台退单信息
     *
     * @param backOrder
     * @return
     */
    int modifyThirdBackBeanCheck(BackOrder backOrder);

    /**
     * 查询第三方退单数量
     * 
     * @param thirdId
     *            第三方ID {@link Long}
     * @return 查询到的数量 {@link Integer}
     */
    int queryBackOrderCountBuy(Long thirdId);

    /**
     * 查询第三方退单数量(已买)
     * 
     * @param customerId
     *            customerID{@link Long}
     * @return 查询到的数量 {@link Integer}
     */
    int queryBackOrderCount(Long customerId);

    /**
     * 插入退单信息
     * 
     * @param backOrder
     * @return
     */
    int insertBackOrderInfo(BackOrder backOrder);

    /**
     * 根据订单编号查找退单信息
     *
     * @param orderNo
     * @return
     */
    BackOrder queryBackOrderByOrderCode(String orderNo);

    /**
     * 根据订单编号和退货/退款标志查找退单信息
     *
     * @param orderNo
     * @param isBack
     * @return
     */
    BackOrder queryBackOrderByOrderCodeAndIsback(String orderNo, String isBack);

    /**
     * 根据退单号获取物流号
     *
     * @param backOrderId
     * @return
     */
    BackOrderGeneral queryBackOrderGeneral(Long backOrderId);

    /**
     * 退款成功后退回库存
     *
     * @param orderId
     * @param backOrderId
     * @return
     */
    Integer addStockOrderBack(Long orderId, Long backOrderId);

    /**
     * 判断该订单是否已经存在退单记录 如果存在 则返回true  否则返回flase
     *
     * @param orderCode 订单编号
     * @return
     */
    boolean isBackOrderRecordExist(String orderCode);

    /**
     * 判断该订单是否已经存在退单记录 如果存在 则返回true  否则返回flase
     *
     * @param orderCode 订单编号
     * @return
     */
    BackOrder isBackOrder(String orderCode);

    /**
     * 根据退单状态和主键ID修改退单审核状态
     *
     * @param backId
     *          主键ID
     * @param backCheck
     *          退单状态
     * @return
     */
    int modifyBackOrderByCheckNew(Long backId, String backCheck);

    /**
     * 判断积分是否够扣除
     * @param orderId 订单id
     * @return 如果够扣除返回0  不够返回1
     */
    int isPointEnough(String orderId);

    /**
     * 通过货品订单ID设置bean
     * @param goodsinfoId
     * @param OrderId
     * @return
     */
    BackGoods selectOrdersetBackGodds(Long goodsinfoId, Long OrderId);

    /**
     * 后台修改退单金额，更新退单表
     * @param backOrder 退单实体
     * @return 修改结果
     */
    int updateBackOrderReducePrice(BackOrder backOrder);
}
