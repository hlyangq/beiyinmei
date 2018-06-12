/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.site.customer.controller;

import com.ningpai.common.util.EmailUtils;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.site.customer.vo.CustomerConstantStr;
import com.ningpai.site.threepart.util.StringUtil;
import com.ningpai.temp.service.MegawizardService;
import com.ningpai.temp.service.TempService;
import com.ningpai.util.MyLogger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 找回密码
 *
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @since 2014年7月18日 上午10:08:57
 */
@Controller
public class FindPwdController {
    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(FindPwdController.class);

    /**
     * spring 注解 会员service
     */
    private CustomerServiceInterface customerServiceInterface;

    /**
     * 邮件
     */
    private EmailUtils emailUtils;

    /**
     * 获取头尾
     */
    private TopAndBottomService topAndBottomService;

    /**
     * 页面说明Service
     */
    private MegawizardService megawizardSerivce;

    /**
     * 模板设置业务类
     */
    private TempService tempService;

    /**
     * 跳转找回密码
     *
     * @return
     */
    @RequestMapping("/findpwd")
    public ModelAndView toFindPwd() {
        // return topAndBottomService.getBottom(new
        // ModelAndView("/customer/findpwd").addObject("type", "mobile"));
        return topAndBottomService.getBottom(new ModelAndView("/customer/find_code1").addObject("type", "mobile"));
    }

    /**
     * 验证用户
     * @param request
     * @param type 验证类型
     * @param msg 验证码验证结果
     * @return
     */
    @RequestMapping("/validuser")
    public ModelAndView validUser(HttpServletRequest request, String type, String msg, String enterValue) {
        request.getSession().setAttribute("flg", "0");
        if(enterValue != null ){
            String patchca = (String) request.getSession().getAttribute("PATCHCA");
            if(!enterValue.equals(patchca)){
                return new ModelAndView(new RedirectView(request.getContextPath() + "/validuser-1.html"));
            }
        }
        try {
            // 根据用户名查询用户简单信息

            CustomerAllInfo cust = customerServiceInterface.selectCustomerByUname((String) request.getSession().getAttribute("uname"));
            request.getSession().setAttribute("user", cust);
            request.getSession().setAttribute("uId", cust.getCustomerId());
            if (type == null) {
                request.getSession().setAttribute("utype", "mobile");
            } else {
                request.getSession().setAttribute("utype", type);
            }
        } catch (Exception e) {
            LOGGER.error("验证用户错误", e);
            return topAndBottomService.getBottom(new ModelAndView("/customer/find_code2")).addObject("explain",
                    megawizardSerivce.selectByType(4, Long.parseLong(tempService.getCurrTemp().getTempId() + "")));
        }
        // return topAndBottomService.getBottom(new
        // ModelAndView("/customer/validuser")).addObject("explain",
        // megawizardSerivce.selectByType(4,
        // Long.parseLong(tempService.getCurrTemp().getTempId() + "")));
        // 根据页面类型和模板ID查询单个页面说明
        String message=null;
        if("1".equals(msg)){
            message="手机验证码错误";
        }else if("2".equals(msg)){
            message="手机验证码已经失效";
        }else if("3".equals(msg)){
            message="请输入手机验证码";
        }
        return topAndBottomService.getBottom(new ModelAndView("/customer/find_code2")).addObject("explain",
                megawizardSerivce.selectByType(4, Long.parseLong(tempService.getCurrTemp().getTempId() + "")))
                .addObject("msg", message);
    }

    /**
     * 跳转重置密码
     *
     * @return
     */
    @RequestMapping("/resetpwd")
    public ModelAndView toReSetPwd(HttpServletRequest request, String code, String emailCode) {
        // return topAndBottomService.getBottom(new
        // ModelAndView("/customer/updatepwd"));
        if (StringUtils.isBlank(code) && StringUtils.isBlank(emailCode)) {
            return new ModelAndView(new RedirectView(request.getContextPath() + "/validuser-1.html"));
        }
        int result = 0;

        if (StringUtils.isNotBlank(code)) {
            result = customerServiceInterface.getMCode(request, code);
        } else {
            result = validEmailCode(request, emailCode);
        }

        if (result == 0) {
        //    return new ModelAndView(new RedirectView(request.getContextPath() + "/validuser.html")).addObject("msg", "手机验证码错误");
            return new ModelAndView(new RedirectView(request.getContextPath() + "/validuser-1.html"));
        } else if (result == -1) {
          //  return new ModelAndView(new RedirectView(request.getContextPath() + "/validuser-2.html")).addObject("msg", "手机验证码已经失效");
            return new ModelAndView(new RedirectView(request.getContextPath() + "/validuser-2.html"));
        } else if (result == -2) {
            //return new ModelAndView(new RedirectView(request.getContextPath() + "/validuser-3.html")).addObject("msg", "请输入手机验证码");
            return new ModelAndView(new RedirectView(request.getContextPath() + "/validuser-3.html"));
        }
        request.getSession().setAttribute("flg", "1");
        return topAndBottomService.getBottom(new ModelAndView("/customer/find_code3"));
    }

    /**
     * 通过邮箱找回密码时
     * @param request
     * @param emailCode
     * @return
     */
    private int validEmailCode(HttpServletRequest request, String emailCode) {
        Object code = request.getSession().getAttribute("email_checkCode");
        if (code == null) {
            return 0;
        }
        if (Objects.equals(String.valueOf(code), emailCode)) {
            request.getSession().setAttribute("email_checkCode",null);
            return 1;
        }
        return 0;
    }

    /**
     * 跳转重置密码
     *
     * @return
     */
    @RequestMapping("/updatesucess")
    public ModelAndView updateSucess(HttpServletRequest request) {
        // return topAndBottomService.getBottom(new
        // ModelAndView("/customer/updatesucess"));
        return topAndBottomService.getBottom(new ModelAndView("/customer/find_code4"));
    }

    /**
     * 重置密码
     *
     * @return
     */
    @RequestMapping("/setpwd")
    @ResponseBody
    public int reSetPwd(HttpServletRequest request, CustomerAllInfo customer) {
//        customer.setCustomerId((Long) request.getSession().getAttribute("uId"));
        Long customerId = (Long) request.getSession().getAttribute("uId");
        String flg = (String)request.getSession().getAttribute("flg");
        if(!"1".equals(flg)){
            return 4;
        }
        request.getSession().setAttribute("flg", "0");
        // 非空验证 会员用户名
        if (null != customer.getCustomerUsername()) {
            LOGGER.info("重置会员【" + customer.getCustomerUsername() + "】的密码！");
        }
        // 修改用户密码
        return customerServiceInterface.updatePwdInfo(customerId, customer.getCustomerPassword());
    }

    /**
     * 验证用户名存在性
     *
     * @return
     */
    @RequestMapping("/checkusernameflag")
    @ResponseBody
    public Long checkUsernameFlag(HttpServletRequest request, String username,String enterValue) {
        String patchca = (String) request.getSession().getAttribute("PATCHCA");
        if (null != enterValue) {

            LOGGER.info("code in session is :" + patchca);
            // 验证页面传过来的验证码是否与session保存的验证码相等
            if(!enterValue.equals(patchca)){
                return 2L;
            }
        }
        // 验证用户名 是否存在
        if (null != username) {
            LOGGER.info("验证用户名【" + username + "】是否存在！");
        }
        request.getSession().setAttribute("uname", username);
        // 检查用户名存在性
        return customerServiceInterface.checkUsernameFlag(username);
    }

    /**
     * 发送找回密码邮件
     *
     * @param request
     */
    @RequestMapping("/sendeamil")
    @ResponseBody
    public int sendEamil(HttpServletRequest request, String email) {
        // 验证是否登录
        if (request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID) != null) {
            // 获得用户信息
            CustomerAllInfo user = (CustomerAllInfo) request.getSession().getAttribute("cust");


            // 用户编号
            user.setCustomerId((Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID));
            // 邮件地址
            user.setInfoEmail(email);
            request.setAttribute("email", email);
            // 账户中心 验证邮件
            if (emailUtils.sendBindEmail(request, user) == 1) {
                // 修改找回密码Code
                return customerServiceInterface.updateFindPwdCode(user);
            } else {
                return 0;
            }
        } else {
            return -1;
        }

    }

    /**
     * 发送找回密码邮件
     *
     * @param request
     */
    @RequestMapping("/newsendeamil")
    @ResponseBody
    public int newsendeamil(HttpServletRequest request) {
        // 获得用户信息
        CustomerAllInfo user = (CustomerAllInfo) request.getSession().getAttribute("user");
        if (null != user) {
            request.setAttribute("email", user.getInfoEmail());
            // 账户中心 验证邮件
            if (emailUtils.forgetsendBindEmail(request, user) == 1) {
                // 修改找回密码Code
                return customerServiceInterface.updateFindPwdCode(user);
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
     * 发送找回密码邮件成功
     *
     * @param request
     */
    @RequestMapping("/sendsuccess")
    public ModelAndView sendSucess(HttpServletRequest request, String email) {
        // return topAndBottomService.getBottom(new
        // ModelAndView("/customer/sendsuccess"));
        return topAndBottomService.getBottom(new ModelAndView("/customer/sendemailsucc"));
    }

    /**
     * 验证邮件
     *
     * @param request
     */
    @RequestMapping("/validpwdemail")
    public ModelAndView validPwdEmail(HttpServletRequest request, String checkCode, Long d) {
        // 验证邮件
        int result = customerServiceInterface.validPwdEmail(request, checkCode, d);
        if (result == 1) {
            return topAndBottomService.getBottom(new ModelAndView("redirect:resetpwd.htm"));
        }
        return topAndBottomService.getBottom(new ModelAndView("/customer/sendsuccess").addObject("eFlag", result));
    }

    public CustomerServiceInterface getCustomerServiceInterface() {
        return customerServiceInterface;
    }

    /**
     * spring 注入属性
     *
     * @param customerServiceInterface
     */
    @Resource(name = "customerServiceSite")
    public void setCustomerServiceInterface(CustomerServiceInterface customerServiceInterface) {
        this.customerServiceInterface = customerServiceInterface;
    }

    public TopAndBottomService getTopAndBottomService() {
        return topAndBottomService;
    }

    @Resource(name = "TopAndBottomService")
    public void setTopAndBottomService(TopAndBottomService topAndBottomService) {
        this.topAndBottomService = topAndBottomService;
    }

    public EmailUtils getEmailUtils() {
        return emailUtils;
    }

    @Resource(name = "emailUtilsSite")
    public void setEmailUtils(EmailUtils emailUtils) {
        this.emailUtils = emailUtils;
    }

    public MegawizardService getMegawizardSerivce() {
        return megawizardSerivce;
    }

    @Resource(name = "MegawizardService")
    public void setMegawizardSerivce(MegawizardService megawizardSerivce) {
        this.megawizardSerivce = megawizardSerivce;
    }

    public TempService getTempService() {
        return tempService;
    }

    @Resource(name = "TempService")
    public void setTempService(TempService tempService) {
        this.tempService = tempService;
    }
}
