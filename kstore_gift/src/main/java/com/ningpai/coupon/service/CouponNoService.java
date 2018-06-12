/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.coupon.service;

import java.util.List;

import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.util.PageBean;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ggn 优惠券券码接口 2014-03-19
 */
public interface CouponNoService {

    /**
     * 查询优惠券卷码
     * 
     * @param couponId
     *            {@link java.lang.Long}
     * @return List
     */
    List<CouponNo> selectNoByCouponId(Long couponId);

    /**
     * 修改优惠券已经使用
     * 
     * @param codeNo
     * @param orderCode
     * @return int
     */
    int updateCodeIsUse(String codeNo, String orderCode);

    /**
     * 返回劵码
     * 
     * @param couponNo
     * @return
     */
    CouponNo selectNoByCouponIdByStatus(Long couponNo);

    /**
     * 根据优惠劵id分页查询劵码
     * 
     * @param pb
     *            分页实体
     * @param couponNo
     *            劵码
     * @return
     */
    PageBean selectList(PageBean pb, Long couponId, CouponNo couponNo);

    /**
     * 修改领取状态
     * 
     * @param codeId
     * @return
     */
    int changeCouponGetAndStatus(Long codeId);

    /**
     * 导出优惠券券码
     * @param response
     * @param couponId
     */
    void exportCouponCodeNo(HttpServletResponse response,Long couponId);

    /**
     * 获取领取数量
     * 
     * @param couponId
     * @return
     */
    int getCouponGetNoByCouponId(Long couponId);

    /**
     * 领取优惠券
     * 
     * @param codeId
     * @param cId
     * @return
     */
    int updateCouponCustomer(Long codeId, Long cId);

    /**
     * 判断用户领取个数
     * 
     * @param couponId
     * @param cId
     * @return
     */
    int selectReadyGet(Long couponId, Long cId);

    /**
     * 根据优惠券ID查询该优惠券被领取的张数
     * 
     * @param couponId
     * @return
     */
    Long queryUsedCountByCouponId(Long couponId);

    /**
     * 查询优惠券总数
     * 
     * @param couponId
     * @return
     */
    int selectCountAllByCouponId(Long couponId);

    /**
     * 根据优惠券id查询可用的优惠券
     * 
     * @param couponId
     * @return
     */
    int selectCouponNoByStatus(Long couponId);

    /**
     * 根据状态查询优惠券列表
     * 
     * @param couponId
     * @param couponNo
     * @return
     */
    List<Object> selectCouponList(Long couponId, CouponNo couponNo);
    
    /**
     * 根据券码查询优惠券
     * 
     * @param couponId
     * @param couponNo
     * @return
     */
    int selectCouponByCode(String couponNo, Long cId);

    /**
     * 根据券码查询优惠券CouponNo
     * @param code
     * @return
     */
    CouponNo selectCouponNoByCode(String code);
}
