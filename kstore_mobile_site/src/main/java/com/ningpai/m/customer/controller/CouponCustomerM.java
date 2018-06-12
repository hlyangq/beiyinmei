/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.controller;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.util.LoginUtil;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员优惠券Controller
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年8月21日 上午11:30:40
 * @version 0.0.1
 */
@Controller
public class CouponCustomerM {

    // 优惠券Service
    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "SeoService")
    private SeoService seoService;

    /**
     * 会员服务接口
     */
    @Resource(name = "customerServiceM")
    private CustomerService customerService;

    /**
     * 优惠券券码接口
     */
    @Resource(name = "CouponNoService")
    private CouponNoService couponNoService;

    private static final String CUSTOMERID = "customerId";

    private static final MyLogger LOGGER = new MyLogger(CouponCustomerM.class);

    /**
     * 获取优惠券
     * 
     * @param request
     * @param type
     *            类型
     * @return {@link ModelAndView}
     */
    @RequestMapping("/customer/coupons")
    public ModelAndView queryMyConpons(HttpServletRequest request,
            PageBean pageBean, String type) {
        String typeNew = type;
        ModelAndView mav = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 检查用户是否登录
            if (LoginUtil.checkLoginStatus(request)) {
                mav = new ModelAndView();
                if (typeNew == null || ("").equals(typeNew)) {
                    typeNew = "1";
                }
                resultMap.put("type", typeNew);
                resultMap.put("pb", couponService.myCouponListM((Long) request.getSession().getAttribute(CUSTOMERID),
                        typeNew));
                resultMap.put("status1", couponService.countByCodeStatus(
                        (Long) request.getSession().getAttribute(CUSTOMERID),
                        "1"));
                resultMap.put("status2", couponService.countByCodeStatus(
                        (Long) request.getSession().getAttribute(CUSTOMERID),
                        "2"));
                resultMap.put("status3", couponService.countByCodeStatus(
                        (Long) request.getSession().getAttribute(CUSTOMERID),
                        "3"));
                mav.setViewName("member/mycoupons");
                mav.addAllObjects(resultMap);
            } else {
                // 没登录的用户跳转到登录页面
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            }
            // 跳转到我的优惠券
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
        }
    }

    /**
     * 领取优惠券
     */
    @RequestMapping("/getOffCouponMobile")
    @ResponseBody
    public int getOffCoupon(HttpServletRequest request, Long couponId) {
        int couponResult = 0;
        // 插叙优惠券详细
        Coupon coupon = couponService.searchCouponById(couponId);

        Long cId = (Long) request.getSession().getAttribute(CUSTOMERID);
        if (cId == null) {
            couponResult = 3;
        } else {
            // // 根据主键 获取会员详细信息
            // CustomerAllInfo customer =
            // customerService.queryCustomerById(cId);
            // // 非空验证 用户名
            // if (null != customer.getCustomerUsername() && null !=
            // coupon.getCouponName()) {
            // // 操作日志
            // OperaLogUtil.addOperaLog(request, customer.getCustomerUsername(),
            // "领取优惠券", "优惠券名称【" + coupon.getCouponName() + "】");
            // // 记录日志
            // LOGGER.info("会员【" + customer.getCustomerUsername() +
            // "】领取优惠券成功！");
            // }
            // 查询该用户此优惠券领取的张数
            int counts = couponNoService.selectReadyGet(couponId, cId);
            // 查询优惠券总数
            int countAll = couponNoService.selectCountAllByCouponId(couponId);

            // 如果等于0代表没有领取，可以进行领取
            // 否则提示已经领取过
            if (counts < coupon.getCouponGetNo().intValue()) {
                // 判断可领取张数减去已经被领取张数是否大于0
                // 如果大于0，可以进行领取
                // 否则提示已领取完
                if ((countAll - couponNoService.queryUsedCountByCouponId(
                        couponId).intValue()) > 0) {
                    Date d = new Date();
                    // 如果开始时间 大于当前时间 并且 结束世界晚于当前时间 为有效
                    if (coupon.getCouponStartTime().before(d)
                            && coupon.getCouponEndTime().after(d)) {
                        // 返回劵码
                        CouponNo couponNo = couponNoService
                                .selectNoByCouponIdByStatus(couponId);
                        if (couponNo != null) {
                            // 领取优惠券
                            int count = couponNoService.updateCouponCustomer(
                                    couponNo.getCodeId(), cId);
                            if (count > 0) {
                                // 领取成功
                                couponResult = 1;
                            } else {
                                // 已领取完
                                couponResult = 2;
                            }
                        } else {
                            // 3就代表要跳转到登录页面
                            couponResult = 3;
                        }
                    } else {
                        // 已领取完
                        couponResult = 2;
                    }
                } else {
                    // 已领取完
                    couponResult = 2;
                }
            } else {
                // 已领取过
                couponResult = 4;
            }
        }

        return couponResult;
    }

    /**
     * 跳转到添加优惠券页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/toaddcoupons")
    public ModelAndView addCoupons(HttpServletRequest request) {
        ModelAndView mav = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 检查用户是否登录
            if (LoginUtil.checkLoginStatus(request)) {
                mav = new ModelAndView();
                //记录状态
                resultMap.put("flag",request.getParameter("flag"));
                //发票类型
                resultMap.put("invoiceType",request.getParameter("invoiceType"));
                //发票抬头
                resultMap.put("invoiceTitle",request.getParameter("invoiceTitle"));
                //使用优惠券编号
                resultMap.put("codeNo",request.getParameter("codeNo"));
                resultMap.put("customercon", customerService
                        .selectByPrimaryKey((Long) request.getSession()
                                .getAttribute(CustomerConstants.CUSTOMERID)));
                mav.setViewName("member/my_coupons_add");
                mav.addAllObjects(resultMap);
            } else {
                // 没登录的用户跳转到登录页面
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            }
            // 跳转到我的优惠券
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
        }
    }

    /**
     * 用户自己添加优惠券
     * @param request
     * @param couponCode
     * @return
     */
    @RequestMapping("addcoupons")
    @ResponseBody
    public Object addCoupons(HttpServletRequest request, String couponCode){
        Object flag;
        if (LoginUtil.checkLoginStatus(request)) {
            flag = couponNoService.selectCouponByCode(couponCode,(Long) request.getSession()
                    .getAttribute(CustomerConstants.CUSTOMERID));
        }else {
            // 没登录的用户跳转到登录页面
            flag = "loginm.html?url=/customercenter.html";
        }
        return flag;
    }
}