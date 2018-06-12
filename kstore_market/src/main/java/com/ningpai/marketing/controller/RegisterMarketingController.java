package com.ningpai.marketing.controller;

import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.marketing.bean.RegisterMarketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.util.MenuSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注册营销控制器 Created by Zhoux on 2015/4/1.
 */
@Controller
public class RegisterMarketingController {
    // 促销service
    @Resource(name = "MarketingService")
    private MarketingService marketingService;
    // 优惠券接口
    @Resource(name = "CouponService")
    private CouponService couponService;
    // 会员积分接口
    @Resource(name = "customerPointServiceMapper")
    private CustomerPointServiceMapper customerPointServiceMapper;

    /**
     * 注册营销页
     * 
     * @param request
     * @param response
     * @param flag
     * @return
     */
    @RequestMapping("/registerMarketing")
    public ModelAndView registerMarketing(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "flag", required = false, defaultValue = "0") final Integer flag) {
        MenuSession.sessionMenu(request);
        return new ModelAndView("jsp/coupon/couponregister", "parameter",
                marketingService.findRegisterMarketing()).addObject("coupon", couponService.queryCouponListToBoss()).addObject("flag",flag);

    }

    /**
     * 修改注册送积分信息
     * 
     * @param registerMarketing
     * @return
     */
    @RequestMapping("/updateRegisterCoupon")
    public ModelAndView updateRegisterCoupon(RegisterMarketing registerMarketing) {
        // 插入注册营销的信息
        marketingService.updateRegisterCoupon(registerMarketing);
        // 如果注册营销开关开启
        if (Integer.parseInt(registerMarketing.getIsUsed()) == 1) {
            // 判断积分是否为空
            if (registerMarketing.getRegisterIntegral() == null) {
                // 设置注册积分为0
                customerPointServiceMapper.updateIntegralById(0);
                // 换行
            } else {
                // 设置注册积分
                customerPointServiceMapper.updateIntegralById(registerMarketing
                        .getRegisterIntegral().intValue());
                // 换行
            }
            // 如果注册营销开关关闭
        } else if (Integer.parseInt(registerMarketing.getIsUsed()) == 0) {
            // 设置注册积分为0
            customerPointServiceMapper.updateIntegralById(0);
        }
        // 返回 重定向到“registerMarketing.htm”
        return new ModelAndView(new RedirectView("registerMarketing.htm"));
    }

}
