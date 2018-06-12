/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.regsiter.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.RegisterPoint;
import com.ningpai.other.bean.IntegralSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.login.service.LoginService;
import com.ningpai.m.regsiter.controller.service.RegisterService;
import com.ningpai.marketing.bean.RegisterMarketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.other.util.IPAddress;

/**
 * 手机端注册Controller
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年8月21日 下午5:09:05
 * @version 0.0.1
 */
@Controller
public class RegisterControllerM {

    // spring 注解 会员服务Service
    private CustomerServiceMapper customerServiceMapper;
    private CustomerPointServiceMapper customerPointServiceMapper;

    @Resource(name = "SeoService")
    private SeoService seoService;
    
    @Resource(name="registerServiceM")
    private RegisterService registerService;
    
    @Resource(name="loginServiceM")
    private LoginService loginService;
    
    @Resource(name = "customerServiceM")
    private CustomerService customerService;
    
    @Resource(name = "MarketingService")
    private MarketingService marketingService;
    
    @Resource(name = "CouponNoService")
    private CouponNoService couponNoService;
    
    Date startTime = null;

    Date endTime = null;
    // 是否赠送优惠卷
    int count = 0;
    private static final String CUSTOMERID = "customerId";
    /**
     * 跳转注册页面
     * 
     * @return {@link ModelAndView}
     */
    @RequestMapping("/customer/register")
    public ModelAndView toRegister(String url) {

        return new ModelAndView(CustomerConstants.REGOSTER).addObject("url",url);
    }

    /**
     * 注册
     * 
     * @param request
     * @param allInfo
     * @return
     * @throws ParseException 
     */
    @RequestMapping("/customer/addcustomer")
    public ModelAndView register(HttpServletRequest request, @Valid CustomerAllInfo allInfo, String code) throws ParseException {
        if (allInfo.getCustomerUsername() == null || allInfo.getCustomerPassword() == null || code == null || (code != null && code.trim().length() == 0)) {
            throw new NullPointerException();
        }
        if (!code.equals((int) request.getSession().getAttribute("mcCode") + "")) {
            throw new IllegalArgumentException();
        }
        allInfo.setLoginIp(IPAddress.getIpAddr(request));
        allInfo.setIsSeller("0");
        allInfo.setIsMobile("1");
        allInfo.setLoginTime(new Date());
        customerServiceMapper.addCustomer(allInfo);
        //loginService.checkCustomerExists(request, allInfo.getCustomerUsername(), allInfo.getCustomerPassword());
        Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);
        customerPointServiceMapper.addIntegralByType(customerId, "0");
        customerPointServiceMapper.addIntegralByType(customerId, "1");
        return new ModelAndView("redirect:/customercenter.html");
    }
    
    /**
     * 注册
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     * @throws ParseException 
     */
    @RequestMapping("/register")
    @ResponseBody
    public Object register2(HttpServletRequest request,HttpServletResponse response, String code, String mobile, String password, String repassword, String url, String custId) throws UnsupportedEncodingException, ParseException {
        if (mobile == null || password == null || repassword == null ||code == null || code.trim().length() == 0) {
            throw new NullPointerException();
        }
        //如果URL为空，跳回首页
        if(url == null || "".equals(url)){
            url = "main.html";
        }


        CustomerAllInfo allInfo = new CustomerAllInfo();
        Object flag;
        int exist = registerService.checkCustomerExists(request, response, mobile, password);
        //已存在
        if (exist==1) {
            flag = 1;
        }else if(!code.equals((int) request.getSession().getAttribute("mcCode") + "")){
            //验证码错误
            flag = 2;
        }else{
            allInfo.setCustomerUsername(mobile);
            allInfo.setCustomerPassword(password);
            allInfo.setLoginIp(IPAddress.getIpAddr(request));
            allInfo.setLoginTime(new Date());
            allInfo.setIsSeller("0");
            allInfo.setIsMobile("1");
            customerServiceMapper.addCustomer(allInfo);
            loginService.checkCustomerExists(request, response, mobile, password, null);
            Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);
            if (null != custId && !"null".equals(custId) && !"".equals(custId)) {

                Long cusId = Long.valueOf(custId);
                customerPointServiceMapper.addIntegralByType(cusId, "5");
                // 保存推广注册成功的信息
                registPoint(request, cusId, allInfo.getCustomerUsername());
            }
            customerPointServiceMapper.addIntegralByType(customerId, "0");
//            customerPointServiceMapper.addIntegralByType(customerId, "1");

            // 判断注册送优惠券是否开启
            RegisterMarketing registerMarketing = marketingService.findRegisterMarketing();

            //如果活动开启，发放优惠券
            if (registerMarketing != null && registerMarketing.getIsUsed() != null && Integer.parseInt(registerMarketing.getIsUsed()) == 1) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //活动开始时间，结束时间
                startTime = sdf.parse(registerMarketing.getStartTime());
                endTime = sdf.parse(registerMarketing.getEndTime());
                Date date = new Date();
                // 判断活动开启关闭时间
                if (startTime.before(date) && endTime.after(date)) {
                    // 判断该优惠券是否还有
                    count = couponNoService.selectCouponNoByStatus(registerMarketing.getRegisterCouponId());
                    if (count > 0) {
                        // 根据优惠券id随机获取一张未使用的优惠券码
                        CouponNo couponNo = couponNoService.selectNoByCouponIdByStatus(registerMarketing.getRegisterCouponId());
                        // 获取用户id 把优惠券给用户
                        couponNoService.updateCouponCustomer(couponNo.getCodeId(), customerId);
                    }
                }
            }
            flag = "/"+url.replaceFirst(request.getContextPath()+"/","");
        }
        return flag;
    }

    /**
     * 保存推广链接注册信息
     *
     * @return
     */
    protected int registPoint(HttpServletRequest request, Long customerId, String customerUsername) {
        int result = 0;
        IntegralSet inte = customerPointServiceMapper.findPointSet();
        // 根据ID获取会员对象
        Customer customer = customerPointServiceMapper.selectCusById(customerId);
        // 推广注册信息
        RegisterPoint point = new RegisterPoint();
        point.setRegPointReferee(customer.getCustomerUsername()); // 推荐人
        point.setRegPointRecom(customerUsername);// 被推荐人
        point.setRegPointNumber(inte.getPsetUser()); // 注册赠送的积分
        point.setRegPointTime(new Date()); // 推广注册时间
        result = customerPointServiceMapper.insertRegisterPoint(point);
        return result;
    }
    /**
     * 发送手机验证码
     * 
     * @throws IOException
     */
    @RequestMapping("/sendcodereg")
    @ResponseBody
    public int sendcodeReg(HttpServletRequest request, HttpServletResponse response, String mobile,String checkCode)
            throws IOException {
        int exist = registerService.checkCustomerExists(request, response, mobile, null);
        String patchca = (String) request.getSession().getAttribute("PATCHCA");
        //已存在
        if (exist==1) {
            return 2;
        }if(patchca!=null&&patchca!=""&&patchca.equals(checkCode)){
            request.getSession().removeAttribute("patchca");
            return customerService.sendPost(request, mobile);
        }else{
            return 3;
        }
    }
    
    public CustomerServiceMapper getCustomerServiceMapper() {
        return customerServiceMapper;
    }

    @Resource(name = "customerServiceMapper")
    public void setCustomerServiceMapper(CustomerServiceMapper customerServiceMapper) {
        this.customerServiceMapper = customerServiceMapper;
    }

    public CustomerPointServiceMapper getCustomerPointServiceMapper() {
        return customerPointServiceMapper;
    }

    @Resource(name = "customerPointServiceMapper")
    public void setCustomerPointServiceMapper(CustomerPointServiceMapper customerPointServiceMapper) {
        this.customerPointServiceMapper = customerPointServiceMapper;
    }
}
