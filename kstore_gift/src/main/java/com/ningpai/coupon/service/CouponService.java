/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.coupon.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.bean.ParamIds;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.system.bean.PointSet;
import com.ningpai.util.PageBean;

/**
 * @author ggn 优惠券接口 2014-03-19
 */
public interface CouponService {

    /**
     * 获取boss优惠券信息
     *
     * @return
     */
    List<Object> queryCouponListToBoss();
    /**
     *查询所有优惠卷信息
     * @return
     */
    List<Coupon> selectCouponList_site();
    /**
     * 根据店铺ID查询优惠券
     * 
     * @return List对象
     */
    List<Coupon> selectCouponListByStoreId(Long storeId);

    /***
     * 修改指定会员总积分
     * 
     * @param point
     * @return
     */
    int updateCustomerPoint(CustomerPoint point);

    /**
     * 获取积分消费规则
     * 
     * @return
     */
    PointSet selectPointSet();

    /***
     * 获取当前会员的所有积分
     * 
     * @param customerId
     * @return
     */
    CustomerPoint selectCustomerPointByCustomerId(Long customerId);

    /**
     * 添加优惠券接口
     * 
     * @param coupon
     *            {@link com.ningpai.coupon.bean.Coupon}
     * 
     * @return int
     */
    int doAddCoupon(Coupon coupon, HttpServletRequest request, Long[] lelvelId);

    /**
     * 添加优惠劵接口（按品牌和分类或者单个商品进行插入）
     * 
     * @param coupon
     * @param request
     * @param lelvelId
     * @param status
     *            0 货品 1品牌 2分类
     * @return
     */
    int doAddCoupon(Coupon coupon, HttpServletRequest request, Long[] lelvelId,
            String status);

    /**
     * 分页查询所有优惠券列表
     * 
     * @param coupon
     *            {@link com.ningpai.coupon.bean.Coupon}
     * @param pageBean
     * @return List
     */
    PageBean searchCouponList(Coupon coupon, PageBean pageBean,
            String startTime, String endTime);

    /**
     * 删除优惠券
     * 
     * @param couponId
     *            {@link java.lang.Long}
     * @return int
     */
    int delCoupon(Long couponId);

    /**
     * 批量删除优惠券
     * 
     * @param couponId
     *            {@link java.lang.Long}
     * @return int
     */
    int delAllCoupon(Long[] couponId);

    /**
     * 查询优惠券详细信息
     * 
     * @param couponId
     *            {@link java.lang.Long}
     * @return Coupon
     */
    Coupon searchCouponById(Long couponId);

    /**
     * 修改优惠券
     * 
     * @param coupon
     *            {@link com.ningpai.coupon.bean.Coupon}
     * @param cateId
     *            {@link java.lang.Long}
     * @param brandId
     *            {@link java.lang.Long}
     * @param skuId
     *            {@link java.lang.Long}
     * @param status
     *            {@link java.lang.String}
     * @return int
     */
    int doUpdateCouponById(Coupon coupon, HttpServletRequest request,
            Long[] lelvelId, String status);

    /**
     * 查询范围
     * 
     * @param couponId
     *            {@link java.lang.Long}
     * @param type
     *            {@link java.lang.String}
     * @return Object
     */
    Object selectCouponRange(Long couponId, String type);

    /**
     * 我的优惠券
     * 
     * @param customerId
     *            {@link java.lang.Long}
     * @param codeStatus
     *            {@link java.lang.String} 1未使用 2已使用 3已经过期
     * @return PageBean
     */
    PageBean myCouponList(PageBean pageBean, Long customerId, String codeStatus);

    /**
     * 查询我可以使用 未过期的所有优惠券总数
     * 
     * @param customerId
     *            {@link java.lang.Long}
     * @return int
     */
    int myCouponNoCount(Long customerId);

    /**
     * * 查询货品可以使用的优惠券
     * 
     * @param list
     * @return List
     */
    List<Coupon> selectCouponListByIds(List<ParamIds> list,
            HttpServletRequest request);

    /**
     * * 查询货品可以使用的优惠券
     *
     * @param list
     * @return List
     */
    List<Coupon> selectThirdCouponListByIds(List<ParamIds> list,
                                       HttpServletRequest request, Long third);

    /**
     * * 查询货品可以使用的优惠券(输入使用优惠券时使用)
     *
     * @param list
     * @return List
     */
    List<Coupon> selectCouponListByIdsNew(List<ParamIds> list,Long thirdId,HttpServletRequest request);

    /**
     * 根据卷码查询优惠券
     * 
     * @param codeNo
     * @return Coupon
     */
    Coupon selectCouponByCodeNo(String codeNo);

    /**
     * 返回一个卷卷码 并且修改此优惠券为已经领取
     * 
     * @param couponNo
     * @return
     */
    Coupon selectOneCouponNoByCouponIdAndUpdateNoIsGet(Long couponNo,
            Long customerId);

    /**
     * 赠送优惠劵
     * 
     * @param couponNo
     *            劵码
     * @param customerId
     *            用户id
     * @return int
     */
    int giveCusCoupon(String couponNo, Long customerId);

    /**
     * 修改优惠劵状态
     * 
     * @param couponNo
     *            劵码
     * @param codeStatus
     *            要修改的优惠劵状态
     * @return
     */
    int modifyNoStatus(String couponNo, String codeStatus);

    /**
     * 新取消订单返回优惠券
     * 如果订单优惠券是用户已经领取并使用的，返还到账户中，如果直接未领取、是输入券码使用，则恢复该券状态
     * @param couponNo
     * @param codeStatus
     * @return
     */
    int modifyNoStatusNew(String couponNo, String codeStatus);

    /**
     * tianjia
     * 
     * @param couponId
     */
    void addCouponC(Long couponId);

    /**
     * 根据codeId获取优惠券时间
     * 
     * @param codeId
     * @return
     */
    Date selectCouponTimeByCodeId(Long codeId);

    /**
     * 查询优惠劵总数量
     * 
     * @return
     */
    int selectCouponCount();

    /**
     * 取消订单取消优惠劵
     * 
     * @param couponNo
     * @return
     */
    int returnCouponNo(String couponNo);

    /**
     * 获取优惠券信息
     * 
     * @return
     */
    List<Object> queryCouponList();

    /**
     * 查询可使用的优惠券
     * 
     * @return List对象
     */
    List<Coupon> selectCouponListByAble();

    /**
     * 删除优惠券
     * 
     * @param couponId
     *            {@link java.lang.Long}
     * @return int
     */
    int newdelCoupon(Long couponId, Long thirdId);

    /**
     * 批量删除优惠券
     * 
     * @param couponId
     *            {@link java.lang.Long}
     * @return int
     */
    int delAllCoupon(Long[] couponId, Long thirdId);
    
    /**
     * 查询优惠券数量
     * @param customerId 用户id
     * @param status 优惠券状态
     * @return
     */
    Long countByCodeStatus(Long customerId, String status);

    /**
     * 添加积分使用记录
     * @param customerId 用户编号
     * @param point 使用积分
     * @param str  使用原因
     */
    void addIntegralUseRecord(Long customerId, Integer point, String str);

    /**
     * 查询用户的所有未过期且未使用的优惠券
     * @param customerId 用户编号
     * @return List<Coupon> 优惠券列表
     */
    List<Coupon> selectCouponListByCustomerId(Long customerId);

    /**
     *     /**
     * 新手机端我的优惠券
     * @param customerId
     *            {@link java.lang.Long}
     * @param codeStatus
     *            {@link java.lang.String} 1未使用 2已使用 3已经过期
     * @return
     */
    public List<Object> myCouponListM(Long customerId, String codeStatus);

    /**
     * * 查询货品可以使用的优惠券
     *
     * @param list
     * @param customerId
     *          用户ID
     * @return List
     */
    List<Coupon> selectCouponListByIdsAndCusId(List<ParamIds> list,
                                       Long customerId);
}
