/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.site.register.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ningpai.common.util.BaseSiteController;
import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.marketing.bean.RegisterMarketing;
import com.ningpai.marketing.service.MarketingService;

import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.util.MyLogger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.RegisterPoint;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.other.bean.IntegralSet;
import com.ningpai.other.util.IPAddress;
import com.ningpai.site.login.service.LoginService;
import com.ningpai.system.service.AuthService;

/**
 * 前台注册Controller
 *
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @since 2014年4月16日 下午5:11:01
 */
@Controller
public class RegisterController extends BaseSiteController{

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(RegisterController.class);

    private static final String CUSTOMERID = "customerId";

    private static final String REGISTER_SUCCESS = "/register/success";

    // Date date = new Date();
    Date startTime = null;

    Date endTime = null;

    // 是否赠送优惠卷
    int count = 0;

    // 是否有赠送的积分
    int pointCount = 0;

    // spring 注解 会员服务Service
    private CustomerServiceMapper customerServiceMapper;

    /**
     * 站点设置服务层接口接口
     */
    @Resource(name = "basicSetService")
    private BasicSetService basicSetService;

    private LoginService loginService;

    private CustomerPointServiceMapper customerPointServiceMapper;

    @Resource(name = "MarketingService")
    private MarketingService marketingService;

    @Resource(name = "CouponNoService")
    private CouponNoService couponNoService;

    // 获取头尾
    private TopAndBottomService topAndBottomService;

    // 获取已启用的第三方登录接口
    @Resource(name = "authService")
    private AuthService authService;

    @Resource(name = "customerServiceSite")
    private CustomerServiceInterface customerServiceInterface;

    /**
     * 跳转注册页面
     *
     * @return ModelAndView
     */
    @RequestMapping("/customer/register")
    public ModelAndView jumpRegister() {
        // return topAndBottomService.getBottom(new
        // ModelAndView("/register/register").addObject("t",
        // authService.findByShow()));
        return topAndBottomService.getBottom(new ModelAndView("/register/registerfirst").addObject("t", authService.findByShow()));
    }

    /**
     * 跳转注册页面第二步
     * @param request HttpServletRequest对象
     * @param mobile 手机号
     * @return 注册页面第二步页面
     */
    @RequestMapping("/customer/registersecond")
    public ModelAndView registersecond(HttpServletRequest request,String mobile) {
        if(request.getSession().getAttribute("mobile") != null){
            if(mobile.equals(request.getSession().getAttribute("mobile").toString())){
                return topAndBottomService.getBottom(new ModelAndView("/register/registersecond").addObject("mobile",mobile).addObject("sign",0));
            }else{
                return new ModelAndView(new RedirectView(request.getContextPath() + "/index.html"));
            }
        }else{
            return new ModelAndView(new RedirectView(request.getContextPath() + "/index.html"));
        }
    }

    /**
     * 跳转注册页面第三步
     * @param request HttpServletRequest对象
     * @return 注册页面第三步页面
     * KKK site新用户注册
     */
    @RequestMapping("/customer/registerthird")
    public ModelAndView registerThird(HttpServletRequest request, String checkCode) {
        if(request.getSession().getAttribute("mcCode") == null || checkCode == null){
            return topAndBottomService.getBottom(new ModelAndView("/register/registersecond").addObject("sign",1));
        }else{
            // 验证码验证, 不通过 跳转到上一页面
            String patchca = String.valueOf(request.getSession().getAttribute("mcCode"));
            if(!checkCode.equals(patchca)){
                //验证码错误超过6次则清空，前台需重新获取验证码
                if(request.getSession().getAttribute("submitCount") == null){
                    request.getSession().setAttribute("submitCount", 1);
                    return topAndBottomService.getBottom(new ModelAndView("/register/registersecond").addObject("sign",1));
                }else{
                    request.getSession().setAttribute("submitCount", (int)request.getSession().getAttribute("submitCount")+1);
                    int submitCount = (int)request.getSession().getAttribute("submitCount");
                    if(submitCount > 5){
                        // 清掉验证码
                        request.getSession().setAttribute("mcCode", null);
                        request.getSession().setAttribute("submitCount", null);
                        return topAndBottomService.getBottom(new ModelAndView("/register/registersecond").addObject("sign",2));
                    }else{
                        return topAndBottomService.getBottom(new ModelAndView("/register/registersecond").addObject("sign",1));
                    }
                }
            }

        }
        // 清掉验证码
        request.getSession().setAttribute("mcCode", null);

        if(request.getSession().getAttribute("mobile") != null){
            String real = request.getSession().getAttribute("mobile").toString();
            if(!real.equals(request.getSession().getAttribute("userMobile").toString())){
                return topAndBottomService.getBottom(new ModelAndView("/register/registersecond").addObject("mobile",real).addObject("sign",1));
            }else{
                return topAndBottomService.getBottom(new ModelAndView("/register/registerthird").addObject("mobile",real));
            }
        }else{
            return new ModelAndView(new RedirectView(request.getContextPath() + "/index.html"));
        }
    }

    /**
     * 注册第四步跳转
     * @param request HttpServletRequest对象
     * @return 注册第四步页面
     */
    @RequestMapping("/customer/registerfourth")
    public ModelAndView registerFourth(HttpServletRequest request) {
        if(request.getSession().getAttribute("mobile") != null){
            String real = request.getSession().getAttribute("mobile").toString();
            return topAndBottomService.getBottom(new ModelAndView("/register/registerfourth").addObject("mobile",real)).addObject("cust", request.getSession().getAttribute("cust"));
        }else{
            return new ModelAndView(new RedirectView(request.getContextPath() + "/index.html"));
        }
    }

    /**
     * 获取当前客户端的IP 传到页面
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("checkRegsiter")
    public ModelAndView checkRegsiter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String getIp = IPAddress.getIpAddr(request);
        // 网页面写参数
        PrintWriter out = response.getWriter();
        // 把获取的IP传到页面
        out.print(getIp);
        return null;
    }

    /**
     * 前端注册第一步，检查手机号是否已注册
     * @param request
     * @param mobile 注册手机号
     * @param custId 推荐会员的会员id
     * @param checkCode 输入的验证码
     * @return
     */
    @RequestMapping("/registerFirst")
    public ModelAndView registerFirst(HttpServletRequest request, String mobile, String custId, String checkCode, Model model){
        request.getSession().setAttribute("mobile", mobile);
        if(custId.equals("null")){
            request.getSession().setAttribute("custId", null);
        }else{
            request.getSession().setAttribute("custId", custId);
        }
        // 验证 验证码是否正确
        // 获取sessoin中的验证码
        String patchca = (String) request.getSession().getAttribute("PATCHCA");
        if (checkCode == null || patchca == null || !checkCode.equals(patchca)) {
            model.addAttribute("codeError", "yes");
            model.addAttribute("msg", "验证码错误");
            return topAndBottomService.getBottom(new ModelAndView("/register/registerfirst"));
        }
        // 清掉session中的验证码
        request.getSession().setAttribute("PATCHCA", null);

        // 验证该手机号是否存在
        Long result = customerServiceMapper.checkMobileExist(mobile);
        if (result == 1) {
            model.addAttribute("mobileError", "yes");
            model.addAttribute("msg", "该手机号已被注册");
            return topAndBottomService.getBottom(new ModelAndView("/register/registerfirst"));
        }

        return new ModelAndView(new RedirectView(request.getContextPath() + "/registersecond.html")).addObject("mobile", mobile);
    }

    /**
     * 前台用户注册时发送手机验证码
     * @param request HttpServletRequest对象
     * @param mobile 手机号
     * @return 发送结果
     * @throws IOException
     */
    @RequestMapping("/regsendcode")
    @ResponseBody
    public int regsendcode(HttpServletRequest request,String mobile,String checkCode) throws IOException{
        String patchca = (String) request.getSession().getAttribute("PATCHCA");
        if(checkCode!=null&&checkCode.equals(patchca)){
            request.getSession().removeAttribute("PATCHCA");

            int a= customerServiceInterface.newsendPost(request, mobile);
            if(a==1){
                request.getSession().setAttribute("mobilePhone",mobile);
                return a;
            }else{
                return a;
            }
        }else{
            return -1;
        }
    }

    /**
     * 注册第一步手机号码填写是去焦点判断存在
     * @param mobile 手机号码
     * @return 是否存在
     */
    @RequestMapping("/checkMobileExist")
    @ResponseBody
    public Long checkMobileNumberExist(String mobile){
        // 验证该手机号是否存在
        return customerServiceMapper.checkMobileExist(mobile);
    }

    /**
     * 校验验证码是否一致
     * @param request HttpServletRequest对象
     * @param code 验证码
     * @return 比较结果
     * @throws IOException
     */
    @RequestMapping("/checkregcaptcha")
    @ResponseBody
    public int checkCaptcha(HttpServletRequest request,String code) throws IOException{
        if(!code.equals((int) request.getSession().getAttribute("mcCode") + "")){
            return 0;
        }else{
            return 1;
        }
    }

    /**
     * 新注册会员（手机验证码注册）
     * @param request HttpServletRequest对象
     * @param allInfo 会员所有信息
     * @return 注册成功页面
     */
    @RequestMapping("/customer/addcust")
    public ModelAndView addcust(HttpServletRequest request, @Valid CustomerAllInfo allInfo) {
        if (allInfo.getCustomerUsername() == null || allInfo.getCustomerPassword() == null) {
            throw new NullPointerException();
        }
        Object mobile = request.getSession().getAttribute("mobile");
        if(mobile != null && allInfo.getInfoMobile().equals(mobile.toString())){
            allInfo.setLoginIp(IPAddress.getIpAddr(request));
            allInfo.setIsSeller("0");
            allInfo.setIsMobile("1");
            // 判断用户名是否存在
            Long isUsernameFlag = customerServiceMapper.selectCustomerByName(allInfo.getCustomerUsername());
            // 如果不存在则注册
            if (isUsernameFlag == 0) {
                // 注册成功返回值
                String pwd = allInfo.getCustomerPassword();
                int register = customerServiceMapper.addCustomer(allInfo);
                // 非空验证 推荐用户 被推荐用户
                if (null != allInfo.getCustomerUsername() && register == 1) {
                    LOGGER.info("会员【" + allInfo.getCustomerUsername() + "】注册成功。");
                }
                loginService.checkCustomerExists(request, allInfo.getCustomerUsername(), pwd);

                // 判断是否是会员推荐的注册链接
                Object custId = request.getSession().getAttribute("custId");

                if (null != custId && !"".equals(custId.toString()) && register == 1) {

                    Long cusId = Long.valueOf(custId.toString());
                    customerPointServiceMapper.addIntegralByType(cusId, "5");
                    // 保存推广注册成功的信息
                    registPoint(request, cusId, allInfo.getCustomerUsername());
                }
                customerPointServiceMapper.addIntegralByType(customerId(), "0");
                customerPointServiceMapper.addIntegralByType(customerId(), "1");


                // 判断注册送优惠券是否开启
                RegisterMarketing registerMarketing = marketingService.findRegisterMarketing();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (registerMarketing != null && registerMarketing.getIsUsed() != null && Integer.parseInt(registerMarketing.getIsUsed()) == 1) {
                    try {
                        startTime = sdf.parse(registerMarketing.getStartTime());
                        endTime = sdf.parse(registerMarketing.getEndTime());
                    } catch (ParseException e) {

                    }

                    Date date = new Date();
                    // 判断活动开启关闭时间
                    if (startTime.before(date) && endTime.after(date)) {
                        // 判断该优惠券是否还有
                        count = couponNoService.selectCouponNoByStatus(registerMarketing.getRegisterCouponId());
                        if (count > 0) {
                            // 根据优惠券id随机获取一张未使用的优惠券码
                            CouponNo couponNo = couponNoService.selectNoByCouponIdByStatus(registerMarketing.getRegisterCouponId());
                            // 获取用户id 把优惠券给用户
                            couponNoService.updateCouponCustomer(couponNo.getCodeId(), customerId());
                            // 如果注册成功赠送了积分 就＋1 用于页面跳转
                            pointCount++;
                        }
                    }
                    return new ModelAndView(new RedirectView(request.getContextPath() + "/success.html"));
                }else{
                    return new ModelAndView(new RedirectView(request.getContextPath() + "/registerfourth.html"));
                }
            } else {
                // 非法操作，跳至首页
                return new ModelAndView(new RedirectView(request.getContextPath() + "/index.html"));
            }
        }else{
            return new ModelAndView(new RedirectView(request.getContextPath() + "/index.html"));
        }
    }

    /**
     * 注册
     *
     * @param request
     * @param allInfo
     * @return
     */
    @RequestMapping("/customer/addcustomer")
    public ModelAndView register(HttpServletRequest request, @Valid CustomerAllInfo allInfo, Long cusId, String user, String psd, String varification) {
        if (allInfo.getCustomerUsername() == null || allInfo.getCustomerPassword() == null) {
            throw new NullPointerException();
        }
        allInfo.setLoginIp(IPAddress.getIpAddr(request));
        allInfo.setIsSeller("0");

        // 判断用户名是否存在
        Long isUsernameFlag = customerServiceMapper.selectCustomerByName(allInfo.getCustomerUsername());
        // 验证码
        String patchca = (String) request.getSession().getAttribute("PATCHCA");
        // 如果不存在则注册，验证码是否一致
        // if (isUsernameFlag == 0 || patchca.equals(varification)){
        if (isUsernameFlag == 0 && patchca.equals(varification)) {
            //先保存下用户的铭文密码,因为下面的checkCustomerExists()要用到,
            // 里面涉及到session设置,代码就不重构了...坑爹...
            // ---liangck于2015年10月20日,修改用户密码加密方式
            String userpwd = allInfo.getCustomerPassword();
            // 注册成功返回值
            int register = customerServiceMapper.addCustomer(allInfo);
            // 非空验证 推荐用户 被推荐用户
            if (null != allInfo.getCustomerUsername() && register == 1) {
                LOGGER.info("会员【" + allInfo.getCustomerUsername() + "】注册成功。");
            }
            loginService.checkCustomerExists(request, allInfo.getCustomerUsername(), userpwd);

            // 判断注册送优惠券是否开启
            RegisterMarketing registerMarketing = marketingService.findRegisterMarketing();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                startTime = sdf.parse(registerMarketing.getStartTime());
                endTime = sdf.parse(registerMarketing.getEndTime());
            } catch (ParseException e) {
                // 日志
                // String operaCode = "注册送优惠券出错";
                // String operaContent =
                // request.getSession().getAttribute(CustomerConstantStr.OPERAPATH)
                // + "注册送优惠券出错";
            }
            // 获取当前系统时间
            Date date = new Date();
            boolean regMarketingEnable = registerMarketing != null && registerMarketing.getIsUsed() != null && Integer.parseInt(registerMarketing.getIsUsed()) == 1;
            // 判断是否是会员推荐的注册链接
            if (null != cusId && register == 1) {
                customerPointServiceMapper.addIntegralByType(cusId, "5");
                // 保存推广注册成功的信息
                registPoint(request, cusId, allInfo.getCustomerUsername());
            }
            // 注册营销是否开启
            if (regMarketingEnable && startTime.before(date) && endTime.after(date)) {
                customerPointServiceMapper.addIntegralByType(customerId(), "0");
                // 如果注册成功赠送了积分 就＋1 用于页面跳转
                pointCount++;
            } else {
                // 没有开始设置注册积分为0keyi
                customerPointServiceMapper.updateIntegralById(0);
                // 根据类型不同添加不同的积分数量
                customerPointServiceMapper.addIntegralByType(customerId(), "0");

            }

            if (regMarketingEnable) {
                // 判断活动开启关闭时间
                if (startTime.before(date) && endTime.after(date)) {
                    // 判断该优惠券是否还有
                    count = couponNoService.selectCouponNoByStatus(registerMarketing.getRegisterCouponId());
                    if (count > 0) {
                        // 根据优惠券id随机获取一张未使用的优惠券码
                        CouponNo couponNo = couponNoService.selectNoByCouponIdByStatus(registerMarketing.getRegisterCouponId());
                        // 获取用户id 把优惠券给用户
                        couponNoService.updateCouponCustomer(couponNo.getCodeId(), customerId());
                    }
                } else {
                    if (registerMarketing.getRegisterIntegral() != null && registerMarketing.getRegisterIntegral() > 0) {
                        // 设置注册积分为0
                        customerPointServiceMapper.updateIntegralById(0);
                        // 换行
                    }
                }
            }
            return new ModelAndView(new RedirectView(request.getContextPath() + "/success.html"));
        } else {
            // 非法操作，跳至首页
            return new ModelAndView(new RedirectView(request.getContextPath() + "/index.html"));
        }

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
        // 非空验证 推荐用户 被推荐用户
        if (null != customer.getCustomerUsername() && null != customerUsername && 1 == result) {
            LOGGER.info("会员【" + customer.getCustomerUsername() + "】推荐用户【" + customerUsername + "】注册成功，并且成功获取积分奖励！");
        }
        return result;
    }

    /**
     * 注册成功
     *
     * @return
     */
    @RequestMapping("/customer/success")
    public ModelAndView success(HttpServletRequest request) {
        RegisterMarketing registerMarketing = marketingService.findRegisterMarketing();
        if (count > 0 || pointCount > 0) {
            if (Integer.parseInt(registerMarketing.getIsUsed()) == 1) {
                Date date = new Date();
                if (startTime.before(date) && endTime.after(date)) {
                    return topAndBottomService.getBottom(new ModelAndView("/register/registersuc").addObject("registerIntegral", registerMarketing.getRegisterIntegral())
                            .addObject("basicSet", basicSetService.findBasicSet())
                            .addObject("couponId", registerMarketing.getRegisterCouponId()));
                } else {
                    return new ModelAndView(new RedirectView(request.getContextPath() + "/registerfourth.html"));
                }
            } else {
                return new ModelAndView(new RedirectView(request.getContextPath() + "/registerfourth.html"));
            }
        } else {
            return new ModelAndView(new RedirectView(request.getContextPath() + "/registerfourth.html"));
        }
    }

    public CustomerServiceMapper getCustomerServiceMapper() {
        return customerServiceMapper;
    }

    @Resource(name = "customerServiceMapper")
    public void setCustomerServiceMapper(CustomerServiceMapper customerServiceMapper) {
        this.customerServiceMapper = customerServiceMapper;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    @Resource(name = "loginServiceSite")
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public CustomerPointServiceMapper getCustomerPointServiceMapper() {
        return customerPointServiceMapper;
    }

    @Resource(name = "customerPointServiceMapper")
    public void setCustomerPointServiceMapper(CustomerPointServiceMapper customerPointServiceMapper) {
        this.customerPointServiceMapper = customerPointServiceMapper;
    }

    public TopAndBottomService getTopAndBottomService() {
        return topAndBottomService;
    }

    @Resource(name = "TopAndBottomService")
    public void setTopAndBottomService(TopAndBottomService topAndBottomService) {
        this.topAndBottomService = topAndBottomService;
    }

}
