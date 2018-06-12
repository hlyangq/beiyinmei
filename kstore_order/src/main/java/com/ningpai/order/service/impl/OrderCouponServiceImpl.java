/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.order.service.impl;

import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerConsume;
import com.ningpai.customer.dao.CustomerConsumeMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.goods.service.GoodsProductService;
import com.ningpai.goods.util.CalcStockUtil;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.bean.OrderGoodsInfoCoupon;
import com.ningpai.order.dao.OrderGoodsInfoCouponMapper;
import com.ningpai.order.dao.OrderGoodsMapper;
import com.ningpai.order.dao.OrderMapper;
import com.ningpai.order.service.OrderCouponService;
import com.ningpai.order.service.OrderService;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.PointSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单优惠劵实现
 *
 * @author NINGPAI-LIH
 * @since 2014年7月31日10:15:38
 */
@Service("OrderCouponService")
public class OrderCouponServiceImpl implements OrderCouponService {

    @Resource(name = "OrderService")
    private OrderService orderService;

    @Resource(name = "customerPointServiceMapper")
    private CustomerPointServiceMapper customerPointServiceMapper;
    // 订单商品
    @Resource(name = "OrderGoodsMapper")
    private OrderGoodsMapper orderGoodsMapper;

    // 商品
    @Resource(name = "GoodsProductService")
    private GoodsProductService goodsProductService;

    // 营销
    @Resource(name = "MarketingService")
    private MarketingService marketingService;

    // 优惠劵
    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "CouponNoService")
    private CouponNoService couponNoService;

    // 订单商品dao
    @Resource(name = "OrderGoodsInfoCouponMapper")
    private OrderGoodsInfoCouponMapper orderGoodsInfoCouponMapper;

    @Resource(name = "OrderMapper")
    private OrderMapper orderMapper;
    // 消费记录
    @Resource(name = "customerConsumeMapper")
    private CustomerConsumeMapper customerConsumeMapper;

    //查看积分设置是否开启
    @Resource(name = "pointSetService")
    private PointSetService pointSetService;

    /*
     * 
     * 
     * @see
     * com.ningpai.order.service.OrderCouponService#modifyCouponByOrderId(java
     * .lang.Long)
     */
    @Override
    public int modifyCouponByOrderId(Long orderId, Long customerId) {
        BigDecimal sumP = BigDecimal.valueOf(0);
        Order order = orderMapper.orderDetail(orderId);
        sumP = order.getOrderPrice();
        // 查询订单商品信息
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectOrderGoodsList(orderId);
        if (orderGoodsList != null && !orderGoodsList.isEmpty()) {
            for (int i = 0; i < orderGoodsList.size(); i++) {
                OrderGoods orderGoods = orderGoodsList.get(i);
                // 查询货品详细信息
                orderGoods.setGoodsProductVo(goodsProductService.queryViewVoByProductId(orderGoods.getGoodsInfoId()));
                // 判断货品是否参加了商品促销
                if (orderGoods.getGoodsMarketingId() != null && !"".equals(orderGoods.getGoodsMarketingId().toString())) {
                    // 查询促销信息
                    orderGoods.setMarketing(marketingService.marketingDetail(orderGoods.getGoodsMarketingId()));
                    // 判断是否有赠送优惠券
                    if (orderGoods.getHaveCouponStatus() != null && "1".equals(orderGoods.getHaveCouponStatus())) {
                        // 有送优惠券
                        // 查询赠送的优惠券信息
                        List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = orderGoodsInfoCouponMapper.selectOrderGoodsInfoCoupon(orderGoods.getOrderGoodsId());
                        // 查询优惠券详细
                        if (orderGoodsInfoCouponList != null && !orderGoodsInfoCouponList.isEmpty()) {
                            for (int j = 0; j < orderGoodsInfoCouponList.size(); j++) {
                                orderGoodsInfoCouponList.get(j).setCoupon(couponService.searchCouponById(orderGoodsInfoCouponList.get(j).getCouponId()));
                                // 赠送优惠劵
                                couponService.giveCusCoupon(orderGoodsInfoCouponList.get(j).getCouponNo(), customerId);
                            }
                        }

                    }

                }
            }
        }

        //查看后台积分设置信息
        PointSet pointSet = this.pointSetService.findPointSet();
        if(null != pointSet && "1".equals(pointSet.getIsOpen()) && order.getBusinessId()==0){
            //只有当后台积分设置为开启时才赠送积分
                // 订单完成赠送积分
                customerPointServiceMapper.addIntegralByType(customerId, "6", sumP.doubleValue(),orderId+"");
        }

        // 添加消费记录
            CustomerConsume cc = new CustomerConsume();
            // 从订单中取出付款方式1.在线支付2.货到付款
            if (order.getPayId() == 2) {
                cc.setPayType("2");
            } else {
                cc.setPayType("1");
            }
            cc.setCustomerId(customerId);
            cc.setBalanceNum(sumP);
            cc.setBalanceRemark("消费完成增加");
            cc.setBalanceType("1");
            cc.setCreateTime(new Date());
            cc.setDelFlag("0");
            cc.setOrderNo(order.getOrderCode());
            customerConsumeMapper.insertSelective(cc);
        return 0;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.order.service.OrderCouponService#modifyCouponStatus(java.
     * lang.Long)
     */
    @Override
    @Transactional
    public int modifyCouponStatus(Long orderId) {
        List<CalcStockUtil> calcStockUtils = new ArrayList<CalcStockUtil>();
        // 返还优惠劵
        Order order = orderService.getPayOrder(orderId);
        couponService.returnCouponNo(order.getCouponNo());

        // 查询订单商品信息
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectOrderGoodsList(orderId);
        if (orderGoodsList != null && !orderGoodsList.isEmpty()) {
            for (int i = 0; i < orderGoodsList.size(); i++) {
                OrderGoods orderGoods = orderGoodsList.get(i);
                // 查询货品详细信息
                orderGoods.setGoodsProductVo(goodsProductService.queryViewVoByProductId(orderGoods.getGoodsInfoId()));
                CalcStockUtil calcStockUtil = new CalcStockUtil();
                // 减去库存
                if (orderGoods.getDistinctId() != null) {
                    if (orderGoods.getGoodsProductVo().getIsThird() == null) {
                        orderGoods.getGoodsProductVo().setIsThird("0");
                    }
                    calcStockUtil.setIsThird(orderGoods.getGoodsProductVo().getIsThird());
                    calcStockUtil.setDistinctId(orderGoods.getDistinctId());
                    calcStockUtil.setProductId(orderGoods.getGoodsInfoId());
                    // 需要添加的库存
                    calcStockUtil.setStock(Integer.parseInt(orderGoods.getGoodsInfoNum().toString()));
                    calcStockUtils.add(calcStockUtil);
                }
                // 判断货品是否参加了商品促销
                if (orderGoods.getGoodsMarketingId() != null && !"".equals(orderGoods.getGoodsMarketingId().toString())) {
                    // 查询促销信息
                    orderGoods.setMarketing(marketingService.marketingDetail(orderGoods.getGoodsMarketingId()));
                    /**
                     * 下面这块代码是以前有完成订单赠送优惠券
                     *  判断如果有增送优惠券就把优惠券收回
                     * */
                    // 判断是否有赠送优惠券
                    if (orderGoods.getHaveCouponStatus() != null && "1".equals(orderGoods.getHaveCouponStatus())) {
                        // 有送优惠券
                        // 查询赠送的优惠券信息
                        List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = orderGoodsInfoCouponMapper.selectOrderGoodsInfoCoupon(orderGoods.getOrderGoodsId());
                        // 查询优惠券详细
                        if (orderGoodsInfoCouponList != null && !orderGoodsInfoCouponList.isEmpty()) {
                            for (int j = 0; j < orderGoodsInfoCouponList.size(); j++) {
                                orderGoodsInfoCouponList.get(j).setCoupon(couponService.searchCouponById(orderGoodsInfoCouponList.get(j).getCouponId()));
                                // 取消优惠劵
                                couponService.modifyNoStatus(orderGoodsInfoCouponList.get(j).getCouponNo(), "0");
                            }
                        }

                    }

                }
            }
        }
        goodsProductService.plusStock(calcStockUtils);
        return 0;
    }

    /**
     * 退货流程确认收货使用
     *
     * @param orderId 订单id
     * @return
     */
    @Override
    public int modifyStock(Long orderId) {
        List<CalcStockUtil> calcStockUtils = new ArrayList<CalcStockUtil>();

        // 查询订单商品信息
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectOrderGoodsList(orderId);
        if (orderGoodsList != null && !orderGoodsList.isEmpty()) {
            for (int i = 0; i < orderGoodsList.size(); i++) {
                OrderGoods orderGoods = orderGoodsList.get(i);
                // 查询货品详细信息
                orderGoods.setGoodsProductVo(goodsProductService.queryViewVoByProductId(orderGoods.getGoodsInfoId()));
                CalcStockUtil calcStockUtil = new CalcStockUtil();
                // 减去库存
                if (orderGoods.getDistinctId() != null) {
                    if (orderGoods.getGoodsProductVo().getIsThird() == null) {
                        orderGoods.getGoodsProductVo().setIsThird("0");
                    }
                    calcStockUtil.setIsThird(orderGoods.getGoodsProductVo().getIsThird());
                    calcStockUtil.setDistinctId(orderGoods.getDistinctId());
                    calcStockUtil.setProductId(orderGoods.getGoodsInfoId());
                    // 需要添加的库存
                    calcStockUtil.setStock(Integer.parseInt(orderGoods.getGoodsInfoNum().toString()));
                    calcStockUtils.add(calcStockUtil);
                }
                // 判断货品是否参加了商品促销
                if (orderGoods.getGoodsMarketingId() != null && !"".equals(orderGoods.getGoodsMarketingId().toString())) {
                    // 查询促销信息
                    orderGoods.setMarketing(marketingService.marketingDetail(orderGoods.getGoodsMarketingId()));
                    /**
                     * 下面这块代码是以前有完成订单赠送优惠券
                     *  判断如果有增送优惠券就把优惠券收回
                     * */
                    // 判断是否有赠送优惠券
                    if (orderGoods.getHaveCouponStatus() != null && "1".equals(orderGoods.getHaveCouponStatus())) {
                        // 有送优惠券
                        // 查询赠送的优惠券信息
                        List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = orderGoodsInfoCouponMapper.selectOrderGoodsInfoCoupon(orderGoods.getOrderGoodsId());
                        // 查询优惠券详细
                        if (orderGoodsInfoCouponList != null && !orderGoodsInfoCouponList.isEmpty()) {
                            for (int j = 0; j < orderGoodsInfoCouponList.size(); j++) {
                                orderGoodsInfoCouponList.get(j).setCoupon(couponService.searchCouponById(orderGoodsInfoCouponList.get(j).getCouponId()));
                                // 取消优惠劵
                                couponService.modifyNoStatus(orderGoodsInfoCouponList.get(j).getCouponNo(), "0");
                            }
                        }

                    }

                }
            }
        }
        goodsProductService.plusStock(calcStockUtils);
        return 0;
    }

    /**
     * 根据订单id退货时退库存
     *
     * @param orderId 订单id
     * @return
     */
    @Override
    @Transactional
    public int modifyStocks(Long orderId) {
        List<CalcStockUtil> calcStockUtils = new ArrayList<CalcStockUtil>();
        // 查询订单商品信息
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectOrderGoodsList(orderId);
        if (orderGoodsList != null && !orderGoodsList.isEmpty()) {
            for (int i = 0; i < orderGoodsList.size(); i++) {
                OrderGoods orderGoods = orderGoodsList.get(i);
                // 查询货品详细信息
                orderGoods.setGoodsProductVo(goodsProductService.queryViewVoByProductId(orderGoods.getGoodsInfoId()));
                CalcStockUtil calcStockUtil = new CalcStockUtil();
                // 减去库存
                if (orderGoods.getDistinctId() != null) {
                    if (orderGoods.getGoodsProductVo().getIsThird() == null) {
                        orderGoods.getGoodsProductVo().setIsThird("0");
                    }
                    calcStockUtil.setIsThird(orderGoods.getGoodsProductVo().getIsThird());
                    calcStockUtil.setDistinctId(orderGoods.getDistinctId());
                    calcStockUtil.setProductId(orderGoods.getGoodsInfoId());
                    // 需要添加的库存
                    calcStockUtil.setStock(Integer.parseInt(orderGoods.getGoodsInfoNum().toString()));
                    calcStockUtils.add(calcStockUtil);
                }
            }
        }
        goodsProductService.plusStock(calcStockUtils);
        return 0;
    }

    /**
     * 订单取消时，修改订单使用的优惠券状态（新）
     * 如果订单优惠券是用户已经领取并使用的，返还到账户中，如果直接未领取、是输入券码使用，则恢复该券状态
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public int modifyCouponStatusNew(Long orderId) {
        List<CalcStockUtil> calcStockUtils = new ArrayList<CalcStockUtil>();
        // 返还优惠劵
        Order order = orderService.getPayOrder(orderId);
        CouponNo coupNo = couponNoService.selectCouponNoByCode(order.getCouponNo());
        //
        if(coupNo != null && coupNo.getCodeGetTime() != null){
            //如果订单优惠券是用户已经领取并使用的，返还到账户中
            couponService.modifyNoStatusNew(order.getCouponNo() , "1");
        }else{
            //如果直接未领取、是输入券码使用，则恢复该券状态
            couponService.modifyNoStatusNew(order.getCouponNo() , "0");
        }
        // 查询订单商品信息
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectOrderGoodsList(orderId);
        if (orderGoodsList != null && !orderGoodsList.isEmpty()) {
            for (int i = 0; i < orderGoodsList.size(); i++) {
                OrderGoods orderGoods = orderGoodsList.get(i);
                // 查询货品详细信息
                orderGoods.setGoodsProductVo(goodsProductService.queryViewVoByProductId(orderGoods.getGoodsInfoId()));
                CalcStockUtil calcStockUtil = new CalcStockUtil();
                // 减去库存
                if (orderGoods.getDistinctId() != null) {
                    if (orderGoods.getGoodsProductVo().getIsThird() == null) {
                        orderGoods.getGoodsProductVo().setIsThird("0");
                    }
                    calcStockUtil.setIsThird(orderGoods.getGoodsProductVo().getIsThird());
                    calcStockUtil.setDistinctId(orderGoods.getDistinctId());
                    calcStockUtil.setProductId(orderGoods.getGoodsInfoId());
                    // 需要添加的库存
                    calcStockUtil.setStock(Integer.parseInt(orderGoods.getGoodsInfoNum().toString()));
                    calcStockUtils.add(calcStockUtil);
                }
            }
        }
        goodsProductService.plusStock(calcStockUtils);
        return 0;
    }
}
